package org.dows.sdk.weixin.open.dsjglxcx.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/miniprogram-management/domain-management/setPrefetchDomain.html
 *
 * @author lait.zhang@gmail.com
 * @description 设置DNS预解析域名
 * @date 2023年6月3日 上午10:27:31
 */
@Data
@Schema(name = "设置DNS预解析域名Response", title = "设置DNS预解析域名Response")
public class SheZhiDNSYuJieXiYuMingResponse {
    @Schema(title = "错误码")
    private Integer errcode;
    @Schema(title = "错误信息")
    private String errmsg;
}

