/*
 *  CargaMasivaMovimientoVO.java
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
 *  Apr 27, 2014
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import java.util.List;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class CargaMasivaMovimientoVO {

    /**
     * Distributuvo.
     */
    private DistributivoDetalle distributivoDetalle;

    /**
     * Datos de la carga.
     */
    private List<String> datos;
    
    /**
     * Nombre del servidor.
     */
    private String nombreServidor;

    /**
     * Constructor sin argumentos.
     */
    public CargaMasivaMovimientoVO() {
        super();
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @return the datos
     */
    public List<String> getDatos() {
        return datos;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(final DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }

    /**
     * @param datos the datos to set
     */
    public void setDatos(final List<String> datos) {
        this.datos = datos;
    }

    /**
     * @return the nombreServidor
     */
    public String getNombreServidor() {
        return nombreServidor;
    }

    /**
     * @param nombreServidor the nombreServidor to set
     */
    public void setNombreServidor(final String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }
}
