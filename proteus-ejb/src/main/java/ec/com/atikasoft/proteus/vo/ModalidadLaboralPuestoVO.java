/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import java.util.HashMap;
import java.util.Map;

/**
 * Modela la cantidad de puestos generados para una modalidad desglozado por unidades organizacionales
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
public class ModalidadLaboralPuestoVO {

    /**
     *
     */
    private ModalidadLaboral modalidadLaboral;

    /**
     * key es el id de la unidad value es el total para esa unidad
     */
    private Map<Long, Integer> valoresUnidad = new HashMap<>();

    public ModalidadLaboral getModalidadLaboral() {
        return modalidadLaboral;
    }

    public void setModalidadLaboral(ModalidadLaboral modalidadLaboral) {
        this.modalidadLaboral = modalidadLaboral;
    }

    public Map<Long, Integer> getValoresUnidad() {
        return valoresUnidad;
    }

    public void setValoresUnidad(Map<Long, Integer> valoresUnidad) {
        this.valoresUnidad = valoresUnidad;
    }

    /**
     * Recupera el numero de puestos asignados en esa modalidad laboral para una
     * unidad organizacional especifica
     * @param idUnidad
     * @return 
     */
    public Integer valorEnUnidad(final Long idUnidad) {
        Integer valor = 0;
        if (valoresUnidad.containsKey(idUnidad)) {
            valor = valoresUnidad.get(idUnidad);
        }
        return valor;
    }
}
