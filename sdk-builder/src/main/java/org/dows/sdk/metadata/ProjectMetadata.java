package org.dows.sdk.metadata;

import cn.hutool.core.lang.tree.Tree;
import lombok.Data;

import java.util.List;

@Data
public class ProjectMetadata {

    private String name;
    private String version;
    private String comment;
    private List<Tree<Integer>> databases;
    private List<Tree<Integer>> functions;
    private List<Tree<Integer>> builders;
}
