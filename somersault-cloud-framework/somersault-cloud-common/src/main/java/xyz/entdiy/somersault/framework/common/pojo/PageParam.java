package xyz.entdiy.somersault.framework.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Schema(description="分页参数")
@Data
public class PageParam implements Serializable {

    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    @Schema(description = "页码，从 1 开始", requiredMode = Schema.RequiredMode.REQUIRED,example = "1")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNo = PAGE_NO;

    @Schema(description = "每页条数，最大值为 100", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 10000, message = "每页条数最大值为 10000")
    private Integer pageSize = PAGE_SIZE;

    @Schema(description = "分页排序", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "id,-createTime")
    private String sortOrder;

    /**
     * 基于前端排序参数组装解析参数对象
     */
    public Collection<SortingField> getSortingFields() {
        Collection<SortingField> sortingFields = null;
        if (StringUtils.hasLength(sortOrder)) {
            sortingFields = new ArrayList<>();
            for (String sortIn : sortOrder.split(",")) {
                SortingField sortingField = new SortingField();
                if (sortIn.startsWith("-")) {
                    sortingField.setField(StrUtil.toUnderlineCase(sortIn.substring(1)));
                    sortingField.setOrder(SortingField.ORDER_DESC);
                } else {
                    sortingField.setField(StrUtil.toUnderlineCase(sortIn));
                    sortingField.setOrder(SortingField.ORDER_ASC);
                }
                sortingFields.add(sortingField);
            }
        }
        return sortingFields;
    }

}
