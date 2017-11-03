/*
 *  BaseServicio.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Jan 23, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio.base;

import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;

/**
 * Brinda servicio basicos a los servicios.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class BaseServicio {

    /**
     * Constructor sin argumentos.
     */
    public BaseServicio() {
        super();
    }

    /**
     * Recupera el parametro global desde cache.
     *
     * @param nemonico
     * @param ctx
     * @return
     */
    protected ParametroGlobal buscarParametroGlobal(final String nemonico, final ServletContext ctx) {
        List<ParametroGlobal> parametros = (List<ParametroGlobal>) ctx.getAttribute(CacheEnum.PARAMETROS_GLOBALES.
                getCodigo());
        ParametroGlobal p = null;
        for (ParametroGlobal pg : parametros) {
            if (pg.getNemonico().equals(nemonico)) {
                p = pg;
                break;
            }
        }
        return p;
    }

    /**
     * Recupera el parametro global desde una lista.
     *
     * @param nemonico
     * @param ctx
     * @return
     */
    protected ParametroGlobal buscarParametroGlobal(final String nemonico, final List<ParametroGlobal> parametros) {
        ParametroGlobal p = null;
        for (ParametroGlobal pg : parametros) {
            if (pg.getNemonico().equals(nemonico)) {
                p = pg;
                break;
            }
        }
        return p;
    }

    /**
     * Se encarga de inicializar los valores comunes de una entidad.
     *
     * @param <T> Se espera como parametro cualquier entidad que herede de
     * EntidadBasica.
     * @param entidad La entidad que se esta crearndo o actualizando
     * @param nuevo Indica si se esta creando un nuevo registro.
     */
    public <T extends EntidadBasica> void iniciarDatosEntidad(final T entidad, final Boolean nuevo, final UsuarioVO usuarioCreaOModifica) {
        if (entidad != null) {
            if (nuevo) {
                entidad.setFechaCreacion(new Date());
                entidad.setUsuarioCreacion(usuarioCreaOModifica.getServidor().getNumeroIdentificacion());
                entidad.setVigente(Boolean.TRUE);
            } else {
                entidad.setFechaActualizacion(new Date());
                entidad.setUsuarioActualizacion(usuarioCreaOModifica.getServidor().getNumeroIdentificacion());
            }
        }

    }
}
