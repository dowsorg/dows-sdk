package org.dows.sdk.weixin.open.dsjglgzh.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/officalaccount-management/qrcode-config/deleteJumpQRCode.html
 *
 * @author lait.zhang@gmail.com
 * @description 删除已设置的二维码规则
 * @date 2023年6月3日 上午10:27:31
 */
@Data
@Schema(name = "删除已设置的二维码规则Response", title = "删除已设置的二维码规则Response")
public class ShanChuYiSheZhiDeErWeiMaGuiZeResponse {
    @Schema(title = "错误码")
    private Integer errcode;
    @Schema(title = "错误信息")
    private String errmsg;
}

