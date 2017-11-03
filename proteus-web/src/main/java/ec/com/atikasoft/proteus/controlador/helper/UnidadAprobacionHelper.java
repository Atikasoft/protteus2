/*
 *  UnidadAprobacionHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
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
 *  07/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.UnidadAprobacion;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author LRodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "unidadAprobacionHelper")
@SessionScoped
public class UnidadAprobacionHelper extends CatalogoHelper {

    /**
     * Variable para nueva unidadAprobacion.
     */
    private UnidadAprobacion unidadAprobacion;
    /**
     * Variable para modificar/eliminar una unidadAprobacion.
     */
    private UnidadAprobacion unidadAprobacionEditDelete;
    /**
     * Variable para listar las unidadAprobacions.
     */
    private List<UnidadAprobacion> listaUnidadAprobacion;
   
    /**
     * Variable para listar las unidadAprobacions por codigo.
     */
    private List<UnidadAprobacion> listaUnidadAprobacionCodigo;
    
    /**
     * Variable para listar las unidades organizacionales
     */
     private List<UnidadOrganizacional> listaUnidadesOrganizacionales;
    private List <SelectItem> opcionesUnidadOrganizacional;
    private List <SelectItem> opcionesUnidadOrganizacionalAprob;
       private List<UnidadOrganizacional> listaUnidadesOrganizacionalesSinAprobador;
  
   

    /**
     * Constructor.
     */
    public UnidadAprobacionHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables de la UnidadAprobacionHelper.
     */
    public final void iniciador() {
        setUnidadAprobacion(new UnidadAprobacion());
        setUnidadAprobacionEditDelete(new UnidadAprobacion());
        setListaUnidadAprobacion(new ArrayList<UnidadAprobacion>());
        setListaUnidadAprobacionCodigo(new ArrayList<UnidadAprobacion>());
            setListaUnidadesOrganizacionales(new ArrayList<UnidadOrganizacional>());
                setOpcionesUnidadOrganizacional(new ArrayList<SelectItem>());
        setOpcionesUnidadOrganizacionalAprob(new ArrayList<SelectItem>());
        setListaUnidadesOrganizacionalesSinAprobador(new ArrayList<UnidadOrganizacional>());
    }

    /**
     * @return the unidadAprobacion
     */
    public UnidadAprobacion getUnidadAprobacion() {
        return unidadAprobacion;
    }

    /**
     * @param unidadAprobacion the unidadAprobacion to set
     */
    public void setUnidadAprobacion(final UnidadAprobacion unidadAprobacion) {
        this.unidadAprobacion = unidadAprobacion;
    }

    /**
     * @return the unidadAprobacionEditDelete
     */
    public UnidadAprobacion getUnidadAprobacionEditDelete() {
        return unidadAprobacionEditDelete;
    }

    /**
     * @param unidadAprobacionEditDelete the unidadAprobacionEditDelete to set
     */
    public void setUnidadAprobacionEditDelete(final UnidadAprobacion unidadAprobacionEditDelete) {
        this.unidadAprobacionEditDelete = unidadAprobacionEditDelete;
    }

    /**
     * @return the listaUnidadAprobacion
     */
    public List<UnidadAprobacion> getListaUnidadAprobacion() {
        return listaUnidadAprobacion;
    }

    /**
     * @param listaUnidadAprobacion the listaUnidadAprobacion to set
     */
    public void setListaUnidadAprobacion(final List<UnidadAprobacion> listaUnidadAprobacion) {
        this.listaUnidadAprobacion = listaUnidadAprobacion;
    }

    /**
     * @return the listaUnidadAprobacionNemonico
     */
    public List<UnidadAprobacion> getListaUnidadAprobacionCodigo() {
        return listaUnidadAprobacionCodigo;
    }

    /**
     * @param listaUnidadAprobacionCodigo the listaUnidadAprobacionCodigo to set
     */
    public void setListaUnidadAprobacionCodigo(List<UnidadAprobacion> listaUnidadAprobacionCodigo) {
        this.listaUnidadAprobacionCodigo = listaUnidadAprobacionCodigo;
    }
   
    /**
     * @return the listaUnidadesOrganizacionales
     */
    public List<UnidadOrganizacional> getListaUnidadesOrganizacionales() {
        return listaUnidadesOrganizacionales;
    }

    /**
     * @param listaUnidadesOrganizacionales the listaUnidadesOrganizacionales to set
     */
    public void setListaUnidadesOrganizacionales(List<UnidadOrganizacional> listaUnidadesOrganizacionales) {
        this.listaUnidadesOrganizacionales = listaUnidadesOrganizacionales;
    }

    /**
     * @return the opcionesUnidadOrganizacional
     */
    public List <SelectItem> getOpcionesUnidadOrganizacional() {
        return opcionesUnidadOrganizacional;
    }

    /**
     * @param opcionesUnidadOrganizacional the opcionesUnidadOrganizacional to set
     */
    public void setOpcionesUnidadOrganizacional(List <SelectItem> opcionesUnidadOrganizacional) {
        this.opcionesUnidadOrganizacional = opcionesUnidadOrganizacional;
    }

    /**
     * @return the opcionesUnidadOrganizacionalAprob
     */
    public List <SelectItem> getOpcionesUnidadOrganizacionalAprob() {
        return opcionesUnidadOrganizacionalAprob;
    }

    /**
     * @param opcionesUnidadOrganizacionalAprob the opcionesUnidadOrganizacionalAprob to set
     */
    public void setOpcionesUnidadOrganizacionalAprob(List <SelectItem> opcionesUnidadOrganizacionalAprob) {
        this.opcionesUnidadOrganizacionalAprob = opcionesUnidadOrganizacionalAprob;
    }

    /**
     * @return the listaUnidadesOrganizacionalesSinAprobador
     */
    public List<UnidadOrganizacional> getListaUnidadesOrganizacionalesSinAprobador() {
        return listaUnidadesOrganizacionalesSinAprobador;
    }

    /**
     * @param listaUnidadesOrganizacionalesSinAprobador the listaUnidadesOrganizacionalesSinAprobador to set
     */
    public void setListaUnidadesOrganizacionalesSinAprobador(List<UnidadOrganizacional> listaUnidadesOrganizacionalesSinAprobador) {
        this.listaUnidadesOrganizacionalesSinAprobador = listaUnidadesOrganizacionalesSinAprobador;
    }
}

