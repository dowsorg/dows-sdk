package org.dows.sdk.spider.model.schema;

import cn.hutool.core.util.StrUtil;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
@Data
public class ParamSchema {
    private String type;
    private String name = "";
    private String descr;

    // 参数类型，输入参数还是输出参数，默认输入参数
    private String iot;

    private String collectionType;
    private String suffix;

    private String path;
    private String pkg;




    private final List<FieldSchema> fields = new ArrayList<>();

    private static List<String> javaType = Arrays.asList("String", "string", "Long", "long", "Integer", "integer", "int", "Float", "float", "Double", "double",
            "List", "list", "Map", "map", "Collection", "collection", "Boolean", "boolean", "Void", "void", "Date", "date");


    public ParamSchema addField(FieldSchema fieldInfo) {
        this.fields.add(fieldInfo);
        return this;
    }

    public ParamSchema addFields(List<FieldSchema> fieldInfos) {
        this.fields.addAll(fieldInfos);
        return this;
    }


    public String getType() {
        return StrUtil.upperFirst(type);
    }


    public String getClazz() {
        if (!javaType.contains(type)) {
            return StrUtil.upperFirst(type) + StrUtil.upperFirst(suffix);
        }
        return null;
    }

//    public String getOutType() {
//        String ts = null;
//        boolean contains = javaType.contains(type);
//        if (!contains) {
//            ts = type + suffix;
//        } else {
//            ts = type;
//        }
//        String s = StrUtil.upperFirst(ts);
//        if (StrUtil.isNotBlank(collectionType) && !contains) {
//            String s1 = StrUtil.upperFirst(collectionType);
//            if (collectionType.equalsIgnoreCase("map")) {
//                return s1 + "<String, " + s + ">";
//            }
//            return s1 + "<" + s + ">";
//        }
//        return s;
//    }

    public String getReturnType() {
        String ts = null;
        boolean contains = javaType.contains(type);
        if (!contains) {
            ts = type + suffix;
        } else {
            ts = type;
        }
        String s = StrUtil.upperFirst(ts);
        if (StrUtil.isNotBlank(collectionType) && !contains) {
            String s1 = StrUtil.upperFirst(collectionType);
/*            if (iot.equals("out") && collectionType.equalsIgnoreCase("map")) {
                return "HashMap<String, " + s + ">";
            }
            if(iot.equals("out") && collectionType.equalsIgnoreCase("list")) {
                return "ArrayList<" + s + ">";
            }*/
            if (iot.equals("out") && collectionType.equalsIgnoreCase("map")) {
                return s1 + "<String, " + s + ">";
            }
            if (iot.equals("out") && collectionType.equalsIgnoreCase("list")) {
                return s1 + "<" + s + ">";
            }
        }
        return s;
    }


    public String getParamType() {
        String ts = null;
        boolean contains = javaType.contains(type);
        if (!contains) {
            ts = type + suffix;
        } else {
            ts = type;
        }
        String s = StrUtil.upperFirst(ts);
        if (StrUtil.isNotBlank(collectionType) && !contains) {
            String s1 = StrUtil.upperFirst(collectionType);
            if (iot.equals("out") && collectionType.equalsIgnoreCase("map")) {
                return "HashMap<String, " + s + ">";
            }
            if (iot.equals("out") && collectionType.equalsIgnoreCase("list")) {
                return "ArrayList<" + s + ">";
            }
            if (iot.equals("in") && collectionType.equalsIgnoreCase("map")) {
                return s1 + "<String, " + s + ">";
            }
            if (iot.equals("in") && collectionType.equalsIgnoreCase("list")) {
                return s1 + "<" + s + ">";
            }
        }
        return s;
    }
}
