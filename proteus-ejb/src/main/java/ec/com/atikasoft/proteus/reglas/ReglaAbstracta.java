/*
 *  ReglaAbstracta.java
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
package ec.com.atikasoft.proteus.reglas;

import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.Regla;
import java.util.List;
import java.util.Map;

/**
 * Permite definir una plantilla de reglas.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public abstract class ReglaAbstracta {

    /**
     * Mensaje de error no controlado para una regla.
     */
    protected final static String MENSAJE_ERROR_GENERAL =
            "Se presento un error no controlado en la ejecución de la regla.";

    /**
     * Constructor sin argumentos.
     */
    public ReglaAbstracta() {
        super();
    }

    public abstract void validar(Map<String, Object> parametros, List<ReglaMensaje> mensajes, Boolean obligatorio,
            Regla regla);

    /**
     * Registra mensaje de error.
     *
     * @param mensaje
     * @param obligatorio
     * @return
     */
    protected void registrarMensaje(final String mensaje, final Boolean obligatorio, final List<ReglaMensaje> mensajes,
            final Regla regla, final Movimiento movimiento) {
        ReglaMensaje r = new ReglaMensaje();
        r.setMensaje(mensaje);
        r.setObligatorio(obligatorio);
        r.setRegla(regla);
        if (movimiento != null) {
            r.setPartidaIndividual(movimiento.getDistributivoDetalle().getPartidaIndividual());
        }
        mensajes.add(r);
    }

    /**
     * Recupera el una parametro global.
     *
     * @param nemonico
     * @param parametros
     * @return
     */
    protected ParametroGlobal buscarParametroGlobal(final String nemonico, final Map<String, Object> parametros) {
        List<ParametroGlobal> ps =
                (List<ParametroGlobal>) parametros.get(ReglasParametrosEnum.PARAMETROS_GLOBALES.getCodigo());
        ParametroGlobal obj = null;
        for (ParametroGlobal pg : ps) {
            if (pg.getNemonico().equals(nemonico)) {
                obj = pg;
                break;
            }
        }
        return obj;
    }
}
