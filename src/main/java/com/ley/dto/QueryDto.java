package com.ley.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class QueryDto {
    @NotEmpty
    private Date startDate;
    @NotEmpty
    private Date endDate;
    private String keyword;
    @NotEmpty
    private String filePath;
    @NotEmpty
    private int pageNo;
    @NotEmpty
    private int pageSize;
}
