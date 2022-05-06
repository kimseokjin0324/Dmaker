package com.example.dMaker.controller;

import com.example.dMaker.dto.*;
import com.example.dMaker.exception.DMakerException;
import com.example.dMaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {
    //-서비스라는 빈을 주입을 시켜줘야함
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllEmployedDevelopers() {
        log.info("GET/developers HTTP/1.1");

        return dMakerService.getAllEmployedDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(@PathVariable String memberId) {
        log.info("GET/developers HTTP/1.1");

        return dMakerService.getDeveloperDetail(memberId);
    }

    //- 일반적으로 데이터를 만드는것은 GET이 아니라 POST이다.
    //@RequestBody는 http에 담겨져있는 변수에 담는다는 의미이다.
    @PostMapping("/create-developers")
    public CreateDeveloper.Response createDevelopers(@Valid @RequestBody CreateDeveloper.Request request) {
        log.info("request : {}", request);
        return dMakerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(@PathVariable String memberId,
                                            @Valid @RequestBody EditDeveloper.Request request) {
        log.info("GET/developers HTTP/1.1");

        return dMakerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(@PathVariable String memberId) {
        log.info("GET/developers HTTP/1.1");

        return dMakerService.deleteDeveloper(memberId);
    }


}
