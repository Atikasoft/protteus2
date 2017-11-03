/*
 *  BancoHelper.java
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
 *  26/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Banco;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Banco
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "bancoHelper")
@SessionScoped
public class BancoHelper extends CatalogoHelper {

    /**
     * clase banco.
     */
    private Banco banco;

    /**
     * clase banco puesto para editar.
     */
    private Banco bancoEditDelete;

    /**
     * lista de bancos.
     */
    private List<Banco> listaBancos;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<Banco> listaBancoCodigo;

    /**
     * Constructor por defecto.
     */
    public BancoHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del BancoHelper.
     */
    public final void iniciador() {
        setBanco(new Banco());
        setBancoEditDelete(new Banco());
        setListaBancos(new ArrayList<Banco>());
       setListaBancoCodigo(new ArrayList<Banco>());
        
    }

    /**
     * @return the banco
     */
    public Banco getBanco() {
        return banco;
    }

    /**
     * @param banco the banco to set
     */
    public void setBanco(final Banco banco) {
        this.banco = banco;
    }

    /**
     * @return the bancoEditDelete
     */
    public Banco getBancoEditDelete() {
        return bancoEditDelete;
    }

    /**
     * @param bancoEditDelete the bancoEditDelete to set
     */
    public void setBancoEditDelete(final Banco bancoEditDelete) {
        this.bancoEditDelete = bancoEditDelete;
    }

    /**
     * @return the listaBancos
     */
    public List<Banco> getListaBancos() {
        return listaBancos;
    }

    /**
     * @param listaBancos the listaBancos to set
     */
    public void setListaBancos(final List<Banco> listaBancos) {
        this.listaBancos = listaBancos;
    }

    /**
     * @return the listaBancoCodigo
     */
    public List<Banco> getListaBancoCodigo() {
        return listaBancoCodigo;
    }

    /**
     * @param listaBancoCodigo the listaBancoCodigo to set
     */
    public void setListaBancoCodigo(final List<Banco> listaBancoCodigo) {
        this.listaBancoCodigo = listaBancoCodigo;
    }
}
