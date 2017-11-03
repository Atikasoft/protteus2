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
import ec.com.atikasoft.proteus.modelo.nomina.CotizacionIessEspecial;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * 
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
@ManagedBean(name = "cotizacionIessEspecialHelper")
@SessionScoped
public final class CotizacionIessEspecialHelper extends CatalogoHelper {

    /**
     * CotizacionIessEspecial.
     */
    private CotizacionIessEspecial cotizacionIessEspecial;
    /**
     * CotizacionIessEspecial.
     */
    private CotizacionIessEspecial cotizacionIessEspecialEdirDelete;
    /**
     * lista de CotizacionIessEspecial.
     */
    private List<CotizacionIessEspecial> listaCotizacionIessEspecial;
    

    /**
     * Constructor por defecto.
     */
    public CotizacionIessEspecialHelper() {
        super();
        iniciador();
    }

    /**
     * metodo para iniciar las variables.
     */
    public void iniciador() {
        cotizacionIessEspecial = new CotizacionIessEspecial();
        listaCotizacionIessEspecial = new ArrayList<>();
        cotizacionIessEspecialEdirDelete = new CotizacionIessEspecial();
    }

    public CotizacionIessEspecial getCotizacionIessEspecial() {
        return cotizacionIessEspecial;
    }

    public void setCotizacionIessEspecial(CotizacionIessEspecial cotizacionIessEspecial) {
        this.cotizacionIessEspecial = cotizacionIessEspecial;
    }

    public CotizacionIessEspecial getCotizacionIessEspecialEdirDelete() {
        return cotizacionIessEspecialEdirDelete;
    }

    public void setCotizacionIessEspecialEdirDelete(CotizacionIessEspecial cotizacionIessEspecialEdirDelete) {
        this.cotizacionIessEspecialEdirDelete = cotizacionIessEspecialEdirDelete;
    }

    public List<CotizacionIessEspecial> getListaCotizacionIessEspecial() {
        return listaCotizacionIessEspecial;
    }

    public void setListaCotizacionIessEspecial(List<CotizacionIessEspecial> listaCotizacionIessEspecial) {
        this.listaCotizacionIessEspecial = listaCotizacionIessEspecial;
    }

    
}
