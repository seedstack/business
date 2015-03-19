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

/**
 * SpecificationOrder
 *
 * @author redouane.loulou@ext.mpsa.com
 */
public class SpecificationOrder {

    private String property;

    private Order order;

    private String specificationName;

    /**
     * Provides a sort order to the query.
     */
    public static enum Order {
        ASC, DESC
    }

    /**
     * Constructor.
     */
    public SpecificationOrder() {
        super();
    }

    /**
     * @param specificationName used to specify the name of the specification if you're using NamedSpecification
     * @param property
     * @param order             the sort order
     */
    public SpecificationOrder(String specificationName, String property, Order order) {
        super();
        this.property = property;
        this.order = order;
        this.specificationName = specificationName;
    }

    /**
     * @param property
     * @param order    the sort order
     */
    public SpecificationOrder(String property, Order order) {
        super();
        this.property = property;
        this.order = order;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * Sets the sort order.
     *
     * @return order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Gets the sort order.
     *
     * @param order the sort order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Getter specificationName
     *
     * @return the specificationName
     */
    public String getSpecificationName() {
        return specificationName;
    }

    /**
     * Setter specificationName
     *
     * @param specificationName the specificationName to set
     */
    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }


}
