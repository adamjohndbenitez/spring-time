package com.example.demo.accessingdataneo4j;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/* Define a Simple Entity
Neo4j captures entities and their relationships, with both aspects being of equal importance.
Imagine you are modeling a system where you store a record for each person. However,
you also want to track a person’s co-workers (teammates in this example).
With Spring Data Neo4j, you can capture all that with some simple annotations */
@Node
public class Person {
    @Id @GeneratedValue private long id;

    private String name;

    private Person() {
        // Empty constructor required as of Neo4j API 2.0.5
    };

    public Person(String name) {
        this.name = name;
    }

    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
    @Relationship(type = "TEAMMATE")
    public Set<Person> teammates;

    public void worksWith(Person person) {
        if (teammates == null) teammates = new HashSet<>();

        teammates.add(person);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name + "'s teammates => " +
                Optional.ofNullable(this.teammates)
                        .orElse(Collections.emptySet())
                        .stream().map(Person::getName)
                        .collect(Collectors.toList());
    }
}
/* Prior to install Neo4j Desktop - https://neo4j.com/download/community-edition/
Fire up neo4j:
1: In Neo4j Desktop, create project
2: Add Local DBMS
3: Name/Username should be neo4j
4: Password should be secret
5: Play
Full Details - https://neo4j.com/download-thanks-desktop/?edition=desktop&flavour=osx&release=1.4.5&offline=false */
