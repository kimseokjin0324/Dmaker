package com.example.dMaker.controller;

import com.example.dMaker.dto.CreateDeveloper;
import com.example.dMaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    //- 일반적으로 데이터를 만드는것은 GET이 아니라 POST이다.
    //@RequestBody는 http에 담겨져있는 변수에 담는다는 의미이다.
    @PostMapping("/create-developers")
    public CreateDeveloper.Response createDevelopers(@Valid @RequestBody CreateDeveloper.Request request) {
        log.info("request : {}",request);
        return dMakerService.createDeveloper(request);
    }
}
