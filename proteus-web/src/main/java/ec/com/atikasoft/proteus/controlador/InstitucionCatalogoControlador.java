/*
 *  InstitucionCatalogoControlador.java
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
 *  21/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.InstitucionHelper;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.vo.InstitucionVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "institucionCatalogoControladorBean")
@ViewScoped
public class InstitucionCatalogoControlador extends CatalogoControlador {

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/institucion/lista_institucion.jsf";

    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/institucion/institucion.jsf";

    @EJB
    private AdministracionServicio administracionServicio;

    @ManagedProperty("#{institucionHelper}")
    private InstitucionHelper institucionHelper;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(institucionHelper);
        setInstitucionHelper(institucionHelper);
        buscar();
        institucionHelper.setCampoBusqueda("");
        institucionHelper.setNombre("");

    }

    /**
     * metodo para cargar las instituciones del siith en mi lista de instituciones.
     *
     * @return
     */
    public String buscarInstitucionActiva() {
        try {
            /**
             * lista que recupera todas la instituciones del Siith.
             */
//            List<Institucion> listaInstitucionSiith =
//                    institucionServicio.listarInstitucionesActivasporNombre(institucionHelper.getNombre());
            institucionHelper.getListaInstitucionVO().clear();
            /**
             * lista que recupera todas la instituciones del Proteus.
             */
            List<ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal> listaInstituciones =
                    administracionServicio.listarInstitucionesVigentes();
            /**
             * metodo que genera mis dos listas para actualizar.
             */
//            validarInstitucionesSeleccionadas(listaInstitucionSiith, listaInstituciones);
        } catch (Exception e) {
            ponerMensajeError(NADA, "No se puede listar los registros por nombre" + e.getMessage());
            error(getClass().getName(), "no se puede listar registros por nombre", e);
        }
        return null;
    }

    /**
     * Metodo que se encarga de validar las reglas seleccionados en la pantalla.
     *
     * @param listaInstitucionSiith lista de instituciones siith.
     * @param listaInstitucionProteus lista de instituciones Proteus.
     */
//    public void validarInstitucionesSeleccionadas(final List<Institucion> listaInstitucionSiith,
//            final List<ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal> listaInstituciones) {
//        List<Institucion> sListo = new ArrayList<Institucion>(listaInstitucionSiith);
//        for (Institucion insSiith : listaInstitucionSiith) {
//            for (ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal insProteus : listaInstituciones) {
//                if (insSiith.getNombre().equals(insProteus.getInstitucion().getNombre())) {
//                    sListo.remove(insSiith);
//                    break;
//                }
//            }
//        }
//        for (Institucion insSiith : sListo) {
//            InstitucionVO insVO = new InstitucionVO();
//            insVO.setSeleccionar(Boolean.FALSE);
////            insVO.setInstitucionSiith(insSiith);
//            institucionHelper.getListaInstitucionVO().add(insVO);
//        }
//    }

    /**
     * Metodo que se encarga de iterar la lista de seleccionados y guardar la nueva lista.
     *
     * @return Lista de TipoMovimientoAlertaVO
     */
    public List<InstitucionVO> verificarListaGuardar() {
        List<InstitucionVO> listaINSVO = new ArrayList<InstitucionVO>();
        for (InstitucionVO insvo : institucionHelper.getListaInstitucionVO()) {
            if (insvo.getSeleccionar()) {
                InstitucionVO tmr = new InstitucionVO();
//                tmr.setInstitucionSiith(insvo.getInstitucionSiith());
                tmr.setSeleccionar(insvo.getSeleccionar());
                listaINSVO.add(tmr);
            }
        }
        return listaINSVO;
    }

    /**
     * Metodo para llenar la lista de las intituciones del SIITH.
     *
     * @return.
     */
    public String insertarInstitucion() {
        try {
//            List<InstitucionVO> lista = verificarListaGuardar();
//            getAdministracionServicio().guardarInstitucion(obtenerUsuarioLogeado().getNombre(),
//                    obtenerUsuarioConectado().getIdEjercicioFiscal(), lista);
//            buscarInstitucionActiva();
//            buscar();
//            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar la institucion", ex);
        }
        return null;

    }

    @Override
    public String guardar() {
        try {
            if (institucionHelper.getEsNuevo()) {
                administracionServicio.guardarInstituciones(institucionHelper.getInstitucion());
                iniciarNuevo();
            } else {
                administracionServicio.actualizarInstitucion(institucionHelper.getInstitucion());
            }

            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Erro al guardar el parámetro", e);
        }
        return null;
    }

    /**
     * @return the institucionHelper
     */
    public InstitucionHelper getInstitucionHelper() {
        return institucionHelper;
    }

    /**
     * @param institucionHelper the institucionHelper to set
     */
    public void setInstitucionHelper(InstitucionHelper institucionHelper) {
        this.institucionHelper = institucionHelper;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean =
                    BeanUtils.cloneBean(institucionHelper.getInstitucionEditDelete());
            ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal re =
                    (ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal) cloneBean;
            iniciarDatosEntidad(re, Boolean.FALSE);
            institucionHelper.setInstitucion(re);
            institucionHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        institucionHelper.setInstitucion(new ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal());
        institucionHelper.init();
        iniciarDatosEntidad(institucionHelper.getInstitucion(), Boolean.TRUE);
        institucionHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String buscar() {
        try {
            institucionHelper.getListaInstitucion().clear();
            institucionHelper.setListaInstitucion(
                    administracionServicio.listarTodosInstitucionPorNombre(
                    institucionHelper.getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * valida el vigente de mi institucion.
     *
     * @param vigente
     * @return
     */
    public String verificarVigente(final Boolean vigente) {
        String resultado = "Inactivo";
        if (vigente) {
            resultado = "Activo";
        }
        return resultado;
    }

    /**
     * @return the administracionServicio
     */
    public AdministracionServicio getAdministracionServicio() {
        return administracionServicio;
    }

    /**
     * @param administracionServicio the administracionServicio to set
     */
    public void setAdministracionServicio(final AdministracionServicio administracionServicio) {
        this.administracionServicio = administracionServicio;
    }
}
