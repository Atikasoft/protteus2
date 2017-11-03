/*
 *  TerceroHelper.java
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
 *  26/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Tercero;
import ec.com.atikasoft.proteus.modelo.TerceroNominaDetalle;
import ec.com.atikasoft.proteus.vo.PersonaVO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.UploadedFile;

/**
 * Tercero
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "terceroHelper")
@SessionScoped
public class TerceroHelper extends CatalogoHelper {

    /**
     * clase tercero.
     */
    private Tercero tercero;

    /**
     * clase tercero puesto para editar.
     */
    private Tercero terceroEditDelete;
    /**
     * Variable para busqueda de persona.
     */
    private PersonaVO personaVO;
    /**
     * lista de terceros.
     */
    private List<Tercero> listaTerceros;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<Tercero> listaTerceroCodigo;
    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<SelectItem> listaEstados;
        /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<SelectItem> listaTipoIdentificacion;

          /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<TerceroNominaDetalle> listaTercerosEnNomina;
      /**
     * archivoFile.
     */
    private File archivoFile;
    /**
     * foto del servidor.
     */
    private String nombreArchivo;
    /**
     * Archivo para justificacion.
     */
    private UploadedFile archivoCargado;

    /**
     * Constructor por defecto.
     */
    public TerceroHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del TerceroHelper.
     */
    public final void iniciador() {
        setTercero(new Tercero());
        setTerceroEditDelete(new Tercero());
        setListaTerceros(new ArrayList<Tercero>());
       setListaTerceroCodigo(new ArrayList<Tercero>());
        personaVO = new PersonaVO();
        listaEstados = new ArrayList<SelectItem>();
        listaTipoIdentificacion = new ArrayList<SelectItem>();
        listaTercerosEnNomina = new ArrayList<TerceroNominaDetalle>();
    }

    /**
     * @return the tercero
     */
    public Tercero getTercero() {
        return tercero;
    }

    /**
     * @param tercero the tercero to set
     */
    public void setTercero(final Tercero tercero) {
        this.tercero = tercero;
    }

    /**
     * @return the terceroEditDelete
     */
    public Tercero getTerceroEditDelete() {
        return terceroEditDelete;
    }

    /**
     * @param terceroEditDelete the terceroEditDelete to set
     */
    public void setTerceroEditDelete(final Tercero terceroEditDelete) {
        this.terceroEditDelete = terceroEditDelete;
    }

    /**
     * @return the listaTerceros
     */
    public List<Tercero> getListaTerceros() {
        return listaTerceros;
    }

    /**
     * @param listaTerceros the listaTerceros to set
     */
    public void setListaTerceros(final List<Tercero> listaTerceros) {
        this.listaTerceros = listaTerceros;
    }

    /**
     * @return the listaTerceroCodigo
     */
    public List<Tercero> getListaTerceroCodigo() {
        return listaTerceroCodigo;
    }

    /**
     * @param listaTerceroCodigo the listaTerceroCodigo to set
     */
    public void setListaTerceroCodigo(final List<Tercero> listaTerceroCodigo) {
        this.listaTerceroCodigo = listaTerceroCodigo;
    }

    /**
     * @return the personaVO
     */
    public PersonaVO getPersonaVO() {
        return personaVO;
    }

    /**
     * @param personaVO the personaVO to set
     */
    public void setPersonaVO(PersonaVO personaVO) {
        this.personaVO = personaVO;
    }

    /**
     * @return the listaEstados
     */
    public List<SelectItem> getListaEstados() {
        return listaEstados;
    }

    /**
     * @param listaEstados the listaEstados to set
     */
    public void setListaEstados(List<SelectItem> listaEstados) {
        this.listaEstados = listaEstados;
    }

    /**
     * @return the listaTipoIdentificacion
     */
    public List<SelectItem> getListaTipoIdentificacion() {
        return listaTipoIdentificacion;
    }

    /**
     * @param listaTipoIdentificacion the listaTipoIdentificacion to set
     */
    public void setListaTipoIdentificacion(List<SelectItem> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }

    /**
     * @return the archivoFile
     */
    public File getArchivoFile() {
        return archivoFile;
    }

    /**
     * @param archivoFile the archivoFile to set
     */
    public void setArchivoFile(File archivoFile) {
        this.archivoFile = archivoFile;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the archivoCargado
     */
    public UploadedFile getArchivoCargado() {
        return archivoCargado;
    }

    /**
     * @param archivoCargado the archivoCargado to set
     */
    public void setArchivoCargado(UploadedFile archivoCargado) {
        this.archivoCargado = archivoCargado;
    }

    /**
     * @return the listaTercerosEnNomina
     */
    public List<TerceroNominaDetalle> getListaTercerosEnNomina() {
        return listaTercerosEnNomina;
    }

    /**
     * @param listaTercerosEnNomina the listaTercerosEnNomina to set
     */
    public void setListaTercerosEnNomina(List<TerceroNominaDetalle> listaTercerosEnNomina) {
        this.listaTercerosEnNomina = listaTercerosEnNomina;
    }
}
