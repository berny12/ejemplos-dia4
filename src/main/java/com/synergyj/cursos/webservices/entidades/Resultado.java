/**
 * Resultado.java
 * Fecha de creaci�n: 15/02/2013 , 16:24:30
 *
 * Copyright (c) 2013 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es informaci�n confidencial, propiedad del
 * Synergyj. Esta informaci�n confidencial
 * no deber� ser divulgada y solo se podr� utilizar de acuerdo
 * a los t�rminos que determine la empresa.
 */
package com.synergyj.cursos.webservices.entidades;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO [Agregar documentacion de la clase] 
 * @author Juan Manuel Reyes Medina @jkingsj (jmanuel.reyes@synergyj.com)
 * @version 1.0
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resultado {
  
  String respuesta;

  public String getRespuesta() {
    return respuesta;
  }

  public void setRespuesta(String respuesta) {
    this.respuesta=respuesta;
  }

}
