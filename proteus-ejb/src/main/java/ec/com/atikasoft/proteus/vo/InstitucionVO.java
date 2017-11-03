/*
 *  InstitucionVO.java
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
 *  20/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
public class InstitucionVO implements Serializable {



    /**
     * variable booleana para validar la seleccion de mi institucion del siith.
     */
    private Boolean seleccionar;

    /**
     * variable booleana para verificar el vigente.
     */
    private Boolean vigente;

    /**
     * Id del tipo movimiento.
     */
    private Long tipoMovimientoId;

    /**
     * Id de la institucion.
     */
    private Long institucionId;

    /**
     * constructor.
     */
    public InstitucionVO() {
        super();
    }

    /**
     * Constructor dos parametros.
     *
     * @param institucionSiith Institucion
     * @param seleccionar Boolean
     * @param tipoMovimientoId
     * @param institucionId
     */
//    public InstitucionVO(final Institucion institucionSiith,
//            final Boolean seleccionar,
//            final Long tipoMovimientoId,
//            final Long institucionId) {
//        this.institucionSiith = institucionSiith;
//        this.seleccionar = seleccionar;
//        this.tipoMovimientoId = tipoMovimientoId;
//        this.institucionId = institucionId;
//    }

    /**
     * @return the institucionSiith
     */
//    public Institucion getInstitucionSiith() {
//        return institucionSiith;
//    }
//
//    /**
//     * @param institucionSiith the institucionSiith to set
//     */
//    public void setInstitucionSiith(final Institucion institucionSiith) {
//        this.institucionSiith = institucionSiith;
//    }

    /**
     * @return the seleccionar
     */
    public Boolean getSeleccionar() {
        return seleccionar;
    }

    /**
     * @param seleccionar the seleccionar to set
     */
    public void setSeleccionar(final Boolean seleccionar) {
        this.seleccionar = seleccionar;
    }

    /**
     * @return the vigente
     */
    public Boolean getVigente() {
        return vigente;
    }

    /**
     * @param vigente the vigente to set
     */
    public void setVigente(final Boolean vigente) {
        this.vigente = vigente;
    }

    /**
     * @return the tipoMovimientoId
     */
    public Long getTipoMovimientoId() {
        return tipoMovimientoId;
    }

    /**
     * @param tipoMovimientoId the tipoMovimientoId to set
     */
    public void setTipoMovimientoId(final Long tipoMovimientoId) {
        this.tipoMovimientoId = tipoMovimientoId;
    }

    /**
     * @return the institucionId
     */
    public Long getInstitucionId() {
        return institucionId;
    }

    /**
     * @param institucionId the institucionId to set
     */
    public void setInstitucionId(Long institucionId) {
        this.institucionId = institucionId;
    }
}
