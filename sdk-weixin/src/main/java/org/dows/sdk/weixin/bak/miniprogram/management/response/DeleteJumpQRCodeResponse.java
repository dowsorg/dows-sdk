package org.dows.sdk.weixin.bak.miniprogram.management.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/miniprogram-management/jumpqrcode-config/deleteJumpQRCode.html
 *
 * @author lait.zhang@gmail.com
 * @description
 * @date 2023年5月29日 上午11:32:00
 */
@Data
@Schema(name = "DeleteJumpQRCodeResponse", title = "DeleteJumpQRCodeResponse")
public class DeleteJumpQRCodeResponse {
    @Schema(title = "错误码")
    private Integer errcode;
    @Schema(title = "错误信息")
    private String errmsg;
}

