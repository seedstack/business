/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.finder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation is the interface marker for a Finder in SEED.
 * <p/>
 * A Finder is a CQRS (Command Query Responsibility Segregation) concept that
 * focus on "Read" part of the architecture. It will return DTO or Representation of Entities.
 * <p/>
 * Main reasons are scalability and coupling decrease. Be sure to follow CAST Training : "Business Framework".
 * <h2>How it works ? </h2>
 * 1) First, create an interface annotated.
 * <pre>
 * {@literal @}Finder
 * public interface FooFinder {
 *     List{@literal <FooDto>} findFoosWithName(String name);
 * }
 * </pre>
 * 2) Then, create the implementation of this interface.
 * <pre>
 * {@literal @}Finder
 * public class JpaFooFinder implements FooFinder {
 *
 *    {@literal @}Inject
 *    EntityManager entityManager;
 *
 *    List{@literal <FooDto>} findFoosWithName(String name) {
 *         entityManager.createQuery( ... ) ;
 *         return query.getResultList();
 *    }
 * </pre>
 * 3) Finally, SEED will automatically provide the injection of the Interface
 * <pre>
 * {@literal @}Inject
 * private FooFinder fooFinder;
 *
 * - - - - 8< - - - -
 *
 * {@literal @}Inject
 * public void provideFinder (FooFinder fooFinder)
 *
 * - - - - 8< - - - -
 *
 * fooFinder.findFoosWithName(nameToFind);
 * </pre>
 *
 * @author epo.jemba@ext.mpsa.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Finder {

}
