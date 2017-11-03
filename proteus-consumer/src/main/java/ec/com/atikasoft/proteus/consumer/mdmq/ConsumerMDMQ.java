/*
 *  ConsumerMDMQ.java
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
 *  Dec 10, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.consumer.mdmq;

import ec.gob.quito.mdmq.ArrayOfSEGRECURSO;
import ec.gob.quito.mdmq.ClsMessage;
import ec.gob.quito.mdmq.EWSSEG;
import ec.gob.quito.mdmq.SEGRECURSO;
import ec.com.atikasoft.proteus.consumer.mdmq.vo.MenuVO;
import ec.com.atikasoft.proteus.consumer.mdmq.vo.PersonaVO;
import ec.com.atikasoft.proteus.consumer.mdmq.vo.ResultadoAutentificarVO;
import ec.gob.quito.mdmq.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class ConsumerMDMQ {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ConsumerMDMQ.class.getName());

    /**
     * Codigo que identifica al MDMQ
     */
    private static final String CODIGO_EMPRESA = "MDMQ01";

    /**
     * Codigo de la aplicacion.
     */
    private static final String CODIGO_APLICACION = "SGN001";

    /**
     * Nombre qualificado del ws de sguridades.
     */
    private static final QName QNAME_SEGURIDADES = new QName("http://mdmq.quito.gob.ec/", "EWS_SEG");

    /**
     * Nombre qualificado del ws de personas.
     */
    private static final QName QNAME_PERSONAS = new QName("http://mdmq.quito.gob.ec/", "ServicePersonas");

    /**
     * Constructor sin argumentos.
     */
    public ConsumerMDMQ() {
        super();
    }

    /**
     * Realiza la autentificacion de usuario.
     *
     * @param usuario
     * @param clave
     * @param oficina
     * @param ip
     * @param endpoint
     * @return
     * @throws MalformedURLException
     */
    public ResultadoAutentificarVO autentificar(final String usuario, final String clave, final int oficina,
            final String ip, final String endpoint) throws MalformedURLException {
        EWSSEG ws = new EWSSEG(new URL(endpoint), QNAME_SEGURIDADES);
//        EWSSEG ws = new EWSSEG();
        Holder<String> identificador = new Holder<String>();
        Holder<String> mensaje = new Holder<String>();
        Holder<Integer> resultado = new Holder<Integer>();
        ws.getEWSSEGSoap12().fnLogin(usuario, clave, CODIGO_EMPRESA, oficina, ip, identificador, mensaje, resultado);
        ResultadoAutentificarVO vo = new ResultadoAutentificarVO();
        vo.setIdentificadorSesion(identificador.value);
        vo.setMensaje(mensaje.value);
        vo.setResultado(resultado.value);
        Holder<ClsMessage> msg = new Holder<ClsMessage>();
        Holder<ArrayOfSEGUSUARIO> arraysegusuario = new Holder<ArrayOfSEGUSUARIO>();
        SEGUSUARIO segusuario = new SEGUSUARIO();
        segusuario.setUSLOGINID(usuario);
        segusuario.setUSPERSONA(0);
        Holder<Integer> fnUSUARIOConsultarResult = new Holder<Integer>();
        ws.getEWSSEGSoap12().fnUSUARIOConsultar(identificador.value, null, "V", 0, 0, segusuario,
                fnUSUARIOConsultarResult, msg, arraysegusuario);
        if (!arraysegusuario.value.getSEGUSUARIO().isEmpty()) {
            segusuario = arraysegusuario.value.getSEGUSUARIO().get(0);
            vo.setNumeroIdentificacion(segusuario.getUSID());
            vo.setNumeroPersona(segusuario.getUSPERSONA());
        }
//        vo.setNumeroIdentificacion("1718934100");
//        vo.setNumeroPersona(0l);
        return vo;
    }

    /**
     * Realiza la autorizacion de un usuario, recupera los recursos a donde puede acceder el usuario.
     *
     * @param identificadorSesion
     * @param usuario
     * @param endpoint
     * @throws MalformedURLException
     */
    public List<MenuVO> autorizar(final String identificadorSesion, final String usuario, final String endpoint) throws
            MalformedURLException {
        List<MenuVO> menus = new ArrayList<MenuVO>();
        EWSSEG ws = new EWSSEG(new URL(endpoint), QNAME_SEGURIDADES);
        Holder<Integer> resultado = new Holder<Integer>();
        Holder<ClsMessage> mensaje = new Holder<ClsMessage>();
        Holder<ArrayOfSEGRECURSO> recursos = new Holder<ArrayOfSEGRECURSO>();
        ws.getEWSSEGSoap12().consultarMenu(identificadorSesion, usuario, CODIGO_EMPRESA, resultado, mensaje, recursos);

        for (SEGRECURSO r : recursos.value.getSEGRECURSO()) {
            if (r.getREAPLICACION().equals(CODIGO_APLICACION)) {
                MenuVO menu = new MenuVO();
                menu.setCodigo(r.getRECODIGOID());
                menu.setEtiqueta(r.getRETEXTO());
                menu.setOrden(r.getRESECUENCIAL());
                menu.setUrl(r.getREIDURL());
                menus.add(menu);
            }
        }
        return menus;
    }

    /**
     *
     * @param identificadorSesion
     * @param usuario
     * @param claveAnterior
     * @param claveNueva
     * @param endpoint
     * @return
     * @throws MalformedURLException
     */
    public Boolean cambioClave(final String identificadorSesion, final String usuario, final String claveAnterior,
            final String claveNueva, final String endpoint) throws MalformedURLException {
        Boolean resultado = Boolean.FALSE;
        EWSSEG ws = new EWSSEG(new URL(endpoint), QNAME_SEGURIDADES);
        Holder<Integer> respuesta = new Holder<Integer>();
        Holder<String> mensaje = new Holder<String>();
        ws.getEWSSEGSoap12().fnCambiarClave(identificadorSesion, usuario, claveAnterior, claveNueva, mensaje, respuesta);
        if (respuesta.value == 0) {
            resultado = Boolean.TRUE;
        }
        return resultado;
    }

    /**
     *
     * @param identificadorSesion
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param endpoint
     * @return
     * @throws MalformedURLException
     * @throws DatatypeConfigurationException
     */
    public PersonaVO buscarPersona(final String tipoIdentificacion, final String numeroIdentificacion,
            final String usuario, String clave, final String endpoint) throws MalformedURLException,
            DatatypeConfigurationException {
        PersonaVO vo = null;
        Holder<ClsPerPersonaCompleta> persona = new Holder<ClsPerPersonaCompleta>();
        Holder<String> mensaje = new Holder<String>();
        mensaje.value = "";
        Holder<Integer> resultado = new Holder<Integer>();
        ServicePersonas servicio = new ServicePersonas(new URL(endpoint), QNAME_PERSONAS);
        ServicePersonasSoap port = servicio.getServicePersonasSoap12();
        Cabecera cabecera = new Cabecera();
        cabecera.setUserName(usuario);
        cabecera.setPassword(clave);
        port.fnConsultaBuscarPersonaCompleto(numeroIdentificacion, tipoIdentificacion, resultado, mensaje, persona,
                cabecera);
        if (resultado.value == 0) {
            // ok consulta
            vo = new PersonaVO();
            vo.setTipoIdentificacion(tipoIdentificacion);
            vo.setNumeroIdentificacion(numeroIdentificacion);
            vo.setApellidosNombres(persona.value.getPEDENOMINACION());
            vo.setApellidos(persona.value.getPEAPELLIDOS());
            vo.setNombres(persona.value.getPENOMBRES());
            vo.setEstadoCivil(persona.value.getPEESTADOCIVIL());
            vo.setMail(persona.value.getPEMAIL());
            vo.setFechaNacimiento(persona.value.getPEFECHANACIMIENTO().toGregorianCalendar().getTime());
        } else {
            System.out.println("Error:" + mensaje.value);
        }
        return vo;
    }
}
