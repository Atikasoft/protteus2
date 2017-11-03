/*
 *  ObjetoNominaVO.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Apr 4, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.nomina.NominaProblema;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javolution.util.FastMap;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class ObjetoNominaVO {

    /**
     * Datos de la ejecucion.
     */
    private EjecucionNominaVO ejecucionNominaVO;

    /**
     * Nomina a ejecutarse.
     */
    private Nomina nomina;

    /**
     * Lista de rubros.
     */
    private List<Rubro> rubros;

    /**
     * Lista de servidores.
     */
    private List<DistributivoDetalle> servidores;

    /**
     * Lista de anticipos.
     */
//    private List<Anticipo> anticipos;
    /**
     * Lista de pasivos.
     */
//    private List<Pasivo> pasivos;
    /**
     * Lista de problemas.
     */
    private List<NominaProblema> problemas;

    /**
     * Beneficiarios institucionales.
     */
    private List<BeneficiarioInstitucionalNominaVO> beneficiariosInstitucionales;

    /**
     * Lista de auxiliar para beneficiarios.
     */
//    private Map<String, ProcesoNominaAuxiliar> beneficiarios;
    /**
     * Contexto del sistema.
     */
    private ServletContext servletContext;

    /**
     * Rubro seleccionado.
     */
    private Rubro rubro;

    /**
     * Persona de nomina.
     */
    private PersonaNominaVO personaNominaVO;

    /**
     * Beneficiarios de nomina.
     */
    private List<PersonaNominaVO> beneficiariosNominaVO;

    /**
     * Institucion x ejercicio fiscal.
     */
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    /**
     * Distributivo.
     */
    private DistributivoDetalle distributivoDetalle;

    /**
     *
     */
    private Liquidacion liquidacion;

    /**
     *
     */
    private Anticipo anticipo;

    /**
     * Usuario conectado.
     */
    private UsuarioVO usuario;

    /**
     * Enlaces presupuestarios de rubros.
     */
