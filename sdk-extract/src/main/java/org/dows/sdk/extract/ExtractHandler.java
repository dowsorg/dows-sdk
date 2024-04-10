package org.dows.sdk.extract;

import org.dows.sdk.elements.Element;
import org.seimicrawler.xpath.JXDocument;

public interface ExtractHandler {

    void handle(JXDocument jxDocument, ExtractPojo extractPojo);
}
