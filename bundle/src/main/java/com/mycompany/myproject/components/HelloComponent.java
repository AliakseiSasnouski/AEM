package com.mycompany.myproject.components;

import com.adobe.cq.sightly.WCMUsePojo;
import org.apache.felix.scr.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

public class HelloComponent extends WCMUsePojo {

    List<String> arr;
    private String title;
    private String selectedValue;
    private String displayValue;
    @Reference
    private MyService ms;
    @Override
    public void activate() throws Exception {

        //System.out.print("");
        //Resource componentResource = getResource();
        //lingModel slingModel = componentResource.adaptTo(SlingModel.class);

        //title = vm.get("titleMy", "");
        //selectValue = vm.get("selection","");

        /*selectValue = vm.get("displayType", "");
        Node node = getCurrentPage().getContentResource("displayType").adaptTo(Node.class);
        title = node.getProperty("displayType").toString();
        String text = node.hasProperty("jcr:text") ? node.getProperty("jcr:text").getString() : "default text";
*/
        //title = slingModel.getTitleMy();
        //displayValue = this.getValue(slingModel.getDisplayType());

        /*Node node = getResource().adaptTo(Node.class);
        if(node.hasProperty("titleMy")){
            title = node.getProperty("titleMy").getString();
        }

        if(node.hasProperty("displayType")){
            selectValue = node.getProperty("displayType").getString();
            displayValue = this.getValue(selectValue);
        }*/
      //  arr = ms.getTagCount();


       // int n = arr.size();


        System.out.print("uyyuiuyyi");
    }

    public String getTitle() {
        return title;
    }

    public List<String> getArr() {
        MyService ms = getSlingScriptHelper().getService(MyService.class);
        List<String> result = ms.getTagCount("cat:cats");
        return result;
    }
    public String getSelectValue() {return selectedValue;}

    private String getValue(String tag) {
        String value = "<" + tag + ">" + this.getTitle() + "</" + tag + ">";
        return value;
    }

    public String getDisplayValue() {return displayValue;}
}
