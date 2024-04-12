package org.dows.sdk.builder;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateEngine;
import lombok.Data;
import org.dows.sdk.FileBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/12/2024 10:48 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Component
@Data
public class EntityBuilder implements FileBuilder {

    private String template = "entity.ftl";

    private TemplateEngine templateEngine;


    @Override
    public FileBuilder setTemplate(String template) {
        this.template = template;
        return this;
    }

    @Override
    public void build(String path, String ext) {
        Template tmp = templateEngine.getTemplate(template);
        Map<String, Object> data = new HashMap<>();
        File file = new File("");


        //render
        tmp.render(data, file);
    }
}

