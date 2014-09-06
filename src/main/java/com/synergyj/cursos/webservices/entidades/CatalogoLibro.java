/**
 * Fecha de creación: 06/02/2011 14:48:14
 * 
 * Copyright (c) 2011 SynergyJ. Todos los derechos reservados.
 * 
 * Este software es información pueder ser mofificado, utilizado haciendo referencia al autor
 * intelectual.
 */
package com.synergyj.cursos.webservices.entidades;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO [Agregar documentacion de la clase] 
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CatalogoLibro {

    @XmlElement(name = "libro")
    List<Book> libros;

    public List<Book> getLibros() {
        return libros;
    }

    public void setLibros(List<Book> libros) {
        this.libros = libros;
    }
}
