/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.comparadores;

import ec.com.atikasoft.proteus.modelo.TrayectoriaLaboral;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.Comparator;

/**
 * Comparador de trayectoria laboral.
 *
 * @author henry
 */
public class TrayectoriaLaboralComparador implements Comparator<TrayectoriaLaboral> {

    @Override
    public int compare(TrayectoriaLaboral o1, TrayectoriaLaboral o2) {
        return UtilFechas.convertirFechaStringEnDate(o2.getFechaDesde(), "dd/MM/yyyy").compareTo(
                UtilFechas.convertirFechaStringEnDate(o1.getFechaDesde(), "dd/MM/yyyy"));
    }

}
