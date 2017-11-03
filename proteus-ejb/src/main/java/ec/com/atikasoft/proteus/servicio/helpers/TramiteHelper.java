/*
 *  TramiteHelper.java
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
 *  Apr 28, 2014
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio.helpers;

import ec.com.atikasoft.proteus.enums.formatos.FormatoIngresoEnum;
import ec.com.atikasoft.proteus.enums.formatos.FormatoSalidaEnum;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.List;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class TramiteHelper {

    /**
     * Constructor sin argumentos.
     */
    public TramiteHelper() {
        super();
    }

    /**
     *
     * @param tm
     * @param dd
     * @param errores
     * @param f
     */
    public static void validarRegimenLaboral(final TipoMovimiento tm, final DistributivoDetalle dd,
            final List<String> errores, final int f) {
        if (tm.getClase().getRegimenLaboral() != null) {
            if (dd.getEscalaOcupacional().getNivelOcupacional().getIdRegimenLaboral().compareTo(tm.getClase().getRegimenLaboral().getId()) != 0) {
                errores.add(UtilCadena.concatenar("Fila:", f, ",Columna:",
                        FormatoIngresoEnum.CODIGO_PUESTO.getIndice() + 1, ",Campo:",
                        FormatoIngresoEnum.CODIGO_PUESTO.getNombre(),
                        ", regimen laboral (", dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getNombre(),
                        ") del puesto no corresponde a regimen laboral definido en la clase ", tm.getClase().getNombre(),
                        "(", tm.getClase().getRegimenLaboral().getNombre(), ")"));
            }
        }
    }

    /**
     *
     * @param tm
     * @param dd
     * @param errores
     * @param f
     */
    public static void validarEstadoPersonal(final TipoMovimiento tm, final DistributivoDetalle dd,
            final List<String> errores, final int f) {
        if (tm.getAreaEstadoPersonal() && dd.getServidor() != null && tm.getEstadoPersonalInicialCore() != null && dd.
                getServidor().getEstadoPersonal().getId().longValue() != tm.getEstadoPersonalInicialCore().
                longValue()) {
            errores.add(UtilCadena.concatenar("Fila:", f, ",Columna:",
                    FormatoIngresoEnum.CODIGO_PUESTO.getIndice() + 1, ",Campo:",
                    FormatoIngresoEnum.CODIGO_PUESTO.getNombre(),
                    ", estado del servidor (", dd.getServidor().getEstadoPersonal().getNombre(),
                    ") no corresponde al estado definido en el tipo de movimiento (", tm.getEstadoPersonalInicialCore(),
                    ")"));
        }
    }

    /**
     *
     * @param tm
     * @param dd
     * @param errores
     * @param f
     */
    public static void validarEstadoPuesto(final TipoMovimiento tm, final DistributivoDetalle dd,
            final List<String> errores, final int f) {
        // validar estados.
        if (tm.getAreaEstadoPuesto() && tm.getEstadoPuestoInicialCore() != null && dd.getEstadoPuesto().
                getId().longValue() != tm.getEstadoPuestoInicialCore().longValue()) {
            errores.add(UtilCadena.concatenar("Fila:", f, ",Columna:",
                    FormatoIngresoEnum.CODIGO_PUESTO.getIndice() + 1, ",Campo:",
                    FormatoIngresoEnum.CODIGO_PUESTO.getNombre(), ",Valor:", dd.getCodigoPuesto(),
                    ", estado del puesto (", dd.getEstadoPuesto().getNombre(),
                    ") no corresponde al estado definido en el tipo de movimiento (", tm.
                    getEstadoPuestoInicialCoreNombre(), ")"));
        }
    }

    /**
     *
     * @param tm
     * @param dd
     * @param errores
     * @param f
     */
    public static void validarEstadoPuestoPropuesto(final TipoMovimiento tm, final DistributivoDetalle dd,
            final List<String> errores, final int f) {
        if (tm.getAreaEstadoPuestoPropuesto() && tm.getEstadoPuestoInicialPropuestaCore() != null && dd.getEstadoPuesto().
                getId().longValue() != tm.getEstadoPuestoInicialPropuestaCore().
                longValue()) {
            errores.add(UtilCadena.concatenar("Fila:", f, ",Columna:",
                    FormatoIngresoEnum.CODIGO_PUESTO_PROPUESTO.getIndice() + 1, ",Campo:",
                    FormatoIngresoEnum.CODIGO_PUESTO_PROPUESTO.getNombre(),
                    ", estado del puesto (", dd.getEstadoPuesto().getNombre(), ",Valor:", dd.getCodigoPuesto(),
                    ") no corresponde al estado definido en el tipo de movimiento (", tm.
                    getEstadoPuestoInicialCoreNombre(), ")"));
        }
    }

    /**
     *
     * @param uo
     * @param usuario
     * @param p
     * @param dd
     * @param errores
     * @param f
     */
    public static void validarPuestoPerteneceAUnidad(List<UnidadOrganizacional> unidadesAcceso, final UsuarioVO usuario,
            final ParametroInstitucional p, final DistributivoDetalle dd, final List<String> errores, final int f) {
        String[] unidades = p == null ? new String[0] : p.getValorTexto().trim().split(";");
        boolean existe = false;
        for (String unidad : unidades) {
            if (usuario.getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo().
                    equals(unidad)) {
                existe = true;
                break;
            } else {
                for (UnidadOrganizacional uo : unidadesAcceso) {
                    if (uo.getCodigo().equals(dd.getDistributivo().getUnidadOrganizacional().getCodigo())) {
                        existe = true;
                        break;
                    }
                }

            }
        }
        if (!existe) {
            errores.add(
                    UtilCadena.concatenar("Fila:", f, ",Columna:",
                            FormatoIngresoEnum.CODIGO_PUESTO.getIndice() + 1, ",Campo:",
                            FormatoIngresoEnum.CODIGO_PUESTO.getNombre(),
                            ",Valor:", dd.getCodigoPuesto(), ", puesto no pueder ser usado por la unidad "
                            + "ya que tiene acceso."));
        }
    }

    /**
     *
     * @param errores
     * @param f
     * @param fmt
     * @param error
     */
    public static void registrarError(final List<String> r, final List<String> errores, final int f,
            final FormatoIngresoEnum fmt, final String error) {
        errores.add(UtilCadena.concatenar("Fila:", f, ",Columna:", fmt.getIndice() + 1, ",Campo:", fmt.getNombre(),
                ",Valor:", r.get(fmt.getIndice()), ",", error));
    }

    /**
     *
     * @param errores
     * @param f
     * @param fmt
     * @param error
     */
    public static void registrarError(final List<String> r, final List<String> errores, final int f,
            final FormatoSalidaEnum fmt, final String error) {
        errores.add(UtilCadena.concatenar("Fila:", f, ",Columna:", fmt.getIndice() + 1, ",Campo:", fmt.getNombre(),
                ",Valor:", r.get(fmt.getIndice()), ",", error));
    }
}
