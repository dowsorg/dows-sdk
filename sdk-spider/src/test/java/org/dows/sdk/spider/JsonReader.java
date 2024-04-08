package org.dows.sdk.spider;


import cn.hutool.core.io.resource.ClassPathResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/8/2024 6:18 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
public class JsonReader {

    private static final Logger log = LoggerFactory.getLogger(JsonReader.class);

    public static Map<String, Object> readJsonFile(InputStream inputStream) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(inputStream, Map.class);
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> map = readJsonFile(new ClassPathResource("name.json").getStream());
            // 使用map中的数据
            log.info("....");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

