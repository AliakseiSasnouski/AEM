package com.mycompany.myproject.components;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.*;
import com.day.cq.workflow.metadata.MetaDataMap;

import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.JcrResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;

import javax.jcr.Session;

import javax.jcr.Node;
/**
 * Created by aliaksei.sasnouski on 7/1/2016.
 */
@Component(
        label = "CQ Workflow Process",
        description = "Sample Workflow Process implementation",
        metatype = false,
        immediate = false
)
@Properties({
        @Property(
                name = "service.description",
                value = "CQ Workflow Process implementation.",
                propertyPrivate = true
        ),
        @Property(
                label = "Workflow Label",
                name = "process.label",
                value = "CQ Workflow Process",
                description = "CQ Workflow Process description"
        )
})
@Service
public class MyWorkflow implements WorkflowProcess {
    /**
     * OSGi Service References *
     */

    @Reference
    private JcrResourceResolverFactory factory;

    /**
     * Fields *
     */
    private ResourceResolver resourceResolver;

    private static final Logger log = LoggerFactory.getLogger(MyService.class);
    private String arguments;

    /**
     * Work flow execute method *
     */

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {
        final WorkflowData workflowData = workItem.getWorkflowData();
        final String type = workflowData.getPayloadType();


        Session session = workflowSession.getSession();
        String path = workflowData.getPayload().toString();
        int lastPath = path.indexOf("jcr:content");
        String newPath = path.substring(0, lastPath + "jcr:content".length());
        newPath += "/metadata";
        try {
            if (args.containsKey("PROCESS_ARGS")){
                arguments = args.get("PROCESS_ARGS","string");
                String[] arrayArguments = arguments.split("[^0-9a-zA-Z]+");
                String[] arrayPath = path.split("[^0-9a-zA-Z]+");
                String name = arrayPath[4];

                String[] keyWords = new String[arrayArguments.length];
                String tagStr;
                int k = 1;
                while(!arrayArguments[k].equals("tagName")) {
                    keyWords[k-1] = arrayArguments[k];
                    k++;
                }
                Tag[] tag = new Tag[1];
                tagStr = arrayArguments[arrayArguments.length-1];
                for (int i = 0; i < keyWords.length; i++) {
                        if(name.toLowerCase().contains(keyWords[i])){

                            Resource resource = factory.getResourceResolver(session).getResource(newPath);
                            resourceResolver = resource.getResourceResolver();
                            TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
                            tag[0] = tagManager.createTag("catsTag:taskCat",tagStr,tagStr);
                            tagManager.setTags(resource,tag);

                            //Node node = session.getNode(path);
                            //node.setProperty("cq:tags","catsTag:taskCat");
                            //node.getSession().save();
                        }
                    }

            }
            /* Write your custom code here.  */
        } catch (Exception ex) {
            /* Use this block for Exception Handling.  */
        }
    }

    public String getArguments() {
        return arguments;
    }
}