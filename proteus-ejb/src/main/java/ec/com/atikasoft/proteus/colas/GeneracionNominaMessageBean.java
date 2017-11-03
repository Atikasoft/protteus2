package ec.com.atikasoft.proteus.colas;

import ec.com.atikasoft.proteus.dao.NominaDao;
import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.servicio.NominaServicio;
import ec.com.atikasoft.proteus.servicio.ProblemaServicio;
import ec.com.atikasoft.proteus.singlenton.SinglentoSistema;
import ec.com.atikasoft.proteus.vo.EjecucionNominaVO;
import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;
import javax.servlet.ServletContext;

/**
 * Receptor de cola que permite ejecutar la nomina.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * http://www.greenkode.com/2011/09/using-glassfish-and-ejb-3-0-message-driven-beans-for-java-messaging/
 * @version $Revision 1.0 $
 */
@MessageDriven(mappedName = "jms/proteusGeneracionNominaQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "endpointExceptionRedeliveryAttempts", propertyValue = "0"),
    @ActivationConfigProperty(propertyName = "sendUndeliverableMsgsToDMQ ", propertyValue = "true")
})
public class GeneracionNominaMessageBean implements MessageListener {

    /**
     *
     */
    @EJB
    private NominaServicio nominaServicio;

    /**
     *
     */
    @EJB
    private ProblemaServicio problemaServicio;

    /**
     *
     */
    @EJB
    private NominaDao nominaDao;

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(GeneracionNominaMessageBean.class.getName());

    /**
     * Constructor sin argumentos.
     */
    public GeneracionNominaMessageBean() {
        super();

    }

    @Override
    public void onMessage(final Message msg) {
        try {
            procesar((ObjectMessage) msg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Nomina nomina = nominaDao.buscarPorId(msg.getLongProperty("nomina_id"));
                nomina.setCalculando(Boolean.FALSE);
                nominaDao.actualizar(nomina);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    /**
     * Proceso los archivos recibidos.
     *
     * @param msg Contenido del mensaje
     * @throws JMSException error de mensajeria
     */
    private void procesar(final ObjectMessage msg) throws JMSException {
        Date fechaActual = new Date();
        String usuario = msg.getStringProperty("usuario");
        Long nominaId = msg.getLongProperty("nomina_id");
        Long fechaEnvio = msg.getLongProperty("fecha");
        if (fechaActual.getTime() - fechaEnvio <= 5000) {
            EjecucionNominaVO vo = (EjecucionNominaVO) msg.getObject();
            try {
                ServletContext sc = SinglentoSistema.getInstancia().getVariablesSistema().getServletContext();
                problemaServicio.registrarEjecucion("INICIANDO CALCULO.....", usuario, nominaId);
                nominaServicio.ejecutar(vo, nominaId, vo.getUsuarioVO(), sc);
                problemaServicio.registrarEjecucion("NOMINA CALCULADO EXITOSAMENTE", usuario, nominaId);
            } catch (Exception e) {
                problemaServicio.registrarEjecucion("ERROR AL TRATAR DE EJECUTAR LA NOMINA:" + e.getMessage(), usuario, nominaId);
            }
        }
    }
}
