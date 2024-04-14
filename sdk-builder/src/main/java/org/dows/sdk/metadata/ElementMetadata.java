package org.dows.sdk.metadata;

import lombok.Data;
import org.dows.sdk.annotations.ElementMapping;
import org.dows.sdk.annotations.Extract;

import java.util.List;

@Data
public class ElementMetadata {

    //属性code
    @ElementMapping(channel = "weixin", name = "属性名")
    private String name;
    //参数类型
    @ElementMapping(channel = "weixin", name = "类型")
    private String type;
    //说明
    @ElementMapping(channel = "weixin", name = "说明")
    private String comment;
    //必填
    @ElementMapping(channel = "weixin", name = "必填")
    private Boolean nullable;
    // 默认值
    @ElementMapping(channel = "weixin", name = "默认值")
    private String defaultValue;

    private String length;

    // pkg或命名空间
    private String pkg;
    // 顺序
    private int seq;
    //
    private int io;
    // 集合[List,Set,map,array]
    private String assemble;
    // 孩子节点TreeNode ,Tree
    private List<ElementMetadata> childs;


    public String getRootXpath() {
        Extract extract = this.getClass().getAnnotation(Extract.class);
        return extract.xpath();
    }
}