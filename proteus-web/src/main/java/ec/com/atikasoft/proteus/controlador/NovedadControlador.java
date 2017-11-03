/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.NovedadHelper;
import ec.com.atikasoft.proteus.dao.DatoAdicionalDao;
import ec.com.atikasoft.proteus.dao.NominaDao;
import ec.com.atikasoft.proteus.dao.NovedadDetalleDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.enums.EstadoNominaEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoDatoEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoDetalleServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.NovedadServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.util.UtilNumeros;
import ec.com.atikasoft.proteus.vo.BusquedaNovedadVO;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "novedadBean")
@ViewScoped
public class NovedadControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(TipoNominaControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/novedad/lista_novedades.jsf";

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/novedad/novedad.jsf";

    /**
     * ListadatoAdicionales Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de administracion de novedades.
     */
    @EJB
    private NovedadServicio novedadServicio;

    /**
     * Dao de dato adicional.
     */
    @EJB
    private DatoAdicionalDao datoAdicionalDao;

    /**
     *
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     *
     */
    @EJB
    private UnidadOrganizacionalDao unidadOrganizacionalDao;

    /**
     *
     */
    @EJB
    private NominaDao nominaDao;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @EJB
    private NovedadDetalleDao novedadDetalleDao;

    /**
     *
     */
    @EJB
    private DistributivoDetalleServicio distributivoDetalleServicio;

    @EJB
    /**
     *
     */
    private DesconcentradoServicio desconcentradoServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{novedadHelper}")
    private NovedadHelper novedadHelper;

    @Override
    @PostConstruct
    public void init() {
        try {
            novedadHelper.setBusquedaNovedadVO(new BusquedaNovedadVO());
            if (novedadHelper.getEsNuevo()) {
                novedadHelper.setDatoAdicionalId(null);
                novedadHelper.setPeriodoNominaId(null);
                novedadHelper.setNominaId(null);
            }
            if (novedadHelper.getNominaId() == null && novedadHelper.getPeriodoNominaId() == null) {
                llenarComboPeriodoNomina();
                llenarComboNominas();
                llenarComboDatosAdicionales();
                novedadHelper.getListaNovedades().clear();
            }
            ParametroInstitucional piRRHH
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            novedadHelper.setConAcceso(Boolean.TRUE);
            novedadHelper.setEsRRHH(esRRHH(piRRHH.getValorTexto()));
            novedadHelper.setUsuarioVO(obtenerUsuarioConectado());
            novedadHelper.getListaNovedadDetalleEliminados().clear();

            // verifica si el usuario puede registrar o modificar novedades.
            ParametroInstitucional piDiaMax = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
                    obtenerUsuarioConectado().getInstitucion().getId(),
                    ParametroInstitucionCatalogoEnum.DIA_MAXIMO_REGISTRO_NOVEDADES_DESCONCENTRADOS.getCodigo());
            novedadHelper.setTienePermiso(UtilFechas.obtenerDiaDelMes(new Date()) <= piDiaMax.getValorNumerico().intValue());
            // verifica si el usuario pertenece a un desconcentrado.
            novedadHelper.setEsDesconcentrado(null);
            if (novedadHelper.getUnidadOrganizacional() != null) {
                novedadHelper.setEsDesconcentrado(novedadHelper.getUnidadOrganizacional().getDesconcentrado());
            }
            buscarTiposDocumentos();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Recupera el nombre completo de una unidad organizacional.
     *
     * @return
     */
    private String nombreUnidadOrganizacional() {
        return nombreUnidadOrganizacional(obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().
                getUnidadOrganizacional());
    }

    private void buscarTiposDocumentos() {
        iniciarCombos(novedadHelper.getListaTipoIdentificacion());
        for (TipoDocumentoEnum et : TipoDocumentoEnum.values()) {
            novedadHelper.getListaTipoIdentificacion().add(
                    new SelectItem(et.getNemonico(), et.getNombre()));
        }
    }

    /**
     *
     * @param uo
     * @return
     */
    private String nombreUnidadOrganizacional(UnidadOrganizacional uo) {
        return uo == null ? "" : uo.getRuta();
    }

    /**
     * metodo que busca el servidor por ej nombre
     */
    public void buscarServidorPorNombre() {
        try {
            if (novedadHelper.getNombreServidor().length() < 3) {
                mostrarMensajeEnPantalla("Ingrese al menos tres letras para la busqueda... ",
                        FacesMessage.SEVERITY_INFO);
            } else {
                ParametroInstitucional pi
                        = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                                getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
                novedadHelper.getListaNovedadDetalles();
                novedadHelper.getListaServidores().clear();
                novedadHelper.setListaServidores(admServicio.buscarServidorPorNombreDistributivo(
                        novedadHelper.getNombreServidor(), obtenerUsuarioConectado(), esRRHH(pi.getValorTexto())));
            }

        } catch (Exception ex) {
            Logger.getLogger(NovedadControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * metodo para lenar la lista de novedades detalle
     */
    public void llenarListaNovedadDetalle() {
        if (!novedadHelper.getListaServidores().isEmpty()) {
            // verificar qu no se haya seleccionado anteriormente.
            boolean existe = false;
            for (NovedadDetalle nd : novedadHelper.getListaNovedadDetalles()) {
                if (nd.getServidorId().compareTo(novedadHelper.getServidorSelecto().getId()) == 0) {
                    if (novedadHelper.getDatoAdicionalSeleccionado().getAceptaDuplicado() != null
                            && novedadHelper.getDatoAdicionalSeleccionado().getAceptaDuplicado()) {
                        if (nd.getValor() == null) {
                            existe = true;
                            break;
                        }
                    } else {
                        existe = true;
                        break;
                    }
                }
            }
            if (!existe) {
                NovedadDetalle novedadDetalle = new NovedadDetalle();
                novedadDetalle.setServidor(novedadHelper.getServidorSelecto());
                novedadDetalle.setServidorId(novedadHelper.getServidorSelecto().getId());
                novedadHelper.getListaNovedadDetalles().add(novedadDetalle);
                novedadHelper.setTotalRegistrosCargados(0);
            }
            novedadHelper.getListaServidores().clear();
            novedadHelper.setServidorSelecto(null);
        }
    }

    /**
     * evento cambio de tab.
     *
     * @param event
     */
    public void onTabChange(TabChangeEvent event) {
        limpiarFiltros();
        if ("tab1".equals(event.getTab().getId())) {
            novedadHelper.setTab1("tab1");
        }
        if ("tab2".equals(event.getTab().getId())) {
            novedadHelper.setTab1("tab2");
        }
        if ("tab3".equals(event.getTab().getId())) {
            llenarComboPeriodoNomina();
            novedadHelper.setTab1("tab3");
        }
    }

    /**
     * comprueba los campos requeridos de los filtros por cada tab.
     *
     * @param tab String
     * @return
     */
    private Boolean camposRequeridos() {
        FacesContext context = FacesContext.getCurrentInstance();
        Boolean retorno = Boolean.TRUE;
        String mensaje = "";
        int x = 0;
        if ((novedadHelper.getNombreServidor() == null || novedadHelper.getNombreServidor().isEmpty())
                && novedadHelper.getTab1().equals("tab1")) {
            retorno = Boolean.FALSE;
            mensaje = "El nombre del servidor es un campo requerido";
            mostrarMensajeEnPantalla(mensaje, FacesMessage.SEVERITY_ERROR);
        }
        if ((novedadHelper.getNombreArchivo() == null || novedadHelper.getNombreArchivo().isEmpty())
                && novedadHelper.getTab1().equals("tab2")) {
            retorno = Boolean.FALSE;
            mensaje = "El nombre del archivo es un campo requerido";
            mostrarMensajeEnPantalla(mensaje, FacesMessage.SEVERITY_ERROR);
        }
        if ((novedadHelper.getPeriodoNominaCopiaId() == null)
                && novedadHelper.getTab1().equals("tab3")) {
            retorno = Boolean.FALSE;
            mensaje = "El periodo nómina es un campo requerido";
            mostrarMensajeEnPantalla(mensaje, FacesMessage.SEVERITY_ERROR);
        }
        return retorno;
    }

    /**
     * cargar el Archido csv con los servidores.
     *
     * @param event FileUploadEvent
     */
    public void cargarArchivo(final FileUploadEvent event) {
        try {
            UploadedFile file = event.getFile();
            InputStream in = file.getInputstream();
            String nombreArchivo = file.getFileName();
            int indice = nombreArchivo.lastIndexOf(".");
            String extencion = nombreArchivo.substring(indice);
            File tempFile = UtilArchivos.getFileFromBytes(in, nombreArchivo, extencion);
            novedadHelper.setNombreArchivo(nombreArchivo);
            RandomAccessFile acc = new RandomAccessFile(tempFile, "r");
            String linea;
            novedadHelper.getListaNovedadDetallesNoExistenServidor().clear();
            novedadHelper.getListaNovedadDetalles().clear();

            while ((linea = acc.readLine()) != null) {
                String[] split = linea.split("[;]");
                if (split.length == 3) {
                    NovedadDetalle novedad = new NovedadDetalle();
                    novedad.setServidor(new Servidor());
                    novedad.setServidorId(null);
                    novedad.getServidor().setTipoIdentificacion(split[0]);
                    novedad.getServidor().setNumeroIdentificacion(split[1]);
                    novedad.setValorNumericoEnCSV(split[2].trim());
                    novedadHelper.getListaNovedadDetallesNoExistenServidor().add(novedad);
                }
            }
            cargarListaDetalles();
            if (novedadHelper.getValoresEditar()) {
                FacesMessage msg = new FacesMessage("Total registros cargados=" + " " + novedadHelper.
                        getTotalRegistrosCargados());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                novedadHelper.setValoresEditar(Boolean.FALSE);
            }

        } catch (Exception e) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     *
     * @param uo
     */
    public void cargarListaDetalles() {
        try {
            List<UnidadOrganizacional> unidadesAcceso = desconcentradoServicio.buscarUnidadesDeAcceso(
                    obtenerUsuarioConectado().getServidor().getId(),
                    FuncionesDesconcentradosEnum.NOVEDADES.getCodigo());
            int count = 0;
            Nomina nomina = nominaDao.buscarPorId(novedadHelper.getNominaFormularioId());
            novedadHelper.setTotalRegistrosCargados(0);
            for (NovedadDetalle nd : novedadHelper.getListaNovedadDetallesNoExistenServidor()) {
                nd.getErroresAlCargarServidor().clear();
                novedadServicio.validarServidor(nomina, obtenerUsuarioConectado().
                        getInstitucionEjercicioFiscal().getId(), nd, novedadHelper.getEsRRHH(), unidadesAcceso);
                if (nd.getErroresAlCargarServidor().isEmpty()) {
                    novedadHelper.setValoresEditar(Boolean.TRUE);
                    count++;
                    iniciarDatosEntidad(novedadHelper.getNovedadDetalle(), Boolean.TRUE);
                    nd.setValorDescontado(BigDecimal.ZERO);
                    nd.setNovedadId(nd.getNovedadId());
                    nd.setValor(new BigDecimal(nd.getValorNumericoEnCSV().trim()));
                    novedadHelper.setNovedadDetalle(nd);
                    novedadHelper.getListaNovedadDetalles().add(nd);
                }
            }
            novedadHelper.setTotalRegistrosCargados(count);
            calculoValores();
            for (NovedadDetalle n : novedadHelper.getListaNovedadDetalles()) {
                novedadHelper.getListaNovedadDetallesNoExistenServidor().remove(n);
            }
        } catch (Exception ex) {
            Logger.getLogger(NovedadControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarRepetidos(String tipoDocumento, String numeroDocumento) {
        Boolean retorno = Boolean.FALSE;
//        for (NovedadDetalle novedadDetalle : novedadHelper.getListaNovedadDetalles()) {
//            if (novedadDetalle.getServidor().getTipoIdentificacion().equals(tipoDocumento)
//                    && novedadDetalle.getServidor().getNumeroIdentificacion().equals(numeroDocumento)) {
//                return Boolean.TRUE;
//            }
//        }
        return retorno;
    }

    public List eliminarDuplicados(List list) {
        novedadHelper.setListaNovedadDetalles(new ArrayList<NovedadDetalle>());
        Set<NovedadDetalle> set = new LinkedHashSet(list);
        novedadHelper.getListaNovedadDetalles().addAll(set);
        return novedadHelper.getListaNovedadDetalles();
    }

    /**
     * metodo para limpiar los registros.
     */
    public void limpiarFiltros() {
        novedadHelper.getListaServidores().clear();
        novedadHelper.setNombreServidor("");
        novedadHelper.setNombreArchivo("");
        novedadHelper.setPeriodoNominaCopiaId(null);
    }

    /**
     *
     * @return
     */
    public String guardarNovedadPopup() {
        if (validarNovedad()) {
            ejecutarComandoPrimefaces("confirmGuardar.show();");
        }
        return null;
    }

    /**
     * este metodo procesa el guardar para los tres tipos diferentes de carga a los servidores.
     *
     * @return null
     */
    public String guardarNovedad() {
        try {
            if (novedadHelper.getEsNuevo()) {
                if (!novedadHelper.getListaNovedadDetalles().isEmpty()) {
                    iniciarDatosEntidad(novedadHelper.getNovedad(), Boolean.TRUE);
                    novedadHelper.getNovedad().setDatoAdicionalId(novedadHelper.getDatoAdicionalFormularioId());
                    novedadHelper.getNovedad().setInstitucionEjercicioFiscalId(obtenerUsuarioConectado().
                            getInstitucionEjercicioFiscal().getId());
                    novedadHelper.getNovedad().setNominaId(novedadHelper.getNominaFormularioId());
                    novedadHelper.getNovedad().setNumero(admServicio.generarNumeroTramite(
                            obtenerUsuarioConectado().getInstitucionEjercicioFiscal(), obtenerUsuarioConectado().
                            getInstitucionEjercicioFiscal().getId()));
                    novedadHelper.getNovedad().setDescripcion(novedadHelper.getDescripcion().toUpperCase());
                    novedadHelper.getNovedad().setId(null);
                    if (novedadHelper.getUnidadOrganizacional() != null) {
                        novedadHelper.getNovedad().setUnidadOrganizacionalId(
                                novedadHelper.getUnidadOrganizacional().getId());
                    }
                    if (valorNull()) {
                        mostrarMensajeEnPantalla("La columna del valor en los detalles es un campo requerido",
                                FacesMessage.SEVERITY_ERROR);
                    } else {
                        for (NovedadDetalle nd : novedadHelper.getListaNovedadDetalles()) {
                            iniciarDatosEntidad(nd, Boolean.TRUE);
                            nd.setId(null);
                            nd.setValorDescontado(BigDecimal.ZERO);
                        }
                        novedadServicio.crearNovedad(novedadHelper.getNovedad(), novedadHelper.
                                getListaNovedadDetalles(),
                                obtenerUsuarioConectado().getUsuario());
                        limpiar();
                        mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);

                    }
                }
            } else {
                novedadHelper.getNovedad().setDescripcion(novedadHelper.getDescripcion().toUpperCase());
                admServicio.actualizarNovedad(novedadHelper.getNovedad(), novedadHelper.getListaNovedadDetalles(),
                        novedadHelper.getListaNovedadDetalleEliminados(), obtenerUsuarioConectado());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al guardar", e);
        }
        return iniciarNuevo();
    }

    /**
     * metodo para validar si ya existe el nemónico.
     *
     * @return haynemonico Boolean.
     */
    public Boolean validarNovedad() {
        Boolean validado = true;
        try {
            Nomina nomina = admServicio.listarPorNomina(novedadHelper.getNominaFormularioId());
            if (nomina.getBloqueado()) {
                validado = false;
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.procesos.novedades.nominaBloqueada",
                        FacesMessage.SEVERITY_ERROR);
            } else if (novedadHelper.getListaNovedadDetalles().isEmpty()) {
                validado = false;
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.procesos.novedades.empleado", FacesMessage.SEVERITY_ERROR);
            } else {
                DatoAdicional datoAdicional = admServicio.buscarDatoAdicional(
                        novedadHelper.getDatoAdicionalFormularioId());
                if (datoAdicional.getAceptaDuplicado() == null || !datoAdicional.getAceptaDuplicado()) {
                    InstitucionEjercicioFiscal ejercicioFiscal = nomina.getInstitucionEjercicioFiscal();
                    List<NovedadDetalle> detalles = novedadHelper.getListaNovedadDetalles();
                    List<NovedadDetalle> resultados = null;
                    for (NovedadDetalle detalle : detalles) {
                        resultados = novedadDetalleDao.buscarDuplicados(ejercicioFiscal.getId(), nomina.getId(),
                                datoAdicional.getId(), detalle.getServidor().getId(), detalle.getId());
                        if (!resultados.isEmpty()) {
                            mostrarMensajeEnPantalla("ec.gob.mrl.smp.procesos.novedades.valorRepetidoDetalle",
                                    FacesMessage.SEVERITY_ERROR, new String[]{detalle.getServidor().getNombres()});
                            resultados.clear();
                            validado = false;
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nemonico", ex);
        }
        return validado;
    }

    /**
     * metodo para cargar la nueva carga de novedades.
     *
     * @return
     */
    public String iniciarNuevo() {
        Boolean existeAcceso;
        existeAcceso = true;
        if (novedadHelper.getTienePermiso()) {
            existeAcceso = true;
        } else {
            mostrarMensajeEnPantalla("NO PUEDE REGISTRAR NOVEDADES POR QUE FINALIZO LA FECHA MÁXIMA",
                    FacesMessage.SEVERITY_ERROR);
        }
        if (existeAcceso) {
            novedadHelper.setNovedad(new Novedad());
            novedadHelper.setBusquedaNovedadVO(new BusquedaNovedadVO());
            novedadHelper.getBusquedaNovedadVO().setDatoAdicionalId(null);
            novedadHelper.getBusquedaNovedadVO().setPeriodoNomina(null);
            novedadHelper.getBusquedaNovedadVO().setTipoNominaId(null);
            novedadHelper.setTotalRegistros(0);
            novedadHelper.setTotalValor(BigDecimal.ZERO);
            novedadHelper.setDatoAdicionalFormularioId(null);
            novedadHelper.setPeriodoNominaFormularioId(null);
            novedadHelper.setNominaFormularioId(null);
            novedadHelper.setDescripcion(null);
            iniciarDatosEntidad(novedadHelper.getNovedad(), Boolean.TRUE);
            iniciarCombos(novedadHelper.getListaPeriodoNomina());
            iniciarCombos(novedadHelper.getListaNomina());
            iniciarCombos(novedadHelper.getListaDatoAdicionales());
            llenarComboPeriodoNomina();
            novedadHelper.setAceptaCambios(Boolean.TRUE);
            novedadHelper.setEsNuevo(Boolean.TRUE);
            novedadHelper.getListaNovedadDetalles().clear();
            novedadHelper.setNombreServidor("");
            return FORMULARIO_ENTIDAD;
        } else {
            return null;
        }
    }

    /**
     * METODO PARA LIMPIAR LOS DATOS.
     */
    public void limpiar() {
        llenarComboPeriodoNomina();

        novedadHelper.getListaNovedadDetalles().clear();
        novedadHelper.setNombreServidor(null);
        novedadHelper.getListaServidores().clear();
        novedadHelper.setPeriodoNominaId(null);
        novedadHelper.setNominaId(null);
        novedadHelper.setDescripcion(null);
        novedadHelper.setDatoAdicionalId(null);
        novedadHelper.setNombreArchivo("");
        novedadHelper.setBusquedaNovedadVO(new BusquedaNovedadVO());
        novedadHelper.getBusquedaNovedadVO().setDatoAdicionalId(null);
        novedadHelper.getBusquedaNovedadVO().setPeriodoNomina(null);
        novedadHelper.getBusquedaNovedadVO().setTipoNominaId(null);

        novedadHelper.getListaNomina().clear();
        novedadHelper.getListaNovedades().clear();
        novedadHelper.setNovedad(new Novedad());
    }

    /**
     * retornar a la lista.
     *
     * @return
     */
    public String regresarLista() {
        return LISTA_ENTIDAD;
    }

    public Boolean valorNull() {
        boolean existeNull = false;
        for (NovedadDetalle nd : novedadHelper.getListaNovedadDetalles()) {
            if (nd.getValor() == null) {
                existeNull = true;
                break;
            }
        }
        return existeNull;
    }

    /**
     * METODO PARA BORRAR LOS REGISTROS DE LAS LISTAS.
     *
     * @return
     */
    public String borrar() {
        try {
            if (!novedadHelper.getListaNovedadDetalles().isEmpty()) {
                Nomina nomina = nominaDao.buscarPorId(novedadHelper.getNominaFormularioId());
                if (nomina.getBloqueado()) {
                    mostrarMensajeEnPantalla("ec.gob.mrl.smp.procesos.novedades.nominaBloqueada",
                            FacesMessage.SEVERITY_ERROR);
                } else {
                    if (novedadHelper.getNovedadDetalleEliminar().getId() != null) {
                        novedadHelper.getListaNovedadDetalleEliminados().add(novedadHelper.getNovedadDetalleEliminar());
                    }
                    novedadHelper.getListaNovedadDetalles().remove(novedadHelper.getNovedadDetalleEliminar());
                    mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
                }
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    /**
     *
     */
    public void seleccionDatoAdicional() {
        try {
            if (novedadHelper.getDatoAdicionalFormularioId() != null) {
                DatoAdicional da = datoAdicionalDao.buscarPorId(novedadHelper.getDatoAdicionalFormularioId());
                novedadHelper.setDatoAdicionalSeleccionado(da);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de datos adicionales.
     */
    public void llenarComboDatosAdicionales() {
        try {
            iniciarCombos(novedadHelper.getListaDatoAdicionales());
            if (novedadHelper.getNominaId() != null) {
                Nomina nomina = admServicio.listarTodosNominasPorId(novedadHelper.getNominaId());
                novedadHelper.setEsNominaAbierta(nomina.getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo()));
                List<DatoAdicional> listaDatosAdicional = admServicio.listarTodosDatosAdicionalesPorTipoNomina(
                        nomina.getTipoNominaId());
                for (DatoAdicional g : listaDatosAdicional) {
                    if (novedadHelper.getEsRRHH()) {
                        novedadHelper.getListaDatoAdicionales().add(new SelectItem(g.getId(), g.getNombre()
                                + " - " + g.getUnidad()));
                    } else if (g.getDescentralizado()) {
                        novedadHelper.getListaDatoAdicionales().add(new SelectItem(g.getId(), g.getNombre()
                                + " - " + g.getUnidad()));
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(NovedadControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de datos adicionales.
     */
    public void llenarComboDatosAdicionalesFormulario() {
        try {
            iniciarCombos(novedadHelper.getListaDatoAdicionales());
            if (novedadHelper.getNominaFormularioId() != null) {
                Nomina nomina = admServicio.listarTodosNominasPorId(novedadHelper.getNominaFormularioId());
                novedadHelper.setEsNominaAbierta(nomina.getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo()));
                List<DatoAdicional> listaDatosAdicional = admServicio.listarTodosDatosAdicionalesPorTipoNomina(
                        nomina.getTipoNominaId());
                for (DatoAdicional g : listaDatosAdicional) {
                    if (novedadHelper.getEsRRHH()) {
                        novedadHelper.getListaDatoAdicionales().add(new SelectItem(g.getId(), g.getNombre()
                                + " - " + g.getUnidad()));
                    } else if (g.getDescentralizado()) {
                        novedadHelper.getListaDatoAdicionales().add(new SelectItem(g.getId(), g.getNombre()
                                + " - " + g.getUnidad()));
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(NovedadControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * metodo que copia las novedades realizadaas en periodos anteriores.
     *
     * @return null
     */
    public String copiaNovedad() {
        try {
            //novedadHelper.getListaNovedadDetalles().clear();
            Nomina nomina = admServicio.listarTodosNominasPorIdPeriodoYEstado(
                    novedadHelper.getPeriodoNominaId(), obtenerUsuarioConectado().
                    getInstitucionEjercicioFiscal().getId(), EstadoNominaEnum.ABIERTO.getCodigo()).get(0);
            if (novedadHelper.getPeriodoNominaCopiaId() != null
                    && nomina.getTipoNominaId() != null) {
                List<Nomina> listanomina = admServicio.listarTodosNominasPorIdPeriodoYEstado(
                        novedadHelper.getPeriodoNominaCopiaId(), obtenerUsuarioConectado().
                        getInstitucionEjercicioFiscal().getId(), EstadoNominaEnum.ABIERTO.getCodigo());
                if (!listanomina.isEmpty()) {
                    List<Novedad> novedades = admServicio.listarTodosNovedadesPorPeriodoIdYTipoNominaId(
                            novedadHelper.getPeriodoNominaCopiaId(), nomina.getTipoNominaId());
                    if (!novedades.isEmpty()) {
                        Novedad n = novedades.get(0);
                        if (n.getId() != null) {
                            List<NovedadDetalle> ndt = admServicio.listarTodosNovedadDetalleNovedadId(n.getId());
                            if (!ndt.isEmpty()) {
//                                NovedadDetalle nd = ndt.get(0);
                                for (NovedadDetalle nd : ndt) {
                                    Servidor servidor = admServicio.buscarServidorPorTipoDocumento(nd.getServidor().
                                            getNumeroIdentificacion());
                                    if (servidor != null && !validarRepetidos(servidor.getTipoIdentificacion(),
                                            servidor.getNumeroIdentificacion())) {
                                        novedadHelper.getListaNovedadDetalles().add(nd);
                                    }
                                }
                                calculoValores();
                            }
                        } else {
                            mostrarMensajeEnPantalla("no existen registros para este periodo",
                                    FacesMessage.SEVERITY_ERROR);
                        }
                    }

                } else {
                    mostrarMensajeEnPantalla("No tiene alguna nómina", FacesMessage.SEVERITY_ERROR);
                }
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de nominas.
     *
     */
    public void llenarComboNominas() {
        try {
            novedadHelper.getListaNomina().clear();
            iniciarCombos(novedadHelper.getListaNomina());
            novedadHelper.getListaDatoAdicionales().clear();
            iniciarCombos(novedadHelper.getListaDatoAdicionales());
            novedadHelper.setNominaId(null);
            novedadHelper.setDatoAdicionalId(null);
            if (novedadHelper.getPeriodoNominaId() != null) {
                //PeriodoNomina periodoNomina = admServicio.buscarPeriodoNominaPorId(novedadHelper.getPeriodoNominaId());
                List<Nomina> listaNomina = admServicio.listarTodosNominasPorIdPeriodoYVigentes(novedadHelper.
                        getPeriodoNominaId(), obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
                for (Nomina g : listaNomina) {
                    if (g.getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo())) {
                        novedadHelper.getListaNomina().add(new SelectItem(g.getId(),
                                UtilCadena.concatenar(g.getTipoNomina().getNombre(), " / ", g.getDescripcion())));
                    }
                }
            } else {
                mostrarMensajeEnPantalla("Debe seleccionar un periodo", FacesMessage.SEVERITY_INFO);
            }

        } catch (ServicioException ex) {
            Logger.getLogger(NovedadControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de nominas.
     *
     */
    public void llenarComboNominasFormulario() {
        try {
            novedadHelper.getListaNomina().clear();
            iniciarCombos(novedadHelper.getListaNomina());
            novedadHelper.getListaDatoAdicionales().clear();
            iniciarCombos(novedadHelper.getListaDatoAdicionales());

            novedadHelper.setNominaFormularioId(null);
            novedadHelper.setDatoAdicionalFormularioId(null);
            if (novedadHelper.getPeriodoNominaFormularioId() != null) {
                List<Nomina> listaNomina = admServicio.listarTodosNominasPorIdPeriodoYVigentes(novedadHelper.
                        getPeriodoNominaFormularioId(), obtenerUsuarioConectado().
                        getInstitucionEjercicioFiscal().getId());
                for (Nomina g : listaNomina) {
                    if (g.getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo()) && !g.getBloqueado()) {
                        novedadHelper.getListaNomina().add(new SelectItem(g.getId(),
                                UtilCadena.concatenar(g.getTipoNomina().getNombre(), " / ", g.getDescripcion())));
                    }
                }
            } else {
                mostrarMensajeEnPantalla("Debe seleccionar un periodo", FacesMessage.SEVERITY_INFO);
            }

        } catch (ServicioException ex) {
            Logger.getLogger(NovedadControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de periodo nomina
     *
     */
    public void llenarComboPeriodoNomina() {
        try {
            novedadHelper.getListaPeriodoNomina().clear();
            iniciarCombos(novedadHelper.getListaPeriodoNomina());
            List<PeriodoNomina> listaPeriodoNomina = admServicio.buscarPeriodoNominaPorEjercicio();
            for (PeriodoNomina g : listaPeriodoNomina) {
                novedadHelper.getListaPeriodoNomina().add(new SelectItem(g.getId(), g.getNombre()));
            }
            iniciarCombos(novedadHelper.getListaNomina());
            iniciarCombos(novedadHelper.getListaDatoAdicionales());
            novedadHelper.setPeriodoNominaFormularioId(null);
            novedadHelper.setNominaFormularioId(null);
            novedadHelper.setDatoAdicionalFormularioId(null);
        } catch (ServicioException ex) {
            Logger.getLogger(NovedadControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    /**
//     * Permite eliminar un detalle de la Novedade de la Nomina.
//     *
//     * @return
//     */
//    public String borrarNovedadDetalle() {
//        try {
//            if (novedadHelper.getNovedadNominaVO().getNovedadDetalle() != null) {
//                System.out.println(" eliminado... " + novedadHelper.getNovedadNominaVO().getNovedadDetalle().getValor()
//                        + " ... " + novedadHelper.getNovedadNominaVO().getNovedadDetalle().getNovedad().getDatoAdicional().getNombre());
//                novedadHelper.getNovedadNominaVO().getListaNovedadesDetalles().remove(novedadHelper.getNovedadNominaVO().getNovedadDetalle());
//                iniciarDatosEntidad(novedadHelper.getNovedadNominaVO().getNovedadDetalle(), Boolean.FALSE);
//                administracionServicio.eliminarNovedadDetalle(novedadHelper.getNovedadNominaVO().getNovedadDetalle());
//                buscarNovedadesDetalles(false);
//                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
//            }
//        } catch (ServicioException ex) {
//            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
//            error(getClass().getName(), "Error al procesar la eliminacion de detalles de la nd", ex);
//        }
//
//        return FORMULARIO_NOVEDADES_NOMINA;
//    }

    /**
     * buscar las novedades.
     *
     * @return
     */
    public String buscarNovedades() {
        try {
            novedadHelper.getListaNovedades().clear();
            novedadHelper.getBusquedaNovedadVO().setPeriodoFiscal(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            novedadHelper.getBusquedaNovedadVO().setDatoAdicionalId(novedadHelper.getDatoAdicionalId());
            novedadHelper.getBusquedaNovedadVO().setPeriodoNomina(novedadHelper.getPeriodoNominaId());
            novedadHelper.getBusquedaNovedadVO().setTipoNominaId(novedadHelper.getNominaId());
            novedadHelper.getBusquedaNovedadVO().setNumeroDocumento(novedadHelper.getNumeroIdentificacion());
            novedadHelper.getBusquedaNovedadVO().setNombres(novedadHelper.getNombres());
            novedadHelper.getBusquedaNovedadVO().setTipoDocumento(novedadHelper.getTipoIdentificacion());
//            if (!novedadHelper.getEsRRHH()) {
//                UnidadOrganizacional uo = unidadOrganizacionalDao.buscarAgrupador(
//                        obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
//                if (uo == null) {
//                    mostrarMensajeEnPantalla("No se encuentra definido la unidad organizacional agrupador.",
//                            FacesMessage.SEVERITY_ERROR);
//                } else {
//                    novedadHelper.getBusquedaNovedadVO().setUnidadOrganizacional(uo);
//
//                }
//            }
            if (validarRequerido()) {
                novedadHelper.setListaNovedades(novedadServicio.buscar(novedadHelper.getBusquedaNovedadVO(), true));
                for (Novedad novedad : novedadHelper.getListaNovedades()) {
                    novedad.setNombreUnidadOrganizacional(nombreUnidadOrganizacional(novedad.getUnidadOrganizacional()));
                }
            }

        } catch (ServicioException ex) {
            ex.printStackTrace();
            Logger.getLogger(NovedadControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public boolean validarRequerido() {
        boolean validar = true;
        if (novedadHelper.getBusquedaNovedadVO().getPeriodoNomina() != null
                && novedadHelper.getBusquedaNovedadVO().getTipoNominaId() != null) {
        } else {
            validar = false;
            mostrarMensajeEnPantalla("El periodo nómina y la nómina son campos requeridos.", FacesMessage.SEVERITY_ERROR);
        }

        return validar;
    }

    /**
     * Permite eliminar una Novedade de la Nomina.
     *
     * @return
     */
    public String borrarNovedad() {
        try {
            if (novedadHelper.getNovedadEditar() != null) {
                Nomina nomina = nominaDao.buscarPorId(novedadHelper.getNovedadEditar().getNomina().getId());
                if (nomina.getBloqueado()) {
                    mostrarMensajeEnPantalla("No se permite eliminar debido a que la nómina se encuentra bloqueada.",
                            FacesMessage.SEVERITY_ERROR);
                } else {
                    novedadHelper.getListaNovedades().remove(novedadHelper.getNovedadEditar());
                    iniciarDatosEntidad(novedadHelper.getNovedadEditar(), Boolean.FALSE);
                    admServicio.eliminarNovedad(novedadHelper.getNovedadEditar());
                    novedadHelper.setNovedadEditar(null);
                    mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
                }
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion de la novedad", ex);
        }

        return LISTA_ENTIDAD;
    }

    /**
     * Este metodo transacciona el formateo de una fecha especifica.
     *
     * @param fecha Date
     * @return String
     */
    public final String obtenerFechaFormateada(final Date fecha) {
        if (fecha != null) {
            return UtilFechas.formatearLargo(fecha);
        } else {
            return "";
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de documento parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDocumentoEnum.obtenerNombre(codigo);
    }

    /**
     * Permite buscar los detalles de las novedades de una nomina.
     *
     * @param alCargarPopUp
     * @return
     */
    public String buscarNovedadesDetalles(boolean alCargarPopUp) {
        if (alCargarPopUp) {
            novedadHelper.setAceptaCambios(false);
            novedadHelper.getListaNovedadDetallesEditables().clear();
        }
        novedadHelper.setEsNuevo(Boolean.FALSE);
        novedadHelper.setTotalValor(BigDecimal.ZERO);
        novedadHelper.setTotalValorDescontado(BigDecimal.ZERO);
        if (!novedadHelper.getListaNovedades().isEmpty()
                && novedadHelper.getNovedad() != null) {
            try {
                novedadHelper.setPeriodoNominaId(novedadHelper.getNovedad().getNomina().getPeriodoNomina().getId());
                novedadHelper.setNominaId(novedadHelper.getNovedad().getNomina().getId());
                novedadHelper.setDatoAdicionalId(novedadHelper.getNovedad().getDatoAdicionalId());
                novedadHelper.setListaNovedadDetalles(
                        admServicio.listarTodosNovedadDetalleNovedadId(novedadHelper.getNovedad().getId()));
                calculoValores();
            } catch (ServicioException ex) {
                mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
                error(getClass().getName(), "Error al procesar la busqueda DE Novedades Detalles", ex);
            }
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     *
     * @return
     */
    public String iniciarEdicion() {
        try {
            Object novedadEditar = BeanUtils.cloneBean(novedadHelper.getNovedadEditar());
            Novedad a = (Novedad) novedadEditar;
            iniciarDatosEntidad(a, Boolean.FALSE);
            novedadHelper.setNovedad(a);
            novedadHelper.setPeriodoNominaFormularioId(a.getNomina().getPeriodoNomina().getId());
            novedadHelper.setNominaFormularioId(a.getNomina().getId());
            novedadHelper.setDatoAdicionalFormularioId(a.getDatoAdicionalId());
            novedadHelper.setAceptaCambios(Boolean.FALSE);
            novedadHelper.setDescripcion(a.getDescripcion());
            novedadHelper.setUnidadOrganizacional(a.getUnidadOrganizacional());
            if (!novedadHelper.getListaNovedades().isEmpty() && novedadHelper.getNovedad() != null) {
                novedadHelper.setListaNovedadDetalles(admServicio.listarTodosNovedadDetalleNovedadId(a.getId()));
                calculoValores();
            }
            novedadHelper.setEsNominaAbierta(a.getNomina().getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo()));
            novedadHelper.setEsNuevo(Boolean.FALSE);
            seleccionDatoAdicional();
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * metodo para calcular los valores.
     *
     * @return
     */
    public String calculoValores() {
        try {

            novedadHelper.setTotalValor(BigDecimal.ZERO);
            novedadHelper.setTotalValorDescontado(BigDecimal.ZERO);
            for (NovedadDetalle det : novedadHelper.getListaNovedadDetalles()) {
                BigDecimal valor = det.getValor();
                if (!novedadHelper.getListaNovedadDetallesEditables().isEmpty() && det.getId() != null) {
                    det.setValor(obtenerValorEditado(det.getId()));
                }
                if (det.getValor() == null) {
                    det.setValor(det.getValor());
                }
                novedadHelper.setTotalValor(novedadHelper.getTotalValor().add(
                        det.getValor()));
            }
            novedadHelper.setTotalRegistros(
                    novedadHelper.getListaNovedadDetalles().size());
        } catch (Exception e) {
            Logger.getLogger(NovedadControlador.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    /**
     * Permite obtener el nuevo valor de un registro editado.
     *
     * @param idDetalle
     * @return
     */
    private BigDecimal obtenerValorEditado(Long idDetalle) {
        for (NovedadDetalle det : novedadHelper.getListaNovedadDetallesEditables()) {
            if (det.getId().equals(idDetalle)) {
                return det.getValor();
            }
        }
        return null;
    }

    /**
     * Agrega a la lista de valores editados para su registro respectivo.
     *
     * @param tipo
     */
    public void esValorDetalleEditado(NovedadDetalle tipo) {
        try {
            if (tipo.getValor() != null) {
                novedadHelper.getListaNovedadDetallesEditables().add(tipo);
            } else {
                mostrarMensajeEnPantalla("El valor es un campo numérico requerido", FacesMessage.SEVERITY_ERROR);
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla("Tipo de Dato no corresponde!", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    /**
     *
     * @return
     */
    public Boolean puedeAcceder() {
        Boolean resultado = false;
        if (novedadHelper.getEsNominaAbierta()) {
            if (novedadHelper.getEsRRHH()) {
                resultado = true;
            } else if (novedadHelper.getTienePermiso()) {
                resultado = true;
            }

        }
        return resultado;
    }

    /**
     *
     * @param nd
     */
    public void validarValor(final NovedadDetalle nd) {
        if (novedadHelper.getDatoAdicionalSeleccionado().getTipoDato().equals(TipoDatoEnum.NUMERICO.getCodigo())) {
            if (novedadHelper.getDatoAdicionalSeleccionado().getValorMinimoNumerico() != null
                    && novedadHelper.getDatoAdicionalSeleccionado().getValorMaximoNumerico() != null) {
                if (!UtilNumeros.between(nd.getValor(), novedadHelper.getDatoAdicionalSeleccionado().
                        getValorMinimoNumerico(), novedadHelper.getDatoAdicionalSeleccionado().
                        getValorMaximoNumerico())) {
                    mostrarMensajeEnPantalla(UtilCadena.concatenar("El valor ingresado debe estar entre ",
                            novedadHelper.getDatoAdicionalSeleccionado().getValorMinimoNumerico(), " y ",
                            novedadHelper.getDatoAdicionalSeleccionado().getValorMaximoNumerico()),
                            FacesMessage.SEVERITY_ERROR);
                    nd.setValor(null);
                }
            } else if (novedadHelper.getDatoAdicionalSeleccionado().getValorMinimoNumerico() != null
                    && novedadHelper.getDatoAdicionalSeleccionado().getValorMaximoNumerico() == null) {
                if (nd.getValor().compareTo(novedadHelper.getDatoAdicionalSeleccionado().getValorMinimoNumerico())
                        < 0) {
                    mostrarMensajeEnPantalla(UtilCadena.concatenar("El valor ingresado no puede ser menor a ",
                            novedadHelper.getDatoAdicionalSeleccionado().getValorMinimoNumerico()),
                            FacesMessage.SEVERITY_ERROR);
                    nd.setValor(null);
                }
            } else if (novedadHelper.getDatoAdicionalSeleccionado().getValorMinimoNumerico() == null
                    && novedadHelper.getDatoAdicionalSeleccionado().getValorMaximoNumerico() != null) {
                if (nd.getValor().compareTo(novedadHelper.getDatoAdicionalSeleccionado().getValorMaximoNumerico())
                        > 0) {
                    mostrarMensajeEnPantalla(UtilCadena.concatenar("El valor ingresado no puede ser mayor a ",
                            novedadHelper.getDatoAdicionalSeleccionado().getValorMinimoNumerico()),
                            FacesMessage.SEVERITY_ERROR);
                    nd.setValor(null);
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public Boolean puedeModificar() {
        Boolean resultado = false;
        if (novedadHelper.getEsNominaAbierta() != null && novedadHelper.getEsNominaAbierta()) {
            if (novedadHelper.getEsRRHH()) {
                resultado = true;
            } else if (novedadHelper.getTienePermiso()) {
                resultado = true;
            }
        }
        return resultado;
    }

    /**
     *
     * @return
     */
    public String imprimir() {
        setReporte(ReportesEnum.PROTEUS_REPORTE_NOVEDADES.getReporte());
        Long novedadId = novedadHelper.getNovedad().getId();
        if (novedadId != null) {
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("p_titulo", "REPORTE NOVEDADES");
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_novedad", novedadId.toString());
            generarUrlDeReporte();
        } else {
            mostrarMensajeEnPantalla("No se puede generar el reporte sin guardar la Novedad",
                    FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }

    /**
     * @return the novedadHelper
     */
    public NovedadHelper getNovedadHelper() {
        return novedadHelper;
    }

    /**
     * @param novedadHelper the novedadHelper to set
     */
    public void setNovedadHelper(final NovedadHelper novedadHelper) {
        this.novedadHelper = novedadHelper;
    }

    /**
     *
     * @return
     */
    public StreamedContent getFormatoNovedades() {
        InputStream streamOperativo = ((ServletContext) FacesContext.getCurrentInstance().
                getExternalContext().getContext()).getResourceAsStream("/pdf/Formato_archivos_novedades.pdf");
        return new DefaultStreamedContent(streamOperativo, "application/pdf", "Formato_archivos_novedades.pdf");
    }
}
