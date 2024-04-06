package org.dows.sdk.spider.metadata;

import lombok.Data;

import java.util.List;

@Data
public class Field {
    private String code;
    private String name;
    private String type;
    private Boolean required;
    private String def;
    private String descr;
    private List<Field> fields;
}