/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Desconcentrado;
import ec.com.atikasoft.proteus.modelo.Horario;
import ec.com.atikasoft.proteus.modelo.HorarioDetalle;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.HorarioMonitoreoVO;
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
@ManagedBean(name = "horarioMonitoreoHelper")
@SessionScoped
@Setter
@Getter
public class HorarioMonitoreoHelper extends CatalogoHelper {
    
    /**
     * 
     */
    private String opcionTipoServidorHorario;
    
    /**
     * 
     */
    private List<SelectItem> opcionesTipoServidorHorario;
    
    /**
     * 
     */
    private List<HorarioDetalle> listaHorarioDetalles;
    
    /**
     * 
     */
    private Horario horario;
    
    /**
     * 
     */
    private List<Desconcentrado> desconcentrados;
    
    /**
     * 
     */
    private List<HorarioMonitoreoVO> listaHorarioMonitoreoVO;
    
    /**
     * Constructor por defecto.
     */
    public HorarioMonitoreoHelper() {
        super();
    }

    /**
     * Este método Inicializa las variables del Helper.
     */
    @PostConstruct
    public final void iniciador() {
        listaHorarioDetalles = new ArrayList<>();
        horario = new Horario();
        desconcentrados = new ArrayList<>();
        listaHorarioMonitoreoVO = new ArrayList<>();
    }
    
    /**
     * Retorna el nombre del archivo excel generado a partir de la lista de asiganciones de horario
     * @return 
     */
    public String getNombreArchivo() {
        return "Reporte_Monitoreo_Horarios_" + UtilFechas.formatear2(new Date());
    }

}
