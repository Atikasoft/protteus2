package ec.com.atikasoft.proteus.controlador.paginador;

import ec.com.atikasoft.proteus.controlador.helper.BusquedaServidorHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.pagination.base.PaginacionBase;
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
@ManagedBean(name = "consultaServidorPaginacion")
@ViewScoped
public class ConsultaServidorPaginacion extends PaginacionBase<Servidor> {

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{busquedaServidorHelper}")
    private BusquedaServidorHelper busquedaServidorHelper;
    @EJB
    private ServidorServicio servidorServicio;

    public Long contadorRegistrosPaginacion = 0L;

    @PostConstruct
    public void init() {
        busquedaServidorHelper.setContadorRegistrosPaginacion(0L);
    }

    @Override
    protected List<Servidor> loadData(int firstRow, int numberOfRows,
            String orderField, SortOrder orderDirection, Map<String, String> filters) {
        List<Servidor> servidores = new ArrayList<Servidor>();
        try {
            servidores = servidorServicio.buscarServidores(busquedaServidorHelper.
                    getBusquedaServidorVO(), firstRow, numberOfRows, orderField, orderDirection.name(), filters);
            busquedaServidorHelper.setContadorRegistrosPaginacion(busquedaServidorHelper.getContadorRegistrosPaginacion() + servidores.size());
        } catch (ServicioException ex) {
            Logger.getLogger(ConsultaServidorPaginacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return servidores;
    }

    @Override
    protected int countRecords(Map<String, String> filters) {

        int totalRegistros = 0;

        try {
            totalRegistros = servidorServicio.contarServidores(busquedaServidorHelper.
                    getBusquedaServidorVO(), filters);

            System.out.println("total de registros en el contador de servidores***********************" + totalRegistros);
            busquedaServidorHelper.setTotalRegistro(totalRegistros);
        } catch (ServicioException ex) {
            Logger.getLogger(ConsultaServidorPaginacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalRegistros;

    }

    /**
     * @param busquedaServidorHelper the busquedaServidorHelper to set
     */
    public void setBusquedaServidorHelper(BusquedaServidorHelper busquedaServidorHelper) {
        this.busquedaServidorHelper = busquedaServidorHelper;
    }
}
