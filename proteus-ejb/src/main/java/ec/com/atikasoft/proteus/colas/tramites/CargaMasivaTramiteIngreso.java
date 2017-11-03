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
import ec.com.atikasoft.proteus.dao.IngresoDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.TramiteDao;
import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.enums.FormatoArchivoTramiteIngresoEnum;
import ec.com.atikasoft.proteus.modelo.Ingreso;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.servicio.ReglaServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.vo.CargaMasivaTramiteVO;
import ec.com.atikasoft.proteus.vo.DetalleMovimientoVO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
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
public class CargaMasivaTramiteIngreso extends CargaMasivaTramite {

    /**
     * Servicio de tramite.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     * Dao de ingreso.
     */
    @EJB
    private IngresoDao ingresoDao;

    /**
     * Dao de tramite.
     */
    @EJB
    private TramiteDao tramiteDao;

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
     * @param vo
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws Exception
     */
    public Boolean procesarArchivo(final CargaMasivaTramiteVO vo) throws FileNotFoundException, IOException, Exception {
        List<String> opis = new ArrayList<String>();
        List<String> numerosDocumentos = new ArrayList<String>();
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
//                fila = 1;
//                for (String[] r : datos) {
//                    if (fila > 1) {
//                        filtro.setId(Long.parseLong(r[FormatoArchivoTramiteIngresoEnum.OPI_ID.getColumna() - 1]));
//                        filtro.setInstitucionId(vo.getTramite().getInstitucionEjercicioFiscal().getId());
//                        puestos.addAll(puestoServicio.buscarPuestoporFiltros(filtro));
//                    }
//                    fila++;
//                }
                // registra movimintos
//                tramiteServicio.guardarMovimientos(vo.getTramite(), puestos, vo.getUsuario(), false);
//                tramiteDao.flush();
                //registrar datos en movimientos creados.
                List<Ingreso> ingresos = ingresoDao.buscarPorTramite(vo.getTramite().getId());
                csvp = new CSVParser(new FileInputStream(vo.getFile()), ';');
                datos = csvp.getAllValues();
                Long opi;
                fila = 1;
                for (String[] r : datos) {
                    if (fila > 1) {
                        opi = Long.parseLong(r[FormatoArchivoTramiteIngresoEnum.OPI_ID.getColumna() - 1]);
                        Ingreso i = null;
                        for (Ingreso ingreso : ingresos) {
//                            if (ingreso.getMovimiento().getOrganigramaPosicionalIndividualId().compareTo(opi) == 0) {
//                                i = ingreso;
//                                break;
//                            }
                        }
                        Movimiento m = i.getMovimiento();
                        m.setConJustificacion(true);
                        m.getListaIngresos().add(i);
//                        poblarDatos(m, r, i, vo, catalogos);
                        DetalleMovimientoVO dmvo = new DetalleMovimientoVO();
                        dmvo.setIngreso(i);
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
                                " Partida Individual").append(m.getPartidaIndividual()).append(" Regla:").append(m.
                                getRegla().getNombre()).append(" Obligatorio:").append(m.getObligatorio() ? "SI" : "NO").
                                append("\n");
                        vo.getMensaje().append("\tObservación:").append(m.getMensaje()).append("\n\n");
                        i++;
                    }
                }
            }
        }
        return resultado;
    }

    /**
     *
     * @param m
     * @param r
     * @param i
     * @param vo
     * @param catalogos
     * @throws NumberFormatException
     * @throws ParseException
     */
