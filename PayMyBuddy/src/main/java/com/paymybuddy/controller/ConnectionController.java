package com.paymybuddy.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paymybuddy.repository.entity.Connection;

@Controller
@RequestMapping("/api/v1/users/{userLogin}")
public class ConnectionController {

    @GetMapping("/connections")
    public List<Connection> connections() {
        return null;
    }
}
