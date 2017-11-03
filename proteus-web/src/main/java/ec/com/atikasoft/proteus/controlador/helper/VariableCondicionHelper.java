/*
 *  VariableCondicionHelper.java
 *  proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with proteus.
 *
 *  proteus
 *  Quito - Ecuador
 *
 *  08/08/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.VariableCondicion;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * VariableCondicion
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "variableCondicionHelper")
@SessionScoped
public class VariableCondicionHelper extends CatalogoHelper {

    /**
     * clase variableCondicion.
     */
    private VariableCondicion variableCondicion;

    /**
     * clase variableCondicion puesto para editar.
     */
    private VariableCondicion variableCondicionEditDelete;

    /**
     * lista de variableCondiciones.
     */
    private List<VariableCondicion> listaVariableCondiciones;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<VariableCondicion> listaVariableCondicionCodigo;
    
     /**
     * Lista de tipos de orgigen de los datos.
     */
    private List<SelectItem> operadorComparacion;
    
   /**
    * variable para concatenar las condiciones
    */
    private String cadenaCondicion;
    /**
     * Variable para saber si se esta editando un registro
     */
    private Boolean esNuevaCondicion;
   
    
    /**
     * Constructor por defecto.
     */
    public VariableCondicionHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del VariableCondicionHelper.
     */
    public final void iniciador() {
        setVariableCondicion(new VariableCondicion());
        setVariableCondicionEditDelete(new VariableCondicion());
        setListaVariableCondiciones(new ArrayList<VariableCondicion>());
       setListaVariableCondicionCodigo(new ArrayList<VariableCondicion>());
       setOperadorComparacion(new ArrayList<SelectItem>());
       setCadenaCondicion(new String());
       setEsNuevaCondicion(Boolean.TRUE);
    }

    /**
     * @return the variableCondicion
     */
    public VariableCondicion getVariableCondicion() {
        return variableCondicion;
    }

    /**
     * @param variableCondicion the variableCondicion to set
     */
    public void setVariableCondicion(final VariableCondicion variableCondicion) {
        this.variableCondicion = variableCondicion;
    }

    /**
     * @return the variableCondicionEditDelete
     */
    public VariableCondicion getVariableCondicionEditDelete() {
        return variableCondicionEditDelete;
    }

    /**
     * @param variableCondicionEditDelete the variableCondicionEditDelete to set
     */
    public void setVariableCondicionEditDelete(final VariableCondicion variableCondicionEditDelete) {
        this.variableCondicionEditDelete = variableCondicionEditDelete;
    }

    /**
     * @return the listaVariableCondiciones
     */
    public List<VariableCondicion> getListaVariableCondiciones() {
        return listaVariableCondiciones;
    }

    /**
     * @param listaVariableCondiciones the listaVariableCondiciones to set
     */
    public void setListaVariableCondiciones(final List<VariableCondicion> listaVariableCondiciones) {
        this.listaVariableCondiciones = listaVariableCondiciones;
    }

    /**
     * @return the listaVariableCondicionCodigo
     */
    public List<VariableCondicion> getListaVariableCondicionCodigo() {
        return listaVariableCondicionCodigo;
    }

    /**
     * @param listaVariableCondicionCodigo the listaVariableCondicionCodigo to set
     */
    public void setListaVariableCondicionCodigo(final List<VariableCondicion> listaVariableCondicionCodigo) {
        this.listaVariableCondicionCodigo = listaVariableCondicionCodigo;
    }

    /**
     * @return the operadorComparacion
     */
    public List<SelectItem> getOperadorComparacion() {
        return operadorComparacion;
    }

    /**
     * @param operadorComparacion the operadorComparacion to set
     */
    public void setOperadorComparacion(List<SelectItem> operadorComparacion) {
        this.operadorComparacion = operadorComparacion;
    }

    /**
     * @return the cadenaCondicion
     */
    public String getCadenaCondicion() {
        return cadenaCondicion;
    }

    /**
     * @param cadenaCondicion the cadenaCondicion to set
     */
    public void setCadenaCondicion(String cadenaCondicion) {
        this.cadenaCondicion = cadenaCondicion;
    }
    /**
     * @return the esNuevaCondicion
     */
    public Boolean getEsNuevaCondicion() {
        return esNuevaCondicion;
    }

    /**
     * @param esNuevaCondicion the esNuevaCondicion to set
     */
    public void setEsNuevaCondicion(Boolean esNuevaCondicion) {
        this.esNuevaCondicion = esNuevaCondicion;
    }

    
}
