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
 *  26/06/2017
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.DesconcentradoUnidadOrganizacional;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@LocalBean
@Stateless
public class DesconcentradosUnidadOrganizacionalDao
        extends GenericDAO<DesconcentradoUnidadOrganizacional, Long> {

    /**
     * Constructor sin argumentos.
     */
    public DesconcentradosUnidadOrganizacionalDao() {
        super(DesconcentradoUnidadOrganizacional.class);
    }

    /**
     * RECUPERA LOS DESCONCENTRADOSUNIDADESORGANIZACIONALES DE ACUERDO AL ID DEL DESCONCERTADO
     *
     * @param desconcentradoId identificador del desconcentrado
     * @return lista de unidades organizacionales
     * @throws DaoException error en acceso a datos
     */
    public List<DesconcentradoUnidadOrganizacional> buscarPorDesconcentradoId(
            Long desconcentradoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoUnidadOrganizacional.BUSCAR_POR_DESCONCENTRADO_ID,
                    desconcentradoId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * RECUPERA LOS DESCONCENTRADOSUNIDADESORGANIZACIONALES DE ACUERDO AL NOMBRE DEL DESCONCERTADO
     *
     * @param desconcentradoNombre nombre del desconcetrado
     * @return lista de unidades organizacionales del desconcentrado
     * @throws DaoException error en acceso a datos
     */
    public List<DesconcentradoUnidadOrganizacional> buscarPorDesconcentradoNombre(
            String desconcentradoNombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoUnidadOrganizacional.BUSCAR_POR_DESCONCENTRADO_NOMBRE, desconcentradoNombre);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * RECUPERA LOS DESCONCENTRADOSUNIDADESORGANIZACIONALES DE ACUERDO AL ID DEL SERVIDOR RESPONSABLE DEL DESCONCENTRADO
     *
     * @param servidorResponsableId identificador del responsable
     * @return lista de unidades organizacionales del desconcentrado
     * @throws DaoException error en acceso a datos
     */
    public List<DesconcentradoUnidadOrganizacional> buscarPorServidorResponsableId(
            Long servidorResponsableId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    DesconcentradoUnidadOrganizacional.BUSCAR_POR_DESCONCENTRADO_SERVIDOR_RESPONSABLE_ID,
                    servidorResponsableId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * RECUPERA LOS DESCONCENTRADOSUNIDADESORGANIZACIONALES DE ACUERDO AL ID DE LA UNIDAD ORGANIZAIONAL
     *
     * @param unidadOrganizacionalId identificador de la unidad organizacional
     * @return lista de unidades organizacionales del desconcentrado
     * @throws DaoException error en acceso a datos
     */
    public List<DesconcentradoUnidadOrganizacional> buscarPorUnidadOrganizacionalId(
            Long unidadOrganizacionalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    DesconcentradoUnidadOrganizacional.BUSCAR_POR_UNIDAD_ORGANIZACIONAL_ID, unidadOrganizacionalId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * RECUPERA LOS DESCONCENTRADOSUNIDADESORGANIZACIONALES DE ACUERDO AL CODIGO DE LA UNIDAD ORGANIZAIONAL
     *
     * @param codigoUnidadOrganizacional codigo de la unidad organizacional
     * @return lista de unidades organizacionales del desconcentrado
     * @throws DaoException error de acceso de datos
     */
    public List<DesconcentradoUnidadOrganizacional> buscarPorCodigoUnidadOrganizacional(
            String codigoUnidadOrganizacional) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    DesconcentradoUnidadOrganizacional.BUSCAR_POR_CODIGO_UNIDAD_ORGANIZACIONAL,
                    codigoUnidadOrganizacional);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @return lista de unidades organizacionales del desconcentrado
     * @throws DaoException error de acceso a datos
     */
    public List<DesconcentradoUnidadOrganizacional> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoUnidadOrganizacional.BUSCAR_VIGENTES);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param desconcentradoId
     * @param unidadOrganizacionalId
     * @return
     * @throws DaoException
     */
    public DesconcentradoUnidadOrganizacional buscar(Long desconcentradoId, Long unidadOrganizacionalId)
            throws DaoException {
        try {
            DesconcentradoUnidadOrganizacional entidad = null;
            List<DesconcentradoUnidadOrganizacional> lista = buscarPorConsultaNombrada(
                    DesconcentradoUnidadOrganizacional.BUSCAR_POR_DESCONCENTRADO_UNIDAD_ORGANIZACIONAL,
                    desconcentradoId, unidadOrganizacionalId);
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

}
