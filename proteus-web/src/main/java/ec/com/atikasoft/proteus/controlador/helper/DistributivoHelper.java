/*
 *  DistributivoHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  11/11/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.DenominacionPuesto;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.EscalaOcupacional;
import ec.com.atikasoft.proteus.modelo.EstadoPuesto;
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.modelo.UbicacionGeografica;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.UnidadPresupuestaria;
import ec.com.atikasoft.proteus.modelo.distributivo.CargaMasivaPuesto;
import ec.com.atikasoft.proteus.modelo.distributivo.Distributivo;
import ec.com.atikasoft.proteus.vo.ModalidadLaboralPuestoVO;
import ec.com.atikasoft.proteus.vo.UnidadOrganizacionalPuestoVO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Distributivo
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "distributivoHelper")
@SessionScoped
public class DistributivoHelper extends CatalogoHelper {

    /**
     * clase distributivo.
     */
    private Distributivo distributivo;
    /**
     * clase distributivo puesto para editar.
     */
    private Distributivo distributivoEditDelete;
    /**
     * lista de distributivos.
     */
    private List<Distributivo> listaDistributivos;
    /**
     * Variable para listar las alertas por codigo.
     */
    private List<Distributivo> listaDistributivoCodigo;
    /**
     * Variable para opciones de nuevo puesto.
     */
    private List<SelectItem> opcionesModalidadLaboral;
    private List<SelectItem> opcionesEscalaOcupacional;
    private List<SelectItem> opcionesUbicacionGeografica;
    private List<SelectItem> opcionesUnidadOrganizacional;
    private List<SelectItem> opcionesDenominacionPuesto;
    private List<SelectItem> opcionesGrupoPresupuestario;
    private List<SelectItem> opcionesServidor;
    private TreeNode rootUnidadOrganizacional;
    private TreeNode unidadSeleccionada;
    private TreeNode rootUbicacionGeografica;
    private TreeNode ubicacionSeleccionada;
    private TreeNode rootRegimen;
    private TreeNode escalaSeleccionada;
    private UnidadPresupuestaria unidadPresupuestariaSeleccionada;

    private DistributivoDetalle distributivoDetalle;
    private DistributivoDetalle distributivoDetalleEliminar;

    private List<UnidadOrganizacional> unidadesOrganizacionales;
    private List<UnidadPresupuestaria> unidadesPresupuestarias;
    private List<DistributivoDetalle> distributivosDetalles;
    private List<ModalidadLaboral> modalidadesLaborales;
    private List<UbicacionGeografica> ubicacionesGeograficas;
    private List<RegimenLaboral> escalasOcupacionales;
    private List<DenominacionPuesto> denominacionPuestos;

    private Long idDenominacion;
    private Date fechainicio;
    private Date fechafin;
    private BigDecimal sueldoBasico;
    /**
     * Variable para almacenar el estado predeterminado del puesto: VACANTE.
     */
    private EstadoPuesto estadoPuestoPredeterminado = new EstadoPuesto();

    /**
     * Variables para edicion del puesto.
     */
    private Boolean esEdicionPuesto;
    /**
     * Total de puestos.
     */
    private Long totalPuestos;

    /**
     *
     */
    private Boolean esNuevo;

    //----- CARGA MASIVA DE PUESTOS
    /**
     * Numero total de registros cargados
     */
    private int totalRegistrosCargadosCSV;
    /**
     * Numero total de registros que fallaron
     */
    private int totalRegistrosFallidosCSV;
    /**
     * Nombre del archivo cargado
     */
    private String nombreArchivoCSV;
    /**
     * Lista con la descripcion de errores encontrados al 
     * parsear el csv
     */
    private List<String> errores;

    /**
     * Lista de puestos parseados para mostrar
     */
    private List<DistributivoDetalle> listaPuestosCargadosCSV;

    /**
     *Lista de distributivo que sera guardada
     */
    private List<Distributivo> listaDistributivosGenerados;

    /**
     *Formato de fecha que se acepta en el csv
     */
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * mapa de unidades organizacionales para validar el csv
     */
    private Map<String, UnidadOrganizacional> unidadesOrganizacionalesValidacion = new HashMap<>();
    /**
     * mapa de modalidades laborales para validar el csv
     */
    private Map<String, ModalidadLaboral> modalidadesLaboralesValidacion = new HashMap<>();
    /**
     * mapa de unidades presupuestarias para validar el csv
     */
    private Map<String, UnidadPresupuestaria> unidadesPresupuestariasValidacion = new HashMap<>();
    /**
     * mapa de escalas ocupacionales para validar el csv
     */
    private Map<String, EscalaOcupacional> escalaOcupacionalValidacion = new HashMap<>();
    /**
     * mapa de denominaciones de puestos para validar el csv
     */
    private Map<String, DenominacionPuesto> denominacionesPuestosValidacion = new HashMap<>();
    /**
     * mapa de ubicaciones geograficas para validar el csv
     */
    private Map<String, UbicacionGeografica> ubicacionesGeograficasValidacion = new HashMap<>();

    /**
     * lista de las unidades organizacionales que se cargaron en el csv
     */
    private List<UnidadOrganizacionalPuestoVO> listaUnidadesOrganizacionalesCargadasCSV;
    /**
     * total de unidades que se cargaron en el csv
     */
    private Integer totalUnidadesOrganizacionalesCargadasCSV;
    /**
     * Lista con el resumen de los puestos cargados por modalidad laboral y unidades organizacionales
     */
    private List<ModalidadLaboralPuestoVO> listaPuestoPorUnidadesCSV;
    /**
     * Lista de cargas masivas realizadas
     */
    private List<CargaMasivaPuesto> listaCargasMasivasPuestos;
    
    /**
     * 
     */
    private CargaMasivaPuesto cargaMasivaPuesto;

    /**
     * Constructor por defecto.
     */
    public DistributivoHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del DistributivoHelper.
     */
    public final void iniciador() {
        Distributivo d = new Distributivo();
        d.setUnidadOrganizacional(new UnidadOrganizacional());
        setDistributivo(d);
        setDistributivoEditDelete(new Distributivo());
        setListaDistributivos(new ArrayList<Distributivo>());
        setListaDistributivoCodigo(new ArrayList<Distributivo>());
        opcionesModalidadLaboral = new ArrayList<SelectItem>();
        opcionesEscalaOcupacional = new ArrayList<SelectItem>();
        opcionesUbicacionGeografica = new ArrayList<SelectItem>();
        opcionesUnidadOrganizacional = new ArrayList<SelectItem>();
        opcionesDenominacionPuesto = new ArrayList<SelectItem>();
        opcionesServidor = new ArrayList<SelectItem>();
        opcionesGrupoPresupuestario = new ArrayList<SelectItem>();
        distributivoDetalleEliminar = new DistributivoDetalle();

        setDistributivoDetalle(new DistributivoDetalle());
        setModalidadesLaborales(new ArrayList<ModalidadLaboral>());
        setUbicacionesGeograficas(new ArrayList<UbicacionGeografica>());
        setUnidadesOrganizacionales(new ArrayList<UnidadOrganizacional>());
        setDenominacionPuestos(new ArrayList<DenominacionPuesto>());
        setEscalasOcupacionales(new ArrayList<RegimenLaboral>());
        unidadesPresupuestarias = new ArrayList<UnidadPresupuestaria>();

        rootUnidadOrganizacional = new DefaultTreeNode();
        unidadSeleccionada = new DefaultTreeNode();

        rootUbicacionGeografica = new DefaultTreeNode();
        ubicacionSeleccionada = new DefaultTreeNode();

        rootRegimen = new DefaultTreeNode();
        escalaSeleccionada = new DefaultTreeNode();
        setEsEdicionPuesto(Boolean.FALSE);
        setEsNuevo(false);

        //---- CARGA MASIVA DE PUESTOS
        errores = new ArrayList<>();
        listaPuestosCargadosCSV = new ArrayList<>();
        listaDistributivosGenerados = new ArrayList<>();
        listaUnidadesOrganizacionalesCargadasCSV = new ArrayList<>();
        listaPuestoPorUnidadesCSV = new ArrayList<>();
        listaCargasMasivasPuestos= new ArrayList<>();
    }

    /**
     * @return the distributivo
     */
    public Distributivo getDistributivo() {
        return distributivo;
    }

    /**
     * @param distributivo the distributivo to set
     */
    public void setDistributivo(final Distributivo distributivo) {
        this.distributivo = distributivo;
    }

    /**
     * @return the distributivoEditDelete
     */
    public Distributivo getDistributivoEditDelete() {
        return distributivoEditDelete;
    }

    /**
     * @param distributivoEditDelete the distributivoEditDelete to set
     */
    public void setDistributivoEditDelete(final Distributivo distributivoEditDelete) {
        this.distributivoEditDelete = distributivoEditDelete;
    }

    /**
     * @return the listaDistributivos
     */
    public List<Distributivo> getListaDistributivos() {
        return listaDistributivos;
    }

    /**
     * @param listaDistributivos the listaDistributivos to set
     */
    public void setListaDistributivos(final List<Distributivo> listaDistributivos) {
        this.listaDistributivos = listaDistributivos;
    }

    /**
     * @return the listaDistributivoCodigo
     */
    public List<Distributivo> getListaDistributivoCodigo() {
        return listaDistributivoCodigo;
    }

    /**
     * @param listaDistributivoCodigo the listaDistributivoCodigo to set
     */
    public void setListaDistributivoCodigo(final List<Distributivo> listaDistributivoCodigo) {
        this.listaDistributivoCodigo = listaDistributivoCodigo;
    }

    /**
     * @return the opcionesModalidadLaboral
     */
    public List<SelectItem> getOpcionesModalidadLaboral() {
        return opcionesModalidadLaboral;
    }

    /**
     * @param opcionesModalidadLaboral the opcionesModalidadLaboral to set
     */
    public void setOpcionesModalidadLaboral(List<SelectItem> opcionesModalidadLaboral) {
        this.opcionesModalidadLaboral = opcionesModalidadLaboral;
    }

    /**
     * @return the opcionesEscalaOcupacional
     */
    public List<SelectItem> getOpcionesEscalaOcupacional() {
        return opcionesEscalaOcupacional;
    }

    /**
     * @param opcionesEscalaOcupacional the opcionesEscalaOcupacional to set
     */
    public void setOpcionesEscalaOcupacional(List<SelectItem> opcionesEscalaOcupacional) {
        this.opcionesEscalaOcupacional = opcionesEscalaOcupacional;
    }

    /**
     * @return the opcionesUbicacionGeografica
     */
    public List<SelectItem> getOpcionesUbicacionGeografica() {
        return opcionesUbicacionGeografica;
    }

    /**
     * @param opcionesUbicacionGeografica the opcionesUbicacionGeografica to set
     */
    public void setOpcionesUbicacionGeografica(List<SelectItem> opcionesUbicacionGeografica) {
        this.opcionesUbicacionGeografica = opcionesUbicacionGeografica;
    }

    /**
     * @return the opcionesUnidadOrganizacional
     */
    public List<SelectItem> getOpcionesUnidadOrganizacional() {
        return opcionesUnidadOrganizacional;
    }

    /**
     * @param opcionesUnidadOrganizacional the opcionesUnidadOrganizacional to
     * set
     */
    public void setOpcionesUnidadOrganizacional(List<SelectItem> opcionesUnidadOrganizacional) {
        this.opcionesUnidadOrganizacional = opcionesUnidadOrganizacional;
    }

    /**
     * @return the opcionesDenominacionPuesto
     */
    public List<SelectItem> getOpcionesDenominacionPuesto() {
        return opcionesDenominacionPuesto;
    }

    /**
     * @param opcionesDenominacionPuesto the opcionesDenominacionPuesto to set
     */
    public void setOpcionesDenominacionPuesto(List<SelectItem> opcionesDenominacionPuesto) {
        this.opcionesDenominacionPuesto = opcionesDenominacionPuesto;
    }

    /**
     * @return the opcionesServidor
     */
    public List<SelectItem> getOpcionesServidor() {
        return opcionesServidor;
    }

    /**
     * @param opcionesServidor the opcionesServidor to set
     */
    public void setOpcionesServidor(List<SelectItem> opcionesServidor) {
        this.opcionesServidor = opcionesServidor;
    }

    /**
     * @return the rootUnidadOrganizacional
     */
    public TreeNode getRootUnidadOrganizacional() {
        return rootUnidadOrganizacional;
    }

    /**
     * @param rootUnidadOrganizacional the rootUnidadOrganizacional to set
     */
    public void setRootUnidadOrganizacional(TreeNode rootUnidadOrganizacional) {
        this.rootUnidadOrganizacional = rootUnidadOrganizacional;
    }

    /**
     * @return the unidadSeleccionada
     */
    public TreeNode getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    /**
     * @param unidadSeleccionada the unidadSeleccionada to set
     */
    public void setUnidadSeleccionada(TreeNode unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    /**
     * @return the rootUbicacionGeografica
     */
    public TreeNode getRootUbicacionGeografica() {
        return rootUbicacionGeografica;
    }

    /**
     * @param rootUbicacionGeografica the rootUbicacionGeografica to set
     */
    public void setRootUbicacionGeografica(TreeNode rootUbicacionGeografica) {
        this.rootUbicacionGeografica = rootUbicacionGeografica;
    }

    /**
     * @return the ubicacionSeleccionada
     */
    public TreeNode getUbicacionSeleccionada() {
        return ubicacionSeleccionada;
    }

    /**
     * @param ubicacionSeleccionada the ubicacionSeleccionada to set
     */
    public void setUbicacionSeleccionada(TreeNode ubicacionSeleccionada) {
        this.ubicacionSeleccionada = ubicacionSeleccionada;
    }

    /**
     * @return the rootRegimen
     */
    public TreeNode getRootRegimen() {
        return rootRegimen;
    }

    /**
     * @param rootRegimen the rootRegimen to set
     */
    public void setRootRegimen(TreeNode rootRegimen) {
        this.rootRegimen = rootRegimen;
    }

    /**
     * @return the escalaSeleccionada
     */
    public TreeNode getEscalaSeleccionada() {
        return escalaSeleccionada;
    }

    /**
     * @param escalaSeleccionada the escalaSeleccionada to set
     */
    public void setEscalaSeleccionada(TreeNode escalaSeleccionada) {
        this.escalaSeleccionada = escalaSeleccionada;
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }

    /**
     * @return the unidadesOrganizacionales
     */
    public List<UnidadOrganizacional> getUnidadesOrganizacionales() {
        return unidadesOrganizacionales;
    }

    /**
     * @param unidadesOrganizacionales the unidadesOrganizacionales to set
     */
    public void setUnidadesOrganizacionales(List<UnidadOrganizacional> unidadesOrganizacionales) {
        this.unidadesOrganizacionales = unidadesOrganizacionales;
    }

    /**
     * @return the distributivosDetalles
     */
    public List<DistributivoDetalle> getDistributivosDetalles() {
        return distributivosDetalles;
    }

    /**
     * @param distributivosDetalles the distributivosDetalles to set
     */
    public void setDistributivosDetalles(List<DistributivoDetalle> distributivosDetalles) {
        this.distributivosDetalles = distributivosDetalles;
    }

    /**
     * @return the ubicacionesGeograficas
     */
    public List<UbicacionGeografica> getUbicacionesGeograficas() {
        return ubicacionesGeograficas;
    }

    /**
     * @param ubicacionesGeograficas the ubicacionesGeograficas to set
     */
    public void setUbicacionesGeograficas(List<UbicacionGeografica> ubicacionesGeograficas) {
        this.ubicacionesGeograficas = ubicacionesGeograficas;
    }

    /**
     * @return the escalasOcupacionales
     */
    public List<RegimenLaboral> getEscalasOcupacionales() {
        return escalasOcupacionales;
    }

    /**
     * @param escalasOcupacionales the escalasOcupacionales to set
     */
    public void setEscalasOcupacionales(List<RegimenLaboral> escalasOcupacionales) {
        this.escalasOcupacionales = escalasOcupacionales;
    }

    /**
     * @return the denominacionPuestos
     */
    public List<DenominacionPuesto> getDenominacionPuestos() {
        return denominacionPuestos;
    }

    /**
     * @param denominacionPuestos the denominacionPuestos to set
     */
    public void setDenominacionPuestos(List<DenominacionPuesto> denominacionPuestos) {
        this.denominacionPuestos = denominacionPuestos;
    }

    /**
     * @return the modalidadesLaborales
     */
    public List<ModalidadLaboral> getModalidadesLaborales() {
        return modalidadesLaborales;
    }

    /**
     * @param modalidadesLaborales the modalidadesLaborales to set
     */
    public void setModalidadesLaborales(List<ModalidadLaboral> modalidadesLaborales) {
        this.modalidadesLaborales = modalidadesLaborales;
    }

    /**
     * @return the idDenominacion
     */
    public Long getIdDenominacion() {
        return idDenominacion;
    }

    /**
     * @param idDenominacion the idDenominacion to set
     */
    public void setIdDenominacion(Long idDenominacion) {
        this.idDenominacion = idDenominacion;
    }

    /**
     * @return the fechainicio
     */
    public Date getFechainicio() {
        return fechainicio;
    }

    /**
     * @param fechainicio the fechainicio to set
     */
    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    /**
     * @return the fechafin
     */
    public Date getFechafin() {
        return fechafin;
    }

    /**
     * @param fechafin the fechafin to set
     */
    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    /**
     * @return the sueldoBasico
     */
    public BigDecimal getSueldoBasico() {
        return sueldoBasico;
    }

    /**
     * @param sueldoBasico the sueldoBasico to set
     */
    public void setSueldoBasico(BigDecimal sueldoBasico) {
        this.sueldoBasico = sueldoBasico;
    }

    /**
     * @return the distributivoDetalleEliminar
     */
    public DistributivoDetalle getDistributivoDetalleEliminar() {
        return distributivoDetalleEliminar;
    }

    /**
     * @param distributivoDetalleEliminar the distributivoDetalleEliminar to set
     */
    public void setDistributivoDetalleEliminar(DistributivoDetalle distributivoDetalleEliminar) {
        this.distributivoDetalleEliminar = distributivoDetalleEliminar;
    }

    /**
     * @return the unidadPresupuestariaSeleccionada
     */
    public UnidadPresupuestaria getUnidadPresupuestariaSeleccionada() {
        return unidadPresupuestariaSeleccionada;
    }

    /**
     * @param unidadPresupuestariaSeleccionada the
     * unidadPresupuestariaSeleccionada to set
     */
    public void setUnidadPresupuestariaSeleccionada(UnidadPresupuestaria unidadPresupuestariaSeleccionada) {
        this.unidadPresupuestariaSeleccionada = unidadPresupuestariaSeleccionada;
    }

    /**
     * @return the unidadesPresupuestarias
     */
    public List<UnidadPresupuestaria> getUnidadesPresupuestarias() {
        return unidadesPresupuestarias;
    }

    /**
     * @param unidadesPresupuestarias the unidadesPresupuestarias to set
     */
    public void setUnidadesPresupuestarias(List<UnidadPresupuestaria> unidadesPresupuestarias) {
        this.unidadesPresupuestarias = unidadesPresupuestarias;
    }

    /**
     * @return the estadoPuestoPredeterminado
     */
    public EstadoPuesto getEstadoPuestoPredeterminado() {
        return estadoPuestoPredeterminado;
    }

    /**
     * @param estadoPuestoPredeterminado the estadoPuestoPredeterminado to set
     */
    public void setEstadoPuestoPredeterminado(EstadoPuesto estadoPuestoPredeterminado) {
        this.estadoPuestoPredeterminado = estadoPuestoPredeterminado;
    }

    /**
     * @return the opcionesGrupoPresupuestario
     */
    public List<SelectItem> getOpcionesGrupoPresupuestario() {
        return opcionesGrupoPresupuestario;
    }

    /**
     * @param opcionesGrupoPresupuestario the opcionesGrupoPresupuestario to set
     */
    public void setOpcionesGrupoPresupuestario(List<SelectItem> opcionesGrupoPresupuestario) {
        this.opcionesGrupoPresupuestario = opcionesGrupoPresupuestario;
    }

    /**
     * @return the esEdicionPuesto
     */
    public Boolean getEsEdicionPuesto() {
        return esEdicionPuesto;
    }

    /**
     * @param esEdicionPuesto the esEdicionPuesto to set
     */
    public void setEsEdicionPuesto(Boolean esEdicionPuesto) {
        this.esEdicionPuesto = esEdicionPuesto;
    }

    /**
     * @return the totalPuestos
     */
    public Long getTotalPuestos() {
        return totalPuestos;
    }

    /**
     * @param totalPuestos the totalPuestos to set
     */
    public void setTotalPuestos(Long totalPuestos) {
        this.totalPuestos = totalPuestos;
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
    public void setEsNuevo(Boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public int getTotalRegistrosCargadosCSV() {
        return totalRegistrosCargadosCSV;
    }

    public void setTotalRegistrosCargadosCSV(int totalRegistrosCargadosCSV) {
        this.totalRegistrosCargadosCSV = totalRegistrosCargadosCSV;
    }

    public String getNombreArchivoCSV() {
        return nombreArchivoCSV;
    }

    public void setNombreArchivoCSV(String nombreArchivoCSV) {
        this.nombreArchivoCSV = nombreArchivoCSV;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    public List<DistributivoDetalle> getListaPuestosCargadosCSV() {
        return listaPuestosCargadosCSV;
    }

    public void setListaPuestosCargadosCSV(List<DistributivoDetalle> listaPuestosCargadosCSV) {
        this.listaPuestosCargadosCSV = listaPuestosCargadosCSV;
    }

    public int getTotalRegistrosFallidosCSV() {
        return totalRegistrosFallidosCSV;
    }

    public void setTotalRegistrosFallidosCSV(int totalRegistrosFallidosCSV) {
        this.totalRegistrosFallidosCSV = totalRegistrosFallidosCSV;
    }

    public Map<String, UnidadOrganizacional> getUnidadesOrganizacionalesValidacion() {
        return unidadesOrganizacionalesValidacion;
    }

    public void setUnidadesOrganizacionalesValidacion(Map<String, UnidadOrganizacional> unidadesOrganizacionalesValidacion) {
        this.unidadesOrganizacionalesValidacion = unidadesOrganizacionalesValidacion;
    }

    public Map<String, ModalidadLaboral> getModalidadesLaboralesValidacion() {
        return modalidadesLaboralesValidacion;
    }

    public void setModalidadesLaboralesValidacion(Map<String, ModalidadLaboral> modalidadesLaboralesValidacion) {
        this.modalidadesLaboralesValidacion = modalidadesLaboralesValidacion;
    }

    public Map<String, UnidadPresupuestaria> getUnidadesPresupuestariasValidacion() {
        return unidadesPresupuestariasValidacion;
    }

    public void setUnidadesPresupuestariasValidacion(Map<String, UnidadPresupuestaria> unidadesPresupuestariasValidacion) {
        this.unidadesPresupuestariasValidacion = unidadesPresupuestariasValidacion;
    }

    public Map<String, EscalaOcupacional> getEscalaOcupacionalValidacion() {
        return escalaOcupacionalValidacion;
    }

    public void setEscalaOcupacionalValidacion(Map<String, EscalaOcupacional> escalaOcupacionalValidacion) {
        this.escalaOcupacionalValidacion = escalaOcupacionalValidacion;
    }

    public Map<String, DenominacionPuesto> getDenominacionesPuestosValidacion() {
        return denominacionesPuestosValidacion;
    }

    public void setDenominacionesPuestosValidacion(Map<String, DenominacionPuesto> denominacionesPuestosValidacion) {
        this.denominacionesPuestosValidacion = denominacionesPuestosValidacion;
    }

    public Map<String, UbicacionGeografica> getUbicacionesGeograficasValidacion() {
        return ubicacionesGeograficasValidacion;
    }

    public void setUbicacionesGeograficasValidacion(Map<String, UbicacionGeografica> ubicacionesGeograficasValidacion) {
        this.ubicacionesGeograficasValidacion = ubicacionesGeograficasValidacion;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public List<Distributivo> getListaDistributivosGenerados() {
        return listaDistributivosGenerados;
    }

    public void setListaDistributivosGenerados(List<Distributivo> listaDistributivosGenerados) {
        this.listaDistributivosGenerados = listaDistributivosGenerados;
    }

    public List<ModalidadLaboralPuestoVO> getListaPuestoPorUnidadesCSV() {
        return listaPuestoPorUnidadesCSV;
    }

    public void setListaPuestoPorUnidadesCSV(List<ModalidadLaboralPuestoVO> listaPuestoPorUnidadesCSV) {
        this.listaPuestoPorUnidadesCSV = listaPuestoPorUnidadesCSV;
    }

    public List<UnidadOrganizacionalPuestoVO> getListaUnidadesOrganizacionalesCargadasCSV() {
        return listaUnidadesOrganizacionalesCargadasCSV;
    }

    public void setListaUnidadesOrganizacionalesCargadasCSV(List<UnidadOrganizacionalPuestoVO> listaUnidadesOrganizacionalesCargadasCSV) {
        this.listaUnidadesOrganizacionalesCargadasCSV = listaUnidadesOrganizacionalesCargadasCSV;
    }

    public Integer getTotalUnidadesOrganizacionalesCargadasCSV() {
        return totalUnidadesOrganizacionalesCargadasCSV;
    }

    public void setTotalUnidadesOrganizacionalesCargadasCSV(Integer totalUnidadesOrganizacionalesCargadasCSV) {
        this.totalUnidadesOrganizacionalesCargadasCSV = totalUnidadesOrganizacionalesCargadasCSV;
    }

    public List<CargaMasivaPuesto> getListaCargasMasivasPuestos() {
        return listaCargasMasivasPuestos;
    }

    public void setListaCargasMasivasPuestos(List<CargaMasivaPuesto> listaCargasMasivasPuestos) {
        this.listaCargasMasivasPuestos = listaCargasMasivasPuestos;
    }

    public CargaMasivaPuesto getCargaMasivaPuesto() {
        return cargaMasivaPuesto;
    }

    public void setCargaMasivaPuesto(CargaMasivaPuesto cargaMasivaPuesto) {
        this.cargaMasivaPuesto = cargaMasivaPuesto;
    }
    
    
}
