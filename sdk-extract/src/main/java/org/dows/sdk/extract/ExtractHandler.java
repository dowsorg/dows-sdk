package org.dows.sdk.extract;

import org.seimicrawler.xpath.JXDocument;

public interface ExtractHandler {

    void handle(JXDocument jxDocument, ExtractPojo extractPojo);
}
