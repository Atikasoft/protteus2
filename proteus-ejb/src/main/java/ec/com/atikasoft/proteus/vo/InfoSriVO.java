/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"empleado", "suelsual","sobsuelcomremu","partutil","intgrabgen","imprentempl",
                      "decimoTercero","decimoCuarto", "fondoReserva", "salarioDigno", "otrosIngresosGravables",
                      "inggravconesteempl","sissalnet","apoperiess","aporperiessconotrosempls","deducibleVivienda",
                      "deducibleSalud","deducibleEducacion","deducibleAlimentacion","deducibleVestimenta",
                      "exoneracionDiscapacidad","exoneracionTerceraEdad","baseImponible","impuestoRentaCausado",
                      "valorRetenidoOtrosEmpleadores","valorImpuestoAsumidoEmpleados","valorRetenido"})
@XmlRootElement
public class InfoSriVO implements Serializable{
    @XmlTransient 
    private String ruc;
    @XmlTransient 
    private String anio;
    private EmpleadoRdepVO empleado;
    private String suelsual;
    private String sobsuelcomremu;
    private String partutil;
    private String intgrabgen;
    private String imprentempl;
    private String decimoTercero;
    private String decimoCuarto;
    private String fondoReserva;
    private String salarioDigno;
    private String otrosIngresosGravables;
    private String inggravconesteempl;
    private String sissalnet;
    private String apoperiess;
    private String aporperiessconotrosempls;
    private String deducibleVivienda;
    private String deducibleSalud;
    private String deducibleAlimentacion;
    private String deducibleEducacion;
    private String deducibleVestimenta;
    private String exoneracionDiscapacidad;
    private String exoneracionTerceraEdad;
    private String baseImponible; 
    private String impuestoRentaCausado;
    private String valorRetenidoOtrosEmpleadores;
    private String valorImpuestoAsumidoEmpleados;
    private String valorRetenido;
    @XmlTransient 
    private String tipoGenera;
    @XmlTransient 
    private String nombreEmpleador;
    @XmlTransient 
    private String periodo;
    @XmlTransient 
    private String regimenLaboral;
    @XmlTransient 
    private String unidadPresupuestaria;

