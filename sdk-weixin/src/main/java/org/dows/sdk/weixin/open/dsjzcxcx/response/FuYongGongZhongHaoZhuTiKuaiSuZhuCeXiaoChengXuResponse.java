package org.dows.sdk.weixin.open.dsjzcxcx.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/register-management/fast-registration-officalaccount/registerMiniprogramByOffiaccount.html
 *
 * @author lait.zhang@gmail.com
 * @description 复用公众号主体快速注册小程序
 * @date 2023年6月3日 上午10:27:31
 */
@Data
@Schema(name = "复用公众号主体快速注册小程序Response", title = "复用公众号主体快速注册小程序Response")
public class FuYongGongZhongHaoZhuTiKuaiSuZhuCeXiaoChengXuResponse {
    @Schema(title = "错误码")
    private Integer errcode;
    @Schema(title = "错误信息")
    private String errmsg;
    @Schema(title = "新创建小程序的 appid")
    private String appid;
    @Schema(title = "新创建小程序的授权码；注：使用 appid 及 authorization_code 换取 authorizer_refresh_token 后需及时保存。")
    private String authorization_code;
    @Schema(title = "复用公众号微信认证小程序是否成功")
    private Boolean is_wx_verify_succ;
    @Schema(title = "小程序是否和公众号关联成功")
    private Boolean is_link_succ;
}

