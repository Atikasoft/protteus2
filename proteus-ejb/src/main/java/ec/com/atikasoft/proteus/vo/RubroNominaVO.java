/*
 *  RubroNominaVO.java
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
 *  Apr 3, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Anticipo;
import ec.com.atikasoft.proteus.modelo.AnticipoPlanPago;
import ec.com.atikasoft.proteus.modelo.NovedadDetalle;
import ec.com.atikasoft.proteus.modelo.Rubro;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class RubroNominaVO implements Serializable {

    /**
     * Serial de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Valor del rubro.
     */
    private BigDecimal valorRubro;

    /**
     * Rubro.
     */
    private Rubro rubro;

    /**
     *
     */
    private Boolean beneficiarioEspecial;

    /**
     * Detalle de la novedad.
     */
    private List<NovedadDetalle> listaNovedadDetalle;

    /**
     *
     */
    private NovedadDetalle novedadDetalle;

    /**
     * cuota del anticipo.
     */
    private List<AnticipoPlanPago> listaAnticiposPlanPagos;

    /**
     * creaditos de anticipos.
     */
    private List<Anticipo> listaAnticipos;

    /**
     * Valores de la formula.
     */
    private Map<String, Object> valores;

    /**
     * Constructor sin argumentos.
     */
    public RubroNominaVO() {
        super();
    }

    /**
     * @return the valorRubro
     */
    public BigDecimal getValorRubro() {
        return valorRubro;
    }

    /**
     * @param valorRubro the valorRubro to set
     */
    public void setValorRubro(final BigDecimal valorRubro) {
        this.valorRubro = valorRubro;
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
     * @return the valores
     */
    public Map<String, Object> getValores() {
        if (valores == null) {
            valores = new HashMap<String, Object>();
        }
        return valores;
    }

    /**
     * @param valores the valores to set
     */
    public void setValores(final Map<String, Object> valores) {
        this.valores = valores;
    }

    /**
     * @return the listaNovedadDetalle
     */
    public List<NovedadDetalle> getListaNovedadDetalle() {
        return listaNovedadDetalle;
    }

    /**
     * @param listaNovedadDetalle the listaNovedadDetalle to set
     */
    public void setListaNovedadDetalle(final List<NovedadDetalle> listaNovedadDetalle) {
        this.listaNovedadDetalle = listaNovedadDetalle;
    }

    /**
     * @return the beneficiarioEspecial
     */
    public Boolean getBeneficiarioEspecial() {
        return beneficiarioEspecial;
    }

    /**
     * @param beneficiarioEspecial the beneficiarioEspecial to set
     */
    public void setBeneficiarioEspecial(final Boolean beneficiarioEspecial) {
        this.beneficiarioEspecial = beneficiarioEspecial;
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
     * @return the listaAnticipos
     */
    public List<Anticipo> getListaAnticipos() {
        if (listaAnticipos == null) {
            listaAnticipos = new ArrayList<Anticipo>();
        }
        return listaAnticipos;
    }

    /**
     * @param listaAnticipos the listaAnticipos to set
     */
    public void setListaAnticipos(List<Anticipo> listaAnticipos) {
        this.listaAnticipos = listaAnticipos;
    }

    /**
     * @return the novedadDetalle
     */
    public NovedadDetalle getNovedadDetalle() {
        return novedadDetalle;
    }

    /**
     * @param novedadDetalle the novedadDetalle to set
     */
    public void setNovedadDetalle(NovedadDetalle novedadDetalle) {
        this.novedadDetalle = novedadDetalle;
    }
}
