package com.amic.security.springsecurity.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FilterServiceTest {


    FilterService filterService;
    ParserService parserService;
    RuleService ruleService;


    @BeforeEach
    void setUp() {
        parserService = new ParserService();
        filterService = new FilterService();
        ruleService = new RuleService();
    }
//
//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void filter() {
        ArrayList<Map<String, Object>> fileData = null;
        ArrayList<Map<String, Object>> rule = null;
        try {
            fileData = parserService.getFileData();
            rule = ruleService.getRule();
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
        assert fileData != null;
        filterService.filter(fileData, rule);
        assert true;
    }
}