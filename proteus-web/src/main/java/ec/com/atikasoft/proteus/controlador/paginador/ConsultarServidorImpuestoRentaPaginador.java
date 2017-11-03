/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.paginador;

import ec.com.atikasoft.proteus.controlador.helper.BusquedaImpuestoRentaHelper;
import ec.com.atikasoft.proteus.dao.InstitucionEjercicioFiscalDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.vistas.FormularioIR;
import ec.com.atikasoft.proteus.pagination.base.PaginacionBase;
import ec.com.atikasoft.proteus.servicio.FormularioIrServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.vo.BusquedaImpuestoRentaVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.SortOrder;

/**
 * paginacion y busqueda para consulta de servidores del Impuesto a la Renta.
 *
 * @author atikasoft
 */
@ManagedBean(name = "consultaServidorImpuestoRentaPaginador")
@ViewScoped
public class ConsultarServidorImpuestoRentaPaginador extends PaginacionBase<BusquedaImpuestoRentaVO> {

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{busquedaImpuestoRentaHelper}")
    private BusquedaImpuestoRentaHelper busquedaImpuestoRentaHelper;

    /**
     *
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     *
     */
    @EJB
    private FormularioIrServicio formularioIrServicio;

    /**
     *
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionEjercicioFiscalDao;

    /**
     *
     */
    @PostConstruct
    public void init() {

    }

    @Override
    protected List<BusquedaImpuestoRentaVO> loadData(int firstRow, int numberOfRows, String orderField,
            SortOrder orderDirection, Map<String, String> filters) {
        List<BusquedaImpuestoRentaVO> servidoresIR = new ArrayList<>();
        try {
            Long efid = 1L;
            Long iefid = busquedaImpuestoRentaHelper.getBusquedaServidorVO().getIntitucionEjercicioFiscalId();
            if (iefid != null) {
                efid = institucionEjercicioFiscalDao.buscarPorId(iefid).getEjercicioFiscal().getId();
            }
            List<FormularioIR> listaFormularios = formularioIrServicio.buscarServidores(getBusquedaImpuestoRentaHelper().
                    getBusquedaServidorVO(), firstRow, numberOfRows, orderField, orderDirection.name(), filters,
                    efid, busquedaImpuestoRentaHelper.getUnidadOrganizacionalCodigo());
            for (FormularioIR fir : listaFormularios) {
                Servidor s = servidorServicio.buscarServidor(fir.getTipoIdentificacion(),
                        fir.getNumeroIdentificacion());
                if (s != null) {
                    BusquedaImpuestoRentaVO busquedaIRVO = new BusquedaImpuestoRentaVO();
                    busquedaIRVO.setFormularioIR(fir);
                    busquedaIRVO.setServidor(s);
                    servidoresIR.add(busquedaIRVO);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ConsultarServidorImpuestoRentaPaginador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return servidoresIR;
    }

    @Override
    protected int countRecords(Map<String, String> filters) {
        int totalRegistros = 0;
        try {
            Long efid = 1L;
            Long iefid = busquedaImpuestoRentaHelper.getBusquedaServidorVO().getIntitucionEjercicioFiscalId();
            if (iefid != null) {
                efid = institucionEjercicioFiscalDao.buscarPorId(iefid).getEjercicioFiscal().getId();
            }
            totalRegistros = formularioIrServicio.contarFormularios(getBusquedaImpuestoRentaHelper().
                    getBusquedaServidorVO(), filters, efid,
                    busquedaImpuestoRentaHelper.getUnidadOrganizacionalCodigo());
        } catch (ServicioException ex) {
            Logger.getLogger(ConsultaServidorPaginacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DaoException ex) {
            Logger.getLogger(ConsultarServidorImpuestoRentaPaginador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalRegistros;
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

}
