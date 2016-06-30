package com.mycompany.myproject.components;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Sling Imports
//AEM Tagging Imports


@Component(
        label = "MyService"
)
@Service({MyService.class})
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