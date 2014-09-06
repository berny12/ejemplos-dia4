/**
 * ImageBookService.java
 * Fecha de creación: 15/02/2013 , 18:10:08
 *
 * Copyright (c) 2013 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es información confidencial, propiedad del
 * Synergyj. Esta información confidencial
 * no deberá ser divulgada y solo se podrá utilizar de acuerdo
 * a los términos que determine la empresa.
 */
package com.synergyj.cursos.webservices.restful.service;

import com.synergyj.cursos.webservices.entidades.Book;
import com.synergyj.cursos.webservices.entidades.CatalogoLibro;
import com.synergyj.cursos.webservices.entidades.Resultado;

/**
 * TODO [Agregar documentacion de la clase] 
 * @author Juan Manuel Reyes Medina @jkingsj (jmanuel.reyes@synergyj.com)
 * @version 1.0
 * 
 */
public interface ImageBookService {

  Resultado guardaLibro(Book libro);
  
  CatalogoLibro getListaLibros(String titulo, String Author);
  
}
