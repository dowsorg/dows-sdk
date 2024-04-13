package org.dows.sdk.extract;

import lombok.Data;
import org.dows.sdk.annotations.ElementMapping;
import org.dows.sdk.annotations.Extract;

import java.util.List;

@Data
public class ArgumentMetadata {
    //属性code
    @ElementMapping(channel = "weixin", name = "属性")
    private String code;
    //参数类型
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
    // "code","type","descr","required","def","index","io","collectionType"
    // pkg
    private String pkg;
    // 顺序
    private int index;
    // 输入输出[0:input, 1:output]
    private int io;
    // 元素类型
    private Integer elementType = 0;
    // 集合[List,Set,map,array]
    private String assemble;

    private List<ArgumentMetadata> childs;

    public String getRootXpath() {
        Extract extract = this.getClass().getAnnotation(Extract.class);
        return extract.xpath();
    }
}