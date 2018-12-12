package org.nprog.demo.ignite.services.pubsub.impl;

import org.apache.ignite.Ignite;
import org.nprog.demo.ignite.services.pubsub.Channels;
import org.nprog.demo.ignite.services.pubsub.IPubSubService;
import org.nprog.demo.ignite.services.pubsub.TaskProgressListener;
import org.nprog.demo.ignite.services.pubsub.TaskProgressMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PubSubServiceIgniteImpl implements IPubSubService {

    private Ignite ignite;

    @Autowired
    public PubSubServiceIgniteImpl(Ignite ignite) {
        this.ignite = ignite;
    }

    @Override
    public void publishTaskProgress(TaskProgressMessage message) {
        ignite.message().send(Channels.TASK_PROGRESS, message);
    }

    @Override
    public void subscribeTasksProgress(TaskProgressListener listener) {
        ignite.message().localListen(Channels.TASK_PROGRESS, (nodeId, message) -> {
            listener.onMessage((TaskProgressMessage) message);
            return true;
        });
    }
}
