package com.foro.hub.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

    @GetMapping("/topic")
    public String greeting(){
        return "Hisasssssssss";
    }
}
