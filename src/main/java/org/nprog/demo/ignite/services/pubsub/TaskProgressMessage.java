package org.nprog.demo.ignite.services.pubsub;

import java.io.Serializable;
import java.util.UUID;

public class TaskProgressMessage implements Serializable {

    private UUID taskId;
    private double percent;

    public TaskProgressMessage(UUID taskId, double percent) {
        this.taskId = taskId;
        this.percent = percent;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public double getPercent() {
        return percent;
    }
}
