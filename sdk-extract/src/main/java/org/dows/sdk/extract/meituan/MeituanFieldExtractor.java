package org.dows.sdk.extract.meituan;

import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.extract.ExtractPojo;
import org.dows.sdk.extract.Extractable;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MeituanFieldExtractor implements Extractable {
    @Override
    public void extract(JXDocument jxDocument, ExtractPojo extractPojo) {
        log.info(this.getClass().getSimpleName() + " extract form :{}", extractPojo.getUrl());
        /*System.out.println(this.getClass().getSimpleName() + " extract form :" + extractPojo.getUrl());*/
    }
}
