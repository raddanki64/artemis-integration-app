package com.apps.artemisIntegration.config;

import  org.apache.camel.component.amqp.AMQPComponent;
import  org.springframework.beans.factory.annotation.Value;
import  org.springframework.context.annotation.Bean;
import  org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig extends JmsConfig {
    @Value("${spring.broker.uri}")
    private String brokerUri;

    @Value("${spring.broker.user}")
    private String userName;

    @Value("${spring.broker.password}")
    private String password;

    @Bean(name = "appsBroker")
    public AMQPComponent activeAMQPComponent() throws Exception {
        return super.activeAMQPComponent(brokerUri, userName, password);
    }
}
