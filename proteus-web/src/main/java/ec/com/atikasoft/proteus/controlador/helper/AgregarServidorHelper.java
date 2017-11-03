/*
 *  AgregarServidorHelper.java
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
 *  27/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.distributivo.Distributivo;
import ec.com.atikasoft.proteus.vo.DetalleMovimientoVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.model.SelectableDataModel;

/**
 * Helper de Llenar Puesto Controlador.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "agregarServidorHelper")
@SessionScoped
public class AgregarServidorHelper extends ListDataModel<DistributivoDetalle> implements
        SelectableDataModel<DistributivoDetalle>, Serializable {

    public AgregarServidorHelper(List<DistributivoDetalle> list) {
        super(list);
    }
    /**
     * Objeto movimiento.
     */
    private Movimiento movimiento = new Movimiento();

    /**
     * Diferencia de horas.
     */
    private String diferenciaHoras = "00:00";

    /**
     * NOmbre de institucion para buscar.
     */
    private String nombreInstitucionBuscar;

    /**
     * Lista instituciones.
     */
    private List<Institucion> listaInstituciones = new ArrayList<>();

    /**
     * Lista de EstructuraOrganizacionalDireccion.
     */
    private List<SelectItem> listaDireccionesEstructuraOrganizacional = new ArrayList<>();

    /**
     * Lista de EstructuraOrganizacionalDireccion Cambio.
     */
    private List<SelectItem> listaDireccionesEstructuraOrganizacionalCambio = new ArrayList<>();

    /**
     * Lista de EstructuraOrganizacionalDireccion Cambio.
     */
    private List<SelectItem> listaDireccionesEstructuraOrganizacionalTraspaso = new ArrayList<>();

    /**
     * Lista de puestos de institucion.
     */
    private List<SelectItem> puestosInstitucionales = new ArrayList<>();

    /**
     * lista SOPI.
     */
    private List<DistributivoDetalle> listaPuestos = new ArrayList<>();

    /**
     * Variable de seleccion de atributo, tabla traslados.
     */
    private DistributivoDetalle puesto = new DistributivoDetalle();

    /**
     * Vo de Detalle Movimiento.
     */
    private DetalleMovimientoVO detalleMovimientoVO = new DetalleMovimientoVO();

    /**
     * Objeto ingreso.
     */
    private Ingreso ingreso = new Ingreso();

    /**
     * Objeto Regimen Disciplinario.
     */
    private RegimenDisciplinario regimenDisciplinario = new RegimenDisciplinario();

    /**
     * Objeto Licencia.
     */
    private Licencia licencia = new Licencia();

    /**
     * Objeto de comision de servicio.
     */
    private ComisionServicio comisionServicio = new ComisionServicio();

    /**
     * Objecto de cambio administrativo.
     */
    private CambioAdministrativo cambioAdministrativo = new CambioAdministrativo();

    /**
     * Objeto de traslado administrativo.
     */
    private TrasladoAdministrativo trasladoAdministrativo = new TrasladoAdministrativo();

    /**
     * Numero de partida individual.
     */
    private Long codigoPuesto;

    /**
     * Objecto de traspaso.
     */
    private Traspaso traspaso = new Traspaso();

    /**
     * Objeto subrogacion.
     */
    private Subrogacion subrogacion = new Subrogacion();

    /**
     * Objeto de encargo.
     */
    private Encargo encargo = new Encargo();

    /**
     * Objeti de finalizacion.
     */
    private Finalizacion finalizacion = new Finalizacion();

    /**
     * Bandera que controla la vision del campo.
     */
    private Boolean areaDiscapacidad = Boolean.FALSE;

    /**
     * Bandera que controla la vision del campo.
     */
    private Boolean areaDetalleDiscapacidad = Boolean.FALSE;

    /**
     * Bandera para el control la vision de campo.
     */
    private Boolean areaDetalleEnfermedadCatastrofica = Boolean.FALSE;

    /**
     * Bandera para el control el tipo de documento cedula.
     */
    private Boolean tipoCedula = Boolean.FALSE;

    /**
     * Objeto cesacion.
     */
    private Cesacion cesacion = new Cesacion();

    /**
     * Descripcion para la ubicacion geografica.
     */
    private String descripcionUbicacionGeografica = "";

    /**
     * Valor para el numero de documento.
     */
    private InputText numeroDocumento = new InputText();

    /**
     * Lista de especifique discapacidad.
     */
    private List<SelectItem> especifiqueDiscapacidad = new ArrayList<>();

    /**
     * Lista de corresponde discapacidad.
     */
    private List<SelectItem> correspondeDiscapacidad = new ArrayList<>();

    /**
     * Lista de Tipo temporada.
     */
    private List<SelectItem> tipoTemporada = new ArrayList<>();

    /**
     * Lista de regimenes laborales.
     */
    private List<SelectItem> listaRegimenLaboral = new ArrayList<>();

    /**
     * Lista de niveles ocupacionales.
     */
    private List<SelectItem> listaNivelOcupacional = new ArrayList<>();

    /**
     * Lista de niveles ocupacionales.
     */
    private List<SelectItem> listaEscalaOcupacional = new ArrayList<>();

    /**
     * Lista de roles de puesto..
     */
    private List<SelectItem> rolPuesto = new ArrayList<>();

    /**
     * Lista de puestos adicionales.
     */
    private List<SelectItem> puestoAdicional = new ArrayList<>();

    /**
     * Lista de modalidades laborales.
     */
    private List<SelectItem> listaModalidadLaboral = new ArrayList<>();

    /**
     * Lista de denminacion de puestos.
     */
    private List<SelectItem> listaDenominacionPuesto = new ArrayList<>();

    /**
     * Lista de documentos previo.
     */
    private List<SelectItem> listaDocumentoPrevio = new ArrayList<>();

    /**
     * Lista de tipo documento.
     */
    private List<SelectItem> tipoDocumento = new ArrayList<>();

    /**
     * Lista de opciones de imprimir accion personal.
     */
    private List<SelectItem> imprimirAccionPersonal = new ArrayList<>();

    /**
     * Lista de opciones de nivel de estudio hijos para matricula.
     */
    private List<SelectItem> listaNivelEstudioHijo = new ArrayList<>();

    /**
     * Lista de motivos de salida.
     */
    private List<SelectItem> motivosSalida = new ArrayList<>();

    /**
     * Lista de tipo asignación.
     */
    private List<SelectItem> tipoDesignacion = new ArrayList<>();

    /**
     * Lista de generos.
     */
    private List<SelectItem> genero = new ArrayList<>();

    /**
     * Lista de nacionalidades.
     */
    private List<SelectItem> nacionalidad = new ArrayList<>();

    /**
     * Lista de Identificaciones Étnica.
     */
    private List<SelectItem> identificacionEtnica = new ArrayList<>();

    /**
     * Lista de estados civiles.
     */
    private List<SelectItem> estadoCivil = new ArrayList<>();

    /**
     * Lista de motivos de ingreso.
     */
    private List<SelectItem> motivoIngreso = new ArrayList<>();

    /**
     * Lista de Casos de Fallecimiento.
     */
    private List<SelectItem> casosFallecimiento = new ArrayList<>();

    /**
     * Lista de Tipo Discapacidad.
     */
    private List<SelectItem> tiposDiscapacidad = new ArrayList<>();

    /**
     * Lista de Nivel Institucion.
     */
    private List<SelectItem> nivelInstitucion = new ArrayList<>();

    /**
     * Lista de Tipo Periodo.
     */
    private List<SelectItem> tipoPeriodo = new ArrayList<>();

    /**
     * Lista de Tipo Fallecimiento.
     */
    private List<SelectItem> tipoFallecimiento = new ArrayList<>();

    /**
     * Variable de control de navegacion.
     */
    private String controlNavegacion;

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaPais = new ArrayList<>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaRegion = new ArrayList<>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaProvincia = new ArrayList<>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaCanton = new ArrayList<>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaParroquia = new ArrayList<>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaPaisServidor = new ArrayList<>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaRegionServidor = new ArrayList<>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaProvinciaServidor = new ArrayList<>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaCantonServidor = new ArrayList<>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaParroquiaServidor = new ArrayList<>();

    /**
     * Lista de Puesto Institucion.
     */
