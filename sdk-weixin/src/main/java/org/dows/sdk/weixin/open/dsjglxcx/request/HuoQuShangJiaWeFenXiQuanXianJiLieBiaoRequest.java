package org.dows.sdk.weixin.open.dsjglxcx.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/miniprogram-management/we-analysis/weDataGetPermList.html
 *
 * @author lait.zhang@gmail.com
 * @description 获取商家We分析权限集列表
 * @date 2023年6月3日 上午10:27:31
 */
@Data
@Schema(name = "获取商家We分析权限集列表Request", title = "获取商家We分析权限集列表Request")
public class HuoQuShangJiaWeFenXiQuanXianJiLieBiaoRequest {
    @Schema(title = "接口调用凭证，该参数为 URL 参数，非 Body 参数。使用")
    private String access_token;
}

