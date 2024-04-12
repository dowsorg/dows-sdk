package org.dows.sdk.builder;

import lombok.Getter;
import org.dows.sdk.FileBuilder;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/12/2024 11:16 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
public class InterfaceBuilder implements FileBuilder {
    @Getter
    private String template = "interface.ftl";

    @Override
    public FileBuilder setTemplate(String template) {
        this.template = template;
        return this;
    }
}

