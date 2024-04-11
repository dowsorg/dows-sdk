package org.dows.sdk.extract;

import org.seimicrawler.xpath.JXDocument;

public interface Extractable {

    default void extract(JXDocument jxDocument, ExtractPojo extractPojo) {
    }

}
