/*
 *  CRCServicio.java
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
 *  Apr 11, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.integracion.CiudadanoIntegracion;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Node;
import org.dom4j.io.DOMReader;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

/**
 * Realiza consulta de datos al registro civil.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class CRCServicio {

    /**
     * Mensaje que se muestra cuando no existen datos.
     */
    public static String MENSAJE_NO_DATOS = "DATO NO EXISTE";

    /**
     * Constructor sin argumentos.
     */
    public CRCServicio() {
        super();
    }

    public CiudadanoIntegracion buscarPorCedula(final String cedula) throws ServicioException {
        try {

            // hacer post y obtener el doc. html

            String htmlCRC = doPostCiudadanoCRC(cedula);

            // aplicar tidy al doc. html
            Document documentCRC = applyTidy(htmlCRC);

            // obtener un dom xml
            org.dom4j.Document doCRC = generateDom(documentCRC);
            // System.out.println("------------------------------------------------------");
            // final OutputFormat format = OutputFormat.createPrettyPrint();
            // final XMLWriter writer = new XMLWriter(System.out, format);
            // writer.write(doCRC);
            // writer.close();
            // System.out.println("------------------------------------------------------");

            // extraer datos
            CiudadanoIntegracion ciudadano = extractDataCiudadano(doCRC, cedula);
            return ciudadano;

        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Hacer peticion post y obtener el doc html resultante
     *
     * @param url
     * @throws ServiceException
     */
    private String doPostCiudadanoCRC(String cedula) throws ServicioException {
        HttpClient client = new HttpClient();
        BufferedReader br = null;
        // recuperar url
        String url = "http://www.corporacionregistrocivil.gov.ec/OnLine/show_cedula.asp";
        PostMethod method = new PostMethod(url);
        method.addRequestHeader(new Header("Host", "www.corporacionregistrocivil.gov.ec"));
        method.addRequestHeader(new Header("User-Agent", "Mozilla/5.0 Firefox/3.6.8"));
        method.addRequestHeader(new Header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
        method.addRequestHeader(new Header("Accept-Language", "en-us,en;q=0.5"));
        method.addRequestHeader(new Header("Content-Typ", "application/x-www-form-urlencoded"));
        method.addRequestHeader(new Header("Keep-Alive", "115"));
        method.addRequestHeader(new Header("Connection", "keep-alive"));
        method.addRequestHeader(new Header("Referer",
                "http://www.corporacionregistrocivil.gov.ec/OnLine/find_cedula.asp"));
        method.addRequestHeader(
                new Header(
                "Cookie",
                "__utma=255300250.1438459864.1321913112.1321913112.1322442945.2; __utmz=255300250.1321913112.1.1.utmccn=(direct)|utmcsr=(direct)|utmcmd=(none); __utmb=255300250; ASPSESSIONIDCCAAASRR=NLNNIOGBNGOEAFAGGOOPLCHK; __utmc=255300250; ASPSESSIONIDAABDBSQR=KOFNGIGBKOAEPAKMMAECLNNG"));
        method.addParameter("strCAPTCHA", "12345678");
        method.addParameter("strCAPTCHAsc", "12345678");
        method.addParameter("nroced", cedula);

        try {
            Integer timeoutMs = new Integer(100 * 1000);
            client.getParams().setContentCharset("UTF-8");
            client.getParams().setSoTimeout(timeoutMs);
            int returnCode = client.executeMethod(method);
            if (returnCode == HttpStatus.SC_OK) {
                return method.getResponseBodyAsString();
            } else {
                throw new ServicioException("Error Http:" + returnCode);
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        } finally {
            method.releaseConnection();
            if (br != null) {
                try {
                    br.close();
                } catch (Exception fe) {
                }
            }
        }
    }

    /**
     * Extrae los datos del vehiculos desde el documento xml y los serializa a un objeto
     *
     * @param docSRI
     * @param placa
     * @return
     * @throws NotFoundVehicleException
     */
    private CiudadanoIntegracion extractDataCiudadano(org.dom4j.Document docCRC, String cedula) throws ServicioException {
        CiudadanoIntegracion c = null;
        Node node = docCRC.selectSingleNode("/html/body/div/table/tr/td/div[3]/table[1]/tr[2]/td[2]");
        if (node != null) {
            c = new CiudadanoIntegracion();
            c.setCedula(node.getStringValue().trim());
            // Digito verificador
            node = docCRC.selectSingleNode("/html/body/div/table/tr/td/div[3]/table[1]/tr[4]/td[2]");
            if (node != null) {
                c.setDigitoVerificador(node.getStringValue().trim());
                c.setCedula(c.getCedula() + c.getDigitoVerificador());
            } else {
                c.setDigitoVerificador(MENSAJE_NO_DATOS);
            }

            // Nombre
            node = docCRC.selectSingleNode("/html/body/div/table/tr/td/div[3]/table[1]/tr[6]/td[2]");
            if (node != null) {
                c.setNombreCedulado(node.getStringValue().trim());
            } else {
                c.setNombreCedulado(MENSAJE_NO_DATOS);
            }
            // Fecha nacimiento
            node = docCRC.selectSingleNode("/html/body/div/table/tr/td/div[3]/table[1]/tr[8]/td[2]");
            if (node != null) {
                c.setFechaNacimiento(node.getStringValue().trim());
            } else {
                c.setFechaNacimiento(MENSAJE_NO_DATOS);
            }
            // Reside en gye
            node = docCRC.selectSingleNode("/html/body/div/table/tr/td/div[3]/table[1]/tr[10]/td[2]");
            if (node != null) {
                c.setResideEnGuayaquil(node.getStringValue().trim());
            } else {
                c.setResideEnGuayaquil(MENSAJE_NO_DATOS);
            }
            // Condicion de cedula.
            node = docCRC.selectSingleNode("/html/body/div/table/tr/td/div[3]/table[1]/tr[12]/td[2]");
            if (node != null) {
                c.setCondicionDeCedula(node.getStringValue().trim());
            } else {
                c.setCondicionDeCedula(MENSAJE_NO_DATOS);
            }
            // Estado civil.
            node = docCRC.selectSingleNode("/html/body/div/table/tr/td/div[3]/table[1]/tr[14]/td[2]");
            if (node != null) {
                c.setEstadoCivil(node.getStringValue().trim());
            } else {
                c.setEstadoCivil(MENSAJE_NO_DATOS);
            }
            // Nombre conyuge
            node = docCRC.selectSingleNode("/html/body/div/table/tr/td/div[3]/table[1]/tr[16]/td[2]");
            if (node != null) {
                c.setNombreConyuge(node.getStringValue().trim());
            } else {
                c.setNombreConyuge(MENSAJE_NO_DATOS);
            }
            // Posee ficha indice
            node = docCRC.selectSingleNode("/html/body/div/table/tr/td/div[3]/table[1]/tr[18]/td[2]");
            if (node != null) {
                c.setPoseeFichaIndice(node.getStringValue().trim());
            } else {
                c.setPoseeFichaIndice(MENSAJE_NO_DATOS);
            }
            // Posee ficha dactiloscopica
            node = docCRC.selectSingleNode("/html/body/div/table/tr/td/div[3]/table[1]/tr[20]/td[2]");
            if (node != null) {
                c.setPoseeFichaDactilodcopica(node.getStringValue().trim());
            } else {
                c.setPoseeFichaDactilodcopica(MENSAJE_NO_DATOS);
            }

            c.setCedula(c.getCedula().replaceAll("[^\\p{Print}]", ""));
            c.setCondicionDeCedula(c.getCondicionDeCedula().replaceAll("[^\\p{Print}]", ""));
            c.setDigitoVerificador(c.getDigitoVerificador().replaceAll("[^\\p{Print}]", ""));
            c.setEstadoCivil(c.getEstadoCivil().replaceAll("[^\\p{Print}]", ""));
            c.setFechaNacimiento(c.getFechaNacimiento().replaceAll("[^\\p{Print}]", ""));
            //c.setNombreCedulado(c.getNombreCedulado().replaceAll("[^\\p{Print}]", ""));
            //c.setNombreConyuge(c.getNombreConyuge().replaceAll("[^\\p{Print}]", ""));
            c.setPoseeFichaDactilodcopica(c.getPoseeFichaDactilodcopica().replaceAll("[^\\p{Print}]", ""));
            c.setPoseeFichaIndice(c.getPoseeFichaIndice().replaceAll("[^\\p{Print}]", ""));
            c.setResideEnGuayaquil(c.getResideEnGuayaquil().replaceAll("[^\\p{Print}]", ""));
        }
        return c;
    }

    /**
     * Toma el codigo html y lo transforma en un XML bien formado
     *
     * @param in
     * @return
     * @throws IOException
     */
    protected Document applyTidy(String html) throws IOException {
        InputStream is = new ByteArrayInputStream(html.getBytes());
        Tidy tidy = new Tidy();
        tidy.setUpperCaseAttrs(true);
        tidy.setHideComments(true);
        tidy.setIndentAttributes(true);
        tidy.setUpperCaseTags(true);
        tidy.setFixBackslash(true);
        tidy.setInputEncoding("UTF-8");
        tidy.setXHTML(true);
        tidy.setDropEmptyParas(true);
        tidy.setDropEmptyParas(true);
        tidy.setDropFontTags(true);
        tidy.setDropProprietaryAttributes(true);
        Document document = tidy.parseDOM(is, null);
        return document;
    }

    /**
     * Transforma un document estandar en un document de dom4j
     *
     * @param document
     * @return
     */
    protected org.dom4j.Document generateDom(Document document) {
        DOMReader reader = new DOMReader();
        org.dom4j.Document doc = reader.read(document);
        return doc;
    }
}
