/*
 *  BancoControlador.java
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
 *  26/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.BancoHelper;
import ec.com.atikasoft.proteus.modelo.Banco;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Banco
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "bancoBean")
@ViewScoped
public class BancoControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(BancoControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/banco/banco.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/banco/lista_banco.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{bancoHelper}")
    private BancoHelper bancoHelper;

    /**
     * Constructor por defecto.
     */
    public BancoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(bancoHelper);
        setBancoHelper(bancoHelper);
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    @Override
    public String guardar() {
        try {
            if (bancoHelper.getEsNuevo()) {
                if (validarCodigo()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    admServicio.guardarBanco(bancoHelper.getBanco());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                }

            } else {
                admServicio.actualizarBanco(bancoHelper.getBanco());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe el código.
     *
     * @return hayCodigo Boolean.
     */
    public Boolean validarCodigo() {
        Boolean hayCodigo = true;
        try {
            bancoHelper.getListaBancoCodigo().clear();
            bancoHelper.setListaBancoCodigo(admServicio.listarTodosBancoPorCodigo(
                    bancoHelper.getBanco().getCodigo()));
            
            if (bancoHelper.getListaBancoCodigo().isEmpty()) {
                hayCodigo = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del código", ex);
        }
        return hayCodigo;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean =
                    BeanUtils.cloneBean(bancoHelper.getBancoEditDelete());
            Banco d = (Banco) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            bancoHelper.setBanco(d);
            bancoHelper.setEsNuevo(Boolean.FALSE);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        bancoHelper.setBanco(new Banco());
        bancoHelper.iniciador();
        iniciarDatosEntidad(bancoHelper.getBanco(), Boolean.TRUE);
        bancoHelper.setEsNuevo(Boolean.TRUE);
        
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarBanco(bancoHelper.getBancoEditDelete());
            bancoHelper.getListaBancos().
                    remove(bancoHelper.getBancoEditDelete());
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
            bancoHelper.getListaBancos().clear();
            bancoHelper.setListaBancos(
                    admServicio.listarTodasBancosPorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * @return the bancoHelper
     */
    public BancoHelper getBancoHelper() {
        return bancoHelper;
    }

    /**
     * @param bancoHelper the bancoHelper to set
     */
    public void setBancoHelper(BancoHelper bancoHelper) {
        this.bancoHelper = bancoHelper;
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
