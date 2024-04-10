package org.dows.sdk.extract;

import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.annotations.Extract;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CodeExtractHandler implements ExtractHandler {

    @Override
    public void handle(JXDocument jxDocument, ExtractPojo extractPojo) {
        //
        log.info(this.getClass().getSimpleName() + " extract form :{}", extractPojo.getUrl());
        /*System.out.println(this.getClass().getSimpleName() + " extract form :" + extractPojo.getUrl());*/
        Extract extract = extractPojo.getExtract();
        try {
            JXNode jxNode = jxDocument.selNOne(extract.xpath());
            String code = jxNode.asString();
            extractPojo.setValue(code);
            log.info(jxNode.toString());
        } catch (Exception e) {
            log.error("提取失败", e);
        }
    }
}
