package com.apps.artemisIntegration.persistance;

import  com.apps.artemisIntegration.service.domain.SampleMessage;

public interface SqlServerService {
    void process(SampleMessage sampleMessage) throws Exception;
}
