/*
 *  BusquedaPuestoControlador.java
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
 *  22/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.BusquedaImpuestoRentaHelper;
import ec.com.atikasoft.proteus.dao.InstitucionEjercicioFiscalDao;
import ec.com.atikasoft.proteus.dao.PeriodoNominaDao;
import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.PeriodoNomina;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.component.datatable.DataTable;

/**
 * Conttolador para La busqueda del Impuesto a la Renta
 *
 * @author atikasoft
 */
@ManagedBean(name = "busquedaImpuestoRentaBean")
@ViewScoped
public class BusquedaImpuestoRentaControlador extends BaseControlador {

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(BusquedaImpuestoRentaControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD
            = "/pages/consultas/busqueda_impuesto_renta.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * DAO de Ejercicio fiscal.
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionEjercicioFiscalDao;
    /**
     * Dao de Periodo Nomina.
     */
    @EJB
    private PeriodoNominaDao periodoNominaDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{busquedaImpuestoRentaHelper}")
    private BusquedaImpuestoRentaHelper busquedaImpuestoRentaHelper;

    /**
     * consturctor por defecto.
     */
    public BusquedaImpuestoRentaControlador() {
        super();
    }

    /**
     * @return the busquedaImpuestoRentaHelper
     */
    public BusquedaImpuestoRentaHelper getBusquedaImpuestoRentaHelper() {
        return busquedaImpuestoRentaHelper;
    }

    /**
     * @param busquedaImpuestoRentaHelper the busquedaImpuestoRentaHelper to set
     */
    public void setBusquedaImpuestoRentaHelper(BusquedaImpuestoRentaHelper busquedaImpuestoRentaHelper) {
        this.busquedaImpuestoRentaHelper = busquedaImpuestoRentaHelper;
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param nemonico
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String nemonico) {
        return TipoDocumentoEnum.obtenerNombre(nemonico);
    }

    public final String obtenerEjercicioFiscal(final Long insitucionEjercicioFiscalId) {
        String ejercicio = "";
        try {
            InstitucionEjercicioFiscal ief = institucionEjercicioFiscalDao.buscarPorId(insitucionEjercicioFiscalId);
            ejercicio = ief.getEjercicioFiscal().getNombre();
        } catch (DaoException ex) {
            Logger.getLogger(BusquedaImpuestoRentaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ejercicio;
    }

    private String esObligatorio(String campo) {
        String retorno = "";
        if (campo.equals(CamposConfiguracionEnum.OBLIGATORIO.getCodigo())) {
            retorno = "(*) ";
        }
        return retorno;
    }

    /**
     * actualiza la paginación.
     */
    public void refreshPagination() {
        ((DataTable) FacesContext.getCurrentInstance().
                getViewRoot().
                findComponent("frmPrincipal").
                findComponent("busquedaImpuestoRentaHelper_listaServidores")).setFirst(0);
    }

    /**
     * Método para la navegación.
     *
     * @return String
     */
    public String cancelarBusqueda() {
        reglaNavegacionDirecta(FORMULARIO_ENTIDAD);
        return null;
    }

    /**
     * Este método carga las opciones de seleccion de la pantalla.
     */
    private void iniciarOpciones() {
        try {
            if (obtenerUsuarioConectado() != null) {
                //Periodo fiscal            
                List<InstitucionEjercicioFiscal> ejercicios = institucionEjercicioFiscalDao.buscarPorInstitucion(
                        obtenerUsuarioConectado().getInstitucion().getId());
                busquedaImpuestoRentaHelper.getListaEjercicioFiscal().clear();
                for (InstitucionEjercicioFiscal ef : ejercicios) {
                    busquedaImpuestoRentaHelper.getListaEjercicioFiscal().add(new SelectItem(ef.getId(),
                            ef.getEjercicioFiscal().getNombre()));
                }
                busquedaImpuestoRentaHelper.getBusquedaServidorVO().setIntitucionEjercicioFiscalId(
                        (Long) busquedaImpuestoRentaHelper.getListaEjercicioFiscal().get(0).getValue());
                /**
                 * Poblar unidades organizacionales.
                 */
                List<UnidadOrganizacional> unidades = administracionServicio.listarUnidadOrganizacionalVigente();
                busquedaImpuestoRentaHelper.setListaUnidadesOrganizacionales(unidades);

                iniciarCombosTodos(getBusquedaImpuestoRentaHelper().getListaTipoDocumento());
                getBusquedaImpuestoRentaHelper().getListaTipoDocumento().add(new SelectItem(
                        TipoDocumentoEnum.CEDULA.getNemonico(),
                        TipoDocumentoEnum.CEDULA.getNombre()));
                getBusquedaImpuestoRentaHelper().getListaTipoDocumento().add(new SelectItem(
                        TipoDocumentoEnum.PASAPORTE.getNemonico(),
                        TipoDocumentoEnum.PASAPORTE.getNombre()));

                //Poblar Periodos.
                iniciarCombosTodos(busquedaImpuestoRentaHelper.getListaPeriodos());
                List<PeriodoNomina> periodos = periodoNominaDao.buscarVigente();
                for (PeriodoNomina perNom : periodos) {
                    busquedaImpuestoRentaHelper.getListaPeriodos().add(new SelectItem(perNom.getId(),
                            perNom.getNombre()));
                }
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar las opciones", e);
        }
    }

    @PostConstruct
    @Override
    public void init() {
        iniciarOpciones();
        busquedaImpuestoRentaHelper.setBusquedaServidorVO(new BusquedaServidorVO());
    }

    /**
     * Método imprimir.
     *
     * @return String
     * @throws DaoException
     */
    public void imprimirFormulario107() {
        try {
            setReporte(ReportesEnum.PROTEUS_FORMULARIO_107.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("p_titulo", "FORMULARIO 107");
            parametrosReporte.put("__format", "pdf");
            Long InstitucionEjercicioFiscalId = busquedaImpuestoRentaHelper.getBusquedaServidorVO().
                    getIntitucionEjercicioFiscalId();
            Long pEjercicioFiscalId = institucionEjercicioFiscalDao.buscarPorId(InstitucionEjercicioFiscalId).
                    getEjercicioFiscal().getId();
            String pUnidadAministrativaCod = busquedaImpuestoRentaHelper.getUnidadOrganizacionalCodigo();
            String pTipoDocumentoId = busquedaImpuestoRentaHelper.getBusquedaServidorVO().getTipoDocumentoServidor();
            String pNumeroDocumento = busquedaImpuestoRentaHelper.getBusquedaServidorVO().getNumeroDocumentoServidor();
            String pNombresApellidos = busquedaImpuestoRentaHelper.getBusquedaServidorVO().getNombreServidor();
            if (pEjercicioFiscalId != null) {
                parametrosReporte.put("p_ejerFiscal", pEjercicioFiscalId.toString());
            }
            if (pUnidadAministrativaCod != null) {
                parametrosReporte.put("p_unidadAdmin", pUnidadAministrativaCod);
            }
            if (pTipoDocumentoId != null && pTipoDocumentoId.length() > 0) {
                parametrosReporte.put("p_tipoDoc", pTipoDocumentoId);
            }
            if (pNumeroDocumento != null && pNumeroDocumento.length() > 0) {
                parametrosReporte.put("p_numDoc", pNumeroDocumento);
            }
            if (pNombresApellidos != null && pNombresApellidos.length() > 0) {
                parametrosReporte.put("p_nombres", pNombresApellidos);
            }
            generarReporte();
        } catch (Exception e) {
            error(getClass().getName(), "error al generar reporte de formulario 107 "
                    + "UATH" + e.getMessage(), e);
        }

        //return null;
    }

    /**
     * Método imprimir.
     *
     * @return String
     * @throws DaoException
     */
    public void imprimirIRPeriodos() {
        try {
            setReporte(ReportesEnum.PROTEUS_IMPUESTO_RENTA_PERIODO.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("p_titulo", "REPORTE IMPUESTO A LA RENTA POR PERIODOS");
            parametrosReporte.put("__format", "pdf");
            Long InstitucionEjercicioFiscalId = busquedaImpuestoRentaHelper.getBusquedaServidorVO().
                    getIntitucionEjercicioFiscalId();
            Long pEjercicioFiscalId = institucionEjercicioFiscalDao.buscarPorId(InstitucionEjercicioFiscalId).
                    getEjercicioFiscal().getId();
            String pTipoDocumentoId = busquedaImpuestoRentaHelper.getBusquedaServidorVO().getTipoDocumentoServidor();
            String pNumeroDocumento = busquedaImpuestoRentaHelper.getBusquedaServidorVO().getNumeroDocumentoServidor();
            String pNombresApellidos = busquedaImpuestoRentaHelper.getBusquedaServidorVO().getNombreServidor();
            Long pPeriodoId = busquedaImpuestoRentaHelper.getPeriodoId();
            String pUnidadAministrativaCod = busquedaImpuestoRentaHelper.getUnidadOrganizacionalCodigo();
            if (pEjercicioFiscalId != null) {
                parametrosReporte.put("p_ejerFiscal", pEjercicioFiscalId.toString());
            }
            if (pTipoDocumentoId != null && pTipoDocumentoId.length() > 0) {
                parametrosReporte.put("p_tipoDoc", pTipoDocumentoId);
            }
            if (pNumeroDocumento != null && pNumeroDocumento.length() > 0) {
                parametrosReporte.put("p_numDoc", pNumeroDocumento);
            }
            if (pNombresApellidos != null && pNombresApellidos.length() > 0) {
                parametrosReporte.put("p_nombres", pNombresApellidos);
            }
            if (pPeriodoId != null) {
                parametrosReporte.put("p_periodo", pPeriodoId.toString());
            }
            if (pUnidadAministrativaCod != null) {
                parametrosReporte.put("p_unidadAdmin", pUnidadAministrativaCod);
            }
            generarReporte();
        } catch (Exception e) {
            error(getClass().getName(), "error al generar reporte de impuesto a la renta por periodos "
                    + "UATH" + e.getMessage(), e);
        }

        //return null;
    }

    @Override
    public void generarReporte() {
        try {
            generarUrlDeReporte();
        } catch (Exception e) {
            error(getClass().getName(), "No tiene accion de personal" + e.getMessage(), e);
        }
    }
}
