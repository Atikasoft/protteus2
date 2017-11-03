/*
 *  ConsultaPuestoPaginador.java
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
package ec.com.atikasoft.proteus.controlador.paginador;

import ec.com.atikasoft.proteus.controlador.helper.ConsultaPuestoHelper;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.pagination.base.PaginacionBase;
import ec.com.atikasoft.proteus.servicio.MovimientoServicio;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.SortOrder;

/**
 * Paginador para Consulta Puesto.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 * @author Alvaro Tituaña <alvaro.tituania@markasoft.ec>
 */
@ManagedBean(name = "consultaMovimientoPaginador")
@ViewScoped
public class ConsultaMovimientoPaginador extends PaginacionBase<Movimiento> {

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{consultaPuestoHelper}")
    private ConsultaPuestoHelper consultaPuestoHelper;

    /**
     * Servicio de Movimiento.
     */
    @EJB
    private MovimientoServicio movimientoServicio;

    private List<Movimiento> resultado = null;

    /**
     * Constructor por defecto.
     */
    public ConsultaMovimientoPaginador() {
        super();
    }

    @Override
    protected List<Movimiento> loadData(int firstRow, int numberOfRows, String orderField, SortOrder orderDirection, Map<String, String> filters) {
        try {
            resultado = movimientoServicio.buscarTramites(consultaPuestoHelper.getConsultaTramiteVO(), firstRow, numberOfRows);
            setPageSize(numberOfRows);
        } catch (Exception e) {
            Logger.getLogger(ConsultaMovimientoPaginador.class.getName()).log(Level.SEVERE, null, e);
        }
        return resultado;
    }

    @Override
    protected int countRecords(Map<String, String> filters) {
        Long contarTramites = 0l;
        try {
            contarTramites = movimientoServicio.contarTramites(consultaPuestoHelper.getConsultaTramiteVO());
        } catch (DaoException ex) {
            Logger.getLogger(ConsultaMovimientoPaginador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return contarTramites.intValue();
    }

    public void setConsultaPuestoHelper(ConsultaPuestoHelper consultaPuestoHelper) {
        this.consultaPuestoHelper = consultaPuestoHelper;
    }

    public List<Movimiento> getResultado() {
        return resultado;
    }

    public void setResultado(List<Movimiento> resultado) {
        this.resultado = resultado;
    }
}
