package com.example.demo.accessingdataneo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/*
Create Simple Queries
Spring Data Neo4j is focused on storing data in Neo4j.
But it inherits functionality from the Spring Data Commons project,
including the ability to derive queries.
Essentially, you need not learn the query language of Neo4j.
Instead, you can write a handful of methods and let the queries be written for you.

PersonRepository extends the Neo4jRepository interface
and plugs in the type on which it operates: Person.
This interface comes with many operations,
including standard CRUD (create, read, update, and delete) operations.
 */
public interface PersonRepository extends Neo4jRepository<Person, Long> {
    Person findByName(String name);

    List<Person> findByTeammatesName(String name);
}
/*
TIP: By default, @EnableNeo4jRepositories scans the current package for any interfaces
    that extend one of Spring Data’s repository interfaces.
    You can use its basePackageClasses=MyRepository.class to safely tell Spring Data Neo4j
    to scan a different root package by type if your project layout has multiple projects and
    it does not find your repositories.
 */