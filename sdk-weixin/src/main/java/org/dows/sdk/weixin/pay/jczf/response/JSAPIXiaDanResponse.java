package org.dows.sdk.weixin.pay.jczf.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter4_5_1.shtml
 *
 * @author lait.zhang@gmail.com
 * @description
 * @date 2023年6月3日 上午10:38:51
 */
@Data
@Schema(name = "JSAPI下单Response", title = "JSAPI下单Response")
public class JSAPIXiaDanResponse {
    @Schema(title = "预支付交易会话标识。用于后续接口调用中使用，该值有效期为2小时")
    private String prepay_id;
}

