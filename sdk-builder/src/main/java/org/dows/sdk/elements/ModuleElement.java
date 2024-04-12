package org.dows.sdk.elements;

import cn.hutool.extra.template.Template;
import lombok.Data;

import java.util.Map;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/11/2024 2:22 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Data
public class ModuleElement {

    private Map<String, Template> templates;
}

