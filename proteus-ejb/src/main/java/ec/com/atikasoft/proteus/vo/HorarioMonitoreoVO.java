/*
 *  AccesoServidorVO.java
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
 *  02/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Desconcentrado;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.HorarioDesconcentrado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * VO para gestión del monitoreo de asignacion de horarios
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@Getter
@Setter
public class HorarioMonitoreoVO implements Serializable {

    /**
     * Unidad desconcentrada.
     */
    private Desconcentrado desconcentrado;
    
    /**
     * Lista de horarios que han sido asociados a la unidad desconcentrada
     */
    private List<HorarioDesconcentrado> horariosAsociados;
    /**
     * Lista de puestos de los servidores asociados a la unidad desconcetrada con o sin horario.
     */
    private List<DistributivoDetalle> puestos;
    
    /**
     * Constructor por defecto.
     */
    public HorarioMonitoreoVO() {
        super();
        desconcentrado = new Desconcentrado();
        horariosAsociados = new ArrayList<>();
        puestos = new ArrayList<>();
    }

}
