package com.benny.multiple.cloud.before.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @Operation(summary = "Example GET endpoint", description = "This is an example endpoint for the GET method.")
    @GetMapping("/example")
    public String getExample() {
        return "Hello, World!";
    }
}
