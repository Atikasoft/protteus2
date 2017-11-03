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
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
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
 * Puesto esta disponible por q servidor fue destituido.
 *
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarPuestoDisponiblePorServidorDestituido extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG =
            Logger.getLogger(ValidarPuestoDisponiblePorServidorDestituido.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALPUEDISSERDES";

    /**
     * Dao de movimientos.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Constructor sin argumentos.
     */
    public ValidarPuestoDisponiblePorServidorDestituido() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.fine("**** Validar puesto disponible por servidor destituido.");
        Movimiento movimiento = null;
        try {
            movimiento = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            if (movimiento.getNumeroIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el número de identificación de la persona."), Boolean.TRUE, mensajes, regla,
                        movimiento);
            } else if (movimiento.getTipoIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el tipo de identificación de la persona."), Boolean.TRUE, mensajes, regla,
                        movimiento);
            } else {
                ParametroGlobal pTipoMovimiento =
                        buscarParametroGlobal(ParametroGlobalEnum.NEMONICO_TIPO_MOVIMIENTO_CESACION_POR_DESTITUCION.
                        getCodigo(), parametros);
                boolean existe = false;
                Date fechaActual = UtilFechas.truncarFecha(new Date());
                String[] nemonicos = pTipoMovimiento.getValorTexto().split(",");
                for (String n : nemonicos) {
                    List<Movimiento> movimientos = movimientoDao.buscarPorPuestoYTipoMovimiento(movimiento.
                            getDistributivoDetalleId(), n, EstadosTramiteEnum.REGISTRADO.getCodigo());
                    for (Movimiento mAnterior : movimientos) {
                        if (UtilFechas.between(fechaActual, mAnterior.getRigeApartirDe(), mAnterior.getFechaHasta() == null
                                ? fechaActual : mAnterior.getFechaHasta())) {
                            existe = true;
                            break;
                        }
                    }
                    if (existe) {
                        break;
                    }
                }
                if (!existe) {
                    StringBuilder msg = new StringBuilder();
                    msg.append("Puesto no se encuentra disponible por ");
                    msg.append("CESACIÓN POR DESTITUCIÓN");
                    registrarMensaje(msg.toString(), obligatorio, mensajes, regla, movimiento);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, movimiento);
        }
    }
}
