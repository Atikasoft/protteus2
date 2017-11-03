/*
 *  CargaMasivaTramite.java
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
 *  Jun 28, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.colas.tramites;

import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.CargaMasivaTramiteVO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class CargaMasivaTramite {

    /**
     * Total de columnas para tramites de ingresos.
     */
    protected static final int COLUMNAS_INGRESOS = 62;

    /**
     * Total de columnas para tramites de cesaciones.
     */
    protected static final int COLUMNAS_CESACIONES = 38;

    /**
     * Mesanje de validacion en campo obligatorio.
     */
    protected static final String MENSAJE_VALIDACION_CAMPO_OBLIGATORIO = " es obligatorio.";

    /**
     * Mesanje de validacion en campo de tipo numerico.
     */
    protected static final String MENSAJE_VALIDACION_CAMPO_NUMERICO = "debe ser númerico.";

    /**
     * Mesanje de validacion en campo de tipo fecha.
     */
    protected static final String MENSAJE_VALIDACION_CAMPO_FECHA = "debe ser fecha.";

    /**
     * Mesanje de validacion en campo de tipo fecha.
     */
    protected static final String MENSAJE_VALIDACION_DOMINIO = "contiene un valor no válido";

    /**
     * Mensaje de validacion de repetidos.
     */
    protected static final String MENSAJE_VALIDACION_REPETIDOS = "se encuentra repetido en el archivo.";

    /**
     * Constructor sin argumentos.
     */
    public CargaMasivaTramite() {
        super();

    }

    /**
     * Validar de columnas
     *
     * @param vo datos de los tramites a migrar
     * @return indicar si la validacion es correcta
     */
    protected Boolean validarNumeroColumnas(final CargaMasivaTramiteVO vo) {
        Boolean validado = Boolean.FALSE;
        if (vo.getTramite().getTipoMovimiento().getClase().getGrupo().getNemonico().equals(
                GrupoEnum.INGRESOS.getCodigo())) {
            // validar numero de columnas para ingresos
            if (vo.getLinea().length == COLUMNAS_INGRESOS) {
                validado = Boolean.TRUE;
            } else {
                vo.getMensaje().append("\t* Para un trámite de INGRESO el archivo debe tener ").append(COLUMNAS_INGRESOS).
                        append(" columnas, actualmente tiene ").append(vo.getLinea().length).append(" columnas.").append(
                                "\n");
            }
        } else if (vo.getTramite().getTipoMovimiento().getClase().getGrupo().getNemonico().equals(
                GrupoEnum.SALIDAS.getCodigo())) {
            // validar numero de columnas para ingresos
            if (vo.getLinea().length == COLUMNAS_CESACIONES) {
                validado = Boolean.TRUE;
            } else {
                vo.getMensaje().append("\t* Para un trámite de CESACION el archivo debe tener ").append(
                        COLUMNAS_CESACIONES).
                        append("columnas, actualmente tiene ").append(vo.getLinea().length).append(" columnas.").append(
                                "\n");
            }
        } else {
            if (vo.getLinea().length != COLUMNAS_INGRESOS) {
                vo.getMensaje().append("\t* Solo se puede cargar masivamente trámites de INGRESOS o CESACIONES,").append(
                        "el presente trámite corresponde al ").append(vo.getTramite().getTipoMovimiento().getClase().
                                getGrupo().
                                getNombre()).append("\n");
            }
        }
        return validado;
    }

    /**
     * Valida campo obligatorio
     *
     * @param vo datos de los tramites
     * @param mensaje mensaje de validacion
     */
    protected void validarCampoObligatorio(final CargaMasivaTramiteVO vo, final StringBuilder mensaje) {
        if (vo.getCampo() == null || vo.getCampo().trim().isEmpty()) {
            mensaje.append("\t* fila: ").append(vo.getFila()).append(",columna: ").append(vo.getColumna()).append(
                    ", campo ").append("'").append(vo.getNombreCampo()).append("' ").append(
                            MENSAJE_VALIDACION_CAMPO_OBLIGATORIO).append("\n");
        }
    }

    /**
     * Validar campos numericos
     *
     * @param vo datos de los tramites
     * @param mensaje mensaje de validacion
     */
    protected void validarCampoNumerico(final CargaMasivaTramiteVO vo, final StringBuilder mensaje) {
        try {
            if (!vo.getCampo().trim().isEmpty()) {
                BigDecimal bigDecimal = new BigDecimal(vo.getCampo());
            }
        } catch (Exception e) {
            mensaje.append("\t* fila: ").append(vo.getFila()).append(",columna: ").append(vo.getColumna()).append(
                    ", campo ").append("'").append(vo.getNombreCampo()).append("' con valor '").append(vo.getCampo()).
                    append("' ").append(MENSAJE_VALIDACION_CAMPO_NUMERICO).append("\n");
        }
    }

    /**
     * Validar campos tipo fecha
     *
     * @param vo datos de los tramites
     * @param mensaje mensaje de validacion
     */
    protected void validarCampoFecha(final CargaMasivaTramiteVO vo, final StringBuilder mensaje) {
        try {
            if (!vo.getCampo().trim().isEmpty()) {
                new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(vo.getCampo());
            }
        } catch (Exception e) {
            mensaje.append("\t* fila: ").append(vo.getFila()).append(",columna: ").append(vo.getColumna()).append(
                    ", campo ").append("'").append(vo.getNombreCampo()).append("' con valor '").append(vo.getLinea()[vo.
                            getColumna() - 1]).append("' ").append(MENSAJE_VALIDACION_CAMPO_FECHA).append("\n");
        }
    }

    /**
     * Validar un dominio
     *
     * @param vo datos del tramite
     * @param mensaje mensaje de validacion
     */
    protected void validarDominioSimple(final CargaMasivaTramiteVO vo, final StringBuilder mensaje) {
        if (!vo.getCampo().trim().isEmpty() && vo.getDominio() != null && vo.getDominio().indexOf(vo.getLinea()[vo.
                getColumna() - 1]) == -1) {
            mensaje.append("\t* fila: ").append(vo.getFila()).append(",columna: ").append(vo.getColumna()).append(
                    ", campo ").append("'").append(vo.getNombreCampo()).append("' con valor '").append(vo.getLinea()[vo.
                            getColumna() - 1]).append("' ").append(MENSAJE_VALIDACION_DOMINIO).append(", valores aceptados ").
                    append(vo.getDominio()).append(".\n");
        }
    }

