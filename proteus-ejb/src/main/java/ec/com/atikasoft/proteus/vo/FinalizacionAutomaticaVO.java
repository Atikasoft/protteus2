/*
 *  FinalizacionAutomaticaVO.java
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
 *  Jul 23, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class FinalizacionAutomaticaVO {

    /**
     *
     */
    private InstitucionEjercicioFiscal institucion;

    /**
     *
     */
    private List<String> errores;

    /**
     *
     */
    private List<String> tramites;
    /**
     * correos.
     */
    private String correoAdministrado;
    private String correoRHH;

    /**
     * Constructor sin argumentos.
     */
    public FinalizacionAutomaticaVO() {
        super();
        errores = new ArrayList<String>();
        tramites = new ArrayList<String>();
    }

    /**
     * @return the institucion
     */
    public InstitucionEjercicioFiscal getInstitucion() {
        return institucion;
    }

    /**
     * @return the errores
     */
    public List<String> getErrores() {
        return errores;
    }

    /**
     * @return the tramites
     */
    public List<String> getTramites() {
        return tramites;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(InstitucionEjercicioFiscal institucion) {
        this.institucion = institucion;
    }

    /**
     * @param errores the errores to set
     */
    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    /**
     * @param tramites the tramites to set
     */
    public void setTramites(List<String> tramites) {
        this.tramites = tramites;
    }

    /**
     * @return the correoAdministrado
     */
    public String getCorreoAdministrado() {
        return correoAdministrado;
    }

    /**
     * @param correoAdministrado the correoAdministrado to set
     */
    public void setCorreoAdministrado(String correoAdministrado) {
        this.correoAdministrado = correoAdministrado;
    }

    /**
     * @return the correoRHH
     */
    public String getCorreoRHH() {
        return correoRHH;
    }

    /**
     * @param correoRHH the correoRHH to set
     */
    public void setCorreoRHH(String correoRHH) {
        this.correoRHH = correoRHH;
    }
}
