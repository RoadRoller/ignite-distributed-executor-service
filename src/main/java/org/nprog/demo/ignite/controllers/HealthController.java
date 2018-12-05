package org.nprog.demo.ignite.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> success = new HashMap<>();
        success.put("status", "OK");
        success.put("timestamp", new Date());
        return success;
    }
}
