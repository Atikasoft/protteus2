/*
 *  ProcesoServicio.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Oct 21, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.AsignacionDao;
import ec.com.atikasoft.proteus.dao.DetalleDao;
import ec.com.atikasoft.proteus.dao.FaseDao;
import ec.com.atikasoft.proteus.dao.FormularioDao;
import ec.com.atikasoft.proteus.dao.InstanciaDao;
import ec.com.atikasoft.proteus.dao.InstanciaVariableDao;
import ec.com.atikasoft.proteus.dao.ProcesoDao;
import ec.com.atikasoft.proteus.dao.TareasDao;
import ec.com.atikasoft.proteus.dao.TransicionDao;
import ec.com.atikasoft.proteus.dto.AvanzaInstanciaDTO;
import ec.com.atikasoft.proteus.dto.IniciaInstanciaDTO;
import ec.com.atikasoft.proteus.enums.TipoAtencionEnum;
import ec.com.atikasoft.proteus.enums.TipoVariableEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Asignacion;
import ec.com.atikasoft.proteus.modelo.Detalle;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Fase;
import ec.com.atikasoft.proteus.modelo.Formulario;
import ec.com.atikasoft.proteus.modelo.Instancia;
import ec.com.atikasoft.proteus.modelo.InstanciaVariable;
import ec.com.atikasoft.proteus.modelo.Proceso;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.Tarea;
import ec.com.atikasoft.proteus.modelo.Transicion;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilMatematicas;
import ec.com.atikasoft.proteus.vo.ServidorVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.persistence.LockModeType;

/**
 * Servicio que permite gestionar el proceso.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class GestionServicio {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(GestionServicio.class.getCanonicalName());

    /**
     * Dao de instancia.
     */
    @EJB
    private InstanciaDao instanciaDao;

    /**
     * Dao de detalle.
     */
    @EJB
    private DetalleDao detalleDao;

    /**
     * Dao de proceso.
     */
    @EJB
    private ProcesoDao procesoDao;

    /**
     * Dao de tareas.
     */
    @EJB
    private TareasDao tareaDao;

    /**
     * Dao de transicion.
     */
    @EJB
    private TransicionDao transicionDao;

    /**
     * Dao de variblaes de instancia.
     */
    @EJB
    private InstanciaVariableDao instanciaVariableDao;

    /**
     * Dao de formulario.
     */
    @EJB
    private FormularioDao formularioDao;

    /**
     * Dao de fases.
     */
    @EJB
    private FaseDao faseDao;

    /**
     * Dao de asignaciones.
     */
    @EJB
    private AsignacionDao asignacionDao;

    /**
     * Servicio de mensajeria.
     */
    @EJB
    private MensajeriaServicio mensajeriaServicio;

    /**
     *
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     *
     */
    @EJB
    private SeguridadServicio seguridadServicio;

    /**
     * Constructor sin argumentos.
     */
    public GestionServicio() {
        super();
    }

    /**
     * Inicia una instancia nueva del proceso.
     *
     * @param dto
     * @return
     * @throws ServicioException
     *
     */
    public Instancia iniciarInstancia(final IniciaInstanciaDTO dto) throws ServicioException {
        try {
            if (dto.getCodigoInstitucion() == null) {
                throw new ServicioException("No se recibio el codigo de la institucion");
            }
            if (dto.getEjercicioFiscal() == null) {
                throw new ServicioException("No se recibio el ejercicio fiscal");
            }
            if (dto.getIdentificadorExterno() == null) {
                throw new ServicioException("No se recibio el identificador externo");
            }
            if (dto.getComentario() == null) {
                throw new ServicioException("No se recibio comentario");
            }
            if (dto.getUsuario() == null) {
                throw new ServicioException("No se recibio el usuario");
            }

            Proceso proceso = procesoDao.buscarPorCodigo(dto.getCodigoProceso());
            if (proceso == null) {
                throw new ServicioException("No existe el proceso.");
            }
            // obtener el estado inicial del proceso.
            Transicion transicion = transicionDao.buscarTransicionInicialDelProceso(dto.getCodigoProceso());
            if (transicion == null) {
                throw new ServicioException("No existe definido el estado inicial del proceso.");
            }
            // grabar la nueva instancia.
            Instancia instancia = new Instancia();
            instancia.setFase(transicion.getFaseFinal());
            instancia.setFechaCreacion(new Date());
            instancia.setProceso(proceso);
            instancia.setIdentificadorExterno(dto.getIdentificadorExterno());
            instancia.setUsuarioCreacion(dto.getUsuario());
            instancia.setVigente(Boolean.TRUE);
            instancia.setFinalizado(Boolean.FALSE);
            instancia.setNumeroExterno(dto.getNumeroExterno());
            instancia.setDescripcion(dto.getComentario().toUpperCase());
            instancia.setOrigen(dto.getOrigen());
            instancia.setCodigoInstitucion(dto.getCodigoInstitucion());
            instancia.setNombreInstitucion(dto.getNombreInstitucion());
            instancia.setInstitucionCoreId(dto.getInstitucionId());
            instancia.setUsuarioAsignadoCedula(null);
            instancia.setUsuarioAsignadoNombre(null);
            instancia = instanciaDao.crear(instancia);
            instanciaDao.flush();
//            Detalle detalle = crearDetalle(instancia, transicion, dto.getComentario(), dto.getUsuario(), dto.
//                    getNombreUsuario(), dto.getCodigoInstitucion(), dto.getNombreInstitucion(), dto.getInstitucionId());
            // determinar si se crear una tareas.
//            if (transicion.isCrearTarea()) {
//                Tarea tarea = new Tarea();
//                tarea.setCodigoInstitucionElaborador(instancia.getCodigoInstitucion());
//                tarea.setNombreInstitucionElaborador(instancia.getNombreInstitucion());
//                tarea.setCodigoInstitucion(dto.getCodigoInstitucion());
//                tarea.setNombreInstitucion(dto.getNombreInstitucion());
//                tarea.setInstitucionCoreId(dto.getInstitucionId());
//                tarea.setComentario(dto.getComentario().toUpperCase());
//                tarea.setDescripcion(dto.getComentario().toUpperCase());
//                tarea.setDetalle(detalle);
//                tarea.setEjercicioFiscal(dto.getEjercicioFiscal());
//                tarea.setIdentificadorExterno(dto.getIdentificadorExterno());
//                tarea.setIdentificadorFormulario(transicion.getFormulario().getId());
//                tarea.setNombreEstadoActual(transicion.getFaseFinal().getNombre());
//                tarea.setNumeroExterno(dto.getNumeroExterno());
//                crearTarea(instancia, proceso, transicion, tarea, dto.getUsuario(), dto.getInstitucionId(), null);
//            } else {
//                instancia.setUsuarioAsignadoCedula(null);
//                instancia.setUsuarioAsignadoNombre(null);
//            }
            return instancia;
        } catch (ServicioException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServicioException(e);
        }
    }

    /**
     * Actualiza la descripcion de una instancia dado su identificador eterno.
     *
     * @param identificadorExterno
     * @param descripcion
     * @throws ServicioException
     */
    public void actualizarDescripcionInstanticia(final Long identificadorExterno, final String descripcion) throws
            ServicioException {
        try {
            Instancia instancia = instanciaDao.buscarPorIdentificadorExterno(identificadorExterno);
            if (instancia != null) {
                instancia.setDescripcion(descripcion);
                instanciaDao.actualizar(instancia);
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Realiza el avance de la instancia de un proceso.
     *
     * @param dto
     * @return
     * @throws ServicioException Error de ejecucion.
     */
    public Instancia avanzarInstancia(final AvanzaInstanciaDTO dto) throws ServicioException {
        LOG.fine(UtilCadena.concatenarLog("avanzarInstancia", "instanciaId=", dto.getInstanciaId()));
        try {
            Transicion transicion = transicionDao.buscarPorFaseInicialFinal(dto.getCodigoFaseInicial(), dto.
                    getCodigoFaseFinal(),
                    dto.getCodigoProceso());
            if (transicion == null) {
                throw new ServicioException(UtilCadena.concatenar("No existe transición para la fase inicial:",
                        dto.getCodigoFaseInicial(), " y fase fase final:", dto.getCodigoFaseFinal()));
            } else {
                return avanzarInstancia(dto, transicion.getId());
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Realiza el avance de la instancia de un proceso.
     *
     * @param dto
     * @param transicionId Identificador de la transicion.
     * @return
     * @throws ServicioException Error de ejecucion.
     */
    public synchronized Instancia avanzarInstancia(final AvanzaInstanciaDTO dto, final Long transicionId) throws
            ServicioException {
        try {
            Proceso proceso = procesoDao.buscarPorCodigo(dto.getCodigoProceso());
            // bloquear instancia.
            Instancia instancia = instanciaDao.buscarPorId(dto.getInstanciaId());
            instanciaDao.getEntityManager().lock(instancia, LockModeType.PESSIMISTIC_WRITE);
            Transicion transicion = transicionDao.buscarPorId(transicionId);
            // validar que la instancia se encuentre en el mismo estado inicial al original.
//            if (instancia.getFase().getCodigo().equals(transicion.getFaseInicial().getCodigo())) {
            // determinar el tipo de atencion.
            if (instancia.getFase().getTipoAtencion().equals(TipoAtencionEnum.AUTOMATICO.getCodigo())) {
                // analizar estado a seguir.
                // Pendiente avance de estado automatico.
                instancia.setFechaCreacion(new Date());
                instancia.setUsuarioActualizacion(dto.getUsuario());
                instancia.setFase(transicion.getFaseFinal());
            } else if (instancia.getFase().getTipoAtencion().equals(TipoAtencionEnum.MANUAL.getCodigo())) {
                instancia.setFechaCreacion(new Date());
                instancia.setUsuarioActualizacion(dto.getUsuario());
                instancia.setFase(transicion.getFaseFinal());
                List<Transicion> siguientes = transicionDao.listarEstadosSiguientesDelProceso(instancia.getFase().
                        getCodigo(), dto.getCodigoProceso());
                if (siguientes.isEmpty()) {
                    instancia.setFinalizado(Boolean.TRUE);
                }
            } else {
                throw new ServicioException(UtilCadena.concatenar("Tipo de atencion no existe :",
                        instancia.getFase().getTipoAtencion()));
            }
            Detalle detalle = crearDetalle(instancia, transicion, dto.getComentario(), dto.getUsuario(), dto.
                    getUsuarioNombre(), dto.getCodigoInstitucion(), dto.getNombreInstitucion(),
                    dto.getInstitucionId());
            // determinar si se crear una tareas.
            if (transicion.isCrearTarea()) {
                Tarea tarea = new Tarea();
                tarea.setCodigoInstitucionElaborador(instancia.getCodigoInstitucion());
                tarea.setNombreInstitucionElaborador(instancia.getNombreInstitucion());

                tarea.setCodigoInstitucion(dto.getCodigoInstitucion());
                tarea.setNombreInstitucion(dto.getNombreInstitucion());
                tarea.setInstitucionCoreId(dto.getInstitucionId());
                tarea.setComentario(instancia.getDescripcion());
                tarea.setDescripcion(instancia.getDescripcion());
                tarea.setDetalle(detalle);
                tarea.setEjercicioFiscal(dto.getEjercicioFiscal());
                tarea.setIdentificadorExterno(instancia.getIdentificadorExterno());
                tarea.setNumeroExterno(instancia.getNumeroExterno());
                tarea.setIdentificadorFormulario(transicion.getFormulario().getId());
                tarea.setNombreEstadoActual(transicion.getFaseFinal().getNombre());
                crearTarea(instancia, proceso, transicion, tarea, dto.getUsuario(), dto.getInstitucionId(),
                        dto.getUsuarioAsignar());
            } else {
                instancia.setUsuarioAsignadoCedula(null);
                instancia.setUsuarioAsignadoNombre(null);
            }
            // finalizar la tarea anterior
            if (dto.getTareaId() != null) {
                Tarea tarea = tareaDao.buscarPorId(dto.getTareaId());
                tarea.setAsignado(Boolean.FALSE);
                tarea.setFechaAtencion(new Date());
                tarea.setFechaActualizacion(new Date());
                tarea.setNombreEstadoSiguiente(instancia.getFase().getNombre());
                tarea.setUsuarioActualizacion(dto.getUsuario());
                tarea.setComentario(dto.getComentario());
                tareaDao.actualizar(tarea);
            }
            instanciaDao.actualizar(instancia);
            return instancia;
//            } else {
//                throw new ServicioException(
//                        "El estado inicial de la transición es diferente al estado de la instancia del trámite.");
//            }
        } catch (ServicioException e) {
            throw e;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Crear el detalle de una instancia.
     *
     * @param transicion Datos de la transicion.
     * @param instancia Datos de la instancia.
     * @param comentario Comentario.
     * @param usuario Usuario conectado.
     * @return Datos del detalle.
     */
    private Detalle crearDetalle(final Instancia instancia, final Transicion transicion, final String comentario,
            final String usuario, final String usuarioNombre, final String codigoInstitucion,
            final String nombreInstitucion, final Long institucionCoreId) throws DaoException {
        // grabar el detalle inicial de la instancia.
        Detalle detalle = new Detalle();
        detalle.setComentario(comentario.toUpperCase());
        detalle.setEstado(transicion.getFaseInicial());
        detalle.setFechaCreacion(new Date());
        detalle.setInstancia(instancia);
        detalle.setUsuario(usuario);
        detalle.setUsuarioCreacion(usuario);
        detalle.setVigente(Boolean.TRUE);
        detalle.setNombreUsuario(usuarioNombre);
        detalle.setCodigoInstitucion(codigoInstitucion);
        detalle.setNombreInstitucion(nombreInstitucion);
        detalle.setInstitucionCoreId(institucionCoreId);
        detalleDao.crear(detalle);
        detalleDao.flush();
        return detalle;
    }

    /**
     * Realiza la creacion de una nueva tarea.
     *
     * @param proceso Datos del Proceso
     * @param tarea Datos de la tarea.
     * @param usuario Usuario conectado.
     * @return Tarea nueva.
     * @throws ServicioException Error de ejecucion.
     */
    private Tarea crearTarea(final Instancia instancia, final Proceso proceso, final Transicion transicion,
            final Tarea tarea, final String usuario, final Long institucionId, final String usuarioAsignador) throws
            DaoException, ServicioException {
        procesoDao.lock(proceso);
        proceso.setContadorTarea(proceso.getContadorTarea() + 1);
        procesoDao.actualizar(proceso);
        tarea.setAsignado(Boolean.TRUE);
        tarea.setFechaAsignacion(new Date());
        tarea.setFechaCreacion(new Date());
        tarea.setUsuarioCreacion(usuario);
        tarea.setVigente(Boolean.TRUE);
        tarea.setOrigen(instancia.getOrigen());

        final ServidorVO servidorVO = asignarUsuario(instancia, transicion, tarea.getCodigoInstitucion(), institucionId,
                usuarioAsignador);
        final Servidor servidor = servidorVO.getServidor();
        String nemonicoRol = servidorVO.getNemonicoRol();

        List<DistributivoDetalle> puestos = administracionServicio.listarTodosDistributivoDetallePorServidor(servidor.getId());
        if (puestos.isEmpty()) {
            throw new ServicioException(UtilCadena.concatenar("Servidor ", servidor.getApellidosNombres(),
                    " no se encuentra ocupando un puesto del distributivo. "));
        } else {
            if (puestos.size() == 1) {
                tarea.setUsuarioAsignado(servidor.getNumeroIdentificacion());
                tarea.setNombreUsuarioAsignado(servidor.getApellidosNombres());
                tarea.setNemonicoRol(nemonicoRol);
                tarea.setNumero(UtilMatematicas.rellenarConCerosIzq(proceso.getContadorTarea(), 10));
                tarea.setCodigoInstitucion(puestos.get(0).getDistributivo().getUnidadOrganizacional().getCodigo());
                tarea.setNombreInstitucion(puestos.get(0).getDistributivo().getUnidadOrganizacional().getNombreCompleto());
                instancia.setUsuarioAsignadoCedula(tarea.getUsuarioAsignado());
                instancia.setUsuarioAsignadoNombre(tarea.getNombreUsuarioAsignado());
                if (transicion.getTituloFormulario() == null || transicion.getTituloFormulario().trim().isEmpty()) {
                    tarea.setTituloFormulario(transicion.getFormulario().getTitulo());
                } else {
                    tarea.setTituloFormulario(transicion.getTituloFormulario());
                }
                Runnable runnable = new Runnable() {

                    @Override
                    public void run() {
//                        enviarMail(usuarioRol, tarea, usuarioNombre);
                    }
                };
                runnable.run();
                return tareaDao.crear(tarea);
            } else {
                throw new ServicioException(UtilCadena.concatenar("Servidor ", servidor.getApellidosNombres(),
                        " se encuentra ocupando mas de un puesto del distributivo. "));
            }
        }
    }

    /**
     * Envia un mail sobre la tarea asignada.
     *
     * @param usuarioRol Datos del usuario.
     */
//    private void enviarMail(final UsuarioRol usuarioRol, final Tarea tarea, final String usuarioNombre) {
//        try {
//            if (usuarioRol.getUsuario().getCorreoElectronico() != null
//                    && !usuarioRol.getUsuario().getCorreoElectronico().trim().isEmpty()) {
//
//                StringBuilder asunto = new StringBuilder();
//                asunto.append("Proteus - ASIGNACIÓN DE TAREA :").append(tarea.getNumero());
//                StringBuilder mensaje = new StringBuilder();
//                mensaje.append("Estimado(a):\n\n");
//                mensaje.append(usuarioRol.getUsuario().getNombre()).append("\n");
//                mensaje.append("Cédula:").append(usuarioRol.getUsuario().getCedula()).append("\n\n");
//
//                mensaje.append("Ha recibido un trámite en el Sistema de Movimientos de Personal - ");
//                mensaje.append("SIITH, con el siguiente detalle:\n");
//                mensaje.append("No de Trámite: ").append(tarea.getNumeroExterno()).append("\n");
//                mensaje.append("Estado de Trámite: ").append(tarea.getNombreEstadoActual()).append("\n");
//                mensaje.append("Fecha de Asignación: ").append(new SimpleDateFormat(UtilFechas.FORMATO_FECHA_LARGO).
//                        format(tarea.getFechaAsignacion())).append("\n");
//                mensaje.append("Asignado por: ").append(usuarioNombre).append("\n");
//                mensaje.append("Descripción: ").append(tarea.getDescripcion()).append("\n\n");
//
//                mensaje.append("Por favor revise su Bandeja de Tareas en http://Proteus.mrl.gob.ec\n\n");
//
//                mensaje.append("Saludos cordiales,\n");
//                mensaje.append("Proteus - SIITH.\n\n");
//
//                mensaje.append(
//                        "Nota: Este mensaje fue enviado automáticamente por el sistema, por favor no lo responda.");
//                mensajeriaServicio.enviarMail(asunto.toString(), null, new String[]{usuarioRol.getUsuario().
//                    getCorreoElectronico()}, null, mensaje.toString(), null, null);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOG.severe(e.getMessage());
//        }
//    }
    /**
     * Se encarga de asignar un usuario para la tarea.
     *
     * @param transicion Datos de la transicion.
     * @return Usuario.
     * @throws DaoException Error de ejecucion.
     */
    private ServidorVO asignarUsuario(final Instancia instancia, final Transicion transicion,
            final String codigoInstitucion, final Long institucionId, final String usuarioAsignador) throws DaoException,
            ServicioException {
        ServidorVO usuarioSeleccionado = null;
        if (transicion.getDevolucion()) {
            // verificar el usuario que atendio anterior el estado final de la transicion.
            List<Detalle> detalles = detalleDao.buscarPorIdentificadorExternoYEstado(instancia.getIdentificadorExterno(),
                    transicion.getFaseFinal().getId());
            if (detalles.size() > 0) {
                Detalle detalle = detalles.get(0);
                Asignacion asignacion = transicion.getListaAsignaciones().get(0);
                Servidor servidor = seguridadServicio.buscarUsuarioPorRol(
                        detalle.getUsuario(), asignacion.getNemonicoRol(), codigoInstitucion);
                if (servidor == null) {
                    usuarioSeleccionado = asignarUsuarioPorBalanceo(transicion, institucionId, codigoInstitucion);
                } else {
                    usuarioSeleccionado = new ServidorVO(servidor, asignacion.getNemonicoRol());
                }
            } else {
                usuarioSeleccionado = asignarUsuarioPorBalanceo(transicion, institucionId, codigoInstitucion);
            }
        } else {
            if (transicion.getTipoAtencion().equals(TipoAtencionEnum.AUTOMATICO.getCodigo())) {
                //usuarioSeleccionado = asignarUsuarioPorBalanceo(transicion, institucionId, codigoInstitucion);
                usuarioSeleccionado = asignarUsuarioDirectamente(usuarioAsignador, transicion, codigoInstitucion);
            } else if (transicion.getTipoAtencion().equals(TipoAtencionEnum.MANUAL.getCodigo())) {
                usuarioSeleccionado = asignarUsuarioDirectamente(usuarioAsignador, transicion, codigoInstitucion);
            } else {
                throw new ServicioException(
                        "Tipo de asignación de usuario no es reconocido :" + transicion.getTipoAtencion());
            }
        }
        return usuarioSeleccionado;
    }

    /**
     * Busca los detalles de tarea para un estado y un identificador externo de instancia
     *
     * @param idExterno
     * @param idFase
     * @return
     * @throws ServicioException
     */
    public List<Detalle> buscarDetalles(final Long idExterno, final Long idFase) throws ServicioException {
        try {
            List<Detalle> detalles = detalleDao.buscarPorIdentificadorExternoYEstado(idExterno, idFase);
            return (detalles != null) ? detalles : new ArrayList<Detalle>();
        } catch (DaoException ex) {
            Logger.getLogger(GestionServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param transicion
     * @param institucionId
     * @param codigoInstitucion
     * @param ejercicioFiscal
     * @return
     * @throws Exception
     * @throws DaoException
     */
    private ServidorVO asignarUsuarioPorBalanceo(final Transicion transicion, final Long institucionId,
            final String codigoInstitucion) throws DaoException, ServicioException {
        List<Asignacion> asignaciones = asignacionDao.buscarPorTransicion(transicion.getId());
        List<ServidorVO> servidores = determinarUsuarios(asignaciones, codigoInstitucion);
        return balancearUsuarios(servidores, codigoInstitucion);
    }

    /**
     * Determina los usuarios que corresponde a los roles recibidas. Cada nodo de la lista est'a encapsulado en un VO
     * que agrupa, el Servidor y el rol de la asginacion por el que se cargo
     *
     * @param asignaciones
     * @param codigoInstitucion
     * @return
     * @throws ServicioException_Exception
     * @throws ServicioException
     */
    private List<ServidorVO> determinarUsuarios(final List<Asignacion> asignaciones, final String codigoInstitucion)
            throws ServicioException {
        StringBuilder roles = new StringBuilder();
        List<ServidorVO> servidores = new ArrayList<ServidorVO>();
        for (Asignacion asignacion : asignaciones) {
            roles.append(asignacion.getNemonicoRol()).append(",");
            String nemonicoRol = asignacion.getNemonicoRol();
            List<Servidor> lista = seguridadServicio.buscarUsuariosPorRol(nemonicoRol, codigoInstitucion);

            if (lista != null) {
                for (Servidor s : lista) {
                    servidores.add(new ServidorVO(s, nemonicoRol));
                }
                // servidores.addAll(res.getServidores());
            }

        }
        if (servidores.isEmpty()) {
            throw new ServicioException(UtilCadena.concatenar("No existen usuarios para los roles (", roles.toString().
                    trim().isEmpty() ? "SIN ROLES" : roles.toString(), ") en la unidad organizacional ",
                    codigoInstitucion));
        }
        return servidores;

    }

    /**
     * Recupera un Servidor segun un rol, el resultado es un ServidorVO que encapsula tanto al Servidor como al nemonico
     * del Rol
     *
     * @param usuarioAsignador
     * @param transicion
     * @param codigoInstitucion
     * @return
     * @throws ServicioException
     * @throws ServicioException_Exception
     */
    private ServidorVO asignarUsuarioDirectamente(final String usuarioAsignador, final Transicion transicion,
            final String codigoInstitucion) throws ServicioException {
        ServidorVO vo = null;
        String nemonicoRol = null;
        if (usuarioAsignador == null) {
            throw new ServicioException(
                    "El tipo de asignación de usuario es manual, pero no se ha proveido la identificación del usuario.");
        } else {
            for (Asignacion a : transicion.getListaAsignaciones()) {
                nemonicoRol = a.getNemonicoRol();
                Servidor servidor = seguridadServicio.buscarUsuarioPorRol(usuarioAsignador, nemonicoRol, codigoInstitucion);
                if (servidor != null) {
                    vo = new ServidorVO(servidor, nemonicoRol);
                    break;
                }

            }
            if (vo == null) {
                throw new ServicioException(
                        "El usuario que desea asignar no se encuentra registrado en la institución con los roles "
                        + " respectivos:" + usuarioAsignador);
            }
        }
        return vo;
    }

    /**
     * Selecciona un usuario para atender la tarea.
     *
     * @param servidores
     * @param codigoInstitucion
     * @return
     * @throws DaoException
     */
    private ServidorVO balancearUsuarios(final List<ServidorVO> servidores, final String codigoInstitucion) throws
            DaoException {
        ServidorVO usuarioAsignado = null;
        long minimo = 9999999;
        for (ServidorVO vos : servidores) {
            Servidor servidor = vos.getServidor();
            long contador = tareaDao.contar(servidor.getNumeroIdentificacion(), codigoInstitucion, true);
            if (contador < minimo) {
                usuarioAsignado = vos;
                minimo = contador;
            }
        }
        return usuarioAsignado;
    }

    /**
     *
     * @param instancia
     * @param usuario
     */
    private void crearVariablesBasicas(final Instancia instancia, final String usuario) throws DaoException {
        crearVariableProcesoId(instancia, usuario);
    }

    /**
     *
     * @param instancia
     * @param usuario
     */
    private void crearVariableProcesoId(final Instancia instancia, final String usuario) throws DaoException {
        InstanciaVariable iv = new InstanciaVariable();
        iv.setInstancia(instancia);
        iv.setFechaCreacion(new Date());
        iv.setUsuarioCreacion(usuario);
        iv.setCodigo("PROCESO_ID");
        iv.setNombre("IDENTIFICADOR DEL PROCESO");
        iv.setTipo(TipoVariableEnum.NUMERICO.getCodigo());
        iv.setValorNumerico(new BigDecimal(instancia.getId()));
        iv.setVigente(Boolean.TRUE);
        instanciaVariableDao.crear(iv);
    }

    /**
     * Recupera las transiciones siguientes de un estado individual.
     *
     * @param codigoFaseActual Codigo de la factual actual.
     * @param codigoProceso Codigo del proceso.
     * @return Lista de transiciones.
     * @throws DaoException Error de ejecucion.
     */
    public List<Transicion> buscarTransicionesSiguientes(final String codigoFaseActual, final String codigoProceso)
            throws DaoException {
        try {
            return transicionDao.listarEstadosSiguientesDelProceso(codigoFaseActual, codigoProceso);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recuera un formualario.
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public Formulario buscarFormulario(final Long id) throws ServicioException {
        try {
            return formularioDao.buscarPorId(id);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    public List<Detalle> listarDetalles(final Long identificadorExterno) throws ServicioException {
        try {
            return detalleDao.buscarPorIdentificadorExterno(identificadorExterno);
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     *
     * @param nemonicoProceso
     * @return
     * @throws ServicioException
     */
    public List<Fase> buscarFases() throws ServicioException {
        try {
            return faseDao.buscarTodosOrdenados();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Busca una transicion dado su indentificador.
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public Transicion buscarTransicion(final Long id) throws ServicioException {
        try {
            return transicionDao.buscarPorId(id);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera las transiciones siguientes de un estado individual.
     *
     * @param codigoFaseActual Codigo de la factual actual.
     * @param codigoProceso Codigo del proceso.
     * @return Lista de transiciones.
     * @throws DaoException Error de ejecucion.
     */
    public Transicion buscarTransicion(final String codigoFaseActual, final String codigoFaseSiguiente,
            final String codigoProceso) throws DaoException {
        try {
            return transicionDao.buscarPorFaseInicialFinal(codigoFaseActual, codigoFaseSiguiente, codigoProceso);
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }
}
