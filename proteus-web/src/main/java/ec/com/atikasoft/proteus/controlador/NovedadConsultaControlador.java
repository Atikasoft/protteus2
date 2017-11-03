/*
 *  NovedadConsultaControlador.java
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
 *  03/01/2014
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.NovedadConsultaHelper;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.enums.EstadoNominaEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.NovedadServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.component.datatable.DataTable;

/**
 * Controlador de Busqueda Puesto.
 *
 *
 */
@ManagedBean(name = "novedadConsultaBean")
@ViewScoped
public class NovedadConsultaControlador extends BaseControlador {

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(NovedadConsultaControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD =
            "/pages/consultas/consulta_novedad.jsf";


    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

     /**
     * Servicio de servidor.
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{novedadConsultaHelper}")
    private NovedadConsultaHelper novedadConsultaHelper;   

    /**
     * Constructor por defecto.
     */
    public NovedadConsultaControlador() {
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
     * @param idServidor String
     * @return String
     */
    public final String obtenerNombreServidor(final Long idServidor) {
        String servidor ="";
        try {
            Servidor s =administracionServicio.buscarServidorPorId(idServidor);
            if(s!=null){
                servidor = s.getNumeroIdentificacion()+" - "+ s.getApellidosNombres();
            }
        } catch (ServicioException ex) {
            error(getClass().getName(), "Error al iniciar las opciones", ex);
        }
        return servidor;
    }

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
            
            novedadConsultaHelper.getConsultaNovedadVO().setDatoAdicionalId(null);
            novedadConsultaHelper.getConsultaNovedadVO().setEjercicioFiscalId(null);
            novedadConsultaHelper.getConsultaNovedadVO().setNovedadNumero(null);
            novedadConsultaHelper.getConsultaNovedadVO().setNominaNumero(null);
            novedadConsultaHelper.getConsultaNovedadVO().setServidorId(null);
            novedadConsultaHelper.getConsultaNovedadVO().setNombresServidor(null);
            novedadConsultaHelper.getConsultaNovedadVO().setNominaEstado(null);
            novedadConsultaHelper.getConsultaNovedadVO().setTipoNominaId(null);
            novedadConsultaHelper.getConsultaNovedadVO().setPeriodoNominaId(null);
            novedadConsultaHelper.getConsultaNovedadVO().setNovedadFechaCreacionDesde(null);
            novedadConsultaHelper.getConsultaNovedadVO().setNovedadFechaCreacionHasta(null);
            novedadConsultaHelper.getConsultaNovedadVO().setNovedadDetalleValorDesde(null);
            novedadConsultaHelper.getConsultaNovedadVO().setNovedadDetalleValorHasta(null);
            
            /**
             * llenar estado nomina.
             */
            getNovedadConsultaHelper().getListaOpcionEstadoNomina().clear();
            iniciarCombosTodos(getNovedadConsultaHelper().getListaOpcionEstadoNomina());
            for (EstadoNominaEnum t : EstadoNominaEnum.values()) {
                getNovedadConsultaHelper().getListaOpcionEstadoNomina().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
            }
            
             /**
             * llenar tipoNomina.
             */
            List<TipoNomina> listaTipoNomina = administracionServicio.listarTodosTipoNominaVigentes();
            iniciarCombosTodos(getNovedadConsultaHelper().getListaOpcionTipoNomina());
            for (TipoNomina t : listaTipoNomina) {
                getNovedadConsultaHelper().getListaOpcionTipoNomina().add(new SelectItem(t.getId(), t.getNombre()));
            }
            
              /**
             * llenar periodo Nomina.
             */
            List<PeriodoNomina> listaPeriodoNomina = administracionServicio.buscarPeriodoNominaPorEjercicio();
            iniciarCombosTodos(getNovedadConsultaHelper().getListaOpcionPeriodoNomina());
            for (PeriodoNomina t : listaPeriodoNomina) {
                getNovedadConsultaHelper().getListaOpcionPeriodoNomina().add(new SelectItem(t.getId(), t.getNombre()));
            }
                       
             iniciarComboDatosAdicionales();

            iniciarCombosTodos(getNovedadConsultaHelper().getListaTipoDocumento());
            getNovedadConsultaHelper().getListaTipoDocumento().add(new SelectItem(
                    TipoDocumentoEnum.CEDULA.getNemonico(),
                    TipoDocumentoEnum.CEDULA.getNombre()));
            getNovedadConsultaHelper().getListaTipoDocumento().add(new SelectItem(
                    TipoDocumentoEnum.PASAPORTE.getNemonico(),
                    TipoDocumentoEnum.PASAPORTE.getNombre()));
            
            
               /**
     * Permite obtener Ejercicios fiscales vigentes.
     */
        List<InstitucionEjercicioFiscal> lista;
            lista = administracionServicio.listarTodosInstitucionesEjercicioFiscalPorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId());
            getNovedadConsultaHelper().getListaOpcionEjercicioFiscal().clear();
//            iniciarCombos(getNovedadConsultaHelper().getListaOpcionEjercicioFiscal());
            for (InstitucionEjercicioFiscal c : lista) {
                if(c.getVigente() && c.getEjercicioFiscal().getVigente()){
                    getNovedadConsultaHelper().getListaOpcionEjercicioFiscal().add(
                            new SelectItem(c.getId(), c.getEjercicioFiscal().getNombre()));
                }
            }
              if(novedadConsultaHelper.getConsultaNovedadVO().getInstitucionEjercicioFiscalId() == null){
                novedadConsultaHelper.getConsultaNovedadVO().setInstitucionEjercicioFiscalId(
                        obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            }
        } catch (ServicioException e) {
            error(getClass().getName(), "Error al iniciar las opciones", e);
        }
    }

