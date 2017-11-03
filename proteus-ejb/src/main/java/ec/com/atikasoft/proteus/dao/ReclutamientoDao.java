
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
import ec.com.atikasoft.proteus.modelo.Reclutamiento;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@markasoft.ec>
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class ReclutamientoDao extends GenericDAO<Reclutamiento, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ReclutamientoDao.class.getName());

    /**
     * Constructor sin argumentos.
     */
    public ReclutamientoDao() {
        super(Reclutamiento.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return List
     * @throws DaoException DaoException
     */
    public Reclutamiento buscar(final String tipoIdentificacion, final String numeroIdentificacion) throws DaoException {
        try {
            Reclutamiento entidad = null;
            List<Reclutamiento> lista = buscarPorConsultaNombrada(Reclutamiento.BUSCAR_POR_IDENTIFICACION,
                    tipoIdentificacion,
                    numeroIdentificacion);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por tipo y numero de
     * identificacion, y estado.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param estado
     * @return List
     * @throws DaoException DaoException
     */
    public Reclutamiento buscar(final String tipoIdentificacion, final String numeroIdentificacion, final String estado) throws DaoException {
        try {
            Reclutamiento entidad = null;
            List<Reclutamiento> lista = buscarPorConsultaNombrada(Reclutamiento.BUSCAR_POR_IDENTIFICACION_YESTADO,
                    tipoIdentificacion,
                    numeroIdentificacion, estado);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Permite buscar por número de identificación.
     *
     * @param identificacion
     * @return
     * @throws DaoException
     */
    public Reclutamiento buscarPorIdentificacion(final String identificacion) throws
            DaoException {
        try {

            Reclutamiento entidad = null;
            List<Reclutamiento> lista = buscarPorConsultaNombrada(
                    Reclutamiento.BUSCAR_POR_NUMERO_IDENTIFICACION, identificacion);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    public List<Reclutamiento> buscarPorNumeroIdentificacion(final String numeroIdentificacion) throws
            DaoException {
        try {
            List<Reclutamiento> lista = buscarPorConsultaNombrada(
                    Reclutamiento.BUSCAR_POR_NUMERO_IDENTIFICACION, numeroIdentificacion);
            return lista;
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    public List<Reclutamiento> buscarPorEscalaModalidadUnidad(final Long escala,
            final Long modalidad, final Long unidad) throws
            DaoException {
        try {
            List<Reclutamiento> lista = buscarPorConsultaNombrada(
                    Reclutamiento.BUSCAR_POR_ESCALA_MODALIDAD_UNIDAD, escala, modalidad, unidad);
            return lista;
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<Reclutamiento> buscarPorNombre(final String nombre) throws
            DaoException {
        try {
            List<Reclutamiento> lista = buscarPorConsultaNombrada(
                    Reclutamiento.BUSCAR_POR_NOMBRE_SERVIDOR, UtilCadena.concatenar("%", nombre.toUpperCase(), "%"));
            return lista;
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre y número de
     * identificacion.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<Reclutamiento> buscarPorNombreYIdentificacion(final String nombre, final String numeroIdentificacion)
            throws
            DaoException {
        try {
            List<Reclutamiento> lista = buscarPorConsultaNombrada(
                    Reclutamiento.BUSCAR_POR_NOMBRE_Y_IDENTIFICACION_SERVIDOR, UtilCadena.concatenar("%", nombre.
                            toUpperCase(), "%"), numeroIdentificacion);
            return lista;
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda de registros a través de los atributos:
     * tipo de doc.,numero de doc., modalidad laboral,escala ocupacional, unidad
     * organizacional, institucion,estado = R
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param idModalidad
     * @param idEscalaOcupacional
     * @param idUnidadOrgan
     * @param idInstitucion
     * @param estado
     * @return List
     * @throws DaoException DaoException
     */
    public List<Reclutamiento> buscarPorDatosMovimientoPersonal(final String tipoIdentificacion,
            final String numeroIdentificacion,
            final Long idModalidad, final Long idEscalaOcupacional, final Long idUnidadOrgan, final Long idInstitucion,
            final String estado) throws
            DaoException {
        try {
            List<Reclutamiento> lista = buscarPorConsultaNombrada(
                    Reclutamiento.BUSCAR_DESDE_MOVIM_PERSONAL, tipoIdentificacion, numeroIdentificacion, idModalidad,
                    idEscalaOcupacional, idUnidadOrgan, idInstitucion, estado);
            return lista;
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param distributivoDetalleId
     * @return
     * @throws DaoException
     */
    public Reclutamiento buscar(final Long distributivoDetalleId) throws DaoException {
        try {
            Reclutamiento entidad = null;
            List<Reclutamiento> lista = buscarPorConsultaNombrada(Reclutamiento.BUSCAR_POR_DISTRIBUTIVO,
                    distributivoDetalleId);
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
     * @param distributivoDetalleId
     * @param estado
     * @return
     * @throws DaoException
     */
    public Reclutamiento buscar(final Long distributivoDetalleId, final String estado) throws DaoException {
        try {
            Reclutamiento entidad = null;
            List<Reclutamiento> lista = buscarPorConsultaNombrada(Reclutamiento.BUSCAR_POR_DISTRIBUTIVO_Y_ESTADO, distributivoDetalleId, estado);
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
     * @param codigoPuesto
     * @return
     * @throws DaoException
     */
    public List<Reclutamiento> buscarPorCodigoPuesto(final Long codigoPuesto) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Reclutamiento.BUSCAR_POR_CODIGO_PUESTO, codigoPuesto);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
