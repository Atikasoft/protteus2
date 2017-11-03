package ec.com.atikasoft.proteus.colas;

import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.MensajeriaServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.util.UtilMail;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;

/**
 * Receptor de cola que permite procesar las cargas masivas del tramites.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * http://www.greenkode.com/2011/09/using-glassfish-and-ejb-3-0-message-driven-beans-for-java-messaging/
 * @version $Revision 1.0 $
 */
@MessageDriven(mappedName = "jms/proteusGenMasivaClavesQueue", activationConfig = {
    //    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "endpointExceptionRedeliveryAttempts", propertyValue = "0"),
    @ActivationConfigProperty(propertyName = "sendUndeliverableMsgsToDMQ ", propertyValue = "true")
})
public class GeneracionMasivaClaveMessageBean implements MessageListener {

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(GeneracionMasivaClaveMessageBean.class.getName());
    /**
     * Indica el total de servidores que se procesara.
     */
    private static final Integer TOTAL_LOTE_SERVIDORES = 1000;
    /**
     * Servicio de distributivo.
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;

    /**
     * Dao de parametros globales.
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     * Servidor Servicio.
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     * Mensajeria Servicio.
     */
    @EJB
    private MensajeriaServicio mensajeriaServicio;

    /**
     * Constructor sin argumentos.
     */
    public GeneracionMasivaClaveMessageBean() {
        super();

    }

    @Override
    public void onMessage(final Message msg) {
        try {
            LOG.info("------------------------ Generacion Masiva de Claves MBD -------------------------");
            procesar(msg);
        } catch (Exception e) {
            e.printStackTrace();
            //LOG.severe(e.getMessage());
        }
    }

