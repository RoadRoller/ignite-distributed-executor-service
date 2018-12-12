package org.nprog.demo.ignite.services.pubsub;

import java.util.EventListener;

@FunctionalInterface
public interface TaskProgressListener extends EventListener {
    void onMessage(TaskProgressMessage message);
}
