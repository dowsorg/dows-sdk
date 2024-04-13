package org.dows.sdk;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;
import org.dows.sdk.extract.FunctionMetadata;
import org.dows.sdk.spider.SdkSpider;
import org.dows.sdk.tooler.MavenTooler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    private MavenTooler mavenTooler;

    @Test
    public void test() {
        // extract
        //List<ExtractElement> extractElements = weixinOpenSpider.crawling("weixin-open");
        List<FunctionMetadata> functionMetadata = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CollectionType collectionType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, FunctionMetadata.class);
            InputStream inputStream = new ClassPathResource("metadata.json").getInputStream();
            functionMetadata = objectMapper.readValue(inputStream, collectionType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info(JSONUtil.toJsonPrettyStr(functionMetadata));
        // todo builder
        mavenTooler.build("weixin-open", functionMetadata);
        // todo dispatch


    }
}

