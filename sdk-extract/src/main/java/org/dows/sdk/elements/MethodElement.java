package org.dows.sdk.elements;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.annotations.Extract;
import org.dows.sdk.extract.ExtractPojo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class MethodElement implements Element {

    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h3[@id='接口英文名']/following-sibling::*[1]/text()")
    private ClassElement classElement;

    // 方法code
    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h3[@id='接口英文名']/following-sibling::*[1]/text()")
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
    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h3[@id='功能描述']/following-sibling::*[1]/text()")
    private String descr;

    // 方法接口地址
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    @Extract(channel = "weixin", xpath = "//div[@class='language- extra-class']//code/text()")
    private String methodUrl;

    // http方法
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    @Extract(channel = "weixin", xpath = "//div[@class='language- extra-class']//code/text()")
    private String httpMethod;

    @Extract(channel = "weixin", xpath = "//h3[@id='请求参数']/following-sibling::div[1]/table//tr")
    private Map<String, List<FieldElement>> input;
    @Extract(channel = "weixin", xpath = "//h3[@id='返回参数']/following-sibling::div[1]/table//tr")
    private Map<String, List<FieldElement>> output;

    // 顺序
    private int index;
    // 元素类型[1:方法]
    private Integer elementType = 1;


    public static void main(String[] args) {
        MethodElement methodElement = new MethodElement();
        List<ExtractPojo> weixin = methodElement.getXpath("weixin");
        System.out.println(JSONUtil.toJsonPrettyStr(weixin));
    }

    public List<ExtractPojo> getXpath(String platform) {
        Map<String, Extract> map = new HashMap<>();
        List<ExtractPojo> extractPojos = new ArrayList<>();
        for (Field declaredField : this.getClass().getDeclaredFields()) {
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
                System.out.println(declaredField.getName() + ":" + extract.xpath());
            }
        }
        return extractPojos;
    }
}
