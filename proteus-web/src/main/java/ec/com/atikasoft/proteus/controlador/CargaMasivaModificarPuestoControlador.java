/*
 *  MetadataTablaControlador.java
 *  SMP V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of SMP
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with SMP.
 *
 *  SMP
 *  Quito - Ecuador
 *
 *  03/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.CargaMasivaModificarPuestoHelper;
import ec.com.atikasoft.proteus.dao.EscalaOcupacionalDao;
import ec.com.atikasoft.proteus.dao.EstadoAdministracionPuestoRegimenLaboralDao;
import ec.com.atikasoft.proteus.enums.CamposArchivoModificarPuestosMasivoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DenominacionPuesto;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.EscalaOcupacional;
import ec.com.atikasoft.proteus.modelo.EstadoAdministracionPuesto;
import ec.com.atikasoft.proteus.modelo.EstadoAdministracionPuestoRegimenLaboral;
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.UnidadPresupuestaria;
import ec.com.atikasoft.proteus.modelo.distributivo.Distributivo;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * Carga Masiva
 * @author Leydis Garzon
 */
@ManagedBean(name = "cargaMasivaModificarPuestosBean")
@ViewScoped
public class CargaMasivaModificarPuestoControlador extends BaseControlador {

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD="/pages/administracion/carga_masiva_servidor/carga_masiva_servidor.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Servicio de distributivo.
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;

    /**
     * Clase de escala ocupcional dao.
     */
    @EJB
    private EscalaOcupacionalDao escalaOcupacionalDao;

    /**
     * Clase de estado administracion de puestos dao.
     */
    @EJB
    private EstadoAdministracionPuestoRegimenLaboralDao administracionPuestoRegimenLaboralDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{cargaMasivaModificarPuestoHelper}")
    private CargaMasivaModificarPuestoHelper cargaMasivaModificarPuestoHelper;

    /**
     * Constructor por defecto.
     */
    public CargaMasivaModificarPuestoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            cargaMasivaModificarPuestoHelper.getEscalasOcupacionales().clear();
            cargaMasivaModificarPuestoHelper.getModalidadesLaborales().clear();
            cargaMasivaModificarPuestoHelper.getDenominacionesPuestos().clear();
            cargaMasivaModificarPuestoHelper.getUnidadesOrganizacionales().clear();
            cargaMasivaModificarPuestoHelper.getUnidadesPresupuestarias().clear();
            cargaMasivaModificarPuestoHelper.getEstadosAdministracionPuesto().clear();
            cargaMasivaModificarPuestoHelper.getSectores().clear();

            cargaMasivaModificarPuestoHelper.setModalidadesLaborales(administracionServicio.
                    listarTodosModalidadLaboralVigentes());
            cargaMasivaModificarPuestoHelper.setUnidadesOrganizacionales(administracionServicio.
                    listarTodosUnidadOrganizacionalVigentes());
            cargaMasivaModificarPuestoHelper.setUnidadesPresupuestarias(administracionServicio.
                    listarTodosUnidadesPresupuestarias());
            cargaMasivaModificarPuestoHelper.setDenominacionesPuestos(administracionServicio.
                    listarTodosDenominacionPuestoVigentes());
            cargaMasivaModificarPuestoHelper.setSectores(administracionServicio.listarTodosSectores());

