package org.dows.sdk.metadata;

import lombok.Data;

import java.util.Map;

@Data
public class Interfaz {
    private String pkg;
    private String suffix;
    private Map<String,Annotation> annotations;
    private Map<String, Method> methods;
}
