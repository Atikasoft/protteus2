/*
 *  PeriodoNominaHelper.java
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
 *  02/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.PeriodoNomina;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "periodoNominaHelper")
@SessionScoped
public class PeriodoNominaHelper extends CatalogoHelper {

    /**
     * obj.
     */
    private PeriodoNomina periodoNomina;

    /**
     * lista del obj.
     */
    private List<PeriodoNomina> listaPeriodoNominas;

    /**
     * Constructor por defecto.
     */
    public PeriodoNominaHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del DocumentoHabilitanteHelper.
     */
    public final void iniciador() {

        setPeriodoNomina(new PeriodoNomina());
        setListaPeriodoNominas(new ArrayList<PeriodoNomina>());
    }

    /**
     * @return the periodoNomina
     */
    public PeriodoNomina getPeriodoNomina() {
        return periodoNomina;
    }

    /**
     * @param periodoNomina the periodoNomina to set
     */
    public void setPeriodoNomina(final PeriodoNomina periodoNomina) {
        this.periodoNomina = periodoNomina;
    }

    /**
     * @return the listaPeriodoNominas
     */
    public List<PeriodoNomina> getListaPeriodoNominas() {
        return listaPeriodoNominas;
    }

    /**
     * @param listaPeriodoNominas the listaPeriodoNominas to set
     */
    public void setListaPeriodoNominas(final List<PeriodoNomina> listaPeriodoNominas) {
        this.listaPeriodoNominas = listaPeriodoNominas;
    }
}
