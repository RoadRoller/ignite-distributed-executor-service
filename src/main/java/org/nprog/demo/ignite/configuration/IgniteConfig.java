package org.nprog.demo.ignite.configuration;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;

@Configuration
public class IgniteConfig {

    private static final String LOCAL_CONFIG = "ignite-config.xml";
    private static final String CONFIG_BEAN_ID = "grid.cfg";

    @Bean
    public Ignite igniteClient() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(LOCAL_CONFIG);
        IgniteConfiguration config = (IgniteConfiguration) context.getBean(CONFIG_BEAN_ID);
        config.setClientMode(true);

        return Ignition.start(config);
    }

    @Bean
    public ExecutorService getExecutor() {
        ClusterGroup computeNodes = igniteClient().cluster().forServers();
        return igniteClient().executorService(computeNodes);
    }
}
