/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.NominaDetalleDao;
import ec.com.atikasoft.proteus.dao.NominaEjecucionDao;
import ec.com.atikasoft.proteus.dao.TipoProblemaDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.nomina.NominaEjecucion;
import ec.com.atikasoft.proteus.modelo.nomina.NominaProblema;
import ec.com.atikasoft.proteus.modelo.nomina.TipoProblema;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.ObjetoNominaVO;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author henry
 */
@Stateless
@LocalBean
public class ProblemaServicio extends BaseServicio {

    /**
     *
     */
    @EJB
    private TipoProblemaDao tipoProblemaDao;

    /**
     *
     */
    @EJB
    private NominaEjecucionDao nominaEjecucionDao;

    /**
     *
     */
    @EJB
    private NominaDetalleDao nominaDetalleDao;

    /**
     * Registra problema
     *
     * @param objeto
     * @param idTipoProblema
     * @param parametros
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registrarProblema(final ObjetoNominaVO objeto, final Long idTipoProblema, final String... parametros)
            throws DaoException {
        NominaProblema p = new NominaProblema();
        p.setNomina(objeto.getNomina());
        if (objeto.getPersonaNominaVO() != null) {
            p.setTipoDocumento(objeto.getPersonaNominaVO().getTipoDocumento());
            p.setNumeroDocumento(objeto.getPersonaNominaVO().getNumeroDocumento());
            p.setApellidosNombres(objeto.getPersonaNominaVO().getNombres());
        }
        if (objeto.getRubro() != null) {
            p.setCodigoRubro(objeto.getRubro().getCodigo());
            p.setNombreRubro(objeto.getRubro().getNombre());
        }
        p.setTipoProblema(new TipoProblema(idTipoProblema));
        StringBuilder problema = new StringBuilder();
        TipoProblema tp = tipoProblemaDao.buscarPorId(idTipoProblema);
        if (tp == null) {
            problema.append("TIPO PROBLEMA CON ID").append(idTipoProblema).append(
                    " NO SE ENCUENTRA REGISTRADO EN EL SISTEMA");
        } else {
            problema.append(tp.getNombre());
            int i = 1;
            for (String parametro : parametros) {
                int idx = problema.indexOf("<p" + i + ">");
                if (idx != -1) {
                    problema = problema.replace(idx, idx + 4, parametro == null ? "DESCONOCIDO" : parametro);
                }
                i++;
            }
        }
        p.setProblema(problema.toString().trim());
        p.setFechaCreacion(new Date());
        p.setUsuarioCreacion(objeto.getUsuario().getServidor().getNumeroIdentificacion());
        p.setVigente(Boolean.TRUE);
        objeto.getProblemas().add(p);
    }

    /**
     * Registra los eventos de ejecucion de una nomina.
     *
     * @param evento
     * @param usuario
     * @param nominaId
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registrarEjecucion(final String evento, final String usuario, final Long nominaId) {
        try {
            NominaEjecucion ne = new NominaEjecucion();
            ne.setEvento(evento);
            ne.setFechaCreacion(new Date());
            ne.setNomina(new Nomina(nominaId));
            ne.setUsuarioCreacion(usuario);
            ne.setVigente(Boolean.TRUE);
            nominaEjecucionDao.crear(ne);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Registra los eventos de ejecucion de una nomina.
     *
     * @param usuario
     * @param nominaId
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registrarEjecucion(final String usuario, final Long nominaId) {
        try {
            Long contador = nominaDetalleDao.contarServidoresEjecutados(nominaId);
            NominaEjecucion ne = new NominaEjecucion();
            ne.setEvento(UtilCadena.concatenar("CALCULANDO NOMINA DE ", contador, " SERVIDORES"));
            ne.setFechaCreacion(new Date());
            ne.setNomina(new Nomina(nominaId));
            ne.setUsuarioCreacion(usuario);
            ne.setVigente(Boolean.TRUE);
            nominaEjecucionDao.crear(ne);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
