package com.mycompany.myproject.components;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.QueryManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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


    public ArrayList<String> getTagCount()  {




        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
        } catch (LoginException e) {
            e.printStackTrace();
        }


        ArrayList<String> title = new ArrayList<String>();

        QueryBuilder queryBuilder = null;
        if (resourceResolver != null) {
            queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
        }
        if (resourceResolver != null) {
            session = resourceResolver.adaptTo(Session.class);
        }

        Map<String, String> predicates = new HashMap<String, String>();
        predicates.put("path", "/content/myproject");
        predicates.put("type", "cq:Page");
        predicates.put("property", "jcr:content/cq:tags");
        predicates.put("property.value", "myTag:simpleTag");
        predicates.put("property.operation", "equals");
        predicates.put("p.limit", "10");

        Query query = null;
        if (queryBuilder != null) {
            query = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
        }
        Resource current;

        for(Hit hit : query.getResult().getHits()) {
            try {


                title.add(hit.getTitle());
            } catch (RepositoryException e) {
                e.printStackTrace();
            }


        }
        session.logout();
        return title;
    }
}