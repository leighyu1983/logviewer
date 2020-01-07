package com.ley.controller;


import com.ley.dto.QueryDto;
import com.ley.service.LogReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logviewer-api")
public class LogViewerController {

    @Autowired private LogReaderService logReaderService;

    @GetMapping("/file/list")
    public String listFolder() {
        return "";
    }

    /**
     * {
     *   "startDate":"2017-12-12",
     *   "endDate":"2017-12-12",
     *   "keyword":"tom jerry",
     *   "pageNo": 1,
     *   "pageSize": 10,
     *   "filePath":"D:\\tmp\\a.log"
     * }
     *
     * @return
     */
    @PostMapping("/query")
    public Map<Integer, List<String>> query(@RequestBody @Validated QueryDto queryDto) {
        return logReaderService.getLogs(queryDto);
    }
}
