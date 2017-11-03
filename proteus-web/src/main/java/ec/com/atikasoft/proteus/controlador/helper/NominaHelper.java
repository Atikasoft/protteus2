/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.ArchivoSipari;
import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.NominaDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.nomina.NominaEjecucion;
import ec.com.atikasoft.proteus.modelo.nomina.NominaProblema;
import ec.com.atikasoft.proteus.vo.BusquedaNominaVO;
import ec.com.atikasoft.proteus.vo.FiltroNominaVO;
import ec.com.atikasoft.proteus.vo.NominaVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@SessionScoped
@ManagedBean(name = "nominaHelper")
public class NominaHelper implements Serializable {

    /**
     * VO de filtro.
     */
    private FiltroNominaVO filtro = new FiltroNominaVO();

    /**
     * Objeto de tipo nomina.
     */
    private Nomina nomina;

    /**
     * Vo de nomima.
     */
    private NominaVO datosNomina = new NominaVO();

    /**
     * Objeto de tipo Detalle Nomina.
     */
    private NominaDetalle nominaDetalle;

    /**
     * Lista de Nominas.
     */
    private List<Nomina> listaNominas = new ArrayList<Nomina>();

    /**
     * Variable de tipo Boolean.
     */
    private Boolean esNuevo;

    /**
     * Indica la accion a seguir.
     */
    private String accion;

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaPeriodoFiscal = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaPeriodoNomina = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaTipoNomina = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaRegimenLaboral = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaNivelOcupacional = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaModalidadLaboral = new ArrayList<SelectItem>();

    /**
     * Lista de acciones a seguir.
     */
    private List<SelectItem> listaAcciones = new ArrayList<SelectItem>();

    /**
     * Lista de servidores a agregar.
     */
    private List<Servidor> listaServidores = new ArrayList<Servidor>();

    /**
     * Lista de observaciones de la nomina.
     */
    private List<NominaProblema> listaProblemas = new ArrayList<NominaProblema>();

    /**
     * Lista de ejecuciones de la nomina.
     */
    private List<NominaEjecucion> listaEjecuciones = new ArrayList<NominaEjecucion>();

    /**
     * Variables de iteracción de botones de acuerdo al estado de la nomina.
     */
    private Boolean calcular = Boolean.FALSE;
    private Boolean nominaAprobada = Boolean.FALSE;
    private Boolean nominaAnulada = Boolean.FALSE;

    /**
     * Lista de opciones para crear y dscargar archivos sipari.
     */
    private List<ArchivoSipari> listaTipoArchivoSIPARI = new ArrayList<ArchivoSipari>();
    /**
     *
     */
    private ArchivoSipari archivoSipari = new ArchivoSipari();

    /**
     *
     */
    private List<ArchivoSipari> archivosSeleccionados = new ArrayList<ArchivoSipari>();
    /**
     * Variable que permite almacenar los mensajes de errores producidos.
     */
    private List<String> listaMensajes = new ArrayList<String>();
    /**
     * BusquedaNominaVO.
     */
    private BusquedaNominaVO busquedaNominaVO = new BusquedaNominaVO();
    /**
     * presentar el regresar cuando sea una consulta.
     */
//    private Boolean presentarRegresar;
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaTipoNominaEnum = new ArrayList<SelectItem>();
    /**
     * codigo de la nomina enum.
     */
    private String nominaEnumCodigo;

    /**
     * listaEstadoNomina.
     */
    private List<SelectItem> listaEstadoNomina = new ArrayList<SelectItem>();
    /**
     * Indica si puede selecciona la unidad organizacional al buscar los
     * movimientos de ese unidad organizacional.
     */
    private Boolean seleccionarUnidadOrganizacional;
    /**
     * Lista de unidades organizacionales.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();

    /**
     * Indica si la nomina se encuentra calculandose.
     */
    private Boolean calculando;
    /**
     * Lista de opciones para crear y dscargar archivos SPI.
     */
    private List<ArchivoSipari> listaTipoArchivoSPI = new ArrayList<ArchivoSipari>();
    
    /**
     * 
     */
    private String modo;
    
    /**
     * 
     */
    private String claveCalculo;
    
    /**
     * 
     */
    private String claveDesbloqueo;
    
    

