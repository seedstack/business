/*
 * Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.internal.data;

import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Inject;
import org.seedstack.business.data.DataManager;
import org.seedstack.seed.command.CommandDefinition;
import org.seedstack.seed.command.Option;
import org.seedstack.seed.command.StreamCommand;

/**
 * Command to import data in the application.
 */
@CommandDefinition(scope = "business", name = "import", description = "Import application data")
public class DataImportCommand implements StreamCommand {
    @Inject
    private DataManager dataManager;
    @Option(name = "g",
            longName = "group",
            description = "The group of data to import (other groups are ignored)",
            hasArgument = true)
    private String group;
    @Option(name = "n",
            longName = "name",
            description = "The name of the data set of group to export (other items are ignored)",
            hasArgument = true)
    private String name;
    @Option(name = "c",
            longName = "clear",
            description = "Clear existing data before import")
    private boolean clear;

    @Override
    public void execute(InputStream inputStream, OutputStream outputStream, OutputStream errorStream) {
        dataManager.importData(inputStream, group, name);
    }

    @Override
    public Object execute(Object object) throws Exception {
        throw new IllegalStateException("This command cannot be invoked in interactive mode");
    }
}
