/*
 *  CotizacionIessDao.java
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
 *  09/10/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.nomina.CotizacionIessEspecial;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * CotizacionIessEspecialDao
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
@LocalBean
@Stateless
public class CotizacionIessEspecialDao extends GenericDAO<CotizacionIessEspecial, Long> {
    
    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(CotizacionIessEspecialDao.class.getCanonicalName());
    
    /**
     * Constructor por defecto.
     */
    public CotizacionIessEspecialDao() {
        super(CotizacionIessEspecial.class);
    }

    /**
     * Busca las CotizacionIessEspecial vigentes
     * @return
     * @throws DaoException 
     */
    public List<CotizacionIessEspecial> buscarVigentes() throws DaoException {
        try{
            return buscarPorConsultaNombrada(CotizacionIessEspecial.BUSCAR_VIGENTES);
        }catch(Exception e){
            LOG.log(Level.SEVERE, "Error recuperando las CotizacionIessEspecial vigentes", e);
            throw new DaoException(e);
        }
    }
   
    /**
     * Verifica si el nombre de CotizacionIessEspecial ya esta en uso
     * @param nombre
     * @param idIgnorar
     * @return
     * @throws DaoException 
     */
    public boolean existeNombreEnUso(final String nombre, final Long idIgnorar) throws DaoException {
        try{
            long cantidad = idIgnorar == null ?
                    contarPorConsultaNombrada(CotizacionIessEspecial.CONTAR_POR_NOMBRE, nombre) :
                    contarPorConsultaNombrada(CotizacionIessEspecial.CONTAR_POR_NOMBRE_IGNORAR_ID, nombre, idIgnorar) ;
            return cantidad > 0;
        }catch(Exception e){
            LOG.log(Level.SEVERE, "Error recuperando las CotizacionIessEspecial vigentes", e);
            throw new DaoException(e);
        }
    }
}
