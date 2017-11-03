/*
 *  UnidadPresupuestariaHelper.java
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
 *  20/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.CertificacionPresupuestaria;
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.UnidadPresupuestaria;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * UnidadPresupuestaria
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "unidadPresupuestariaHelper")
@SessionScoped
@Getter
@Setter
public class UnidadPresupuestariaHelper extends CatalogoHelper {

    /**
     * clase unidadPresupuestaria.
     */
    private UnidadPresupuestaria unidadPresupuestaria;
       /**
     * clase unidadPresupuestaria.
     */
    private UnidadPresupuestaria unidadPresupuestariaEditDelete;

    /**
     * lista de unidadPresupuestarias.
     */
    private List<UnidadPresupuestaria> listaUnidadesPresupuestarias;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<UnidadPresupuestaria> listaUnidadPresupuestariaCodigo;
   
        /**
     * Lista de grupos presupuestarios.
     */
    private List<SelectItem> listaGruposPresupuestarios;
    
     /**
     * Lista de sectores.
     */
    private List<SelectItem> listaSectores;
    
         /**
     * Lista de de tipos.
     */
    private List<SelectItem> listaTipos;
    
    /**
     * Lista de certificaciones presupuestarias.
     */
    private List<CertificacionPresupuestaria> certificacionesPresupuestarias;


    /**
     * Constructor por defecto.
     */
    public UnidadPresupuestariaHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del UnidadPresupuestariaHelper.
     */
    public final void iniciador() {
        setUnidadPresupuestaria(new UnidadPresupuestaria());
        setListaUnidadesPresupuestarias(new ArrayList<UnidadPresupuestaria>());
        setListaUnidadPresupuestariaCodigo(new ArrayList<UnidadPresupuestaria>());
        certificacionesPresupuestarias = new ArrayList<>();
        listaGruposPresupuestarios = new ArrayList<SelectItem>();
        unidadPresupuestariaEditDelete=new UnidadPresupuestaria();
        listaSectores = new ArrayList<SelectItem>();
        listaTipos = new ArrayList<SelectItem>();
    }

}
