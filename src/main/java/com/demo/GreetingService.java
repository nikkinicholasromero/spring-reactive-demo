package com.demo;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    public Greeting getGreeting(String name) {
        if (null == name) {
            throw new RuntimeException();
        }

        return new Greeting("Hello, " + name);
    }
}
