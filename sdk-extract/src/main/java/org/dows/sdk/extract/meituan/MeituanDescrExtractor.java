package org.dows.sdk.extract.meituan;

import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.extract.ExtractPojo;
import org.dows.sdk.extract.Extractable;
import org.dows.sdk.extract.Extractor;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MeituanDescrExtractor implements Extractable {
    @Override
    public void extract(JXDocument jxDocument, ExtractPojo extractPojo) {
        Extractor extractor = extractPojo.getExtractor();
        String xpath = extractor.getXpath();
        log.info(this.getClass().getSimpleName() + " extract form :{}", extractPojo.getUrl());
        /*System.out.println(this.getClass().getSimpleName() + " extract form :" + extractPojo.getUrl());*/
    }
}
