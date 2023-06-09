package org.dows.sdk.spider.properties;

import lombok.Data;

@Data
public class Flow {
    private Integer step;
    private Integer next;
    private Rule rule;
    private Class<?> handler;
}