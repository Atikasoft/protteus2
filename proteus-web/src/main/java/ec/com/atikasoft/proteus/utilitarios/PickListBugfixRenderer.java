package ec.com.atikasoft.proteus.utilitarios;

import java.io.IOException;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import org.primefaces.component.picklist.PickList;
import org.primefaces.component.picklist.PickListRenderer;
import org.primefaces.util.WidgetBuilder;

/**
 * Esta implementacion del pick list corrige el buck de los filtros cuando el atributo filterMatchMode='contains'
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
public class PickListBugfixRenderer extends PickListRenderer {

    @Override
    protected void encodeClientBehaviors(
            FacesContext context, ClientBehaviorHolder component, WidgetBuilder wb) throws IOException {
        super.encodeClientBehaviors(context, component, wb);
        PickList pickList = (PickList) component;
        wb.attr("filterMatchMode", pickList.getFilterMatchMode(), null);
    }
}
