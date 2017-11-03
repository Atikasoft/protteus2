package ec.com.atikasoft.proteus.controlador.paginador;

import ec.com.atikasoft.proteus.controlador.helper.NovedadConsultaHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.vistas.VistaNovedad;
import ec.com.atikasoft.proteus.pagination.base.PaginacionBase;
import ec.com.atikasoft.proteus.servicio.NovedadServicio;
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
@ManagedBean(name = "consultaNovedadPaginacion")
@ViewScoped
public class ConsultaNovedadPaginacion extends PaginacionBase<VistaNovedad> {

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{novedadConsultaHelper}")
    private NovedadConsultaHelper novedadConsultaHelper;
    @EJB
    private NovedadServicio novedadServicio;
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
    protected List<VistaNovedad> loadData(int firstRow, int numberOfRows,
            String orderField, SortOrder orderDirection, Map<String, String> filters) {
        List<VistaNovedad> lista = new ArrayList<VistaNovedad>();
        try {
           
            novedadConsultaHelper.getConsultaNovedadVO().setInicioConsulta(firstRow);
            novedadConsultaHelper.getConsultaNovedadVO().setFinConsulta(numberOfRows);
            lista = (novedadServicio.
                    buscar(novedadConsultaHelper.getConsultaNovedadVO()));
            LOG.log(Level.INFO, "==========> tama\u00f1o de la lista en la consulta, tamaño de la lista obtenida:{0}", lista.size());
        } catch (ServicioException ex) {
            Logger.getLogger(ConsultaNovedadPaginacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    
    /**
     * Permite contar el total de registros para para segmentar en la datatable.
     * @return 
     */
    @Override
    protected int countRecords(Map<String, String> filters) {

        int totalRegistros = 0;
        try {
            totalRegistros = novedadServicio.contarTotalNovedades(novedadConsultaHelper.getConsultaNovedadVO());
           LOG.log(Level.INFO, "===========>totalRegistros en paginacion:{0}", totalRegistros);
        } catch (ServicioException ex) {
            Logger.getLogger(ConsultaNovedadPaginacion.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return totalRegistros;

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
