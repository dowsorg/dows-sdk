package org.dows.sdk.spider.extract;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.spider.model.Catalog;
import org.dows.sdk.spider.schema.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ZfbPayExtracter implements Extractable {

    @Autowired
    private final TemplateEngine templateEngine;
    private static Map<String, String> map = new HashMap<>();

    static {
        map.put("overview", "//div[@class='overview']/*/text()");
        map.put("url", "//strong[contains(text(),'请求URL')]/parent::p/text()");
        map.put("httpMethod", "//strong[contains(text(),'请求方式')]/parent::p/text()");
        map.put("inputs", "//h3[contains(text(),'请求参数')]/following-sibling::div[@class='table-wrp']/table//tr");
        map.put("output", "//h3[contains(text(),'返回参数')]/following-sibling::div[@class='table-wrp']/table//tr");
        map.put("descr", "//div[@class='overview']/*/text()");
    }


    public void genSdk(String seed, String regex) {

        List<Catalog> catalogs = getCatalogs(seed, regex);


        List<ModuleSchema> moduleSchemas = new ArrayList<>();
        List<BeanSchema> beanSchemas = new ArrayList<>();

        ProjectSchema projectSchema = new ProjectSchema();
        projectSchema.setName("sdk-weixin");
        projectSchema.setRootPath("E:/workspace/java/projects/dows/dows-pay/pay-sdk1");
        projectSchema.setBasePkg("org.dows.sdk.weixin.pay");
        projectSchema.setModules(moduleSchemas);
        buildModuleSchema(catalogs, projectSchema, moduleSchemas, beanSchemas, null, null);


        Template apiSchemaTemplate = templateEngine.getTemplate("weixin-api-schema.ftl");
        Template template = templateEngine.getTemplate("api1.ftl");
        Template modelTemplate = templateEngine.getTemplate("param1.ftl");
        Template ymlTemplate = templateEngine.getTemplate("wexiapi.ftl");

        try {
            Path of = Path.of(projectSchema.getRootPath());
            Files.createDirectories(of);
            Map<String, Object> map = new HashMap<>();
            map.put("beanSchemas", beanSchemas);
            Files.write(of.resolve("weixin-api-schema.yml"), apiSchemaTemplate.render(map).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (BeanSchema beanSchema : beanSchemas) {
            String beanName = beanSchema.getName();
            String path = beanSchema.getPath();
            try {
                Files.createDirectories(Path.of(path));
                Files.write(Path.of(path + "/" + StrUtil.upperFirst(beanName) + ".java"),
                        template.render(BeanUtil.beanToMap(beanSchema)).getBytes());

                List<MethodSchema> methods = beanSchema.getMethods();
                for (MethodSchema method : methods) {
                    List<ParamSchema> inputs = method.getInputs();
                    for (ParamSchema input : inputs) {
                        Files.createDirectories(Path.of(path, "request"));
                        input.setPkg(beanSchema.getPkg() + ".request");
                        String request = modelTemplate.render(BeanUtil.beanToMap(input));
                        Files.write(Path.of(path + "/request/" + StrUtil.upperFirst(input.getType()) + ".java"), request.getBytes());
                    }
                    ParamSchema output = method.getOutput();
                    output.setPkg(beanSchema.getPkg() + ".response");
                    Files.createDirectories(Path.of(path, "response"));
                    Map<String, Object> stringObjectMap = BeanUtil.beanToMap(output);
                    String response = modelTemplate.render(stringObjectMap);
                    Files.write(Path.of(path + "/response/" + StrUtil.upperFirst(output.getType()) + ".java"), response.getBytes());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // 生成yml
        Map<String, List<BeanSchema>> collect = beanSchemas.stream().collect(Collectors.groupingBy(BeanSchema::getPkg));
        collect.forEach((k, v) -> {
            try {
                ModuleSchema moduleSchema = v.get(0).getModule();
                Files.createDirectories(Path.of(moduleSchema.getResourcesPath()));
                BeanUtil.beanToMap(v);
                Map<String, List<BeanSchema>> map = new HashMap<>();
                map.put("beanSchemas", v);
                String render = ymlTemplate.render(map);
                Files.write(Path.of(moduleSchema.getResourcesPath() + "/" + k.replaceAll("\\.", "-") + ".yml"), render.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private List<Catalog> getCatalogs(String seed, String regex) {
        List<Catalog> catalogs = new ArrayList<>();
        JXDocument jxDocument = JXDocument.create(getDocument(seed));

        String[] regexs = regex.split(",");
        List<JXNode> jxNodes = jxDocument.selN(regexs[0]);
        Long id1 = 1L;
        for (JXNode jxNode : jxNodes) {
            String menu1 = jxNode.selOne("/text()").asString();
            Catalog catalog = new Catalog();
            catalog.setName(menu1);
            catalog.setId(id1);
            catalog.setType("pkg");
            catalog.setPid(0L);
            id1++;
            catalogs.add(catalog);
            Catalog catalog2 = null;
            List<JXNode> sels = jxNode.sel(regexs[1]);
            for (JXNode sel : sels) {
                JXNode href = sel.selOne("/@href");
                JXNode text = sel.selOne("/text()");
                if (href.asString().startsWith("javascript")) {
                    catalog2 = new Catalog();
                    catalog2.setName(text.asString());
                    catalog2.setHref(href.asString());
                    catalog2.setType("bean");
                    catalog2.setId(id1);
                    catalog2.setPid(catalog.getId());
                    catalog2.setPatent(catalog);
                    catalog.addChild(catalog2);
                } else {
                    Catalog catalog3 = new Catalog();
                    catalog3.setName(text.asString());
                    catalog3.setHref(href.asString());
                    catalog3.setType("method");
                    catalog3.setId(id1);
                    if (catalog2 != null) {
                        catalog3.setPid(catalog2.getId());
                        catalog2.addChild(catalog3);
                        catalog3.setPatent(catalog2);
                    } else {
                        catalog3.setPid(catalog.getId());
                        catalog.addChild(catalog3);
                        catalog3.setPatent(catalog);
                    }
                }
                id1++;
            }
        }
        return catalogs;
    }


    private void buildModuleSchema(List<Catalog> catalogs,
                                   ProjectSchema projectSchema, List<ModuleSchema> moduleSchemas, List<BeanSchema> beanSchemas,
                                   ModuleSchema moduleSchema, BeanSchema beanSchema) {
        for (Catalog catalog : catalogs) {

            if (catalog.getName().contains("商户进件")) {
                continue;
            }
            if (catalog.getName().contains("经营能力")) {
                return;
            }
            if (catalog.getType().equals("pkg")) {
                catalog.setPkg(catalog.getName());
                moduleSchema = new ModuleSchema();
                //moduleSchema.setName(catalog.getName());
                moduleSchema.setPkg(catalog.getName());
                moduleSchema.setProjectSchema(projectSchema);

                moduleSchemas.add(moduleSchema);
            } else if (catalog.getType().equals("bean")) {
                beanSchema = new BeanSchema();
                beanSchema.setPkg(catalog.getName());
                beanSchema.setName(catalog.getName());
                if (moduleSchema != null) {
                    moduleSchema.addBeanSchema(beanSchema);
                    beanSchema.setModule(moduleSchema);
                }
                beanSchemas.add(beanSchema);
            } else {
                MethodSchema methodSchema = new MethodSchema();
                methodSchema.setName(catalog.getName());
                if (beanSchema != null) {
                    beanSchema.addMethod(methodSchema);
                }
                methodSchema.setDocUrl(catalog.getWxPayDocUrl());
                Document document = getDocument(catalog.getWxPayDocUrl());
                JXDocument jxDocument = JXDocument.create(document);
                map.forEach((k, v) -> {
                    List<JXNode> jxNodes = jxDocument.selN(v);

                    List<JXNode> ths = new ArrayList<>();
                    StringBuilder sb = new StringBuilder();
                    if (k.equalsIgnoreCase("inputs")) {

                        ParamSchema paramSchema = new ParamSchema();
                        paramSchema.setName(methodSchema.getName() + "Request");
                        paramSchema.setType(methodSchema.getName() + "Request");
                        paramSchema.setIot("in");
                        // 为method 设置 input入参列表
                        methodSchema.addInput(paramSchema);
                        for (JXNode jxNode : jxNodes) {
                            buildParamByTable(ths, paramSchema, jxNode);
                        }
                        paramSchema.setDocUrl(methodSchema.getDocUrl());
                    } else if (k.equalsIgnoreCase("output")) {
                        ParamSchema paramSchema = new ParamSchema();
                        paramSchema.setName(methodSchema.getName() + "Response");
                        paramSchema.setType(methodSchema.getName() + "Response");
                        paramSchema.setIot("out");
                        methodSchema.setOutput(paramSchema);
                        for (JXNode jxNode : jxNodes) {
                            buildParamByTable(ths, paramSchema, jxNode);
                        }
                        paramSchema.setDocUrl(methodSchema.getDocUrl());
                    } else {
                        for (JXNode jxNode : jxNodes) {
                            String string = jxNode.asString();
                            if (!StrUtil.isBlank(string)) {
                                sb.append(string + ",");
                            }
                        }
                        if(sb.length() != 0){
                            methodSchema.setFieldValue(k, sb.deleteCharAt(sb.length() - 1).toString().trim());
                        }
                    }
                    //log.info("jxNodes:{}", jxNodes);
                });
            }
            if (catalog.getChilds().size() != 0) {
                buildModuleSchema(catalog.getChilds(), projectSchema, moduleSchemas, beanSchemas, moduleSchema, beanSchema);
            }
        }
    }

    private void buildParamByTable(List<JXNode> ths, ParamSchema paramSchema, JXNode jxNode) {
        Element element = (Element) jxNode.value();
        if (element.tagName().equalsIgnoreCase("tr")) {
            // 处理表头
            if (ths.isEmpty()) {
                ths.addAll(jxNode.sel("th"));
            }

            List<JXNode> tds = jxNode.sel("td");
            if (tds.size() != ths.size()) {
                return;
            }

            FieldSchema fieldSchema = new FieldSchema();
            paramSchema.addField(fieldSchema);

            Map<String, Field> fields = fieldSchema.getWexinPayFieldMap();
            for (int i = 0; i < ths.size(); i++) {
                Field field = fields.get(ths.get(i).selOne("/text()").asString());
                if (field != null) {
                    field.setAccessible(true);
                    try {
                        JXNode jxNode1 = tds.get(i);
                        JXNode jxNode2 = jxNode1.selOne("/text()");
                        if (jxNode1 != null && jxNode2 != null) {
                            field.set(fieldSchema, jxNode2.asString());
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

}
