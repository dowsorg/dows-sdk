package org.dows.sdk.model;

import lombok.Data;
import org.dows.sdk.BuilderSetting;
import org.dows.sdk.FileBuilder;

import java.util.List;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/15/2024 12:29 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Data
public class BuilderModel {
    private String dialect;
    private String builder;
    private String pkg;
    private String path;

    private String groupId;
    private String artifactId;
    private String version;

    private List<Class<? extends FileBuilder>> builders;

    private List<BuilderModel> childs;

}

