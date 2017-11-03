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

import com.Ostermiller.util.CSVParse;
import com.Ostermiller.util.CSVParser;
import ec.com.atikasoft.proteus.dao.CesacionDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.TramiteDao;
import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.enums.FormatoArchivoTramiteCesacionEnum;
import ec.com.atikasoft.proteus.modelo.Cesacion;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.servicio.ReglaServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.CargaMasivaTramiteVO;
import ec.com.atikasoft.proteus.vo.DetalleMovimientoVO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class CargaMasivaTramiteCesacion extends CargaMasivaTramite {

    /**
     * Dao de cesacion.
     */
    @EJB
    private CesacionDao cesacionDao;

    /**
     * Servicio de regla.
     */
    @EJB
    private ReglaServicio reglaServicio;

    /**
     * Dao de paramtros.
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     * Procesa un archivo especifico.
     *
     * @param vo datos del tramite de cesacion
     * @return indicador si el proceso se realizo correctamente
     * @throws FileNotFoundException error de archivo no existe
     * @throws IOException error de entrada / salida
     * @throws Exception error general
     */
    public Boolean procesarArchivo(final CargaMasivaTramiteVO vo) throws FileNotFoundException, IOException, Exception {
        List<String> opis = new ArrayList<>();
        List<String> numerosDocumentos = new ArrayList<>();
        CSVParse csvp = new CSVParser(new FileInputStream(vo.getFile()), ';');
        String[] linea = csvp.getLine();
        vo.setLinea(linea);
        Boolean resultado = validarNumeroColumnas(vo);
        if (resultado) {
//            Map<String, List<CatalogoDetalle>> catalogos = cargarCatalogos();
            // validacion de datos.
            int fila = 1;
            while (linea != null) {
                if (fila > 1) {
                    vo.setLinea(linea);
                    vo.setFila(fila);
//                    vo.setCatalogos(catalogos);
                    Boolean r = validar(vo);
                    if (!r) {
                        resultado = r;
                    }
                    r = validarDatosRepetidos(vo, opis, numerosDocumentos);
                    if (!r) {
                        resultado = r;
                    }
                }
                linea = csvp.getLine();
                fila++;
                vo.getMensaje().append("\n");
            }
            // registro de datos.
            if (resultado) {
                // guarda los puesto.
                csvp = new CSVParser(new FileInputStream(vo.getFile()), ';');
                String[][] datos = csvp.getAllValues();
//                List<SrhvOrganicoPosicionalIndividual> puestos = new ArrayList<SrhvOrganicoPosicionalIndividual>();
//                SrhvOrganicoPosicionalIndividual filtro = new SrhvOrganicoPosicionalIndividual();
                fila = 1;
                for (String[] r : datos) {
                    if (fila > 1) {
//                        filtro.setId(Long.parseLong(r[FormatoArchivoTramiteCesacionEnum.OPI_ID.getColumna() - 1]));
//                        filtro.setInstitucionId(vo.getTramite().getInstitucionEjercicioFiscal().getId());
//                        puestos.addAll(puestoServicio.buscarPuestoporFiltros(filtro));
                    }
                    fila++;
                }
                // registra movimintos
//                tramiteServicio.guardarMovimientos(vo.getTramite(), puestos, vo.getUsuario(), false);
//                tramiteDao.flush();
                //registrar datos en movimientos creados.
                List<Cesacion> cesaciones = cesacionDao.buscarPorTramite(vo.getTramite().getId());
                csvp = new CSVParser(new FileInputStream(vo.getFile()), ';');
                datos = csvp.getAllValues();
                Long opi;
                fila = 1;
                for (String[] r : datos) {
                    if (fila > 1) {
                        opi = Long.parseLong(r[FormatoArchivoTramiteCesacionEnum.OPI_ID.getColumna() - 1]);
                        Cesacion c = null;
                        for (Cesacion cesacion : cesaciones) {
                            if (cesacion.getMovimiento().getDistributivoDetalleId().compareTo(opi) == 0) {
                                c = cesacion;
                                break;
                            }
                        }
                        Movimiento m = c.getMovimiento();
                        m.setConJustificacion(true);
                        m.getListaCesaciones().add(c);
                        poblarDatos(r, m, c);
                        DetalleMovimientoVO dmvo = new DetalleMovimientoVO();
                        dmvo.setCesacion(c);
//                        tramiteServicio.guardarEdicionMovimiento(m, m.getTramite().getTipoMovimiento(), dmvo);
                    }
                    fila++;
                }

                // ejecutar reglas de validacion
                List<ParametroGlobal> parametros = parametroGlobalDao.buscarTodos();
                List<ReglaMensaje> mensajes = new ArrayList<ReglaMensaje>();
                Boolean mensaje = reglaServicio.ejecutar(vo.getTramite().getId(), mensajes, vo.getUsuario(), false,
                        parametros);
                if (!mensaje) {
                    resultado = Boolean.FALSE;
                    vo.getMensaje().append("\nOBSERVACIONES ENCONTRADAS\n");
                    int i = 1;
                    for (ReglaMensaje m : mensajes) {
                        vo.getMensaje().append(i).append(") Partida General:").append(m.getPartidaGeneral()).append(
                                " Partida Individual").append(m.getPartidaIndividual()).append(" Obligatorio:").append(m.
                                        getObligatorio() ? "SI" : "NO");
                        if (m.getRegla() != null) {
                            vo.getMensaje().append(" Regla:").append(m.getRegla().getNombre());

                        }
                        vo.getMensaje().append("\n");
                        vo.getMensaje().append("\tObservación:").append(m.getMensaje()).append("\n\n");
                        i++;
                    }
                }
            }
        }
        return resultado;
    }

    /**
     * Se encargar de poblar los datos en el tramite
     *
     * @param r datos
     * @param m movimiento a poblar
     * @param c datos de la cesacion
     * @throws ParseException error de transformacion
     */
    private void poblarDatos(String[] r, Movimiento m, Cesacion c) throws ParseException {
        String valor = buscarValor(FormatoArchivoTramiteCesacionEnum.ISA, r);
        if (valor != null && valor.equals(FormatoArchivoTramiteCesacionEnum.OBLIGATORIEDAD_SI)) {
            m.setSituacionActual(true);
        } else {
            m.setSituacionActual(false);
        }
        valor = buscarValor(FormatoArchivoTramiteCesacionEnum.ISP, r);
        if (valor != null && valor.equals(FormatoArchivoTramiteCesacionEnum.OBLIGATORIEDAD_SI)) {
            m.setSituacionPropuesta(true);
        } else {
            m.setSituacionPropuesta(false);
        }
        valor = buscarValor(FormatoArchivoTramiteCesacionEnum.FDPAP, r);
        if (valor != null) {
            m.setAccionPersonalFechaDocumento(
                    new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(valor));
        }
        valor = buscarValor(FormatoArchivoTramiteCesacionEnum.FAR, r);
        if (valor != null) {
            c.setFechaAceptacionRenuncia(new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(valor));
        }
        valor = buscarValor(FormatoArchivoTramiteCesacionEnum.FPR, r);
        if (valor != null) {
            c.setFechaPresentaRenuncia(new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(valor));
        }
        valor = buscarValor(FormatoArchivoTramiteCesacionEnum.FFA, r);
        if (valor != null) {
            c.setFechaFallecimiento(new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(valor));
        }

        valor = buscarValor(FormatoArchivoTramiteCesacionEnum.FI, r);
        if (valor != null) {
            m.setRigeApartirDe(new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(valor));
        }
        valor = buscarValor(FormatoArchivoTramiteCesacionEnum.FF, r);
        if (valor != null) {
            m.setFechaFin(new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(valor));
        }
        m.setAccionPersonalExplicacion(buscarValor(FormatoArchivoTramiteCesacionEnum.EAP, r));
        m.setAccionPersonalDocumentoPrevio(buscarValor(FormatoArchivoTramiteCesacionEnum.DPAP, r));
        m.setAccionPersonalNumeroDocumento(buscarValor(FormatoArchivoTramiteCesacionEnum.NDPAP, r));
        m.setAsuntoMemo(buscarValor(FormatoArchivoTramiteCesacionEnum.AM, r));
        m.setNumeroMemo(buscarValor(FormatoArchivoTramiteCesacionEnum.NM, r));
        m.setContenidoMemo(buscarValor(FormatoArchivoTramiteCesacionEnum.CM, r));
        c.setCasoFallecimiento(buscarValor(FormatoArchivoTramiteCesacionEnum.CFA, r));
    }

    /**
     * Recuperar datos
     *
     * @param en enumeracion del formato del archivo
     * @param r datos
     * @return valor recuperado
     */
    private String buscarValor(final FormatoArchivoTramiteCesacionEnum en, final String[] r) {
        String valor = null;
        if (!r[en.getColumna() - 1].trim().isEmpty()) {
            valor = r[en.getColumna() - 1].toUpperCase();
        }
        return valor;
    }

    /**
     * Valida los campos obligatorios y su tipo de dato.
     *
     * @param vo datos del tramite de cesacion
     * @return indicador si la validacion es correcta
     */
    private Boolean validar(final CargaMasivaTramiteVO vo) {
        // verificar si se obtiene los datos del servidor del registro civil (cedula) o no (pasaporte)
        if (vo.getLinea()[FormatoArchivoTramiteCesacionEnum.TI.getColumna() - 1].equals("C")) {
            FormatoArchivoTramiteCesacionEnum.AS.setObligatoriedad(FormatoArchivoTramiteCesacionEnum.OBLIGATORIEDAD_NO);
            FormatoArchivoTramiteCesacionEnum.NS.setObligatoriedad(FormatoArchivoTramiteCesacionEnum.OBLIGATORIEDAD_NO);
            FormatoArchivoTramiteCesacionEnum.FN.setObligatoriedad(FormatoArchivoTramiteCesacionEnum.OBLIGATORIEDAD_NO);
            FormatoArchivoTramiteCesacionEnum.EC.setObligatoriedad(FormatoArchivoTramiteCesacionEnum.OBLIGATORIEDAD_NO);
        } else {
            FormatoArchivoTramiteCesacionEnum.AS.setObligatoriedad(FormatoArchivoTramiteCesacionEnum.OBLIGATORIEDAD_SI);
            FormatoArchivoTramiteCesacionEnum.NS.setObligatoriedad(FormatoArchivoTramiteCesacionEnum.OBLIGATORIEDAD_SI);
            FormatoArchivoTramiteCesacionEnum.FN.setObligatoriedad(FormatoArchivoTramiteCesacionEnum.OBLIGATORIEDAD_SI);
            FormatoArchivoTramiteCesacionEnum.EC.setObligatoriedad(FormatoArchivoTramiteCesacionEnum.OBLIGATORIEDAD_SI);
        }
        StringBuilder mensajeLocal = new StringBuilder();
        Boolean validado = Boolean.TRUE;
        FormatoArchivoTramiteCesacionEnum[] formatos = FormatoArchivoTramiteCesacionEnum.values();
        for (FormatoArchivoTramiteCesacionEnum f : formatos) {
            vo.setNombreCampo(f.getDescripcion());
            vo.setColumna(f.getColumna());
            vo.setDominio(f.getDominio());
            vo.setCatalogo(f.getCatalogo());
            vo.setCampo(vo.getLinea()[vo.getColumna() - 1]);
            // Validar obligatoriedad
            if (f.getObligatoriedad().equals(FormatoArchivoTramiteCesacionEnum.OBLIGATORIEDAD_SI)) {

                validarCampoObligatorio(vo, mensajeLocal);
            } else if (f.getObligatoriedad().equals(FormatoArchivoTramiteCesacionEnum.OBLIGATORIEDAD_CONFIGURACION)) {
                // verificar en el tipo e movimiento.
                validarCampoObligatorioTipoMovimiento(vo, mensajeLocal);
            }
            // Validar tipo d dato.
            if (f.getTipo().equals(FormatoArchivoTramiteCesacionEnum.TIPO_NUMERICO)) {
                vo.setNombreCampo(f.getDescripcion());
                vo.setColumna(f.getColumna());
                validarCampoNumerico(vo, mensajeLocal);
            } else if (f.getTipo().equals(FormatoArchivoTramiteCesacionEnum.TIPO_FECHA)) {
                validarCampoFecha(vo, mensajeLocal);
            }
            // Validar dominio.
            validarDominioSimple(vo, mensajeLocal);
//            validarDominioCatalogos(vo, vo.getCatalogos().get(f.getCatalogo()), mensajeLocal);
        }
        if (mensajeLocal.length() > 0) {
            vo.getMensaje().append(mensajeLocal);
            validado = Boolean.FALSE;
        }
        return validado;
    }

    /**
     *
     * @param vo datos del tramite
     * @param mensaje mensaje de validacion
     */
    private void validarCampoObligatorioTipoMovimiento(final CargaMasivaTramiteVO vo, final StringBuilder mensaje) {
        TipoMovimiento tm = vo.getTramite().getTipoMovimiento();
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.TI.getColumna(), tm.getTipoDocumento(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.NI.getColumna(), tm.getNumeroDocumento(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.AS.getColumna(), tm.getApellidoNombre(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.NS.getColumna(), tm.getApellidoNombre(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.FII.getColumna(), tm.getFechaIngresoInstitucion(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.FISP.getColumna(), tm.getFechaIngresoSectorPublico(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.FI.getColumna(), tm.getFechaRigeAPartirDe(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.FF.getColumna(), tm.getFechaHasta(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.EAP.getColumna(), tm.getApExplicacion(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.DPAP.getColumna(), tm.getApDocumentoPrevio(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.NDPAP.getColumna(), tm.getApNumeroDocumento(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.FDPAP.getColumna(), tm.getApFechaDocumento(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.AM.getColumna(), tm.getAsuntoMemorando(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.NM.getColumna(), tm.getNumeroMemorando(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.CM.getColumna(), tm.getContenidoMemorando(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.FAR.getColumna(), tm.getFechaAceptacionRenuncia(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.FPR.getColumna(), tm.getFechaPresentaRenuncia(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.FFA.getColumna(), tm.getFechaFallecimiento(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteCesacionEnum.CFA.getColumna(), tm.getCasoFallecimiento(), mensaje);
    }

    /**
     * Valida un campo especifico
     *
     * @param vo datos del tramite
     * @param columnaValidar numero de columna a validar
     * @param campoTipoMovimiento codigo del tipo de movimiento
     * @param mensaje mensaje de validacion
     */
    private void validarCampo(final CargaMasivaTramiteVO vo, final int columnaValidar, final String campoTipoMovimiento,
            final StringBuilder mensaje) {
        if (columnaValidar == vo.getColumna() && campoTipoMovimiento != null
                && campoTipoMovimiento.equals(CamposConfiguracionEnum.OBLIGATORIO.getCodigo())) {
            validarCampoObligatorio(vo, mensaje);
        }
    }

    /**
     *
     * @param opis
     * @param numerosDocumentos
     * @param linea
     * @param mensaje
     * @return
     */
    private Boolean validarDatosRepetidos(final CargaMasivaTramiteVO vo, final List<String> opis,
            final List<String> numerosDocumentos) {
        Boolean resultado = Boolean.TRUE;
        if (opis.contains(vo.getLinea()[FormatoArchivoTramiteCesacionEnum.OPI_ID.getColumna() - 1])) {
            resultado = Boolean.FALSE;
            vo.getMensaje().append("\t* fila: ").append(vo.getFila()).append(",columna: ").
                    append(FormatoArchivoTramiteCesacionEnum.OPI_ID.
                            getColumna()).
                    append(", campo ").append("'").append(
                            FormatoArchivoTramiteCesacionEnum.OPI_ID.getDescripcion()).append("' con valor '").
                    append(vo.getLinea()[FormatoArchivoTramiteCesacionEnum.OPI_ID.getColumna() - 1]).append("' ").
                    append(MENSAJE_VALIDACION_REPETIDOS);
        }
        if (numerosDocumentos.contains(vo.getLinea()[FormatoArchivoTramiteCesacionEnum.NI.getColumna() - 1])) {
            resultado = Boolean.FALSE;
            vo.getMensaje().append("\t* fila: ").append(vo.getFila()).append(",columna: ").
                    append(FormatoArchivoTramiteCesacionEnum.NI.
                    getColumna()).
                    append(", campo ").append("'").append(
                            FormatoArchivoTramiteCesacionEnum.NI.getDescripcion()).append("' con valor '").
                    append(vo.getLinea()[FormatoArchivoTramiteCesacionEnum.NI.getColumna() - 1]).append("' ").append(
                            MENSAJE_VALIDACION_REPETIDOS);
        }
        opis.add(vo.getLinea()[FormatoArchivoTramiteCesacionEnum.OPI_ID.getColumna() - 1]);
        numerosDocumentos.add(vo.getLinea()[FormatoArchivoTramiteCesacionEnum.NI.getColumna() - 1]);
        return resultado;
    }

    /**
     * Recupera catalogos para validacion.
     *
     * @return
     * @throws Exception
     */
//    private Map<String, List<CatalogoDetalle>> cargarCatalogos() throws Exception {
//        Map<String, List<CatalogoDetalle>> catalogos = new HashMap<String, List<CatalogoDetalle>>();
//        FormatoArchivoTramiteCesacionEnum[] formatos = FormatoArchivoTramiteCesacionEnum.values();
//        for (FormatoArchivoTramiteCesacionEnum f : formatos) {
//            if (f.getCatalogo() != null) {
//                List<CatalogoDetalle> catalogo = catalogoServicio.buscarCatalogoDetalles(f.getCatalogo());
//                catalogos.put(f.getCatalogo(), catalogo);
//            }
//        }
//        return catalogos;
//    }
}
