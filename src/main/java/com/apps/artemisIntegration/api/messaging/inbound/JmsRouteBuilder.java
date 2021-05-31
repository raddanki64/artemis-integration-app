package com.apps.artemisIntegration.api.messaging.inbound;

import  com.apps.artemisIntegration.api.messaging.exception.ValidationException;

import  org.apache.camel.EndpointInject;
import  org.apache.camel.Endpoint;
import  org.apache.camel.Processor;
import  org.apache.camel.Exchange;
import  org.apache.camel.LoggingLevel;
import  org.apache.camel.builder.RouteBuilder;
import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;
import  org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Component;

@Component
public class JmsRouteBuilder extends RouteBuilder {
    public static final String INPUT = "Input";
    private static Logger LOG = LoggerFactory.getLogger(JmsRouteBuilder.class);

    @Autowired
    private MessageProcessor processor;

    @EndpointInject(uri = "properties:{{spring.inbound.endpoint}}")
    private Endpoint input;

    @EndpointInject(uri = "properties:{{spring.inbound.dlq}}")
    private Endpoint dlq;

    @Override
    public void configure() throws Exception {
        onException(ValidationException.class)
                .handled(true)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        LOG.error("Observed input data validation exception");
                    }
                })
                .to(dlq)
                .end();

        onException(Exception.class)
            .handled(true)
            .process(new Processor() {
                public void process(Exchange exchange) throws Exception {
                    LOG.error("Observed exception");
                }
            })
            .end();

        LOG.info("Establishing single route to demo non-working of JMSXGroupId");
        from(input)
            .routeId(INPUT)
            .log(LoggingLevel.INFO, "Inbound message headers: ${headers}")
            .log(LoggingLevel.INFO, JmsRouteBuilder.class.getName(), "InBound message payload: ${body} from " + INPUT)
            .process(processor)
            .end();
    }
}