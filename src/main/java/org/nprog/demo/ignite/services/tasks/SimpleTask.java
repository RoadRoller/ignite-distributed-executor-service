package org.nprog.demo.ignite.services.tasks;

import org.apache.ignite.Ignition;
import org.nprog.demo.ignite.services.pubsub.IPubSubService;
import org.nprog.demo.ignite.services.pubsub.TaskProgressMessage;
import org.nprog.demo.ignite.services.pubsub.impl.PubSubServiceIgniteImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.Callable;

public class SimpleTask implements Callable<String> {

    private static Logger logger = LoggerFactory.getLogger(SimpleTask.class);

    private UUID taskId;
    private String argument;

    private IPubSubService pubSubService;

    public SimpleTask(UUID taskId, String argument) {
        this.taskId = taskId;
        this.argument = argument;

        this.pubSubService = new PubSubServiceIgniteImpl(Ignition.ignite());
    }

    @Override
    public String call() throws Exception {
        logger.info("Task {} started", taskId);
        for (int i = 0; i <= 20; i++) {
            pubSubService.publishTaskProgress(new TaskProgressMessage(taskId, (double) i / 20));
            Thread.sleep(1000);
        }
        String result = "It's a simple task result. Passed argument: " + argument;
        logger.info(result);
        logger.info("Task {} finished", taskId);
        return result;
    }
}
