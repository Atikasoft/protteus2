/*
 *  SeguridadService.java
 *  MEF $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information Advance Consulting and and AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Feb 8, 2014
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.ws;

import ec.com.atikasoft.proteus.servicio.SeguridadServicio;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@WebService(name = "Seguridad", serviceName = "SeguridadService", targetNamespace = "ec.markasoft.proteus.services")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED, style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@Stateless()
public class SeguridadService {

    /**
     *
     */
    @EJB
    private SeguridadServicio seguridadServicio;

    /**
     *
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     *
     * @param codigoRol
     * @param codigoUnidadOrganizacional
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    @WebMethod(operationName = "buscarUsuariosPorRol", action = "urn:buscarUsuariosPorRol")
    @WebResult(name = "servidores")
    @ResponseWrapper(className = "ec.com.atikasoft.proteus.ws.jaxws.BuscarUsuariosPorRolResponse", localName
            = "buscarUsuariosPorRolResponse", targetNamespace = "ec.markasoft.proteus.services")
    @RequestWrapper(className = "ec.com.atikasoft.proteus.ws.jaxws.BuscarUsuariosPorRolRequest", localName
            = "buscarUsuariosPorRolRequest", targetNamespace = "ec.markasoft.proteus.services")
    public List<Servidor> buscarUsuariosPorRol(@WebParam(name = "codigoRol") final String codigoRol, @WebParam(name
            = "codigoUnidadOrganizacional") final String codigoUnidadOrganizacional) throws
            ServicioException {
        return seguridadServicio.buscarUsuariosPorRol(codigoRol, codigoUnidadOrganizacional);
    }

    /**
     *
     * @param numeroIdentificacion
     * @param codigoRol
     * @param codigoUnidadOrganizacional
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    @WebMethod(operationName = "buscarUsuarioPorRol", action = "urn:buscarUsuarioPorRol")
    @WebResult(name = "servidor")
    @ResponseWrapper(className = "ec.com.atikasoft.proteus.ws.jaxws.BuscarUsuarioPorRolResponse", localName
            = "buscarUsuarioPorRolResponse", targetNamespace = "ec.markasoft.proteus.services")
    @RequestWrapper(className = "ec.com.atikasoft.proteus.ws.jaxws.BuscarUsuarioPorRolRequest", localName
            = "buscarUsuarioPorRolRequest", targetNamespace = "ec.markasoft.proteus.services")
    public Servidor buscarUsuarioPorRol(@WebParam(name = "numeroIdentificacion") final String numeroIdentificacion,
            @WebParam(name = "codigoRol") final String codigoRol, @WebParam(name = "codigoUnidadOrganizacional")
            final String codigoUnidadOrganizacional) throws ServicioException {
        return seguridadServicio.buscarUsuarioPorRol(numeroIdentificacion, codigoRol, codigoUnidadOrganizacional);
    }

    /**
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     * @throws ServicioException
     */
    @WebMethod(operationName = "buscarUsuario", action = "urn:buscarUsuario")
    @WebResult(name = "servidor")
    @ResponseWrapper(className = "ec.com.atikasoft.proteus.ws.jaxws.BuscarUsuarioResponse", localName
            = "buscarUsuarioResponse", targetNamespace = "ec.markasoft.proteus.services")
    @RequestWrapper(className = "ec.com.atikasoft.proteus.ws.jaxws.BuscarUsuarioRequest", localName
            = "buscarUsuarioRequest", targetNamespace = "ec.markasoft.proteus.services")
    public Servidor buscarUsuario(@WebParam(name = "tipoIdentificacion") final String tipoIdentificacion,
            @WebParam(name = "numeroIdentificacion") final String numeroIdentificacion) throws
            ServicioException {
        return seguridadServicio.buscarUsuario(tipoIdentificacion, numeroIdentificacion);
    }

    /**
     *
     * @param servidorId
     * @return
     * @throws ServicioException
     */
    @WebMethod(operationName = "buscarDistributivoPorServidor", action = "urn:buscarDistributivoPorServidor")
    @WebResult(name = "distributivos")
    @ResponseWrapper(className = "ec.com.atikasoft.proteus.ws.jaxws.BuscarDistributivoPorServidorResponse", localName
            = "buscarDistributivoPorServidorResponse", targetNamespace = "ec.markasoft.proteus.services")
    @RequestWrapper(className = "ec.com.atikasoft.proteus.ws.jaxws.BuscarDistributivoPorServidorRequest", localName
            = "buscarDistributivoPorServidorRequest", targetNamespace = "ec.markasoft.proteus.services")
    public List<DistributivoDetalle> buscarDistributivoPorServidor(@WebParam(name = "servidorId") final Long servidorId)
            throws ServicioException {
        return administracionServicio.listarTodosDistributivoDetallePorServidor(servidorId);
    }
}
