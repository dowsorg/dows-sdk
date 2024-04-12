package org.dows.sdk;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;
import org.dows.sdk.elements.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/8/2024 10:05 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
public class BeanBuilder2 {

    private static final Map<String, Object> map = new HashMap<>();

    static {
        try {
            map.putAll(JsonReader.readJsonFile(new ClassPathResource("name.json").getStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        String path = "第三方平台管理.模板库管理@dddd";
        String path1 = "第三方平台管理.模板库管理@获取模板列表1";
        String path2 = "第三方平台管理.模板库管理@获取模板列表2";
        String channel = "appa";
        String channel1 = "appb";

        String host = "https://developers.weixin.qq.com";
        String prefix = "/doc/oplatform/openApi/OpenApiDoc/";
        String urlXpath = "//aside[@class='sidebar']/div/div/ul/li//a";
        JXDocument jxDocument = JXDocument.create(getDocument("https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc"));
        List<JXNode> jxNodes = jxDocument.selN(urlXpath);
        Map<String, String> map = new TreeMap<>();
        jxNodes.forEach(n -> {

            String uri = n.selOne("@href").asString();

            String pcmPath = uri.replace(prefix, "").replace(".html", "");
            int methodStart = pcmPath.lastIndexOf("/");
            String method = pcmPath.substring(methodStart + 1);

            String classPath = pcmPath.substring(0, methodStart);
            int classStart = classPath.lastIndexOf("/");
            String clazz = classPath.substring(classStart + 1);
            clazz = StrUtil.toCamelCase(clazz, '-');

            String key;
            if (classStart == -1) {
                key = clazz + "@" + method;
            } else {
                String pkg = pcmPath.substring(0, classStart);
                key = pkg + "/" + clazz + "@" + method;
            }
            String targetUrl = host + uri;
            map.put(key, targetUrl);

            //System.out.println(n.selOne("@href").asString());
        });
        System.out.println(JSONUtil.toJsonPrettyStr(map));

        List<TreeNode<String>> treeNodes = new ArrayList<>();

        MethodElement methodElement = new MethodElement();
//        PackageElement packageElement = new PackageElement();
        ClassElement classElement = new ClassElement();
        FieldElement fieldElement = getFieldElement();

        Map<ElementType, Element> elements = new ConcurrentHashMap<>();
//        elements.put(ElementType.PKG_ELEMENT, packageElement);
        elements.put(ElementType.CLASS_ELEMENT, classElement);
        elements.put(ElementType.METHOD_ELEMENT, methodElement);
        elements.put(ElementType.FIELD_ELEMENT, fieldElement);

        buildTree(channel, path, treeNodes, elements);
//        buildTree(channel1, path1, treeNodes, elements);
//        buildTree(channel1, path2, treeNodes, elements);


        /*List<TreeNode<String>> elementType = treeNodes.stream().filter(tn -> tn.getExtra().get("elementType").toString().equals("0"))
                .toList();
        List<Tree<String>> build1 = TreeUtil.builder(elementType, "4891509c-2b5f-431d-9013-e832e41371ef");
        System.out.println(JSONUtil.toJsonPrettyStr(build1));*/

        List<Tree<String>> build = TreeUtil.build(treeNodes, "appa");
        System.out.println(JSONUtil.toJsonPrettyStr(build));
    }

    private static FieldElement getFieldElement() {

        List<FieldElement> fieldElements = new ArrayList<>();
        List<FieldElement> fieldElements3 = new ArrayList<>();

        FieldElement fieldElement = new FieldElement();
        fieldElement.setType("Object");
        fieldElement.setCode("aaa");
        fieldElement.setDescr("aaa");
        fieldElement.setIndex(0);
        //fieldElement.setIo(1);
        // 默认入参
        //fieldElement.setIo(0);


        FieldElement f1 = new FieldElement();
        f1.setType("String");
        f1.setCode("aaa-1");
        f1.setDescr("测");
        f1.setIndex(0);
        FieldElement f2 = new FieldElement();
        f2.setType("String");
        f2.setCode("aaa-2");
        f2.setDescr("试");
        f2.setIndex(1);
        FieldElement f3 = new FieldElement();
        f3.setType("Object");
        f3.setCode("aaa-3");
        f3.setDescr("测试");
        f3.setIndex(2);

        FieldElement f331 = new FieldElement();
        f331.setType("String");
        f331.setCode("f331");
        f331.setDescr("f332");
        FieldElement f332 = new FieldElement();
        f332.setType("String");
        f332.setCode("f332");
        f332.setDescr("f332");


        fieldElements.add(f1);
        fieldElements.add(f2);
        fieldElements.add(f3);

        fieldElements3.add(f331);
        fieldElements3.add(f332);


        fieldElement.setFields(fieldElements);
        f3.setFields(fieldElements3);

        return fieldElement;
    }

    private static void buildTree(String channel, String path, List<TreeNode<String>> treeNodes,
                                  Map<ElementType, ? extends Element> elements) {
        // 构建树（第三方平台管理 /模板库管理 /获取模板列表）
        //String path = "第三方平台管理.模板库管理@获取模板列表";
        int methodIndex = path.lastIndexOf("@");
        int classIndex = path.lastIndexOf(".");

        String method = path.substring(methodIndex + 1);
        String clazz = path.substring(classIndex + 1, methodIndex);
        String pkg = path.substring(0, classIndex);

        // 替换中文
        String[] pkgItems = pkg.split("\\.");
        for (String item : pkgItems) {
            Object o = map.get(item);
            if (o != null) {
                pkg = pkg.replace(item, o.toString());
            }
        }
        Object co = map.get(clazz);
        if (co != null) {
            clazz = co.toString();
        }
        

        /*TreeNode<String> treeRoot = new TreeNode<>();
        treeRoot.setId(channel);
        treeRoot.setName(channel);
        treeRoot.setParentId(null);
        Map<String, Object> rootMap = elements.get(ElementType.PKG_ELEMENT).toMap();
        rootMap.put("type", "channel");
        treeRoot.setExtra(rootMap);
        treeNodes.add(treeRoot);*/


        String methodId = UUID.fastUUID().toString();
        String clazzId = UUID.fastUUID().toString();
        String pkgId = UUID.fastUUID().toString();

        FieldElement fieldElement = (FieldElement) elements.get(ElementType.FIELD_ELEMENT);
        fieldElement.setPkg(pkg);
        // 多个参数
        reduceField(pkg, methodId, fieldElement, treeNodes);

        TreeNode<String> treeMethod = new TreeNode<>();
        treeMethod.setId(methodId);
        treeMethod.setName(method);
        treeMethod.setParentId(clazzId);

        MethodElement methodElement = (MethodElement) elements.get(ElementType.METHOD_ELEMENT);
        methodElement.setCode("${method}");
        methodElement.setDescr("${method descr}");
        treeMethod.setExtra(methodElement.toMap());
        treeNodes.add(treeMethod);


        TreeNode<String> treeClazz = new TreeNode<>();
        treeClazz.setId(clazzId);
        ClassElement classElement = (ClassElement) elements.get(ElementType.CLASS_ELEMENT);
        classElement.setCode(clazz);
        classElement.setPgk(pkg + ".api");
        classElement.setDescr("${classDescr}");
        treeClazz.setExtra(classElement.toMap());
        treeNodes.add(treeClazz);

        if (StrUtil.isNotBlank(pkg)) {
            TreeNode<String> treePkg = new TreeNode<>();
            /*treePkg.setName(pkg);*/
            treePkg.setId(pkgId);
            treePkg.setParentId(channel);
            PackageElement packageElement = (PackageElement) elements.get(ElementType.PKG_ELEMENT);
            packageElement.setPgk(pkg);
            packageElement.setDescr("${pkgDescr}");
           /* pkgMap.put("type", "pkg");
            pkgMap.put("code", "pkg");*/
            treePkg.setExtra(packageElement.toMap());
            treeNodes.add(treePkg);

            treeClazz.setParentId(pkgId);
        } else {
            treeClazz.setParentId(channel);
        }
    }

    private static void reduceField(String pkg, String parentId, FieldElement element, List<TreeNode<String>> treeNodes) {

        if (element.getType().equalsIgnoreCase("object")) {
            if (element.getIo() == 0) {
                element.setPkg(pkg + ".request");
            } else {
                element.setPkg(pkg + ".response");
            }
        }
        Map<String, Object> map = element.toMap(element.fieldFields());
        TreeNode<String> node = new TreeNode<>();
        node.setExtra(map);
        node.setId(UUID.fastUUID().toString());
        node.setParentId(parentId);
        node.setName(element.getCode());
        treeNodes.add(node);
        // 递归字段
        List<FieldElement> fields = element.getFields();
        if (fields != null && !fields.isEmpty()) {
            for (FieldElement field : fields) {
                reduceField(pkg, node.getId(), field, treeNodes);
            }
        }
    }

    public static Document getDocument(String seed) {
        Document document = null;
        if (seed.startsWith("http://") || seed.startsWith("https://")) {

            /** 创建模拟指定浏览器的客户端对象 */
            final WebClient webClient = new WebClient(BrowserVersion.EDGE);

            /** JS执行出错不抛出异常 */
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            /** HTTP状态不是200时不抛出异常 */
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            /** 不启用CSS */
            webClient.getOptions().setCssEnabled(false);
            /** 启用JS(非常重要) */
            webClient.getOptions().setJavaScriptEnabled(true);
            // webClient.getOptions().setActiveXNative(false);
            //webClient.getOptions().setTimeout(3000);
            /** 支持AJAX(非常重要) */
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
            /** JS执行需要一定时间，设置等待时间(非常重要) */
            webClient.waitForBackgroundJavaScript(3000);


            /** 加载网页 */
            HtmlPage page = null;
            try {
                page = webClient.getPage(seed);
                //Thread.sleep(3000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            /** 将加载的网页转换成XML形式 */
            String pageXml = page.asXml();
            /** Jsoup获取HTML文档 */
            document = Jsoup.parse(pageXml);
            webClient.close();
        } else if (seed.startsWith("file://")) {
            try {
                document = Jsoup.parse(new File(seed.substring("file://".length())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (seed.startsWith("classpath://")) {
            ClassPathResource classPathResource = new ClassPathResource(seed.substring("classpath://".length()));
            document = Jsoup.parse(classPathResource.readUtf8Str());
        } else {
            document = Jsoup.parse(seed);
        }
        return document;
    }
}

