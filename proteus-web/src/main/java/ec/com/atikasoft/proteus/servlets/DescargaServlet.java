 /*
 *  DescargaServlet.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  05/02/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servlets;

import com.Ostermiller.util.CSVPrinter;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.servicio.ArchivoServicio;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 * Servlet para recuperar archivos.
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 * @version $Revision 1.0 $
 */
@WebServlet(name = "descargaServlet", urlPatterns = "/descargaServlet/*")
public class DescargaServlet extends HttpServlet {

    /**
     * Servicio de archivos.
     */
    @EJB
    private ArchivoServicio archivoServicio;

    static int ARRAY_SIZE = 16384;

    private static final String CSV = "csv";

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(DescargaServlet.class.getName());

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cadena = request.getPathInfo();
        int posicion = cadena.indexOf("+", 0);
        Long idArchivo = Long.valueOf(cadena.substring(1, posicion));
        int segundaPosicion = cadena.lastIndexOf("+");
        LOG.log(Level.INFO, "valor del parametro:{0} primera pos:{1} seg pos:{2}", new Object[]{cadena, posicion,
                    segundaPosicion});
        if (posicion + 1 <= cadena.length()) {
            String nombreArchivo = cadena.substring(posicion + 1, segundaPosicion);
            String tipoArchivo = cadena.substring(segundaPosicion + 1);
            LOG.log(Level.INFO, "Nombre del archivo: {0}",
                    nombreArchivo + " :Id del archivo: " + idArchivo + ":tipo:" + tipoArchivo);

            OutputStream out = null;
            try {
//        response.setContentType("application/octet-strem");
                response.setHeader("Content-Type", CSV);
                response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo + "");
                out = new BufferedOutputStream(response.getOutputStream());
                List<List<String>> lista = archivoServicio.armarArchivoSipari(idArchivo, tipoArchivo);
                CSVPrinter csvp = new CSVPrinter(out);
                csvp.setAlwaysQuote(false);
                csvp.changeDelimiter(';');
                //csvp.changeQuote(' ');
                for (List<String> a : lista) {
                    csvp.writeln(a.toArray(new String[a.size()]));
                }
                csvp.close();
                out.close();
            } catch (DaoException e) {
                LOG.log(Level.SEVERE, "Problemas al descargar archivo.{0}", e);
            } catch (IOException e) {
                LOG.log(Level.SEVERE, "Problemas al descargar archivo.{0}", e);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException logOrIgnore) {
                        LOG.log(Level.SEVERE, "Problemas al descargar archivo.{0}", logOrIgnore);
                    }
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Métodos HttpServlet. Click en el más (+) para desplegar el código.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
