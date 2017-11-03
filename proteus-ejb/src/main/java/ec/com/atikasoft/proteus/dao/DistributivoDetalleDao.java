
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

import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoUbicacionGeograficaEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.EjecucionNominaVO;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.eclipse.persistence.sessions.DatabaseRecord;
import org.eclipse.persistence.sessions.Session;

/**
 * Dao de distributivo detalle.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class DistributivoDetalleDao extends GenericDAO<DistributivoDetalle, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(DistributivoDetalleDao.class.getCanonicalName());

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Constructor sin argumentos.
     */
    public DistributivoDetalleDao() {
        super(DistributivoDetalle.class);
    }

    /**
     *
     * @param vo
     * @param inicio
     * @param longitud
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscar(final EjecucionNominaVO vo, final Integer inicio, Integer longitud)
            throws DaoException {
        System.out.println("buscar: inicio=" + inicio + ",longitud=" + longitud + "::" + vo.
                getIntitucionEjercicioFiscalId() + " todos:" + vo.getTodos());
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM DistributivoDetalle o ");
        sql.append(" WHERE o.vigente=true AND o.distributivo.vigente=true ");
        sql.append(" AND o.idServidor IS NOT NULL ");
        sql.append(" AND o.distributivo.institucionEjercicioFiscal.id=:institucionEjercicioFiscalId ");
        parametros.put("institucionEjercicioFiscalId", vo.getIntitucionEjercicioFiscalId());
        if (vo.getEstadosPuestoId() != null) {
            sql.append(" AND o.idEstadoPuesto IN (");
            for (Long id : vo.getEstadosPuestoId()) {
                sql.append(id).append(",");
            }
            sql.delete(sql.length() - 1, sql.length());
            sql.append(") ");
        }
        if (vo.getEstadosServidorId() != null) {
            sql.append(" AND o.servidor.estadoPersonalId IN (");
            for (Long id : vo.getEstadosServidorId()) {
                sql.append(id).append(",");
            }
            sql.delete(sql.length() - 1, sql.length());
            sql.append(") ");
        }
        if (vo.getTipoNomina().getRegimenLaboral() != null) {
            sql.append(" AND o.escalaOcupacional.nivelOcupacional.idRegimenLaboral=:idRegimenLaboral");
            parametros.put("idRegimenLaboral", vo.getTipoNomina().getRegimenLaboral().getId());
        }
//        System.out.println("buscar.2:" + vo.getTodos());
        if (!vo.getTodos()) {
            if (vo.getEscalaOcupacionalId() != null) {
                sql.append(" AND o.idEscalaOcupacional=:idEscalaOcupacional");
                parametros.put("idEscalaOcupacional", vo.getEscalaOcupacionalId());
            } else if (vo.getNivelOcupacionalId() != null) {
                sql.append(" AND o.escalaOcupacional.idNivelOcupacional=:idNivelOcupacional");
                parametros.put("idNivelOcupacional", vo.getNivelOcupacionalId());
            } else if (vo.getRegimenLaboralId() != null) {
                sql.append(" AND o.escalaOcupacional.nivelOcupacional.idRegimenLaboral=:idRegimenLaboral ");
                parametros.put("idRegimenLaboral", vo.getRegimenLaboralId());
            }
            if (vo.getModalidadLaboralId() != null) {
                sql.append(" AND o.distributivo.idModalidadLaboral=:idModalidadLaboral");
                parametros.put("idModalidadLaboral", vo.getModalidadLaboralId());
            }
            if (vo.getDenominacionPuestoId() != null) {
                sql.append(" AND o.idDenominacionPuesto=:idDenominacionPuesto ");
                parametros.put("idDenominacionPuesto", vo.getDenominacionPuestoId());
            }
            if (vo.getUnidadAdministrativaId() != null) {
                sql.append(" AND o.distributivo.unidadOrganizacional.id="
                        + ":idUnidadOrganizacional");
                parametros.put("idUnidadOrganizacional", vo.getUnidadAdministrativaId());
            }
            if (vo.getServidores() != null && !vo.getServidores().isEmpty()) {
                sql.append(" AND o.servidor.id IN (");
                for (Servidor s : vo.getServidores()) {
                    sql.append(s.getId()).append(",");
                }
                sql.delete(sql.length() - 1, sql.length());
                sql.append(") ");
            }
        }

        // filtrar de acuerdo al estado actual del puesto
        sql.append(" AND (o.estadosAdmPuestosRegimenLaboralId IS NULL OR o.estadosAdmPuestosRegimenLaboralId IN "
                + " (SELECT DISTINCT estado.id FROM EstadoAdministracionPuestoRegimenLaboral estado "
                + " WHERE estado.estadoAdministracionPuesto.activo=true AND estado.regimenLaboral.id="
                + " o.escalaOcupacional.nivelOcupacional.idRegimenLaboral ))");

        sql.append(" ORDER BY o.id");

        Query query = getEntityManager().createQuery(sql.toString());
        query.setFirstResult(inicio);
        query.setMaxResults(longitud);
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        query.setHint(QueryHints.FETCH, "o.unidadPresupuestaria");
        query.setHint(QueryHints.FETCH, "o.escalaOcupacional.nivelOcupacional.regimenLaboral");
        query.setHint(QueryHints.FETCH, "o.denominacionPuesto");
        query.setHint(QueryHints.FETCH, "o.estadoPuesto");
        query.setHint(QueryHints.FETCH, "o.ubicacionGeografica");
        query.setHint(QueryHints.FETCH, "o.servidor");
        query.setHint(QueryHints.FETCH, "o.distributivo.modalidadLaboral");
        query.setHint(QueryHints.FETCH, "o.distributivo.unidadOrganizacional");

        List<DistributivoDetalle> lista = query.getResultList();

        System.out.println("total:" + lista.size());
        return lista;

    }

    /**
     * Busca un distributivoDetalle por numero de partida individual
     *
     * @param nroPartidaIndividual
     * @exception DaoException
     * @return
     */
    public List<DistributivoDetalle> buscarPorPartidaIndividual(String nroPartidaIndividual) throws DaoException {
        List<DistributivoDetalle> ldd = buscarPorConsultaNombrada(
                DistributivoDetalle.BUSCAR_POR_PARTIDA_INDIVIDUAL, nroPartidaIndividual);
        return ldd;
    }

    /**
     *
     * @param vo
     * @return
     * @throws DaoException
     */
    public Long contar(final EjecucionNominaVO vo) throws DaoException {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(o) FROM DistributivoDetalle o ");
        sql.append(" WHERE o.vigente=true AND o.distributivo.vigente=true ");
        sql.append(" AND o.idServidor IS NOT NULL ");
        sql.append(" AND o.distributivo.institucionEjercicioFiscal.id=:institucionEjercicioFiscalId ");
        parametros.put("institucionEjercicioFiscalId", vo.getIntitucionEjercicioFiscalId());
        if (vo.getEstadosPuestoId() != null) {
            sql.append(" AND o.idEstadoPuesto IN (");
            for (Long id : vo.getEstadosPuestoId()) {
                sql.append(id).append(",");
            }
            sql.delete(sql.length() - 1, sql.length());
            sql.append(") ");
        }
        if (vo.getEstadosServidorId() != null) {
            sql.append(" AND o.servidor.estadoPersonalId IN (");
            for (Long id : vo.getEstadosServidorId()) {
                sql.append(id).append(",");
            }
            sql.delete(sql.length() - 1, sql.length());
            sql.append(") ");
        }
        if (vo.getTipoNomina().getRegimenLaboral() != null) {
            sql.append(" AND o.escalaOcupacional.nivelOcupacional.idRegimenLaboral=:idRegimenLaboral");
            parametros.put("idRegimenLaboral", vo.getTipoNomina().getRegimenLaboral().getId());
        }
//        System.out.println("buscar.2:" + vo.getTodos());
        if (!vo.getTodos()) {
            if (vo.getEscalaOcupacionalId() != null) {
                sql.append(" AND o.idEscalaOcupacional=:idEscalaOcupacional");
                parametros.put("idEscalaOcupacional", vo.getEscalaOcupacionalId());
            } else if (vo.getNivelOcupacionalId() != null) {
                sql.append(" AND o.escalaOcupacional.idNivelOcupacional=:idNivelOcupacional");
                parametros.put("idNivelOcupacional", vo.getNivelOcupacionalId());
            } else if (vo.getRegimenLaboralId() != null) {
                sql.append(" AND o.escalaOcupacional.nivelOcupacional.idRegimenLaboral=:idRegimenLaboral ");
                parametros.put("idRegimenLaboral", vo.getRegimenLaboralId());
            }
            if (vo.getModalidadLaboralId() != null) {
                sql.append(" AND o.distributivo.idModalidadLaboral=:idModalidadLaboral");
                parametros.put("idModalidadLaboral", vo.getModalidadLaboralId());
            }
            if (vo.getDenominacionPuestoId() != null) {
                sql.append(" AND o.idDenominacionPuesto=:idDenominacionPuesto ");
                parametros.put("idDenominacionPuesto", vo.getDenominacionPuestoId());
            }
            if (vo.getUnidadAdministrativaId() != null) {
                sql.append(" AND o.distributivo.unidadOrganizacional.id="
                        + ":idUnidadOrganizacional");
                parametros.put("idUnidadOrganizacional", vo.getUnidadAdministrativaId());
            }
            if (vo.getServidores() != null && !vo.getServidores().isEmpty()) {
                sql.append(" AND o.servidor.id IN (");
                for (Servidor s : vo.getServidores()) {
                    sql.append(s.getId()).append(",");
                }
                sql.delete(sql.length() - 1, sql.length());
                sql.append(") ");
            }
        }

        Query query = getEntityManager().createQuery(sql.toString());
        DatabaseQuery databaseQuery = ((EJBQueryImpl) query).getDatabaseQuery();
        Session session = getEntityManager().unwrap(JpaEntityManager.class).getActiveSession();
        databaseQuery.prepareCall(session, new DatabaseRecord());
        String sqlString = databaseQuery.getSQLString();
        System.out.println("SQL1:" + sql.toString());
        System.out.println("SQ2:" + sqlString);

        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }

        Long contador = (Long) query.getSingleResult();
        return contador;

    }

    /**
     *
     * @param vo
     * @param especial
     * @return
     * @throws DaoException
     * @throws ServicioException
     */
