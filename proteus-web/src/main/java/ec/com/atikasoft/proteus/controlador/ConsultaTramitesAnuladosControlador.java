/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.ConsultaTramitesAnuladosHelper;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Controlador para la administración de tramites anulados.
 *
 */
@ManagedBean(name = "tramitesAnuladosBean")
@ViewScoped
public class ConsultaTramitesAnuladosControlador extends BaseControlador {

    /**
     * Servicio de Administración.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     *
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * Helper de Clase.
     */
    @ManagedProperty("#{tramitesAnuladosHelper}")
    private ConsultaTramitesAnuladosHelper tramitesAnuladosHelper;

    @Override
    @PostConstruct
    public void init() {
        buscar();
    }

    public void buscar() {
        try {
            Map<String, String> servidores = new HashMap<>();
            tramitesAnuladosHelper.getListaTramitesAnulados().clear();
            String[] estados = {EstadosTramiteEnum.ANULADO.getCodigo(), EstadosTramiteEnum.RECHAZADO.getCodigo()};
            tramitesAnuladosHelper.setListaTramitesAnulados(administracionServicio.listarTramitePorEstados(estados));
            for (Tramite t : tramitesAnuladosHelper.getListaTramitesAnulados()) {
                if (servidores.containsKey(t.getUsuarioAnulacionRechazo())) {
                    t.setNombreUsuarioAnulacionRechazo(servidores.get(t.getUsuarioAnulacionRechazo()));
                } else {
                    Servidor s = servidorDao.buscar("C", t.getUsuarioAnulacionRechazo());
                    if (s != null) {
                        servidores.put(s.getNumeroIdentificacion(), s.getApellidosNombres());
                        t.setNombreUsuarioAnulacionRechazo(servidores.get(t.getUsuarioAnulacionRechazo()));
                    }
                }
            }
            tramitesAnuladosHelper.setEsNuevo(Boolean.TRUE);

        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
    }

    public ConsultaTramitesAnuladosHelper getTramitesAnuladosHelper() {
        return tramitesAnuladosHelper;
    }

    public void setTramitesAnuladosHelper(ConsultaTramitesAnuladosHelper tramitesAnuladosHelper) {
        this.tramitesAnuladosHelper = tramitesAnuladosHelper;
    }

}
