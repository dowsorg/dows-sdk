package org.dows.sdk.weixin.bak.cloudbase.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/cloudbase-common/scf-management/getCodeSecret.html
 *
 * @author lait.zhang@gmail.com
 * @description
 * @date 2023年5月29日 上午11:32:00
 */
@Data
@Schema(name = "GetCodeSecretRequest", title = "GetCodeSecretRequest")
public class GetCodeSecretRequest {
    @Schema(title = "接口调用凭证，该参数为 URL 参数，非 Body 参数。使用")
    private String access_token;
}

