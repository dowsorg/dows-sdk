package org.dows.sdk.elements;

import cn.hutool.extra.template.Template;
import lombok.Data;

import java.util.Map;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/11/2024 2:18 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Data
public class ProjectElement {
    // 项目名
    private String name;
    // 根路径
    private String root;
    // 基础包名
    private String basePkg;
    // 模块
    private Map<String, ModuleElement> modules;

}

