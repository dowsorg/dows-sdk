package org.dows.sdk.metadata;

import lombok.Data;
import org.dows.sdk.FileBuilder;

import java.util.List;

@Data
public class AbstractMetadata {
    private List<Class<? extends FileBuilder>> builders;

}
