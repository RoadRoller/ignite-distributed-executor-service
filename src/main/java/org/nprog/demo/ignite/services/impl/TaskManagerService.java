package org.nprog.demo.ignite.services.impl;

import org.nprog.demo.ignite.services.ITaskManagerService;
import org.nprog.demo.ignite.services.pubsub.IPubSubService;
import org.nprog.demo.ignite.services.tasks.SimpleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class TaskManagerService implements ITaskManagerService {

    private static Logger logger = LoggerFactory.getLogger(SimpleTask.class);

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private IPubSubService pubSubService;

    private Map<UUID, Future<String>> simpleTaskResults = new HashMap<>();
    private Map<UUID, Double> simpleTaskProgress = new HashMap<>();

    @PostConstruct
    protected void init() {
        pubSubService.subscribeTasksProgress((message) -> {
            logger.info("Task progress received. TaskID: {}, progress {}%", message.getTaskId(), message.getPercent() * 100);
            simpleTaskProgress.put(message.getTaskId(), message.getPercent());
        });
    }

    @Override
    public UUID performSimpleTask(String argument) {
        UUID taskId = UUID.randomUUID();
        Future<String> futureResult = executorService.submit(new SimpleTask(taskId, argument));
        simpleTaskResults.put(taskId, futureResult);
        simpleTaskProgress.put(taskId, 0d);
        return taskId;
    }

    @Override
    public String getSimpleTaskResult(UUID taskId) {
        Future<String> futureResult = simpleTaskResults.get(taskId);
        if (futureResult == null) {
            return "Task not found";
        }
        if (futureResult.isDone()) {
            try {
                return "Task completed: " + futureResult.get();
            } catch (InterruptedException | ExecutionException e) {
                return "Task failed: " + e.getMessage();
            }
        }
        Double progress = simpleTaskProgress.get(taskId);
        return "Task in progress " + progress * 100 + "% completed.";
    }
}
