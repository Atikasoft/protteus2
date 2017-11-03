/*
 *  TramiteValidacionHelper.java
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
 *
 *  07/12/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.AtencionHelper;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.UploadedFile;

/**
 * Helper de Revision Validacion.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "tramiteLegalizacionHelper")
@SessionScoped
public class TramiteLegalizacionHelper extends AtencionHelper {

    /**
     * Movimiento del tramite.
     */
    private Movimiento movimiento = new Movimiento();

    /**
     * Archivo para cada movimiento.
     */
    private UploadedFile archivo;

    /**
     * Bandera para el control de accion de personal.
     */
    private Boolean esAccionPersonal = Boolean.FALSE;

    /**
     * Bandera para el control de accion de personal.
     */
    private Boolean esMemorando = Boolean.FALSE;

    /**
     * Lista de novedades de validacion movimientos.
     */
    private List<Object> listaNovedades = new ArrayList<Object>();

    /**
     * Constructor por defecto.
     */
    public TramiteLegalizacionHelper() {
        super();
    }

    /**
     * @return the esAccionPersonal
     */
    public Boolean getEsAccionPersonal() {
        return esAccionPersonal;
    }

    /**
     * @param esAccionPersonal the esAccionPersonal to set
     */
    public void setEsAccionPersonal(final Boolean esAccionPersonal) {
        this.esAccionPersonal = esAccionPersonal;
    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the archivo
     */
    public UploadedFile getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(final UploadedFile archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the listaNovedades
     */
    public List<Object> getListaNovedades() {
        return listaNovedades;
    }

    /**
     * @param listaNovedades the listaNovedades to set
     */
    public void setListaNovedades(final List<Object> listaNovedades) {
        this.listaNovedades = listaNovedades;
    }

    /**
     * @return the esMemorando
     */
    public Boolean getEsMemorando() {
        return esMemorando;
    }

    /**
     * @param esMemorando the esMemorando to set
     */
    public void setEsMemorando(Boolean esMemorando) {
        this.esMemorando = esMemorando;
    }
}
