/*
 *  ValidarRegistroCivil.java
 *  ESIPREN V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Nov 14, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.reglas.validaciones;

import ec.com.atikasoft.proteus.dao.IngresoDao;
import ec.com.atikasoft.proteus.dao.MovimientoDao;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.modelo.ComisionServicio;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Ingreso;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.reglas.ReglaAbstracta;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Verifica si un servidor publico tiene una comision de servicio vigente.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarComisionServicio extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarComisionServicio.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALCOMSER";

    /**
     * Dao de movimientos.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     *
     */
    @EJB
    private IngresoDao ingresoDao;

    /**
     * Constructor sin argumentos.
     */
    public ValidarComisionServicio() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.fine("**** Validacion de comision de servicio.");
        Movimiento m = null;
        try {
            m = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            DistributivoDetalle p
                    = (DistributivoDetalle) parametros.get(ReglasParametrosEnum.PUESTO.getCodigo());
            Ingreso ingreso = ingresoDao.buscarPorMovimiento(m.getId());
//            if (ingreso.getFechaIngresoSectorPublico() == null) {
//                registrarMensaje(UtilCadena.concatenar(
//                        "El campo 'Fecha Ingreso Sector Público' debe ser ingresado"), Boolean.TRUE, mensajes, regla, m);
//            }
            if (m.getNumeroIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el número de identificación de la persona."), Boolean.TRUE, mensajes, regla, m);
            }if (m.getTipoIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el tipo de identificación de la persona."), Boolean.TRUE, mensajes, regla, m);
            } else if (m.getRigeApartirDe() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "El campo 'Rige A Partir De' debe ser ingresado"), Boolean.TRUE, mensajes, regla, m);
            } else {
                if (p.getServidor() != null && ingreso.getFechaIngresoSectorPublico() != null) {
                    Date fechaDesde = UtilFechas.truncarFecha(ingreso.getFechaIngresoSectorPublico());
                    Date fechaHasta = UtilFechas.truncarFecha(new Date());
                    List<Movimiento> movimientos = movimientoDao.buscarPorPeriodo(m.getTipoIdentificacion(),
                            m.getNumeroIdentificacion(), EstadosTramiteEnum.REGISTRADO.getCodigo(), fechaDesde,
                            fechaHasta);
                    for (Movimiento mInterno : movimientos) {
                        if (!mInterno.getListaComisionServicio().isEmpty()) {
                            ComisionServicio cs = mInterno.getListaComisionServicio().get(0);
                            if (mInterno.getFechaHasta().compareTo(fechaHasta) == 1) {
                                StringBuilder msg = new StringBuilder();
                                msg.append("Servidor con identificación ").append(m.getNumeroIdentificacion());
                                msg.append(" y nombres ").append(m.getApellidosNombres());
                                msg.append(" tiene una comisión de servicio en curso");
                                msg.append(", en la institución '").append(cs.getInstitucion()).append("', ");
                                msg.append("unidad administrativa '").append(cs.getUnidad()).append("' ");
                                msg.append("durante el periodo ").append(UtilFechas.formatear(
                                        mInterno.getRigeApartirDe()));
                                msg.append(" a ").append(UtilFechas.formatear(mInterno.getFechaHasta()));
                                registrarMensaje(msg.toString(), obligatorio, mensajes, regla, m);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }
}
