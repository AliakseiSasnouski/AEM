package com.mycompany.myproject.components;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.*;
import com.day.cq.workflow.metadata.MetaDataMap;

import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.ResourceResolverFactory;
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
    ResourceResolverFactory resourceResolverFactory;

    /**
     * Fields *
     */

    private static final Logger log = LoggerFactory.getLogger(MyService.class);

    /**
     * Work flow execute method *
     */

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {
        final WorkflowData workflowData = workItem.getWorkflowData();
        final String type = workflowData.getPayloadType();

        Session session = workflowSession.getSession();
        final String path = workflowData.getPayload().toString();
        try {
            Node node = session.getNode(path);
            /* Write your custom code here.  */
        } catch (Exception ex) {
            /* Use this block for Exception Handling.  */
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }
}