    /**
     * Proceso los archivos recibidos.
     *
     * @param msg Contenido del mensaje recibido
     * @throws JMSException error de mensajeria
     */
    private void procesar(final Message msg) throws JMSException {

        Boolean resultado = Boolean.TRUE;
        String usuario = msg.getStringProperty("usuario");
        Long institucionEjercicioFiscal = msg.getLongProperty("institucion_ejercicio_fiscal");
        Date fechaActual = new Date(msg.getLongProperty("fecha"));
        StringBuilder mensaje = new StringBuilder();

        try {
            List<Servidor> listaServidorSinMail = new ArrayList<>();
            int fila = 0, cont = 0;
            BusquedaPuestoVO vo = new BusquedaPuestoVO();
            vo.setPuestoVacante(Boolean.FALSE);
            vo.setIntitucionEjercicioFiscalId(institucionEjercicioFiscal);
            vo.setInicioConsulta(fila);
            vo.setFinConsulta(TOTAL_LOTE_SERVIDORES);
            List<DistributivoDetalle> lista = distributivoPuestoServicio.buscar(vo, false, null, true);
//            int con1 = 0;

            if (lista.isEmpty()) {
                LOG.info("NO HAY DISTRIBUTIVOS PARA GENERAR CLAVES!!!!!!");
                StringBuilder texto = new StringBuilder("");
                texto.append("NO HAY DISTRIBUTIVOS  PARA GENERAR CLAVES!!!!!!\n No se han encontrado servidores para procesar");
                mensaje.append("MDMQ-SIGEN: NO HAY DISTRIBUTIVOS PARA GENERAR CLAVES!!!!!!");
                resultado = Boolean.FALSE;
                enviarMail(usuario, fechaActual, resultado, mensaje);
            } else {
                //System.out.println("*************inicio.... "+UtilFechas.formatearLargo(new Date()));
                while (!lista.isEmpty()) {
                    for (DistributivoDetalle dd : lista) {
                        synchronized (dd) {
                            if (dd.getServidor() == null) {
                                continue;
                            }
                            //System.out.println(">>>>>>>>>>>>>>>>>> CONTADOR>>>>>>>>>>>>>>>>:" + con1);
                            //////////////////quemado quitar///////////////////////////
//                            con1++;
//                            if (con1 > 10) {
//                                break;
//                            }
                            ///////////////////quemado quitar//////////////////////////
                            if (dd.getServidor().getMail() != null && !dd.getServidor().getMail().isEmpty()
                                    && UtilMail.esCorreoElectronico(dd.getServidor().getMail())) {
                                servidorServicio.generarClaves(dd.getServidor(), usuario, Boolean.TRUE);
                                try {
                                    dd.wait(500); // 0.5 segundos
                                } catch (InterruptedException ie) {
                                }
                                //System.out.println(" ejecutando envio .... "+UtilFechas.formatearLargo(new Date()));
                            } else {
                                listaServidorSinMail.add(dd.getServidor());
                            }
                        }
                    }
                    fila += TOTAL_LOTE_SERVIDORES;
                    vo.setInicioConsulta(fila);
                    //lista = distributivoPuestoServicio.buscar(vo, false);

                }

                if (!listaServidorSinMail.isEmpty()) {
                    StringBuilder texto = new StringBuilder("");
                    texto.append("Los siguientes servidores no tienen cuenta de correo electrónico registrada\n");
                    for (Servidor s : listaServidorSinMail) {
                        cont++;

                        texto.append(cont).append(" .-Número de identificación:").append(s.getNumeroIdentificacion())
                                .append(", Nombres:").append(s.getApellidosNombres()).append("\n");
                    }
                    resultado = Boolean.FALSE;
                    servidorServicio.enviarNotificacionAdministradorEjecucionFallida(
                            "MDMQ-SIGEN: Servidores sin cuenta de correo electrónico",
                            "SIGEN: CULMINACIÓN GENERACION MASIVA DE CLAVES", texto.toString());
                } else {
                    resultado = Boolean.TRUE;
                    enviarMail(usuario, fechaActual, resultado, mensaje);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultado = Boolean.FALSE;
            mensaje.append("\t* ERROR GRAVE: ").append(e.getMessage()).append("\n");
//            throw new JMSException(e.getMessage());
        } finally {
            try {
                enviarMail(usuario, fechaActual, resultado, mensaje);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Envio mail notificando el resultado del proceso.
     *
     * @param usuario usuario a quien se dirije el mail
     * @param fecha fecha de la generacion masiva
     * @param resultado indicador dle resultado
     * @param mensaje contenido del mail
     * @throws Exception error general
     */
    private void enviarMail(final String usuario,
            final Date fecha, final Boolean resultado, final StringBuilder mensaje) throws Exception {

        StringBuilder mensajeFinal = new StringBuilder();
        ParametroGlobal pNemonicoCorreoAdm
                = parametroGlobalDao.buscarPorNemonico(ParametroGlobalEnum.CORREO_ELECTRONICO_ADMINISTRADOR_SISTEMA.
                        getCodigo());

        if (pNemonicoCorreoAdm != null) {
            String[] to = pNemonicoCorreoAdm.getValorTexto().split(",");
            if (to == null || to.length == 0) {
                to[0] = pNemonicoCorreoAdm.getValorTexto();
            }
            if (resultado) {
                mensajeFinal.append("Estimada(o):\n\n");
                mensajeFinal.append("Se le informa que el proceso de Generación masiva de claves '").append(
                        "'que envió a ejecutar el usuario:").append(usuario).append(" el ").append(new SimpleDateFormat(
                                        UtilFechas.FORMATO_FECHA_SQL_CORTA).format(fecha)).append(" se proceso con éxito.\n\n");
                mensajeFinal.append("Usted puede continuar con la revisión y atención del mismo.\n\n");
            } else {
                mensajeFinal.append("Estimada(o):\n\n");
                mensajeFinal.append("Se le informa que el proceso de Generación masiva de claves  '").append(
                        "'que envió a ejecutar el usuario:").append(usuario).append(" el ").append(new SimpleDateFormat(
                                        UtilFechas.FORMATO_FECHA_SQL_CORTA).format(fecha)).append(
                                " no ha sido procesado debido a que se encontro");
                mensajeFinal.append(" los siguientes errores:\n\n");
                mensajeFinal.append(mensaje.toString()).append("\n");
            }
            mensajeriaServicio.enviarMail("CULMINACIÓN DE GENERACION MASIVA DE CLAVES ", null, to, null, mensajeFinal.toString(), null, null);
        }
    }
}