    /**
     * @return the nomina
     */
    public Nomina getNomina() {
        return nomina;
    }

    /**
     * @param nomina the nomina to set
     */
    public void setNomina(final Nomina nomina) {
        this.nomina = nomina;
    }

    /**
     * @return the nominaDetalle
     */
    public NominaDetalle getNominaDetalle() {
        return nominaDetalle;
    }

    /**
     * @param nominaDetalle the nominaDetalle to set
     */
    public void setNominaDetalle(final NominaDetalle nominaDetalle) {
        this.nominaDetalle = nominaDetalle;
    }

    /**
     * @return the listaNominas
     */
    public List<Nomina> getListaNominas() {
        return listaNominas;
    }

    /**
     * @param listaNominas the listaNominas to set
     */
    public void setListaNominas(final List<Nomina> listaNominas) {
        this.listaNominas = listaNominas;
    }

    /**
     * @return the esNuevo
     */
    public Boolean getEsNuevo() {
        return esNuevo;
    }

    /**
     * @param esNuevo the esNuevo to set
     */
    public void setEsNuevo(final Boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    /**
     * @return the listaPeriodoFiscal
     */
    public List<SelectItem> getListaPeriodoFiscal() {
        return listaPeriodoFiscal;
    }

    /**
     * @param listaPeriodoFiscal the listaPeriodoFiscal to set
     */
    public void setListaPeriodoFiscal(final List<SelectItem> listaPeriodoFiscal) {
        this.listaPeriodoFiscal = listaPeriodoFiscal;
    }

    /**
     * @return the listaPeriodoNomina
     */
    public List<SelectItem> getListaPeriodoNomina() {
        return listaPeriodoNomina;
    }

    /**
     * @param listaPeriodoNomina the listaPeriodoNomina to set
     */
    public void setListaPeriodoNomina(final List<SelectItem> listaPeriodoNomina) {
        this.listaPeriodoNomina = listaPeriodoNomina;
    }

    /**
     * @return the listaTipoNomina
     */
    public List<SelectItem> getListaTipoNomina() {
        return listaTipoNomina;
    }

    /**
     * @param listaTipoNomina the listaTipoNomina to set
     */
    public void setListaTipoNomina(final List<SelectItem> listaTipoNomina) {
        this.listaTipoNomina = listaTipoNomina;
    }

    /**
     * @return the filtro
     */
    public FiltroNominaVO getFiltro() {
        return filtro;
    }

    /**
     * @param filtro the filtro to set
     */
    public void setFiltro(final FiltroNominaVO filtro) {
        this.filtro = filtro;
    }

    /**
     * @return the datosNomina
     */
    public NominaVO getDatosNomina() {
        return datosNomina;
    }

    /**
     * @param datosNomina the datosNomina to set
     */
    public void setDatosNomina(final NominaVO datosNomina) {
        this.datosNomina = datosNomina;
    }

    /**
     * @return the listaRegimenLaboral
     */
    public List<SelectItem> getListaRegimenLaboral() {
        return listaRegimenLaboral;
    }

    /**
     * @param listaRegimenLaboral the listaRegimenLaboral to set
     */
    public void setListaRegimenLaboral(final List<SelectItem> listaRegimenLaboral) {
        this.listaRegimenLaboral = listaRegimenLaboral;
    }

    /**
     * @return the listaNivelOcupacional
     */
    public List<SelectItem> getListaNivelOcupacional() {
        return listaNivelOcupacional;
    }

    /**
     * @param listaNivelOcupacional the listaNivelOcupacional to set
     */
    public void setListaNivelOcupacional(final List<SelectItem> listaNivelOcupacional) {
        this.listaNivelOcupacional = listaNivelOcupacional;
    }

    /**
     * @return the listaModalidadLaboral
     */
    public List<SelectItem> getListaModalidadLaboral() {
        return listaModalidadLaboral;
    }

    /**
     * @param listaModalidadLaboral the listaModalidadLaboral to set
     */
    public void setListaModalidadLaboral(final List<SelectItem> listaModalidadLaboral) {
        this.listaModalidadLaboral = listaModalidadLaboral;
    }

    /**
     * @return the listaServidores
     */
    public List<Servidor> getListaServidores() {
        if(listaServidores==null){
            listaServidores = new ArrayList<Servidor>();
        }
        return listaServidores;
    }

    /**
     * @param listaServidores the listaServidores to set
     */
    public void setListaServidores(final List<Servidor> listaServidores) {
        this.listaServidores = listaServidores;
    }

    /**
     * @return the listaAcciones
     */
    public List<SelectItem> getListaAcciones() {
        return listaAcciones;
    }

    /**
     * @param listaAcciones the listaAcciones to set
     */
    public void setListaAcciones(final List<SelectItem> listaAcciones) {
        this.listaAcciones = listaAcciones;
    }

    /**
     * @return the accion
     */
    public String getAccion() {
        return accion;
    }

    /**
     * @param accion the accion to set
     */
    public void setAccion(final String accion) {
        this.accion = accion;
    }

    /**
     * @return the listaProblemas
     */
    public List<NominaProblema> getListaProblemas() {
        return listaProblemas;
    }

    /**
     * @param listaProblemas the listaProblemas to set
     */
    public void setListaProblemas(final List<NominaProblema> listaProblemas) {
        this.listaProblemas = listaProblemas;
    }

    /**
     * @return the calcular
     */
    public Boolean getCalcular() {
        return calcular;
    }

    /**
     * @param calcular the calcular to set
     */
    public void setCalcular(final Boolean calcular) {
        this.calcular = calcular;
    }

    /**
     * @return the nominaAprobada
     */
    public Boolean getNominaAprobada() {
        return nominaAprobada;
    }

    /**
     * @param nominaAprobada the nominaAprobada to set
     */
    public void setNominaAprobada(final Boolean nominaAprobada) {
        this.nominaAprobada = nominaAprobada;
    }

    /**
     * @return the listaTipoArchivoSIPARI
     */
    public List<ArchivoSipari> getListaTipoArchivoSIPARI() {
        return listaTipoArchivoSIPARI;
    }

    /**
     * @param listaTipoArchivoSIPARI the listaTipoArchivoSIPARI to set
     */
    public void setListaTipoArchivoSIPARI(final List<ArchivoSipari> listaTipoArchivoSIPARI) {
        this.listaTipoArchivoSIPARI = listaTipoArchivoSIPARI;
    }

    /**
     * @return the archivoSipari
     */
    public ArchivoSipari getArchivoSipari() {
        return archivoSipari;
    }

    /**
     * @param archivoSipari the archivoSipari to set
     */
    public void setArchivoSipari(final ArchivoSipari archivoSipari) {
        this.archivoSipari = archivoSipari;
    }

    /**
     * @return the archivosSeleccionados
     */
    public List<ArchivoSipari> getArchivosSeleccionados() {
        return archivosSeleccionados;
    }

    /**
     * @param archivosSeleccionados the archivosSeleccionados to set
     */
    public void setArchivosSeleccionados(final List<ArchivoSipari> archivosSeleccionados) {
        this.archivosSeleccionados = archivosSeleccionados;
    }

    /**
     * @return the listaMensajes
     */
    public List<String> getListaMensajes() {
        return listaMensajes;
    }

    /**
     * @param listaMensajes the listaMensajes to set
     */
    public void setListaMensajes(final List<String> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }

    /**
     * @return the busquedaNominaVO
     */
    public BusquedaNominaVO getBusquedaNominaVO() {
        return busquedaNominaVO;
    }

    /**
     * @param busquedaNominaVO the busquedaNominaVO to set
     */
    public void setBusquedaNominaVO(BusquedaNominaVO busquedaNominaVO) {
        this.busquedaNominaVO = busquedaNominaVO;
    }

    /**
     * @return the nominaAnulada
     */
    public Boolean getNominaAnulada() {
        return nominaAnulada;
    }

    /**
     * @param nominaAnulada the nominaAnulada to set
     */
    public void setNominaAnulada(Boolean nominaAnulada) {
        this.nominaAnulada = nominaAnulada;
    }

    /**
     * @return the presentarRegresar
     */
//    public Boolean getPresentarRegresar() {
//        return presentarRegresar;
//    }
//
//    /**
//     * @param presentarRegresar the presentarRegresar to set
//     */
//    public void setPresentarRegresar(Boolean presentarRegresar) {
//        this.presentarRegresar = presentarRegresar;
//    }
    /**
     * @return the listaTipoNominaEnum
     */
    public List<SelectItem> getListaTipoNominaEnum() {
        return listaTipoNominaEnum;
    }

    /**
     * @param listaTipoNominaEnum the listaTipoNominaEnum to set
     */
    public void setListaTipoNominaEnum(List<SelectItem> listaTipoNominaEnum) {
        this.listaTipoNominaEnum = listaTipoNominaEnum;
    }

    /**
     * @return the nominaEnumCodigo
     */
    public String getNominaEnumCodigo() {
        return nominaEnumCodigo;
    }

    /**
     * @param nominaEnumCodigo the nominaEnumCodigo to set
     */
    public void setNominaEnumCodigo(final String nominaEnumCodigo) {
        this.nominaEnumCodigo = nominaEnumCodigo;
    }

    /**
     * @return the listaEstadoNomina
     */
    public List<SelectItem> getListaEstadoNomina() {
        return listaEstadoNomina;
    }

    /**
     * @param listaEstadoNomina the listaEstadoNomina to set
     */
    public void setListaEstadoNomina(final List<SelectItem> listaEstadoNomina) {
        this.listaEstadoNomina = listaEstadoNomina;
    }

    /**
     * @return the seleccionarUnidadOrganizacional
     */
    public Boolean getSeleccionarUnidadOrganizacional() {
        return seleccionarUnidadOrganizacional;
    }

    /**
     * @param seleccionarUnidadOrganizacional the
     * seleccionarUnidadOrganizacional to set
     */
    public void setSeleccionarUnidadOrganizacional(final Boolean seleccionarUnidadOrganizacional) {
        this.seleccionarUnidadOrganizacional = seleccionarUnidadOrganizacional;
    }

    /**
     * @return the listaUnidadesOrganizacionales
     */
    public List<UnidadOrganizacional> getListaUnidadesOrganizacionales() {
        return listaUnidadesOrganizacionales;
    }

    /**
     * @param listaUnidadesOrganizacionales the listaUnidadesOrganizacionales to
     * set
     */
    public void setListaUnidadesOrganizacionales(final List<UnidadOrganizacional> listaUnidadesOrganizacionales) {
        this.listaUnidadesOrganizacionales = listaUnidadesOrganizacionales;
    }

    /**
     * @return the calculando
     */
    public Boolean getCalculando() {
        return calculando;
    }

    /**
     * @param calculando the calculando to set
     */
    public void setCalculando(Boolean calculando) {
        this.calculando = calculando;
    }

    /**
     * @return the listaEjecuciones
     */
    public List<NominaEjecucion> getListaEjecuciones() {
        return listaEjecuciones;
    }

    /**
     * @param listaEjecuciones the listaEjecuciones to set
     */
    public void setListaEjecuciones(List<NominaEjecucion> listaEjecuciones) {
        this.listaEjecuciones = listaEjecuciones;
    }

    /**
     * @return the listaTipoArchivoSPI
     */
    public List<ArchivoSipari> getListaTipoArchivoSPI() {
        return listaTipoArchivoSPI;
    }

    /**
     * @param listaTipoArchivoSPI the listaTipoArchivoSPI to set
     */
    public void setListaTipoArchivoSPI(List<ArchivoSipari> listaTipoArchivoSPI) {
        this.listaTipoArchivoSPI = listaTipoArchivoSPI;
    }

    /**
     * @return the modo
     */
    public String getModo() {
        return modo;
    }

    /**
     * @param modo the modo to set
     */
    public void setModo(String modo) {
        this.modo = modo;
    }

    /**
     * @return the claveCalculo
     */
    public String getClaveCalculo() {
        return claveCalculo;
    }

    /**
     * @param claveCalculo the claveCalculo to set
     */
    public void setClaveCalculo(String claveCalculo) {
        this.claveCalculo = claveCalculo;
    }

    /**
     * @return the claveDesbloqueo
     */
    public String getClaveDesbloqueo() {
        return claveDesbloqueo;
    }

    /**
     * @param claveDesbloqueo the claveDesbloqueo to set
     */
    public void setClaveDesbloqueo(String claveDesbloqueo) {
        this.claveDesbloqueo = claveDesbloqueo;
    }
}
