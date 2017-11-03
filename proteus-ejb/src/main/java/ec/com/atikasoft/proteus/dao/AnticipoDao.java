/*
 *  AnticipoDao.java
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
 *  03/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.enums.EstadoAnticipoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Anticipo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class AnticipoDao extends GenericDAO<Anticipo, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(AnticipoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public AnticipoDao() {
        super(Anticipo.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de Anticipo que esten vigentes
     * true.
     *
     * @return Listado Anticipo
     * @throws DaoException En caso de error
     */
    public List<Anticipo> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Anticipo.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(AnticipoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Anticipo que esten vigentes
     * true de un servidor específico.
     *
     * @param idServidor Long id del servidor
     * @return Listado Anticipo
     * @throws DaoException En caso de error
     */
    public List<Anticipo> buscarPorServidor(final Long idServidor) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Anticipo.BUSCAR_POR_SERVIDOR, idServidor);
        } catch (DaoException ex) {
            Logger.getLogger(AnticipoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Anticipo que esten vigentes
     * true con un estado específico.
     *
     * @param estado Estado del anticipo Registrado o Aprobado o N anulado
     * @param idinstitucionEjericioFiscal
     * @return Listado Anticipo
     * @throws DaoException En caso de error
     */
    public List<Anticipo> buscarPorEstado(final String estado, final Long idinstitucionEjericioFiscal) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Anticipo.BUSCAR_POR_ESTADO_INST_EJERCICIO, estado, idinstitucionEjericioFiscal);
        } catch (DaoException ex) {
            Logger.getLogger(AnticipoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Permite obtener la lista de planificacion de vacaciones para aprobar.
     *
     * @param idInstitucionEjercicioFiscal
     * @param idUnidadOrganizacional
     * @param estado si es null, busca todos los estados
     * @param idAprobador
     * @return
     * @throws DaoException
     */
    public List<Anticipo> buscarListaAprobacion(final Long idInstitucionEjercicioFiscal,
            final Long idUnidadOrganizacional, final String estado, final Long idAprobador) throws DaoException {
        List<Anticipo> lista1 = new ArrayList<Anticipo>();
        try {
            Map<String, Object> parametros = new HashMap<String, Object>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT a FROM Anticipo a , DistributivoDetalle d "
                    + " where a.vigente = true "
                    + " and d.vigente=true  and a.servidor.id = d.servidor.id and a.servidor.vigente=true"
                    + " and a.institucionEjercicioFiscal.id = d.distributivo.institucionEjercicioFiscal.id");

            if (idInstitucionEjercicioFiscal != null) {
                sql.append(" AND a.institucionEjercicioFiscal.id = :ejercicio ");
                parametros.put("ejercicio", idInstitucionEjercicioFiscal);
            }
            if (estado != null && !estado.isEmpty()) {
                if (!estado.equals(EstadoAnticipoEnum.REGISTRADO.getCodigo())) {
                    if (idAprobador != null) {
                        sql.append(" AND a.aprobador.id = :aprobador ");
                        parametros.put("aprobador", idAprobador);
                    }
                } else {
                    sql.append(" AND a.aprobador is null ");
                }
                sql.append(" AND a.estado = :estado ");
                parametros.put("estado", estado);
            }
            if (idUnidadOrganizacional != null) {
                sql.append(" AND d.distributivo.unidadOrganizacional.id = :unidad ");
                parametros.put("unidad", idUnidadOrganizacional);
            }

            sql.append(" order by a.servidor.apellidosNombres, a.fechaCreacion desc ");
            Query query = getEntityManager().createQuery(sql.toString());
            Set<String> keys = parametros.keySet();
            for (String key : keys) {
                query.setParameter(key, parametros.get(key));
            }
            lista1 = query.getResultList();

        } catch (Exception e) {
            Logger.getLogger(PlanificacionVacacionDao.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error obtener lista de aprobacion de anticipos.", e);
        }
        return lista1;

    }

    public List<Anticipo> buscarPorServidor(String estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param nominaId
     * @return
     * @throws DaoException
     */
    public List<Anticipo> buscarPorNomina(final Long nominaId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Anticipo.BUSCAR_POR_NOMINA, nominaId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public List<Anticipo> buscarPorNominaServidor(final Long nominaId, final Long servidorId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Anticipo.BUSCAR_POR_NOMINA_SERVIDOR, nominaId, servidorId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @return
     * @throws DaoException
     */
    public Integer quitarNomina(final Long nominaId) throws DaoException {
        return ejecutarPorConsultaNombrada(Anticipo.QUITAR_NOMINA, nominaId);
    }

    /**
     *
     * @param nominaId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public Integer quitarNominaServidor(final Long nominaId, final Long servidorId) throws DaoException {
        return ejecutarPorConsultaNombrada(Anticipo.QUITAR_NOMINA_SERVIDOR, nominaId, servidorId);
    }

}
