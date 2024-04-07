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

    /**
     * 将元素转换为map
     *
     * @return
     */
    default Map<String, Object> toMap(String... properties) {
        return BeanUtil.beanToMap(this, properties);
    }
}
