/*
 *  ConstanteHelper.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *x|
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  02/10/2013
 *
 */

package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Constante;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;


/**
 *
 * @author LRodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean (name = "constanteHelper")
@SessionScoped
public class ConstanteHelper extends CatalogoHelper {
    /**
     * Variable para nueva constante.
     */
    private Constante constante;
    /**
     * Variable para modificar/eliminar una constante.
     */
    private Constante constanteEditDelete;
    /**
     * Variable para listar las constantes.
     */
    private List<Constante> listaConstante;
    /**
     * Variable para listar las constantes por codigo.
     */
    private List<Constante> listaConstanteCodigo;
    /**
     * Lista de tipos de periodos.
     */
    private List<SelectItem> tipoDato;
    
    private Boolean valor;
    
    private String prefijoCodigo;
    /**
     * Constructor.
     */
    public ConstanteHelper() {
        super();
        iniciador();
    }
    /**
     * Método para iniciar las variables de la ConstanteHelper.
     */
    public final void iniciador() {
        setConstante(new Constante()); 
        setConstanteEditDelete(new Constante());
        setListaConstante(new ArrayList<Constante>());
        setTipoDato(new ArrayList<SelectItem>());
        setListaConstanteCodigo(new ArrayList<Constante>());
        setValor(Boolean.FALSE);
        prefijoCodigo ="C_";
    }

    /**
     * @return the constante
     */
    public Constante getConstante() {
        return constante;
    }

    /**
     * @param constante the constante to set
     */
    public void setConstante(final Constante constante) {
        this.constante = constante;
    }

    /**
     * @return the constanteEditDelete
     */
    public Constante getConstanteEditDelete() {
        return constanteEditDelete;
    }

    /**
     * @param constanteEditDelete the constanteEditDelete to set
     */
    public void setConstanteEditDelete(final Constante constanteEditDelete) {
        this.constanteEditDelete = constanteEditDelete;
    }

    /**
     * @return the listaConstante
     */
    public List<Constante> getListaConstante() {
        return listaConstante;
    }

    /**
     * @param listaConstante the listaConstante to set
     */
    public void setListaConstante(final List<Constante> listaConstante) {
        this.listaConstante = listaConstante;
    }

    /**
     * @return the listaConstanteNemonico
     */
    public List<Constante> getListaConstanteCodigo() {
        return listaConstanteCodigo;
    }

    /**
     * @param listaConstanteCodigo the listaConstanteCodigo to set
     */
    public void setListaConstanteCodigo(List<Constante> listaConstanteCodigo) {
        this.listaConstanteCodigo = listaConstanteCodigo;
    }

    /**
     * @return the tipoDato
     */
    public List<SelectItem> getTipoDato() {
        return tipoDato;
    }

    /**
     * @param tipoDato the tipoDato to set
     */
    public void setTipoDato(List<SelectItem> tipoDato) {
        this.tipoDato = tipoDato;
    }

    /**
     * @return the valor
     */
    public Boolean isValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Boolean valor) {
        this.valor = valor;
    }

    /**
     * @return the prefijoCodigo
     */
    public String getPrefijoCodigo() {
        return prefijoCodigo;
    }

    /**
     * @param prefijoCodigo the prefijoCodigo to set
     */
    public void setPrefijoCodigo(String prefijoCodigo) {
        this.prefijoCodigo = prefijoCodigo;
    }
}
