package com.ley.controller;


import com.ley.dto.QueryDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/ley-logs")
public class LogViewerController {

    @GetMapping("/file/list")
    public String listFolder() {
        return "";
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @param keyword
     * @param logPath log files in the path.
     * @return
     */
    @PostMapping("/query")
    public String query(@Validated QueryDto queryDto) {

        return "";
    }
}
