package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/api")
    public ResponseEntity<Map<String, String>> home() {
        Map<String, String> response = new HashMap<>();
        
        response.put("status", "API Running OK");
        response.put("service", "EV3 FullStack Ventas Backend");
        response.put("version", "0.0.1-SNAPSHOT");
        response.put("documentation", "/swagger-ui/index.html");
        response.put("api_prefix", "/api/");
        
        return ResponseEntity.ok(response);
    }
}