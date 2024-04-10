package org.dows.sdk.extract.weixin;

import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.extract.Extractable;
import org.dows.sdk.extract.ExtractPojo;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WeixinFieldExtractor implements Extractable {
    @Override
    public void extract(JXDocument jxDocument, ExtractPojo extractPojo) {
        log.info(this.getClass().getSimpleName() + " extract form :{}", extractPojo.getUrl());
        /*System.out.println(this.getClass().getSimpleName() + " extract form :" + extractPojo.getUrl());*/
    }
}
