package com.mycompany.myproject.components;

/**
 * Created by aliaksei.sasnouski on 7/5/2016.
 */
import java.util.Map;

import com.day.cq.commons.RangeIterator;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.tagging.TagManager;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.jcr.resource.JcrResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;

@Component(metatype = true, label = "A scheduled task",
        description = "Simple demo for cron-job like task with properties")
@Service(value = Runnable.class)
@Properties({
        @Property(name = "scheduler.period", longValue = 10,
                description = "Cron-job expression"),
        @Property(name = "scheduler.concurrent", boolValue=false,
                description = "Whether or not to schedule this task concurrently")
})
public class Scheduler implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Property(label = "A parameter", description = "Can be configured in /system/console/configMgr")
    public static final String MY_PARAMETER = "myParameter";
    private String myParameter;

    @Reference
    private Replicator replicator;

    @Reference
    private ResourceResolverFactory resolverFactory;

    private Session session;

    @Override
    public void run() {

        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
            session = resourceResolver.adaptTo(Session.class);
        } catch (LoginException e) {
            e.printStackTrace();
        }

        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        RangeIterator<Resource> it = tagManager.find(myParameter);
        String path;
        while (it != null && it.hasNext()) {
            path = it.next().getPath();
            int lastPath = path.indexOf("jcr:content");
            String newPath = path.substring(0, lastPath);
            path = newPath ;
            try {
                replicator.replicate(session, ReplicationActionType.ACTIVATE, path);
            } catch (ReplicationException e) {
                e.printStackTrace();
            }
        }

        //System.out.println("Yes!");

    }



    @Activate
    protected void activate(final Map<String, Object> config) {
        configure(config);
    }

    private void configure(final Map<String, Object> config) {
        myParameter = PropertiesUtil.toString(config.get(MY_PARAMETER), null);
    }
}