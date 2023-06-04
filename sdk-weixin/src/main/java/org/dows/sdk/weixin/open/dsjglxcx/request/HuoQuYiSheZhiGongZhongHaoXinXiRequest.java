package org.dows.sdk.weixin.open.dsjglxcx.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/miniprogram-management/subscribe-component/getShowItem.html
 *
 * @author lait.zhang@gmail.com
 * @description 获取已设置公众号信息
 * @date 2023年6月3日 上午10:27:31
 */
@Data
@Schema(name = "获取已设置公众号信息Request", title = "获取已设置公众号信息Request")
public class HuoQuYiSheZhiGongZhongHaoXinXiRequest {
    @Schema(title = "接口调用凭证，该参数为 URL 参数，非 Body 参数。使用")
    private String access_token;
}

