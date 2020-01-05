package com.ley.service;

import com.ley.dto.QueryDto;
import com.ley.properties.LogConfigBean;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class LogReaderService {

    @Autowired private LogConfigBean logConfigBean;

    /**
     *
     * @param queryDto
     * @return  key:last line number, value, matched lines result.
     */
    @SneakyThrows
    public Map<Integer, List<String>> getLogs(QueryDto queryDto) {
        List<String> matchedLines = new ArrayList<>();
        String pattern = getQueryPattern(queryDto);

        int startLineNum = (queryDto.getPageNo() - 1) * queryDto.getPageSize() + 1;
        int endLineNum = queryDto.getPageNo() * queryDto.getPageSize();

        LineIterator it = null;
        String lastLine = null;
        int lineNum = 1;
        try {
            it = FileUtils.lineIterator(new File(queryDto.getFilePath()), "UTF-8");
            while (it.hasNext()) {
                // paging
                if(lineNum < startLineNum) {
                    continue;
                }

                String lineContent = it.nextLine();

                if(isNewLine(lineContent)) {
                    if(StringUtils.isEmpty(lastLine)) {
                        // this is a new line, not sure if current line has multiple line.
                        // E.g. in other words, not sure if this is an exception line.
                        lastLine = lineContent;
                        continue;
                    } else {
                        // process last line
                        matchedLines.add(processLine(pattern, lastLine));
                        lastLine = lineContent;
                    }
                } else {
                    // muti line paragraph, concat to lastline.
                    lastLine += StringUtils.isEmpty(lastLine) ? lineContent : "\r\n" + lineContent;
                    continue;
                }

                lineNum++;
                matchedLines.removeAll(Collections.singleton(null));
                // get page size
                if(matchedLines.size() == endLineNum) {
                    break;
                }
            }
            // process last line in case last line is a new line that didn't process yet.
            matchedLines.add(processLine(pattern, lastLine));
            matchedLines.removeAll(Collections.singleton(null));
        } finally {
            if(it != null) {
                it.close();
            }
        }

        Map<Integer, List<String>> result = new HashMap<>();
        result.put(--lineNum, matchedLines);
        return result;
    }

    /**
     * A B => A.*B
     */
    private String getQueryPattern(QueryDto queryDto) {
        String pattern = StringUtils.isEmpty(queryDto.getKeyword()) ? "" :
                Arrays.asList(queryDto.getKeyword().trim().split(" ")).stream().collect(Collectors.joining(".*"));
        return pattern;
    }

    /**
     * E.g. Exception content line is not new Line since the line doesn't start with date.
     * @param content
     * @return
     */
    private boolean isNewLine(String content) {
        return Pattern.matches(logConfigBean.getLinePattern(), content);
    }

    private String processLine(String pattern, String content) {
        if(StringUtils.isEmpty(content)) {
            return null;
        }

        if(Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(content).find()) {
            return content;
        }

        return null;
    }
}
