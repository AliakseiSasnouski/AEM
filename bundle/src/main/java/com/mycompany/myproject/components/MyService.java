package com.mycompany.myproject.components;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import aQute.bnd.annotation.component.Component;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.Query;
import javax.jcr.Repository;

import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.foundation.Search;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;


import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.sling.api.resource.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//Sling Imports
import org.apache.sling.api.resource.ResourceResolverFactory ;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.Resource;


//AEM Tagging Imports
import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.commons.RangeIterator ;
import sun.plugin.dom.core.Document;
import sun.plugin.dom.core.Element;


@Service
@Component
public class MyService implements SampleService {

    @Reference
    QueryBuilder builder;


    private Session session;

    private ArrayList<String> title;

    public void getTagCount() {
        String fulltextSearchTerm = "Geometrixx";

        Map<String, String> map = new HashMap<String, String>();

        map.put("path", "/content");
        map.put("type", "cq:tags");
        map.put("p.offset", "0");
        map.put("p.limit", "20");

        Query query = builder.createQuery(PredicateGroup.create(map), session);
        query.setStart(0);
        query.setHitsPerPage(20);

        SearchResult searchResult = query.getResult();

        for (Hit hit : searchResult.getHits()) {

            Page page = null;
            try {
                page = hit.getResource().adaptTo(Page.class);
            } catch (RepositoryException e) {
                e.printStackTrace();
            }

            title.add(page.getTitle());
        }
    }

    public String getResultTitle(int index){
        return title.get(index);
    }
}