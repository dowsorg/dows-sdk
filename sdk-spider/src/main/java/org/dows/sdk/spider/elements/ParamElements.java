package org.dows.sdk.spider.elements;

import lombok.Data;
import org.dows.sdk.extract.FieldMapping;

@Data
public class ParamElements {

    private ParamElements name;

    //属性code
    @FieldMapping(channel = "weixin", name = "属性")
    private String code;
    //类型
    @FieldMapping(channel = "weixin", name = "类型")
    private String type;
    //说明
    @FieldMapping(channel = "weixin", name = "说明")
    private String descr;
    //必填
    @FieldMapping(channel = "weixin", name = "必填")
    private Boolean required;
}