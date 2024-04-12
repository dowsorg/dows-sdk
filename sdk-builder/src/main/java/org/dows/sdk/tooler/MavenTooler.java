package org.dows.sdk.tooler;

import cn.hutool.extra.template.TemplateEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.BuildTooler;
import org.dows.sdk.BuilderProperties;
import org.dows.sdk.BuilderSetting;
import org.dows.sdk.FileBuilder;
import org.dows.sdk.elements.ClassElement;
import org.dows.sdk.extract.ExtractMetadata;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
public class MavenTooler implements BuildTooler {

    private final TemplateEngine templateEngine;

    private final BuilderProperties builderProperties;

    private final Map<String, FileBuilder> fileBuilders;

    public void build(String platform, List<ExtractMetadata> extractMetadata) {
        BuilderSetting builderSetting = builderProperties.getBuilders().get(platform);
        if (null != builderSetting) {
            // 分组
            Map<String, List<ExtractMetadata>> classMetas = extractMetadata.stream()
                    .collect(Collectors.groupingBy(ExtractMetadata::getClazzCode));
            Set<String> classNames = classMetas.keySet();

            List<ClassElement> classElements = new ArrayList<>();
            classNames.stream().parallel().forEach(className -> {
                ClassElement classElement = ClassElement.toElement(className,classMetas.get(className));
                classElements.add(classElement);
            });

            classElements.stream().parallel().forEach(cm -> {
                FileBuilder fileBuilder = fileBuilders.get(cm.getBuilder());
                fileBuilder.build(cm);
            });

        }

    }

}