            try {
                cargaMasivaModificarPuestoHelper.setEscalasOcupacionales(escalaOcupacionalDao.buscarVigente());
            } catch (DaoException ex) {
                Logger.getLogger(CargaMasivaModificarPuestoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ServicioException ex) {
            Logger.getLogger(CargaMasivaModificarPuestoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public CargaMasivaModificarPuestoHelper getCargaMasivaModificarPuestoHelper() {
        return cargaMasivaModificarPuestoHelper;
    }

    public void setCargaMasivaModificarPuestoHelper(CargaMasivaModificarPuestoHelper cargaMasivaModificarPuestoHelper){
        this.cargaMasivaModificarPuestoHelper = cargaMasivaModificarPuestoHelper;
    }

    public void cargarArchivo(final FileUploadEvent event) {
        try {
            UploadedFile archivo = event.getFile();
            cargaMasivaModificarPuestoHelper.setNombreArchivoCSV(archivo.getFileName());
            cargaMasivaModificarPuestoHelper.setArchivoSinError(!validarArchivoCVS(archivo));
            if (!cargaMasivaModificarPuestoHelper.getArchivoSinError()) {
                ejecutarComandoPrimefaces("dlgCargaArchivoMasivoErrores.show();");

            } else {
                mostrarMensajeEnPantalla("Archivo cargado satisfactoriamente.", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public String procesarArchivo() {
        if (cargaMasivaModificarPuestoHelper.getArchivoSinError()) {
            int registrosProcesados = -1;
            try {
                registrosProcesados = distributivoPuestoServicio.guardarClasificacionValoracionPuestoMasiva(
                        cargaMasivaModificarPuestoHelper.getDistributivosDetalleMasivo(), obtenerUsuarioConectado());
            } catch (Exception ex) {
                Logger.getLogger(CargaMasivaModificarPuestoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (registrosProcesados > 0) {
                mostrarMensajeEnPantalla("Ocurrió un error al guardar la información.", FacesMessage.SEVERITY_ERROR);
            } else if (registrosProcesados == 0) {
                mostrarMensajeEnPantalla("Los puestos fueron modificados correctamente, "
                        + cargaMasivaModificarPuestoHelper.getDistributivosDetalleMasivo().size()
                        + " registros procesados.", FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla("Ocurrió un error al guardar la información, 0 registros procesados.",
                        FacesMessage.SEVERITY_ERROR);
            }
        } else {
            mostrarMensajeEnPantalla("No se puede ejecutar el cambio: El archivo cargado presenta errores",
                    FacesMessage.SEVERITY_ERROR);
        }
        limpiarDatosArchivoCargado();
        return null;
    }

    private boolean validarArchivoCVS(UploadedFile archivoSubido) {
        cargaMasivaModificarPuestoHelper.getDistributivosDetalleMasivo().clear();

        cargaMasivaModificarPuestoHelper.getErroresEnArchivo().clear();
        Boolean error = Boolean.FALSE;
        String errorMsg;
        OutputStream outStream = null;
        try {
            //CARGAR ARCHIVO AL SERVIDOR
            File f = new File(archivoSubido.getFileName());
            outStream = new FileOutputStream(f);
            long pesoArchivo = archivoSubido.getSize();
            byte[] buffer = new byte[(int) pesoArchivo];
            InputStream stream = archivoSubido.getInputstream();
            stream.read(buffer, 0, (int) pesoArchivo);
            stream.close();
            outStream.write(buffer);
            outStream.close();

            List<String[]> lineas = UtilArchivos.leerArchivoCSVSubido(f);

            DistributivoDetalle puesto = null;
            int i = 1;
            for (String[] l : lineas) {
                Boolean conModificacion = Boolean.FALSE;
                ModalidadLaboral modalidad = null;
                UnidadOrganizacional unidadOrganizacional = null;
                EscalaOcupacional escala = null;
                boolean unidadOrganizacionalCambiada = false;
                boolean modalidadCambiada = false;
                //comprueba que tenga las cantidad de columnas establecidad, 
                //evita procesar lineas mal formadas, ej. lineas vacias
                int cantidadCampos = CamposArchivoModificarPuestosMasivoEnum.values().length;
                if (l.length != cantidadCampos) {
                    error = Boolean.TRUE;
                    errorMsg = "Error en la línea " + i + ". No cumple con el formato de " + cantidadCampos
                            + " campos separador por ; ";
                    cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);
                } else {
                    //campo código
                    if (l[0] == null || l[0].trim().isEmpty()) {
                        errorCampoObligatorio(i, CamposArchivoModificarPuestosMasivoEnum.CODIGO_PUESTO.getColumna(),
                                CamposArchivoModificarPuestosMasivoEnum.CODIGO_PUESTO.getDescripcion());
                        error = Boolean.TRUE;
                    } else if (!l[0].trim().matches("[0-9]+")) {
                        errorCampoTipo(i, CamposArchivoModificarPuestosMasivoEnum.CODIGO_PUESTO.getColumna(),
                                CamposArchivoModificarPuestosMasivoEnum.CODIGO_PUESTO.getDescripcion(),
                                CamposArchivoModificarPuestosMasivoEnum.CODIGO_PUESTO.getTipo());
                        error = Boolean.TRUE;
                    } else {
                        puesto = buscarPuesto(i, new Long(l[0].trim()));
                        if (puesto == null) {
                            error = Boolean.TRUE;
                        } else {
                            
                            //campo escala ocupacional
                            if (l[CamposArchivoModificarPuestosMasivoEnum.ESCALA_OCUPACIONAL.getColumna() - 1] != null
                                    && !l[CamposArchivoModificarPuestosMasivoEnum.ESCALA_OCUPACIONAL.getColumna() - 1].
                                    trim().isEmpty()) {
                                escala = (EscalaOcupacional) buscarEntidadPorCodigo(i,
                                        CamposArchivoModificarPuestosMasivoEnum.ESCALA_OCUPACIONAL,
                                        cargaMasivaModificarPuestoHelper.getEscalasOcupacionales(),
                                        l[CamposArchivoModificarPuestosMasivoEnum.ESCALA_OCUPACIONAL.
                                        getColumna() - 1]);
                                conModificacion = Boolean.TRUE;
                                if (escala == null) {
                                    error = Boolean.TRUE;
                                } else if(validarVacanteOAsignacionPrevia(i, puesto, escala.getId(), 
                                            CamposArchivoModificarPuestosMasivoEnum.ESCALA_OCUPACIONAL)){
                                    error = Boolean.TRUE;
                                } else {
                                    puesto.setIdEscalaOcupacional(escala.getId());
                                    puesto.setEscalaOcupacional(escala);
                                }
                            }
                            
                            //campo estado administracion de puesto
                            if (l[CamposArchivoModificarPuestosMasivoEnum.ESTADO_ADMINISTRACION_PUESTO.getColumna() - 1]
                                    != null
                                    && !l[CamposArchivoModificarPuestosMasivoEnum.ESTADO_ADMINISTRACION_PUESTO.
                                    getColumna() - 1].trim().isEmpty()) {
                                
                                cargarEstadosDelRegimen(escala!=null ? 
                                    escala.getNivelOcupacional().getIdRegimenLaboral() :
                                    puesto.getEscalaOcupacional().getNivelOcupacional().getIdRegimenLaboral());
                                
                                EstadoAdministracionPuesto estado = (EstadoAdministracionPuesto) buscarEntidadPorCodigo(
                                        i,CamposArchivoModificarPuestosMasivoEnum.ESTADO_ADMINISTRACION_PUESTO,
                                        cargaMasivaModificarPuestoHelper.getEstadosAdministracionPuesto(),
                                        l[CamposArchivoModificarPuestosMasivoEnum.ESTADO_ADMINISTRACION_PUESTO.
                                        getColumna() - 1]);
                                EstadoAdministracionPuestoRegimenLaboral estadoRegimen = estado == null ? null 
                                        : buscarEstadoRegimen(estado.getId());
                                conModificacion = Boolean.TRUE;
                                if (estado == null) {
                                    error = Boolean.TRUE;
                                } else if(puesto.getEstadosAdmPuestosRegimenLaboralId() != null && 
                                        validarVacanteOAsignacionPrevia(i, puesto,estadoRegimen.getId(), 
                                            CamposArchivoModificarPuestosMasivoEnum.ESTADO_ADMINISTRACION_PUESTO)){
                                    error = Boolean.TRUE;
                                } else {
                                    puesto.setEstadosAdmPuestosRegimenLaboral(estadoRegimen);
                                    puesto.setEstadosAdmPuestosRegimenLaboralId(estadoRegimen.getId());
                                }
                            }


                            //campo modalidad laboral
                            if (l[CamposArchivoModificarPuestosMasivoEnum.MODALIDAD_LABORAL.getColumna() - 1] != null
                                    && !l[CamposArchivoModificarPuestosMasivoEnum.MODALIDAD_LABORAL.getColumna() - 1].
                                    trim().isEmpty()) {

                                modalidad = (ModalidadLaboral) buscarEntidadPorCodigo(i,
                                        CamposArchivoModificarPuestosMasivoEnum.MODALIDAD_LABORAL,
                                        cargaMasivaModificarPuestoHelper.getModalidadesLaborales(),
                                        l[CamposArchivoModificarPuestosMasivoEnum.MODALIDAD_LABORAL.
                                        getColumna() - 1]);
                                conModificacion = Boolean.TRUE;
                                if (modalidad == null) {
                                    error = Boolean.TRUE;
                                } else if(validarVacanteOAsignacionPrevia(i, puesto, modalidad.getId(), 
                                            CamposArchivoModificarPuestosMasivoEnum.MODALIDAD_LABORAL)){
                                    error = Boolean.TRUE;
                                }  else {
                                    modalidadCambiada = true;
                                }
                            }

                            //campo denominacion de puesto
                            if (l[CamposArchivoModificarPuestosMasivoEnum.DENOMINACION_PUESTO.getColumna() - 1] != null
                                    && !l[CamposArchivoModificarPuestosMasivoEnum.DENOMINACION_PUESTO.getColumna() - 1]
                                    .trim().isEmpty()) {
                                conModificacion = Boolean.TRUE;
                                DenominacionPuesto denominacion = (DenominacionPuesto) buscarEntidadPorCodigo(i,
                                        CamposArchivoModificarPuestosMasivoEnum.DENOMINACION_PUESTO,
                                        cargaMasivaModificarPuestoHelper.getDenominacionesPuestos(),
                                        l[CamposArchivoModificarPuestosMasivoEnum.DENOMINACION_PUESTO.
                                        getColumna() - 1]);
                                if (denominacion == null) {
                                    error = Boolean.TRUE;
                                } else if(validarVacanteOAsignacionPrevia(i, puesto, denominacion.getId(), 
                                            CamposArchivoModificarPuestosMasivoEnum.DENOMINACION_PUESTO)){
                                    error = Boolean.TRUE;
                                }  else {
                                    puesto.setIdDenominacionPuesto(denominacion.getId());
                                    puesto.setDenominacionPuesto(denominacion);
                                }
                            }

                            //campo unidad organizacional
                            if(l[CamposArchivoModificarPuestosMasivoEnum.UNIDAD_ORGANIZACIONAL.getColumna() - 1] != null
                                   && !l[CamposArchivoModificarPuestosMasivoEnum.UNIDAD_ORGANIZACIONAL.getColumna() - 1]
                                    .trim().isEmpty()) {
                                unidadOrganizacional = (UnidadOrganizacional) buscarEntidadPorCodigo(i,
                                        CamposArchivoModificarPuestosMasivoEnum.UNIDAD_ORGANIZACIONAL,
                                        cargaMasivaModificarPuestoHelper.getUnidadesOrganizacionales(),
                                        l[CamposArchivoModificarPuestosMasivoEnum.UNIDAD_ORGANIZACIONAL.
                                        getColumna() - 1]);
                                conModificacion = Boolean.TRUE;
                                if (unidadOrganizacional == null) {
                                    error = Boolean.TRUE;
                                } else if(validarVacanteOAsignacionPrevia(i, puesto, unidadOrganizacional.getId(), 
                                            CamposArchivoModificarPuestosMasivoEnum.UNIDAD_ORGANIZACIONAL)){
                                    error = Boolean.TRUE;
                                }  else {
                                    unidadOrganizacionalCambiada = true;
                                }
                            }

                            //campo partida individual
                            if (l[CamposArchivoModificarPuestosMasivoEnum.PARTIDA_INDIVIDUAL.getColumna() - 1] != null
                                    && !l[CamposArchivoModificarPuestosMasivoEnum.PARTIDA_INDIVIDUAL.getColumna() - 1].
                                    trim().isEmpty()) {
                                conModificacion = Boolean.TRUE;
                                if (!l[CamposArchivoModificarPuestosMasivoEnum.PARTIDA_INDIVIDUAL.getColumna() - 1].
                                        trim().matches("[0-9]+")) {
                                    errorCampoTipo(i, CamposArchivoModificarPuestosMasivoEnum.PARTIDA_INDIVIDUAL.
                                            getColumna(), CamposArchivoModificarPuestosMasivoEnum.PARTIDA_INDIVIDUAL.
                                            getDescripcion(),
                                            CamposArchivoModificarPuestosMasivoEnum.PARTIDA_INDIVIDUAL.getTipo());
                                    error = Boolean.TRUE;
                                } else if(validarVacanteOAsignacionPrevia(i, puesto, 
                                        l[CamposArchivoModificarPuestosMasivoEnum.PARTIDA_INDIVIDUAL.getColumna() - 1].
                                        trim(), CamposArchivoModificarPuestosMasivoEnum.PARTIDA_INDIVIDUAL)){
                                    error = Boolean.TRUE;
                                } else {
                                    error = validarExistenciaDePartida(i, l[CamposArchivoModificarPuestosMasivoEnum.
                                            PARTIDA_INDIVIDUAL.getColumna() - 1].trim(), error);
                                    puesto.setPartidaIndividual(l[CamposArchivoModificarPuestosMasivoEnum.
                                            PARTIDA_INDIVIDUAL.getColumna() - 1].trim());
                                }
                            }

                            UnidadPresupuestaria unidadPresupuestaria = null;
                            //campo unidad presupuestaria
                            if(l[CamposArchivoModificarPuestosMasivoEnum.UNIDAD_PRESUPUESTARIA.getColumna() - 1] != null
                                    && !l[CamposArchivoModificarPuestosMasivoEnum.UNIDAD_PRESUPUESTARIA.
                                    getColumna() - 1].trim().isEmpty()) {
                                unidadPresupuestaria = buscarUnidadPresupuestaria(i,
                                        l[CamposArchivoModificarPuestosMasivoEnum.UNIDAD_PRESUPUESTARIA.
                                        getColumna() - 1]);
                                conModificacion = Boolean.TRUE;
                                if (unidadPresupuestaria == null) {
                                    error = Boolean.TRUE;
                                } else if(validarVacanteOAsignacionPrevia(i, puesto, unidadPresupuestaria.getId(), 
                                            CamposArchivoModificarPuestosMasivoEnum.UNIDAD_PRESUPUESTARIA)){
                                    error = Boolean.TRUE;
                                }  else {
                                    puesto.setUnidadPresupuestaria(unidadPresupuestaria);
                                }
                            }

                            //campo grupo presupuestario
                            if (l[CamposArchivoModificarPuestosMasivoEnum.GRUPO_PRESUPUESTARIO.getColumna() - 1] != null
                                    && !l[CamposArchivoModificarPuestosMasivoEnum.GRUPO_PRESUPUESTARIO.
                                    getColumna() - 1].trim()
                                    .isEmpty()) {

                                conModificacion = Boolean.TRUE;
                                if (!l[CamposArchivoModificarPuestosMasivoEnum.GRUPO_PRESUPUESTARIO.
                                        getColumna() - 1].trim().matches("[0-9]+")) {
                                    errorCampoTipo(i, CamposArchivoModificarPuestosMasivoEnum.GRUPO_PRESUPUESTARIO.
                                            getColumna(), CamposArchivoModificarPuestosMasivoEnum.GRUPO_PRESUPUESTARIO.
                                            getDescripcion(),
                                            CamposArchivoModificarPuestosMasivoEnum.GRUPO_PRESUPUESTARIO.getTipo());
                                    error = Boolean.TRUE;
                                } else if (!validarGrupoPresupuestario(i,
                                        l[CamposArchivoModificarPuestosMasivoEnum.GRUPO_PRESUPUESTARIO.
                                        getColumna() - 1].trim(), unidadPresupuestaria, puesto)) {
                                    error = Boolean.TRUE;
                                } else if(validarVacanteOAsignacionPrevia(i, puesto, 
                                        l[CamposArchivoModificarPuestosMasivoEnum.GRUPO_PRESUPUESTARIO.getColumna() - 1]
                                        .trim(), CamposArchivoModificarPuestosMasivoEnum.GRUPO_PRESUPUESTARIO)){
                                    error = Boolean.TRUE;
                                }  else {
                                    puesto.setGrupoPresupuestario(l[CamposArchivoModificarPuestosMasivoEnum.
                                            GRUPO_PRESUPUESTARIO.getColumna() - 1].trim());
                                }
                            }

                            //campo certificacion presupuestaria
                            /*if (l[CamposArchivoModificarPuestosMasivoEnum.CERTIFICACION_PRESUPUESTARIA.
                                    getColumna() - 1] != null
                                    && !l[CamposArchivoModificarPuestosMasivoEnum.CERTIFICACION_PRESUPUESTARIA.
                                    getColumna() - 1]
                                    .trim()
                                    .isEmpty()) {
                                conModificacion = Boolean.TRUE;
                                if(validarVacanteOAsignacionPrevia(i, puesto, 
                                    l[CamposArchivoModificarPuestosMasivoEnum.CERTIFICACION_PRESUPUESTARIA.getColumna() 
                                      - 1].trim(), CamposArchivoModificarPuestosMasivoEnum.CERTIFICACION_PRESUPUESTARIA)
                                   ){
                                    error = Boolean.TRUE;
                                } else {
                                    puesto.setCertificacionPresupuestaria(l[CamposArchivoModificarPuestosMasivoEnum.
                                            CERTIFICACION_PRESUPUESTARIA.getColumna() - 1].trim());
                                }

                            }*/
                            
                            //campo observacion
                            if (l[CamposArchivoModificarPuestosMasivoEnum.OBSERVACION.getColumna() - 1] != null
                                    && !l[CamposArchivoModificarPuestosMasivoEnum.OBSERVACION.getColumna() - 1].trim()
                                    .isEmpty()) {
                                puesto.setObservacionUltimaModificacion(l[CamposArchivoModificarPuestosMasivoEnum.
                                        OBSERVACION.getColumna() - 1].trim());
                            } else {
                                error = Boolean.TRUE;
                                errorCampoObligatorio(i, CamposArchivoModificarPuestosMasivoEnum.OBSERVACION.getColumna(),
                                CamposArchivoModificarPuestosMasivoEnum.OBSERVACION.getDescripcion());    
                            }

                            

                            if (!conModificacion) {
                                error = Boolean.TRUE;
                                errorMsg = "Error en la línea " + i
                                        + ". No se ha especificado modificación alguna.";
                                cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);
                            } else {
                                puesto.setUsuarioActualizacion(obtenerUsuarioConectado().getUsuario());
                                puesto.setFechaActualizacion(new Date());
                                if (unidadOrganizacionalCambiada || modalidadCambiada) {
                                    try {
                                        Distributivo distributivo = distributivoPuestoServicio.buscarDistributivo(
                                                unidadOrganizacionalCambiada ? unidadOrganizacional.getId() : puesto.
                                                        getDistributivo().getIdUnidadOrganizacional(),
                                                modalidadCambiada ? modalidad.getId() : puesto.getDistributivo().
                                                        getIdModalidadLaboral(),
                                                puesto.getDistributivo().getIdInstitucionEjercicioFiscal());

                                        if (distributivo == null) {
                                            distributivo = new Distributivo();
                                            iniciarDatosEntidad(distributivo, Boolean.TRUE);
                                            distributivo.setContadorPartida(0l);

                                            distributivo.setIdModalidadLaboral(modalidadCambiada ? modalidad.getId()
                                                    : puesto.getDistributivo().
                                                    getIdModalidadLaboral());
                                            distributivo.setModalidadLaboral(modalidadCambiada ? modalidad
                                                    : puesto.getDistributivo().
                                                    getModalidadLaboral());

                                            distributivo.setIdUnidadOrganizacional(unidadOrganizacionalCambiada
                                                    ? unidadOrganizacional.getId() : puesto.
                                                    getDistributivo().getIdUnidadOrganizacional());
                                            distributivo.setUnidadOrganizacional(unidadOrganizacionalCambiada
                                                    ? unidadOrganizacional : puesto.
                                                    getDistributivo().getUnidadOrganizacional());

                                            distributivo.setIdInstitucionEjercicioFiscal(puesto.getDistributivo().
                                                    getIdInstitucionEjercicioFiscal());
                                        }

                                        puesto.setDistributivo(distributivo);
                                        puesto.setIdDistributivo(distributivo.getId());

                                    } catch (ServicioException ex) {
                                        Logger.getLogger(CargaMasivaModificarPuestoControlador.class.getName()).
                                                log(Level.SEVERE, null, ex);
                                    }
                                }
                                cargaMasivaModificarPuestoHelper.getDistributivosDetalleMasivo().add(puesto);
                            }

                        }

                    }
                }
                i++;
            }

            /*if (!error) {
                cargaMasivaModificarPuestoHelper.getFilas().clear();
                cargaMasivaModificarPuestoHelper.getFilas().addAll(lineas);
            }*/
            if (error) {
                cargaMasivaModificarPuestoHelper.getDistributivosDetalleMasivo().clear();
            }
            f.delete();
        } catch (IOException ex) {
            Logger.getLogger(CambioModalidadControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error;
    }

    /**
     * Crea mensaje de error cuando se omite un campo obligatorio
     * @param nroLinea
     * @param campo
     */
    private void errorCampoObligatorio(int nroLinea, int nroColumna, String campo) {
        String errorMsg = "Error en la línea " + nroLinea + ", columna " + nroColumna + ". Se omitió el campo \""
                + campo + "\".";
        cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);
    }

    /**
     * Crea mensaje de error cuando el tipo de dato no es el esperado para ese campo del archivo
     * @param nroLinea
     * @param campo
     */
    private void errorCampoTipo(int nroLinea, int nroColumna, String campo, String tipo) {
        String errorMsg = "Error en la línea " + nroLinea + ", columna " + nroColumna + ". El campo \"" + campo + "\""
                + " debe ser de tipo \"" + tipo + "\".";
        cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);
    }

    /**
     * Este método procesa la busqueda de puestos por filtro.
     *
     * @param line
     * @param puestoCodigo
     * @return
     */
    private DistributivoDetalle buscarPuesto(int line, Long puestoCodigo) {
        String errorMsg;
        try {
            List<DistributivoDetalle> listaPuestos = distributivoPuestoServicio.buscarPorCodigoPuesto(puestoCodigo,
                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());

            if (!listaPuestos.isEmpty()) {
                return listaPuestos.get(0);
            } else {
                errorMsg = "Error en la línea " + line + ", columna "
                        + CamposArchivoModificarPuestosMasivoEnum.CODIGO_PUESTO.getColumna()
                        + ". No existe puesto con el código " + puestoCodigo;
                cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);
            }
        } catch (Exception e) {
            errorMsg = "Error al consultar los puestos. Linea " + line;
            cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);
        }
        return null;
    }

    /**
     * Busca la unidad presupuestaria uniendo codigo del sector y codigo de la unidad.
     * @param line
     * @param codigoUnidad
     * @return
     */
    private UnidadPresupuestaria buscarUnidadPresupuestaria(int line, String codigoUnidad) {

        for (UnidadPresupuestaria up : cargaMasivaModificarPuestoHelper.getUnidadesPresupuestarias()) {
            //unir codigo sector y codigo unidad presupuestaria
            String codigoSectorUnidad = up.getSector().getCodigo().trim().concat(up.getCodigo().trim());
            if (codigoSectorUnidad.equalsIgnoreCase(codigoUnidad.trim())) {
                return up;
            }
        }
        String errorMsg = "Error en la línea " + line + ", columna "
                + CamposArchivoModificarPuestosMasivoEnum.UNIDAD_PRESUPUESTARIA.getColumna()
                + ". No existe unidad presupuestaria con código " + codigoUnidad;
        cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);

        return null;
    }


    /**
     * Busca la entidad por código en las listas cargadas en memoria
     * @param line
     * @param campo
     * @param listaEntidad
     * @param codigoUnidad
     * @return
     */
    private Object buscarEntidadPorCodigo(int line, CamposArchivoModificarPuestosMasivoEnum campo, List<?> listaEntidad,
            String codigoUnidad) {
        try {
            Method methodGetCodigo = listaEntidad.get(0).getClass().getDeclaredMethod("getCodigo", null);
            Method methodGetId = listaEntidad.get(0).getClass().getDeclaredMethod("getId", null);
            for (Object o : listaEntidad) {
                String codigo = methodGetCodigo.invoke(o, null).toString();
                if (codigo.equalsIgnoreCase(codigoUnidad.trim())) {
                    return o;
                }
            }
            String errorMsg = "Error en la línea " + line + ", columna " + campo.getColumna() + ". No existe \""
                    + campo.getDescripcion() + "\" " + codigoUnidad;
            if (campo.getCodigo().equalsIgnoreCase("CEAP")) {
                errorMsg = errorMsg +" o el estado especificado no está habilitado para el régimen laboral del puesto.";
            }

            cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);

        } catch (Exception ex) {
            Logger.getLogger(CargaMasivaModificarPuestoControlador.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    /**
     * Valida si el campo puede ser modificado con el puesto ocupado y 
     * si el puesto ya tiene asignado el valor del campo especificado en el archivo
     * @param line
     * @param puesto
     * @param campoEnArchivo
     * @param enumerador
     * @return
     */
    private boolean validarVacanteOAsignacionPrevia(int line, DistributivoDetalle puesto, /*Long*/Object campoEnArchivo,
            CamposArchivoModificarPuestosMasivoEnum enumerador) {
        String errorMsg;
        boolean error = false;
       Object campoEnPuesto = 0L;
        switch (enumerador) {
            case ESCALA_OCUPACIONAL:
                campoEnPuesto = puesto.getIdEscalaOcupacional();
                break;
            case ESTADO_ADMINISTRACION_PUESTO:
                campoEnPuesto = puesto.getEstadosAdmPuestosRegimenLaboralId();
                break;
            case MODALIDAD_LABORAL:
                campoEnPuesto = puesto.getDistributivo().getIdModalidadLaboral();
                break;
            case UNIDAD_ORGANIZACIONAL:
                campoEnPuesto = puesto.getDistributivo().getIdUnidadOrganizacional();
                break;
            case DENOMINACION_PUESTO:
                campoEnPuesto = puesto.getIdDenominacionPuesto();
                break;
            case UNIDAD_PRESUPUESTARIA:
                campoEnPuesto = puesto.getUnidadPresupuestaria().getId();
                break;
            case PARTIDA_INDIVIDUAL:
                campoEnPuesto = puesto.getPartidaIndividual();
                break;
            case GRUPO_PRESUPUESTARIO:
                campoEnPuesto = puesto.getGrupoPresupuestario();
                break;
            /*case CERTIFICACION_PRESUPUESTARIA:
                campoEnPuesto = puesto.getCertificacionPresupuestaria();
                break;*/
        }

        //if (puesto != null) {
        if (puesto.getServidor() != null) {
            if (enumerador != CamposArchivoModificarPuestosMasivoEnum.ESTADO_ADMINISTRACION_PUESTO) {
                error = true;
                errorMsg = "Error en la línea " + line + ", columna "
                        + enumerador.getColumna()
                        + ". No se permite modificar el campo \""
                        + enumerador.getDescripcion()
                        .toUpperCase()
                        + "\" porque el puesto especificado se encuentra ocupado.";
                cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);
            }
        } else if (campoEnPuesto.equals(campoEnArchivo)) {
            error = true;
            errorMsg = "Error en la línea " + line + ", columna "
                    + enumerador.getColumna()
                    + ". El puesto ya tiene asignado el valor de " + enumerador.getDescripcion() + " especificado.";
            cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);
        }
        //}
        return error;
    }

    /**
     * Valida que el grupo presupuestario sea de los permitidos para la unidad presupuestaria
     *
     * @param line
     * @param grupo
     * @param unidadPresupuestaria
     * @param puesto
     * @return
     */
    private boolean validarGrupoPresupuestario(int line, String grupo, UnidadPresupuestaria unidadPresupuestaria,
            DistributivoDetalle puesto) {

        String[] gruposPosibles = unidadPresupuestaria != null
                ? unidadPresupuestaria.getGrupoPresupuestario().split(",")
                : puesto.getUnidadPresupuestaria().getGrupoPresupuestario().split(",");
        //: (puesto != null ? puesto.getUnidadPresupuestaria().getGrupoPresupuestario().split(",")
        //        : new String[]{});

        if (gruposPosibles.length == 0) {
            String errorMsg = "Error en la línea " + line + ", columna "
                    + CamposArchivoModificarPuestosMasivoEnum.GRUPO_PRESUPUESTARIO.getColumna()
                    + ". La unidad presupuestaria del puesto no tiene definidos " + "grupos presupuestarios ";
            cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);
            return false;
        }
        if (!Arrays.asList(gruposPosibles).contains(grupo)) {
            String errorMsg = "Error en la línea " + line + ", columna "
                    + CamposArchivoModificarPuestosMasivoEnum.GRUPO_PRESUPUESTARIO.getColumna()
                    + ". Para la unidad presupuestaria del puesto solo se permiten los siguientes "
                    + "grupos presupuestarios: " + Arrays.toString(gruposPosibles);
            cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);
            return false;
        }
        return true;
    }

    /**
     * limpia la información en memoria relativa al archivo subido.
     */
    public void limpiarDatosArchivoCargado() {
        cargaMasivaModificarPuestoHelper.setNombreArchivoCSV("");
        cargaMasivaModificarPuestoHelper.getFilas().clear();
        cargaMasivaModificarPuestoHelper.getDistributivosDetalleMasivo().clear();
        cargaMasivaModificarPuestoHelper.getErroresEnArchivo().clear();
        cargaMasivaModificarPuestoHelper.setArchivoSinError(false);
    }

    /**
     * cancela la carga masiva
     */
    public void cancelar() {
        limpiarDatosArchivoCargado();
    }

    /**
     * Obtiene archivo del servidor con explicación del formato del archivo para la carga masiva
     *
     * @return
     */
    public StreamedContent getFormatoArchivo() {
        InputStream streamOperativo = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().
                getContext()).getResourceAsStream("/pdf/FORMATO_DE_ARCHIVO_PARA_EDICION_MASIVA_DE_PUESTOS.pdf");
        return new DefaultStreamedContent(streamOperativo, "application/pdf",
                "FORMATO_DE_ARCHIVO_PARA_EDICION_MASIVA_DE_PUESTOS.pdf");
    }

    /**
     * Carga los estados actuales de puesto según regimen del puesto
     *
     * @param puesto
     */
    private void cargarEstadosDelRegimen(Long regimenId) {
        try {
            cargaMasivaModificarPuestoHelper.getEstadosAdministracionPuesto().clear();
            cargaMasivaModificarPuestoHelper.getEstadoAdministracionPuestoRegimenLaborales().clear();
            cargaMasivaModificarPuestoHelper.setEstadoAdministracionPuestoRegimenLaborales(
                    administracionPuestoRegimenLaboralDao.buscarPorRegimenLaboralId(regimenId));
            for (EstadoAdministracionPuestoRegimenLaboral eaprl
                    : cargaMasivaModificarPuestoHelper.getEstadoAdministracionPuestoRegimenLaborales()) {
                cargaMasivaModificarPuestoHelper.getEstadosAdministracionPuesto().add(eaprl.
                        getEstadoAdministracionPuesto());
            }
        } catch (DaoException ex) {
            Logger.getLogger(CargaMasivaModificarPuestoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Verifica que la partida no esté asignada a algun puesto
     *
     * @param line
     * @param partida
     * @param error
     * @return
     */
    private Boolean validarExistenciaDePartida(int line, String partida, Boolean error) {
        try {
            if (distributivoPuestoServicio.buscarPorPartidaIndividual(partida) != null) {
                String errorMsg = "Error en la línea " + line + ", columna "
                        + CamposArchivoModificarPuestosMasivoEnum.PARTIDA_INDIVIDUAL.getColumna()
                        + ". Ya existe un puesto con la partida individual especificada.";
                cargaMasivaModificarPuestoHelper.getErroresEnArchivo().add(errorMsg);
                return Boolean.TRUE;
            }

        } catch (ServicioException ex) {
            Logger.getLogger(CargaMasivaModificarPuestoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error;
    }

    /**
     * Busca que el estado esté entre los permitidos para el regimen del puesto
     *
     * @param estadoId
     * @return
     */
    private EstadoAdministracionPuestoRegimenLaboral buscarEstadoRegimen(Long estadoId) {
        for (EstadoAdministracionPuestoRegimenLaboral earl : cargaMasivaModificarPuestoHelper.
                getEstadoAdministracionPuestoRegimenLaborales()) {
            if (earl.getEstadoAdministracionPuesto().getId().equals(estadoId)) {
                return earl;
            }
        }
        return null;
    }

}
