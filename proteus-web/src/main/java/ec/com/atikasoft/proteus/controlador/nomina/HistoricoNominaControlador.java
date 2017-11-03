/*
 *  HistoricoNominaControlador.java
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
 *  09/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.nomina;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.ResultadoNominaHelper;
import ec.com.atikasoft.proteus.converter.ServidorConverter;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.utilitarios.Utilitarios;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
@ViewScoped
@ManagedBean(name = "historicoNominaBean")
public class HistoricoNominaControlador extends CatalogoControlador {

    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Helper de resultado de la nomina.
     */
    @ManagedProperty("#{resultadoNominaHelper}")
    private ResultadoNominaHelper resultadoNominaHelper;

    @Override
    @PostConstruct
    public void init() {
        iniciarCatalogos();
    }

    private void iniciarCatalogos() {
        resultadoNominaHelper.setTipoIdentificacion(null);
        resultadoNominaHelper.getTiposDocumentos().clear();
        for (TipoDocumentoEnum tp : TipoDocumentoEnum.values()) {
            resultadoNominaHelper.getTiposDocumentos().add(new SelectItem(tp.getNemonico(), tp.getNombre()));
        }
        resultadoNominaHelper.setServidor(null);
        resultadoNominaHelper.getServidores().clear();
    }

    public List<Servidor> buscarServidor(String query) {
        try {
            resultadoNominaHelper.setServidor(null);
            actualizarComponente(":frmprincipal:infoServidor");
            Long idInstEF = obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId();
            List<Servidor> lista = administracionServicio.buscarServidorPorNombreDistributivoLimite(query, null, 10);
            for (Servidor s : lista) {
                s.setNombreTipoIdentificacion(TipoDocumentoEnum.obtenerNombre(s.getTipoIdentificacion()));
            }
            Utilitarios.ponerAtributoSession(ServidorConverter.CLAVE_SESSION, lista);
            return lista;
        } catch (ServicioException ex) {
            Logger.getLogger(HistoricoNominaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void seleccionarServidor(SelectEvent event) {
        Servidor item = (Servidor) event.getObject();
        resultadoNominaHelper.setServidor(item);
        resultadoNominaHelper.setTipoIdentificacion(item.getTipoIdentificacion());
        resultadoNominaHelper.setNumeroIdentificacion(item.getNumeroIdentificacion());
    }

    public void limpiarServidor() {
        if (resultadoNominaHelper.getServidor() == null) {
            resultadoNominaHelper.setServidor(null);
            resultadoNominaHelper.setTipoIdentificacion(null);
            resultadoNominaHelper.setNumeroIdentificacion(null);
        }
    }

    public ResultadoNominaHelper getResultadoNominaHelper() {
        return resultadoNominaHelper;
    }

    public void setResultadoNominaHelper(ResultadoNominaHelper resultadoNominaHelper) {
        this.resultadoNominaHelper = resultadoNominaHelper;
    }

    @Override
    public String guardar() {
        return null;
    }

    @Override
    public String iniciarEdicion() {
        return null;
    }

    @Override
    public String iniciarNuevo() {
        return null;
    }

    @Override
    public String borrar() {
        return null;
    }

    @Override
    public String buscar() {
        try {
            Long idInstEF = obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId();
            String tipoDocumento = resultadoNominaHelper.getTipoIdentificacion();
            String numero = resultadoNominaHelper.getNumeroIdentificacion();
            Servidor s = administracionServicio.buscarServidor(tipoDocumento, numero);
            s.setNombreTipoIdentificacion(TipoDocumentoEnum.obtenerNombre(s.getTipoIdentificacion()));
            resultadoNominaHelper.setServidor(s);
        } catch (ServicioException ex) {
            Logger.getLogger(HistoricoNominaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void buscarDetalles() {

    }
}
