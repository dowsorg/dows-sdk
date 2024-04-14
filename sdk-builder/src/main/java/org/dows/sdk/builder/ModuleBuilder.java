package org.dows.sdk.builder;

import org.dows.sdk.BuilderSetting;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class ModuleBuilder {

    private String javaDir = "src/main/java";
    private String resDir = "src/main/resources";
    private String pomPath = "pom.xml";

    public void initModule(BuilderSetting builderSetting) {
        // 创建项目目录
        Path projectDir = Paths.get(builderSetting.getPath());
        createDir(projectDir);
        createModule(projectDir, builderSetting);
    }

    public void createDir(Path directory) {
        try {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPom(Path directory, BuilderSetting builderSetting) {
        String pomContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                "    <modelVersion>4.0.0</modelVersion>\n" +
                "    <groupId>" + builderSetting.getGroupId() + "</groupId>\n" +
                "    <artifactId>" + builderSetting.getArtifactId() + "</artifactId>\n" +
                "    <version>" + builderSetting.getVersion() + "</version>\n" +
                "    <!-- 其他配置 -->\n" +
                "</project>";

        try (BufferedWriter writer = Files.newBufferedWriter(directory.resolve("pom.xml"))) {
            writer.write(pomContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createModule(Path parentDir, BuilderSetting builderSetting) {
        String artifactId = builderSetting.getArtifactId();
        Path modulePath = parentDir.resolve(artifactId);
        createDir(modulePath);
        createPom(modulePath, builderSetting);
        List<BuilderSetting> childs = builderSetting.getChilds();
        if (childs != null && !childs.isEmpty()) {
            for (BuilderSetting child : childs) {
                child.setVersion(builderSetting.getVersion());
                child.setGroupId(builderSetting.getGroupId());
                child.setPath(modulePath.resolve(child.getArtifactId()).resolve(javaDir).toString());
                createModule(modulePath, child);
            }
        } else {
            String pkg = builderSetting.getPkg();
            Path javadir = modulePath.resolve(javaDir);
            if (null != pkg) {
                String bp = pkg.replaceAll("\\.", "/");
                javadir.resolve(bp);
            }
            builderSetting.setPath(javadir.toString());
            createDir(javadir);
            createDir(modulePath.resolve(resDir));
        }
    }
}