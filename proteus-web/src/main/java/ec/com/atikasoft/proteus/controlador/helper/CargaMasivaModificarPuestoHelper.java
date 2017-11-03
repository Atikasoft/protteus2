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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.StreamedContent;

/**
 * BusquedaPuestoHelper.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "cargaMasivaModificarPuestoHelper")
@SessionScoped
public class CargaMasivaModificarPuestoHelper implements Serializable {

   
    /**
     * lista de ids de modalidades para carga masiva.
     */
    private Map<Integer, Long> idsModalidadesMap = new HashMap<>();
    
    /**
     * lista de puestos para carga masiva.
     */
    private List<DistributivoDetalle> distributivosDetalleMasivo ;
    
    /**
     * lista de estados administración puesto - regimenes laborales del puesto.
     */
    private List<EstadoAdministracionPuestoRegimenLaboral> estadoAdministracionPuestoRegimenLaborales;

    /**
     * Lista de todas las modalidades laborales activas en BD
     */
    private List<ModalidadLaboral> modalidadesLaborales;
    
    /**
     * Lista de todas las unidades organizacionales activas en BD
     */
    private List<UnidadOrganizacional> unidadesOrganizacionales;

    /**
     * Lista de todas las unidades presupuestarias activas en BD
     */
    private List<UnidadPresupuestaria> unidadesPresupuestarias;
    
    /**
     * Lista de todos los Estados Administracion Puesto activos en BD
     */
    private List<EstadoAdministracionPuesto> estadosAdministracionPuesto;
   
    /**
     * Lista de todas las Escalas Ocupacionales activas en BD
     */
    private List<EscalaOcupacional> escalasOcupacionales;
    
    /**
     * Lista de todas las Denominaciones de Puesto activas en BD
     */
    private List<DenominacionPuesto> denominacionesPuestos;
    
    /**
     * Lista de todos los Sectores activos en BD
     */
    private List<Sector> sectores;
    
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
    public CargaMasivaModificarPuestoHelper() {
        super();
        iniciador();
    }

    /**
     * Este metodo inicia los parametros de busqueda.
     */
    public final void iniciador() {
        filas = new ArrayList<>();
        erroresEnArchivo = new ArrayList<>();
        denominacionesPuestos = new ArrayList<>();
        escalasOcupacionales = new ArrayList<>();
        unidadesOrganizacionales = new ArrayList<>();
        unidadesPresupuestarias = new ArrayList<>();
        estadosAdministracionPuesto = new ArrayList<>();
        modalidadesLaborales = new ArrayList<>();
        sectores = new ArrayList<>();
        estadoAdministracionPuestoRegimenLaborales = new ArrayList<>();
        distributivosDetalleMasivo = new ArrayList<>();
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

    public List<DistributivoDetalle> getDistributivosDetalleMasivo() {
        return distributivosDetalleMasivo;
    }

    public void setDistributivosDetalleMasivo(List<DistributivoDetalle> distributivosDetalleMasivo) {
        this.distributivosDetalleMasivo = distributivosDetalleMasivo;
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

    public List<ModalidadLaboral> getModalidadesLaborales() {
        return modalidadesLaborales;
    }

    public void setModalidadesLaborales(List<ModalidadLaboral> modalidadesLaborales) {
        this.modalidadesLaborales = modalidadesLaborales;
    }

    public List<UnidadOrganizacional> getUnidadesOrganizacionales() {
        return unidadesOrganizacionales;
    }

    public void setUnidadesOrganizacionales(List<UnidadOrganizacional> unidadesOrganizacionales) {
        this.unidadesOrganizacionales = unidadesOrganizacionales;
    }

    public List<UnidadPresupuestaria> getUnidadesPresupuestarias() {
        return unidadesPresupuestarias;
    }

    public void setUnidadesPresupuestarias(List<UnidadPresupuestaria> unidadesPresupuestarias) {
        this.unidadesPresupuestarias = unidadesPresupuestarias;
    }

    public List<EstadoAdministracionPuesto> getEstadosAdministracionPuesto() {
        return estadosAdministracionPuesto;
    }

    public void setEstadosAdministracionPuesto(List<EstadoAdministracionPuesto> estadosAdministracionPuesto) {
        this.estadosAdministracionPuesto = estadosAdministracionPuesto;
    }

    public List<EscalaOcupacional> getEscalasOcupacionales() {
        return escalasOcupacionales;
    }

    public void setEscalasOcupacionales(List<EscalaOcupacional> escalasOcupacionales) {
        this.escalasOcupacionales = escalasOcupacionales;
    }

    public List<DenominacionPuesto> getDenominacionesPuestos() {
        return denominacionesPuestos;
    }

    public void setDenominacionesPuestos(List<DenominacionPuesto> denominacionesPuestos) {
        this.denominacionesPuestos = denominacionesPuestos;
    }

    public List<Sector> getSectores() {
        return sectores;
    }

    public void setSectores(List<Sector> sectores) {
        this.sectores = sectores;
    }

    public List<EstadoAdministracionPuestoRegimenLaboral> getEstadoAdministracionPuestoRegimenLaborales() {
        return estadoAdministracionPuestoRegimenLaborales;
    }

    public void setEstadoAdministracionPuestoRegimenLaborales(List<EstadoAdministracionPuestoRegimenLaboral> 
            estadoAdministracionPuestoRegimenLaborales) {
        this.estadoAdministracionPuestoRegimenLaborales = estadoAdministracionPuestoRegimenLaborales;
    }
    
}
