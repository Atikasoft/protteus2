/*
 *  Test.java
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
package ec.com.atikasoft.proteus.consumer.mdmq.util;

import ec.com.atikasoft.proteus.consumer.mdmq.ConsumerMDMQ;
import ec.com.atikasoft.proteus.consumer.mdmq.vo.PersonaVO;
import ec.com.atikasoft.proteus.consumer.mdmq.vo.ResultadoAutentificarVO;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class Test {

    /**
     * Constructor sin argumentos.
     */
    public Test() {
        super();

    }

    public static void main(String[] args) {
        ConsumerMDMQ ws = new ConsumerMDMQ();
        try {
            PersonaVO p = ws.buscarPersona("C", "0702619321", "30", "123456",
                    "http://172.22.8.108/MDMQ_WS_PERSONAS2012/Presentacion/PersonaSQL.asmx");
            System.out.println(p.getApellidosNombres());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
