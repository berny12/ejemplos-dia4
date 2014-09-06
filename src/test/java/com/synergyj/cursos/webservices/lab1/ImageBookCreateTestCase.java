/**
 * Fecha de creación: 08/02/2011 13:30:38
 * 
 * Copyright (c) 2011 SynergyJ. Todos los derechos reservados.
 * 
 * Este software es información pueder ser mofificado, utilizado haciendo referencia al autor
 * intelectual.
 */
package com.synergyj.cursos.webservices.lab1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.http.HTTPBinding;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synergyj.cursos.webservices.entidades.Book;
import com.synergyj.cursos.webservices.entidades.Resultado;

/**
 * Test case empleado para realizar peticiones POST en un WS tipo restful.
 * @author Jorge Rodríguez Campos (jorge.rodriguez@synergyj.com)
 * @version 1.0
 */
public class ImageBookCreateTestCase {

  /**
   * Logger para todas las instancias de la clase
   */
  private static final Logger logger=LoggerFactory.getLogger(ImageBookCreateTestCase.class);

  private static final String IMAGE_DIR="cliente-libros/imagenes";

  List<Book> libros;

  /**
   * Inicializando lista de libros
   */
  @Before
  public void setup() {

    Book book;
    logger.debug("inicializando catalogo de libros");

    libros=new ArrayList<Book>();
    book=new Book();
    book.setTitulo("Java Web Services: Up and Running");
    book.setAutor("Martin Kalin");
    book.setFileName("ws-up-and-running.jpg");
    libros.add(book);
    // libro 2
    book=new Book();
    book.setTitulo("Restful Web Services");
    book.setAutor("David Heinemeier Hansson");
    book.setFileName("restful-ws.jpg");
    libros.add(book);
    // libro 3
    book=new Book();
    book.setTitulo("Beginning Database Design");
    book.setAutor("Clare Churcher ");
    book.setFileName("db-design.jpg");
    libros.add(book);

  }

  /**
   * invoca a un Restful WS via POST para agregat un nuevo libro al catálogo.
   */
  @Test
  public void addImageBook() throws Exception {

    QName serviceName;
    QName port;
    String namespaceURL;
    String endpoint;
    Service service;
    Dispatch<Source> dispatch;
    Source source;
    StreamSource respuesta;

    logger.debug("Construyendo dispatcher para realizar la peticion.");

    namespaceURL="http://www.synergyj.com/cursos/webservices/xmlbeans/books";
    serviceName=new QName("ImageBookService", namespaceURL);
    port=new QName("ImageBookServicePort", namespaceURL);

    endpoint="http://127.0.0.1:9878/searchBooks";
    service=Service.create(serviceName);
    service.addPort(port, HTTPBinding.HTTP_BINDING, endpoint);
    dispatch=service.createDispatch(port, Source.class, Service.Mode.PAYLOAD);

    logger.debug("Obteniendo los datos del libro a agregar");
    source=obtenDatosLibro();

    logger.debug("invocando al WS..");

    respuesta=(StreamSource) dispatch.invoke(source);
    logger.debug("mostrando respuesta del WS");
    muestraRespuesta(respuesta);

  }

  /**
   * @param respuesta
   */
  private void muestraRespuesta(StreamSource respuesta) throws Exception {

    Resultado resultado;

    JAXBContext context=JAXBContext.newInstance(Resultado.class);

    Unmarshaller unma=context.createUnmarshaller();

    resultado=(Resultado) unma.unmarshal(respuesta);

    logger.debug("Resultado obtenido: {}", resultado.getRespuesta());
  }

  /**
   * Obtiene los datos de un libro par ser agregados al catalogo via WS.
   * @return
   */
  private Source obtenDatosLibro() {

    // obtiene un libro aleatorio
    Book book=libros.get((int) (Math.random() * libros.size()));
    book.setImagen(getImage(File.separator + book.getFileName()));

    JAXBContext contexto;
    JAXBSource source=null;
    try
    {
      contexto=JAXBContext.newInstance(Book.class);
      source=new JAXBSource(contexto, book);
    } catch (JAXBException e)
    {
      logger.error("Huboi un error al serializar el libro", e);
    }
    return source;
  }

  /**
   * @param fileName
   * @return
   */
  private byte[] getImage(String fileName) {
    File file;
    FileInputStream fis=null;
    byte[] bytes;

    try
    {
      file=new File(IMAGE_DIR, fileName);
      fis=new FileInputStream(file);
      bytes=new byte[(int) file.length()];
      fis.read(bytes);
      return bytes;
    } catch (IOException e)
    {
      throw new RuntimeException(e);
    } finally
    {
      try
      {
        fis.close();
      } catch (IOException e)
      {
        throw new RuntimeException(e);
      }

    }
  }

}
