/**
 * VacacionRecuperacionHelper.java PROTEUS V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the
 * confidential and proprietary information of Proteus ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with
 * PROTEUS.
 *
 * PROTEUS Quito - Ecuador
 *
 * 29/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import ec.com.atikasoft.proteus.vo.BusquedaVacacionVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * VacacionRecuperacionHelper
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "vacacionRecuperacionHelper")
@SessionScoped
public class VacacionRecuperacionHelper extends CatalogoHelper {

    /**
     * clase vacacionSolicitud.
     */
    private VacacionSolicitud vacacionSolicitud;
    /**
     * clase vacacionSolicitud puesto para editar.
     */
    private VacacionSolicitud vacacionSolicitudEditDelete;
    /**
     * lista de vacacionSolicitudes.
     */
    private List<VacacionSolicitud> listaVacacionSolicitudes;
    private List<Vacacion> listaVacacion;

    /**
     * Variables para opciones tipo vacacion y estado vacacion.
     */
    private List<SelectItem> listaOpcionesTipoVacacion;
    private List<SelectItem> listaOpcionesEstadoVacacion;

    /**
     * Lista de unidades organizacionales.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaTipoDocumento = new ArrayList<SelectItem>();
    /**
     * Variable para asignar valores de busqueda.
     */
    private BusquedaVacacionVO busquedaVacacionVO = new BusquedaVacacionVO();
    /**
     * Variable quitar días de la solicitud Vacaciones.
     */
    private Boolean editar = Boolean.FALSE;
    private Boolean esRRHH = Boolean.FALSE;

    /**
     * Constructor por defecto.
     */
    public VacacionRecuperacionHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del VacacionSolicitudHelper.
     */
    public final void iniciador() {
        setVacacionSolicitud(new VacacionSolicitud());
        getVacacionSolicitud().setMinutosImputados(BigDecimal.ZERO);
        setVacacionSolicitudEditDelete(new VacacionSolicitud());
        setListaVacacionSolicitudes(new ArrayList<VacacionSolicitud>());
        setListaVacacion(new ArrayList<Vacacion>());
        setListaOpcionesTipoVacacion(new ArrayList<SelectItem>());
        setListaOpcionesEstadoVacacion(new ArrayList<SelectItem>());
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
    public void setVacacionSolicitud(final VacacionSolicitud vacacionSolicitud) {
        this.vacacionSolicitud = vacacionSolicitud;
    }

    /**
     * @return the vacacionSolicitudEditDelete
     */
    public VacacionSolicitud getVacacionSolicitudEditDelete() {
        return vacacionSolicitudEditDelete;
    }

    /**
     * @param vacacionSolicitudEditDelete the vacacionSolicitudEditDelete to set
     */
    public void setVacacionSolicitudEditDelete(final VacacionSolicitud vacacionSolicitudEditDelete) {
        this.vacacionSolicitudEditDelete = vacacionSolicitudEditDelete;
    }

    /**
     * @return the listaVacacionSolicitudes
     */
    public List<VacacionSolicitud> getListaVacacionSolicitudes() {
        return listaVacacionSolicitudes;
    }

    /**
     * @param listaVacacionSolicitudes the listaVacacionSolicitudes to set
     */
    public void setListaVacacionSolicitudes(final List<VacacionSolicitud> listaVacacionSolicitudes) {
        this.listaVacacionSolicitudes = listaVacacionSolicitudes;
    }

    /**
     * @return the listaVacacion
     */
    public List<Vacacion> getListaVacacion() {
        return listaVacacion;
    }

    /**
     * @param listaVacacion the listaVacacion to set
     */
    public void setListaVacacion(List<Vacacion> listaVacacion) {
        this.listaVacacion = listaVacacion;
    }

    /**
     * @return the listaOpcionesTipoVacacion
     */
    public List<SelectItem> getListaOpcionesTipoVacacion() {
        return listaOpcionesTipoVacacion;
    }

    /**
     * @param listaOpcionesTipoVacacion the listaOpcionesTipoVacacion to set
     */
    public void setListaOpcionesTipoVacacion(List<SelectItem> listaOpcionesTipoVacacion) {
        this.listaOpcionesTipoVacacion = listaOpcionesTipoVacacion;
    }

    /**
     * @return the listaOpcionesEstadoVacacion
     */
    public List<SelectItem> getListaOpcionesEstadoVacacion() {
        return listaOpcionesEstadoVacacion;
    }

    /**
     * @param listaOpcionesEstadoVacacion the listaOpcionesEstadoVacacion to set
     */
    public void setListaOpcionesEstadoVacacion(List<SelectItem> listaOpcionesEstadoVacacion) {
        this.listaOpcionesEstadoVacacion = listaOpcionesEstadoVacacion;
    }

    /**
     * @return the listaUnidadesOrganizacionales
     */
    public List<UnidadOrganizacional> getListaUnidadesOrganizacionales() {
        return listaUnidadesOrganizacionales;
    }

    /**
     * @param listaUnidadesOrganizacionales the listaUnidadesOrganizacionales to
     * set
     */
    public void setListaUnidadesOrganizacionales(List<UnidadOrganizacional> listaUnidadesOrganizacionales) {
        this.listaUnidadesOrganizacionales = listaUnidadesOrganizacionales;
    }

    /**
     * @return the listaTipoDocumento
     */
    public List<SelectItem> getListaTipoDocumento() {
        return listaTipoDocumento;
    }

    /**
     * @param listaTipoDocumento the listaTipoDocumento to set
     */
    public void setListaTipoDocumento(List<SelectItem> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    /**
     * @return the busquedaVacacionVO
     */
    public BusquedaVacacionVO getBusquedaVacacionVO() {
        return busquedaVacacionVO;
    }

    /**
     * e
     *
     * @param busquedaVacacionVO the busquedaVacacionVO to set
     */
    public void setBusquedaVacacionVO(BusquedaVacacionVO busquedaVacacionVO) {
        this.busquedaVacacionVO = busquedaVacacionVO;
    }

    /**
     * @return the editar
     */
    public Boolean getEditar() {
        return editar;
    }

    /**
     * @param editar the editar to set
     */
    public void setEditar(Boolean editar) {
        this.editar = editar;
    }

    /**
     * @return the esRRHH
     */
    public Boolean getEsRRHH() {
        return esRRHH;
    }

    /**
     * @param esRRHH the esRRHH to set
     */
    public void setEsRRHH(Boolean esRRHH) {
        this.esRRHH = esRRHH;
    }
}
