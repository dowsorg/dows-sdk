package org.dows.sdk.metadata;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DatabaseMetadata extends AbstractMetadata {
    private String name;
    private String comment;
    private String host;
    private String port;
    private String user;
    private String password;
    private String driver;
    private String dialect;
    private String version;


    public static void main(String[] args) {
        List<TreeNode<Integer>> databaseNodes = new ArrayList<>();
        TreeNode<Integer> database = new TreeNode<>(1, 0, "database1", 0);
        DatabaseMetadata databaseMetadata = new DatabaseMetadata();
        databaseMetadata.setName("database1");
        databaseMetadata.setComment("数据库1");
        databaseMetadata.setHost("127.0.0.1");
        databaseMetadata.setPort("3306");
        databaseMetadata.setUser("root");
        databaseMetadata.setPassword("123456");
        databaseMetadata.setDriver("com.mysql.cj.jdbc.Driver");
        databaseMetadata.setDialect("mysql");
        databaseMetadata.setVersion("8.0.28");
        database.setExtra(BeanUtil.beanToMap(databaseMetadata));

        TreeNode<Integer> tableNode = new TreeNode<>(2, 1, "table1", 0);
        TableMetadata tableMetadata = new TableMetadata();
        tableMetadata.setName("table1");
        tableMetadata.setComment("table1");
        tableMetadata.setPrefix("t_");
        tableNode.setExtra(BeanUtil.beanToMap(tableMetadata));

        TreeNode<Integer> columnNode = new TreeNode<>(3, 2, "column1", 0);
        ColumnMetadata columnMetadata = new ColumnMetadata();
        columnMetadata.setName("column1");
        columnMetadata.setComment("column1");
        columnMetadata.setDataType("varchar");
        columnMetadata.setLength("255");
        columnMetadata.setNullable(true);
        columnNode.setExtra(BeanUtil.beanToMap(columnMetadata));

        TreeNode<Integer> column2Node = new TreeNode<>(4, 2, "column2", 0);
        ColumnMetadata columnMetadata1 = new ColumnMetadata();
        columnMetadata1.setName("column2");
        columnMetadata1.setComment("column12");
        columnMetadata1.setDataType("varchar");
        columnMetadata1.setLength("19");
        columnMetadata1.setNullable(true);
        column2Node.setExtra(BeanUtil.beanToMap(columnMetadata1));


        databaseNodes.add(database);
        databaseNodes.add(tableNode);
        databaseNodes.add(columnNode);
        databaseNodes.add(column2Node);

        List<Tree<Integer>> databaseTree = TreeUtil.build(databaseNodes);
        System.out.println(JSONUtil.toJsonPrettyStr(databaseTree));
        System.out.println("=========================");


        ElementMetadata elementMetadata = new ElementMetadata();
        elementMetadata.setName("element1");
        elementMetadata.setComment("元素1");
        elementMetadata.setType("string");

        TreeNode<Integer> elementNode = new TreeNode<>(1, 0, "element1", 0);
        elementNode.setExtra(BeanUtil.beanToMap(elementMetadata));
        List<TreeNode<Integer>> elementNodes = new ArrayList<>();
        elementNodes.add(elementNode);

        List<Tree<Integer>> inputElementTree = TreeUtil.build(elementNodes);
        System.out.println(JSONUtil.toJsonPrettyStr(inputElementTree));
        System.out.println("=========================");


        FunctionMetadata functionMetadata = new FunctionMetadata();
        functionMetadata.setMethodCode("dd");
        functionMetadata.setMethodName("function1");
        functionMetadata.setMethodDescr("方法1");
        functionMetadata.setHttpMethod("get");
        functionMetadata.setInputs(inputElementTree);
        functionMetadata.setOutput(null);

        TreeNode<Integer> functionNode = new TreeNode<>(1, 0, "function1", 0);
        functionNode.setExtra(BeanUtil.beanToMap(functionMetadata));
        List<TreeNode<Integer>> functionNodes = new ArrayList<>();
        functionNodes.add(functionNode);

        List<Tree<Integer>> functionTree = TreeUtil.build(functionNodes);
        System.out.println(JSONUtil.toJsonPrettyStr(functionTree));
        System.out.println("=========================");


        List<TreeNode<Integer>> projectNodes = new ArrayList<>();
        TreeNode<Integer> projectNode = new TreeNode<>(1, 0, "module1", 0);
        ProjectMetadata projectMetadata = new ProjectMetadata();
        projectMetadata.setName("module1");
        projectMetadata.setComment("模块1");
        projectMetadata.setVersion("1.0.0");
        projectMetadata.setFunctions(functionTree);
        projectMetadata.setDatabases(databaseTree);
        projectNode.setExtra(BeanUtil.beanToMap(projectMetadata));
        projectNodes.add(projectNode);

        System.out.println(JSONUtil.toJsonPrettyStr(TreeUtil.build(projectNodes)));


    }
}
