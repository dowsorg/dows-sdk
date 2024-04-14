package org.dows.sdk.metadata;

import lombok.Data;

@Data
public class IdGenerator {

    private int moduleId;

    private int functionId;

    private int databaseId;

    private int tableId;

    private int columnId;
}
