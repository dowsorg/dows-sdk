package org.dows.sdk.spider.elements;

import lombok.Data;
import org.dows.sdk.extract.ElementMapping;

@Data
public class FieldElement implements Element {

    private FieldElement name;
    //属性code
    @ElementMapping(channel = "weixin", name = "属性")
    private String code;
    //类型
    @ElementMapping(channel = "weixin", name = "类型")
    private String type;
    //说明
    @ElementMapping(channel = "weixin", name = "说明")
    private String descr;
    //必填
    @ElementMapping(channel = "weixin", name = "必填")
    private Boolean required;
    // 默认值
    @ElementMapping(channel = "weixin", name = "默认值")
    private String def;
}
