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

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.TrayectoriaLaboral;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.ConsultaTrayectoriaLaboralVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class TrayectoriaLaboralDao extends GenericDAO<TrayectoriaLaboral, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(TrayectoriaLaboralDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public TrayectoriaLaboralDao() {
        super(TrayectoriaLaboral.class);
    }

    /**
     * Consulta de trayectoria laboral
     *
     * @param vo
     * @return
     * @throws DaoException
     */
    public List<TrayectoriaLaboral> buscarTrayectoria(final ConsultaTrayectoriaLaboralVO vo) throws DaoException {
        try {
            Map<String, Object> parametros = new HashMap<String, Object>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT o FROM TrayectoriaLaboral o ");
            sql.append(" WHERE o.vigente=true ");
//        if (vo.getFechaDesde() != null) {
//            sql.append(" AND o.fechaHasta>=:fechaDesde");
//            parametros.put("fechaDesde", UtilFechas.formatear(vo.getFechaDesde()));
//        }
//        if (vo.getFechaHasta() != null) {
//            sql.append(" AND o.fechaHasta<=:fechaHasta");
//            parametros.put("fechaHasta", UtilFechas.formatear(UtilFechas.sumarDias(vo.getFechaHasta(), 1)));
//        }

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
            sql.append(" ORDER BY o.fechaDesde DESC");
            Query query = getEntityManager().createQuery(sql.toString());
            Set<String> keys = parametros.keySet();
            for (String key : keys) {
                query.setParameter(key, parametros.get(key));
            }
            List<TrayectoriaLaboral> lista = query.getResultList();
            if (vo.getFechaDesde() != null && vo.getFechaHasta() != null) {
                List<TrayectoriaLaboral> listaFinal = new ArrayList<>();
                for (TrayectoriaLaboral tl : lista) {
                    if (UtilFechas.between(UtilFechas.formatear(tl.getFechaDesde()),
                            UtilFechas.truncarFecha(vo.getFechaDesde()),
                            UtilFechas.truncarFecha(vo.getFechaHasta()))) {
                        listaFinal.add(tl);
                    }
                }
                return listaFinal;
            } else {
                return lista;
            }

        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    /**
     * Busca el primer regitro para un tipo y numero de documento
     *
     * @param tipoDocumento
     * @param numeroDocumento
     * @return
     * @throws DaoException
     */
    public TrayectoriaLaboral buscarPrimerRegitro(final String tipoDocumento, final String numeroDocumento) throws
            DaoException {
        try {
            TrayectoriaLaboral entidad = null;
            List<TrayectoriaLaboral> lista = buscarPorConsultaNombrada(
                    TrayectoriaLaboral.BUSCAR_POR_PRIMERO, tipoDocumento, numeroDocumento);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
