/*
 *  CampoAccesoDao.java
 *
 *  Quito - Ecuador
 *
 *   04/10/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.CampoAcceso;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class CampoAccesoDao extends GenericDAO<CampoAcceso, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(CampoAccesoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public CampoAccesoDao() {
        super(CampoAcceso.class);
    }
 /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<CampoAcceso> buscarTodosPorNombre(final String nombre, final boolean exacto) throws DaoException {
        String nombreBuscar= nombre;
        if (!exacto){
            nombreBuscar = UtilCadena.concatenar("%", nombre,
                    "%");
        }
        try {
            return buscarPorConsultaNombrada(CampoAcceso.BUSCAR_POR_NOMBRE,nombreBuscar );
        } catch (DaoException ex) {
            Logger.getLogger(AlertaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Este metodo procesa la busqueda de todo los registros vigentes
     * por la tabla a la cual pertenezcan a la tabla
     * 
     * @param idTabla clave primaria de la metadata tabla
     * @return List Lista de registros de Campos de Acceso
     * @throws DaoException DaoException
     */
    public List<CampoAcceso> buscarCampoAccesoPorTabla(final Long idTabla) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CampoAcceso.BUSCAR_POR_TABLA, idTabla);
        } catch (DaoException ex) {
            Logger.getLogger(CampoAccesoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
       /**
     * Este metodo procesa la busqueda de todo los registros vigentes
     * por la tabla a la cual pertenezcan a la tabla
     * 
     * @param id clave primaria del campo acceso
     * @return List de registros de Campos de Acceso
     * @throws DaoException DaoException
     */
    public List<CampoAcceso> buscarCampoAccesoPorId(final Long id) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CampoAcceso.BUSCAR_POR_ID, id);
        } catch (DaoException ex) {
            Logger.getLogger(CampoAccesoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

      /**
     * Este metodo procesa la busqueda de todo los registros vigentes
     * por la tabla a la cual pertenezcan a la columna
     * 
     * @param idColumna clave primaruia de la metadata columna
     * @return List Lista de registros de Campos de Acceso
     * @throws DaoException DaoException
     */
    public List<CampoAcceso> buscarCampoAccesoPorColumna(final Long idColumna) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CampoAcceso.BUSCAR_POR_COLUMNA, idColumna);
        } catch (DaoException ex) {
            Logger.getLogger(CampoAccesoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Metodo que se encarga de buscar un listado de CampoAcceso que esten vigentes true.
     *
     * @return Listado CampoAcceso
     * @throws DaoException En caso de error
     */
    public List<CampoAcceso> buscarCampoAccesoVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(CampoAcceso.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(CampoAccesoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
      /**
     * Metodo que se encarga de buscar un listado de CampoAcceso que esten vigentes true 
     * por tipo : Nomina o Tipo Movimiento.
     *
     * @return Listado CampoAcceso
     * @throws DaoException En caso de error
     */
    public List<CampoAcceso> buscarCampoAccesoVigentePorTipo(final String tipo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CampoAcceso.BUSCAR_POR_TIPO, tipo);
        } catch (DaoException ex) {
            Logger.getLogger(CampoAccesoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}