//    private List<EscalaLaboral> listaGrupoOcupacional = new ArrayList<EscalaLaboral>();
    /**
     * Lista de Unidades Administrativas.
     */
    private List<UnidadOrganizacional> listaUnidadOrganizacional = new ArrayList<>();

    /**
     *
     */
    private List<SelectItem> opcionesFaltaRegimenDisciplinario = new ArrayList<>();

    /**
     * Opciones de tipos de nacimiento.
     */
    private List<SelectItem> opcionesTipoNacimiento = new ArrayList<>();

    /**
     * Movimientos a finalizar.
     */
    private List<Movimiento> movimientosAFinalizar = new ArrayList<>();

    /**
     * Identificador del regimen laboral.
     */
    private Long regimenLaboralId;

    /**
     * Identificador del nivel ocupacional.
     */
    private Long nivelOcupacionalId;

    /**
     * Identificador de la escala ocupacional.
     */
    private Long escalaOcupacionalId;

    /**
     * Identificador de pais.
     */
    private Long paisId;

    /**
     * Identificador de provincia.
     */
    private Long provinciaId;

    /**
     * Identificador de canton.
     */
    private Long cantonId;

    /**
     * Identificador de parroquia.
     */
    private Long parroquiaId;

    /**
     *
     */
    private List<SelectItem> listaUnidadesPresupuestarios = new ArrayList<>();

    /**
     * Lista de tipos de peridos.
     */
    private List<SelectItem> listaTipoPeriodos = new ArrayList<>();

    /**
     * Ultimo trabajo del servidor.
     */
    private ServidorInstitucion ultimoTrabajoServidor;

    /**
     *
     */
    private Boolean registroManualNombres;

    /**
     * Constructor por defecto.
     */
    public AgregarServidorHelper() {
        super();
        tipoDesignacion.clear();
    }

    /**
     * Este método inicia las variables del helper.
     */
    public final void iniciadorEntidades() {
        //CatalogoServicio
        Movimiento movimiento = new Movimiento();
        DistributivoDetalle distributivoDetalle = new DistributivoDetalle();
        Distributivo distributivo = new Distributivo();
        ModalidadLaboral modalidadLaboral = new ModalidadLaboral();
        distributivo.setModalidadLaboral(modalidadLaboral);
        distributivoDetalle.setDistributivo(distributivo);
        movimiento.setDistributivoDetalle(distributivoDetalle);
        setMovimiento(movimiento);
        setIngreso(new Ingreso());
        setCesacion(new Cesacion());
        registroManualNombres = true;

    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the ingreso
     */
    public Ingreso getIngreso() {
        return ingreso;
    }

    /**
     * @param ingreso the ingreso to set
     */
    public void setIngreso(final Ingreso ingreso) {
        this.ingreso = ingreso;
    }

    /**
     * @return the cesacion
     */
    public Cesacion getCesacion() {
        return cesacion;
    }

    /**
     * @param cesacion the cesacion to set
     */
    public void setCesacion(final Cesacion cesacion) {
        this.cesacion = cesacion;
    }

    /**
     * @return the regimenLaboral
     */
    public List<SelectItem> getListaRegimenLaboral() {
        return listaRegimenLaboral;
    }

    /**
     * @param regimenLaboral the regimenLaboral to set
     */
    public void setListaRegimenLaboral(final List<SelectItem> regimenLaboral) {
        this.listaRegimenLaboral = regimenLaboral;
    }

    /**
     * @return the puestoAdicional
     */
    public List<SelectItem> getPuestoAdicional() {
        return puestoAdicional;
    }

    /**
     * @param puestoAdicional the puestoAdicional to set
     */
    public void setPuestoAdicional(final List<SelectItem> puestoAdicional) {
        this.puestoAdicional = puestoAdicional;
    }

    /**
     * @return the modalidadLaboral
     */
    public List<SelectItem> getListaModalidadLaboral() {
        return listaModalidadLaboral;
    }

    /**
     * @param modalidadLaboral the modalidadLaboral to set
     */
    public void setListaModalidadLaboral(final List<SelectItem> modalidadLaboral) {
        this.listaModalidadLaboral = modalidadLaboral;
    }

    /**
     * @return the tipoDocumento
     */
    public List<SelectItem> getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(final List<SelectItem> tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the tipoAsignacion
     */
    public List<SelectItem> getTipoDesignacion() {
        return tipoDesignacion;
    }

    /**
     * @param tipoDesignacion the tipoAsignacion to set
     */
    public void setTipoDesignacion(final List<SelectItem> tipoDesignacion) {
        this.tipoDesignacion = tipoDesignacion;
    }

    /**
     * @return the genero
     */
    public List<SelectItem> getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(final List<SelectItem> genero) {
        this.genero = genero;
    }

    /**
     * @return the nacionalidad
     */
    public List<SelectItem> getNacionalidad() {
        return nacionalidad;
    }

    /**
     * @param nacionalidad the nacionalidad to set
     */
    public void setNacionalidad(final List<SelectItem> nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * @return the identificacionEtnica
     */
    public List<SelectItem> getIdentificacionEtnica() {
        return identificacionEtnica;
    }

    /**
     * @param identificacionEtnica the identificacionEtnica to set
     */
    public void setIdentificacionEtnica(final List<SelectItem> identificacionEtnica) {
        this.identificacionEtnica = identificacionEtnica;
    }

    /**
     * @return the estadoCivil
     */
    public List<SelectItem> getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * @param estadoCivil the estadoCivil to set
     */
    public void setEstadoCivil(final List<SelectItem> estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * @return the motivoIngreso
     */
    public List<SelectItem> getMotivoIngreso() {
        return motivoIngreso;
    }

    /**
     * @param motivoIngreso the motivoIngreso to set
     */
    public void setMotivoIngreso(final List<SelectItem> motivoIngreso) {
        this.motivoIngreso = motivoIngreso;
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
     * @return the listaRegion
     */
    public List<SelectItem> getListaRegion() {
        return listaRegion;
    }

    /**
     * @param listaRegion the listaRegion to set
     */
    public void setListaRegion(final List<SelectItem> listaRegion) {
        this.listaRegion = listaRegion;
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
     * @return the casosFallecimiento
     */
    public List<SelectItem> getCasosFallecimiento() {
        return casosFallecimiento;
    }

    /**
     * @param casosFallecimiento the casosFallecimiento to set
     */
    public void setCasosFallecimiento(final List<SelectItem> casosFallecimiento) {
        this.casosFallecimiento = casosFallecimiento;
    }

    /**
     * @return the tiposDiscapacidad
     */
    public List<SelectItem> getTiposDiscapacidad() {
        return tiposDiscapacidad;
    }

    /**
     * @param tiposDiscapacidad the tiposDiscapacidad to set
     */
    public void setTiposDiscapacidad(final List<SelectItem> tiposDiscapacidad) {
        this.tiposDiscapacidad = tiposDiscapacidad;
    }

    /**
     * @return the nivelInstitucion
     */
    public List<SelectItem> getNivelInstitucion() {
        return nivelInstitucion;
    }

    /**
     * @param nivelInstitucion the nivelInstitucion to set
     */
    public void setNivelInstitucion(final List<SelectItem> nivelInstitucion) {
        this.nivelInstitucion = nivelInstitucion;
    }

    /**
     * @return the tipoPeriodo
     */
    public List<SelectItem> getTipoPeriodo() {
        return tipoPeriodo;
    }

    /**
     * @param tipoPeriodo the tipoPeriodo to set
     */
    public void setTipoPeriodo(final List<SelectItem> tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

//    /**
//     * @return the listaPuestoInstitucion
//     */
//    public List<PuestoInstitucionDTO> getListaPuestoInstitucion() {
//        return listaPuestoInstitucion;
//    }
//
//    /**
//     * @param listaPuestoInstitucion the listaPuestoInstitucion to set
//     */
//    public void setListaPuestoInstitucion(final List<PuestoInstitucionDTO> listaPuestoInstitucion) {
//        this.listaPuestoInstitucion = listaPuestoInstitucion;
//    }
//
//    /**
//     * @return the listaGrupoOcupacional
//     */
//    public List<EscalaLaboral> getListaGrupoOcupacional() {
//        return listaGrupoOcupacional;
//    }
//
//    /**
//     * @param listaGrupoOcupacional the listaGrupoOcupacional to set
//     */
//    public void setListaGrupoOcupacional(final List<EscalaLaboral> listaGrupoOcupacional) {
//        this.listaGrupoOcupacional = listaGrupoOcupacional;
//    }
    /**
     * @return the listaUnidadAdministrativa
     */
//    public List<EstructuraOrganizacional> getListaUnidadAdministrativa() {
//        return listaUnidadAdministrativa;
//    }
//
//    /**
//     * @param listaUnidadAdministrativa the listaUnidadAdministrativa to set
//     */
//    public void setListaUnidadAdministrativa(final List<EstructuraOrganizacional> listaUnidadAdministrativa) {
//        this.listaUnidadAdministrativa = listaUnidadAdministrativa;
//    }
    /**
     * @return the listaPaisServidor
     */
    public List<SelectItem> getListaPaisServidor() {
        return listaPaisServidor;
    }

    /**
     * @param listaPaisServidor the listaPaisServidor to set
     */
    public void setListaPaisServidor(final List<SelectItem> listaPaisServidor) {
        this.listaPaisServidor = listaPaisServidor;
    }

    /**
     * @return the listaRegionServidor
     */
    public List<SelectItem> getListaRegionServidor() {
        return listaRegionServidor;
    }

    /**
     * @param listaRegionServidor the listaRegionServidor to set
     */
    public void setListaRegionServidor(final List<SelectItem> listaRegionServidor) {
        this.listaRegionServidor = listaRegionServidor;
    }

    /**
     * @return the listaProvinciaServidor
     */
    public List<SelectItem> getListaProvinciaServidor() {
        return listaProvinciaServidor;
    }

    /**
     * @param listaProvinciaServidor the listaProvinciaServidor to set
     */
    public void setListaProvinciaServidor(final List<SelectItem> listaProvinciaServidor) {
        this.listaProvinciaServidor = listaProvinciaServidor;
    }

    /**
     * @return the listaCantonServidor
     */
    public List<SelectItem> getListaCantonServidor() {
        return listaCantonServidor;
    }

    /**
     * @param listaCantonServidor the listaCantonServidor to set
     */
    public void setListaCantonServidor(final List<SelectItem> listaCantonServidor) {
        this.listaCantonServidor = listaCantonServidor;
    }

    /**
     * @return the listaParroquiaServidor
     */
    public List<SelectItem> getListaParroquiaServidor() {
        return listaParroquiaServidor;
    }

    /**
     * @param listaParroquiaServidor the listaParroquiaServidor to set
     */
    public void setListaParroquiaServidor(final List<SelectItem> listaParroquiaServidor) {
        this.listaParroquiaServidor = listaParroquiaServidor;
    }

    /**
     * @return the descripcionUbicacionGeografica
     */
    public String getDescripcionUbicacionGeografica() {
        return descripcionUbicacionGeografica;
    }

    /**
     * @param descripcionUbicacionGeografica the descripcionUbicacionGeografica to set
     */
    public void setDescripcionUbicacionGeografica(final String descripcionUbicacionGeografica) {
        this.descripcionUbicacionGeografica = descripcionUbicacionGeografica;
    }

    /**
     * @return the tipoTemporada
     */
    public List<SelectItem> getTipoTemporada() {
        return tipoTemporada;
    }

    /**
     * @param tipoTemporada the tipoTemporada to set
     */
    public void setTipoTemporada(final List<SelectItem> tipoTemporada) {
        this.tipoTemporada = tipoTemporada;
    }

    /**
     * @return the tipoCedula
     */
    public Boolean getTipoCedula() {
        return tipoCedula;
    }

    /**
     * @param tipoCedula the tipoCedula to set
     */
    public void setTipoCedula(final Boolean tipoCedula) {
        this.tipoCedula = tipoCedula;
    }

    /**
     * @return the detalleMovimientoVO
     */
    public DetalleMovimientoVO getDetalleMovimientoVO() {
        detalleMovimientoVO.setIngreso(ingreso);
        detalleMovimientoVO.setCesacion(cesacion);
        detalleMovimientoVO.setRegimenDisciplinario(regimenDisciplinario);
        detalleMovimientoVO.setLicencia(licencia);
        detalleMovimientoVO.setComisionServicio(comisionServicio);
        detalleMovimientoVO.setCambioAdministrativo(cambioAdministrativo);
        detalleMovimientoVO.setTrasladoAdministrativo(trasladoAdministrativo);
        detalleMovimientoVO.setTraspaso(traspaso);
        detalleMovimientoVO.setEncargo(encargo);
        detalleMovimientoVO.setSubrogacion(subrogacion);
        detalleMovimientoVO.setFinalizacion(finalizacion);
        return detalleMovimientoVO;
    }

    /**
     * @return the motivosSalida
     */
    public List<SelectItem> getMotivosSalida() {
        return motivosSalida;
    }

    /**
     * @param motivosSalida the motivosSalida to set
     */
    public void setMotivosSalida(final List<SelectItem> motivosSalida) {
        this.motivosSalida = motivosSalida;
    }

    /**
     * @return the numeroDocumento
     */
    public InputText getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * @param numeroDocumento the numeroDocumento to set
     */
    public void setNumeroDocumento(final InputText numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * @return the controlNavegacion
     */
    public String getControlNavegacion() {
        return controlNavegacion;
    }

    /**
     * @param controlNavegacion the controlNavegacion to set
     */
    public void setControlNavegacion(final String controlNavegacion) {
        this.controlNavegacion = controlNavegacion;
    }

    /**
     * @return the imprimirAccionPersonal
     */
    public List<SelectItem> getImprimirAccionPersonal() {
        return imprimirAccionPersonal;
    }

    /**
     * @param imprimirAccionPersonal the imprimirAccionPersonal to set
     */
    public void setImprimirAccionPersonal(final List<SelectItem> imprimirAccionPersonal) {
        this.imprimirAccionPersonal = imprimirAccionPersonal;
    }

    /**
     * @return the regimenDisciplinario
     */
    public RegimenDisciplinario getRegimenDisciplinario() {
        return regimenDisciplinario;
    }

    /**
     * @param regimenDisciplinario the regimenDisciplinario to set
     */
    public void setRegimenDisciplinario(final RegimenDisciplinario regimenDisciplinario) {
        this.regimenDisciplinario = regimenDisciplinario;
    }

    /**
     * @return the opcionesFaltaRegimenDisciplinario
     */
    public List<SelectItem> getOpcionesFaltaRegimenDisciplinario() {
        return opcionesFaltaRegimenDisciplinario;
    }

    /**
     * @param opcionesFaltaRegimenDisciplinario the opcionesFaltaRegimenDisciplinario to set
     */
    public void setOpcionesFaltaRegimenDisciplinario(final List<SelectItem> opcionesFaltaRegimenDisciplinario) {
        this.opcionesFaltaRegimenDisciplinario = opcionesFaltaRegimenDisciplinario;
    }

    /**
     * @return the tipoFallecimiento
     */
    public List<SelectItem> getTipoFallecimiento() {
        return tipoFallecimiento;
    }

    /**
     * @param tipoFallecimiento the tipoFallecimiento to set
     */
    public void setTipoFallecimiento(final List<SelectItem> tipoFallecimiento) {
        this.tipoFallecimiento = tipoFallecimiento;
    }

    /**
     * @return the rolPuesto
     */
    public List<SelectItem> getRolPuesto() {
        return rolPuesto;
    }

    /**
     * @param rolPuesto the rolPuesto to set
     */
    public void setRolPuesto(final List<SelectItem> rolPuesto) {
        this.rolPuesto = rolPuesto;
    }

    /**
     * @return the especifiqueDiscapacidad
     */
    public List<SelectItem> getEspecifiqueDiscapacidad() {
        return especifiqueDiscapacidad;
    }

    /**
     * @return the correspondeDiscapacidad
     */
    public List<SelectItem> getCorrespondeDiscapacidad() {
        return correspondeDiscapacidad;
    }

    /**
     * @param especifiqueDiscapacidad the especifiqueDiscapacidad to set
     */
    public void setEspecifiqueDiscapacidad(final List<SelectItem> especifiqueDiscapacidad) {
        this.especifiqueDiscapacidad = especifiqueDiscapacidad;
    }

    /**
     * @param correspondeDiscapacidad the correspondeDiscapacidad to set
     */
    public void setCorrespondeDiscapacidad(final List<SelectItem> correspondeDiscapacidad) {
        this.correspondeDiscapacidad = correspondeDiscapacidad;
    }

    /**
     * @return the areaDiscapacidad
     */
    public Boolean getAreaDiscapacidad() {
        return areaDiscapacidad;
    }

    /**
     * @param areaDiscapacidad the areaDiscapacidad to set
     */
    public void setAreaDiscapacidad(final Boolean areaDiscapacidad) {
        this.areaDiscapacidad = areaDiscapacidad;
    }

    /**
     * @return the areaDetalleDiscapacidad
     */
    public Boolean getAreaDetalleDiscapacidad() {
        return areaDetalleDiscapacidad;
    }

    /**
     * @return the areaDetalleEnfermedadCatastrofica
     */
    public Boolean getAreaDetalleEnfermedadCatastrofica() {
        return areaDetalleEnfermedadCatastrofica;
    }

    /**
     * @param areaDetalleDiscapacidad the areaDetalleDiscapacidad to set
     */
    public void setAreaDetalleDiscapacidad(final Boolean areaDetalleDiscapacidad) {
        this.areaDetalleDiscapacidad = areaDetalleDiscapacidad;
    }

    /**
     * @param areaDetalleEnfermedadCatastrofica the areaDetalleEnfermedadCatastrofica to set
     */
    public void setAreaDetalleEnfermedadCatastrofica(final Boolean areaDetalleEnfermedadCatastrofica) {
        this.areaDetalleEnfermedadCatastrofica = areaDetalleEnfermedadCatastrofica;
    }

    /**
     * @return the licencia
     */
    public Licencia getLicencia() {
        return licencia;
    }

    /**
     * @param licencia the licencia to set
     */
    public void setLicencia(final Licencia licencia) {
        this.licencia = licencia;
    }

    /**
     * @return the opcionesTipoNacimiento
     */
    public List<SelectItem> getOpcionesTipoNacimiento() {
        return opcionesTipoNacimiento;
    }

    /**
     * @param opcionesTipoNacimiento the opcionesTipoNacimiento to set
     */
    public void setOpcionesTipoNacimiento(final List<SelectItem> opcionesTipoNacimiento) {
        this.opcionesTipoNacimiento = opcionesTipoNacimiento;
    }

    /**
     * @return the listaNivelEstudioHijo
     */
    public List<SelectItem> getListaNivelEstudioHijo() {
        return listaNivelEstudioHijo;
    }

    /**
     * @param listaNivelEstudioHijo the listaNivelEstudioHijo to set
     */
    public void setListaNivelEstudioHijo(final List<SelectItem> listaNivelEstudioHijo) {
        this.listaNivelEstudioHijo = listaNivelEstudioHijo;
    }

    /**
     * @return the diferenciaHoras
     */
    public String getDiferenciaHoras() {
        return diferenciaHoras;
    }

    /**
     * @param diferenciaHoras the diferenciaHoras to set
     */
    public void setDiferenciaHoras(final String diferenciaHoras) {
        this.diferenciaHoras = diferenciaHoras;
    }

    /**
     * @return the comisionServicio
     */
    public ComisionServicio getComisionServicio() {
        return comisionServicio;
    }

    /**
     * @param comisionServicio the comisionServicio to set
     */
    public void setComisionServicio(final ComisionServicio comisionServicio) {
        this.comisionServicio = comisionServicio;
    }

    /**
     * @return the nombreInstitucionBuscar
     */
    public String getNombreInstitucionBuscar() {
        return nombreInstitucionBuscar;
    }

    /**
     * @param nombreInstitucionBuscar the nombreInstitucionBuscar to set
     */
    public void setNombreInstitucionBuscar(final String nombreInstitucionBuscar) {
        this.nombreInstitucionBuscar = nombreInstitucionBuscar;
    }

    /**
     * //
     *
     * @return the listaInstituciones //
     */
//    public List<ec.gob.mrl.siith.adm.model.Institucion> getListaInstituciones() {
//        return listaInstituciones;
//    }
    /**
     * @param listaInstituciones the listaInstituciones to set
     */
//    public void setListaInstituciones(final List<ec.gob.mrl.siith.adm.model.Institucion> listaInstituciones) {
//        this.listaInstituciones = listaInstituciones;
//    }
    /**
     * @return the listaDireccionesEstructuraOrganizacional
     */
    public List<SelectItem> getListaDireccionesEstructuraOrganizacional() {
        return listaDireccionesEstructuraOrganizacional;
    }

    /**
     * @param listaDireccionesEstructuraOrganizacional the listaDireccionesEstructuraOrganizacional to set
     */
    public void setListaDireccionesEstructuraOrganizacional(
            final List<SelectItem> listaDireccionesEstructuraOrganizacional) {
        this.listaDireccionesEstructuraOrganizacional = listaDireccionesEstructuraOrganizacional;
    }

    /**
     * @return the cambioAdministrativo
     */
    public CambioAdministrativo getCambioAdministrativo() {
        return cambioAdministrativo;
    }

    /**
     * @return the trasladoAdministrativo
     */
    public TrasladoAdministrativo getTrasladoAdministrativo() {
        return trasladoAdministrativo;
    }

    /**
     * @param cambioAdministrativo the cambioAdministrativo to set
     */
    public void setCambioAdministrativo(final CambioAdministrativo cambioAdministrativo) {
        this.cambioAdministrativo = cambioAdministrativo;
    }

    /**
     * @param trasladoAdministrativo the trasladoAdministrativo to set
     */
    public void setTrasladoAdministrativo(final TrasladoAdministrativo trasladoAdministrativo) {
        this.trasladoAdministrativo = trasladoAdministrativo;
    }

    /**
     * @return the listaDireccionesEstructuraOrganizacionalCambio
     */
    public List<SelectItem> getListaDireccionesEstructuraOrganizacionalCambio() {
        return listaDireccionesEstructuraOrganizacionalCambio;
    }

    /**
     * @param listaDireccionesEstructuraOrganizacionalCambio the listaDireccionesEstructuraOrganizacionalCambio to set
     */
    public void setListaDireccionesEstructuraOrganizacionalCambio(
            final List<SelectItem> listaDireccionesEstructuraOrganizacionalCambio) {
        this.listaDireccionesEstructuraOrganizacionalCambio = listaDireccionesEstructuraOrganizacionalCambio;
    }

//    /**
//     * @return the listaEODComisionServicio
//     */
//    public HashMap<Long, EstructuraOrganizacionalDireccion> getListaEODComisionServicio() {
//        return listaEODComisionServicio;
//    }
//
//    /**
//     * @param listaEODComisionServicio the listaEODComisionServicio to set
//     */
//    public void setListaEODComisionServicio(
//            final HashMap<Long, EstructuraOrganizacionalDireccion> listaEODComisionServicio) {
//        this.listaEODComisionServicio = listaEODComisionServicio;
//    }
//
//    /**
//     * @return the listaEODCambioAdministrativo
//     */
//    public HashMap<Long, EstructuraOrganizacionalDireccion> getListaEODCambioAdministrativo() {
//        return listaEODCambioAdministrativo;
//    }
//
//    /**
//     * @param listaEODCambioAdministrativo the listaEODCambioAdministrativo to set
//     */
//    public void setListaEODCambioAdministrativo(
//            final HashMap<Long, EstructuraOrganizacionalDireccion> listaEODCambioAdministrativo) {
//        this.listaEODCambioAdministrativo = listaEODCambioAdministrativo;
//    }
    /**
     * @return the traspaso
     */
    public Traspaso getTraspaso() {
        return traspaso;
    }

    /**
     * @param traspaso the traspaso to set
     */
    public void setTraspaso(final Traspaso traspaso) {
        this.traspaso = traspaso;
    }

    /**
     * @return the listaDireccionesEstructuraOrganizacionalTraspaso
     */
    public List<SelectItem> getListaDireccionesEstructuraOrganizacionalTraspaso() {
        return listaDireccionesEstructuraOrganizacionalTraspaso;
    }

    /**
     * @param listaDireccionesEstructuraOrganizacionalTraspaso the listaDireccionesEstructuraOrganizacionalTraspaso to
     * set
     */
    public void setListaDireccionesEstructuraOrganizacionalTraspaso(
            final List<SelectItem> listaDireccionesEstructuraOrganizacionalTraspaso) {
        this.listaDireccionesEstructuraOrganizacionalTraspaso = listaDireccionesEstructuraOrganizacionalTraspaso;
    }

    /**
     * @return the puestosInstitucionales
     */
    public List<SelectItem> getPuestosInstitucionales() {
        return puestosInstitucionales;
    }

    /**
     * @param puestosInstitucionales the puestosInstitucionales to set
     */
    public void setPuestosInstitucionales(final List<SelectItem> puestosInstitucionales) {
        this.puestosInstitucionales = puestosInstitucionales;
    }

//    /**
//     * @return the listaEODTraspaso
//     */
//    public HashMap<Long, EstructuraOrganizacionalDireccion> getListaEODTraspaso() {
//        return listaEODTraspaso;
//    }
//
//    /**
//     * @param listaEODTraspaso the listaEODTraspaso to set
//     */
//    public void setListaEODTraspaso(final HashMap<Long, EstructuraOrganizacionalDireccion> listaEODTraspaso) {
//        this.listaEODTraspaso = listaEODTraspaso;
//    }
//
//    /**
//     * @return the listaPuestoInstitucionDTO
//     */
//    public HashMap<Long, PuestoInstitucionDTO> getListaPuestoInstitucionDTO() {
//        return listaPuestoInstitucionDTO;
//    }
//
//    /**
//     * @param listaPuestoInstitucionDTO the listaPuestoInstitucionDTO to set
//     */
//    public void setListaPuestoInstitucionDTO(final HashMap<Long, PuestoInstitucionDTO> listaPuestoInstitucionDTO) {
//        this.listaPuestoInstitucionDTO = listaPuestoInstitucionDTO;
//    }
    /**
     * @return the numeroPartidaIndividual
     */
//    /**
//     * @return the listaSopi
//     */
//    public List<SrhvOrganicoPosicionalIndividual> getListaSopi() {
//        return listaSopi;
//    }
//
//    /**
//     * @param listaSopi the listaSopi to set
//     */
//    public void setListaSopi(final List<SrhvOrganicoPosicionalIndividual> listaSopi) {
//        this.listaSopi = listaSopi;
//    }
//
    @Override
    public Object getRowKey(DistributivoDetalle t) {
        return t.getId();
    }

    @Override
    public DistributivoDetalle getRowData(String string) {
        List<DistributivoDetalle> cars = (List<DistributivoDetalle>) getWrappedData();

        for (DistributivoDetalle car : cars) {
            if (car.getId() == Long.valueOf(string)) {
                return car;
            }
        }
        return null;
    }
//
//    /**
//     * @return the sopi
//     */
//    public SrhvOrganicoPosicionalIndividual getSopi() {
//        return sopi;
//    }

    /**
     * @param sopi the sopi to set
     */
//    public void setSopi(final SrhvOrganicoPosicionalIndividual sopi) {
//        this.sopi = sopi;
//    }
    /**
     * @return the listaDocumentoPrevio
     */
    public List<SelectItem> getListaDocumentoPrevio() {
        return listaDocumentoPrevio;
    }

    /**
     * @param listaDocumentoPrevio the listaDocumentoPrevio to set
     */
    public void setListaDocumentoPrevio(final List<SelectItem> listaDocumentoPrevio) {
        this.listaDocumentoPrevio = listaDocumentoPrevio;
    }

    /**
     * @return the subrogacion
     */
    public Subrogacion getSubrogacion() {
        return subrogacion;
    }

    /**
     * @param subrogacion the subrogacion to set
     */
    public void setSubrogacion(final Subrogacion subrogacion) {
        this.subrogacion = subrogacion;
    }

    /**
     * @return the encargo
     */
    public Encargo getEncargo() {
        return encargo;
    }

    /**
     * @param encargo the encargo to set
     */
    public void setEncargo(final Encargo encargo) {
        this.encargo = encargo;
    }

    /**
     * @return the finalizacion
     */
    public Finalizacion getFinalizacion() {
        return finalizacion;
    }

    /**
     * @param finalizacion the finalizacion to set
     */
    public void setFinalizacion(final Finalizacion finalizacion) {
        this.finalizacion = finalizacion;
    }

    /**
     * @return the movimientosAFinalizar
     */
    public List<Movimiento> getMovimientosAFinalizar() {
        return movimientosAFinalizar;
    }

    /**
     * @param movimientosAFinalizar the movimientosAFinalizar to set
     */
    public void setMovimientosAFinalizar(final List<Movimiento> movimientosAFinalizar) {
        this.movimientosAFinalizar = movimientosAFinalizar;
    }

    /**
     * @return the listaUnidadOrganizacional
     */
    public List<UnidadOrganizacional> getListaUnidadOrganizacional() {
        return listaUnidadOrganizacional;
    }

    /**
     * @param listaUnidadOrganizacional the listaUnidadOrganizacional to set
     */
    public void setListaUnidadOrganizacional(final List<UnidadOrganizacional> listaUnidadOrganizacional) {
        this.listaUnidadOrganizacional = listaUnidadOrganizacional;
    }

    /**
     * @return the listaNivelOcupacional
     */
    public List<SelectItem> getListaNivelOcupacional() {
        return listaNivelOcupacional;
    }

    /**
     * @return the listaEscalaOcupacional
     */
    public List<SelectItem> getListaEscalaOcupacional() {
        return listaEscalaOcupacional;
    }

    /**
     * @param listaNivelOcupacional the listaNivelOcupacional to set
     */
    public void setListaNivelOcupacional(final List<SelectItem> listaNivelOcupacional) {
        this.listaNivelOcupacional = listaNivelOcupacional;
    }

    /**
     * @param listaEscalaOcupacional the listaEscalaOcupacional to set
     */
    public void setListaEscalaOcupacional(final List<SelectItem> listaEscalaOcupacional) {
        this.listaEscalaOcupacional = listaEscalaOcupacional;
    }

    /**
     * @return the regimenLaboralId
     */
    public Long getRegimenLaboralId() {
        return regimenLaboralId;
    }

    /**
     * @return the nivelOcupacionalId
     */
    public Long getNivelOcupacionalId() {
        return nivelOcupacionalId;
    }

    /**
     * @return the escalaOcupacionalId
     */
    public Long getEscalaOcupacionalId() {
        return escalaOcupacionalId;
    }

    /**
     * @param regimenLaboralId the regimenLaboralId to set
     */
    public void setRegimenLaboralId(final Long regimenLaboralId) {
        this.regimenLaboralId = regimenLaboralId;
    }

    /**
     * @param nivelOcupacionalId the nivelOcupacionalId to set
     */
    public void setNivelOcupacionalId(final Long nivelOcupacionalId) {
        this.nivelOcupacionalId = nivelOcupacionalId;
    }

    /**
     * @param escalaOcupacionalId the escalaOcupacionalId to set
     */
    public void setEscalaOcupacionalId(final Long escalaOcupacionalId) {
        this.escalaOcupacionalId = escalaOcupacionalId;
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
     * @return the paisId
     */
    public Long getPaisId() {
        return paisId;
    }

    /**
     * @return the provinciaId
     */
    public Long getProvinciaId() {
        return provinciaId;
    }

    /**
     * @return the cantonId
     */
    public Long getCantonId() {
        return cantonId;
    }

    /**
     * @return the parroquiaId
     */
    public Long getParroquiaId() {
        return parroquiaId;
    }

    /**
     * @param paisId the paisId to set
     */
    public void setPaisId(final Long paisId) {
        this.paisId = paisId;
    }

    /**
     * @param provinciaId the provinciaId to set
     */
    public void setProvinciaId(final Long provinciaId) {
        this.provinciaId = provinciaId;
    }

    /**
     * @param cantonId the cantonId to set
     */
    public void setCantonId(final Long cantonId) {
        this.cantonId = cantonId;
    }

    /**
     * @param parroquiaId the parroquiaId to set
     */
    public void setParroquiaId(final Long parroquiaId) {
        this.parroquiaId = parroquiaId;
    }

    /**
     * @return the codigoPuesto
     */
    public Long getCodigoPuesto() {
        return codigoPuesto;
    }

    /**
     * @param codigoPuesto the codigoPuesto to set
     */
    public void setCodigoPuesto(final Long codigoPuesto) {
        this.codigoPuesto = codigoPuesto;
    }

    /**
     * @return the listaPuestos
     */
    public List<DistributivoDetalle> getListaPuestos() {
        return listaPuestos;
    }

    /**
     * @param listaPuestos the listaPuestos to set
     */
    public void setListaPuestos(final List<DistributivoDetalle> listaPuestos) {
        this.listaPuestos = listaPuestos;
    }

    /**
     * @return the puesto
     */
    public DistributivoDetalle getPuesto() {
        return puesto;
    }

    /**
     * @param puesto the puesto to set
     */
    public void setPuesto(final DistributivoDetalle puesto) {
        this.puesto = puesto;
    }

    /**
     * @return the listaUnidadesPresupuestarios
     */
    public List<SelectItem> getListaUnidadesPresupuestarios() {
        return listaUnidadesPresupuestarios;
    }

    /**
     * @param listaUnidadesPresupuestarios the listaUnidadesPresupuestarios to set
     */
    public void setListaUnidadesPresupuestarios(final List<SelectItem> listaUnidadesPresupuestarios) {
        this.listaUnidadesPresupuestarios = listaUnidadesPresupuestarios;
    }

    /**
     * @return the listaTipoPeriodos
     */
    public List<SelectItem> getListaTipoPeriodos() {
        return listaTipoPeriodos;
    }

    /**
     * @param listaTipoPeriodos the listaTipoPeriodos to set
     */
    public void setListaTipoPeriodos(final List<SelectItem> listaTipoPeriodos) {
        this.listaTipoPeriodos = listaTipoPeriodos;
    }

    /**
     * @return the ultimoTrabajoServidor
     */
    public ServidorInstitucion getUltimoTrabajoServidor() {
        return ultimoTrabajoServidor;
    }

    /**
     * @param ultimoTrabajoServidor the ultimoTrabajoServidor to set
     */
    public void setUltimoTrabajoServidor(ServidorInstitucion ultimoTrabajoServidor) {
        this.ultimoTrabajoServidor = ultimoTrabajoServidor;
    }

    /**
     * @return the registroManualNombres
     */
    public Boolean getRegistroManualNombres() {
        return registroManualNombres;
    }

    /**
     * @param registroManualNombres the registroManualNombres to set
     */
    public void setRegistroManualNombres(Boolean registroManualNombres) {
        this.registroManualNombres = registroManualNombres;
    }

}
