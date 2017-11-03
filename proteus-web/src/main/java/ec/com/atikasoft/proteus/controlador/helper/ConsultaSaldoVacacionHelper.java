/*
 *  ConsultaSaldoVacacionHelper.java
 *  proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  26/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionDetalle;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Banco
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "consultaSaldoVacacionHelper")
@SessionScoped
public class ConsultaSaldoVacacionHelper extends CatalogoHelper {

    /**
     * clase vacacionSolicitud.
     */
    private VacacionSolicitud vacacionSolicitud;
    /**
     * Obtener usuario conectado.
     */
    private UsuarioVO usuario;
    /**
     * Variables temporales para mostrar datos calculados
     */
    private String cadenaSaldo;

    /**
     *
     */
    private String cadenaSaldoProporcional;

    /**
     *
     */
    private String cadenaSaldoDiasPerdidos;

    /**
     *
     */
    private String cadenaSaldoDetalle;
    /**
     *
     */
    private Integer saldo[];

    /**
     *
     */
    private Integer saldoProporcional[];
    
    /**
     * 
     */
    private Integer saldoDiasPerdidos[];

    /**
     *
     */
    private Vacacion vacacion;
    /**
     *
     */
    private List<VacacionDetalle> listaVacacionDetalle;
//     private Vacacion vacacion;

    /**
     * Constructor por defecto.
     */
    public ConsultaSaldoVacacionHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del BancoHelper.
     */
    public final void iniciador() {
        vacacionSolicitud = new VacacionSolicitud();
        listaVacacionDetalle = new ArrayList<VacacionDetalle>();
        setSaldo(new Integer[3]);
    }

    /**
     * @return the vacacionSolicitud
     */
    public VacacionSolicitud getVacacionSolicitud() {
        return vacacionSolicitud;
    }

    /**
     * @param vacacionSolicitud the vacacionSolicitud to set
     */
    public void setVacacionSolicitud(VacacionSolicitud vacacionSolicitud) {
        this.vacacionSolicitud = vacacionSolicitud;
    }

    /**
     * @return the usuario
     */
    public UsuarioVO getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioVO usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the cadenaSaldo
     */
    public String getCadenaSaldo() {
        return cadenaSaldo;
    }

    /**
     * @param cadenaSaldo the cadenaSaldo to set
     */
    public void setCadenaSaldo(String cadenaSaldo) {
        this.cadenaSaldo = cadenaSaldo;
    }

    /**
     * @return the saldo
     */
    public Integer[] getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(Integer[] saldo) {
        this.saldo = saldo;
    }

    /**
     * @return the listaVacacionDetalle
     */
    public List<VacacionDetalle> getListaVacacionDetalle() {
        return listaVacacionDetalle;
    }

    /**
     * @param listaVacacionDetalle the listaVacacionDetalle to set
     */
    public void setListaVacacionDetalle(List<VacacionDetalle> listaVacacionDetalle) {
        this.listaVacacionDetalle = listaVacacionDetalle;
    }

    /**
     * @return the vacacion
     */
    public Vacacion getVacacion() {
        return vacacion;
    }

    /**
     * @param vacacion the vacacion to set
     */
    public void setVacacion(Vacacion vacacion) {
        this.vacacion = vacacion;
    }

    /**
     * @return the cadenaSaldoProporcional
     */
    public String getCadenaSaldoProporcional() {
        return cadenaSaldoProporcional;
    }

    /**
     * @param cadenaSaldoProporcional the cadenaSaldoProporcional to set
     */
    public void setCadenaSaldoProporcional(String cadenaSaldoProporcional) {
        this.cadenaSaldoProporcional = cadenaSaldoProporcional;
    }

    /**
     * @return the saldoProporcional
     */
    public Integer[] getSaldoProporcional() {
        return saldoProporcional;
    }

    /**
     * @param saldoProporcional the saldoProporcional to set
     */
    public void setSaldoProporcional(Integer[] saldoProporcional) {
        this.saldoProporcional = saldoProporcional;
    }

    /**
     * @return the cadenaSaldoDetalle
     */
    public String getCadenaSaldoDetalle() {
        return cadenaSaldoDetalle;
    }

    /**
     * @param cadenaSaldoDetalle the cadenaSaldoDetalle to set
     */
    public void setCadenaSaldoDetalle(String cadenaSaldoDetalle) {
        this.cadenaSaldoDetalle = cadenaSaldoDetalle;
    }

    /**
     * @return the cadenaSaldoDiasPerdidos
     */
    public String getCadenaSaldoDiasPerdidos() {
        return cadenaSaldoDiasPerdidos;
    }

    /**
     * @param cadenaSaldoDiasPerdidos the cadenaSaldoDiasPerdidos to set
     */
    public void setCadenaSaldoDiasPerdidos(String cadenaSaldoDiasPerdidos) {
        this.cadenaSaldoDiasPerdidos = cadenaSaldoDiasPerdidos;
    }

    /**
     * @return the saldoDiasPerdidos
     */
    public Integer[] getSaldoDiasPerdidos() {
        return saldoDiasPerdidos;
    }

    /**
     * @param saldoDiasPerdidos the saldoDiasPerdidos to set
     */
    public void setSaldoDiasPerdidos(Integer[] saldoDiasPerdidos) {
        this.saldoDiasPerdidos = saldoDiasPerdidos;
    }

}
