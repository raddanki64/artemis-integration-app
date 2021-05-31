package com.apps.artemisIntegration.config;

import  javax.jms.Connection;
import  javax.jms.JMSException;
import  org.apache.activemq.ActiveMQSslConnectionFactory;
import  org.apache.activemq.RedeliveryPolicy;
import  org.apache.activemq.camel.component.ActiveMQComponent;
import  org.apache.activemq.jms.pool.PooledConnectionFactory;
import  org.apache.camel.component.amqp.AMQPComponent;
import  org.apache.qpid.jms.JmsConnectionFactory;
import  org.apache.qpid.jms.policy.JmsDefaultRedeliveryPolicy;
import  org.springframework.jms.connection.CachingConnectionFactory;
import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;

import javax.jms.ConnectionFactory;

public class JmsConfig {
  private static final Logger LOG = LoggerFactory.getLogger(JmsConfig.class);

  protected AMQPComponent activeAMQPComponent(String jmsUri,
                                              String userName,
                                              String password) throws Exception {
    JmsDefaultRedeliveryPolicy redeliveryPolicy = new JmsDefaultRedeliveryPolicy();
    redeliveryPolicy.setMaxRedeliveries(RedeliveryPolicy.DEFAULT_MAXIMUM_REDELIVERIES);

    JmsConnectionFactory jmsConnectionFactory = new JmsConnectionFactory();
    jmsConnectionFactory.setRemoteURI(jmsUri);
    jmsConnectionFactory.setUsername(userName);
    jmsConnectionFactory.setPassword(password);
    jmsConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
    jmsConnectionFactory
        .setExceptionListener(e -> LOG.error("JMS Connection factory failed with exception:", e));

    validateMQConnection(jmsConnectionFactory);
    ConnectionFactory connectionFactory = getJmsConnectionFactory(jmsConnectionFactory);

    AMQPComponent amqpComponent = new AMQPComponent();
    amqpComponent.setConnectionFactory(connectionFactory);
    return amqpComponent;
  }

  //
  // below is used while interacting with external broker (IBM WebSphere)
  // (also referred as pubsub broker or inbound broker)
  //
  protected ActiveMQComponent activeJMSComponent(String jmsUri,
                                                 String userName,
                                                 String password) throws Exception {
    RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
    redeliveryPolicy.setMaximumRedeliveries(RedeliveryPolicy.DEFAULT_MAXIMUM_REDELIVERIES);

    ActiveMQSslConnectionFactory jmsConnectionFactory = new ActiveMQSslConnectionFactory();

    jmsConnectionFactory.setBrokerURL(jmsUri);
    jmsConnectionFactory.setUserName(userName);
    jmsConnectionFactory.setPassword(password);
    jmsConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);

    jmsConnectionFactory
        .setExceptionListener(e -> LOG.error("JMS Connection factory failed with exception:", e));

    //validateMQConnection(jmsConnectionFactory);
    ConnectionFactory connectionFactory = getJmsConnectionFactory(jmsConnectionFactory);

    ActiveMQComponent activeMQComponent = new ActiveMQComponent();
    activeMQComponent.setConnectionFactory(connectionFactory);

    return activeMQComponent;
  }

  protected ConnectionFactory getJmsConnectionFactory(ConnectionFactory jmsConnectionFactory) {
      return new CachingConnectionFactory(jmsConnectionFactory);
  }

  private void validateMQConnection(Object connectionFactory) {
    try {
      Connection connection = null;
      if (connectionFactory instanceof ActiveMQSslConnectionFactory) {
        connection = ((ActiveMQSslConnectionFactory) connectionFactory).createConnection();
      }
      else {
        connection = ((JmsConnectionFactory) connectionFactory).createConnection();
        connection.start();
      }

      connection.close();
    }
    catch (JMSException e) {
      LOG.error("JMS Connection factory failed with exception:", e);
      throw new RuntimeException(e);
    }
  }
}
