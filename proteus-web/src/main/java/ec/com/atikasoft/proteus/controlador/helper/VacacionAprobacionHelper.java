/*
 *  VacacionAprobacionHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  04/11/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.enums.DocumentoPrevioEnum;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;

/**
 * Banco
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "vacacionAprobacionHelper")
@SessionScoped
@Setter
@Getter
public class VacacionAprobacionHelper extends CatalogoHelper {

    /**
     * clase vacacionSolicitud.
     */
    private VacacionSolicitud vacacionSolicitud;

    /**
     * clase vacacionSolicitud para editar.
     */
    private VacacionSolicitud vacacionSolicitudEditDelete;
    /**
     * Lista de unidades organizacionales.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales = new ArrayList<>();
    /**
     *
     */
    private UnidadOrganizacional unidadOrganizacional = new UnidadOrganizacional();

    /**
     * lista de vacacionSolicitud.
     */
    private List<VacacionSolicitud> listaVacacionSolicitudes;

    /**
     * Variables para opciones tipo vacacion y estado vacacion
     */
    private List<SelectItem> listaOpcionesEstadoVacacion;

    /**
     *
     */
    private List<SelectItem> listaDocumentoPrevio;
    /**
     *
     */
    private String codigoDocPrevio;
    /**
     *
     */
    private Date fechaDocPrevio;

    /*
     * Variable para mostrar saldo de vacaciones del servidor
     */
    private String cadenaSaldo;

    /**
     *
     */
    private String cadenaSaldoProporcional;

    /**
     *
     */
    private String cadenaTiempoEmpresa;
    /**
     *
     */
    private Boolean guardado = Boolean.FALSE;
    /**
     *
     */
    private Boolean esRRHH = Boolean.FALSE;

    /**
     * Variable para actualizar el saldo de las vacaciones
     */
    private Vacacion vacacion;

    /**
     *
     */
    private String explicacion;

    /**
     * Lista de autoridad nominadora.
     */
    private List<SelectItem> listaAutoridadNominadora = new ArrayList<>();

    /**
     * Lista de representnates de rrhh.
     */
    private List<SelectItem> listaRepresentanteRRHH = new ArrayList<>();

    /**
     *
     */
    private Long representanteRRHHId;

    /**
     *
     */
    private Long autoridadNominadoraId;

    /**
     *
     */
    private String nombreAutoridadNominadora;

    /**
     *
     */
    private String nombreRepresentanteRRHH;

    /**
     * Constructor por defecto.
     */
    public VacacionAprobacionHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del BancoHelper.
     */
    public final void iniciador() {
        vacacionSolicitud = new VacacionSolicitud();
        vacacionSolicitudEditDelete = new VacacionSolicitud();
        listaVacacionSolicitudes = new ArrayList<>();
        listaOpcionesEstadoVacacion = new ArrayList<>();
        listaDocumentoPrevio = new ArrayList<>();
        listaDocumentoPrevio.add(new SelectItem("null", "Seleccione..."));
        for (DocumentoPrevioEnum dp : DocumentoPrevioEnum.values()) {
            listaDocumentoPrevio.add(new SelectItem(dp.getCodigo(), dp.getDescripcion()));
        }
        setCadenaSaldo("");
        setCadenaSaldoProporcional("");
        setCadenaTiempoEmpresa("");
        setVacacion(new Vacacion());
    }

}
