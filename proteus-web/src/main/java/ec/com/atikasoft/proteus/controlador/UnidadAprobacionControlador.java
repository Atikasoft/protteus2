/*
 *  UnidadAprobacionControlador.java
 *  PROTEUS V 2.0 $Revision 1.0 $
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
 *  07/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.UnidadAprobacionHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.UnidadAprobacion;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;

/**
 * UnidadAprobacion
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "unidadAprobacionBean")
@ViewScoped
public class UnidadAprobacionControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(UnidadAprobacionControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/unidad_organizacional/unidad_aprobacion.jsf";
    /*
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{unidadAprobacionHelper}")
    private UnidadAprobacionHelper unidadAprobacionHelper;

    /**
     * Constructor por defecto.
     */
    public UnidadAprobacionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(unidadAprobacionHelper);
        setUnidadAprobacionHelper(unidadAprobacionHelper);
        iniciarNuevo();
        buscar();
    }

    @Override
    public String guardar() {
        
        try {
            if (unidadAprobacionHelper.getEsNuevo()) {
                if (!verTieneAprobador()) {
                    if (!esAprobadorInconsistente()) {
                        admServicio.guardarUnidadAprobacion(unidadAprobacionHelper.getUnidadAprobacion());
                        mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                        iniciarNuevo();
                        ejecutarComandoPrimefaces("editDialog.hide();");
                    } else {
                        mostrarMensajeEnPantalla(UNIDAD_APROBADORA_COMO_DEPENDIENTE, FacesMessage.SEVERITY_INFO);
                    }
                } else {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_INFO);
                }
            } else {    
                admServicio.actualizarUnidadAprobacion(unidadAprobacionHelper.getUnidadAprobacion());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }

        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return LISTA_ENTIDAD;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean =
                    BeanUtils.cloneBean(unidadAprobacionHelper.getUnidadAprobacionEditDelete());
            UnidadAprobacion d = (UnidadAprobacion) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            unidadAprobacionHelper.setUnidadAprobacion(d);
            unidadAprobacionHelper.setEsNuevo(Boolean.FALSE);

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
        return LISTA_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        unidadAprobacionHelper.setUnidadAprobacion(new UnidadAprobacion());
        unidadAprobacionHelper.iniciador();
        iniciarDatosEntidad(unidadAprobacionHelper.getUnidadAprobacion(), Boolean.TRUE);
        unidadAprobacionHelper.setEsNuevo(Boolean.TRUE);
        iniciarComboUnidadOrganizacional();
        ejecutarComandoPrimefaces("editDialog.show();");
        return null;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarUnidadAprobacion(unidadAprobacionHelper.getUnidadAprobacionEditDelete());
            unidadAprobacionHelper.getListaUnidadAprobacion().
                    remove(unidadAprobacionHelper.getUnidadAprobacionEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            unidadAprobacionHelper.getListaUnidadAprobacion().clear();
            unidadAprobacionHelper.setListaUnidadAprobacion(
                    admServicio.listarTodosUnidadAprobacionVigentes());
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }

        return LISTA_ENTIDAD;
    }

    /**
     * Este metodo llena las opciones para el combo de Opciones de
     * UnidadOrganizacional vigentes
     */
    private void iniciarComboUnidadOrganizacional() {
        try {
            List<UnidadOrganizacional> lista = new ArrayList<UnidadOrganizacional>(admServicio.listarTodosUnidadOrganizacionalVigentes());

            iniciarCombos(unidadAprobacionHelper.getOpcionesUnidadOrganizacional());
            iniciarCombos(unidadAprobacionHelper.getOpcionesUnidadOrganizacionalAprob());
            for (UnidadOrganizacional c : lista) {
                unidadAprobacionHelper.getOpcionesUnidadOrganizacional().add(new SelectItem(c.getId(), c.getRuta()));
                unidadAprobacionHelper.getOpcionesUnidadOrganizacionalAprob().add(new SelectItem(c.getId(), c.getRuta()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda UnidadOrganizacional ", ex);
        }
    }

    /**
     * Permite verificar que no existan duplicados en la Base de datos Mediante
     * el id de la Unidad Aprobadora y la Unidad Organizacional verifican que no
     * exista ya en la base un registro identico en estado vigente
     *
     * @return true si el registro ya existe
     */
    private Boolean esDuplicado() {
        boolean existe = true;

        try {
            unidadAprobacionHelper.getListaUnidadAprobacionCodigo().clear();
            unidadAprobacionHelper.setListaUnidadAprobacionCodigo(
                    admServicio.listarTodosUnidadAprobacionPorSiExiste(
                    unidadAprobacionHelper.getUnidadAprobacion().getIdUnidadOrganizacionalAprobador(),
                    unidadAprobacionHelper.getUnidadAprobacion().getIdUnidadOrganizacional()));
            if (unidadAprobacionHelper.getListaUnidadAprobacionCodigo().isEmpty()) {
                existe = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion del registro", ex);
        }
        return existe;
    }

    /**
     * Se encarga de verificar que la Unidad Aprobadora no sea dependiente de la
     * Unidad Organizacional a la que tiene que Aprobar. Si el buscar con el
     * metodo admServicio.listarTodosUnidadAprobacionPorSiExiste con los
     * parametros invertidos se obtienen registros, indica que la Unidad
     * Organizacional a asignarle un Aprobador, ésta ya es aprobador de la misma
     * Unidad organizacional que se pretende asignar como Aprobador.
     *
     * @return true si el registro tiene falencias
     */
    private boolean esAprobadorInconsistente() {
        boolean existe = true;
        try {
            unidadAprobacionHelper.getListaUnidadAprobacionCodigo().clear();
            unidadAprobacionHelper.setListaUnidadAprobacionCodigo(
                    admServicio.listarTodosUnidadAprobacionPorSiExiste(
                    unidadAprobacionHelper.getUnidadAprobacion().getIdUnidadOrganizacional(),
                    unidadAprobacionHelper.getUnidadAprobacion().getIdUnidadOrganizacionalAprobador()));
            if (unidadAprobacionHelper.getListaUnidadAprobacionCodigo().isEmpty()) {
                existe = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion del registro consistente", ex);
        }
        return existe;
    }
    /**
     * Permite verificar que la Unidad Organizacional ya tenga un Aprobador
     * @return true si la UNIDAD ORGANIZACIONAL ya tiene un Aprobador
     */
    public boolean verTieneAprobador(){
        boolean yaTieneAprobador = true;
         try {
            unidadAprobacionHelper.getListaUnidadAprobacionCodigo().clear();
            unidadAprobacionHelper.setListaUnidadAprobacionCodigo(
                    admServicio.listarTodosUnidadAprobacionPorUnidad(
                    unidadAprobacionHelper.getUnidadAprobacion().getIdUnidadOrganizacional()));
            if (unidadAprobacionHelper.getListaUnidadAprobacionCodigo().isEmpty()) {
                yaTieneAprobador = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion de unidad organizacional ", ex);
        }
        return yaTieneAprobador;
    }

    /**
     * Permite obtener el listado de UnidadesOrganizaciones que no registran
     * UnidadesAprobadoras
     * @return 
      */
    public String obtenerUnidadesOrganizacionalesSinAprobador() {
          try{
            unidadAprobacionHelper.getListaUnidadesOrganizacionalesSinAprobador().clear();
            unidadAprobacionHelper.getListaUnidadesOrganizacionales().clear();
            
            unidadAprobacionHelper.getListaUnidadesOrganizacionales().addAll(
                   admServicio.listarTodosUnidadOrganizacionalVigentes());
            unidadAprobacionHelper.getListaUnidadesOrganizacionalesSinAprobador().addAll(
                    unidadAprobacionHelper.getListaUnidadesOrganizacionales());
            
            for(UnidadOrganizacional u : unidadAprobacionHelper.getListaUnidadesOrganizacionales()){
                for(UnidadAprobacion ua: unidadAprobacionHelper.getListaUnidadAprobacion()){
                    if(u.getId().equals(ua.getIdUnidadOrganizacional())){
                      unidadAprobacionHelper.getListaUnidadesOrganizacionalesSinAprobador().remove(u);
                    }
                }
            }
            ejecutarComandoPrimefaces("listarDialog.show()");
             } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda UnidadOrganizacional ", ex);
        }
            return null;
    }
    /**
     * @return the unidadAprobacionHelper
     */
    public UnidadAprobacionHelper getUnidadAprobacionHelper() {
        return unidadAprobacionHelper;
    }

    /**
     * @param unidadAprobacionHelper the unidadAprobacionHelper to set
     */
    public void setUnidadAprobacionHelper(UnidadAprobacionHelper unidadAprobacionHelper) {
        this.unidadAprobacionHelper = unidadAprobacionHelper;
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
    public void setAdmServicio(AdministracionServicio admServicio) {
        this.admServicio = admServicio;
    }
}
