/*
 *  AsignacionDao.java
 *  ESIPREN V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of MRL
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with MRL.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Oct 21, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Proceso;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Acceso a datos de la entidad Proceso.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ProcesoDao extends GenericDAO<Proceso, Long> {

    /**
     * Constructor sin argumentos.
     */
    public ProcesoDao() {
        super(Proceso.class);
    }

    /**
     * Recupera un proceso especifico dado su codigo.
     *
     * @param codigo Codigo del proceso.
     * @return Proceso
     * @throws DaoException Error en capa de persistencia.
     */
    public Proceso buscarPorCodigo(final String codigo) throws DaoException {
        try {
            Proceso entidad = null;
            List<Proceso> lista = buscarPorConsultaNombrada(Proceso.BUSCAR_POR_CODIGO, codigo);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
