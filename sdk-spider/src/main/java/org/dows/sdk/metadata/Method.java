package org.dows.sdk.metadata;

import lombok.Data;

import java.util.Map;

@Data
public class Method {
    private String descr;
    private String httpMethod;
    private String httpUri;
    private String version;
    // 如果暴露，那么则提升到api一份
    private Boolean expose;
    private Map<String, Annotation> annotations;
    private Map<String, Field> inputs;
    private Map<String, Field> output;
}
