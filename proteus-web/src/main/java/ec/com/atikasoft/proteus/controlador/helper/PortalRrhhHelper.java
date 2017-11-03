/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.enums.EstadoDatoAdiconalServidorEnum;
import ec.com.atikasoft.proteus.modelo.CuentaBancaria;
import ec.com.atikasoft.proteus.modelo.PortalRhh;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorCargaFamiliar;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorCapacitacion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorEvaluacion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorExperiencia;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorInformacionMedica;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorInstruccion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorParienteInstitucion;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author atikasoft
 */
/**
 * ServidorHelper
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "portalRrhhHelper")
@SessionScoped
public final class PortalRrhhHelper implements Serializable {

    /**
     * listaPortalRhhs.
     */
    private List<PortalRhh> listaPortalRhhs;
    /**
     * servidor.
     */
    private Servidor servidor;
      /**
     * servidor.
     */
    private Servidor servidorEditDelete;
    /**
     * tipo sangre.
     */
    private List<SelectItem> listaTipoSangre;
    /**
     * tipo listaTipoEstadoCivil.
     */
    private List<SelectItem> listaTipoEstadoCivil;
    /**
     * tipo listaTipoEstadoCivil.
     */
    private List<SelectItem> listaTipoEtnia;
    /**
     * tipo listaGenero.
     */
    private List<SelectItem> listaGenero;
    /**
     * tipo tipoEstadoCivil.
     */
    private Long tipoEstadoCivil;
    /**
     * tipo listaTallaUniforme.
     */
    private List<SelectItem> listaTallaUniforme;
    /**
     * tipo listaTallaUniforme.
     */
    private List<SelectItem> listaNumeroCalzado;
    /**
     * tipo listaCapacidadEspecial.
     */
    private List<SelectItem> listaCapacidadEspecial;
    /**
     * tipo listaNacionalidad.
     */
    private List<SelectItem> listaNacionalidad;
    /**
     * tipo listaCanto.
     */
    private List<SelectItem> listaParentezco;
    /**
     * tipo listaCanto.
     */
    private List<SelectItem> listaDependencia;
    /**
     * tipo listaCanto.
     */
    private List<SelectItem> listaEnfermedades;
    /**
     * tipo listaCanto.
     */
    private List<SelectItem> listaDiscapacidades;
    /**
     * tipo listaCanto.
     */
    private List<SelectItem> listaParentestoConDiscapacidad;
    /**
     * tipo listaCanto.
     */
    private List<SelectItem> listaEnfermedadesCatastroficas;
    /**
     * tipo listaCanto.
     */
    private List<SelectItem> listaParentescoEnfermedadCatastrofica;
    /**
     * tipo listaProvincia.
     */
    private List<SelectItem> listaTipoDiploma;
    /**
     * tipo listaProvincia.
     */
    private List<SelectItem> listaProvincia;
    /**
     * tipo provincia.
     */
    private Long provincia;
    /**
     * tipo listaPais.
     */
    private List<SelectItem> listaPais;
    /**
     * tipo pais.
     */
    private Long pais;
    /**
     * tipo listaCanto.
     */
    private List<SelectItem> listaCanto;
    /**
     * tipo canto.
     */
    private Long canto;
    /**
     * tipo listaParroquia.
     */
    private List<SelectItem> listaParroquia;
    /**
     * tipo listaCanto.
     */
    private List<SelectItem> listaNivelInstruccion;
    /**
     * tipo parroquia.
     */
    private Long nivelInstruccion;
    /**
     * servidorCapacitacion
     */
    private ServidorCapacitacion servidorCapacitacion;
    /**
     * listaServidorCapacitaciones
     */
    private List<ServidorCapacitacion> listaServidorCapacitaciones;
    /**
     * servidorCapacitacion
     */
    private ServidorCapacitacion servidorCapacitacionEditDelete;
    /**
     * servidorCapacitacion
     */
    private ServidorEvaluacion servidorEvaluacion;
    /**
     * servidorCapacitacion
     */
    private ServidorEvaluacion servidorEvaluacionEditDelete;
    /**
     * listaServidorCapacitaciones
     */
    private List<ServidorEvaluacion> listaServidorEvaluaciones;
    /**
     * servidorCapacitacion
     */
    private ServidorExperiencia servidorExperiencia;
    /**
     * ServidorExperiencia
     */
    private ServidorExperiencia servidorExperienciaEditDelete;
    /**
     * ServidorExperiencia
     */
    private List<ServidorExperiencia> listaServidorExperiencias;
    /**
     * servidorInstitucion
     */
    private ServidorInstitucion servidorInstitucion;
    /**
     * servidorInstruccion
     */
    private ServidorInstruccion servidorInstruccion;
    /**
     * ServidorInstruccion
     */
    private ServidorInstruccion servidorInstruccionEditDelete;
    /**
     * ServidorInstruccion
     */
    private List<ServidorInstruccion> listaServidorInstrucciones;
    /**
     * ServidorParienteInstitucion
     */
    private ServidorParienteInstitucion servidorParienteInstitucion;
    /**
     * ServidorParienteInstitucion
     */
    private ServidorParienteInstitucion servidorParienteInstitucionEditDelete;
    /**
     * ServidorParienteInstitucion
     */
    private List<ServidorParienteInstitucion> listaServidorParienteInstituciones;
    /**
     * servidorInformacionMedica
     */
    private ServidorInformacionMedica servidorInformacionMedica;
    /**
     * ServidorInformacionMedica
     */
    private ServidorInformacionMedica servidorInformacionMedicaEditDelete;
    /**
     * ServidorInformacionMedica
     */
    private List<ServidorInformacionMedica> listaServidorInformacionMedicas;
    /**
     * ServidorCargaFamiliar
     */
    private ServidorCargaFamiliar servidorCargaFamiliar;
    /**
     * ServidorCargaFamiliar
     */
    private ServidorCargaFamiliar servidorCargaFamiliarEditDelete;
    /**
     * ServidorCargaFamiliar
     */
    private List<ServidorCargaFamiliar> listaservidorCargaFamiliares;
    /**
     * archivoFile
     */
    private File archivoFile;
    /**
     * imagen
     */
    private StreamedContent imagen;
    /**
     * foto del servidor
     */
    private String fotoServidor;
    /**
     * Archivo para foto.
     */
    private UploadedFile archivoFoto;
    /**
     * preentar
     */
    private Boolean presentar;
    /**
     * preentar
     */
    private Boolean ServidorEditable;
    /**
     * Variable para restringir el inicio del calendario para la fecha de inicio
     */
    private Date hoy;
    /**
     * validar tabs
     */
    private String tab;

    /**
     * presentar areas de las nuebas tablas.
     */
    private Boolean esNuevoDialog;
    /**
     * tipo listaCanto.
     */
    private List<SelectItem> listaEstadoCivil;
    /**
     * presentar discapacidad.
     */
    private Boolean discapacidad;
    
    /**
     * Lista de cuentas bancarias
     */
    private List<CuentaBancaria> listaCuentasBancarias;
    
    /**
     * Referencia para manejo de crud
     */
    private CuentaBancaria cuentaBancaria;
    
    /**
     * Lista de bancos
     */
    private List<SelectItem> bancos;
    
    /**
     * Lista de tipos de cuentas
     */
    private List<SelectItem> tiposCuenta;
    
    /**
     * Banco seleccionado en el popup de cuenta bancaria
     */
    private Long bancoSeleccionado;
    
    /**
     * Tipo de cuenta seleccionada en el popup de cuenta bancaria
     */
    private Long tipoCuentaSeleccionado;
    
    /**
     * Indica si puede modificar los pagos de decimos.
     */
    private Boolean modificaDecimos;
    
    /**
     * Estados de los datos que necesitan aprobacion
     */
    private List<SelectItem> estadosDatos;

    public PortalRrhhHelper() {
        super();
        iniciador();
    }

    public void iniciador() {
        setListaPortalRhhs(new ArrayList<PortalRhh>());
//        servidor = new Servidor();
//       getServidor().setUbicacionGeograficaCantonNacimiento(new UbicacionGeografica());
        setListaTipoSangre(new ArrayList<SelectItem>());
        listaTipoEstadoCivil = new ArrayList<SelectItem>();
        listaTipoEtnia = new ArrayList<SelectItem>();
        listaTallaUniforme = new ArrayList<SelectItem>();
        setListaNumeroCalzado(new ArrayList<SelectItem>());
        listaCapacidadEspecial = new ArrayList<SelectItem>();
        listaNacionalidad = new ArrayList<SelectItem>();
        listaProvincia = new ArrayList<SelectItem>();
        listaCanto = new ArrayList<SelectItem>();
        listaPais = new ArrayList<SelectItem>();
        listaParroquia = new ArrayList<SelectItem>();
        servidorCapacitacion = new ServidorCapacitacion();
        servidorEvaluacion = new ServidorEvaluacion();
        setServidorExperiencia(new ServidorExperiencia());
        servidorInstitucion = new ServidorInstitucion();
        servidorInstruccion = new ServidorInstruccion();
        listaNivelInstruccion = new ArrayList<SelectItem>();
        servidorParienteInstitucion = new ServidorParienteInstitucion();
        listaParentezco = new ArrayList<SelectItem>();
        listaDependencia = new ArrayList<SelectItem>();
        setServidorInformacionMedica(new ServidorInformacionMedica());
        listaEnfermedades = new ArrayList<SelectItem>();
        listaParentestoConDiscapacidad = new ArrayList<SelectItem>();
        listaEnfermedadesCatastroficas = new ArrayList<SelectItem>();
        listaParentescoEnfermedadCatastrofica = new ArrayList<SelectItem>();
        listaDiscapacidades = new ArrayList<SelectItem>();
        listaTipoDiploma = new ArrayList<SelectItem>();
        hoy = new Date();
        setListaServidorCapacitaciones(new ArrayList<ServidorCapacitacion>());
        setServidorCapacitacionEditDelete(new ServidorCapacitacion());
        setServidorEvaluacionEditDelete(new ServidorEvaluacion());
        setListaServidorEvaluaciones(new ArrayList<ServidorEvaluacion>());
        setServidorExperienciaEditDelete(new ServidorExperiencia());
        setListaServidorExperiencias(new ArrayList<ServidorExperiencia>());
        setListaServidorInstrucciones(new ArrayList<ServidorInstruccion>());
        setServidorInstruccionEditDelete(new ServidorInstruccion());
        setServidorParienteInstitucionEditDelete(new ServidorParienteInstitucion());
        setListaServidorParienteInstituciones(new ArrayList<ServidorParienteInstitucion>());
        setListaServidorInformacionMedicas(new ArrayList<ServidorInformacionMedica>());
        setServidorInformacionMedicaEditDelete(new ServidorInformacionMedica());
        setListaGenero(new ArrayList<SelectItem>());
        setListaEstadoCivil(new ArrayList<SelectItem>());
        setServidorCargaFamiliar(new ServidorCargaFamiliar());
        setServidorCargaFamiliarEditDelete(new ServidorCargaFamiliar());
        setListaservidorCargaFamiliares(new ArrayList<ServidorCargaFamiliar>());
        setServidorEditable(Boolean.FALSE);
        listaCuentasBancarias = new ArrayList<CuentaBancaria>();
        bancos = new ArrayList<SelectItem>();
        tiposCuenta = new ArrayList<SelectItem>();
        cuentaBancaria = new CuentaBancaria();
        estadosDatos = new ArrayList<SelectItem>();
        for(EstadoDatoAdiconalServidorEnum e: EstadoDatoAdiconalServidorEnum.values()){
            estadosDatos.add(new SelectItem(e.getCodigo(), e.getNombre()));
        }
    }

    /**
     * @return the listaPortalRhhs
     */
    public List<PortalRhh> getListaPortalRhhs() {
        return listaPortalRhhs;
    }

    /**
     * @param listaPortalRhhs the listaPortalRhhs to set
     */
    public void setListaPortalRhhs(final List<PortalRhh> listaPortalRhhs) {
        this.listaPortalRhhs = listaPortalRhhs;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(final Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the listaTipoSangre
     */
    public List<SelectItem> getListaTipoSangre() {
        return listaTipoSangre;
    }

    /**
     * @param listaTipoSangre the listaTipoSangre to set
     */
    public void setListaTipoSangre(final List<SelectItem> listaTipoSangre) {
        this.listaTipoSangre = listaTipoSangre;
    }

    /**
     * @return the listaTipoEstadoCivil
     */
    public List<SelectItem> getListaTipoEstadoCivil() {
        return listaTipoEstadoCivil;
    }

    /**
     * @param listaTipoEstadoCivil the listaTipoEstadoCivil to set
     */
    public void setListaTipoEstadoCivil(final List<SelectItem> listaTipoEstadoCivil) {
        this.listaTipoEstadoCivil = listaTipoEstadoCivil;
    }

    /**
     * @return the listaTipoEtnia
     */
    public List<SelectItem> getListaTipoEtnia() {
        return listaTipoEtnia;
    }

    /**
     * @param listaTipoEtnia the listaTipoEtnia to set
     */
    public void setListaTipoEtnia(final List<SelectItem> listaTipoEtnia) {
        this.listaTipoEtnia = listaTipoEtnia;
    }

    /**
     * @return the tipoEstadoCivil
     */
    public Long getTipoEstadoCivil() {
        return tipoEstadoCivil;
    }

    /**
     * @param tipoEstadoCivil the tipoEstadoCivil to set
     */
    public void setTipoEstadoCivil(final Long tipoEstadoCivil) {
        this.tipoEstadoCivil = tipoEstadoCivil;
    }

    /**
     * @return the listaTallaUniforme
     */
    public List<SelectItem> getListaTallaUniforme() {
        return listaTallaUniforme;
    }

    /**
     * @param listaTallaUniforme the listaTallaUniforme to set
     */
    public void setListaTallaUniforme(final List<SelectItem> listaTallaUniforme) {
        this.listaTallaUniforme = listaTallaUniforme;
    }

    /**
     * @return the listaNumeroCalzado
     */
    public List<SelectItem> getListaNumeroCalzado() {
        return listaNumeroCalzado;
    }

    /**
     * @param listaNumeroCalzado the listaNumeroCalzado to set
     */
    public void setListaNumeroCalzado(final List<SelectItem> listaNumeroCalzado) {
        this.listaNumeroCalzado = listaNumeroCalzado;
    }

    /**
     * @return the listaCapacidadEspecial
     */
    public List<SelectItem> getListaCapacidadEspecial() {
        return listaCapacidadEspecial;
    }

    /**
     * @param listaCapacidadEspecial the listaCapacidadEspecial to set
     */
    public void setListaCapacidadEspecial(final List<SelectItem> listaCapacidadEspecial) {
        this.listaCapacidadEspecial = listaCapacidadEspecial;
    }

    /**
     * @return the listaNacionalidad
     */
    public List<SelectItem> getListaNacionalidad() {
        return listaNacionalidad;
    }

    /**
     * @param listaNacionalidad the listaNacionalidad to set
     */
    public void setListaNacionalidad(final List<SelectItem> listaNacionalidad) {
        this.listaNacionalidad = listaNacionalidad;
    }

    /**
     * @return the listaProvincia
     */
    public List<SelectItem> getListaProvincia() {
        return listaProvincia;
    }

    /**
     * @param listaProvincia the listaProvincia to set
     */
    public void setListaProvincia(final List<SelectItem> listaProvincia) {
        this.listaProvincia = listaProvincia;
    }

    /**
     * @return the provincia
     */
    public Long getProvincia() {
        return provincia;
    }

    /**
     * @param provincia the provincia to set
     */
    public void setProvincia(final Long provincia) {
        this.provincia = provincia;
    }

    /**
     * @return the listaPais
     */
    public List<SelectItem> getListaPais() {
        return listaPais;
    }

    /**
     * @param listaPais the listaPais to set
     */
    public void setListaPais(final List<SelectItem> listaPais) {
        this.listaPais = listaPais;
    }

    /**
     * @return the pais
     */
    public Long getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(final Long pais) {
        this.pais = pais;
    }

    /**
     * @return the listaCanto
     */
    public List<SelectItem> getListaCanto() {
        return listaCanto;
    }

    /**
     * @param listaCanto the listaCanto to set
     */
    public void setListaCanto(final List<SelectItem> listaCanto) {
        this.listaCanto = listaCanto;
    }

    /**
     * @return the canto
     */
    public Long getCanto() {
        return canto;
    }

    /**
     * @param canto the canto to set
     */
    public void setCanto(final Long canto) {
        this.canto = canto;
    }

    /**
     * @return the listaParroquia
     */
    public List<SelectItem> getListaParroquia() {
        return listaParroquia;
    }

    /**
     * @param listaParroquia the listaParroquia to set
     */
    public void setListaParroquia(final List<SelectItem> listaParroquia) {
        this.listaParroquia = listaParroquia;
    }

    /**
     * @return the servidorCapacitacion
     */
    public ServidorCapacitacion getServidorCapacitacion() {
        return servidorCapacitacion;
    }

    /**
     * @param servidorCapacitacion the servidorCapacitacion to set
     */
    public void setServidorCapacitacion(final ServidorCapacitacion servidorCapacitacion) {
        this.servidorCapacitacion = servidorCapacitacion;
    }

    /**
     * @return the servidorEvaluacion
     */
    public ServidorEvaluacion getServidorEvaluacion() {
        return servidorEvaluacion;
    }

    /**
     * @param servidorEvaluacion the servidorEvaluacion to set
     */
    public void setServidorEvaluacion(final ServidorEvaluacion servidorEvaluacion) {
        this.servidorEvaluacion = servidorEvaluacion;
    }

    /**
     * @return the servidorExperiencia
     */
    public ServidorExperiencia getServidorExperiencia() {
        return servidorExperiencia;
    }

    /**
     * @param servidorExperiencia the servidorExperiencia to set
     */
    public void setServidorExperiencia(final ServidorExperiencia servidorExperiencia) {
        this.servidorExperiencia = servidorExperiencia;
    }

    /**
     * @return the servidorInstitucion
     */
    public ServidorInstitucion getServidorInstitucion() {
        return servidorInstitucion;
    }

    /**
     * @param servidorInstitucion the servidorInstitucion to set
     */
    public void setServidorInstitucion(final ServidorInstitucion servidorInstitucion) {
        this.servidorInstitucion = servidorInstitucion;
    }

    /**
     * @return the servidorInstruccion
     */
    public ServidorInstruccion getServidorInstruccion() {
        return servidorInstruccion;
    }

    /**
     * @param servidorInstruccion the servidorInstruccion to set
     */
    public void setServidorInstruccion(final ServidorInstruccion servidorInstruccion) {
        this.servidorInstruccion = servidorInstruccion;
    }

    /**
     * @return the listaNivelInstruccion
     */
    public List<SelectItem> getListaNivelInstruccion() {
        return listaNivelInstruccion;
    }

    /**
     * @param listaNivelInstruccion the listaNivelInstruccion to set
     */
    public void setListaNivelInstruccion(final List<SelectItem> listaNivelInstruccion) {
        this.listaNivelInstruccion = listaNivelInstruccion;
    }

    /**
     * @return the nivelInstruccion
     */
    public Long getNivelInstruccion() {
        return nivelInstruccion;
    }

    /**
     * @param nivelInstruccion the nivelInstruccion to set
     */
    public void setNivelInstruccion(final Long nivelInstruccion) {
        this.nivelInstruccion = nivelInstruccion;
    }

    /**
     * @return the servidorParienteInstitucion
     */
    public ServidorParienteInstitucion getServidorParienteInstitucion() {
        return servidorParienteInstitucion;
    }

    /**
     * @param servidorParienteInstitucion the servidorParienteInstitucion to set
     */
    public void setServidorParienteInstitucion(final ServidorParienteInstitucion servidorParienteInstitucion) {
        this.servidorParienteInstitucion = servidorParienteInstitucion;
    }

    /**
     * @return the listaParentezco
     */
    public List<SelectItem> getListaParentezco() {
        return listaParentezco;
    }

    /**
     * @param listaParentezco the listaParentezco to set
     */
    public void setListaParentezco(final List<SelectItem> listaParentezco) {
        this.listaParentezco = listaParentezco;
    }

    /**
     * @return the listaDependencia
     */
    public List<SelectItem> getListaDependencia() {
        return listaDependencia;
    }

    /**
     * @param listaDependencia the listaDependencia to set
     */
    public void setListaDependencia(final List<SelectItem> listaDependencia) {
        this.listaDependencia = listaDependencia;
    }

    /**
     * @return the servidorInformacionMedica
     */
    public ServidorInformacionMedica getServidorInformacionMedica() {
        return servidorInformacionMedica;
    }

    /**
     * @param servidorInformacionMedica the servidorInformacionMedica to set
     */
    public void setServidorInformacionMedica(final ServidorInformacionMedica servidorInformacionMedica) {
        this.servidorInformacionMedica = servidorInformacionMedica;
    }

    /**
     * @return the listaEnfermedades
     */
    public List<SelectItem> getListaEnfermedades() {
        return listaEnfermedades;
    }

    /**
     * @param listaEnfermedades the listaEnfermedades to set
     */
    public void setListaEnfermedades(final List<SelectItem> listaEnfermedades) {
        this.listaEnfermedades = listaEnfermedades;
    }

    /**
     * @return the listaParentestoConDiscapacidad
     */
    public List<SelectItem> getListaParentestoConDiscapacidad() {
        return listaParentestoConDiscapacidad;
    }

    /**
     * @param listaParentestoConDiscapacidad the listaParentestoConDiscapacidad
     * to set
     */
    public void setListaParentestoConDiscapacidad(final List<SelectItem> listaParentestoConDiscapacidad) {
        this.listaParentestoConDiscapacidad = listaParentestoConDiscapacidad;
    }

    /**
     * @return the listaEnfermedadesCatastroficas
     */
    public List<SelectItem> getListaEnfermedadesCatastroficas() {
        return listaEnfermedadesCatastroficas;
    }

    /**
     * @param listaEnfermedadesCatastroficas the listaEnfermedadesCatastroficas
     * to set
     */
    public void setListaEnfermedadesCatastroficas(final List<SelectItem> listaEnfermedadesCatastroficas) {
        this.listaEnfermedadesCatastroficas = listaEnfermedadesCatastroficas;
    }

    /**
     * @return the listaParentescoEnfermedadCatastrofica
     */
    public List<SelectItem> getListaParentescoEnfermedadCatastrofica() {
        return listaParentescoEnfermedadCatastrofica;
    }

    /**
     * @param listaParentescoEnfermedadCatastrofica the
     * listaParentescoEnfermedadCatastrofica to set
     */
    public void setListaParentescoEnfermedadCatastrofica(final List<SelectItem> listaParentescoEnfermedadCatastrofica) {
        this.listaParentescoEnfermedadCatastrofica = listaParentescoEnfermedadCatastrofica;
    }

    /**
     * @return the listaDiscapacidades
     */
    public List<SelectItem> getListaDiscapacidades() {
        return listaDiscapacidades;
    }

    /**
     * @param listaDiscapacidades the listaDiscapacidades to set
     */
    public void setListaDiscapacidades(final List<SelectItem> listaDiscapacidades) {
        this.listaDiscapacidades = listaDiscapacidades;
    }

    /**
     * @return the listaTipoDiploma
     */
    public List<SelectItem> getListaTipoDiploma() {
        return listaTipoDiploma;
    }

    /**
     * @param listaTipoDiploma the listaTipoDiploma to set
     */
    public void setListaTipoDiploma(final List<SelectItem> listaTipoDiploma) {
        this.listaTipoDiploma = listaTipoDiploma;
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
    public void setArchivoFile(final File archivoFile) {
        this.archivoFile = archivoFile;
    }

    /**
     * @return the imagen
     */
    public StreamedContent getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(final StreamedContent imagen) {
        this.imagen = imagen;
    }

    /**
     * @return the fotoServidor
     */
    public String getFotoServidor() {
        return fotoServidor;
    }

    /**
     * @param fotoServidor the fotoServidor to set
     */
    public void setFotoServidor(final String fotoServidor) {
        this.fotoServidor = fotoServidor;
    }

    /**
     * @return the archivoFoto
     */
    public UploadedFile getArchivoFoto() {
        return archivoFoto;
    }

    /**
     * @param archivoFoto the archivoFoto to set
     */
    public void setArchivoFoto(final UploadedFile archivoFoto) {
        this.archivoFoto = archivoFoto;
    }

    /**
     * @return the presentar
     */
    public Boolean getPresentar() {
        return presentar;
    }

    /**
     * @param presentar the presentar to set
     */
    public void setPresentar(final Boolean presentar) {
        this.presentar = presentar;
    }

    /**
     * @return the hoy
     */
    public Date getHoy() {
        return hoy;
    }

    /**
     * @param hoy the hoy to set
     */
    public void setHoy(final Date hoy) {
        this.hoy = hoy;
    }

    /**
     * @return the tab
     */
    public String getTab() {
        return tab;
    }

    /**
     * @param tab the tab to set
     */
    public void setTab(String tab) {
        this.tab = tab;
    }

    /**
     * @return the esNuevoDialog
     */
    public Boolean getEsNuevoDialog() {
        return esNuevoDialog;
    }

    /**
     * @param esNuevoDialog the esNuevoDialog to set
     */
    public void setEsNuevoDialog(Boolean esNuevoDialog) {
        this.esNuevoDialog = esNuevoDialog;
    }

    /**
     * @return the listaServidorCapacitaciones
     */
    public List<ServidorCapacitacion> getListaServidorCapacitaciones() {
        return listaServidorCapacitaciones;
    }

    /**
     * @param listaServidorCapacitaciones the listaServidorCapacitaciones to set
     */
    public void setListaServidorCapacitaciones(List<ServidorCapacitacion> listaServidorCapacitaciones) {
        this.listaServidorCapacitaciones = listaServidorCapacitaciones;
    }

    /**
     * @return the servidorCapacitacionEditDelete
     */
    public ServidorCapacitacion getServidorCapacitacionEditDelete() {
        return servidorCapacitacionEditDelete;
    }

    /**
     * @param servidorCapacitacionEditDelete the servidorCapacitacionEditDelete
     * to set
     */
    public void setServidorCapacitacionEditDelete(ServidorCapacitacion servidorCapacitacionEditDelete) {
        this.servidorCapacitacionEditDelete = servidorCapacitacionEditDelete;
    }

    /**
     * @return the servidorEvaluacionEditDelete
     */
    public ServidorEvaluacion getServidorEvaluacionEditDelete() {
        return servidorEvaluacionEditDelete;
    }

    /**
     * @param servidorEvaluacionEditDelete the servidorEvaluacionEditDelete to
     * set
     */
    public void setServidorEvaluacionEditDelete(ServidorEvaluacion servidorEvaluacionEditDelete) {
        this.servidorEvaluacionEditDelete = servidorEvaluacionEditDelete;
    }

    /**
     * @return the listaServidorEvaluaciones
     */
    public List<ServidorEvaluacion> getListaServidorEvaluaciones() {
        return listaServidorEvaluaciones;
    }

    /**
     * @param listaServidorEvaluaciones the listaServidorEvaluaciones to set
     */
    public void setListaServidorEvaluaciones(List<ServidorEvaluacion> listaServidorEvaluaciones) {
        this.listaServidorEvaluaciones = listaServidorEvaluaciones;
    }

    /**
     * @return the servidorExperienciaEditDelete
     */
    public ServidorExperiencia getServidorExperienciaEditDelete() {
        return servidorExperienciaEditDelete;
    }

    /**
     * @param servidorExperienciaEditDelete the servidorExperienciaEditDelete to
     * set
     */
    public void setServidorExperienciaEditDelete(ServidorExperiencia servidorExperienciaEditDelete) {
        this.servidorExperienciaEditDelete = servidorExperienciaEditDelete;
    }

    /**
     * @return the listaServidorExperiencias
     */
    public List<ServidorExperiencia> getListaServidorExperiencias() {
        return listaServidorExperiencias;
    }

    /**
     * @param listaServidorExperiencias the listaServidorExperiencias to set
     */
    public void setListaServidorExperiencias(List<ServidorExperiencia> listaServidorExperiencias) {
        this.listaServidorExperiencias = listaServidorExperiencias;
    }

    /**
     * @return the servidorInstruccionEditDelete
     */
    public ServidorInstruccion getServidorInstruccionEditDelete() {
        return servidorInstruccionEditDelete;
    }

    /**
     * @param servidorInstruccionEditDelete the servidorInstruccionEditDelete to
     * set
     */
    public void setServidorInstruccionEditDelete(ServidorInstruccion servidorInstruccionEditDelete) {
        this.servidorInstruccionEditDelete = servidorInstruccionEditDelete;
    }

    /**
     * @return the listaServidorInstrucciones
     */
    public List<ServidorInstruccion> getListaServidorInstrucciones() {
        return listaServidorInstrucciones;
    }

    /**
     * @param listaServidorInstrucciones the listaServidorInstrucciones to set
     */
    public void setListaServidorInstrucciones(List<ServidorInstruccion> listaServidorInstrucciones) {
        this.listaServidorInstrucciones = listaServidorInstrucciones;
    }

    /**
     * @return the servidorParienteInstitucionEditDelete
     */
    public ServidorParienteInstitucion getServidorParienteInstitucionEditDelete() {
        return servidorParienteInstitucionEditDelete;
    }

    /**
     * @param servidorParienteInstitucionEditDelete the
     * servidorParienteInstitucionEditDelete to set
     */
    public void setServidorParienteInstitucionEditDelete(ServidorParienteInstitucion servidorParienteInstitucionEditDelete) {
        this.servidorParienteInstitucionEditDelete = servidorParienteInstitucionEditDelete;
    }

    /**
     * @return the listaServidorParienteInstituciones
     */
    public List<ServidorParienteInstitucion> getListaServidorParienteInstituciones() {
        return listaServidorParienteInstituciones;
    }

    /**
     * @param listaServidorParienteInstituciones the
     * listaServidorParienteInstituciones to set
     */
    public void setListaServidorParienteInstituciones(List<ServidorParienteInstitucion> listaServidorParienteInstituciones) {
        this.listaServidorParienteInstituciones = listaServidorParienteInstituciones;
    }

    /**
     * @return the servidorInformacionMedicaEditDelete
     */
    public ServidorInformacionMedica getServidorInformacionMedicaEditDelete() {
        return servidorInformacionMedicaEditDelete;
    }

    /**
     * @param servidorInformacionMedicaEditDelete the
     * servidorInformacionMedicaEditDelete to set
     */
    public void setServidorInformacionMedicaEditDelete(ServidorInformacionMedica servidorInformacionMedicaEditDelete) {
        this.servidorInformacionMedicaEditDelete = servidorInformacionMedicaEditDelete;
    }

    /**
     * @return the listaServidorInformacionMedicas
     */
    public List<ServidorInformacionMedica> getListaServidorInformacionMedicas() {
        return listaServidorInformacionMedicas;
    }

    /**
     * @param listaServidorInformacionMedicas the
     * listaServidorInformacionMedicas to set
     */
    public void setListaServidorInformacionMedicas(List<ServidorInformacionMedica> listaServidorInformacionMedicas) {
        this.listaServidorInformacionMedicas = listaServidorInformacionMedicas;
    }

    /**
     * @return the listaGenero
     */
    public List<SelectItem> getListaGenero() {
        return listaGenero;
    }

    /**
     * @param listaGenero the listaGenero to set
     */
    public void setListaGenero(List<SelectItem> listaGenero) {
        this.listaGenero = listaGenero;
    }

    /**
     * @return the listaEstadoCivil
     */
    public List<SelectItem> getListaEstadoCivil() {
        return listaEstadoCivil;
    }

    /**
     * @param listaEstadoCivil the listaEstadoCivil to set
     */
    public void setListaEstadoCivil(List<SelectItem> listaEstadoCivil) {
        this.listaEstadoCivil = listaEstadoCivil;
    }

    /**
     * @return the discapacidad
     */
    public Boolean getDiscapacidad() {
        return discapacidad;
    }

    /**
     * @param discapacidad the discapacidad to set
     */
    public void setDiscapacidad(Boolean discapacidad) {
        this.discapacidad = discapacidad;
    }

    /**
     * @return the servidorCargaFamiliar
     */
    public ServidorCargaFamiliar getServidorCargaFamiliar() {
        return servidorCargaFamiliar;
    }

    /**
     * @param servidorCargaFamiliar the servidorCargaFamiliar to set
     */
    public void setServidorCargaFamiliar(ServidorCargaFamiliar servidorCargaFamiliar) {
        this.servidorCargaFamiliar = servidorCargaFamiliar;
    }

    /**
     * @return the servidorCargaFamiliarEditDelete
     */
    public ServidorCargaFamiliar getServidorCargaFamiliarEditDelete() {
        return servidorCargaFamiliarEditDelete;
    }

    /**
     * @param servidorCargaFamiliarEditDelete the servidorCargaFamiliarEditDelete to set
     */
    public void setServidorCargaFamiliarEditDelete(ServidorCargaFamiliar servidorCargaFamiliarEditDelete) {
        this.servidorCargaFamiliarEditDelete = servidorCargaFamiliarEditDelete;
    }

    /**
     * @return the listaservidorCargaFamiliares
     */
    public List<ServidorCargaFamiliar> getListaservidorCargaFamiliares() {
        return listaservidorCargaFamiliares;
    }

    /**
     * @param listaservidorCargaFamiliares the listaservidorCargaFamiliares to set
     */
    public void setListaservidorCargaFamiliares(List<ServidorCargaFamiliar> listaservidorCargaFamiliares) {
        this.listaservidorCargaFamiliares = listaservidorCargaFamiliares;
    }

    /**
     * @return the ServidorEditable
     */
    public Boolean getServidorEditable() {
        return ServidorEditable;
    }

    /**
     * @param ServidorEditable the ServidorEditable to set
     */
    public void setServidorEditable(Boolean ServidorEditable) {
        this.ServidorEditable = ServidorEditable;
    }

    /**
     * @return the servidorEditDelete
     */
    public Servidor getServidorEditDelete() {
        return servidorEditDelete;
    }

    /**
     * @param servidorEditDelete the servidorEditDelete to set
     */
    public void setServidorEditDelete(Servidor servidorEditDelete) {
        this.servidorEditDelete = servidorEditDelete;
    }

    public List<CuentaBancaria> getListaCuentasBancarias() {
        return listaCuentasBancarias;
    }

    public void setListaCuentasBancarias(List<CuentaBancaria> listaCuentasBancarias) {
        this.listaCuentasBancarias = listaCuentasBancarias;
    }

    public CuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public List<SelectItem> getBancos() {
        return bancos;
    }

    public void setBancos(List<SelectItem> bancos) {
        this.bancos = bancos;
    }

    public List<SelectItem> getTiposCuenta() {
        return tiposCuenta;
    }

    public void setTiposCuenta(List<SelectItem> tiposCuenta) {
        this.tiposCuenta = tiposCuenta;
    }

    public Long getBancoSeleccionado() {
        return bancoSeleccionado;
    }

    public void setBancoSeleccionado(Long bancoSeleccionado) {
        this.bancoSeleccionado = bancoSeleccionado;
    }

    public Long getTipoCuentaSeleccionado() {
        return tipoCuentaSeleccionado;
    }

    public void setTipoCuentaSeleccionado(Long tipoCuentaSeleccionado) {
        this.tipoCuentaSeleccionado = tipoCuentaSeleccionado;
    }

    /**
     * @return the modificaDecimos
     */
    public Boolean getModificaDecimos() {
        return modificaDecimos;
    }

    /**
     * @param modificaDecimos the modificaDecimos to set
     */
    public void setModificaDecimos(Boolean modificaDecimos) {
        this.modificaDecimos = modificaDecimos;
    }

    public List<SelectItem> getEstadosDatos() {
        return estadosDatos;
    }

    public void setEstadosDatos(List<SelectItem> estadosDatos) {
        this.estadosDatos = estadosDatos;
    }
}
