/*
 *  ServidorHelper.java
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
 *  10/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.EstadoPuesto;
import ec.com.atikasoft.proteus.modelo.Reclutamiento;
import ec.com.atikasoft.proteus.modelo.ReclutamientoCapacitacion;
import ec.com.atikasoft.proteus.modelo.ReclutamientoInstruccion;
import ec.com.atikasoft.proteus.modelo.ReclutamientoTrayectoriaLaboral;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javolution.util.FastMap;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.UploadedFile;

/**
 * ServidorHelper
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "reclutamientoHelper")
@SessionScoped
@Setter
@Getter
public final class ReclutamientoHelper extends CatalogoPHelper {

    /**
     * nombreServidor.
     */
    private String nombreServidor;

    /**
     * Numero de identificacion.
     */
    private String numeroIdentificacion;

    /**
     *
     */
    private Long codigoPuesto;
    /**
     * tipo de identificacion.
     */
    private String tipoIdentificacion;
    /**
     * Lista de tipo documento.
     */
    private List<SelectItem> tipoDocumento;
    /**
     * tipo licencia Booleana.
     */
    private Boolean tipoCedula;

    /**
     * servidor.
     */
    private Reclutamiento reclutamiento;
    /**
     * servidor.
     */
    private Reclutamiento reclutamientoEditDelete;

    /**
     * listaServidores.
     */
    private List<Servidor> listaServidores;
    /**
     * listaServidores.
     */
    private List<Reclutamiento> listaReclutamientos;
    /**
     * listaServidores.
     */
    private List<SelectItem> listaRegimenLaboral;
    /**
     * id regimen laboral.
     */
    private Long regimenLaboralId;
    /**
     * listaNivelOcupacional.
     */
    private List<SelectItem> listaNivelOcupacional;
    /**
     * nivelOcupacionalId.
     */
    private Long nivelOcupacionalId;
    /**
     * listaEscalaOcupacional.
     */
    private List<SelectItem> listaEscalaOcupacional;
    /**
     * escalaOcupacionalId.
     */
    private Long escalaOcupacionalId;
    /**
     * Lista de unidades organizacionales.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales;
    /**
     * listaModalidadLaboral
     */
    private List<SelectItem> listaModalidadLaboral;

    /**
     * Campo para el filtro.
     */
    private String unidadAdministrativaNombre;
    /**
     * Campo para el filtro.
     */
    private Long unidadAdministrativaId;
    /**
     * modalidad laboral id.
     */
    private Long modalidadLaboralId;

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaCatalogoGenero;
    /**
     * reclutamientos.
     */
    private ReclutamientoInstruccion reclutamientoInstruccion;
    /**
     * lista de reclutamientos.
     */
    private List<ReclutamientoInstruccion> listaReclutamientoInstruccion;
    /**
     * lista de reclutamientos.
     */
    private List<Reclutamiento> listaReclutamientoNemonico;
    /**
     * nivel instruccion.
     */
    private List<SelectItem> ListaNivelInstruccion;
    /**
     * presentar areas de las nuebas tablas.
     */
    private Boolean presentarArea;

    /**
     * presentar areas de las nuebas tablas.
     */
    private Boolean esNuevoDialog;
    /**
     * reclutamientos.
     */
    private ReclutamientoInstruccion reclutamientoInstruccionEditDelete;
    /**
     * listaReclutamientoCapacitacion.
     */
    private List<ReclutamientoCapacitacion> listaReclutamientoCapacitacion;
    /**
     * ReclutamientoCapacitacion
     */
    private ReclutamientoCapacitacion reclutamientoCapacitacion;
    /**
     * ReclutamientoCapacitacion
     */
    private ReclutamientoCapacitacion reclutamientoCapacitacionEditDelete;
    /**
     * ReclutamientoTrayectoriaLaboral.
     */
    private List<ReclutamientoTrayectoriaLaboral> listaReclutamientoTrayectoriaLaborales;
    /**
     * ReclutamientoTrayectoriaLaboral
     */
    private ReclutamientoTrayectoriaLaboral reclutamientoTrayectoriaLaboral;
    /**
     * ReclutamientoTrayectoriaLaboral
     */
    private ReclutamientoTrayectoriaLaboral reclutamientoTrayectoriaLaboralEditDelete;
    private Long anios;
    /**
     * lista que busca los puestos.
     */
    private List<DistributivoDetalle> ListaDistributivoDetalles;
    /**
     * Variable para asignar valores de busqueda.
     */
    private BusquedaPuestoVO busquedaPuestoVO;
    /**
     * distributivo detalle.
     */
    private DistributivoDetalle distributivoDetalle;
    /**
     * estado del puesto.
     */
    private EstadoPuesto estadoPuesto;
    /**
     * listaGenero.
     */
    private List<SelectItem> listaGenero;

    /**
     * listaNacionalidad.
     */
    private List<SelectItem> listaNacionalidad;

    /**
     * Archivo subido para datos generales reclutamiento masivo
     */
    private UploadedFile archivoRDG;
    /**
     *
     */
    private Map<String, Map<String, String>> camposArchivoRDG;
    /**
     * Archivo subido para capacitacion reclutamiento masivo
     */
    private UploadedFile archivoRC;
    /**
     *
     */
    private Map<String, Map<String, String>> camposArchivoRC;
    /**
     * Archivo subido para datos de instruccion reclutamiento masivo
     */
    private UploadedFile archivoRI;
    /**
     *
     */
    private Map<String, Map<String, String>> camposArchivoRI;
    /**
     * Archivo subido para datos de trayectoria laboral reclutamiento masivo
     */
    private UploadedFile archivoRTL;
    /**
     *
     */
    private Map<String, Map<String, String>> camposArchivoRTL;
    /**
     *
     */
    private Map<String, List<SelectItem>> mapaListasCatalogos;

    /**
     * Variable que alamacena los datos que serán guardados masivamente
     *
     * @return
     */
    private Map<String, List<String[]>> lineasGuardarMasivamente;

    /**
     * 
     */
    private Boolean registroManualNombres;

    /**
     * Constructor por defecto.
     */
    public ReclutamientoHelper() {
        super();
        iniciador();
    }

    public void iniciador() {
        setTipoDocumento(new ArrayList<SelectItem>());
        setListaServidores(new ArrayList<Servidor>());
        setListaReclutamientos(new ArrayList<Reclutamiento>());
        setReclutamientoEditDelete(new Reclutamiento());
        setListaRegimenLaboral(new ArrayList<SelectItem>());
        setListaNivelOcupacional(new ArrayList<SelectItem>());
        setListaEscalaOcupacional(new ArrayList<SelectItem>());
        listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();
        setListaModalidadLaboral(new ArrayList<SelectItem>());
        setListaCatalogoGenero(new ArrayList<SelectItem>());
        setReclutamiento(new Reclutamiento());
        setListaReclutamientos(new ArrayList<Reclutamiento>());
        setListaNivelOcupacional(new ArrayList<SelectItem>());
        setListaNivelInstruccion(new ArrayList<SelectItem>());
        setListaReclutamientoInstruccion(new ArrayList<ReclutamientoInstruccion>());
        setReclutamientoInstruccionEditDelete(new ReclutamientoInstruccion());
        setListaReclutamientoCapacitacion(new ArrayList<ReclutamientoCapacitacion>());
        setReclutamientoCapacitacion(new ReclutamientoCapacitacion());
        setReclutamientoCapacitacionEditDelete(new ReclutamientoCapacitacion());
        setListaReclutamientoTrayectoriaLaborales(new ArrayList<ReclutamientoTrayectoriaLaboral>());
        setReclutamientoTrayectoriaLaboral(new ReclutamientoTrayectoriaLaboral());
        setReclutamientoTrayectoriaLaboralEditDelete(new ReclutamientoTrayectoriaLaboral());
        setListaReclutamientoNemonico(new ArrayList<Reclutamiento>());
        setListaDistributivoDetalles(new ArrayList<DistributivoDetalle>());
        setBusquedaPuestoVO(new BusquedaPuestoVO());
        setDistributivoDetalle(new DistributivoDetalle());
        setEstadoPuesto(new EstadoPuesto());
        setListaGenero(new ArrayList<SelectItem>());
        setListaNacionalidad(new ArrayList<SelectItem>());
        setMapaListasCatalogos(new FastMap<String, List<SelectItem>>());
        setLineasGuardarMasivamente(new FastMap<String, List<String[]>>());
        System.out.println("setRegistroManualNombres(2)");
        setRegistroManualNombres(Boolean.FALSE);
    }

}
