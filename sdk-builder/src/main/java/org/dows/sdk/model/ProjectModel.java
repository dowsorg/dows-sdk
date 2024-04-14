package org.dows.sdk.model;

import lombok.Data;
import org.dows.sdk.metadata.MavenMetadata;

import java.util.List;

@Data
public class ProjectModel {
    private String name;
    private List<DatabaseModel> databases;
    private List<FunctionModel> functions;
//    private MavenMetadata builder;
//    private ConfigModel configs;

}
