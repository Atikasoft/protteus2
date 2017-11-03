/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.ExcepcionAsistenciaPersonal;
import ec.com.atikasoft.proteus.modelo.Servidor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "excepcionAsistenciaPersonalHelper")
@SessionScoped
@Setter
@Getter
public class ExcepcionAsistenciaPersonalHelper extends CatalogoHelper {
    /**
     * Entidad Principal.
     */
    private ExcepcionAsistenciaPersonal excepcion;
    
    /**
     * Lista de excepciones
     */
    private List<ExcepcionAsistenciaPersonal> listaExcepciones;
    
    /**
     * Servidor asociado a la excepcion seleccionada
     */
    private Servidor servidorExcepcionActual;

    /**
     * Constructor por defecto.
     */
    public ExcepcionAsistenciaPersonalHelper() {
        super();
    }

    /**
     * Este método Inicializa las variables del Helper.
     */
    @PostConstruct
    public final void iniciador() {
        listaExcepciones = new ArrayList<>();
        servidorExcepcionActual = new Servidor();
    }
    
}
