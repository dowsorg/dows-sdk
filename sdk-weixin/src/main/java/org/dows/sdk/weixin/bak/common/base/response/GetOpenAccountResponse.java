package org.dows.sdk.weixin.bak.common.base.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/openplatform-management/getOpenAccount.html
 *
 * @author lait.zhang@gmail.com
 * @description
 * @date 2023年5月29日 上午11:32:00
 */
@Data
@Schema(name = "GetOpenAccountResponse", title = "GetOpenAccountResponse")
public class GetOpenAccountResponse {
    @Schema(title = "公众号或小程序所绑定的开放平台帐号的 appid")
    private String open_appid;
    @Schema(title = "错误码")
    private Integer errcode;
    @Schema(title = "错误信息")
    private String errmsg;
}

