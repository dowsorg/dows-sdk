package org.dows.sdk;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;
import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.annotations.Extract;
import org.dows.sdk.elements.ClassElement;
import org.dows.sdk.extract.ExtractElement;
import org.dows.sdk.extract.ExtractHandler;
import org.dows.sdk.extract.ExtractPojo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.lang.model.element.PackageElement;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@SpringBootTest
public class ExtractTest {
    private static final Map<String, Object> map = new HashMap<>();

    private static Map<String, PackageElement> packageMap = new HashMap<>();
    private static Map<String, ClassElement> classMap = new HashMap<>();


    static {
        try {
            map.putAll(JsonReader.readJsonFile(new ClassPathResource("name.json").getStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void test() {
        String channel = "weixin";
        Map<String, String> uriMap = extractedUri(channel);
        List<TreeNode<String>> treeNodes = new ArrayList<>();
        List<ExtractElement> extractElements = new ArrayList<>();
        uriMap.forEach((path, docUrl) -> {
            buildTree(channel, path, docUrl);
        });

        List<Tree<String>> build = TreeUtil.build(treeNodes, "weixin");
        System.out.println(JSONUtil.toJsonPrettyStr(build));
    }


    public static ExtractElement buildTree(String channel, String path, String url) {
        int methodIndex = path.lastIndexOf("@");
        int classIndex = path.lastIndexOf(".");

        String method = path.substring(methodIndex + 1);
        String clazz = path.substring(classIndex + 1, methodIndex);
        String pkg = "";
        if (classIndex != -1) {
            pkg = path.substring(0, classIndex);
        }

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

        ClassElement classElement = classMap.get(pkg + "." + clazz);
        if (classElement == null) {
            classElement = new ClassElement();
            classElement.setCode(clazz);
            classElement.setPgk(pkg);
        }


        ApplicationContext applicationContext = SpringUtil.getApplicationContext();

        JXDocument jxDocument = JXDocument.create(getDocument(url));

        ExtractElement extractElement = new ExtractElement();
        List<ExtractPojo> extractPojos = extractElement.getXpath(channel);
        for (ExtractPojo extractPojo : extractPojos) {
            extractPojo.setUrl(url);
            Extract extract = extractPojo.getExtract();
            ExtractHandler extractHandler = applicationContext.getBean(extract.handler());
            extractHandler.handle(jxDocument, extractPojo);
        }
        return extractElement;
    }


    private static Map<String, String> extractedUri(String channel) {
        if (channel.equalsIgnoreCase("weixin")) {
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
                    key = pkg.replace("/", ".") + "." + clazz + "@" + method;
                }
                String targetUrl = host + uri;
                map.put(key, targetUrl);
            });
            System.out.println(JSONUtil.toJsonPrettyStr(map));
            return map;
        }
        return new HashMap<>();
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