     /**
     * Este metodo llena las opciones para el combo de Opciones de Datos Adicionales.
     */
    private void iniciarComboDatosAdicionales() {

        try {
            getNovedadConsultaHelper().getListaOpcionDatoAdicional().clear();
           List<DatoAdicional> lista =    administracionServicio.listarTodosDatosAdicionalesVigentes();
            iniciarCombosConsulta(getNovedadConsultaHelper().getListaOpcionDatoAdicional());

            for (DatoAdicional c : lista) {
                getNovedadConsultaHelper().getListaOpcionDatoAdicional().add(new SelectItem(c.getId(), c.getNombre()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda datos adicionales", ex);
        }

    }
    /**
     * Este método procesa la busqueda de puestos por filtro.
     *
     * @return String
     */
    public String buscarServidores() {
        try {
            BusquedaServidorVO vo = new BusquedaServidorVO();
            getNovedadConsultaHelper().getListaServidores().clear();
            getNovedadConsultaHelper().getListaServidores().addAll(distributivoDetalleDao.buscarServidoresP(
                    vo, null, null, null, null, null));
            ejecutarComandoPrimefaces("dlgServidor.show();");
            actualizarComponente("frmServidor");
        } catch (DaoException e) {
            error(getClass().getName(), "Error al consultar los servidores anticipos.", e);
        }
        
        return null;
    }
       /**
     * actualiza la paginación.Este método procesa la busqueda. Obtiene totales por grupos.
     */
    public void refreshPagination() {
        if(novedadConsultaHelper.getConsultaNovedadVO().getInstitucionEjercicioFiscalId() == null){
                novedadConsultaHelper.getConsultaNovedadVO().setInstitucionEjercicioFiscalId(
                        obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            }
        ((DataTable) FacesContext.getCurrentInstance().getViewRoot().
                findComponent("frmPrincipal").
                findComponent("frmPrincipal:tblListaNovedad")).setFirst(0);
    }


    
        /**
     * Este metodo transacciona la busqueda de la descripcion del estado de la nómina
     * parametro.
     *
     * @param nemonico String
     * @return String
     */
    public final String obtenerDescripcionEstadoNomina(final String nemonico) {           
        return  EstadoNominaEnum.obtenerDescripcion(nemonico);
    }
    /**
     * @return the novedadConsultaHelper
     */
    public NovedadConsultaHelper getNovedadConsultaHelper() {
        return novedadConsultaHelper;
    }

    
    /**
     * @param novedadConsultaHelper the novedadConsultaHelper to set
     */
    public void setNovedadConsultaHelper(NovedadConsultaHelper novedadConsultaHelper) {
        this.novedadConsultaHelper = novedadConsultaHelper;
    }

 

  
}
