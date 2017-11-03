/*
 *  PlanificacionVacacionHelper.java
 *
 *  PROTEUS V 2.0 $Revision 1.0 $
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  19/11/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.enums.DocumentoPrevioEnum;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitudLiquidacion;
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
@ManagedBean(name = "liquidacionVacacionesHelper")
@SessionScoped
@Getter
@Setter
public class LiquidacionVacacionesHelper extends CatalogoHelper {

    /**
     * Variable para combo de opciones de periodos fiscales.
     */
    private List<SelectItem> opcionesEjercicioFiscal;

    /**
     *
     */
    private List<SelectItem> opcionesTipoIdentificacion;
    /*
     * Variables de interaccion con la pantalla.
     */
    private boolean botonProcesar;

    /**
     *
     */
    private String tipoIdentificacion;
    /**
     *
     */
    private String nroIdentificacion;
    /**
     *
     */
    private String apellidosNombres;

    /**
     * ISTA DE OPCIONES POR LA QUE SE REALIZARA LA BUSQUEDA DE UNS SERVIDOR
     */
    private List<SelectItem> opcionesBuscarServidorPor;

    /**
     * OPCION SELECCIONADA DE LA LISTA DE OPCIONES PARA LA BUSQUEDA DE UNS SERVIDOR
     */
    private String buscarServidorPor;

    /**
     * LISTA DE DETALLES DE LOS PUESTOS OCUPADOS POR LOS SERVIDORES ENCOTNRADOS AL REALIZAR LA BUSQUEDA
     */
    private List<Servidor> listaServidores;

    /**
     * SERVIDOR ENCONTRADO Y SELECCIONADO COMO RESULTADO DE LA BUSQUEDA
     */
    private Servidor servidor;

    /**
     * LISTA DE DETALLES DE LOS PUESTOS OCUPADOS POR LOS SERVIDORES ENCOTNRADOS AL REALIZAR LA BUSQUEDA
     */
    private List<DistributivoDetalle> listaDistributivosDetalles;

    /**
     *
     */
    private DistributivoDetalle distributivoDetalleSeleccionado;

    /**
     * Datos de las vaciones del servidor buscado
     */
    private Vacacion vacacion;

    /**
     *
     */
    private Long saldoVacacion;

    /**
     *
     */
    private String saldoVacacionTexto;

    /**
     *
     */
    private Long saldoVacacionProporcional;

    /**
     *
     */
    private String saldoVacacionProporcionalTexto;

    /**
     * JUSTIFICACION LEGAL LIQUIDACION VACACION
     */
    private String explicacionLiquidacionVacacion;

    /**
     *
     */
    private String codigoTipoDocPrevio;

    /**
     *
     */
    private String numeroDocumentoPrevio;

    /**
     *
     */
    private Date fechaDocPrevio;

    /**
     * lista de opciones para seleccionar documento legal que justifica la liquidacion
     */
    private List<SelectItem> listaDocumentoPrevio;

    /**
     *
     */
    private VacacionSolicitudLiquidacion liquidacionSeleccionada;

    /**
     * lista de opciones para seleccionar documento legal que justifica la liquidacion
     */
    private List<VacacionSolicitudLiquidacion> listaLiquidaciones = new ArrayList<>();

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
     *
     */
    /**
     * Método para iniciar las variables del PlanificacionVacacionHelper.
     */
    @PostConstruct
    public final void iniciador() {
        setOpcionesEjercicioFiscal(new ArrayList<SelectItem>());
        setOpcionesTipoIdentificacion(new ArrayList<SelectItem>());
        setListaAutoridadNominadora(new ArrayList<SelectItem>());
        setListaRepresentanteRRHH(new ArrayList<SelectItem>());
        setBotonProcesar(false);
        opcionesBuscarServidorPor = new ArrayList<>();
        opcionesBuscarServidorPor.add(new SelectItem(null, "Seleccione..."));
        opcionesBuscarServidorPor.add(new SelectItem("id", "Identificación"));
        opcionesBuscarServidorPor.add(new SelectItem("an", "Apellidos Nombres"));
        listaDistributivosDetalles = new ArrayList<>();
        listaServidores = new ArrayList<>();

        listaDocumentoPrevio = new ArrayList<>();
        listaDocumentoPrevio.add(new SelectItem(" ", "Seleccione..."));
        for (DocumentoPrevioEnum dp : DocumentoPrevioEnum.values()) {
            listaDocumentoPrevio.add(new SelectItem(dp.getCodigo(), dp.getDescripcion()));
        }
        saldoVacacion = 0l;
        saldoVacacionTexto = "0 Días";
        saldoVacacionProporcional = 0l;
        saldoVacacionProporcionalTexto = "0 Días";
    }

}
