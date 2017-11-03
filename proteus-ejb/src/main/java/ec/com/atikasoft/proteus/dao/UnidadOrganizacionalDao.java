package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/*
 * UnidadOrganizacionalDao.java
 * Proteus V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved.
 * This software is the confidential and proprietary information of Proteus
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into
 * with Proteus.
 *
 * Proteus
 * Quito - Ecuador
 * 
 *
 * 26/09/2013
 *
 */
/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@LocalBean
@Stateless
public class UnidadOrganizacionalDao extends GenericDAO<UnidadOrganizacional, Long> {

    /**
     * Constructor por defecto.
     */
    public UnidadOrganizacionalDao() {
        super(UnidadOrganizacional.class);
    }

    public List<UnidadOrganizacional> buscarActivosOrdenados() throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadOrganizacional.BUSCAR_ORDENADA);
        } catch (Exception ex) {
            Logger.getLogger(UnidadOrganizacionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    public List<UnidadOrganizacional> buscarPorIdUbicacion(final Long idUbicacionGeografica) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadOrganizacional.BUSCAR_ID_UBICACION, idUbicacionGeografica);
        } catch (DaoException ex) {
            Logger.getLogger(UnidadOrganizacionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    public List<UnidadOrganizacional> buscarPorNivel(final Long nivel, final Long id) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadOrganizacional.BUSCAR_POR_NIVEL, nivel, id);
        } catch (Exception ex) {
            Logger.getLogger(UnidadOrganizacionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nemonico.
     *
     * @param codigo String
     * @return List
     * @throws DaoException DaoException
     */
    public List<UnidadOrganizacional> buscarPorNemonico(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadOrganizacional.BUSCAR_POR_NEMONICO, codigo);
        } catch (Exception ex) {
            Logger.getLogger(UnidadOrganizacionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nemonico.
     *
     * @param codigo String
     * @return List
     * @throws DaoException DaoException
     */
    public List<UnidadOrganizacional> buscarPorCodigoLike(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadOrganizacional.BUSCAR_POR_CODIGO, UtilCadena.concatenar(codigo, "%"));
        } catch (Exception ex) {
            Logger.getLogger(UnidadOrganizacionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo busca undiades organizacionales cuyo codigo inicie con alguno de los valores pasados por parametros.
     *
     * @param codigos String
     * @return List
     * @throws DaoException DaoException
     */
    public List<UnidadOrganizacional> buscarPorCodigoLikeAnidado(final String codigos) throws DaoException {
        try {
            StringBuilder sb = new StringBuilder("Select * from sch_proteus.unidades_organizacionales WHERE ");
            int i = 0;
            for (String codigo : codigos.split(";")) {
                if (i == 0) {
                    sb.append("codigo like '").append(codigo).append("%'");
                } else {
                    sb.append(" OR codigo like '").append(codigo).append("%'");
                }
                i++;
            }
            sb.append(" ORDER BY ruta");
            Query q = getEntityManager().createNativeQuery(sb.toString(), UnidadOrganizacional.class);
            List<UnidadOrganizacional> resultado = q.getResultList();
            return resultado;
                    
        } catch (Exception ex) {
            Logger.getLogger(UnidadOrganizacionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List de Variables
     * @throws DaoException DaoException
     */
    public List<UnidadOrganizacional> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    UnidadOrganizacional.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre, "%"));
        } catch (Exception ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todos los registros vigentes
     *
     * @return List de Variables
     * @throws DaoException DaoException
     */
    public List<UnidadOrganizacional> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadOrganizacional.BUSCAR_VIGENTES);
        } catch (Exception ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera la unidad organizacion de agrupacion.
     *
     * @param unidadOrganizacionalId
     * @return
     * @throws ServicioException
     */
    private UnidadOrganizacional buscarAgrupador(final Long unidadOrganizacionalId) throws ServicioException {
        try {
            UnidadOrganizacional uoAgrupador = null;
            UnidadOrganizacional uo = buscarPorId(unidadOrganizacionalId);
            while (uo != null) {
                if (uo.getEsAgrupador()) {
                    uoAgrupador = uo;
                    break;
                } else {
                    uo = uo.getUnidadOrganizacional();
                }
            }
            return uoAgrupador;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }
    
    /**
     * Recuepera las Undiades Organizacionales que tienen asigando un reloj determinado
     * @param relojId
     * @return
     * @throws DaoException 
     */
    public List<UnidadOrganizacional> buscarPorRelojId(final Long relojId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadOrganizacional.BUSCAR_POR_RELOJ_ID, relojId);
        } catch (Exception ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

}
