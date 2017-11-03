/*
 *  ConsultaServlet.java
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
 *  Apr 10, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servlets;

import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

/**
 * Servlet para recuperar archivos.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@WebServlet(name = "imageServlet", urlPatterns = "/imageServlet/*")
public class ImageServlet extends HttpServlet {

    /**
     * Servicio de archivo.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        Long imageId = Long.valueOf(request.getPathInfo().substring(1));
        try {
            Archivo archivo = administracionServicio.buscarArchivoPorId(imageId);
            response.setHeader("Content-Type", getServletContext().getMimeType(archivo.getNombre()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + archivo.getNombre() + "\"");
            InputStream input = null;
            OutputStream output = null;

            try {
                input = new ByteArrayInputStream(archivo.getArchivo());
                output = new BufferedOutputStream(response.getOutputStream());
                byte[] buffer = new byte[8192];
                for (int length = 0; (length = input.read(buffer)) > 0;) {
                    output.write(buffer, 0, length);
                }
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException logOrIgnore) {
                    }
                }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException logOrIgnore) {
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
