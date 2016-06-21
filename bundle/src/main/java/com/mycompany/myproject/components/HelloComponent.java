package com.mycompany.myproject.components;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class HelloComponent extends WCMUsePojo {

    private String message;
    private String imagePath;

    @Override
    public void activate() throws Exception {
        Page currentPage = getCurrentPage();

        get

        imagePath = getResource().getPath();

        Resource resource = currentPage.adaptTo(Resource.class);

        ValueMap vm = resource.adaptTo(ValueMap.class);

        this.message = "message";
    }

    public String getMessage() {
        return message;
    }
}
