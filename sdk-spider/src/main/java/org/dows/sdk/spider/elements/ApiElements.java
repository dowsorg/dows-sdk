package org.dows.sdk.spider.elements;

import lombok.Data;
import org.dows.sdk.extract.Extract;

import java.util.List;

//alipay:(//div[@class="ant-tree-list-holder-inner"]//a[contains(@href, "_")])
@Data
//aside[@class='sidebar']/div/div/ul/li//a
public class ApiElements {

    // 接口文档
    private String docUrl;

    // 包路径
//    @Extract(channel = "alipay", xpath = "")
//    @Extract(channel = "douyin", xpath = "")
//    @Extract(channel = "weixin", xpath = "//div[@class='Breadcrumb']//span[@class='breadcrumb-inner is-link']")
    private String pkg;

    // class
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    @Extract(channel = "weixin", xpath = "//div[@class='Breadcrumb']//span[@class='breadcrumb-inner is-link']")
    private String clazz;
    // 类描述
    @Extract(channel = "weixin", xpath = "//div[@class='Breadcrumb']//span[@class='breadcrumb-inner is-link']")
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    private String clazzDescr;

    // 方法code
    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h3[@id='接口英文名']/following-sibling::*[1]/text()")
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    private String methodCode;
    // 方法名
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h1/text()")
    private String methodName;
    // 方法描述
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    @Extract(channel = "weixin", xpath = "//div[@class='content custom']/h3[@id='功能描述']/following-sibling::*[1]/text()")
    private String methodDescr;

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


    // 入参
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    @Extract(channel = "weixin", xpath = "//h3[@id='请求参数']/following-sibling::div[1]/table//tr")
    private List<ParamElements> inputs;
    // 出参
    @Extract(channel = "alipay", xpath = "")
    @Extract(channel = "douyin", xpath = "")
    @Extract(channel = "weixin", xpath = "//h3[@id='返回参数']/following-sibling::div[1]/table//tr")
    private List<ParamElements> outputs;

}
