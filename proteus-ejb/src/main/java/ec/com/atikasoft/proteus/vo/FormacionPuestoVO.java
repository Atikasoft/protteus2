/*
 *  FormacionPuestoVO.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  26/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * FormacionPuestoVO
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class FormacionPuestoVO implements Serializable {

    private Date fechaInicio;

    private Date fechaFin;

    private Long regimenLaboral;

    private String partidaGeneral;

    private Long nivelOcupacional;

    private String partidaIndividual;

    private String escalaOcupacional;

    private Long escalaOcupacionalId;

    private String rmu;

    private String puestoIndivudual;

    private Long puestoIndivudualId;

    private String rmuSobreValorado;

    private String puestoAdicional;

    private String unidadAdministrativa;

    private Long unidadAdministrativaId;

    private Long modalidadLaboral;

    /**
     * Constructor por defecto.
     */
    public FormacionPuestoVO() {
        super();
    }
}
