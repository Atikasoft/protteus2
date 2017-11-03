/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.ServidorHistoricosJornadaDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorHistoricosJornada;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Nelson Jumbo <nelson.jumbo@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class ServidorHistoricosJornadaServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(ServidorHistoricosJornadaServicio.class.getCanonicalName());

    /**
     * DAO ServidorHistoricosJornadaDao
     */
    @EJB
    private ServidorHistoricosJornadaDao servidorHistoricosJornadaDao;
    
    public void generarHistoricoJornadaLaboral(final Servidor servidorConDatosAnteriores, final UsuarioVO usuarioCreaOModifica) throws DaoException{
        
        ServidorHistoricosJornada historico = new ServidorHistoricosJornada();
        iniciarDatosEntidad(historico, Boolean.TRUE, usuarioCreaOModifica);
        historico.setHoraEntrada(servidorConDatosAnteriores.getHoraEntrada());
        historico.setJornada(servidorConDatosAnteriores.getJornada());
        historico.setMotivoCambioJornada(servidorConDatosAnteriores.getMotivoCambioJornada());
        historico.setMarcacionObligatoria(servidorConDatosAnteriores.getDistributivoDetalle().getEscalaOcupacional().getMarcacionObligatoria());
        historico.setRecibeHorasExtra(servidorConDatosAnteriores.getDistributivoDetalle().getEscalaOcupacional().getRecibeHorasExtra());
        historico.setServidor(servidorConDatosAnteriores);
        
        servidorHistoricosJornadaDao.crear(historico);
        servidorHistoricosJornadaDao.flush();
    }

}
