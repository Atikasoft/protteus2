package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.DesconcentradoHelper;
import ec.com.atikasoft.proteus.converter.ServidorConverter;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.enums.EstadoPuestoEnum;
import ec.com.atikasoft.proteus.enums.EstadosPersonalEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Desconcentrado;
import ec.com.atikasoft.proteus.modelo.DesconcentradoApoyo;
import ec.com.atikasoft.proteus.modelo.DesconcentradoUnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.servicio.TareaServicio;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "desconcentradoBean")
@ViewScoped
public class DesconcentradoControlador extends CatalogoControlador {

    /**
     * pagina listado de unidades desconcentradas
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/desconcentrado/lista_desconcentrado.jsf";
    /**
     * pagina donde se configuran las unidades organizacionales y funciones
     */
    public static final String FORMULARIO_FUNCION_UNID_ORG
            = "/pages/administracion/desconcentrado/funciones_unid_org.jsf";
    /**
     * pagina principal
     */
    public static final String PAGINA_PORTAL = "/pages/portal_rrhh.jsf";
    /**
     * servicio administracion
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * servicio de desconcentrado
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;
    /**
     * servicio distributivo puesto
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;
    /**
     * servicio tareas
     */
    @EJB
    private TareaServicio tareaServicio;
    /**
     * servicio servidor
     */
    @EJB
    private ServidorServicio servidorServicio;
    /**
     * dao de parametro institucional
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;
    /**
     * helper de la clase
     */
    @ManagedProperty("#{desconcentradoHelper}")
    private DesconcentradoHelper desconcentradoHelper;

    /**
     *
     */
    public DesconcentradoControlador() {
        super();
    }

