/*
 *  DocumentoHabilitanteControlador.java
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
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.DocumentoHabilitanteHelper;
import ec.com.atikasoft.proteus.dao.CatalogoDao;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.TipoCatalogoEnum;
import ec.com.atikasoft.proteus.modelo.Catalogo;
import ec.com.atikasoft.proteus.modelo.DocumentoHabilitante;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@ManagedBean(name = "documentoHabilitanteBean")
@ViewScoped
public class DocumentoHabilitanteControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(DocumentoHabilitanteControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion"
            + "/documento_habilitante/documento_habilitante.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion"
            + "/documento_habilitante/lista_documento_habilitante.jsf";
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{documentoHabilitanteHelper}")
    private DocumentoHabilitanteHelper documentoHabilitanteHelper;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     *
     */
    @EJB
    private CatalogoDao catalogoDao;

    /**
     * Constructor.
     */
    public DocumentoHabilitanteControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(documentoHabilitanteHelper);
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
        iniciarCombos();
    }

    /**
     *
     */
    private void iniciarCombos() {
        try {
            iniciarCombos(documentoHabilitanteHelper.getListaTipoDocumentosHabilitantes());
            List<Catalogo> tipos = catalogoDao.buscarPorTipoCatalogoCodigo(TipoCatalogoEnum.TIPO_DOCUMENTO_HABILITANTE.getCodigo());
            for (Catalogo tipo : tipos) {
                documentoHabilitanteHelper.getListaTipoDocumentosHabilitantes().add(new SelectItem(tipo.getId(), tipo.getNombre()));
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.DOCUMENTOS_HABILITANTES.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE DOCUMENTOS HABILITANTES");
        generarUrlDeReporte();
    }

    @Override
    public String guardar() {
        try {
            if (getDocumentoHabilitanteHelper().getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE,
                            FacesMessage.SEVERITY_WARN);
                } else {
                    administracionServicio.guardarDocumentoHabilitante(getDocumentoHabilitanteHelper().
                            getDocumentoHabilitante());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                administracionServicio.actualizarDocumentoHabilitante(getDocumentoHabilitanteHelper().
                        getDocumentoHabilitante());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object documentoHabilitante
                    = BeanUtils.cloneBean(getDocumentoHabilitanteHelper().getDocumentoHabilitanteEditDelete());
            DocumentoHabilitante d = (DocumentoHabilitante) documentoHabilitante;
            iniciarDatosEntidad(d, Boolean.FALSE);
            getDocumentoHabilitanteHelper().setDocumentoHabilitante(d);
            getDocumentoHabilitanteHelper().setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        getDocumentoHabilitanteHelper().setDocumentoHabilitante(new DocumentoHabilitante());
        iniciarDatosEntidad(getDocumentoHabilitanteHelper().getDocumentoHabilitante(), Boolean.TRUE);
        getDocumentoHabilitanteHelper().setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            if (!(administracionServicio.tieneRestricciones("documentoHabilitante.id",
                    "DocumentoHabilitante", "TipoMovimiento",
                    documentoHabilitanteHelper.getDocumentoHabilitanteEditDelete().getId()))) {
                administracionServicio.eliminarDocumentoHabilitante(getDocumentoHabilitanteHelper().
                        getDocumentoHabilitanteEditDelete());
                getDocumentoHabilitanteHelper().getListaDocumentoHabilitante().
                        remove(getDocumentoHabilitanteHelper().getDocumentoHabilitanteEditDelete());
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR, " en Tipo de Movimiento");
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            documentoHabilitanteHelper.getListaDocumentoHabilitante().clear();
            documentoHabilitanteHelper.setListaDocumentoHabilitante(
                    administracionServicio.listarTodosDocumentoHabilitantePorNombre(
                            getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para validar si ya existe el nemónico.
     *
     * @return haynemonico Boolean.
     */
    public Boolean validarNemonico() {
        Boolean hayNemonico = true;
        try {
            documentoHabilitanteHelper.getListaDocumentoHabilitanteNemonico().clear();
            documentoHabilitanteHelper.setListaDocumentoHabilitanteNemonico(
                    administracionServicio.listarTodosDocumentoHabilitantePorNemonico(
                            documentoHabilitanteHelper.getDocumentoHabilitante().getNemonico()));
            if (documentoHabilitanteHelper.getListaDocumentoHabilitanteNemonico().isEmpty()) {
                hayNemonico = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nemonico", ex);
        }
        return hayNemonico;
    }

    /**
     * @return the documentoHabilitanteHelper
     */
    public DocumentoHabilitanteHelper getDocumentoHabilitanteHelper() {
        return documentoHabilitanteHelper;
    }

    /**
     * @param documentoHabilitanteHelper the documentoHabilitanteHelper to set
     */
    public void setDocumentoHabilitanteHelper(final DocumentoHabilitanteHelper documentoHabilitanteHelper) {
        this.documentoHabilitanteHelper = documentoHabilitanteHelper;
    }
}
