/*
 *  ClaseDao.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.ConsultaTramiteVO;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.ConsultaTrayectoriaLaboralVO;
import java.util.*;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class MovimientoDao extends GenericDAO<Movimiento, Long> {

    /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(MovimientoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public MovimientoDao() {
        super(Movimiento.class);
    }

    /**
     * Este método busca los movimientos segun el tramite.
     *
     * @param idTramite Long
     * @return List
     * @throws DaoException DaoException
     */
    public List<Movimiento> buscarPorTramite(final Long idTramite) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Movimiento.BUSCAR_POR_TRAMITE, idTramite);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo controla que el servidor no este registrado con otro movimiento.
     *
     * @param movimientos
     * @return
     * @throws DaoException
     */
    public List<Integer> validarDuplicadorCreacion(final Map<Integer, DistributivoDetalle> movimientos)
            throws DaoException {
        List<Integer> duplicados = new ArrayList<Integer>();
        Set<Integer> keys = movimientos.keySet();
        for (Integer key : keys) {
            DistributivoDetalle dd = movimientos.get(key);
            List<Movimiento> duplicado = buscarPorConsultaNombrada(Movimiento.BUSCAR_PUESTOS_DUPLICADOS_VALIDACION,
                    dd.getId());
            if (!duplicado.isEmpty()) {
                duplicados.add(key);
            }
        }
        return duplicados;
    }

    /**
     * Recupera movientos duplicados, asignados al mismo servidor.
     *
     * @param tipoIdentificacion Tipo de identificacion.
     * @param numeroIdentificacion Numero de la identificacion.
     * @param movimientoId Identificador del movimiento.
     * @return Lista de movimientos duplicados.
     * @throws DaoException Error de ejecucion.
     */
    public List<Movimiento> buscarPersonasDuplicadas(final String tipoIdentificacion, final String numeroIdentificacion,
            final Long movimientoId) throws DaoException {
        try {
            List<Movimiento> lista = buscarPorConsultaNombrada(Movimiento.BUSCAR_PERSONAS_DUPLICADAS,
                    tipoIdentificacion, numeroIdentificacion, movimientoId);
            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera movientos duplicados, asignados al mismo servidor y tipo de movimiento.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoMovimientoId Identificador del tipo de movimiento.
     * @param tramiteExcluirId
     * @return Lista de movimientos duplicados.
     * @throws DaoException Error de ejecucion.
     */
    public List<Movimiento> buscarPersonasPorTipoMovimientoDuplicadas(final String tipoIdentificacion,
            final String numeroIdentificacion,
            final Long tipoMovimientoId, final Long tramiteExcluirId) throws DaoException {
        try {
            List<Movimiento> lista = buscarPorConsultaNombrada(
                    Movimiento.BUSCAR_PERSONAS_POR_TIPO_MOVIMIENTO_DUPLICADAS,
                    tipoIdentificacion, numeroIdentificacion, tipoMovimientoId, tramiteExcluirId);
            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera movientos duplicados, asignados al mismo puesto.
     *
     * @param puestoId Identificador unico del puesto.
     * @param movimientoId Identificador del movimiento.
     * @return Lista de movimientos duplicados.
     * @throws DaoException Error de ejecucion.
     */
    public List<Movimiento> buscarPuestosDuplicados(final Long puestoId, final Long movimientoId) throws DaoException {
        try {
            List<Movimiento> lista = buscarPorConsultaNombrada(Movimiento.BUSCAR_PUESTOS_DUPLICADOS, puestoId,
                    movimientoId);
            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Quita vigencia de los movimientos de un tramite especifico.
     *
     * @param tramiteId Identificador unico del tramite.
     * @throws DaoException Error de persistencia.
     */
    public void quitarVigencia(final Long tramiteId) throws DaoException {
        try {
            Query query = getEntityManager().createNamedQuery(Movimiento.QUITAR_VIGENCIA);
            query.setParameter(1, tramiteId);
            query.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo actualiza los datos del movimiento segun los del tramite.
     *
     * @param tramite Tramite
     * @throws DaoException Captura de errores
     */
    public void actualizarDatosDocumentoHabilitenteMasivos(final Tramite tramite) throws
            DaoException {
        Query query = getEntityManager().createNamedQuery(Movimiento.ACTUALIZAR_POR_TRAMITE);
        query.setParameter(1, tramite.getTramiteAuxiliar().getRigeApartirDe());
        query.setParameter(2, tramite.getTramiteAuxiliar().getFechaHasta());
        query.setParameter(3, tramite.getTramiteAuxiliar().getExplicacion());
        query.setParameter(4, tramite.getTramiteAuxiliar().getDocumentoPrevio());
        query.setParameter(5, tramite.getTramiteAuxiliar().getNumeroDocumentoAccion());
        query.setParameter(6, tramite.getTramiteAuxiliar().getFechaDocumentoAccion());
        query.setParameter(7, tramite.getTramiteAuxiliar().getAsuntoMeno());
        query.setParameter(8, tramite.getTramiteAuxiliar().getContenidoMemo());
        query.setParameter(9, tramite.getTramiteAuxiliar().getTipoPeriodo());
        query.setParameter(10, tramite.getId());
        query.executeUpdate();
    }

    /**
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param nemonicoTipoMovimiento
     * @param codigoFase
     * @return
     * @throws DaoException
     */
    public List<Movimiento> buscarPorServidorTipoMovimientoYEstadoTramite(final String tipoIdentificacion,
            final String numeroIdentificacion, final String nemonicoTipoMovimiento, final String codigoFase) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(Movimiento.BUSCAR_POR_SERVIDOR_Y_ESTADO_TRAMITE, tipoIdentificacion,
                    numeroIdentificacion, nemonicoTipoMovimiento, codigoFase);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo busca un movimiento segun el numero de documetno habilitante.
     *
     * @param documentoHabilitante Integer
     * @return Movimiento
     * @throws DaoException Captura de errores
     */
    public Movimiento buscarPorDocumentoHabilitante(final String documentoHabilitante) throws DaoException {
        Movimiento m = null;
        List<Movimiento> movimientos = buscarPorConsultaNombrada(
                Movimiento.BUSCAR_POR_DOCUMENTO_HABILITANTE, documentoHabilitante);
        if (movimientos != null && !movimientos.isEmpty()) {
            m = movimientos.get(0);
        }
        return m;
    }

    /**
     * Este metodo busca un movimiento segun para encontrar la licencia academica segun la cedula.
     *
     * @param cedula Sring param tipoLicencia Sring
     * @return Movimiento
     * @throws DaoException Captura de errores
     */
    public Movimiento buscarMovimientoPorCedulaLicenciaAcademica(final String cedula, final String tipoLicencia) throws
            DaoException {
        Movimiento m = null;
        List<Movimiento> movimientos = buscarPorConsultaNombrada(
                Movimiento.BUSCAR_POR_CEDULA_LICENCIA_ACADEMICA, cedula, tipoLicencia);
        if (movimientos != null && !movimientos.isEmpty()) {
            m = movimientos.get(0);
        }
        return m;
    }

    /**
     * Este metodo busca un movimiento segun la cedula.
     *
     * @param cedula Sring param tipoLicencia Sring
     * @return Movimiento
     * @throws DaoException Captura de errores
     */
    public Movimiento buscarMovimientoPorCedula(final String cedula) throws DaoException {
        Movimiento m = null;
        List<Movimiento> movimientos = buscarPorConsultaNombrada(
                Movimiento.BUSCAR_POR_CEDULA, cedula);
        if (movimientos != null && !movimientos.isEmpty()) {
            m = movimientos.get(0);
        }
        return m;
    }

    /**
     * Permite recuperar movimientos.
     *
     * @param tipoMovimientoId
     * @param numeroIdentificacion
     * @param codigoFase
     * @param fechaCorte
     * @return
     * @throws DaoException
     */
    public List<Movimiento> buscar(final Long institucionId, final String tipoIdentificacion,
            final String numeroIdentificacion, final String nemonicoTipoMovimiento, final String codigoFase,
            final Date fechaCorte) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Movimiento.BUSCAR_POR_SERVIDOR_ESTADO_FECHALEGALIZACION, institucionId,
                    tipoIdentificacion, numeroIdentificacion, nemonicoTipoMovimiento, codigoFase, fechaCorte);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera los movimientos de suspencion temporal que se encuentran vigentes.
     *
     * @param nemonicoTipoMovimiento Nemonico del tipo de movimiento de suspension temporal.
     * @return
     * @throws DaoException
     */
    public List<Movimiento> buscarPorSuspensionTemporalVigente(final String nemonicoTipoMovimiento)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(Movimiento.BUSCAR_POR_SUSPENSION_TEMPORAL_VIGENTE, nemonicoTipoMovimiento,
                    EstadosTramiteEnum.REGISTRADO.getCodigo(), new Date());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo es usado para el buscardor de tramites.
     *
     * @param ctvo ConsultaTramiteVO
     * @param posicion Integer
     * @param maximoResultado Integer
     * @return lista de movimientos
     * @throws DaoException
     */
    public List<Movimiento> consultaTramite(final ConsultaTramiteVO ctvo, Integer posicion, Integer maximoResultado)
            throws DaoException {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("Select o from Movimiento o where o.vigente=true ");
        sqlStringConsultaTramite(sql, parametros, ctvo);
        System.out.println(":" + sql.toString());
        sql.append(" ORDER BY o.tramite.tramiteBitacora.fechaElaboracion DESC");
        Query query = getEntityManager().createQuery(sql.toString());

        if (maximoResultado >= 0) {
            query.setMaxResults(maximoResultado);
        }
        if (posicion >= 0) {
            query.setFirstResult(posicion);
        }

        for (String key : parametros.keySet()) {
            query.setParameter(key, parametros.get(key));
            System.out.println(key + "-->" + parametros.get(key));
        }
        List<Movimiento> resultList = query.getResultList();
        for (Movimiento m : resultList) {
            m.getTramite().getListaBitacoras().size();
        }
        return resultList;
    }

    /**
     * Metodo que adiciona los filtros al sqlstring para la consulta de tramite.
     *
     * @param sql StringBuilder
     * @param parametros Map<String, Object>
     * @param vo ConsultaTramiteVO
     */
    private void sqlStringConsultaTramite(final StringBuilder sql,
            final Map<String, Object> parametros, final ConsultaTramiteVO vo) {
        if (vo.getCodigoInstitucion() != null) {
            generarFiltroMovimiento(sql, parametros, "tramite.institucion.id", vo.getCodigoInstitucion());
        }
        if (vo.getPartidaPresupuestariaIndividual() != null) {
            generarFiltroMovimiento(sql, parametros, "distributivoDetalle.partidaIndividual",
                    vo.getPartidaPresupuestariaIndividual());
        }
        if (vo.getCodigoPuesto() != null) {
            generarFiltroMovimiento(sql, parametros, "distributivoDetalle.codigoPuesto", vo.getCodigoPuesto());
        }
        if (vo.getTipoMovimiento() != null) {
            generarFiltroMovimiento(sql, parametros, "tramite.tipoMovimiento.id", vo.getTipoMovimiento());
        }
        if (vo.getGrupo() != null) {
            generarFiltroMovimiento(sql, parametros, "tramite.tipoMovimiento.clase.grupo.id", vo.getGrupo());
        }
        if (vo.getClase() != null) {
            generarFiltroMovimiento(sql, parametros, "tramite.tipoMovimiento.clase.id", vo.getClase());
        }
        generarFiltroMovimientoLike(sql, parametros, "tramite.numericoTramite", vo.getNumeroTramite());
        generarFiltroMovimiento(sql, parametros, "numeroDocumentoHabilitante", vo.getNumeroDocumentoHabilitante());
        generarFiltroMovimiento(sql, parametros, "tramite.codigoFase", vo.getEstado());
//        if (vo.getUnidadAdministrativaId() != null) {
//            generarFiltroMovimiento(sql, parametros, "distributivoDetalle.distributivo.unidadOrganizacional.id",
//                    vo.getUnidadAdministrativaId());
//        } else {
//            sql.append(" AND o.distributivoDetalle.distributivo.unidadOrganizacional.id != -1 ");
//        }
//        generarFiltroMovimiento(sql, parametros, "partidaGeneral", ctvo.getPartidaPresupuestariaGeneral());

        generarFiltroMovimiento(sql, parametros, "tipoIdentificacion", vo.getTipoIdentificacion());
        generarFiltroMovimientoLike(sql, parametros, "apellidosNombres", vo.getNombres() == null ? ""
                : vo.getNombres().toUpperCase());
        generarFiltroMovimientoLike(sql, parametros, "numeroIdentificacion", vo.getNumeroIdentificacion());
        generarFiltroMovimientoRangoFechas(sql, parametros, "rigeApartirDe", vo.getFechaVigenciaDesde(),
                vo.getFechaVigenciaHasta());
        generarFiltroMovimientoRangoFechasTramiteBitacora(sql, parametros, "fechaElaboracion",
                vo.getFechaElaboracionDesde(),
                vo.getFechaElaboracionHasta());
        generarFiltroMovimientoRangoFechasTramiteBitacora(sql, parametros, "fechaAprobacion",
                vo.getFechaAprobacionDesde(),
                vo.getFechaAprobacionHasta());
        generarFiltroMovimientoRangoFechasTramiteBitacora(sql, parametros, "fechaLegalizacion",
                vo.getFechaLegalizacionDesde(),
                vo.getFechaLegalizacionHasta());
        generarFiltroMovimientoRangoFechasTramiteBitacora(sql, parametros, "fechaValidacion",
                vo.getFechaValidacionDesde(),
                vo.getFechaValidacionHasta());
        generarFiltroMovimientoRangoFechasTramiteBitacora(sql, parametros, "fechaRevision",
                vo.getFechaRevisionDesde(),
                vo.getFechaRevisionHasta());
        if (vo.getConsultaServidor()) {
            String valor = vo.getCedulaServidor();
            if (valor != null && !valor.isEmpty()) {
                sql.append(" AND o.tramite.id in (SELECT tbc").append(".tramite.id");
                sql.append(" FROM TramiteBitacora tbc WHERE");
                sql.append(" tbc.cedulaEliminacion = :cedulaEliminacion or");
                sql.append(" tbc.cedulaLegalizacion = :cedulaLegalizacion or");
                sql.append(" tbc.cedulaAprobacion = :cedulaAprobacion or");
                sql.append(" tbc.cedulaValidacion = :cedulaValidacion or");
                sql.append(" tbc.cedulaRevision = :cedulaRevision or");
                sql.append(" tbc.cedulaElaboracion = :cedulaElaboracion )");
                parametros.put("cedulaEliminacion", valor);
                parametros.put("cedulaLegalizacion", valor);
                parametros.put("cedulaAprobacion", valor);
                parametros.put("cedulaValidacion", valor);
                parametros.put("cedulaRevision", valor);
                parametros.put("cedulaElaboracion", valor);
            }
        }
        if (vo.getUnidades() != null && !vo.getUnidades().isEmpty()) {
            sql.append(" AND o.distributivoDetalle.distributivo.unidadOrganizacional.id IN (");
            for (UnidadOrganizacional uo : vo.getUnidades()) {
                sql.append(uo.getId()).append(",");
            }
            sql.delete(sql.length() - 1, sql.length());
            sql.append(") ");
        }
    }

    /**
     * Este metodo es usado para el buscardor de tramites.
     *
     * @param ctvo ConsultaTramiteVO
     * @return lista de movimientos
     * @throws DaoException
     */
    public Long contarTramite(final ConsultaTramiteVO ctvo) throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("Select COUNT(o.id) from Movimiento o where o.vigente=true ");
        sqlStringConsultaTramite(sql, parametros, ctvo);
        Query query = getEntityManager().createQuery(sql.toString());
        System.out.println("Parametros.");
        for (String key : parametros.keySet()) {
            query.setParameter(key, parametros.get(key));
            System.out.println(key + "-->" + parametros.get(key));
        }
        Long count = (Long) query.getSingleResult();
        System.out.println("count:" + count);
        return count;
    }

    public String consultaTramiteString(final ConsultaTramiteVO ctvo)
            throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder();
        System.out.println("Entra a buscar y resultados SELECT ");
        sql.append("Select o from Movimiento o where o.vigente=true ");
        sqlStringConsultaTramite(sql, parametros, ctvo);
        System.out.println("QUERY= " + sql.toString());
        Query query = getEntityManager().createQuery(sql.toString());
        return obtenerSql(query, parametros);
    }

    /**
     * Genera filtro por parametro de consulta.
     *
     * @param sql Sql.
     * @param parametros Parametros.
     * @param nombre Nombre.
     * @param valor Valor.
     */
    private void generarFiltroMovimiento(final StringBuilder sql, final Map<String, Object> parametros,
            final String nombre, final Object valor) {
        if (valor != null && !valor.toString().isEmpty()) {
            sql.append(" AND o.");
            sql.append(nombre);
            sql.append("=:");
            sql.append(nombre.replace('.', '_'));
            parametros.put(nombre.replace('.', '_'), valor);
        }
    }

    /**
     * Genera filtro por parametro de consulta.
     *
     * @param sql
     * @param parametros
     * @param nombre
     * @param valor
     */
    private void generarFiltroMovimientoLike(final StringBuilder sql, final Map<String, Object> parametros,
            final String nombre, final Object valor) {
        if (valor != null && !valor.toString().trim().isEmpty()) {
            sql.append(" AND o.");
            sql.append(nombre);
            sql.append(" LIKE :");
            sql.append(nombre.replace('.', '_'));
            parametros.put(nombre.replace('.', '_'), "%" + valor + "%");
        }
    }

    //rigeApartirDe
    private void generarFiltroMovimientoRangoFechas(final StringBuilder sql, final Map<String, Object> parametros,
            final String nombre, final Date desde, final Date hasta) {
        //Control Filtro fecha Aprobacion;
        String fInicio = nombre.concat("I");
        String fFin = nombre.concat("F");
        if (desde != null && hasta == null) {
            sql.append(" AND o.").append(nombre).append(" >= :").append(fInicio);
            parametros.put(fInicio, UtilFechas.obtenerInicioDelDia(desde));
        } else if (desde == null && hasta != null) {
            sql.append(" AND o.").append(nombre).append(" <= :").append(fFin);
            parametros.put(fFin, UtilFechas.obtenerFinDelDia(hasta));
        } else if (desde != null && hasta != null) {
            sql.append(" AND (o.").append(nombre).append(" BETWEEN :").
                    append(fInicio).append(" AND :").append(fFin).append(")");
            parametros.put(fInicio, UtilFechas.obtenerInicioDelDia(desde));
            parametros.put(fFin, UtilFechas.obtenerFinDelDia(hasta));
        }

    }

    /**
     *
     * @param sql
     * @param parametros
     * @param nombre
     * @param desde
     * @param hasta
     */
    private void generarFiltroMovimientoRangoFechasTramiteBitacora(final StringBuilder sql,
            final Map<String, Object> parametros, final String nombre, final Date desde, final Date hasta) {
        String fInicio = nombre.concat("I");
        String fFin = nombre.concat("F");
        if (desde != null && hasta == null) {
            sql.append(" AND o.tramite.id in (SELECT tb").append(nombre).append(".tramite.id FROM TramiteBitacora tb").
                    append(nombre);
            sql.append(" WHERE  tb").append(nombre).append(".").append(nombre).append(" >= :").append(
                    fInicio).append(")");
            parametros.put(fInicio, UtilFechas.obtenerInicioDelDia(desde));
        } else if (desde == null && hasta != null) {
            sql.append(" AND o.tramite.id in (SELECT tb").append(nombre).append(".tramite.id FROM TramiteBitacora tb").
                    append(nombre);
            sql.append(" WHERE  tb").append(nombre).append(".").append(nombre).append(" <= :").append(
                    fFin).append(")");
            parametros.put(fFin, UtilFechas.obtenerFinDelDia(hasta));
        } else if (desde != null && hasta != null) {
            sql.append(" AND o.tramite.id in (SELECT tb").append(nombre).append(".tramite.id FROM TramiteBitacora tb").
                    append(nombre);
            sql.append(" WHERE  (tb").append(nombre).append(".").append(nombre).append(" BETWEEN :").append(
                    fInicio).append(" AND :").append(
                            fFin).append("))");
            parametros.put(fInicio, UtilFechas.obtenerInicioDelDia(desde));
            parametros.put(fFin, UtilFechas.obtenerFinDelDia(hasta));
        }

    }

    /**
     * Metodo que busca los movimientos segun los parametros de busqueda para los reportes.
     *
     * @param usuario
     * @param codigoInstitucion
     * @param fechaVigenciaDesde
     * @param fechaVigenciaHasta
     * @param tipoMovimientoId
     * @return
     * @throws DaoException
     */
    public List<Movimiento> buscar(final String usuario, final String codigoInstitucion,
            final Date fechaVigenciaDesde, final Date fechaVigenciaHasta, final Long tipoMovimientoId,
            final String codigoFase, final Date fechaEstadoDesde, final Date fechaEstadoHasta)
            throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        StringBuilder sql = construirQueryBuscar(parametros, usuario, codigoInstitucion, tipoMovimientoId, codigoFase,
                fechaVigenciaDesde, fechaVigenciaHasta, fechaEstadoDesde, fechaEstadoHasta);
        Query query = getEntityManager().createQuery(sql.toString());
        for (String key : parametros.keySet()) {
            query.setParameter(key, parametros.get(key));
        }
        return query.getResultList();
    }

    /**
     * Metodo que genera el sql de buscar movimientos.
     *
     * @param usuario
     * @param codigoInstitucion
     * @param fechaVigenciaDesde
     * @param fechaVigenciaHasta
     * @param tipoMovimientoId
     * @return
     * @throws DaoException
     */
    public String buscarSqlMovimientoServidor(final String usuario, final String codigoInstitucion,
            final Date fechaVigenciaDesde, final Date fechaVigenciaHasta, final Long tipoMovimientoId,
            final String codigoFase, final Date fechaEstadoDesde, final Date fechaEstadoHasta)
            throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        StringBuilder sql
                = construirQueryBuscar(parametros, usuario, codigoInstitucion, tipoMovimientoId, codigoFase,
                        fechaVigenciaDesde, fechaVigenciaHasta, fechaEstadoDesde, fechaEstadoHasta);
        Query query = getEntityManager().createQuery(sql.toString());
        for (String key : parametros.keySet()) {
            query.setParameter(key, parametros.get(key));
        }
        return obtenerSql(query, parametros);
    }

    /**
     * Permite construir el query para buscar movimientos.
     *
     * @param parametros
     * @param usuario
     * @param codigoInstitucion
     * @param tipoMovimientoId
     * @param codigoFase
     * @param fechaVigenciaDesde
     * @param fechaVigenciaHasta
     * @param fechaEstadoDesde
     * @param fechaEstadoHasta
     * @return
     */
    private StringBuilder construirQueryBuscar(final Map<String, Object> parametros, final String usuario,
            final String codigoInstitucion, final Long tipoMovimientoId, final String codigoFase,
            final Date fechaVigenciaDesde, final Date fechaVigenciaHasta, final Date fechaEstadoDesde,
            final Date fechaEstadoHasta) {
        StringBuilder sql = new StringBuilder("SELECT o FROM Movimiento o WHERE ");
        sql.append("  o.numeroIdentificacion=:numeroIdentificacion  ");
        sql.append(" AND o.tramite.codigoInstitucion=:codigoInstitucion  ");
        sql.append(" AND o.vigente=true");
        parametros.put("numeroIdentificacion", usuario);
        parametros.put("codigoInstitucion", codigoInstitucion);
        if (tipoMovimientoId != null) {
            sql.append(" AND o.tramite.tipoMovimiento.id=:tipoMovimientoId  ");
            parametros.put("tipoMovimientoId", tipoMovimientoId);
        }
        if (codigoFase != null) {
            sql.append(" AND o.tramite.codigoFase=:codigoFase  ");
            parametros.put("codigoFase", codigoFase);
        }
        if (fechaVigenciaDesde != null && fechaVigenciaHasta != null) {
            sql.append(" AND o.rigeApartirDe BETWEEN :f1 AND :f2  ");
            parametros.put("f1", UtilFechas.truncarFecha(fechaVigenciaDesde));
            parametros.put("f2", UtilFechas.redondearFecha(fechaVigenciaHasta));
        } else if (fechaVigenciaDesde != null && fechaVigenciaHasta == null) {
            sql.append(" AND o.rigeApartirDe >= :f1");
            parametros.put("f1", UtilFechas.truncarFecha(fechaVigenciaDesde));
        } else if (fechaVigenciaDesde == null && fechaVigenciaHasta != null) {
            sql.append(" AND o.rigeApartirDe <= :f2");
            parametros.put("f2", UtilFechas.redondearFecha(fechaVigenciaHasta));
        }
        if (fechaEstadoDesde != null && fechaEstadoHasta != null) {
            sql.append(" AND o.tramite.fechaCreacion BETWEEN :f3 AND :f4  ");
            parametros.put("f3", UtilFechas.truncarFecha(fechaEstadoDesde));
            parametros.put("f4", UtilFechas.redondearFecha(fechaEstadoHasta));
        } else if (fechaEstadoDesde != null && fechaEstadoHasta == null) {
            sql.append(" AND o.tramite.fechaCreacion >= :f3");
            parametros.put("f3", UtilFechas.truncarFecha(fechaEstadoDesde));
        } else if (fechaEstadoDesde == null && fechaEstadoHasta != null) {
            sql.append(" AND o.tramite.fechaCreacion <= :f4");
            parametros.put("f4", UtilFechas.redondearFecha(fechaEstadoHasta));
        }
        return sql;
    }

    /**
     * Ordena la consulta.
     *
     * @param ordenar
     * @param tipo
     * @param sql
     */
    protected void ordenar(final String ordenar, final String tipo, final StringBuilder sql) {
        if (ordenar != null && !ordenar.trim().isEmpty()) {
            sql.append(" ORDER BY o.");
            sql.append(ordenar);
            if (tipo != null && !tipo.trim().isEmpty()) {
                sql.append(" ");
                sql.append(tipo);
            }
        }
    }

    /**
     * Recupera todos los movimientos que debe realizar un reintegro automatico en el sistema.
     *
     * @return
     * @throws DaoException
     */
    public List<Movimiento> buscarParFinalizacionAutomatica() throws DaoException {
        try {
            Date fechaActual = UtilFechas.truncarFecha(UtilFechas.sumarDias(new Date(), -1));
            Map<String, Object> parametros = new HashMap<String, Object>();
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT o FROM Movimiento o WHERE ");
            sql.append(" o.vigente=true AND o.tramite.tipoMovimiento.tipoMovimientoFinalizacionId IS NOT NULL AND ");
            sql.append(" o.tramite.codigoFase = '").append(EstadosTramiteEnum.REGISTRADO.getCodigo()).append("' AND ");
            sql.append(" o.finalizado=false  AND ");
            sql.append(" o.fechaHasta <= :fechaHasta ");
            parametros.put("fechaHasta", fechaActual);

            Query query = getEntityManager().createQuery(sql.toString());
            for (String key : parametros.keySet()) {
                query.setParameter(key, parametros.get(key));
            }
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera todos los movimientos que debe realizar un reintegro automatico en el sistema.
     *
     * @return
     * @throws DaoException
     */
    public List<Movimiento> buscarParaNotificacionFinalizacion() throws DaoException {
        try {
            Date fechaActual = UtilFechas.truncarFecha(UtilFechas.sumarDias(new Date(), 4));
            Map<String, Object> parametros = new HashMap<String, Object>();
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT o FROM Movimiento o WHERE ");
            sql.append(" o.vigente=true AND ");
            sql.append(" o.tramite.codigoFase = '").append(EstadosTramiteEnum.REGISTRADO.getCodigo()).append("' AND ");
            sql.append(" o.fechaHasta between :fechaDesde and :fechaHasta");
            parametros.put("fechaDesde", UtilFechas.truncarFecha(new Date()));
            parametros.put("fechaHasta", fechaActual);
            sql.append(" order by o.tramite.tipoMovimiento.id,o.fechaHasta ");
            Query query = getEntityManager().createQuery(sql.toString());
            for (String key : parametros.keySet()) {
                query.setParameter(key, parametros.get(key));
            }
            System.out.println("SQL==>" + obtenerSql(query, parametros));
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera movimientos en un periodo de tiempo por servidor.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param codigoFase
     * @param fechaDesde
     * @param fechaHasta
     * @return
     * @throws DaoException
     */
    public List<Movimiento> buscarPorPeriodo(final String tipoIdentificacion, final String numeroIdentificacion,
            final String codigoFase, final Date fechaDesde, final Date fechaHasta) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(Movimiento.BUSCAR_POR_PERIODO, tipoIdentificacion, numeroIdentificacion,
                    codigoFase, fechaDesde, fechaHasta);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera movimientos en un periodo de tiempo por servidor.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoMovimientoId
     * @param codigoFase
     * @param fechaDesde
     * @param fechaHasta
     * @return
     * @throws DaoException
     */
    public List<Movimiento> buscarPorPeriodoPorTipoMovimiento(final String tipoIdentificacion,
            final String numeroIdentificacion, final Long tipoMovimientoId, final String codigoFase,
            final Date fechaDesde, final Date fechaHasta) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(Movimiento.BUSCAR_POR_PERIODO_POR_TIPO_MOVIMIENTO, tipoIdentificacion,
                    numeroIdentificacion, tipoMovimientoId, codigoFase, fechaDesde, fechaHasta);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera movimientos por puesto y tipo de movimiento.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoMovimientoId
     * @param codigoFase
     * @param fechaDesde
     * @param fechaHasta
     * @return
     * @throws DaoException
     */
    public List<Movimiento> buscarPorPuestoYTipoMovimiento(final Long puestoId,
            final String nemonicoTipoMovimiento, final String codigoFase) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Movimiento.BUSCAR_POR_PUESTO_Y_TIPO_MOVIMIENTO, puestoId,
                    nemonicoTipoMovimiento, codigoFase);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera los movimientos para su finalizacion.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoMovimientoFinalizacionId
     * @return
     * @throws DaoException
     */
    public List<Movimiento> buscarParaFinalizacion(final String tipoIdentificacion, final String numeroIdentificacion,
            final Long tipoMovimientoFinalizacionId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Movimiento.BUSCAR_PARA_FINALIZACION, tipoIdentificacion,
                    numeroIdentificacion, tipoMovimientoFinalizacionId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param vo
     * @return
     * @throws DaoException
     */
    public List<Movimiento> buscarTrayectoria(final ConsultaTrayectoriaLaboralVO vo) throws DaoException {

        Map<String, Object> parametros = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM Movimiento o ");
        sql.append(" WHERE o.vigente=true and o.tramite.codigoFase='REG' ");
        if (vo.getFechaDesde() != null && vo.getFechaHasta() == null) {
            sql.append(" AND o.rigeApartirDe>=:fechaDesde");
            parametros.put("fechaDesde", UtilFechas.truncarFecha(vo.getFechaDesde()));
        } else if (vo.getFechaDesde() == null && vo.getFechaHasta() != null) {
            sql.append(" AND o.rigeApartirDe<=:fechaHasta");
            parametros.put("fechaHasta", UtilFechas.truncarFecha(UtilFechas.sumarDias(vo.getFechaHasta(), 1)));
        } else if (vo.getFechaDesde() != null && vo.getFechaHasta() != null) {
            sql.append(" AND o.rigeApartirDe BETWEEN :fechaDesde AND :fechaHasta");
            parametros.put("fechaDesde", UtilFechas.truncarFecha(vo.getFechaDesde()));
            parametros.put("fechaHasta", UtilFechas.truncarFecha(UtilFechas.sumarDias(vo.getFechaHasta(), 1)));
        }
        if (vo.getTipoDocumentoServidor() != null) {
            sql.append(" AND o.tipoIdentificacion=:tipoIdentificacion");
            parametros.put("tipoIdentificacion", vo.getTipoDocumentoServidor());
        }
        if (vo.getNumeroDocumentoServidor() != null && !vo.getNumeroDocumentoServidor().trim().isEmpty()) {
            sql.append(" AND o.numeroIdentificacion LIKE :numeroIdentificacion");
            parametros.put("numeroIdentificacion", UtilCadena.concatenar("%", vo.getNumeroDocumentoServidor(), "%"));
        }
//        if (vo.getApellidosNombresServidor() != null && !vo.getApellidosNombresServidor().trim().isEmpty()) {
//            sql.append(" AND o.apellidosNombres LIKE :apellidosNombres");
//            parametros.put("apellidosNombres", UtilCadena.concatenar("%", vo.getApellidosNombresServidor(), "%"));
//        }
        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        return query.getResultList();
    }

    /**
     * buscar por numero de identificacion.
     *
     * @param tipoIdentificacion
     * @return
     * @throws DaoException
     */
    public Movimiento buscarPorTipoIdentificacion(final String tipoIdentificacion) throws
            DaoException {
        try {

            Movimiento entidad = null;
            List<Movimiento> lista = buscarPorConsultaNombrada(
                    Movimiento.BUSCAR_POR_NUMERO_IDENTIFICACION, tipoIdentificacion);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param identificacion
     * @return
     * @throws DaoException
     */
    public List<Movimiento> buscarIngresosRegistrados(String identificacion) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Movimiento.BUSCAR_INGRESOS_REGISTRADOS_POR_IDENTIFICACION, identificacion);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
