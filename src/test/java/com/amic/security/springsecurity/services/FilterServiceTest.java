package com.amic.security.springsecurity.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class FilterServiceTest {


    FilterService filterService;
    @BeforeEach
    void setUp() {
        filterService = new FilterService();
    }
//
//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void filter() {
        filterService.filter();
        assert true;
    }
}