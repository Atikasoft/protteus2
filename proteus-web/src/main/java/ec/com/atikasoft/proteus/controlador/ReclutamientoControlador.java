/*
 *  ServidorControlador.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  10/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import au.com.bytecode.opencsv.CSVReader;
import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.EstadoPuestoHelper;
import ec.com.atikasoft.proteus.controlador.helper.ReclutamientoHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.ReclutamientoDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.enums.EstadoReclutamientoEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.SiNoEnum;
import ec.com.atikasoft.proteus.enums.TipoCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoIdentificacionEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Catalogo;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.EscalaOcupacional;
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.NivelOcupacional;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.Reclutamiento;
import ec.com.atikasoft.proteus.modelo.ReclutamientoCapacitacion;
import ec.com.atikasoft.proteus.modelo.ReclutamientoInstruccion;
import ec.com.atikasoft.proteus.modelo.ReclutamientoTrayectoriaLaboral;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.ReclutamientoServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.PersonaVO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.FastArrayList;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * ServidorControlador
 *
 * @author Alvaro Tituaña <alvaro.tituania@markasoft.ec>
 */
@ManagedBean(name = "reclutamientoBean")
@ViewScoped
public class ReclutamientoControlador extends BaseControlador {

    /**
     * Servicio de distributivo.
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(ReclutamientoControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/reclutamiento/reclutamiento.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/reclutamiento/lista_reclutamiento.jsf";

    /**
     * Regla de navegación.
     */
    public static final String PANTALLA_INICIAL = "/pages/index.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private ReclutamientoServicio reclutamientoServicio;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     *
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     *
     */
    @EJB
    private ReclutamientoDao reclutamientoDao;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{reclutamientoHelper}")
    private ReclutamientoHelper reclutamientoHelper;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{estadoPuestoHelper}")
    private EstadoPuestoHelper estadoPuestoHelper;

