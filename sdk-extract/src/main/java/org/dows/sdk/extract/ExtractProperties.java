package org.dows.sdk.extract;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/11/2024 9:50 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Component
@ConfigurationProperties(prefix = "dows.sdk")
@Data
public class ExtractProperties {

    /**
     * weixin:
     * host: https://developers.weixin.qq.com
     * prefix: /doc/oplatform/openApi/OpenApiDoc/
     * seed: //aside[@class='sidebar']/div/div/ul/li//a
     * mapping:
     * - code: code
     * xpath: //div[@class='content custom']/h3[@id='接口英文名']/following-sibling::p[1]/text()
     * handler: WexinCodeExtractor.class
     * <p>
     * - code: code
     * xpath: //div[@class='content custom']/h3[@id='接口英文名']/following-sibling::p[1]/text()
     * handler: WexinCodeExtractor.class
     * <p>
     * - code: code
     * xpath: //div[@class='content custom']/h3[@id='接口英文名']/following-sibling::p[1]/text()
     * handler: WexinCodeExtractor.class
     */
    private Map<String, ExtractSetting> extractors;

}

