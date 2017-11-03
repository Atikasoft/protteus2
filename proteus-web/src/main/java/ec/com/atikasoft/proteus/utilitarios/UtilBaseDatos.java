/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.utilitarios;

import java.sql.Connection;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author fernando
 */
public class UtilBaseDatos {

    public Connection getConexion() {
        try {
            Connection con = dataSource().getConnection();
            return con;
        } catch (Exception e) {
            return null;
        }
    }
    
    private DataSource dataSource() {
        try {
            DataSource ds;
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("jdbc/siithMovimientos");
            return ds;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }    
}
