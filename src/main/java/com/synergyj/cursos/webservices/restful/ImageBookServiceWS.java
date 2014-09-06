/**
 * Fecha de creaci�n: 06/02/2011 12:00:07
 * 
 * Copyright (c) 2011 SynergyJ. Todos los derechos reservados.
 * 
 * Este software es informaci�n pueder ser mofificado, utilizado haciendo referencia al autor
 * intelectual.
 */
package com.synergyj.cursos.webservices.restful;

import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synergyj.cursos.webservices.entidades.Book;
import com.synergyj.cursos.webservices.entidades.CatalogoLibro;
import com.synergyj.cursos.webservices.entidades.Resultado;
import com.synergyj.cursos.webservices.restful.service.ImageBookService;
import com.synergyj.cursos.webservices.restful.service.impl.ImageBookServiceImpl;

/**
 * Restful WS que realiza la misma funcion del servicio SOAP ImageBookSearchService paro ahora
 * empleando los conceptos de REST
 * @author Jorge Rodr�guez Campos (jorge.rodriguez@synergyj.com)
 * @version 1.0
 */

@WebServiceProvider
@ServiceMode(Mode.MESSAGE)
@BindingType(value=HTTPBinding.HTTP_BINDING)
public class ImageBookServiceWS implements Provider<Source> {

  @Resource
  private WebServiceContext wsContext;

   
  ImageBookService servicio;

  JAXBContext contexto ;
  
  /**
   * Logger para todas las instancias de la clase
   */
  private static final Logger logger=LoggerFactory.getLogger(ImageBookServiceWS.class);
  public ImageBookServiceWS() {
  servicio = new ImageBookServiceImpl();
     try
    {
      contexto = JAXBContext.newInstance("com.synergyj.cursos.webservices.entidades");
    } catch (JAXBException e)
    {
     throw new RuntimeException(e);
    }
  }

  /*
   * (non-Javadoc)
   * @see javax.xml.ws.Provider#invoke(java.lang.Object)
   */
  @Override
  public Source invoke(Source source) {
    MessageContext messageContext;
    String httpMethod;
    logger.debug("Peticion recibida ResFul WS, obteniendo messageContext");
    messageContext=wsContext.getMessageContext();
    httpMethod=(String) messageContext.get(MessageContext.HTTP_REQUEST_METHOD);
    if (httpMethod.equals("GET"))
    {
      logger.debug("procesando peticion GET");
      return doGet(messageContext);
    } else
      if (httpMethod.equals("POST"))
      {
        logger.debug("Procesando peticion POST");
        return doPost(messageContext, source);
      } else
      {
        logger.error("Metodo HTTP no soportado por esta implementacion: {}", httpMethod);
        throw new HTTPException(405);
      }
  }

  /**
   * Procesa una peticion GET
   * @param messageContext
   */
  private Source doGet(MessageContext messageContext) {
    String titulo;
    String autor;
    String queryString;
    List<Book> resultado;
    queryString=(String) messageContext.get(MessageContext.QUERY_STRING);
    titulo=getRequestParameter("titulo", queryString);
    autor=getRequestParameter("autor", queryString);

    logger.debug("criterios de busqueda, autor: {}, titulo: {}",
      new Object[] { autor, titulo });

    CatalogoLibro catalogo =  servicio.getListaLibros(titulo, autor);
    
    return serializaResultado(catalogo);
  }

  /**
   * Procesa una peticion POST para agregar una nueva entrada al catalogo de libros.
   * @param messageContext
   * @param source
   * @return
   */
  private Source doPost(MessageContext messageContext, Source source) {

    logger.debug("Obteniendo el documento XML del source:");
    try
    {
      Unmarshaller unma=contexto.createUnmarshaller();
      Book libro=(Book) unma.unmarshal(source);
      logger.debug("validando la existencia del libro en catalogo");
      Resultado resultado = servicio.guardaLibro(libro);  
      return serializaResultadoActualizacion(resultado);
    } catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * genera una respuesta {@link XMLBResultadoActualizacion} para mostrar al cliente
   * @param resultado
   * @return
   */
  private Source serializaResultadoActualizacion(Resultado mensaje) {
    JAXBSource source=null;
    try
    {
      source=new JAXBSource(contexto, mensaje);
    } catch (JAXBException e)
    {
      logger.error("Error al serializar la respuesta",e);
    }
    return source;
  }


  /**
   * Este metodo se encarga de serialiar la respuesta del WS empleando xmlbeans con el esquema
   * definido en ela rchivo busquedaLibros.xsd
   * @param resultado
   * @return
   * @throws
   */
  private Source serializaResultado(CatalogoLibro cat) {
    JAXBSource source=null;
    try
    {
      source=new JAXBSource(contexto, cat);
    } catch (JAXBException e)
    {
      logger.error("Error al serializar la lista de libros");
    }
    return source;
  }


  /**
   * Obtiene el parametro especificado de la peticion http
   * @param string
   * @param queryString
   * @return
   */
  private String getRequestParameter(String paramName, String queryString) {
    String[] params;
    logger.debug("queryString: {}", queryString);
    if (queryString == null)
    {
      logger.warn("No se especificaron parametros");
      return null;
    }
    params=queryString.split("=");
    for (int i=0; i < params.length; i++)
    {
      if (params[i].equals(paramName))
      {
        return params[i + 1];
      }
    }
    logger.debug("No se encontro valor para el parametro {}", paramName);
    return null;
  }

}