//    public List<DistributivoDetalle> buscarRecusivamente(final BusquedaPuestoVO vo, boolean especial)
//            throws DaoException, ServicioException {
//        List<DistributivoDetalle> puestos = new ArrayList<>();
//        if (vo.getUnidadAdministrativaId() == null) {
//            puestos.addAll(buscar(vo, especial));
//        } else {
//
//            // recursivamente por unidad organizacional
//            List<UnidadOrganizacional> unidades = new ArrayList<>();
//            // verificar si se encuentra con alguna asociacion.
//            Asociacion asociacion = asociacionDao.buscarPorUnidadOrganizacional(vo.getUnidadAdministrativaId());
//            if (asociacion == null) {
//                // no se encuentra
//                UnidadOrganizacional uo = unidadOrganizacionalDao.buscarAgrupador(vo.getUnidadAdministrativaId());
//                unidades.add(uo);
//            } else {
//                // si se encuentra
//                if (asociacion.getUnidadOrganizacional1() != null) {
//                    unidades.add(asociacion.getUnidadOrganizacional1());
//                }
//                if (asociacion.getUnidadOrganizacional2() != null) {
//                    unidades.add(asociacion.getUnidadOrganizacional2());
//                }
//                if (asociacion.getUnidadOrganizacional3() != null) {
//                    unidades.add(asociacion.getUnidadOrganizacional3());
//                }
//                if (asociacion.getUnidadOrganizacional4() != null) {
//                    unidades.add(asociacion.getUnidadOrganizacional4());
//                }
//                if (asociacion.getUnidadOrganizacional5() != null) {
//                    unidades.add(asociacion.getUnidadOrganizacional5());
//                }
//            }
//            for (UnidadOrganizacional unidad : unidades) {
//                buscarPorUnidadOrganizacional(vo, unidad, puestos, especial);
//            }
//        }
//
//        return puestos;
//    }
    /**
     *
     * @param vo
     * @param uo
     * @param puestos
     * @throws DaoException
     */