//    protected void validarDominioCatalogos(final CargaMasivaTramiteVO vo, final List<CatalogoDetalle> catalogo,
//            final StringBuilder mensaje) {
//        if (vo.getCatalogo() != null && !vo.getCampo().trim().isEmpty()) {
//            Boolean existe = Boolean.FALSE;
//            for (CatalogoDetalle cd : catalogo) {
//                if (cd.getCodigo().equals(vo.getCampo())) {
//                    existe = Boolean.TRUE;
//                    break;
//                }
//            }
//            if (!existe) {
//                mensaje.append("\t* fila: ").append(vo.getFila()).append(",columna: ").append(vo.getColumna()).append(
//                        ", campo ").append("'").append(vo.getNombreCampo()).append("' con valor '").append(vo.getCampo()).
//                        append("' ").append(MENSAJE_VALIDACION_DOMINIO).append(".\n");
//            }
//        }
//    }
    /**
     *
     * @param vo datos del tramite
     * @param columnaValidar numero de la columna a validar
     * @param campoTipoMovimiento tipo d emovimiento
     * @param mensaje mensaje de la validacion
     */
    private void validarCampo(final CargaMasivaTramiteVO vo, final int columnaValidar, final String campoTipoMovimiento,
            final StringBuilder mensaje) {
        if (columnaValidar == vo.getColumna() && campoTipoMovimiento != null
                && campoTipoMovimiento.equals(CamposConfiguracionEnum.OBLIGATORIO.getCodigo())) {
            validarCampoObligatorio(vo, mensaje);
        }
    }
}
