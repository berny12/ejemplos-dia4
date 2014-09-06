/**
 * Fecha de creación: 06/02/2011 18:02:24
 *
 * Copyright (c) 2011 SynergyJ. 
 * Todos los derechos reservados.
 *
 * Este software es información pueder ser mofificado, utilizado
 * haciendo referencia al autor intelectual.
 */
package com.synergyj.cursos.webservices.lab1;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synergyj.cursos.webservices.restful.ImageBookServiceWS;

/**
 * Esta clase se encarga de publicar un restful WS
 * @author Jorge Rodríguez Campos (jorge.rodriguez@synergyj.com)
 * @version 1.0
 */
public class ImageBookSearchPublisher {

	/**
	 * Logger para todas las instancias de la clase
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ImageBookSearchPublisher.class);

	public static void main(String[] args) {
		String url = "http://127.0.0.1:9878/searchBooks";
		logger.debug("Publicando WS estilo document en {} ", url);
		logger.debug("Para terminar presione Ctrl + C");
		Endpoint.publish(url, new ImageBookServiceWS());
	}

}
