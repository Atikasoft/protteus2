/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.FormularioIRDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.vistas.FormularioIR;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author atikasoft
 */
@Stateless
@LocalBean
public class FormularioIrServicio  extends BaseServicio {
   /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(ServidorServicio.class.getCanonicalName());
    
    @EJB
    private FormularioIRDao formularioIRDao;
    /**
     * Método para buscar servidores para el Impuesto a la Renta según Parámetros
     * @param vo
     * @param firstRow
     * @param numberOfRows
     * @param orderField
     * @param orderDirection
     * @param filters
     * @return
     * @throws ServicioException
     * @throws DaoException 
     */
    public List<FormularioIR> buscarServidores(final BusquedaServidorVO vo, int firstRow,
            int numberOfRows, String orderField, String orderDirection,
            Map<String, String> filters, Long efId, String codigoUA) throws ServicioException, DaoException {
        
        List<FormularioIR> listaFIR = formularioIRDao.buscarServidoresP(vo, 
                firstRow, numberOfRows, orderField, orderDirection, filters, efId, codigoUA);
        return listaFIR;
    }
    /**
     * metodo de conteo de formularios.
     * @param vo
     * @param filters
     * @param efId
     * @param codigoUA
     * @return
     * @throws ServicioException 
     */
    public int contarFormularios(final BusquedaServidorVO vo,
            Map<String, String> filters, Long efId, String codigoUA) throws ServicioException {
        int total = formularioIRDao.contarFormularios(vo, filters, efId, codigoUA);
        return total;
    }
}
