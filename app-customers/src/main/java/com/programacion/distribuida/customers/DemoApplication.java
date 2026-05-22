package com.programacion.distribuida.customers;

import java.time.LocalDateTime;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping(path = "/test")
    public String test() {
        return "Hola Mundo " + LocalDateTime.now();
    }

}

