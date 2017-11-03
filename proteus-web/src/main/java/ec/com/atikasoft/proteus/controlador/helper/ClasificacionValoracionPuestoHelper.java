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
import ec.com.atikasoft.proteus.modelo.distributivo.DistributivoDetalleHistorico;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import ec.com.atikasoft.proteus.vo.NominaDetalleVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "clasificacionValoracionPuestoHelper")
@SessionScoped
public class ClasificacionValoracionPuestoHelper implements Serializable {

    /**
     * Lista de puesto
     */
    private List<DistributivoDetalle> listaPuestos = new ArrayList<DistributivoDetalle>();
    
    /**
     * Distributivo Detalle seleccionado para edición
     */
    private DistributivoDetalle distributivoDetalle;

    /**
     * Lista de unidades organizacionales.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();
    
    /**
     * 
     */
    private List<UnidadPresupuestaria> listaUnidadesPresupuestarias = new ArrayList<UnidadPresupuestaria>();
    
    /**
     * 
     */
    private List<SelectItem> listaOpcionesUnidadesPresupuestarias = new ArrayList<SelectItem>();
    
    /**
     * 
     */
    private List<SelectItem> listaOpcionesEstadoAdminPuestoRegimenLaboral = new ArrayList<SelectItem>();
    
    /**
     * 
     */
    private Long idEstadoAdminPuestoRegimenLaboralSeleccionado;

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
     * Lista de opciones.
     */
    private List<EscalaOcupacional> listaEscalasOcupacionales = new ArrayList<EscalaOcupacional>();
    
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaOpcionesEscalaOcupacional = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaModalidadLaboral = new ArrayList<SelectItem>();
    
    /**
     * 
     */
    private Long idModalidadLaboralSeleccionada;

    /**
     * Lista de opciones.
     */
    private List<DenominacionPuesto> listaDenominacionPuesto = new ArrayList<DenominacionPuesto>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaOpcionesDenominacionPuesto = new ArrayList<SelectItem>();

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
     * Lista de opciones.
     */
    private List<SelectItem> listaUbicacionGeograficaRuta = new ArrayList<SelectItem>();

    /**
     * Mensaje de validacion.
     */
    private String mensajeValidaciones;

    /**
     * variable para saber si genero archivo.
     */
    private Boolean generoArchivo = Boolean.FALSE;

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
     * nombre archivo reporte de histórico.
     */
    private String nombreArchivoHistorico = "";
   
    /**
     * cabecera archivo reporte de histórico.
     */
    private final String[] cabeceraArchivoHistorico = {"Nº","Usuario Cambio","Fecha Cambio","Observación","Código Puesto",
        "Régimen Laboral","Nivel Ocupacional","Escala Ocupacional","Grado Escala Ocupacional","Modalidad Laboral",
        "Sueldo Escala","Sueldo Máximo","Sueldo","Sueldo Básico","Fecha Inicio","Fecha Fin","Fecha Ingreso",
        "Partida Individual","Unidad Organizacional","Sector","Código Unidad Presupuestaria","Unidad Presupuestaria",
        "Ubicación Geográfica","Denominación Puesto","Certificación Presupuestaria","Estado Puesto",
        "Estado Actual Puesto","Tipo Identificación","Número Identificación","Apellidos Nombres"};

    /**
     * nombre archivo reporte de nómina.
     */
    private String nombreArchivoNomina = "";
    
    /**
     * cabecera archivo reporte de nómina.
     */
    private final String[] cabeceraArchivoNomina = {"Nº","Año","Mes","Número Nómina","Tipo Nómina","Servidor"};
    
    /**
     * Especifica que cabecera de reporte tomar
     */
    private boolean historico;

    /**
     * Indica si puede selecciona la unidad organizacional en en buscador del
     * puestos.
     */
    private Boolean seleccionarUnidadOrganizacional;

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
     * 
     */
    private Boolean esNuevo;
    
    
    /**
     * 
     */
    private TreeNode root;
    
    /**
     * 
     */
    private TreeNode treeNodoSeleccionado;

    /**
     * Unidad organizaciona.
     */
    private UnidadOrganizacional uo;
    
    /**
     * Unidad organizacional superior.
     */
    private UnidadOrganizacional uoSuperior;

    /**
     * ver nodo selecciones.
     */
    private int indexNodoSeleccionado;
    
    /**
     * lista de puestos seleccionados.
     */
    private List<DistributivoDetalleHistorico> listaHistoricos;
    /**
     * lista de puestos seleccionados.
     */
    private List<NominaDetalleVO> listaNominas;
    
