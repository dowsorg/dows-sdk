package org.dows.sdk.model.schema;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class MethodSchema {

    private String url;

    /**
     * 方法注释
     */
    //private final Set<Annot> annotations = new LinkedHashSet<>();
    /**
     * 接口方法im，类方法cm
     */
    private String methodType;
    /**
     * 支持http请求类型
     */
    private String httpMethod;
    /**
     * 是否 restful 风格 API
     */
    private boolean restMethod;
    /**
     * 方法名
     */
    private String name;


    /**
     * 方法泛型值: T -> public <T> 方法返回值 方法名称(方法参数);
     */
    private String genericVal;
    /**
     * 方法描述
     */
    private String descr;
    /**
     * 方法请求入参
     */
    private final List<ParamSchema> inputs = new ArrayList<>();
    /**
     * 方法返回值或出参
     */
    private ParamSchema output;





    public String getName(){
        return name.trim();
    }

    public MethodSchema addInputParam(ParamSchema paramInfo) {
        inputs.add(paramInfo);
        return this;
    }

    public MethodSchema addInputParams(List<ParamSchema> paramInfos) {
        inputs.addAll(paramInfos);
        return this;
    }

}
