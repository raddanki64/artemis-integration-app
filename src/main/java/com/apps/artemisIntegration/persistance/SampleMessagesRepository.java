package com.apps.artemisIntegration.persistance;

import  com.apps.artemisIntegration.persistance.model.EntitySampleMessage;

import  org.springframework.data.repository.CrudRepository;

public interface SampleMessagesRepository extends CrudRepository<EntitySampleMessage, String> {
}