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
import ec.com.atikasoft.proteus.modelo.Instancia;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Acceso a datos de la entidad Instancia.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class InstanciaDao extends GenericDAO<Instancia, Long> {

    /**
     * Constructor sin argumentos.
     */
    public InstanciaDao() {
        super(Instancia.class);
    }

    /**
     * Recupera la instancia dado su identifiador externo.
     *
     * @param identificadorExterno
     * @param descripcion
     * @return
     * @throws DaoException
     */
    public Instancia buscarPorIdentificadorExterno(final Long identificadorExterno) throws
            DaoException {
        try {
            Instancia entidad = null;
            List<Instancia> lista = buscarPorConsultaNombrada(Instancia.BUSCAR_POR_IDENTIFICADOR_EXTERNO,
                    identificadorExterno);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