    /**
     * Constructor por defecto.
     */
    public ClasificacionValoracionPuestoHelper() {
        super();
    }

    /**
     * Este metodo inicia los parametros de busqueda.
     */
    @PostConstruct
    public final void iniciador() {
        busquedaPuestoVO = new BusquedaPuestoVO();
        filas = new ArrayList<String[]>();
        erroresEnArchivo = new ArrayList<String>();
        puesto = DistributivoDetalle.getDistributivoDetalleVacio();
        listaNominas = new ArrayList<>();
        listaHistoricos = new ArrayList<>();
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
     * 
     * @return 
     */
    public Long getIdModalidadLaboralSeleccionada() {
        return idModalidadLaboralSeleccionada;
    }

    /**
     * 
     * @param idModalidadLaboralSeleccionada 
     */
    public void setIdModalidadLaboralSeleccionada(Long idModalidadLaboralSeleccionada) {
        this.idModalidadLaboralSeleccionada = idModalidadLaboralSeleccionada;
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
     * 
     * @return 
     */
    public List<SelectItem> getListaUbicacionGeograficaRuta() {
        return listaUbicacionGeograficaRuta;
    }

    /**
     * 
     * @param listaUbicacionGeograficaRuta 
     */
    public void setListaUbicacionGeograficaRuta(List<SelectItem> listaUbicacionGeograficaRuta) {
        this.listaUbicacionGeograficaRuta = listaUbicacionGeograficaRuta;
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
     * 
     * @return 
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }


    /**
     * 
     * @param distributivoDetalle 
     */
    public void setDistributivoDetalle(DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
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
     * 
     * @return 
     */
    public List<UnidadOrganizacional> getListaUnidadesOrganizacionales() {
        return listaUnidadesOrganizacionales;
    }

    /**
     * 
     * @param listaUnidadesOrganizacionales 
     */
    public void setListaUnidadesOrganizacionales(List<UnidadOrganizacional> listaUnidadesOrganizacionales) {
        this.listaUnidadesOrganizacionales = listaUnidadesOrganizacionales;
    }

    /**
     * 
     * @return 
     */
    public List<UnidadPresupuestaria> getListaUnidadesPresupuestarias() {
        return listaUnidadesPresupuestarias;
    }

    /**
     * 
     * @param listaUnidadesPresupuestarias 
     */
    public void setListaUnidadesPresupuestarias(List<UnidadPresupuestaria> listaUnidadesPresupuestarias) {
        this.listaUnidadesPresupuestarias = listaUnidadesPresupuestarias;
    }

    /**
     * 
     * @return 
     */
    public List<SelectItem> getListaOpcionesUnidadesPresupuestarias() {
        return listaOpcionesUnidadesPresupuestarias;
    }

    /**
     * 
     * @param listaOpcionesUnidadesPresupuestarias 
     */
    public void setListaOpcionesUnidadesPresupuestarias(List<SelectItem> listaOpcionesUnidadesPresupuestarias) {
        this.listaOpcionesUnidadesPresupuestarias = listaOpcionesUnidadesPresupuestarias;
    }

    /**
     * 
     * @return 
     */
    public List<SelectItem> getListaOpcionesEstadoAdminPuestoRegimenLaboral() {
        return listaOpcionesEstadoAdminPuestoRegimenLaboral;
    }

    /**
     * 
     * @param listaOpcionesEstadoAdminPuestoRegimenLaboral 
     */
    public void setListaOpcionesEstadoAdminPuestoRegimenLaboral(List<SelectItem> listaOpcionesEstadoAdminPuestoRegimenLaboral) {
        this.listaOpcionesEstadoAdminPuestoRegimenLaboral = listaOpcionesEstadoAdminPuestoRegimenLaboral;
    }

    /**
     * 
     * @return 
     */
    public Long getIdEstadoAdminPuestoRegimenLaboralSeleccionado() {
        return idEstadoAdminPuestoRegimenLaboralSeleccionado;
    }

    /**
     * 
     * @param idEstadoAdminPuestoRegimenLaboralSeleccionado 
     */
    public void setIdEstadoAdminPuestoRegimenLaboralSeleccionado(Long idEstadoAdminPuestoRegimenLaboralSeleccionado) {
        this.idEstadoAdminPuestoRegimenLaboralSeleccionado = idEstadoAdminPuestoRegimenLaboralSeleccionado;
    }

    /**
     * 
     * @return 
     */
    public List<EscalaOcupacional> getListaEscalasOcupacionales() {
        return listaEscalasOcupacionales;
    }

    /**
     * 
     * @param listaEscalasOcupacionales 
     */
    public void setListaEscalasOcupacionales(List<EscalaOcupacional> listaEscalasOcupacionales) {
        this.listaEscalasOcupacionales = listaEscalasOcupacionales;
    }

    /**
     * @return the listaOpcionesEscalaOcupacional
     */
    public List<SelectItem> getListaOpcionesEscalaOcupacional() {
        return listaOpcionesEscalaOcupacional;
    }

    /**
     * @param listaOpcionesEscalaOcupacional the listaOpcionesEscalaOcupacional to set
     */
    public void setListaOpcionesEscalaOcupacional(final List<SelectItem> listaOpcionesEscalaOcupacional) {
        this.listaOpcionesEscalaOcupacional = listaOpcionesEscalaOcupacional;
    }

    /**
     * @return the listaOpcionesDenominacionPuesto
     */
    public List<SelectItem> getListaOpcionesDenominacionPuesto() {
        return listaOpcionesDenominacionPuesto;
    }

    /**
     * @param listaOpcionesDenominacionPuesto the listaOpcionesDenominacionPuesto to set
     */
    public void setListaOpcionesDenominacionPuesto(final List<SelectItem> listaOpcionesDenominacionPuesto) {
        this.listaOpcionesDenominacionPuesto = listaOpcionesDenominacionPuesto;
    }

    /**
     * 
     * @return 
     */
    public List<DenominacionPuesto> getListaDenominacionPuesto() {
        return listaDenominacionPuesto;
    }

    /**
     * 
     * @param listaDenominacionPuesto 
     */
    public void setListaDenominacionPuesto(List<DenominacionPuesto> listaDenominacionPuesto) {
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
     * 
     * @return 
     */
    public Long getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * 
     * @param totalRegistros 
     */
    public void setTotalRegistros(Long totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    /**
     * 
     * @return 
     */
    public DistributivoDetalle getPuesto() {
        return puesto;
    }
    
    /**
     * 
     * @param puesto 
     */
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

    public Boolean getEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(Boolean esNuevo) {
        this.esNuevo = esNuevo;
    }
    
    /**
     * VERIFICA SI EL PUESTO ESTA VACANTE
     * @return 
     */
    public Boolean esPuestoVacante () {
        return this.distributivoDetalle.getIdServidor() == null;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getTreeNodoSeleccionado() {
        return treeNodoSeleccionado;
    }

    public void setTreeNodoSeleccionado(TreeNode treeNodoSeleccionado) {
        this.treeNodoSeleccionado = treeNodoSeleccionado;
    }

    public UnidadOrganizacional getUo() {
        return uo;
    }

    public void setUo(UnidadOrganizacional uo) {
        this.uo = uo;
    }

    public UnidadOrganizacional getUoSuperior() {
        return uoSuperior;
    }

    public void setUoSuperior(UnidadOrganizacional uoSuperior) {
        this.uoSuperior = uoSuperior;
    }

    public int getIndexNodoSeleccionado() {
        return indexNodoSeleccionado;
    }

    public void setIndexNodoSeleccionado(int indexNodoSeleccionado) {
        this.indexNodoSeleccionado = indexNodoSeleccionado;
    }

    public List<DistributivoDetalleHistorico> getListaHistoricos() {
        return listaHistoricos;
    }

    public void setListaHistoricos(List<DistributivoDetalleHistorico> listaHistoricos) {
        this.listaHistoricos = listaHistoricos;
    }

    public List<NominaDetalleVO> getListaNominas() {
        return listaNominas;
    }

    public void setListaNominas(List<NominaDetalleVO> listaNominas) {
        this.listaNominas = listaNominas;
    }

    public String getNombreArchivoHistorico() {
        return nombreArchivoHistorico;
    }

    public void setNombreArchivoHistorico(String nombreArchivoHistorico) {
        this.nombreArchivoHistorico = nombreArchivoHistorico;
    }

    public String getNombreArchivoNomina() {
        return nombreArchivoNomina;
    }

    public void setNombreArchivoNomina(String nombreArchivoNomina) {
        this.nombreArchivoNomina = nombreArchivoNomina;
    }

    public String[] getCabeceraArchivoHistorico() {
        return cabeceraArchivoHistorico;
    }

    public String[] getCabeceraArchivoNomina() {
        return cabeceraArchivoNomina;
    }

    public boolean isHistorico() {
        return historico;
    }

    public void setHistorico(boolean esHistorico) {
        this.historico = esHistorico;
    }

    
}
