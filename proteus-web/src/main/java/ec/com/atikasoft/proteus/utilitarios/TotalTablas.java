/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.atikasoft.proteus.utilitarios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author christian
 */
public class TotalTablas {

    private UtilBaseDatos utilBaseDatos = new UtilBaseDatos();
    private Connection con;
    private ResultSet res;
    private Statement stt;

    public int countTabla(String tabla, String campo) throws Exception {
        int retornar = 0;
        String sql = "select count (*) as total from  sch_meritocracia_uath."+tabla+" where "+campo+" > 0";
        this.con = utilBaseDatos.getConexion();
        this.stt = this.con.createStatement();
        this.res = this.stt.executeQuery(sql);
        while (this.res.next()) {
            retornar = this.res.getInt("total");
        }
        this.res.close();
        this.stt.close();
        this.con.close();
        return retornar;
    }

    public int countCondicion(String tabla, String condicion) throws Exception {
        int retornar = 0;
        String sql = "select count (*) as total from  sch_meritocracia_uath."+tabla+" where "+condicion;
        this.con = utilBaseDatos.getConexion();
        this.stt = this.con.createStatement();
        this.res = this.stt.executeQuery(sql);
        while (this.res.next()) {
            retornar = this.res.getInt("total");
        }
        this.res.close();
        this.stt.close();
        this.con.close();
        return retornar;
    }

    public int obtenerSecuenciaRporte() throws Exception {
        int retornar = 0;
        String sql = "select nextval('secuencia_reportes') as total";
        this.con = utilBaseDatos.getConexion();
        this.stt = this.con.createStatement();
        this.res = this.stt.executeQuery(sql);
        while (this.res.next()) {
            retornar = this.res.getInt("total");
        }
        this.res.close();
        this.stt.close();
        this.con.close();
        return retornar;
    }

}
