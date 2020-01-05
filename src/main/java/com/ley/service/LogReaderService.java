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
        StringBuffer lastLine = new StringBuffer("");
        int lineNum = 0;
        try {
            it = FileUtils.lineIterator(new File(queryDto.getFilePath()), "UTF-8");
            while (it.hasNext()) {
                lineNum++;
                // paging
                if(lineNum < startLineNum) {
                    continue;
                }

                String lineContent = it.nextLine();
                processLines(lineContent, lastLine, matchedLines, pattern);

                // get page size
                matchedLines.removeAll(Collections.singleton(null));
                if(matchedLines.size() == endLineNum) {
                    break;
                }
            }
        } finally {
            if(it != null) {
                it.close();
            }
        }

        // process last line in case last line is a new line that didn't process yet.
        matchedLines.add(processOneLine(pattern, lastLine.toString()));
        matchedLines.removeAll(Collections.singleton(null));

        Map<Integer, List<String>> result = new HashMap<>();
        result.put(--lineNum, matchedLines);
        return result;
    }

    private void processLines(String lineContent, StringBuffer lastLine, List<String> matchedLines, String pattern) {
        if(isNewLine(lineContent)) {
            // this is a new line, but cannot judge if current line has multiple lines.
            // E.g. in other words, not sure if this is an exception line.
            // lastline is empty means this is the first time run. Should be executed once.
            if(StringUtils.isEmpty(lastLine)) {
                lastLine.append(lineContent);
                // should 'continue' but this is in method non-loop code.
            } else {
                // current process handles last time result.
                // Only this code can judge mutli-lines paragraph finishes.
                matchedLines.add(processOneLine(pattern, lastLine.toString()));
                // current line will be handled next time. Clear cache and set new line.
                lastLine.delete(0, lastLine.length());
                lastLine.append(lineContent);
            }
        } else {
            // muti line paragraph, concat to lastline.
            lastLine.append(lastLine.length() == 0 ? lineContent : "\r\n" + lineContent);
            // should 'continue' but this is in method non-loop code.
        }
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

    private String processOneLine(String pattern, String content) {
        if(StringUtils.isEmpty(content)) {
            return null;
        }

        if(Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(content).find()) {
            return content;
        }

        return null;
    }
}
