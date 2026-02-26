package com.example.orders_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/orders/ping")
    public String ping() {
        return "orders ok";
    }
}