package com.zakafikry.school.dto;

import lombok.Data;

import java.util.List;

@Data
public class DataTablesOutput<T> {
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<T> data;
}