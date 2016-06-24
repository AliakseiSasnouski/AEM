package com.mycompany.myproject.components;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import javax.jcr.Node;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class HelloComponent extends WCMUsePojo {

    private String title;
    private String selectValue;
    private String displayValue;
    @Override
    public void activate() throws Exception {

        Resource componentResource = getResource();
        ValueMap vm = componentResource.adaptTo(ValueMap.class);

        //title = vm.get("titleMy", "");
        //selectValue = vm.get("selection","");

        /*selectValue = vm.get("displayType", "");
        Node node = getCurrentPage().getContentResource("displayType").adaptTo(Node.class);
        title = node.getProperty("displayType").toString();
        String text = node.hasProperty("jcr:text") ? node.getProperty("jcr:text").getString() : "default text";
*/
        Node node = getResource().adaptTo(Node.class);
        if(node.hasProperty("titleMy")){
            title = node.getProperty("titleMy").getString();
        }

        if(node.hasProperty("displayType")){
            selectValue = node.getProperty("displayType").getString();
            displayValue = this.getValue(selectValue);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getSelectValue() {return selectValue;}

    private String getValue(String tag) {
        String value = "<" + tag + ">" + this.getTitle() + "</" + tag + ">";
        return value;
    }

    public String getDisplayValue() {return displayValue;}
}
