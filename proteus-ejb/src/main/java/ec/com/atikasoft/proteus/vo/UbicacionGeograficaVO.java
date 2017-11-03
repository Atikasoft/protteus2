/*
 *  UbicacionGeograficaVO.java
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
 *  22/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

/**
 * UbicacionGeograficaVO
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class UbicacionGeograficaVO {

    /**
     * Id de pais.
     */
    private Long paisId;

    /**
     * Id de provincia.
     */
    private Long provinciaId;

    /**
     * Id de canton.
     */
    private Long cantonId;

    /**
     * Id de parroquia.
     */
    private Long parroquiaId;

    /**
     * Constructor por defecto.
     */
    public UbicacionGeograficaVO() {
        super();
    }

    /**
     * @return the paisId
     */
    public Long getPaisId() {
        return paisId;
    }

    /**
     * @param paisId the paisId to set
     */
    public void setPaisId(final Long paisId) {
        this.paisId = paisId;
    }

   
    /**
     * @return the provinciaId
     */
    public Long getProvinciaId() {
        return provinciaId;
    }

    /**
     * @param provinciaId the provinciaId to set
     */
    public void setProvinciaId(final Long provinciaId) {
        this.provinciaId = provinciaId;
    }

    /**
     * @return the cantonId
     */
    public Long getCantonId() {
        return cantonId;
    }

    /**
     * @param cantonId the cantonId to set
     */
    public void setCantonId(final Long cantonId) {
        this.cantonId = cantonId;
    }

    /**
     * @return the parroquiaId
     */
    public Long getParroquiaId() {
        return parroquiaId;
    }

    /**
     * @param parroquiaId the parroquiaId to set
     */
    public void setParroquiaId(final Long parroquiaId) {
        this.parroquiaId = parroquiaId;
    }
}
