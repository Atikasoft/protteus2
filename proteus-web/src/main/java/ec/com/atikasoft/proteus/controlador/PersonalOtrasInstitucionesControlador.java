/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_GENERICO;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.PersonalOtrasInstitucionesHelper;
import ec.com.atikasoft.proteus.enums.TipoIdentificacionEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.PersonalOtrasInstituciones;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.PersonalOtrasInstitucionesServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.vo.PersonaVO;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Controlador para la administración de PersonalOtrasInstituciones.
 *
 */
@ManagedBean(name = "personalOtrasInstitucionesBean")
@ViewScoped
public class PersonalOtrasInstitucionesControlador extends CatalogoControlador {

    /**
     * Regla de Navegación.
     */
    public static final String FORMULARIO_ENTIDAD
            = "/pages/administracion/personal_otras_instituciones/personal_otras_instituciones.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD
            = "/pages/administracion/personal_otras_instituciones/lista_personal_otras_instituciones.jsf";

    /**
     * Servicio de Administración.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Servicio de PersonalOtrasInstituciones.
     */
    @EJB
    private PersonalOtrasInstitucionesServicio personalOtrasInstitucionesServicio;

    /**
     *
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     * Helper de Clase.
     */
    @ManagedProperty("#{personalOtrasInstitucionesHelper}")
    private PersonalOtrasInstitucionesHelper personalOtrasInstitucionesHelper;

    @Override
    @PostConstruct
    public void init() {
        personalOtrasInstitucionesHelper.setExisteSistemaPersonas(Boolean.FALSE);

    }

