/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.BeneficiarioEspecial;
import ec.com.atikasoft.proteus.modelo.BeneficiarioInstitucional;
import ec.com.atikasoft.proteus.modelo.CuentaBancaria;
import ec.com.atikasoft.proteus.modelo.Servidor;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author atikasoft
 */
@ManagedBean(name = "cuentaBancariaHelper")
@SessionScoped
public class CuentaBancariaHelper extends CatalogoHelper {

    /**
     * cuentaBancaria
     */
    private CuentaBancaria cuentaBancaria;
    /**
     * cuentaBancaria
     */
    private CuentaBancaria cuentaBancariaEditDelete;
    /**
     * listaTipoCuenta
     */
    private List<SelectItem> listaTipoCuenta;
    
     /**
     * lista Tipo Propietario de cuenta.
     */
    private List<SelectItem> listaTipoPersona;
    /**
     * listaCuentaBancarias
     */
    private List<CuentaBancaria> listaCuentaBancarias;
        /**
     * listaCuentaBancarias eliminadas.
     */
    private List<CuentaBancaria> listaCuentaBancariasEliminadas;
    /**
     * listaNumeroCuentaBancaria
     */
    private List<CuentaBancaria> listaNumeroCuentaBancaria;

    /**
     * Numero de identificacion.
     */
    private String numeroIdentificacion = new String();
    /**
     * tipo de identificacion.
     */
    private String tipoIdentificacion;
    /**
     * Lista de tipo documento.
     */
    private List<SelectItem> tipoDocumento;
    /**
     * lista bancos.
     */
    private List<SelectItem> listaBancos;
    
  private String tipoPersonaFiltro = new String();
  
  private String nombreEncontrado= new String();
  
    /**
     * Constructor por defecto.
     */
    public CuentaBancariaHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del CuentaBancariaHelper.
     */
    public final void iniciador() {
        setCuentaBancaria(new CuentaBancaria());
        cuentaBancariaEditDelete= new CuentaBancaria();
        cuentaBancariaEditDelete.setServidor(new Servidor());
        cuentaBancariaEditDelete.setBeneficiarioEspecial(new BeneficiarioEspecial());
        cuentaBancariaEditDelete.setBeneficiarioInstitucion(new BeneficiarioInstitucional());
        setListaCuentaBancarias(new ArrayList<CuentaBancaria>());
        setCuentaBancariaEditDelete(new CuentaBancaria());
        setListaTipoCuenta(new ArrayList<SelectItem>());
        setTipoDocumento(new ArrayList<SelectItem>());
        setListaNumeroCuentaBancaria(new ArrayList<CuentaBancaria>());
        setListaBancos(new ArrayList<SelectItem>());
        listaCuentaBancariasEliminadas = new ArrayList<CuentaBancaria>();
        listaTipoPersona = new ArrayList<SelectItem>();
    }

    /**
     * @return the cuentaBancaria
     */
    public CuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    /**
     * @param cuentaBancaria the cuentaBancaria to set
     */
    public void setCuentaBancaria(final CuentaBancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    /**
     * @return the listaCuentaBancarias
     */
    public List<CuentaBancaria> getListaCuentaBancarias() {
        return listaCuentaBancarias;
    }

    /**
     * @param listaCuentaBancarias the listaCuentaBancarias to set
     */
    public void setListaCuentaBancarias(final List<CuentaBancaria> listaCuentaBancarias) {
        this.listaCuentaBancarias = listaCuentaBancarias;
    }

    /**
     * @return the cuentaBancariaEditDelete
     */
    public CuentaBancaria getCuentaBancariaEditDelete() {
        return cuentaBancariaEditDelete;
    }

    /**
     * @param cuentaBancariaEditDelete the cuentaBancariaEditDelete to set
     */
    public void setCuentaBancariaEditDelete(final CuentaBancaria cuentaBancariaEditDelete) {
        this.cuentaBancariaEditDelete = cuentaBancariaEditDelete;
    }

    /**
     * @return the listaTipoCuenta
     */
    public List<SelectItem> getListaTipoCuenta() {
        return listaTipoCuenta;
    }

    /**
     * @param listaTipoCuenta the listaTipoCuenta to set
     */
    public void setListaTipoCuenta(final List<SelectItem> listaTipoCuenta) {
        this.listaTipoCuenta = listaTipoCuenta;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(final String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(final String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
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
     * @return the listaNumeroCuentaBancaria
     */
    public List<CuentaBancaria> getListaNumeroCuentaBancaria() {
        return listaNumeroCuentaBancaria;
    }

    /**
     * @param listaNumeroCuentaBancaria the listaNumeroCuentaBancaria to set
     */
    public void setListaNumeroCuentaBancaria(final List<CuentaBancaria> listaNumeroCuentaBancaria) {
        this.listaNumeroCuentaBancaria = listaNumeroCuentaBancaria;
    }

    /**
     * @return the listaBancos
     */
    public List<SelectItem> getListaBancos() {
        return listaBancos;
    }

    /**
     * @param listaBancos the listaBancos to set
     */
    public void setListaBancos(final List<SelectItem> listaBancos) {
        this.listaBancos = listaBancos;
    }

    /**
     * @return the listaCuentaBancariasEliminadas
     */
    public List<CuentaBancaria> getListaCuentaBancariasEliminadas() {
        return listaCuentaBancariasEliminadas;
    }

    /**
     * @param listaCuentaBancariasEliminadas the listaCuentaBancariasEliminadas to set
     */
    public void setListaCuentaBancariasEliminadas(List<CuentaBancaria> listaCuentaBancariasEliminadas) {
        this.listaCuentaBancariasEliminadas = listaCuentaBancariasEliminadas;
    }

    /**
     * @return the listaTipoPersona
     */
    public List<SelectItem> getListaTipoPersona() {
        return listaTipoPersona;
    }

    /**
     * @param listaTipoPersona the listaTipoPersona to set
     */
    public void setListaTipoPersona(List<SelectItem> listaTipoPersona) {
        this.listaTipoPersona = listaTipoPersona;
    }

    /**
     * @return the tipoPersonaFiltro
     */
    public String getTipoPersonaFiltro() {
        return tipoPersonaFiltro;
    }

    /**
     * @param tipoPersonaFiltro the tipoPersonaFiltro to set
     */
    public void setTipoPersonaFiltro(String tipoPersonaFiltro) {
        this.tipoPersonaFiltro = tipoPersonaFiltro;
    }

    /**
     * @return the nombreEncontrado
     */
    public String getNombreEncontrado() {
        return nombreEncontrado;
    }

    /**
     * @param nombreEncontrado the nombreEncontrado to set
     */
    public void setNombreEncontrado(String nombreEncontrado) {
        this.nombreEncontrado = nombreEncontrado;
    }
}
