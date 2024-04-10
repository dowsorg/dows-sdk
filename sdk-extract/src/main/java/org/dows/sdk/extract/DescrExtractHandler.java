package org.dows.sdk.extract;

import lombok.extern.slf4j.Slf4j;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DescrExtractHandler implements ExtractHandler {
    @Override
    public void handle(JXDocument jxDocument, ExtractPojo extractPojo) {
        log.info(this.getClass().getSimpleName() + " extract form :{}", extractPojo.getUrl());
        System.out.println(this.getClass().getSimpleName() + " extract form :" + extractPojo.getUrl());
    }
}
