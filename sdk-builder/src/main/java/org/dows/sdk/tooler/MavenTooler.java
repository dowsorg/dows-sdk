package org.dows.sdk.tooler;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.BuildTooler;
import org.dows.sdk.BuilderProperties;
import org.dows.sdk.BuilderSetting;
import org.dows.sdk.FileBuilder;
import org.dows.sdk.builder.ApiBuilder;
import org.dows.sdk.builder.ModuleBuilder;
import org.dows.sdk.builder.RestBuilder;
import org.dows.sdk.elements.BeanElement;
import org.dows.sdk.metadata.FunctionMetadata;
import org.dows.sdk.model.DatabaseModel;
import org.dows.sdk.model.FunctionModel;
import org.dows.sdk.model.ProjectModel;
import org.dows.sdk.model.TableModel;
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


    private final ModuleBuilder moduleBuilder;

    public void build(String platform, List<FunctionMetadata> functionMetadata) {
        BuilderSetting builderSetting = builderProperties.getBuilders().get(platform);
        if (null != builderSetting) {
            // 分组
            Map<String, List<FunctionMetadata>> classMetas = functionMetadata.stream()
                    .collect(Collectors.groupingBy(FunctionMetadata::getClazzCode));
            Set<String> classNames = classMetas.keySet();

            List<BeanElement> beanElements = new ArrayList<>();
            classNames.stream().parallel().forEach(className -> {
                BeanElement beanElement = BeanElement.toBeanElement(className, classMetas.get(className));
                beanElements.add(beanElement);
            });
            List<Class<? extends FileBuilder>> builders = builderSetting.getBuilders();
            moduleBuilder.initModule(builderSetting);
            log.info("===={}", JSONUtil.toJsonPrettyStr(builderSetting));

            beanElements.stream().parallel().forEach(cm -> {
                /*String beanName = StrUtil.lowerFirst(cm.getBuilder().getSimpleName());
                FileBuilder fileBuilder = fileBuilders.get(beanName);
                fileBuilder.build(cm, builderSetting);*/
            });

        }

    }

    public void build(String platform, ProjectModel projectModel) {

        BuilderSetting builderSetting = builderProperties.getBuilders().get(platform);
        if (null != builderSetting) {

            List<DatabaseModel> databases = projectModel.getDatabases();

            for (DatabaseModel database : databases) {
                List<TableModel> tables = database.getTables();
                for (TableModel table : tables) {

                }
            }


            List<FunctionModel> functions = projectModel.getFunctions();
            // 分组
            Map<String, List<FunctionModel>> classMetas = functions.stream()
                    .collect(Collectors.groupingBy(FunctionModel::getClazzCode));
            Set<String> classNames = classMetas.keySet();


            List<Class<? extends FileBuilder>> builderClass = new ArrayList<>();
            builderClass.add(ApiBuilder.class);
            builderClass.add(RestBuilder.class);
            List<BeanElement> beanElements = new ArrayList<>();
            classNames.stream().parallel().forEach(className -> {
                BeanElement beanElement = BeanElement.toFunctionBeanElement(className, classMetas.get(className));
                beanElement.setBuilders(builderClass);
                beanElements.add(beanElement);
            });


            moduleBuilder.initModule(builderSetting);
            log.info("===={}", JSONUtil.toJsonPrettyStr(builderSetting));

            beanElements.stream().parallel().forEach(beanElement -> {
                List<Class<? extends FileBuilder>> builders = beanElement.getBuilders();
                for (Class<? extends FileBuilder> builder : builders) {
                    FileBuilder fileBuilder = SpringUtil.getBean(builder);
                    fileBuilder.build(beanElement, builderSetting);
                }
            });
        }

    }
}

