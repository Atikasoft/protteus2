/*
 *  ClaseHelper.java
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
 *  06/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Clase;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "claseHelper")
@SessionScoped
public class ClaseHelper extends CatalogoHelper {

    /**
     * Variable para nuevo documento habilitante.
     */
    private Clase clase;

    /**
     * Variable para modificar/eliminar un documento habilitante.
     */
    private Clase claseEditDelete;

    /**
     * Variable para listar los documentos habilitantes.
     */
    private List<Clase> listaClase;
    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<Clase> listaAlertaNemonico;

    /**
     * Lista para llenar el combo de grupo desde el catalogo del siit core.
     */
    private List<SelectItem> listaGrupo;

    /**
     * Lista de regimen laborales.
     */
    private List<SelectItem> listaRegimenLaboral;

    /**
     * Grupo seleccionado en el filtro de la pagina
     */
    private Long grupoSeleccionado;

    public ClaseHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del DocumentoHabilitanteHelper.
     */
    public final void iniciador() {
        setClase(new Clase());
        setClaseEditDelete(new Clase());
        claseEditDelete.setRegimenLaboral(new RegimenLaboral());
        claseEditDelete.setRegimenLaboral2(new RegimenLaboral());
        setListaClase(new ArrayList<Clase>());
        setListaGrupo(new ArrayList<SelectItem>());
        listaAlertaNemonico = new ArrayList<Clase>();

    }

    /**
     * @return the clase
     */
    public Clase getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(Clase clase) {
        this.clase = clase;
    }

    /**
     * @return the claseEditDelete
     */
    public Clase getClaseEditDelete() {
        return claseEditDelete;
    }

    /**
     * @param claseEditDelete the claseEditDelete to set
     */
    public void setClaseEditDelete(final Clase claseEditDelete) {
        this.claseEditDelete = claseEditDelete;
    }

    /**
     * @return the listaClase
     */
    public List<Clase> getListaClase() {
        return listaClase;
    }

    /**
     * @param listaClase the listaClase to set
     */
    public void setListaClase(final List<Clase> listaClase) {
        this.listaClase = listaClase;
    }

    /**
     * @return the listaGrupo
     */
    public List<SelectItem> getListaGrupo() {
        return listaGrupo;
    }

    /**
     * @param listaGrupo the listaGrupo to set
     */
    public void setListaGrupo(List<SelectItem> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    /**
     * @return the listaAlertaNemonico
     */
    public List<Clase> getListaAlertaNemonico() {
        return listaAlertaNemonico;
    }

    /**
     * @param listaAlertaNemonico the listaAlertaNemonico to set
     */
    public void setListaAlertaNemonico(List<Clase> listaAlertaNemonico) {
        this.listaAlertaNemonico = listaAlertaNemonico;
    }

    public Long getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(Long grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    /**
     * @return the listaRegimenLaboral
     */
    public List<SelectItem> getListaRegimenLaboral() {
        if (listaRegimenLaboral == null) {
            listaRegimenLaboral = new ArrayList<SelectItem>();
        }
        return listaRegimenLaboral;
    }

    /**
     * @param listaRegimenLaboral the listaRegimenLaboral to set
     */
    public void setListaRegimenLaboral(List<SelectItem> listaRegimenLaboral) {
        this.listaRegimenLaboral = listaRegimenLaboral;
    }
}
