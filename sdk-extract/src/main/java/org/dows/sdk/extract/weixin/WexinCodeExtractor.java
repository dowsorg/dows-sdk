package org.dows.sdk.extract.weixin;

import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.extract.ExtractPojo;
import org.dows.sdk.extract.Extractable;
import org.dows.sdk.extract.Extractor;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WexinCodeExtractor implements Extractable {

    @Override
    public void extract(JXDocument jxDocument, ExtractPojo extractPojo) {
        log.info(this.getClass().getSimpleName() + " extract form :{}", extractPojo.getUrl());
        Extractor extractor = extractPojo.getExtractor();
        try {
            JXNode jxNode = jxDocument.selNOne(extractor.getXpath());
            String code = jxNode.asString();
            extractPojo.setValue(code);
            log.info(jxNode.toString());
        } catch (Exception e) {
            log.error("提取失败", e);
        }
    }
}
