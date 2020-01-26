package com.starling.challenge.rest;

import com.starling.challenge.service.RoundUpService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/starling")
public class RoundUpController {

    private RoundUpService roundUpService;

    @Autowired
    public RoundUpController(RoundUpService roundUpService) {
        this.roundUpService = roundUpService;
    }

    @GetMapping("/roundup/")
    @ApiOperation("Execute round up feature for given user")
    public RoundUpResponse executeRoundUp() {
        return roundUpService.roundUp();
    }
}
