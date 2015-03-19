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
package org.seedstack.business.api.domain.specification;


/**
 * The NamedSpecification identifies a specification by adding a name.
 * 
 * @author redouane.loulou@ext.mpsa.com
 */
public class NamedSpecification<T> {

	private Specification<T> specification;

	private String name;

	private Class<T> aggregateClass;

    /**
     * Constructor.
     *
     * @param specification the specification
     * @param name the specification name
     */
	public NamedSpecification(Specification<T> specification, String name) {
		super();
		this.specification = specification;
		this.name = name;
	}

    /**
     * Constructor.
     *
     * @param aggregateClass the aggregateClass
     * @param specification the specification
     * @param name the specification name
     */
	public NamedSpecification(Class<T> aggregateClass,
			Specification<T> specification, String name) {
		this(specification, name);
		this.aggregateClass = aggregateClass;
	}

    /**
     * @return the specification
     */
	public Specification<T> getSpecification() {
		return specification;
	}

    /**
     * Sets the specification.
     * @param specification the specification to set
     */
	public void setSpecification(Specification<T> specification) {
		this.specification = specification;
	}

    /**
     * Gets the name.
     * @return the name
     */
	public String getName() {
		return name;
	}

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Gets the aggregateClass.
     * @return the aggregateClass
     */
	public Class<T> getAggregateClass() {
		return aggregateClass;
	}

    /**
     * Sets the aggregateClass.
     *
     * @param aggregateClass the aggregateClass to set
     */
	public void setAggregateClass(Class<T> aggregateClass) {
		this.aggregateClass = aggregateClass;
	}

}
