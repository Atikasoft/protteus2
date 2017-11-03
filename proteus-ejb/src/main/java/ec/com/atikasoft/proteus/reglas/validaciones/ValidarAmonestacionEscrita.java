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

import ec.com.atikasoft.proteus.dao.MovimientoDao;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
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
 * Control amonestación Escrita (REGLA2) : Si una persona tiene 2 amonestaciones escritas en un año calendarioel sistema
 * no le permite el ingreso de otro movimiento del mismo tipo y le indica que debe realizar una“Sanción Pecuniaria
 * Administrativa” Calculo de año calendario: inicia desde la fecha que el servidor ingresó a la institución (fecha de
 * ingreso + 1 año menos 1 día).
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarAmonestacionEscrita extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarAmonestacionEscrita.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALAMESC";

    /**
     * Dao de movimientos.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Constructor sin argumentos.
     */
    public ValidarAmonestacionEscrita() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.fine("**** Validacion de amonestacion escrita.");
        Movimiento m = null;
        try {
            m = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            DistributivoDetalle p =
                    (DistributivoDetalle) parametros.get(ReglasParametrosEnum.PUESTO.getCodigo());
            if (m.getNumeroIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el número de identificación de la persona."), Boolean.TRUE, mensajes, regla, m);
            } else if (m.getTipoIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el tipo de identificación de la persona."), Boolean.TRUE, mensajes, regla, m);
            } else if (regla.getTipoPeriodo() == null || regla.getValorPeriodo() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe configurado el periodo en la regla."), Boolean.TRUE, mensajes, regla, m);
            } else {
                ServidorInstitucion seleccionado = null;
                if (p.getServidor() != null) {
                    List<ServidorInstitucion> lista = p.getServidor().getListaServidorInstituicion();
                    for (ServidorInstitucion si : lista) {
                        if (si.getVigente() && si.getIdInstitucion().compareTo(p.getDistributivo().
                                getInstitucionEjercicioFiscal().getInstitucion().getId()) == 0) {
                            seleccionado = si;
                            break;
                        }
                    }
                }
                if (seleccionado != null) {
                    Date fechaDesde = UtilFechas.calcularFechaInicioUltimoAnioDeLabores(seleccionado.getFechaIngreso());
                    Date fechaHasta = UtilFechas.sumarPeriodos(fechaDesde, regla.getTipoPeriodo(),
                            regla.getValorPeriodo());
                    List<Movimiento> movimientos = movimientoDao.buscarPorPeriodoPorTipoMovimiento(m.
                            getTipoIdentificacion(),
                            m.getNumeroIdentificacion(), m.getTramite().getTipoMovimiento().getId(),
                            EstadosTramiteEnum.REGISTRADO.getCodigo(), fechaDesde, fechaHasta);
                    if (movimientos.size() >= 2) {
                        StringBuilder msg = new StringBuilder();
                        msg.append("Servidor con identificación ").append(m.getNumeroIdentificacion());
                        msg.append(" y nombres ").append(m.getApellidosNombres());
                        msg.append(", ya cuenta con 2 amonestaciones escritas en su último periodo de labores, "
                                + "debe ingresar una Sanción Pecuniaria Administrativa");
                        registrarMensaje(msg.toString(), obligatorio, mensajes, regla, m);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }
}
