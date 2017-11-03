/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.consumer.mdmq.ConsumerMDMQ;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.RolServidorDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.excepciones.ServidorSinDistributivoException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.Rol;
import ec.com.atikasoft.proteus.modelo.RolServidor;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.util.UtilMail;
import ec.com.atikasoft.proteus.util.UtilNumeros;
import ec.com.atikasoft.proteus.vo.AccesoServidorVO;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import ec.com.atikasoft.proteus.vo.CargaMasivaServidorVO;
import ec.com.atikasoft.proteus.vo.PersonaVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author atikasoft
 */
@Stateless
@LocalBean
public class ServidorServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(ServidorServicio.class.getCanonicalName());
    /**
     * Fabrica de colas.
     */
    private static final String CONNECTION_FACTORY = "jms/proteusConnectionFactory";

    /**
     * Cola de ejecucion de nomina.
     */
    private static final String CONNECTION_QUEUE = "jms/proteusGenMasivaClavesQueue";
    /**
     * DAO de Distributivo
     */
    @EJB
    private ServidorDao servidorDao;
    /**
     * DAO de RolServidor.
     */
    @EJB
    private RolServidorDao rolServidorDao;

    /**
     * Dao de parametros globales.
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;
    /**
     * Servicio de mensajeria.
     */
    @EJB
    private MensajeriaServicio mensajeriaServicio;
    /**
     * DAO de DistributivoDetalle
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     * Dao de servidor por institucion.
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;

    /**
     * Dao de servidor por institucion.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Dao de servidor por institucion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * servicio de historicos
     */
    @EJB
    private ServidorHistoricosJornadaServicio servidorHistoricosJornadaServicio;

    /**
     *
     */
    @EJB
    private DistributivoDetalleServicio distributivoDetalleServicio;

    /**
     * REcupera servidor basado en un conjunto de parametros.
     *
     * @param vo
     * @return
     * @throws ServicioException
     */
    public List<DistributivoDetalle> buscar(final BusquedaServidorVO vo) throws ServicioException {
        try {
            return distributivoDetalleDao.buscarServidor(vo);
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * REcupera servidor basado en un conjunto de parametros.
     *
     * @param vo
     * @param firstRow
     * @param numberOfRows
     * @param orderField
     * @param orderDirection
     * @param filters
     * @return
     * @throws ServicioException
     */
    public List<Servidor> buscarServidores(final BusquedaServidorVO vo, int firstRow,
            int numberOfRows, String orderField, String orderDirection,
            Map<String, String> filters) throws ServicioException {
        try {
            int contador = 0;
            List<Servidor> servidores = distributivoDetalleDao.buscarServidoresP(
                    vo, firstRow, numberOfRows, orderField, orderDirection, filters);
            for (Servidor p : servidores) {
                List<DistributivoDetalle> dist = administracionServicio.listarTodosDistributivoDetallePorServidor(p.
                        getId());
                for (DistributivoDetalle dd : dist) {
                    if (!dist.isEmpty()) {
                        p.setDistributivoDetalle(dd);
                    }
                    break;
                }
            }
            return servidores;
        } catch (DaoException e) {
            throw new ServicioException(e);
        } catch (ServicioException e) {
            throw new ServicioException(e);
        }

    }

    public int contarServidores(final BusquedaServidorVO vo,
            Map<String, String> filters) throws ServicioException {
        int total = distributivoDetalleDao.contarServidores(vo, filters);
        return total;
    }

    /**
     * REcupera servidor basado basado en su nombres.
     *
     * @param nombre
     * @param institucionEjercicioFiscalId
     * @return
     * @throws ServicioException
     */
    public List<DistributivoDetalle> buscar(final String nombre, final Long institucionEjercicioFiscalId) throws
            ServicioException {
        try {
            return distributivoDetalleDao.buscarPorServidorNombres(nombre, institucionEjercicioFiscalId);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Permite buscar una persona.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param usuario
     * @return
     * @throws ServicioException
     */
    public PersonaVO buscarPersona(final String tipoIdentificacion, final String numeroIdentificacion,
            final UsuarioVO usuarioVO) throws ServicioException {
        try {
            ParametroInstitucional pi = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
                    usuarioVO.getInstitucion().getId(), ParametroInstitucionCatalogoEnum.ENDPOINT_PERSONAS.getCodigo());

            ParametroGlobal usuario = parametroGlobalDao.buscarPorNemonico(
                    ParametroGlobalEnum.USUARIO_SISTEMA_PERSONAS.getCodigo());

            ParametroGlobal clave = parametroGlobalDao.buscarPorNemonico(
                    ParametroGlobalEnum.CLAVE_SISTEMA_PERSONAS.getCodigo());
            ConsumerMDMQ ws = new ConsumerMDMQ();

            ec.com.atikasoft.proteus.consumer.mdmq.vo.PersonaVO p = ws.buscarPersona(tipoIdentificacion,
                    numeroIdentificacion, usuario.getValorTexto(), clave.getValorTexto(), pi.getValorTexto());
            PersonaVO vo = null;
            if (p != null) {
                vo = new PersonaVO();
                vo.setTipoIdentificacion(p.getTipoIdentificacion());
                vo.setNumeroIdentificacion(p.getNumeroIdentificacion());
                vo.setNombres(p.getNombres());
                vo.setApellidosNombres(p.getApellidosNombres());
                vo.setEstadoCivil(p.getEstadoCivil());
                vo.setFechaNacimiento(p.getFechaNacimiento());
                vo.setApellidos(p.getApellidos());
                Servidor s = servidorDao.buscar(tipoIdentificacion, numeroIdentificacion);
                vo.setServidor(s);
            }
            return vo;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Guarda la actualizacion de datos de jornada laboral para un servidor y genera un historico de dicha informacion
     *
     * @param servidorConDatosNuevos servidor con la informacion actualizada
     * @param servidorConDatosAnteriores servidor con datos anteriores a la modificacion
     * @param usuarioCreaOModifica
     * @param idInstitucionEjercicioFiscal
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     * @throws ec.com.atikasoft.proteus.excepciones.ServidorSinDistributivoException En caso de que no se encuentre un
     * distributivodetalle para el servidor
     */
    public void actualizarJornadaServidorConGeneracionHistorico(final Servidor servidorConDatosNuevos,
            final Servidor servidorConDatosAnteriores,
            final UsuarioVO usuarioCreaOModifica,
            final Long idInstitucionEjercicioFiscal) throws DaoException,
            ServicioException, ServidorSinDistributivoException {

        servidorDao.actualizar(servidorConDatosNuevos);

        setDistributivoDetalleEnServidores(servidorConDatosAnteriores, servidorConDatosNuevos,
                idInstitucionEjercicioFiscal);
        if (servidorConDatosAnteriores.getMotivoCambioJornada() == null) {
            servidorConDatosAnteriores.setMotivoCambioJornada("");
        }

        if (esNecesarioCrearHistorico(servidorConDatosAnteriores, servidorConDatosNuevos)) {
            servidorHistoricosJornadaServicio.generarHistoricoJornadaLaboral(servidorConDatosAnteriores,
                    usuarioCreaOModifica);
        }
    }

    /**
     * Setea el distributivo detalle valido para los servidores
     *
     * @param servidorConDatosNuevos
     * @param servidorConDatosAnteriores
     */
    private void setDistributivoDetalleEnServidores(final Servidor servidorConDatosNuevos,
            final Servidor servidorConDatosAnteriores, final Long idInstitucionEjercicioFiscal)
            throws ServicioException, ServidorSinDistributivoException {

        DistributivoDetalle distributivoDetalleServidor = distributivoDetalleServicio.buscarPorServidor(
                servidorConDatosAnteriores.getTipoIdentificacion(),
                servidorConDatosAnteriores.getNumeroIdentificacion(),
                idInstitucionEjercicioFiscal);

        if (distributivoDetalleServidor != null) {
            servidorConDatosNuevos.setDistributivoDetalle(distributivoDetalleServidor);
            servidorConDatosAnteriores.setDistributivoDetalle(distributivoDetalleServidor);
        } else {
            throw new ServidorSinDistributivoException("El servidor no tiene un distributivo asignado");
        }
    }

    /**
     * Permite verificar si se han cambiado datos en el servidor de ser asi se procede a crear historicos
     *
     * @param servidorConDatosAnteriores
     * @param servidorConDatosNuevos
     * @return True:Si es necesario crear historico
     */
    private boolean esNecesarioCrearHistorico(final Servidor servidorConDatosAnteriores,
            final Servidor servidorConDatosNuevos) {

        Boolean sonHorasEntradaIguales = Boolean.TRUE;
        Boolean esMotivoJornadaIguales = Boolean.TRUE;
        if (servidorConDatosAnteriores.getHoraEntrada() != null) {
            sonHorasEntradaIguales = servidorConDatosAnteriores.getHoraEntrada().getTime() == servidorConDatosNuevos.
                    getHoraEntrada().getTime();
        }
        if (servidorConDatosAnteriores.getMotivoCambioJornada() != null) {
            esMotivoJornadaIguales = servidorConDatosAnteriores.getMotivoCambioJornada().
                    equals(servidorConDatosNuevos.getMotivoCambioJornada());
        }
        return !servidorConDatosAnteriores.getJornada().equals(servidorConDatosNuevos.getJornada())
                || !(sonHorasEntradaIguales)
                || !(esMotivoJornadaIguales)
                || !servidorConDatosAnteriores.getDistributivoDetalle().getEscalaOcupacional().
                getMarcacionObligatoria() != servidorConDatosNuevos.
                getDistributivoDetalle().getEscalaOcupacional().getMarcacionObligatoria()
                || !servidorConDatosAnteriores.getDistributivoDetalle().getEscalaOcupacional().
                getRecibeHorasExtra() != servidorConDatosNuevos.
                getDistributivoDetalle().getEscalaOcupacional().getRecibeHorasExtra();
    }

    /**
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     * @throws ServicioException
     */
    public Servidor buscarServidor(final String tipoIdentificacion, final String numeroIdentificacion) throws
            ServicioException {
        try {
            return servidorDao.buscar(tipoIdentificacion, numeroIdentificacion);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca servidores filtrando por nombre.
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<Servidor> buscarServidorPorNombre(final String nombre) throws
            ServicioException {
        try {
            return servidorDao.buscarPorNombre(nombre);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca servidores con puestos asignados filtrando por nombre.
     *
     * @param nombre
     * @return
     * @throws ServicioException
     */
    public List<Servidor> buscarServidorPorNombreConPuesto(final String nombre) throws
            ServicioException {
        try {
            return servidorDao.buscarPorNombreConPuesto(nombre);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca servidores con puestos asignados filtrando por nombre y unidades organizacionales
     *
     * @param nombre
     * @param codigosUnidadesOrg
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<Servidor> buscarServidorConPuestoPorNombreYUnidadOrganizacional(
            final String nombre, final String codigosUnidadesOrg) throws ServicioException {
        try {
            String[] arrCodigos = codigosUnidadesOrg.split(",");
            StringBuilder cadenaCod = new StringBuilder("'");
            int i = 0;
            for (String codigo : arrCodigos) {
                if (++i < arrCodigos.length) {
                    cadenaCod.append(codigo).append("','");
                } else {
                    cadenaCod.append(codigo).append("'");
                }
            }
            return servidorDao.buscarServidorConPuestoPorNombreYUnidadOrganizacional(nombre, cadenaCod.toString());
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * envia notificaciones de errores.
     *
     * @param asunto
     * @param nombreProceso
     * @param contenido
     * @throws Exception
     */
    public void enviarNotificacionAdministradorEjecucionFallida(final String asunto, final String nombreProceso, final String contenido) throws Exception {
        ParametroGlobal pNemonicoCorreoAdm
                = parametroGlobalDao.buscarPorNemonico(ParametroGlobalEnum.CORREO_ELECTRONICO_ADMINISTRADOR_SISTEMA.
                        getCodigo());

        if (pNemonicoCorreoAdm != null) {
            String[] to = pNemonicoCorreoAdm.getValorTexto().split(",");
            if (to == null || to.length == 0) {
                to[0] = pNemonicoCorreoAdm.getValorTexto();
            }
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("La ejecución del proceso  de ").append(nombreProceso).append(" no se ejecutó o presentó errores! \n");
            mensaje.append("\n\n");
            mensaje.append(contenido);
            mensajeriaServicio.enviarMail(asunto, null, to, null, mensaje.toString(), null, null);
        } else {
            LOG.log(Level.INFO, "Problemas al enviar mail a administrador del sistema:{0}", contenido);
        }
    }

    /**
     * metodo para enviar correo.
     *
     * @param usuario
     * @param clave
     * @throws java.lang.Exception
     */
    public void enviarCorreo(final Servidor usuario, final String clave) throws Exception {
        try {
            String asunto, mensaje;
            String[] destinatario = usuario.getMail().split(usuario.getMail().replace(";", ","));
            if (destinatario == null || destinatario.length == 0) {
                destinatario = new String[1];
                destinatario[0] = usuario.getMail();
            }
            asunto = UtilCadena.concatenar("MDMQ: CAMBIO DE CLAVE DE INGRESO AL SISTEMA SIGEN");
            mensaje = UtilCadena.concatenar(
                    " Estimado/a:\n\n",
                    usuario.getApellidosNombres(),
                    ". Se ha cambiado la clave de acceso al sistema de Movimientos de Personal y Nómina SIGEN.",
                    "\n Su nueva clave es: ", clave,
                    "\n\n Recuerde que el ingreso al sistema es a través del número de identificación y la clave. El"
                    + " uso y custodia de su clave de acceso, es responsabilidad solo suya. La clave es personal e instranferible.");
            mensajeriaServicio.enviarMail(asunto, null, destinatario, null, mensaje, null, null);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * METODO PARA VALIDAR EL INGRESO A LA APLICACION BUSCANDO EN LA TABLA DE USUARIOS Y RETORNANDO UN VALOR INT.
     *
     * @param username
     * @param clave
     * @return VALOR VALIDADO.
     */
    public AccesoServidorVO validarIngresoServidor(final String username, final String clave) {
        AccesoServidorVO as = new AccesoServidorVO();
        as.setConAcceso(Boolean.FALSE);
        as.setConCambioClave(Boolean.FALSE);
        try {
            String claveEncriptada = DigestUtils.md5Hex(clave);
            List<Servidor> listaUsuario = servidorDao.buscarServidorPorUsernameClave(username, claveEncriptada);
            if (!listaUsuario.isEmpty()) {
                as.setServidor(listaUsuario.get(0));
                as.setConAcceso(Boolean.TRUE);
                if (as.getServidor().getFechaCaducidad() != null
                        && UtilFechas.truncarFecha(as.getServidor().getFechaCaducidad()).compareTo(new Date()) <= 0) {
                    as.setConCambioClave(Boolean.TRUE);
                }
            } else {
                as.setMensaje("Nombre de Usuario o Clave incorrectos");
            }
        } catch (DaoException e) {
            LOG.log(Level.INFO, "ServidorServicio:Error al validar el ingreso del servidor:{0}", e);
        }
        return as;
    }

    /**
     * METODO PARA VALIDAR EL INGRESO A LA APLICACION BUSCANDO EN LA TABLA DE USUARIOS Y RETORNANDO UN VALOR INT.
     *
     * @param username
     * @param clave
     * @return VALOR VALIDADO.
     */
    public AccesoServidorVO validarIngresoServidorExterno(final String username, final String clave) {
        AccesoServidorVO as = new AccesoServidorVO();
        as.setConAcceso(Boolean.FALSE);
        as.setConCambioClave(Boolean.FALSE);
        try {
            String claveEncriptada = DigestUtils.md5Hex(clave);
            List<Servidor> listaUsuario = servidorDao.buscarExternoServidorPorUsernameClave(username, claveEncriptada);
            if (!listaUsuario.isEmpty()) {
                as.setServidor(listaUsuario.get(0));
                as.setConAcceso(Boolean.TRUE);
                if (as.getServidor().getFechaCaducidad() != null
                        && UtilFechas.truncarFecha(as.getServidor().getFechaCaducidad()).compareTo(new Date()) <= 0) {
                    as.setConCambioClave(Boolean.TRUE);
                }
            } else {
                as.setMensaje("Nombre de Usuario o Clave incorrectos");
            }
        } catch (DaoException e) {
            LOG.log(Level.INFO, "ServidorServicio:Error al validar el ingreso del servidor:{0}", e);
        }
        return as;
    }

    /**
     * generacion masiv de claves. Se generan claves para todo el distributivo detalles
     *
     * @param usuario
     * @throws java.lang.Exception
     */
    public void generarClavesMasivamente(final UsuarioVO usuario)
            throws Exception {
        ConnectionFactory connectionFactory;
        Connection connection = null;
        try {
            LOG.info("INICIANDO GENERACION MASIVA DE CLAVES CON ENVIO DE MAIL.................");
            Date fechaActual = new Date();
            // Conseguimos de la JNDI los objetos administrados
            Context jndiContext = new InitialContext();
            // Creamos la conexion y la sesion
            connectionFactory = (ConnectionFactory) jndiContext.lookup(CONNECTION_FACTORY);
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = null;
            Queue queue = (Queue) jndiContext.lookup(CONNECTION_QUEUE);
            producer = session.createProducer(queue);
            // Creamos un mensaje
            ObjectMessage message = session.createObjectMessage();
            message.setLongProperty("fecha", fechaActual.getTime());
            message.setLongProperty("institucion_ejercicio_fiscal", usuario.getInstitucionEjercicioFiscal().getId());
            message.setStringProperty("usuario", usuario.getUsuario());
            // Enviamos mensaje
            producer.send(message);
            System.out.println("Mensaje enviado: " + message.getJMSMessageID());
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            // Cerramos la conexion
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                LOG.info("error:" + e);
            }
        }
    }

    /**
     * Genera y caduca la clave para un servidor.
     *
     * @param s
     * @param usuario
     * @param esMasivo
     * @return
     * @throws ServicioException
     */
    public synchronized String generarClaves(final Servidor s, final String usuario, Boolean esMasivo) throws Exception {
        s.setFechaActualizacion(new Date());
        s.setUsuarioActualizacion(usuario);
        String clave;
        if (esMasivo) {
            clave = "12345";
        } else {
            clave = crearGenerarClave();
        }
        s.setClave(DigestUtils.md5Hex(clave));
        s.setFechaCaducidad(UtilFechas.sumarMeses(new Date(), 2));

        //enviar mail
        if (s.getMail() != null && !s.getMail().isEmpty() && UtilMail.esCorreoElectronico(s.getMail())) {
            enviarCorreo(s, clave);
            servidorDao.actualizar(s);
            //verificar si tiene rol asignado, caso contrario crear
            List<RolServidor> listaRol = rolServidorDao.buscarTodosPorServidor(s.getId());
            if (listaRol.isEmpty()) {
                RolServidor rs = new RolServidor();
                rs.setFechaCreacion(new Date());
                rs.setServidor(s);
                rs.setRol(new Rol(1l));
                rs.setUsuarioCreacion(usuario);
                rs.setVigente(Boolean.TRUE);
                rolServidorDao.crear(rs);
            }

        } else if (!esMasivo) {
            LOG.log(Level.INFO, "Servidor sin mail:{0} : {1}",
                    new Object[]{s.getNumeroIdentificacion(), s.getApellidosNombres()});
            enviarNotificacionAdministradorEjecucionFallida("SIGEN: Servidor sin cuenta correo",
                    "Envio de notificación de cambio/generación de clave ",
                    "El Servidor con Numero de Identificacion:" + s.getNumeroIdentificacion() + ":"
                    + s.getApellidosNombres() + " no tiene registrada una cuenta de correo.");
        }

        return null;
    }

    /**
     * Este metodo genera una clave alfanumerica de forma randomica.
     *
     * @return String
     * @throws ServicioException control de excepcion
     */
    public String crearGenerarClave() throws ServicioException {
        try {
            StringBuilder clave = new StringBuilder(6);
            for (int i = 1; i <= clave.capacity(); i++) {
                clave.append(
                        UtilCadena.CARACTERES_CLAVE[UtilNumeros.indiceRandomico(UtilCadena.CARACTERES_CLAVE.length)]);
            }
            return clave.toString();
        } catch (Exception e) {
            throw new ServicioException("Error al generar la clave..", e);
        }
    }

    /**
     * Cadula la clave de un servidor.
     *
     * @param s
     * @param usuario
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public void caducarAnularClave(final Servidor s, final String usuario) throws DaoException {
        s.setFechaCaducidad(UtilFechas.sumarDias(new Date(), 0));
        s.setFechaActualizacion(new Date());
        s.setUsuarioActualizacion(usuario);
        s.setClave("345sd22323d");
        servidorDao.actualizar(s);
    }

    /**
     * Verifica que las parejas de tipos de documentos y documentos pasados por parametros existan. Devuelve los pares
     * que NO existen.
     *
     * @param documentos
     * @return
     * @throws ServicioException
     */
    public List<CargaMasivaServidorVO> validarDocumentosExistentes(final List<CargaMasivaServidorVO> documentos) throws ServicioException {
        try {
            List<CargaMasivaServidorVO> noExistentes = servidorDao.chequearDocumentosExistentes(documentos);
            return noExistentes == null ? new ArrayList<CargaMasivaServidorVO>() : noExistentes;
        } catch (DaoException ex) {
            Logger.getLogger(ServidorServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Actualiza los atributos de servidor dados por carga masiva los codigos que se esperan son: En el caso de
     * recibeFondoReserva el codigo es RFR En el caso de tomaTransporte el codigo es TT
     *
     * @param codigoAtributo
     * @param servidores
     * @throws ServicioException
     */
    public void actualizarAtributoServidor(String codigoAtributo, final List<CargaMasivaServidorVO> servidores) throws ServicioException {
        try {
            /**
             * Para no ejecutar una consulta muy grande en ficheros grandes La variable tamanioBlogue define el numero
             * de servidores que seran tenidos en cuenta en una misma query
             */
            int tamanioBlogue = 400;
            int cantElementos = servidores.size();
            int inicio = 0;
            int fin = inicio + tamanioBlogue;
            boolean completado = false;
            do {
                if (fin > cantElementos) {
                    fin = cantElementos;
                    completado = true;
                }
                servidorDao.actualizarAtributo(codigoAtributo, servidores.subList(inicio, fin));
                inicio += tamanioBlogue;
                fin = inicio + tamanioBlogue;
                if (inicio >= cantElementos) {
                    completado = true;
                }
            } while (!completado);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    public ServidorInstitucion buscarServidorInstitucion(Long institucionId, Long servidorId) throws DaoException {
        try {
            return servidorInstitucionDao.buscarPorInstitucionServidor(institucionId, servidorId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
