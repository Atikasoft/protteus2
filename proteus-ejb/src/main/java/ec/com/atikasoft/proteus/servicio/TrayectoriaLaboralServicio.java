/*
 *  MovimientoServicio.java
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
 *  09/01/2013
 *
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.TrayectoriaLaboralDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.TrayectoriaLaboral;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.vo.ConsultaTrayectoriaLaboralVO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;


/**
 * Serivio de Trayectoria laboral
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
@Stateless
@LocalBean
public class TrayectoriaLaboralServicio extends BaseServicio {

    
    /**
     * 
     */
    @EJB
    private TrayectoriaLaboralDao trayectoriaLaboralDao;

    /**
     * Constructor por defecto.
     */
    public TrayectoriaLaboralServicio() {
        super();
    }


    /**
     * Busca el primer registro en base a la fecha desde dado un tipo y un numero de documento
     * @param tipoDocumento
     * @param numeroDocumento
     * @return
     * @throws ServicioException 
     */
    public TrayectoriaLaboral buscarPrimerRegitroTrayectoriaLaboral(final String tipoDocumento, final 
            String numeroDocumento) throws ServicioException {
        try {
            TrayectoriaLaboral servidor = new TrayectoriaLaboral();
            if (tipoDocumento != null) {
                servidor = trayectoriaLaboralDao.buscarPrimerRegitro(tipoDocumento, numeroDocumento);
            }
            return servidor;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Consulta de trayectoria laboral
     * @param vo
     * @return
     * @throws ServicioException 
     */
    public List<TrayectoriaLaboral> buscarMovimientosTrayectoriaLaboral(final ConsultaTrayectoriaLaboralVO vo)
            throws ServicioException {
        try {
            return trayectoriaLaboralDao.buscarTrayectoria(vo);
        } catch (DaoException ex) {
            Logger.getLogger(TrayectoriaLaboralServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }
}
