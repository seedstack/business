/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.business.finder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.seedstack.business.Service;

/**
 * A finder encapsulates interface-specific data queries that have no business meaning. It only has read-only
 * operations.
 *
 * <p>A finder has a different purpose than a repository:</p>
 * <ul>
 * <li>The repository is responsible for domain persistence operations that are meaningful to the business. Domain
 * objects can then be transformed into DTO by using assemblers.</li>
 * <li>The finder is useful when you need to retrieve raw data, bypassing the domain to directly produce an
 * interface-specific DTO.</li>
 * </ul>
 *
 * <p>A finder can retrieve data from anywhere regardless of the persistence mechanism</p>
 *
 * <p>As the finder bypasses the domain model, it must be restricted to read-only queries to avoid the risk of
 * compromising the integrity of the system.</p>
 *
 * <p>The annotation is applied on an interface:</p>
 * <pre>
 * {@literal @}Finder
 *  public interface SomeFinder {
 *      List&lt;SomeDTO&gt; findSomeData(String filter);
 *  }
 * </pre>
 *
 * <p>A JPA implementation of this finder could be:</p>
 * <pre>
 * public class SomeJpaFinder implements SomeFinder {
 *    {@literal @}Override
 *     public SomeDTO findSomeData(String filter) {
 *         // directly use JPA to retrieve raw data and produce the DTO
 *     }
 * }
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Service
@Deprecated
public @interface Finder {

}
