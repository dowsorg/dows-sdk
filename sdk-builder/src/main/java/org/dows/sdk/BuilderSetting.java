package org.dows.sdk;

import lombok.Data;

import java.util.List;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/12/2024 10:28 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Data
public class BuilderSetting {

    private String dialect;

    private String buildTool;
    private String groupId;
    private String artifactId;
    private String version;
    private String basePackage;
    private String template;

    private List<Class<? extends FileBuilder>> builders;

    private List<BuilderSetting> childs;
}

