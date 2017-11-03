/*
 *  ReglaServicio.java
 *  ESIPREN V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Nov 14, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import java.io.File;
import java.util.Date;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Permite ejecutar operaciones de mensajeria.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class MensajeriaServicio extends BaseServicio {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(MensajeriaServicio.class.getCanonicalName());

    @Resource(name = "mail/proteus")
    private Session session;

    /**
     * Constructor sin argumentos.
     */
    public MensajeriaServicio() {
        super();
    }

    /**
     * Permite enviar un mail.
     *
     * @param asunto Asunto del mail.
     * @param from Mail origen.
     * @param to Dirigido a.
     * @param cc Con copia a.
     * @param mensaje Texto del mensaje.
     * @param archivo Archivo.
     * @param nombreArchivo Nombre del archivo.
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void enviarMail(final String asunto, final String from, final String[] to, final String[] cc,
            final String mensaje, final File archivo, final String nombreArchivo) throws ServicioException {
        try {
            LOG.info("mensaje: " + mensaje);

            // Create email and headers.  
            MimeMessage msg = new MimeMessage(session);
            msg.setSubject(asunto);
            for (String t : to) {
                LOG.info("enviar mail: to " + t);
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(t));
//                msg.setRecipient(Message.RecipientType.TO, new InternetAddress(t));
            }
            if (cc != null) {
                for (String c : cc) {
                    msg.addRecipient(Message.RecipientType.CC, new InternetAddress(c));
                }
            }
            if (from != null) {
                msg.setFrom(new InternetAddress(from));
            }
            msg.setSentDate(new Date());
            msg.setText(mensaje, "utf-8", "html");
            // Multipart message.  
            if (archivo != null) {
                BodyPart messageBodyPart = new MimeBodyPart();
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                // Attachment file from file.  
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setFileName(nombreArchivo);
                DataSource src = new FileDataSource(archivo);
                messageBodyPart.setDataHandler(new DataHandler(src));
                multipart.addBodyPart(messageBodyPart);
                // Add multipart message to email.  
                msg.setContent(multipart);
            }
            // Send email.  
            Transport.send(msg);
        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ServicioException(e);
        }
    }
}
