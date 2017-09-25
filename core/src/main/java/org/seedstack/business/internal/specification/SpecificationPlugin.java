/*
 * Copyright © 2013-2017, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */

package org.seedstack.business.internal.specification;

import static org.seedstack.business.internal.utils.BusinessUtils.streamClasses;

import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.seedstack.business.internal.BusinessSpecifications;
import org.seedstack.business.spi.SpecificationConverter;
import org.seedstack.business.spi.SpecificationTranslator;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Plugin handling specifications.
 */
public class SpecificationPlugin extends AbstractSeedPlugin {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpecificationPlugin.class);
  private final Set<Class<? extends SpecificationTranslator>> specificationTranslatorClasses =
      new HashSet<>();
  private final Set<Class<? extends SpecificationConverter>> specificationConverterClasses = new
      HashSet<>();

  @Override
  public String name() {
    return "business-specification";
  }

  @Override
  public Collection<ClasspathScanRequest> classpathScanRequests() {
    return classpathScanRequestBuilder()
        .specification(BusinessSpecifications.SPECIFICATION_TRANSLATOR)
        .specification(BusinessSpecifications.SPECIFICATION_CONVERTER).build();
  }

  @Override
  public InitState initialize(InitContext initContext) {
    streamClasses(initContext, BusinessSpecifications.SPECIFICATION_TRANSLATOR,
        SpecificationTranslator.class)
        .forEach(specificationTranslatorClasses::add);
    LOGGER.debug("Specification translator classes => {}", specificationTranslatorClasses);

    streamClasses(initContext, BusinessSpecifications.SPECIFICATION_CONVERTER,
        SpecificationConverter.class)
        .forEach(specificationConverterClasses::add);
    LOGGER.debug("Specification converter classes => {}", specificationConverterClasses);

    return InitState.INITIALIZED;
  }

  @Override
  public Object nativeUnitModule() {
    return new SpecificationModule(specificationTranslatorClasses, specificationConverterClasses);
  }
}
