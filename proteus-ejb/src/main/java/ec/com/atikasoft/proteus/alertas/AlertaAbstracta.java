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
package ec.com.atikasoft.proteus.alertas;

import ec.com.atikasoft.proteus.enums.TipoPeriodoAlertaEnum;
import ec.com.atikasoft.proteus.modelo.Alerta;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoAlerta;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Permite definir una plantilla de alerta.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public abstract class AlertaAbstracta {

    /**
     * Constructor sin argumentos.
     */
    public AlertaAbstracta() {
        super();
    }

    /**
     *
     * @param parametros parametros de la alertas
     * @param tipoMovimientoAlerta alerta del tipo de movimiento
     */
    public abstract void ejecutar(Map<String, Object> parametros, TipoMovimientoAlerta tipoMovimientoAlerta);

    /**
     *
     * @param nemonico codigo del parametros
     * @param parametros lista de parametros globales
     * @return datos del parametro
     */
    protected ParametroGlobal buscarParametroGlobal(final String nemonico, final List<ParametroGlobal> parametros) {
        ParametroGlobal obj = null;
        for (ParametroGlobal pg : parametros) {
            if (pg.getNemonico().equals(nemonico)) {
                obj = pg;
                break;
            }
        }
        return obj;
    }

    /**
     * Determina las horas antes que se debe ejecutar la comprobacion.
     *
     * @param alerta datos de la alerta
     * @return lista de horas
     */
    protected List<Integer> determinarHorasAntes(final Alerta alerta) {
        List<Integer> horasAntes = new ArrayList<>();
        if (alerta.getTipoPeriodo().equals(TipoPeriodoAlertaEnum.DIA.getCodigo())) {
            horasAntes.add(alerta.getValorPeriodo() * 24);
            if (alerta.getValorPeriodo2() != null) {
                horasAntes.add(alerta.getValorPeriodo2() * 24);
            }
            if (alerta.getValorPeriodo3() != null) {
                horasAntes.add(alerta.getValorPeriodo3() * 24);
            }
            if (alerta.getValorPeriodo4() != null) {
                horasAntes.add(alerta.getValorPeriodo4() * 24);
            }
            if (alerta.getValorPeriodo5() != null) {
                horasAntes.add(alerta.getValorPeriodo5() * 24);
            }
        } else if (alerta.getTipoPeriodo().equals(TipoPeriodoAlertaEnum.HORA.getCodigo())) {
            horasAntes.add(alerta.getValorPeriodo());
            if (alerta.getValorPeriodo2() != null) {
                horasAntes.add(alerta.getValorPeriodo2());
            }
            if (alerta.getValorPeriodo3() != null) {
                horasAntes.add(alerta.getValorPeriodo3());
            }
            if (alerta.getValorPeriodo4() != null) {
                horasAntes.add(alerta.getValorPeriodo4());
            }
            if (alerta.getValorPeriodo5() != null) {
                horasAntes.add(alerta.getValorPeriodo5());
            }
        }
        return horasAntes;
    }
}
