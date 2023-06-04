package org.dows.sdk.weixin.open.pldykf.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * https://developers.weixin.qq.com/doc/oplatform/openApi/OpenApiDoc/cloudbase-batch/db-mgnt/dbImport.html
 *
 * @author lait.zhang@gmail.com
 * @description 数据库导入
 * @date 2023年6月3日 上午10:27:31
 */
@Data
@Schema(name = "数据库导入Request", title = "数据库导入Request")
public class ShuJuKuDaoRuRequest {
    @Schema(title = "接口调用凭证，该参数为 URL 参数，非 Body 参数。使用")
    private String access_token;
    @Schema(title = "云环境ID")
    private String env;
    @Schema(title = "导入collection名")
    private String collection_name;
    @Schema(title = "导入文件路径(导入文件需先上传到同环境的存储中，可使用开发者工具或 HTTP API的上传文件 API上传）")
    private String file_path;
    @Schema(title = "导入文件类型，文件格式参考数据库导入指引中的 文件格式 部分")
    private Integer file_type;
    @Schema(title = "是否在遇到错误时停止导入")
    private Boolean stop_on_error;
    @Schema(title = "冲突处理模式")
    private Integer conflict_mode;
}

