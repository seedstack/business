/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.helpers;

/**
 * This class centralizes all {@link org.seedstack.business.api.interfaces.assembler.Assembler} in the application.
 * SEED Business Framework will fill it the right way.
 * <p/>
 * It can be obtained using the following:
 * <pre>
 *     {@literal @}Inject
 *     Assemblers assemblers;
 * </pre>
 * On top of aggregating all the {@link org.seedstack.business.api.interfaces.assembler.Assembler}S of the
 * application, it offers more value with services like:
 * <ul>
 * <li>automatic Entity loading from appropriate {@link org.seedstack.business.api.domain.Repository}</li>
 * <li>automatic Entity creation from appropriate {@link org.seedstack.business.api.domain.GenericFactory}</li>
 * <li>a combination of both</li>
 * </ul>
 * Public method will describes the possibilities.
 * <p/>
 * This class is <b>now deprecated</b> the class {@link org.seedstack.business.api.interfaces.assembler.Assemblers}
 * should be used instead.
 *
 * @author epo.jemba@ext.mpsa.com
 * @author pierre.thirouin@ext.mpsa.com
 *         Date: 21/08/2014
 * @deprecated Replaced by {@link org.seedstack.business.api.interfaces.assembler.Assemblers}
 */
@Deprecated
public interface Assemblers extends org.seedstack.business.api.interfaces.assembler.Assemblers {
}
