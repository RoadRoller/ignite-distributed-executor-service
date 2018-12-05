package org.nprog.demo.ignite.services.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class SimpleTask implements Callable<String> {

    private static Logger logger = LoggerFactory.getLogger(SimpleTask.class);

    private String argument;

    public SimpleTask(String argument) {
        this.argument = argument;
    }

    @Override
    public String call() throws Exception {
        String result = "It's a simple task result. Passed argument: " + argument;
        logger.info(result);
        return result;
    }
}
