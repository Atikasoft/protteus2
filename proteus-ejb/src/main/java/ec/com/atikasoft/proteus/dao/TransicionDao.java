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
import ec.com.atikasoft.proteus.modelo.Transicion;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Acceso a datos de la entidad Transicion.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class TransicionDao extends GenericDAO<Transicion, Long> {

    /**
     * Constructor sin argumentos.
     */
    public TransicionDao() {
        super(Transicion.class);
    }

    /**
     * Recupera la transicion inicial de un proceso especifico.
     *
     * @param codigoProceso Codigo asignado al proceso.
     * @return Estado inicial del proceso.
     * @throws DaoException Error en capa de persistencia.
     */
    public Transicion buscarTransicionInicialDelProceso(final String codigoProceso) throws DaoException {
        try {
            Transicion entidad = null;
            List<Transicion> lista = buscarPorConsultaNombrada(Transicion.BUSCAR_TRANSICION_INICIAL_POR_PROCESO,
                    codigoProceso);
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
    public List<Transicion> listarEstadosSiguientesDelProceso(final String codigoEstadoActual,
            final String codigoProceso)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(Transicion.BUSCAR_TRANSICION_SIGUIENTE_POR_PROCESO, codigoEstadoActual,
                    codigoProceso);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera una transicion dado la fase inicial y final.
     *
     * @param codigoEstadoInicial Codigo del estado inicial.
     * @param codigoEstadoFinal Codigo del estado final.
     * @param codigoProceso codigo del proceso.
     * @return
     * @throws DaoException
     */
    public Transicion buscarPorFaseInicialFinal(final String codigoEstadoInicial, final String codigoEstadoFinal,
            final String codigoProceso) throws DaoException {
        try {
            Transicion entidad = null;
            List<Transicion> lista = buscarPorConsultaNombrada(Transicion.BUSCAR_TRANSICION_POR_FASE_INICIAL_FINAL,
                    codigoEstadoInicial, codigoEstadoFinal, codigoProceso);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
