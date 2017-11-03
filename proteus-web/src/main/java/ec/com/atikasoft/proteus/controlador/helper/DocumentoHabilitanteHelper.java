/*
 *  DocumentoHabilitanteHelper.java
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
import ec.com.atikasoft.proteus.modelo.DocumentoHabilitante;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@ManagedBean(name = "documentoHabilitanteHelper")
@SessionScoped
public class DocumentoHabilitanteHelper extends CatalogoHelper {

    /**
     * Variable para nuevo documento habilitante.
     */
    private DocumentoHabilitante documentoHabilitante;
    /**
     * Variable para modificar/eliminar un documento habilitante.
     */
    private DocumentoHabilitante documentoHabilitanteEditDelete;
    /**
     * Variable para listar los documentos habilitantes.
     */
    private List<DocumentoHabilitante> listaDocumentoHabilitante;
    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<DocumentoHabilitante> listaDocumentoHabilitanteNemonico;

    /**
     *
     */
    private List<SelectItem> listaTipoDocumentosHabilitantes;

    /**
     * Constructor.
     */
    public DocumentoHabilitanteHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del DocumentoHabilitanteHelper.
     */
    public final void iniciador() {
        documentoHabilitante = new DocumentoHabilitante();
        documentoHabilitanteEditDelete = new DocumentoHabilitante();
        listaDocumentoHabilitante = new ArrayList<DocumentoHabilitante>();
        setListaDocumentoHabilitanteNemonico(new ArrayList<DocumentoHabilitante>());
        setListaTipoDocumentosHabilitantes(new ArrayList<SelectItem>());
    }

    /**
     * @return the documentoHabilitante
     */
    public DocumentoHabilitante getDocumentoHabilitante() {
        return documentoHabilitante;
    }

    /**
     * @param documentoHabilitante the documentoHabilitante to set
     */
    public void setDocumentoHabilitante(final DocumentoHabilitante documentoHabilitante) {
        this.documentoHabilitante = documentoHabilitante;
    }

    /**
     * @return the documentoHabilitanteEditDelete
     */
    public DocumentoHabilitante getDocumentoHabilitanteEditDelete() {
        return documentoHabilitanteEditDelete;
    }

    /**
     * @param documentoHabilitanteEditDelete the documentoHabilitanteEditDelete
     * to set
     */
    public void setDocumentoHabilitanteEditDelete(final DocumentoHabilitante documentoHabilitanteEditDelete) {
        this.documentoHabilitanteEditDelete = documentoHabilitanteEditDelete;
    }

    /**
     * @return the listaDocumentoHabilitante
     */
    public List<DocumentoHabilitante> getListaDocumentoHabilitante() {
        return listaDocumentoHabilitante;
    }

    /**
     * @param listaDocumentoHabilitante the listaDocumentoHabilitante to set
     */
    public void setListaDocumentoHabilitante(final List<DocumentoHabilitante> listaDocumentoHabilitante) {
        this.listaDocumentoHabilitante = listaDocumentoHabilitante;
    }

    /**
     * @return the listaDocumentoHabilitanteNemonico
     */
    public List<DocumentoHabilitante> getListaDocumentoHabilitanteNemonico() {
        return listaDocumentoHabilitanteNemonico;
    }

    /**
     * @param listaDocumentoHabilitanteNemonico the
     * listaDocumentoHabilitanteNemonico to set
     */
    public void setListaDocumentoHabilitanteNemonico(List<DocumentoHabilitante> listaDocumentoHabilitanteNemonico) {
        this.listaDocumentoHabilitanteNemonico = listaDocumentoHabilitanteNemonico;
    }

    /**
     * @return the listaTipoDocumentosHabilitantes
     */
    public List<SelectItem> getListaTipoDocumentosHabilitantes() {
        return listaTipoDocumentosHabilitantes;
    }

    /**
     * @param listaTipoDocumentosHabilitantes the listaTipoDocumentosHabilitantes to set
     */
    public void setListaTipoDocumentosHabilitantes(List<SelectItem> listaTipoDocumentosHabilitantes) {
        this.listaTipoDocumentosHabilitantes = listaTipoDocumentosHabilitantes;
    }

}
