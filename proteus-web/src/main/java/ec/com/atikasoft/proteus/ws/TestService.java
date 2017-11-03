/*
 *  TestService.java
 *  MEF $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information Advance Consulting and and AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Feb 25, 2014
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.ws;

import ec.com.atikasoft.proteus.servicio.SeguridadServicio;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Servidor;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.jws.WebResult;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@WebService(serviceName = "TestService")
@Stateless()
public class TestService {

    @EJB
    private SeguridadServicio seguridadServicio;

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     *
     * @param codigoRol
     * @return
     */
    @WebMethod(operationName = "buscarUsuariosPorRol")
    @WebResult(name = "servidores")
    public List<Servidor> buscarUsuariosPorRol(@WebParam(name = "codigoRol") final String codigoRol) throws
            ServicioException {
        return seguridadServicio.buscarUsuariosPorRol(codigoRol, null);
    }
}
