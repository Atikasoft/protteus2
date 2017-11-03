/*
 *  CotizacionIessHelper.java
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
 *  09/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.CotizacionIess;
import ec.com.atikasoft.proteus.modelo.NivelOcupacional;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * CotizacionIessHelper
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "cotizacionIessHelper")
@SessionScoped
public final class CotizacionIessHelper extends CatalogoHelper {

    /**
     * CotizacionIess.
     */
    private CotizacionIess cotizacionIess;
    /**
     * CotizacionIess.
     */
    private CotizacionIess cotizacionIessEdirDelete;
    /**
     * lista de CotizacionIess.
     */
    private List<CotizacionIess> listaCotizacionIesse;
    /**
     * lista regímenes laborales
     */
    private List<SelectItem> listaRegimenLaborals;
    /**
     * lista listaNivelesOcupacionales.
     */
    private List<SelectItem> listaNivelesOcupacionales;
    /**
     * idRegimenLaboral
     */
    private Long idRegimenLaboral;

    /**
     * Constructor por defecto.
     */
    public CotizacionIessHelper() {
        super();
        iniciador();
    }

    /**
     * metodo para iniciar las variables.
     */
    public void iniciador() {
        cotizacionIess = new CotizacionIess();
        listaCotizacionIesse = new ArrayList<CotizacionIess>();
        cotizacionIessEdirDelete = new CotizacionIess();
        listaRegimenLaborals = new ArrayList<SelectItem>();
        listaNivelesOcupacionales = new ArrayList<SelectItem>();
        cotizacionIess.setNivelOcupacional(new NivelOcupacional());
    }

    /**
     * @return the cotizacionIess
     */
    public CotizacionIess getCotizacionIess() {
        return cotizacionIess;
    }

    /**
     * @param cotizacionIess the cotizacionIess to set
     */
    public void setCotizacionIess(final CotizacionIess cotizacionIess) {
        this.cotizacionIess = cotizacionIess;
    }

    /**
     * @return the listaCotizacionIesse
     */
    public List<CotizacionIess> getListaCotizacionIesse() {
        return listaCotizacionIesse;
    }

    /**
     * @param listaCotizacionIesse the listaCotizacionIesse to set
     */
    public void setListaCotizacionIesse(final List<CotizacionIess> listaCotizacionIesse) {
        this.listaCotizacionIesse = listaCotizacionIesse;
    }

    /**
     * @return the cotizacionIessEdirDelete
     */
    public CotizacionIess getCotizacionIessEdirDelete() {
        return cotizacionIessEdirDelete;
    }

    /**
     * @param cotizacionIessEdirDelete the cotizacionIessEdirDelete to set
     */
    public void setCotizacionIessEdirDelete(final CotizacionIess cotizacionIessEdirDelete) {
        this.cotizacionIessEdirDelete = cotizacionIessEdirDelete;
    }

    /**
     * @return the listaRegimenLaborals
     */
    public List<SelectItem> getListaRegimenLaborals() {
        return listaRegimenLaborals;
    }

    /**
     * @param listaRegimenLaborals the listaRegimenLaborals to set
     */
    public void setListaRegimenLaborals(final List<SelectItem> listaRegimenLaborals) {
        this.listaRegimenLaborals = listaRegimenLaborals;
    }

    /**
     * @return the listaNivelesOcupacionales
     */
    public List<SelectItem> getListaNivelesOcupacionales() {
        return listaNivelesOcupacionales;
    }

    /**
     * @param listaNivelesOcupacionales the listaNivelesOcupacionales to set
     */
    public void setListaNivelesOcupacionales(final List<SelectItem> listaNivelesOcupacionales) {
        this.listaNivelesOcupacionales = listaNivelesOcupacionales;
    }

    /**
     * @return the idRegimenLaboral
     */
    public Long getIdRegimenLaboral() {
        return idRegimenLaboral;
    }

    /**
     * @param idRegimenLaboral the idRegimenLaboral to set
     */
    public void setIdRegimenLaboral(final Long idRegimenLaboral) {
        this.idRegimenLaboral = idRegimenLaboral;
    }
}
