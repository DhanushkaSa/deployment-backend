package com.deploy.deploymentBackend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    private String id;
    private String name;
    private int age;
}
