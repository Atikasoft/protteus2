package ec.com.atikasoft.proteus.colas;

import ec.com.atikasoft.proteus.colas.tramites.CargaMasivaTramiteCesacion;
import ec.com.atikasoft.proteus.colas.tramites.CargaMasivaTramiteIngreso;

import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.servicio.MensajeriaServicio;
import ec.com.atikasoft.proteus.servicio.SeguridadServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.CargaMasivaTramiteVO;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
@MessageDriven(mappedName = "jms/proteusCargaMasivaTramiteQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "endpointExceptionRedeliveryAttempts", propertyValue = "0"),
    @ActivationConfigProperty(propertyName = "sendUndeliverableMsgsToDMQ ", propertyValue = "true")
})
public class CargaMasivaTramiteMessageBean implements MessageListener {

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(CargaMasivaTramiteMessageBean.class.getName());

    /**
     * Servicio de tramite.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     * Servicio de mensajeria.
     */
    @EJB
    private MensajeriaServicio mensajeriaServicio;

    /**
     * Servicio de seguridad.
     */
    @EJB
    private SeguridadServicio seguridadServicio;

    /**
     * Carga masiva de ingresos.
     */
    @EJB
    private CargaMasivaTramiteIngreso cargaMasivaTramiteIngreso;

    /**
     * Carga masiva de cesacion.
     */
    @EJB
    private CargaMasivaTramiteCesacion cargaMasivaTramiteCesacion;

    /**
     * Constructor sin argumentos.
     */
    public CargaMasivaTramiteMessageBean() {
        super();

    }

    @Override
    public void onMessage(final Message msg) {
        try {
            System.out.print("------------------------ CargaMasivaTramiteMessageBean -------------------------");
            procesar((BytesMessage) msg);
        } catch (Exception e) {
            e.printStackTrace();
            //LOG.severe(e.getMessage());
        }
    }

    /**
     * Proceso los archivos recibidos.
     *
     * @param msg contenido del archivo en byte
     * @throws JMSException error de mensajeria
     */
    private void procesar(final BytesMessage msg) throws JMSException {
        Date fechaActual = new Date();
        Boolean resultado = Boolean.TRUE;
        Tramite tramite = null;
        byte[] bytes = new byte[(int) msg.getBodyLength()];
        msg.readBytes(bytes);
        Long tramiteId = msg.getLongProperty("tramite_id");
        String nombreArchivo = msg.getStringProperty("nombre_archivo");
        String usuario = msg.getStringProperty("usuario");
        StringBuilder mensaje = new StringBuilder();
        byte[] buffer = new byte[2048];
        File file = null;
        FileOutputStream out = null;
        FileInputStream in = null;
        ZipInputStream zip = null;
        try {
            tramite = tramiteServicio.buscarTramite(tramiteId);
            file = File.createTempFile(nombreArchivo, null);
            UtilArchivos.copy(bytes, file);
            in = new FileInputStream(file);
            zip = new ZipInputStream(in);
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                File fileCSV = File.createTempFile(entry.getName(), "csv");
                out = new FileOutputStream(fileCSV);
                int len = 0;
                while ((len = zip.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.close();
                CargaMasivaTramiteVO vo = new CargaMasivaTramiteVO();
                vo.setFile(fileCSV);
                vo.setTramite(tramite);
                vo.setMensaje(mensaje);
                vo.setUsuario(usuario);
                if (tramite.getTipoMovimiento().getClase().getGrupo().getNemonico().equals(
                        GrupoEnum.INGRESOS.getCodigo())) {
                    resultado = cargaMasivaTramiteIngreso.procesarArchivo(vo);
                } else if (tramite.getTipoMovimiento().getClase().getGrupo().getNemonico().equals(GrupoEnum.SALIDAS.
                        getCodigo())) {
                    resultado = cargaMasivaTramiteCesacion.procesarArchivo(vo);
                } else {
                    resultado = Boolean.FALSE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultado = Boolean.FALSE;
            mensaje.append("\t* ERROR GRAVE: ").append(e.getMessage()).append("\n");
//            throw new JMSException(e.getMessage());
        } finally {
            try {
                if (zip != null) {
                    try {
                        zip.close();
                    } catch (IOException ex) {
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ex) {
                    }
                }
                if (file != null) {
                    file.deleteOnExit();
                }
                enviarMail(tramite, mensaje, usuario, nombreArchivo, fechaActual, resultado);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     *
     * @param tramite datos del tramite
     * @param mensaje contenido del mail.
     * @param usuario usuario a quien se dirije el mail
     * @param nombreArchivo nombre del archivo fisico
     * @param fecha fecha de la carga masica
     * @param resultado indicador del resultado
     * @throws Exception error general
     */
    private void enviarMail(final Tramite tramite, final StringBuilder mensaje, final String usuario,
            final String nombreArchivo, final Date fecha, final Boolean resultado) throws Exception {
        Servidor s = seguridadServicio.buscarPorUsuario(usuario);

        StringBuilder mensajeFinal = new StringBuilder();
        if (resultado) {
            mensajeFinal.append("Estimada(o):\n\n");
            mensajeFinal.append("Se le informa que el archivo '").append(nombreArchivo).append(
                    "'que usted subió al Sistema de Movimiento de Personal a las  ").append(new SimpleDateFormat(
                                    UtilFechas.FORMATO_FECHA_LARGO).format(fecha)).append(" se proceso con éxito.\n\n");
            mensajeFinal.append("Usted puede continuar con la revisión y atención del mismo.\n\n");
        } else {
            mensajeFinal.append("Estimada(o):\n\n");
            mensajeFinal.append("Se le informa que el archivo '").append(nombreArchivo).append(
                    "' que usted subió al Sistema de Movimiento de Personal a las  ").append(new SimpleDateFormat(
                                    UtilFechas.FORMATO_FECHA_LARGO).format(fecha)).append(
                            " no ha sido procesado debido a que se encontro");
            mensajeFinal.append(" los siguientes errores:\n\n");
            mensajeFinal.append(mensaje.toString()).append("\n");
            mensajeFinal.append("Por favor ajustar el archivo y volver a cargarlo.");
        }
        mensajeriaServicio.enviarMail("CARGA MASIVA EN TRÁMITE No " + tramite.getNumericoTramite(), null,
                new String[]{s.getMail(), "henry.molina@markasoft.ec"}, null,
                mensajeFinal.toString(), null, null);
    }
}
