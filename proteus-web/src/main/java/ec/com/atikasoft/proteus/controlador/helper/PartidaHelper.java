/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Partida;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author elsa.angamarca@atikasoft.com.ec
 */
@ManagedBean(name = "partidaHelper")
@SessionScoped
public class PartidaHelper extends CatalogoHelper {

    /**
     * Entidad Principal.
     */
    private Partida partida;
    /**
     * Entidad de Transacción.
     */
    private Partida partidaEditDelete;

    /**
     * Lista de Partidas.
     */
    private List<Partida> listaPartida;
    
    /**
     * Lista de Partidas por nemónico.
     */
     private List<Partida> listaPartidaCodigo;

    /**
     * Constructor por defecto.
     */
    public PartidaHelper() {
        super();
        iniciador();
    }

    /**
     * Este método Inicializa las variables del Helper.
     */
    public final void iniciador() {
        partida=new Partida();
        partidaEditDelete=new Partida();
        listaPartida=new ArrayList<Partida>();    
        listaPartidaCodigo=new ArrayList<Partida>();
        setEsNuevo(Boolean.TRUE);
    }

    /**
     * @return the partida
     */
    public Partida getPartida() {
        return partida;
    }

    /**
     * @param partida the partida to set
     */
    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    /**
     * @return the partidaEditDelete
     */
    public Partida getPartidaEditDelete() {
        return partidaEditDelete;
    }

    /**
     * @param partidaEditDelete the partidaEditDelete to set
     */
    public void setPartidaEditDelete(Partida partidaEditDelete) {
        this.partidaEditDelete = partidaEditDelete;
    }

    /**
     * @return the listaPartida
     */
    public List<Partida> getListaPartida() {
        return listaPartida;
    }

    /**
     * @param listaPartida the listaPartida to set
     */
    public void setListaPartida(List<Partida> listaPartida) {
        this.listaPartida = listaPartida;
    }

    /**
     * @return the listaPartidaCodigo
     */
    public List<Partida> getListaPartidaCodigo() {
        return listaPartidaCodigo;
    }

    /**
     * @param listaPartidaCodigo the listaPartidaCodigo to set
     */
    public void setListaPartidaCodigo(List<Partida> listaPartidaCodigo) {
        this.listaPartidaCodigo = listaPartidaCodigo;
    }
}
