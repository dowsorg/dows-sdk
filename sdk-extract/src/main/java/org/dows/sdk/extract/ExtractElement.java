package org.dows.sdk.extract;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.dows.sdk.annotations.Extract;
import org.dows.sdk.elements.ClassElement;
import org.dows.sdk.elements.FieldElement;
import org.dows.sdk.elements.MethodElement;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h3[@id='接口英文名']/following-sibling::p[1]/text()", handler = ClassExtractHandler.class)
    private ClassElement classElement;

    // 方法code
    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h3[@id='接口英文名']/following-sibling::p[1]/text()", handler = CodeExtractHandler.class)
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    private String code;
    // 方法名
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h1/text()")
    private String name;
    // 方法描述
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h3[@id='功能描述']/following-sibling::p[1]/text()", handler = DescrExtractHandler.class)
    private String descr;

    // 方法接口地址
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    @Extract(channel = "weixin", xpath = "//div[@class='language- extra-class']//code/text()", handler = MethodUrlExtractHandler.class)
    private String methodUrl;

    // http方法
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    @Extract(channel = "weixin", xpath = "//div[@class='language- extra-class']//code/text()", handler = HttpMethodExtractHandler.class)
    private String httpMethod;

    @Extract(channel = "weixin", xpath = "//h3[@id='请求参数']/following-sibling::div[1]/table//tr", handler = FieldExtractHandler.class)
    private Map<String, List<FieldElement>> input;
    @Extract(channel = "weixin", xpath = "//h3[@id='返回参数']/following-sibling::div[1]/table//tr")
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
            extractPojo.setName(declaredField.getName());
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

}

