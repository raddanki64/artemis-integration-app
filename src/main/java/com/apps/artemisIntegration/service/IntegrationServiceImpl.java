package com.apps.artemisIntegration.service;

import  com.fasterxml.jackson.databind.ObjectMapper;
import  com.fasterxml.jackson.databind.SerializationFeature;
import  com.fasterxml.jackson.datatype.joda.JodaModule;

import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;
import  org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Service;

import  com.apps.artemisIntegration.service.domain.SampleMessage;
import  com.apps.artemisIntegration.persistance.SqlServerService;

@Service
public class IntegrationServiceImpl implements IntegrationService {
    private static Logger LOG = LoggerFactory.getLogger(IntegrationServiceImpl.class);
    private static ObjectMapper om;

    static {
        om = new ObjectMapper();
        om.registerModule(new JodaModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Autowired
    private SqlServerService persistanceService;

    public IntegrationServiceImpl() {
    }

    @Override
    public void process(String payload) throws Exception {
        SampleMessage sm = om.readValue(payload, SampleMessage.class);
        LOG.info("Domain representation: " + sm);
        persistanceService.process(sm);
    }
}