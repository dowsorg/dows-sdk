package org.dows.sdk.builder;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.FileBuilder;
import org.dows.sdk.elements.BeanElement;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/13/2024 7:20 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Slf4j
@Component
@Data
public class BeanBuilder implements FileBuilder {
    private String template = "bean.ftl";

    private TemplateEngine templateEngine;


    @Override
    public FileBuilder setTemplate(String template) {
        this.template = template;
        return this;
    }

    public void build(BeanElement beanElement) {
        log.info("===={}", JSONUtil.toJsonPrettyStr(beanElement));
        Template tmp = templateEngine.getTemplate(template);
        Map<String, Object> data = new HashMap<>();
        File file = new File("");


        //render
        //tmp.render(data, file);
    }

    @Override
    public void build(String path, String ext) {
        log.info("===={}", path);
        Template tmp = templateEngine.getTemplate(template);
        Map<String, Object> data = new HashMap<>();
        File file = new File("");


        //render
        tmp.render(data, file);
    }
}

