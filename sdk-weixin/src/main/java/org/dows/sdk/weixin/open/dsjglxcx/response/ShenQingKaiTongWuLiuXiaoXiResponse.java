package org.dows.sdk.weixin.open.dsjglxcx.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/miniprogram-management/logistics-service/applyMsgPlugin.html
 *
 * @author lait.zhang@gmail.com
 * @description 申请开通物流消息
 * @date 2023年6月3日 上午10:27:31
 */
@Data
@Schema(name = "申请开通物流消息Response", title = "申请开通物流消息Response")
public class ShenQingKaiTongWuLiuXiaoXiResponse {
    @Schema(title = "错误码")
    private Integer errcode;
    @Schema(title = "错误信息")
    private String errmsg;
}

