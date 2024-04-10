package org.dows.sdk.elements;

import cn.hutool.core.bean.BeanUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        return new String[]{"code", "type", "descr", "required", "def", "index", "io", "elementType", "pkg", "collectionType"};
    }

    default String[] methodFields() {
        return new String[]{"methodCode", "methodName", "methodDescr", "methodUrl", "httpMethod", "index", "elementType"};
    }

    Integer getElementType();

    /**
     * 将元素转换为map
     *
     * @return
     */
    default Map<String, Object> toMap(String... properties) {
        Map<String, Object> map = BeanUtil.beanToMap(this, properties);

        return map.entrySet().stream()
                .filter(o -> o.getValue() != null)
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
