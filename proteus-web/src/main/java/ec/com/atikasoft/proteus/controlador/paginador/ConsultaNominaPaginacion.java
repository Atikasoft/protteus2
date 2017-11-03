package ec.com.atikasoft.proteus.controlador.paginador;

import ec.com.atikasoft.proteus.controlador.helper.ResultadoNominaHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.pagination.base.PaginacionBase;
import ec.com.atikasoft.proteus.servicio.NominaServicio;
import ec.com.atikasoft.proteus.vo.ResultadoNominaVO;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Paginacion para el listado de titulos con errores
 */
@ManagedBean(name = "consultaNominaPaginacion")
@ViewScoped
public class ConsultaNominaPaginacion extends PaginacionBase<ResultadoNominaVO> {

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{resultadoNominaHelper}")
    private ResultadoNominaHelper resultadoNominaHelper;
    @EJB
    private NominaServicio nominaServicio;
 /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(ResultadoNominaVO.class.getCanonicalName());
    @PostConstruct
    public void init() {

    }
    /**
     * Obtiene los registros a presentar en la datatable.
     * @param firstRow primer registro
     * @param numberOfRows final del registro
     * @param orderField
     * @param orderDirection
     * @param filters
     * @return 
     */
    @Override
    protected List<ResultadoNominaVO> loadData(int firstRow, int numberOfRows,
            String orderField, SortOrder orderDirection, Map<String, String> filters) {
        List<ResultadoNominaVO> resultadoNominaVO = new ArrayList<ResultadoNominaVO>();
        try {
            resultadoNominaVO = (nominaServicio.
                    listarResultadoNomina(getResultadoNominaHelper().getResultadoNomina(),
                            firstRow, numberOfRows));
            resultadoNominaHelper.setListaResultadoNomina(resultadoNominaVO);
            //Obtener los totales.
            ResultadoNominaVO vo = nominaServicio.buscarTotalesResultadoNomina(getResultadoNominaHelper().getResultadoNomina());
            resultadoNominaHelper.getResultadoNomina().setTotalIngresos(vo.getTotalIngresos());
            resultadoNominaHelper.getResultadoNomina().setTotalDescuentos(vo.getTotalDescuentos());
            resultadoNominaHelper.getResultadoNomina().setTotalAportePatronal(vo.getTotalAportePatronal());
            resultadoNominaHelper.getResultadoNomina().setTotalLiquidoAPagar(vo.getTotalLiquidoAPagar());
        } catch (ServicioException ex) {
            Logger.getLogger(ConsultaNominaPaginacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoNominaVO;
    }

    
    /**
     * Permite contar el total de registros para para segmentar en la datatable.
     * @return 
     */
    @Override
    protected int countRecords(Map<String, String> filters) {

        int totalRegistros = 0;
        try {
            totalRegistros = nominaServicio.contarResultadoNomina(getResultadoNominaHelper().getResultadoNomina());
        } catch (ServicioException ex) {
            Logger.getLogger(ConsultaNominaPaginacion.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return totalRegistros;

    }

    /**
     * @return the resultadoNominaHelper
     */
    public ResultadoNominaHelper getResultadoNominaHelper() {
        return resultadoNominaHelper;
    }

    /**
     * @param resultadoNominaHelper the resultadoNominaHelper to set
     */
    public void setResultadoNominaHelper(ResultadoNominaHelper resultadoNominaHelper) {
        this.resultadoNominaHelper = resultadoNominaHelper;
    }
}
