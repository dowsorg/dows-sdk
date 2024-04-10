package org.dows.sdk.spider;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.dows.sdk.elements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/8/2024 10:05 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
public class BeanBuilder1 {

    public static void main(String[] args) {
        String path = "org.dows.uat.AccountApi@dddd";
        String path1 = "第三方平台管理.模板库管理@获取模板列表1";
        String path2 = "第三方平台管理.模板库管理@获取模板列表2";
        String channel = "appa";
        String channel1 = "appb";
        List<TreeNode<String>> treeNodes = new ArrayList<>();

        MethodElement methodElement = new MethodElement();
        PackageElement packageElement = new PackageElement();
        ClassElement classElement = new ClassElement();
        FieldElement fieldElement = getFieldElement();

        Map<ElementType, Element> elements = new ConcurrentHashMap<>();
        elements.put(ElementType.PKG_ELEMENT, packageElement);
        elements.put(ElementType.CLASS_ELEMENT, classElement);
        elements.put(ElementType.METHOD_ELEMENT, methodElement);
        elements.put(ElementType.FIELD_ELEMENT, fieldElement);

        buildTree(channel, path, treeNodes, elements);
//        buildTree(channel1, path1, treeNodes, elements);
//        buildTree(channel1, path2, treeNodes, elements);

        List<Tree<String>> build = TreeUtil.build(treeNodes, "appa");
        System.out.println(JSONUtil.toJsonPrettyStr(build));
    }

    private static FieldElement getFieldElement() {

        List<FieldElement> fieldElements = new ArrayList<>();
        List<FieldElement> fieldElements3 = new ArrayList<>();

        FieldElement fieldElement = new FieldElement();
        fieldElement.setType("Object");
        fieldElement.setCode("aaa");
        fieldElement.setDescr("aaa");
        fieldElement.setIndex(0);
        // 默认入参
        //fieldElement.setIo(0);


        FieldElement f1 = new FieldElement();
        f1.setType("String");
        f1.setCode("aaa-1");
        f1.setDescr("测");
        f1.setIndex(0);
        FieldElement f2 = new FieldElement();
        f2.setType("String");
        f2.setCode("aaa-2");
        f2.setDescr("试");
        f2.setIndex(1);
        FieldElement f3 = new FieldElement();
        f3.setType("Object");
        f3.setCode("aaa-3");
        f3.setDescr("测试");
        f3.setIndex(2);

        FieldElement f331 = new FieldElement();
        f331.setType("String");
        f331.setCode("f331");
        f331.setDescr("f332");
        FieldElement f332 = new FieldElement();
        f332.setType("String");
        f332.setCode("f332");
        f332.setDescr("f332");


        fieldElements.add(f1);
        fieldElements.add(f2);
        fieldElements.add(f3);

        fieldElements3.add(f331);
        fieldElements3.add(f332);


        fieldElement.setFields(fieldElements);
        f3.setFields(fieldElements3);

        return fieldElement;
    }

    private static void buildTree(String channel, String path, List<TreeNode<String>> treeNodes, Map<ElementType, ? extends Element> elements) {
        // 构建树（第三方平台管理 /模板库管理 /获取模板列表）
        //String path = "第三方平台管理.模板库管理@获取模板列表";
        String[] paths = path.split("/");
        int methodIndex = path.lastIndexOf("@");
        int classIndex = path.lastIndexOf(".");

        String method = path.substring(methodIndex + 1);
        String clazz = path.substring(classIndex + 1, methodIndex);
        String pkg = path.substring(0, classIndex);
//        String channel = "";

//
        /*TreeNode<String> treeRoot = new TreeNode<>();
        treeRoot.setId(channel);
        treeRoot.setName(channel);
        treeRoot.setParentId(null);
        Map<String, Object> rootMap = elements.get(ElementType.PKG_ELEMENT).toMap();
        rootMap.put("type", "channel");
        treeRoot.setExtra(rootMap);
        treeNodes.add(treeRoot);*/


        String methodId = UUID.fastUUID().toString();
        String clazzId = UUID.fastUUID().toString();
        String pkgId = UUID.fastUUID().toString();

        FieldElement fieldElement = (FieldElement) elements.get(ElementType.FIELD_ELEMENT);
//        Map<String, Object> fieldMap = (Map<String, Object>) element;
        // 多个参数
        List<TreeNode<String>> fieldTreeNodes = new ArrayList<>();
        reduceField(methodId, fieldElement, fieldTreeNodes);

        TreeNode<String> treeMethod = new TreeNode<>();
        treeMethod.setId(methodId);
        treeMethod.setName(method);
        treeMethod.setParentId(clazzId);
//        Map<String, Object> methodMap = elements.get(ElementType.METHOD_ELEMENT).toMap();

        MethodElement methodElement = (MethodElement) elements.get(ElementType.METHOD_ELEMENT);
//        methodElement.setInputs();
        //methodElement.setOutput(fieldElement);
        Map<String, Object> map = methodElement.toMap(methodElement.methodFields());
        map.put("inputs",fieldElement);
//        map.put("output",fieldTreeNodes);
        treeMethod.setExtra(map);
        treeNodes.add(treeMethod);


        TreeNode<String> treeClazz = new TreeNode<>();
        treeClazz.setId(clazzId);
        treeClazz.setName(clazz);

        Map<String, Object> clazzMap = elements.get(ElementType.CLASS_ELEMENT).toMap();
        clazzMap.put("type", "clazz");
        clazzMap.put("code", "clazz");
        treeClazz.setExtra(clazzMap);
        treeNodes.add(treeClazz);


        if (StrUtil.isNotBlank(pkg)) {

            TreeNode<String> treePkg = new TreeNode<>();
            treePkg.setName(pkg);
            treePkg.setId(pkgId);
            treePkg.setParentId(channel);
            Map<String, Object> pkgMap = elements.get(ElementType.PKG_ELEMENT).toMap();
            pkgMap.put("type", "pkg");
            pkgMap.put("code", "pkg");
            treePkg.setExtra(pkgMap);
            treeNodes.add(treePkg);

            treeClazz.setParentId(pkgId);
        } else {
            treeClazz.setParentId(channel);
        }


    }

    private static void reduceField(String parentId, FieldElement element, List<TreeNode<String>> treeNodes) {

        Map<String, Object> map = element.toMap(element.fieldFields());
        TreeNode<String> node = new TreeNode<>();
        node.setExtra(map);
        node.setId(UUID.fastUUID().toString());
        node.setParentId(parentId);
        node.setName(element.getCode());
        treeNodes.add(node);

        List<FieldElement> fields = element.getFields();
        if (fields != null && !fields.isEmpty()) {
            for (FieldElement field : fields) {
                reduceField(node.getId(), field, treeNodes);
            }
        }
    }
}

