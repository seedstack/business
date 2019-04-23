/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.fixtures.inmemory.product;

import java.util.List;
import org.seedstack.business.domain.BaseAggregateRoot;

public class Product extends BaseAggregateRoot<Long> {
    private Long id;
    private String designation;
    private String summary;
    private String details;
    private PictureURL mainPicture;
    private List<Picture> pictures;
    private Double price;

    public Product(long productId, String designation, String summary, String details, String mainPicture,
            List<Picture> pictures, Double price) {
        id = productId;
        setDesignation(designation);
        setSummary(summary);
        setDetails(details);
        setMainPicture(new PictureURL(mainPicture));
        setPictures(pictures);
        setPrice(price);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PictureURL getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(PictureURL mainPicture) {
        this.mainPicture = mainPicture;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "Product{" +
                "entityId=" + id +
                ", designation='" + designation + '\'' +
                ", summary='" + summary + '\'' +
                ", details='" + details + '\'' +
                ", pictures=" + pictures +
                ", price=" + price +
                '}';
    }
}
