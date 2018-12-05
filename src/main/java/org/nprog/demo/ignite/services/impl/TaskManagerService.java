package org.nprog.demo.ignite.services.impl;

import org.nprog.demo.ignite.services.ITaskManagerService;
import org.nprog.demo.ignite.services.tasks.SimpleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class TaskManagerService implements ITaskManagerService {

    @Autowired
    private ExecutorService executorService;

    private Map<UUID, Future<String>> simpleTaskResults = new HashMap<>();

    @Override
    public UUID performSimpleTask(String argument) {
        UUID taskId = UUID.randomUUID();
        Future<String> futureResult = executorService.submit(new SimpleTask(argument));
        simpleTaskResults.put(taskId, futureResult);
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
        return "Task in progress...";
    }
}