//    private List<RubroEnlacePresupuestario> rubrosEnlaces;
    /**
     * Detalle de las novedades.
     */
    private List<NovedadDetalle> listaNovedadDetalle;

    /**
     *
     */
    private List<AnticipoPlanPago> listaAnticiposPlanPagos;

    /**
     *
     */
    private Map<Long, BigDecimal> ingresosProyeccionIR;

    /**
     *
     */
    private ImpuestoRenta impuestoRentaExoneracion;

    /**
     *
     */
    private List<ImpuestoRenta> tablaImpuestoRenta;

    /**
     * Constructor sin argumentos.
     */
    public ObjetoNominaVO() {
        super();
    }

    /**
     * @return the ejecucionNominaVO
     */
    public EjecucionNominaVO getEjecucionNominaVO() {
        return ejecucionNominaVO;
    }

    /**
     * @return the nomina
     */
    public Nomina getNomina() {
        return nomina;
    }

    /**
     * @return the rubros
     */
    public List<Rubro> getRubros() {
        return rubros;
    }

    /**
     * @return the servidores
     */
    public List<DistributivoDetalle> getServidores() {
        return servidores;
    }

    /**
     * @return the problemas
     */
    public List<NominaProblema> getProblemas() {
        return problemas;
    }

    /**
     * @return the beneficiariosInstitucionales
     */
    public List<BeneficiarioInstitucionalNominaVO> getBeneficiariosInstitucionales() {
        return beneficiariosInstitucionales;
    }

    /**
     * @return the servletContext
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * @return the rubro
     */
    public Rubro getRubro() {
        return rubro;
    }

    /**
     * @return the personaNominaVO
     */
    public PersonaNominaVO getPersonaNominaVO() {
        return personaNominaVO;
    }

    /**
     * @return the beneficiariosNominaVO
     */
    public List<PersonaNominaVO> getBeneficiariosNominaVO() {
        return beneficiariosNominaVO;
    }

    /**
     * @return the institucionEjercicioFiscal
     */
    public InstitucionEjercicioFiscal getInstitucionEjercicioFiscal() {
        return institucionEjercicioFiscal;
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @return the usuario
     */
    public UsuarioVO getUsuario() {
        return usuario;
    }

    /**
     * @return the listaNovedadDetalle
     */
    public List<NovedadDetalle> getListaNovedadDetalle() {
        return listaNovedadDetalle;
    }

    /**
     * @param ejecucionNominaVO the ejecucionNominaVO to set
     */
    public void setEjecucionNominaVO(final EjecucionNominaVO ejecucionNominaVO) {
        this.ejecucionNominaVO = ejecucionNominaVO;
    }

    /**
     * @param nomina the nomina to set
     */
    public void setNomina(final Nomina nomina) {
        this.nomina = nomina;
    }

    /**
     * @param rubros the rubros to set
     */
    public void setRubros(final List<Rubro> rubros) {
        this.rubros = rubros;
    }

    /**
     * @param servidores the servidores to set
     */
    public void setServidores(final List<DistributivoDetalle> servidores) {
        this.servidores = servidores;
    }

    /**
     * @param problemas the problemas to set
     */
    public void setProblemas(final List<NominaProblema> problemas) {
        this.problemas = problemas;
    }

    /**
     * @param beneficiariosInstitucionales the beneficiariosInstitucionales to
     * set
     */
    public void setBeneficiariosInstitucionales(
            final List<BeneficiarioInstitucionalNominaVO> beneficiariosInstitucionales) {
        this.beneficiariosInstitucionales = beneficiariosInstitucionales;
    }

    /**
     * @param servletContext the servletContext to set
     */
    public void setServletContext(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * @param rubro the rubro to set
     */
    public void setRubro(final Rubro rubro) {
        this.rubro = rubro;
    }

    /**
     * @param personaNominaVO the personaNominaVO to set
     */
    public void setPersonaNominaVO(final PersonaNominaVO personaNominaVO) {
        this.personaNominaVO = personaNominaVO;
    }

    /**
     * @param beneficiariosNominaVO the beneficiariosNominaVO to set
     */
    public void setBeneficiariosNominaVO(final List<PersonaNominaVO> beneficiariosNominaVO) {
        this.beneficiariosNominaVO = beneficiariosNominaVO;
    }

    /**
     * @param institucionEjercicioFiscal the institucionEjercicioFiscal to set
     */
    public void setInstitucionEjercicioFiscal(final InstitucionEjercicioFiscal institucionEjercicioFiscal) {
        this.institucionEjercicioFiscal = institucionEjercicioFiscal;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(final DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(final UsuarioVO usuario) {
        this.usuario = usuario;
    }

    /**
     * @param listaNovedadDetalle the listaNovedadDetalle to set
     */
    public void setListaNovedadDetalle(final List<NovedadDetalle> listaNovedadDetalle) {
        this.listaNovedadDetalle = listaNovedadDetalle;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, "servletContext", "servidores", "listaNovedadDetalle");
    }

    /**
     * @return the liquidacion
     */
    public Liquidacion getLiquidacion() {
        return liquidacion;
    }

    /**
     * @param liquidacion the liquidacion to set
     */
    public void setLiquidacion(Liquidacion liquidacion) {
        this.liquidacion = liquidacion;
    }

    /**
     * @return the listaAnticiposPlanPagos
     */
    public List<AnticipoPlanPago> getListaAnticiposPlanPagos() {
        if (listaAnticiposPlanPagos == null) {
            listaAnticiposPlanPagos = new ArrayList<AnticipoPlanPago>();
        }
        return listaAnticiposPlanPagos;
    }

    /**
     * @param listaAnticiposPlanPagos the listaAnticiposPlanPagos to set
     */
    public void setListaAnticiposPlanPagos(List<AnticipoPlanPago> listaAnticiposPlanPagos) {
        this.listaAnticiposPlanPagos = listaAnticiposPlanPagos;
    }

    /**
     * @return the anticipo
     */
    public Anticipo getAnticipo() {
        return anticipo;
    }

    /**
     * @param anticipo the anticipo to set
     */
    public void setAnticipo(Anticipo anticipo) {
        this.anticipo = anticipo;
    }

    /**
     * @return the ingresosProyeccionIR
     */
    public Map<Long, BigDecimal> getIngresosProyeccionIR() {
        if (ingresosProyeccionIR == null) {
            ingresosProyeccionIR = new FastMap<Long, BigDecimal>();
        }
        return ingresosProyeccionIR;
    }

    /**
     * @param ingresosProyeccionIR the ingresosProyeccionIR to set
     */
    public void setIngresosProyeccionIR(Map<Long, BigDecimal> ingresosProyeccionIR) {
        this.ingresosProyeccionIR = ingresosProyeccionIR;
    }

    /**
     * @return the impuestoRentaExoneracion
     */
    public ImpuestoRenta getImpuestoRentaExoneracion() {
        return impuestoRentaExoneracion;
    }

    /**
     * @param impuestoRentaExoneracion the impuestoRentaExoneracion to set
     */
    public void setImpuestoRentaExoneracion(ImpuestoRenta impuestoRentaExoneracion) {
        this.impuestoRentaExoneracion = impuestoRentaExoneracion;
    }

    /**
     * @return the tablaImpuestoRenta
     */
    public List<ImpuestoRenta> getTablaImpuestoRenta() {
        return tablaImpuestoRenta;
    }

    /**
     * @param tablaImpuestoRenta the tablaImpuestoRenta to set
     */
    public void setTablaImpuestoRenta(List<ImpuestoRenta> tablaImpuestoRenta) {
        this.tablaImpuestoRenta = tablaImpuestoRenta;
    }
}
