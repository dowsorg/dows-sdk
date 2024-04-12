package org.dows.sdk.elements;

import lombok.Data;
import org.dows.sdk.extract.ExtractMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/7/2024 11:59 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Data
public class ClassElement implements Element {
    // 类，接口
    private final Integer elementType = 2;
    private String pgk;
    private String name;
    private String code;
    private String descr;
    // 顺序
    private int index;

    // 元素对应模板的类型[entity,mapper,service,controller]
    private String builder;

    private Map<String, ClassElement> interfaces;

    private Map<String, List<FieldElement>> fields;

    private Map<String, MethodElement> methods;

    public static ClassElement toElement(String className, List<ExtractMetadata> extractMetadata) {
        ClassElement classElement = new ClassElement();
        String classDescr = "";
        Map<String, MethodElement> methods = new HashMap<>();
        for (ExtractMetadata em : extractMetadata) {
            classDescr = em.getClassDescr();
            MethodElement methodElement = new MethodElement();
            methodElement.setCode(em.getMethodCode());
            methodElement.setHttpMethod(em.getHttpMethod());
            methodElement.setDescr(em.getMethodDescr());
            methodElement.setMethodUrl(em.getMethodUrl());
            methodElement.setInput(null);
            methodElement.setOutput(null);
            methods.put(em.getMethodCode(), methodElement);
        }
        classElement.setCode(className);
        classElement.setName(className);
        classElement.setDescr(classDescr);
        classElement.setMethods(methods);
        return classElement;
    }
}

