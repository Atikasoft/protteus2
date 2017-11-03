/*
 *  DistributivoControlador.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  11/11/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_GENERICO;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.DistributivoHelper;
import ec.com.atikasoft.proteus.dao.CargaMasivaPuestoDao;
import ec.com.atikasoft.proteus.dao.DenominacionPuestoDao;
import ec.com.atikasoft.proteus.dao.DistributivoDao;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.dao.EscalaOcupacionalDao;
import ec.com.atikasoft.proteus.dao.EstadoPuestoDao;
import ec.com.atikasoft.proteus.dao.ModalidadLaboralDao;
import ec.com.atikasoft.proteus.dao.UbicacionGeograficaDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.dao.UnidadPresupuestariaDao;
import ec.com.atikasoft.proteus.enums.EstadoPuestoEnum;
import ec.com.atikasoft.proteus.enums.TipoUbicacionGeograficaEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DenominacionPuesto;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.EscalaOcupacional;
import ec.com.atikasoft.proteus.modelo.EstadoPuesto;
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.NivelOcupacional;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.modelo.UbicacionGeografica;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.UnidadPresupuestaria;
import ec.com.atikasoft.proteus.modelo.distributivo.CargaMasivaPuesto;
import ec.com.atikasoft.proteus.modelo.distributivo.Distributivo;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.RegimenLaboralServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.ModalidadLaboralPuestoVO;
import ec.com.atikasoft.proteus.vo.UnidadOrganizacionalPuestoVO;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

/**
 * Distributivo
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "distributivoBean")
@ViewScoped
public class DistributivoControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(DistributivoControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/puesto/puesto.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/puesto/lista_puesto.jsf";

    /**
     * Regla de navegación.
     */
    public static final String UBICACION_GEOGRAFICA_QUITO_CODIGO = "208";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de distribucion de puestos.
     */
    @EJB
    private DistributivoPuestoServicio distributivoServicio;

    /**
     * Servicio de regimen laboral
     */
    @EJB
    private RegimenLaboralServicio regimenLaboralServicio;

    /**
     *
     */
    @EJB
    private UnidadOrganizacionalDao unidadOrganizacionalDao;

    /**
     *
     */
    @EJB
    private ModalidadLaboralDao modalidadLaboralDao;
    /**
     *
     */
    @EJB
    private UnidadPresupuestariaDao unidadPresupuestariaDao;
    /**
     *
     */
    @EJB
    private EscalaOcupacionalDao escalaOcupacionalDao;
    /**
     *
     */
    @EJB
    private DenominacionPuestoDao denominacionPuestoDao;
    /**
     *
     */
    @EJB
    private UbicacionGeograficaDao ubicacionGeograficaDao;
    /**
     *
     */
    @EJB
    private DistributivoDao distributivoDao;

    /**
     *
     */
    @EJB
    private EstadoPuestoDao estadoPuestoDao;

    /**
     *
     */
    @EJB
    private CargaMasivaPuestoDao cargaMasivaPuestoDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{distributivoHelper}")
    private DistributivoHelper distributivoHelper;

    /**
     * Constructor por defecto.
     */
    public DistributivoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(distributivoHelper);
        setDistributivoHelper(distributivoHelper);
        cargarListasControles();
        //buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    @Override
    public String guardar() {
        try {

            if (distributivoHelper.getEsNuevo()) {
                if (existeNombre()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else if (distributivoHelper.getDistributivo().getIdUnidadOrganizacional() == null) {
                    mostrarMensajeEnPantalla("El campo Unidad Organizacional es requerido",
                            FacesMessage.SEVERITY_ERROR);
                } else {
                    distributivoHelper.getDistributivo().setIdInstitucionEjercicioFiscal(
                            obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());

                    distributivoHelper.setDistributivosDetalles(
                            distributivoHelper.getDistributivosDetalles());

                    distributivoServicio.guardarDistributivo(distributivoHelper.getDistributivo(), obtenerUsuarioConectado(), null);
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                }

            } else {
                distributivoServicio.actualizarDistributivo(distributivoHelper.getDistributivo());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * Permite actualizar el puesto.
     *
     * @return
     */
    public String actualizarPuesto() {
        try {

//            if (distributivoHelper.getDistributivoDetalle().getEstadoPuesto().getPuedeOcuparse()) {
            distributivoHelper.getDistributivoDetalle().setFechaInicio(distributivoHelper.getFechainicio());
            distributivoHelper.getDistributivoDetalle().setFechaFin(distributivoHelper.getFechafin());
            distributivoHelper.getDistributivoDetalle().setIdDenominacionPuesto(distributivoHelper.getIdDenominacion());
            distributivoHelper.getDistributivoDetalle().setDenominacionPuesto(admServicio.buscarDenominacionPuesto(
                    distributivoHelper.getIdDenominacion()));
            distributivoServicio.actualizarDistributivoDetalle(distributivoHelper.getDistributivoDetalle(),
                    distributivoHelper.getDistributivo());
            distributivoServicio.crearDistributivoDetalleHistorico(distributivoHelper.getDistributivoDetalle(),
                    obtenerUsuarioConectado());
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            iniciarDetalle();
            buscarDistributivoDetalles(distributivoHelper.getDistributivoDetalle().getDistributivo().getId());
//            } else {
//                mostrarMensajeEnPantalla("Puesto no puede ser modificado ya que actualmente se encuentra en estado OCUPADO", FacesMessage.SEVERITY_ERROR);
//            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe el nombre. En MDMQ no desean nombre, por tanto se valida por unidad y modalidad.
     *
     * @return hayCodigo Boolean.
     */
    public Boolean existeNombre() {
        Boolean hayCodigo = true;
        try {
            distributivoHelper.getListaDistributivoCodigo().clear();
            String codigo = obtenerCodigoModalidad(distributivoHelper.getDistributivo().getIdModalidadLaboral());
            if (codigo != null) {
                Distributivo d = distributivoServicio.buscarDistributivo(distributivoHelper.getDistributivo().
                        getUnidadOrganizacional().getCodigo(),
                        codigo, obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
                hayCodigo = d != null && d.getId() != null;
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda validacion codigo", ex);
        }
        return hayCodigo;
    }

    /**
     *
     * @param idModalidad
     * @return
     */
    private String obtenerCodigoModalidad(Long idModalidad) {

        String codigo = null;

        for (ModalidadLaboral l : distributivoHelper.getModalidadesLaborales()) {
            if (l.getId().equals(idModalidad)) {
                return l.getCodigo();
            }
        }
        return codigo;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(distributivoHelper.getDistributivoEditDelete());
            Distributivo d = (Distributivo) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            distributivoHelper.setDistributivo(d);
            distributivoHelper.setEsNuevo(Boolean.FALSE);
            buscarDistributivoDetalles(d.getId());
            iniciarDetalle();

        } catch (IllegalAccessException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InstantiationException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InvocationTargetException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (NoSuchMethodException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        //distributivoHelper.iniciador();
        iniciarDatosEntidad(distributivoHelper.getDistributivo(), Boolean.TRUE);
        distributivoHelper.setEsNuevo(Boolean.TRUE);
        cargarListasControles();
        distributivoHelper.setEsNuevo(true);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {

        return null;
    }

    public void limpiar() {
        distributivoHelper.getListaDistributivos().clear();
    }

    /**
     * llena unidad organizacional.
     *
     * @return
     */
    public String cargarArbolUnidadOrganizacional() {
        try {
            distributivoHelper.getUnidadesOrganizacionales().clear();
            if (distributivoHelper.getRootUnidadOrganizacional().getChildCount() == 0) {
                distributivoHelper.setUnidadesOrganizacionales(admServicio.listarTodosUnidadOrganizacional());
                TreeNode nodoPrincipal;
                nodoPrincipal = new DefaultTreeNode(distributivoHelper.getUnidadesOrganizacionales().get(0), null);
                TreeNode nodoPadre = nodoPrincipal;
                distributivoHelper.setRootUnidadOrganizacional(nodoPrincipal);
                for (UnidadOrganizacional un : distributivoHelper.getUnidadesOrganizacionales()) {
                    if (un.getVigente()) {
                        nodoPadre = new DefaultTreeNode(un, nodoPrincipal);
                        if (un.getId() != null) {
                            obtenerHijos(un, nodoPadre);
                        }
                    }
                }
            }

            actualizarComponente("frmUnidad");
            ejecutarComandoPrimefaces("dlgUnidadOrganizacional.show();");
        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
        return null;
    }

    public void obtenerHijos(UnidadOrganizacional registroPadre, TreeNode nodoPadre) {
        try {
            for (UnidadOrganizacional unidad : registroPadre.getListaUnidadesOrganizacionales()) {
                if (unidad.getVigente()) {
                    TreeNode nodoHijo = new DefaultTreeNode(unidad, nodoPadre);
                    if (!unidad.getListaUnidadesOrganizacionales().isEmpty()) {
                        obtenerHijos(unidad, nodoHijo);
                    }
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
    }

    /**
     * seleccion de nodo.
     *
     * @param event
     */
    public void onNodeSelectUnidad(NodeSelectEvent event) {
        UnidadOrganizacional un = (UnidadOrganizacional) distributivoHelper.getUnidadSeleccionada().getData();
        distributivoHelper.getDistributivo().setUnidadOrganizacional(un);
        distributivoHelper.getDistributivo().setIdUnidadOrganizacional(un.getId());
        distributivoHelper.getListaDistributivos().clear();
        actualizarComponente("frmPrincipal:tabladistributivo");
    }

    public void onNodeSelectUbicacion(NodeSelectEvent event) {
        UbicacionGeografica un = (UbicacionGeografica) distributivoHelper.getUbicacionSeleccionada().getData();
        if (un != null && un.getId() != null) {
            distributivoHelper.getDistributivoDetalle().setIdUbicacionGeografica(un.getId());
            distributivoHelper.getDistributivoDetalle().setUbicacionGeografica(un);
        } else {
            mostrarMensajeEnPantalla("Debe seleccionar una Ubicación Válida", FacesMessage.SEVERITY_INFO);
        }
    }

    public void onNodeSelectRegimen(NodeSelectEvent event) {
        if (distributivoHelper.getEscalaSeleccionada().getData() instanceof EscalaOcupacional) {
            EscalaOcupacional un = (EscalaOcupacional) distributivoHelper.getEscalaSeleccionada().getData();
            distributivoHelper.getDistributivoDetalle().setIdEscalaOcupacional(un.getId());
            distributivoHelper.getDistributivoDetalle().setEscalaOcupacional(un);
            distributivoHelper.getDistributivoDetalle().setRmu(un.getRmu());
            distributivoHelper.getDistributivoDetalle().setRmuOriginal(un.getRmu());
            distributivoHelper.getDistributivoDetalle().setRmuEscala(un.getRmu());
            distributivoHelper.getDistributivoDetalle().setRmuSobrevalorado(BigDecimal.ZERO);
        } else {
            mostrarMensajeEnPantalla(SELECCIONAR_ESCALA_OCUPACIONAL, FacesMessage.SEVERITY_INFO);
        }
    }

    /**
     * Permite cargar un Tree con los datos de Regimen Laboral, Nivel Ocupacional, Escala Ocupacional
     *
     * @return
     */
    public String cargarArbolEscalaOcupacional() {
        try {
            buscarEscalaLaboral();
            distributivoHelper.setRootRegimen(new DefaultTreeNode("root", null));
            List<RegimenLaboral> lista = distributivoHelper.getEscalasOcupacionales();
            TreeNode nodoPrincipal = new DefaultTreeNode(lista.get(0),
                    distributivoHelper.getRootRegimen());
            lista.remove(0);
            for (RegimenLaboral reg : lista) {

                TreeNode nodoRegimen = new DefaultTreeNode(reg, nodoPrincipal);
                if (reg.getListaNivelOcupacional().size() > 0) {
                    for (NivelOcupacional nivel : reg.getListaNivelOcupacional()) {
                        if (nivel.getVigente()) {
                            TreeNode nodoNivel = new DefaultTreeNode(nivel, nodoRegimen);
                            for (EscalaOcupacional escala : nivel.getListaEscalaOcupacional()) {
                                if (escala.getVigente()) {
                                    TreeNode nodoEscala = new DefaultTreeNode(escala, nodoNivel);
                                }
                            }
                        }
                    }
                }
            }
            actualizarComponente("frmRegimen");
            ejecutarComandoPrimefaces("dlgRegimenLaboral.show();");
        } catch (Exception ex) {
            error(getClass().getName(), "Error al procesar la busqueda regimen", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            if (distributivoHelper.getDistributivo().getUnidadOrganizacional() == null || distributivoHelper.getDistributivo().getUnidadOrganizacional().getCodigo() == null) {
                mostrarMensajeEnPantalla("Debe seleccionar una unidad organizacional", FacesMessage.SEVERITY_ERROR);
            } else {
                distributivoHelper.setTotalPuestos(0l);
                Long tam;
                distributivoHelper.getListaDistributivos().clear();
                distributivoHelper.setListaDistributivos(
                        distributivoDao.buscar(distributivoHelper.getDistributivo().getUnidadOrganizacional().getCodigo(), obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId()));
                for (Distributivo d : distributivoHelper.getListaDistributivos()) {
                    tam = distributivoServicio.contarDetallesPorDistributivo(d);
                    d.setTotalDetalles(tam);
                    distributivoHelper.setTotalPuestos(distributivoHelper.getTotalPuestos() + tam);
                }
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * guarda los detalles del distributivo.
     *
     * @return
     */
    public String agregarDetalle() {
        if (distributivoHelper.getIdDenominacion() != null) {
            distributivoHelper.getDistributivoDetalle().setIdDenominacionPuesto(distributivoHelper.getIdDenominacion());
            distributivoHelper.getDistributivoDetalle().setDenominacionPuesto(new DenominacionPuesto());
            if (distributivoHelper.getIdDenominacion() != null) {
                distributivoHelper.getDistributivoDetalle().getDenominacionPuesto().setNombre(
                        obtenerDescripcionSelectItem(distributivoHelper.getOpcionesDenominacionPuesto(), distributivoHelper.
                                getIdDenominacion()));
            }
        }
        if (distributivoHelper.getFechainicio() != null) {
            distributivoHelper.getDistributivoDetalle().setFechaInicio(distributivoHelper.getFechainicio());
        }
//        System.out.println("distributivoHelper.getDistributivoDetalle().getGrupoPresupuestario()=>" + distributivoHelper.
//                getDistributivoDetalle().getGrupoPresupuestario());
        distributivoHelper.getDistributivoDetalle().setFechaFin(distributivoHelper.getFechafin());
        distributivoHelper.getDistributivoDetalle().setSueldoBasico(BigDecimal.ZERO);
        if (distributivoHelper.getDistributivoDetalle().getIdEscalaOcupacional() == null) {
            mostrarMensajeEnPantalla("El campo Denominación de Puesto es requerido", FacesMessage.SEVERITY_ERROR);
        } else if (distributivoHelper.getDistributivoDetalle().getIdDenominacionPuesto() == null) {
            mostrarMensajeEnPantalla("El campo Grupo Ocupacional es requerido", FacesMessage.SEVERITY_ERROR);
        } else if (distributivoHelper.getDistributivoDetalle().getIdUbicacionGeografica() == null) {
            mostrarMensajeEnPantalla("El campo Ubicación Geográfica es requerido", FacesMessage.SEVERITY_ERROR);
        } else if (distributivoHelper.getDistributivoDetalle().getFechaInicio() == null) {
            mostrarMensajeEnPantalla("El campo Fecha de Creación es requerido", FacesMessage.SEVERITY_ERROR);
        } else if (distributivoHelper.getDistributivoDetalle().getUnidadPresupuestaria() == null) {
            mostrarMensajeEnPantalla("El campo Unidad Presupuestaria es requerido", FacesMessage.SEVERITY_ERROR);
        } else if (distributivoHelper.getDistributivoDetalle().getUnidadPresupuestaria().getGrupoPresupuestario()
                == null) {
            mostrarMensajeEnPantalla("El campo Grupo Presupuestario es requerido", FacesMessage.SEVERITY_ERROR);
        } else {
            try {
                if (distributivoHelper.getDistributivoDetalle().getFechaFin() != null
                        && !UtilFechas.validarFechaInicioFin(distributivoHelper.getDistributivoDetalle().getFechaInicio(), distributivoHelper.
                                getDistributivoDetalle().getFechaFin())) {
                    mostrarMensajeEnPantalla("La fecha inicio no puede ser mayor a la fecha final", FacesMessage.SEVERITY_ERROR);
                    return null;
                }
                distributivoHelper.getDistributivoDetalle().setDenominacionPuesto(new DenominacionPuesto());
                if (distributivoHelper.getOpcionesDenominacionPuesto() != null) {
                    distributivoHelper.getDistributivoDetalle().getDenominacionPuesto().setNombre(
                            obtenerDescripcionSelectItem(distributivoHelper.getOpcionesDenominacionPuesto(),
                                    distributivoHelper.getIdDenominacion()));
                }

                distributivoHelper.getDistributivosDetalles().add(distributivoHelper.getDistributivoDetalle());
                distributivoServicio.guardarDistributivoDetalle(distributivoHelper.getDistributivoDetalle(),
                        obtenerUsuarioConectado(), null);
                mostrarMensajeEnPantalla(
                        "Puesto " + distributivoHelper.getDistributivoDetalle().getCodigoPuesto() + " Creado Exitosamente.",
                        FacesMessage.SEVERITY_INFO);
                iniciarDetalle();
            } catch (ServicioException ex) {
                mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
                error(getClass().getName(), "Error al guardar detalle distributivo", ex);
            }

        }
        return null;

    }

    /**
     * Inicializa los valores y listas del distributivo detalles.
     */
    public void iniciarDetalle() {
        try {
            distributivoHelper.setDistributivoDetalle(new DistributivoDetalle());
            if (distributivoHelper.getEstadoPuestoPredeterminado() != null) {
                distributivoHelper.getDistributivoDetalle().setIdEstadoPuesto(distributivoHelper.getEstadoPuestoPredeterminado().getId());
                distributivoHelper.getDistributivoDetalle().setEstadoPuesto(distributivoHelper.getEstadoPuestoPredeterminado());
            }
            List<UbicacionGeografica> lista = admServicio.listarUbicacionGeograficaPorNemonico(UBICACION_GEOGRAFICA_QUITO_CODIGO);
            if (!lista.isEmpty()) {
                distributivoHelper.getDistributivoDetalle().setUbicacionGeografica(lista.get(0));
                distributivoHelper.getDistributivoDetalle().setIdUbicacionGeografica(lista.get(0).getId());
            }
            iniciarDatosEntidad(distributivoHelper.getDistributivoDetalle(), Boolean.TRUE);
            distributivoHelper.getDistributivoDetalle().setDistributivo(distributivoHelper.getDistributivo());
            distributivoHelper.getDistributivoDetalle().setIdDistributivo(distributivoHelper.getDistributivo().getId());
            distributivoHelper.setIdDenominacion(null);
            distributivoHelper.setFechainicio(null);
            distributivoHelper.setFechafin(null);
            distributivoHelper.setSueldoBasico(BigDecimal.ZERO);
            buscarDenominacionPuestos();
            iniciarOpcionesDenominacionPuestos();
            distributivoHelper.setEsEdicionPuesto(Boolean.FALSE);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de Ubicacion geografica por defecto", ex);
        }

    }

    /**
     * Regresa a la lista anterior
     *
     * @return
     */
    public String irLista() {
        distributivoHelper.setEsNuevo(Boolean.TRUE);
        return LISTA_ENTIDAD;
    }

    /**
     * Permite eliminar distributivos detalles.
     */
    public void eliminarDetalle() {
//        try {
        mostrarMensajeEnPantalla("El puesto no puede ser eliminado.", FacesMessage.SEVERITY_ERROR);
//            if (distributivoHelper.getDistributivoDetalleEliminar().getId() == null) {
//                distributivoHelper.getDistributivosDetalles().remove(
//                        distributivoHelper.getDistributivoDetalleEliminar());
//                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
//                ejecutarComandoPrimefaces("confirmEliminacion.hide()");
//            } else if (distributivoHelper.getDistributivoDetalleEliminar().getId() != null
//                    && distributivoHelper.getDistributivoDetalleEliminar().getEstadoPuesto().isPredeterminado()
//                    && distributivoHelper.getDistributivoDetalleEliminar().getIdServidor() == null) {
//                iniciarDatosEntidad(distributivoHelper.getDistributivoDetalleEliminar(), Boolean.TRUE);
//                distributivoHelper.getDistributivosDetalles().remove(
//                        distributivoHelper.getDistributivoDetalleEliminar());
//                
//                
//                distributivoServicio.eliminarDistributivoDetalle(distributivoHelper.getDistributivoDetalleEliminar());
//                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
//                ejecutarComandoPrimefaces("confirmEliminacion.hide()");
//            } else {
//                mostrarMensajeEnPantalla("El puesto se encuentra Ocupado. No se puede Eliminar.", FacesMessage.SEVERITY_ERROR);
//            }
//        } catch (ServicioException ex) {
//            Logger.getLogger(DistributivoControlador.class.getName()).log(Level.SEVERE, null, ex);
//            mostrarMensajeEnPantalla("Problemas al Eliminar el Puesto: " + distributivoHelper.
//                    getDistributivoDetalleEliminar().getCodigoPuesto(), FacesMessage.SEVERITY_ERROR);
//        }
    }

    /**
     * Permite editar distributivos detalles.
     *
     * @return
     */
    public String editarDetalle() {
        distributivoHelper.setEsEdicionPuesto(Boolean.TRUE);
        distributivoHelper.setFechainicio(distributivoHelper.getDistributivoDetalle().getFechaInicio());
        distributivoHelper.setFechafin(distributivoHelper.getDistributivoDetalle().getFechaFin());
        distributivoHelper.setIdDenominacion(distributivoHelper.getDistributivoDetalle().getIdDenominacionPuesto());
        iniciarDatosEntidad(distributivoHelper.getDistributivoDetalle(), Boolean.FALSE);
        iniciarOpcionesGrupoPresupuestario();
        return null;
    }

    /**
     * Inicializa variables de la cabecera del distributivo.
     */
    private void cargarListasControles() {
        buscarModalidadLaboral();
        iniciarOpcionesModalidadLaboral();
        distributivoHelper.setDistributivoDetalleEliminar(new DistributivoDetalle());
        try {
            distributivoHelper.setEstadoPuestoPredeterminado(admServicio.buscarEstadoPuestoPredeterminado());
            if (distributivoHelper.getEstadoPuestoPredeterminado() == null) {
                mostrarMensajeEnPantalla("Configurar un Estado de Puesto Predeterminado!", FacesMessage.SEVERITY_ERROR);
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de estado predeterminado de puesto", ex);
        }
    }

    /**
     *
     * @param idDistributivo
     * @return
     */
    public String buscarDistributivoDetalles(Long idDistributivo) {
        try {
            if (distributivoHelper.getDistributivosDetalles() == null) {
                distributivoHelper.setDistributivosDetalles(new ArrayList<DistributivoDetalle>());
            }
            Distributivo d = distributivoDao.buscarPorId(idDistributivo);
            distributivoHelper.getDistributivosDetalles().clear();
            List<DistributivoDetalle> lista = distributivoServicio.buscarPuestos(
                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId(), d.getIdUnidadOrganizacional(),
                    d.getIdModalidadLaboral());

            distributivoHelper.setDistributivosDetalles(lista);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda detalles", ex);
        }
        return null;
    }

    private void buscarEscalaLaboral() {
        try {

            distributivoHelper.getEscalasOcupacionales().clear();
            distributivoHelper.setEscalasOcupacionales(
                    regimenLaboralServicio.listarTodosRegimenLaboral());
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda escala laboral", ex);
        }
    }

    private void buscarModalidadLaboral() {
        try {
            distributivoHelper.getModalidadesLaborales().clear();
            distributivoHelper.setModalidadesLaborales(
                    admServicio.listarTodosModalidadLaboralVigentes());
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda modalidad laboral", ex);
        }
    }

    /**
     *
     * @param uo
     */
    private void buscarUnidadOrganizacional(UnidadOrganizacional uo) {
        try {
            distributivoHelper.getUnidadesOrganizacionales().clear();
            distributivoHelper.setUnidadesOrganizacionales(admServicio.listarTodosUnidadOrganizacional());
//
//            if (uo == null) {
//                distributivoHelper.setUnidadesOrganizacionales(admServicio.listarTodosUnidadOrganizacional());
//            } else {
//                distributivoHelper.setUnidadesOrganizacionales(unidadOrganizacionalDao.buscarPorCodigoLike(uo.getCodigo()));
//            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda unidades organizacionales", ex);
        }
    }

    public void buscarUnidadesPresupuestarias() {
        try {
            distributivoHelper.setUnidadesPresupuestarias(admServicio.listarTodosUnidadesPresupuestarias());
            actualizarComponente("frmPrestacional");
            ejecutarComandoPrimefaces("dlgUnidadPresupuestaria.show();");
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda unidades presupuestarias", ex);
        }
    }

    private void buscarUbicacionGeografica() {
        try {
            distributivoHelper.getUbicacionesGeograficas().clear();
            distributivoHelper.setUbicacionesGeograficas(
                    admServicio.listarTodosubicacionGeografica());
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda ubicacion geografica", ex);
        }
    }

    /**
     * llena ubiacion geografica.
     *
     * @return
     */
    public String cargarArbolUbicacionGeografica() {
        try {
            buscarUbicacionGeografica();
            distributivoHelper.setRootUbicacionGeografica(new DefaultTreeNode("root", null));

            TreeNode nodoPrincipal = new DefaultTreeNode(distributivoHelper.getUbicacionesGeograficas().get(0),
                    distributivoHelper.getRootUbicacionGeografica());
            distributivoHelper.getUbicacionesGeograficas().remove(0);

            for (UbicacionGeografica ug : distributivoHelper.getUbicacionesGeograficas()) {
                if (ug.getTipo().equals(TipoUbicacionGeograficaEnum.PAIS.getCodigo())) {
                    TreeNode pais = new DefaultTreeNode(ug, nodoPrincipal);
                    for (UbicacionGeografica ugp : distributivoHelper.getUbicacionesGeograficas()) {
                        if (ugp.getTipo().equals(TipoUbicacionGeograficaEnum.PROVINCIA.getCodigo())
                                && ugp.getIdUbicacionGeografica().equals(ug.getId())) {
                            TreeNode provincia = new DefaultTreeNode(ugp, pais);
                            for (UbicacionGeografica ugc : distributivoHelper.getUbicacionesGeograficas()) {
                                if (ugc.getTipo().equals(TipoUbicacionGeograficaEnum.CANTON.getCodigo())
                                        && ugc.getIdUbicacionGeografica().equals(ugp.getId())) {
                                    TreeNode canton = new DefaultTreeNode(ugc, provincia);
                                    for (UbicacionGeografica ugpa : distributivoHelper.getUbicacionesGeograficas()) {
                                        if (ugpa.getTipo().equals(TipoUbicacionGeograficaEnum.PARROQUIA.getCodigo())
                                                && ugpa.getIdUbicacionGeografica().equals(ugc.getId())) {
                                            TreeNode parroquia = new DefaultTreeNode(ugpa, canton);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            actualizarComponente("frmUbicacion");
            ejecutarComandoPrimefaces("dlgUbicacionGeografica.show();");
        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
        return null;
    }

    /**
     * Este metodo llena lista de Denominación de puestos.
     */
    private void buscarDenominacionPuestos() {
        try {
            distributivoHelper.getDenominacionPuestos().clear();
            distributivoHelper.setDenominacionPuestos(
                    admServicio.listarTodosDenominacionPuestoVigentes());
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda denominacion de puestos", ex);
        }
    }

    /**
     * Este metodo llena las opciones para el combo de Modalidad Laboral.
     */
    private void iniciarOpcionesModalidadLaboral() {
        distributivoHelper.getOpcionesModalidadLaboral().clear();
        iniciarCombos(distributivoHelper.getOpcionesModalidadLaboral());
        if (distributivoHelper.getModalidadesLaborales() != null) {
            for (ModalidadLaboral r : distributivoHelper.getModalidadesLaborales()) {
                distributivoHelper.getOpcionesModalidadLaboral().add(new SelectItem(r.getId(), r.getNombre()));
            }
        }
    }

    /**
     * Este metodo llena las opciones para el combo de Denominacion de puestos.
     */
    private void iniciarOpcionesDenominacionPuestos() {
        distributivoHelper.getOpcionesDenominacionPuesto().clear();
        iniciarCombos(distributivoHelper.getOpcionesDenominacionPuesto());
        for (DenominacionPuesto r : distributivoHelper.getDenominacionPuestos()) {
            distributivoHelper.getOpcionesDenominacionPuesto().add(new SelectItem(r.getId(), r.getNombre()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de Grupos Presupuestarios dependiendo de la unidad presupuestaria
     * seleccionada.
     */
    public void iniciarOpcionesGrupoPresupuestario() {
        distributivoHelper.getOpcionesGrupoPresupuestario().clear();
        iniciarCombos(distributivoHelper.getOpcionesGrupoPresupuestario());
        String[] grupos = distributivoHelper.getDistributivoDetalle().getUnidadPresupuestaria().getGrupoPresupuestario().split(",");
        for (String grupo : grupos) {
            distributivoHelper.getOpcionesGrupoPresupuestario().add(new SelectItem(grupo, grupo));
        }
    }

    public void seleccionarUnidadPresupuestaria() {
        distributivoHelper.getDistributivoDetalle().setUnidadPresupuestaria(distributivoHelper.getUnidadPresupuestariaSeleccionada());
        iniciarOpcionesGrupoPresupuestario();
        distributivoHelper.getDistributivoDetalle().setCertificacionPresupuestaria(
                distributivoServicio.obtenerNumeroCertificacionPresupuestaria(distributivoHelper.getDistributivoDetalle().
                        getUnidadPresupuestaria().getId(), distributivoHelper.getDistributivoDetalle().getDistributivo().
                        getIdModalidadLaboral()));
//        actualizarComponente(":frmPrinc:panelNuevoReg");
//        ejecutarComandoPrimefaces("dlgUnidadPresupuestaria.hide();");
    }

    ///---------------- CARGA MASIVA DE PUESTOS
    /**
     * Buscar las cargas masivas realizadas para esta institucion ejecicio fiscal
     *
     * @return
     */
    public String cargarCargasMasivasRealizadas() {
        distributivoHelper.getListaCargasMasivasPuestos().clear();
        try {
            List<CargaMasivaPuesto> lista = cargaMasivaPuestoDao.
                    buscarPorInstitucionEjericioFiscal(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            if (lista != null) {
                distributivoHelper.getListaCargasMasivasPuestos().addAll(lista);
            }
            ejecutarComandoPrimefaces("registrosCargaMasivaPuestosHistoricoDlgWV.show();");
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar las cargas masivas realizadas", e);
        }
        return null;
    }

    /**
     * Generar Excel de carga masiva
     *
     * @return
     */
    public StreamedContent generarExcelCargaMasivaPuestos() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyy");
        String nombre = "CARGA_MASIVA_" + (sdf.format(distributivoHelper.getCargaMasivaPuesto().getFechaCreacion())) + ".xls";
        try {
            InputStream stream = distributivoServicio.generarExcelPuestosGenerados(distributivoHelper.getCargaMasivaPuesto().getId());
            StreamedContent sc = new DefaultStreamedContent(
                    stream, "application/vnd.ms-excel", nombre);
            return sc;
        } catch (Exception e) {
            e.getMessage();
        }

        return null;

    }

    /**
     * Inicia los mapas de validaciones
     */
    private void iniciarValidadores() {
        limpiar();
        iniciarValidadorUnidadesOrganizaciones();
        iniciarValidadorModalidadLaboral();
        iniciarValidadorUnidadesPresupuestarias();
        iniciarValidadorEscalaOcupacional();
        iniciarValidadorDenominacionPuestos();
        iniciarValidadorUbicacionesGeograficas();
    }

    /**
     * Limpia la memoria usada por los mapas de validaciones
     */
    private void limpiarValidadores() {
        distributivoHelper.getUnidadesOrganizacionalesValidacion().clear();
        distributivoHelper.getModalidadesLaboralesValidacion().clear();
        distributivoHelper.getUnidadesPresupuestariasValidacion().clear();
        distributivoHelper.getEscalaOcupacionalValidacion().clear();
        distributivoHelper.getDenominacionesPuestosValidacion().clear();
        distributivoHelper.getUbicacionesGeograficasValidacion().clear();
    }

    /**
     * Carga los codigos de la unidades existentes para validar
     */
    private void iniciarValidadorUnidadesOrganizaciones() {
        try {
            List<UnidadOrganizacional> unidades = unidadOrganizacionalDao.buscarVigentes();
            for (UnidadOrganizacional uo : unidades) {
                UnidadOrganizacional uoT = new UnidadOrganizacional(uo.getId());
                uoT.setNombre(uo.getNombre());
                uoT.setCodigo(uo.getCodigo());
                distributivoHelper.getUnidadesOrganizacionalesValidacion().put(uo.getCodigo(), uoT);
            }
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Carga los codigos de las modalidades laborales exintes para validar
     */
    private void iniciarValidadorModalidadLaboral() {
        try {
            List<ModalidadLaboral> modalidadLaborals = modalidadLaboralDao.buscarVigente();
            for (ModalidadLaboral ml : modalidadLaborals) {
                ModalidadLaboral mlT = new ModalidadLaboral(ml.getId());
                mlT.setNombre(ml.getNombre());
                mlT.setCodigo(ml.getCodigo());
                distributivoHelper.getModalidadesLaboralesValidacion().put(ml.getCodigo(), mlT);
            }
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Carga los codigos de las unidades presupuestarias usadas para validaciones
     */
    private void iniciarValidadorUnidadesPresupuestarias() {
        try {
            List<UnidadPresupuestaria> unidadPresupuestarias = unidadPresupuestariaDao.buscarVigentes();
            for (UnidadPresupuestaria up : unidadPresupuestarias) {
                distributivoHelper.getUnidadesPresupuestariasValidacion().put(up.getCodigoInterno(), up);
            }
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Carga los codigos de las escalas ocupacionales para validaciones validaciones
     */
    private void iniciarValidadorEscalaOcupacional() {
        try {
            List<EscalaOcupacional> escalaOcupacionals = escalaOcupacionalDao.buscarVigente();
            for (EscalaOcupacional eo : escalaOcupacionals) {
                distributivoHelper.getEscalaOcupacionalValidacion().put(eo.getCodigo(), eo);
            }
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Carga los codigos de las denominaciones de puestos para validaciones validaciones
     */
    private void iniciarValidadorDenominacionPuestos() {
        try {
            List<DenominacionPuesto> denominacionPuestos = denominacionPuestoDao.buscarVigente();
            for (DenominacionPuesto dp : denominacionPuestos) {
                DenominacionPuesto dpT = new DenominacionPuesto(dp.getId());
                dpT.setNombre(dp.getNombre());
                distributivoHelper.getDenominacionesPuestosValidacion().put(dp.getCodigo(), dpT);
            }
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Carga los codigos de las ubicaciones geograficas para validaciones validaciones
     */
    private void iniciarValidadorUbicacionesGeograficas() {
        try {
            List<UbicacionGeografica> ubicacionGeograficas = ubicacionGeograficaDao.buscarActivosOrdenados();
            for (UbicacionGeografica ug : ubicacionGeograficas) {
                UbicacionGeografica ugT = new UbicacionGeografica(ug.getId());
                ugT.setNombre(ug.getNombre());
                ugT.setNombreCompleto(ug.getNombreCompleto());
                distributivoHelper.getUbicacionesGeograficasValidacion().put(ug.getCodigo(), ugT);
            }
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Inicializa la carga masiva de puestos
     *
     * @return
     */
    public String inicializarCargaMasiva() {
        distributivoHelper.getErrores().clear();
        distributivoHelper.getListaPuestosCargadosCSV().clear();
        distributivoHelper.setTotalRegistrosCargadosCSV(0);
        distributivoHelper.setTotalRegistrosFallidosCSV(0);
        distributivoHelper.setNombreArchivoCSV(null);
        ejecutarComandoPrimefaces("cargaMasivaPuestosDlg.show();");
        return null;
    }

    /**
     * Carga el Archivo csv
     *
     * @param event
     */
    public void cargarArchivo(final FileUploadEvent event) {
        try {
            distributivoHelper.getErrores().clear();
            distributivoHelper.getListaPuestosCargadosCSV().clear();
            distributivoHelper.setTotalRegistrosCargadosCSV(0);
            distributivoHelper.setTotalRegistrosFallidosCSV(0);
            distributivoHelper.getListaDistributivosGenerados().clear();
            distributivoHelper.getListaUnidadesOrganizacionalesCargadasCSV().clear();
            UploadedFile csv = event.getFile();
            File csvFile = UtilArchivos.getFileFromBytes(csv.getInputstream(), "carga_masiva_puestos" + Calendar.getInstance().getTimeInMillis(), "csv");
            distributivoHelper.setNombreArchivoCSV(csv.getFileName());
            String[][] contenido = UtilArchivos.obtenerContenidoCSV(csvFile, ';');
            int totalRegistros = contenido.length;
            if (contenido[0][0].startsWith("#")) {
                totalRegistros--;
            }
            List<String> errores = new ArrayList<>();
            List<Distributivo> listaDistributivos = new ArrayList<>();
            boolean csvCorrecto = validarDataCVS(contenido, errores, listaDistributivos);
            distributivoHelper.setTotalRegistrosCargadosCSV(totalRegistros);
            if (!csvCorrecto) {
                distributivoHelper.getErrores().addAll(errores);
                distributivoHelper.setTotalRegistrosFallidosCSV(errores.size());
                ejecutarComandoPrimefaces("erroresCargaMasivaPuestosDlg.show();");
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.puestos.masivo.parser.error", FacesMessage.SEVERITY_INFO);
            } else {
                distributivoHelper.getListaDistributivosGenerados().addAll(listaDistributivos);
                for (Distributivo d : listaDistributivos) {
                    for (DistributivoDetalle dd : d.getDistributivoDetalles()) {
                        distributivoHelper.getListaPuestosCargadosCSV().add(dd);
                    }
                }
                cargarResumen();
                mostrarMensajeEnPantalla("Archivo cargado satisfactoriamente.", FacesMessage.SEVERITY_INFO);
                ejecutarComandoPrimefaces("registrosCargaMasivaPuestosDlg.show();");
            }
        } catch (Exception e) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void cargarResumen() {
        distributivoHelper.getListaPuestoPorUnidadesCSV().clear();
        distributivoHelper.getListaUnidadesOrganizacionalesCargadasCSV().clear();
        List<UnidadOrganizacional> uos = new ArrayList<>();
        for (DistributivoDetalle dd : distributivoHelper.getListaPuestosCargadosCSV()) {
            if (!uos.contains(dd.getDistributivo().getUnidadOrganizacional())) {
                uos.add(dd.getDistributivo().getUnidadOrganizacional());
            }
        }
        distributivoHelper.setTotalUnidadesOrganizacionalesCargadasCSV(uos.size());
        for (UnidadOrganizacional uo : uos) {
            UnidadOrganizacionalPuestoVO vo = new UnidadOrganizacionalPuestoVO();
            vo.setUnidadOrganizacional(uo);
            vo.setTotal(0);
            distributivoHelper.getListaUnidadesOrganizacionalesCargadasCSV().add(vo);
        }
        Integer total = 0;
        Integer valor = 0;
        Map<Long, ModalidadLaboral> modalidades = new HashMap<>();
        Map<Long, Map<Long, Integer>> resumen = new HashMap<>();
        Map<Long, Integer> totalPorUnidad = new HashMap<>();
        for (DistributivoDetalle dd : distributivoHelper.getListaPuestosCargadosCSV()) {
            Distributivo d = dd.getDistributivo();
            Map<Long, Integer> totales = new HashMap<>();
            total = 0;
            if (resumen.containsKey(d.getModalidadLaboral().getId())) {
                totales = resumen.get(d.getModalidadLaboral().getId());
            }
            if (totalPorUnidad.containsKey(d.getUnidadOrganizacional().getId())) {
                total = totalPorUnidad.get(d.getUnidadOrganizacional().getId());
            }
            for (UnidadOrganizacionalPuestoVO uo : distributivoHelper.getListaUnidadesOrganizacionalesCargadasCSV()) {
                valor = 0;
                if (totales.containsKey(uo.getUnidadOrganizacional().getId())) {
                    valor = totales.get(uo.getUnidadOrganizacional().getId());
                }
                if (uo.getUnidadOrganizacional().getId().equals(d.getUnidadOrganizacional().getId())) {
                    valor++;
                    total++;
                }
                totales.put(uo.getUnidadOrganizacional().getId(), valor);
            }
            resumen.put(d.getModalidadLaboral().getId(), totales);
            modalidades.put(d.getModalidadLaboral().getId(), d.getModalidadLaboral());
            totalPorUnidad.put(d.getUnidadOrganizacional().getId(), total);
        }

        for (Map.Entry<Long, Map<Long, Integer>> r : resumen.entrySet()) {
            ModalidadLaboralPuestoVO vo = new ModalidadLaboralPuestoVO();
            vo.setModalidadLaboral(modalidades.get(r.getKey()));
            vo.setValoresUnidad(r.getValue());
            distributivoHelper.getListaPuestoPorUnidadesCSV().add(vo);
        }
        for (UnidadOrganizacionalPuestoVO uo : distributivoHelper.getListaUnidadesOrganizacionalesCargadasCSV()) {
            uo.setTotal(totalPorUnidad.get(uo.getUnidadOrganizacional().getId()));
        }

    }

    /**
     * Realiza la validacion de la data en el csv
     *
     * @param contenido
     * @param errores
     * @return
     */
    private boolean validarDataCVS(final String[][] contenido, final List<String> errores, final List<Distributivo> listaDistributivosGenerados) {

        iniciarValidadores();

        boolean csvCorrecto = true;
        boolean lineaCorrecta = true;
        Distributivo distributivo = null;
        DistributivoDetalle distributivoDetalle = null;
        Map<String, Distributivo> mapa = new HashMap<>();
        try {
            EstadoPuesto estadoPuesto = estadoPuestoDao.buscarPorCodigo(EstadoPuestoEnum.VACANTE.getCodigo());

            for (int i = 0; i < contenido.length; i++) {
                distributivo = null;
                distributivoDetalle = null;
                lineaCorrecta = true;
                StringBuilder sbErrores = new StringBuilder();
                sbErrores.append(getBundle("ec.gob.mrl.smp.administracion.puestos.masivo.error.linea", null)).append(i + 1).append(" : ");
                String[] linea = contenido[i];
                if (i == 0 && linea[0].startsWith("#")) {
                    continue;
                }

                if (linea.length != 7) {
                    sbErrores.append("Colmnas esperadas 7 columnas recibidas [").append(linea.length).append("]");
                    lineaCorrecta = false;
                } else {

                    //UO
                    if (!validarValorColumna(linea[0], distributivoHelper.getUnidadesOrganizacionalesValidacion(),
                            getBundle("ec.gob.mrl.smp.administracion.puestos.masivo.error.unidadOrganizacional", null), true, sbErrores)) {
                        lineaCorrecta = false;
                    }

                    //Modalidad Laboral
                    if (!validarValorColumna(linea[1], distributivoHelper.getModalidadesLaboralesValidacion(),
                            getBundle("ec.gob.mrl.smp.administracion.puestos.masivo.error.modalidadLaboral", null), true, sbErrores)) {
                        lineaCorrecta = false;
                    }

                    if (csvCorrecto && lineaCorrecta) {
                        //evitar crear memoria si de todas formas no se va a hacer la persistencia
                        //debido a la presencia de errores en el parseo, en cuyo caso de todas formas
                        //hay que llegar al fondo del csv
                        distributivo = mapa.get(linea[0] + ";" + linea[1]);
                        if (distributivo == null) {
                            distributivo = new Distributivo();
                            iniciarDatosEntidad(distributivo, Boolean.TRUE);
                            distributivo.setIdInstitucionEjercicioFiscal(
                                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
                            distributivo.setInstitucionEjercicioFiscal(
                                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal());
                            distributivo.setUnidadOrganizacional(distributivoHelper.getUnidadesOrganizacionalesValidacion().get(linea[0]));
                            distributivo.setIdUnidadOrganizacional(distributivo.getUnidadOrganizacional().getId());
                            distributivo.setModalidadLaboral(distributivoHelper.getModalidadesLaboralesValidacion().get(linea[1]));
                            distributivo.setIdModalidadLaboral(distributivo.getModalidadLaboral().getId());
                            distributivo.setDistributivoDetalles(new ArrayList<DistributivoDetalle>());
                        }

                        distributivoDetalle = new DistributivoDetalle();
                        iniciarDatosEntidad(distributivoDetalle, Boolean.TRUE);
                        distributivoDetalle.setDistributivo(distributivo);
                        distributivoDetalle.setEstadoPuesto(estadoPuesto);
                        distributivoDetalle.setIdEstadoPuesto(estadoPuesto.getId());
                    }

                    //Unidades Presupuestarias
                    if (!validarValorColumna(linea[2], distributivoHelper.getUnidadesPresupuestariasValidacion(),
                            getBundle("ec.gob.mrl.smp.administracion.puestos.masivo.error.unidadPresupuestaria", null), true, sbErrores)) {
                        lineaCorrecta = false;
                    }

                    if (distributivoDetalle != null && lineaCorrecta) {
                        distributivoDetalle.setUnidadPresupuestaria(distributivoHelper.getUnidadesPresupuestariasValidacion().get(linea[2]));
                    }

                    //Denominacion Puestos
                    if (!validarValorColumna(linea[3], distributivoHelper.getEscalaOcupacionalValidacion(),
                            getBundle("ec.gob.mrl.smp.administracion.puestos.masivo.error.escalaOcupacional", null), true, sbErrores)) {
                        lineaCorrecta = false;
                    }

                    if (distributivoDetalle != null && lineaCorrecta) {
                        distributivoDetalle.setEscalaOcupacional(distributivoHelper.getEscalaOcupacionalValidacion().get(linea[3]));
                        distributivoDetalle.setIdEscalaOcupacional(distributivoDetalle.getEscalaOcupacional().getId());
                    }

                    //Fecha Creacion
                    String fechaCreacion = linea[4];
                    Date date = null;
                    try {
                        date = parseDate(fechaCreacion);
                        if (date == null) {
                            sbErrores.append(getBundle("ec.gob.mrl.smp.administracion.puestos.masivo.error.fechaCreacion", null))
                                    .append(" es requerida.");
                            lineaCorrecta = false;
                        }
                    } catch (Exception e) {
                        sbErrores.append("Error parseando ").
                                append(getBundle("ec.gob.mrl.smp.administracion.puestos.masivo.error.fechaCreacion", null)).
                                append(" [").append(fechaCreacion).append("]");
                        lineaCorrecta = false;
                    }

                    if (distributivoDetalle != null && lineaCorrecta) {
                        distributivoDetalle.setFechaInicio(date);
                    }

                    //Grupo Ocupacional
                    if (!validarValorColumna(linea[5], distributivoHelper.getDenominacionesPuestosValidacion(),
                            getBundle("ec.gob.mrl.smp.administracion.puestos.masivo.error.grupoOcupacion", null), false, sbErrores)) {
                        lineaCorrecta = false;
                    }
                    if (distributivoDetalle != null && lineaCorrecta) {
                        distributivoDetalle.setDenominacionPuesto(distributivoHelper.getDenominacionesPuestosValidacion().get(linea[5]));
                        distributivoDetalle.setIdDenominacionPuesto(distributivoDetalle.getDenominacionPuesto().getId());
                    }

                    //Ubicaciones Geograficas
                    if (!validarValorColumna(linea[6], distributivoHelper.getUbicacionesGeograficasValidacion(),
                            getBundle("ec.gob.mrl.smp.administracion.puestos.masivo.error.ubicacionGeografica", null), true, sbErrores)) {
                        lineaCorrecta = false;
                    }
                    if (distributivoDetalle != null && lineaCorrecta) {
                        distributivoDetalle.setUbicacionGeografica(distributivoHelper.getUbicacionesGeograficasValidacion().get(linea[6]));
                        distributivoDetalle.setIdUbicacionGeografica(distributivoDetalle.getUbicacionGeografica().getId());
                    }

                }

                if (!lineaCorrecta) {
                    csvCorrecto = false;
                    errores.add(sbErrores.toString());
                }

                if (distributivo != null) {
                    distributivo.getDistributivoDetalles().add(distributivoDetalle);
                    mapa.put(linea[0] + ";" + linea[1], distributivo);
                }
            }

            for (Map.Entry<String, Distributivo> entry : mapa.entrySet()) {
                listaDistributivosGenerados.add(entry.getValue());
            }

        } catch (Exception e) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, e);
        }

        limpiarValidadores();

        return csvCorrecto;
    }

    /**
     * Realiza la generacion de puestos
     *
     * @return
     */
    public String generarPuestos() {
        try {
            distributivoServicio.generarPuestosMasivo(distributivoHelper.getListaDistributivosGenerados(), obtenerUsuarioConectado());
            ejecutarComandoPrimefaces("confirmacionCargaMasivaPuestosDlg.hide(); cargaMasivaPuestosDlg.hide();");
            mostrarMensajeEnPantalla("Puestos generados satisfactoriamente", FacesMessage.SEVERITY_INFO);
        } catch (ServicioException ex) {
            Logger.getLogger(DistributivoControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.puestos.masivo.guardar.error", FacesMessage.SEVERITY_INFO);
        }
        return null;
    }

    /**
     * Verifica que el valor exista en el catalogo
     *
     * @param valorColumna
     * @param validador
     * @param label Identifica la columna donde se detecta el error
     * @param requerido
     * @param errores
     * @return
     */
    private boolean validarValorColumna(final String valorColumna, final Map validador, final String label, final boolean requerido, final StringBuilder errores) {
        boolean columnaCorrecta = true;
        if (valorColumna == null || valorColumna.trim().isEmpty()) {
            if (requerido) {
                if (!errores.toString().trim().endsWith(":")) {
                    errores.append(", ");
                }
                errores.append(label).append(" es requerido");
                columnaCorrecta = false;
            }
        } else if (!validador.containsKey(valorColumna)) {
            if (!errores.toString().trim().endsWith(":")) {
                errores.append(", ");
            }
            errores.append(label).append(" [").append(valorColumna).append("] no existe.");
            columnaCorrecta = false;
        }
        return columnaCorrecta;
    }

    /**
     * Parsea fechas
     *
     * @param text
     * @return
     * @throws Exception
     */
    private Date parseDate(final String text) throws Exception {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        try {
            if (!text.matches("\\d{2}/\\d{2}/\\d{4}")) {
                throw new Exception("Formato fecha no valido");
            }
            String[] partesFecha = text.split("/");
            Calendar c = new GregorianCalendar();
            c.setLenient(false);
            c.set(Integer.parseInt(partesFecha[2]), Integer.parseInt(partesFecha[1]) - 1, Integer.parseInt(partesFecha[0]));
            return c.getTime();
            //return distributivoHelper.getSdf().parse(text);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * @return the distributivoHelper
     */
    public DistributivoHelper getDistributivoHelper() {
        return distributivoHelper;
    }

    /**
     * @param distributivoHelper the distributivoHelper to set
     */
    public void setDistributivoHelper(DistributivoHelper distributivoHelper) {
        this.distributivoHelper = distributivoHelper;
    }

    /**
     * Busca la certificación de la unidad presupuestaria correspondiente
     *
     * @param puesto
     * @return
     */
    public String buscarCertificacionPresupuestaria(DistributivoDetalle puesto) {
        return null;
    }
}
