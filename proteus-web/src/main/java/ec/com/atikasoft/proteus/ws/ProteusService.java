/*
 *  SeguridadService.java
 *  MEF $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information Advance Consulting and and AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with  AtikaSoft.
 *
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

import ec.com.atikasoft.proteus.dao.DistributivoDetalleHistoricoDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.distributivo.DistributivoDetalleHistorico;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.EmpleadoAuditoriaVO;
import ec.com.atikasoft.proteus.vo.ResultadoVO;
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
@WebService(name = "Proteus", serviceName = "ProteusService", targetNamespace = "ec.markasoft.proteus.services")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED, style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
@Stateless()
public class ProteusService {

    /**
     *
     */
    @EJB
    private DistributivoDetalleHistoricoDao distributivoDetalleHistoricoDao;
    /**
     *
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     *
     * @param vo
     * @return
     */
    @WebMethod(operationName = "buscarEmpleado", action = "urn:buscarEmpleado")
    @WebResult(name = "resultado")
    @ResponseWrapper(className = "ec.com.atikasoft.proteus.ws.jaxws.BuscarEmpleadoResponse", localName = "buscarEmpleadoResponse", targetNamespace = "ec.markasoft.proteus.services")
    @RequestWrapper(className = "ec.com.atikasoft.proteus.ws.jaxws.BuscarEmpleadoRequest", localName = "buscarEmpleadoRequest", targetNamespace = "ec.markasoft.proteus.services")
    public ResultadoVO buscarEmpleado(@WebParam(name = "vo") final EmpleadoAuditoriaVO vo) {
        ResultadoVO resultado = new ResultadoVO();
        try {
            List<DistributivoDetalleHistorico> historico = distributivoDetalleHistoricoDao.buscar(vo);
            if (historico.isEmpty()) {
                resultado.setExistenResultado(Boolean.FALSE);
            } else {
                resultado.setExistenResultado(Boolean.TRUE);
                DistributivoDetalleHistorico r = historico.get(0);
                Servidor s = servidorDao.buscar(r.getServidorTipoIdentificacion(), r.getServidorNumeroIdentificacion());
                EmpleadoAuditoriaVO empleadoAuditoriaVO = new EmpleadoAuditoriaVO();
                empleadoAuditoriaVO.setApellidos(s.getApellidos());
                empleadoAuditoriaVO.setDenominacion(r.getEscalaOcupacionalNombre());
                empleadoAuditoriaVO.setDependencias(r.getUnidadOrganizacionalNombre());
                empleadoAuditoriaVO.setFechaInicial(UtilFechas.truncarFecha(r.getFechaIngreso()));
                if (r.getFechaFin() != null) {
                    empleadoAuditoriaVO.setFechaFinal(UtilFechas.truncarFecha(r.getFechaFin()));
                }
                empleadoAuditoriaVO.setNombres(s.getNombres());
                empleadoAuditoriaVO.setNumeroIdentificacion(r.getServidorTipoIdentificacion());
                empleadoAuditoriaVO.setRegimen(r.getRegimenLaboralNombre());
                empleadoAuditoriaVO.setTipoIdentificacion(r.getServidorNumeroIdentificacion());
                resultado.setEmpleadoAuditoria(empleadoAuditoriaVO);
            }
            resultado.setEjecutadoExitosamente(Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            resultado.setEjecutadoExitosamente(Boolean.FALSE);
            resultado.setExistenResultado(Boolean.FALSE);
            resultado.setMensajeError(e.getMessage()==null ? "ERROR DESCONOCIDO":e.getMessage());
        }
        return resultado;
    }

}
