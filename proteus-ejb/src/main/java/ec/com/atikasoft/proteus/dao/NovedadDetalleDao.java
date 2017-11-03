/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.NovedadDetalle;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author atikasoft
 */
@LocalBean
@Stateless
public class NovedadDetalleDao extends GenericDAO<NovedadDetalle, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(NovedadDetalleDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public NovedadDetalleDao() {
        super(NovedadDetalle.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de NovedadDetalle que esten
     * vigentes true.
     *
     * @return Listado NovedadDetalle
     * @throws DaoException En caso de error
     */
    public List<NovedadDetalle> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(NovedadDetalle.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(NovedadDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param NominaId identificador de nomina
     * @return
     * @throws DaoException
     */
    public List<NovedadDetalle> buscarNovedadesDetallesPorNominaId(final long NominaId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(NovedadDetalle.BUSCAR_VIGENTES_POR_NOMINA_ID, NominaId);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param NominaId
     * @throws DaoException
     */
    public void encerarNovedadesDetallesPorNomina(final long NominaId) throws DaoException {
        try {
            ejecutarPorConsultaNombrada(NovedadDetalle.ENCERAR_VIGENTES_POR_NOMINA_ID, NominaId);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
     /**
     *
     * @param nominaId
     * @param servidorId
     * @throws DaoException
     */
    public void encerarNovedadesDetallesPorNominas(final long nominaId,final Long servidorId) throws DaoException {
        try {
            ejecutarPorConsultaNombrada(NovedadDetalle.ENCERAR_VIGENTES_POR_NOMINA_SERVIDOR, nominaId,servidorId);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param novedadId
     * @return
     * @throws DaoException
     */
    public List<NovedadDetalle> buscarNovedadesDetallesPorNovedadId(final long novedadId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(NovedadDetalle.BUSCAR_VIGENTES_POR_NOVEDAD, novedadId);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param institucionEjercicioFiscal
     * @param nominaId
     * @param datoAdicionalId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public List<NovedadDetalle> buscarPorServidorNominaDatoAdicional(final Long institucionEjercicioFiscal,
            final Long nominaId, final Long datoAdicionalId, final Long servidorId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(NovedadDetalle.BUSCAR_POR_SERVIDOR_NOMINA_DATOADICIONAL,
                    institucionEjercicioFiscal, nominaId, datoAdicionalId, servidorId);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param institucionEjercicioFiscal
     * @param nominaId
     * @param datoAdicionalId
     * @param servidorId
     * @param novedadDetalleId
     * @return
     * @throws DaoException
     */
    public List<NovedadDetalle> buscarDuplicados(final Long institucionEjercicioFiscal,
            final Long nominaId, final Long datoAdicionalId, final Long servidorId, final Long novedadDetalleId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(NovedadDetalle.BUSCAR_DUPLICADOS,
                    institucionEjercicioFiscal, nominaId, datoAdicionalId, servidorId, novedadDetalleId);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
