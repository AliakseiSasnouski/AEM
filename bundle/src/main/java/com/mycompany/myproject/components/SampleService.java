package com.mycompany.myproject.components;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.ArrayList;

public interface SampleService {

    public ArrayList<String> getTagCount() throws LoginException;

}