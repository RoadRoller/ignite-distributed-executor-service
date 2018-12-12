package org.nprog.demo.ignite.services.pubsub;

public interface IPubSubService {

    void publishTaskProgress(TaskProgressMessage message);

    void subscribeTasksProgress(TaskProgressListener listener);
}