    @XmlTransient 
    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    @XmlTransient 
    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    @XmlElement(name = "empleado")
    public EmpleadoRdepVO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoRdepVO empleado) {
        this.empleado = empleado;
    }

    @XmlElement(name = "suelSal")
    public String getSuelsual() {
        return suelsual;
    }

    public void setSuelsual(String suelsual) {
        this.suelsual = suelsual;
    }

    @XmlElement(name = "sobSuelComRemu")
    public String getSobsuelcomremu() {
        return sobsuelcomremu;
    }

    public void setSobsuelcomremu(String sobsuelcomremu) {
        this.sobsuelcomremu = sobsuelcomremu;
    }

    @XmlElement(name = "partUtil")
    public String getPartutil() {
        return partutil;
    }

    public void setPartutil(String partutil) {
        this.partutil = partutil;
    }

    @XmlElement(name = "intGrabGen")
    public String getIntgrabgen() {
        return intgrabgen;
    }

    public void setIntgrabgen(String intgrabgen) {
        this.intgrabgen = intgrabgen;
    }

    @XmlElement(name = "impRentEmpl")
    public String getImprentempl() {
        return imprentempl;
    }

    public void setImprentempl(String imprentempl) {
        this.imprentempl = imprentempl;
    }

    @XmlElement(name = "decimTer")
    public String getDecimoTercero() {
        return decimoTercero;
    }

    public void setDecimoTercero(String decimoTercero) {
        this.decimoTercero = decimoTercero;
    }

    @XmlElement(name = "decimCuar")
    public String getDecimoCuarto() {
        return decimoCuarto;
    }

    public void setDecimoCuarto(String decimoCuarto) {
        this.decimoCuarto = decimoCuarto;
    }

    @XmlElement(name = "fondoReserva")
    public String getFondoReserva() {
        return fondoReserva;
    }

    public void setFondoReserva(String fondoReserva) {
        this.fondoReserva = fondoReserva;
    }

    @XmlElement(name = "salarioDigno")
    public String getSalarioDigno() {
        return salarioDigno;
    }

    public void setSalarioDigno(String salarioDigno) {
        this.salarioDigno = salarioDigno;
    }

    @XmlElement(name = "otrosIngRenGrav")
    public String getOtrosIngresosGravables() {
        return otrosIngresosGravables;
    }

    public void setOtrosIngresosGravables(String otrosIngresosGravables) {
        this.otrosIngresosGravables = otrosIngresosGravables;
    }

    @XmlElement(name = "ingGravConEsteEmpl")
    public String getInggravconesteempl() {
        return inggravconesteempl;
    }

    public void setInggravconesteempl(String inggravconesteempl) {
        this.inggravconesteempl = inggravconesteempl;
    }

    @XmlElement(name = "sisSalNet")
    public String getSissalnet() {
        return sissalnet;
    }

    public void setSissalnet(String sissalnet) {
        this.sissalnet = sissalnet;
    }

    @XmlElement(name = "apoPerIess")
    public String getApoperiess() {
        return apoperiess;
    }

    public void setApoperiess(String apoperiess) {
        this.apoperiess = apoperiess;
    }

    @XmlElement(name = "aporPerIessConOtrosEmpls")
    public String getAporperiessconotrosempls() {
        return aporperiessconotrosempls;
    }

    public void setAporperiessconotrosempls(String aporperiessconotrosempls) {
        this.aporperiessconotrosempls = aporperiessconotrosempls;
    }

    @XmlElement(name = "deducVivienda")
    public String getDeducibleVivienda() {
        return deducibleVivienda;
    }

    public void setDeducibleVivienda(String deducibleVivienda) {
        this.deducibleVivienda = deducibleVivienda;
    }

    @XmlElement(name = "deducSalud")
    public String getDeducibleSalud() {
        return deducibleSalud;
    }

    public void setDeducibleSalud(String deducibleSalud) {
        this.deducibleSalud = deducibleSalud;
    }

    @XmlElement(name = "deducAliement")
    public String getDeducibleAlimentacion() {
        return deducibleAlimentacion;
    }

    public void setDeducibleAlimentacion(String deducibleAlimentacion) {
        this.deducibleAlimentacion = deducibleAlimentacion;
    }

    @XmlElement(name = "deducEduca")
    public String getDeducibleEducacion() {
        return deducibleEducacion;
    }

    public void setDeducibleEducacion(String deducibleEducacion) {
        this.deducibleEducacion = deducibleEducacion;
    }

    @XmlElement(name = "deducVestim")
    public String getDeducibleVestimenta() {
        return deducibleVestimenta;
    }

    public void setDeducibleVestimenta(String deducibleVestimenta) {
        this.deducibleVestimenta = deducibleVestimenta;
    }

    @XmlElement(name = "exoDiscap")
    public String getExoneracionDiscapacidad() {
        return exoneracionDiscapacidad;
    }

    public void setExoneracionDiscapacidad(String exoneracionDiscapacidad) {
        this.exoneracionDiscapacidad = exoneracionDiscapacidad;
    }

    @XmlElement(name = "exoTerEd")
    public String getExoneracionTerceraEdad() {
        return exoneracionTerceraEdad;
    }

    public void setExoneracionTerceraEdad(String exoneracionTerceraEdad) {
        this.exoneracionTerceraEdad = exoneracionTerceraEdad;
    }

    @XmlElement(name = "basImp")
    public String getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(String baseImponible) {
        this.baseImponible = baseImponible;
    }

    @XmlElement(name = "impRentCaus")
    public String getImpuestoRentaCausado() {
        return impuestoRentaCausado;
    }

    public void setImpuestoRentaCausado(String impuestoRentaCausado) {
        this.impuestoRentaCausado = impuestoRentaCausado;
    }

    @XmlElement(name = "valRetAsuOtrosEmpls")
    public String getValorRetenidoOtrosEmpleadores() {
        return valorRetenidoOtrosEmpleadores;
    }

    public void setValorRetenidoOtrosEmpleadores(String valorRetenidoOtrosEmpleadores) {
        this.valorRetenidoOtrosEmpleadores = valorRetenidoOtrosEmpleadores;
    }

    @XmlElement(name = "valImpAsuEsteEmpl")
    public String getValorImpuestoAsumidoEmpleados() {
        return valorImpuestoAsumidoEmpleados;
    }

    public void setValorImpuestoAsumidoEmpleados(String valorImpuestoAsumidoEmpleados) {
        this.valorImpuestoAsumidoEmpleados = valorImpuestoAsumidoEmpleados;
    }

    @XmlElement(name = "valRet")
    public String getValorRetenido() {
        return valorRetenido;
    }

    public void setValorRetenido(String valorRetenido) {
        this.valorRetenido = valorRetenido;
    }

    @XmlTransient
    public String getTipoGenera() {
        return tipoGenera;
    }

    public void setTipoGenera(String tipoGenera) {
        this.tipoGenera = tipoGenera;
    }

    @XmlTransient
    public String getNombreEmpleador() {
        return nombreEmpleador;
    }

    public void setNombreEmpleador(String nombreEmpleador) {
        this.nombreEmpleador = nombreEmpleador;
    }

    @XmlTransient
    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @XmlTransient
    public String getRegimenLaboral() {
        return regimenLaboral;
    }

    public void setRegimenLaboral(String regimenLaboral) {
        this.regimenLaboral = regimenLaboral;
    }

    @XmlTransient
    public String getUnidadPresupuestaria() {
        return unidadPresupuestaria;
    }

    public void setUnidadPresupuestaria(String unidadPresupuestaria) {
        this.unidadPresupuestaria = unidadPresupuestaria;
    }  
    
}
