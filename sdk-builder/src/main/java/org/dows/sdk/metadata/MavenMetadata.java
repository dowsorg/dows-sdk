package org.dows.sdk.metadata;

import cn.hutool.core.lang.tree.Tree;
import lombok.Data;

import java.util.List;

@Data
public class MavenMetadata extends AbstractMetadata {

    private String version;
    private String groupId;
    private String artifactId;
    private String pkg;

    private List<Tree<Integer>> childs;
}
