//package org.dows.sdk;
//
//import cn.hutool.core.lang.UUID;
//import cn.hutool.core.lang.tree.Tree;
//import cn.hutool.core.lang.tree.TreeNode;
//import cn.hutool.core.lang.tree.TreeUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.extra.template.TemplateEngine;
//import cn.hutool.json.JSONUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.dows.sdk.elements.*;
//import org.dows.sdk.extract.DyOpenExtracter;
//import org.dows.sdk.extract.WxOpenExtracter;
//import org.dows.sdk.extract.WxPayExtracter;
//import org.dows.sdk.util.SchemaUtil;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//@SpringBootTest
//public class ApiTest {
//
////    @Autowired
////    private LinkExtractHandler linkExtractHandler;
////
////    @Autowired
////    private CrawlerConfig crawlerConfig;
//
//
//    @Autowired
//    private WxPayExtracter wxPayExtracter;
//
//    @Autowired
//    private WxOpenExtracter wxOpenExtracter;
//
//    @Autowired
//    private DyOpenExtracter dyOpenExtracter;
//
////    @Autowired
////    private CatalogCrawler catalogCrawler;
//
//    @Autowired
//    private TemplateEngine templateEngine;
//
////    @Test
////    public void testApi() {
////        catalogCrawler.crawlerApi();
////    }
//
//
//    @Test
//    public void test() {
//        String 测试小程序 = SchemaUtil.getPingYin("测试小程序Api");
//        log.info(测试小程序);
//    }
//
//    @Test
//    public void testDyOpen() {
//        String seed = "classpath://html/application-douyin-api.html";
//        String regex = "//ul[@role='menu']/li[@role='menuitem']//a,//span[@title]";
//        dyOpenExtracter.genSdk(seed, regex);
//        log.info("");
//    }
//
//    @Test
//    public void testWxOpen() {
//        //https://developers.weixin.qq.com/
//        String seed = "https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc";
//        //String regex = "//div[@class='TreeNavigation']/div/ul/li/div/ul//li/span/a";
//        // //div[@class='TreeNavigation']/div/ul/li/div//ul/li
//
//        // //aside[@class='sidebar']/div/div/ul/li//a
//        String regex = "//div[@class='TreeNavigation']/div/ul/li/div//ul,li//a";
//
//
//        wxOpenExtracter.genSdk(seed, regex);
//        log.info("");
//    }
//
//
//    public static void main(String[] args) {
//        String path = "第三方平台管理.模板库管理@获取模板列表";
//        String path1 = "第三方平台管理.模板库管理@获取模板列表1";
//        String path2 = "第三方平台管理.模板库管理@获取模板列表2";
//        String channel = "weixin";
//        String channel1 = "alipay";
//        List<TreeNode<String>> treeNodes = new ArrayList<>();
//
//        Element methodElement = new MethodElement();
//        Element pkgElement = new PackageElement();
//        Element classElement = new ClassElement();
//        FieldElement fieldElement = getFieldElement();
//
//        Map<ElementType, Element> elements = new ConcurrentHashMap<>();
//        elements.put(ElementType.PKG_ELEMENT, pkgElement);
//        elements.put(ElementType.CLASS_ELEMENT, classElement);
//        elements.put(ElementType.METHOD_ELEMENT, methodElement);
//        elements.put(ElementType.FIELD_ELEMENT, fieldElement);
//
//        buildTree(channel, path, treeNodes, elements);
////        buildTree(channel1, path1, treeNodes, elements);
////        buildTree(channel1, path2, treeNodes, elements);
//
//        List<Tree<String>> builder = TreeUtil.builder(treeNodes, "weixin");
//        System.out.println(JSONUtil.toJsonPrettyStr(builder));
//    }
//
//    private static FieldElement getFieldElement() {
//
//        List<FieldElement> fieldElements = new ArrayList<>();
//        List<FieldElement> fieldElements3 = new ArrayList<>();
//
//        FieldElement fieldElement = new FieldElement();
//        fieldElement.setType("Object");
//        fieldElement.setCode("aaa");
//        fieldElement.setDescr("aaa");
//        fieldElement.setIndex(0);
//        // 默认入参
//        //fieldElement.setIo(0);
//
//
//        FieldElement f1 = new FieldElement();
//        f1.setType("String");
//        f1.setCode("aaa-1");
//        f1.setDescr("测");
//        f1.setIndex(0);
//        FieldElement f2 = new FieldElement();
//        f2.setType("String");
//        f2.setCode("aaa-2");
//        f2.setDescr("试");
//        f2.setIndex(1);
//        FieldElement f3 = new FieldElement();
//        f3.setType("Object");
//        f3.setCode("aaa-3");
//        f3.setDescr("测试");
//        f3.setIndex(2);
//
//        FieldElement f331 = new FieldElement();
//        f331.setType("String");
//        f331.setCode("f331");
//        f331.setDescr("f332");
//        FieldElement f332 = new FieldElement();
//        f332.setType("String");
//        f332.setCode("f332");
//        f332.setDescr("f332");
//
//
//        fieldElements.add(f1);
//        fieldElements.add(f2);
//        fieldElements.add(f3);
//
//        fieldElements3.add(f331);
//        fieldElements3.add(f332);
//
//
//        fieldElement.setFields(fieldElements);
//        f3.setFields(fieldElements3);
//
//        return fieldElement;
//    }
//
//    private static void buildTree(String channel, String path, List<TreeNode<String>> treeNodes, Map<ElementType, ? extends Element> elements) {
//        // 构建树（第三方平台管理 /模板库管理 /获取模板列表）
//        //String path = "第三方平台管理.模板库管理@获取模板列表";
//        String[] paths = path.split("/");
//        int methodIndex = path.lastIndexOf("@");
//        int classIndex = path.lastIndexOf(".");
//
//        String method = path.substring(methodIndex + 1);
//        String clazz = path.substring(classIndex + 1, methodIndex);
//        String pkg = path.substring(0, classIndex);
////        String channel = "";
//
////
//        /*TreeNode<String> treeRoot = new TreeNode<>();
//        treeRoot.setId(channel);
//        treeRoot.setName(channel);
//        treeRoot.setParentId(null);
//        Map<String, Object> rootMap = elements.get(ElementType.PKG_ELEMENT).toMap();
//        rootMap.put("type", "channel");
//        treeRoot.setExtra(rootMap);
//        treeNodes.add(treeRoot);*/
//
//
//        String methodId = UUID.fastUUID().toString();
//        String clazzId = UUID.fastUUID().toString();
//        String pkgId = UUID.fastUUID().toString();
//
//        FieldElement element = (FieldElement) elements.get(ElementType.FIELD_ELEMENT);
////        Map<String, Object> fieldMap = (Map<String, Object>) element;
//        // 多个参数
//        reduceField(methodId, element, treeNodes);
//
//        TreeNode<String> treeMethod = new TreeNode<>();
//        treeMethod.setId(methodId);
//        treeMethod.setName(method);
//        treeMethod.setParentId(clazzId);
//        Map<String, Object> methodMap = elements.get(ElementType.METHOD_ELEMENT).toMap();
//        methodMap.put("type", "method");
//        methodMap.put("code", "method");
//        treeMethod.setExtra(methodMap);
//        treeNodes.add(treeMethod);
//
//
//        TreeNode<String> treeClazz = new TreeNode<>();
//        treeClazz.setId(clazzId);
//        treeClazz.setName(clazz);
//
//        Map<String, Object> clazzMap = elements.get(ElementType.CLASS_ELEMENT).toMap();
//        clazzMap.put("type", "clazz");
//        clazzMap.put("code", "clazz");
//        treeClazz.setExtra(clazzMap);
//        treeNodes.add(treeClazz);
//
//
//        if (StrUtil.isNotBlank(pkg)) {
//
//            TreeNode<String> treePkg = new TreeNode<>();
//            treePkg.setName(pkg);
//            treePkg.setId(pkgId);
//            treePkg.setParentId(channel);
//            Map<String, Object> pkgMap = elements.get(ElementType.PKG_ELEMENT).toMap();
//            pkgMap.put("type", "pkg");
//            pkgMap.put("code", "pkg");
//            treePkg.setExtra(pkgMap);
//            treeNodes.add(treePkg);
//
//            treeClazz.setParentId(pkgId);
//        } else {
//            treeClazz.setParentId(channel);
//        }
//
//
//    }
//
//    private static void reduceField(String parentId, FieldElement element, List<TreeNode<String>> treeNodes) {
//
//        Map<String, Object> map = element.toMap(element.fieldFields());
//        TreeNode<String> node = new TreeNode<>();
//        node.setExtra(map);
//        node.setId(UUID.fastUUID().toString());
//        node.setParentId(parentId);
//        node.setName(element.getCode());
//        treeNodes.add(node);
//
//        List<FieldElement> fields = element.getFields();
//        if (fields != null && !fields.isEmpty()) {
//            for (FieldElement field : fields) {
//                reduceField(node.getId(), field, treeNodes);
//            }
//        }
//    }
//
//
//    @Test
//    public void testWxPay() {
//        String seed = "https://pay.weixin.qq.com/wiki/doc/apiv3_partner/apis/index.shtml";
//        String regex = "//div[@class='doc-menu ']/dl/dt,/following-sibling::dd//a";
//        wxPayExtracter.genSdk(seed, regex);
//        log.info("");
//    }
//
//
//
//
//
//
//
//
//
//
///*    @Test
//    public void testWxOpen1() {
//        log.info("");
//        String file = this.getClass().getResource("").getPath() + File.separator + "bean-schema.json";
//
//        Map<String, BeanSchema> link = new HashMap<>();
//        try {
//            String s = FileUtil.readString(new File(file), Charset.defaultCharset());
//            link = JSONUtil.toBean(s, Map.class);
//        } catch (Exception e) {
//
//        }
//        if (link.size() == 0) {
//            link = weixinDeveloperExtracter.get("https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc", "//div[@class=\"TreeNavigation\"]/div/ul/li/div/ul//li/span/a");
//            String jsonPrettyStr = JSONUtil.toJsonPrettyStr(link);
//            FileUtil.writeString(jsonPrettyStr, file, Charset.defaultCharset());
//        }
//
//        List<ModuleSchema> moduleSchemas = new ArrayList<>();
//        ProjectSchema projectSchema = new ProjectSchema();
//        projectSchema.setName("sdk-weixin");
//        projectSchema.setRootPath("E:/workspaces/java/projects/dows/dows-pay/pay-sdk");
//        projectSchema.setBasePkg("org.dows.sdk.weixin");
//        projectSchema.setModules(moduleSchemas);
//
//        String apiSuffix = "Api";
//
//
//        ModuleSchema moduleSchema = new ModuleSchema();
//        moduleSchema.setProjectSchema(projectSchema);
//        moduleSchemas.add(moduleSchema);
//        //moduleSchema.setBeanSchemas(beanSchemas);
//
//        Template template = templateEngine.getTemplate("api1.ftl");
//        Template modelTemplate = templateEngine.getTemplate("param1.ftl");
//        Template ymlTemplate = templateEngine.getTemplate("wexiapi.ftl");
//
//        //link.entrySet().stream().iterator().next().getValue().stream().collect(Collectors.groupingBy(BeanSchema::getPkg));
//        List<BeanSchema> beanSchemas = new ArrayList<>();
//        Set<String> strings = link.keySet();
//        for (String string : strings) {
//            //BeanSchema beanSchema = JSONUtil.toBean((JSONObject) link.get(string), BeanSchema.class);
//            BeanSchema beanSchema = (BeanSchema) link.get(string);
//            beanSchema.setDescr(string);
//            String beanName = beanSchema.getName();
//            beanSchema.setModule(moduleSchema);
//            moduleSchema.addBeanSchema(beanSchema);
//            String path = beanSchema.getPath();
//            try {
//                Files.createDirectories(Path.of(path));
//
//                String render = template.render(BeanUtil.beanToMap(beanSchema));
//
//                Files.write(Path.of(path + "/" + StrUtil.upperFirst(beanName) + ".java"), render.getBytes());
//                log.info("");
//
//                List<MethodSchema> methods = beanSchema.getMethods();
//
//                for (MethodSchema method : methods) {
//                    List<ParamSchema> inputs = method.getInputs();
//                    for (ParamSchema input : inputs) {
//                        Files.createDirectories(Path.of(path, "request"));
//                        input.setPkg(beanSchema.getPkg() + ".request");
//                        String request = modelTemplate.render(BeanUtil.beanToMap(input));
//                        Files.write(Path.of(path + "/request/" + StrUtil.upperFirst(input.getType()) + ".java"), request.getBytes());
//                    }
//                    ParamSchema output = method.getOutput();
//                    output.setPkg(beanSchema.getPkg() + ".response");
//                    Files.createDirectories(Path.of(path, "response"));
//                    Map<String, Object> stringObjectMap = BeanUtil.beanToMap(output);
//                    String response = modelTemplate.render(stringObjectMap);
//                    Files.write(Path.of(path + "/response/" + StrUtil.upperFirst(output.getType()) + ".java"), response.getBytes());
//
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            beanSchemas.add(beanSchema);
//        }
//
//        // 生成yml
//        Map<String, List<BeanSchema>> collect = beanSchemas.stream().collect(Collectors.groupingBy(BeanSchema::getPkg));
//        collect.forEach((k, v) -> {
//            try {
//                Files.createDirectories(Path.of(moduleSchema.getResourcesPath()));
//                BeanUtil.beanToMap(v);
//                Map<String, List<BeanSchema>> map = new HashMap<>();
//                map.put("beanSchemas", v);
//                String render = ymlTemplate.render(map);
//                Files.write(Path.of(moduleSchema.getResourcesPath() + "/" + k.replaceAll("\\.", "-") + ".yml"), render.getBytes());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//    }*/
//
//}
