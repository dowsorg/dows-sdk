package org.dows.sdk.weixin.open.dsjglxcx.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/miniprogram-management/code-management/undoAudit.html
 *
 * @author lait.zhang@gmail.com
 * @description 撤回代码审核
 * @date 2023年6月3日 上午10:27:31
 */
@Data
@Schema(name = "撤回代码审核Response", title = "撤回代码审核Response")
public class CheHuiDaiMaShenHeResponse {
    @Schema(title = "返回码")
    private Integer errcode;
    @Schema(title = "错误信息")
    private String errmsg;
}

