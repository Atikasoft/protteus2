/*
 *  VacacionDao.java
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
 *  29/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.enums.TipoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.vo.RegistroReporteSaldosVacacionesVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class VacacionDao extends GenericDAO<Vacacion, Long> {

    /**
     *
     */
    @EJB
    private VacacionDetalleDao vacacionDetalleDao;

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VacacionDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public VacacionDao() {
        super(Vacacion.class);
    }

    /**
     * BUSCA POR EL ID DEL REGISTRO SERVIDORINSTITUCION
     *
     * @param servidorInstitucionId identificador del servidor en la institucion
     * @return datos de la vacacion del servidor.
     * @throws DaoException error de acceso a datos
     */
    public Vacacion buscarPorServidor(Long servidorInstitucionId) throws DaoException {
        Vacacion entidad = null;
        List<Vacacion> lista = buscarPorConsultaNombrada(
                Vacacion.BUSCAR_POR_SERVIDORINSTITUCIONID, servidorInstitucionId);
        if (!lista.isEmpty()) {
            entidad = lista.get(0);
        }
        return entidad;
    }

    /**
     * BUSCA POR EL ID DEL SERVIDOR
     *
     * @param servidorId identificador del servidor
     * @return datos de vacaciones del servidor
     * @throws DaoException error en el acceso a datos
     */
    public Vacacion buscarPorServidorId(Long servidorId) throws DaoException {
        Vacacion entidad = null;
        List<Vacacion> lista = buscarPorConsultaNombrada(Vacacion.BUSCAR_POR_SERVIDOR_ID, servidorId);
        if (!lista.isEmpty()) {
            entidad = lista.get(0);
        }
        return entidad;
    }

    /**
     * Totaliza saldo de vavaciones de un servidor
     *
     * @param servidorInstitucionId id del registro InstitucionId que contiene el id del servidor al que se le quiere
     * totalizar el saldo
     * @throws DaoException error en el acceso a datos
     */
    public void totalizarSaldosVacaciones(Long servidorInstitucionId) throws DaoException {
        try {
            //  totalizar vacaciones.
            StringBuilder update = new StringBuilder();
            update.append("update v ");
            update.append("set v.saldo=f.saldo ");
            update.append("from sch_proteus.vacaciones v  ");
            update.append("inner join ");
            update.append("( ");
            update.append("select vd.vacacion_id as vacacion_id , ")
                    .append("sum(case when vd.tipo='V' then vd.credito else 0 end) - ")
                    .append("sum(case when vd.tipo='V' then vd.debito else 0 end) as saldo ");
            update.append("from sch_proteus.vacaciones_detalle vd ");
            update.append("where vd.vigente=1 and vd.vacacion_id ");
            update.append("in (select id from sch_proteus.vacaciones where servidor_institucion_id=").
                    append(servidorInstitucionId).
                    append(")");
            update.append("group by vd.vacacion_id ");
            update.append(") f on v.id = f.vacacion_id ");
            Query query = getEntityManager().createNativeQuery(update.toString());
            query.executeUpdate();

            // totalizar proporcionales
            StringBuilder update2 = new StringBuilder();
            update2.append("update v ");
            update2.append("set v.saldo_proporcional=f.saldo ");
            update2.append("from sch_proteus.vacaciones v ");
            update2.append("inner join ");
            update2.append("( ");
            update2.append("select vd.vacacion_id as vacacion_id ,")
                    .append("sum(case when vd.tipo='P' then vd.credito else 0 end) - ")
                    .append("sum(case when vd.tipo='P' then vd.debito else 0 end) as saldo ");
            update2.append("from sch_proteus.vacaciones_detalle vd ");
            update2.append("where vd.vigente=1 and vd.vacacion_id ");
            update2.append("in (select id from sch_proteus.vacaciones where servidor_institucion_id=").
                    append(servidorInstitucionId).
                    append(")");
            update2.append("group by vd.vacacion_id ");
            update2.append(") f on v.id = f.vacacion_id ");
            Query query2 = getEntityManager().createNativeQuery(update2.toString());
            query2.executeUpdate();

            // totalizar perdidas
            StringBuilder update3 = new StringBuilder();
            update3.append("update v ");
            update3.append("set v.saldo_perdida=f.saldo ");
            update3.append("from sch_proteus.vacaciones v ");
            update3.append("inner join ");
            update3.append("( ");
            update3.append("select vd.vacacion_id as vacacion_id ,")
                    .append("sum(case when vd.tipo='E' then vd.credito else 0 end) - ")
                    .append("sum(case when vd.tipo='E' then vd.debito else 0 end) as saldo ");
            update3.append("from sch_proteus.vacaciones_detalle vd ");
            update3.append("where vd.vigente=1 and vd.vacacion_id ");
            update3.append("in (select id from sch_proteus.vacaciones where servidor_institucion_id=").
                    append(servidorInstitucionId).
                    append(")");
            update3.append("group by vd.vacacion_id ");
            update3.append(") f on v.id = f.vacacion_id ");
            Query query3 = getEntityManager().createNativeQuery(update3.toString());
            query3.executeUpdate();

        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    /**
     * Totaliza los saldos de todoas las vacaciones.
     *
     * @throws DaoException error en el acceso a datos
     */
    public void totalizarSaldosVacaciones() throws DaoException {
        try {
            //  totalizar vacaciones.
            StringBuilder update = new StringBuilder();
            update.append("update v ");
            update.append("set v.saldo=f.saldo ");
            update.append("from sch_proteus.vacaciones v ");
            update.append("inner join ");
            update.append("( ");
            update.append("select vd.vacacion_id as vacacion_id , ");
            update.append("sum(case when vd.tipo='V' then vd.credito else 0 end)-sum(case when vd.tipo='V' then "
                    + "vd.debito else 0 end) as saldo ");
            update.append("from sch_proteus.vacaciones_detalle vd ");
            update.append("where vd.vigente=1");
            update.append("group by vd.vacacion_id ");
            update.append(") f on v.id = f.vacacion_id ");
            Query query = getEntityManager().createNativeQuery(update.toString());
            query.executeUpdate();

            // totalizar proporcionales
            StringBuilder update2 = new StringBuilder();
            update2.append("update v ");
            update2.append("set v.saldo_proporcional=f.saldo ");
            update2.append("from sch_proteus.vacaciones v ");
            update2.append("inner join ");
            update2.append("( ");
            update2.append("select vd.vacacion_id as vacacion_id , ");
            update2.append("sum(case when vd.tipo='P' then vd.credito else 0 end)-sum(case when vd.tipo='P' then "
                    + "vd.debito else 0 end) as saldo ");
            update2.append("from sch_proteus.vacaciones_detalle vd ");
            update2.append("where vd.vigente=1 ");
            update2.append("group by vd.vacacion_id ");
            update2.append(") f on v.id = f.vacacion_id ");
            Query query2 = getEntityManager().createNativeQuery(update2.toString());
            query2.executeUpdate();

            // totalizar perdidas
            StringBuilder update3 = new StringBuilder();
            update3.append("update v ");
            update3.append("set v.saldo_perdida=f.saldo ");
            update3.append("from sch_proteus.vacaciones v ");
            update3.append("inner join ");
            update3.append("( ");
            update3.append("select vd.vacacion_id as vacacion_id , ");
            update3.append("sum(case when vd.tipo='E' then vd.credito else 0 end)-sum(case when vd.tipo='E' then "
                    + "vd.debito else 0 end) as saldo ");
            update3.append("from sch_proteus.vacaciones_detalle vd ");
            update3.append("where vd.vigente=1");
            update3.append("group by vd.vacacion_id ");
            update3.append(") f on v.id = f.vacacion_id ");
            Query query3 = getEntityManager().createNativeQuery(update3.toString());
            query3.executeUpdate();

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @throws DaoException error en el acceso a datos
     */
    public void ajustarVacacionesProporcionalesExcedidas() throws DaoException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" select a.vacacion_id ,a.saldo from  ( ");
            sql.append(" select vd.vacacion_id as vacacion_id ,sum(vd.credito)-sum(vd.debito) as saldo ");
            sql.append(" from sch_proteus.vacaciones_detalle vd");
            sql.append(" where vd.vigente=1 and vd.tipo='P' ");
            sql.append(" group by vd.vacacion_id ) a ");
            sql.append("  where a.saldo >=30*8*60 ");
            List<Object[]> resultado = getEntityManager().createNativeQuery(sql.toString()).getResultList();
            for (Object[] registro : resultado) {
                Long vacacionId = ((BigDecimal) registro[0]).longValue();
                vacacionDetalleDao.actualizarTipo(TipoVacacionDetalleEnum.VACACION.getCodigo(),
                        TipoVacacionDetalleEnum.PROPORCIONAL.getCodigo(), vacacionId);
            }

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param regimenLaboralId identificador del regimen laboral
     * @param valorMaximo valor maximo
     * @return lista de vacaciones
     * @throws DaoException error en el acceso a datos
     */
    public List<Vacacion> buscarPorServidorSobrelimitado(final Long regimenLaboralId, final Integer valorMaximo)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(Vacacion.BUSCAR_POR_SERVIDOR_SOBRELIMITE, valorMaximo, regimenLaboralId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param institucionId identificador de la institucion
     * @param codigoAgrupador codigo de la unidad agrupadora
     * @return lista de saldos de vacaciones
     * @throws DaoException error en el acceso a datos
     */
    public List<RegistroReporteSaldosVacacionesVO> obtenerDatosParaReporteSaldos(final Long institucionId,
            List<UnidadOrganizacional> unidadesAcceso, final Boolean esRRHH) throws DaoException {
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT s.tipo_identificacion,");
            sql.append("       s.numero_identificacion,");
            sql.append("       s.apellidos_nombres,");
            sql.append("       v.saldo,");
            sql.append("       v.saldo_proporcional,");
            sql.append("       uo.nombre,");
            sql.append("       uo.codigo, ");
            sql.append("       s.jornada ");
            sql.append("FROM sch_proteus.vacaciones v");
            sql.append("     inner join sch_proteus.servidor_institucion si on v.servidor_institucion_id=si.id ");
            sql.append("     inner join sch_proteus.servidor s on si.servidor_id=s.id ");
            sql.append("     inner join sch_proteus.distributivo_detalles dd on dd.servidor_id= s.id ");
            sql.append("     inner join sch_proteus.distributivo d on d.id= dd.distributivo_id ");
            sql.append("     inner join sch_proteus.unidades_organizacionales uo on d.unidad_organizacional_id = uo.id ");
            sql.append("WHERE si.institucion_id =").append(institucionId);
            if (!esRRHH) {
                // recolectar codigos de unidades de acceso
                StringBuilder codigos = new StringBuilder();
                for (UnidadOrganizacional uo : unidadesAcceso) {
                    codigos.append("'").append(uo.getCodigo()).append("',");
                }
                if(codigos.length()>0){
                    codigos.delete(codigos.length()-1, codigos.length());
                }
                sql.append("  AND uo.codigo IN (").append(codigos).append(")");
            }
            sql.append("ORDER BY uo.codigo ");

            Query query = getEntityManager().createNativeQuery(sql.toString());
            List<Object[]> lista = query.getResultList();
            List<RegistroReporteSaldosVacacionesVO> registros = new ArrayList<>();

            for (Object[] o : lista) {
                RegistroReporteSaldosVacacionesVO v = new RegistroReporteSaldosVacacionesVO();
                v.setTipoIdentificacion(o[0] == null ? null : o[0].toString());
                v.setNumeroIdentificacion(o[1] == null ? null : o[1].toString());
                v.setApellidosNombres(o[2] == null ? null : o[2].toString());
                v.setSaldo(o[3] == null ? 0l : (Long) o[3]);
                v.setSaldoProporcional(o[4] == null ? 0l : (Long) o[4]);
                v.setNombreUnidadOrganizacional(o[5] == null ? null : o[5].toString());
                v.setCodigoUnidadOrganizacional(o[6] == null ? null : o[6].toString());
                v.setJornada(o[7] == null ? 1 : new Integer(o[7].toString()));
                registros.add(v);
            }
            return registros;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param servidorId
     * @param institucionId
     * @return
     * @throws DaoException
     */
    public Vacacion buscarPorServidorInstitucion(Long servidorId, Long institucionId) throws DaoException {
        Vacacion entidad = null;
        List<Vacacion> lista = buscarPorConsultaNombrada(Vacacion.BUSCAR_POR_SERVIDOR_INSTITUCION,
                servidorId, institucionId);
        if (!lista.isEmpty()) {
            entidad = lista.get(0);
        }
        return entidad;

    }

}
