package org.dows.sdk.model;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.dows.sdk.annotations.Extract;
import org.dows.sdk.extract.ExtractPojo;
import org.dows.sdk.extract.ExtractProperties;
import org.dows.sdk.extract.ExtractSetting;
import org.dows.sdk.extract.Extractor;
import org.dows.sdk.metadata.ElementMetadata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/10/2024 11:55 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Data
public class FunctionModel {
    // 类
    private String clazzCode;
    // 类描述
    private String classDescr;
    // 方法code
    private String methodCode;
    // 方法名
    private String methodName;
    // 方法描述
    private String methodDescr;
    // 方法接口地址
    private String methodUrl;
    // http方法
    private String httpMethod;
    // 文档地址
    private String docUrl;
    // 入参
    private List<ElementMetadata> inputs;
    // 出参
    private ElementMetadata output;

    public static void main(String[] args) {
        FunctionModel methodElement = new FunctionModel();
        //List<ExtractPojo> weixin = methodElement.getXpath("weixin");
        System.out.println(JSONUtil.toJsonStr(methodElement, JSONConfig.create().setIgnoreNullValue(false)));
    }

    public static List<ExtractPojo> getExtract(String platform) {
        FunctionModel functionMetadata = new FunctionModel();
        return functionMetadata.getXpath(platform);
    }

    public List<ExtractPojo> getXpath(String platform) {
        List<ExtractPojo> extractPojos = new ArrayList<>();
        Field[] declaredFields = FunctionModel.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            ExtractPojo extractPojo = new ExtractPojo();
            extractPojo.setField(declaredField);
            extractPojo.setElement(this);
            extractPojo.setFieldName(declaredField.getName());
            Extract[] annotationsByType = declaredField.getAnnotationsByType(Extract.class);
            for (Extract extract : annotationsByType) {
                if (extract.channel().equalsIgnoreCase(platform)) {
                    extractPojo.setExtract(extract);
                    extractPojos.add(extractPojo);
                }
            }
        }
        return extractPojos;
    }

    /**
     * 通过配置方式
     *
     * @param platform
     * @return
     */
    public List<ExtractPojo> toExtracts(String platform) {
        ExtractProperties extractProperties = SpringUtil.getBean(ExtractProperties.class);
        Map<String, ExtractSetting> extractors = extractProperties.getExtractors();
        ExtractSetting extractSetting = extractors.get(platform);
        List<Extractor> extracts = extractSetting.getExtractors();
        Map<String, Extractor> extractorMap = new HashMap<>();
        for (Extractor extract : extracts) {
            String name = extract.getName();
            extractorMap.put(name, extract);
        }
        List<ExtractPojo> extractPojos = new ArrayList<>();
        Field[] declaredFields = FunctionModel.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            Extractor extractor = extractorMap.get(name);
            if (extractor != null) {
                ExtractPojo extractPojo = new ExtractPojo();
                extractPojo.setElement(this);
                extractPojo.setField(declaredField);
                extractPojo.setFieldName(name);
                extractPojo.setExtractor(extractor);
                extractPojos.add(extractPojo);
            }
        }
        return extractPojos;
    }


}

