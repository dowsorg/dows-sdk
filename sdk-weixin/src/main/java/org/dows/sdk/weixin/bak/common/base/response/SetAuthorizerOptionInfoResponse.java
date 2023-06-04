package org.dows.sdk.weixin.bak.common.base.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/authorization-management/setAuthorizerOptionInfo.html
 *
 * @author lait.zhang@gmail.com
 * @description
 * @date 2023年5月29日 上午11:32:00
 */
@Data
@Schema(name = "SetAuthorizerOptionInfoResponse", title = "SetAuthorizerOptionInfoResponse")
public class SetAuthorizerOptionInfoResponse {
    @Schema(title = "错误码")
    private Integer errcode;
    @Schema(title = "错误信息")
    private String errmsg;
}

