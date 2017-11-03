/*
 *  BusquedaPuestoHelper.java
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
 *  22/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import ec.com.atikasoft.proteus.vo.Valor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.StreamedContent;

/**
 * BusquedaPuestoHelper.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "busquedaPuestoHelper")
@SessionScoped
public class BusquedaPuestoHelper implements Serializable {

    /**
     * Lista de puesto
     */
    private List<DistributivoDetalle> listaPuestos = new ArrayList<DistributivoDetalle>();

    /**
     * Lista de unidades organizacionales.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();
    
    /**
     * Lista opciones de unidades organizacionales.
     */
    private List<SelectItem> listaOpcionesUnidadesOrganizacionales = new ArrayList<SelectItem>();

    /**
     * Variable para asignar valores de busqueda.
     */
    private BusquedaPuestoVO busquedaPuestoVO = new BusquedaPuestoVO();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaRegimenLaboral = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaNivelOcupacional = new ArrayList<SelectItem>();
    /**
     * Lista de niveles ocupacionales.
     */
    private List<NivelOcupacional> nivelesOcupacionales = new ArrayList<>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaEscalaOcupacional = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaModalidadLaboral = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaDenominacionPuesto = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaTipoDocumento = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaEstadoPuesto = new ArrayList<SelectItem>();
    
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaEstadoAdminPuestoRegimenLaboral = new ArrayList<SelectItem>();
    
    /**
     * Lista de opciones. LA LISTA ANTERIOR FILTRAD POR REGIMEN LABORAL
     */
    private List<SelectItem> listaFiltradaEstadoAdminPuestoRegimenLaboral = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaEstadoPersonal = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaPais = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaProvincia = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaCanton = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaParroquia = new ArrayList<SelectItem>();

    /**
     * Mensaje de validacion.
     */
    private String mensajeValidaciones;

    /**
     * variable para saber si genero archivo.
     */
    private Boolean generoArchivo = Boolean.FALSE;

    /**
     * variable para continuar guardadndo.
     */
    private Boolean continuar = Boolean.FALSE;

    /**
     * lista de peustos seleccionados.
     */
    private List<DistributivoDetalle> listaSelecto = new ArrayList<DistributivoDetalle>();

    /**
     * lista de peustos seleccionados.
     */
    private Map<Integer, DistributivoDetalle> seleccionado = new HashMap<Integer, DistributivoDetalle>();
    
    /**
     * lista de ids de modalidades para carga masiva.
     */
    private Map<Integer, Long> idsModalidadesMap = new HashMap<Integer, Long>();
    
    /**
     * lista de puestos para carga masiva.
     */
    private Map<Integer, DistributivoDetalle> distributivosDetalleMasivoMap = new HashMap<Integer, DistributivoDetalle>();

    /**
     * Archivo.
     */
    private StreamedContent archivoDescarga;

    /**
     * Indica si el panel de parametros esta activo.
     */
    private String activo = "0";

    /**
     * nombre archivo.
     */
    private String nombreArchivo = "";

    /**
     * Indica si puede selecciona la unidad organizacional en en buscador del
     * puestos.
     */
    private Boolean seleccionarUnidadOrganizacional;

    /**
     *
     */
    private List<Valor> unidadesOrganizacionalPermitidas;

    /**
     *
     */
    private List<Valor> unidadesOrganizacionalPermitidasFiltradas;

    /**
     *
     */
    private Long totalRegistros;

    /**
     *
     */
    private DistributivoDetalle puesto;

    /**
     * Nombre archivo de carga masiva para cambio de modalidad
     */
    private String nombreArchivoCSV;
    
    /**
     * Contenido ArchivoCSV
     */
    List<String[]> filas;

    /**
     * Lista de errores en la carga de archivo CSV.
     */
    private List<String> erroresEnArchivo;
    
    /**
     * Resultado validación de archivo CSV.
     */
    private Boolean archivoSinError;

    /**
     * Constructor por defecto.
     */
    public BusquedaPuestoHelper() {
        super();
    }

    /**
     * Este metodo inicia los parametros de busqueda.
     */
    public final void iniciador() {
        busquedaPuestoVO = new BusquedaPuestoVO();
        unidadesOrganizacionalPermitidasFiltradas = new ArrayList<Valor>();
        filas = new ArrayList<String[]>();
        erroresEnArchivo = new ArrayList<String>();
        puesto = DistributivoDetalle.getDistributivoDetalleVacio();

    }

    /**
     * @return the busquedaPuestoVO
     */
    public BusquedaPuestoVO getBusquedaPuestoVO() {
        return busquedaPuestoVO;
    }

    /**
     * @param busquedaPuestoVO the busquedaPuestoVO to set
     */
    public void setBusquedaPuestoVO(final BusquedaPuestoVO busquedaPuestoVO) {
        this.busquedaPuestoVO = busquedaPuestoVO;
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
     * @return the listaTipoDocumento
     */
    public List<SelectItem> getListaTipoDocumento() {
        return listaTipoDocumento;
    }

    /**
     * @param listaTipoDocumento the listaTipoDocumento to set
     */
    public void setListaTipoDocumento(final List<SelectItem> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    /**
     * @return the listaEstadoPuesto
     */
    public List<SelectItem> getListaEstadoPuesto() {
        return listaEstadoPuesto;
    }

    /**
     * @param listaEstadoPuesto the listaEstadoPuesto to set
     */
    public void setListaEstadoPuesto(final List<SelectItem> listaEstadoPuesto) {
        this.listaEstadoPuesto = listaEstadoPuesto;
    }

    /**
     * 
     * @return 
     */
    public List<SelectItem> getListaEstadoAdminPuestoRegimenLaboral() {
        return listaEstadoAdminPuestoRegimenLaboral;
    }

    /**
     * 
     * @param listaEstadoAdminPuestoRegimenLaboral 
     */
    public void setListaEstadoAdminPuestoRegimenLaboral(List<SelectItem> listaEstadoAdminPuestoRegimenLaboral) {
        this.listaEstadoAdminPuestoRegimenLaboral = listaEstadoAdminPuestoRegimenLaboral;
    }

    /**
     * 
     * @return 
     */
    public List<SelectItem> getListaFiltradaEstadoAdminPuestoRegimenLaboral() {
        return listaFiltradaEstadoAdminPuestoRegimenLaboral;
    }

    /**
     * 
     * @param listaFiltradaEstadoAdminPuestoRegimenLaboral 
     */
    public void setListaFiltradaEstadoAdminPuestoRegimenLaboral(
            List<SelectItem> listaFiltradaEstadoAdminPuestoRegimenLaboral) {
        this.listaFiltradaEstadoAdminPuestoRegimenLaboral = listaFiltradaEstadoAdminPuestoRegimenLaboral;
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
     * @return the listaCanton
     */
    public List<SelectItem> getListaCanton() {
        return listaCanton;
    }

    /**
     * @param listaCanton the listaCanton to set
     */
    public void setListaCanton(final List<SelectItem> listaCanton) {
        this.listaCanton = listaCanton;
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
     * @return the puestoInstitucion
     */
//    public PuestoInstitucionDTO getPuestoInstitucion() {
//        return puestoInstitucion;
//    }
    /**
     * @param puestoInstitucion the puestoInstitucion to set
     */
//    public void setPuestoInstitucion(final PuestoInstitucionDTO puestoInstitucion) {
//        this.puestoInstitucion = puestoInstitucion;
//    }
    /**
     * @return the parametrosBusqueda
     */
//    public SrhvOrganicoPosicionalIndividual getParametrosBusqueda() {
//        return parametrosBusqueda;
//    }
    /**
     * @param parametrosBusqueda the parametrosBusqueda to set
     */
//    public void setParametrosBusqueda(final SrhvOrganicoPosicionalIndividual parametrosBusqueda) {
//        this.parametrosBusqueda = parametrosBusqueda;
//    }
    /**
     * @return the escalaLaboral
     */
//    public EscalaLaboral getEscalaLaboral() {
//        return escalaLaboral;
//    }
    /**
     * @param escalaLaboral the escalaLaboral to set
     */
//    public void setEscalaLaboral(final EscalaLaboral escalaLaboral) {
//        this.escalaLaboral = escalaLaboral;
//    }
    /**
     * @return the listaPuestos
     */
//    public List<SrhvOrganicoPosicionalIndividual> getListaPuestos() {
//        return listaPuestos;
//    }
    /**
     * @param listaPuestos the listaPuestos to set
     */
//    public void setListaPuestos(final List<SrhvOrganicoPosicionalIndividual> listaPuestos) {
//        this.listaPuestos = listaPuestos;
//    }
    /**
     * @return the listaUnidadAdministrativa
     */
//    public List<EstructuraOrganizacional> getListaUnidadAdministrativa() {
//        return listaUnidadAdministrativa;
//    }
    /**
     * @param listaUnidadAdministrativa the listaUnidadAdministrativa to set
     */
//    public void setListaUnidadAdministrativa(final List<EstructuraOrganizacional> listaUnidadAdministrativa) {
//        this.listaUnidadAdministrativa = listaUnidadAdministrativa;
//    }
    /**
     * @return the listaEstadoPersonal
     */
    public List<SelectItem> getListaEstadoPersonal() {
        return listaEstadoPersonal;
    }

    /**
     * @param listaEstadoPersonal the listaEstadoPersonal to set
     */
    public void setListaEstadoPersonal(final List<SelectItem> listaEstadoPersonal) {
        this.listaEstadoPersonal = listaEstadoPersonal;
    }

    /**
     * @return the mensajeValidaciones
     */
    public String getMensajeValidaciones() {
        return mensajeValidaciones;
    }

    /**
     * @param mensajeValidaciones the mensajeValidaciones to set
     */
    public void setMensajeValidaciones(final String mensajeValidaciones) {
        this.mensajeValidaciones = mensajeValidaciones;
    }

    /**
     * @return the generoArchivo
     */
    public Boolean getGeneroArchivo() {
        return generoArchivo;
    }

    /**
     * @param generoArchivo the generoArchivo to set
     */
    public void setGeneroArchivo(final Boolean generoArchivo) {
        this.generoArchivo = generoArchivo;
    }

    /**
     * @return the continuar
     */
    public Boolean getContinuar() {
        return continuar;
    }

    /**
     * @param continuar the continuar to set
     */
    public void setContinuar(final Boolean continuar) {
        this.continuar = continuar;
    }

    /**
     * @return the listaSelecto
     */
//    public List<SrhvOrganicoPosicionalIndividual> getListaSelecto() {
//        return listaSelecto;
//    }
    /**
     * @param listaSelecto the listaSelecto to set
     */
//    public void setListaSelecto(final List<SrhvOrganicoPosicionalIndividual> listaSelecto) {
//        this.listaSelecto = listaSelecto;
//    }
    /**
     * @return the seleccionado
     */
//    public Map<Integer, SrhvOrganicoPosicionalIndividual> getSeleccionado() {
//        return seleccionado;
//    }
    /**
     * @param seleccionado the seleccionado to set
     */
//    public void setSeleccionado(final Map<Integer, SrhvOrganicoPosicionalIndividual> seleccionado) {
//        this.seleccionado = seleccionado;
//    }
    /**
     * @return the archivoDescarga
     */
    public StreamedContent getArchivoDescarga() {
        return archivoDescarga;
    }

    /**
     * @param archivoDescarga the archivoDescarga to set
     */
    public void setArchivoDescarga(final StreamedContent archivoDescarga) {
        this.archivoDescarga = archivoDescarga;
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
    public void setNombreArchivo(final String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the listaPuestos
     */
    public List<DistributivoDetalle> getListaPuestos() {
        return listaPuestos;
    }

    /**
     * @return the listaSelecto
     */
    public List<DistributivoDetalle> getListaSelecto() {
        return listaSelecto;
    }

    /**
     * @return the seleccionado
     */
    public Map<Integer, DistributivoDetalle> getSeleccionado() {
        return seleccionado;
    }

    /**
     * @param listaPuestos the listaPuestos to set
     */
    public void setListaPuestos(final List<DistributivoDetalle> listaPuestos) {
        this.listaPuestos = listaPuestos;
    }

    /**
     * @param listaSelecto the listaSelecto to set
     */
    public void setListaSelecto(final List<DistributivoDetalle> listaSelecto) {
        this.listaSelecto = listaSelecto;
    }

    /**
     * @param seleccionado the seleccionado to set
     */
    public void setSeleccionado(final Map<Integer, DistributivoDetalle> seleccionado) {
        this.seleccionado = seleccionado;
    }

    /**
     * @return the listaEscalaOcupacional
     */
    public List<SelectItem> getListaEscalaOcupacional() {
        return listaEscalaOcupacional;
    }

    /**
     * @param listaEscalaOcupacional the listaEscalaOcupacional to set
     */
    public void setListaEscalaOcupacional(final List<SelectItem> listaEscalaOcupacional) {
        this.listaEscalaOcupacional = listaEscalaOcupacional;
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
     * 
     * @return 
     */
    public List<SelectItem> getListaOpcionesUnidadesOrganizacionales() {
        return listaOpcionesUnidadesOrganizacionales;
    }

    /**
     * 
     * @param listaOpcionesUnidadesOrganizacionales 
     */
    public void setListaOpcionesUnidadesOrganizacionales(
            List<SelectItem> listaOpcionesUnidadesOrganizacionales) {
        this.listaOpcionesUnidadesOrganizacionales = listaOpcionesUnidadesOrganizacionales;
    }

    /**
     * @return the listaDenominacionPuesto
     */
    public List<SelectItem> getListaDenominacionPuesto() {
        return listaDenominacionPuesto;
    }

    /**
     * @param listaDenominacionPuesto the listaDenominacionPuesto to set
     */
    public void setListaDenominacionPuesto(final List<SelectItem> listaDenominacionPuesto) {
        this.listaDenominacionPuesto = listaDenominacionPuesto;
    }

    /**
     * @return the activo
     */
    public String getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(final String activo) {
        this.activo = activo;
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
     * @return the unidadesOrganizacionalPermitidas
     */
    public List<Valor> getUnidadesOrganizacionalPermitidas() {
        return unidadesOrganizacionalPermitidas;
    }

    /**
     * @param unidadesOrganizacionalPermitidas the
     * unidadesOrganizacionalPermitidas to set
     */
    public void setUnidadesOrganizacionalPermitidas(List<Valor> unidadesOrganizacionalPermitidas) {
        this.unidadesOrganizacionalPermitidas = unidadesOrganizacionalPermitidas;
    }

    public Long getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Long totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public List<Valor> getUnidadesOrganizacionalPermitidasFiltradas() {
        return unidadesOrganizacionalPermitidasFiltradas;
    }

    public void setUnidadesOrganizacionalPermitidasFiltradas(List<Valor> unidadesOrganizacionalPermitidasFiltradas) {
        this.unidadesOrganizacionalPermitidasFiltradas = unidadesOrganizacionalPermitidasFiltradas;
    }

    public DistributivoDetalle getPuesto() {
        return puesto;
    }

    public void setPuesto(DistributivoDetalle puesto) {
        this.puesto = puesto;
    }

    public List<String[]> getFilas() {
        return filas;
    }

    public void setFilas(List<String[]> filas) {
        this.filas = filas;
    }

    public List<String> getErroresEnArchivo() {
        return erroresEnArchivo;
    }

    public void setErroresEnArchivo(List<String> erroresEnArchivo) {
        this.erroresEnArchivo = erroresEnArchivo;
    }

    public Map<Integer, Long> getIdsModalidadesMap() {
        return idsModalidadesMap;
    }

    public void setIdsModalidadesMap(Map<Integer, Long> idsModalidadesMap) {
        this.idsModalidadesMap = idsModalidadesMap;
    }

    public Map<Integer, DistributivoDetalle> getDistributivosDetalleMasivoMap() {
        return distributivosDetalleMasivoMap;
    }

    public void setDistributivosDetalleMasivoMap(Map<Integer, DistributivoDetalle> distributivosDetalleMasivoMap) {
        this.distributivosDetalleMasivoMap = distributivosDetalleMasivoMap;
    }

    public Boolean getArchivoSinError() {
        return archivoSinError;
    }

    public void setArchivoSinError(Boolean archivoSinError) {
        this.archivoSinError = archivoSinError;
    }

    public String getNombreArchivoCSV() {
        return nombreArchivoCSV;
    }

    public void setNombreArchivoCSV(String nombreArchivoCSV) {
        this.nombreArchivoCSV = nombreArchivoCSV;
    }

    public List<NivelOcupacional> getNivelesOcupacionales() {
        return nivelesOcupacionales;
    }

    public void setNivelesOcupacionales(List<NivelOcupacional> nivelesOcupacionales) {
        this.nivelesOcupacionales = nivelesOcupacionales;
    }
    
    

}
