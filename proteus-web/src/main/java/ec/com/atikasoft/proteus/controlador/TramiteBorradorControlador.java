/*
 *  TramiteBorradorControlador.java
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
 *  15/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.TramiteBorradorHelper;
import ec.com.atikasoft.proteus.controlador.helper.TramiteHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoOrdenamientoEnum;
import ec.com.atikasoft.proteus.enums.TramiteBorradorBuscarPorEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Clase;
import ec.com.atikasoft.proteus.modelo.Grupo;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Controlador que permite administrar la bandeja de borrador.
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "tramiteBorradorBean")
@ViewScoped
public class TramiteBorradorControlador extends BaseControlador {

    /**
     * Logger de clase.
     */
    private static final Logger LOG = Logger.getLogger(TramiteBorradorControlador.class.getName());

    /**
     * ULR a la pagina.
     */
    public static final String PAGINA_PRINCIPAL = "/pages/procesos/tramite/tramite_borrador.jsf";

    /**
     * Helper de la clase.
     */
    @ManagedProperty("#{tramiteBorradorHelper}")
    private TramiteBorradorHelper tramiteBorradorHelper;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tramiteHelper}")
    private TramiteHelper tramiteHelper;

    /**
     * EJB de administracion de servicio.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Servicios de tramite.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Constructor por defecto.
     */
    public TramiteBorradorControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setTramiteBorradorHelper(tramiteBorradorHelper);
        llenarComboOrdenarPor();
        llenarComboTipoOrdenamiento();
        cargarTablaTramitesBorrador();
        tramiteHelper.setGrupoId(null);
        tramiteHelper.setClaseId(null);
        tramiteHelper.setTipoMovimientoId(null);
        cargarGrupos();
        llenarComboClaseFiltros();
        tramiteBorradorHelper.setToken("");
    }

    /**
     * Este médoto procesa el inicio de la edicion.
     *
     * @return String
     */
    public String iniciarEdicion() {
        tramiteHelper.setEsAprobar(Boolean.FALSE);
        reglaNavegacionDirecta(TramiteControlador.FORMULARIO_ENTIDAD.concat("?est=edt"));
        return null;
    }

    /**
     * Metodo que se encarga de llenar el combo de ordenar por.
     */
    public void llenarComboOrdenarPor() {
        tramiteBorradorHelper.getListaOrdenarPor().clear();
        for (TramiteBorradorBuscarPorEnum tbo : TramiteBorradorBuscarPorEnum.values()) {
            tramiteBorradorHelper.getListaOrdenarPor().add(new SelectItem(tbo.getColumna(), tbo.getDescripcion()));
        }
    }

    /**
     * Metodo que se encarga de llenar el combo de ordenar por.
     */
    public void llenarComboTipoOrdenamiento() {
        tramiteBorradorHelper.getListaTipoOrdenamiento().clear();
        for (TipoOrdenamientoEnum to : TipoOrdenamientoEnum.values()) {
            tramiteBorradorHelper.getListaTipoOrdenamiento().add(new SelectItem(to.getCodigo(), to.getDescripcion()));
        }
    }

    /**
     * Listar los tramites en borrador.
     */
    public void cargarTablaTramitesBorrador() {
        try {
            tramiteBorradorHelper.getListaTramitesBorrador().clear();
            tramiteBorradorHelper.setListaTramitesBorrador(administracionServicio.buscarTramitesPorEstadoYUsuario(
                    EstadosTramiteEnum.ELABORACION.getCodigo(), obtenerUsuario()));

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al cargar la tabla de tarmites en borrador", ex);
        }
    }

    /**
     * Metodo que se encarga de buscar los tramites en borrador segun los parametros enviados.
     *
     * @return String
     */
    public String buscar() {
        try {
            tramiteBorradorHelper.setListaTramitesBorrador(administracionServicio.listarTramitePorFiltros(
                    EstadosTramiteEnum.ELABORACION.getCodigo(), obtenerUsuario(),
                    "000", Integer.parseInt(obtenerUsuarioConectado().getEjercicioFiscal().getNombre()),
                    tramiteBorradorHelper.getToken().toUpperCase(), tramiteBorradorHelper.getOrdenarPor(),
                    tramiteBorradorHelper.getTipoOrdenamiento()));
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            Logger.getLogger(TramiteBorradorControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Realiza la eliminacion de un tramita.
     *
     * @return null
     */
    public String borrar() {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            Tramite t = tramiteBorradorHelper.getTramite();
            tramiteServicio.eliminarTramite(t.getId(), obtenerUsuarioConectado().
                    getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo(),
                    Integer.parseInt(obtenerUsuarioConectado().getEjercicioFiscal().getNombre()),
                    "ELIMINADO EN ELABORACION", obtenerUsuarioConectado(), esRRHH(pi.getValorTexto()));
            //cargarTablaTramitesBorrador();
            tramiteBorradorHelper.getListaTramitesBorrador().remove(t);
            mostrarMensajeEnPantalla("ec.gob.mrl.smp.tramiteBorrador.borrarTramitte", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al eliminar el tramite", e);
        }
        return null;
    }

    /**
     * Este método eliminar un movimiento de un tramite.
     *
     * @return
     */
    public String rechazarTramite() {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            Tramite t = tramiteBorradorHelper.getTramite();
            tramiteServicio.rechazarTramite(t.getId(), obtenerUsuarioConectado().
                    getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo(),
                    Integer.parseInt(obtenerUsuarioConectado().getEjercicioFiscal().getNombre()),
                    tramiteBorradorHelper.getComentario(), obtenerUsuarioConectado(), esRRHH(pi.getValorTexto()));
            //cargarTablaTramitesBorrador();
            tramiteBorradorHelper.getListaTramitesBorrador().remove(t);
            mostrarMensajeEnPantalla("Trámite se rechazo correctamente.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al eliminar el tramite", e);
        }
        return null;
    }

    /**
     * Metodo que se encarga de dar el formato para la busqueda de la columna de fecha.
     *
     * @param date Fecha
     * @return String fecha encontrada
     */
    public String localizedDateRep(final Date date) {
        String retorno = "";
        if (date != null) {
            retorno = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
        }
        return retorno;
    }

    /**
     * Filtros de grupos
     */
    public void cargarGrupos() {
        try {
            iniciarCombosTodos(tramiteHelper.getListaGrupo());
            List<Grupo> lista = administracionServicio.listarTodosVigente();
            for (Grupo g : lista) {
                SelectItem item = new SelectItem(g.getId(), g.getNombre());
                tramiteHelper.getListaGrupo().add(item);
            }
        } catch (ServicioException ex) {
            Logger.getLogger(TramiteBorradorControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Maneja el evento de seleccion de filtros de la tabla desde el cliente Se encarga de recibir el fitro seleccionado
     * en la tabla y encontrar la gerarquia de seleccion Carga el grupo o clase seleccionado para operar con el
     */
    public void llenarComboClaseFiltrosEvent() {
        String idGrupoStr = null;
        Long idGrupo = null;
        String idClaseStr = null;
        Long idClase = null;
        String idMovimientoStr = null;
        Long idMovimiento = null;
        Map<String, String> parametros = obtenerParametrosFaces();
        try {
            idGrupoStr = parametros.get("frmTramoteBorrador:grupo_filtro_seleccionado");
            idGrupo = Long.parseLong(idGrupoStr);
        } catch (Exception e) {
        }
        try {
            idClaseStr = parametros.get("frmTramoteBorrador:clase_filtro_seleccionado");
            idClase = Long.parseLong(idClaseStr);
        } catch (Exception e) {
        }
        try {
            idMovimientoStr = parametros.get("frmTramoteBorrador:movimiento_filtro_seleccionado");
            idMovimiento = Long.parseLong(idMovimientoStr);
        } catch (Exception e) {
        }

        Clase cl = null;
        if (idMovimiento != null) {
            try {
                TipoMovimiento tp = administracionServicio.buscarTipoMovimientoPorId(idMovimiento);
                cl = tp.getClase();
                idClase = cl.getId();
            } catch (ServicioException ex) {
                Logger.getLogger(TramiteBorradorControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (idClase != null) {
            Grupo g = null;
            if (cl != null) {
                g = cl.getGrupo();
            } else {
                try {
                    cl = administracionServicio.buscarClasePorId(idClase);
                    g = cl.getGrupo();
                } catch (ServicioException ex) {
                    Logger.getLogger(TramiteBorradorControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (g != null) {
                idGrupo = g.getId();
            }
        }

        tramiteHelper.setGrupoId(idGrupo);
        tramiteHelper.setClaseId(idClase);
        tramiteHelper.setTipoMovimientoId(idMovimiento);
        llenarComboClaseFiltros();
    }

    /**
     * Helper para mantener el estado de los filtros de la tabla y la dependencia entre estos sin recargar varias veces
     * la misma, ya que se manejan en el cliente con js Llenar el combo de clases segun un grupo si hay uno seleccionado
     * en filtros Generar la representacion en JSON de estas clases
     */
    public void llenarComboClaseFiltros() {
        try {
            iniciarCombos(tramiteHelper.getListaClase());
            List<Grupo> listaGrupos = null;
            List<Clase> listaClase = null;
            List<TipoMovimiento> listaTipoMovimiento = null;
            Long idGrupo = tramiteHelper.getGrupoId();
            Long idClase = tramiteHelper.getClaseId();
            Long idTipoMovimiento = tramiteHelper.getTipoMovimientoId();

            listaGrupos = administracionServicio.listarTodosVigente();

            if (idGrupo == null) {
                listaClase = administracionServicio.listarTodosClase();
            } else {
                listaClase = administracionServicio.listarClasePorGrupoId(idGrupo);
            }

            if (idClase == null) {
                listaTipoMovimiento = administracionServicio.listarTiposMovimientosActivos();
            } else {
                listaTipoMovimiento = administracionServicio.listarTipoMovimientoActivosPorClase(idClase);
            }

            JSONObject data = new JSONObject();
            data.put("grupo", idGrupo);
            data.put("clase", idClase);
            data.put("movimiento", idTipoMovimiento);
            data.put("seleccion", obtenerProperties().
                    getString("ec.gob.mrl.smp.genericos.combo.consulta"));
            JSONArray arrayC = new JSONArray();
            for (Clase c : listaClase) {
                SelectItem it = new SelectItem(c.getId(), c.getNombre());
                tramiteHelper.getListaClase().add(it);
                JSONObject object = new JSONObject();
                object.put("id", c.getId());
                object.put("name", c.getNombre());
                arrayC.add(object);
            }
            data.put("clases", arrayC);
            JSONArray arrayM = new JSONArray();
            for (TipoMovimiento c : listaTipoMovimiento) {
                SelectItem it = new SelectItem(c.getId(), c.getNombre());
                tramiteHelper.getListaTipoMovimiento().add(it);
                JSONObject object = new JSONObject();
                object.put("id", c.getId());
                object.put("name", c.getNombre());
                arrayM.add(object);
            }
            data.put("movimientos", arrayM);
            JSONArray arrayG = new JSONArray();
            for (Grupo c : listaGrupos) {
                SelectItem it = new SelectItem(c.getId(), c.getNombre());
                tramiteHelper.getListaGrupo().add(it);
                JSONObject object = new JSONObject();
                object.put("id", c.getId());
                object.put("name", c.getNombre());
                arrayG.add(object);
            }
            data.put("grupos", arrayG);
            tramiteBorradorHelper.setClasesFiltrosSeleccionados(data.toString());
        } catch (ServicioException ex) {
            info(getClass().getName(), "Error al llenar el combo de clase" + ex);
        }
    }

    /**
     * @return the tramiteBorradorHelper
     */
    public TramiteBorradorHelper getTramiteBorradorHelper() {
        return tramiteBorradorHelper;
    }

    /**
     * @param tramiteBorradorHelper the tramiteBorradorHelper to set
     */
    public void setTramiteBorradorHelper(final TramiteBorradorHelper tramiteBorradorHelper) {
        this.tramiteBorradorHelper = tramiteBorradorHelper;
    }

    /**
     * @return the tramiteHelper
     */
    public TramiteHelper getTramiteHelper() {
        return tramiteHelper;
    }

    /**
     * @param tramiteHelper the tramiteHelper to set
     */
    public void setTramiteHelper(final TramiteHelper tramiteHelper) {
        this.tramiteHelper = tramiteHelper;
    }
}
