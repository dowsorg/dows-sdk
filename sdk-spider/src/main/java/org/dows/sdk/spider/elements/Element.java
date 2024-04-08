package org.dows.sdk.spider.elements;

import cn.hutool.core.bean.BeanUtil;

import java.util.Map;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/7/2024 10:40 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
public interface Element {

    default String[] fieldFields() {
        return new String[]{"code", "type", "descr", "required", "def", "index", "io", "elementType", "collectionType"};
    }

    default String[] methodFields() {
        return new String[]{"methodCode","methodName","methodDescr","methodUrl","httpMethod","index","elementType"};
    }

    Integer getElementType();

    /**
     * 将元素转换为map
     *
     * @return
     */
    default Map<String, Object> toMap(String... properties) {
        return BeanUtil.beanToMap(this, properties);
    }
}
