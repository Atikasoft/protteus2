/*
 *  FeriadoHelper.java
 *  PROTEUS
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of PROTEUS
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  25/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Feriado;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * Feriado
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "feriadoHelper")
@SessionScoped
public class FeriadoHelper extends CatalogoHelper {

    /**
     * clase feriado.
     */
    private Feriado feriado;

    /**
     * clase feriado puesto para editar.
     */
    private Feriado feriadoEditDelete;

    /**
     * lista de feriados.
     */
    private List<Feriado> listaFeriados;

    /**
     * Variable para listar las alertas por codigo.
     */
    private List<Feriado> listaFeriadoCodigo;
    
     /**
     * Variable para listar las opciones de ejercicios fiscales.
     */
    private List<SelectItem> listaOpcionEjercicioFiscal;
        
     /**
     * Variable para listar las opciones de regimenes Laborales.
     */
    private List<SelectItem> listaOpcionRegimenLaboral;

    /**
     * Constructor por defecto.
     */
    public FeriadoHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del FeriadoHelper.
     */
    public final void iniciador() {
        setFeriado(new Feriado());
        getFeriado().setRegimenLaboral(new RegimenLaboral());
        setFeriadoEditDelete(new Feriado());
        getFeriadoEditDelete().setRegimenLaboral(new RegimenLaboral());
        setListaFeriados(new ArrayList<Feriado>());
       setListaFeriadoCodigo(new ArrayList<Feriado>());
        setListaOpcionEjercicioFiscal(new ArrayList<SelectItem>());
        listaOpcionRegimenLaboral=new ArrayList<SelectItem>();
    }

    /**
     * @return the feriado
     */
    public Feriado getFeriado() {
        return feriado;
    }

    /**
     * @param feriado the feriado to set
     */
    public void setFeriado(final Feriado feriado) {
        this.feriado = feriado;
    }

    /**
     * @return the feriadoEditDelete
     */
    public Feriado getFeriadoEditDelete() {
        return feriadoEditDelete;
    }

    /**
     * @param feriadoEditDelete the feriadoEditDelete to set
     */
    public void setFeriadoEditDelete(final Feriado feriadoEditDelete) {
        this.feriadoEditDelete = feriadoEditDelete;
    }

    /**
     * @return the listaFeriados
     */
    public List<Feriado> getListaFeriados() {
        return listaFeriados;
    }

    /**
     * @param listaFeriados the listaFeriados to set
     */
    public void setListaFeriados(final List<Feriado> listaFeriados) {
        this.listaFeriados = listaFeriados;
    }

    /**
     * @return the listaFeriadoCodigo
     */
    public List<Feriado> getListaFeriadoCodigo() {
        return listaFeriadoCodigo;
    }

    /**
     * @param listaFeriadoCodigo the listaFeriadoCodigo to set
     */
    public void setListaFeriadoCodigo(final List<Feriado> listaFeriadoCodigo) {
        this.listaFeriadoCodigo = listaFeriadoCodigo;
    }

    /**
     * @return the listaOpcionEjercicioFiscal
     */
    public List<SelectItem> getListaOpcionEjercicioFiscal() {
        return listaOpcionEjercicioFiscal;
    }

    /**
     * @param listaOpcionEjercicioFiscal the listaOpcionEjercicioFiscal to set
     */
    public void setListaOpcionEjercicioFiscal(List<SelectItem> listaOpcionEjercicioFiscal) {
        this.listaOpcionEjercicioFiscal = listaOpcionEjercicioFiscal;
    }

    /**
     * @return the listaOpcionRegimenLaboral
     */
    public List<SelectItem> getListaOpcionRegimenLaboral() {
        return listaOpcionRegimenLaboral;
    }

    /**
     * @param listaOpcionRegimenLaboral the listaOpcionRegimenLaboral to set
     */
    public void setListaOpcionRegimenLaboral(List<SelectItem> listaOpcionRegimenLaboral) {
        this.listaOpcionRegimenLaboral = listaOpcionRegimenLaboral;
    }
}
