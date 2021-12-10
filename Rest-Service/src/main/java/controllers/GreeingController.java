package controllers;


import java.util.concurrent.atomic.AtomicLong;

import models.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GreetingController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot";
    }
}