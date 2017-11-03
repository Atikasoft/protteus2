/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.CuentaBancariaHelper;
import ec.com.atikasoft.proteus.enums.TipoCuentaEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoPersonaEnum;
import ec.com.atikasoft.proteus.modelo.Banco;
import ec.com.atikasoft.proteus.modelo.BeneficiarioEspecial;
import ec.com.atikasoft.proteus.modelo.BeneficiarioInstitucional;
import ec.com.atikasoft.proteus.modelo.CuentaBancaria;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
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
 *
 * @author atikasoft
 */
@ManagedBean(name = "cuentaBancariaBean")
@ViewScoped
public class CuentaBancariaControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(CuentaBancariaControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/cuenta_bancaria/cuenta_bancaria.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/cuenta_bancaria/lista_cuenta_bancaria.jsf";
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{cuentaBancariaHelper}")
    private CuentaBancariaHelper cuentaBancariaHelper;

    @Override
    @PostConstruct
    public void init() {
        iniciarComboTipoCuenta();
        iniciarComboTipoDocumento();
        iniciarComboBancos();
        iniciarComboTipoPersona();
        encerarComponentes();
    }

    /**
     * Este metodo llena las opciones para el combo de tipo Cuenta.
     */
    private void iniciarComboTipoCuenta() {
        cuentaBancariaHelper.getListaTipoCuenta().clear();
        iniciarCombos(cuentaBancariaHelper.getListaTipoCuenta());
        for (TipoCuentaEnum t : TipoCuentaEnum.values()) {
            cuentaBancariaHelper.getListaTipoCuenta().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    public String regresarLista() {
        return LISTA_ENTIDAD;
    }

    /**
     * Este metodo llena las opciones para el combo de tipo Documento.
     */
    private void iniciarComboTipoDocumento() {
        cuentaBancariaHelper.getTipoDocumento().clear();
        iniciarCombos(cuentaBancariaHelper.getTipoDocumento());
        for (TipoDocumentoEnum t : TipoDocumentoEnum.values()) {
            cuentaBancariaHelper.getTipoDocumento().add(new SelectItem(t.getNemonico(), t.getNombre()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo personas.
     */
    private void iniciarComboTipoPersona() {
        cuentaBancariaHelper.getListaTipoPersona().clear();
        iniciarCombos(cuentaBancariaHelper.getListaTipoPersona());
        for (TipoPersonaEnum t : TipoPersonaEnum.values()) {
            cuentaBancariaHelper.getListaTipoPersona().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo llena catalo de bancos.
     */
    private void iniciarComboBancos() {
        try {
            cuentaBancariaHelper.getListaBancos().clear();
            iniciarCombos(cuentaBancariaHelper.getListaBancos());
            List<Banco> buscarBancos = admServicio.listarTodasBancosVigente();
            for (Banco c : buscarBancos) {
                cuentaBancariaHelper.getListaBancos().add(new SelectItem(c.getId(), c.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "ec.markasoft.claseabierta.administracion.materia.consulta.materia", e);
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoCuenta(final String codigo) {
        return TipoCuentaEnum.obtenerDescripcion(codigo);
    }

    @Override
    public String buscar() {
        char opcion;
        cuentaBancariaHelper.setNombreEncontrado("");
        cuentaBancariaHelper.setCuentaBancaria(new CuentaBancaria());
        if (cuentaBancariaHelper.getTipoPersonaFiltro() != null && !cuentaBancariaHelper.getTipoPersonaFiltro().isEmpty()) {
            if (cuentaBancariaHelper.getNumeroIdentificacion() != null && !cuentaBancariaHelper.getNumeroIdentificacion().isEmpty()) {
                if (cuentaBancariaHelper.getTipoIdentificacion().equals(
                        TipoDocumentoEnum.CEDULA.getNemonico())
                        || cuentaBancariaHelper.getTipoIdentificacion().
                        equals(TipoDocumentoEnum.PASAPORTE.getNemonico())) {
                    opcion = cuentaBancariaHelper.getTipoPersonaFiltro().charAt(0);

                    switch (opcion) {
                        case 'S':
                            cuentaBancariaHelper.getCuentaBancaria().setBeneficiarioInstitucion(null);
                            cuentaBancariaHelper.getCuentaBancaria().setBeneficiarioEspecial(null);
                            buscarServidorPorCedula();
                            break;
                        case 'B':
                            cuentaBancariaHelper.getCuentaBancaria().setServidorId(null);
                            cuentaBancariaHelper.getCuentaBancaria().setBeneficiarioEspecial(null);
                            buscarBeneficiarioInstitucionalPorCedula();
                            break;
                        case 'E':
                            cuentaBancariaHelper.getCuentaBancaria().setServidorId(null);
                            cuentaBancariaHelper.getCuentaBancaria().setBeneficiarioInstitucion(null);
                            buscarBeneficiarioEspecialPorCedula();
                            break;
                        default:
                            mostrarMensajeEnPantalla("El campo tipo de Persona es requerido", FacesMessage.SEVERITY_ERROR);
                    }
                } else {
                    ponerMensajeError(NADA, "No ha ingresado el numero de identificación");
                }
            }
        }
        return null;
    }

    /**
     * metodo para buscar un servidor segun su numero y tipo de identificacion.
     *
     * @return
     */
    public String buscarServidorPorCedula() {
        try {
            cuentaBancariaHelper.getCuentaBancaria().setServidor(
                    admServicio.buscarServidorPorTipoDocumento(
                            cuentaBancariaHelper.getNumeroIdentificacion()));
            if (cuentaBancariaHelper.getCuentaBancaria().getServidor() == null) {
                cuentaBancariaHelper.getListaCuentaBancarias().clear();
                ponerMensajeError(NADA, "EL SERVIDOR NO ESTÁ REGISTRADO");
            } else {
                cuentaBancariaHelper.getCuentaBancaria().setServidorId(cuentaBancariaHelper.getCuentaBancaria().getServidor().getId());
                buscar(cuentaBancariaHelper.getTipoPersonaFiltro().charAt(0));
                cuentaBancariaHelper.setNombreEncontrado(cuentaBancariaHelper.getCuentaBancaria().getServidor().getApellidosNombres());
            }

        } catch (Exception ex) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * metodo para buscar un beneficiario institucional segun su numero y tipo
     * de identificacion.
     *
     * @return
     */
    public String buscarBeneficiarioInstitucionalPorCedula() {
        try {
            List<BeneficiarioInstitucional> lista = admServicio.listarTodosBeneficiarioInstitucionalPorNumeroIdentificacion(
                    cuentaBancariaHelper.getNumeroIdentificacion());
            System.out.println(" buscarBeneficiarioInstitucionalPorCedula encontrado ==>" + lista.size());
            if (!lista.isEmpty()) {
                cuentaBancariaHelper.getCuentaBancaria().setBeneficiarioInstitucion(lista.get(0));
                buscar(cuentaBancariaHelper.getTipoPersonaFiltro().charAt(0));
                cuentaBancariaHelper.setNombreEncontrado(cuentaBancariaHelper.getCuentaBancaria().getBeneficiarioInstitucion().getNombreBeneficiario());
            }
            if (cuentaBancariaHelper.getCuentaBancaria().getBeneficiarioInstitucion() == null) {
                cuentaBancariaHelper.getListaCuentaBancarias().clear();
                ponerMensajeError(NADA, "EL BENEFICIARIO INSTITUCIONAL NO ESTÁ REGISTRADO");
            }
        } catch (Exception ex) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * metodo para buscar un beneficiario especial segun su numero y tipo de
     * identificacion.
     *
     * @return
     */
    public String buscarBeneficiarioEspecialPorCedula() {
        try {
            List<BeneficiarioEspecial> lista = admServicio.listarTodosBeneficiarioEspecialPorNumeroIdentificacion(
                    cuentaBancariaHelper.getNumeroIdentificacion());
            System.out.println(" buscarBeneficiarioEspecialPorCedula encontrado ==>" + lista.size());
            if (!lista.isEmpty()) {
                cuentaBancariaHelper.getCuentaBancaria().setBeneficiarioEspecial(lista.get(0));
                buscar(cuentaBancariaHelper.getTipoPersonaFiltro().charAt(0));
                cuentaBancariaHelper.setNombreEncontrado(cuentaBancariaHelper.getCuentaBancaria().getBeneficiarioEspecial().getNombreBeneficiario());
            }
            if (cuentaBancariaHelper.getCuentaBancaria().getBeneficiarioEspecial() == null) {
                cuentaBancariaHelper.getListaCuentaBancarias().clear();
                ponerMensajeError(NADA, "EL BENEFICIARIO ESPECIAL NO ESTÁ REGISTRADO");
            }
        } catch (Exception ex) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String buscar(char tipo) {
        System.out.println(" tipo de busqueda ==> " + tipo);
        String mensaje = "SIN CUENTA BANCARIA REGISTRADA";
        try {
            cuentaBancariaHelper.getListaCuentaBancarias().clear();
            switch (tipo) {
                case 'S':
                    cuentaBancariaHelper.setListaCuentaBancarias(
                            admServicio.listarTodasCuentaBancariaVigentesPorServidor(
                                    cuentaBancariaHelper.getCuentaBancaria().getServidor().getId()));

                    mensaje = "EL SERVIDOR NO TIENE CUENTAS BANCARIAS REGISTRADAS";
                    break;

                case 'B':
                    cuentaBancariaHelper.setListaCuentaBancarias(
                            admServicio.listarTodasCuentaBancariaVigentesPorBeneficiarioInstitucional(
                                    cuentaBancariaHelper.getCuentaBancaria().getBeneficiarioInstitucion().getId()));
//                    cuentaBancariaHelper.setServidor(cuentaBancariaHelper.getCuentaBancaria().getServidor());
                    mensaje = "EL BENEFICIARIO INSTITUCIONAL NO TIENE CUENTAS BANCARIAS REGISTRADAS";
                    break;
                case 'E':
                    cuentaBancariaHelper.setListaCuentaBancarias(
                            admServicio.listarTodasCuentaBancariaVigentesPorBeneficiarioEspecial(
                                    cuentaBancariaHelper.getCuentaBancaria().getBeneficiarioEspecial().getId()));
//                    cuentaBancariaHelper.setServidor(cuentaBancariaHelper.getCuentaBancaria().getServidor());

                    mensaje = "EL BENEFICIARIO ESPECIAL NO TIENE CUENTAS BANCARIAS REGISTRADAS";
                    break;
            }
            if (cuentaBancariaHelper.getListaCuentaBancarias().isEmpty()) {
                cuentaBancariaHelper.getListaCuentaBancarias().clear();
                mostrarMensajeEnPantalla(mensaje, FacesMessage.SEVERITY_INFO);
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de cuentas bancarias", ex);
        }
        return null;
    }

    @Override
    public String guardar() {
        try {
            if (cuentaBancariaHelper.getEsNuevo()) {
                if (validarCodigo()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    admServicio.guardarCuentaBancaria(cuentaBancariaHelper.getCuentaBancaria());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    limpiador();
                }

            } else {
                admServicio.actualizarCuentaBancaria(cuentaBancariaHelper.getCuentaBancaria());
                iniciarDatosEntidad(cuentaBancariaHelper.getCuentaBancaria(), Boolean.FALSE);
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para limpiar los campos despues de guardar;
     */
    public void limpiador() {
        cuentaBancariaHelper.getCuentaBancaria().setNumerCuenta("");
        cuentaBancariaHelper.getCuentaBancaria().setPagoNomina(Boolean.FALSE);
        cuentaBancariaHelper.getCuentaBancaria().setId(null);
        cuentaBancariaHelper.getCuentaBancaria().setTipoCuenta("");
        cuentaBancariaHelper.getCuentaBancaria().setBancoId(null);
        cuentaBancariaHelper.getListaBancos().clear();
        cuentaBancariaHelper.getListaTipoCuenta().clear();
        iniciarComboTipoCuenta();
        iniciarComboBancos();
        iniciarComboTipoPersona();
        iniciarDatosEntidad(cuentaBancariaHelper.getCuentaBancaria(), Boolean.TRUE);
        cuentaBancariaHelper.setEsNuevo(Boolean.TRUE);
    }

    /**
     * metodo para validar si ya existe el código.
     *
     * @return hayCodigo Boolean.
     */
    public Boolean validarCodigo() {
        Boolean hayCodigo = true;
        try {
            cuentaBancariaHelper.getListaNumeroCuentaBancaria().clear();
            cuentaBancariaHelper.setListaNumeroCuentaBancaria(admServicio.listarTodosCuentaBancariaPorCodigo(
                    cuentaBancariaHelper.getCuentaBancaria().getNumerCuenta()));
            if (cuentaBancariaHelper.getListaNumeroCuentaBancaria().isEmpty()) {
                hayCodigo = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del código", ex);
        }
        return hayCodigo;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(cuentaBancariaHelper.getCuentaBancariaEditDelete());
            CuentaBancaria d = (CuentaBancaria) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            cuentaBancariaHelper.setCuentaBancaria(d);
            cuentaBancariaHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * LImpia los componentes de la pantalla.
     */
    public void encerarComponentes() {
        cuentaBancariaHelper.setTipoIdentificacion(null);
        cuentaBancariaHelper.setNumeroIdentificacion(null);
        cuentaBancariaHelper.setNombreEncontrado(null);
        cuentaBancariaHelper.setTipoPersonaFiltro(null);
        cuentaBancariaHelper.getListaCuentaBancarias().clear();
    }

    @Override
    public String iniciarNuevo() {
        cuentaBancariaHelper.getCuentaBancaria().setBanco(new Banco());
        cuentaBancariaHelper.setEsNuevo(Boolean.TRUE);
        cuentaBancariaHelper.getCuentaBancaria().setPagoNomina(Boolean.TRUE);
        iniciarDatosEntidad(cuentaBancariaHelper.getCuentaBancaria(), Boolean.TRUE);
        cuentaBancariaHelper.getListaCuentaBancarias().clear();
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the cuentaBancariaHelper
     */
    public CuentaBancariaHelper getCuentaBancariaHelper() {
        return cuentaBancariaHelper;
    }

    /**
     * @param cuentaBancariaHelper the cuentaBancariaHelper to set
     */
    public void setCuentaBancariaHelper(CuentaBancariaHelper cuentaBancariaHelper) {
        this.cuentaBancariaHelper = cuentaBancariaHelper;
    }
}
