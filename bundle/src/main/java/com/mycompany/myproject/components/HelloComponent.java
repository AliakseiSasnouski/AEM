package com.mycompany.myproject.components;

import com.adobe.cq.sightly.WCMUsePojo;

public class HelloComponent extends WCMUsePojo {

    private String message;

    @Override
    public void activate() throws Exception {
        this.message = "message from the HelloComponent.java";
    }

    public String getMessage() {
        return message;
    }
}
