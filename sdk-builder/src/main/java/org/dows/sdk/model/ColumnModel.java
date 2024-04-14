package org.dows.sdk.model;

import lombok.Data;

@Data
public class ColumnModel {

    private String name;
    private String comment;
    private String type;
    private String length;
    private String defaultValue;


    private Boolean primaryKey;
    private Boolean autoIncrement;
    private Boolean index;
    private Boolean unique;
    private Boolean nullable;

}
