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

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.UbicacionGeograficaHelper;
import ec.com.atikasoft.proteus.enums.TipoUbicacionGeograficaEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.UbicacionGeografica;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.logging.Logger;
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
@ManagedBean(name = "ubicacionGeograficaBean")
@ViewScoped
public class UbicacionGeograficaControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(UbicacionGeograficaControlador.class.getCanonicalName());
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{ubicacionGeograficaHelper}")
    private UbicacionGeograficaHelper ubicacionGeograficaHelper;

    @Override
    @PostConstruct
    public void init() {
        llenarUbicacionGeografica();
    }

    /**
     * llena ubiacion geografica.
     */
    public String llenarUbicacionGeografica() {
        try {
            ubicacionGeograficaHelper.getListaUbicacionGeografica().clear();
            ubicacionGeograficaHelper.getListaUbicacionGeografica().
                    addAll(admServicio.listarTodosubicacionGeografica());
            ubicacionGeograficaHelper.setRoot(new DefaultTreeNode("root", null));

            TreeNode nodoPrincipal = new DefaultTreeNode(ubicacionGeograficaHelper.getListaUbicacionGeografica().get(0), ubicacionGeograficaHelper.getRoot());
            ubicacionGeograficaHelper.getListaUbicacionGeografica().remove(0);

            for (UbicacionGeografica ug : ubicacionGeograficaHelper.getListaUbicacionGeografica()) {
                if (ug.getTipo().equals(TipoUbicacionGeograficaEnum.PAIS.getCodigo())) {
                    TreeNode pais = new DefaultTreeNode(ug, nodoPrincipal);
                    for (UbicacionGeografica ugp : ubicacionGeograficaHelper.getListaUbicacionGeografica()) {
                        if (ugp.getTipo().equals(TipoUbicacionGeograficaEnum.PROVINCIA.getCodigo())
                                && ugp.getIdUbicacionGeografica().equals(ug.getId())) {
                            TreeNode provincia = new DefaultTreeNode(ugp, pais);
                            for (UbicacionGeografica ugc : ubicacionGeograficaHelper.getListaUbicacionGeografica()) {
                                if (ugc.getTipo().equals(TipoUbicacionGeograficaEnum.CANTON.getCodigo())
                                        && ugc.getIdUbicacionGeografica().equals(ugp.getId())) {
                                    TreeNode canton = new DefaultTreeNode(ugc, provincia);
                                    for (UbicacionGeografica ugpa : ubicacionGeograficaHelper.getListaUbicacionGeografica()) {
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
        } catch (Exception e) {
            // notificarError(e);
            error(getClass().getName(), ERROR_GENERICO, e);
        }
        return null;
    }

    /**
     * seleccion de nodo.
     *
     * @param event
     */
    public void onNodeSelect() {
        UbicacionGeografica ugN = (UbicacionGeografica) ubicacionGeograficaHelper.getSelectedNode().getData();
        ubicacionGeograficaHelper.setUgN(ugN);
        nivelUbicacionGeografica(ugN);
    }

    public void nivelUbicacionGeografica(UbicacionGeografica ub) {
        if (ub.getTipo() == null) {
            iniciarDatosEntidad(ubicacionGeograficaHelper.getUbicacionGeografica(), Boolean.TRUE);
            ubicacionGeograficaHelper.setNodeSeleccionado(0);
            if (!ubicacionGeograficaHelper.getEsNuevo()) {                
                ejecutarComandoPrimefaces("msgDialog.show()");
            }
            if (ubicacionGeograficaHelper.getEsNuevo()) {                
                ejecutarComandoPrimefaces("dlgUbicacionGeografica.show()");
            }
        } else if (ub.getTipo().equals(TipoUbicacionGeograficaEnum.PAIS.getCodigo())) {
            ubicacionGeograficaHelper.setNodeSeleccionado(1);
            ejecutarComandoPrimefaces("dlgUbicacionGeografica.show()");
        } else if (ub.getTipo().equals(TipoUbicacionGeograficaEnum.PROVINCIA.getCodigo())) {
            ubicacionGeograficaHelper.setNodeSeleccionado(2);
            ejecutarComandoPrimefaces("dlgUbicacionGeografica.show()");
        } else if (ub.getTipo().equals(TipoUbicacionGeograficaEnum.CANTON.getCodigo())) {
            ubicacionGeograficaHelper.setNodeSeleccionado(3);
            ejecutarComandoPrimefaces("dlgUbicacionGeografica.show()");
        } else if (ub.getTipo().equals(TipoUbicacionGeograficaEnum.PARROQUIA.getCodigo())) {
            ubicacionGeograficaHelper.setNodeSeleccionado(4);
            if (ubicacionGeograficaHelper.getEsNuevo()) {
                ejecutarComandoPrimefaces("msgDialog.show()");
            } else {
                ejecutarComandoPrimefaces("dlgUbicacionGeografica.show()");
            }

        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboTipo() {
        ubicacionGeograficaHelper.getListaTipoUbicacionGeografica().clear();
        for (TipoUbicacionGeograficaEnum t : TipoUbicacionGeograficaEnum.values()) {
            ubicacionGeograficaHelper.getListaTipoUbicacionGeografica().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Constructor por defecto.
     */
    public UbicacionGeograficaControlador() {
        super();
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionUbicacionGeografica(final String codigo) {
        return TipoUbicacionGeograficaEnum.obtenerDescripcion(codigo);
    }

    @Override
    public String guardar() {
        try {
            if (ubicacionGeograficaHelper.getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    getAdmServicio().guardarUbicacionGeografica(ubicacionGeograficaHelper.getUbicacionGeografica());
                    iniciarNuevo();
                    llenarUbicacionGeografica();
                    ejecutarComandoPrimefaces("dlgUbicacionGeografica.hide()");
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                getAdmServicio().actualizarUbicacionGeografica(ubicacionGeograficaHelper.getUbicacionGeografica());
                ejecutarComandoPrimefaces("dlgUbicacionGeografica.hide()");
                llenarUbicacionGeografica();
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe el nemónico.
     *
     * @return haynemonico Boolean.
     */
    public Boolean validarNemonico() {
        Boolean hayNemonico = true;
        try {
            ubicacionGeograficaHelper.getListaTipoUbicacionGeografica().clear();
            ubicacionGeograficaHelper.setListaUbicacionGeografica(admServicio.listarUbicacionGeograficaPorNemonico(
                    ubicacionGeograficaHelper.getUbicacionGeografica().getCodigo()));
            if (ubicacionGeograficaHelper.getListaUbicacionGeografica().isEmpty()) {
                hayNemonico = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nemonico", ex);
        }
        return hayNemonico;
    }

    @Override
    public String iniciarEdicion() {
        try {
            ubicacionGeograficaHelper.setEsNuevo(Boolean.FALSE);
            onNodeSelect();
            Object cloneBean
                    = BeanUtils.cloneBean(ubicacionGeograficaHelper.getUgN());
            UbicacionGeografica d = (UbicacionGeografica) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            ubicacionGeograficaHelper.setUbicacionGeografica(d);

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", e);
        }
        return null;
    }

    @Override
    public String iniciarNuevo() {
        try {
            ubicacionGeograficaHelper.setEsNuevo(Boolean.TRUE);
            onNodeSelect();
            ubicacionGeograficaHelper.setUbicacionGeografica(new UbicacionGeografica());
            iniciarDatosEntidad(ubicacionGeograficaHelper.getUbicacionGeografica(), Boolean.TRUE);

            if (ubicacionGeograficaHelper.getUgN().getTipo() == null) {
                ubicacionGeograficaHelper.getUbicacionGeografica().setId(null);
                ubicacionGeograficaHelper.getUbicacionGeografica().setIdUbicacionGeografica(null);
                ubicacionGeograficaHelper.getUbicacionGeografica().setTipo(ubicacionGeograficaHelper.getUgN().getTipo());
                ubicacionGeograficaHelper.getUbicacionGeografica().setTipo(TipoUbicacionGeograficaEnum.PAIS.getCodigo());
            }
            if (ubicacionGeograficaHelper.getUgN().getTipo().equals(TipoUbicacionGeograficaEnum.PAIS.getCodigo())) {

                ubicacionGeograficaHelper.getUbicacionGeografica().setId(null);
                ubicacionGeograficaHelper.getUbicacionGeografica().setIdUbicacionGeografica(ubicacionGeograficaHelper.getUgN().getId());
                ubicacionGeograficaHelper.getUbicacionGeografica().setTipo(TipoUbicacionGeograficaEnum.PROVINCIA.getCodigo());
            }
            if (ubicacionGeograficaHelper.getUgN().getTipo().equals(TipoUbicacionGeograficaEnum.PROVINCIA.getCodigo())) {
                ubicacionGeograficaHelper.getUbicacionGeografica().setId(null);
                ubicacionGeograficaHelper.getUbicacionGeografica().setIdUbicacionGeografica(ubicacionGeograficaHelper.getUgN().getId());
                ubicacionGeograficaHelper.getUbicacionGeografica().setTipo(TipoUbicacionGeograficaEnum.CANTON.getCodigo());
            }
            if (ubicacionGeograficaHelper.getUgN().getTipo().equals(TipoUbicacionGeograficaEnum.CANTON.getCodigo())) {
                ubicacionGeograficaHelper.getUbicacionGeografica().setId(null);
                ubicacionGeograficaHelper.getUbicacionGeografica().setIdUbicacionGeografica(ubicacionGeograficaHelper.getUgN().getId());
                ubicacionGeograficaHelper.getUbicacionGeografica().setTipo(TipoUbicacionGeograficaEnum.PARROQUIA.getCodigo());
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public String borrar() {
        try {
            ubicacionGeograficaHelper.setEsNuevo(Boolean.FALSE);
            onNodeSelect();
            ubicacionGeograficaHelper.getListaUbicacionGeografica().clear();
            ubicacionGeograficaHelper.getListaUbicacionGeografica().
                    addAll(admServicio.listarTodosIdUbicacionGeografica(ubicacionGeograficaHelper.getUgN().getId()));
            if (ubicacionGeograficaHelper.getListaUbicacionGeografica().isEmpty()) {
                admServicio.eliminarUbicacionGeografica(ubicacionGeograficaHelper.getUgN());
                ubicacionGeograficaHelper.getListaUbicacionGeografica().
                        remove(ubicacionGeograficaHelper.getUgN());
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
                llenarUbicacionGeografica();
                ejecutarComandoPrimefaces("dlgUbicacionGeografica.hide()");
            } else {
                mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_INFO);
                ejecutarComandoPrimefaces("dlgUbicacionGeografica.hide()");
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the admServicio
     */
    public AdministracionServicio getAdmServicio() {
        return admServicio;
    }

    /**
     * @param admServicio the admServicio to set
     */
    public void setAdmServicio(final AdministracionServicio admServicio) {
        this.admServicio = admServicio;
    }

    /**
     * @return the ubicacionGeograficaHelper
     */
    public UbicacionGeograficaHelper getUbicacionGeograficaHelper() {
        return ubicacionGeograficaHelper;
    }

    /**
     * @param ubicacionGeograficaHelper the ubicacionGeograficaHelper to set
     */
    public void setUbicacionGeograficaHelper(final UbicacionGeograficaHelper ubicacionGeograficaHelper) {
        this.ubicacionGeograficaHelper = ubicacionGeograficaHelper;
    }
}
