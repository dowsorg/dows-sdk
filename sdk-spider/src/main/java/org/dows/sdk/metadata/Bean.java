package org.dows.sdk.metadata;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.Map;

@Data
public class Bean {

    // 包名
    private String pkg;
    //   类名
    private String code;
    //  后缀
    private String suffix;
    //  bean的描述
    private String descr;
    // bean的类型[class|interface|enum|annotation|other]
    private String type;
    // 注解
    private Map<String, Annotation> annotations;
    // 接口
    private Map<String, Interfaz> interfaces;
    //  字段
    private Map<String, Field> fields;
    //  方法
    private Map<String, Method> methods;

}
