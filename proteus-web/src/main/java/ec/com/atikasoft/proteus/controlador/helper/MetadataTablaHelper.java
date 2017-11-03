/*
 *  MetadataTablaHelper.java
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
import ec.com.atikasoft.proteus.modelo.MetadataTabla;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean (name = "metadataTablaHelper")
@SessionScoped
public class MetadataTablaHelper extends CatalogoHelper {
    /**
     * Variable para nueva metadataTabla.
     */
    private MetadataTabla metadataTabla;
    
    /**
     * Variable para listar las metadataTablas.
     */
    private List<MetadataTabla> listaMetadataTabla;
   
    /**
     * Constructor.
     */
    public MetadataTablaHelper() {
        super();
        iniciador();
    }
    /**
     * Método para iniciar las variables de la MetadataTablaHelper.
     */
    public final void iniciador() {
        setMetadataTabla(new MetadataTabla());        
        setListaMetadataTabla(new ArrayList<MetadataTabla>());
     }

    /**
     * @return the metadataTabla
     */
    public MetadataTabla getMetadataTabla() {
        return metadataTabla;
    }

    /**
     * @param metadataTabla the metadataTabla to set
     */
    public void setMetadataTabla(final MetadataTabla metadataTabla) {
        this.metadataTabla = metadataTabla;
    }
  
    /**
     * @return the listaMetadataTabla
     */
    public List<MetadataTabla> getListaMetadataTabla() {
        return listaMetadataTabla;
    }

    /**
     * @param listaMetadataTabla the listaMetadataTabla to set
     */
    public void setListaMetadataTabla(final List<MetadataTabla> listaMetadataTabla) {
        this.listaMetadataTabla = listaMetadataTabla;
    }

}
