package org.dows.sdk.spider.elements;

import lombok.Data;

import java.util.Map;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/7/2024 11:59 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Data
public class PkgElement implements Element {
    private Integer elementType = 3;

    private String pgk;
    private String descr;
    // 类
    private Map<String, ClassElement> classes;
}

