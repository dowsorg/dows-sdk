package org.dows.sdk.spider;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.extract.*;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/11/2024 11:07 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class WeixinOpenSpider implements SdkSpider {

    private final ApplicationContext applicationContext;

    public Map<String, String> extractUris(String platform) {
        ExtractProperties extractProperties = applicationContext.getBean(ExtractProperties.class);
        ExtractSetting extractSetting = extractProperties.getExtractors().get(platform);
        if (null == extractSetting) {
            return new HashMap<>();
        }
        String host = extractSetting.getHost();
        String seed = extractSetting.getSeed();
        String xpath = extractSetting.getXpath();
        JXDocument jxDocument = JXDocument.create(SdkSpider.getDocument(host + seed));
        List<JXNode> jxNodes = jxDocument.selN(xpath);
        Map<String, String> map = new TreeMap<>();
        jxNodes.forEach(n -> {
            String uri = n.selOne("@href").asString();
            String pcmPath = uri.replace(seed, "").replace(".html", "");
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
        return map;
    }


    public ExtractMetadata extractElement(String platform, String path, String url) {
        JXDocument jxDocument = JXDocument.create(SdkSpider.getDocument(url));
        ExtractMetadata extractMetadata = new ExtractMetadata();
        List<ExtractPojo> extractPojos = extractMetadata.toExtracts(platform);
        for (ExtractPojo extractPojo : extractPojos) {
            extractPojo.setUrl(url);
            extractPojo.setPath(path);
            Extractor extractor = extractPojo.getExtractor();
            Extractable extractable = applicationContext.getBean(extractor.getHandler());
            extractable.extract(jxDocument, extractPojo);
        }
        return extractMetadata;
    }
}

