/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Reloj;
import java.util.ArrayList;
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
@ManagedBean(name = "relojHelper")
@SessionScoped
@Setter
@Getter
public class RelojHelper extends CatalogoHelper {
    
    /**
     * Entodad principal
     */
    private Reloj reloj;
    
    /**
     * Lista de relojes vigentes
     */
    private List<Reloj> listaRelojesVigentes;
    
    /**
     * Lista de seleccion de modelos de reloj
     */
    private List<SelectItem> listaModelosReloj;
    
    /**
     * 
     */
    private Long modeloRelojSeleccionado;
    
    /**
     * Constructor por defecto.
     */
    public RelojHelper() {
        super();
    }

    /**
     * Este método Inicializa las variables del Helper.
     */
    @PostConstruct
    public final void iniciador() {
        reloj = new Reloj();
        listaRelojesVigentes = new ArrayList<>();
        listaModelosReloj = new ArrayList<>();
    }
    
}
