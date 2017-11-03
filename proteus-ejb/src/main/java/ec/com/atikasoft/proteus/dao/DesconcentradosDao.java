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
import ec.com.atikasoft.proteus.modelo.Desconcentrado;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@LocalBean
@Stateless
public class DesconcentradosDao extends GenericDAO<Desconcentrado, Long> {

    /**
     * Constructor sin argumentos.
     */
    public DesconcentradosDao() {
        super(Desconcentrado.class);
    }
    
    /**
     * RECUPERA LOS DESCONCENTRADOS CON NOMBRE IGUAL AL PASADO COMO PARAMETRO
     * @param nombre nombre del desconcetrado
     * @return lista de desconcentrado
     * @throws DaoException error en acceso a datos
     */
    public List<Desconcentrado> buscarPorNombre(String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Desconcentrado.BUSCAR_POR_NOMBRE, "%" + nombre + "%");
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * RECUPERA LOS DESCONCENTRADOS DE ACUERDO AL ID PASADO COMO PARAMETRO
     * @param servidorResponsableId Identificador del responsable
     * @return lista de desconcentrado
     * @throws DaoException error en acceso a datos
     */
    public List<Desconcentrado> buscarPorServidorResponsableId(Long servidorResponsableId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Desconcentrado.BUSCAR_POR_SERVIDOR_RESPONSABLE_ID, servidorResponsableId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * 
     * @return lista de desconcentrados.
     * @throws DaoException error en acceso a datos
     */
    public List<Desconcentrado> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Desconcentrado.BUSCAR_VIGENTES);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
}