//    private void buscarPorUnidadOrganizacional(
//            final BusquedaPuestoVO vo, UnidadOrganizacional uo, List<DistributivoDetalle> puestos, boolean especial)
//            throws DaoException {
//        vo.setUnidadAdministrativaId(uo.getId());
//        puestos.addAll(buscar(vo, especial));
//        for (UnidadOrganizacional hijo : uo.getListaUnidadesOrganizacionales()) {
//            if (!hijo.getEsAgrupador()) {
//                buscarPorUnidadOrganizacional(vo, hijo, puestos, especial);
//            }
//        }
//    }
    /**
     * Permite buscar los puestos según los filtros especificados por el usuario.
     *
     * @param vo objeto con los filtros definidos por el usuario
     * @param especial
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscar(final BusquedaPuestoVO vo, boolean especial) throws DaoException {

        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM DistributivoDetalle o ");
        sql.append(" WHERE o.vigente=true ");
        if (vo.getIntitucionEjercicioFiscalId() != null) {
            sql.append(" AND o.distributivo.institucionEjercicioFiscal.id=:institucionEjercicioFiscalId ");
            parametros.put("institucionEjercicioFiscalId", vo.getIntitucionEjercicioFiscalId());
        }
        if (vo.getEscalaOcupacionalId() != null) {
            sql.append(" AND o.idEscalaOcupacional=:idEscalaOcupacional");
            parametros.put("idEscalaOcupacional", vo.getEscalaOcupacionalId());
        } else if (vo.getNivelOcupacionalId() != null) {
            sql.append(" AND o.escalaOcupacional.idNivelOcupacional=:idNivelOcupacional");
            parametros.put("idNivelOcupacional", vo.getNivelOcupacionalId());
        } else if (vo.getRegimenLaboralId() != null) {
            sql.append(" AND o.escalaOcupacional.nivelOcupacional.idRegimenLaboral=:idRegimenLaboral ");
            parametros.put("idRegimenLaboral", vo.getRegimenLaboralId());
        }
        if (vo.getModalidadLaboralId() != null) {
            sql.append(" AND o.distributivo.idModalidadLaboral=:idModalidadLaboral");
            parametros.put("idModalidadLaboral", vo.getModalidadLaboralId());
        }

        if (vo.getCertificacionPresupuestaria() != null && !vo.getCertificacionPresupuestaria().trim().isEmpty()) {
            sql.append(" AND EXISTS (SELECT cp  FROM CertificacionPresupuestaria cp WHERE cp.vigente=true AND "
                    + " cp.certificacionPresupuestaria LIKE :certificacionPresupuestaria AND "
                    + " cp.modalidadLaboral.id = o.distributivo.modalidadLaboral.id AND"
                    + " cp.unidadPresupuestaria.id = o.unidadPresupuestaria.id)");
            parametros.put("certificacionPresupuestaria", UtilCadena.concatenar("%", vo.getCertificacionPresupuestaria(), "%"));
        }

        if (vo.getTipoModalidad() != null) {
            sql.append(" AND o.distributivo.modalidadLaboral.modalidad =:tipoModalidad");
            parametros.put("tipoModalidad", vo.getTipoModalidad());
        }
        if (vo.getDenominacionPuestoId() != null) {
            sql.append(" AND o.idDenominacionPuesto=:idDenominacionPuesto ");
            parametros.put("idDenominacionPuesto", vo.getDenominacionPuestoId());
        }
        if (vo.getPartidaIndividual() != null && !vo.getPartidaIndividual().trim().isEmpty()) {
            sql.append(" AND o.partidaIndividual=:partidaIndividual");
            parametros.put("partidaIndividual", vo.getPartidaIndividual());
        }
        if (vo.getUnidadAdministrativaId() != null) {
            sql.append(" AND o.distributivo.unidadOrganizacional.id=" + ":idUnidadOrganizacional");
            parametros.put("idUnidadOrganizacional", vo.getUnidadAdministrativaId());
        }
        if (vo.getCodigoPuesto() != null) {
            sql.append(" AND o.codigoPuesto=" + ":codigoPuesto");
            parametros.put("codigoPuesto", vo.getCodigoPuesto());
        }
        if (vo.getUbicacionGeograficaId() != null && vo.getUbicacionGeograficaTipo() != null) {
            if (vo.getUbicacionGeograficaTipo().equals(TipoUbicacionGeograficaEnum.PAIS.getCodigo())) {
                sql.append(" AND o.ubicacionGeografica.ubicacionGeografica.ubicacionGeografica.idUbicacionGeografica"
                        + " =:idUbicacionGeografica");
                parametros.put("idUbicacionGeografica", vo.getUbicacionGeograficaId());
            } else if (vo.getUbicacionGeograficaTipo().equals(TipoUbicacionGeograficaEnum.PROVINCIA.getCodigo())) {
                sql.append(" AND o.ubicacionGeografica.ubicacionGeografica.idUbicacionGeografica=:idUbicacionGeografica");
                parametros.put("idUbicacionGeografica", vo.getUbicacionGeograficaId());
            } else if (vo.getUbicacionGeograficaTipo().equals(TipoUbicacionGeograficaEnum.CANTON.getCodigo())) {
                sql.append(" AND o.ubicacionGeografica.idUbicacionGeografica=:idUbicacionGeografica");
                parametros.put("idUbicacionGeografica", vo.getUbicacionGeograficaId());
            } else if (vo.getUbicacionGeograficaTipo().equals(TipoUbicacionGeograficaEnum.PARROQUIA.getCodigo())) {
                sql.append(" AND o.idUbicacionGeografica=:idUbicacionGeografica");
                parametros.put("idUbicacionGeografica", vo.getUbicacionGeograficaId());
            }
        }
        if (vo.getFechaInicio() != null && vo.getFechaFin() == null) {
            sql.append(" AND o.fechaInicio>=:fechaInicio");
            parametros.put("fechaInicio", UtilFechas.truncarFecha(vo.getFechaInicio()));
        } else if (vo.getFechaInicio() == null && vo.getFechaFin() != null) {
            sql.append(" AND o.fechaInicio<=:fechaFin");
            parametros.put("fechaFin", UtilFechas.truncarFecha(UtilFechas.sumarDias(vo.getFechaFin(), 1)));
        } else if (vo.getFechaInicio() != null && vo.getFechaFin() != null) {
            sql.append(" AND o.fechaInicio BETWEEN :fechaInicio AND :fechaFin");
            parametros.put("fechaInicio", UtilFechas.truncarFecha(vo.getFechaInicio()));
            parametros.put("fechaFin", UtilFechas.truncarFecha(UtilFechas.sumarDias(vo.getFechaFin(), 1)));
        }
        if (vo.getRmu() != null) {
            sql.append(" AND o.rmu=:rmu");
            parametros.put("rmu", vo.getRmu());
        }
        if (vo.getRmuSobrevalorado() != null) {
            sql.append(" AND o.rmuSobrevalorado=:rmuSobrevalorado");
            parametros.put("rmuSobrevalorado", vo.getRmuSobrevalorado());
        }
        if (vo.getSueldoBasico() != null) {
            sql.append(" AND o.sueldoBasico=:sueldoBasico");
            parametros.put("sueldoBasico", vo.getSueldoBasico());
        }
        if (vo.getRenovacionComisionServicio() != null) {
            if (vo.getRenovacionComisionServicio()) {
                sql.append(" AND o.servidorComisionServicio.id IS NOT NULL ");
                if (vo.getTipoDocumentoServidor() != null && !vo.getTipoDocumentoServidor().trim().isEmpty()) {
                    sql.append(" AND o.servidorComisionServicio.tipoIdentificacion=:tipoIdentificacion");
                    parametros.put("tipoIdentificacion", vo.getTipoDocumentoServidor());
                }
                if (vo.getNumeroDocumentoServidor() != null && !vo.getNumeroDocumentoServidor().trim().isEmpty()) {
                    sql.append(" AND o.servidorComisionServicio.numeroIdentificacion LIKE :numeroIdentificacion");
                    parametros.put("numeroIdentificacion", UtilCadena.concatenar("%", vo.getNumeroDocumentoServidor(), "%"));
                }
                if (vo.getNombreServidor() != null && !vo.getNombreServidor().trim().isEmpty()) {
                    sql.append(" AND o.servidorComisionServicio.apellidosNombres LIKE :apellidosNombres");
                    parametros.put("apellidosNombres", UtilCadena.concatenar("%", vo.getNombreServidor(), "%"));
                }
            } else {
                if (vo.getTipoDocumentoServidor() != null) {
                    sql.append(" AND o.servidor.tipoIdentificacion=:tipoIdentificacion");
                    parametros.put("tipoIdentificacion", vo.getTipoDocumentoServidor());
                }
                if (vo.getNumeroDocumentoServidor() != null && !vo.getNumeroDocumentoServidor().trim().isEmpty()) {
                    sql.append(" AND o.servidor.numeroIdentificacion LIKE :numeroIdentificacion");
                    parametros.put("numeroIdentificacion", UtilCadena.concatenar("%", vo.getNumeroDocumentoServidor(), "%"));
                }
                if (vo.getNombreServidor() != null && !vo.getNombreServidor().trim().isEmpty()) {
                    sql.append(" AND o.servidor.apellidosNombres LIKE :apellidosNombres");
                    parametros.put("apellidosNombres", UtilCadena.concatenar("%", vo.getNombreServidor(), "%"));
                }
            }
        } else {
            if (vo.getTipoDocumentoServidor() != null && !vo.getTipoDocumentoServidor().trim().isEmpty()) {
                sql.append(" AND o.servidor.tipoIdentificacion=:tipoIdentificacion");
                parametros.put("tipoIdentificacion", vo.getTipoDocumentoServidor());
            }
            if (vo.getNumeroDocumentoServidor() != null && !vo.getNumeroDocumentoServidor().trim().isEmpty()) {
                sql.append(" AND o.servidor.numeroIdentificacion LIKE :numeroIdentificacion");
                parametros.put("numeroIdentificacion", UtilCadena.concatenar("%", vo.getNumeroDocumentoServidor(), "%"));
            }
            if (vo.getNombreServidor() != null && !vo.getNombreServidor().trim().isEmpty()) {
                sql.append(" AND o.servidor.apellidosNombres LIKE :apellidosNombres");
                parametros.put("apellidosNombres", UtilCadena.concatenar("%", vo.getNombreServidor(), "%"));
            }
        }
        if (vo.getEstadoPuestoId() != null) {
            sql.append(" AND o.idEstadoPuesto=:idEstadoPuesto");
            parametros.put("idEstadoPuesto", vo.getEstadoPuestoId());
        }
        if (vo.getEstadoPuestoCodigo() != null) {
            sql.append(" AND o.estadoPuesto.codigo=:codigoEstadoPuesto");
            parametros.put("codigoEstadoPuesto", vo.getEstadoPuestoCodigo());
        }
        if (vo.getEstadoAdmPuestoRegLabId() != null) {
            sql.append(" AND o.estadosAdmPuestosRegimenLaboralId=:estadosAdmPuestosRegimenLaboralId");
            parametros.put("estadosAdmPuestosRegimenLaboralId", vo.getEstadoAdmPuestoRegLabId());
        }
        if (vo.getPuestoVacante() != null) {
            if (vo.getPuestoVacante()) {
                sql.append(" AND o.servidor is null");
            } else {
                sql.append(" AND o.servidor is not null");
            }
        }
        if (vo.getEstadoServidorId() != null) {
            sql.append(" AND o.servidor.estadoPersonalId=:estadoPersonalId");
            parametros.put("estadoPersonalId", vo.getEstadoServidorId());
        }
        if (vo.getEstadoServidorCodigo() != null) {
            sql.append(" AND o.servidor.estadoPersonal.codigo=:estadoPersonalCodigo");
            parametros.put("estadoPersonalCodigo", vo.getEstadoServidorCodigo());
        }
        if (vo.getObligadoTimbrar() != null && vo.getObligadoTimbrar()) {
            sql.append(" AND o.escalaOcupacional.marcacionObligatoria=:esMarcacionObligatoria");
            parametros.put("esMarcacionObligatoria", vo.getObligadoTimbrar());
        }
        // filtrar de acuerdo al estado actual del puesto
        if (!especial) {
            sql.append(" AND (o.estadosAdmPuestosRegimenLaboralId IS NULL OR o.estadosAdmPuestosRegimenLaboralId IN "
                    + " (SELECT DISTINCT estado.id FROM EstadoAdministracionPuestoRegimenLaboral estado "
                    + " WHERE estado.estadoAdministracionPuesto.activo=true AND estado.regimenLaboral.id="
                    + " o.escalaOcupacional.nivelOcupacional.idRegimenLaboral ))");
        }

        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        //paginacion.
        if (vo.getInicioConsulta() != null && vo.getFinConsulta() != null) {
            query.setFirstResult(vo.getInicioConsulta());
            query.setMaxResults(vo.getFinConsulta());
        }
//        System.out.println(obtenerSql(query, parametros));
        return query.getResultList();
    }

    /**
     * buscar servidores por parametros.
     *
     * @param vo
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscarServidor(final BusquedaServidorVO vo) throws DaoException {

        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        String parametro = null;

        sql.append("SELECT o FROM DistributivoDetalle o ");
        sql.append(" WHERE o.vigente=true ");
        if (vo.getIntitucionEjercicioFiscalId() != null) {
            sql.append(" AND o.distributivo.institucionEjercicioFiscal.id=:institucionEjercicioFiscalId ");
            parametros.put("institucionEjercicioFiscalId", vo.getIntitucionEjercicioFiscalId());
        }
        if (vo.getPartidaIndividual() != null && !vo.getPartidaIndividual().trim().isEmpty()) {
            sql.append(" AND o.partidaIndividual=:partidaIndividual");
            parametros.put("partidaIndividual", vo.getPartidaIndividual());
        }
        if (vo.getConClave() != null) {
            if (vo.getConClave()) {
                sql.append(" AND o.servidor.clave IS NOT NULL");
            } else {
                sql.append(" AND o.servidor.clave IS NULL");
            }
        }
        if (vo.getUnidadAdministrativaId() != null) {
            sql.append(" AND o.distributivo.unidadOrganizacional.id="
                    + ":idUnidadOrganizacional");
            parametros.put("idUnidadOrganizacional", vo.getUnidadAdministrativaId());
        }
        //si el usuario es de RRHH puede ver de todo el MDQ
        if (vo.getCodUnidadAdministrativa() != null) {
            if (vo.getIdInstitucion() != null) {
                parametro = parametroInstitucionalDao.buscarPorInstitucionYNemonico(vo.getIdInstitucion(),
                        ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo()).getValorTexto();
//                System.out.println(" codigo de rrhh: " + parametro + " cod de la unidad:"
//                        + vo.getCodUnidadAdministrativa() + " institucion:" + vo.
//                        getIdInstitucion());
                String[] unidades = parametro.trim().split(";");
                boolean existe = false;
                for (String unidad : unidades) {
                    if (unidad.equals(vo.getCodUnidadAdministrativa())) {
                        existe = true;
                        break;
                    }
                }
                if (!existe) {
                    sql.append(" AND o.distributivo.unidadOrganizacional.codigo =:codUnidadOrganizacional ");
                    parametros.put("codUnidadOrganizacional", vo.getCodUnidadAdministrativa());
                }
            }
        }
        if (vo.getUbicacionGeograficaId() != null && vo.getUbicacionGeograficaTipo() != null) {
            if (vo.getUbicacionGeograficaTipo().equals(TipoUbicacionGeograficaEnum.PAIS.getCodigo())) {
                sql.append(" AND o.ubicacionGeografica.ubicacionGeografica.ubicacionGeografica.idUbicacionGeografica"
                        + " =:idUbicacionGeografica");
                parametros.put("idUbicacionGeografica", vo.getUbicacionGeograficaId());
            } else if (vo.getUbicacionGeograficaTipo().equals(TipoUbicacionGeograficaEnum.PROVINCIA.getCodigo())) {
                sql.append(" AND o.ubicacionGeografica.ubicacionGeografica.idUbicacionGeografica=:idUbicacionGeografica");
                parametros.put("idUbicacionGeografica", vo.getUbicacionGeograficaId());
            } else if (vo.getUbicacionGeograficaTipo().equals(TipoUbicacionGeograficaEnum.CANTON.getCodigo())) {
                sql.append(" AND o.ubicacionGeografica.idUbicacionGeografica=:idUbicacionGeografica");
                parametros.put("idUbicacionGeografica", vo.getUbicacionGeograficaId());
            } else if (vo.getUbicacionGeograficaTipo().equals(TipoUbicacionGeograficaEnum.PARROQUIA.getCodigo())) {
                sql.append(" AND o.idUbicacionGeografica=:idUbicacionGeografica");
                parametros.put("idUbicacionGeografica", vo.getUbicacionGeograficaId());
            }
        }
        if (vo.getPuestoVacante() != null) {
            if (vo.getPuestoVacante()) {
                sql.append(" AND o.servidor is null");
            } else {
                sql.append(" AND o.servidor is not null");
            }
        }
        if (vo.getFechaInicio() != null && vo.getFechaFin() == null) {
            sql.append(" AND o.fechaInicio>=:fechaInicio");
            parametros.put("fechaInicio", UtilFechas.truncarFecha(vo.getFechaInicio()));
        } else if (vo.getFechaInicio() == null && vo.getFechaFin() != null) {
            sql.append(" AND o.fechaInicio<=:fechaFin");
            parametros.put("fechaFin", UtilFechas.truncarFecha(UtilFechas.sumarDias(vo.getFechaFin(), 1)));
        } else if (vo.getFechaInicio() != null && vo.getFechaFin() != null) {
            sql.append(" AND o.fechaInicio BETWEEN :fechaInicio AND :fechaFin");
            parametros.put("fechaInicio", UtilFechas.truncarFecha(vo.getFechaInicio()));
            parametros.put("fechaFin", UtilFechas.truncarFecha(UtilFechas.sumarDias(vo.getFechaFin(), 1)));
        }

        if (vo.getTipoDocumentoServidor() != null) {
            sql.append(" AND o.servidor.tipoIdentificacion=:tipoIdentificacion");
            parametros.put("tipoIdentificacion", vo.getTipoDocumentoServidor());
        }
        if (vo.getNumeroDocumentoServidor() != null && !vo.getNumeroDocumentoServidor().trim().isEmpty()) {
            sql.append(" AND o.servidor.numeroIdentificacion LIKE :numeroIdentificacion");
            parametros.put("numeroIdentificacion", UtilCadena.concatenar("%", vo.
                    getNumeroDocumentoServidor(), "%"));
        }
        if (vo.getNombreServidor() != null && !vo.getNombreServidor().trim().isEmpty()) {
            sql.append(" AND o.servidor.apellidosNombres LIKE :apellidosNombres");
            parametros.put("apellidosNombres", UtilCadena.concatenar("%", vo.
                    getNombreServidor(), "%"));
        }
        if (vo.getCatalogoEstadoCivil() != null) {
            sql.append(" AND o.servidor.catalogoEstadoCivilId=:catalogoEstadoCivilId");
            parametros.put("catalogoEstadoCivilId", vo.getCatalogoEstadoCivil());
        }
        if (vo.getCatalogoGeneroId() != null) {
            sql.append(" AND o.servidor.catalogoGeneroId=:catalogoGeneroId");
            parametros.put("catalogoGeneroId", vo.getCatalogoGeneroId());
        }
        if (vo.getEstadoServidorId() != null) {
            sql.append(" AND o.servidor.estadoPersonalId=:estadoPersonalId");
            parametros.put("estadoPersonalId", vo.getEstadoServidorId());
        }
        if (vo.getConHorarioAsignado() != null && vo.getConHorarioAsignado()) {
            sql.append(" AND o.servidor.horario IS NOT NULL");
        } else if (vo.getConHorarioAsignado() == null || !vo.getConHorarioAsignado()) {
            sql.append(" AND o.servidor.horario IS NULL");
        }
        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        return query.getResultList();
    }

    /**
     *
     */
    /**
     * buscar servidores por parametros.
     *
     * @param vo
     * @param firstRow
     * @param numberOfRows
     * @param orderField
     * @param orderDirection
     * @param filters
     * @return
     * @throws DaoException
     */
    public List<Servidor> buscarServidoresP(final BusquedaServidorVO vo, Integer firstRow,
            Integer numberOfRows, String orderField, String orderDirection,
            Map<String, String> filters) throws DaoException {

        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s FROM Servidor s LEFT JOIN s.listaDistributivoDetalle o ");
        sql.append(" WHERE (o.id is null or o.vigente=true) ");

        if (vo.getIntitucionEjercicioFiscalId() != null) {
            sql.append(" AND o.distributivo.institucionEjercicioFiscal.id=:institucionEjercicioFiscalId ");
            parametros.put("institucionEjercicioFiscalId", vo.getIntitucionEjercicioFiscalId());
        }
        if (vo.getUnidadAdministrativaId() != null) {
            sql.append(" AND o.distributivo.unidadOrganizacional.id="
                    + ":idUnidadOrganizacional");
            parametros.put("idUnidadOrganizacional", vo.getUnidadAdministrativaId());
        }
        if (vo.getCodigoPuesto() != null) {
            sql.append(" AND o.codigoPuesto="
                    + ":codigoPuesto");
            parametros.put("codigoPuesto", vo.getCodigoPuesto());
        }
        if (vo.getUbicacionGeograficaId() != null && vo.getUbicacionGeograficaTipo() != null) {
            if (vo.getUbicacionGeograficaTipo().equals(TipoUbicacionGeograficaEnum.PAIS.getCodigo())) {
                sql.append(" AND o.ubicacionGeografica.ubicacionGeografica.ubicacionGeografica.idUbicacionGeografica"
                        + " =:idUbicacionGeografica");
                parametros.put("idUbicacionGeografica", vo.getUbicacionGeograficaId());
            } else if (vo.getUbicacionGeograficaTipo().equals(TipoUbicacionGeograficaEnum.PROVINCIA.getCodigo())) {
                sql.append(" "
                        + "AND o.ubicacionGeografica.ubicacionGeografica.idUbicacionGeografica=:idUbicacionGeografica");
                parametros.put("idUbicacionGeografica", vo.getUbicacionGeograficaId());
            } else if (vo.getUbicacionGeograficaTipo().equals(TipoUbicacionGeograficaEnum.CANTON.getCodigo())) {
                sql.append(" AND o.ubicacionGeografica.idUbicacionGeografica=:idUbicacionGeografica");
                parametros.put("idUbicacionGeografica", vo.getUbicacionGeograficaId());
            } else if (vo.getUbicacionGeograficaTipo().equals(TipoUbicacionGeograficaEnum.PARROQUIA.getCodigo())) {
                sql.append(" AND o.idUbicacionGeografica=:idUbicacionGeografica");
                parametros.put("idUbicacionGeografica", vo.getUbicacionGeograficaId());
            }
        }
        if (vo.getFechaInicio() != null && vo.getFechaFin() == null) {
            sql.append(" AND s.fechaNacimiento>=:fechaInicio");
            parametros.put("fechaInicio", UtilFechas.truncarFecha(vo.getFechaInicio()));
        } else if (vo.getFechaInicio() == null && vo.getFechaFin() != null) {
            sql.append(" AND s.fechaNacimiento<=:fechaFin");
            parametros.put("fechaFin", UtilFechas.truncarFecha(UtilFechas.sumarDias(vo.getFechaFin(), 1)));
        } else if (vo.getFechaInicio() != null && vo.getFechaFin() != null) {
            sql.append(" AND s.fechaNacimiento BETWEEN :fechaInicio AND :fechaFin");
            parametros.put("fechaInicio", UtilFechas.truncarFecha(vo.getFechaInicio()));
            parametros.put("fechaFin", UtilFechas.truncarFecha(UtilFechas.sumarDias(vo.getFechaFin(), 1)));
        }
        /**
         * fecaha de ingreso a la institución
         */
        if (vo.getFechaInicioIns() != null && vo.getFechaFinIns() == null) {
            sql.append(" AND s.fechaIngresoInstitucion>=:fechaInicio");
            parametros.put("fechaInicio", UtilFechas.truncarFecha(vo.getFechaInicioIns()));
        } else if (vo.getFechaInicio() == null && vo.getFechaFin() != null) {
            sql.append(" AND s.fechaIngresoInstitucion<=:fechaFin");
            parametros.put("fechaFin", UtilFechas.truncarFecha(UtilFechas.sumarDias(vo.getFechaFinIns(), 1)));
        } else if (vo.getFechaInicio() != null && vo.getFechaFin() != null) {
            sql.append(" AND s.fechaIngresoInstitucion BETWEEN :fechaInicio AND :fechaFin");
            parametros.put("fechaInicio", UtilFechas.truncarFecha(vo.getFechaInicioIns()));
            parametros.put("fechaFin", UtilFechas.truncarFecha(UtilFechas.sumarDias(vo.getFechaFinIns(), 1)));
        }

        if (vo.getTipoDocumentoServidor() != null) {
            sql.append(" AND s.tipoIdentificacion=:tipoIdentificacion");
            parametros.put("tipoIdentificacion", vo.getTipoDocumentoServidor());
        }
        if (vo.getNumeroDocumentoServidor() != null && !vo.getNumeroDocumentoServidor().trim().isEmpty()) {
            sql.append(" AND s.numeroIdentificacion LIKE :numeroIdentificacion");
            parametros.put("numeroIdentificacion", UtilCadena.concatenar("%", vo.
                    getNumeroDocumentoServidor(), "%"));
        }
        if (vo.getNombreServidor() != null && !vo.getNombreServidor().trim().isEmpty()) {
            sql.append(" AND s.apellidosNombres LIKE :apellidosNombres");
            parametros.put("apellidosNombres", UtilCadena.concatenar("%", vo.
                    getNombreServidor(), "%"));
        }
        if (vo.getCatalogoEstadoCivil() != null) {
            sql.append(" AND s.catalogoEstadoCivilId=:catalogoEstadoCivilId");
            parametros.put("catalogoEstadoCivilId", vo.getCatalogoEstadoCivil());
        }
        if (vo.getCatalogoGeneroId() != null) {
            sql.append(" AND s.catalogoGeneroId=:catalogoGeneroId");
            parametros.put("catalogoGeneroId", vo.getCatalogoGeneroId());
        }
        if (vo.getEstadoServidorId() != null) {
            sql.append(" AND s.estadoPersonalId=:estadoPersonalId");
            parametros.put("estadoPersonalId", vo.getEstadoServidorId());
        }
        sql.append(" ORDER BY TRIM(BOTH FROM s.apellidosNombres) ");
        Query query = getEntityManager().createQuery(sql.toString());
        if (firstRow != null) {
            query.setFirstResult(firstRow);
        }
        if (numberOfRows != null) {
            query.setMaxResults(numberOfRows);
        }
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        return query.getResultList();
    }

    /**
     * construir el conteo.
     *
     * @param vo
     * @param filters
     * @return
     */
    public int contarServidores(BusquedaServidorVO vo,
            Map<String, String> filters) {
        Query q = getEntityManager().createNativeQuery(
                buildConsultaConteoServidores(vo).toString());

        q.setParameter(1, vo.getUnidadAdministrativaId());
        q.setParameter(2, vo.getUbicacionGeograficaId());
        q.setParameter(3, vo.getFechaInicio());
        q.setParameter(4, vo.getFechaFin());
        q.setParameter(5, vo.getTipoDocumentoServidor());
        q.setParameter(6, UtilCadena.concatenar("'%", vo.getNumeroDocumentoServidor(), "%'"));
        q.setParameter(7, UtilCadena.concatenar("'%", vo.getNombreServidor(), "%'").toUpperCase());
        q.setParameter(8, vo.getCatalogoEstadoCivil());
        q.setParameter(9, vo.getCatalogoGeneroId());
        q.setParameter(10, vo.getEstadoServidorId());
        q.setParameter(11, vo.getCodigoPuesto());
        q.setParameter(12, vo.getFechaInicioIns());
        q.setParameter(13, vo.getFechaFinIns());
        return Integer.parseInt(q.getSingleResult().toString());

    }

    /**
     * metodo para contar los servidores.
     *
     * @param filters
     * @param usuarioLogueado
     * @return
     */
    private StringBuilder buildConsultaConteoServidores(final BusquedaServidorVO vo) {
        StringBuilder consulta = new StringBuilder();
        consulta.append(" SELECT ");
        consulta.append(" count(DISTINCT s.id) ");
        consulta.append(" FROM ");
        consulta.append(" sch_proteus.servidor s ");
        consulta.append(" left join ");
        consulta.append(" sch_proteus.distributivo_detalles o ");
        consulta.append(" on s.id = o.servidor_id ");
        consulta.append(" left JOIN ");
        consulta.append(" sch_proteus.distributivo d ");
        consulta.append(" ON d.id = o.distributivo_id ");
        consulta.append(" where ");
        consulta.append(" (o.id is null or (o.vigente=1 and d.id is not null  )) ");
        if (vo.getUnidadAdministrativaId() != null) {
            consulta.append(" and d.unidad_organizacional_id=?1 ");
        }
        if (vo.getUbicacionGeograficaId() != null) {
            consulta.append(" and o.ubicacion_geografica_id=?2 ");
        }

        if (vo.getFechaInicio() != null && vo.getFechaFin() == null) {
            consulta.append(" and s.fecha_nacimiento>=?3 ");
        } else if (vo.getFechaInicio() == null && vo.getFechaFin() != null) {
            consulta.append(" and s.fecha_nacimiento<=?4 ");
        } else if (vo.getFechaInicio() != null && vo.getFechaFin() != null) {
            consulta.append(" and s.fecha_nacimiento BETWEEN ?3 and ?4 ");
        }
        if (vo.getTipoDocumentoServidor() != null) {
            consulta.append(" and s.tipo_identificacion=?5 ");
        }
        if (vo.getNumeroDocumentoServidor() != null && !vo.getNumeroDocumentoServidor().trim().isEmpty()) {
            consulta.append(" and s.numero_identificacion like ");
            consulta.append(UtilCadena.concatenar("'%", vo.getNumeroDocumentoServidor(), "%'"));
        }
        if (vo.getNombreServidor() != null && !vo.getNombreServidor().trim().isEmpty()) {
            consulta.append(" and s.apellidos_nombres like ");
            consulta.append(UtilCadena.concatenar("'%", vo.getNombreServidor(), "%'"));
        }
        if (vo.getCatalogoEstadoCivil() != null) {
            consulta.append(" and s.catalogo_estado_civil_id=?8 ");
        }
        if (vo.getCatalogoGeneroId() != null) {
            consulta.append(" and s.catalogo_genero_id=?9 ");
        }
        if (vo.getEstadoServidorId() != null) {
            consulta.append(" and s.estado_personal_id=?10 ");
        }
        if (vo.getCodigoPuesto() != null) {
            consulta.append(" and o.codigo_puesto=?11 ");
        }

        if (vo.getFechaInicioIns() != null && vo.getFechaFinIns() == null) {
            consulta.append(" and s.fecha_ingreso_institucion>=?12 ");
        } else if (vo.getFechaInicioIns() == null && vo.getFechaFinIns() != null) {
            consulta.append(" and s.fecha_ingreso_institucion<=?13 ");
        } else if (vo.getFechaInicioIns() != null && vo.getFechaFinIns() != null) {
            consulta.append(" and s.fecha_ingreso_institucion BETWEEN ?12 and ?13 ");
        }
        return consulta;
    }

    /**
     * Este metodo procesa la busqueda de todo por id del servidor.
     *
     * @param idServidor Id servidor
     * @return List
     * @throws DaoException DaoException
     */
    public List<DistributivoDetalle> buscarPorServidor(final Long idServidor) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DistributivoDetalle.BUSCAR_POR_SERVIDOR, idServidor);
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nombres del servidor e institucionEjeccicioFiscal.
     *
     * @param nombres
     * @param institucionEjercicioFiscalId
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscarPorServidorNombres(final String nombres,
            final Long institucionEjercicioFiscalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DistributivoDetalle.BUSCAR_POR_NOMBRES, UtilCadena.concatenar("%", nombres,
                    "%"), institucionEjercicioFiscalId);
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de distributivoDetalle que esten vigentes true.
     *
     * @return Listado DistributivoDetalle
     * @throws DaoException En caso de error
     */
    public List<DistributivoDetalle> buscarVigente() throws DaoException {
        try {
            List<DistributivoDetalle> lista = buscarPorConsultaNombrada(DistributivoDetalle.BUSCAR_VIGENTES);
            return lista;
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de distributivoDetalle que esten vigentes true.
     *
     * @param idDistributivo
     * @return Listado DistributivoDetalle
     * @throws DaoException En caso de error
     */
    public List<DistributivoDetalle> buscarPorDistributivo(Long idDistributivo) throws DaoException {
        try {
            List<DistributivoDetalle> lista = buscarPorConsultaNombrada(DistributivoDetalle.BUSCAR_POR_DISTRIBUTIVO,
                    idDistributivo);
            return lista;
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param unidadOrganizacionalId
     * @param modalidadLaboralId
     * @return
     * @throws DaoException
     */
    public Long contarDetallesporDistributivo(final Long unidadOrganizacionalId, final Long modalidadLaboralId)
            throws DaoException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(a.id) FROM DistributivoDetalle a where a.vigente=true ");
        sql.append("AND a.distributivo.unidadOrganizacional.id = :unidadOrganizacionalId and "
                + "a.distributivo.unidadOrganizacional.id = :unidadOrganizacionalId ");
        sql.append("AND a.distributivo.modalidadLaboral.id = :modalidadLaboralId ");
        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("unidadOrganizacionalId", unidadOrganizacionalId);
        createQuery.setParameter("modalidadLaboralId", modalidadLaboralId);
        Object obj = createQuery.getSingleResult();
        return obj != null ? (Long) obj : 0L;
    }

    /**
     *
     * @param codigo
     * @param institucionEjercicioFiscalId
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscarPorCodigo(final Long codigo, final Long institucionEjercicioFiscalId)
            throws DaoException {
        return buscarPorConsultaNombrada(DistributivoDetalle.BUSCAR_POR_CODIGO, codigo, institucionEjercicioFiscalId);
    }

    /**
     * RECUPERA EL PUESTO VACANE DADO SU CODIGO
     *
     * @param codigo
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscarVacantePorCodigo(final Long codigo) throws
            DaoException {
        return buscarPorConsultaNombrada(DistributivoDetalle.BUSCAR_VACANTE_POR_CODIGO, codigo);
    }

    /**
     *
     * @param codigoUnidadOrganizacional
     * @return
     */
    public List<DistributivoDetalle> buscar(final String codigoUnidadOrganizacional) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM DistributivoDetalle o ");
        sql.append(" WHERE o.distributivo.unidadOrganizacional.codigo = '").append(codigoUnidadOrganizacional).append(
                "'");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }

    /**
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param idInstitucionEjercicioFiscal
     * @return
     * @throws DaoException
     */
    public DistributivoDetalle buscarPorServidor(final String tipoIdentificacion, final String numeroIdentificacion,
            final Long idInstitucionEjercicioFiscal) throws DaoException {
        List<DistributivoDetalle> lista = buscarPorConsultaNombrada(DistributivoDetalle.BUSCAR_SERVIDOR,
                tipoIdentificacion, numeroIdentificacion, idInstitucionEjercicioFiscal, Boolean.TRUE);

        DistributivoDetalle distributivoDetalle = null;

        if (!lista.isEmpty()) {
            distributivoDetalle = lista.get(0);
        }

        return distributivoDetalle;

    }

    /**
     * Buscar servidores
     *
     * @param numeroIdentificacion
     * @param nombres
     * @param codigoUnidadOrganizacional
     * @return
     */
    public List<DistributivoDetalle> buscar(
            final String numeroIdentificacion, final String nombres, final String codigoUnidadOrganizacional) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM DistributivoDetalle o WHERE o.vigente=true ");
        if (numeroIdentificacion != null && !numeroIdentificacion.trim().isEmpty()) {
            sql.append(" AND o.servidor.numeroIdentificacion=:numeroIdentificacion");
            parametros.put("numeroIdentificacion", numeroIdentificacion);
        }
        if (nombres != null && !nombres.trim().isEmpty()) {
            sql.append(" AND  o.servidor.apellidosNombres LIKE :nombres");
            parametros.put("nombres", nombres);
        }
        if (codigoUnidadOrganizacional != null && !codigoUnidadOrganizacional.trim().isEmpty()) {
            sql.append(" AND o.distributivo.unidadOrganizacional.codigo=:codigoUnidadOrganizacional ");
            parametros.put("codigoUnidadOrganizacional", codigoUnidadOrganizacional);
        }
        Query query = getEntityManager().createQuery(sql.toString());
//        query.setFirstResult(0);
//        query.setMaxResults(100);
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        return query.getResultList();
    }

    /**
     *
     * @param codigoUnidadOrganizacional
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscarPorUnidadOrganizacional(final String codigoUnidadOrganizacional) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(DistributivoDetalle.BUSCAR_POR_UNIDAD_ORGANIZACIONAL,
                    codigoUnidadOrganizacional);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param codigoUnidadOrganizacional
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscarPorUnidadOrganizacionalServidor(final String codigoUnidadOrganizacional,
            final Long servidorId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DistributivoDetalle.BUSCAR_POR_UNIDAD_ORGANIZACIONAL_SERVIDOR,
                    codigoUnidadOrganizacional, servidorId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param codigo
     * @return
     * @throws DaoException
     */
    public DistributivoDetalle buscarServidorPorCodigo(final Long codigo) throws DaoException {
        try {
            DistributivoDetalle entidad = null;
            List<DistributivoDetalle> lista = buscarPorConsultaNombrada(
                    DistributivoDetalle.BUSCAR_SERVIDOR_POR_CODIGO, codigo);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    /**
     * Crear una copia identica del distributivo detalles, en una fecha concreta.
     *
     * @param nombreTabla
     * @throws java.sql.SQLException
     */
    public void crearInsertarCopiaMensualDistributivo(final String nombreTabla) throws SQLException {
        StringBuilder sql = new StringBuilder(" SELECT * INTO sch_proteus.");
        sql.append(nombreTabla.toLowerCase());
        sql.append(" FROM sch_proteus.distributivo_detalles"
                + " WHERE vigente = 1 ");
        Query query = getEntityManager().createNativeQuery(sql.toString());
        query.executeUpdate();
        LOG.log(Level.INFO, " nombre tabla creada:{0}", nombreTabla);
    }

    /**
     *
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscarPorServidorComisionServicio(Long servidorId) throws DaoException {
        return buscarPorConsultaNombrada(DistributivoDetalle.BUSCAR_POR_SERVIDOR_COMISION_SERVICIO, servidorId);
    }

    /**
     *
     * @param institucionEjercicioFiscalId
     * @param unidadOrganizacionalId
     * @param modalidadLaboralId
     * @return
     */
    public List<DistributivoDetalle> buscarPorUnidadOrganizacionalYModalidadLaboral(
            final Long institucionEjercicioFiscalId, final Long unidadOrganizacionalId,
            final Long modalidadLaboralId) throws DaoException {
        return buscarPorConsultaNombrada(DistributivoDetalle.BUSCAR_POR_UO_Y_ML, institucionEjercicioFiscalId,
                unidadOrganizacionalId, modalidadLaboralId);

    }

    /**
     *
     * @param regimenLaboralId
     * @param iefId
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscarParaVacaciones(Long regimenLaboralId, Long institucionEjercicioFiscalId,
            int inicio, int longitud) throws DaoException {
        return buscarPorConsultaNombradaPaginacion(DistributivoDetalle.BUSCAR_PARA_VACACIONES,
                inicio, longitud, regimenLaboralId, institucionEjercicioFiscalId);

    }

    /**
     * Buscar por codigos internos
     *
     * @param codigos
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscarPorCodigos(final String codigos) throws DaoException {
        try {
            StringBuilder sql = new StringBuilder(" SELECT o FROM DistributivoDetalle o WHERE o.vigente=true ");
            sql.append(" AND o.codigoPuesto in ( ");
            sql.append(codigos);
            sql.append(" ) ORDER BY o.distributivo.unidadOrganizacional.nombre, ");
            sql.append(" o.distributivo.modalidadLaboral.nombre, o.unidadPresupuestaria.nombre, o.denominacionPuesto.nombre ");
            Query query = getEntityManager().createQuery(sql.toString(), DistributivoDetalle.class);
            return query.getResultList();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, " Error buscando los puestos por codigo ", e);
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param fechaInicio
     * @param fechaFina
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscarFinalizacionesDelMes(Date fechaInicio, Date fechaFinal, Integer inicio,
            Integer longitud) throws DaoException {
        try {
            return buscarPorConsultaNombradaPaginacion(DistributivoDetalle.BUSCAR_FINALIZACIONES_MES, inicio, longitud,
                    fechaInicio, fechaFinal);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param numeroIdentificacion
     * @return
     * @throws DaoException
     */
    public DistributivoDetalle buscarPorIdentificacion(String numeroIdentificacion) throws DaoException {
        try {
            DistributivoDetalle entidad = null;
            List<DistributivoDetalle> lista = buscarPorConsultaNombrada(
                    DistributivoDetalle.BUSCAR_POR_IDENTIFICACION, numeroIdentificacion);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Lista de DistributivoDetalle perteneciente a un InstitucionEjercicioFiscal
     *
     * @param institucionEjercicioFiscalId
     * @param inicio
     * @param longitud
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalle> buscarPorInstitucionEjercicioFiscal(
            final Long institucionEjercicioFiscalId, final int inicio, final int longitud)
            throws DaoException {
        try {
            List<DistributivoDetalle> lista
                    = buscarPorConsultaNombradaPaginacion(
                            DistributivoDetalle.BUSCAR_POR_INSTITUCION_EJECICIO_FISCAL, inicio, longitud, institucionEjercicioFiscalId);
            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
