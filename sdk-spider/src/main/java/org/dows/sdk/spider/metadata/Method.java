package org.dows.sdk.spider.metadata;

import lombok.Data;

import java.util.Map;

@Data
public class Method {
    private String descr;
    private String httpMethod;
    private String httpUri;
    private String version;
    private Boolean expose;
    private Annotation annotation;
    private Map<String, Field> inputs;
    private Map<String, Field> output;
}
