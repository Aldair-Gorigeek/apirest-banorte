package com.gorigeek.banorte.apirest.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/banorte")
public class SecurePlus3DController {

    @PostMapping("/secure3d")
    public Map<String, String> handle3DSecure(@RequestParam Map<String, String> params) {
        Map<String, String> response = new HashMap<>();
        params.forEach((key, value) -> {
            System.out.println(key + " = " + value);
            response.put(key, value);
        });
        return response;
    }
}
