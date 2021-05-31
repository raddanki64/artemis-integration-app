package com.apps.artemisIntegration.persistance;

import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;
import  org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Service;

import  com.apps.artemisIntegration.service.domain.SampleMessage;

import  javax.persistence.EntityManager;

@Service
public class SqlServerServiceImpl implements SqlServerService {
    private static Logger LOG = LoggerFactory.getLogger(SqlServerServiceImpl.class);

    @Autowired
    private SampleMessagesRepository sampleMessagesRepository;

    @Autowired
    private EntityManager em;

    public SqlServerServiceImpl() {
    }

    @Override
    public void process(SampleMessage msg) throws Exception {
        LOG.info("Dropping message intentionally");
        //sampleMessagesRepository.save(msg);
    }
}