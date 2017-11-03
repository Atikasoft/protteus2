/*
 *  RubroHelper.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *
 *  14/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.ClasificadorGasto;
import ec.com.atikasoft.proteus.modelo.CodigoContable;
import ec.com.atikasoft.proteus.modelo.DatoAdicional;
import ec.com.atikasoft.proteus.modelo.Formula;
import ec.com.atikasoft.proteus.modelo.PeriodoNomina;
import ec.com.atikasoft.proteus.modelo.Rubro;
import ec.com.atikasoft.proteus.modelo.TipoNomina;
import ec.com.atikasoft.proteus.vo.PersonaVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * Rubro
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "rubroHelper")
@SessionScoped
public class RubroHelper extends CatalogoHelper {

    /**
     * clase rubro.
     */
    private Rubro rubro;

    /**
     * clase rubro puesto para editar.
     */
    private Rubro rubroEditDelete;

    /**
     * Lista de usos del rubro.
     */
    private PersonaVO personaVO = new PersonaVO();

    /**
     * lista de rubros.
     */
    private List<Rubro> listaRubros;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<Rubro> listaRubroCodigo;

    /**
     * Lista de tipos de rubros.
     */
    private List<SelectItem> tipo;

    /**
     * Lista de tipos de rubros.
     */
    private List<SelectItem> tipoDocumento;

    /**
     * Lista de tipos de beneficiario.
     */
    private List<SelectItem> tipoBeneficiario;

    /**
     * Lista de usos del rubro.
     */
    private List<SelectItem> usoRubro;

    /**
     * Opciones de Datos Adicionales.
     */
    private List<SelectItem> opcionDatoAdicional;

    /**
     *
     */
    private List<DatoAdicional> listaDatosAdicionales;

    /**
     * Opciones de Datos Formula.
     */
    private List<SelectItem> opcionFormula;

    /**
     *
     */
    private List<SelectItem> opcionPartida;

    /**
     *
     */
    private List<Formula> listaFormulas;

    /**
     * Lista de Rubros Tipo Nomina
     */
    private List<TipoNomina> listaTipoNomina;

    /**
     * Lista de Rubros Periodo Nomina
     */
    private List<PeriodoNomina> listaPeriodoNomina;

    /**
     * Lista de Codigos Contables vigentes
     */
    private List<CodigoContable> listaCodigoContable;

    private List<SelectItem> opcionCodigoContable;

    /**
     * Lista de Codigos Contables vigentes
     */
    private List<ClasificadorGasto> listaClasificadorGasto;

    private List<SelectItem> opcionClasificadorGasto;

    /**
     * Opciones de Datos Formula.
     */
    private List<SelectItem> opcionPrioridad;

    /**
     * Variables para indicar si el componente ha sido modificado
     */
    private boolean editaRubroTipoNomina;

    private boolean editaRubroPeriodoNomina;

    /**
     * variable para ver si existe beneficiario
     */
    private Boolean existeBeneficiario = Boolean.FALSE;

    /**
     * Constructor por defecto.
     */
    public RubroHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del RubroHelper.
     */
    public final void iniciador() {
        setRubro(new Rubro());
        setRubroEditDelete(new Rubro());
        setListaRubros(new ArrayList<Rubro>());
        setListaRubroCodigo(new ArrayList<Rubro>());

        setTipo(new ArrayList<SelectItem>());
        setTipoBeneficiario(new ArrayList<SelectItem>());
        setOpcionDatoAdicional(new ArrayList<SelectItem>());
        setOpcionFormula(new ArrayList<SelectItem>());
        setUsoRubro(new ArrayList<SelectItem>());
        setListaFormulas(new ArrayList<Formula>());
        setListaDatosAdicionales(new ArrayList<DatoAdicional>());
        setListaTipoNomina(new ArrayList<TipoNomina>());
        setListaPeriodoNomina(new ArrayList<PeriodoNomina>());
        setListaCodigoContable(new ArrayList<CodigoContable>());
        setListaClasificadorGasto(new ArrayList<ClasificadorGasto>());
        setOpcionPrioridad(new ArrayList<SelectItem>());
        setEditaRubroTipoNomina(false);
        setEditaRubroPeriodoNomina(false);
        setOpcionCodigoContable(new ArrayList<SelectItem>());
        setOpcionClasificadorGasto(new ArrayList<SelectItem>());
        setOpcionPartida(new ArrayList<SelectItem>());
        tipoDocumento = new ArrayList<SelectItem>();
    }

    /**
     * @return the rubro
     */
    public Rubro getRubro() {
        return rubro;
    }

    /**
     * @param rubro the rubro to set
     */
    public void setRubro(final Rubro rubro) {
        this.rubro = rubro;
    }

    /**
     * @return the rubroEditDelete
     */
    public Rubro getRubroEditDelete() {
        return rubroEditDelete;
    }

    /**
     * @param rubroEditDelete the rubroEditDelete to set
     */
    public void setRubroEditDelete(final Rubro rubroEditDelete) {
        this.rubroEditDelete = rubroEditDelete;
    }

    /**
     * @return the listaRubroCodigo
     */
    public List<Rubro> getListaRubroCodigo() {
        return listaRubroCodigo;
    }

    /**
     * @param listaRubroCodigo the listaRubroCodigo to set
     */
    public void setListaRubroCodigo(final List<Rubro> listaRubroCodigo) {
        this.listaRubroCodigo = listaRubroCodigo;
    }

    /**
     * @return the tipo
     */
    public List<SelectItem> getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(final List<SelectItem> tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the listaRubros
     */
    public List<Rubro> getListaRubros() {
        return listaRubros;
    }

    /**
     * @param listaRubros the listaRubros to set
     */
    public void setListaRubros(final List<Rubro> listaRubros) {
        this.listaRubros = listaRubros;
    }

    /**
     * @return the tipoBeneficiario
     */
    public List<SelectItem> getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    /**
     * @param tipoBeneficiario the tipoBeneficiario to set
     */
    public void setTipoBeneficiario(final List<SelectItem> tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    /**
     * @return the usoRubro
     */
    public List<SelectItem> getUsoRubro() {
        return usoRubro;
    }

    /**
     * @param usoRubro the usoRubro to set
     */
    public void setUsoRubro(final List<SelectItem> usoRubro) {
        this.usoRubro = usoRubro;
    }

    /**
     * @return the opcionDatoAdicional
     */
    public List<SelectItem> getOpcionDatoAdicional() {
        return opcionDatoAdicional;
    }

    /**
     * @param opcionDatoAdicional the opcionDatoAdicional to set
     */
    public void setOpcionDatoAdicional(final List<SelectItem> opcionDatoAdicional) {
        this.opcionDatoAdicional = opcionDatoAdicional;
    }

    /**
     * @return the listaDatosAdicionales
     */
    public List<DatoAdicional> getListaDatosAdicionales() {
        return listaDatosAdicionales;
    }

    /**
     * @param listaDatosAdicionales the listaDatosAdicionales to set
     */
    public void setListaDatosAdicionales(final List<DatoAdicional> listaDatosAdicionales) {
        this.listaDatosAdicionales = listaDatosAdicionales;
    }

    /**
     * @return the opcionFormula
     */
    public List<SelectItem> getOpcionFormula() {
        return opcionFormula;
    }

    /**
     * @param opcionFormula the opcionFormula to set
     */
    public void setOpcionFormula(final List<SelectItem> opcionFormula) {
        this.opcionFormula = opcionFormula;
    }

    /**
     * @return the listaFormulas
     */
    public List<Formula> getListaFormulas() {
        return listaFormulas;
    }

    /**
     * @param listaFormulas the listaFormulas to set
     */
    public void setListaFormulas(final List<Formula> listaFormulas) {
        this.listaFormulas = listaFormulas;
    }

    public List<TipoNomina> getListaTipoNomina() {
        return listaTipoNomina;
    }

    /**
     * @param listaTipoNomina the listaTipoNomina to set
     */
    public void setListaTipoNomina(final List<TipoNomina> listaTipoNomina) {
        this.listaTipoNomina = listaTipoNomina;
    }

    /**
     * @return the listaPeriodoNomina
     */
    public List<PeriodoNomina> getListaPeriodoNomina() {
        return listaPeriodoNomina;
    }

    /**
     * @param listaPeriodoNomina the listaPeriodoNomina to set
     */
    public void setListaPeriodoNomina(final List<PeriodoNomina> listaPeriodoNomina) {
        this.listaPeriodoNomina = listaPeriodoNomina;
    }

    /**
     * @return the listaCodigoContable
     */
    public List<CodigoContable> getListaCodigoContable() {
        return listaCodigoContable;
    }

    /**
     * @param listaCodigoContable the listaCodigoContable to set
     */
    public void setListaCodigoContable(final List<CodigoContable> listaCodigoContable) {
        this.listaCodigoContable = listaCodigoContable;
    }

    /**
     * @return the listaClasificadorGasto
     */
    public List<ClasificadorGasto> getListaClasificadorGasto() {
        return listaClasificadorGasto;
    }

    /**
     * @param listaClasificadorGasto the listaClasificadorGasto to set
     */
    public void setListaClasificadorGasto(final List<ClasificadorGasto> listaClasificadorGasto) {
        this.listaClasificadorGasto = listaClasificadorGasto;
    }

    /**
     * @return the opcionPrioridad
     */
    public List<SelectItem> getOpcionPrioridad() {
        return opcionPrioridad;
    }

    /**
     * @param opcionPrioridad the opcionPrioridad to set
     */
    public void setOpcionPrioridad(final List<SelectItem> opcionPrioridad) {
        this.opcionPrioridad = opcionPrioridad;
    }

    /**
     * @return the editaRubroTipoNomina
     */
    public boolean isEditaRubroTipoNomina() {
        return editaRubroTipoNomina;
    }

    /**
     * @param editaRubroTipoNomina the editaRubroTipoNomina to set
     */
    public void setEditaRubroTipoNomina(final boolean editaRubroTipoNomina) {
        this.editaRubroTipoNomina = editaRubroTipoNomina;
    }

    /**
     * @return the editaRubroPeriodoNomina
     */
    public boolean isEditaRubroPeriodoNomina() {
        return editaRubroPeriodoNomina;
    }

    /**
     * @param editaRubroPeriodoNomina the editaRubroPeriodoNomina to set
     */
    public void setEditaRubroPeriodoNomina(final boolean editaRubroPeriodoNomina) {
        this.editaRubroPeriodoNomina = editaRubroPeriodoNomina;
    }

    /**
     * @return the opcionCodigoContable
     */
    public List<SelectItem> getOpcionCodigoContable() {
        return opcionCodigoContable;
    }

    /**
     * @param opcionCodigoContable the opcionCodigoContable to set
     */
    public void setOpcionCodigoContable(final List<SelectItem> opcionCodigoContable) {
        this.opcionCodigoContable = opcionCodigoContable;
    }

    /**
     * @return the opcionClasificadorGasto
     */
    public List<SelectItem> getOpcionClasificadorGasto() {
        return opcionClasificadorGasto;
    }

    /**
     * @param opcionClasificadorGasto the opcionClasificadorGasto to set
     */
    public void setOpcionClasificadorGasto(final List<SelectItem> opcionClasificadorGasto) {
        this.opcionClasificadorGasto = opcionClasificadorGasto;
    }

    /**
     * @return the personaVO
     */
    public PersonaVO getPersonaVO() {
        return personaVO;
    }

    /**
     * @param personaVO the personaVO to set
     */
    public void setPersonaVO(final PersonaVO personaVO) {
        this.personaVO = personaVO;
    }

    /**
     * @return the tipoDocumento
     */
    public List<SelectItem> getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(final List<SelectItem> tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the existeBeneficiario
     */
    public Boolean getExisteBeneficiario() {
        return existeBeneficiario;
    }

    /**
     * @param existeBeneficiario the existeBeneficiario to set
     */
    public void setExisteBeneficiario(final Boolean existeBeneficiario) {
        this.existeBeneficiario = existeBeneficiario;
    }

    /**
     * @return the opcionPartida
     */
    public List<SelectItem> getOpcionPartida() {
        return opcionPartida;
    }

    /**
     * @param opcionPartida the opcionPartida to set
     */
    public void setOpcionPartida(final List<SelectItem> opcionPartida) {
        this.opcionPartida = opcionPartida;
    }
}
