package org.dows.sdk.weixin.open.dsjglxcx.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/miniprogram-management/basic-info-management/getNickNameStatus.html
 *
 * @author lait.zhang@gmail.com
 * @description 查询小程序名称审核状态
 * @date 2023年6月3日 上午10:27:31
 */
@Data
@Schema(name = "查询小程序名称审核状态Response", title = "查询小程序名称审核状态Response")
public class ChaXunXiaoChengXuMingChengShenHeZhuangTaiResponse {
    @Schema(title = "返回码")
    private Integer errcode;
    @Schema(title = "错误信息")
    private String errmsg;
    @Schema(title = "审核昵称")
    private String nickname;
    @Schema(title = "审核状态，1：审核中，2：审核失败，3：审核成功")
    private Integer audit_stat;
    @Schema(title = "失败原因")
    private String fail_reason;
    @Schema(title = "审核提交时间")
    private Integer create_time;
    @Schema(title = "审核完成时间")
    private Integer audit_time;
}

