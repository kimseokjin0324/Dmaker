package com.example.dMaker.controller;

import com.example.dMaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {
    //-서비스라는 빈을 주입을 시켜줘야함
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<String> getAllDevelopers() {
        log.info("GET/developers HTTP/1.1");
        return Arrays.asList("snow", "else", "olaf");
    }

    @GetMapping("/create-developers")
    public List<String> createDevelopers() {
        log.info("GET/developers HTTP/1.1");

        dMakerService.createDeveloper();
        return Arrays.asList("Olaf");
    }
}
