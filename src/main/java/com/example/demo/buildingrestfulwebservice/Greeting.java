package com.example.demo.buildingrestfulwebservice;

/**
 * To model the greeting representation, create a resource representation class.
 */
public class Greeting {
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

/*
The Greeting object must be converted to JSON.
Thanks to Spring’s HTTP message converter support, you need not do this conversion manually.
Because Jackson 2 is on the classpath, Spring’s MappingJackson2HttpMessageConverter
is automatically chosen to convert the Greeting instance to JSON.
 */