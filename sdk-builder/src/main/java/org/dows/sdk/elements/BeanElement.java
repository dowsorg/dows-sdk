package org.dows.sdk.elements;

import lombok.Getter;
import lombok.Setter;
import org.dows.sdk.extract.ArgumentMetadata;
import org.dows.sdk.extract.FunctionMetadata;

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
@Getter
public class BeanElement implements Element {
    // 类，接口
    private final Integer elementType = 2;
    private String pkg;
    @Setter
    private String name;
    private String code;
    @Setter
    private String descr;

    // 顺序
    @Setter
    private int index;
    // 元素对应模板的类型[entity,mapper,service,controller]
    @Setter
    private String builder;

    @Setter
    private Map<String, BeanElement> interfaces;
    @Setter
    private Map<String, List<FieldElement>> fields;
    @Setter
    private Map<String, MethodElement> methods;


    public void setCode(String code) {
        int i = code.lastIndexOf(".");
        this.code = code.substring(i + 1);
        this.pkg = code.substring(0, i);
    }

    public static BeanElement toBeanElement(String className, List<FunctionMetadata> functionMetadata) {
        BeanElement beanElement = new BeanElement();
        String classDescr = "";
        Map<String, MethodElement> methods = new HashMap<>();
        for (FunctionMetadata em : functionMetadata) {
            classDescr = em.getClassDescr();
            MethodElement methodElement = new MethodElement();
            methodElement.setCode(em.getMethodCode());
            methodElement.setHttpMethod(em.getHttpMethod());
            methodElement.setDescr(em.getMethodDescr());
            methodElement.setMethodUrl(em.getMethodUrl());
            List<ArgumentMetadata> inputs = em.getInputs();


            //methodElement.setInput();
            methodElement.setOutput(null);
            methods.put(em.getMethodCode(), methodElement);
        }
        beanElement.setCode(className);
        beanElement.setName(className);
        beanElement.setDescr(classDescr);
        beanElement.setMethods(methods);
        return beanElement;
    }


    public void buildArgs(FunctionMetadata em) {
        List<ArgumentMetadata> inputs = em.getInputs();


    }
}

