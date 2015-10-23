/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.api.interfaces.view;

import java.io.Serializable;
import java.util.List;

/**
 * View is a viewpoint of an already Result list. It focus on
 * providing a portion of the list.
 * <p>
 * Lists can come from anywhere.
 * </p>
 * It won't handle external aspects like ordering or filtering, only a
 * viewpoint segmentation.
 * 
 * @author epo.jemba@ext.mpsa.com
 * 
 * @param <Item> the item type
 */
public interface View<Item> extends Serializable {

	/**
	 * return the view of a result. 
	 * 
	 * @return the list of items
	 */
	List<Item> getView();

}
