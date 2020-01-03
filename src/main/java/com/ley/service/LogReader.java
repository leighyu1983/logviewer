package com.ley.service;

import com.ley.dto.QueryDto;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class LogReader {
    /**
     *
     */
    @SneakyThrows
    public List<String> getLogs(QueryDto queryDto) {
        List<String> result = new ArrayList<>();
        String pattern = getPattern(queryDto);

        LineIterator it = null;
        int index = 0;
        try {
            it = FileUtils.lineIterator(new File(queryDto.getFilePath()), "UTF-8");
            while (it.hasNext()) {
                String content = it.nextLine();
                if(Pattern.matches(pattern, content)) {
                    result.add(content);
                }
            }
        } finally {
            if(it != null) {
                it.close();
            }
        }

        return result;
    }

    private String getPattern(QueryDto queryDto) {
        String pattern = StringUtils.isEmpty(queryDto.getKeyword()) ? "" :
                Arrays.asList(queryDto.getKeyword().trim().split(" ")).stream().collect(Collectors.joining(".*"));
        return pattern;
    }

    private List<String> processLogFile(String rowContent, ) {

    }
}
