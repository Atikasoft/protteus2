/*
 *  proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.vo.BienesVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "consultaBienesAsignadosHelper")
@SessionScoped
public class ConsultaBienesAsignadosHelper extends CatalogoHelper {


    /**
     * Obtener usuario conectado.
     */
    private UsuarioVO usuario;
    
    /**
     * 
     */
    private List<BienesVO> listaBienes;

    /**
     * Constructor por defecto.
     */
    public ConsultaBienesAsignadosHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables.
     */
    public final void iniciador() {
       listaBienes = new ArrayList<>();
       listaBienes.add(new BienesVO("506415", "LAPTOP" ,"Procesador intel core i5...", "CNU346BN80", BigDecimal.valueOf(1112.45d)));
       listaBienes.add(new BienesVO("503924", "IMPRESORA" ,"LASER JET...", "CNDF220718", BigDecimal.valueOf(512.51d)));
       listaBienes.add(new BienesVO("499798", "SOPLADORA" ,"SOPLADORA ASPIRADORA...", "", BigDecimal.valueOf(98.00d)));
       listaBienes.add(new BienesVO("492547", "RACK" ,"RACK CERRADO DESMONTABLE...", "FTX1201A04W", BigDecimal.valueOf(580.08d)));
    }

     /**
     * @return the usuario
     */
    public UsuarioVO getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioVO usuario) {
        this.usuario = usuario;
    }

    public List<BienesVO> getListaBienes() {
        return listaBienes;
    }

    public void setListaBienes(List<BienesVO> listaBienes) {
        this.listaBienes = listaBienes;
    }
    
    

}
