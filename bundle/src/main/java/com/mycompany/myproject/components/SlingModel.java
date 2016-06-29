package com.mycompany.myproject.components;

/**
 * Created by aliaksei.sasnouski on 6/28/2016.
 */

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = Resource.class)
public class SlingModel {

    @Inject
    @Named("titleMy")
    private String title;

    @Inject
    @Named("displayType")
    private String displType;

    public SlingModel() {
    }

    public String getTitleMy() {
        return title;
    }

    public String getDisplayType() {
        return displType;
    }
}
