/*
 *  HorarioVO.java
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
 *  04/02/2013
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.LicenciaHorario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * VO de Horario.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class HorarioVO implements Serializable {

    /**
     * Total de horas normales o licencia-permisos.
     */
    private Long totalHoras = 0L;

    /**
     * Total horario recuperacion.
     */
    private Long totalHorasRecuperacion = 0L;

    /**
     * Horario independiente.
     */
    private List<LicenciaHorario> horario = new ArrayList<LicenciaHorario>();

    /**
     * Constructor por defecto.
     */
    public HorarioVO() {
        super();
    }

    /**
     * @return the totalHoras
     */
    public Long getTotalHoras() {
        return totalHoras;
    }

    /**
     * @param totalHoras the totalHoras to set
     */
    public void setTotalHoras(final Long totalHoras) {
        this.totalHoras = totalHoras;
    }

    /**
     * @return the totalHorasRecuperacion
     */
    public Long getTotalHorasRecuperacion() {
        return totalHorasRecuperacion;
    }

    /**
     * @param totalHorasRecuperacion the totalHorasRecuperacion to set
     */
    public void setTotalHorasRecuperacion(final Long totalHorasRecuperacion) {
        this.totalHorasRecuperacion = totalHorasRecuperacion;
    }

    /**
     * @return the horario
     */
    public List<LicenciaHorario> getHorario() {
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(final List<LicenciaHorario> horario) {
        this.horario = horario;
    }
}
