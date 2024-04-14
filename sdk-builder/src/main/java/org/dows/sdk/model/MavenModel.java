package org.dows.sdk.model;

import lombok.Data;
import org.dows.sdk.metadata.AbstractMetadata;

import java.util.List;

@Data
public class MavenModel {

    private String version;
    private String groupId;
    private String artifactId;
    private String pkg;

    private List<MavenModel> childs;
}
