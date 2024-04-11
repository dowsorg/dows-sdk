package org.dows.sdk.schema;

import lombok.Data;

@Data
public class PomSchema {
    private String artifactId;
    private String groupId;
    private String version;
}
