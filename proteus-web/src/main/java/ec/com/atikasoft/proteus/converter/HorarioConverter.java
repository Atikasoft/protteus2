/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.converter;

import ec.com.atikasoft.proteus.modelo.Horario;
import ec.com.atikasoft.proteus.utilitarios.Utilitarios;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@FacesConverter("horarioConverter")
public class HorarioConverter implements Converter {
    
    public static final String CLAVE_SESSION = "HorarioConverter";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            try {
                Long id = Long.valueOf(value);
                Horario h = buscar(id);
                if (h == null) {
                    return new Horario(id);
                } else {
                    if (h.getId().equals(0L) ) {
                        return null;
                    }
                } 
                return h;
            } catch (NumberFormatException ex) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en conversión ",
                        "Horario"));
            }
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Horario) value).getId());
        }

    }
    
    /**
     * Intenta encontrar el horario en la session
     * @param id
     * @return 
     */
    private Horario buscar(final Long id){
        List<Horario> l = (List<Horario>) Utilitarios.obtenerAtributoSession(CLAVE_SESSION);
        if(l != null){
            for(Horario h: l){
                if(h.getId().equals(id)){
                    return h;
                }
            }
        }
        return null;
    }
}
