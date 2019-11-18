package com.ley.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogViewerController {

    @RequestMapping("/list-log-files")
    public String listLogFiles() {
        return "";
    }

    @RequestMapping("/query")
    public String query() {
        return "";
    }
}
