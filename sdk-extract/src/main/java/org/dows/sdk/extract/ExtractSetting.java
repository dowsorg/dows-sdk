package org.dows.sdk.extract;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

@Data
public class ExtractSetting {
    private String host;
    private String seed;
    private String xpath;
    @NestedConfigurationProperty
    private List<Extractor> extractors;


}