    /**
     * Constructor por defecto.
     */
    public ReclutamientoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        reclutamientoHelper.setNombreServidor("");
        reclutamientoHelper.setNumeroIdentificacion("");
        reclutamientoHelper.setCodigoPuesto(null);
        reclutamientoHelper.getListaReclutamientos().clear();
        if (reclutamientoHelper.getRegistroManualNombres() == null) {
            reclutamientoHelper.setRegistroManualNombres(Boolean.FALSE);
        }
//        iniciarComboTipo();
//        llenarComboRegimenLaboral();
//        llenarComboEscala();

    }

    /**
     * actualiza la paginación.
     */
    public void refreshPagination() {
        ((DataTable) FacesContext.getCurrentInstance().
                getViewRoot().
                findComponent("frmpuestos").
                findComponent("unidadAdministrativa")).setFirst(0);
    }

    /**
     *
     */
    public void buscarPuestoIndividual() {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            reclutamientoHelper.getBusquedaPuestoVO().setIntitucionEjercicioFiscalId(
                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
//            reclutamientoHelper.setEstadoPuesto(administracionServicio.buscarEstadoPuestoPredeterminado());
//            reclutamientoHelper.getBusquedaPuestoVO().setEstadoPuestoId(reclutamientoHelper.getEstadoPuesto().getId());
            List<DistributivoDetalle> puestos = distributivoPuestoServicio.buscar(reclutamientoHelper.
                    getBusquedaPuestoVO(), false, obtenerUsuarioConectado(), esRRHH(pi.getValorTexto()));
            if (puestos.isEmpty()) {
                mostrarMensajeEnPantalla("Código del puesto " + reclutamientoHelper.getBusquedaPuestoVO().
                        getCodigoPuesto() + " no existe en el distributivo", FacesMessage.SEVERITY_ERROR);
            } else {
                DistributivoDetalle puesto = puestos.get(0);
                if (puesto.getEstadoPuesto().getPuedeOcuparse()) {
                    // validar que no este siendo usado por un reclutamiente en curso
                    Reclutamiento anterior = reclutamientoServicio.buscarPorDistributivoYEstado(puesto.getId(),
                            EstadoReclutamientoEnum.REGISTRADO.getCodigo());
                    if (anterior == null) {
                        reclutamientoHelper.setDistributivoDetalle(puesto);
                    } else {
                        mostrarMensajeEnPantalla("Código del puesto " + reclutamientoHelper.getBusquedaPuestoVO().
                                getCodigoPuesto() + " se encuentra usado en el reclutamiento " + anterior.getId(),
                                FacesMessage.SEVERITY_ERROR);
                    }
                } else {
                    mostrarMensajeEnPantalla("El estado del puesto con código " + reclutamientoHelper.
                            getBusquedaPuestoVO().getCodigoPuesto() + " no se encuentra vacante",
                            FacesMessage.SEVERITY_ERROR);
                }
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error en la procesar la busqueda reclutamiento trayectoria", e);
        }

    }

    /**
     * metodo para buscar las listas trayectoria.
     *
     * @return
     */
    public String buscarPuestos() {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());

            reclutamientoHelper.getListaDistributivoDetalles().clear();
            reclutamientoHelper.getBusquedaPuestoVO().setIntitucionEjercicioFiscalId(
                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            reclutamientoHelper.getBusquedaPuestoVO().setUnidadAdministrativaId(reclutamientoHelper.
                    getUnidadAdministrativaId());
            reclutamientoHelper.getBusquedaPuestoVO().setUnidadAdministrativaNombre(reclutamientoHelper.
                    getUnidadAdministrativaNombre());
            reclutamientoHelper.getBusquedaPuestoVO().setModalidadLaboralId(reclutamientoHelper.getModalidadLaboralId());
            reclutamientoHelper.setEstadoPuesto(administracionServicio.buscarEstadoPuestoPredeterminado());
            reclutamientoHelper.getBusquedaPuestoVO().setEstadoPuestoId(reclutamientoHelper.getEstadoPuesto().getId());
            if (reclutamientoHelper.getBusquedaPuestoVO().getUnidadAdministrativaId() != null) {
                reclutamientoHelper.setListaDistributivoDetalles(distributivoPuestoServicio.buscar(
                        reclutamientoHelper.getBusquedaPuestoVO(), false, obtenerUsuarioConectado(),
                        esRRHH(pi.getValorTexto())));
                refreshPagination();
                ejecutarComandoPrimefaces("puestosDialog.show()");
            } else {
                mostrarMensajeEnPantalla("El parametros de la unidad organizacional es requerido",
                        FacesMessage.SEVERITY_ERROR);
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error en la procesar la busqueda reclutamiento trayectoria", ex);
        }
        return null;
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboTipo() {
        reclutamientoHelper.getTipoDocumento().clear();
        iniciarCombos(reclutamientoHelper.getTipoDocumento());
        reclutamientoHelper.getTipoDocumento().add(new SelectItem(TipoDocumentoEnum.CEDULA.getNemonico(),
                TipoDocumentoEnum.CEDULA.getNombre()));
        reclutamientoHelper.getTipoDocumento().add(new SelectItem(TipoDocumentoEnum.PASAPORTE.getNemonico(),
                TipoDocumentoEnum.PASAPORTE.getNombre()));

    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de tipo genero desde la tabla catalogos.
     *
     * @param paraReclutamientoMasivo
     */
    public void llenarComboTipoGenero(boolean paraReclutamientoMasivo) {
        try {
            reclutamientoHelper.getListaGenero().clear();
            iniciarCombos(reclutamientoHelper.getListaGenero());
            List<Catalogo> listaCatalogo = administracionServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.GENERO.getCodigo());
            for (Catalogo g : listaCatalogo) {
                reclutamientoHelper.getListaGenero().add(new SelectItem(g.getId(), g.getNombre(), g.getCodigo()));
            }
            if (paraReclutamientoMasivo) {
                reclutamientoHelper.getMapaListasCatalogos().put(
                        TipoCatalogoEnum.GENERO.getCodigo(), reclutamientoHelper.getListaGenero());
            }

        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de nacionalidad la tabla catalogos.
     *
     * @param paraReclutamientoMasivo
     */
    public void llenarComboNacionalidad(boolean paraReclutamientoMasivo) {
        try {
            reclutamientoHelper.getListaNacionalidad().clear();
            iniciarCombos(reclutamientoHelper.getListaNacionalidad());
            List<Catalogo> listaCatalogo = administracionServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.NACIONALIDAD.getCodigo());
            for (Catalogo g : listaCatalogo) {
                reclutamientoHelper.getListaNacionalidad().add(new SelectItem(g.getId(), g.getNombre(), g.getCodigo()));
            }
            if (paraReclutamientoMasivo) {
                reclutamientoHelper.getMapaListasCatalogos().put(
                        TipoCatalogoEnum.NACIONALIDAD.getCodigo(), reclutamientoHelper.getListaNacionalidad());
            }

        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * llenar combo de niveles de instruccion.
     */
    private void iniciarComboNivelInstruccion(boolean paraReclutamientoMasivo) {
        try {
            reclutamientoHelper.getListaNivelInstruccion().clear();
            iniciarCombos(reclutamientoHelper.getListaNivelInstruccion());
            List<Catalogo> listaCatalogo = administracionServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.NIVEL_INSTRUCCION.getCodigo());
            for (Catalogo g : listaCatalogo) {
                reclutamientoHelper.getListaNivelInstruccion().add(new SelectItem(g.getId(), g.getNombre(), g.getCodigo()));
            }
            if (paraReclutamientoMasivo) {
                reclutamientoHelper.getMapaListasCatalogos().put(
                        TipoCatalogoEnum.NIVEL_INSTRUCCION.getCodigo(), reclutamientoHelper.getListaNivelInstruccion());
            }

        } catch (Exception ex) {
            Logger.getLogger(ReclutamientoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * metodo para buscar un servidor segun su Id.
     *
     * @return
     */
    public String buscarPorCedula() {
        try {
            reclutamientoHelper.setReclutamiento(new Reclutamiento());
            if (reclutamientoHelper.getNumeroIdentificacion() != null) {
                if (reclutamientoHelper.getTipoIdentificacion().equals(
                        TipoDocumentoEnum.CEDULA.getNemonico())
                        || reclutamientoHelper.getTipoIdentificacion().
                        equals(TipoDocumentoEnum.PASAPORTE.getNemonico())) {
                    reclutamientoHelper.setReclutamiento(
                            reclutamientoServicio.buscarServidorPorDocumento(reclutamientoHelper.
                                    getNumeroIdentificacion()));

                    if (reclutamientoHelper.getReclutamiento() == null) {
                        reclutamientoHelper.setReclutamiento(new Reclutamiento());
                        ponerMensajeError(NADA, "Servidor no registrado");
                    }
                }
            } else {
                ponerMensajeError(NADA, "No a ingresado el numero de identificación");
            }

        } catch (Exception ex) {
            Logger.getLogger(ReclutamientoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String regresarLista() {
        reclutamientoHelper.setNombreServidor("");
        reclutamientoHelper.setNumeroIdentificacion("");
        reclutamientoHelper.getListaReclutamientos().clear();
        return LISTA_ENTIDAD;
    }

    public String buscar() {
        return PANTALLA_INICIAL;
    }

    public String iniciarNuevo() {
        try {
            reclutamientoHelper.setReclutamiento(new Reclutamiento());
            reclutamientoHelper.setUnidadAdministrativaNombre(null);
            reclutamientoHelper.setModalidadLaboralId(null);
            reclutamientoHelper.setTipoIdentificacion(null);
            reclutamientoHelper.setRegimenLaboralId(null);
            reclutamientoHelper.setNivelOcupacionalId(null);
            reclutamientoHelper.setEscalaOcupacionalId(null);
            reclutamientoHelper.setPresentarArea(Boolean.FALSE);
            reclutamientoHelper.getListaReclutamientoInstruccion().clear();
            reclutamientoHelper.getReclutamiento().setUnidadOrganizacionalId(null);
            reclutamientoHelper.setDistributivoDetalle(new DistributivoDetalle());
            reclutamientoHelper.getBusquedaPuestoVO().setCodigoPuesto(null);
            iniciarComboTipo();
            llenarComboTipoGenero(false);
            llenarComboNacionalidad(false);
            cargarUnidadOrganizacional();
            llenarComboModalidadLaboral();
            reclutamientoHelper.setReclutamientoInstruccion(new ReclutamientoInstruccion());
            iniciarDatosEntidad(reclutamientoHelper.getReclutamiento(), Boolean.TRUE);
            reclutamientoHelper.setEsNuevo(Boolean.TRUE);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar el nuevo", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * iniciar edicion del reclutamiento.
     *
     * @return
     */
    public String iniciarEdicion() {
        try {
            Object reclutamiento = BeanUtils.cloneBean(reclutamientoHelper.getReclutamientoEditDelete());
            Reclutamiento a = (Reclutamiento) reclutamiento;
            iniciarDatosEntidad(a, Boolean.FALSE);
            reclutamientoHelper.setPresentarArea(Boolean.TRUE);
            reclutamientoHelper.setReclutamiento(a);
            reclutamientoHelper.setUnidadAdministrativaId(reclutamientoHelper.getReclutamiento().
                    getUnidadOrganizacional().getId());
            reclutamientoHelper.setUnidadAdministrativaNombre(reclutamientoHelper.getReclutamiento().
                    getUnidadOrganizacional().getRuta());
            reclutamientoHelper.setModalidadLaboralId(reclutamientoHelper.getReclutamiento().
                    getModalidadLaboral().getId());
            reclutamientoHelper.setTipoIdentificacion(reclutamientoHelper.getReclutamiento().
                    getTipoIdentificacion());
            reclutamientoHelper.setRegimenLaboralId(reclutamientoHelper.getReclutamiento().
                    getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getId());
            reclutamientoHelper.setNivelOcupacionalId(reclutamientoHelper.getReclutamiento().
                    getEscalaOcupacional().getNivelOcupacional().getId());
            reclutamientoHelper.setEscalaOcupacionalId(reclutamientoHelper.getReclutamiento().
                    getEscalaOcupacional().getId());
            reclutamientoHelper.setDistributivoDetalle(a.getDistributivoDetalle());
            iniciarComboTipo();
            llenarComboTipoGenero(false);
            llenarComboNacionalidad(false);
            cargarUnidadOrganizacional();
            llenarComboModalidadLaboral();
            buscarListas();
            buscarListasCapacitacion();
            buscarListasTrayectoria();
            reclutamientoHelper.setEsNuevo(Boolean.FALSE);
            // verificar si identificacion existe en personas
            PersonaVO personaVO = servidorServicio.buscarPersona(a.getTipoIdentificacion(), a.getNumeroIdentificacion(),
                    obtenerUsuarioConectado());
            if (personaVO == null) {
                reclutamientoHelper.setRegistroManualNombres(Boolean.TRUE);
            } else {
                reclutamientoHelper.setRegistroManualNombres(Boolean.FALSE);
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Poblar unidades organizacionales.
     *
     * @return
     */
    public String cargarUnidadOrganizacional() {
        try {
            List<UnidadOrganizacional> unidades = administracionServicio.listarUnidadOrganizacionalVigente();
            reclutamientoHelper.setListaUnidadesOrganizacionales(unidades);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar unidad organizacional", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipo(final String codigo) {
        return TipoIdentificacionEnum.obtenerDescripcion(codigo);
    }

    /**
     * metodo que busca el servidor por ej nombre
     *
     * @return
     */
    public String buscarServidorPorNombre() {
        try {
            reclutamientoHelper.getListaReclutamientos().clear();
            if (reclutamientoHelper.getCodigoPuesto() == null
                    && reclutamientoHelper.getNombreServidor().isEmpty()
                    && reclutamientoHelper.getNumeroIdentificacion().isEmpty()) {
                mostrarMensajeEnPantalla("Ingrese al menos una letra para realizar la busqueda",
                        FacesMessage.SEVERITY_INFO);
            } else if (reclutamientoHelper.getCodigoPuesto() == null) {
                if (!reclutamientoHelper.getNombreServidor().isEmpty() && !reclutamientoHelper.getNumeroIdentificacion().isEmpty()) {
                    reclutamientoHelper.setListaReclutamientos(reclutamientoServicio.
                            buscarServidorPorNombreYIdentificacion(
                                    reclutamientoHelper.getNombreServidor().toUpperCase(),
                                    reclutamientoHelper.getNumeroIdentificacion()));
                }
                if (!reclutamientoHelper.getNombreServidor().isEmpty()
                        && reclutamientoHelper.getNumeroIdentificacion().isEmpty()) {
                    if (reclutamientoHelper.getNombreServidor().length() < 3) {
                        mostrarMensajeEnPantalla("Ingrese al menos tres letras para realizar la busqueda",
                                FacesMessage.SEVERITY_INFO);
                    } else {
                        reclutamientoHelper.setListaReclutamientos(reclutamientoServicio.buscarServidorPorNombre(
                                reclutamientoHelper.getNombreServidor().toUpperCase()));
                    }
                }
                if (!reclutamientoHelper.getNumeroIdentificacion().isEmpty()
                        && reclutamientoHelper.getNombreServidor().isEmpty()) {
                    reclutamientoHelper.getListaReclutamientos().clear();
                    Reclutamiento r = reclutamientoServicio.buscarServidorPorDocumento(
                            reclutamientoHelper.getNumeroIdentificacion());
                    if (r != null) {
                        reclutamientoHelper.getListaReclutamientos().add(r);
                    } else {
                        mostrarMensajeEnPantalla("Servidor no registrado", FacesMessage.SEVERITY_INFO);
                    }
                }
            } else {
                reclutamientoHelper.getListaReclutamientos().addAll(
                        reclutamientoDao.buscarPorCodigoPuesto(reclutamientoHelper.getCodigoPuesto()));
                if (reclutamientoHelper.getListaReclutamientos().isEmpty()) {
                    mostrarMensajeEnPantalla("Código de puesto no registrado", FacesMessage.SEVERITY_INFO);
                }
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error en la procesar la busqueda", ex);
        }
        return null;
    }

    /**
     * Recupera los datos del servidor en el sistema.
     */
    public void buscarServidor() {
        try {
            reclutamientoHelper.setRegistroManualNombres(Boolean.FALSE);
            if (reclutamientoHelper.getReclutamiento().getTipoIdentificacion() != null
                    && reclutamientoHelper.getReclutamiento().getNumeroIdentificacion() != null) {
                // recupera datos del sistema de personas
                PersonaVO vo = servidorServicio.buscarPersona(reclutamientoHelper.getReclutamiento().getTipoIdentificacion(),
                        reclutamientoHelper.getReclutamiento().getNumeroIdentificacion(),
                        obtenerUsuarioConectado());
                if (vo == null) {
                    // No existe persona
                    // verificar si es cedula
                    if (reclutamientoHelper.getReclutamiento().getTipoIdentificacion().equals(
                            TipoIdentificacionEnum.CEDULA.getCodigo())) {
                        // errror, no existe un ciudadano con ese numero de cedula
                        reclutamientoHelper.getReclutamiento().setNumeroIdentificacion(null);
                        mostrarMensajeEnPantalla("El número de identificación ingresada no existe.", FacesMessage.SEVERITY_ERROR);
                    } else {
                        // error, no existe pasaporte, se registra manualmente
                        reclutamientoHelper.getReclutamiento().setApellidosNombres(null);
                        reclutamientoHelper.getReclutamiento().setApellidosPaterno(null);
                        reclutamientoHelper.getReclutamiento().setNombres(null);
                        recuperarDatosIngresadosAnteriormente();
                        reclutamientoHelper.setRegistroManualNombres(Boolean.TRUE);
                        mostrarMensajeEnPantalla("El número de pasaporte ingresado no existe en el Sistema de "
                                + " Personas, proceda a ingresar el Apellido y Nombre"
                                + " asegurándose que sean correctos.", FacesMessage.SEVERITY_WARN);

                    }
                } else {
                    // Si existe persona
                    if ((vo.getApellidos() + " " + vo.getNombres()).equals(vo.getApellidosNombres())) {
                        reclutamientoHelper.getReclutamiento().setApellidosNombres(vo.getApellidosNombres());
                        reclutamientoHelper.getReclutamiento().setApellidosPaterno(vo.getApellidos());
                        reclutamientoHelper.getReclutamiento().setNombres(vo.getNombres());
                    } else {
                        reclutamientoHelper.getReclutamiento().setApellidosNombres(vo.getApellidosNombres());
                        reclutamientoHelper.getReclutamiento().setApellidosPaterno(null);
                        reclutamientoHelper.getReclutamiento().setNombres(null);
                        reclutamientoHelper.setRegistroManualNombres(Boolean.TRUE);
                    }
                    recuperarDatosIngresadosAnteriormente();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(ReclutamientoControlador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     *
     */
    private void recuperarDatosIngresadosAnteriormente() throws DaoException {
        Servidor s = servidorDao.buscar(reclutamientoHelper.getReclutamiento().getTipoIdentificacion(),
                reclutamientoHelper.getReclutamiento().getNumeroIdentificacion());
        if (s == null) {
            reclutamientoHelper.getReclutamiento().setCatalogoGenero(null);
            reclutamientoHelper.getReclutamiento().setCatalogoGeneroId(null);
            reclutamientoHelper.getReclutamiento().setCatalogoNacionalidad(null);
            reclutamientoHelper.getReclutamiento().setCatalogoNacionalidadId(null);
            reclutamientoHelper.getReclutamiento().setTelefono(null);
            reclutamientoHelper.getReclutamiento().setCelular(null);
            reclutamientoHelper.getReclutamiento().setMail(null);
            reclutamientoHelper.getReclutamiento().setJornada(null);
            reclutamientoHelper.getReclutamiento().setHoraEntrada(null);
            reclutamientoHelper.getReclutamiento().setCallePrincipal(null);
            reclutamientoHelper.getReclutamiento().setCalleSecundaria(null);
            reclutamientoHelper.getReclutamiento().setReferenciaDomicilio(null);
            reclutamientoHelper.getReclutamiento().setNumeroDomicilio(null);
        } else {
            if (s.getCatalogoGenero() != null) {
                reclutamientoHelper.getReclutamiento().setCatalogoGenero(s.getCatalogoGenero());
                reclutamientoHelper.getReclutamiento().setCatalogoGeneroId(s.getCatalogoGenero().getId());
            }
            if (s.getCatalogoNacionalidad() != null) {
                reclutamientoHelper.getReclutamiento().setCatalogoNacionalidad(s.getCatalogoNacionalidad());
                reclutamientoHelper.getReclutamiento().setCatalogoNacionalidadId(
                        s.getCatalogoNacionalidad().getId());
            }
            reclutamientoHelper.getReclutamiento().setApellidosNombres(s.getApellidosNombres());
            reclutamientoHelper.getReclutamiento().setApellidosPaterno(s.getApellidos());
            reclutamientoHelper.getReclutamiento().setNombres(s.getNombres());
            reclutamientoHelper.getReclutamiento().setTelefono(s.getTelefono());
            reclutamientoHelper.getReclutamiento().setCelular(s.getCelular());
            reclutamientoHelper.getReclutamiento().setMail(s.getMail());
            reclutamientoHelper.getReclutamiento().setJornada(s.getJornada());
            reclutamientoHelper.getReclutamiento().setHoraEntrada(s.getHoraEntrada());
            reclutamientoHelper.getReclutamiento().setCallePrincipal(s.getBarrioDomicilio());
            reclutamientoHelper.getReclutamiento().setCalleSecundaria(s.getCalleDomicilio());
            reclutamientoHelper.getReclutamiento().setReferenciaDomicilio(s.getReferenciaDomicilio());
            reclutamientoHelper.getReclutamiento().setNumeroDomicilio(s.getNumeroDomicilio());
        }

    }

    /**
     * metodo para carlos todos los regimenes laborable.
     */
    public void llenarComboModalidadLaboral() {
        try {
            reclutamientoHelper.getListaModalidadLaboral().clear();
            iniciarCombos(reclutamientoHelper.getListaModalidadLaboral());
            List<ModalidadLaboral> listaModalidadLaboral = reclutamientoServicio.listarTodosModalidadLaboral();
            for (ModalidadLaboral g : listaModalidadLaboral) {
                reclutamientoHelper.getListaModalidadLaboral().add(new SelectItem(g.getId(), g.getNombre()));
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * metodo para carlos todos los regimenes laborable.
     */
    public void llenarComboRegimenLaboral() {
        try {
            reclutamientoHelper.getListaRegimenLaboral().clear();
            reclutamientoHelper.getListaEscalaOcupacional().clear();
            iniciarCombos(reclutamientoHelper.getListaRegimenLaboral());
            List<RegimenLaboral> listaRegimenLaboral
                    = reclutamientoServicio.listarTodosRegimenesLaborales(reclutamientoHelper.getDistributivoDetalle().
                            getEscalaOcupacional().
                            getNivelOcupacional().getRegimenLaboral().getId());
            for (RegimenLaboral g : listaRegimenLaboral) {
                reclutamientoHelper.getListaRegimenLaboral().add(new SelectItem(g.getId(), g.getNombre()));
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * llenar combo niveles ocupacionales segun id regimen.
     */
    public void llenarComboNivelOcupacional() {
        try {
            reclutamientoHelper.getListaNivelOcupacional().clear();
            iniciarCombos(reclutamientoHelper.getListaNivelOcupacional());
            reclutamientoHelper.setRegimenLaboralId(reclutamientoHelper.getDistributivoDetalle().getEscalaOcupacional().
                    getNivelOcupacional().getRegimenLaboral().getId());
            List<NivelOcupacional> listaNivelOcupacional
                    = reclutamientoServicio.listarTodosNivelesOcupacional(reclutamientoHelper.getRegimenLaboralId());
            for (NivelOcupacional g : listaNivelOcupacional) {
                reclutamientoHelper.getListaNivelOcupacional().add(new SelectItem(g.getId(), g.getNombre()));
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * llenar combo niveles ocupacionales segun id regimen.
     */
    public void llenarComboEscala() {
        try {
            reclutamientoHelper.getListaEscalaOcupacional().clear();
            iniciarCombos(reclutamientoHelper.getListaEscalaOcupacional());
            List<EscalaOcupacional> listaEscala
                    = reclutamientoServicio.listarTodosEscalaOcupacional(reclutamientoHelper.getNivelOcupacionalId());
            for (EscalaOcupacional g : listaEscala) {
                reclutamientoHelper.getListaEscalaOcupacional().add(new SelectItem(g.getId(), UtilCadena.concatenar(g.
                        getNombre(), " - ", g.getGrado(), " - ", g.getRmu())));
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * metodo de guardar.
     *
     * @return
     */
    public String guardar() {
        try {
            if (reclutamientoHelper.getEsNuevo()) {
                if (validarNemonico() == 1) {
                    mostrarMensajeEnPantalla("El puesto actual esta siendo utilizado", FacesMessage.SEVERITY_ERROR);
                } else if (validarNemonico() == 2) {
                    mostrarMensajeEnPantalla("El número de identificación ya se encuentra registrado",
                            FacesMessage.SEVERITY_ERROR);
                } else {
                    if (reclutamientoHelper.getRegistroManualNombres()) {
                        reclutamientoHelper.getReclutamiento().setApellidosNombres(
                                reclutamientoHelper.getReclutamiento().getApellidosPaterno() + " "
                                + reclutamientoHelper.getReclutamiento().getNombres());

                    }
                    if (reclutamientoHelper.getReclutamiento().getApellidosNombres() != null
                            && reclutamientoHelper.getReclutamiento().getApellidosNombres().equals(
                                    (reclutamientoHelper.getReclutamiento().getApellidosPaterno() + " "
                                    + reclutamientoHelper.getReclutamiento().getNombres()))) {

                        reclutamientoHelper.getReclutamiento().setModalidadLaboralId(reclutamientoHelper.
                                getDistributivoDetalle().getDistributivo().getModalidadLaboral().getId());
                        reclutamientoHelper.getReclutamiento().setUnidadOrganizacionalId(reclutamientoHelper.
                                getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
                        reclutamientoHelper.getReclutamiento().setEscalaOcupacionalId(reclutamientoHelper.
                                getDistributivoDetalle().getEscalaOcupacional().getId());
                        reclutamientoHelper.getReclutamiento().setInstitucion(obtenerUsuarioConectado().getInstitucion());
                        reclutamientoHelper.getReclutamiento().setInstitucionId(obtenerUsuarioConectado().getInstitucion().
                                getId());
                        reclutamientoHelper.getReclutamiento().setEstado(EstadoReclutamientoEnum.REGISTRADO.getCodigo());
                        reclutamientoHelper.getReclutamiento().setDistributivoDetalle(reclutamientoHelper.
                                getDistributivoDetalle());
                        reclutamientoServicio.guardarReclutamiento(reclutamientoHelper.getReclutamiento());

                        reclutamientoHelper.setPresentarArea(Boolean.TRUE);
                        reclutamientoHelper.setReclutamientoInstruccion(new ReclutamientoInstruccion());
                        mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                        reclutamientoHelper.setReclutamientoEditDelete(reclutamientoDao.buscarPorId(
                                reclutamientoHelper.getReclutamiento().getId()));
                        iniciarEdicion();
                    } else {
                        if (reclutamientoHelper.getReclutamiento().getApellidosNombres() != null) {
                            mostrarMensajeEnPantalla("El apellido y nombres es diferente a "
                                    + reclutamientoHelper.getReclutamiento().getApellidosNombres(),
                                    FacesMessage.SEVERITY_WARN);
                        }
                    }
                }
            } else {
                reclutamientoHelper.getReclutamiento().setModalidadLaboralId(
                        reclutamientoHelper.getModalidadLaboralId());
                reclutamientoHelper.getReclutamiento().setUnidadOrganizacionalId(reclutamientoHelper.
                        getUnidadAdministrativaId());
//                reclutamientoHelper.getReclutamiento().setEscalaOcupacionalId(reclutamientoHelper.
//                        getDistributivoDetalle().getEscalaOcupacional().getId());
                reclutamientoServicio.actualizarReclutamiento(reclutamientoHelper.getReclutamiento());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe Reclutamiento para ese puesto. 1. NO debe haber mas de un registro en estado
     * registrado para un mismo puesto. 2.-NO debe haber un mismo número de identidad en estado registrado para mas de
     * un puesto. respuesta: 0=> no existe codigo, 1=> existe el codigo en el distributivo, 2 ==> ya esta reclutado
     *
     * @return haynemonico Boolean.
     */
    public int validarNemonico() {
        int tipo = 0;
        try {
            if (reclutamientoHelper.getDistributivoDetalle() != null) {
                Reclutamiento reclutamiento = reclutamientoServicio.buscarPorDistributivoYEstado(
                        reclutamientoHelper.getDistributivoDetalle().getId(),
                        EstadoReclutamientoEnum.REGISTRADO.getCodigo());

                if (reclutamiento != null) {
                    tipo = 1;
                }
            }
            if (tipo == 0 && reclutamientoHelper.getReclutamiento() != null) {
                Reclutamiento reclutamiento = reclutamientoServicio.buscarPorIdentificacionYEstado(
                        reclutamientoHelper.getReclutamiento().getTipoIdentificacion(),
                        reclutamientoHelper.getReclutamiento().getNumeroIdentificacion(),
                        EstadoReclutamientoEnum.REGISTRADO.getCodigo());

                if (reclutamiento != null) {
                    tipo = 2;
                }
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error en la procesar la validacion del nemonico", ex);
        }
        return tipo;
    }

    /**
     * metodo para buscar las listas.
     *
     * @return
     */
    public String buscarListas() {
        try {
            reclutamientoHelper.getListaReclutamientoInstruccion().clear();
            reclutamientoHelper.setListaReclutamientoInstruccion(
                    reclutamientoServicio.listarTodosReclutamientoInstruccionPorIdReclutado(
                            reclutamientoHelper.getReclutamiento().getId()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error en la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * método borrar.
     *
     * @return String
     */
    public String borrar() {
        String[] borrar = {"", ""};
        try {
//            Boolean constraint = Boolean.FALSE;
//            if (reclutamientoServicio.tieneRestricciones1(
//                    "reclutamiento", "ReclutamientoInstruccion",
//                    reclutamientoHelper.getReclutamientoEditDelete().getId())) {
//                constraint = Boolean.TRUE;
//                borrar[0] = " reclutamiento ";
//            }
//            if (reclutamientoServicio.tieneRestricciones1(
//                    "reclutamiento", "ReclutamientoCapacitacion",
//                    reclutamientoHelper.getReclutamientoEditDelete().getId())) {
//                constraint = Boolean.TRUE;
//                borrar[1] = " reclutamiento y reclutamiento-capacitacion";
//            }
//            if (constraint) {
//                mostrarMensajeEnPantalla("ec.gob.mq.prometeo.administracion.reclutamiento.constraint",
//                        FacesMessage.SEVERITY_ERROR, borrar[1]);
//            } else {
//                reclutamientoServicio.eliminarReclutamiento(reclutamientoHelper.getReclutamientoEditDelete());
//                reclutamientoHelper.getListaReclutamientos().
//                        remove(reclutamientoHelper.getReclutamientoEditDelete());
//                //buscarListas();
//                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
//            }
            reclutamientoServicio.eliminarReclutamiento(reclutamientoHelper.getReclutamientoEditDelete());
            reclutamientoHelper.getListaReclutamientos().
                    remove(reclutamientoHelper.getReclutamientoEditDelete());
            //buscarListas();
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el reclutamiento eliminar", e);
        }
        return null;
    }

    /**
     * metodo de guardar.
     *
     * @return
     */
    public String guardarReclutamientoInstruccion() {
        try {
            if (reclutamientoHelper.getEsNuevoDialog() && reclutamientoHelper.getReclutamiento().getId() != null) {
                iniciarDatosEntidad(reclutamientoHelper.getReclutamientoInstruccion(), Boolean.TRUE);
                reclutamientoHelper.getReclutamientoInstruccion().setReclutamiento(
                        reclutamientoHelper.getReclutamiento());
                reclutamientoHelper.getReclutamientoInstruccion().setReclutamientoId(reclutamientoHelper.
                        getReclutamiento().getId());
                reclutamientoServicio.guardarReclutamientoInstruccion(
                        reclutamientoHelper.getReclutamientoInstruccion());
                buscarListas();
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            } else {
                reclutamientoServicio.actualizarReclutamientoInstruccion(
                        reclutamientoHelper.getReclutamientoInstruccion());
                buscarListas();
                mostrarMensajeEnPantalla(REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el reclutamiento instrucción", e);
        }
        return null;
    }

    /**
     * eliminar de las listas.
     *
     * @return
     */
    public String borrarInstruccion() {
        try {
            reclutamientoServicio.eliminarReclutamientoInstruccion(reclutamientoHelper.
                    getReclutamientoInstruccionEditDelete());
            reclutamientoHelper.getListaReclutamientoInstruccion().
                    remove(reclutamientoHelper.getReclutamientoInstruccionEditDelete());
            //buscarListas();
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error en la procesar la eliminacion", ex);
        }
        return null;
    }

    /**
     * metodo para limpiar los dartos del dialog.
     */
    public void limpiarDatosDialog() {
        /**
         * DATOS DE INSTRUCCION.
         */
        reclutamientoHelper.getReclutamientoInstruccion().setEspecializacion("");
        reclutamientoHelper.getListaNivelInstruccion().clear();
        iniciarCombos(reclutamientoHelper.getListaNivelInstruccion());
        reclutamientoHelper.getReclutamientoInstruccion().setNombreInstitucion("");
        reclutamientoHelper.getReclutamientoInstruccion().setTituloObtenido("");
    }

    /**
     * presentar dialog y validar nuevo.
     *
     * @return
     */
    public String presentarDialog() {
        try {
            reclutamientoHelper.setReclutamientoInstruccion(new ReclutamientoInstruccion());
            reclutamientoHelper.setEsNuevoDialog(Boolean.TRUE);
            iniciarComboNivelInstruccion(false);
            ejecutarComandoPrimefaces("reclutamientoInstruccion.show()");
        } catch (Exception e) {
            error(getClass().getName(), "Error al presentar dialogo instrucción", e);
        }
        return null;
    }

    /**
     * metodo para editar las listas de los tabs.
     *
     * @return
     */
    public String iniciarEdicionListas() {
        try {

            Object instruccion = BeanUtils.cloneBean(reclutamientoHelper.getReclutamientoInstruccionEditDelete());
            ReclutamientoInstruccion a = (ReclutamientoInstruccion) instruccion;
            iniciarDatosEntidad(a, Boolean.FALSE);
            reclutamientoHelper.setReclutamientoInstruccion(a);
            iniciarComboNivelInstruccion(false);
            reclutamientoHelper.setEsNuevoDialog(Boolean.FALSE);
            ejecutarComandoPrimefaces("reclutamientoInstruccion.show()");

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return null;
    }

    /**
     * ********************** RECLUTAMIENTO CAPACITACIÓN*****************
     */
    /**
     * presentar dialog y validar nuevo.
     *
     * @return
     */
    public String presentarDialogCapacitacion() {
        try {
            reclutamientoHelper.setReclutamientoCapacitacion(new ReclutamientoCapacitacion());
            reclutamientoHelper.setEsNuevoDialog(Boolean.TRUE);
            ejecutarComandoPrimefaces("reclutamientoCapacitacion.show()");
        } catch (Exception e) {
            error(getClass().getName(), "Error al presentar dialogo instrucción", e);
        }
        return null;
    }

    /**
     * metodo para buscar las listas capacitación.
     *
     * @return
     */
    public String buscarListasCapacitacion() {
        try {
            reclutamientoHelper.getListaReclutamientoCapacitacion().clear();
            reclutamientoHelper.setListaReclutamientoCapacitacion(
                    reclutamientoServicio.listarTodosReclutamientoCapacitacionPorIdReclutado(
                            reclutamientoHelper.getReclutamiento().getId()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error en la procesar la busqueda reclutamiento capacitacion", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para editar las listas de los tabs.
     *
     * @return
     */
    public String iniciarEdicionListasCapacitacion() {
        try {

            Object capacitacion = BeanUtils.cloneBean(reclutamientoHelper.getReclutamientoCapacitacionEditDelete());
            ReclutamientoCapacitacion a = (ReclutamientoCapacitacion) capacitacion;
            iniciarDatosEntidad(a, Boolean.FALSE);
            reclutamientoHelper.setReclutamientoCapacitacion(a);
            reclutamientoHelper.setEsNuevoDialog(Boolean.FALSE);
            ejecutarComandoPrimefaces("reclutamientoCapacitacion.show()");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return null;
    }

    /**
     * metodo de guardar.
     *
     * @return
     */
    public String guardarReclutamientoCapacitacion() {
        try {
            if (reclutamientoHelper.getEsNuevoDialog() && reclutamientoHelper.getReclutamiento().getId() != null) {
                iniciarDatosEntidad(reclutamientoHelper.getReclutamientoCapacitacion(), Boolean.TRUE);
                reclutamientoHelper.getReclutamientoCapacitacion().setReclutamiento(
                        reclutamientoHelper.getReclutamiento());
                reclutamientoHelper.getReclutamientoCapacitacion().setReclutamientoId(reclutamientoHelper.
                        getReclutamiento().getId());
                reclutamientoServicio.guardarReclutamientoCapacitacion(
                        reclutamientoHelper.getReclutamientoCapacitacion());
                buscarListasCapacitacion();
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            } else {
                reclutamientoServicio.actualizarReclutamientoCapacitacion(reclutamientoHelper.
                        getReclutamientoCapacitacion());
                buscarListasCapacitacion();
                mostrarMensajeEnPantalla(REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el reclutamiento instrucción", e);
        }
        return null;
    }

    /**
     * eliminar de las listas.
     *
     * @return
     */
    public String borrarCapacitacion() {
        try {

            reclutamientoServicio.eliminarReclutamientoCapacitacion(reclutamientoHelper.
                    getReclutamientoCapacitacionEditDelete());
            reclutamientoHelper.getListaReclutamientoCapacitacion().
                    remove(reclutamientoHelper.getReclutamientoCapacitacionEditDelete());
            //buscarListasCapacitacion();
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error en la procesar la eliminacion", ex);
        }
        return null;
    }

    /**
     * ******************* RECLUTAMIENTO TRAYECTORIA LABORAL***************
     */
    /**
     * presentar dialog y validar nuevo.
     *
     * @return
     */
    public String presentarDialogTrayectoria() {
        try {
            reclutamientoHelper.setReclutamientoTrayectoriaLaboral(new ReclutamientoTrayectoriaLaboral());
            reclutamientoHelper.setEsNuevoDialog(Boolean.TRUE);
            ejecutarComandoPrimefaces("reclutamientoTrayectoria.show()");
        } catch (Exception e) {
            error(getClass().getName(), "Error al presentar dialogo instrucción", e);
        }
        return null;
    }

    /**
     * metodo para buscar las listas trayectoria.
     *
     * @return
     */
    public String buscarListasTrayectoria() {
        try {
            reclutamientoHelper.getListaReclutamientoTrayectoriaLaborales().clear();
            reclutamientoHelper.setListaReclutamientoTrayectoriaLaborales(
                    reclutamientoServicio.listarTodosReclutamientoTrayectoriaPorIdReclutado(
                            reclutamientoHelper.getReclutamiento().getId()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error en la procesar la busqueda reclutamiento trayectoria", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para editar las listas de los tabs.
     *
     * @return
     */
    public String iniciarEdicionListasTrayectoria() {
        try {

            Object capacitacion
                    = BeanUtils.cloneBean(reclutamientoHelper.getReclutamientoTrayectoriaLaboralEditDelete());
            ReclutamientoTrayectoriaLaboral a = (ReclutamientoTrayectoriaLaboral) capacitacion;
            iniciarDatosEntidad(a, Boolean.FALSE);
            reclutamientoHelper.setReclutamientoTrayectoriaLaboral(a);
            reclutamientoHelper.setEsNuevoDialog(Boolean.FALSE);
            ejecutarComandoPrimefaces("reclutamientoTrayectoria.show()");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return null;
    }

    /**
     * metodo de guardar.
     *
     * @return
     */
    public String guardarReclutamientoTrayectoria() {
        try {
            Boolean fechaComparar = UtilFechas.validarFechaInicioFin(reclutamientoHelper.
                    getReclutamientoTrayectoriaLaboral().getFechaInicio(),
                    reclutamientoHelper.getReclutamientoTrayectoriaLaboral().getFechaFin());
            if (fechaComparar) {
                if (reclutamientoHelper.getEsNuevoDialog() && reclutamientoHelper.getReclutamiento().getId() != null) {
                    iniciarDatosEntidad(reclutamientoHelper.getReclutamientoTrayectoriaLaboral(), Boolean.TRUE);
                    reclutamientoHelper.getReclutamientoTrayectoriaLaboral().setReclutamiento(reclutamientoHelper.
                            getReclutamiento());
                    reclutamientoHelper.getReclutamientoTrayectoriaLaboral().setReclutamientoId(reclutamientoHelper.
                            getReclutamiento().getId());
                    reclutamientoServicio.guardarReclutamientoTrayectoria(reclutamientoHelper.
                            getReclutamientoTrayectoriaLaboral());
                    buscarListasTrayectoria();
                    // reclutamientoHelper.setReclutamientoTrayectoriaLaboral(new ReclutamientoTrayectoriaLaboral());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                } else {
                    reclutamientoServicio.actualizarReclutamientoTrayectoria(reclutamientoHelper.
                            getReclutamientoTrayectoriaLaboral());
                    buscarListasTrayectoria();
                    mostrarMensajeEnPantalla(REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                mostrarMensajeEnPantalla("la fecha inicio no puede ser mayor a la fecha fin",
                        FacesMessage.SEVERITY_ERROR);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el reclutamiento instrucción", e);
        }
        return null;
    }

    /**
     * Obtiene el numero de dias.
     */
    public void calcularNumeroDias() {
        Integer[] mesesAnios;
        if (reclutamientoHelper.getReclutamientoTrayectoriaLaboral().getFechaInicio() != null
                && reclutamientoHelper.getReclutamientoTrayectoriaLaboral().getFechaFin() != null) {
            mesesAnios = UtilFechas.calcularAniosMesesDiasEntreFechas(
                    reclutamientoHelper.getReclutamientoTrayectoriaLaboral().getFechaInicio(),
                    reclutamientoHelper.getReclutamientoTrayectoriaLaboral().getFechaFin());
            reclutamientoHelper.getReclutamientoTrayectoriaLaboral().setMesTrayectoria(mesesAnios[1].longValue());
            reclutamientoHelper.getReclutamientoTrayectoriaLaboral().setAnios(mesesAnios[0].longValue());
        }
    }

    /**
     * eliminar de las listas.
     *
     * @return
     */
    public String borrarTrayectoria() {
        try {
            reclutamientoServicio.eliminarReclutamientoTrayectoria(reclutamientoHelper.
                    getReclutamientoTrayectoriaLaboralEditDelete());
            reclutamientoHelper.getListaReclutamientoTrayectoriaLaborales().
                    remove(reclutamientoHelper.getReclutamientoTrayectoriaLaboralEditDelete());
            // buscarListasTrayectoria();
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error en la procesar la eliminacion", ex);
        }
        return null;
    }

    /**
     *
     */
    public void cancelarReclutamientoMasivo() {
        reclutamientoHelper.setArchivoRC(null);
        reclutamientoHelper.setArchivoRDG(null);
        reclutamientoHelper.setArchivoRI(null);
        reclutamientoHelper.setArchivoRTL(null);
        ejecutarComandoPrimefaces("dlgReclutamientoMasivo.hide()");
    }

    /**
     *
     */
    public void inciarReclutamientoMasivo() {
        llenarComboTipoGenero(true);
        llenarComboNacionalidad(true);
        iniciarComboNivelInstruccion(true);
        configurarValidacionArchivoRDG();
        configurarValidacionArchivoRC();
        configurarValidacionArchivoRI();
        configurarValidacionArchivoRTL();
        ejecutarComandoPrimefaces("dlgReclutamientoMasivo.show();");
    }

    /**
     *
     */
    private void configurarValidacionArchivoRDG() {
        reclutamientoHelper.setCamposArchivoRDG(new LinkedHashMap<String, Map<String, String>>());

        reclutamientoHelper.getCamposArchivoRDG().put("Código Puesto",
                llenarMapaValConf("String,Integer", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put(
                "Tipo Identificación",
                llenarMapaValConf("String", TipoDocumentoEnum.CEDULA.getNemonico()
                        + "," + TipoDocumentoEnum.PASAPORTE.getNemonico(), "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Número Identificación",
                llenarMapaValConf("String,Integer", "", "true"));

        reclutamientoHelper.getCamposArchivoRDG().put("Apellido Paterno", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Apellido Materno", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Nombres", llenarMapaValConf("String", "", "true"));

        reclutamientoHelper.getCamposArchivoRDG().put("Género",
                llenarMapaValConf("String", ":" + TipoCatalogoEnum.GENERO.getCodigo(), "true"));

        reclutamientoHelper.getCamposArchivoRDG().put("Nacionalidad",
                llenarMapaValConf("String", ":" + TipoCatalogoEnum.NACIONALIDAD.getCodigo(), "true"));

        reclutamientoHelper.getCamposArchivoRDG().put("Teléfono", llenarMapaValConf("String,Integer,Long", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Celular", llenarMapaValConf("String,Integer,Long", "", "true"));

        reclutamientoHelper.getCamposArchivoRDG().put("Mail", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Hora Ingreso", llenarMapaValConf("HR12", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Duración Jornada", llenarMapaValConf("Integer", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Calle Principal", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Calle Secundaria", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Referencia Domicilio", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Número Domicilio", llenarMapaValConf("String", "", "true"));

        reclutamientoHelper.getCamposArchivoRDG().put("Persona con Discapacidad",
                llenarMapaValConf("String", SiNoEnum.SI.getCodigo() + "," + SiNoEnum.NO.getCodigo(), "true"));

        reclutamientoHelper.getCamposArchivoRDG().put("Número Carnet CONADIS",
                llenarMapaValConf("String", "", "false"));
        reclutamientoHelper.getCamposArchivoRDG().put("Criterio Técnico", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Recomendaciones", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Elaborado Por", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Cargo Elaborado por", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Aprobado Por", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRDG().put("Cargo Aprobado Por", llenarMapaValConf("String", "", "true"));

    }

    /**
     *
     */
    private void configurarValidacionArchivoRC() {
        reclutamientoHelper.setCamposArchivoRC(new LinkedHashMap<String, Map<String, String>>());

        reclutamientoHelper.getCamposArchivoRC().put(
                "Tipo Identificación",
                llenarMapaValConf("String", TipoDocumentoEnum.CEDULA.getNemonico()
                        + "," + TipoDocumentoEnum.PASAPORTE.getNemonico(), "true"));

        reclutamientoHelper.getCamposArchivoRC().put("Número Identificación",
                llenarMapaValConf("String,Integer", "", "true"));
        reclutamientoHelper.getCamposArchivoRC().put("Nombre Evento", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRC().put("Nombre Institución", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRC().put("Tipo Diploma", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRC().put("Duración", llenarMapaValConf("Integer", "", "true"));

    }

    /**
     *
     */
    private void configurarValidacionArchivoRI() {
        reclutamientoHelper.setCamposArchivoRI(new LinkedHashMap<String, Map<String, String>>());

        reclutamientoHelper.getCamposArchivoRI().put(
                "Tipo Identificación",
                llenarMapaValConf("String", TipoDocumentoEnum.CEDULA.getNemonico()
                        + "," + TipoDocumentoEnum.PASAPORTE.getNemonico(), "true"));

        reclutamientoHelper.getCamposArchivoRI().put("Número Identificación",
                llenarMapaValConf("String,Integer", "", "true"));
        reclutamientoHelper.getCamposArchivoRI().put("Nivel Instrucción",
                llenarMapaValConf("String,Integer", ":" + TipoCatalogoEnum.NIVEL_INSTRUCCION.getCodigo(), "true"));
        reclutamientoHelper.getCamposArchivoRI().put("Nombre Institución", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRI().put("Especialización", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRI().put("Título Obtenido", llenarMapaValConf("String", "", "true"));

    }

    /**
     *
     */
    private void configurarValidacionArchivoRTL() {
        reclutamientoHelper.setCamposArchivoRTL(new LinkedHashMap<String, Map<String, String>>());

        reclutamientoHelper.getCamposArchivoRTL().put(
                "Tipo Identificación",
                llenarMapaValConf("String", TipoDocumentoEnum.CEDULA.getNemonico()
                        + "," + TipoDocumentoEnum.PASAPORTE.getNemonico(), "true"));
        reclutamientoHelper.getCamposArchivoRTL().put("Número Identificación",
                llenarMapaValConf("String,Integer", "", "true"));
        reclutamientoHelper.getCamposArchivoRTL().put("Fecha Inicio", llenarMapaValConf("Date", "", "true"));
        reclutamientoHelper.getCamposArchivoRTL().put("Fecha Fín", llenarMapaValConf("Date", "", "true"));
        reclutamientoHelper.getCamposArchivoRTL().put("Mes Trayectoria", llenarMapaValConf("Integer", "", "true"));
        reclutamientoHelper.getCamposArchivoRTL().put("Empresa", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRTL().put("Denominación Puesto", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRTL().put("Responsabilidades", llenarMapaValConf("String", "", "true"));
        reclutamientoHelper.getCamposArchivoRTL().put("Razón de salida", llenarMapaValConf("String", "", "true"));

    }

    /**
     *
     * @param tipo
     * @param dominio
     * @param esRequerido
     * @return
     */
    private Map<String, String> llenarMapaValConf(String tipo, String dominio, String esRequerido) {
        Map<String, String> config = new LinkedHashMap<>();
        config.put("tipo", tipo);
        config.put("dominio", dominio);
        config.put("requerido", esRequerido);

        return config;
    }

    /**
     *
     * @param event
     */
    public void cargarArchivo(final FileUploadEvent event) {
        try {
            boolean error = false;
            UploadedFile archivo = event.getFile();
            if (event.getComponent().getAttributes().get("archivoId").toString().equals("RDG")) {
                error |= validarArchivoCSV("RDG", archivo, reclutamientoHelper.getCamposArchivoRDG());
                if (error) {
                    reclutamientoHelper.setArchivoRDG(null);
                } else {
                    reclutamientoHelper.setArchivoRDG(archivo);
                }

            } else if (event.getComponent().getAttributes().get("archivoId").toString().equals("RC")) {
                error |= validarArchivoCSV("RC", archivo, reclutamientoHelper.getCamposArchivoRC());
                if (error) {
                    reclutamientoHelper.setArchivoRC(null);
                } else {
                    reclutamientoHelper.setArchivoRC(archivo);
                }

            } else if (event.getComponent().getAttributes().get("archivoId").toString().equals("RI")) {
                error |= validarArchivoCSV("RI", archivo, reclutamientoHelper.getCamposArchivoRI());
                if (error) {
                    reclutamientoHelper.setArchivoRI(null);
                } else {
                    reclutamientoHelper.setArchivoRI(archivo);
                }

            } else {
                error |= validarArchivoCSV("RTL", archivo, reclutamientoHelper.getCamposArchivoRTL());
                if (error) {
                    reclutamientoHelper.setArchivoRTL(null);
                } else {
                    reclutamientoHelper.setArchivoRTL(archivo);
                }
            }

            if (!error) {
                mostrarMensajeEnPantalla("Archivo cargado satisfactoriamente.", FacesMessage.SEVERITY_INFO);
            }

        } catch (Exception e) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     *
     * @param archivoSubido
     * @param valConfig
     * @return
     */
    private boolean validarArchivoCSV(
            String identificadorArchivo,
            UploadedFile archivoSubido,
            Map<String, Map<String, String>> valConfig) {

        List<String[]> lineas = leerArchivoSubido(archivoSubido);
        Set<String> setClaves = valConfig.keySet();
        String[] claves = setClaves.toArray(new String[setClaves.size()]);

        String[] codigosPuestosSeleccionados = new String[lineas.size()];

        int i = 1;
        boolean error = false;
        String formatoCedula = "\\d{10}";

        for (String[] l : lineas) {
            String tipoDocId = identificadorArchivo.equals("RDG") ? l[1] : l[0];
            int posTipoDocId = identificadorArchivo.equals("RDG") ? 1 : 0;

            int j = 0;
            for (String valorCampo : l) {
                boolean err = false;
                Map<String, String> campoConf = valConfig.get(claves[j]);
                if (Boolean.valueOf(campoConf.get("requerido"))
                        && (valorCampo == null || valorCampo.trim().isEmpty())) {
                    errorCampoObligatorio(i, claves[j]);
                    err = true;

                } else if (!campoConf.get("dominio").trim().isEmpty()) {
                    err = errorValidandoDominio(valorCampo, campoConf.get("dominio"));
                    if (err) {
                        mostrarMensajeEnPantalla("Error en la línea " + i + ". El valor del campo \""
                                + claves[j] + "\" no existe en la lista de catálogos definidos. ",
                                FacesMessage.SEVERITY_ERROR);
                    }

                } else {
                    boolean errorNroIdentificacion = false;
                    if (identificadorArchivo.equals("RDG") && j == 0) {
                        boolean encontrado = false;

                        //Verificando que el codigo del puesto no aparezca repetido dentro del documento subido
                        for (String cps : codigosPuestosSeleccionados) {
                            if (cps != null && cps.equals(valorCampo)) {
                                err = true;
                                encontrado = true;
                                mostrarMensajeEnPantalla("Error en la línea " + i + ". El valor del campo \""
                                        + claves[j]
                                        + "\" ya está asociado a otro reclutamiento dentro de este archivo. ",
                                        FacesMessage.SEVERITY_ERROR);
                                break;
                            }
                        }
                        try {
                            if (!encontrado) {
                                //verifcar si el puesto esta vacante, caso positivo se puede asignar, caso contrario no
                                DistributivoDetalle dd = distributivoPuestoServicio.buscarPorCodigoPuesto(
                                        Long.valueOf(valorCampo),
                                        obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId()).get(0);
                                if (distributivoPuestoServicio.buscarVacantesPorCodigo(
                                        Long.valueOf(valorCampo)).isEmpty()
                                        || reclutamientoServicio.buscarPorDistributivoYEstado(
                                                dd.getId(), EstadoReclutamientoEnum.REGISTRADO.getCodigo()) != null) {
                                    mostrarMensajeEnPantalla("Error en la línea " + i + ". El valor del campo \""
                                            + claves[j] + "\" ya está registrado en la base de datos. ",
                                            FacesMessage.SEVERITY_ERROR);
                                    err = true;

                                } else {
                                    codigosPuestosSeleccionados[i - 1] = valorCampo;
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (tipoDocId != null && (j == posTipoDocId + 1)) {
                        if (tipoDocId.equals("C")) {
                            if (!valorCampo.matches(formatoCedula)) {
                                err = true;
                                errorNroIdentificacion = true;
                                mostrarMensajeEnPantalla("Error en la línea " + i + ". El valor del campo \""
                                        + claves[j]
                                        + "\" no cumple con el formato establecido para este tipo de documento.",
                                        FacesMessage.SEVERITY_ERROR);
                            }

                        } else {
                            try { //verificando que el numero de identificacion no este asignado ya a un puesto o reclutamiento
                                if (reclutamientoServicio.buscarPorIdentificacionYEstado(
                                        tipoDocId, valorCampo, EstadoReclutamientoEnum.REGISTRADO.getCodigo()) != null
                                        || distributivoPuestoServicio.buscarDistributivoPorServidor(
                                                tipoDocId, valorCampo,
                                                obtenerUsuarioConectado().
                                                getInstitucionEjercicioFiscal().getId()) != null) {
                                    err = true;
                                    mostrarMensajeEnPantalla("Error en la línea " + i + ". El valor del campo \""
                                            + claves[j]
                                            + "\" ya está asignado a un puesto o registro de reclutamiento en "
                                            + " la base de datos.",
                                            FacesMessage.SEVERITY_ERROR);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    if (!errorNroIdentificacion) {
                        err |= errorTipoCampo(i, campoConf.get("tipo"), claves[j], valorCampo);
                    }
                }

                error |= err;
                j++;
            }
            i++;
        }

        if (!error) {
            reclutamientoHelper.getLineasGuardarMasivamente().put(identificadorArchivo, lineas);
        } else {
            reclutamientoHelper.getLineasGuardarMasivamente().put(identificadorArchivo, null);
        }

        return error;
    }

    /**
     *
     * @param archivo
     * @return
     */
    private List<String[]> leerArchivoSubido(UploadedFile archivo) {
        try {
            File f = new File(archivo.getFileName());
            OutputStream outStream = new FileOutputStream(f);
            long pesoArchivo = archivo.getSize();

            byte[] buffer = new byte[(int) pesoArchivo];
            InputStream stream = archivo.getInputstream();
            stream.read(buffer, 0, (int) pesoArchivo);
            stream.close();

            outStream.write(buffer);
            outStream.close();

            CSVReader reader;

            List<String[]> lineas = new FastArrayList();
            reader = new CSVReader(new FileReader(f), ';');
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                lineas.add(nextLine);
            }

            return lineas;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param nroLinea
     * @param campo
     */
    private void errorCampoObligatorio(int nroLinea, String campo) {
        mostrarMensajeEnPantalla(
                "Error en la línea " + nroLinea
                + " del archivo subido. Se omitió el campo \""
                + campo + "\".", FacesMessage.SEVERITY_ERROR);
    }

    /**
     *
     * @param valorCampo
     * @param dominio
     * @return
     */
    private boolean errorValidandoDominio(String valorCampo, String dominio) {

        boolean error = true;
        String[] dominioArr = dominio.split(":");
        if (dominioArr.length == 1) {
            if (dominio.contains(valorCampo)) {
                error = false;
            }

        } else {
            for (String c : dominioArr[1].split(",")) {
                Long idCatalogo = extraerIdCatalogoDadoCodigo(
                        valorCampo, reclutamientoHelper.getMapaListasCatalogos().get(c));
                if (idCatalogo != null) {
                    error = false;
                    break;
                }
            }
        }
        return error;
    }

    /**
     * Extrae el id de un elemento en una lista, dado su codigo, el cual fue annadido como descripcion al llenar la
     * lista
     *
     * @param codigo
     * @param lista
     * @return
     */
    private Long extraerIdCatalogoDadoCodigo(String codigo, List<SelectItem> lista) {
        try {
            for (SelectItem item : lista) {
                if (item.getDescription() != null && item.getDescription().equals(codigo)) {
                    return (Long) item.getValue();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     *
     * @param nroLinea
     * @param campo
     */
    private boolean errorTipoCampo(
            int nroLinea,
            String tiposCampo,
            String campo, String valorCampo) {

        boolean error = false;
        String[] tiposPermitidos = tiposCampo.split(",");

        if (tiposCampo.equals("Date")) {
            String formato1 = "\\d{2}/\\d{2}/\\d{4}";
            String formato2 = "\\d{2}-\\d{2}-\\d{4}";
            error |= !(valorCampo.matches(formato1) || valorCampo.matches(formato2));
            if (!error) {
                Integer dia = new Integer(valorCampo.substring(0, 2));
                Integer mes = new Integer(valorCampo.substring(3, 5));
                error |= (dia < 1 || dia > 31 || mes < 1 || mes > 12);
            }
            if (error) {
                mostrarMensajeEnPantalla("Error en la línea " + nroLinea + ". El valor del campo \""
                        + campo + "\" tiene un formato de fecha incorrecto.",
                        FacesMessage.SEVERITY_ERROR);
            }

        } else if (tiposCampo.equals("HR24")) {
            String formatoHR24 = "\\d{2}:\\d{2}";
            error |= !valorCampo.matches(formatoHR24);
            if (!error) {
                String[] horaMinuto = valorCampo.split(":");
                Integer valorHora = new Integer(horaMinuto[0]);
                Integer valorMinutos = new Integer(horaMinuto[1]);
                error |= (valorHora < 0 || valorHora > 23 || valorMinutos < 0 || valorMinutos > 59);
            }
            if (error) {
                mostrarMensajeEnPantalla("Error en la línea " + nroLinea + ". El valor del campo \""
                        + campo + "\" tiene un formato de fecha incorrecto.",
                        FacesMessage.SEVERITY_ERROR);
            }

        } else if (tiposCampo.equals("HR12")) {
            String formatoHR12 = "\\d{1,2}:\\d{2}";
            error |= !valorCampo.matches(formatoHR12);
            if (!error) {
                String[] horaMinuto = valorCampo.split(":");
                Integer valorHora = new Integer(horaMinuto[0]);
                Integer valorMinutos = new Integer(horaMinuto[1]);
                error |= (valorHora < 1 || valorHora > 12 || valorMinutos < 0 || valorMinutos > 59);
            }
            if (error) {
                mostrarMensajeEnPantalla("Error en la línea " + nroLinea + ". El valor del campo \""
                        + campo + "\" tiene un formato de hora incorrecto.",
                        FacesMessage.SEVERITY_ERROR);
            }

        } else {
            Object campoConTipo = obtenerCampoConSuTipo(valorCampo);
            boolean tipoEncontrado = false;

            for (String tp : tiposPermitidos) {
                tipoEncontrado = campoConTipo.getClass().getSimpleName().equals(tp);
                if (tipoEncontrado) {
                    break;
                }
            }

            error = !tipoEncontrado;

            if (error) {
                if (tiposPermitidos.length > 1) {
                    mostrarMensajeEnPantalla("Error en la línea " + nroLinea + ". El valor del campo \""
                            + campo + "\" debe ser de uno de los siguientes tipos: "
                            + Arrays.toString(tiposPermitidos), FacesMessage.SEVERITY_ERROR);

                } else if (tiposPermitidos.length == 1) {
                    mostrarMensajeEnPantalla("Error en la línea " + nroLinea + ". El valor del campo \""
                            + campo + "\" debe ser de tipo: "
                            + tiposPermitidos[0], FacesMessage.SEVERITY_ERROR
                    );
                }
            }
        }

        return error;
    }

    /**
     * EXTRAE DE UNA CADENA STRING CADA TROZO DE LA MISMA CASTEADO DE ACUERDO A SU TIPO. EJ: SI ENCUENTRA UN NUMERO LO
     * DEVUELVE COMO NUMERO, NO COMO STRING
     *
     * @param campo
     * @return
     */
    private Object obtenerCampoConSuTipo(String campo) {
        Scanner sc = new Scanner(campo);
        return sc.hasNextInt() ? sc.nextInt()
                : sc.hasNextLong() ? sc.nextLong()
                        : sc.hasNextDouble() ? sc.nextDouble()
                                : sc.hasNextBoolean() ? sc.nextBoolean()
                                        : sc.hasNext() ? sc.next()
                                                : campo;
    }

    /**
     *
     */
    public void guardarMasivamente() {
        if (reclutamientoHelper.getArchivoRDG() != null) {
            try {
                ParametroInstitucional pi
                        = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                                getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());

                reclutamientoServicio.guardarReclutamientosMasivamente(
                        reclutamientoHelper.getLineasGuardarMasivamente(),
                        EstadoReclutamientoEnum.REGISTRADO.getCodigo(), obtenerUsuarioConectado(),
                        esRRHH(pi.getValorTexto()));

                mostrarMensajeEnPantalla("Reclutamiento Masivo Ejecutado Satisfactoriamente.",
                        FacesMessage.SEVERITY_INFO);
                ejecutarComandoPrimefaces("dlgReclutamientoMasivo.hide();");
                cancelarReclutamientoMasivo();

            } catch (Exception e) {
                mostrarMensajeEnPantalla("Error ejecutando reclutamiento masivo.", FacesMessage.SEVERITY_ERROR);
                e.printStackTrace();
            }

        } else {
            mostrarMensajeEnPantalla(
                    "Debe subir el archivo con los datos generales de los posibles nuevos servidores.",
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * @return the reclutamientoHelper
     */
    public ReclutamientoHelper getReclutamientoHelper() {
        return reclutamientoHelper;
    }

    /**
     * @param reclutamientoHelper the reclutamientoHelper to set
     */
    public void setReclutamientoHelper(ReclutamientoHelper reclutamientoHelper) {
        this.reclutamientoHelper = reclutamientoHelper;
    }

    /**
     * @return the estadoPuestoHelper
     */
    public EstadoPuestoHelper getEstadoPuestoHelper() {
        return estadoPuestoHelper;
    }

    /**
     * @param estadoPuestoHelper the estadoPuestoHelper to set
     */
    public void setEstadoPuestoHelper(EstadoPuestoHelper estadoPuestoHelper) {
        this.estadoPuestoHelper = estadoPuestoHelper;
    }
}
