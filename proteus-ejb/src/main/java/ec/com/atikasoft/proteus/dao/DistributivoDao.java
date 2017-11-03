/*
 *  ClaseDao.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.distributivo.Distributivo;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Dao de distributivo.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class DistributivoDao extends GenericDAO<Distributivo, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(DistributivoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public DistributivoDao() {
        super(Distributivo.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @param like
     * @return List
     * @throws DaoException DaoException
     */
    public List<Distributivo> buscarTodosPorNombre(final String nombre, final boolean like) throws DaoException {
        try {
            if (like) {
                return buscarPorConsultaNombrada(Distributivo.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre.toUpperCase(), "%"));
            } else {
                return buscarPorConsultaNombrada(Distributivo.BUSCAR_POR_NOMBRE, UtilCadena.concatenar(nombre.toUpperCase()));
            }
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Distributivo que esten
     * vigentes true.
     *
     * @return Listado
     * @throws DaoException En caso de error
     */
    public List<Distributivo> buscarVigente() throws DaoException {
        try {
            List<Distributivo> lista = buscarPorConsultaNombrada(Distributivo.BUSCAR_VIGENTES);
            return lista;
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar el ultimo valor asignado a la partida
     * presupuestaria.
     *
     * @param id
     * @return MAXIMO contador de la partida presupuestaria
     * @throws DaoException En caso de error
     */
    public Long buscarContadorPartida(Long id) throws DaoException {
        try {
            List<Distributivo> lista = buscarPorConsultaNombrada(Distributivo.BUSCAR_SECUENCIA_PARTIDA_POR_ID, id);
            if (!lista.isEmpty()) {
                return lista.get(0).getContadorPartida();
            } else {
                return 0L;
            }

        } catch (DaoException ex) {
            Logger.getLogger(DistributivoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param unidadOrganizacional
     * @param modalidadLaboral
     * @param institucionEsjercicioFiscalId
     * @return
     * @throws DaoException
     */
    public Distributivo buscar(final String unidadOrganizacional, final String modalidadLaboral, final Long institucionEsjercicioFiscalId) throws DaoException {
        try {
            Distributivo entidad = null;
            List<Distributivo> lista = buscarPorConsultaNombrada(
                    Distributivo.BUSCAR_POR_MODALIDAD_Y_UNIDAD_ORGANIZACIONAL, unidadOrganizacional, modalidadLaboral,
                    institucionEsjercicioFiscalId);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
        /**
     *
     * @param unidadOrganizacionalId
     * @param modalidadLaboralId
     * @param institucionEsjercicioFiscalId
     * @return
     * @throws DaoException
     */
    public Distributivo buscar(final Long unidadOrganizacionalId, final Long modalidadLaboralId, final Long institucionEsjercicioFiscalId) throws DaoException {
        try {
            Distributivo entidad = null;
            List<Distributivo> lista = buscarPorConsultaNombrada(
                    Distributivo.BUSCAR_POR_MODALIDAD_ID_Y_UNIDAD_ORGANIZACIONAL_ID, unidadOrganizacionalId, modalidadLaboralId,
                    institucionEsjercicioFiscalId);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param unidadOrganizacional
     * @param institucionEsjercicioFiscalId
     * @return
     * @throws DaoException
     */
    public List<Distributivo> buscar(final String unidadOrganizacional, final Long institucionEsjercicioFiscalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Distributivo.BUSCAR_POR_UNIDAD_ORGANIZACIONAL, unidadOrganizacional, institucionEsjercicioFiscalId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

}
