/*
 *  SenescytServicio.java
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
 *  Jan 10, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.integracion.TituloIntegracion;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.*;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * Acceso anonimo al sitio web del senescyt.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class SenescytServicio extends BaseServicio {

    /**
     * Nombre asignado para titulos del tercer nivel".
     */
    private static final String TITULO_TERCER_NIVEL = "Títulos de Tercer Nivel";

    /**
     * Nombre asignado para titulos de nivel tecnico o tecnologico.
     */
    private static final String TITULO_TECNOLOGICO = "Títulos de Nivel Técnico o Tecnológico Superior";

    /**
     * Nombre asignado para titulos de cuarto nivel.
     */
    private static final String TITULO_CUARTO_NIVEL = "Títulos de Cuarto Nivel";

    /**
     * Constructor sin argumentos.
     */
    public SenescytServicio() {
        super();

    }

    public List<TituloIntegracion> buscarTitulos(final String cedula) throws ServicioException {
        try {
            List<TituloIntegracion> titulos = new ArrayList<TituloIntegracion>();
            // ejecuta post
            String html = doPost(cedula);
            // aplicar tidy al doc. html
            Document document = applyTidy(html);
            //printDocumentXML(document);
            // extraer los datos
            extraerDatos(document, TITULO_CUARTO_NIVEL, titulos);
            extraerDatos(document, TITULO_TERCER_NIVEL, titulos);
            extraerDatos(document, TITULO_TECNOLOGICO, titulos);
            return titulos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServicioException(e);
        }
    }

    /**
     * Extrae los datos desde la pagina web.
     *
     * @param document
     * @param nivel
     * @param titulos
     * @throws XPathExpressionException
     */
    private void extraerDatos(final Document document, final String nivel, final List<TituloIntegracion> titulos) throws
            XPathExpressionException {
        XPathExpression expr = null;
        XPathFactory xFactory = XPathFactory.newInstance();
        XPath xpath = xFactory.newXPath();
        // extrar titulos de tercer nivel
        expr = xpath.compile("//*[contains(.,'" + nivel + "')]");
        NodeList resultado = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        if (resultado.getLength() > 0) {
            Node principal = resultado.item(resultado.getLength() - 1);
            principal = principal.getParentNode().getParentNode().getParentNode();
            if (principal.hasChildNodes()) {
                Node tbody = principal.getChildNodes().item(principal.getChildNodes().getLength() - 1);
                for (int i = 0; i < tbody.getChildNodes().getLength(); i++) {
                    Node tr = tbody.getChildNodes().item(i);
                    TituloIntegracion t = new TituloIntegracion();
                    for (int j = 0; j < tr.getChildNodes().getLength(); j++) {
                        t.setNivelTitulo(nivel);
                        t.setTitulo(tr.getChildNodes().item(0).getChildNodes().item(0).getNodeValue());
                        t.setInstitucion(tr.getChildNodes().item(1).getChildNodes().item(0).getNodeValue());
                        t.setTipo(tr.getChildNodes().item(2).getChildNodes().item(0).getNodeValue());
                        t.setNumeroRegistro(tr.getChildNodes().item(4).getChildNodes().item(0).getNodeValue());
                        t.setFechaRegistro(tr.getChildNodes().item(5).getChildNodes().item(0).getNodeValue());
                    }
                    titulos.add(t);
                }
            }
        }
    }

    /**
     * Realiza el post a la pagina.
     *
     */
    private String doPost(final String cedula) throws ServicioException {
        // post a pagina web
        HttpClient client = new HttpClient();
        StringBuilder url = new StringBuilder(
                "http://www.senescyt.gob.ec/web/guest/certificacion-de-titulos?inicial=1&buscarPorCedula=");
        url.append(cedula);
        GetMethod method = new GetMethod(url.toString());
        method.addRequestHeader(new Header("Host", "www.senescyt.gob.ec"));
        method.addRequestHeader(new Header("User-Agent", "Mozilla/5.0 Firefox/3.6.8"));
        method.addRequestHeader(new Header("Accept", "text/html,application/xhtml+xml,application/"
                + "xml;q=0.9,*/*;q=0.8"));
        method.addRequestHeader(new Header("Accept-Language", "en-us,en;q=0.5"));
        method.addRequestHeader(new Header("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7"));
        method.addRequestHeader(new Header("Keep-Alive", "115"));
        method.addRequestHeader(new Header("Connection", "keep-alive"));
        method.addRequestHeader(new Header("Referer", "http://www.senescyt.gob.ec/web/guest"));
        method.addRequestHeader(
                new Header("Cookie",
                "GUEST_LANGUAGE_ID=en_US; COOKIE_SUPPORT=true; __utma=116947716.23970804.1319060710.1328583101.13286644"
                + "37.6; __utmz=116947716.1319060710.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); HASH_GUEST_"
                + "LANGUAGE_ID=481B1ED1984D38386F54E3D2A8C171BE6D8C27DC; HASH_COOKIE_SUPPORT=AF5AB4DE3B1C500F09342EC0D"
                + "B073D868DB52552; JSESSIONID=8927F5095E64EB010F5266123D37DFAF; __utmb=116947716.1.10.1328664437;"
                + " __utmc=116947716"));
        Integer timeoutMs = Integer.valueOf(100 * 1000);
        client.getParams().setSoTimeout(timeoutMs);
        try {
            int returnCode = client.executeMethod(method);
            if (returnCode == HttpStatus.SC_OK) {
                return method.getResponseBodyAsString();
            } else {
                throw new ServicioException("Error Http:" + returnCode);
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        } finally {
            method.releaseConnection();
        }
    }

    /**
     * Aplica formato al html.
     *
     * @param html
     * @return
     * @throws IOException
     */
    private Document applyTidy(final String html) throws IOException {
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
        return tidy.parseDOM(is, null);
    }

    /**
     * Imprime un documento xml, usado para test.
     *
     * @param document
     * @throws TransformerException
     */
    private static void printDocumentXML(final Document document) throws TransformerException {
        javax.xml.transform.TransformerFactory tfactory = TransformerFactory.newInstance();
        javax.xml.transform.Transformer xform = tfactory.newTransformer();
        javax.xml.transform.Source src = new DOMSource(document);
        java.io.StringWriter writer = new StringWriter();
        javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(writer);
        xform.transform(src, result);

        System.out.println("------------------------------------------------------");
        System.out.println(writer.toString());
        System.out.println("------------------------------------------------------");
    }
}
