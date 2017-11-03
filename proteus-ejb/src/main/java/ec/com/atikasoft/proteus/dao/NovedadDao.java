/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Novedad;
import ec.com.atikasoft.proteus.vo.BusquedaNovedadVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author atikasoft
 */
@LocalBean
@Stateless
public class NovedadDao extends GenericDAO<Novedad, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(NovedadDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public NovedadDao() {
        super(Novedad.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de Novedades que esten
     * vigentes true.
     *
     * @return Listado Novedades
     * @throws DaoException En caso de error
     */
    public List<Novedad> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Novedad.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(NovedadDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    public List<Novedad> buscarNovedadesPorPeriodoIdYTipoNominaId(final long periodoNominaId,
            final long tipoNominaId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Novedad.BUSCAR_POR_PERIODO_ID_Y_TIPO_NOMINA_ID,
                    periodoNominaId, tipoNominaId);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    public List<Novedad> buscarNovedadesPorNominaYPeriodoId(final long nominaId,
            final long periodoNominaId, final long datoAdicionalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Novedad.BUSCAR_POR_NOMINA_ID_Y_PERIDO_NOMINA_ID,
                    nominaId, periodoNominaId, datoAdicionalId);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Permite buscar las novedades por el id de la nomina a la cual está
     * asociada.
     *
     * @param
     * @return
     * @throws DaoException
     */
    public List<Novedad> buscarNovedadesNominaId(final long nominaId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Novedad.BUSCAR_POR_NOMINA_ID,
                    nominaId);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Consulta de nominas.
     *
     * @param vo
     * @return
     * @throws DaoException
     */
    public List<Novedad> buscar(final BusquedaNovedadVO vo) throws DaoException {
        try {
            Map<String, Object> parametros = new HashMap<String, Object>();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT o FROM Novedad o WHERE o.nomina.estado in ('A', 'V') and o.vigente = true ");
            sql.append(" AND o.institucionEjercicioFiscal.id=:institucionEjercicioFiscalId ");
            parametros.put("institucionEjercicioFiscalId", vo.getPeriodoFiscal());

            if (vo.getPeriodoNomina() != null) {
                sql.append(" AND o.nomina.periodoNominaId=:periodoNominaId ");
                parametros.put("periodoNominaId", vo.getPeriodoNomina());
            }
            if (vo.getTipoNominaId() != null) {
                sql.append(" AND o.nomina.id=:tipoNominaId ");
                parametros.put("tipoNominaId", vo.getTipoNominaId());
            }
            if (vo.getDatoAdicionalId() != null) {
                sql.append(" AND o.datoAdicionalId=:datoAdicionalId ");
                parametros.put("datoAdicionalId", vo.getDatoAdicionalId());
            }
            if (vo.getUnidadOrganizacional() != null) {
                sql.append(" AND o.unidadOrganizacionalId=:unidadOrganizacionalId ");
                if (vo.getUnidadOrganizacional().getDesconcentrado()) {
                    parametros.put("unidadOrganizacionalId", vo.getUnidadOrganizacional().getId());
                } else {
                    parametros.put("unidadOrganizacionalId", 0l);
                }
            }
            if ((vo.getNumeroDocumento() != null && !vo.getNumeroDocumento().isEmpty())
                    || (vo.getTipoDocumento() != null && !vo.getTipoDocumento().isEmpty())
                    || (vo.getNombres() != null && !vo.getNombres().isEmpty())) {

            }

//            if (vo.getNumeroDocumento() != null && !vo.getNumeroDocumento().isEmpty()) {
//                sql.append(" AND nd.servidor.numeroIdentificacion = :numeroIdentificacion ");
//                parametros.put("numeroIdentificacion", vo.getNumeroDocumento());
//            }
//            if (vo.getTipoDocumento() != null && !vo.getTipoDocumento().isEmpty()) {
//                System.out.println("vo.getTipoDocumento() " + vo.getTipoDocumento());
//                sql.append(" AND nd.servidor.tipoIdentificacion = :tipoIdentificacion ");
//                parametros.put("tipoIdentificacion", vo.getTipoDocumento());
//            }
//            if (vo.getNombres() != null && !vo.getNombres().isEmpty()) {
//                sql.append(" AND nd.servidor.apellidosNombres like :apellidosNombres ");
//                parametros.put("apellidosNombres", "%" + vo.getNombres() + "%");
//            }
            Query query = getEntityManager().createQuery(sql.toString());
            for (String key : parametros.keySet()) {
                query.setParameter(key, parametros.get(key));
            }
            query.setHint(QueryHints.FETCH, "o.listaDetalles");

            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
