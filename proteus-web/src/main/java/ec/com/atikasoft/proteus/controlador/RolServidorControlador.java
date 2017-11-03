/*
 *  RolServidorControlador.java
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
 *  17/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.RolServidorHelper;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Rol;
import ec.com.atikasoft.proteus.modelo.RolServidor;
import ec.com.atikasoft.proteus.servicio.MenuServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import java.lang.reflect.InvocationTargetException;
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

/**
 * RolServidor
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "rolServidorBean")
@ViewScoped
public class RolServidorControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(RolServidorControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/rol/rol_por_servidor.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/rol/lista_rol_por_servidor.jsf";
    /**
     * Regla de navegación.
     */
    public static final String PANTALLA_INICIAL = "/pages/index.jsf";
    /**
     * Servicio de administracion.
     */
    @EJB
    private MenuServicio menuServicio;
    /**
     * Servicio de servidor.
     */
    @EJB
    private ServidorServicio servidorServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{rolServidorHelper}")
    private RolServidorHelper rolServidorHelper;

    /**
     * Constructor por defecto.
     */
    public RolServidorControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        rolServidorHelper.setNombreServidor("");
        rolServidorHelper.setNumeroIdentificacion("");
    }

    public String guardar() {
        try {
            setearListaRolesPorServidor();
            menuServicio.guardarRolServidor(rolServidorHelper.getListaRolPorServidor());
            buscarRoles();
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);

        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     *
     * @param rol
     * @return
     */
    public boolean existeEnLista(Rol rol) {
        boolean existe = false;
        for (RolServidor r : rolServidorHelper.getListaRolPorServidor()) {
            if (r.getRol().getId().equals(rol.getId())) {
                if (r.getVigente()) {
                    r.setVigente(Boolean.TRUE);
                    existe = true;
                }
                break;
            }
        }
        return existe;
    }

    /**
     * Gestiona los roles que deben ser almacenados y cuales deben ser
     * eliminados.
     */
    public void setearListaRolesPorServidor() {

        //elementos a eliminar
        for (Rol rol : rolServidorHelper.getListaRolesServidores().getSource()) {
            for (RolServidor s : rolServidorHelper.getListaRolPorServidor()) {
                if (s.getRol().getId().equals(rol.getId())) {
                    s.setVigente(Boolean.FALSE);
                    iniciarDatosEntidad(s, Boolean.FALSE);
                }
            }
        }
        //elementos a agregar
        for (Rol rol : rolServidorHelper.getListaRolesServidores().getTarget()) {
            if (!existeEnLista(rol)) {
                RolServidor rs = new RolServidor();
                iniciarDatosEntidad(rs, Boolean.TRUE);
                rs.setRol((Rol) rol);
                rs.setServidor(rolServidorHelper.getServidor());
                rolServidorHelper.getListaRolPorServidor().add(rs);
            }
        }
    }

    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(rolServidorHelper.getRolServidorEditDelete());
            RolServidor d = (RolServidor) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            rolServidorHelper.setRolServidor(d);
            rolServidorHelper.setEsNuevo(Boolean.FALSE);

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

    /**
     * inicializa variables para crear un nuevo registro
     *
     * @return
     */
    public String iniciarNuevo() {
        if (rolServidorHelper.getServidor() != null & rolServidorHelper.getServidor().getId() != null) {
            rolServidorHelper.setEsNuevo(Boolean.TRUE);
            buscarRolesVigentes();
            buscarRoles();
        } else {
        }
        return FORMULARIO_ENTIDAD;
    }

    public String borrar() {
        try {
            menuServicio.actualizarRolServidor(rolServidorHelper.getRolServidorEditDelete());
            rolServidorHelper.getListaRolPorServidor().
                    remove(rolServidorHelper.getRolServidorEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion", ex);
        }
        return null;
    }

    /**
     * Enlista los roles vigentes del sistema.
     */
    public void buscarRolesVigentes() {
        try {
            rolServidorHelper.setListaRolesVigentes(menuServicio.listarTodosRolesVigentes());
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de roles vigentes", ex);
        }
    }

    /**
     * metodo que busca el servidor por nombre y/o número de identificacion.
     *
     * @return
     */
    public String buscar() {

        try {
            rolServidorHelper.getListaServidores().clear();

            if (rolServidorHelper.getNombreServidor().length() < 3 && !rolServidorHelper.getNombreServidor().isEmpty()) {
                mostrarMensajeEnPantalla(CARACTERES_PARA_BUSQUEDA_X_NOMBRE, FacesMessage.SEVERITY_INFO);
                return LISTA_ENTIDAD;
            }

            if (rolServidorHelper.getNumeroIdentificacion().length() < 3 && !rolServidorHelper.getNumeroIdentificacion().isEmpty()) {
                mostrarMensajeEnPantalla(CARACTERES_PARA_BUSQUEDA_X_IDENTIFICACION, FacesMessage.SEVERITY_INFO);
                return LISTA_ENTIDAD;
            }

            if (rolServidorHelper.getNombreServidor().isEmpty() && rolServidorHelper.getNumeroIdentificacion().isEmpty()) {
                mostrarMensajeEnPantalla(PARAMETROS_PARA_BUSQUEDA, FacesMessage.SEVERITY_INFO);
                return LISTA_ENTIDAD;
            }
            if (!rolServidorHelper.getNombreServidor().trim().isEmpty()) {
                rolServidorHelper.setNombreServidor(rolServidorHelper.getNombreServidor().toUpperCase());
            }
            BusquedaServidorVO ser = new BusquedaServidorVO();
            ser.setNombreServidor(rolServidorHelper.getNombreServidor());
            ser.setNumeroDocumentoServidor(rolServidorHelper.getNumeroIdentificacion());
            ser.setPuestoVacante(Boolean.FALSE);

            List<DistributivoDetalle> lista = servidorServicio.buscar(ser);
            rolServidorHelper.getListaServidores().addAll(lista);
            LOG.log(Level.INFO, "Registros recuperados en la busqueda de servidores para asignacion de permisos:{0}",
                    rolServidorHelper.getListaServidores().size());

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de servidores", ex);
        }
        return LISTA_ENTIDAD;
    }

    public void buscarRoles() {
        try {
            rolServidorHelper.getListaRolPorServidor().clear();
            rolServidorHelper.getListaOrigen().clear();
            rolServidorHelper.getListaDestino().clear();
            if (rolServidorHelper.getServidor() != null && !rolServidorHelper.getListaRolesVigentes().isEmpty()) {

                rolServidorHelper.setListaRolPorServidor(menuServicio.listarTodosRolServidorPorServidor(
                        rolServidorHelper.getServidor().getId()));

                for (Rol rol : rolServidorHelper.getListaRolesVigentes()) {
                    boolean agregado = false;
                    for (RolServidor reg : rolServidorHelper.getListaRolPorServidor()) {
                        if (rol.getId().equals(reg.getRol().getId())) {
                            rolServidorHelper.getListaDestino().add(rol);
                            agregado = true;
                            break;
                        }
                    }
                    if (!agregado) {
                        rolServidorHelper.getListaOrigen().add(rol);
                    }
                }
            } else {
                LOG.info("Lista de roles vigentes vacía o servidor es nulo ");
            }
            rolServidorHelper.getListaRolesServidores().setSource(rolServidorHelper.getListaOrigen());
            rolServidorHelper.getListaRolesServidores().setTarget(rolServidorHelper.getListaDestino());

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de roles vigentes", ex);
        }
    }

    /**
     * Regla de navegacion.
     *
     * @return String
     */
    public String regresar() {
        return LISTA_ENTIDAD;
    }

    /**
     * Este metodo transacciona la busqueda de la descripción del tipo de
     * documento de identificacion parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDocumentoEnum.obtenerNombre(codigo);
    }

    /**
     * @return the rolServidorHelper
     */
    public RolServidorHelper getRolServidorHelper() {
        return rolServidorHelper;
    }

    /**
     * @param rolServidorHelper the rolServidorHelper to set
     */
    public void setRolServidorHelper(RolServidorHelper rolServidorHelper) {
        this.rolServidorHelper = rolServidorHelper;
    }
}
