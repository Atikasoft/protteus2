/*
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
 *  Mar 16, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.singlenton;

/**
 * Singlenton thread safe del sistema.
 *
 * @author Henry Molina  <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class SinglentoSistema {

    /**
     * Instancia del singleton.
     */
    private static SinglentoSistema instancia;

    /**
     * Variables del sistema.
     */
    private static VariablesSistema variablesSistema = new VariablesSistema();

    /**
     * Constructor sin argumentos.
     */
    private SinglentoSistema() {
        super();
    }

    public static SinglentoSistema getInstancia() {
        if (instancia == null) {
            synchronized (variablesSistema) {
                if (instancia == null) {
                    instancia = new SinglentoSistema();
                }
            }
        }
        return instancia;
    }

    /**
     * @return the variablesSistema
     */
    public VariablesSistema getVariablesSistema() {
        return variablesSistema;
    }

    /**
     * @param variablesSistema the variablesSistema to set
     */
    public void setVariablesSistema(final VariablesSistema variablesSistema) {
        SinglentoSistema.variablesSistema = variablesSistema;
    }
}
