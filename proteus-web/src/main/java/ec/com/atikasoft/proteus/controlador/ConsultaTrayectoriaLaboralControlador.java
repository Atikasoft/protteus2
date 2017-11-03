/*
 *  ServidorControlador.java
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
 *  10/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.comparadores.TrayectoriaLaboralComparador;
import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.ConsultaTrayectoriaLaboralHelper;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.TrayectoriaLaboral;
import ec.com.atikasoft.proteus.servicio.TrayectoriaLaboralServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.ConsultaTrayectoriaLaboralVO;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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

/**
 * ServidorControlador
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "consultaTrayectoriaLaboralBean")
@ViewScoped
public class ConsultaTrayectoriaLaboralControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ConsultaTrayectoriaLaboralControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/servidor/registro_firma.jsf";

    /**
     *
     */
    @EJB
    private TrayectoriaLaboralServicio trayectoriaLaboralServicio;

    /**
     *
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{consultaTrayectoriaLaboralHelper}")
    private ConsultaTrayectoriaLaboralHelper consultaTrayectoriaLaboralHelper;

    /**
     *
     */
    private int tabViewIndex;

    /**
     * Constructor por defecto.
     */
    public ConsultaTrayectoriaLaboralControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        iniciarComboTipo();
        consultaTrayectoriaLaboralHelper.getMovimiento().setNombres("");
        consultaTrayectoriaLaboralHelper.getMovimiento().setApellidos("");
        consultaTrayectoriaLaboralHelper.getConsultaTrayectoriaLaboralVO().setTipoDocumentoServidor("");
        consultaTrayectoriaLaboralHelper.setTipoIdentificacion("");
        iniciarComboTipo();
        consultaTrayectoriaLaboralHelper.setNumeroIdentificacion("");
        consultaTrayectoriaLaboralHelper.getConsultaTrayectoriaLaboralVO().setFechaDesde(null);
        consultaTrayectoriaLaboralHelper.getConsultaTrayectoriaLaboralVO().setFechaHasta(null);
        consultaTrayectoriaLaboralHelper.getListaMovimientos().clear();
        consultaTrayectoriaLaboralHelper.setGenerarTeporte(false);
    }

    /**
     * combo del tipo de documento.
     */
    private void iniciarComboTipo() {
        iniciarCombos(consultaTrayectoriaLaboralHelper.getTipoDocumento());
        consultaTrayectoriaLaboralHelper.getTipoDocumento().add(new SelectItem(TipoDocumentoEnum.CEDULA.getNemonico(),
                TipoDocumentoEnum.CEDULA.getNombre()));
        consultaTrayectoriaLaboralHelper.getTipoDocumento().add(new SelectItem(
                TipoDocumentoEnum.PASAPORTE.getNemonico(),
                TipoDocumentoEnum.PASAPORTE.getNombre()));
    }

    public void validarCampoDisable() {
        if (consultaTrayectoriaLaboralHelper.getTipoIdentificacion() != null) {
            consultaTrayectoriaLaboralHelper.setTipoCedula(Boolean.FALSE);
        } else {
            consultaTrayectoriaLaboralHelper.setTipoCedula(Boolean.TRUE);
        }
    }

    /**
     * Este metodo lista la nomina.
     *
     * @return String
     */
    public String buscarMovimientos() {
        try {
            if (consultaTrayectoriaLaboralHelper.getConsultaTrayectoriaLaboralVO().getNumeroDocumentoServidor() != null) {
                consultaTrayectoriaLaboralHelper.setListaMovimientos(
                        trayectoriaLaboralServicio.buscarMovimientosTrayectoriaLaboral(
                                consultaTrayectoriaLaboralHelper.getConsultaTrayectoriaLaboralVO()));
                Collections.sort(consultaTrayectoriaLaboralHelper.getListaMovimientos(),
                        new TrayectoriaLaboralComparador());
                if (consultaTrayectoriaLaboralHelper.getListaMovimientos() != null
                        && !consultaTrayectoriaLaboralHelper.getListaMovimientos().isEmpty()) {
                    consultaTrayectoriaLaboralHelper.setGenerarTeporte(true);
                } else {
                    consultaTrayectoriaLaboralHelper.setGenerarTeporte(false);
                }
                actualizarComponente("frmPrincipal:btnReporte");
            } else {
                mostrarMensajeEnPantalla("Por favor ingrese algún criterio de búsqueda", FacesMessage.SEVERITY_ERROR);
            }
        } catch (ServicioException ex) {
            error(this.getClass().getName(), "Error al buscar movimientos por filtro", ex);
        }
        return null;
    }

    /**
     * metodo para buscar un servidor segun su Id.
     *
     * @return
     */
    public String buscarPorCedula() {
        try {
            if (consultaTrayectoriaLaboralHelper.getNumeroIdentificacion() != null && !consultaTrayectoriaLaboralHelper.getNumeroIdentificacion().trim().isEmpty()) {
                if (consultaTrayectoriaLaboralHelper.getTipoIdentificacion().equals(TipoDocumentoEnum.CEDULA.getNemonico())
                        || consultaTrayectoriaLaboralHelper.getTipoIdentificacion().equals(TipoDocumentoEnum.PASAPORTE.getNemonico())) {

                    consultaTrayectoriaLaboralHelper.setServidorSeleccionado(servidorDao.buscar(consultaTrayectoriaLaboralHelper.getTipoIdentificacion(), consultaTrayectoriaLaboralHelper.getNumeroIdentificacion()));

                    if (consultaTrayectoriaLaboralHelper.getServidorSeleccionado() == null) {
                        ponerMensajeError(NADA, "No existe servidor con la identificación ingresada");
                    } else {
                        procesarSeleccion();
                    }
                }

            } else {
                ponerMensajeError(NADA, "No ha ingresado el número de identificación");
            }

        } catch (Exception ex) {
            Logger.getLogger(ConsultaTrayectoriaLaboralControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * metodo para buscar un servidor segun su nombre.
     *
     * @return
     */
    public String buscarPorNombre() {
        try {
            if (consultaTrayectoriaLaboralHelper.getFiltroNombre() != null && !consultaTrayectoriaLaboralHelper.getFiltroNombre().trim().isEmpty()) {

                List<Servidor> servidores = servidorDao.buscarPorNombre(consultaTrayectoriaLaboralHelper.getFiltroNombre());
                if (servidores == null) {
                    //ponerMensajeError(NADA, "No existen servidores con la identificación ingresada");
                    mostrarMensajeEnPantalla("No existen servidores con el nombre ingresado", FacesMessage.SEVERITY_INFO);
                } else {
                    consultaTrayectoriaLaboralHelper.setServidoresEncontrados(servidores);
                    ejecutarComandoPrimefaces("dlgResultadosNombre.show();");
                }

            } else {
                //ponerMensajeError(NADA, "No ha ingresado el nombre por el que desea realizar la búsqueda");
                mostrarMensajeEnPantalla("No ha ingresado el nombre por el que desea realizar la búsqueda", FacesMessage.SEVERITY_ERROR);
            }

        } catch (Exception ex) {
            Logger.getLogger(ConsultaTrayectoriaLaboralControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Busqueda de servidor segun filtros
     */
    public void buscar() {
        consultaTrayectoriaLaboralHelper.getListaMovimientos().clear();
        consultaTrayectoriaLaboralHelper.setGenerarTeporte(false);
        actualizarComponente("frmPrincipal:tblListaNominas");
        if (tabViewIndex == 1) {
            buscarPorNombre();
        } else {
            buscarPorCedula();
        }
    }

    /**
     * Borra los filtros al cambiar de tab
     */
    public void limpiarTab() {
        consultaTrayectoriaLaboralHelper.setTipoIdentificacion(null);
        consultaTrayectoriaLaboralHelper.setNumeroIdentificacion(null);
        consultaTrayectoriaLaboralHelper.setFiltroNombre(null);
        consultaTrayectoriaLaboralHelper.setConsultaTrayectoriaLaboralVO(new ConsultaTrayectoriaLaboralVO());
        consultaTrayectoriaLaboralHelper.getListaMovimientos().clear();
        consultaTrayectoriaLaboralHelper.setGenerarTeporte(false);
        actualizarComponente("frmPrincipal:tblListaNominas");
        actualizarComponente("frmPrincipal:tabViewId:numeroDocumento");
        actualizarComponente("frmPrincipal:tabViewId:tipoDocumento");
        actualizarComponente("frmPrincipal:tabViewId:apellidoNombreFilter");
        actualizarComponente("frmPrincipal:apellidoNombre");
        actualizarComponente("frmPrincipal:btnReporte");
    }

    /**
     * Obtiene los valores necesarios para la busqueda de la trayectoria del servidor seleccionado de la lista de
     * servidores
     */
    public void procesarSeleccion() {
        consultaTrayectoriaLaboralHelper.getConsultaTrayectoriaLaboralVO().
                setNumeroDocumentoServidor(consultaTrayectoriaLaboralHelper.getServidorSeleccionado().
                        getNumeroIdentificacion());
        consultaTrayectoriaLaboralHelper.getConsultaTrayectoriaLaboralVO().
                setTipoDocumentoServidor(consultaTrayectoriaLaboralHelper.getServidorSeleccionado().
                        getTipoIdentificacion());
        consultaTrayectoriaLaboralHelper.getConsultaTrayectoriaLaboralVO().
                setApellidosNombresServidor(consultaTrayectoriaLaboralHelper.getServidorSeleccionado().
                        getApellidosNombres());
        if (consultaTrayectoriaLaboralHelper.getMovimiento() == null) {
            consultaTrayectoriaLaboralHelper.setMovimiento(new TrayectoriaLaboral());
            consultaTrayectoriaLaboralHelper.getMovimiento().setNombres("");
            consultaTrayectoriaLaboralHelper.getMovimiento().setApellidos("");
        }/* else {
         consultaTrayectoriaLaboralHelper.setGenerarTeporte(true);
         }*/

    }

    /**
     * Este metodo busca la descripcion de un grupo segun el codigo.
     *
     * @param grupo
     * @return
     */
    public String nombreGrupo(String grupo) {
        return GrupoEnum.obtenerDescripcion(grupo);
    }

    @Override
    public void generarReporte() {
        ConsultaTrayectoriaLaboralVO vo = consultaTrayectoriaLaboralHelper.getConsultaTrayectoriaLaboralVO();
        TrayectoriaLaboral tl = consultaTrayectoriaLaboralHelper.getMovimiento();
        StringBuilder nSb = new StringBuilder(tl.getApellidos());
        nSb.append(" ").append(tl.getNombres());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fd;
        String fh;
        String tipoDocumento = TipoDocumentoEnum.obtenerNombre(vo.getTipoDocumentoServidor());
        tipoDocumento = tipoDocumento == null ? vo.getTipoDocumentoServidor() : tipoDocumento;

        if (vo.getFechaDesde() == null) {
            fd = sdf.format(UtilFechas.sumarAnios(new Date(), -10));
        } else {
            fd = sdf.format(vo.getFechaDesde());
        }
        if (vo.getFechaHasta() == null) {
            fh = sdf.format(new Date());
        } else {
            fh = sdf.format(vo.getFechaHasta());
        }
        setReporte(ReportesEnum.PROTEUS_CONSULTA_TRAYECTORIA_LABORAL.getReporte());
        parametrosReporte = new HashMap<>();
        parametrosReporte.put("p_titulo", "CONSULTA TRAYECTORIA LABORAL");
        parametrosReporte.put("fecha_hasta", fh);
        parametrosReporte.put("fecha_desde", fd);
        parametrosReporte.put("__format", "pdf");
        parametrosReporte.put("s_t_doc", vo.getTipoDocumentoServidor());
        parametrosReporte.put("s_t_doc_nombre", tipoDocumento);
        parametrosReporte.put("s_doc", vo.getNumeroDocumentoServidor());
        parametrosReporte.put("s_nombres", tl.getNombres());
        generarUrlDeReporte();
    }

    /**
     * @return the consultaTrayectoriaLaboralHelper
     */
    public ConsultaTrayectoriaLaboralHelper getConsultaTrayectoriaLaboralHelper() {
        return consultaTrayectoriaLaboralHelper;
    }

    /**
     * @param consultaTrayectoriaLaboralHelper the consultaTrayectoriaLaboralHelper to set
     */
    public void setConsultaTrayectoriaLaboralHelper(ConsultaTrayectoriaLaboralHelper consultaTrayectoriaLaboralHelper) {
        this.consultaTrayectoriaLaboralHelper = consultaTrayectoriaLaboralHelper;
    }

    /**
     *
     * @return
     */
    public int getTabViewIndex() {
        return tabViewIndex;
    }

    /**
     *
     * @param tabViewIndex
     */
    public void setTabViewIndex(int tabViewIndex) {
        this.tabViewIndex = tabViewIndex;
    }

}
