/*
 *  BandejaTareaControlador.java
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
 *  31/10/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.BandejaTareaHelper;
import ec.com.atikasoft.proteus.controlador.helper.TramiteValidacionHelper;
import ec.com.atikasoft.proteus.enums.TipoActividadEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Clase;
import ec.com.atikasoft.proteus.modelo.Grupo;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.TareaServicioSMP;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.vo.TareaVO;
import ec.com.atikasoft.proteus.vo.TramiteFormularioVO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "bandejaTareaBean")
@ViewScoped
public class BandejaTareaControlador extends BaseControlador {

    /**
     * Servicio de tarea.
     */
    @EJB
    private TareaServicioSMP tareaServicio;

    /**
     * Url de la pagina.
     */
    public static final String LISTA_ENTIDAD = "/pages/bandeja_tarea/bandeja_tarea.jsf";

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(BandejaTareaControlador.class.getCanonicalName());

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{bandejaTareaHelper}")
    private BandejaTareaHelper bandejaTareaHelper;

    /**
     * Helper de Tramite Validacion.
     */
    @ManagedProperty("#{tramiteValidacionHelper}")
    private TramiteValidacionHelper tramiteValidacionHelper;

    /**
     * Servicio de tramite.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    @EJB
    private AdministracionServicio administracionServicio;

    @Override
    @PostConstruct
    public void init() {
        if(obtenerParametrosFaces().get("__desde_buscar__") == null){
            bandejaTareaHelper.getBusquedaTareaVO().setActividad(TipoActividadEnum.ASIGNADO.getCodigo());            
        }
        buscar();
        bandejaTareaHelper.setVerComentario(Boolean.FALSE);
        bandejaTareaHelper.setGrupoId(null);
        bandejaTareaHelper.setClaseId(null);
        bandejaTareaHelper.setTipoMovimientoId(null);
        cargarGrupos();
        llenarComboClaseFiltros();
    }

    /**
     * Este metodo buscara las tareas segun los parametros ingresados.
     *
     * @return String
     */
    public String buscar() {
        try {
            Boolean x;
            x = getBandejaTareaHelper().getBusquedaTareaVO().getActividad();
            if (x == null) {
                getBandejaTareaHelper().getBusquedaTareaVO().setActividad(Boolean.TRUE);
            }
            bandejaTareaHelper.getListaTareas().clear();
            List<TareaVO> tareas = tareaServicio.buscar(obtenerUsuario(), obtenerUsuarioConectado().
                    getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo(),
                    getBandejaTareaHelper().getBusquedaTareaVO().getActividad(), bandejaTareaHelper.getBusquedaTareaVO().
                    getValor(), getBandejaTareaHelper().getBusquedaTareaVO().getOrdenarPor(),
                    getBandejaTareaHelper().getBusquedaTareaVO().getTipoOrdenamiento());
            bandejaTareaHelper.setListaTareas(tareas);
            bandejaTareaHelper.getListaTareasFiltradas().clear();
            bandejaTareaHelper.getListaTareasFiltradas().addAll(tareas);
            verificarActividad(x);
        } catch (Exception e) {
            LOG.log(Level.INFO, "Error al buscar las tareas {0}", e);
            error(getClass().getName(), "Error al buscar las tareas.", e);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Metodo que se encarga de verificar la actividad si es true muestra los
     * campos si no los oculta.
     *
     * @param actividad Actividad
     */
    public void verificarActividad(Boolean actividad) {
        if (actividad == null) {
            actividad = Boolean.TRUE;
        }
        if (actividad) {
            bandejaTareaHelper.setVerCamposAtendido(Boolean.FALSE);
        } else {
            bandejaTareaHelper.setVerCamposAtendido(Boolean.TRUE);
        }
    }

    /**
     * Este metodo procesara un objeto de tipo tarea y para al formulario
     * respectivo.
     *
     * @return String
     */
    public String atenderTarea() {
        try {
            TramiteFormularioVO vo = tramiteServicio.obtenerTramiteFormulario(bandejaTareaHelper.getTarea());
            bandejaTareaHelper.setTramiteFormularioVO(vo);
            reglaNavegacionDirecta(vo.getFormulario().getUrl());
        } catch (Exception ex) {
            ex.printStackTrace();
            error(getClass().getName(), "Error al iniciar la atecion del tramite.", ex);
        }
        return null;
    }

    /**
     * Metodo que se encarga de dar el formato para la busqueda de la columna de
     * fecha.
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
     * Maneja el evento de seleccion de filtros de la tabla desde el cliente Se
     * encarga de recibir el fitro seleccionado en la tabla y encontrar la
     * gerarquia de seleccion Carga el grupo o clase seleccionado para operar
     * con el
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
            idGrupoStr = parametros.get("frmBandejaTramites:grupo_filtro_seleccionado");
            idGrupo = Long.parseLong(idGrupoStr);
        } catch (Exception e) {
        }
        try {
            idClaseStr = parametros.get("frmBandejaTramites:clase_filtro_seleccionado");
            idClase = Long.parseLong(idClaseStr);
        } catch (Exception e) {
        }
        try {
            idMovimientoStr = parametros.get("frmBandejaTramites:movimiento_filtro_seleccionado");
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

        bandejaTareaHelper.setGrupoId(idGrupo);
        bandejaTareaHelper.setClaseId(idClase);
        bandejaTareaHelper.setTipoMovimientoId(idMovimiento);
        llenarComboClaseFiltros();
    }

    public void cargarGrupos() {
        try {
            iniciarCombosTodos(bandejaTareaHelper.getListaGrupo());
            List<Grupo> lista = administracionServicio.listarTodosVigente();
            for (Grupo g : lista) {
                SelectItem item = new SelectItem(g.getId(), g.getNombre());
                bandejaTareaHelper.getListaGrupo().add(item);
            }
        } catch (ServicioException ex) {
            Logger.getLogger(TramiteBorradorControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Helper para mantener el estado de los filtros de la tabla y la
     * dependencia entre estos sin recargar varias veces la misma, ya que se
     * manejan en el cliente con js Llenar el combo de clases segun un grupo si
     * hay uno seleccionado en filtros Generar la representacion en JSON de
     * estas clases
     */
    public void llenarComboClaseFiltros() {
        try {
            iniciarCombosTodos(bandejaTareaHelper.getListaGrupo());
            iniciarCombosTodos(bandejaTareaHelper.getListaClase());
            iniciarCombosTodos(bandejaTareaHelper.getListaTipoMovimiento());
            List<Grupo> listaGrupos = null;
            List<Clase> listaClase = null;
            List<TipoMovimiento> listaTipoMovimiento = null;
            Long idGrupo = bandejaTareaHelper.getGrupoId();
            Long idClase = bandejaTareaHelper.getClaseId();
            Long idTipoMovimiento = bandejaTareaHelper.getTipoMovimientoId();

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
                bandejaTareaHelper.getListaClase().add(it);
                JSONObject object = new JSONObject();
                object.put("id", c.getId());
                object.put("name", c.getNombre());
                arrayC.add(object);
            }
            data.put("clases", arrayC);
            JSONArray arrayM = new JSONArray();
            for (TipoMovimiento c : listaTipoMovimiento) {
                SelectItem it = new SelectItem(c.getId(), c.getNombre());
                bandejaTareaHelper.getListaTipoMovimiento().add(it);
                JSONObject object = new JSONObject();
                object.put("id", c.getId());
                object.put("name", c.getNombre());
                arrayM.add(object);
            }
            data.put("movimientos", arrayM);
            JSONArray arrayG = new JSONArray();
            for (Grupo c : listaGrupos) {
                SelectItem it = new SelectItem(c.getId(), c.getNombre());
                bandejaTareaHelper.getListaGrupo().add(it);
                JSONObject object = new JSONObject();
                object.put("id", c.getId());
                object.put("name", c.getNombre());
                arrayG.add(object);
            }
            data.put("grupos", arrayG);
            bandejaTareaHelper.setClasesFiltrosSeleccionados(data.toString());
        } catch (ServicioException ex) {
            info(getClass().getName(), "Error al llenar el combo de clase" + ex);
        }
    }

    /**
     * Metodo que se encarga de setear la bandera para poder ver el popup de
     * comentario.
     */
    public void verPopUpComentario() {
        bandejaTareaHelper.setVerComentario(Boolean.TRUE);
    }

    /**
     * @return the bandejaTareaHelper
     */
    public BandejaTareaHelper getBandejaTareaHelper() {
        return bandejaTareaHelper;
    }

    /**
     * @param bandejaTareaHelper the bandejaTareaHelper to set
     */
    public void setBandejaTareaHelper(final BandejaTareaHelper bandejaTareaHelper) {
        this.bandejaTareaHelper = bandejaTareaHelper;
    }

    /**
     * @return the tramiteValidacionHelper
     */
    public TramiteValidacionHelper getTramiteValidacionHelper() {
        return tramiteValidacionHelper;
    }

    /**
     * @param tramiteValidacionHelper the tramiteValidacionHelper to set
     */
    public void setTramiteValidacionHelper(final TramiteValidacionHelper tramiteValidacionHelper) {
        this.tramiteValidacionHelper = tramiteValidacionHelper;
    }
}