//    private void poblarDatos(Movimiento m, String[] r, Ingreso i, final CargaMasivaTramiteVO vo,
//            Map<String, List<CatalogoDetalle>> catalogos) throws NumberFormatException, ParseException {
//        m.setTipoIdentificacion(buscarValor(FormatoArchivoTramiteIngresoEnum.TI, r));
//        m.setNumeroIdentificacion(buscarValor(FormatoArchivoTramiteIngresoEnum.NI, r));
//        if (m.getTipoIdentificacion().equals(TipoIdentificacionEnum.CEDULA.getCodigo().substring(0, 1))) {
//            m.setTipoIdentificacion(TipoIdentificacionEnum.CEDULA.getCodigo());
//            busquedaPorCedula(m.getNumeroIdentificacion(), m, i, vo.getTramite().getInstitucionEjercicioFiscal().
//                    getId());
//        } else {
//            m.setTipoIdentificacion(TipoIdentificacionEnum.PASAPORTE.getCodigo());
//            m.setNombres(buscarValor(FormatoArchivoTramiteIngresoEnum.NS, r));
//            m.setApellidos(buscarValor(FormatoArchivoTramiteIngresoEnum.AS, r));
//            m.setApellidosNombres(UtilCadena.concatenar(m.getApellidos(), " ", m.getNombres()));
//            m.setConyugue(buscarValor(FormatoArchivoTramiteIngresoEnum.CO, r));
//            String valor = buscarValor(FormatoArchivoTramiteIngresoEnum.FN, r);
//            if (valor != null) {
//                i.setFechaNacimiento(new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(valor));
//                i.setEdad(UtilFechas.calcularEdadEnAnios(i.getFechaNacimiento()));
//                if (i.getEdad() > 80) {
//                    i.setEdad(0);
//                }
//            }
//        }
//        m.setGenero(buscarValor(FormatoArchivoTramiteIngresoEnum.GE, r));
//        i.setAniosResidencia(new BigDecimal(buscarValor(FormatoArchivoTramiteIngresoEnum.AR, r)));
//        i.setDomicilioCallePrincipal(buscarValor(FormatoArchivoTramiteIngresoEnum.CPD, r));
//        i.setDomicilioCalleSecudaria(buscarValor(FormatoArchivoTramiteIngresoEnum.CSD, r));
//        i.setDomicilioNumero(buscarValor(FormatoArchivoTramiteIngresoEnum.NUD, r));
//        i.setDomicilioReferencia(buscarValor(FormatoArchivoTramiteIngresoEnum.RED, r));
//        i.setTelefonoDomicilio(buscarValor(FormatoArchivoTramiteIngresoEnum.TED, r));
//        i.setTelefonoTrabajo(buscarValor(FormatoArchivoTramiteIngresoEnum.TET, r));
//        i.setCorreoElectronico(buscarValor(FormatoArchivoTramiteIngresoEnum.CE, r));
//        i.setNumeroCarrera(buscarValor(FormatoArchivoTramiteIngresoEnum.NC, r));
//        i.setNumeroConadis(buscarValor(FormatoArchivoTramiteIngresoEnum.NCO, r));
//        i.setEspecifiqueDiscapacidad(buscarValor(FormatoArchivoTramiteIngresoEnum.ESD, r));
//        i.setPorcentajeDiscapacidad(Long.valueOf(buscarValor(FormatoArchivoTramiteIngresoEnum.POD, r)));
//        i.setCorrespondeDiscapacidad(buscarValor(FormatoArchivoTramiteIngresoEnum.COD, r));
//        i.setJustificacionDiscapacidad(buscarValor(FormatoArchivoTramiteIngresoEnum.JDI, r));
//        i.setTipoDesignacion(buscarValor(FormatoArchivoTramiteIngresoEnum.TD, r));
//        i.setTiempoJornadaDiaria(Integer.valueOf(buscarValor(FormatoArchivoTramiteIngresoEnum.TJD, r)));
//        i.setTipoTemporada(buscarValor(FormatoArchivoTramiteIngresoEnum.TT, r));
//        i.setAntecedentesContrato(buscarValor(FormatoArchivoTramiteIngresoEnum.AC, r));
//        i.setActividadesContrato(buscarValor(FormatoArchivoTramiteIngresoEnum.CC, r));
//        i.setSiglasTituloAcademico(buscarValor(FormatoArchivoTramiteIngresoEnum.STA, r));
//        m.setAccionPersonalExplicacion(buscarValor(FormatoArchivoTramiteIngresoEnum.EAP, r));
//        m.setAccionPersonalDocumentoPrevio(buscarValor(FormatoArchivoTramiteIngresoEnum.DPAP, r));
//        m.setAccionPersonalNumeroDocumento(buscarValor(FormatoArchivoTramiteIngresoEnum.NDPAP, r));
//        m.setAsuntoMemo(buscarValor(FormatoArchivoTramiteIngresoEnum.AM, r));
//        m.setNumeroMemo(buscarValor(FormatoArchivoTramiteIngresoEnum.NM, r));
//        m.setContenidoMemo(buscarValor(FormatoArchivoTramiteIngresoEnum.CM, r));
//        //
//        String valor = buscarValor(FormatoArchivoTramiteIngresoEnum.EC, r);
//        if (valor != null) {
//            CatalogoDetalle cd = buscarCatalogo(catalogos.get(FormatoArchivoTramiteIngresoEnum.EC.getCatalogo()), valor);
//            i.setEstadoCivil(cd.getId());
//            i.setEstadoCivilDescripcion(cd.getNombre());
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.NA, r);
//        if (valor != null) {
//            CatalogoDetalle cd = buscarCatalogo(catalogos.get(FormatoArchivoTramiteIngresoEnum.NA.getCatalogo()), valor);
//            i.setNacionalidadId(cd.getId());
//            i.setNacionalidadNombre(cd.getNombre());
//            i.setNacionalidadDescripcion(cd.getDescripcion());
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.IE, r);
//        if (valor != null) {
//            CatalogoDetalle cd = buscarCatalogo(catalogos.get(FormatoArchivoTramiteIngresoEnum.IE.getCatalogo()), valor);
//            i.setIdentificacionEtnica(cd.getId());
//            i.setIdentificacionEtnicaNombre(cd.getNombre());
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.PD, r);
//        if (valor != null) {
//            CatalogoDetalle cd = buscarCatalogo(catalogos.get(FormatoArchivoTramiteIngresoEnum.PD.getCatalogo()), valor);
//            i.setParroquia(cd.getId());
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.TID, r);
//        if (valor != null) {
//            CatalogoDetalle cd =
//                    buscarCatalogo(catalogos.get(FormatoArchivoTramiteIngresoEnum.TID.getCatalogo()), valor);
//            i.setTipoDiscapacidad(cd.getId());
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.FII, r);
//        if (valor != null) {
//            i.setFechaIngresoInstitucion(new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(valor));
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.FISP, r);
//        if (valor != null) {
//            i.setFechaIngresoSectorPublico(new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(valor));
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.FI, r);
//        if (valor != null) {
//            m.setRigeApartirDe(new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(valor));
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.FF, r);
//        if (valor != null) {
//            m.setFechaFin(new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(valor));
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.SP, r);
//        if (valor != null && valor.equals(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI)) {
//            i.setServidorPasante(true);
//        }
//        if (valor != null && valor.equals(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO)) {
//            i.setServidorPasante(false);
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.SC, r);
//        if (valor != null && valor.equals(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI)) {
//            i.setServidorCarrera(true);
//        }
//        if (valor != null && valor.equals(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO)) {
//            i.setServidorCarrera(false);
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.DI, r);
//        if (valor != null && valor.equals(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI)) {
//            i.setDiscapacidad(true);
//        }
//        if (valor != null && valor.equals(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO)) {
//            i.setDiscapacidad(false);
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.RC, r);
//        if (valor != null && valor.equals(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI)) {
//            i.setRenovacionContrato(true);
//        }
//        if (valor != null && valor.equals(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO)) {
//            i.setRenovacionContrato(false);
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.ISA, r);
//        if (valor != null && valor.equals(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI)) {
//            m.setSituacionActual(true);
//        } else {
//            m.setSituacionActual(false);
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.ISP, r);
//        if (valor != null && valor.equals(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI)) {
//            m.setSituacionPropuesta(true);
//        } else {
//            m.setSituacionPropuesta(false);
//        }
//        valor = buscarValor(FormatoArchivoTramiteIngresoEnum.FDPAP, r);
//        if (valor != null) {
//            m.setAccionPersonalFechaDocumento(
//                    new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(valor));
//        }
//    }

    private String buscarValor(final FormatoArchivoTramiteIngresoEnum en, final String[] r) {
        String valor = null;
        if (!r[en.getColumna() - 1].trim().isEmpty()) {
            valor = r[en.getColumna() - 1].toUpperCase();
        }
        return valor;
    }

    /**
     * Validad los campos obligatorios y su tipo de dato.
     *
     * @param tramite
     * @param linea
     * @param mensaje
     * @return
     */
    private Boolean validar(final CargaMasivaTramiteVO vo) {
        // verificar si se realizar la validacion de discapacidad.
        if (vo.getLinea()[FormatoArchivoTramiteIngresoEnum.DI.getColumna() - 1].equals(
                FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI)) {
            FormatoArchivoTramiteIngresoEnum.NCO.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI);
            FormatoArchivoTramiteIngresoEnum.ESD.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI);
            FormatoArchivoTramiteIngresoEnum.TID.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI);
            FormatoArchivoTramiteIngresoEnum.POD.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI);
            FormatoArchivoTramiteIngresoEnum.COD.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI);
            FormatoArchivoTramiteIngresoEnum.JDI.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI);
        } else {
            FormatoArchivoTramiteIngresoEnum.NCO.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO);
            FormatoArchivoTramiteIngresoEnum.ESD.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO);
            FormatoArchivoTramiteIngresoEnum.TID.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO);
            FormatoArchivoTramiteIngresoEnum.POD.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO);
            FormatoArchivoTramiteIngresoEnum.COD.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO);
            FormatoArchivoTramiteIngresoEnum.JDI.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO);
        }
        // verificar si se obtiene los datos del servidor del registro civil (cedula) o no (pasaporte)
        if (vo.getLinea()[FormatoArchivoTramiteIngresoEnum.TI.getColumna() - 1].equals("C")) {
            FormatoArchivoTramiteIngresoEnum.AS.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO);
            FormatoArchivoTramiteIngresoEnum.NS.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO);
            FormatoArchivoTramiteIngresoEnum.FN.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO);
            FormatoArchivoTramiteIngresoEnum.EC.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_NO);
        } else {
            FormatoArchivoTramiteIngresoEnum.AS.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI);
            FormatoArchivoTramiteIngresoEnum.NS.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI);
            FormatoArchivoTramiteIngresoEnum.FN.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI);
            FormatoArchivoTramiteIngresoEnum.EC.setObligatoriedad(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI);
        }
        StringBuilder mensajeLocal = new StringBuilder();
        Boolean validado = Boolean.TRUE;
        FormatoArchivoTramiteIngresoEnum[] formatos = FormatoArchivoTramiteIngresoEnum.values();
        for (FormatoArchivoTramiteIngresoEnum f : formatos) {
            vo.setNombreCampo(f.getDescripcion());
            vo.setColumna(f.getColumna());
            vo.setDominio(f.getDominio());
            vo.setCatalogo(f.getCatalogo());
            vo.setCampo(vo.getLinea()[vo.getColumna() - 1]);
            // Validar obligatoriedad
            if (f.getObligatoriedad().equals(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_SI)) {

                validarCampoObligatorio(vo, mensajeLocal);
            } else if (f.getObligatoriedad().equals(FormatoArchivoTramiteIngresoEnum.OBLIGATORIEDAD_CONFIGURACION)) {
                // verificar en el tipo e movimiento.
                validarCampoObligatorioTipoMovimiento(vo, mensajeLocal);
            }
            // Validar tipo d dato.
            if (f.getTipo().equals(FormatoArchivoTramiteIngresoEnum.TIPO_NUMERICO)) {
                vo.setNombreCampo(f.getDescripcion());
                vo.setColumna(f.getColumna());
                validarCampoNumerico(vo, mensajeLocal);
            } else if (f.getTipo().equals(FormatoArchivoTramiteIngresoEnum.TIPO_FECHA)) {
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

    private void validarCampoObligatorioTipoMovimiento(final CargaMasivaTramiteVO vo, final StringBuilder mensaje) {
        TipoMovimiento tm = vo.getTramite().getTipoMovimiento();
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.TI.getColumna(), tm.getTipoDocumento(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.NI.getColumna(), tm.getNumeroDocumento(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.AS.getColumna(), tm.getApellidoNombre(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.NS.getColumna(), tm.getApellidoNombre(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.FII.getColumna(), tm.getFechaIngresoInstitucion(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.FISP.getColumna(), tm.getFechaIngresoSectorPublico(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.TD.getColumna(), tm.getTipoDesignacion(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.TJD.getColumna(), tm.getTiempoJornadaDiaria(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.TT.getColumna(), tm.getTipoTemporada(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.FI.getColumna(), tm.getFechaRigeAPartirDe(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.FF.getColumna(), tm.getFechaHasta(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.EAP.getColumna(), tm.getApExplicacion(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.DPAP.getColumna(), tm.getApDocumentoPrevio(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.NDPAP.getColumna(), tm.getApNumeroDocumento(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.FDPAP.getColumna(), tm.getApFechaDocumento(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.AM.getColumna(), tm.getAsuntoMemorando(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.NM.getColumna(), tm.getNumeroMemorando(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.CM.getColumna(), tm.getContenidoMemorando(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.AC.getColumna(), tm.getAntecedentesContrato(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.CC.getColumna(), tm.getActividadesContrato(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.STA.getColumna(), tm.getSiglasTituloAcademico(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.RC.getColumna(), tm.getRenovacion(), mensaje);
        validarCampo(vo, FormatoArchivoTramiteIngresoEnum.DI.getColumna(), tm.getDiscapacidad(), mensaje);
    }

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
        if (opis.contains(vo.getLinea()[FormatoArchivoTramiteIngresoEnum.OPI_ID.getColumna() - 1])) {
            resultado = Boolean.FALSE;
            vo.getMensaje().append("\t* fila: ").append(vo.getFila()).append(",columna: ").append(FormatoArchivoTramiteIngresoEnum.OPI_ID.
                    getColumna()).
                    append(", campo ").append("'").append(
                    FormatoArchivoTramiteIngresoEnum.OPI_ID.getDescripcion()).append("' con valor '").
                    append(vo.getLinea()[FormatoArchivoTramiteIngresoEnum.OPI_ID.getColumna() - 1]).append("' ").append(
                    MENSAJE_VALIDACION_REPETIDOS);
        }
        if (numerosDocumentos.contains(vo.getLinea()[FormatoArchivoTramiteIngresoEnum.NI.getColumna() - 1])) {
            resultado = Boolean.FALSE;
            vo.getMensaje().append("\t* fila: ").append(vo.getFila()).append(",columna: ").append(FormatoArchivoTramiteIngresoEnum.NI.
                    getColumna()).
                    append(", campo ").append("'").append(
                    FormatoArchivoTramiteIngresoEnum.NI.getDescripcion()).append("' con valor '").
                    append(vo.getLinea()[FormatoArchivoTramiteIngresoEnum.NI.getColumna() - 1]).append("' ").append(
                    MENSAJE_VALIDACION_REPETIDOS);
        }
        opis.add(vo.getLinea()[FormatoArchivoTramiteIngresoEnum.OPI_ID.getColumna() - 1]);
        numerosDocumentos.add(vo.getLinea()[FormatoArchivoTramiteIngresoEnum.NI.getColumna() - 1]);
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
//        FormatoArchivoTramiteIngresoEnum[] formatos = FormatoArchivoTramiteIngresoEnum.values();
//        for (FormatoArchivoTramiteIngresoEnum f : formatos) {
//            if (f.getCatalogo() != null) {
//                List<CatalogoDetalle> catalogo = catalogoServicio.buscarCatalogoDetalles(f.getCatalogo());
//                catalogos.put(f.getCatalogo(), catalogo);
//            }
//        }
//        return catalogos;
//    }

    /**
     *
     * @param numeroIdentificacion
     * @param m
     * @param i
     * @param institucionId
     */
    private void busquedaPorCedula(final String numeroIdentificacion, final Movimiento m, final Ingreso i,
            final Long institucionId) {
//        RegistroCivilServicio registroCivilServicio = new RegistroCivilServicio();
//        try {
//            buscarServidorSithh(numeroIdentificacion, m, i, institucionId);
//            try {
//                Persona persona = registroCivilServicio.obtenerPorCedula(numeroIdentificacion);
//                if (persona != null) {
//                    i.setFechaNacimiento(java.sql.Date.valueOf(persona.getFechaNacimiento()));
//                    i.setEdad(UtilFechas.calcularEdadEnAnios(i.getFechaNacimiento()));
//                    m.setApellidosNombres(persona.getApellidos().concat(" ").concat(persona.getNombres()));
//                    m.setApellidos(persona.getApellidos());
//                    m.setNombres(persona.getNombres());
//                    m.setConyugue(persona.getConyugue());
//                }
//            } catch (Exception e) {
//            }
//        } catch (Exception e) {
//        }
    }

    /**
     * Este metodo busca a un servidor en el sithh.
     *
     * @param i Ingreso
     * @param numeroIdentificacion String
     * @throws Exception control de error.
     */
//    private void buscarServidorSithh(final String numeroIdentificacion, final Movimiento m, final Ingreso i,
//            final Long institucionId) throws Exception {
//        // obtener datos del servidor desde el sith.
//        Servidor s = null;//servidorServicio.buscarPorNumeroDocumento(numeroIdentificacion);
//        if (s == null) {
//            i.setGenero(null);
//            i.setNacionalidadId(null);
//            i.setAniosResidencia(null);
//            i.setParroquia(null);
//            i.setPais(null);
//            i.setRegion(null);
//            i.setProvincia(null);
//            i.setCanton(null);
//            i.setParroquia(null);
//            i.setServidorPasante(null);
//            i.setServidorCarrera(null);
//            i.setDomicilioCallePrincipal(null);
//            i.setDomicilioCalleSecudaria(null);
//            i.setDomicilioNumero(null);
//            i.setDomicilioReferencia(null);
//            i.setTelefonoDomicilio(null);
//            i.setTelefonoTrabajo(null);
//            i.setCorreoElectronico(null);
//        } else {
//            i.setGenero(s.getSexoId());
//            i.setNacionalidadId(s.getNacionalidadId());
//            m.setServidorId(s.getId());
//            if (s.getAniosResidencia() != null) {
//                i.setAniosResidencia(new BigDecimal(s.getAniosResidencia()));
//            }
//            i.setIdentificacionEtnica(s.getIndentificacionEtnicaId());
//            if (s.getDireccionProvincia() != null) {
//                CatalogoDetalle provincia = catalogoServicio.buscarProvincia(s.getDireccionProvinciaId());
//                i.setParroquia(s.getDireccionParroquiaId());
//                i.setPais(provincia.getCatalogoDetalle().getCatalogoDetalle().getId());
//                i.setRegion(provincia.getCatalogoDetalle().getId());
//                i.setProvincia(s.getDireccionProvinciaId());
//                i.setCanton(s.getDireccionCantonId());
//            }
//            i.setServidorPasante(
//                    s.getServidorPasanteConvenio() == null || s.getServidorPasanteConvenio().
//                    intValue() == 0 ? false : true);
//            i.setServidorCarrera(
//                    s.getServidorCarrera() == null || s.getServidorCarrera().intValue() == 0
//                    ? false : true);
//            i.setDomicilioCallePrincipal(s.getDireccionCallePrincipal());
//            i.setDomicilioCalleSecudaria(s.getDireccionCalleSecundaria());
//            i.setDomicilioNumero(s.getDireccionNumero());
//            i.setDomicilioReferencia(s.getDireccionReferencia());
//            i.setTelefonoDomicilio(s.getTelefonoDomicilio());
//            i.setTelefonoTrabajo(s.getTelefonoTrabajo());
//            i.setCorreoElectronico(s.getCorreoElectronico());
//            i.setMotivoIngreso(i.getMotivoIngreso());
//
//            SrhvOrganicoPosicionalIndividual sopi = new SrhvOrganicoPosicionalIndividual();
//            sopi.setServidorId(s.getId());
//            sopi.setInstitucionId(institucionId);
//            List<SrhvOrganicoPosicionalIndividual> puestos = puestoServicio.buscarPuestoporFiltros(sopi);
//            if (puestos.isEmpty()) {
//                i.setFechaIngresoInstitucion(null);
//                i.setFechaIngresoSectorPublico(null);
//            } else {
//                SrhvOrganicoPosicionalIndividual get = puestos.get(0);
//                i.setFechaIngresoInstitucion(get.getFechaIngresoInstitucion());
//                i.setFechaIngresoSectorPublico(get.getFechaIngresoSectorPublico());
//            }
//        }
//    }

//    private CatalogoDetalle buscarCatalogo(final List<CatalogoDetalle> detalles, final String catalogo) {
//        CatalogoDetalle cd = null;
//        for (CatalogoDetalle d : detalles) {
//            if (d.getCodigo().equals(catalogo)) {
//                cd = d;
//                break;
//            }
//        }
//        return cd;
//    }
}
