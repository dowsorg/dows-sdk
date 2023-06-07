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
public class DyOpenExtracter implements Extractable {

    private static final Map<String, String> map = new HashMap<>();

    static {
        map.put("overview", "//div[@class='overview']/*/text()");
        map.put("url", "//strong[contains(text(),'请求URL：')]/parent::p/text()");
        map.put("httpMethod", "(//strong[contains(text(),'请求方式：')])[1]/parent::p/text()");
        map.put("inputs", "//h3[contains(text(),'请求参数')]/following-sibling::div[@class='table-wrp']/table//tr");
        map.put("output", "//h3[contains(text(),'返回参数')]/following-sibling::div[@class='table-wrp']/table//tr");
        map.put("descr", "//div[@class='overview']/*/text()");
    }

    private final TemplateEngine templateEngine;

    public void genSdk(String seed, String regex) {

        List<Catalog> catalogs = getCatalogs(seed, regex);


        List<ModuleSchema> moduleSchemas = new ArrayList<>();
        List<BeanSchema> beanSchemas = new ArrayList<>();

        ProjectSchema projectSchema = new ProjectSchema();
        projectSchema.setName("sdk-weixin");
        projectSchema.setRootPath("E:/workspaces/java/projects/dows/dows-pay/pay-sdk1");
        projectSchema.setBasePkg("org.dows.sdk.douyin");
        projectSchema.setModules(moduleSchemas);
        buildModuleSchema(catalogs, projectSchema, moduleSchemas, beanSchemas);


        Template apiSchemaTemplate = templateEngine.getTemplate("weixin-api-schema.ftl");
        Template template = templateEngine.getTemplate("api1.ftl");
        Template modelTemplate = templateEngine.getTemplate("param1.ftl");
        Template ymlTemplate = templateEngine.getTemplate("wexiapi.ftl");
//        genApiYml(beanSchemas, apiSchemaTemplate);

        try {
            Path of = Path.of(projectSchema.getRootPath());
            Files.createDirectories(of);
            Map<String, Object> map = new HashMap<>();
            map.put("beanSchemas", beanSchemas);
            Files.write(of.resolve("douyin-api-schema.yml"), apiSchemaTemplate.render(map).getBytes());
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
        String prefix = "/docs/resource/zh-CN/mini-app/develop/server/";
        List<Catalog> catalogs = new ArrayList<>();
        JXDocument jxDocument = JXDocument.create(getDocument(seed));
        String[] regexs = regex.split(",");
        List<JXNode> jxNodes = jxDocument.selN(regexs[0]);
        Long id1 = 1L;

        Map<String, Catalog> beanCatalogMap = new HashMap<>();
        Map<String, Catalog> pkgCatalogMap = new HashMap<>();
        for (JXNode jxNode : jxNodes) {

            Catalog methodCatalog = null;
            Catalog beanCatalog = null;
            Catalog pkgCatalog = null;
            String href = jxNode.selOne("/@href").asString();
            String hrefName = jxNode.selOne(regexs[1]).selOne("/text()").asString();
            String pkgBeanMethodString = href.substring(prefix.length());

            int methodIndex = pkgBeanMethodString.lastIndexOf("/");



            String pkgBeanString = pkgBeanMethodString.substring(0, methodIndex);
            int beanIndex = pkgBeanString.lastIndexOf("/");



            if ( beanIndex != -1) {

                String pkg = pkgBeanMethodString.substring(0, beanIndex);
                if(!StrUtil.isBlank(pkg)){
                    String javaPkg = pkg.replaceAll("/", ".");

                    pkgCatalog = pkgCatalogMap.get(javaPkg);
                    if (pkgCatalog == null) {
                        pkgCatalog = new Catalog();
                        pkgCatalog.setId(id1);
                        pkgCatalog.setPid(0L);
                        pkgCatalog.setPkg(javaPkg);
                        pkgCatalog.setType("pkg");
                        pkgCatalogMap.put(javaPkg, pkgCatalog);
                        //catalogs.add(catalogs.size() - 1, pkgCatalog);
                        catalogs.add(pkgCatalog);
                        id1++;
                    }
                    /*if (beanCatalog != null) {
                        beanCatalog.setPatent(pkgCatalog);
                        beanCatalog.setPid(pkgCatalog.getId());
                        pkgCatalog.addChild(beanCatalog);

                    }*/
                }

            }

            if (!StrUtil.isBlank(pkgBeanString)) {
                String beanName = null;
                if (beanIndex != -1) {
                    beanName = pkgBeanString.substring(beanIndex + 1);
                } else {
                    beanName = pkgBeanString;
                }
                beanCatalog = beanCatalogMap.get(beanName);
                if (beanCatalog == null) {
                    beanCatalog = new Catalog();
                    beanCatalog.setId(id1);
                    beanCatalog.setName(beanName);
                    beanCatalog.setCode(beanName);
                    beanCatalog.setType("bean");
                    beanCatalog.setPid(0L);

                    beanCatalogMap.put(beanName, beanCatalog);
                    //catalogs.add(catalogs.size() - 1, beanCatalog);
                    catalogs.add(beanCatalog);
                    id1++;
                }
                if (pkgCatalog != null) {
                    beanCatalog.setPatent(pkgCatalog);
                    beanCatalog.setPid(pkgCatalog.getId());
                    pkgCatalog.addChild(beanCatalog);
                }
               /* if (methodCatalog != null) {
                    methodCatalog.setPid(beanCatalog.getId());
                    methodCatalog.setPatent(beanCatalog);
                    beanCatalog.addChild(methodCatalog);
                    beanCatalog.setPid(pkgCatalog.getId());
                }*/
            }

            if (methodIndex != -1) {
                String methodCode = pkgBeanMethodString.substring(methodIndex + 1);
                methodCatalog = new Catalog();
                methodCatalog.setName(hrefName);
                methodCatalog.setCode(methodCode);
                methodCatalog.setType("method");
                methodCatalog.setHref(href);
                methodCatalog.setId(id1);
                id1++;
                if (beanCatalog != null) {
                    methodCatalog.setPid(beanCatalog.getId());
                    methodCatalog.setPatent(beanCatalog);
                    beanCatalog.addChild(methodCatalog);
                }
                catalogs.add(methodCatalog);
            }

        }
        return catalogs;
    }


    /*private List<Catalog> getCatalogs(String seed, String regex) {
        List<Catalog> catalogs = new ArrayList<>();


        JXDocument jxDocument = JXDocument.create(getDocument(seed));

        String[] regexs = regex.split(",");
        List<JXNode> jxNodes = jxDocument.selN(regexs[0]);

        Map<String, Catalog> catalogMap = new HashMap<>();
        Long id1 = 1L;
        for (JXNode jxNode : jxNodes) {
            String menu1 = jxNode.selOne(regexs[1] + "/text()").asString();
            Catalog catalog = catalogMap.get(menu1);
            if (catalog == null) {
                catalog = new Catalog();
                catalog.setName(menu1);
                catalog.setId(id1);
                catalog.setType("bean");
                catalog.setPid(0L);
                id1++;
                catalogs.add(catalog);
                catalogMap.put(menu1, catalog);
            }

            Catalog catalog2 = new Catalog();
            catalog2.setName(jxNode.selOne("//span[@title]/text()").asString());
            catalog2.setHref(jxNode.selOne("/@href").asString());
            catalog2.setType("method");
            catalog2.setId(id1);
            catalog2.setPid(catalog.getId());
            catalog2.setPatent(catalog);
            catalog.addChild(catalog2);
            id1++;
        }
        return catalogs;
    }*/

   /* private List<Catalog> getCatalogs(String seed, String regex) {
        List<Catalog> catalogs = new ArrayList<>();
        JXDocument jxDocument = JXDocument.create(getDocument(seed));
        String[] regexs = regex.split(",");
        List<JXNode> jxNodes = jxDocument.selN(regexs[0]);
        Long id1 = 1L;
        Catalog catalogPkg = null;
        for (JXNode jxNode : jxNodes) {
            Catalog catalogBean = null;
            List<JXNode> methodNodes = jxNode.sel("li/span/a");
            if (methodNodes.size() != 0) {
                JXNode beanNode = jxNode.selOne("../..//a");
                if (beanNode != null) {
                    // bean
                    log.info("bean:{}", beanNode.asString());
                    catalogBean = new Catalog();
                    catalogBean.setName(beanNode.selOne("/text()").asString());
                    catalogBean.setId(id1);
                    catalogBean.setType("bean");
                    catalogBean.setPatent(catalogPkg);
                    if (catalogPkg != null) {
                        catalogBean.setPid(catalogPkg.getId());
                    } else {
                        catalogBean.setPid(0L);
                    }
                    id1++;
                    catalogs.add(catalogBean);
                }
                // method
                for (JXNode methodNode : methodNodes) {
                    log.info("method:{}", methodNode.asString());
                    Catalog catalogMethod = new Catalog();
                    catalogMethod.setName(methodNode.selOne("/text()").asString());
                    catalogMethod.setHref(methodNode.selOne("/@href").asString());
                    catalogMethod.setType("method");
                    catalogMethod.setId(id1);
                    catalogMethod.setPid(catalogBean.getId());
                    catalogMethod.setPatent(catalogBean);
                    id1++;
                    catalogBean.addChild(catalogMethod);
                    catalogs.add(catalogMethod);
                }
            } else {
                // pkg
                JXNode pkg = jxNode.selOne("../..//a");
                if (pkg != null) {
                    log.info("pkg:{}", pkg.asString());
                    catalogPkg = new Catalog();
                    catalogPkg.setName(pkg.selOne("/text()").asString());
                    catalogPkg.setId(id1);
                    catalogPkg.setType("pkg");
                    catalogPkg.setPid(0L);
                    id1++;
                    if (catalogBean != null) {
                        catalogPkg.addChild(catalogBean);
                    }
                    catalogs.add(catalogPkg);
                }
            }
        }
        return catalogs;
    }
    */


    private void buildModuleSchema(List<Catalog> catalogs, ProjectSchema projectSchema,
                                   List<ModuleSchema> moduleSchemas, List<BeanSchema> beanSchemas) {


        Map<String, ModuleSchema> moduleSchemaMap = new HashMap<>();
        Map<String, BeanSchema> beanSchemaMap = new HashMap<>();

        for (Catalog catalog : catalogs) {
            log.info("catalogType:{}, module:{} ,bean:{}", catalog.getType(), catalog.getName(), catalog.getFullName());
            /*if (catalog.getName().contains("商户进件")) {
                continue;
            }
            if (catalog.getName().contains("经营能力")) {
                return;
            }
            if (catalog.getName().contains("代商家管理小程序")) {
                return;
            }*/
            if (catalog.getType().equals("pkg")) {

                ModuleSchema moduleSchema = new ModuleSchema();
                moduleSchema.setName(catalog.getName());
                //moduleSchema.setPkg(catalog.getName());
                moduleSchema.setProjectSchema(projectSchema);

                if (catalog.getPatent() == null) {
                    moduleSchema.setProjectSchema(projectSchema);
                    moduleSchemas.add(moduleSchema);
                } else {
                    ModuleSchema parentModuleSchema = moduleSchemaMap.get(catalog.getPatent().getName());
                    parentModuleSchema.addModule(moduleSchema);
                }
                moduleSchemaMap.put(catalog.getName(), moduleSchema);
            } else if (catalog.getType().equals("bean")) {

                BeanSchema beanSchema = new BeanSchema();
                beanSchema.setPkg(catalog.getName());
                beanSchema.setName(catalog.getName());

                if (catalog.getPatent() == null) {
                    ModuleSchema moduleSchema = new ModuleSchema();
                    moduleSchema.setProjectSchema(projectSchema);
                    moduleSchema.setName(catalog.getName());
                    moduleSchema.addBeanSchema(beanSchema);
                    moduleSchemas.add(moduleSchema);
                    beanSchema.setModule(moduleSchema);
                } else {
                    ModuleSchema moduleSchema = moduleSchemaMap.get(catalog.getPatent().getName());
                    moduleSchema.addBeanSchema(beanSchema);
                    beanSchema.setModule(moduleSchema);
                }

                beanSchemas.add(beanSchema);
                beanSchemaMap.put(catalog.getName(), beanSchema);
            } else if (catalog.getType().equals("method")) {
                BeanSchema beanSchema = beanSchemaMap.get(catalog.getPatent().getName());
                if (beanSchema == null) {
                    continue;
                }
                MethodSchema methodSchema = new MethodSchema();
                methodSchema.setName(catalog.getName());
                methodSchema.setDocUrl(catalog.getWxOpenDocUrl());
                if (beanSchema != null) {
                    beanSchema.addMethod(methodSchema);
                }

                /*Document document = getDocument(catalog.getWxOpenDocUrl());
                JXDocument jxDocument = JXDocument.create(document);
                map.forEach((k, v) -> {
                    List<JXNode> jxNodes = jxDocument.selN(v);

                    List<JXNode> ths = new ArrayList<>();
                    StringBuilder sb = new StringBuilder();
                    if (k.equalsIgnoreCase("inputs")) {

                        ParamSchema paramSchema = new ParamSchema();
                        paramSchema.setDescr(methodSchema.getName());
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
                        paramSchema.setDescr(methodSchema.getName());
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
                        if (sb.length() != 0) {
                            methodSchema.setFieldValue(k, sb.deleteCharAt(sb.length() - 1).toString().trim());
                        }
                    }
                    //log.info("jxNodes:{}", jxNodes);
                });*/
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

            Map<String, Field> fields = fieldSchema.getWexinOpenFieldMap();
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

/*

    private List<Catalog> getCatalogs(String seed, String regex) {
        List<Catalog> catalogs = new ArrayList<>();


        JXDocument jxDocument = JXDocument.create(getDocument(seed));

        String[] regexs = regex.split(",");
        List<JXNode> jxNodes = jxDocument.selN(regexs[0]);

        Map<String, Catalog> catalogMap = new HashMap<>();
        Long id1 = 1L;
        for (JXNode jxNode : jxNodes) {
            String menu1 = jxNode.selOne(regexs[1] + "/text()").asString();
            Catalog catalog = catalogMap.get(menu1);
            if (catalog == null) {
                catalog = new Catalog();
                catalog.setName(menu1);
                catalog.setId(id1);
                catalog.setType("bean");
                catalog.setPid(0L);
                id1++;
                catalogs.add(catalog);
                catalogMap.put(menu1, catalog);
            }

            Catalog catalog2 = new Catalog();
            catalog2.setName(jxNode.selOne("//span[@title]/text()").asString());
            catalog2.setHref(jxNode.selOne("/@href").asString());
            catalog2.setType("method");
            catalog2.setId(id1);
            catalog2.setPid(catalog.getId());
            catalog2.setPatent(catalog);
            catalog.addChild(catalog2);
            id1++;
        }
        return catalogs;
    }


    private void buildModuleSchema(List<Catalog> catalogs,
                                   ProjectSchema projectSchema, List<ModuleSchema> moduleSchemas, List<BeanSchema> beanSchemas,
                                   ModuleSchema moduleSchema, BeanSchema beanSchema) {
        for (Catalog catalog : catalogs) {

            if (catalog.getName().contains("搜索能力")) {
                return;
            }
            if (catalog.getType().equals("pkg")) {
                catalog.setPkg(catalog.getName());
                moduleSchema = new ModuleSchema();
                moduleSchema.setName(catalog.getName());
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
                methodSchema.setDocUrl(catalog.getDyOpenDocUrl());
                Document document = getDocument(catalog.getDyOpenDocUrl());
                JXDocument jxDocument = JXDocument.create(document);
                //    xpath://div[@class='markdown-render-content']
                map.forEach((k, v) -> {
                    List<JXNode> jxNodes = jxDocument.selN(v);
//                    https://partner.open-douyin.com/docs/resource/zh-CN/mini-app/develop/server/interface-request-credential/get-access-token/
//                    https://partner.open-douyin.com/docs/resource/zh-CN/mini-app/develop/server/interface-request-credential/get-access-token
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
                            sb.append(jxNode.asString() + "\n");
                        }
                        methodSchema.setFieldValue(k, sb.toString());
                    }
                    log.info("jxNodes:{}", jxNodes);
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
    }*/
}
