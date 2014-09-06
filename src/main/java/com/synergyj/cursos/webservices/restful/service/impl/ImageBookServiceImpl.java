/**
 * ImageBookServiceImpl.java Fecha de creación: 15/02/2013 , 18:08:00
 * 
 * Copyright (c) 2013 Synergyj. Todos los derechos reservados.
 * 
 * Este software es información confidencial, propiedad del Synergyj. Esta información
 * confidencial no deberá ser divulgada y solo se podrá utilizar de acuerdo a los términos que
 * determine la empresa.
 */
package com.synergyj.cursos.webservices.restful.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synergyj.cursos.webservices.entidades.Book;
import com.synergyj.cursos.webservices.entidades.CatalogoLibro;
import com.synergyj.cursos.webservices.entidades.Resultado;
import com.synergyj.cursos.webservices.restful.service.ImageBookService;

/**
 * TODO [Agregar documentacion de la clase]
 * @author Juan Manuel Reyes Medina @jkingsj (jmanuel.reyes@synergyj.com)
 * @version 1.0
 * 
 */
public class ImageBookServiceImpl implements ImageBookService {

  private static final String IMAGE_DIR="server-libros/imagenes";

  private static final String XML_CATALOGO="server-libros/catalogo.xml";

  private static final Logger logger=LoggerFactory.getLogger(ImageBookServiceImpl.class);

  CatalogoLibro catalogo;
  JAXBContext contexto;
  
  public ImageBookServiceImpl() {
    File file;
    logger.debug("inicializando catalogo de libros");

    try
    {
      contexto=JAXBContext.newInstance(CatalogoLibro.class);
      file=new File(XML_CATALOGO);
     
      if (!file.exists())
      {
        logger.debug("No existen elementos en el catalogo");
        catalogo=new CatalogoLibro();
        catalogo.setLibros(new ArrayList<Book>());
      } else
      {
        Unmarshaller unmarshaller=contexto.createUnmarshaller();
        catalogo=(CatalogoLibro) unmarshaller.unmarshal(new File(XML_CATALOGO));
      }
      logger.debug("Se encontraron {} elementos en el catalogo.", catalogo.getLibros().size());
    } catch (Exception e)
    {
      throw new RuntimeException(e);
    }

  }

  public Resultado guardaLibro(Book libro) {
    Resultado resultado=new Resultado();
    FileOutputStream fos=null;
    File file;
    for (Book b : catalogo.getLibros())
    {
      if (b.getTitulo().equals(libro.getTitulo()))
      {
        logger.debug("El libro ya existe no se agrega al catalogo");
        resultado.setRespuesta("El libro ya existe en catalogo");
        return resultado;
      }
    }
    logger.debug("guardando la imagen");
    file=new File(IMAGE_DIR, libro.getFileName());
    try
    {
      fos=new FileOutputStream(file);
      fos.write(libro.getImagen());
      catalogo.getLibros().add(libro);
      updateCatalogoXml();
      resultado.setRespuesta("El libro fue agregado exitosamente.");
    } catch (IOException e)
    {
      resultado.setRespuesta("Hubo un error al guardar el libro.");
      logger.error("Hubo un error al guardar el libro", e);
    } finally
    {
      try
      {
        fos.close();
      } catch (IOException e)
      {

      }
    }
    return resultado;
  }

  public CatalogoLibro getListaLibros(String titulo, String Author) {

    List<Book> resultado=new ArrayList<Book>();
    
    for (Book book : catalogo.getLibros())
    {
      if (Author != null && book.getAutor().indexOf(Author) >= 0)
      {
        resultado.add(book);
      } else
        if (titulo != null && book.getTitulo().indexOf(titulo) >= 0)
        {
          resultado.add(book);
        }
    }
    CatalogoLibro catalogoResultado=new CatalogoLibro();
    catalogoResultado.setLibros(resultado);

    return catalogoResultado;
  }

  /**
   * Actualiza el documento xml con la lista en memoria.
   * @throws IOException
   */
  private void updateCatalogoXml() throws IOException {
    logger.debug("Actualizando datos en disco");
    Marshaller marshaller;
    try
    {
      marshaller=contexto.createMarshaller();
      marshaller.marshal(catalogo, new File(XML_CATALOGO));
    } catch (JAXBException e)
    {
      logger.error("Error al serializar la lista de libros", e);
    }
  }

}
