/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.ComprobanteRetencionImpuestoRentaDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.ComprobanteRetencionImpuestoRenta;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Servicio para el trabajo con Formularios 107
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
@Stateless
@LocalBean
public class ComprobanteRetencionIRServicio extends BaseServicio{
    
    @EJB
    private ComprobanteRetencionImpuestoRentaDao comprobanteRetencionImpuestoRentaDao;
    
    /**
     * Obtiene una lista de formularios 107 dado el ejericio fiscal y opcionalmente los nombres y apellidos de servidor y su numero de indentificacion
     * ordenada por fecha de registro y servidor
     * @param ejecicioFiscalId
     * @param apellidosNombre
     * @param numeroIdentificacion
     * @return
     * @throws ServicioException 
     */
    public List<ComprobanteRetencionImpuestoRenta> busquedaComprobanteRetencion(final Long ejecicioFiscalId, 
            final String apellidosNombre, final String numeroIdentificacion) throws ServicioException {
        try {
            return comprobanteRetencionImpuestoRentaDao.buscar(ejecicioFiscalId, apellidosNombre, numeroIdentificacion);
        } catch (DaoException ex) {
            Logger.getLogger(NominaServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }
    
    /**
     * Periste un nuevo Fomulario 107
     * @param comprobanteRetencionImpuestoRenta
     * @throws ServicioException 
     */
    public void crearComprobanteRetencionImpuestoRenta(final ComprobanteRetencionImpuestoRenta comprobanteRetencionImpuestoRenta) throws ServicioException{
        try {
            comprobanteRetencionImpuestoRentaDao.crear(comprobanteRetencionImpuestoRenta);
        } catch (DaoException ex) {
            Logger.getLogger(NominaServicio.class.getName()).log(Level.SEVERE, null, ex);
             throw new ServicioException(ex);
        }
    }
    
    /**
     * Actualiza el estado de un Formulario 107
     * @param comprobanteRetencionImpuestoRenta
     * @throws ServicioException 
     */
    public void actualizarComprobanteRetencionImpuestoRenta(final ComprobanteRetencionImpuestoRenta comprobanteRetencionImpuestoRenta) throws ServicioException{
        try {
            comprobanteRetencionImpuestoRentaDao.actualizar(comprobanteRetencionImpuestoRenta);
        } catch (DaoException ex) {
            Logger.getLogger(NominaServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }
    
    /**
     * borrar un formulario 107
     * @param comprobanteRetencionImpuestoRenta
     * @throws ServicioException 
     */
    public void eliminarComprobanteRetencionImpuestoRenta(final ComprobanteRetencionImpuestoRenta comprobanteRetencionImpuestoRenta) throws ServicioException{
        comprobanteRetencionImpuestoRenta.setVigente(Boolean.FALSE);
        actualizarComprobanteRetencionImpuestoRenta(comprobanteRetencionImpuestoRenta);
    }
    
    /**
     * Buscar por ID
     * @param id
     * @return
     * @throws ServicioException 
     */
    public ComprobanteRetencionImpuestoRenta buscar(final Long id) throws ServicioException{
        try {
            return comprobanteRetencionImpuestoRentaDao.buscarPorId(id);
        } catch (DaoException ex) {
            Logger.getLogger(ComprobanteRetencionIRServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }
}
