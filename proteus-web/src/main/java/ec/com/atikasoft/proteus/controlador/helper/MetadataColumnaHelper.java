/*
 *  MetadataColumnaHelper.java
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
 *  03/10/2013
 *
 */

package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.MetadataColumna;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean (name = "metadataColumnaHelper")
@SessionScoped
public class MetadataColumnaHelper extends CatalogoHelper {
    /**
     * Variable para nueva metadataColumna.
     */
    private MetadataColumna metadataColumna;
    
    /**
     * Variable para listar las metadataColumnas.
     */
    private List<MetadataColumna> listaMetadataColumna;
   
    /**
     * Constructor.
     */
    public MetadataColumnaHelper() {
        super();
        iniciador();
    }
    /**
     * Método para iniciar las variables de la MetadataColumnaHelper.
     */
    public final void iniciador() {
        setMetadataColumna(new MetadataColumna());        
        setListaMetadataColumna(new ArrayList<MetadataColumna>());
     }

    /**
     * @return the metadataColumna
     */
    public MetadataColumna getMetadataColumna() {
        return metadataColumna;
    }

    /**
     * @param metadataColumna the metadataColumna to set
     */
    public void setMetadataColumna(final MetadataColumna metadataColumna) {
        this.metadataColumna = metadataColumna;
    }
  
    /**
     * @return the listaMetadataColumna
     */
    public List<MetadataColumna> getListaMetadataColumna() {
        return listaMetadataColumna;
    }

    /**
     * @param listaMetadataColumna the listaMetadataColumna to set
     */
    public void setListaMetadataColumna(final List<MetadataColumna> listaMetadataColumna) {
        this.listaMetadataColumna = listaMetadataColumna;
    }

}
