package com.mycompany.myproject.components;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import javax.jcr.Node;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class HelloComponent extends WCMUsePojo {

    private String title;

    @Override
    public void activate() throws Exception {

        Resource componentResource = getResource();
        ValueMap vm = componentResource.adaptTo(ValueMap.class);

       title = vm.get("titleMy", "");
        getCurrentPage().getContentResource();
    }

    public String getTitle() {
        return title;
    }
}