    /**
     *
     */
    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(desconcentradoHelper);
        setDesconcentradoHelper(desconcentradoHelper);
        desconcentradoHelper.setCodigoFuncionSeleccionada("");
        desconcentradoHelper.setCodigoFuncionOrigenCopia("");
        buscar();
    }

    /**
     * recupera la lista de unidades desconcentradas
     *
     * @return
     */
    @Override
    public String buscar() {
        try {
            desconcentradoHelper.getListaDesconcentrados().clear();
            desconcentradoHelper.setListaDesconcentrados(desconcentradoServicio.listarDesconcentradosTodosVigentes());
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), NADA, ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * prepara las variables para crear una nueva unidad descocnetrada
     *
     * @return
     */
    @Override
    public String iniciarNuevo() {
        desconcentradoHelper.setEsNuevo(Boolean.TRUE);
        desconcentradoHelper.setDesconcentrado(new Desconcentrado());
        desconcentradoHelper.getDesconcentradoVO().setDesconcentrado(new Desconcentrado());
        iniciarDatosEntidad(desconcentradoHelper.getDesconcentradoVO().getDesconcentrado(), Boolean.TRUE);
        desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradoApoyos().clear();
        desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradosUniOrg().clear();
        desconcentradoHelper.setListaServidoresApoyoSeleccionados(new ArrayList<Servidor>());
        ejecutarComandoPrimefaces("dlgDesconcentrado.show()");
        return null;
    }

    /**
     * prepara las variables para la edicion de una unidad desconcentrada
     *
     * @return
     */
    @Override
    public String iniciarEdicion() {
        try {
            desconcentradoHelper.setEsNuevo(Boolean.FALSE);
            Desconcentrado dCopia = (Desconcentrado) BeanUtils.cloneBean(
                    desconcentradoHelper.getDesconcentradoVO().getDesconcentrado());
            desconcentradoHelper.setDesconcentrado(dCopia);
            desconcentradoHelper.setServidorResponsableTempId(
                    desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().getServidorResponsable().getId());

            poblarListaServidoresApoyoSeleccionados();
            desconcentradoHelper.setListaDesconcentradosUnidadesOrganizacionales(
                    desconcentradoServicio.recuperarUnidadesOrganizacionalesAsociadasADesconcentrado(
                            desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().getId()));
            ejecutarComandoPrimefaces("dlgDesconcentrado.show()");

        } catch (Exception e) {
            error(getClass().getName(), "Error al intentar iniciar la edición de la Unidad Desconcentrada", e);
        }
        return null;
    }

    /**
     * prepara las variables para la configuración de funciones por unidades organizacionales
     *
     * @return
     */
    public String irAConfigurarFuncionesPorUnidadesOrganizacionales() {
        try {
            iniciarCombos(desconcentradoHelper.getListaFuncionesDesconcentrado());
            desconcentradoHelper.setCodigoFuncionSeleccionada("");
            desconcentradoHelper.setCodigoFuncionOrigenCopia("");
            for (FuncionesDesconcentradosEnum fd : FuncionesDesconcentradosEnum.values()) {
                desconcentradoHelper.getListaFuncionesDesconcentrado().add(
                        new SelectItem(fd.getCodigo(), fd.getDescripcion()));
            }

            llenarArbolUnidadesOrganizacionales();
            iniciarCombos(desconcentradoHelper.getListaFuncionesDesconcentrado2());
            poblarListaServidoresApoyoSeleccionados();

            desconcentradoHelper.setListaDesconcentradosUnidadesOrganizacionales(
                    desconcentradoServicio.recuperarUnidadesOrganizacionalesAsociadasADesconcentrado(
                            desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().getId()));

            desconcentradoHelper.getListaIdUnidadesOrganizacionalesSeleccionadas().clear();
            for (DesconcentradoUnidadOrganizacional duo : desconcentradoHelper
                    .getListaDesconcentradosUnidadesOrganizacionales()) {
                desconcentradoHelper.getListaIdUnidadesOrganizacionalesSeleccionadas().add(
                        duo.getUnidadOrganizacional().getId());
            }

            desconcentradoHelper.setServidorResponsableTempId(
                    desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().getServidorResponsable().getId());

            desconcentradoHelper.getMapUnidOrgFunciones().clear();
            desconcentradoHelper.setEsNuevo(Boolean.FALSE);

        } catch (Exception e) {
            error(getClass().getName(), "Error al intentar cargar la lista de Unidades Organizacionales", e);
            return null;
        }
        return FORMULARIO_FUNCION_UNID_ORG;
    }

    /**
     * llena la lista de servidores de apoyos seleccionados para mostrar en pantalla
     */
    private void poblarListaServidoresApoyoSeleccionados() {
        try {
            List<DesconcentradoApoyo> lDa = desconcentradoServicio.recuperarTodosServidoresDeApoyoDadoDesconcentradoId(
                    desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().getId());

            desconcentradoHelper.setListaServidoresApoyoSeleccionados(new ArrayList<Servidor>());
            desconcentradoHelper.setListaServApoyoSeleccionadosTemp(new ArrayList<Servidor>());
            desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradoApoyos().clear();
            for (DesconcentradoApoyo da : lDa) {
                if (da.getVigente()) {
                    desconcentradoHelper.getListaServidoresApoyoSeleccionados().add(da.getServidorApoyo());
                    desconcentradoHelper.getListaServApoyoSeleccionadosTemp().add(da.getServidorApoyo());
                }
                desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradoApoyos().add(da);
            }
        } catch (ServicioException e) {
            error(getClass().getName(), "Error al intentar poblarListaServidoresApoyoSeleccionados()", e);
        }
    }

    /**
     * verifica si el serevidor seleccionado como responsable no haya sido seleccionado ya ni tenga tramites pendientes
     *
     * @param e evento disparado en la vista
     */
    public void verificarServidorResponsableNoEsteSeleccionado(SelectEvent e) {
        Servidor serv = (Servidor) e.getObject();
        if (validarServidorResponsable(null, true)) {
            int l = desconcentradoHelper.getListaServidoresApoyoSeleccionados().size();
            ListIterator<Servidor> li = desconcentradoHelper.getListaServidoresApoyoSeleccionados().listIterator(l);
            while (li.hasPrevious()) {
                Servidor s = li.previous();
                if (s.getId() != null && s.getId().equals(serv.getId())) {
                    desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().setServidorResponsable(
                            desconcentradoHelper.getDesconcentrado().getServidorResponsable());
                    mostrarMensajeEnPantalla(
                            "El servidor seleccionado ya está incluído como servidor de apoyo de la "
                            + "Unidad Desconcentrada.", FacesMessage.SEVERITY_ERROR);
                    break;
                }
            }
        }
    }

    /**
     * verifica si el serevidor seleccionado como apoyo no haya sido seleccionado ya
     *
     * @param e evento disparado en la vista
     */
    public void verificarServidorApoyoNoEsteSeleccionado(SelectEvent e) {
        Servidor serv = (Servidor) e.getObject();
        Servidor servResponsable = desconcentradoHelper
                .getDesconcentradoVO().getDesconcentrado().getServidorResponsable();
        if (servResponsable.getId() != null && servResponsable.getId().equals(serv.getId())) {
            int cant = desconcentradoHelper.getListaServidoresApoyoSeleccionados().size();
            desconcentradoHelper.getListaServidoresApoyoSeleccionados().remove(cant - 1);
            mostrarMensajeEnPantalla(
                    "El servidor seleccionado ya está incluído como servidor responsable de la Unidad Desconcentrada.",
                    FacesMessage.SEVERITY_ERROR);
        } else {
            int l = desconcentradoHelper.getListaServidoresApoyoSeleccionados().size();
            List<Servidor> ls = (List<Servidor>) obtenerAtributoSession(ServidorConverter.CLAVE_SESSION);
            ListIterator<Servidor> li = desconcentradoHelper.getListaServidoresApoyoSeleccionados().listIterator(l - 1);
            while (li.hasPrevious()) {
                Servidor s = li.previous();
                if (s.getId() != null && serv.getId().equals(s.getId())) {
                    li.next();
                    li.remove();
                    mostrarMensajeEnPantalla(
                            "El servidor seleccionado ya está incluído como servidor de apoyo "
                            + "de la Unidad Desconcentrada.",
                            FacesMessage.SEVERITY_ERROR);
                    break;
                }
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String guardar() {
        try {
            if (!nombreDesconcentradoRepetido()) {
                List<DesconcentradoUnidadOrganizacional> lUOTemp;
                if (desconcentradoHelper.getGuardarSoloDatosGeneralesYApoyos()) {
                    List<DesconcentradoApoyo> lTemp;
                    if (validarServidorResponsable(null, true) && validarTodosServidoresDeApoyo(null, true)) {
                        lTemp = new ArrayList(
                                desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradoApoyos());
                        desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradoApoyos().clear();
                        if (desconcentradoHelper.getListaServidoresApoyoSeleccionados() != null
                                && !desconcentradoHelper.getListaServidoresApoyoSeleccionados().isEmpty()) {
                            for (Servidor s : desconcentradoHelper.getListaServidoresApoyoSeleccionados()) {
                                DesconcentradoApoyo da = new DesconcentradoApoyo();
                                boolean encontrado = false;
                                for (DesconcentradoApoyo desAp : lTemp) {
                                    if (s.getId().equals(desAp.getServidorApoyo().getId())) {
                                        da = desAp;
                                        if (!da.getVigente()) {
                                            iniciarDatosEntidad(da, Boolean.FALSE);
                                            da.setVigente(Boolean.TRUE);
                                        }
                                        encontrado = true;
                                        break;
                                    }
                                }
                                if (!encontrado) {
                                    iniciarDatosEntidad(da, Boolean.TRUE);
                                    da.setServidorApoyo(s);
                                }
                                da.setDesconcentrado(new Desconcentrado());
                                desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradoApoyos().add(da);
                            }
                        }

                    } else {
                        return null;
                    }
                    actualizarComponente("frmListaDesconcentrados");

                } else if (!desconcentradoHelper.getMapUnidOrgFunciones().isEmpty()) {
                    lUOTemp = new ArrayList(desconcentradoHelper.getListaDesconcentradosUnidadesOrganizacionales());
                    desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradosUniOrg().clear();
                    for (Long idUo : desconcentradoHelper.getMapUnidOrgFunciones().keySet()) {
                        DesconcentradoUnidadOrganizacional dUniOrg = new DesconcentradoUnidadOrganizacional();
                        boolean encontrada = false;
                        for (DesconcentradoUnidadOrganizacional duo : lUOTemp) {
                            if (idUo.equals(duo.getUnidadOrganizacional().getId())) {
                                dUniOrg = duo;
                                iniciarDatosEntidad(dUniOrg, Boolean.FALSE);
                                dUniOrg.setVigente(Boolean.TRUE);
                                encontrada = true;
                                break;
                            }
                        }
                        if (!encontrada) {
                            iniciarDatosEntidad(dUniOrg, Boolean.TRUE);
                            dUniOrg.setUnidadOrganizacional(new UnidadOrganizacional(idUo));
                        }
                        StringBuilder funciones = new StringBuilder("");
                        for (String f : desconcentradoHelper.getMapUnidOrgFunciones().get(idUo).keySet()) {
                            if (!f.trim().isEmpty()) {
                                funciones.append(f).append(",");
                            }
                        }
                        int longitud = funciones.length();
                        if (longitud > 0) {
                            funciones.deleteCharAt(longitud - 1);
                        }
                        dUniOrg.setFunciones(funciones.toString());
                        dUniOrg.setDesconcentrado(new Desconcentrado());
                        desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradosUniOrg().add(dUniOrg);
                    }
                } else {
                    mostrarMensajeEnPantalla(
                            "Debe seleccionar al menos una Unidad Organizacional.",
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                desconcentradoServicio.guardarDesconcentrado(
                        desconcentradoHelper.getGuardarSoloDatosGeneralesYApoyos(),
                        desconcentradoHelper.getDesconcentradoVO().getDesconcentrado(),
                        desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradoApoyos(),
                        desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradosUniOrg());

                if (desconcentradoHelper.getEsNuevo()) {
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                } else {
                    mostrarMensajeEnPantalla(REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_INFO);
                }

                if (desconcentradoHelper.getGuardarSoloDatosGeneralesYApoyos()) {
                    ejecutarComandoPrimefaces("dlgDesconcentrado.hide();");
                    buscar();
                } else {
                    desconcentradoHelper.setListaDesconcentradosUnidadesOrganizacionales(
                            desconcentradoServicio.recuperarUnidadesOrganizacionalesAsociadasADesconcentrado(
                                    desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().getId()));

                    desconcentradoHelper.getListaIdUnidadesOrganizacionalesSeleccionadas().clear();
                    for (DesconcentradoUnidadOrganizacional duo
                            : desconcentradoHelper.getListaDesconcentradosUnidadesOrganizacionales()) {
                        desconcentradoHelper.getListaIdUnidadesOrganizacionalesSeleccionadas().add(
                                duo.getUnidadOrganizacional().getId());
                    }
                    actualizarArbolUO(Boolean.FALSE);
                    return null;
                }
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error al guardar", e);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * verifica que el servidor responsable no tenga tareas pendientes en la unidad organizacional pasada como parametro
     *
     * @param codigoUnidadOrg codigo unidad organizacional
     * @param mostrarMensaje mostrar o no un mensaje
     * @return
     */
    private Boolean validarServidorResponsable(String codigoUnidadOrg, boolean mostrarMensaje) {
        if (!desconcentradoHelper.getEsNuevo()) {
            try {
                Servidor nuevoServResponsable = desconcentradoHelper
                        .getDesconcentradoVO().getDesconcentrado().getServidorResponsable();
                if (desconcentradoHelper.getServidorResponsableTempId() != null
                        && !desconcentradoHelper.getServidorResponsableTempId().equals(nuevoServResponsable.getId())
                        && servidorTieneTareasAsignadas(
                                desconcentradoHelper.getServidorResponsableTempId(), codigoUnidadOrg)
                        && mostrarMensaje) {

                    Servidor sr = servidorServicio.buscarServidorInstitucion(
                            obtenerUsuarioConectado().getInstitucion().getId(),
                            desconcentradoHelper.getServidorResponsableTempId()).getServidor();
                    desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().setServidorResponsable(sr);

                    mostrarMensajeEnPantalla(
                            "No se puede realizar el cambio de responsable de la Unidad Desconcentrada"
                            + " debido a que el sistema registra trámites pendientes de atención",
                            FacesMessage.SEVERITY_ERROR);
                    actualizarComponente("frmDesconcentrado:servidorResponsable");
                    return Boolean.FALSE;
                }

            } catch (Exception e) {
                mostrarMensajeEnPantalla(ERROR_GENERICO, FacesMessage.SEVERITY_ERROR);
                e.printStackTrace();
            }
        }
        return Boolean.TRUE;
    }

    /**
     * verifica que los servidores de apoyo no tengan tareas pendientes en la unidad organizacional pasada como
     * parametro
     *
     * @param codigoUnidadOrg codigo unidad organizacional
     * @param mostrarMensaje mostrar o no un mensaje
     * @return
     */
    private Boolean validarTodosServidoresDeApoyo(String codigoUnidadOrg, boolean mostrarMensaje) {
        if (!desconcentradoHelper.getEsNuevo()) {
            for (Servidor s : desconcentradoHelper.getListaServApoyoSeleccionadosTemp()) {
                boolean encontrado = false;
                if (desconcentradoHelper.getListaServidoresApoyoSeleccionados() != null) {
                    for (Servidor ss : desconcentradoHelper.getListaServidoresApoyoSeleccionados()) {
                        if (s.getVigente() && s.getId().equals(ss.getId())) {
                            encontrado = true;
                            break;
                        }
                    }
                }
                if (!encontrado && servidorTieneTareasAsignadas(s.getId(), codigoUnidadOrg) && mostrarMensaje) {
                    mostrarMensajeEnPantalla(
                            "No se puede cambiar o eliminar el servidor de apoyo " + s.getApellidosNombres()
                            + " de la Unidad Desconcentrada, debido a que el sistema registra "
                            + "trámites pendientes de atención", FacesMessage.SEVERITY_ERROR);
                    if (desconcentradoHelper.getListaServidoresApoyoSeleccionados() == null) {
                        desconcentradoHelper.setListaServidoresApoyoSeleccionados(new ArrayList<Servidor>());
                    }
                    desconcentradoHelper.getListaServidoresApoyoSeleccionados().addAll(
                            desconcentradoHelper.getListaServApoyoSeleccionadosTemp());
                    actualizarComponente("frmDesconcentrado:servidoresApoyos");
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;
    }

    /**
     * verifica que no existan unidades desconcetradas con el mismo nombre
     *
     * @return
     */
    private Boolean nombreDesconcentradoRepetido() {
        boolean mostrarMensaje = false;
        final Collator instance = Collator.getInstance();
        instance.setStrength(Collator.NO_DECOMPOSITION);

        Desconcentrado desc = desconcentradoHelper.getDesconcentradoVO().getDesconcentrado();
        for (Desconcentrado d : desconcentradoHelper.getListaDesconcentrados()) {
            if (desc.getId() != null && !desc.getId().equals(d.getId())
                    && instance.compare(
                            desc.getNombre().toLowerCase().trim(), d.getNombre().toLowerCase().trim()) == 0) {
                mostrarMensaje = true;
            } else if (desc.getId() == null && instance.compare(
                    desc.getNombre().toLowerCase().trim(), d.getNombre().toLowerCase().trim()) == 0) {
                mostrarMensaje = true;
            }
            if (mostrarMensaje) {
                mostrarMensajeEnPantalla("La Unidad Desconcentrada ya existe", FacesMessage.SEVERITY_ERROR);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * elimina una undad desconcentrada
     *
     * @return
     */
    @Override
    public String borrar() {
        try {
            desconcentradoHelper.setEsNuevo(Boolean.FALSE);
            desconcentradoHelper.setServidorResponsableTempId(
                    desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().getServidorResponsable().getId());

            boolean err = servidorTieneTareasAsignadas(desconcentradoHelper.getServidorResponsableTempId(), null);
            if (!err) {
                desconcentradoHelper.getDesconcentradoVO().setListaDesconcentradoApoyos(
                        desconcentradoServicio.recuperarServidoresDeApoyoDadoDesconcentradoId(
                                desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().getId()));

                for (DesconcentradoApoyo da : desconcentradoHelper
                        .getDesconcentradoVO().getListaDesconcentradoApoyos()) {
                    err |= servidorTieneTareasAsignadas(da.getServidorApoyo().getId(), null);
                    if (err) {
                        break;
                    }
                    iniciarDatosEntidad(da, Boolean.FALSE);
                    da.setDesconcentrado(new Desconcentrado());
                    da.setVigente(Boolean.FALSE);
                }
            }

            if (err) {
                mostrarMensajeEnPantalla("No se puede eliminar la Unidad Desconcentrada debido a que el sistema"
                        + " registra trámites pendientes de atención", FacesMessage.SEVERITY_ERROR);
                return null;
            }

            iniciarDatosEntidad(desconcentradoHelper.getDesconcentradoVO().getDesconcentrado(), Boolean.FALSE);
            desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().setVigente(Boolean.FALSE);
            desconcentradoHelper.getDesconcentradoVO().setListaDesconcentradosUniOrg(
                    desconcentradoServicio.recuperarUnidadesOrganizacionalesAsociadasADesconcentrado(
                            desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().getId()));

            for (DesconcentradoUnidadOrganizacional duo
                    : desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradosUniOrg()) {
                iniciarDatosEntidad(duo, Boolean.FALSE);
                duo.setDesconcentrado(new Desconcentrado());
                duo.setVigente(Boolean.FALSE);
            }

            desconcentradoServicio.guardarDesconcentrado(
                    desconcentradoHelper.getGuardarSoloDatosGeneralesYApoyos(),
                    desconcentradoHelper.getDesconcentradoVO().getDesconcentrado(),
                    desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradoApoyos(),
                    desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradosUniOrg());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
            buscar();

        } catch (Exception e) {
            error(getClass().getName(), ERROR_REGISTRO_ELIMINADO, e);
        }
        ejecutarComandoPrimefaces("confirmEliminacion.hide();");
        return null;
    }

    /**
     * recupera los servidores que estén activos y ocupando un puesto dado un nombre
     *
     * @param nombre nombre del servidor
     * @return
     */
    public List<Servidor> buscarServidoresActivosEnPuestosOcupadosPorNombre(String nombre) {
        List<Servidor> servidores = new ArrayList();
        try {
            if (nombre != null && !nombre.isEmpty()) {
                ParametroInstitucional pi
                        = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                                getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());

                BusquedaPuestoVO b = new BusquedaPuestoVO();
                b.setNombreServidor(nombre);
                b.setInicioConsulta(0);
                b.setFinConsulta(10);
                b.setPuestoVacante(Boolean.FALSE);
                b.setEstadoPuestoCodigo(EstadoPuestoEnum.OCUPADO.getCodigo());
                b.setEstadoServidorCodigo(EstadosPersonalEnum.ACTIVO.getCodigo());
                List<DistributivoDetalle> puestos = distributivoPuestoServicio.buscar(
                        b, false, obtenerUsuarioConectado(), esRRHH(pi.getValorTexto()));

                for (DistributivoDetalle puesto : puestos) {
                    servidores.add(puesto.getServidor());
                }

                if (servidores.isEmpty()) {
                    Servidor s = new Servidor(0L);
                    s.setApellidosNombres("No se encontraron resultados...");
                    servidores.add(s);
                }

                List<Servidor> s = new ArrayList<>(servidores);
                Servidor servidorResponsable = desconcentradoHelper
                        .getDesconcentradoVO().getDesconcentrado().getServidorResponsable();
                String idComponente = obtenerParametrosFaces().get("javax.faces.source");
                if (idComponente.endsWith("servidorResponsable") && servidorResponsable != null) {
                    s.add(servidorResponsable);
                } else if (idComponente.endsWith("servidoresApoyos")) {
                    s.addAll(desconcentradoHelper.getListaServidoresApoyoSeleccionados());
                }

                ponerAtributoSession(ServidorConverter.CLAVE_SESSION, s);
            }

        } catch (DaoException | ServicioException e) {
            error(getClass().getName(), "Error al buscar servidores", e);
        }

        return servidores;
    }

    /**
     * verifica si un servidor tiene tareas pendientes en una unidad organizacional
     *
     * @param servidorId id del servidor
     * @param codigosunidadesOrg codigos de las unidades organizacionales
     * @return
     */
    private Boolean servidorTieneTareasAsignadas(Long servidorId, String codigosunidadesOrg) {
        try {
            Servidor serv = servidorServicio.buscarServidorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId(), servidorId).getServidor();
            if (codigosunidadesOrg == null) {
                StringBuilder codigosUnidadesOrg = new StringBuilder();
                for (DesconcentradoUnidadOrganizacional duo : desconcentradoHelper
                        .getListaDesconcentradosUnidadesOrganizacionales()) {
                    codigosUnidadesOrg.append(duo.getUnidadOrganizacional().getCodigo()).append(",");
                }
                codigosunidadesOrg = codigosUnidadesOrg.length() > 0
                        ? codigosUnidadesOrg.deleteCharAt(codigosUnidadesOrg.length() - 1).toString()
                        : "";
            }

            Long totalTareasAsignadas = tareaServicio.contarTareasPorUsuarioUnidadesOrganizacionalesEstado(
                    serv.getNumeroIdentificacion(), codigosunidadesOrg, Boolean.TRUE);
            if (totalTareasAsignadas != null && totalTareasAsignadas > 0L) {
                return Boolean.TRUE;
            }

        } catch (DaoException | ServicioException e) {
            error(getClass().getName(), "verificarServidorTieneTareasAsignadas()", e);
        }

        return Boolean.FALSE;
    }

    /**
     * importa la lista de unidades configuradas en una funcion a la funcion actual
     */
    public void importarUnidadesOrganizacionalesDesdeOtraFuncion() {
        String codFunDestino = desconcentradoHelper.getCodigoFuncionSeleccionada();
        for (Long idUo : desconcentradoHelper.getMapUnidOrgFunciones().keySet()) {
            desconcentradoHelper.getMapUnidOrgFunciones().get(idUo).put(codFunDestino, null);
        }
        actualizarArbolUO(Boolean.TRUE);
        mostrarMensajeEnPantalla("Configuración importada satisfactoriamente", FacesMessage.SEVERITY_INFO);
    }

    /**
     * actualiza el árbol de unidades organizacionales una vez que se selecciona una función
     *
     * @param actualizarComboFuncionCopia indica si se actualiza el combo de funciones a copiar
     * @return
     */
    public String actualizarArbolUO(Boolean actualizarComboFuncionCopia) {
        try {
            desconcentradoHelper.setCodigoFuncionOrigenCopia("");
            if (actualizarComboFuncionCopia) {
                iniciarCombos(desconcentradoHelper.getListaFuncionesDesconcentrado2());
                for (SelectItem item : desconcentradoHelper.getListaFuncionesDesconcentrado()) {
                    if (!((String) item.getValue()).equals("")
                            && !desconcentradoHelper.getCodigoFuncionSeleccionada().equals(item.getValue())) {
                        desconcentradoHelper.getListaFuncionesDesconcentrado2().add(item);
                    }
                }
            }
            List<DesconcentradoUnidadOrganizacional> lduo = desconcentradoHelper
                    .getListaDesconcentradosUnidadesOrganizacionales();
            for (DesconcentradoUnidadOrganizacional duo : lduo) {
                if (!desconcentradoHelper.getMapUnidOrgFunciones().containsKey(duo.getUnidadOrganizacional().getId())) {
                    Map<String, String> mapaFunciones = llenarMapaFunciones(
                            duo.getUnidadOrganizacional().getId(), false);
                    for (String f : duo.getFuncionesArreglo()) {
                        mapaFunciones.put(f, null);
                    }
                }
            }
            marcarUnidadesSeleccionadasPorFuncion(desconcentradoHelper.getRoot());

        } catch (Exception e) {
            error(getClass().getName(), "Error Actualizando Árbol de Unidades Organizacionales", e);
        }

        return null;
    }

    /**
     * identifica las funciones que se pueden realizar en una unidad organizacional
     *
     * @param unidadOrgId id de la unidad organizacional
     * @param unidadYaSeleccionada indica si la unidad ya ha sido seleccionada
     * @return
     */
    private Map<String, String> llenarMapaFunciones(Long unidadOrgId, boolean unidadYaSeleccionada) {
        Map<String, String> mapaFunciones = new HashMap();
        if (unidadYaSeleccionada) {
            mapaFunciones = desconcentradoHelper.getMapUnidOrgFunciones().get(unidadOrgId);
        } else {
            desconcentradoHelper.getMapUnidOrgFunciones().put(unidadOrgId, mapaFunciones);
        }
        return mapaFunciones;
    }

    /**
     * marca checkbox de la columna "asignada" en el arbol de unidades organizacionales de la unidades que están
     * seleccionadas
     *
     * @param arbol
     */
    private void marcarUnidadesSeleccionadasPorFuncion(TreeNode arbol) {
        try {
            UnidadOrganizacional uo = (UnidadOrganizacional) arbol.getData();
            uo.setSeleccionado(Boolean.FALSE);
            uo.setTieneConfiguracionInterna(Boolean.FALSE);
            Map<String, String> funciones = desconcentradoHelper.getMapUnidOrgFunciones().get(uo.getId());
            if (funciones != null && funciones.containsKey(desconcentradoHelper.getCodigoFuncionSeleccionada())) {
                uo.setSeleccionado(Boolean.TRUE);
                marcarNodosAncestrosComoConfigurados(arbol);
            }
            for (TreeNode nodo : arbol.getChildren()) {
                marcarUnidadesSeleccionadasPorFuncion(nodo);
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error en marcarUnidadesSeleccionadas()...", e);
        }
    }

    /**
     * marca como configuradas las unidades organizacionales que tienen al menos una unidad descendiente seleccionada
     *
     * @param nodo
     */
    private void marcarNodosAncestrosComoConfigurados(TreeNode nodo) {
        TreeNode nodoPadre = nodo.getParent();
        if (nodoPadre != null) {
            UnidadOrganizacional uoPadre = (UnidadOrganizacional) nodoPadre.getData();
            uoPadre.setTieneConfiguracionInterna(Boolean.TRUE);
            marcarNodosAncestrosComoConfigurados(nodoPadre);
        }
    }

    /**
     * marca o desmarca el chekbox de asiganción de la unidad para la función seleccionada y actualiza el árbol
     *
     * @param unidadOrg unidad organizacional
     */
    public void actualizarUnidadModificada(Object unidadOrg) {
        UnidadOrganizacional uo = (UnidadOrganizacional) unidadOrg;
        boolean unidadSeleccionadaGestionada = false;
        //verificar si la unidad seleccionada no esta siendo gestionada por otra Unidad Desconcentrada
        if (uo.getSeleccionado()) {
            for (Desconcentrado d : desconcentradoHelper.getListaDesconcentrados()) {
                if (!desconcentradoHelper.getDesconcentradoVO().getDesconcentrado().getId().equals(d.getId())) {
                    for (DesconcentradoUnidadOrganizacional uod : d.getListaUnidadOrganizacional()) {
                        if (uod.getVigente() && uo.getCodigo().equals(uod.getUnidadOrganizacional().getCodigo())) {
                            unidadSeleccionadaGestionada = true;
                            break;
                        }
                    }
                    if (unidadSeleccionadaGestionada) {
                        break;
                    }
                }
            }
        }
        //--------------------------------------------------------------------------------
        if (!unidadSeleccionadaGestionada) {
            Map<String, String> mapaFunciones = llenarMapaFunciones(
                    uo.getId(), desconcentradoHelper.getMapUnidOrgFunciones().containsKey(uo.getId()));

            if (uo.getSeleccionado()) {
                mapaFunciones.put(desconcentradoHelper.getCodigoFuncionSeleccionada(), null);
            } else if (mapaFunciones.size() == 1 && unidadOrgYaEstaConfigurada(uo.getId())) {
                if (!servidorTieneTareasAsignadas(
                        desconcentradoHelper.getServidorResponsableTempId(), uo.getCodigo())) {
                    boolean pendientes = false;
                    for (DesconcentradoApoyo da
                            : desconcentradoHelper.getDesconcentradoVO().getListaDesconcentradoApoyos()) {
                        Servidor s = da.getServidorApoyo();
                        if (servidorTieneTareasAsignadas(s.getId(), uo.getCodigo())) {
                            pendientes = true;
                            mostrarMensajeEnPantalla("El servidor " + s.getApellidosNombres()
                                    + " tiene trámites pendientes por atender en la Unidad Organizacional "
                                    + uo.getNombre() + ", por lo cual no se permite eliminar de la configuración.",
                                    FacesMessage.SEVERITY_ERROR);
                        }
                    }

                    if (!pendientes) {
                        mapaFunciones.remove(desconcentradoHelper.getCodigoFuncionSeleccionada());
                        desconcentradoHelper.getMapUnidOrgFunciones().remove(uo.getId());
                    }

                } else {
                    Servidor s = desconcentradoHelper
                            .getDesconcentradoVO().getDesconcentrado().getServidorResponsable();
                    mostrarMensajeEnPantalla("El servidor " + s.getApellidosNombres()
                            + " tiene trámites pendientes por atender en la Unidad Organizacional "
                            + uo.getNombre() + ", por lo cual no se permite eliminar de la configuración.",
                            FacesMessage.SEVERITY_ERROR);
                }

            } else {
                mapaFunciones.remove(desconcentradoHelper.getCodigoFuncionSeleccionada());
            }

        } else {
            mostrarMensajeEnPantalla(
                    "Esta Unidad Organizacional ya está siendo gestionada por otra Unidad Desconcentrada",
                    FacesMessage.SEVERITY_ERROR);
        }
        marcarUnidadesSeleccionadasPorFuncion(desconcentradoHelper.getRoot());
    }

    /**
     * verifica si una unidad ya está configurada con al menos una función en base de datos
     *
     * @param unidadOrgId id de la unidad organizacional
     * @return
     */
    private Boolean unidadOrgYaEstaConfigurada(Long unidadOrgId) {
        for (DesconcentradoUnidadOrganizacional duo
                : desconcentradoHelper.getListaDesconcentradosUnidadesOrganizacionales()) {
            if (duo.getUnidadOrganizacional().getId().equals(unidadOrgId)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * recupera el listado de unidades organizacionales y construye el árbol
     *
     * @return
     */
    private String llenarArbolUnidadesOrganizacionales() {
        try {
            desconcentradoHelper.getListaUnidadOrganizacional().clear();
            desconcentradoHelper.getListaUnidadOrganizacional().addAll(admServicio.listarTodosUnidadOrganizacional());

            TreeNode nodoPrincipal = new DefaultTreeNode(
                    desconcentradoHelper.getListaUnidadOrganizacional().get(0), null);
            desconcentradoHelper.setRoot(nodoPrincipal);

            for (UnidadOrganizacional un : desconcentradoHelper.getListaUnidadOrganizacional()) {
                if (un.getVigente()) {
                    TreeNode nodoPadre = new DefaultTreeNode(un, nodoPrincipal);
                    if (un.getId() != null) {
                        obtenerHijos(un, nodoPadre);
                    }
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
        return null;
    }

    /**
     * obtiene los hijos de un nodo en el arbol
     *
     * @param registroPadre
     * @param nodoPadre
     */
    private void obtenerHijos(UnidadOrganizacional registroPadre, TreeNode nodoPadre) {
        try {
            for (UnidadOrganizacional unidad : registroPadre.getListaUnidadesOrganizacionales()) {
                if (unidad.getVigente()) {
                    TreeNode nodoHijo = new DefaultTreeNode(unidad, nodoPadre);
                    if (!unidad.getListaUnidadesOrganizacionales().isEmpty()) {
                        obtenerHijos(unidad, nodoHijo);
                    }
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
    }

    /**
     * concatena el nombre del servidor con su numero de identificación para mostrar en pantalla
     *
     * @param s servidor
     * @return
     */
    public String nombreIdentificacionString(Servidor s) {
        StringBuilder nombreIdentificacion = new StringBuilder("");
        if (s != null) {
            nombreIdentificacion.append(s.getApellidosNombres());
            if (s.getId() != 0L) {
                nombreIdentificacion.append(" (").append(s.getNumeroIdentificacion()).append(")");
            }
        }
        return nombreIdentificacion.toString();
    }

    /**
     * regresa a la lista de undiades desconcentradas
     *
     * @return
     */
    public String regresar() {
        return LISTA_ENTIDAD;
    }

    /**
     *
     * @return
     */
    public DesconcentradoHelper getDesconcentradoHelper() {
        return desconcentradoHelper;
    }

    /**
     *
     * @param desconcentradoHelper
     */
    public void setDesconcentradoHelper(DesconcentradoHelper desconcentradoHelper) {
        this.desconcentradoHelper = desconcentradoHelper;
    }
}
