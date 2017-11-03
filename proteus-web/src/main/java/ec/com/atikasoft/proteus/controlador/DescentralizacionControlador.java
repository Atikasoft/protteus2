/*
 *  DescentralizacionControlador.java
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
 *  22/02/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_GENERICO;
import ec.com.atikasoft.proteus.controlador.helper.DescentralizacionHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Clase;
import ec.com.atikasoft.proteus.modelo.Grupo;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoDescentralizado;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Controlador de Descentralizacion.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "descentralizacionBean")
@ViewScoped
public class DescentralizacionControlador extends BaseControlador {

    /**
     * Regla de navegacion.
     */
    public static final String CONFIGURACION = "/pages/procesos/descentralizacion/descentralizacion.jsf";
    /**
     * Regla de navegacion.
     */
    public static final String LISTA_TIPO_MOVIMIENTO = "/pages/procesos/descentralizacion/lista_tipo_movimiento.jsf";
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{descentralizacionHelper}")
    private DescentralizacionHelper descentralizacionHelper;

    /**
     * Constructor por defecto.
     */
    public DescentralizacionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        try {

            descentralizacionHelper.getListaTiposMovimientos().clear();
            descentralizacionHelper.getListaTiposMovimientos().addAll(
                    administracionServicio.listarTiposMovimientosOrdenados());
            buscarInstitucionesPorTipoMovimiento();
            llenarUnidadOrganizacional(true);
            descentralizacionHelper.setIdClaseSeleccionada(null);
            descentralizacionHelper.setIdGrupoSeleccionado(null);
            llenarComboGrupo();
            llenarComboClaseFiltros();
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar los tipos de movimientos.", e);
        }
    }

    /**
     * Regla de navegacion.
     *
     * @return String
     */
    public String regresar() {
        return LISTA_TIPO_MOVIMIENTO;
    }

    /**
     * Este metodo busca las instituciones hijas segun la institucion actual.
     *
     * @return String
     */
    public String buscarInstitucionesPorTipoMovimiento() {
        try {

            if (descentralizacionHelper.getTipoMovimientoId() != null) {
                List<TipoMovimientoDescentralizado> buscarTodosTipoMovimientoDescentralizado =
                        administracionServicio.buscarTodosTipoMovimientoDescentralizado(
                        descentralizacionHelper.getTipoMovimientoId(), obtenerUsuarioConectado().getInstitucion().getId());

                descentralizacionHelper.setListaTipoMovimientoDescentralizado(buscarTodosTipoMovimientoDescentralizado);
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar las unidades organizacionales hijas.", e);
        }
        return CONFIGURACION;
    }

    private void llenarListaInicia() {
        try {
            List<UnidadOrganizacional> listarInstitucionesHijas =
                    administracionServicio.listarTodosUnidadOrganizacional();
            descentralizacionHelper.getListaUnidadesOrganizacionales().clear();
            descentralizacionHelper.getListaUnidadesOrganizacionales().addAll(listarInstitucionesHijas);
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar las unidades organizacionales.", e);
        }
    }

    /**
     * Este metodo busca las instituciones hijas y eliminas las existentes.
     *
     * @return String
     */
    public String buscarProcesarUnidadesOrganizacionales() {
        try {

            for (UnidadOrganizacional i : descentralizacionHelper.getListaUnidadesOrganizacionales()) {
                i.setSeleccionado(Boolean.FALSE);
                for (TipoMovimientoDescentralizado d : descentralizacionHelper.getListaTipoMovimientoDescentralizado()) {
                    if (d.getIdUnidadOrganizacional() == i.getId() && i.getVigente()) {
                        i.setSeleccionado(Boolean.TRUE);
                    }
                }
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar las unidades organizacionales.", e);
        }
        return null;
    }

    /**
     * Permite armar el arbol para presentar en la página.
     *
     * @return
     */
    public String llenarUnidadOrganizacional(boolean cargarLista) {
        try {
            TreeNode nodoPrincipal;
            TreeNode nodoPadre, nodoHijo;
            /*
             * carga el primer registro (nodo principal)
             */
            if (cargarLista) {
                llenarListaInicia();
            }
            nodoPrincipal = new DefaultTreeNode(descentralizacionHelper.getListaUnidadesOrganizacionales().get(0), null);
            descentralizacionHelper.setRootUnidadOrganizacional(nodoPrincipal);
            /*
             * cargar los primeros nodos
             */
            nodoPadre = nodoPrincipal;

            for (UnidadOrganizacional un : descentralizacionHelper.getListaUnidadesOrganizacionales()) {
                if (un.getVigente()) {
                    nodoPadre = new DefaultTreeNode(un, nodoPrincipal);
                    /*
                     * cargar los hijos
                     */
                    if (un.getId() != null) {
                        obtenerHijos(un, nodoPadre);
                    }
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
        return null;
    }

    public void obtenerHijos(UnidadOrganizacional registroPadre, TreeNode nodoPadre) {
        try {
            for (UnidadOrganizacional unidad : registroPadre.getListaUnidadesOrganizacionales()) {
                if (unidad.getVigente()) {
                    TreeNode nodoHijo = new DefaultTreeNode(unidad, nodoPadre);
                    if (!unidad.getListaUnidadesOrganizacionales().isEmpty()) {
                        obtenerHijos(unidad, nodoHijo);
                    }
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
    }

    /**
     * Este metodo elimina una unidad organizacional de la tabla
     * descentralizacion.
     *
     * @return String
     */
    public String eliminarUnidadOrganizacional() {
        try {
            iniciarDatosEntidad(descentralizacionHelper.getTipoMovimientoDescentralizado(), Boolean.FALSE);
            descentralizacionHelper.getTipoMovimientoDescentralizado().setVigente(Boolean.FALSE);
            administracionServicio.actualizarTipoMovimientoDescentralizado(
                    descentralizacionHelper.getTipoMovimientoDescentralizado());
            buscarInstitucionesPorTipoMovimiento();
            actualizarComponente("frmMain:tblInstitucionesDesconcentradas");
            ejecutarComandoPrimefaces("confEliminacion.hide();");
        } catch (Exception e) {
            error(getClass().getName(), "Error al eliminar las Unidades.", e);
        }
        return null;
    }

    /**
     * Este metodo agrega las instituciones seleccionadas.
     *
     * @return String
     */
    public String agregarUnidadesOrganizacionales() {
        try {
            List<TipoMovimientoDescentralizado> lista = procesarSeleccionInstituciones();
            if (descentralizacionHelper.getUnidadesSeleccionadas() != null
                    && descentralizacionHelper.getUnidadesSeleccionadas().length > 0 && lista.isEmpty()) {
                mostrarMensajeEnPantalla("Unidades Organizacionales ya agregadas.", FacesMessage.SEVERITY_WARN);
            } else if (lista.isEmpty()) {
                mostrarMensajeEnPantalla("No ha seleccionado ninguna Unidad Organizacional.", FacesMessage.SEVERITY_WARN);

            } else {
                administracionServicio.guardarTiposMovimientosDescentralizados(lista);
                buscarInstitucionesPorTipoMovimiento();
                ejecutarComandoPrimefaces("dlgAgregar.hide();");
                actualizarComponente("frmMain:tblInstitucionesDesconcentradas");
                llenarUnidadOrganizacional(false);
                mostrarMensajeEnPantalla("Unidades Organizacionales agregadas con éxito.", FacesMessage.SEVERITY_INFO);
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error al agregar las unidades.", e);
        }
        return null;
    }

    /**
     * Este metodo procesa la seleccion de instucion en la lista.
     *
     * @return Lista.
     */
    private List<TipoMovimientoDescentralizado> procesarSeleccionInstituciones() {
        List<TipoMovimientoDescentralizado> listaIns = new ArrayList<TipoMovimientoDescentralizado>();
        boolean duplicado;

        if (descentralizacionHelper.getUnidadesSeleccionadas() != null) {
            for (int i = 0; i < descentralizacionHelper.getUnidadesSeleccionadas().length; i++) {
                duplicado = false;
                UnidadOrganizacional uni = (UnidadOrganizacional) descentralizacionHelper.getUnidadesSeleccionadas()[i].getData();
                for (TipoMovimientoDescentralizado tp : descentralizacionHelper.getListaTipoMovimientoDescentralizado()) {
                    if (tp.getIdUnidadOrganizacional().equals(uni.getId())) {
                        duplicado = true;
                    }
                }
                if (!duplicado) {
                    TipoMovimientoDescentralizado insti = new TipoMovimientoDescentralizado();
                    iniciarDatosEntidad(insti, Boolean.TRUE);
                    insti.setTipoMovimiento(descentralizacionHelper.getTipoMovimiento());
                    insti.setIdTipoMovimiento(descentralizacionHelper.getTipoMovimiento().getId());
                    insti.setUnidadOrganizacional(uni);
                    insti.setIdUnidadOrganizacional(uni.getId());
                    insti.setInstitucion(obtenerUsuarioConectado().getInstitucionEjercicioFiscal());
                    insti.setIdInstitucion(insti.getInstitucion().getId());
                    listaIns.add(insti);
                }

            }
        }

        return listaIns;
    }
    
    /**
     * Llena la lista de grupos para el filtro de la tabla
     */
    public void llenarComboGrupo() {
        try {
            iniciarCombos(descentralizacionHelper.getListaGrupos());
            List<Grupo> listaGrupo = administracionServicio.listarTodosVigente();
            for (Grupo g : listaGrupo) {
                descentralizacionHelper.getListaGrupos().add(new SelectItem(g.getId(), g.getNombre()));
            }
        } catch (ServicioException ex) {
            info(getClass().getName(), "Error al llenar el combo de grupo" + ex);
        }
    }
    
    /**
     * Llena la lista de clases necesaria en el filtro de la tabla
     * Arma el json correspondiente para mantener el estado entre los filtros
     * a nivel de cliente
     */
    public void llenarComboClaseFiltros() {
        try {
            iniciarCombos(descentralizacionHelper.getListaClases());
            List<Clase> listaClase = null;
            Long idGrupo = descentralizacionHelper.getIdGrupoSeleccionado();
            Long idClase = descentralizacionHelper.getIdClaseSeleccionada();
            if (idGrupo == null) {
                listaClase = administracionServicio.listarTodosClase();
            } else {
                listaClase = administracionServicio.listarClasePorGrupoId(idGrupo);
            }
            JSONObject data = new JSONObject();
            data.put("grupo", idGrupo);
            data.put("clase", idClase);
            data.put("seleccion",obtenerProperties().
                    getString("ec.gob.mrl.smp.genericos.combo.seleccione"));
            JSONArray array = new JSONArray();
            for (Clase c : listaClase) {
                SelectItem it = new SelectItem(c.getId(), c.getNombre());
                descentralizacionHelper.getListaClases().add(it);
                JSONObject object = new JSONObject();
                object.put("id", c.getId());
                object.put("name", c.getNombre());
                array.add(object);
            }
            data.put("clases", array);
            descentralizacionHelper.setClasesJson(data.toString());
        } catch (ServicioException ex) {
            info(getClass().getName(), "Error al llenar el combo de clase" + ex);
        }
    }
    
    public void llenarComboClaseFiltrosEvent() {
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        DataTable component = (DataTable) view.findComponent(":tiposMovimientosFrom:tablaTiposMovimientos");
        Map<String, String> filters = component.getFilters();
        String idGrupoStr = null;
        String idClaseStr = null;
        for (Map.Entry<String, String> e : filters.entrySet()) {
            if (e.getKey().equals("clase.grupo.id")) {
                idGrupoStr = e.getValue();
            }
            if (e.getKey().equals("clase.id")) {
                idClaseStr = e.getValue();
            }
        }
        if (idGrupoStr != null) {
            descentralizacionHelper.setIdGrupoSeleccionado(Long.parseLong(idGrupoStr));
        }
        if (idClaseStr != null) {
            descentralizacionHelper.setIdClaseSeleccionada(Long.parseLong(idClaseStr));
        }
        llenarComboClaseFiltros();
    }

    /**
     * @return the descentralizacionHelper
     */
    public DescentralizacionHelper getDescentralizacionHelper() {
        return descentralizacionHelper;
    }

    /**
     * @param descentralizacionHelper the descentralizacionHelper to set
     */
    public void setDescentralizacionHelper(final DescentralizacionHelper descentralizacionHelper) {
        this.descentralizacionHelper = descentralizacionHelper;
    }
}
