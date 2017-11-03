/**
 * SeguridadServicio.java ESIPREN V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the
 * confidential and proprietary information of AtikaSoft ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with AtikaSoft.
 *
 *
 * AtikaSoft Quito - Ecuador http://www.atikasoft.com.ec/
 *
 * Nov 14, 2012
 *
 * Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.consumer.mdmq.ConsumerMDMQ;
import ec.com.atikasoft.proteus.consumer.mdmq.vo.ResultadoAutentificarVO;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.RolServidorDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoIdentificacionEnum;
import ec.com.atikasoft.proteus.enums.TipoModalidadEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.SeguridadClaveAnteriroIncorrectaException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.RolServidor;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.util.UtilMail;
import ec.com.atikasoft.proteus.vo.AccesoServidorVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Realiza opracion de seguridad.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class SeguridadServicio extends BaseServicio {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(SeguridadServicio.class.getName());

    /**
     * Codigos de acceso al sistema.
     */
    private static final long[] CODIGOS_ACCESO = new long[]{20997, 20998, 0};

    /**
     * Codigo que indica que se debe cambiar de clave.
     */
    private static final long CODIGOS_CAMBIO_CLAVE = 20999;

    /**
     * Numero de meses de vida de una clave.
     */
    private static final int MESES_DURACION_CLAVE = 2;

    /**
     * Dao de servidores.
     */
    @EJB
    private ServidorDao servidorDao;
    /**
     * Servicio de mensajeria.
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     *
     */
    @EJB
    private UnidadOrganizacionalDao unidadOrganizacionalDao;

    /**
     *
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @EJB
    private RolServidorDao rolServidorDao;

    /**
     * Constructor sin argumentos.
     */
    public SeguridadServicio() {
        super();
    }

    public Servidor buscarPorUsuario(final String usuario) throws ServicioException {
        try {
            return servidorDao.buscar("", "");
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param codigoRecibido
     * @return
     */
    private Boolean verificarCodigoAcceso(final long codigoRecibido) {
        Boolean resultado = Boolean.FALSE;
//         return Boolean.TRUE;
        for (long codigoAutorizado : CODIGOS_ACCESO) {
            if (codigoAutorizado == codigoRecibido) {
                resultado = Boolean.TRUE;
                break;
            }
        }
        return resultado;
    }

    /**
     * Realiza la auentifica de usuario.
     *
     * @param usuario
     * @param clave
     * @param oficina
     * @param ip
     * @return
     * @throws ServicioException
     */
    public UsuarioVO autentificar(final String usuario, final String clave, final Integer oficina, final String ip)
            throws ServicioException {
        try {
            String endpoint = parametroInstitucionalDao.buscarPorInstitucionYNemonico(1l,
                    ParametroInstitucionCatalogoEnum.ENDPOINT_SEGURIDADES.getCodigo()).getValorTexto();
//            LOG.log(Level.INFO, "WS Seguridad: {0}", endpoint);
            UsuarioVO vo = new UsuarioVO();
            vo.setCambiarClave(Boolean.FALSE);
            ConsumerMDMQ ws = new ConsumerMDMQ();
            ResultadoAutentificarVO r = ws.autentificar(usuario, clave, oficina, ip, endpoint);
//            LOG.log(Level.INFO, "  resultado autenticacion: {1} del usuario {0}:", new Object[]{usuario, r.getIdentificadorSesion()});
            //.

            vo.setResultadoAutentificarVO(r);
            if (verificarCodigoAcceso(r.getResultado())) {
                vo = obtenerValoresServidor(usuario, r, true);
            } else if (r.getResultado().longValue() == CODIGOS_CAMBIO_CLAVE) {
                vo.setCambiarClave(Boolean.TRUE);
                vo.setEsUsuarioWS(Boolean.TRUE);
                vo.setConAcceso(Boolean.FALSE);
            } else {
                vo.setConAcceso(Boolean.FALSE);
                vo.setCambiarClave(Boolean.FALSE);
                vo.setEsUsuarioWS(Boolean.FALSE);
                //ingreso solo al portal
                AccesoServidorVO as = servidorServicio.validarIngresoServidor(usuario, clave);
                r.setMensaje(as.getMensaje());
                if (as.getConCambioClave()) {
                    vo.setCambiarClave(Boolean.TRUE);
                } else if (as.getConAcceso()) {
                    r.setNumeroIdentificacion(as.getServidor().getNumeroIdentificacion());
                    vo = obtenerValoresServidor(usuario, r, false);
                } else {
                    // ingreso invitado (externo)
                    AccesoServidorVO asExterno = servidorServicio.validarIngresoServidorExterno(usuario, clave);
                    r.setMensaje(asExterno.getMensaje());
                    if (asExterno.getConCambioClave()) {
                        vo.setCambiarClave(Boolean.TRUE);
                    } else if (asExterno.getConAcceso()) {
                        r.setNumeroIdentificacion(asExterno.getServidor().getNumeroIdentificacion());
                        vo = obtenerValoresServidorExterno(usuario, r, false);
                    } else {
                        vo.setConAcceso(Boolean.FALSE);
                    }
                }
            }
            return vo;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Obtiene valores del servidor conectado.
     *
     * @param usuario
     * @param r
     * @return
     * @throws ServicioException
     */
    private UsuarioVO obtenerValoresServidor(final String usuario, final ResultadoAutentificarVO r, 
            final boolean esUsuarioWS) throws ServicioException {
        UsuarioVO vo = new UsuarioVO();
        vo.setCambiarClave(Boolean.FALSE);
        vo.setResultadoAutentificarVO(r);
        vo.setConAcceso(Boolean.TRUE);
        vo.setEsUsuarioWS(esUsuarioWS);
        try {
            Servidor s = servidorDao.buscar(r.getNumeroIdentificacion().trim().length() == 10
                    ? TipoIdentificacionEnum.CEDULA.getCodigo() : TipoIdentificacionEnum.PASAPORTE.getCodigo(), r.
                    getNumeroIdentificacion());
            if (s == null) {
                vo.setConAcceso(Boolean.FALSE);
                r.setMensaje("SIGEN:Servidor no existe en el sistema.");
            } else {
                if (s.getListaDistributivoDetalle().isEmpty()) {
                    vo.setConAcceso(Boolean.FALSE);
                    r.setMensaje("SIGEN:Servidor no se encuentra activo en el distributivo..");
                } else {
                    EjercicioFiscal ejercicioFiscalActivo = administracionServicio.buscarEjercicioFiscalActivo();
                    if (ejercicioFiscalActivo == null) {
                        vo.setConAcceso(Boolean.FALSE);
                        r.setMensaje("SIGEN:No existe un ejercicio fiscal activo.");
                    } else {
//                            List<MenuVO> menus = ws.autorizar(r.getIdentificadorSesion(), usuario, endpoint);
//                            vo.setMenus(menus);
                        DistributivoDetalle dd = s.getListaDistributivoDetalle().get(0);
                        vo.setUsuario(usuario);
                        vo.setDistributivoDetalle(dd);
                        vo.setEjercicioFiscal(ejercicioFiscalActivo);
                        vo.setFechaIngreso(new Date());
                        vo.setInstitucion(dd.getDistributivo().getInstitucionEjercicioFiscal().getInstitucion());
                        vo.setInstitucionEjercicioFiscal(dd.getDistributivo().getInstitucionEjercicioFiscal());
                        vo.setServidor(s);
                        if (!esUsuarioWS) {
                            //verificar fecha de contrato
                            if (dd.getDistributivo().getModalidadLaboral().getModalidad().equals(TipoModalidadEnum.CONTRATO.getCodigo())
                                    && dd.getFechaFin() != null
                                    && UtilFechas.truncarFecha(dd.getFechaFin()).compareTo(UtilFechas.truncarFecha(new Date())) < 0) {
                                vo.setConAcceso(Boolean.FALSE);
                                servidorServicio.caducarAnularClave(s, usuario);
                            }

                        }

                    }
                }
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }
        return vo;
    }

    /**
     * Obtiene valores del servidor conectado.
     *
     * @param usuario
     * @param r
     * @return
     * @throws ServicioException
     */
    private UsuarioVO obtenerValoresServidorExterno(final String usuario, final ResultadoAutentificarVO r, final boolean esUsuarioWS) throws ServicioException {
        UsuarioVO vo = new UsuarioVO();
        vo.setCambiarClave(Boolean.FALSE);
        vo.setResultadoAutentificarVO(r);
        vo.setConAcceso(Boolean.TRUE);
        vo.setEsUsuarioWS(esUsuarioWS);
        try {
            Servidor s = servidorDao.buscarExterno(r.getNumeroIdentificacion().trim().length() == 10
                    ? TipoIdentificacionEnum.CEDULA.getCodigo() : TipoIdentificacionEnum.PASAPORTE.getCodigo(), r.
                    getNumeroIdentificacion());
            if (s == null) {
                vo.setConAcceso(Boolean.FALSE);
                r.setMensaje("SIGEN:Servidor no existe en el sistema.");
            } else {
                EjercicioFiscal ejercicioFiscalActivo = administracionServicio.buscarEjercicioFiscalActivo();
                if (ejercicioFiscalActivo == null) {
                    vo.setConAcceso(Boolean.FALSE);
                    r.setMensaje("SIGEN:No existe un ejercicio fiscal activo.");
                } else {
                    vo.setUsuario(usuario);
                    vo.setEjercicioFiscal(ejercicioFiscalActivo);
                    vo.setFechaIngreso(new Date());
                    vo.setServidor(s);
                }
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }
        return vo;
    }

    /**
     * Obtiene valores del servidor conectado.
     *
     * @param usuario
     * @param r
     * @return
     * @throws ServicioException
     */
    private UsuarioVO obtenerValoresInvitado(final String usuario, final ResultadoAutentificarVO r, final boolean esUsuarioWS) throws ServicioException {
        UsuarioVO vo = new UsuarioVO();
        vo.setCambiarClave(Boolean.FALSE);
        vo.setResultadoAutentificarVO(r);
        vo.setConAcceso(Boolean.TRUE);
        vo.setEsUsuarioWS(esUsuarioWS);
        vo.setUsuario(usuario);
        vo.setFechaIngreso(new Date());
        EjercicioFiscal ejercicioFiscalActivo = administracionServicio.buscarEjercicioFiscalActivo();
        if (ejercicioFiscalActivo == null) {
            vo.setConAcceso(Boolean.FALSE);
            r.setMensaje("SIGEN:No existe un ejercicio fiscal activo.");
        } else {
            vo.setEjercicioFiscal(ejercicioFiscalActivo);
        }
        return vo;
    }

    /**
     *
     * @param codigoRol
     * @param codigoUnidadOrganizacional
     * @return
     * @throws ServicioException
     */
    public List<Servidor> buscarUsuariosPorRol(final String codigoRol, final String codigoUnidadOrganizacional) throws
            ServicioException {
        try {
            List<Servidor> usuarios = new ArrayList<>();
            List<UnidadOrganizacional> unidades = unidadOrganizacionalDao.buscarPorNemonico(codigoUnidadOrganizacional);
            if (!unidades.isEmpty()) {
                UnidadOrganizacional uo = unidades.get(0);
                buscarUsuariosPorRol(uo, usuarios);
            }
            return usuarios;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param uo
     * @param usuarios
     * @throws DaoException
     */
    private void buscarUsuariosPorRol(UnidadOrganizacional uo, List<Servidor> usuarios) throws DaoException {
        List<RolServidor> servidores = rolServidorDao.buscarPorRol(uo.getCodigo(), uo.getCodigo());
        for (RolServidor rs : servidores) {
            usuarios.add(rs.getServidor());
        }
        for (UnidadOrganizacional hijo : uo.getListaUnidadesOrganizacionales()) {
            buscarUsuariosPorRol(hijo, usuarios);
        }
    }

    /**
     *
     * @param numeroIdentificacion
     * @param codigoRol
     * @param codigoUnidadOrganizacional
     * @return
     * @throws ServicioException
     */
    public Servidor buscarUsuarioPorRol(final String numeroIdentificacion, final String codigoRol,
            final String codigoUnidadOrganizacional) throws ServicioException {
        try {
            Servidor servidor = null;
            RolServidor rs = rolServidorDao.buscarServidorRol(numeroIdentificacion, codigoRol,
                    codigoUnidadOrganizacional);
            if (rs != null) {
                servidor = rs.getServidor();
            }
            return servidor;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     * @throws ServicioException
     */
    public Servidor buscarUsuario(final String tipoIdentificacion, final String numeroIdentificacion) throws
            ServicioException {
        try {
            return servidorDao.buscar(tipoIdentificacion, numeroIdentificacion);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Realiza el cambio de clave para los usuarios del WS.
     *
     * @param identificadorSesion
     * @param usuario
     * @param claveAnterior
     * @param claveNueva
     * @return
     * @throws ServicioException
     */
    public Boolean cambioClaveWS(final String identificadorSesion, final String usuario, final String claveAnterior,
            final String claveNueva)
            throws ServicioException {
        try {
            String endpoint = parametroInstitucionalDao.buscarPorInstitucionYNemonico(1l,
                    ParametroInstitucionCatalogoEnum.ENDPOINT_SEGURIDADES.getCodigo()).getValorTexto();
            ConsumerMDMQ ws = new ConsumerMDMQ();
            return ws.cambioClave(identificadorSesion, usuario, claveAnterior, claveNueva, endpoint);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Realiza el cambio de clave para los usuarios del WS.
     *
     * @param usuario
     * @param claveAnterior
     * @param claveNueva
     * @return true si se cambio la clave
     * @throws
     * ec.com.atikasoft.proteus.excepciones.SeguridadClaveAnteriroIncorrectaException
     * @throws ServicioException
     */
    public Boolean cambioClaveServidor(final String usuario, final String claveAnterior, final String claveNueva) throws ServicioException {
        Boolean cambioClave = Boolean.FALSE;
        try {
            Servidor s = servidorDao.buscar(usuario.trim().length() == 10 ? TipoIdentificacionEnum.CEDULA.getCodigo() : TipoIdentificacionEnum.PASAPORTE.getCodigo(), usuario);
            if (s == null) {
                s = servidorDao.buscarExterno(usuario.trim().length() == 10 ? TipoIdentificacionEnum.CEDULA.getCodigo() : TipoIdentificacionEnum.PASAPORTE.getCodigo(), usuario);
                if (s == null) {
                    return cambioClave;
                }
            }
            if (!DigestUtils.md5Hex(claveAnterior).equals(s.getClave())) {
                throw new SeguridadClaveAnteriroIncorrectaException("clave anterior no corresponde a la registrada actualmente en el sistema");
            }
            s.setFechaActualizacion(new Date());
            s.setUsuarioActualizacion(usuario);
            s.setClave(DigestUtils.md5Hex(claveNueva));
            s.setFechaCaducidad(UtilFechas.sumarMeses(new Date(), MESES_DURACION_CLAVE));
            servidorDao.actualizar(s);
            cambioClave = Boolean.TRUE;
            if (s.getMail() == null) {
                throw new ServicioException("Correo electrónico no se encuentra registrado.");
            } else {
                if (UtilMail.esCorreoElectronico(s.getMail())) {
                    servidorServicio.enviarCorreo(s, claveNueva);
                } else {
                    throw new ServicioException("Correo electrónico registrado no corresponde a uno válido.");
                }
            }
        } catch (SeguridadClaveAnteriroIncorrectaException e) {
            throw e;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
        return cambioClave;
    }

}
