package com.mycompany.myproject.components;

import com.adobe.cq.sightly.WCMUsePojo;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;

import java.util.ArrayList;
import java.util.List;
import javax.jcr.Node;

public class HelloComponent extends WCMUsePojo {

    List<String> arr;
    private String title;
    private String selectedValue;
    private String displayValue;
    @Reference
    private MyService ms;
    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    @Override
    public void activate() throws Exception {


        String path = getResource().getPath();
        ResourceResolver resourceResolver = getResourceResolver();
        Resource res = resourceResolver.getResource(path);
        Resource resC = res.getChild(path + "/selection");
        ValueMap mapResource = res.adaptTo(ValueMap.class);

        title = mapResource.get("titleMy").toString();

        selectedValue = mapResource.get("displayType").toString();
        displayValue = this.getValue(selectedValue);

    }

    public String getTitle() {
        return title;
    }

    public List<String> getArr() {
        MyService ms = getSlingScriptHelper().getService(MyService.class);
        List<String> result = ms.getTagCount("cat:cats");
        return result;
    }
    public String getSelectedValueValue() {return selectedValue;}

    private String getValue(String tag) {
        String value = "<" + tag + ">" + this.getTitle() + "</" + tag + ">";
        return value;
    }

    public String getDisplayValue() {return displayValue;}
}
