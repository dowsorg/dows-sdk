package org.dows.sdk;

import org.dows.sdk.elements.ClassElement;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/12/2024 10:47 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
public interface FileBuilder {

    default FileBuilder setTemplate(String template) {
        return this;
    }

    default void build(String path, String ext) {

    }

    default void build(ClassElement classElement) {

    }

}
