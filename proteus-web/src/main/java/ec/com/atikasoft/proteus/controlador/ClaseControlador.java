/*
 *  ClaseControlador.java
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
 *  06/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.ClaseHelper;
import ec.com.atikasoft.proteus.dao.RegimenLaboralDao;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Clase;
import ec.com.atikasoft.proteus.modelo.Grupo;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.HashMap;
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
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "claseBean")
@ViewScoped
public class ClaseControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(ClaseControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/clase/clase.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/clase/lista_clase.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     *
     */
    @EJB
    private RegimenLaboralDao regimenLaboralDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{claseHelper}")
    private ClaseHelper claseHelper;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(claseHelper);
        setClaseHelper(claseHelper);
        llenarComboGrupo();
        llenarComboRegimenLaboral();
        //claseHelper.getClase().setGrupo(new Grupo());
        buscar();
        getCatalogoHelper().setCampoBusqueda("");

    }

    /**
     * Costructor
     */
    public ClaseControlador() {
        super();

    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboGrupo() {
        try {
            iniciarCombos(claseHelper.getListaGrupo());
            List<Grupo> listaGrupo = admServicio.listarTodosGrupo();
            for (Grupo g : listaGrupo) {
                claseHelper.getListaGrupo().add(new SelectItem(g.getId(), g.getNombre()));
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de regimen laboral
     */
    public void llenarComboRegimenLaboral() {
        try {
            iniciarCombos(claseHelper.getListaRegimenLaboral());
            List<RegimenLaboral> lista = regimenLaboralDao.buscarVigente();
            for (RegimenLaboral g : lista) {
                claseHelper.getListaRegimenLaboral().add(new SelectItem(g.getId(), g.getNombre()));
            }
        } catch (Exception ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.CLASES.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE CLASES");
        generarUrlDeReporte();
    }

    @Override
    public String guardar() {
        try {
            if (claseHelper.getClase().getRegimenLaboral().getId() == null) {
                claseHelper.getClase().setRegimenLaboral(null);
            }
            if (claseHelper.getClase().getRegimenLaboral2().getId() == null) {
                claseHelper.getClase().setRegimenLaboral2(null);
            }

            if (claseHelper.getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    getAdmServicio().guardarClase(claseHelper.getClase());
                    iniciarNuevo();
                    llenarComboGrupo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                getAdmServicio().actualizarClase(claseHelper.getClase());
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
            claseHelper.getListaAlertaNemonico().clear();
            claseHelper.setListaAlertaNemonico(admServicio.listarClasePorNemonico(claseHelper.getClase().getNemonico()));
            if (claseHelper.getListaAlertaNemonico().isEmpty()) {
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
            Object cloneBean = BeanUtils.cloneBean(claseHelper.getClaseEditDelete());
            Clase d = (Clase) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            if (d.getRegimenLaboral() == null) {
                d.setRegimenLaboral(new RegimenLaboral());
            }
            if (d.getRegimenLaboral2() == null) {
                d.setRegimenLaboral2(new RegimenLaboral());
            }
            claseHelper.setClase(d);
            claseHelper.setEsNuevo(Boolean.FALSE);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        claseHelper.setClase(new Clase());
        claseHelper.iniciador();
        iniciarDatosEntidad(claseHelper.getClase(), Boolean.TRUE);
        claseHelper.getClase().setRegimenLaboral(new RegimenLaboral());
        claseHelper.getClase().setRegimenLaboral2(new RegimenLaboral());
        claseHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            int cont = 0;
            String mensaje;
            mensaje = " en ";
            if ((admServicio.tieneRestricciones("clase.id",
                    "Clase", "TipoMovimiento",
                    claseHelper.getClaseEditDelete().getId()))) {
                cont += 1;
                mensaje = UtilCadena.concatenar(mensaje, " Tipo Movimiento");
            }
            if (cont == 0) {
                admServicio.eliminarClase(claseHelper.getClaseEditDelete());
                claseHelper.getListaClase().
                        remove(claseHelper.getClaseEditDelete());
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR, mensaje);
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {

            claseHelper.getListaClase().clear();
            claseHelper.setListaClase(
                    admServicio.listarTodosClasePorNombre(
                            getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * @return the claseHelper
     */
    public ClaseHelper getClaseHelper() {
        return claseHelper;
    }

    /**
     * @param claseHelper the claseHelper to set
     */
    public void setClaseHelper(ClaseHelper claseHelper) {
        this.claseHelper = claseHelper;
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
