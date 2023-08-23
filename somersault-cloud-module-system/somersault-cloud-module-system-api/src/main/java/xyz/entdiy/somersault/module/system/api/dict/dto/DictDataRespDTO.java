package xyz.entdiy.somersault.module.system.api.dict.dto;

import xyz.entdiy.somersault.framework.common.enums.CommonStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "RPC 服务 - 字典数据 Response DTO")
@Data
public class DictDataRespDTO {

    @Schema(description = "字典标签", required = true)
    private String label;
    @Schema(description = "字典值", required = true)
    private String value;
    @Schema(description = "字典类型", required = true, example = "sys_common_sex")
    private String dictType;
    @Schema(description = "状态,见 CommonStatusEnum 枚举", required = true, example = "1")
    private Integer status;

}