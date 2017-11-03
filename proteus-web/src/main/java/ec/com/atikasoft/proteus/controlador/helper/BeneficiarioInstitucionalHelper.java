/*
 *  BeneficiarioInstitucionalHelper.java
 *
 *  PROTEUS V 2.0 $Revision 1.0 $
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
import ec.com.atikasoft.proteus.modelo.BeneficiarioEspecial;
import ec.com.atikasoft.proteus.modelo.BeneficiarioInstitucional;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.vo.PersonaVO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;

/**
 * BeneficiarioInstitucional
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "beneficiarioInstitucionalHelper")
@SessionScoped
@Setter
@Getter
public class BeneficiarioInstitucionalHelper extends CatalogoHelper {

    /**
     * clase beneficiarioInstitucional.
     */
    private BeneficiarioInstitucional beneficiarioInstitucional;
    /**
     * clase beneficiarioInstitucional puesto para editar.
     */
    private BeneficiarioInstitucional beneficiarioInstitucionalEditDelete;
    /**
     * lista de beneficiarioInstitucionals.
     */
    private List<BeneficiarioInstitucional> listaBeneficiarioInstitucionales;
    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<BeneficiarioInstitucional> listaBeneficiarioInstitucionalCodigo;
    /**
     * Lista de servidores a agregar.
     */
    private List<Servidor> listaServidoress = new ArrayList<>();

    /**
     * Variable para Lista de rubros de descuento
     */
    private List<SelectItem> listaOpcionesRubro;
    /**
     * Variable para Lista de tipo de documentos
     */
    private List<SelectItem> listaOpcionesTipoDocumento;

    /**
     * Variable para administrar al beneficiarioEspecial.
     */
    private BeneficiarioEspecial beneficiarioEspecial;
    private BeneficiarioEspecial beneficiarioEspecialTransaccion;
    private List<BeneficiarioEspecial> listaBeneficiarioEspecial;
    private List<BeneficiarioEspecial> listaBeneficiarioEspecialEliminados;

    /**
     * Variable para administrar la busqueda del beneficiario normal y especial.
     */
    private PersonaVO personaVO;
    /**
     * Variable para administrar al beneficiarioEspecial.
     */
    private List<SelectItem> listaOpcionesServidores;

    /**
     *
     */
    private Boolean registroManualNombres;

    /**
     * Constructor por defecto.
     */
    public BeneficiarioInstitucionalHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del BeneficiarioInstitucionalHelper.
     */
    public final void iniciador() {
        setBeneficiarioInstitucional(new BeneficiarioInstitucional());
        setBeneficiarioInstitucionalEditDelete(new BeneficiarioInstitucional());
        setListaBeneficiarioInstitucionales(new ArrayList<BeneficiarioInstitucional>());
        setListaBeneficiarioInstitucionalCodigo(new ArrayList<BeneficiarioInstitucional>());
        setBeneficiarioEspecial(new BeneficiarioEspecial());
        setListaBeneficiarioEspecial(new ArrayList<BeneficiarioEspecial>());
        setListaOpcionesRubro(new ArrayList<SelectItem>());
        setListaBeneficiarioEspecialEliminados(new ArrayList<BeneficiarioEspecial>());
        setListaOpcionesTipoDocumento(new ArrayList<SelectItem>());
        setListaOpcionesServidores(new ArrayList<SelectItem>());
        setRegistroManualNombres(Boolean.FALSE);
        personaVO = new PersonaVO();

    }

}
