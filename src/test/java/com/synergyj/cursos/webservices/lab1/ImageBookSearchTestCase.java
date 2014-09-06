/**
 * Fecha de creación: 06/02/2011 18:04:44
 *
 * Copyright (c) 2011 SynergyJ. 
 * Todos los derechos reservados.
 *
 * Este software es información pueder ser mofificado, utilizado
 * haciendo referencia al autor intelectual.
 */
package com.synergyj.cursos.webservices.lab1;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synergyj.cursos.webservices.entidades.Book;
import com.synergyj.cursos.webservices.entidades.CatalogoLibro;

/**
 * @author Jorge Rodríguez Campos (jorge.rodriguez@synergyj.com)
 * @version 1.0
 */
public class ImageBookSearchTestCase {

    /**
     * Logger para todas las instancias de la clase
     */
	private static final Logger logger = LoggerFactory
			.getLogger(ImageBookSearchTestCase.class);

	private static final String endpoint = "http://localhost:9878/searchBooks?titulo=Database";

	private static final String IMG_DIR = "target/test-classes";

	/**
	 * Este testcase realiza una peticion http GET para invocar al RestfulWS. Se emplea
	 * xmlbeans para recuperar el xml y obtener los datos del resultado de la búsqueda.
	 * @throws Exception
	 */
	@Test
	public void invokeWS() throws Exception {
		HttpURLConnection connection;
		URL url;
		CatalogoLibro resultado;
		File imgFile;
		FileOutputStream fos;
		logger.debug("Invocando WS estilo REST: {}", endpoint);
		url = new URL(endpoint);
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		logger.debug("mostrando la respuesta obtenida.");
		logger.debug("Realizando deserializacion de la respuesta empleando xmlbeans.");
		JAXBContext contexto = JAXBContext.newInstance(CatalogoLibro.class);
		Unmarshaller  unma = contexto.createUnmarshaller();
		resultado = (CatalogoLibro) unma.unmarshal((connection.getInputStream()));
		logger.debug("numero de libros encontrados: {}", resultado.getLibros().size());
		logger.debug("almacenando las imagenes en {}", IMG_DIR);
		for (Book libro : resultado.getLibros()) {
			logger.debug("Autor: {}", libro.getAutor());
			logger.debug("Titulo: {}", libro.getTitulo());
			logger.debug("Tamaño de la imagen en bytes: {}", libro.getImagen().length);
			logger.debug("almacenando imagen {}", libro.getFileName());
			imgFile = new File(IMG_DIR, libro.getFileName());
			fos = new FileOutputStream(imgFile);
			fos.write(libro.getImagen());
			fos.close();
		}

	}
}
