/*
 *  EmpleadoWS.java
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
 *  Apr 29, 2014
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.ws.mdmq;

import ec.com.atikasoft.proteus.dao.DistributivoDetalleHistoricoDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.distributivo.DistributivoDetalleHistorico;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import org.tempuri.ArrayOfClsEntidadRecursosHumanos;
import org.tempuri.ClsEntidadRecursosHumanos;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@WebService(serviceName = "wsEmpleado",
        portName = "wsEmpleadoSoap",
        endpointInterface = "org.tempuri.WsEmpleadoSoap",
        targetNamespace = "http://tempuri.org/",
        wsdlLocation = "WEB-INF/wsdl/empleado.wsdl")
//@BindingType("http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
public class EmpleadoWS {

    /**
     * Servicio de distributiuvo.
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;

    /**
     * Dao de servidor.
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * Dao del historico de puestos.
     */
    @EJB
    private DistributivoDetalleHistoricoDao distributivoDetalleHistoricoDao;

    /**
     *
     * @param inCedula
     * @param inApellidoNombre
     * @param inDependencias
     * @return
     */
    public ArrayOfClsEntidadRecursosHumanos consultaDatosRRHH(final String inCedula,
            final String inApellidoNombre, final int inDependencias) {
        ArrayOfClsEntidadRecursosHumanos rh = new ArrayOfClsEntidadRecursosHumanos();
        try {
            List<DistributivoDetalle> puestos = distributivoPuestoServicio.buscar(inCedula, inApellidoNombre, String.
                    valueOf(inDependencias));
            for (DistributivoDetalle dd : puestos) {
                rh.getClsEntidadRecursosHumanos().add(poblarDatosEmpleado(dd));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rh;
    }

    /**
     *
     * @param inCodEmpleado
     * @param inAno
     * @param inMes
     * @return
     */
    public ArrayOfClsEntidadRecursosHumanos situacionHistoricaEmpleado(final long inCodEmpleado, final int inAno,
            final int inMes) {
        ArrayOfClsEntidadRecursosHumanos rh = new ArrayOfClsEntidadRecursosHumanos();
        try {
            Servidor s = servidorDao.buscarPorCodigo(inCodEmpleado);
            if (s != null) {
                List<DistributivoDetalleHistorico> historico
                        = distributivoDetalleHistoricoDao.buscarPorIdentificacion(s.getTipoIdentificacion(), s.getNumeroIdentificacion());
                DistributivoDetalleHistorico anterior = null;
                for (DistributivoDetalleHistorico r : historico) {
                    if (UtilFechas.obtenerAnio(r.getFechaCreacion()) >= inAno && UtilFechas.obtenerMes(r.getFechaCreacion()) >= inMes) {
                        anterior = r;
                    } else {
                        if (anterior == null) {
                            rh.getClsEntidadRecursosHumanos().add(poblarDatosEmpleado(r, s));
                        } else {
                            rh.getClsEntidadRecursosHumanos().add(poblarDatosEmpleado(anterior, s));
                        }
                        break;
                    }

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rh;
    }

    /**
     *
     * @param inCodEmpleado
     * @return
     */
    public org.tempuri.ArrayOfClsEntidadRecursosHumanos dependenciaHistoricaEmpleado(final long inCodEmpleado) {
        ArrayOfClsEntidadRecursosHumanos rh = new ArrayOfClsEntidadRecursosHumanos();
        try {
            Servidor s = servidorDao.buscarPorCodigo(inCodEmpleado);
            if (s != null) {
                List<DistributivoDetalleHistorico> historico
                        = distributivoDetalleHistoricoDao.buscarPorIdentificacion(s.getTipoIdentificacion(), s.getNumeroIdentificacion());
                for (DistributivoDetalleHistorico registro : historico) {
                    rh.getClsEntidadRecursosHumanos().add(poblarDatosEmpleado(registro, s));
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rh;
    }

    /**
     *
     * @param inCodEmpleado
     * @return
     */
    public org.tempuri.ArrayOfClsEntidadRecursosHumanos regimenHistoricoEmpleado(final long inCodEmpleado) {
        ArrayOfClsEntidadRecursosHumanos rh = new ArrayOfClsEntidadRecursosHumanos();
        try {
            DistributivoDetalle dd = distributivoPuestoServicio.buscarServidorPorCodigo(inCodEmpleado);
            if (dd != null) {
                rh.getClsEntidadRecursosHumanos().add(poblarDatosEmpleado(dd));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rh;
    }

    /**
     *
     * @param inCedula
     * @param inApellidos
     * @return
     */
    public org.tempuri.ArrayOfClsEntidadRecursosHumanos recuperaIDEmpleado(final String inCedula,
            final String inApellidos) {
        ArrayOfClsEntidadRecursosHumanos rh = new ArrayOfClsEntidadRecursosHumanos();
        try {
            List<DistributivoDetalle> puestos = distributivoPuestoServicio.buscar(inCedula, inApellidos, null);
            for (DistributivoDetalle dd : puestos) {
                rh.getClsEntidadRecursosHumanos().add(poblarDatosEmpleado(dd));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rh;
    }

    /**
     *
     * @param inDependencias
     * @return
     */
    public org.tempuri.ArrayOfClsEntidadRecursosHumanos consultaDatosRRHHUnidades(final int inDependencias) {
        ArrayOfClsEntidadRecursosHumanos rh = new ArrayOfClsEntidadRecursosHumanos();
        try {
            List<DistributivoDetalle> puestos = distributivoPuestoServicio.buscar(String.valueOf(inDependencias));
            for (DistributivoDetalle dd : puestos) {
                rh.getClsEntidadRecursosHumanos().add(poblarDatosEmpleado(dd));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rh;
    }

    /**
     *
     * @param dd
     * @return
     */
    private ClsEntidadRecursosHumanos poblarDatosEmpleado(final DistributivoDetalle dd) {
        ClsEntidadRecursosHumanos r = new ClsEntidadRecursosHumanos();
        r.setApellidoRRHH(dd.getServidor().getApellidos());
        r.setCedulaRRHH(dd.getServidor().getNumeroIdentificacion());
        r.setCodigo(dd.getServidor().getCodigoEmpleado() == null ? 0 : dd.getServidor().getCodigoEmpleado().
                intValue());
        r.setCodigoCGrados(dd.getEscalaOcupacional().getId().intValue());
        r.setCodigoRegimen(dd.getEscalaOcupacional().getNivelOcupacional().getIdRegimenLaboral().intValue());
        r.setCodigoUAdministrativa(dd.getDistributivo().getUnidadOrganizacional().getCodigo());
        r.setDenominacion(dd.getEscalaOcupacional().getGrado());
        r.setDependencia(dd.getDistributivo().getUnidadOrganizacional().getRuta());
        r.setDescripcionPuestoRRHH(dd.getEscalaOcupacional().getNombre());
        r.setFechaIngreso(UtilFechas.formatear(dd.getServidor().getFechaIngresoInstitucion()));
        r.setFechaNacimiento(dd.getServidor().getFechaNacimiento() == null ? null
                : UtilFechas.formatear(dd.getServidor().getFechaNacimiento()));
        if (dd.getDenominacionPuesto() != null) {
            r.setGrupoOcupacional(dd.getDenominacionPuesto().getNombre());
        }
        r.setIdDependencia(dd.getDistributivo().getIdUnidadOrganizacional().intValue());
        r.setMailRRHH(dd.getServidor().getMail());
        r.setNacionalidad("ECUATORIANO");
        r.setNombre(dd.getServidor().getNombres());
        r.setNombreGrado(dd.getEscalaOcupacional().getGrado());
        r.setNombreRRHH(dd.getServidor().getNombres());
        r.setNombreUAdminsitrativa(dd.getDistributivo().getUnidadOrganizacional().getRuta());
        r.setRegimen(dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getNombre());
        r.setSexo(dd.getServidor().getCatalogoGenero() == null ? null : dd.getServidor().getCatalogoGenero().
                getNombre());
        r.setSueldorRRHH(dd.getRmu());
        r.setTelefono(dd.getServidor().getTelefono());
        return r;
    }

    /**
     *
     * @param dd
     * @return
     */
    private ClsEntidadRecursosHumanos poblarDatosEmpleado(final DistributivoDetalleHistorico dd, Servidor s) {
        ClsEntidadRecursosHumanos r = new ClsEntidadRecursosHumanos();
        r.setApellidoRRHH(s.getApellidos());
        r.setCedulaRRHH(dd.getServidorNumeroIdentificacion());
        r.setCodigo(s.getCodigoEmpleado() == null ? 0 : s.getCodigoEmpleado().intValue());
        r.setCodigoCGrados(0);
        r.setCodigoRegimen(Integer.valueOf(dd.getRegimenLaboralCodigo()));
        r.setCodigoUAdministrativa(dd.getUnidadOrganizacionalCodigo());
        r.setDenominacion(dd.getEscalaOcupacionalNombre());
        r.setDependencia(dd.getUnidadOrganizacionalNombre());
        r.setDescripcionPuestoRRHH(dd.getEscalaOcupacionalNombre());
        r.setFechaIngreso(UtilFechas.formatear(dd.getFechaIngreso()));
        r.setFechaNacimiento(s.getFechaNacimiento() == null ? null : UtilFechas.formatear(s.getFechaNacimiento()));
        r.setGrupoOcupacional(dd.getDenominacionPuestoNombre());
        r.setIdDependencia(0);
        r.setMailRRHH(s.getMail());
        r.setNacionalidad("ECUATORIANO");
        r.setNombre(s.getNombres());
        r.setNombreGrado(dd.getEscalaOcupacionalGrado());
        r.setNombreRRHH(s.getApellidosNombres());
        r.setNombreUAdminsitrativa(dd.getUnidadOrganizacionalNombre());
        r.setRegimen(dd.getRegimenLaboralNombre());
        r.setSexo(s.getCatalogoGenero() == null ? null : s.getCatalogoGenero().getNombre());
        r.setSueldorRRHH(dd.getRmu());
        r.setTelefono(s.getTelefono());
        return r;
    }

}
