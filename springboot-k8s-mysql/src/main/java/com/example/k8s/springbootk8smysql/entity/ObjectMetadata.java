package com.example.k8s.springbootk8smysql.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class ObjectMetadata {
    private String status;
    private List<String> process_version;
    private List<String> data_version;
    private Date pull_time;
    private Boolean has_data_changed;
    private Integer data_count;
    private String hash;
}
