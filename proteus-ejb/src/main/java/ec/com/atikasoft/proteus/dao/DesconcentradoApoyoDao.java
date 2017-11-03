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
import ec.com.atikasoft.proteus.modelo.DesconcentradoApoyo;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@LocalBean
@Stateless
public class DesconcentradoApoyoDao extends GenericDAO<DesconcentradoApoyo, Long> {

    /**
     * Constructor sin argumentos.
     */
    public DesconcentradoApoyoDao() {
        super(DesconcentradoApoyo.class);
    }
    
    /**
     * RECUPERA LOS DESCONCENTRADOSAPOYOS DE ACUERDO AL ID DEL DESCONCENTRADO ESTEN VIGENTES O NO
     * @param desconcentradoId identificador desconcentrado
     * @return lista de apoyos
     * @throws DaoException error en acceso a datos
     */
    public List<DesconcentradoApoyo> buscarTodosPorDesconcentradoId(Long desconcentradoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoApoyo.BUSCAR_TODOS_POR_DESCONCENTRADO_ID, desconcentradoId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * RECUPERA LOS DESCONCENTRADOSAPOYOS DE ACUERDO AL ID DEL DESCONCENTRADO
     * @param desconcentradoId identificado del desconcentrado
     * @return lista de apoyos
     * @throws DaoException  error en acceso a datos
     */
    public List<DesconcentradoApoyo> buscarPorDesconcentradoId(Long desconcentradoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoApoyo.BUSCAR_POR_DESCONCENTRADO_ID, desconcentradoId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * RECUPERA LOS DESCONCENTRADOSAPOYOS DE ACUERDO AL NOMBRE DEL DESCONCERTADO
     * @param desconcentradoNombre nombre del desconcentrado
     * @return lista de apoyos
     * @throws DaoException error en acceso a datos
     */
    public List<DesconcentradoApoyo> buscarPorDesconcentradoNombre(
            String desconcentradoNombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoApoyo.BUSCAR_POR_DESCONCENTRADO_NOMBRE, desconcentradoNombre);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * RECUPERA LOS DESCONCENTRADOSAPOYOS DE ACUERDO AL ID DEL SERVIDOR RESPONSABLE DEL DESCONCENTRADO
     * @param servidorResponsableId identificador del responsables
     * @return lista de apoyos
     * @throws DaoException error en acceso a datos
     */
    public List<DesconcentradoApoyo> buscarPorServidorResponsableId(
            Long servidorResponsableId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoApoyo.
                    BUSCAR_POR_DESCONCENTRADO_SERVIDOR_RESPONSABLE_ID, servidorResponsableId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * RECUPERA LOS DESCONCENTRADOSAPOYOS DE ACUERDO AL ID DEL SERVIDOR RESPONSABLE DEL DESCONCENTRADO
     * @param servidorApoyoId identificador del apoyo
     * @return lista de apoyos
     * @throws DaoException error en acceso a datos
     */
    public List<DesconcentradoApoyo> buscarPorServidorApoyoId(Long servidorApoyoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoApoyo.BUSCAR_POR_SERVIDOR_APOYO_ID, servidorApoyoId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * 
     * @return lista de apoyos
     * @throws DaoException 
     */
    public List<DesconcentradoApoyo> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(DesconcentradoApoyo.BUSCAR_VIGENTES);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
}
