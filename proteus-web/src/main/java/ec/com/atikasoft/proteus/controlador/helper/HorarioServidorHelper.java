/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.enums.TipoServidorEstadoHorarioEnum;
import ec.com.atikasoft.proteus.modelo.Desconcentrado;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Horario;
import ec.com.atikasoft.proteus.modelo.HorarioDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.distributivo.Distributivo;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "horarioServidorHelper")
@SessionScoped
@Setter
@Getter
public class HorarioServidorHelper extends CatalogoHelper {

    /**
     *
     */
    private Horario horario;

    /**
     * Lista de Horarios.
     */
    private List<Horario> listaHorario;

    /**
     * Lista de Horarios Detalles.
     */
    private List<HorarioDetalle> listaHorarioDetalles;

    /**
     * Lista de opciones de horarios
     */
    private List<SelectItem> opcionesHorario;

    /**
     *
     */
    private List<Desconcentrado> listaDesconcentrados;

    /**
     *
     */
    private Desconcentrado desconcentrado;

    /**
     * Lista de opciones asociadas al tipo
     */
    private List<SelectItem> opcionesTipoServidorHorario;

    /**
     * Opcion seleccionada
     */
    private String opcionTipoServidorHorario;

    /**
     * Lista de puestos
     */
    private List<DistributivoDetalle> listaPuestos;

    /**
     * Puesto al que pertenece el servidor seleccionado para asignarle horario
     */
    private DistributivoDetalle puesto;
    
    /**
     * Lista de servidores a los que se les asignan horario
     * @return 
     */
    private List<DistributivoDetalle> listaAsignacionHorarios;
    
    /**
     * Indica si se esta realizando asignacion masiva de horario
     */
    private Boolean asignacionMasivaActivada;

    /**
     * Constructor por defecto.
     */
    public HorarioServidorHelper() {
        super();
    }

    /**
     * Este método Inicializa las variables del Helper.
     */
    @PostConstruct
    public final void iniciador() {
        listaHorario = new ArrayList<>();
        listaHorarioDetalles = new ArrayList<>();
        opcionesHorario = new ArrayList<>();
        opcionesTipoServidorHorario = new ArrayList<>();
        listaPuestos = new ArrayList<>();
        listaDesconcentrados = new ArrayList<>();

        puesto = new DistributivoDetalle();
        Distributivo d = new Distributivo();
        puesto.setDistributivo(d);
        Servidor s = new Servidor();
        horario = new Horario();
        s.setHorario(horario);
        puesto.setServidor(s);

    }
    
    /**
     * Idica si se est[a buscando los servidores que no tienen horario definido
     * @return 
     */
    public Boolean getSinHorario() {
        return opcionTipoServidorHorario != null 
                ? opcionTipoServidorHorario.equals(TipoServidorEstadoHorarioEnum.SIN_HORARIO.getCodigo())
                : Boolean.FALSE;
    }
    
    /**
     * Retorna el nombre del archivo excel generado a partir de la lista de asiganciones de horario
     * @return 
     */
    public String getNombreArchivo() {
        return "Reporte_Horarios_Servidores_" + UtilFechas.formatear2(new Date());
    }

}
