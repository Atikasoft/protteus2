/*
 *  AnticipoVO.java
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
import ec.com.atikasoft.proteus.modelo.DesconcentradoApoyo;
import ec.com.atikasoft.proteus.modelo.DesconcentradoUnidadOrganizacional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * VO para la gestion de Desconcentrado
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@Getter
@Setter
public class DesconcentradoVO implements Serializable {

    /**
     * ENTIDAD DESCONCENTRADO
     */
    private Desconcentrado desconcentrado;

    /**
     * LISTA DE SERVIDORES DE APOYO
     */
    private List<DesconcentradoApoyo> listaDesconcentradoApoyos;

    /**
     * LISTA DE UNIDADES ORGANIZACIONALES ATENDIDAS POR EL DESCONCENTRADO
     */
    private List<DesconcentradoUnidadOrganizacional> listaDesconcentradosUniOrg;

    /**
     * Constructor por defecto.
     */
    public DesconcentradoVO() {
        super();
        desconcentrado = new Desconcentrado();
        listaDesconcentradoApoyos = new ArrayList<>();
        listaDesconcentradosUniOrg = new ArrayList<>();
    }

}
