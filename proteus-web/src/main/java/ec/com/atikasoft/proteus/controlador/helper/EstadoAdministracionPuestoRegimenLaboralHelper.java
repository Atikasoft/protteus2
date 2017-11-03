/*
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
 *  06/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.EstadoAdministracionPuestoRegimenLaboral;
import ec.com.atikasoft.proteus.vo.EstadoAdministracionPuestoRegimenLaboralVO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name = "estadoAdministracionPuestoRegimenLaboralHelper")
@SessionScoped
public class EstadoAdministracionPuestoRegimenLaboralHelper extends CatalogoHelper {

    /**
     * Lista para combo de regimenes laborales.
     */
    private List<SelectItem> regimenesSelectItems;

    /**
     * Lista de estados administracion puesto - regimenes laborales.
     */
    private List<EstadoAdministracionPuestoRegimenLaboral> estadosRegimenes;
    
    /**
     * Lista de estados administracion puesto - regimenes laborales VO.
     */
    private List<EstadoAdministracionPuestoRegimenLaboralVO> listaEstadosRegimenesVO;

    /**
     *
     */
    private Long regimenSeleccionadoId;

    /**
     * Constructor de la clase.
     */
    public EstadoAdministracionPuestoRegimenLaboralHelper() {
        super();
        init();
    }

    /**
     * Metodo inicializador de la clase.
     */
    public final void init() {
        regimenesSelectItems = new ArrayList<SelectItem>();
        estadosRegimenes = new ArrayList<EstadoAdministracionPuestoRegimenLaboral>();
        listaEstadosRegimenesVO = new ArrayList<EstadoAdministracionPuestoRegimenLaboralVO>();
    }

    /**
     * @return lista regimenesSelectItems
     */
    public List<SelectItem> getRegimenesSelectItems() {
        return regimenesSelectItems;
    }

    /**
     *
     * @param regimenesSelectItems
     */
    public void setRegimenesSelectItems(List<SelectItem> regimenesSelectItems) {
        this.regimenesSelectItems = regimenesSelectItems;
    }

    public Long getRegimenSeleccionadoId() {
        return regimenSeleccionadoId;
    }

    public void setRegimenSeleccionadoId(Long regimenSeleccionadoId) {
        this.regimenSeleccionadoId = regimenSeleccionadoId;
    }

    public List<EstadoAdministracionPuestoRegimenLaboral> getEstadosRegimenes() {
        return estadosRegimenes;
    }

    public void setEstadosRegimenes(List<EstadoAdministracionPuestoRegimenLaboral> estadosRegimenes) {
        this.estadosRegimenes = estadosRegimenes;
    }

    public List<EstadoAdministracionPuestoRegimenLaboralVO> getListaEstadosRegimenesVO() {
        return listaEstadosRegimenesVO;
    }

    public void setListaEstadosRegimenesVO(List<EstadoAdministracionPuestoRegimenLaboralVO> listaEstadosRegimenesVO) {
        this.listaEstadosRegimenesVO = listaEstadosRegimenesVO;
    }

    
    
}
