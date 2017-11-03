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
import ec.com.atikasoft.proteus.modelo.nomina.NominaProblema;
import ec.com.atikasoft.proteus.vo.ObjetoNominaVO;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class NominaProblemaDao extends GenericDAO<NominaProblema, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(NominaProblemaDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public NominaProblemaDao() {
        super(NominaProblema.class);
    }

    /**
     *
     * @param nominaId
     * @throws DaoException
     */
    public void quitarVigencia(final Long nominaId) throws DaoException {
        try {
            ejecutarPorConsultaNombrada(NominaProblema.QUITAR_VIGENCIA, nominaId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Registra los problema encontrados en la nomina.
     *
     * @param objeto
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public void registrarProblemas(final ObjetoNominaVO objeto) throws DaoException {
        try {
            // eliminar los anteriores.
            quitarVigencia(objeto.getNomina().getId());
            flush();
            // crear los nuevos.
            for (NominaProblema p : objeto.getProblemas()) {
                crear(p);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @return 
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public List<NominaProblema> buscar(final Long nominaId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(NominaProblema.BUSCAR_POR_NOMINA, nominaId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
