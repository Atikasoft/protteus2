/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.utilitarios.Utilitarios;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.CloseEvent;

/**
 *
 * @author christian
 */
@ManagedBean
@ViewScoped
public class InstitucionControlador extends Utilitarios implements Serializable {

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    private String nombre;

    private String codigo;

    private boolean desconcentrado;

    private int numeroInstitucion;

//    private List<Institucion> listaInstituciones = new ArrayList<Institucion>();

    public void buscar() {
        try {
//            Usuario usuario = obtenerUsuarioLogeado();
//            if (usuario.getTipoUsuario().getNemonico().equals(RolesEnum.SUPER_ADMINISTRADOR.getNemonico())) {
//                this.listaInstituciones = institucionServicio.listarInstitucionesActivas(this.nombre, this.codigo,
//                        this.desconcentrado);
//                this.listaInstituciones.addAll(institucionServicio.listarInstitucionesUsuario(usuario));
//                this.numeroInstitucion = this.listaInstituciones.size();
//            }
//
//            if (usuario.getTipoUsuario().getNemonico().equals(RolesEnum.ADMINISTRADOR_INSTITUCION.getNemonico())) {
//                if (this.desconcentrado) {
//                    this.listaInstituciones = institucionServicio.listarInstitucionesHijas(obtenerInstitucion(), 20,
//                            false);
//                }
//                this.listaInstituciones.addAll(institucionServicio.listarInstitucionesUsuario(usuario));
//                this.numeroInstitucion = this.listaInstituciones.size();
//            }

        } catch (Exception e) {
            ponerMensajeError(NADA, "No se puede listar los registros " + e.getMessage());
            error(getClass().getName(), "no se puede listar registros", e);
        }
    }

    public void nuevo(CloseEvent event) {
//        this.codigo = null;
//        this.desconcentrado = false;
//        this.listaInstituciones = new ArrayList<Institucion>();
//        this.numeroInstitucion = 0;
//        this.nombre = null;
    }

//    public void cargarInstitucion(Institucion institucion) {
//        try {
//            actualizarInstitucionEnUsuarioConectado(institucion);
//            setearInstitucion(institucion);
//            nuevo1();
//            redirect(getContextName() + "/pages/index.jsf");
//        } catch (Exception e) {
//            ponerMensajeAlerta(NADA, e.getMessage());
//            error(getClass().getName(), "no se puede direccionar", e);
//        }
//    }

    /**
     * Actualiza la institucion en el usuario conectado.
     *
     * @param institucion
     * @throws ServicioException
     */
//    private void actualizarInstitucionEnUsuarioConectado(final Institucion institucion) throws ServicioException {
//        UsuarioVO usuario = (UsuarioVO) getSession().getAttribute(BaseControlador.DTO_USER_NAME);
//        ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal local =
//                administracionServicio.buscarInstitucionPorCodigoYEjercicioFiscal(UtilCadena.concatenar(institucion.
//                getCodigoCatastro(), ".", institucion.getId()), usuario.getIdEjercicioFiscal());
//        if (local == null) {
//            throw new ServicioException(UtilCadena.concatenar("Institución ", institucion.getNombre(),
//                    " no se encuentra habilitado en el sistema."));
//        } else {
//            usuario.setIdInstitucion(local.getId());
//            usuario.setCodigoInstitucion(local.getInstitucion().getCodigo());
//        }
//    }

//    public void nuevo1() {
//        this.codigo = null;
//        this.desconcentrado = false;
//        this.listaInstituciones = new ArrayList<Institucion>();
//        this.numeroInstitucion = 0;
//        this.nombre = null;
//    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the desconcentrado
     */
    public boolean isDesconcentrado() {
        return desconcentrado;
    }

    /**
     * @param desconcentrado the desconcentrado to set
     */
    public void setDesconcentrado(boolean desconcentrado) {
        this.desconcentrado = desconcentrado;
    }

    /**
     * @return the listaInstituciones
     */
//    public List<Institucion> getListaInstituciones() {
//        return listaInstituciones;
//    }

    /**
     * @param listaInstituciones the listaInstituciones to set
     */
//    public void setListaInstituciones(List<Institucion> listaInstituciones) {
//        this.listaInstituciones = listaInstituciones;
//    }

    /**
     * @return the numeroInstitucion
     */
    public int getNumeroInstitucion() {
        return numeroInstitucion;
    }

    /**
     * @param numeroInstitucion the numeroInstitucion to set
     */
    public void setNumeroInstitucion(int numeroInstitucion) {
        this.numeroInstitucion = numeroInstitucion;
    }
}
