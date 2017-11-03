/*
 *  ReasignacionDataModel.java
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
 *  16/01/2013
 *
 */

package ec.com.atikasoft.proteus.utilitarios;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
import ec.com.atikasoft.proteus.vo.UsuarioRolVO;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class ReasignacionDataModel extends ListDataModel<UsuarioRolVO> implements SelectableDataModel<UsuarioRolVO> {

    /**
     * Constructor sin parametros.
     */
    public ReasignacionDataModel() {
    }

    /**
     * 
     * @param data 
     */
    public ReasignacionDataModel(List<UsuarioRolVO> data) {
        super(data);
    }
    
    @Override
    public Object getRowKey(UsuarioRolVO t) {
        return t.getNumeroIdentificacion(); 
    }
    
    @Override
    public UsuarioRolVO getRowData(String rowKey) {
        List<UsuarioRolVO> listaUrvo = (List<UsuarioRolVO>) getWrappedData(); 
        for (UsuarioRolVO urvo : listaUrvo) {            
            if (urvo.getNumeroIdentificacion().equals(rowKey)) {
                return urvo;
            }            
        }        
        return null;        
    }
}
