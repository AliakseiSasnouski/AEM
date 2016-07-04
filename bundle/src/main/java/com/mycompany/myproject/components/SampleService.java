package com.mycompany.myproject.components;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.ArrayList;
import java.util.List;

public interface SampleService {

    public List<String> getTagCount() throws LoginException;

}