package org.dows.sdk.spider.elements;

import lombok.Data;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/7/2024 11:59 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Data
public class ClassElement implements Element {
    private final Integer elementType = 2;
    private String pgk;
    private String name;
    private String code;
    private String descr;
    // 顺序
    private int index;
}

