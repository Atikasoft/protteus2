/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Desconcentrado;
import ec.com.atikasoft.proteus.modelo.Horario;
import ec.com.atikasoft.proteus.modelo.HorarioDesconcentrado;
import ec.com.atikasoft.proteus.modelo.HorarioDetalle;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Leydis Garzon
 */
@ManagedBean(name = "horarioHelper")
@SessionScoped
@Setter
@Getter
public class HorarioHelper extends CatalogoHelper {
    /**
     * Entidad Principal.
     */
    private Horario horario;
    /**
     * Entidad de Transacción.
     */
    private Horario horarioEditDelete;

    /**
     * Lista de Horarios.
     */
    private List<Horario> listaHorario;

    /**
     * Lista de Horarios Detalles.
     */
    private List<HorarioDetalle> listaHorarioDetalles;
    
    /**
     * Lista de Horarios por nemónico.
     */
     private List<Horario> listaHorarioPorNombre;
     
     /**
      * Lista de opciones de horarios
      */
     private List<SelectItem> opcionesHorarios;
     
     /**
      * 
      */
     private List<Desconcentrado> listaDesconcentrados;
     
     /**
      * 
      */
     private Desconcentrado desconcentrado;
     
     /**
      * 
      */
     private List<Horario> horariosAsignables;
     
     /**
      * 
      */
     private List<Horario> horariosAsignados;
     
     /**
      * 
      */
     private DualListModel<Horario> seleccionHorarios;
     
     /**
      * Horarios ya asignados a  unidad desconcetrada seleccionada
      */
     private List<HorarioDesconcentrado> horariosYaAsigados;

    /**
     * Constructor por defecto.
     */
    public HorarioHelper() {
        super();
    }

    /**
     * Este método Inicializa las variables del Helper.
     */
    @PostConstruct
    public final void iniciador() {
        horario=new Horario();
        horarioEditDelete=new Horario();
        listaHorario=new ArrayList<>();    
        listaHorarioPorNombre=new ArrayList<>();
        listaHorarioDetalles=new ArrayList<>();
        opcionesHorarios = new ArrayList<>();
        listaDesconcentrados = new ArrayList<>();
        
        horariosAsignables = new ArrayList<>();
        horariosAsignados = new ArrayList<>();
        seleccionHorarios = new DualListModel<>();
        horariosYaAsigados = new ArrayList<>();
    }
    
}
