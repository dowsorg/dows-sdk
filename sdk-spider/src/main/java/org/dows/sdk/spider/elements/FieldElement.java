package org.dows.sdk.spider.elements;

import lombok.Data;
import org.dows.sdk.extract.ElementMapping;

import java.util.List;

@Data
public class FieldElement implements Element {

    //private Map<FieldElement, List<FieldElement>> fields;
    private List<FieldElement> fields;
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
    // "code","type","descr","required","def","index","io","collectionType"
    // 顺序
    private int index;
    // 输入输出[0:input, 1:output]
    private int io;
    // 元素类型
    private Integer elementType = 0;
    // 集合类型[0:List,1:Set,2:map,3:array]
    private Integer collectionType;


}
