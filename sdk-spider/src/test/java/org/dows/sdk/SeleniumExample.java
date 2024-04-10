package org.dows.sdk;

import cn.hutool.core.lang.tree.TreeNode;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.ArrayList;
import java.util.List;

public class SeleniumExample {
    public static void main(String[] args) {
        List<TreeNode<String>> treeNodes = new ArrayList<>();
        BeanBuilder.buildTree("weixin", "ticket-token@startPushTicket", treeNodes, "https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/ticket-token/startPushTicket.html");


        // 设置ChromeDriver的路径webdriver.edge.driver,webdriver.chrome.driver
        System.setProperty("webdriver.edge.driver", "E:\\workspaces\\project\\dows\\dows-sdk\\sdk-spider\\src\\main\\resources\\dirver\\msedgedriver.exe");
        // 创建一个WebDriver实例
        WebDriver driver = new EdgeDriver();

        // 打开目标网页
        driver.get("https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/ams/ad-black/GetAmsCategoryBlackList.html");

        // 使用JavascriptExecutor获取页面内容
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String pageContent = (String) js.executeScript("return document.documentElement.innerHTML;");

        // 输出页面内容
        System.out.println(pageContent);

        // 关闭WebDriver实例
        driver.quit();
    }
}