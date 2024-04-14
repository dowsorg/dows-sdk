package org.dows.sdk.metadata;

import lombok.Data;

@Data
public class TableMetadata {
    private String name;
    private String comment;
    private String prefix;
}
