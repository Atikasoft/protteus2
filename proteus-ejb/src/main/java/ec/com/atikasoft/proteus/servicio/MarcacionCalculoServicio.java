/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
@Stateless
@LocalBean
public class MarcacionCalculoServicio extends BaseServicio {

    /**
     * 
     * @param listaServidores
     * @throws ServicioException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void procesarMarcaciones(final List<Servidor> listaServidores
    ) throws ServicioException {

    }
}
