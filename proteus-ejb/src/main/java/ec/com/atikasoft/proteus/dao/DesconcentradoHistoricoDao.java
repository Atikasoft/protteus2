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
import ec.com.atikasoft.proteus.modelo.DesconcentradoHistorico;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@LocalBean
@Stateless
public class DesconcentradoHistoricoDao extends GenericDAO<DesconcentradoHistorico, Long> {

    /**
     * Constructor sin argumentos.
     */
    public DesconcentradoHistoricoDao() {
        super(DesconcentradoHistorico.class);
    }
    
    /**
     * RECUPERA LOS DESCONCENTRADOS HISTORICOS DE ACUERDO AL ID DEL DESCONCERTADO
     * @param desconcentradoId identificador del desconcetrado
     * @return lista de la historia del desconcentrado
     * @throws DaoException error en acceso a datos
     */
    public List<DesconcentradoHistorico> buscarPorDesconcentradoId(Long desconcentradoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoHistorico.BUSCAR_POR_DESCONCENTRADO_ID, desconcentradoId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * RECUPERA LOS DESCONCENTRADOS HISTORICOS DE ACUERDO AL NOMBRE DEL DESCONCERTADO
     * @param desconcentradoNombre nombre del desconcentrado
     * @return historia del desconcentrado
     * @throws DaoException error en acceso a datos
     */
    public List<DesconcentradoHistorico> buscarPorDesconcentradoNombre(
            String desconcentradoNombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoHistorico.BUSCAR_POR_DESCONCENTRADO_NOMBRE, desconcentradoNombre);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * RECUPERA LOS DESCONCENTRADOS HISTORICOS DE ACUERDO AL ID DEL SERVIDOR RESPONSABLE DEL DESCONCENTRADO
     * @param servidorResponsableId identificador del responsable
     * @return historia del desconcentrado
     * @throws DaoException error en acceso a datos
     */
    public List<DesconcentradoHistorico> buscarPorServidorResponsableId(
            Long servidorResponsableId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoHistorico.BUSCAR_POR_SERVIDOR_RESPONSABLE_ID, servidorResponsableId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * 
     * @return historia del desconcentrado.
     * @throws DaoException 
     */
    public List<DesconcentradoHistorico> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoHistorico.BUSCAR_VIGENTES);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
}
