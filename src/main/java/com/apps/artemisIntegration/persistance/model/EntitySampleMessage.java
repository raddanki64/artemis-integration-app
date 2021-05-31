package com.apps.artemisIntegration.persistance.model;

import  javax.persistence.Column;
import  javax.persistence.Entity;
import  javax.persistence.Id;
import  javax.persistence.Table;
import  javax.persistence.GeneratedValue;
import  javax.persistence.GenerationType;

import  java.sql.Timestamp;
 
@Entity
@Table(name = "[Employees]")
public class EntitySampleMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "first_name", length = 50)
    private String firstName;
 
    @Column(name = "last_name", length = 50)
    private String lastName;

    public EntitySampleMessage()
    {
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }    
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("Id: ")
    	  .append(getId())
    	  .append(", firstName: ")
    	  .append(getFirstName())
    	  .append(", lastName: ")
    	  .append(getLastName())
    	  ;
    	
    	return sb.toString();
    }
}