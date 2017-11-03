/*
 *  UnidadPresupuestariaControlador.java
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
 *  20/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.UnidadPresupuestariaHelper;
import ec.com.atikasoft.proteus.dao.CertificacionPresupuestariaDao;
import ec.com.atikasoft.proteus.dao.CertificacionPresupuestariaHistoricoDao;
import ec.com.atikasoft.proteus.dao.SectorDao;
import ec.com.atikasoft.proteus.dao.UnidadPresupuestariaDao;
import ec.com.atikasoft.proteus.enums.GrupoPresupuestarioEnum;
import ec.com.atikasoft.proteus.enums.TipoUnidadPresupuestariaEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.CertificacionPresupuestaria;
import ec.com.atikasoft.proteus.modelo.CertificacionPresupuestariaHistorico;
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.Sector;
import ec.com.atikasoft.proteus.modelo.UnidadPresupuestaria;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
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
 * UnidadPresupuestaria
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "unidadPresupuestariaBean")
@ViewScoped
public class UnidadPresupuestariaControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(UnidadPresupuestariaControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/unidad_organizacional/unidad_presupuestaria.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/unidad_organizacional/lista_unidad_presupuestaria.jsf";

    /**
     * Servicio de administracion de unidadPresupuestaria y roles.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Dao de nomina.
     */
    @EJB
    private SectorDao sectorDao;

    /**
     * Dao de certificacion presupuestaria.
     */
    @EJB
    private CertificacionPresupuestariaDao certificacionPresupuestariaDao;

    /**
     * Dao de certificacion presupuestaria historico.
     */
    @EJB
    private CertificacionPresupuestariaHistoricoDao certificacionPresupuestariaHistoricoDao;

    /**
     * Dao de UnidadPresupuestariaDao
     */
    @EJB
    private UnidadPresupuestariaDao unidadPresupuestariaDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{unidadPresupuestariaHelper}")
    private UnidadPresupuestariaHelper unidadPresupuestariaHelper;

    /**
     * Constructor por defecto.
     */
    public UnidadPresupuestariaControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(unidadPresupuestariaHelper);
        setUnidadPresupuestariaHelper(unidadPresupuestariaHelper);
        llenarCombroGruposPresupuestarios();
        llenarCombroSectores();
        llenarCombosTipos();
        buscar();
    }

    @Override
    public String guardar() {
        try {
            if (unidadPresupuestariaDao.existeCodigoInterno(unidadPresupuestariaHelper.
                    getUnidadPresupuestaria().getCodigoInterno(),
                    unidadPresupuestariaHelper.getUnidadPresupuestaria().getId())) {
                mostrarMensajeEnPantalla(
                        "ec.gob.mrl.smp.administracion.unidadPresupuestaria.error.codigoInterno.duplicado",
                        FacesMessage.SEVERITY_ERROR);
                return null;
            }

            if (unidadPresupuestariaHelper.getEsNuevo()) {
                administracionServicio.guardarUnidadPresupuestaria(
                        unidadPresupuestariaHelper.getUnidadPresupuestaria());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                iniciarNuevo();
            } else {
                administracionServicio.actualizarUnidadPresupuestaria(
                        unidadPresupuestariaHelper.getUnidadPresupuestaria());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (ServicioException | DaoException e) {
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
            unidadPresupuestariaHelper.getListaUnidadPresupuestariaCodigo().clear();
            unidadPresupuestariaHelper.setListaUnidadPresupuestariaCodigo(administracionServicio.listarTodosUnidadesPresupuestariasPorCodigo(
                    unidadPresupuestariaHelper.getUnidadPresupuestaria().getCodigo()));

            if (unidadPresupuestariaHelper.getListaUnidadPresupuestariaCodigo().isEmpty()) {
                hayCodigo = false;
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del código", ex);
        }
        return hayCodigo;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(unidadPresupuestariaHelper.getUnidadPresupuestariaEditDelete());
            UnidadPresupuestaria d = (UnidadPresupuestaria) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            unidadPresupuestariaHelper.setUnidadPresupuestaria(d);
            iniciarDatosEntidad(unidadPresupuestariaHelper.getUnidadPresupuestaria(), Boolean.FALSE);
            unidadPresupuestariaHelper.setEsNuevo(Boolean.FALSE);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        unidadPresupuestariaHelper.setUnidadPresupuestaria(new UnidadPresupuestaria());
        unidadPresupuestariaHelper.getUnidadPresupuestaria().setSector(new Sector());
        iniciarDatosEntidad(unidadPresupuestariaHelper.getUnidadPresupuestaria(), Boolean.TRUE);
        unidadPresupuestariaHelper.setEsNuevo(Boolean.TRUE);

        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            administracionServicio.eliminarUnidadPresupuestaria(unidadPresupuestariaHelper.getUnidadPresupuestariaEditDelete());
            unidadPresupuestariaHelper.getListaUnidadesPresupuestarias().remove(unidadPresupuestariaHelper.getUnidadPresupuestariaEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion", ex);
        }
        return LISTA_ENTIDAD;
    }

    @Override
    public String buscar() {

        try {
            unidadPresupuestariaHelper.getListaUnidadesPresupuestarias().clear();
            unidadPresupuestariaHelper.setListaUnidadesPresupuestarias(
                    administracionServicio.listarTodosUnidadesPresupuestarias());
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda unidades Presupuestarias", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Crear lista de grupos presupuestarios.
     */
    private void llenarCombroGruposPresupuestarios() {
        iniciarCombos(unidadPresupuestariaHelper.getListaGruposPresupuestarios());
        for (GrupoPresupuestarioEnum en : GrupoPresupuestarioEnum.values()) {
            unidadPresupuestariaHelper.getListaGruposPresupuestarios().add(new SelectItem(en.getCodigo(), en.
                    getDescripcion()));
        }

    }

    /**
     * Crear lista de tipos de unidades presupuestarios.
     */
    private void llenarCombosTipos() {
        iniciarCombos(unidadPresupuestariaHelper.getListaTipos());
        for (TipoUnidadPresupuestariaEnum en : TipoUnidadPresupuestariaEnum.values()) {
            unidadPresupuestariaHelper.getListaTipos().add(new SelectItem(en.getCodigo(), en.
                    getDescripcion()));
        }

    }

    /**
     * Crear lista de sectores.
     */
    private void llenarCombroSectores() {
        iniciarCombos(unidadPresupuestariaHelper.getListaSectores());
        List<Sector> lista;
        try {
            lista = administracionServicio.listarTodosSectores();
            for (Sector en : lista) {
                unidadPresupuestariaHelper.getListaSectores().add(new SelectItem(en.getId(), en.
                        getNombre()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de sectores", ex);
        }
    }

    public String irLista() {
        return LISTA_ENTIDAD;
    }

    /**
     * @return the unidadPresupuestariaHelper
     */
    public UnidadPresupuestariaHelper getUnidadPresupuestariaHelper() {
        return unidadPresupuestariaHelper;
    }

    /**
     * @param unidadPresupuestariaHelper the unidadPresupuestariaHelper to set
     */
    public void setUnidadPresupuestariaHelper(UnidadPresupuestariaHelper unidadPresupuestariaHelper) {
        this.unidadPresupuestariaHelper = unidadPresupuestariaHelper;
    }

    public void buscarCodigo() {
        Sector sector;
        try {
            sector = sectorDao.buscarPorId(this.unidadPresupuestariaHelper.getUnidadPresupuestaria().getSector().getId());
            this.unidadPresupuestariaHelper.getUnidadPresupuestaria().setSector(sector);
        } catch (DaoException ex) {
            Logger.getLogger(UnidadPresupuestariaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Carga las certificaciones presupuestarias
     */
    public void editarCertificacionesPresupuestarias() {
        try {
            unidadPresupuestariaHelper.getCertificacionesPresupuestarias().clear();
            unidadPresupuestariaHelper.getCertificacionesPresupuestarias().addAll(
                    certificacionPresupuestariaDao.buscarPorUnidadPresupuestaria(
                            unidadPresupuestariaHelper.getUnidadPresupuestariaEditDelete().getId()));

            List<ModalidadLaboral> modalidadesSinCertificacion
                    = administracionServicio.listarModalidadesSinCertificacionPorUnidadPresupuestaria(
                            unidadPresupuestariaHelper.getUnidadPresupuestariaEditDelete().getId());

            for (ModalidadLaboral m : modalidadesSinCertificacion) {
                unidadPresupuestariaHelper.getCertificacionesPresupuestarias().add(new CertificacionPresupuestaria(m,
                        unidadPresupuestariaHelper.getUnidadPresupuestariaEditDelete()));
            }
            actualizarComponente("frmCertificacion:tblCertificacionesPresupuestarias");
        } catch (DaoException | ServicioException ex) {
            Logger.getLogger(UnidadPresupuestariaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Guardar los cambios realizados en las certificaciones y su correspondiente histórico, primero verifica si ya
     * existe la certificación en la BD y en caso de repetirse alguna certificación, no permite guardar ningun cambio.
     */
    public void guardarCertificaciones() {
        //boolean conErrores = false;
        try {
            /*for (CertificacionPresupuestaria cp : unidadPresupuestariaHelper.getCertificacionesPresupuestarias()) {
                if (cp.getCertificacionPresupuestaria() != null) {
                    CertificacionPresupuestaria certificacionEcontrada = 
                            cp.getCertificacionPresupuestaria().trim().isEmpty() ? null : buscaNumeroCertificacion(
                            cp.getCertificacionPresupuestaria(), cp.getId(), cp.getUnidadPresupuestaria().getId());
                    if (certificacionEcontrada != null) {
                        conErrores = true;
                        mostrarMensajeEnPantalla("Ya existe la certificación presupuestaria "
                                + cp.getCertificacionPresupuestaria() + " en la Unidad Presupuestaria: "
                                + certificacionEcontrada.getUnidadPresupuestaria().getNombre(),
                                FacesMessage.SEVERITY_ERROR);
                    }
                }
            }
            if (!conErrores) {*/
                for (CertificacionPresupuestaria cp : unidadPresupuestariaHelper.getCertificacionesPresupuestarias()) {
                    if (cp.getCertificacionPresupuestaria() != null) {
                        if (cp.getId() != null) {
                            iniciarDatosEntidad(cp, Boolean.FALSE);
                            certificacionPresupuestariaDao.actualizar(cp);
                            crearHistoricoCertificacionPresupuestaria(cp);
                        } else {
                            iniciarDatosEntidad(cp, Boolean.TRUE);
                            certificacionPresupuestariaDao.crear(cp);
                            crearHistoricoCertificacionPresupuestaria(cp);
                        }
                    }
                }
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO_VARIOS, FacesMessage.SEVERITY_INFO);
            //}
        } catch (DaoException ex) {
            Logger.getLogger(UnidadPresupuestariaControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        }
    }

    /**
     * Crea un histórico de la certificación presupuestaria.
     *
     * @param cp Certificación presupuestaria modificada
     */
    private void crearHistoricoCertificacionPresupuestaria(CertificacionPresupuestaria cp) {
        try {
            CertificacionPresupuestariaHistorico cph = new CertificacionPresupuestariaHistorico();
            iniciarDatosEntidad(cph, Boolean.TRUE);
            cph.setCertificacionPresupuestaria(cp);
            cph.setNumeroCertificacionPresupuestaria(cp.getCertificacionPresupuestaria());
            cph.setCodigoModalidadLaboral(cp.getModalidadLaboral().getCodigo());
            cph.setNombreModalidadLaboral(cp.getModalidadLaboral().getNombre());
            cph.setTipoModalidad(cp.getModalidadLaboral().getModalidad());
            cph.setNombreUnidadPresupuestaria(cp.getUnidadPresupuestaria().getNombre());
            cph.setCodigoUnidadPresupuestaria(cp.getUnidadPresupuestaria().getCodigo());
            certificacionPresupuestariaHistoricoDao.crear(cph);
        } catch (DaoException ex) {
            Logger.getLogger(UnidadPresupuestariaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Busca dentro de la unidad presupuestaria indicada las certificaciones presupuestarias con el número de
     * certificación especificado.
     *
     * @param numeroCertificacion Número de certificación presupuestaria que se desea buscar
     * @param certificacionId id de la certificación que se desea escluir de la busqueda
     * @param unidadPresupuestariaId id de la unidad presupuestaria dentro de la que se desea buscar
     * @return
     */
    private CertificacionPresupuestaria buscaNumeroCertificacion(String numeroCertificacion, Long certificacionId,
            Long unidadPresupuestariaId) {
        try {
            List<CertificacionPresupuestaria> lista
                    = certificacionPresupuestariaDao.buscarPorCertificacionYUnidadPresupuestaria(numeroCertificacion,
                            certificacionId, unidadPresupuestariaId);
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (DaoException ex) {
            Logger.getLogger(UnidadPresupuestariaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
