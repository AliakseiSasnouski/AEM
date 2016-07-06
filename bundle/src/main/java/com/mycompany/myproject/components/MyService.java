package com.mycompany.myproject.components;

import com.day.cq.commons.RangeIterator;
import com.day.cq.search.QueryBuilder;
import com.day.cq.tagging.TagManager;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import javax.jcr.Session;
import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(
        label = "MyService"
)
@Service({MyService.class})
public class MyService implements SampleService {


    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Reference
    QueryBuilder builder;


    @Reference
    private ResourceResolverFactory resolverFactory;

    private Session session;


    public List<String> getTagCount(String tagName)  {

        List<String> pathListReturn = new ArrayList<String>();

        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
        } catch (LoginException e) {
            e.printStackTrace();
        }


        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        RangeIterator<Resource> it = tagManager.find(tagName);
        String path;
        while (it!=null && it.hasNext()) {
            path = it.next().getPath();
            int lastPath = path.indexOf("jcr:content");
            String newPath = path.substring(0, lastPath + "jcr:content".length());
            path = newPath + "/renditions/cq5dam.thumbnail.100.100.png";
            pathListReturn.add(path);
        }

        return pathListReturn;
    }
}