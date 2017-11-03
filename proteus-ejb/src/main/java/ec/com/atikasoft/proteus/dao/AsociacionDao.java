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
import ec.com.atikasoft.proteus.modelo.Asociacion;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Dao de asociacion.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class AsociacionDao extends GenericDAO<Asociacion, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(AsociacionDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public AsociacionDao() {
        super(Asociacion.class);
    }

    /**
     *
     * @param unidadOrganizacionalId
     * @return
     * @throws DaoException
     */
//    public Asociacion buscarPorUnidadOrganizacional(Long unidadOrganizacionalId) throws DaoException {
//        Asociacion entidad = null;
//        List<Asociacion> lista = buscarPorConsultaNombrada(Asociacion.BUSCAR_POR_UNIDAD_ORGANIZACIONAL,
//                unidadOrganizacionalId,unidadOrganizacionalId,unidadOrganizacionalId,unidadOrganizacionalId,unidadOrganizacionalId);
//        if (!lista.isEmpty()) {
//            entidad = lista.get(0);
//        }
//        return entidad;
//    }
}
