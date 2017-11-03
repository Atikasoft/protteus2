/*
 *  EjercicioFiscalHelper.java
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
 *  01/11/2012
 *
 */

package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name="ejercicioFiscalHelper")
@SessionScoped
public class EjercicioFiscalHelper extends CatalogoHelper{
/**
 * Instancia de la entidad  
 */
    private EjercicioFiscal ejercicioFiscal;
    /**
     *Lista de Ejercicio Fiscal
     */
    private List<EjercicioFiscal> listaEjercicioFiscal;
    
    private EjercicioFiscal ejercicioFiscalEditDelete;
    
    private String fechaEntrega;
    
    private String nombreArchivoFirmaContador;
    
    private String nombreArchivoFirmaRetencion;
    
    private StreamedContent firmaRetencionImage;
    
    private StreamedContent firmaContadorImage;
    
    public EjercicioFiscalHelper() {
        super();
        ejercicioFiscal=new EjercicioFiscal();
        listaEjercicioFiscal=new ArrayList<EjercicioFiscal>();
        ejercicioFiscalEditDelete=new EjercicioFiscal();
    }

    /**
     * @return the ejercicioFiscal
     */
    public EjercicioFiscal getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    /**
     * @param ejercicioFiscal the ejercicioFiscal to set
     */
    public void setEjercicioFiscal(EjercicioFiscal ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    /**
     * @return the listaEjercicioFiscal
     */
    public List<EjercicioFiscal> getListaEjercicioFiscal() {
        return listaEjercicioFiscal;
    }

     /**
     * @return the ejercicioFiscalEditDelete
     */
    public EjercicioFiscal getEjercicioFiscalEditDelete() {
        return ejercicioFiscalEditDelete;
    }

    /**
     * @param ejercicioFiscalEditDelete the ejercicioFiscalEditDelete to set
     */
    public void setEjercicioFiscalEditDelete(EjercicioFiscal ejercicioFiscalEditDelete) {
        this.ejercicioFiscalEditDelete = ejercicioFiscalEditDelete;
    }

    /**
     * @param listaEjercicioFiscal the listaEjercicioFiscal to set
     */
    public void setListaEjercicioFiscal(List<EjercicioFiscal> listaEjercicioFiscal) {
        this.listaEjercicioFiscal = listaEjercicioFiscal;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getNombreArchivoFirmaContador() {
        return nombreArchivoFirmaContador;
    }

    public void setNombreArchivoFirmaContador(String nombreArchivoFirmaContador) {
        this.nombreArchivoFirmaContador = nombreArchivoFirmaContador;
    }

    public String getNombreArchivoFirmaRetencion() {
        return nombreArchivoFirmaRetencion;
    }

    public void setNombreArchivoFirmaRetencion(String nombreArchivoFirmaRetencion) {
        this.nombreArchivoFirmaRetencion = nombreArchivoFirmaRetencion;
    }

    public StreamedContent getFirmaRetencionImage() {
        return firmaRetencionImage;
    }

    public void setFirmaRetencionImage(StreamedContent firmaRetencionImage) {
        this.firmaRetencionImage = firmaRetencionImage;
    }

    public StreamedContent getFirmaContadorImage() {
        return firmaContadorImage;
    }

    public void setFirmaContadorImage(StreamedContent firmaContadorImage) {
        this.firmaContadorImage = firmaContadorImage;
    }

}