    /**
     * guarda la personalOtrasInstituciones (creación y edición).
     *
     * @return
     */
    @Override
    public String guardar() {
        try {
            if (getPersonalOtrasInstitucionesHelper().getEsNuevo()) {
                if (validarExistencia()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else if (validarActivoEnDistributivo()) {
                    mostrarMensajeEnPantalla("El servidor se encuentra activo en el distributivo actual.",
                            FacesMessage.SEVERITY_WARN);
                } /*else if (personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().
                 getFechaSalidaInstitucionOrigen().getTime()
                 > (new Date()).getTime()) {
                 mostrarMensajeEnPantalla("Fecha de salida debe ser menor o igual que la fecha actual.",
                 FacesMessage.SEVERITY_WARN);
                 }*/ else if (personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getFechaInicio().getTime()
                        < personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().
                        getFechaSalidaInstitucionOrigen().getTime()) {
                    mostrarMensajeEnPantalla("Fecha inicio debe ser mayor a la Fecha de salida.",
                            FacesMessage.SEVERITY_WARN);
                } else if (personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getFechaFin().getTime()
                        < personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getFechaInicio().getTime()) {
                    mostrarMensajeEnPantalla("Fecha fin debe ser mayor a la Fecha inicio.", FacesMessage.SEVERITY_WARN);
                } else {
                    personalOtrasInstitucionesServicio.guardarPersonalOtrasInstituciones(
                            getPersonalOtrasInstitucionesHelper().getPersonalOtrasInstituciones());

                    /////GENERAR ACCION DE REGISTRO DE PERSONAL
                    personalOtrasInstitucionesServicio.generarPdfAccionPersonalOtrasInstitucionesRegistro(
                            getPersonalOtrasInstitucionesHelper().getPersonalOtrasInstituciones(),
                            obtenerUsuarioConectado());

                    personalOtrasInstitucionesHelper.setArchivoPdfAccionRegistro(new DefaultStreamedContent(
                            new ByteArrayInputStream(getPersonalOtrasInstitucionesHelper().getPersonalOtrasInstituciones().
                                    getArchivoAccionIngreso().getArchivo()), "application/pdf",
                            getPersonalOtrasInstitucionesHelper().getPersonalOtrasInstituciones().getArchivoAccionIngreso()
                            .getNombre()));
                    ejecutarComandoPrimefaces("dlgAccionPersonalRegistro.show();");

                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                    actualizarComponente("frmPrincipal:tablaformulario");
                }
            }

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el personalOtrasInstituciones", e);
        }
        ejecutarComandoPrimefaces("confirmRegistro.hide();");
        return null;
    }

    /**
     * Permite Regresar.
     *
     * @return
     */
    public String regresar() {
        try {
            personalOtrasInstitucionesHelper.getListaPersonalOtrasInstituciones().clear();
            personalOtrasInstitucionesHelper.setListaPersonalOtrasInstituciones(
                    personalOtrasInstitucionesServicio.buscarPersonalOtrasInstitucionesPorEstado(
                            personalOtrasInstitucionesHelper.getBusquedaActivos()));
        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }

        return LISTA_ENTIDAD;
    }

    /**
     *
     * @return
     */
    public String confirmarRegistro() {
        ejecutarComandoPrimefaces("confirmRegistro.show();");
        return null;
    }

    /**
     * Acciones necesarias antes de la edicion.
     *
     * @return
     */
    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(getPersonalOtrasInstitucionesHelper().
                    getPersonalOtrasInstitucionesEditDelete());
            PersonalOtrasInstituciones personal = (PersonalOtrasInstituciones) cloneBean;
            iniciarDatosEntidad(personal, Boolean.FALSE);
            getPersonalOtrasInstitucionesHelper().setPersonalOtrasInstituciones(personal);
            getPersonalOtrasInstitucionesHelper().setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Acciones necesarias antes de la creación.
     *
     * @return
     */
    @Override
    public String iniciarNuevo() {
        personalOtrasInstitucionesHelper.setPersonalOtrasInstituciones(new PersonalOtrasInstituciones());
        iniciarDatosEntidad(personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones(), Boolean.TRUE);
        personalOtrasInstitucionesHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Realiza el borrado lógico de la personalOtrasInstituciones.
     *
     * @return
     */
    @Override
    public String borrar() {
        try {
            personalOtrasInstitucionesServicio.eliminarPersonalOtrasInstituciones(getPersonalOtrasInstitucionesHelper()
                    .getPersonalOtrasInstitucionesEditDelete());
            getPersonalOtrasInstitucionesHelper().getListaPersonalOtrasInstituciones()
                    .remove(getPersonalOtrasInstitucionesHelper().getPersonalOtrasInstitucionesEditDelete());
            actualizarComponente("frmListaPersonalOtraInstitucion:tbListaEntidad");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al eliminar el personal", ex);
        }
        return null;
    }

    /**
     * Carga resultados de la busqueda por el estado especificado.
     *
     * @return
     */
    @Override
    public String buscar() {
        try {
            personalOtrasInstitucionesHelper.getListaPersonalOtrasInstituciones().clear();
            personalOtrasInstitucionesHelper.setListaPersonalOtrasInstituciones(
                    personalOtrasInstitucionesServicio.buscarPersonalOtrasInstitucionesPorEstado(
                            personalOtrasInstitucionesHelper.getBusquedaActivos()));
            personalOtrasInstitucionesHelper.setEsNuevo(Boolean.TRUE);

        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * valida que no se repita el nombre.
     *
     * @return
     */
    public Boolean validarNombre() {
        Boolean existe = true;
        try {
            List<PersonalOtrasInstituciones> lista
                    = personalOtrasInstitucionesServicio.buscarPersonalOtrasInstitucionesPorNombre(
                            personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getApellidosNombres());
            if (lista.isEmpty()) {
                existe = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validación del nombre", ex);
        }
        return existe;
    }

    /**
     * @return the personalOtrasInstitucionesHelper.
     */
    public PersonalOtrasInstitucionesHelper getPersonalOtrasInstitucionesHelper() {
        return personalOtrasInstitucionesHelper;
    }

    /**
     * @param personalOtrasInstitucionesHelper the personalOtrasInstitucionesHelper to set.
     */
    public void setPersonalOtrasInstitucionesHelper(PersonalOtrasInstitucionesHelper personalOtrasInstitucionesHelper) {
        this.personalOtrasInstitucionesHelper = personalOtrasInstitucionesHelper;
    }

    /**
     * llena arbol de unidades organizacionales.
     *
     * @return
     */
    public String cargarArbolUnidadOrganizacional() {
        try {
            personalOtrasInstitucionesHelper.getUnidadesOrganizacionales().clear();
            if (personalOtrasInstitucionesHelper.getRootUnidadOrganizacional().getChildCount() == 0) {
                personalOtrasInstitucionesHelper.setUnidadesOrganizacionales(
                        administracionServicio.listarTodosUnidadOrganizacional());
                TreeNode nodoPrincipal;
                nodoPrincipal = new DefaultTreeNode(personalOtrasInstitucionesHelper.getUnidadesOrganizacionales().
                        get(0), null);
                TreeNode nodoPadre = nodoPrincipal;
                personalOtrasInstitucionesHelper.setRootUnidadOrganizacional(nodoPrincipal);
                for (UnidadOrganizacional un : personalOtrasInstitucionesHelper.getUnidadesOrganizacionales()) {
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

    /**
     * Carga recursivamente las unidades organizacionales hijas.
     *
     * @param registroPadre
     * @param nodoPadre
     */
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
     * acción a ejecutar en la selección de nodo.
     *
     * @param event
     */
    public void onNodeSelectUnidad(NodeSelectEvent event) {
        UnidadOrganizacional un = (UnidadOrganizacional) personalOtrasInstitucionesHelper.getUnidadSeleccionada().
                getData();
        personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().setUnidadOrganizacional(un);
    }

    /**
     * Valida la previa existencia de la persona que se desea ingresar.
     *
     * @return
     */
    private boolean validarExistencia() {
        try {
            if (personalOtrasInstitucionesServicio.buscarPersonalOtrasInstitucionesVigente(
                    personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getTipoIdentificacion(),
                    personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getNumeroIdentificacion()) != null) {
                return true;
            }
        } catch (ServicioException ex) {
            Logger.getLogger(PersonalOtrasInstitucionesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Valida la previa existencia de la persona que se desea ingresar
     *
     * @return
     */
    private boolean validarActivoEnDistributivo() {
        try {
            if (personalOtrasInstitucionesServicio.buscarServidor(
                    personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getTipoIdentificacion(),
                    personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getNumeroIdentificacion()) != null) {
                return true;
            }
        } catch (ServicioException ex) {
            Logger.getLogger(PersonalOtrasInstitucionesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Ejecuta el borrado logico de la personalOtrasInstituciones y genera la acción de personal de terminación.
     */
    public String generarAccionPersonalTerminacion() {
        try {
            borrar();

            /////GENERAR ACCION DE TERMINACION DE PERSONAL
            personalOtrasInstitucionesServicio.generarPdfAccionPersonalOtrasInstitucionesTerminacion(
                    getPersonalOtrasInstitucionesHelper().getPersonalOtrasInstitucionesEditDelete(),
                    obtenerUsuarioConectado());

            personalOtrasInstitucionesHelper.setArchivoPdfAccionTerminacion(new DefaultStreamedContent(
                    new ByteArrayInputStream(getPersonalOtrasInstitucionesHelper()
                            .getPersonalOtrasInstitucionesEditDelete().getArchivoAccionSalida().getArchivo()),
                    "application/pdf", getPersonalOtrasInstitucionesHelper().getPersonalOtrasInstitucionesEditDelete()
                    .getArchivoAccionSalida()
                    .getNombre()));
            ejecutarComandoPrimefaces("dlgAccionPersonalTerminacionDescarga.show();");

            iniciarNuevo();
            actualizarComponente("frmPrincipal:tablaformulario");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la terminacion", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Descarga el pdf de la acción de personal de terminación del personal de otra institución seleccionado.
     */
    public void descargarAccionPersonalTerminacion() {
        personalOtrasInstitucionesHelper.setArchivoPdfAccionTerminacion(new DefaultStreamedContent(
                new ByteArrayInputStream(getPersonalOtrasInstitucionesHelper().getPersonalOtrasInstitucionesEditDelete().
                        getArchivoAccionSalida().getArchivo()), "application/pdf",
                getPersonalOtrasInstitucionesHelper().getPersonalOtrasInstitucionesEditDelete().getArchivoAccionSalida()
                .getNombre()));
        ejecutarComandoPrimefaces("dlgAccionPersonalTerminacionDescarga.show();");
    }

    /**
     * Descarga el pdf de la acción de personal de registro del personal de otra institución seleccionado.
     */
    public void descargarAccionPersonalRegistro() {
        personalOtrasInstitucionesHelper.setArchivoPdfAccionRegistro(new DefaultStreamedContent(
                new ByteArrayInputStream(getPersonalOtrasInstitucionesHelper().getPersonalOtrasInstitucionesEditDelete().
                        getArchivoAccionIngreso().getArchivo()), "application/pdf",
                getPersonalOtrasInstitucionesHelper().getPersonalOtrasInstitucionesEditDelete().getArchivoAccionIngreso()
                .getNombre()));
        ejecutarComandoPrimefaces("dlgAccionPersonalRegistroDescarga.show();");
    }

    public Date getFechaActual() {
        return new Date();
    }

    /**
     * Recupera los datos del servidor en el sistema.
     */
    public void buscarServidor() {
        try {
            personalOtrasInstitucionesHelper.setExisteSistemaPersonas(Boolean.FALSE);
            if (personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getTipoIdentificacion() != null
                    && personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getNumeroIdentificacion() != null) {
                // recupera datos del sistema de personas
                PersonaVO vo = servidorServicio.buscarPersona(
                        personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getTipoIdentificacion(),
                        personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getNumeroIdentificacion(),
                        obtenerUsuarioConectado());
                if (vo == null) {
                    // No existe persona
                    // verificar si es cedula
                    if (personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().getTipoIdentificacion().equals(
                            TipoIdentificacionEnum.CEDULA.getCodigo())) {
                        // errror, no existe un ciudadano con ese numero de cedula
                        personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().setNumeroIdentificacion(null);
                        mostrarMensajeEnPantalla("El número de identificación ingresada no existe.", FacesMessage.SEVERITY_ERROR);
                    } else {
                        // error, no existe pasaporte, se registra manualmente
                        personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().setApellidosNombres(null);
                        personalOtrasInstitucionesHelper.setExisteSistemaPersonas(Boolean.FALSE);
                        mostrarMensajeEnPantalla("El número de pasaporte ingresado no existe en el Sistema de "
                                + " Personas, proceda a ingresar el Apellido y Nombre"
                                + " asegurándose que sean correctos.", FacesMessage.SEVERITY_WARN);
                    }
                } else {
                    // Si existe persona
                    personalOtrasInstitucionesHelper.getPersonalOtrasInstituciones().setApellidosNombres(
                            vo.getApellidosNombres());
                    personalOtrasInstitucionesHelper.setExisteSistemaPersonas(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(ReclutamientoControlador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
