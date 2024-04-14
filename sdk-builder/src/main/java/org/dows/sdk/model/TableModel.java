package org.dows.sdk.model;

import lombok.Data;

import java.util.List;

@Data
public class TableModel {
    private String name;
    private String comment;
    private String prefix;

    private List<ColumnModel> columns;
}
