package org.dows.sdk;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.extract.ExtractElement;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/11/2024 2:57 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MavenBuilder implements SdkBuilder {

    private final TemplateEngine templateEngine;

    public void build(String platform, List<ExtractElement> extractElements) {
        for (ExtractElement extractElement : extractElements) {
            Template apiSchemaTemplate = templateEngine.getTemplate("weixin-api-schema.ftl");
            Template template = templateEngine.getTemplate("api1.ftl");
            Template modelTemplate = templateEngine.getTemplate("param1.ftl");
            Template ymlTemplate = templateEngine.getTemplate("wexiapi.ftl");
            try {
                /*Path of = Path.of(projectSchema.getRootPath());
                Files.createDirectories(of);
                Map<String, Object> map = new HashMap<>();
                map.put("beanSchemas", beanSchemas);
                Files.write(of.resolve("weixin-open-schema.yml"), apiSchemaTemplate.render(map).getBytes());*/
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}

