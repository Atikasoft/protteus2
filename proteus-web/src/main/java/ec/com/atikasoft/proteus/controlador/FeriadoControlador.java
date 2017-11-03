/*
 *  FeriadoControlador.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  29/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.FeriadoHelper;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Feriado;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.RegimenLaboralServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.lang.reflect.InvocationTargetException;
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
 * Feriado
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "feriadoBean")
@ViewScoped
public class FeriadoControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(FeriadoControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/feriado/feriado.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/feriado/lista_feriado.jsf";

    /**
     * Servicio de vacaciones.
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     * Servicio de regimen laboral.
     */
    @EJB
    private RegimenLaboralServicio regimenLaboralServicio;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{feriadoHelper}")
    private FeriadoHelper feriadoHelper;

    /**
     * Constructor por defecto.
     */
    public FeriadoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(feriadoHelper);
        setFeriadoHelper(feriadoHelper);
        getCatalogoHelper().setCampoBusqueda("");
        iniciarComboEjercicioFiscal();
        iniciarComboRegimenLaboral();
    }

    @Override
    public String guardar() {
        try {
            if (validarFecha()) {
                mostrarMensajeEnPantalla(FERIADO_FECHA_EXISTENTE, FacesMessage.SEVERITY_WARN);
            } else {
                if (verificarFechaEnEjercicioFiscal()) {
                    if (feriadoHelper.getEsNuevo()) {
                        vacacionServicio.guardarFeriado(feriadoHelper.getFeriado());
                        iniciarNuevo();
                    } else {
                        vacacionServicio.actualizarFeriado(feriadoHelper.getFeriado());
                    }
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    
                } else {
                    mostrarMensajeEnPantalla(FERIADO_FECHA_EN_EJERCICIO_FISCAL, FacesMessage.SEVERITY_WARN);
                }
                
                List<Feriado> feriados = vacacionServicio.listarTodosFeriadoPorNombreYPorRegimenLaboral(null,feriadoHelper.getFeriado().getRegimenLaboral().getId());
                getSession().getServletContext().setAttribute(CacheEnum.FERIADOS.getCodigo(), feriados);

            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Permite verificar si la fecha ingresada está dentro del ejercicio fiscal
     *
     * @return
     */
    private boolean verificarFechaEnEjercicioFiscal() {
        boolean valido = false;
        obtieneEjericicioFiscalObject();
        if (UtilFechas.between(feriadoHelper.getFeriado().getFecha(), feriadoHelper.getFeriado().getEjercicioFiscal().
                getFechaInicio(), feriadoHelper.getFeriado().getEjercicioFiscal().getFechaFin())) {
            valido = true;
        }
        return valido;
    }
/**
 * Permite verificar que la fecha se encuentre dentro del periodo fiscal.
 */
    private void obtieneEjericicioFiscalObject() {
        if (feriadoHelper.getFeriado().getIdEjercicioFiscal() != null) {
            try {
                feriadoHelper.getFeriado().setEjercicioFiscal(
                        admServicio.obtenerEjercicioFiscalPorId(
                        feriadoHelper.getFeriado().getIdEjercicioFiscal()));
            } catch (ServicioException ex) {
                mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
                error(getClass().getName(), "Error la procesar la busqueda ejercicio fiscal ", ex);
            }
        }
    }

    /**
     * metodo para validar si ya existe la fecha en el ejercicio fiscal.
     *
     * @return existe Boolean.
     */
    public Boolean validarFecha() {
        boolean existe = false;
        try {
            List<Feriado> feriados = vacacionServicio.listarFeriadoPorFechaEjercicioFiscalYRegimen(
                    feriadoHelper.getFeriado().getFecha(),feriadoHelper.getFeriado().getIdEjercicioFiscal(), 
                    feriadoHelper.getFeriado().getRegimenLaboral().getId());
            if (feriados.isEmpty()) {
                existe = false;
            }else if(!feriadoHelper.getEsNuevo()){
                for(Feriado f: feriados){
                    if(!f.getId().equals(feriadoHelper.getFeriado().getId())){
                        existe = true;
                        break;
                    }
                }
            } else {
                 existe = true;
            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar feriados por fecha", e);
        }
        return existe;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean =
                    BeanUtils.cloneBean(feriadoHelper.getFeriadoEditDelete());
            Feriado d = (Feriado) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            feriadoHelper.setFeriado(d);
            feriadoHelper.setEsNuevo(Boolean.FALSE);

        } catch (IllegalAccessException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InstantiationException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (NoSuchMethodException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InvocationTargetException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        feriadoHelper.setFeriado(new Feriado());
        feriadoHelper.getFeriado().setRegimenLaboral(feriadoHelper.getFeriadoEditDelete().getRegimenLaboral());
        iniciarComboEjercicioFiscal();
        iniciarComboRegimenLaboral();
        iniciarDatosEntidad(feriadoHelper.getFeriado(), Boolean.TRUE);
        feriadoHelper.setEsNuevo(Boolean.TRUE);
       
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            vacacionServicio.eliminarFeriado(feriadoHelper.getFeriadoEditDelete());
            feriadoHelper.getListaFeriados().
                    remove(feriadoHelper.getFeriadoEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            feriadoHelper.getListaFeriados().clear();
            feriadoHelper.setListaFeriados(
                    vacacionServicio.listarTodosFeriadoPorNombreYPorRegimenLaboral(
                    getCatalogoHelper().getCampoBusqueda(), feriadoHelper.getFeriadoEditDelete().getRegimenLaboral().getId()));
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Este metodo llena las opciones para el combo de Ejercicios fiscales.
     */
    private void iniciarComboEjercicioFiscal() {

        try {
            List<EjercicioFiscal> lista = admServicio.listarTodosEjercicioFiscalVigentes();
            iniciarCombos(feriadoHelper.getListaOpcionEjercicioFiscal());

            for (EjercicioFiscal c : lista) {
                feriadoHelper.getListaOpcionEjercicioFiscal().add(new SelectItem(c.getId(), c.getNombre()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda ejercicios fiscales", ex);
        }

    }
  /**
     * Este metodo llena las opciones para el combo de Regímenes Laborales.
     */
    private void iniciarComboRegimenLaboral() {

        try {
            List<RegimenLaboral> lista = regimenLaboralServicio.listarTodosRegimenVigentes();
            iniciarCombos(feriadoHelper.getListaOpcionRegimenLaboral());
            for (RegimenLaboral c : lista) {
                feriadoHelper.getListaOpcionRegimenLaboral().add(new SelectItem(c.getId(), c.getNombre()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda ejercicios fiscales", ex);
        }

    }
    /**
     * Permite regresar al listado principal.
     * @return 
     */
    public String irLista(){
        feriadoHelper.getListaFeriados().clear();
        buscar();
        actualizarComponente("frm_listaFeriados:tablaferiado");
        return LISTA_ENTIDAD;
    }
    /**
     * @return the feriadoHelper
     */
    public FeriadoHelper getFeriadoHelper() {
        return feriadoHelper;
    }

    /**
     * @param feriadoHelper the feriadoHelper to set
     */
    public void setFeriadoHelper(FeriadoHelper feriadoHelper) {
        this.feriadoHelper = feriadoHelper;
    }

}
