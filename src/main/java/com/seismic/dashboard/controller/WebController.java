package com.seismic.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Dashboard Index Page
 */
@Controller
public class WebController {

    /**
     * Index page
     * @return index page
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}


