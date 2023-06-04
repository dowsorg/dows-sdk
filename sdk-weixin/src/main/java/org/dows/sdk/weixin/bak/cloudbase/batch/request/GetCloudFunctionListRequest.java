package org.dows.sdk.weixin.bak.cloudbase.batch.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/cloudbase-batch/scf-mgnt/getCloudFunctionList.html
 *
 * @author lait.zhang@gmail.com
 * @description
 * @date 2023年5月29日 上午11:32:00
 */
@Data
@Schema(name = "GetCloudFunctionListRequest", title = "GetCloudFunctionListRequest")
public class GetCloudFunctionListRequest {
    @Schema(title = "接口调用凭证，该参数为 URL 参数，非 Body 参数。使用")
    private String access_token;
    @Schema(title = "环境ID")
    private String env;
    @Schema(title = "数据偏移量。默认值0")
    private Integer offset;
    @Schema(title = "返回数据长度。默认值20")
    private Integer limit;
    @Schema(title = "模糊匹配 functionName")
    private String searchkey;
}

