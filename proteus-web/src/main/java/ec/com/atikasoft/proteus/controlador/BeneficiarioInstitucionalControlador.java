/*
 *  BeneficiarioInstitucionalControlador.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
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
 *  17/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.BeneficiarioInstitucionalHelper;
import ec.com.atikasoft.proteus.enums.TipoBeneficiarioEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoIdentificacionEnum;
import ec.com.atikasoft.proteus.enums.TipoRubroEnum;
import ec.com.atikasoft.proteus.enums.UsoRubroEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Banco;
import ec.com.atikasoft.proteus.modelo.BeneficiarioEspecial;
import ec.com.atikasoft.proteus.modelo.BeneficiarioInstitucional;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Rubro;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.vo.PersonaVO;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;

/**
 * BeneficiarioInstitucional
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "beneficiarioInstitucionalBean")
@ViewScoped
public class BeneficiarioInstitucionalControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(BeneficiarioInstitucionalControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD
            = "/pages/administracion/beneficiario_institucional/beneficiario_institucional.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD
            = "/pages/administracion/beneficiario_institucional/lista_beneficiario_institucional.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de SERVIDOR.
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{beneficiarioInstitucionalHelper}")
    private BeneficiarioInstitucionalHelper beneficiarioInstitucionalHelper;

    /**
     * Constructor por defecto.
     */
    public BeneficiarioInstitucionalControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(beneficiarioInstitucionalHelper);
        setBeneficiarioInstitucionalHelper(beneficiarioInstitucionalHelper);
        buscar();
        iniciarComboRubro();
        iniciarComboTipoDocumento();
        getCatalogoHelper().setCampoBusqueda("");
        beneficiarioInstitucionalHelper.setRegistroManualNombres(Boolean.FALSE);
    }

    @Override
    public String guardar() {
        try {
            boolean esEspecial = beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getRubro().
                    getTipoBeneficiario().
                    equals(TipoBeneficiarioEnum.ESPECIAL.getCodigo());
            if (beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getIdRubro() == null) {
                mostrarMensajeEnPantalla("El rubro es un campo requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (beneficiarioInstitucionalHelper.getEsNuevo()) {

                if (validarDuplicidadRegistros(esEspecial)) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                    return null;
                }

                if (!esEspecial && (beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getNombreBeneficiario() == null
                        || beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getNombreBeneficiario().
                        isEmpty())) {
                    mostrarMensajeEnPantalla("El nombre del beneficiario es un campo requerido",
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                iniciarDatosEntidad(beneficiarioInstitucionalHelper.getBeneficiarioInstitucional(), Boolean.TRUE);
                beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().setIdInstitucion(obtenerUsuarioConectado().
                        getInstitucion().getId());
                if (esEspecial && beneficiarioInstitucionalHelper.getListaBeneficiarioEspecial().isEmpty()) {

                    mostrarMensajeEnPantalla("No hay Beneficiarios Especiales para guardar", FacesMessage.SEVERITY_ERROR);
                } else {
                    admServicio.guardarBeneficiarioInstitucional(beneficiarioInstitucionalHelper.
                            getBeneficiarioInstitucional());
                    guardarBeneficiarioEspecial();
                    eliminarBeneficiarioEspecial();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                }
            } else {
                if (beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getRubro().getTipoBeneficiario().
                        equals(TipoBeneficiarioEnum.ESPECIAL.getCodigo())
                        && beneficiarioInstitucionalHelper.getListaBeneficiarioEspecial().isEmpty()) {
                    mostrarMensajeEnPantalla("No hay Beneficiarios Especiales para guardar", FacesMessage.SEVERITY_ERROR);
                } else {
                    admServicio.actualizarBeneficiarioInstitucional(beneficiarioInstitucionalHelper.
                            getBeneficiarioInstitucional());
                    guardarBeneficiarioEspecial();
                    eliminarBeneficiarioEspecial();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * Permite evitar la duplicidad de registros : rubros y cedula
     *
     * @param esEspecial
     * @return
     */
    public boolean validarDuplicidadRegistros(boolean esEspecial) {
        boolean duplicado = true;
        try {
            List<BeneficiarioInstitucional> lista;
            if (esEspecial) {
                lista = admServicio.listarBeneficiarioInstitucionalPorServidorYRubro(
                        beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getIdServidor(),
                        beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getRubro().getId());
            } else {
                lista = admServicio.listarBeneficiarioInstitucionalPorNumeroIdentificacionYRubro(
                        beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getNumeroIdentificacion(),
                        beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getRubro().getId());
            }
            if (lista.isEmpty()) {
                duplicado = false;
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al valida duplicidad de registros", ex);
        }
        return duplicado;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(beneficiarioInstitucionalHelper.
                    getBeneficiarioInstitucionalEditDelete());
            BeneficiarioInstitucional d = (BeneficiarioInstitucional) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            beneficiarioInstitucionalHelper.setBeneficiarioInstitucional(d);
            beneficiarioInstitucionalHelper.setEsNuevo(Boolean.FALSE);
            beneficiarioInstitucionalHelper.getListaBeneficiarioEspecialEliminados().clear();
            iniciarBeneficiarioEspecialEdicion();
        } catch (IllegalAccessException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InstantiationException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InvocationTargetException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (NoSuchMethodException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        beneficiarioInstitucionalHelper.iniciador();
        iniciarDatosEntidad(beneficiarioInstitucionalHelper.getBeneficiarioInstitucional(), Boolean.TRUE);
        beneficiarioInstitucionalHelper.setEsNuevo(Boolean.TRUE);
        iniciarComboRubro();
        iniciarComboTipoDocumento();
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarBeneficiarioInstitucional(beneficiarioInstitucionalHelper.
                    getBeneficiarioInstitucionalEditDelete());
            beneficiarioInstitucionalHelper.getListaBeneficiarioInstitucionales().
                    remove(beneficiarioInstitucionalHelper.getBeneficiarioInstitucionalEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            beneficiarioInstitucionalHelper.getListaBeneficiarioInstitucionales().clear();
            beneficiarioInstitucionalHelper.setListaBeneficiarioInstitucionales(
                    admServicio.listarTodosBeneficiarioInstitucionalVigentes());
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * LLena la lista de beneficiario especial
     */
    public void iniciarBeneficiarioEspecialEdicion() {
        try {
            beneficiarioInstitucionalHelper.getListaBeneficiarioEspecial().clear();
            beneficiarioInstitucionalHelper.setListaBeneficiarioEspecial(admServicio.
                    listarBeneficiarioEspecialPorIdBeneficiarioInstitucional(
                            beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getId()));
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda beneficiario especial", ex);
        }
    }

    /**
     * Este metodo muestra la descripcion del tipo de beneficiario.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoBeneficiario(final String codigo) {
        return TipoBeneficiarioEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo muestra la descripcion del tipo de documento.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDocumentoEnum.obtenerNombre(codigo);
    }

    /**
     *
     * @param nombre
     * @return
     */
    public List<Servidor> buscarServidorPorNombre(final String nombre) {
        List<Servidor> servidores = new ArrayList<>();
        try {
            if (beneficiarioInstitucionalHelper.getEsNuevo()) {
                if (nombre != null && !nombre.isEmpty()) {
                    List<DistributivoDetalle> puestos = servidorServicio.buscar(nombre.toUpperCase(),
                            obtenerUsuarioConectado().getInstitucionEjercicioFiscal().
                            getId());
                    for (DistributivoDetalle puesto : puestos) {
                        servidores.add(puesto.getServidor());
                    }
                }
            } else {
                LOG.log(Level.INFO, " servidor: {0}",
                        beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getNombreBeneficiario());
            }

        } catch (ServicioException e) {
            error(getClass().getName(), "Error al buscar servidores", e);
        }
        return servidores;
    }

    /**
     * Obtiene datos del rubro seleccionado
     */
    public void obtenerDatosRubroSeleccionado() {
        try {
            if (beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getIdRubro() != null) {
                beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().setRubro(
                        admServicio.listarRubroPorId(beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().
                                getIdRubro()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de rubro", ex);
        }
    }

    /**
     * Carga los datos para el combo de tipos de documento
     */
    private void iniciarComboTipoDocumento() {
        iniciarCombos(beneficiarioInstitucionalHelper.getListaOpcionesTipoDocumento());
        for (TipoIdentificacionEnum c : TipoIdentificacionEnum.values()) {
            beneficiarioInstitucionalHelper.getListaOpcionesTipoDocumento().add(new SelectItem(c.getCodigo(), c.
                    getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de Opciones de Rubros de descuentos
     */
    private void iniciarComboRubro() {
        List<Rubro> lista = new ArrayList<>();
        try {
            lista.addAll(admServicio.listarTodosRubroPorTipo(TipoRubroEnum.DESCUENTOS.getCodigo()));
            lista.addAll(admServicio.listarTodosRubroPorTipo(TipoRubroEnum.RECUPERACION_ANTICIPOS.getCodigo()));

            iniciarCombos(beneficiarioInstitucionalHelper.getListaOpcionesRubro());
            for (Rubro c : lista) {
                if (c.getUso() != null && c.getTipoBeneficiario() != null && c.getUso().equals(
                        UsoRubroEnum.NINGUNO.getCodigo())) {
                    beneficiarioInstitucionalHelper.getListaOpcionesRubro().add(
                            new SelectItem(c.getId(), c.getNombre()));
                }
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda rubros", ex);
        }

    }

    /*
     * Inicializa campos de servidor especial
     */
    public void iniciarBeneficiarioEspecial(boolean mostrar) {

        beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().setNumeroIdentificacion(null);
        beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().setNombreBeneficiario(null);
        beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().setTipoIdentificacion(null);

        beneficiarioInstitucionalHelper.setBeneficiarioEspecial(new BeneficiarioEspecial());
        beneficiarioInstitucionalHelper.getBeneficiarioEspecial().setBeneficiarioInstitucional(
                beneficiarioInstitucionalHelper.getBeneficiarioInstitucional());
        if (mostrar) {
            ejecutarComandoPrimefaces("addDialog.show();");
        }
    }

    /**
     * Monitorea el comportamiento de la selección del rubro E-Especial.- permite seleccionar al servidor publico ,
     * identificacion y nombre del beneficiario, fecha inici, fin y valor. Es posible agregar mas de un registro.
     * N-Normal .- permite el registro de numero de identificacion y nombres
     */
    public void esRubroSeleccionado() {
        obtenerDatosRubroSeleccionado();
        System.out.println(" rubro seleccionado==>" + beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().
                getRubro().getNombre());
        if (beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getRubro().getTipoBeneficiario().equals(
                TipoBeneficiarioEnum.NORMAL.getCodigo())) {
            iniciarBeneficiarioEspecial(false);
        }
    }

    /**
     * Agrega el registro a la lista.
     */
    public void agregarBeneficiarioEspecial() {
        if (beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getIdRubro() == null) {
            beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().setIdRubro(
                    beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getRubro().getId());
        }

        if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getNombreBeneficiario() == null
                || beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getNombreBeneficiario().isEmpty()) {
            mostrarMensajeEnPantalla("El nombre del beneficiario es un campo requerido", FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getNombreBeneficiario() == null
                || beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getNombreBeneficiario().isEmpty()) {
            mostrarMensajeEnPantalla("El nombre del beneficiario es un campo requerido", FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getValor() == null) {
            mostrarMensajeEnPantalla("El valor es un campo requerido", FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getFechaInicio() == null) {
            mostrarMensajeEnPantalla("La fecha de inicio es un campo requerido", FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getFechaFin() == null) {
            mostrarMensajeEnPantalla("La fecha de fin es un campo requerido", FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getFechaFin().compareTo(
                beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getFechaFin()) < 0) {
            mostrarMensajeEnPantalla(COMPARAR_FECHA, FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (validarDuplicadosBeneficiariosEspeciales()) {
            mostrarMensajeEnPantalla("El beneficiario especial ya ha sido agregado.", FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getServidor() == null) {
            mostrarMensajeEnPantalla("El nombre del servidor público es requerido", FacesMessage.SEVERITY_ERROR);
        } else {
            beneficiarioInstitucionalHelper.getBeneficiarioEspecial().setBeneficiarioInstitucional(
                    beneficiarioInstitucionalHelper.getBeneficiarioInstitucional());
            beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getBeneficiarioInstitucional().setIdServidor(
                    beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getServidor().getId());
            iniciarDatosEntidad(beneficiarioInstitucionalHelper.getBeneficiarioEspecial(), Boolean.FALSE);
            beneficiarioInstitucionalHelper.getListaBeneficiarioEspecial().add(
                    beneficiarioInstitucionalHelper.getBeneficiarioEspecial());
            iniciarBeneficiarioEspecial(false);
            ejecutarComandoPrimefaces("addDialog.hide()");
        }
    }

    /**
     *
     */
    public void iniciarEdicionBeneficiarioEspecial() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(beneficiarioInstitucionalHelper.getBeneficiarioEspecialTransaccion());
            BeneficiarioEspecial d = (BeneficiarioEspecial) cloneBean;
            beneficiarioInstitucionalHelper.setBeneficiarioEspecial(d);
            ejecutarComandoPrimefaces("editDialog.show()");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
    }

    /**
     * Editar Beneficiario
     */
    public void editarBeneficiarioEspecial() {
        if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getValor() == null) {
            mostrarMensajeEnPantalla("El valor es un campo requerido", FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getFechaInicio() == null) {
            mostrarMensajeEnPantalla("La fecha de inicio es un campo requerido", FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getFechaFin() == null) {
            mostrarMensajeEnPantalla("La fecha de fin es un campo requerido", FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getFechaFin().compareTo(
                beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getFechaFin()) < 0) {
            mostrarMensajeEnPantalla(COMPARAR_FECHA, FacesMessage.SEVERITY_ERROR);
            return;
        }
        iniciarDatosEntidad(beneficiarioInstitucionalHelper.getBeneficiarioEspecial(), Boolean.FALSE);
        int pos = beneficiarioInstitucionalHelper.getListaBeneficiarioEspecial()
                .indexOf(beneficiarioInstitucionalHelper.getBeneficiarioEspecialTransaccion());
        beneficiarioInstitucionalHelper.getListaBeneficiarioEspecial().set(pos,
                beneficiarioInstitucionalHelper.getBeneficiarioEspecial());

        iniciarBeneficiarioEspecial(false);
        ejecutarComandoPrimefaces("editDialog.hide()");
    }

    /**
     * Permite evitar que existan registros duplicados en beneficiarios especiales.
     *
     * @return
     */
    public boolean validarDuplicadosBeneficiariosEspeciales() {
        boolean hayDuplicidad = false;
        for (BeneficiarioEspecial es : beneficiarioInstitucionalHelper.getListaBeneficiarioEspecial()) {
            if (es.getNumeroIdentificacion().equals(beneficiarioInstitucionalHelper.getBeneficiarioEspecial().
                    getNumeroIdentificacion())
                    && es.getTipoIdentificacion().equals(beneficiarioInstitucionalHelper.getBeneficiarioEspecial().
                            getTipoIdentificacion())) {
                hayDuplicidad = true;
                break;
            }
        }
        return hayDuplicidad;
    }

    /**
     * *
     * Elimina un registro de la lista de beneficiarios especiales y al mismo tiempo registra en otra lista los
     * registros que se eliminan siempre y cuando tengan un Id, que indica que ese registro esta en la BD
     */
    public void eliminarBeneficiarioEspecial() {
        beneficiarioInstitucionalHelper.getListaBeneficiarioEspecial().remove(
                beneficiarioInstitucionalHelper.getBeneficiarioEspecial());
        if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getId() != null) {
            beneficiarioInstitucionalHelper.getListaBeneficiarioEspecialEliminados().add(
                    beneficiarioInstitucionalHelper.getBeneficiarioEspecial());
        }
    }

    /**
     * Persiste o actualiza en la base de datos.
     */
    public void guardarBeneficiarioEspecial() {
        try {

            for (BeneficiarioEspecial e : beneficiarioInstitucionalHelper.getListaBeneficiarioEspecial()) {
                if (e.getId() == null) {
                    e.setBeneficiarioInstitucional(beneficiarioInstitucionalHelper.getBeneficiarioInstitucional());
                    e.setIdBeneficiarioInstitucion(
                            beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getId());
                    iniciarDatosEntidad(e, Boolean.TRUE);
                    admServicio.guardarBeneficiarioEspecial(e);
                } else {
                    admServicio.actualizarBeneficiarioEspecial(e);
                }
            }
            for (BeneficiarioEspecial e : beneficiarioInstitucionalHelper.getListaBeneficiarioEspecialEliminados()) {
                if (e.getId() != null) {
                    admServicio.eliminarBeneficiarioEspecial(e);
                }
            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar beneficiario especial", e);
        }
    }

    /**
     * Permite buscar al beneficiario especial.
     */
    public void buscarBeneficiarioEspecial() {

        if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getTipoIdentificacion() != null
                && beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getNumeroIdentificacion() != null
                && !beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getTipoIdentificacion().isEmpty()
                && !beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getNumeroIdentificacion().isEmpty()) {
            try {
                beneficiarioInstitucionalHelper.setRegistroManualNombres(Boolean.FALSE);
                PersonaVO personaVO = servidorServicio.buscarPersona(
                        beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getTipoIdentificacion(),
                        beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getNumeroIdentificacion(),
                        obtenerUsuarioConectado());
                beneficiarioInstitucionalHelper.setPersonaVO(personaVO);
                if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getTipoIdentificacion().equals(
                        TipoIdentificacionEnum.CEDULA.getCodigo())) {
                    // es cedula
                    if (personaVO == null) {
                        mostrarMensajeEnPantalla("El número de identificación ingresada no existe.",
                                FacesMessage.SEVERITY_ERROR);
                        beneficiarioInstitucionalHelper.getBeneficiarioEspecial().setNombreBeneficiario("");
                    } else {
                        beneficiarioInstitucionalHelper.getBeneficiarioEspecial().setNombreBeneficiario(
                                beneficiarioInstitucionalHelper.getPersonaVO().getApellidosNombres());
                    }
                } else if (beneficiarioInstitucionalHelper.getBeneficiarioEspecial().getTipoIdentificacion().equals(
                        TipoIdentificacionEnum.PASAPORTE.getCodigo())) {
                    // es pasaporte
                    if (personaVO == null) {
                        beneficiarioInstitucionalHelper.setRegistroManualNombres(Boolean.TRUE);
                        mostrarMensajeEnPantalla("El número de pasaporte ingresado no existe en el Sistema de "
                                + " Personas, proceda a ingresar el Apellido y Nombre"
                                + " asegurándose que sean correctos.", FacesMessage.SEVERITY_WARN);
                        beneficiarioInstitucionalHelper.getBeneficiarioEspecial().setNombreBeneficiario("");
                    } else {
                        beneficiarioInstitucionalHelper.getBeneficiarioEspecial().setNombreBeneficiario(
                                beneficiarioInstitucionalHelper.getPersonaVO().getApellidosNombres());
                    }
                }
//                if (beneficiarioInstitucionalHelper.getPersonaVO() != null && beneficiarioInstitucionalHelper.
//                        getPersonaVO().getNumeroIdentificacion() != null) {
//                    beneficiarioInstitucionalHelper.getBeneficiarioEspecial().setNombreBeneficiario(
//                            beneficiarioInstitucionalHelper.getPersonaVO().getApellidosNombres());
//                } else {
//                    mostrarMensajeEnPantalla("El número de identificación ingresada no existe.",
//                            FacesMessage.SEVERITY_ERROR);
//                    beneficiarioInstitucionalHelper.getBeneficiarioEspecial().setNombreBeneficiario("");
//                }
            } catch (ServicioException ex) {
                mostrarMensajeEnPantalla("Problemas al buscar beneficiario especial", FacesMessage.SEVERITY_ERROR);
                error(getClass().getName(), "Problemas al buscar beneficiario especial ", ex);
            }
        } else {
            mostrarMensajeEnPantalla(
                    "Los campos Tipo y Número de Identificación del Beneficiario Especial son requeridos ",
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * Permite buscar al beneficiario normal.
     */
    public void buscarBeneficiarioNormal() {
        if (beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getTipoIdentificacion() != null
                && beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getNumeroIdentificacion() != null
                && !beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getTipoIdentificacion().isEmpty()
                && !beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getNumeroIdentificacion().isEmpty()) {
            try {
                beneficiarioInstitucionalHelper.setPersonaVO(
                        servidorServicio.buscarPersona(beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().
                                getTipoIdentificacion(),
                                beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().getNumeroIdentificacion(),
                                obtenerUsuarioConectado()));
                if (beneficiarioInstitucionalHelper.getPersonaVO() != null && beneficiarioInstitucionalHelper.
                        getPersonaVO().getNumeroIdentificacion() != null) {
                    beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().setNombreBeneficiario(beneficiarioInstitucionalHelper.
                            getPersonaVO().getNombres());
                } else {
                    mostrarMensajeEnPantalla(BUSQUEDA_SIN_RESULTADOS, FacesMessage.SEVERITY_ERROR);
                    beneficiarioInstitucionalHelper.getBeneficiarioInstitucional().setNombreBeneficiario("");
                }
            } catch (ServicioException ex) {
                mostrarMensajeEnPantalla("Problemas al buscar beneficiario normal", FacesMessage.SEVERITY_ERROR);
                error(getClass().getName(), "Problemas al buscar beneficiario normal ", ex);
            }
        } else {
            mostrarMensajeEnPantalla(
                    "Los campos Tipo y Número de Identificación del Beneficiario Normal son requeridos ",
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * @return the beneficiarioInstitucionalHelper
     */
    public BeneficiarioInstitucionalHelper getBeneficiarioInstitucionalHelper() {
        return beneficiarioInstitucionalHelper;
    }

    /**
     * @param beneficiarioInstitucionalHelper the beneficiarioInstitucionalHelper to set
     */
    public void setBeneficiarioInstitucionalHelper(BeneficiarioInstitucionalHelper beneficiarioInstitucionalHelper) {
        this.beneficiarioInstitucionalHelper = beneficiarioInstitucionalHelper;
    }
}
