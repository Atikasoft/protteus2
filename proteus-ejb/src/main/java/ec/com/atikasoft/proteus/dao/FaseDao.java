/*
 *  AsignacionDao.java
 *  ESIPREN V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of MRL
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with MRL.
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
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Fase;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Acceso a datos de la entidad Estado.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class FaseDao extends GenericDAO<Fase, Long> {

    /**
     * Constructor sin argumentos.
     */
    public FaseDao() {
        super(Fase.class);
    }

    /**
     * Recupera el estadoo inicial de un proceso especifico.
     *
     * @param codigoProceso Codigo asignado al proceso.
     * @return Estado inicial del proceso.
     * @throws DaoException Error en capa de persistencia.
     */
    public Fase buscarEstadoInicialDelProceso(final String codigoProceso) throws DaoException {
        try {
            Fase entidad = null;
            List<Fase> lista = buscarPorConsultaNombrada(Fase.BUSCAR_ESTADO_INICIAL_POR_PROCESO, codigoProceso);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera los estados siguiente a partir de un estado actual de un proceso especifico.
     *
     * @param codigoEstadoActual Codigo del estado actual.
     * @param codigoProceso Codigo asignado al proceso.
     * @return Lista de estados.
     * @throws DaoException Error en capa de persistencia.
     */
    public List<Fase> listarEstadosSiguientesDelProceso(final String codigoEstadoActual, final String codigoProceso)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(Fase.BUSCAR_ESTADOS_SIGUIENTES_POR_PROCESO, codigoEstadoActual,
                    codigoProceso);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Verifica si el estado actual.
     *
     * @param codigoEstadoActual Codigo del estado actual.
     * @param codigoProceso Codigo del estado actual.
     * @return Indica si es estado final.
     * @throws DaoException Error en capa de persistencia.
     */
    public Boolean esEstadoFinalDelProceso(final String codigoEstadoActual, final String codigoProceso) throws
            DaoException {
        try {
            Boolean resultado = Boolean.FALSE;
            if (listarEstadosSiguientesDelProceso(codigoEstadoActual, codigoProceso).isEmpty()) {
                resultado = Boolean.TRUE;
            }
            return resultado;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera todos las fases ordenadas por nombre.
     *
     * @return
     * @throws DaoException
     */
 
    public List<Fase> buscarTodosOrdenados() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Fase.BUSCAR_TODOS);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
