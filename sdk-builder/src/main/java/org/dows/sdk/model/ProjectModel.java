package org.dows.sdk.model;

import lombok.Data;

import java.util.List;

@Data
public class ProjectModel {
    private List<DatabaseModel> databases;
    private List<FunctionModel> functions;
    private List<ConfigModel> configs;
    private BuilderModel builderModel;

}
