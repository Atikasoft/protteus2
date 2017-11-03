/**
 * BusquedaPuestoControlador.java Proteus V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the confidential and proprietary information of
 * Proteus ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Proteus.
 *
 * Proteus Quito - Ecuador
 *
 *
 * 22/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.BusquedaVacionHelper;
import ec.com.atikasoft.proteus.controlador.helper.TramiteHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.enums.EstadoVacacionEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionEnum;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * Controlador de Busqueda Puesto.
 *
 *
 */
@ManagedBean(name = "busquedaVacacionBean")
@ViewScoped
public class BusquedaVacacionControlador extends BaseControlador {

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(BusquedaVacacionControlador.class.getCanonicalName());
    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/tramite/busqueda_puesto.jsf";
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;
    /**
     * Servicio de vacacionServicio.
     */
    @EJB
    private VacacionServicio vacacionServicio;

    /**
     *
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{busquedaVacionHelper}")
    private BusquedaVacionHelper busquedaVacionHelper;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tramiteHelper}")
    private TramiteHelper tramiteHelper;

    /**
     * Constructor por defecto.
     */
    public BusquedaVacacionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        iniciarOpciones();

    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param nemonico String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String nemonico) {
        return TipoDocumentoEnum.obtenerNombre(nemonico);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param nemonico String
     * @return String
     */
    public final String obtenerDescripcionEstadoPlanificacion(final String nemonico) {
        return EstadoVacacionEnum.obtenerDescripcion(nemonico);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param nemonico String
     * @return String
     */
    public final String obtenerDescripcionTipoSolicitud(final String nemonico) {
        return TipoVacacionEnum.obtenerNombre(nemonico);
    }

    /**
     *
     * @param campo
     * @return
     */
    private String esObligatorio(String campo) {
        String retorno = "";
        if (campo.equals(CamposConfiguracionEnum.OBLIGATORIO.getCodigo())) {
            retorno = "(*) ";
        }
        return retorno;
    }

    /**
     * Método para la navegación.
     *
     * @return String
     */
    public String cancelarBusqueda() {
        reglaNavegacionDirecta(TramiteControlador.FORMULARIO_ENTIDAD.concat("?est=edt"));
        return null;
    }

    /**
     * Este método carga las opciones de seleccion de la pantalla.
     */
    private void iniciarOpciones() {
        try {
            iniciarComboTipoVacacion();
            iniciarComboEstadoVacacion();
            busquedaVacionHelper.getListaSolicitudesVacaciones().clear();
            busquedaVacionHelper.getBusquedaVacacionVO().setUnidadAdministrativaId(null);
            busquedaVacionHelper.getBusquedaVacacionVO().setUnidadAdministrativaNombre(null);
            busquedaVacionHelper.getBusquedaVacacionVO().setEstadoVacacion(null);
            busquedaVacionHelper.getBusquedaVacacionVO().setTipoDocumentoServidor(null);
            busquedaVacionHelper.getBusquedaVacacionVO().setTipoVacacion(null);
            busquedaVacionHelper.getBusquedaVacacionVO().setFechaInicioPlanificacio(null);
            busquedaVacionHelper.getBusquedaVacacionVO().setFechaFinPlanificacio(null);
            busquedaVacionHelper.getBusquedaVacacionVO().setNombreServidor(null);
            busquedaVacionHelper.getBusquedaVacacionVO().setNumeroDocumentoServidor(null);
            busquedaVacionHelper.getBusquedaVacacionVO().setTipoDocumentoServidor(null);
            /**
             * Poblar estado de servidor.
             */
            List<EstadoPersonal> estadosPersonal = administracionServicio.listarTodosEstadoPersonalPorNombre(null);
            iniciarCombosTodos(getBusquedaVacionHelper().getListaEstadoPersonal());
            for (EstadoPersonal estado : estadosPersonal) {
                getBusquedaVacionHelper().getListaEstadoPersonal().add(new SelectItem(estado.getId(),
                        estado.getNombre()));
            }
            /**
             * buscar los ejercicios fiscales.
             */
            getBusquedaVacionHelper().getListaEjercicioFiscal();
            iniciarCombosTodos(getBusquedaVacionHelper().getListaEjercicioFiscal());
            List<InstitucionEjercicioFiscal> institucionEjercicioFiscal
                    = administracionServicio.listarTodosInstitucionesEjercicioFiscalPorInstitucion(obtenerUsuarioConectado().getInstitucion().getId());
            for (InstitucionEjercicioFiscal institucion : institucionEjercicioFiscal) {
                getBusquedaVacionHelper().getListaEjercicioFiscal().add(new SelectItem(institucion.getEjercicioFiscal().getId(),
                        institucion.getEjercicioFiscal().getNombre()));
            }

            /**
             * Poblar unidades organizacionales.
             */
            List<UnidadOrganizacional> unidades = administracionServicio.listarUnidadOrganizacionalVigente();
            busquedaVacionHelper.setListaUnidadesOrganizacionales(unidades);
            /**
             * tipo documento
             */
            iniciarCombosTodos(getBusquedaVacionHelper().getListaTipoDocumento());
            getBusquedaVacionHelper().getListaTipoDocumento().add(new SelectItem(
                    TipoDocumentoEnum.CEDULA.getNemonico(),
                    TipoDocumentoEnum.CEDULA.getNombre()));
            getBusquedaVacionHelper().getListaTipoDocumento().add(new SelectItem(
                    TipoDocumentoEnum.PASAPORTE.getNemonico(),
                    TipoDocumentoEnum.PASAPORTE.getNombre()));

        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar las opciones", e);
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo vacacion.
     */
    private void iniciarComboTipoVacacion() {
        iniciarCombos(busquedaVacionHelper.getListaOpcionesTipoVacacion());
        for (TipoVacacionEnum t : TipoVacacionEnum.values()) {
            busquedaVacionHelper.getListaOpcionesTipoVacacion().add(new SelectItem(t.getCodigo(), t.getNombre()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de estado vacacion.
     */
    private void iniciarComboEstadoVacacion() {
        iniciarCombos(busquedaVacionHelper.getListaOpcionesEstadoVacacion());
        for (EstadoVacacionEnum t : EstadoVacacionEnum.values()) {
            if (!t.getCodigo().equals(EstadoVacacionEnum.VALIDADO.getCodigo())) {
                busquedaVacionHelper.getListaOpcionesEstadoVacacion().add(new SelectItem(t.getCodigo(),
                        t.getDescripcion()));
            }

        }
    }

    /**
     * Este método procesa la busqueda de puestos por filtro.
     *
     * @return String
     */
    public String buscarVaciones() {
        try {
            busquedaVacionHelper.setListaSolicitudesVacaciones(vacacionServicio.buscar(busquedaVacionHelper.getBusquedaVacacionVO()));
            busquedaVacionHelper.setActivo("1");
            actualizarComponente("accordionPanel");
        } catch (Exception e) {
            error(getClass().getName(), "Error al consular las vacaciones.", e);
        }
        return null;
    }

    /**
     *
     */
    public void buscarListaSaldosVacaciones() {
        try {
            busquedaVacionHelper.getListaSaldoVacaciones().clear();
            Servidor s = servidorDao.buscar(busquedaVacionHelper.getBusquedaVacacionVO().getTipoDocumentoServidor(),
                    busquedaVacionHelper.getBusquedaVacacionVO().getNumeroDocumentoServidor());
            Vacacion v = vacacionServicio.buscarVacacion(obtenerUsuarioConectado().getInstitucion().getId(), s.getId());
            if (v != null) {
                busquedaVacionHelper.getListaSaldoVacaciones().add(v);
            }
            ejecutarComandoPrimefaces("dlgDetalles.show();");
            busquedaVacionHelper.getBusquedaVacacionVO().setTipoDocumentoServidor(null);
            busquedaVacionHelper.getBusquedaVacacionVO().setTipoDocumentoServidor(null);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda saldo vacacion ", ex);
        }
    }

    /**
     * @return the busquedaVacionHelper
     */
    public BusquedaVacionHelper getBusquedaVacionHelper() {
        return busquedaVacionHelper;
    }

    /**
     * @param busquedaVacionHelper the busquedaVacionHelper to set
     */
    public void setBusquedaVacionHelper(final BusquedaVacionHelper busquedaVacionHelper) {
        this.busquedaVacionHelper = busquedaVacionHelper;
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

    /**
     *
     * @return
     */
    public StreamedContent generarArchivoSaldosVacaciones() {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());

            String nombre = "SALDOS_VACACIONES_" + (new SimpleDateFormat("_yyyy-MM-dd_HH:mm").format(new Date())) + ".xls";
            InputStream stream = vacacionServicio.generarSaldosVacaciones(obtenerUsuarioConectado().
                    getInstitucion().getId(), obtenerUsuarioConectado(), esRRHH(pi.getValorTexto()));
            StreamedContent sc = new DefaultStreamedContent(
                    stream, "application/vnd.ms-excel", nombre);

            return sc;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
}
