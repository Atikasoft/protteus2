/*
 *  AnticipoAprobacionControlador.java
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
 *  04/01/2014
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_GENERICO;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.AnticipoAprobacionHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.enums.EstadoAnticipoEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.AnticipoPlanPago;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.AnticipoServicio;
import ec.com.atikasoft.proteus.temporizadores.VacacionTemporizador;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.AnticipoVO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * AnticipoAprobacionControlador
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "anticipoAprobacionBean")
@ViewScoped
public class AnticipoAprobacionControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(AnticipoAprobacionControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/anticipo/aprobacion_anticipo.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/anticipo/lista_aprobacion_anticipo.jsf";

    /**
     * Servicio de anticipo.
     */
    @EJB
    private AnticipoServicio anticipoServicio;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Dao de parametros institucionales.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{anticipoAprobacionHelper}")
    private AnticipoAprobacionHelper anticipoAprobacionHelper;

    /**
     * Constructor por defecto.
     */
    public AnticipoAprobacionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(anticipoAprobacionHelper);
        setAnticipoAprobacionHelper(anticipoAprobacionHelper);
        iniciarControles();
    }

    private void iniciarControles() {
        if (anticipoAprobacionHelper.getUnidadOrganizacional() == null) {
            anticipoAprobacionHelper.setUnidadOrganizacional(new UnidadOrganizacional());
        }

        anticipoAprobacionHelper.setEjercicioFiscal(null);
        anticipoAprobacionHelper.setEstado(null);
        anticipoAprobacionHelper.getListaAnticipos().clear();
        iniciarComboEstadoAnticipo();
        iniciarComboInstitucionEjercicioFiscal();
        llenarUnidadOrganizacional();
        anticipoAprobacionHelper.setEsRRHH(esUnidadRRHH());
        anticipoAprobacionHelper.getUnidadOrganizacional().setId(
                obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
        anticipoAprobacionHelper.getUnidadOrganizacional().setNombre(
                obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getRuta());

    }

    @Override
    public String iniciarEdicion() {
        try {
            anticipoAprobacionHelper.setEsNuevo(Boolean.FALSE);
            anticipoAprobacionHelper.setGuardado(Boolean.FALSE);
            iniciarDatosEntidad(anticipoAprobacionHelper.getAnticipoVO().getAnticipo(), Boolean.FALSE);
            anticipoAprobacionHelper.setFechaIngreso(obtenerUsuarioConectado().getFechaIngreso());
            anticipoAprobacionHelper.setObservacion(null);
            anticipoAprobacionHelper.getAnticipoVO().getAnticipo().setAprobador(obtenerUsuarioConectado().getServidor());
            buscarDetalles();
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        return FORMULARIO_ENTIDAD;
    }

    /**
     *
     * @return
     */
    public String validarCamposRequeridos() {
        if (anticipoAprobacionHelper.getAnticipoVO().getAnticipo().getEstado() == null) {
            mostrarMensajeEnPantalla("El campo estado es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (anticipoAprobacionHelper.getObservacion() == null
                || anticipoAprobacionHelper.getObservacion().isEmpty()) {
            mostrarMensajeEnPantalla("El campo observación es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        ejecutarComandoPrimefaces("confirmAprobar.show();");
        return null;
    }

    @Override
    public String guardar() {
        try {
            anticipoAprobacionHelper.getAnticipoVO().getAnticipo().setObservaciones(anticipoAprobacionHelper.getObservacion());
            if (anticipoAprobacionHelper.getAnticipoVO().getAnticipo().getEstado().equals(EstadoAnticipoEnum.APROBADO.getCodigo())) {
                mostrarMensajeEnPantalla(SOLICITUD_ANTICIPO_APROBADA, FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla(SOLICITUD_ANTICIPO_NEGADA, FacesMessage.SEVERITY_INFO);
            }
            anticipoAprobacionHelper.getAnticipoVO().getAnticipo().setObservaciones(anticipoAprobacionHelper.getObservacion());
            anticipoServicio.actualizarAnticipo(
                    anticipoAprobacionHelper.getAnticipoVO().getAnticipo());
            anticipoAprobacionHelper.setGuardado(Boolean.TRUE);
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Aprueba masivamente todos los registros de la lista.
     *
     * @param aprobar si se aprueba masivamente, caso contrario se devuelve al
     * remitente.
     * @return
     */
    public String aprobarMasivamente(Boolean aprobar) {
        int count = 0;
        try {
            if (anticipoAprobacionHelper.getObservacion() != null && !anticipoAprobacionHelper.getObservacion().isEmpty()) {
                for (AnticipoVO vo : anticipoAprobacionHelper.getListaAnticipos()) {

                    if (vo.getAnticipo().getEstado().equals(EstadoAnticipoEnum.REGISTRADO.getCodigo())) {
                        if (aprobar) {
                            vo.getAnticipo().setEstado(
                                    EstadoAnticipoEnum.APROBADO.getCodigo());
                        } else {
                            vo.getAnticipo().setEstado(
                                    EstadoAnticipoEnum.RECHAZADO.getCodigo());
                        }
                        count++;
                    }
                    vo.getAnticipo().setAprobador(obtenerUsuarioConectado().getServidor());
                    iniciarDatosEntidad(vo.getAnticipo(), Boolean.FALSE);
                    anticipoServicio.actualizarAnticipo(vo.getAnticipo());
                }
                String mensaje = UtilCadena.concatenar(REGISTRO_GUARDADO_VARIOS, " : TOTAL REGISTROS :", count);
                mostrarMensajeEnPantalla(mensaje, FacesMessage.SEVERITY_INFO);
                if (!aprobar) {
                    ejecutarComandoPrimefaces("confirmationDesaprobar.hide()");
                } else {
                    ejecutarComandoPrimefaces("confirmationAprobar.hide()");

                }
            } else {
                mostrarMensajeEnPantalla("La observación es un campo requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }

        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar masivamente", e);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Aprueba de manera individual el registro.
     *
     * @return
     */
    public String aprobar() {
        if (anticipoAprobacionHelper.getObservacion() != null && !anticipoAprobacionHelper.getObservacion().isEmpty()) {
            anticipoAprobacionHelper.getAnticipoVO().getAnticipo().setEstado(
                    EstadoAnticipoEnum.APROBADO.getCodigo());
            return guardar();
        } else {
            mostrarMensajeEnPantalla("La observación es un campo requerido", FacesMessage.SEVERITY_ERROR);
            return FORMULARIO_ENTIDAD;
        }
    }

    public String noAprobar() {

        if (anticipoAprobacionHelper.getObservacion() != null && !anticipoAprobacionHelper.getObservacion().isEmpty()) {
            anticipoAprobacionHelper.getAnticipoVO().getAnticipo().setEstado(
                    EstadoAnticipoEnum.RECHAZADO.getCodigo());
            return guardar();
        } else {
            mostrarMensajeEnPantalla("La observación es un campo requerido", FacesMessage.SEVERITY_ERROR);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        return null;
    }

    @Override
    /**
     * Busca todas las Unidades Organizacionales que tienen anticipos en estado
     * registrado.
     */
    public String buscar() {
        try {

            List<AnticipoVO> lista;
            lista = anticipoServicio.listarListaAprobacionAnticipos(
                    anticipoAprobacionHelper.getEjercicioFiscal(),
                    anticipoAprobacionHelper.getUnidadOrganizacional() != null
                            ? anticipoAprobacionHelper.getUnidadOrganizacional().getId() : null,
                    anticipoAprobacionHelper.getEstado(),
                    obtenerUsuarioConectado().getServidor().getId());
            anticipoAprobacionHelper.setListaAnticipos(lista);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda por aprobar", ex);
        }
        return null;
    }

    private boolean esUnidadRRHH() {
        boolean esUnidadRRHH = false;
        try {
            String parametro = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().getInstitucion().getId(),
                    ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo()).getValorTexto();
            esUnidadRRHH = esRRHH(parametro);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionTemporizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return esUnidadRRHH;
    }

    /**
     * Setea los detalles vigentes de la instancia de planificacion del usuario
     * conectado.
     */
    public void buscarDetalles() {
        if (anticipoAprobacionHelper.getListaAnticipoPlanPago() == null) {
            anticipoAprobacionHelper.setListaAnticipoPlanPago(
                    new ArrayList<AnticipoPlanPago>());
        }
        anticipoAprobacionHelper.getListaAnticipoPlanPago().clear();
        for (AnticipoPlanPago det : anticipoAprobacionHelper.getAnticipoVO().getAnticipo().getListaAnticipoPlaPlago()) {
            if (det.getVigente()) {
                anticipoAprobacionHelper.getListaAnticipoPlanPago().add(det);
            }
        }
    }

    private void llenarListaInicialUnidadesOrganizacionales() {
        try {
            if (anticipoAprobacionHelper.getListaUnidadesOrganizacionales() == null) {
                anticipoAprobacionHelper.setListaUnidadesOrganizacionales(new ArrayList<UnidadOrganizacional>());
            } else {
                anticipoAprobacionHelper.getListaUnidadesOrganizacionales().clear();
            }
            List<UnidadOrganizacional> listarInstitucionesHijas
                    = admServicio.listarTodosUnidadOrganizacional();

            anticipoAprobacionHelper.getListaUnidadesOrganizacionales().addAll(listarInstitucionesHijas);
        } catch (ServicioException e) {
            error(getClass().getName(), "Error al consultar las unidades organizacionales.", e);
        }
    }

    /**
     * Permite armar el arbol para presentar en la página.
     *
     * @return
     */
    public String llenarUnidadOrganizacional() {
        try {
            TreeNode nodoPrincipal;
            TreeNode nodoPadre, nodoHijo;
            /*
             * carga el primer registro (nodo principal)
             */
            llenarListaInicialUnidadesOrganizacionales();
            nodoPrincipal = new DefaultTreeNode(anticipoAprobacionHelper.getListaUnidadesOrganizacionales().get(0), null);
            anticipoAprobacionHelper.setRootUnidadOrganizacional(nodoPrincipal);
            /*
             * cargar los primeros nodos
             */
            nodoPadre = nodoPrincipal;

            for (UnidadOrganizacional un : anticipoAprobacionHelper.getListaUnidadesOrganizacionales()) {
                if (un.getVigente()) {
                    nodoPadre = new DefaultTreeNode(un, nodoPrincipal);
                    /*
                     * cargar los hijos
                     */
                    if (un.getId() != null) {
                        obtenerHijos(un, nodoPadre);
                    }
                }
            }
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
     */
    public void asignarUnidadOrganizacionalSeleccionada() {
        UnidadOrganizacional un = (UnidadOrganizacional) anticipoAprobacionHelper.getUnidadSeleccionada().getData();
        anticipoAprobacionHelper.setUnidadOrganizacional(un);
        ejecutarComandoPrimefaces("dlgAgregar.hide()");
    }

    /**
     * Permite obtener Ejercicios fiscales vigentes y no vigentes.
     */
    private void iniciarComboInstitucionEjercicioFiscal() {
        List<InstitucionEjercicioFiscal> lista;
        try {
            lista = admServicio.listarTodosInstitucionesEjercicioFiscalPorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId());
            anticipoAprobacionHelper.getListaOpcionesEjercicioFiscal().clear();
            iniciarCombos(anticipoAprobacionHelper.getListaOpcionesEjercicioFiscal());
            for (InstitucionEjercicioFiscal c : lista) {
                if (c.getVigente() && c.getEjercicioFiscal().getVigente()) {
                    anticipoAprobacionHelper.getListaOpcionesEjercicioFiscal().add(
                            new SelectItem(c.getId(), c.getEjercicioFiscal().getNombre()));
                }
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda ejercicios fiscales", ex);
        }

    }

    /**
     * Este metodo llena las opciones para el combo de Estados de la anticipos.
     */
    private void iniciarComboEstadoAnticipo() {
        anticipoAprobacionHelper.getListaOpcionesEstado().clear();
        anticipoAprobacionHelper.getListaOpcionesEstadoList().clear();
        iniciarCombos(anticipoAprobacionHelper.getListaOpcionesEstado());
        iniciarCombosTodos(anticipoAprobacionHelper.getListaOpcionesEstadoList());
        for (EstadoAnticipoEnum t : EstadoAnticipoEnum.values()) {
            if (!t.getCodigo().equals(EstadoAnticipoEnum.REGISTRADO.getCodigo())) {
                anticipoAprobacionHelper.getListaOpcionesEstado().add(
                        new SelectItem(t.getCodigo(), t.getDescripcion()));
                anticipoAprobacionHelper.getListaOpcionesEstadoList().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
            } else {
                anticipoAprobacionHelper.getListaOpcionesEstadoList().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
            }
        }
    }

    /**
     * Permite regresar al listado.
     *
     * @return
     */
    public String irListaAprobacion() {
        anticipoAprobacionHelper.getListaAnticipos().clear();
        anticipoAprobacionHelper.setEjercicioFiscal(null);
        anticipoAprobacionHelper.setUnidadOrganizacional(null);
        anticipoAprobacionHelper.setEstado(null);
        return LISTA_ENTIDAD;
    }

    /**
     * Permite salir.
     *
     * @return
     */
    public String salir() {
        anticipoAprobacionHelper.getListaAnticipos().clear();
        anticipoAprobacionHelper.setEjercicioFiscal(null);
        anticipoAprobacionHelper.setUnidadOrganizacional(null);
        anticipoAprobacionHelper.setEstado(null);
        return PAGINA_PRINCIPAL;
    }

    /**
     * Permite regresar a la lista.
     *
     * @return
     */
    public String regresar() {
        anticipoAprobacionHelper.getListaAnticipos().clear();
        anticipoAprobacionHelper.setEjercicioFiscal(null);
        anticipoAprobacionHelper.setUnidadOrganizacional(null);
        anticipoAprobacionHelper.setEstado(null);
        return LISTA_ENTIDAD;
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de
     * documento parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDocumentoEnum.obtenerNombre(codigo);
    }

    /**
     * Permite obtener el nombre del mes
     *
     * @param mes
     * @return
     */
    public final String obtenerNombreMes(int mes) {
        return UtilCadena.concatenar(mes, " - ",
                UtilFechas.obtenerNombreMes(mes));
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del estado del
     * registro
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoAnticipo(final String codigo) {
        return EstadoAnticipoEnum.obtenerDescripcion(codigo);
    }

    /**
     * @return the anticipoAprobacionHelper
     */
    public AnticipoAprobacionHelper getAnticipoAprobacionHelper() {
        return anticipoAprobacionHelper;
    }

    /**
     * @param anticipoAprobacionHelper the anticipoAprobacionHelper to set
     */
    public void setAnticipoAprobacionHelper(AnticipoAprobacionHelper anticipoAprobacionHelper) {
        this.anticipoAprobacionHelper = anticipoAprobacionHelper;
    }
}
