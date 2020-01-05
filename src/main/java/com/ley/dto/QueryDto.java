package com.ley.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class QueryDto {
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    private String keyword;
    @NotBlank
    private String filePath;
    @Min(1)
    private int pageNo;
    @NotNull
    private int pageSize;
}
