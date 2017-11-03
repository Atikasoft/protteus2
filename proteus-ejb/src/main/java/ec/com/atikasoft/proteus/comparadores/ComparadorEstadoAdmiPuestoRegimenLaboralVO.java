/*
 *  ComparadorNivelOcupacional.java
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
 *  Sep 12, 2011
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.comparadores;

import ec.com.atikasoft.proteus.vo.EstadoAdministracionPuestoRegimenLaboralVO;
import ec.com.atikasoft.proteus.vo.TipoMovimientoRequisitoVO;
import java.util.Comparator;

/**
 * Comparador de nombres de requisitos en tipo de movimiento.
 *
 * @author Juan Carlos Vaca <jvaca@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class ComparadorEstadoAdmiPuestoRegimenLaboralVO implements Comparator<EstadoAdministracionPuestoRegimenLaboralVO> {

    /**
     * Constructor sin argumentos.
     */
    public ComparadorEstadoAdmiPuestoRegimenLaboralVO() {
        super();

    }

    @Override
    public int compare(final EstadoAdministracionPuestoRegimenLaboralVO o1, 
            final EstadoAdministracionPuestoRegimenLaboralVO o2) {
        return o1.getEstadoAdministracionPuesto().getNombre().compareToIgnoreCase(o2.getEstadoAdministracionPuesto()
                .getNombre());
    }
}
