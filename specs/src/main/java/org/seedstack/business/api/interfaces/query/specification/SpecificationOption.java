/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */
package org.seedstack.business.api.interfaces.query.specification;

import java.util.ArrayList;
import java.util.List;

/**
 * SpecificationOption.
 *
 * @author redouane.loulou@ext.mpsa.com
 */
public class SpecificationOption {

    private List<SpecificationOrder> orders;

    private boolean noDuplicate;

    /**
     * Constructor.
     *
     * @param orders      the specification orders
     * @param noDuplicate allows duplicates
     */
    public SpecificationOption(List<SpecificationOrder> orders,
                               boolean noDuplicate) {
        super();
        this.orders = orders;
        this.noDuplicate = noDuplicate;
    }

    /**
     * Constructor.
     *
     * @param noDuplicate allows duplicates
     * @param orders the specification orders
     */
    public SpecificationOption(boolean noDuplicate,
                               SpecificationOrder... orders) {
        super();
        for (SpecificationOrder specificationOrder : orders) {
            addOrder(specificationOrder);
        }
        this.noDuplicate = noDuplicate;
    }

    /**
     * Constructor.
     *
     * @param orders the specification orders
     */
    public SpecificationOption(SpecificationOrder... orders) {
        this(false, orders);
    }

    /**
     * Constructor.
     *
     * @param noDuplicate allows duplicates
     */
    public SpecificationOption(boolean noDuplicate) {
        super();
        this.noDuplicate = noDuplicate;
    }

    /**
     * Constructor.
     *
     * @param orders the specification orders
     */
    public SpecificationOption(List<SpecificationOrder> orders) {
        super();
        this.orders = orders;
    }

    /**
     * Constructor.
     */
    public SpecificationOption() {
        super();
    }

    /**
     * Gets the specification orders.
     *
     * @return orders
     */
    public List<SpecificationOrder> getOrders() {
        return orders;
    }

    /**
     * Sets the specification orders.
     *
     * @param orders the specification orders
     */
    public void setOrders(List<SpecificationOrder> orders) {
        this.orders = orders;
    }

    /**
     * @return true if the duplicates are not allowed, false otherwise
     */
    public boolean isNoDuplicate() {
        return noDuplicate;
    }

    /**
     * Sets the noDuplicates indicator.
     *
     * @param noDuplicate true if duplicates are not allowed
     */
    public void setNoDuplicate(boolean noDuplicate) {
        this.noDuplicate = noDuplicate;
    }

    /**
     * Adds a specification order.
     *
     * @param order a specification order
     */
    private void addOrder(SpecificationOrder order) {
        if (orders == null) {
            orders = new ArrayList<SpecificationOrder>();
        }
        orders.add(order);
    }

}
