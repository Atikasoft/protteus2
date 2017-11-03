/*
 *  PersonaNominaVO.java
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

import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.NominaDetalle;
import java.io.Serializable;
import java.util.List;

/**
 * Contiene los datos de un servidor en el calculo de la nomina.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class PersonaNominaVO implements Serializable {

    /**
     * Serial.
     */
    private static long serialVersionUID = 1L;

    /**
     * Tipo de documento.
     */
    private String tipoDocumento;

    /**
     * Numero de documento.
     */
    private String numeroDocumento;

    /**
     * Tipo de documento.
     */
    private String tipoDocumentoOrigen;

    /**
     * Numero de documento.
     */
    private String numeroDocumentoOrigen;

    /**
     * Nombres.
     */
    private String nombres;

    /**
     * Puesto.
     */
    private DistributivoDetalle distributivoDetalle;

    /**
     *
     */
    private NominaDetalle nominaDetalle;

    /**
     * Lista de rubros de ingresos.
     */
    private List<RubroNominaVO> listaRubrosIngresos;

    /**
     * Lista de rubros de descuentos.
     */
    private List<RubroNominaVO> listaRubrosDescuentos;

    /**
     * Lista de rubros de aportes.
     */
    private List<RubroNominaVO> listaRubrosAportes;

    /**
     * Constructor sin argumentos.
     */
    public PersonaNominaVO() {
        super();

    }

    /**
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @return the numeroDocumento
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(final String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @param numeroDocumento the numeroDocumento to set
     */
    public void setNumeroDocumento(final String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(final String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the listaRubrosIngresos
     */
    public List<RubroNominaVO> getListaRubrosIngresos() {
        return listaRubrosIngresos;
    }

    /**
     * @return the listaRubrosDescuentos
     */
    public List<RubroNominaVO> getListaRubrosDescuentos() {
        return listaRubrosDescuentos;
    }

    /**
     * @return the listaRubrosAportes
     */
    public List<RubroNominaVO> getListaRubrosAportes() {
        return listaRubrosAportes;
    }

    /**
     * @param listaRubrosIngresos the listaRubrosIngresos to set
     */
    public void setListaRubrosIngresos(final List<RubroNominaVO> listaRubrosIngresos) {
        this.listaRubrosIngresos = listaRubrosIngresos;
    }

    /**
     * @param listaRubrosDescuentos the listaRubrosDescuentos to set
     */
    public void setListaRubrosDescuentos(final List<RubroNominaVO> listaRubrosDescuentos) {
        this.listaRubrosDescuentos = listaRubrosDescuentos;
    }

    /**
     * @param listaRubrosAportes the listaRubrosAportes to set
     */
    public void setListaRubrosAportes(final List<RubroNominaVO> listaRubrosAportes) {
        this.listaRubrosAportes = listaRubrosAportes;
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(final DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }

    /**
     * @return the tipoDocumentoOrigen
     */
    public String getTipoDocumentoOrigen() {
        return tipoDocumentoOrigen;
    }

    /**
     * @return the numeroDocumentoOrigen
     */
    public String getNumeroDocumentoOrigen() {
        return numeroDocumentoOrigen;
    }

    /**
     * @param tipoDocumentoOrigen the tipoDocumentoOrigen to set
     */
    public void setTipoDocumentoOrigen(final String tipoDocumentoOrigen) {
        this.tipoDocumentoOrigen = tipoDocumentoOrigen;
    }

    /**
     * @param numeroDocumentoOrigen the numeroDocumentoOrigen to set
     */
    public void setNumeroDocumentoOrigen(final String numeroDocumentoOrigen) {
        this.numeroDocumentoOrigen = numeroDocumentoOrigen;
    }

    /**
     * @return the nominaDetalle
     */
    public NominaDetalle getNominaDetalle() {
        return nominaDetalle;
    }

    /**
     * @param nominaDetalle the nominaDetalle to set
     */
    public void setNominaDetalle(final NominaDetalle nominaDetalle) {
        this.nominaDetalle = nominaDetalle;
    }
}
