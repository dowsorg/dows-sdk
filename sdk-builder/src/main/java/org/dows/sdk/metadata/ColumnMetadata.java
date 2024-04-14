package org.dows.sdk.metadata;

import lombok.Data;

@Data
public class ColumnMetadata {

    private String name;
    private String comment;
    private String dataType;
    private String length;
    private String defaultValue;


    private Boolean primaryKey;
    private Boolean autoIncrement;
    private Boolean index;
    private Boolean unique;
    private Boolean nullable;

}
