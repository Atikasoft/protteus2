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

import ec.com.atikasoft.proteus.enums.EstadoPuestoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.distributivo.DistributivoDetalleHistorico;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.EmpleadoAuditoriaVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class DistributivoDetalleHistoricoDao extends GenericDAO<DistributivoDetalleHistorico, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(DistributivoDetalleHistoricoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public DistributivoDetalleHistoricoDao() {
        super(DistributivoDetalleHistorico.class);
    }

    /**
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalleHistorico> buscarPorIdentificacion(
            String tipoIdentificacion, String numeroIdentificacion) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    DistributivoDetalleHistorico.BUSCAR_POR_IDENTIFICACION, tipoIdentificacion, numeroIdentificacion);
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    /**
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param codigoEstadoPuesto 
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalleHistorico> buscarPorIdentificacionYEstado(
            String tipoIdentificacion, String numeroIdentificacion, String codigoEstadoPuesto) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    DistributivoDetalleHistorico.BUSCAR_POR_IDENTIFICACION_Y_ESTADO,
                    tipoIdentificacion, numeroIdentificacion, EstadoPuestoEnum.OCUPADO.getCodigo());
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    /**
     *
     * @param codigoPuesto
     * @return
     * @throws DaoException
     */
    public List<DistributivoDetalleHistorico> buscarPorPuesto(Long codigoPuesto) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DistributivoDetalleHistorico.BUSCAR_POR_PUESTO, codigoPuesto);
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
    public List<DistributivoDetalleHistorico> buscar(EmpleadoAuditoriaVO vo) throws DaoException {
        try {
            boolean conParametros = false;
            Map<String, Object> parametros = new HashMap<String, Object>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT o FROM DistributivoDetalleHistorico o WHERE o.vigente=true ");
            if (vo.getTipoIdentificacion() != null && !vo.getTipoIdentificacion().trim().isEmpty()) {
                sql.append("AND o.servidorTipoIdentificacion=:tipoIdentificacion ");
                parametros.put("tipoIdentificacion", vo.getTipoIdentificacion());
            }
            if (vo.getNumeroIdentificacion() != null && !vo.getNumeroIdentificacion().trim().isEmpty()) {
                conParametros = true;
                sql.append("AND o.servidorNumeroIdentificacion=:tipoIdentificacion ");
                parametros.put("numeroIdentificacion", vo.getNumeroIdentificacion());
            }
            if ((vo.getApellidos() != null || vo.getNombres() != null) && (!vo.getApellidos().trim().isEmpty() || !vo.getNombres().trim().isEmpty())) {
                conParametros = true;
                sql.append("AND o.servidorNombres LIKE :nombres ");
                parametros.put("nombres", UtilCadena.concatenar(vo.getApellidos() == null ? "%" : vo.getApellidos(), "%", vo.getNombres() == null ? "%" : vo.getNombres(), "%"));
            }
            if (vo.getDenominacion() != null && !vo.getDenominacion().trim().isEmpty()) {
                conParametros = true;
                sql.append("AND o.escalaOcupacionalNombre=:denominacion ");
                parametros.put("denominacion", vo.getDenominacion());
            }
            if (vo.getDependencias() != null && !vo.getDependencias().trim().isEmpty()) {
                conParametros = true;
                sql.append("AND o.unidadOrganizacionalNombre=:dependencia ");
                parametros.put("dependencia", vo.getDependencias());
            }
            if (vo.getRegimen() != null && !vo.getRegimen().trim().isEmpty()) {
                conParametros = true;
                sql.append("AND o.regimenLaboralNombre=:regimen ");
                parametros.put("regimen", vo.getRegimen());
            }
            if (vo.getFechaInicial() != null) {
                conParametros = true;
                sql.append("AND o.fechaInicio=:fechaInicio ");
                parametros.put("fechaInicio", vo.getFechaInicial());
            }
            if (vo.getFechaFinal() != null) {
                conParametros = true;
                sql.append("AND o.fechaFin=:fechaFinal ");
                parametros.put("fechaFinal", vo.getFechaFinal());
            }
            sql.append("ORDER BY o.fechaCreacion DESC");
            if (!conParametros) {
                throw new DaoException("No se envio parametros");
            }
            Query query = getEntityManager().createQuery(sql.toString());
            for (String codigo : parametros.keySet()) {
                query.setParameter(codigo, parametros.get(codigo));
            }
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

}
