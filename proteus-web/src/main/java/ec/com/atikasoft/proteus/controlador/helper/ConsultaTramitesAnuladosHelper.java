/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Tramite;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@ManagedBean(name = "tramitesAnuladosHelper")
@SessionScoped
@Getter
@Setter
public class ConsultaTramitesAnuladosHelper extends CatalogoHelper {

    /**
     * Lista de Trámites Anulados.
     */
    private List<Tramite> listaTramitesAnulados;

    /**
     * Constructor por defecto.
     */
    public ConsultaTramitesAnuladosHelper() {
        super();
        iniciador();
    }

    /**
     * Este método Inicializa las variables del Helper.
     */
    public final void iniciador() {
        listaTramitesAnulados = new ArrayList<>();
    }  

}
