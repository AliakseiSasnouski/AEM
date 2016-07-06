package com.mycompany.myproject.components;
import com.day.cq.commons.RangeIterator;
import com.day.cq.tagging.TagManager;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.servlets.OptingServlet;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

@SlingServlet(

        description = "My Awesome All Powerful Servlet",
        resourceTypes = { "sling/servlet/default" },
        extensions = { "json" },
        label = "No, really. THIS is the only Servlet you will ever need...",
        metatype = true,
        name = "com.example.cq5.servlet.NewerAwesomeServlet",

        selectors = { "awesome" }
)
public class MyServlet extends SlingAllMethodsServlet implements OptingServlet{
    private static final Logger log = LoggerFactory.getLogger(MyServlet.class);

    @Override
    protected final void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)  {

        response.setContentType("text/html");

        // Do some work

        String selector = request.getRequestPathInfo().getSelectorString();

        String[] arrSelector = selector.split("[^0-9a-zA-Z]+");
        String path = new String();
        int i = 1;
        while (!arrSelector[i].equals("tagId")) {

            path += "/" + arrSelector[i];
            i++;
        }
        String tagName = arrSelector[++i] + ":" + arrSelector[++i];

        Resource resource = request.getResourceResolver().getResource(path);


        if (resource != null) {

        TagManager tagManager = resource.getResourceResolver().adaptTo(TagManager.class);
        RangeIterator<Resource> it = tagManager.find(tagName);
        JSONObject jsonResponse = new JSONObject();

        while (it != null && it.hasNext()) {
            Iterator<Resource> children  = it.next().getParent().listChildren();
            while(children != null && children.hasNext()) {
                String s = children.next().getName();
            }
            int lastPath = path.indexOf("jcr:content");
            String newPath = path.substring(0, lastPath + "jcr:content".length());
            try {
                jsonResponse.put("Tags", tagName);
                // Write the JSON to the response
                try {
                    response.getWriter().write(jsonResponse.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Be default, a 200 HTTP Response Status code is used
            } catch (JSONException e) {
                log.error("Could not formulate JSON response");
            }



        }


            // Writing HTML in servlets is usually inadvisable, and is better suited to be provided via a JSP/Sightly template
            // This is just an example.
            try {
                jsonResponse.put("success", true);
                jsonResponse.put("new-world","YS");
                // Write the JSON to the response
                try {
                    response.getWriter().write(jsonResponse.toString(2));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Be default, a 200 HTTP Response Status code is used
            } catch (JSONException e) {
                log.error("Could not formulate JSON response");
                // Servlet failures should always return an approriate HTTP Status code
                response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                // If you do not set your own HTML Response content, the OOTB HATEOS Response is used
                try {
                    response.getWriter().write("ERROR");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            // By Default the 200 HTTP Response status code is used; below explicitly sets it.
            response.setStatus(SlingHttpServletResponse.SC_OK);
        } else {
            // Set HTTP Response Status code appropriately
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("ERROR");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /** OptingServlet Acceptance Method **/

    @Override
    public final boolean accepts(SlingHttpServletRequest request) {
        /*
         * Add logic which inspects the request which determines if this servlet
         * should handle the request. This will only be executed if the
         * Service Configuration's paths/resourcesTypes/selectors accept the request.
         */
        return true;
    }

}

