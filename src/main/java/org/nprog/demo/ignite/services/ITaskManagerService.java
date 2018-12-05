package org.nprog.demo.ignite.services;

import java.util.UUID;

public interface ITaskManagerService {

    /**
     * Pass simple task to executor and return task ID
     */
    UUID performSimpleTask(String argument);

    /**
     * Return task status and result by task ID
     */
    String getSimpleTaskResult(UUID taskId);
}
