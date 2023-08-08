package com.amic.security.springsecurity.controller;

import com.amic.security.springsecurity.services.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    final FilterService filterService;

    public HelloController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> helloWorld()
    {
        String s = "Hello World";
//        filterService.filter();
        return ResponseEntity.ok(s);
    }
}
