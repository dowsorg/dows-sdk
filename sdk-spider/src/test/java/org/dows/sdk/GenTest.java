package org.dows.sdk;

import cn.hutool.extra.template.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.extract.ExtractElement;
import org.dows.sdk.spider.SdkSpider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/11/2024 1:41 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Slf4j
@SpringBootTest
public class GenTest {

    @Autowired
    private SdkSpider weixinOpenSpider;

    @Autowired
    private MavenBuilder mavenBuilder;

    @Test
    public void test() {
        // extract
        List<ExtractElement> extractElements = weixinOpenSpider.crawling("weixin-open");
        // todo build
        mavenBuilder.build("weixin-open",extractElements);
        // todo dispatch


    }
}

