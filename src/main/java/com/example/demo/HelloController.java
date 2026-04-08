package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Félicitations ! Votre projet Spring Boot fonctionne parfaitement sur le port 9090.";
    }
}

