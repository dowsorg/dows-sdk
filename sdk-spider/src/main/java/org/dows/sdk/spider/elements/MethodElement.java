package org.dows.sdk.spider.elements;

import lombok.Data;
import org.dows.sdk.extract.Extract;

import java.util.List;

@Data
public class MethodElement implements Element {

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

    //"methodCode","methodName","methodDescr","methodUrl","httpMethod","index","",""
    // 顺序
    private int index;

    private Integer elementType = 1;
/*    private List<FieldElement> inputs;
    private FieldElement output;*/

}
