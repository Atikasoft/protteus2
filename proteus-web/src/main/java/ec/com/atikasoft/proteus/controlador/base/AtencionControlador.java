/*
 *  AtencionControlador.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  07/12/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.base;

import ec.com.atikasoft.proteus.controlador.BandejaTareaControlador;
import ec.com.atikasoft.proteus.controlador.TramiteControlador;
import ec.com.atikasoft.proteus.controlador.helper.BandejaTareaHelper;
import ec.com.atikasoft.proteus.controlador.helper.TramiteHelper;
import ec.com.atikasoft.proteus.controlador.helper.base.AtencionHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.modelo.Transicion;
import ec.com.atikasoft.proteus.servicio.GestionServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

/**
 * Controlador base de Atención.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public abstract class AtencionControlador extends BaseControlador {

    /**
     * Helper del controlador.
     */
    private AtencionHelper atencionHelper;

    /**
     * Servicio de gestion.
     */
    @EJB
    private GestionServicio gestionServicio;

    /**
     * Servicio Tramite.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tramiteHelper}")
    private TramiteHelper tramiteHelper;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{bandejaTareaHelper}")
    private BandejaTareaHelper bandejaTareaHelper;

    /**
     * Constructor por defecto.
     */
    public AtencionControlador() {
        super();
    }

    /**
     * Este metodo muestra el popUp cuando ya este todo correcto.
     */
    public void verConfirmacion() {
        ejecutarComandoPrimefaces("confirmacionAvanzar.show()");
    }

    /**
     * Este método procesa la regla de navegación.
     *
     * @return String
     */
    public String cancelar() {
        reglaNavegacionDirecta(BandejaTareaControlador.LISTA_ENTIDAD);
        return null;
    }

    /**
     * Este metodo inicia el ver tramite.
     *
     * @return String
     */
    public String verTramite() {
        establecerTramite();
        String estado = bandejaTareaHelper.getTarea().getNombreEstadoActual();
        tramiteHelper.setEsAprobar(Boolean.TRUE);
        establecerPanginaAnterior(TramiteControlador.FORMULARIO_ENTIDAD.concat("?est=vr"));
        return null;
    }

    /**
     * Este metodo lleva el fomulario de tramite.
     */
    protected void establecerTramite() {
        getTramiteHelper().setTramite(atencionHelper.getTramiteFormularioVO().getTramite());
        getTramiteHelper().setListaMovimientos(
                atencionHelper.getTramiteFormularioVO().getTramite().getListaMovimientos());
    }

    /**
     * Este metodo busca el detalle del tramite.
     */
    public void verDetalleHistorico() {
        try {
            atencionHelper.getListaDetalles().clear();
            atencionHelper.setListaDetalles(gestionServicio.listarDetalles(
                    atencionHelper.getTarea().getIdentificadorExterno()));
        } catch (Exception e) {
            error(getClass().getName(), "Error al mostrar el historico.", e);
        }
    }

    /**
     * Este metodo busca la bitacora el tramite.
     */
    public void verBitacora() {
        try {
            atencionHelper.setTramiteBitacora(
                    tramiteServicio.buscarBitacoraDeTramite(
                            atencionHelper.getTramiteFormularioVO().getTramite().getId()));
        } catch (Exception e) {
            error(getClass().getName(), "Error al mostrar la bitacora", e);
        }
    }

    /**
     * Este metodo proces el avance del tramite.
     */
    public void avanzarTramite() {
        try {
            if (atencionHelper.getComentario() != null && !atencionHelper.getComentario().trim().isEmpty()) {
                ParametroInstitucional pi
                        = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                                getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
                tramiteServicio.avanzarTramite(atencionHelper.getTramiteFormularioVO().getTramite().getId(),
                        atencionHelper.getTransicionId(), atencionHelper.getTarea().getId(),
                        atencionHelper.getComentario(), obtenerUsuarioConectado(), esRRHH(pi.getValorTexto()));
                cancelar();
            } else {
                mostrarMensajeEnPantalla("message.comentario.obligatorio", FacesMessage.SEVERITY_ERROR);
            }
        } catch (Exception ex) {
            System.out.println("errror:" + ex.getMessage());
            error(getClass().getName(), "Error al avanzar el trámite", ex);
        }
    }

    /**
     * Este mete inicia la atencion.
     *
     * @param tramite Tramite
     */
    private void iniciarAtencion(final Tramite tramite) {
        crearTitulo(tramite);
        crearOpciones(tramite);
    }

    /**
     * Este metodo genera y asigna el titulo del tramite.
     *
     * @param tramite Tramite
     */
    private void crearTitulo(final Tramite tramite) {
        atencionHelper.setTitulo(
                UtilCadena.concatenar(
                        tramite.getTipoMovimiento().getNombre(), " / ", tramite.getEstado(), " / ", tramite.getNumericoTramite()));
    }

    /**
     * Este metodo crea las opciones del proceso.
     *
     * @param tramite Tramite
     */
    private void crearOpciones(final Tramite tramite) {
        try {
            List<Transicion> buscarTransicionesSiguientes = gestionServicio.buscarTransicionesSiguientes(
                    tramite.getCodigoFase(), tramite.getCodigoProceso());
            iniciarCombos(atencionHelper.getOpciones());
            for (Transicion t : buscarTransicionesSiguientes) {
                atencionHelper.getOpciones().add(new SelectItem(t.getId(), t.getAccion() == null ? "" : t.getAccion()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al crear las opcion.", e);
        }

    }

    /**
     * @return the atencionHelper
     */
    public AtencionHelper getAtencionHelper() {
        return atencionHelper;
    }

    /**
     * Este método relaciona el controlador y el helper.
     *
     * @param ah AtencionHelper
     */
    public void establecerHelper(final AtencionHelper ah) {
        this.atencionHelper = ah;
        atencionHelper.setTramiteFormularioVO(bandejaTareaHelper.getTramiteFormularioVO());
        atencionHelper.setTarea(bandejaTareaHelper.getTarea());
        atencionHelper.setTransicionId(null);
        atencionHelper.setComentario(null);
        iniciarAtencion(atencionHelper.getTramiteFormularioVO().getTramite());
    }

    /**
     * @return the tramiteHelper
     */
    public TramiteHelper getTramiteHelper() {
        return tramiteHelper;
    }

    /**
     * @param tramiteHelper the tramiteHelper to set
     */
    public void setTramiteHelper(final TramiteHelper tramiteHelper) {
        this.tramiteHelper = tramiteHelper;
    }

    /**
     * @return the bandejaTareaHelper
     */
    public BandejaTareaHelper getBandejaTareaHelper() {
        return bandejaTareaHelper;
    }

    /**
     * @param bandejaTareaHelper the bandejaTareaHelper to set
     */
    public void setBandejaTareaHelper(final BandejaTareaHelper bandejaTareaHelper) {
        this.bandejaTareaHelper = bandejaTareaHelper;
    }
}
