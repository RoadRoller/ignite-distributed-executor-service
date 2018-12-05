package org.nprog.demo.ignite.controllers;

import org.nprog.demo.ignite.services.ITaskManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskManagerService taskManagerService;

    @GetMapping("/{taskId}")
    public Map<String, Object> getTaskResult(@PathVariable UUID taskId) {
        String result = taskManagerService.getSimpleTaskResult(taskId);
        Map<String, Object> response = new HashMap<>();
        response.put("result", result);
        return response;
    }

    @PostMapping
    public Map<String, Object> performTask(@RequestParam String argument) {
        UUID taskId = taskManagerService.performSimpleTask(argument);
        Map<String, Object> response = new HashMap<>();
        response.put("taskId", taskId);
        return response;
    }
}
