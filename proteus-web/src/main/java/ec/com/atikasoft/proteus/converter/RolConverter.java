/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.converter;

import ec.com.atikasoft.proteus.modelo.Rol;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@FacesConverter("rolConverter")
public class RolConverter implements Converter {
//  private static final Logger LOG = LoggerFactory.getLogger(RolConverter.class);
 
  
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            try {
                Long id = Long.valueOf(value);
                return new Rol(id);
            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en conversión ",
                        "Rol"));
            }
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Rol) value).getId());
        }       

    }    
    
       private Rol getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<Rol> dualList;
        try{
            dualList = (DualListModel<Rol>) ((PickList)component).getValue();
            Rol team = getObjectFromList(dualList.getSource(),Integer.valueOf(value));
            if(team==null){
                team = getObjectFromList(dualList.getTarget(),Integer.valueOf(value));
            }
             
            return team;
        }catch(ClassCastException cce){
            throw new ConverterException();
        }catch(NumberFormatException nfe){
            throw new ConverterException();
        }
    }
 
    private Rol getObjectFromList(final List<?> list, final Integer identifier) {
        for(final Object object:list){
            final Rol rol = (Rol) object;
            if(rol.getId().equals(identifier)){
                return rol;
            }
        }
        return null;
    }
}
