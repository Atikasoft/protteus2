/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.CuentaBancaria;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author atikasoft
 */
@LocalBean
@Stateless
public class CuentaBancariaDao extends GenericDAO<CuentaBancaria, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(CuentaBancariaDao.class.getCanonicalName());

    public CuentaBancariaDao() {
        super(CuentaBancaria.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de Cuentas bancarias que esten vigentes true por id del servidor.
     *
     * @return Listado Banco
     * @throws DaoException En caso de error
     */
    public List<CuentaBancaria> buscarVigentePorServidor(Long servidorId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CuentaBancaria.BUSCAR_VIGENTES, servidorId);
        } catch (DaoException ex) {
            Logger.getLogger(CuentaBancariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Cuentas bancarias que esten vigentes true por id del servidor.
     *
     * @param servidorId
     * @return Listado Banco
     * @throws DaoException En caso de error
     */
    public List<CuentaBancaria> buscarVigenteParaNominaPorServidor(Long servidorId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CuentaBancaria.BUSCAR_PARA_NOMINA_POR_SERVIDOR, servidorId);
        } catch (DaoException ex) {
            Logger.getLogger(CuentaBancariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Cuentas bancarias que esten vigentes true por id del beneficiario
     * especial.
     *
     * @param numeroIdentificacion
     * @return Listado Banco
     * @throws DaoException En caso de error
     */
    public List<CuentaBancaria> buscarVigenteParaNominaPorBeneficiarioEspecial(final String numeroIdentificacion)
            throws DaoException {
        try {
            System.out.println("buscarVigenteParaNominaPorBeneficiarioEspecial:" + numeroIdentificacion);
            return buscarPorConsultaNombrada(
                    CuentaBancaria.BUSCAR_PARA_NOMINA_POR_BENEFICIARIO_ESPECIAL, numeroIdentificacion);
        } catch (DaoException ex) {
            Logger.getLogger(CuentaBancariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Cuentas bancarias que esten vigentes true por id del beneficiario
     * institucional.
     *
     * @param numeroIdentificacion
     * @return Listado Cuentas bancarias
     * @throws DaoException En caso de error
     */
    public List<CuentaBancaria> buscarVigenteParaNominaPorBeneficiarioInstitucional(
            final String numeroIdentificacion)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    CuentaBancaria.BUSCAR_PARA_NOMINA_POR_BENEFICIARIO_INTITUCIONAL,
                    numeroIdentificacion);
        } catch (DaoException ex) {
            Logger.getLogger(CuentaBancariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Cuentas bancarias que esten vigentes true por id del beneficiario
     * institucional.
     *
     * @param beneficiarioId
     * @return Listado Banco
     * @throws DaoException En caso de error
     */
    public List<CuentaBancaria> buscarVigentePorBeneficiarioInstitucional(final Long beneficiarioId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CuentaBancaria.BUSCAR_POR_BENEFICIARIO_INSTITUCIONAL, beneficiarioId);
        } catch (DaoException ex) {
            Logger.getLogger(CuentaBancariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Cuentas bancarias que esten vigentes true por id del beneficiario
     * especial.
     *
     * @param beneficiarioId
     * @return Listado Banco
     * @throws DaoException En caso de error
     */
    public List<CuentaBancaria> buscarVigentePorBeneficiarioEspecial(final Long beneficiarioId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CuentaBancaria.BUSCAR_POR_BENEFICIARIO_ESPECIAL, beneficiarioId);
        } catch (DaoException ex) {
            Logger.getLogger(CuentaBancariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Cuentas bancarias que esten true.
     *
     * @return Listado Banco
     * @throws DaoException En caso de error
     */
    public List<CuentaBancaria> buscarTieneNomina() throws DaoException {
        try {
            return buscarPorConsultaNombrada(CuentaBancaria.BUSCAR_TIENE_NOMINA);
        } catch (DaoException ex) {
            Logger.getLogger(CuentaBancariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por codigo.
     *
     * @param codigo String
     * @return List de registros de CuentaBancaria
     * @throws DaoException DaoException
     */
    public List<CuentaBancaria> buscarTodosPorCodigo(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CuentaBancaria.BUSCAR_POR_CODIGO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(CuentaBancariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Busca  una cuenta dado el banco, el tipo de cuenta y el numero de cuenta
     * @param bancoId
     * @param codigo
     * @param numero
     * @return
     * @throws DaoException 
     */
    public CuentaBancaria buscarPorBancoTipoyNumero(final Long bancoId, final String codigo, final String numero) throws DaoException{
        List<CuentaBancaria> lista =  buscarPorConsultaNombrada(CuentaBancaria.BUSCAR_POR_BANCO_TIPO_Y_NUMERO, bancoId, codigo, numero);
        CuentaBancaria c = null;
        if(lista != null && !lista.isEmpty()){
            c = lista.get(0);
        }
        return c;
    }
}
