package org.dows.sdk.extract;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.dows.sdk.annotations.Extract;
import org.dows.sdk.elements.ClassElement;
import org.dows.sdk.elements.FieldElement;
import org.dows.sdk.elements.MethodElement;

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
public class ExtractElement {
    //@Extract(channel = "weixin", xpath = "//div[@class='content custom']/h3[@id='接口英文名']/following-sibling::p[1]/text()", handler = WexinClassExtractor.class)
    private ClassElement classElement;

    // 方法code
//    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h3[@id='接口英文名']/following-sibling::p[1]/text()", handler = WexinCodeExtractor.class)
//    @Extract(channel = "alipay", xpath = "")
//    @Extract(channel = "douyin", xpath = "")
    private String code;
    // 方法名
//    @Extract(channel = "alipay", xpath = "")
//    @Extract(channel = "douyin", xpath = "")
//    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h1/text()")
    private String name;
    // 方法描述
//    @Extract(channel = "alipay", xpath = "")
//    @Extract(channel = "douyin", xpath = "")
//    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h3[@id='功能描述']/following-sibling::p[1]/text()", handler = WeixinDescrExtractor.class)
    private String descr;

    // 方法接口地址
//    @Extract(channel = "alipay", xpath = "")
//    @Extract(channel = "douyin", xpath = "")
//    @Extract(channel = "weixin", xpath = "//div[@class='language- extra-class']//code/text()", handler = WeixinMethodUrlExtractor.class)
    private String methodUrl;

    // http方法
//    @Extract(channel = "alipay", xpath = "")
//    @Extract(channel = "douyin", xpath = "")
//    @Extract(channel = "weixin", xpath = "//div[@class='language- extra-class']//code/text()", handler = WeixinHttpMethodExtractor.class)
    private String httpMethod;

//    @Extract(channel = "weixin", xpath = "//h3[@id='请求参数']/following-sibling::div[1]/table//tr", handler = WeixinFieldExtractor.class)
    private Map<String, List<FieldElement>> input;
//    @Extract(channel = "weixin", xpath = "//h3[@id='返回参数']/following-sibling::div[1]/table//tr")
    private Map<String, List<FieldElement>> output;


    public static void main(String[] args) {
        MethodElement methodElement = new MethodElement();
        List<ExtractPojo> weixin = methodElement.getXpath("weixin");
        System.out.println(JSONUtil.toJsonPrettyStr(weixin));
    }

    public static List<ExtractPojo> getExtract(String platform) {
        ExtractElement extractElement = new ExtractElement();
        return extractElement.getXpath(platform);
    }

    public List<ExtractPojo> getXpath(String platform) {
        List<ExtractPojo> extractPojos = new ArrayList<>();
        Field[] declaredFields = ExtractElement.class.getDeclaredFields();
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
     * @param platform
     * @return
     */
    public List<ExtractPojo> getXpath1(String platform) {
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
        Field[] declaredFields = ExtractElement.class.getDeclaredFields();
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

