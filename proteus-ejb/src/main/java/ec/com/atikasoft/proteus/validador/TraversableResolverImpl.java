/*
 *  TraversableResolverImpl.java
 *  ESIPREN V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Jul 5, 2011
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.validador;

import javax.validation.Path;
import javax.validation.TraversableResolver;
import java.lang.annotation.ElementType;

/**
 * Clase para navegar una bean.
 * @author Juan Carlos Vaca  <jvaca@atikasoft.com.ec>
 */
public class TraversableResolverImpl implements TraversableResolver {

    /**
     * isReachable.
     * @param traversableObject
     *      traversableObject
     * @param traversableProperty
     *      traversableProperty
     * @param rootBeanType
     *      rootBeanType
     * @param pathToTraversableObject
     *      pathToTraversableObject
     * @param elementType
     *      elementType
     * @return
     *      true or false
     */
    @Override
    public boolean isReachable(final Object traversableObject, final Path.Node traversableProperty,
            final Class<?> rootBeanType,
            final Path pathToTraversableObject, final ElementType elementType) {
        return traversableObject == null;// || Hibernate.isInitialized(traversableObject);
    }

    /**
     * isCascadable.
     * @param traversableObject
     *      traversableObject
     * @param traversableProperty
     *      traversableProperty
     * @param rootBeanType
     *      rootBeanType
     * @param pathToTraversableObject
     *      pathToTraversableObject
     * @param elementType
     *      elementType
     * @return
     *      true or false
     */
    @Override
    public boolean isCascadable(final Object traversableObject, final Path.Node traversableProperty,
            final Class<?> rootBeanType,
            final Path pathToTraversableObject, final ElementType elementType) {
        return true;
    }
}

