package org.dows.sdk.extract;

import lombok.Data;

@Data
public class Extractor {
    private String name;
    private String xpath;
    private Class<? extends Extractable> handler = DefaultExtractable.class;
}