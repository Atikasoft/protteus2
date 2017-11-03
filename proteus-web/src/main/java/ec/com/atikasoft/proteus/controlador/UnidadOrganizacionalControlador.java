/*
 *  UbicacionGeograficaControlador.java
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
 *  20/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_GENERICO;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.UnidadOrganizacionalHelper;
import ec.com.atikasoft.proteus.converter.RelojConverter;
import ec.com.atikasoft.proteus.enums.GrupoPresupuestarioEnum;
import ec.com.atikasoft.proteus.modelo.Reloj;
import ec.com.atikasoft.proteus.modelo.RelojUnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.AsistenciaDePersonalServicio;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "unidadOrganizacionalBean")
@ViewScoped
public class UnidadOrganizacionalControlador extends CatalogoControlador {

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    
    /**
     * Servicio de administracion.
     */
    @EJB
    private AsistenciaDePersonalServicio asistenciaPersonalServicio;

    Set<Long> todos;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{unidadOrganizacionalHelper}")
    private UnidadOrganizacionalHelper unidadOrganizacionalHelper;

    /**
     * Constructor por defecto.
     */
    public UnidadOrganizacionalControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        llenarUnidadOrganizacional();
        getUnidadOrganizacionalHelper().setUnidadOrganizacional(new UnidadOrganizacional());
        getUnidadOrganizacionalHelper().getUnidadOrganizacional()
                .setRelojesUnidadesOrganizacionales(new ArrayList<RelojUnidadOrganizacional>());
        getUnidadOrganizacionalHelper().setUo(null);
        getUnidadOrganizacionalHelper().setUoSuperior(null);
        unidadOrganizacionalHelper.setNodeSeleccionado(0);
        llenarCombroGruposPresupuestarios();
        recuperarRelojes();
    }

    /**
     * Crear lista de grupos presupiestarios.
     */
    private void llenarCombroGruposPresupuestarios() {
        unidadOrganizacionalHelper.getListaGruposPresupuestarios().clear();
        unidadOrganizacionalHelper.getListaGruposPresupuestarios().add(new SelectItem(null, SELECCIONE));
        for (GrupoPresupuestarioEnum en : GrupoPresupuestarioEnum.values()) {
            unidadOrganizacionalHelper.getListaGruposPresupuestarios().add(new SelectItem(en.getCodigo(), en.
                    getDescripcion()));
        }
    }

    /**
     * Llena la lista de opciones de relojes a escoger
     */
    private void recuperarRelojes() {
        try {
            unidadOrganizacionalHelper.getRelojesVigentes().clear();
            unidadOrganizacionalHelper.getRelojesVigentes().addAll(asistenciaPersonalServicio.listarRelojesVigentes());
            ponerAtributoSession(RelojConverter.CLAVE_SESSION, unidadOrganizacionalHelper.getRelojesVigentes());
        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
    }

    /**
     * llena ubiacion geografica.
     *
     * @return
     */
    public String llenarUnidadOrganizacional() {
        try {
            TreeNode nodoPrincipal;
            TreeNode nodoPadre, nodoHijo;
            /*
             * cargar lista de registros padre iniciales
             */
            getUnidadOrganizacionalHelper().getListaUnidadOrganizacional().clear();
            getUnidadOrganizacionalHelper().getListaUnidadOrganizacional().
                    addAll(admServicio.listarTodosUnidadOrganizacional());
            /*
             * carga el primer registro (nodo principal)
             */

            nodoPrincipal = new DefaultTreeNode(unidadOrganizacionalHelper.getListaUnidadOrganizacional().get(0), null);
            getUnidadOrganizacionalHelper().setRoot(nodoPrincipal);
            //unidadOrganizacionalHelper.getListaUnidadOrganizacional().remove(0);
            /*
             * cargar los primeros nodos
             */
            nodoPadre = nodoPrincipal;

            for (UnidadOrganizacional un : unidadOrganizacionalHelper.getListaUnidadOrganizacional()) {
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
            // notificarError(e);
            error(getClass().getName(), ERROR_GENERICO, e);
        }
        return null;
    }

    public void obtenerHijos(UnidadOrganizacional registroPadre, TreeNode nodoPadre) {
        /*
         * buscar hijos del padre
         */
        try {
//            unidadOrganizacionalHelper.setListaUnidadOrganizacionalIdPadre(new ArrayList<UnidadOrganizacional>());
//            unidadOrganizacionalHelper.getListaUnidadOrganizacionalIdPadre().addAll(admServicio.listarTodosUnidadOrganizacionalId(registroPadre.getIdUnidadOrganizacional()));            
//            for (UnidadOrganizacional unidad : unidadOrganizacionalHelper.getListaUnidadOrganizacionalIdPadre()) {
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
    public void onNodeSelect() {
        UnidadOrganizacional un = (UnidadOrganizacional) getUnidadOrganizacionalHelper().getSelectedNode().getData();
        getUnidadOrganizacionalHelper().setUo(un);
        getUnidadOrganizacionalHelper().setUoSuperior(un.getUnidadOrganizacional());
        unidadOrganizacionalHelper.setNodeSeleccionado(1);
        actualizarComponente("frmPrincipal");
    }

    @Override
    public String guardar() {
        try {
            if (unidadOrganizacionalHelper.getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                    return null;
                } else {
                    getUnidadOrganizacionalHelper().getUnidadOrganizacional().setFechaCreacion(new Date());
                    getUnidadOrganizacionalHelper().getUnidadOrganizacional().setUsuarioCreacion(obtenerUsuario());
                    getUnidadOrganizacionalHelper().getUnidadOrganizacional().setDesconcentrado(Boolean.FALSE);
                    getUnidadOrganizacionalHelper().getUnidadOrganizacional().setVigente(Boolean.TRUE);

                    prepararListaRelojesUnidadesOrganizacionalesParaGuardar();

                    admServicio.guardarUnidadOrganizacional(getUnidadOrganizacionalHelper().getUnidadOrganizacional());
                    ejecutarComandoPrimefaces("dlgUnidadOrganizacional.hide()");
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }

            } else {
                getUnidadOrganizacionalHelper().getUnidadOrganizacional().setFechaActualizacion(new Date());
                getUnidadOrganizacionalHelper().getUnidadOrganizacional().setUsuarioActualizacion(obtenerUsuario());

                prepararListaRelojesUnidadesOrganizacionalesParaGuardar();

                admServicio.actualizarUnidadOrganizacional(
                        getUnidadOrganizacionalHelper().getUnidadOrganizacional());
                ejecutarComandoPrimefaces("dlgUnidadOrganizacional.hide()");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
            unidadOrganizacionalHelper.getSeleccionRelojes().getSource().clear();
            unidadOrganizacionalHelper.getSeleccionRelojes().getTarget().clear();
            getUnidadOrganizacionalHelper().setEsNuevo(Boolean.FALSE);
            llenarUnidadOrganizacional();
            unidadOrganizacionalHelper.setNodeSeleccionado(0);

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * prepara la lista de relojesunidadesorganizacionales para ser guardada en base de datos
     */
    private void prepararListaRelojesUnidadesOrganizacionalesParaGuardar() {
        unidadOrganizacionalHelper.getUnidadOrganizacional().getRelojesUnidadesOrganizacionales().clear();
        for (Reloj r : unidadOrganizacionalHelper.getSeleccionRelojes().getTarget()) {
            RelojUnidadOrganizacional ruo = null;
            if (unidadOrganizacionalHelper.getEsNuevo()) {
                ruo = new RelojUnidadOrganizacional(
                        r, unidadOrganizacionalHelper.getUnidadOrganizacional());
                ruo.setFechaCreacion(unidadOrganizacionalHelper.getUnidadOrganizacional().getFechaCreacion());
                ruo.setUsuarioCreacion(unidadOrganizacionalHelper.getUnidadOrganizacional().getUsuarioCreacion());
                ruo.setVigente(Boolean.TRUE);
            } else {
                boolean encontrado = false;
                for (RelojUnidadOrganizacional relUo
                        : unidadOrganizacionalHelper.getRelojesUnidadesOrganizaccionesActuales()) {
                    if (relUo.getReloj().getId().equals(r.getId())) {
                        encontrado = true;
                        ruo = relUo;
                        iniciarDatosEntidad(ruo, Boolean.FALSE);
                        ruo.setFechaActualizacion(
                                unidadOrganizacionalHelper.getUnidadOrganizacional().getFechaActualizacion());
                        break;
                    }
                }
                if (!encontrado) {
                    ruo = new RelojUnidadOrganizacional(
                            r, unidadOrganizacionalHelper.getUnidadOrganizacional());
                    ruo.setFechaCreacion(
                            unidadOrganizacionalHelper.getUnidadOrganizacional().getFechaActualizacion());
                    ruo.setUsuarioCreacion(
                            unidadOrganizacionalHelper.getUnidadOrganizacional().getUsuarioActualizacion());
                    ruo.setVigente(Boolean.TRUE);
                }
            }
            
            unidadOrganizacionalHelper.getUnidadOrganizacional().getRelojesUnidadesOrganizacionales().add(ruo);
        }
    }

    /**
     * metodo para validar si ya existe el nemónico.
     *
     * @return haynemonico Boolean.
     */
    public Boolean validarNemonico() {
        Boolean hayNemonico = true;
        try {
            unidadOrganizacionalHelper.getListaUnidadOrganizacional().clear();
            unidadOrganizacionalHelper.setListaUnidadOrganizacional(admServicio.listarUnidadOrganizacionalPorNemonico(
                    unidadOrganizacionalHelper.getUnidadOrganizacional().getCodigo()));
            if (unidadOrganizacionalHelper.getListaUnidadOrganizacional().isEmpty()) {
                hayNemonico = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nemonico", ex);
        }
        return hayNemonico;
    }

    /**
     * Inicia la edicion del registro
     * @return 
     */
    @Override
    public String iniciarEdicion() {
        try {
            onNodeSelect();
            Object cloneBean = BeanUtils.cloneBean(getUnidadOrganizacionalHelper().getUo());
            UnidadOrganizacional d = (UnidadOrganizacional) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            getUnidadOrganizacionalHelper().setUnidadOrganizacional(d);
            getUnidadOrganizacionalHelper().setEsNuevo(Boolean.FALSE);
            configurarListasRelojes();
            actualizarComponente("dlgUnidadOrganizacional");
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", e);
        }
        return null;
    }

    @Override
    public String iniciarNuevo() {
        try {
            onNodeSelect();
            getUnidadOrganizacionalHelper().setEsNuevo(Boolean.TRUE);
            getUnidadOrganizacionalHelper().setUnidadOrganizacional(new UnidadOrganizacional());
            getUnidadOrganizacionalHelper().getUnidadOrganizacional()
                    .setRelojesUnidadesOrganizacionales(new ArrayList<RelojUnidadOrganizacional>());
            iniciarDatosEntidad(getUnidadOrganizacionalHelper().getUnidadOrganizacional(), Boolean.TRUE);
            getUnidadOrganizacionalHelper().setUoSuperior(getUnidadOrganizacionalHelper().getUo());
            getUnidadOrganizacionalHelper().getUnidadOrganizacional()
                    .setCodigo(getUnidadOrganizacionalHelper().getUo().getCodigo());
            getUnidadOrganizacionalHelper().getUnidadOrganizacional()
                    .setIdUnidadOrganizacional(getUnidadOrganizacionalHelper().getUo().getId());
            configurarListasRelojes();
            actualizarComponente("dlgUnidadOrganizacional");

        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
        return null;
    }

    @Override
    public String borrar() {
        try {
            getUnidadOrganizacionalHelper().getListaUnidadOrganizacional().clear();
            onNodeSelect();
            getUnidadOrganizacionalHelper().getListaUnidadOrganizacional().
                    addAll(admServicio.listarTodosUnidadOrganizacionalId(
                            getUnidadOrganizacionalHelper().getUo().getId()));
            if (getUnidadOrganizacionalHelper().getListaUnidadOrganizacional().isEmpty()) {
                admServicio.eliminarUnidadOrganizacional(getUnidadOrganizacionalHelper().getUo());
                getUnidadOrganizacionalHelper().getListaUnidadOrganizacional().
                        remove(getUnidadOrganizacionalHelper().getUo());
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
                llenarUnidadOrganizacional();
            } else {
                mostrarMensajeEnPantalla("NO SE PUEDE ELIMINAR POR QUE TIENE HIJOS", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    /**
     * Muestra el diálogo para la asignación de relojes a la unidad organizacional
     */
    public void abrirDialogoAsignacionRelojes() {
        ejecutarComandoPrimefaces("dlgAsignacionRelojes.show();");
    }

    /**
     * Llena las listas de relojes disponibles y asignables
     */
    private void configurarListasRelojes() {
        unidadOrganizacionalHelper.getRelojesAsignados().clear();
        unidadOrganizacionalHelper.getRelojesAsignables().clear();
        boolean encontrado = false;
        for (Reloj r : unidadOrganizacionalHelper.getRelojesVigentes()) {
            for (RelojUnidadOrganizacional ruo
                    : unidadOrganizacionalHelper.getUnidadOrganizacional().getRelojesUnidadesOrganizacionales()) {
                if (ruo.getVigente() && ruo.getReloj().getId().equals(r.getId())) {
                    encontrado = true;
                    unidadOrganizacionalHelper.getRelojesAsignados().add(r);
                    break;
                }
            }
            if (!encontrado) {
                unidadOrganizacionalHelper.getRelojesAsignables().add(r);
            }
        }
        unidadOrganizacionalHelper.getSeleccionRelojes().setSource(unidadOrganizacionalHelper.getRelojesAsignables());
        unidadOrganizacionalHelper.getSeleccionRelojes().setTarget(unidadOrganizacionalHelper.getRelojesAsignados());
    }

    /**
     * Guarda en memoria las asignaciones de relojes realizadas
     */
    public void guardarAsignacionRelojes() {
        mostrarMensajeEnPantalla("Asignación de relojes realizada satisfactoriamente", FacesMessage.SEVERITY_INFO);
        ejecutarComandoPrimefaces("dlgAsignacionRelojes.hide();");
    }

    /**
     *
     * @return
     */
    @Override
    public String buscar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the unidadOrganizacionalHelper
     */
    public UnidadOrganizacionalHelper getUnidadOrganizacionalHelper() {
        return unidadOrganizacionalHelper;
    }

    /**
     * @param unidadOrganizacionalHelper the unidadOrganizacionalHelper to set
     */
    public void setUnidadOrganizacionalHelper(final UnidadOrganizacionalHelper unidadOrganizacionalHelper) {
        this.unidadOrganizacionalHelper = unidadOrganizacionalHelper;
    }
}
