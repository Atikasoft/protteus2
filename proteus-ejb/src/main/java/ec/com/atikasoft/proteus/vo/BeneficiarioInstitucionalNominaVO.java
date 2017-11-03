/*
 *  BeneficiarioInstitucionalNominaVO.java
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
 *  Apr 6, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.BeneficiarioInstitucional;
import ec.com.atikasoft.proteus.modelo.Rubro;

/**
 * Contiene los beneficiarios institucionales x rubro.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class BeneficiarioInstitucionalNominaVO {

    /**
     * Rubro.
     */
    private Rubro rubro;

    /**
     * Beneficiario institucional.
     */
    private BeneficiarioInstitucional beneficiarioInstitucional;

    /**
     * Constructor sin argumentos.
     */
    public BeneficiarioInstitucionalNominaVO() {
        super();

    }

    /**
     * @return the rubro
     */
    public Rubro getRubro() {
        return rubro;
    }

    /**
     * @return the beneficiarioInstitucional
     */
    public BeneficiarioInstitucional getBeneficiarioInstitucional() {
        return beneficiarioInstitucional;
    }

    /**
     * @param rubro the rubro to set
     */
    public void setRubro(final Rubro rubro) {
        this.rubro = rubro;
    }

    /**
     * @param beneficiarioInstitucional the beneficiarioInstitucional to set
     */
    public void setBeneficiarioInstitucional(final BeneficiarioInstitucional beneficiarioInstitucional) {
        this.beneficiarioInstitucional = beneficiarioInstitucional;
    }
}
