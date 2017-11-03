/*
 *  ComparadorTipoMovimientoReglaVO.java
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

import ec.com.atikasoft.proteus.vo.TipoMovimientoReglaVO;
import java.util.Comparator;

/**
 * Comparador de nombres de requisitos en tipo de movimiento.
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class ComparadorTipoMovimientoReglaVO implements Comparator<TipoMovimientoReglaVO> {

    /**
     * Constructor sin argumentos.
     */
    public ComparadorTipoMovimientoReglaVO() {
        super();

    }

    @Override
    public int compare(final TipoMovimientoReglaVO o1, final TipoMovimientoReglaVO o2) {
        return o1.getRegla().getNombre().compareToIgnoreCase(o2.getRegla().getNombre());
    }
}
