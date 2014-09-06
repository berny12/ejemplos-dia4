/**
 * Fecha de creación: 06/02/2011 14:48:14
 *
 * Copyright (c) 2011 SynergyJ. 
 * Todos los derechos reservados.
 *
 * Este software es información pueder ser mofificado, utilizado
 * haciendo referencia al autor intelectual.
 */
package com.synergyj.cursos.webservices.entidades;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Jorge Rodríguez Campos (jorge.rodriguez@synergyj.com)
 * @version 1.0
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Book {

	private String titulo;

	private String autor;

	private String fileName;

	private byte[] imagen;

	/**
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo para inicializar el atributo titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return autor
	 */
	public String getAutor() {
		return autor;
	}

	/**
	 * @param autor para inicializar el atributo autor
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}

	/**
	 * @return imagen
	 */
	public byte[] getImagen() {
		return imagen;
	}

	/**
	 * @param imagen para inicializar el atributo imagen
	 */
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName para inicializar el atributo fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
