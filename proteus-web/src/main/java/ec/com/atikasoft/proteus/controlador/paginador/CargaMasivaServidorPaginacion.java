package ec.com.atikasoft.proteus.controlador.paginador;

import ec.com.atikasoft.proteus.controlador.helper.CargaMasivaAtributoServidorHelper;
import ec.com.atikasoft.proteus.pagination.base.PaginacionBase;
import ec.com.atikasoft.proteus.vo.CargaMasivaServidorVO;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Paginacion de registros de carga masiva de atributos de servidores
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
@ManagedBean(name = "cargaMasivaAtributosServidorPaginacion")
@ViewScoped
public class CargaMasivaServidorPaginacion extends PaginacionBase<CargaMasivaServidorVO> {

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{cargaMasivaAtributoServidorHelper}")
    private CargaMasivaAtributoServidorHelper cargaMasivaAtributoServidorHelper;

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(CargaMasivaServidorPaginacion.class.getCanonicalName());

    @PostConstruct
    public void init() {

    }

    /**
     * Obtiene los registros a presentar en la datatable.
     *
     * @param firstRow primer registro
     * @param numberOfRows final del registro
     * @param orderField
     * @param orderDirection
     * @param filters
     * @return
     */
    @Override
    protected List<CargaMasivaServidorVO> loadData(int firstRow, int numberOfRows,
            String orderField, SortOrder orderDirection, Map<String, String> filters) {
        if(cargaMasivaAtributoServidorHelper.getDatos().isEmpty()){
            return new ArrayList<CargaMasivaServidorVO>();
        }
        
        int toIndex = firstRow + numberOfRows;
        if (toIndex > cargaMasivaAtributoServidorHelper.getDatos().size()) {
            toIndex = cargaMasivaAtributoServidorHelper.getDatos().size();
        }
        return cargaMasivaAtributoServidorHelper.getDatos().subList(firstRow, toIndex);
    }

    @Override
    public Object getRowKey(CargaMasivaServidorVO object) {
        return object.getId();
    }
    
    

    /**
     *
     * @param filters
     * @return
     */
    @Override
    protected int countRecords(Map<String, String> filters) {
        return cargaMasivaAtributoServidorHelper.getDatos().size();
    }

    public void setCargaMasivaAtributoServidorHelper(CargaMasivaAtributoServidorHelper cargaMasivaAtributoServidorHelper) {
        this.cargaMasivaAtributoServidorHelper = cargaMasivaAtributoServidorHelper;
    }
}
