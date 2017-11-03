/*
 *
 *  Quito - Ecuador
 *
 *  19/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.EscalaOcupacionalHelper;
import ec.com.atikasoft.proteus.controlador.helper.NivelOcupacionalHelper;
import ec.com.atikasoft.proteus.controlador.helper.RegimenLaboralHelper;
import ec.com.atikasoft.proteus.modelo.EscalaOcupacional;
import ec.com.atikasoft.proteus.modelo.NivelOcupacional;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.servicio.RegimenLaboralServicio;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Controlador para la Administracion de RegimenLaborals.
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "regimenLaboralBean")
@ViewScoped
public class RegimenLaboralControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(RegimenLaboralControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ESCALAS = "/pages/administracion"
            + "/regimen_laboral/lista_escala_ocupacional.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion"
            + "/regimen_laboral/lista_regimen_laboral.jsf";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{regimenLaboralHelper}")
    private RegimenLaboralHelper regimenLaboralHelper;

    /**
     * Servicio de administracion.
     */
    @EJB
    private RegimenLaboralServicio regimenLaboralServicio;

    /**
     * Helper de clase Nivel Ocupacional.
     */
    @ManagedProperty("#{nivelOcupacionalHelper}")
    private NivelOcupacionalHelper nivelOcupacionalHelper;

    /**
     * Helper de clase Escala Ocupacional.
     */
    @ManagedProperty("#{escalaOcupacionalHelper}")
    private EscalaOcupacionalHelper escalaOcupacionalHelper;

    @Override
    @PostConstruct
    public void init() {
        if (regimenLaboralHelper.isEsPaginaArbol()) {
            cargarArbol();
        }
    }

    /**
     * Permite cargar un Tree con los datos de Regimen Laboral, Nivel Ocupacional, Escala Ocupacional
     *
     * @return
     */
    public String cargarArbol() {
        try {
            buscar();
            regimenLaboralHelper.setRoot(new DefaultTreeNode("root", null));

            TreeNode nodoPrincipal = new DefaultTreeNode(regimenLaboralHelper.getListaRegimenLaboral().get(0),
                    regimenLaboralHelper.getRoot());
            regimenLaboralHelper.getListaRegimenLaboral().remove(0);
            for (RegimenLaboral reg : regimenLaboralHelper.getListaRegimenLaboral()) {
                TreeNode nodoRegimen = new DefaultTreeNode(reg, nodoPrincipal);
                if (reg.getListaNivelOcupacional().size() > 0) {
                    for (NivelOcupacional nivel : reg.getListaNivelOcupacional()) {
                        if (nivel.getVigente()) {
                            TreeNode nodoNivel = new DefaultTreeNode(nivel, nodoRegimen);
                            for (EscalaOcupacional escala : nivel.getListaEscalaOcupacional()) {
                                if (escala.getVigente()) {
                                    TreeNode nodoEscala = new DefaultTreeNode(escala, nodoNivel);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return null;
    }

    public String guardarq() {
        try {
            if (getRegimenLaboralHelper().getEsNuevo()) {
                if (regimenLaboralHelper.getNodoSeleccionado() == 0) {
                    if (validarCodigoRegimen()) {
                        mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                    } else {
                        regimenLaboralServicio.guardarRegimenLaboral(getRegimenLaboralHelper().
                                getRegimenLaboralEditDelete(), obtenerUsuarioConectado());
                        ejecutarComandoPrimefaces("editDialog.hide()");
                        mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    }

                } else if (regimenLaboralHelper.getNodoSeleccionado() == 1) {
                    if (validarCodigoNivel()) {
                        mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                    } else {
                        regimenLaboralServicio.guardarNivelOcupacional(nivelOcupacionalHelper.
                                getNivelOcupacionalEditDelete());
                        ejecutarComandoPrimefaces("editDialogNivel.hide()");
                        mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    }
                } else if (regimenLaboralHelper.getNodoSeleccionado() == 2) {
                    if (validarCodigoEscala()) {
                        mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                    } else {
                        regimenLaboralServicio.guardarEscalaOcupacional(escalaOcupacionalHelper.
                                getEscalaOcupacionalEditDelete());
                        ejecutarComandoPrimefaces("editDialogEscala.hide()");
                        mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    }
                }
                cargarArbol();
            } else {
                if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof RegimenLaboral) {
                    regimenLaboralServicio.actualizarRegimenLaboral(regimenLaboralHelper.getRegimenLaboralEditDelete(),
                            obtenerUsuarioConectado());
                    ejecutarComandoPrimefaces("editDialog.hide()");
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                } else if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof NivelOcupacional) {
                    regimenLaboralServicio.actualizarNivelOcupacional(nivelOcupacionalHelper.
                            getNivelOcupacionalEditDelete());
                    ejecutarComandoPrimefaces("editDialogNivel.hide()");
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                } else if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof EscalaOcupacional) {
                    regimenLaboralServicio.actualizarEscalaOcupacional(escalaOcupacionalHelper.
                            getEscalaOcupacionalEditDelete());
                    ejecutarComandoPrimefaces("editDialogEscala.hide()");
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }

            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }

        return null;
    }

    @Override
    public String guardar() {
        int nivelArbol = regimenLaboralHelper.getNodoSeleccionado();
        try {
            if (getRegimenLaboralHelper().getEsNuevo()) {
                switch (nivelArbol) {
                    case 0:
                        if (validarCodigoRegimen()) {
                            mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                        } else {
                            regimenLaboralServicio.guardarRegimenLaboral(getRegimenLaboralHelper().
                                    getRegimenLaboralEditDelete(), obtenerUsuarioConectado());
                            ejecutarComandoPrimefaces("editDialog.hide()");
                            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                        }
                        break;
                    case 1:
                        if (validarCodigoNivel()) {
                            mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                        } else {
                            regimenLaboralServicio.guardarNivelOcupacional(nivelOcupacionalHelper.
                                    getNivelOcupacionalEditDelete());
                            ejecutarComandoPrimefaces("editDialogNivel.hide()");
                            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                        }
                        break;
                    case 2:
                        if (validarCodigoEscala()) {
                            mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                        } else if (verificarRMU()) {
                            regimenLaboralServicio.guardarEscalaOcupacional(escalaOcupacionalHelper.
                                    getEscalaOcupacionalEditDelete());
                            ejecutarComandoPrimefaces("editDialogEscala.hide()");
                            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                        }
                        break;
                }
                cargarArbol();
            } else {
                if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof RegimenLaboral) {
                    regimenLaboralServicio.actualizarRegimenLaboral(regimenLaboralHelper.getRegimenLaboralEditDelete(),
                            obtenerUsuarioConectado());
                    ejecutarComandoPrimefaces("editDialog.hide()");
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                } else if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof NivelOcupacional) {
                    regimenLaboralServicio.actualizarNivelOcupacional(nivelOcupacionalHelper.
                            getNivelOcupacionalEditDelete());
                    ejecutarComandoPrimefaces("editDialogNivel.hide()");
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                } else if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof EscalaOcupacional) {
                    if (verificarRMU()) {
                        regimenLaboralServicio.actualizarEscalaOcupacional(escalaOcupacionalHelper.
                                getEscalaOcupacionalEditDelete());
                        ejecutarComandoPrimefaces("editDialogEscala.hide()");
                        mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    }
                }
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }

        return null;
    }

    @Override
    public String iniciarEdicion() {
        onNodeSelect();
        regimenLaboralHelper.setEsNuevo(Boolean.FALSE);
        if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof RegimenLaboral
                && regimenLaboralHelper.getNodoSeleccionado() != 0) {
            iniciarDatosEntidad(regimenLaboralHelper.getRegimenLaboralEditDelete(), Boolean.FALSE);
            ejecutarComandoPrimefaces("editDialog.show()");
        } else if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof NivelOcupacional) {
            iniciarDatosEntidad(nivelOcupacionalHelper.getNivelOcupacionalEditDelete(), Boolean.FALSE);
            ejecutarComandoPrimefaces("editDialogNivel.show()");
        } else if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof EscalaOcupacional) {
            iniciarDatosEntidad(escalaOcupacionalHelper.getEscalaOcupacionalEditDelete(), Boolean.FALSE);
            ejecutarComandoPrimefaces("editDialogEscala.show()");
        } else if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof RegimenLaboral
                && regimenLaboralHelper.getNodoSeleccionado() == 0) {
            ejecutarComandoPrimefaces("msgDialog.show()");
        }
        return null;
    }

    @Override
    public String iniciarNuevo() {
        onNodeSelect();
        regimenLaboralHelper.setEsNuevo(Boolean.TRUE);
        if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof RegimenLaboral
                && regimenLaboralHelper.getNodoSeleccionado() == 0) {
            regimenLaboralHelper.setRegimenLaboralEditDelete(new RegimenLaboral());
            iniciarDatosEntidad(regimenLaboralHelper.getRegimenLaboralEditDelete(), Boolean.TRUE);
            ejecutarComandoPrimefaces("editDialog.show()");
        } else if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof RegimenLaboral
                && regimenLaboralHelper.getNodoSeleccionado() == 1) {
            nivelOcupacionalHelper.setNivelOcupacionalEditDelete(new NivelOcupacional());
            iniciarDatosEntidad(nivelOcupacionalHelper.getNivelOcupacionalEditDelete(), Boolean.TRUE);
            nivelOcupacionalHelper.getNivelOcupacionalEditDelete().setRegimenLaboral((RegimenLaboral) regimenLaboralHelper.
                    getRegimenSeleccionado().getData());
            ejecutarComandoPrimefaces("editDialogNivel.show()");
        } else if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof NivelOcupacional
                && regimenLaboralHelper.getNodoSeleccionado() == 2) {
            escalaOcupacionalHelper.setEscalaOcupacionalEditDelete(new EscalaOcupacional());
            iniciarDatosEntidad(escalaOcupacionalHelper.getEscalaOcupacionalEditDelete(), Boolean.TRUE);
            escalaOcupacionalHelper.getEscalaOcupacionalEditDelete().setNivelOcupacional((NivelOcupacional) regimenLaboralHelper.
                    getRegimenSeleccionado().getData());
            ejecutarComandoPrimefaces("editDialogEscala.show()");
        } else if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof EscalaOcupacional) {
            ejecutarComandoPrimefaces("msgDialog.show()");
        }
        return null;
    }

    @Override
    public String borrar() {
        try {
            onNodeSelect();
            if (!regimenLaboralHelper.getRegimenLaboralEditDelete().equals(null)
                    && regimenLaboralHelper.getNodoSeleccionado() == 0) {
                mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR);
                ejecutarComandoPrimefaces("msgDialog.show()");
            } else if (!regimenLaboralHelper.getRegimenLaboralEditDelete().equals(null)
                    && regimenLaboralHelper.getNodoSeleccionado() == 1) {
                if (existenNivelesOcupacionales(regimenLaboralHelper.getRegimenLaboralEditDelete())) {
                    mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR);
                } else {
                    regimenLaboralServicio.eliminarRegimenLaboral(regimenLaboralHelper.getRegimenLaboralEditDelete());
                    quitarNodo();
                }
            } else if (!nivelOcupacionalHelper.getNivelOcupacionalEditDelete().equals(null)
                    && regimenLaboralHelper.getNodoSeleccionado() == 2) {
                if (existenEscalasLaborales(nivelOcupacionalHelper.getNivelOcupacionalEditDelete())) {
                    mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR);
                } else {
                    regimenLaboralServicio.eliminarNivelOcupacional(
                            nivelOcupacionalHelper.getNivelOcupacionalEditDelete());
                    quitarNodo();
                }

            } else if (!escalaOcupacionalHelper.getEscalaOcupacionalEditDelete().equals(null)
                    && regimenLaboralHelper.getNodoSeleccionado() == 3) {
                regimenLaboralServicio.eliminarEscalaOcupacional(
                        escalaOcupacionalHelper.getEscalaOcupacionalEditDelete());
                quitarNodo();
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion", ex);
        }
        return null;
    }

    /**
     * Elimina fisicamente el registro del arbol
     */
    public void quitarNodo() {
        regimenLaboralHelper.getRegimenSeleccionado().getChildren().clear();
        regimenLaboralHelper.getRegimenSeleccionado().getParent().getChildren().remove(regimenLaboralHelper.
                getRegimenSeleccionado());
        regimenLaboralHelper.getRegimenSeleccionado().setParent(null);
        regimenLaboralHelper.setRegimenSeleccionado(null);
        mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
    }

    /**
     * Permite obtener el objeto del Nodo seleccionado
     *
     * @param event NodoSeleccionado
     */
    public String onNodeSelect() {
        regimenLaboralHelper.setEsNuevo(Boolean.FALSE);
        if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof RegimenLaboral) {
            regimenLaboralHelper.setRegimenLaboralEditDelete((RegimenLaboral) regimenLaboralHelper.
                    getRegimenSeleccionado().getData());
            if (regimenLaboralHelper.getRegimenLaboralEditDelete() != null) {
                if (regimenLaboralHelper.getRegimenLaboralEditDelete().getEsRoot() != null) {
                    regimenLaboralHelper.setNodoSeleccionado(0);
                } else {
                    regimenLaboralHelper.setNodoSeleccionado(1);
                }
            }
        } else if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof NivelOcupacional) {
            nivelOcupacionalHelper.setNivelOcupacionalEditDelete((NivelOcupacional) regimenLaboralHelper.
                    getRegimenSeleccionado().getData());
            regimenLaboralHelper.setNodoSeleccionado(2);
        } else if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof EscalaOcupacional) {
            escalaOcupacionalHelper.setEscalaOcupacionalEditDelete((EscalaOcupacional) regimenLaboralHelper.
                    getRegimenSeleccionado().getData());
            regimenLaboralHelper.setNodoSeleccionado(3);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            regimenLaboralHelper.getListaRegimenLaboral().clear();
            regimenLaboralHelper.setListaRegimenLaboral(
                    regimenLaboralServicio.listarTodosRegimenLaboral());
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Verifica que el campo RMU Maximo sea mayor o igual que RMU
     */
    public boolean verificarRMU() {
        boolean mayor = true;
        if (escalaOcupacionalHelper.getEscalaOcupacionalEditDelete().getRmu() != null
                && escalaOcupacionalHelper.getEscalaOcupacionalEditDelete().getRmuMaximo() != null) {
            if (escalaOcupacionalHelper.getEscalaOcupacionalEditDelete().getRmuMaximo().compareTo(
                    escalaOcupacionalHelper.getEscalaOcupacionalEditDelete().getRmu()) < 0) {
                mostrarMensajeEnPantalla(REGIMEN_COMPARA_RMU, FacesMessage.SEVERITY_INFO);
                mayor = false;
            }

        }
        return mayor;
    }

    /**
     * Busca las Escalas Ocupacionales del Nivel Seleccionado en el arbol
     *
     * @return lista de Escalas Ocupacionales Vigentes
     */
    public String buscarEscalasOcupacionales() {
        try {
            escalaOcupacionalHelper.setListaEscalaOcupacional(new ArrayList<EscalaOcupacional>());
            if (nivelOcupacionalHelper.getNivelOcupacionalEditDelete().getId() != null) {
                escalaOcupacionalHelper.setListaEscalaOcupacional(
                        regimenLaboralServicio.listarTodosEscalaOcupacionalPorNivelOcup(nivelOcupacionalHelper.
                        getNivelOcupacionalEditDelete().getId()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de Escalas Ocupacionales", ex);
        }
        return FORMULARIO_ESCALAS;
    }

    /**
     * metodo para validar si ya existe el codigo/nemonico de regimen laboral.
     *
     * @return hayCodigo Boolean.
     */
    public Boolean validarCodigoRegimen() {
        Boolean hayCodigo = true;
        try {
            regimenLaboralHelper.getListaRegimenLaboralCodigo().clear();
            regimenLaboralHelper.setListaRegimenLaboralCodigo(
                    regimenLaboralServicio.listarTodosRegimenLaboralPorCodigo(
                    regimenLaboralHelper.getRegimenLaboralEditDelete().getCodigo()));

            if (regimenLaboralHelper.getListaRegimenLaboralCodigo().isEmpty()) {
                hayCodigo = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion del codigo", ex);
        }
        return hayCodigo;
    }

    /**
     * metodo para validar si ya existe el codigo/nemonico de nivel ocupacional
     *
     * @return hayCodigo Boolean.
     */
    public Boolean validarCodigoNivel() {
        Boolean hayCodigo = true;
        try {
            nivelOcupacionalHelper.getListaNivelOcupacionalCodigo().clear();
            nivelOcupacionalHelper.setListaNivelOcupacionalCodigo(
                    regimenLaboralServicio.listarTodosNivelOcupacionalPorCodigo(
                    nivelOcupacionalHelper.getNivelOcupacionalEditDelete().getCodigo()));
            if (nivelOcupacionalHelper.getListaNivelOcupacionalCodigo().isEmpty()) {
                hayCodigo = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion del codigo", ex);
        }
        return hayCodigo;
    }

    /**
     * metodo para validar si ya existe el codigo/nemonico de nivel ocupacional
     *
     * @return hayCodigo Boolean.
     */
    public Boolean validarCodigoEscala() {
        Boolean hayCodigo = true;
        try {
            escalaOcupacionalHelper.getListaEscalaOcupacionalCodigo().clear();
            escalaOcupacionalHelper.setListaEscalaOcupacionalCodigo(
                    regimenLaboralServicio.listarTodosEscalaOcupacionalPorCodigo(
                    escalaOcupacionalHelper.getEscalaOcupacionalEditDelete().getCodigo()));
            if (escalaOcupacionalHelper.getListaEscalaOcupacionalCodigo().isEmpty()) {
                hayCodigo = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion del codigo", ex);
        }
        return hayCodigo;
    }

    /**
     * Permite verificar si un Nivel Ocupacional tiene Escalas Ocupacionales asociadas
     *
     * @param nivelOcup Nivel ocupacional a verificar
     * @return true si hay Escalas en ese Nivel ocupacional
     */
    public Boolean existenEscalasLaborales(NivelOcupacional nivelOcup) {
        Boolean hayEscalas = true;
        try {
            if (regimenLaboralServicio.listarTodosEscalaOcupacionalPorNivelOcup(nivelOcup.getId()).size() > 0) {
                hayEscalas = true;
            } else {
                hayEscalas = false;
            }


        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de Escalas Ocupacionales", ex);
        }
        return hayEscalas;
    }

    /**
     * Permite verificar si un Regimen Laboral tiene Niveles ocupacionales asociados
     *
     * @param regimen Regimen Laboral a verificar
     * @return true si hay Nivel ocupacional en ese regimen
     */
    public Boolean existenNivelesOcupacionales(RegimenLaboral regimen) {
        Boolean hayEscalas = true;
        try {
            if (regimenLaboralServicio.listarTodosNivelOcupacionalPorRegimen(regimen.getId()).size() > 0) {
                hayEscalas = true;
            } else {
                hayEscalas = false;
            }


        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de Escalas Ocupacionales", ex);
        }
        return hayEscalas;
    }

    /**
     * Permite navegar desde la lista de escalas ocupacionales hacia la página donde se presenta el árbol
     *
     * @return
     */
    public String regresarAdministracion() {
        cargarArbol();
        return LISTA_ENTIDAD;
    }

    /**
     * Permite navegar desde la página dondde se presenta el árbol hacia la página la lista de escalas ocupacionales
     *
     * @return
     */
    public String irListaEscalas() {
        onNodeSelect();
        regimenLaboralHelper.setEsPaginaArbol(false);
        if (regimenLaboralHelper.getRegimenSeleccionado().getData() instanceof NivelOcupacional
                && nivelOcupacionalHelper.getNivelOcupacionalEditDelete() != null) {
            buscarEscalasOcupacionales();
            return FORMULARIO_ESCALAS;
        } else {
            ejecutarComandoPrimefaces("msgDialog.show()");
            return LISTA_ENTIDAD;
        }

    }

    /**
     * @return the regimenLaboralHelper
     */
    public RegimenLaboralHelper getRegimenLaboralHelper() {
        return regimenLaboralHelper;
    }

    /**
     * @param regimenLaboralHelper the regimenLaboralHelper to set
     */
    public void setRegimenLaboralHelper(final RegimenLaboralHelper regimenLaboralHelper) {
        this.regimenLaboralHelper = regimenLaboralHelper;
    }

    /**
     * @return the regimenLaboralServicio
     */
    public RegimenLaboralServicio getRegimenLaboralServicio() {
        return regimenLaboralServicio;
    }

    /**
     * @param regimenLaboralServicio the regimenLaboralServicio to set
     */
    public void setRegimenLaboralServicio(final RegimenLaboralServicio regimenLaboralServicio) {
        this.regimenLaboralServicio = regimenLaboralServicio;
    }

    /**
     * @return the nivelOcupacionalHelper
     */
    public NivelOcupacionalHelper getNivelOcupacionalHelper() {
        return nivelOcupacionalHelper;
    }

    /**
     * @return the escalaOcupacionalHelper
     */
    public EscalaOcupacionalHelper getEscalaOcupacionalHelper() {
        return escalaOcupacionalHelper;
    }

    /**
     * @param nivelOcupacionalHelper the nivelOcupacionalHelper to set
     */
    public void setNivelOcupacionalHelper(NivelOcupacionalHelper nivelOcupacionalHelper) {
        this.nivelOcupacionalHelper = nivelOcupacionalHelper;
    }

    /**
     * @param nivelOcupacionalHelper the escalaOcupacionalHelper to set
     */
    public void setEscalaOcupacionalHelper(EscalaOcupacionalHelper escalaOcupacionalHelper) {
        this.escalaOcupacionalHelper = escalaOcupacionalHelper;
    }
}
