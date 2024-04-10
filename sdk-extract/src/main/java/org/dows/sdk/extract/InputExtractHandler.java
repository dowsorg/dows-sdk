package org.dows.sdk.extract;

import lombok.extern.slf4j.Slf4j;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class InputExtractHandler implements ExtractHandler {
    @Override
    public void handle(JXDocument jxDocument, ExtractPojo extractPojo) {
        log.info(this.getClass().getSimpleName() + " extract form :{}", extractPojo.getUrl());
        /*System.out.println(this.getClass().getSimpleName() + " extract form :" + extractPojo.getUrl());*/
        String xpath = extractPojo.getExtract().xpath();
        List<JXNode> jxNodes = jxDocument.selN(xpath);
        if (jxNodes.size() > 1) {
            for (JXNode jxNode : jxNodes) {
                try {
                    log.info("field extractor");

                   /* String code = jxNode.selOne("./td[2]/code").asString();
                    String descr = jxNode.selOne("./td[3]").asString();
                    int index = Integer.parseInt(jxNode.selOne("./td[4]").asString());
                    int io = Integer.parseInt(jxNode.selOne("./td[5]").asString());*/
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
