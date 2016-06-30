package com.mycompany.myproject.components;

import com.adobe.cq.sightly.WCMUsePojo;

public class HelloComponent extends WCMUsePojo {

    private String title;
    private String selectedValue;
    private String displayValue;
    @Override
    public void activate() throws Exception {
    MyService ms = getSlingScriptHelper().getService(MyService.class);
        System.out.print("");
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

        selectedValue = ms.getResultTitle(0);
    }

    public String getTitle() {
        return title;
    }

    public String getSelectValue() {return selectedValue;}

    private String getValue(String tag) {
        String value = "<" + tag + ">" + this.getTitle() + "</" + tag + ">";
        return value;
    }

    public String getDisplayValue() {return displayValue;}
}
