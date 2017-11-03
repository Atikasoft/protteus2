/*
 *  NominaNovedadHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
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
 *  23/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.vo.NovedadNominaVO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * BusquedaPuestoHelper.
 *
 *
 */
@ManagedBean(name = "nominaNovedadHelper")
@SessionScoped
public class NominaNovedadHelper implements Serializable {

      
    private NovedadNominaVO novedadNominaVO;
    
    /**
     * Variable de la nomina.
     */
     private Nomina nomina;
     
   /**
     * Variable de la nomina.
     */
     private boolean aceptaCambios;
     /**
      * Variable para regresar a formulario.
      *  1.- Nomina - NovedadesNomina
      *  2.- Nomina DIferenciada - NovedadesNomina
      */
     private int retorno;
    /**
     * Constructor por defecto.
     */
    public NominaNovedadHelper() {
        super();
        nomina = new Nomina();
        novedadNominaVO = new NovedadNominaVO();
        aceptaCambios = false;
        retorno = 1;
    }

    /**
     * @return the nomina
     */
    public Nomina getNomina() {
        return nomina;
    }

    /**
     * @param nomina the nomina to set
     */
    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
    }

    /**
     * @return the novedadNominaVO
     */
    public NovedadNominaVO getNovedadNominaVO() {
        return novedadNominaVO;
    }

    /**
     * @param novedadNominaVO the novedadNominaVO to set
     */
    public void setNovedadNominaVO(NovedadNominaVO novedadNominaVO) {
        this.novedadNominaVO = novedadNominaVO;
    }

    /**
     * @return the aceptaCambios
     */
    public boolean isAceptaCambios() {
        return aceptaCambios;
    }

    /**
     * @param aceptaCambios the aceptaCambios to set
     */
    public void setAceptaCambios(boolean aceptaCambios) {
        this.aceptaCambios = aceptaCambios;
    }

    /**
     * @return the retorno
     */
    public int getRetorno() {
        return retorno;
    }

    /**
     * @param retorno the retorno to set
     */
    public void setRetorno(int retorno) {
        this.retorno = retorno;
    }
}
