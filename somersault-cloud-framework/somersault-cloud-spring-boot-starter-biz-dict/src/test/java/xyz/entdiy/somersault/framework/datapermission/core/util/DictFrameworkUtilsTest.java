package xyz.entdiy.somersault.framework.datapermission.core.util;

import xyz.entdiy.somersault.framework.common.enums.CommonStatusEnum;
import xyz.entdiy.somersault.framework.common.pojo.CommonResult;
import xyz.entdiy.somersault.framework.dict.core.util.DictFrameworkUtils;
import xyz.entdiy.somersault.framework.test.core.ut.BaseMockitoUnitTest;
import xyz.entdiy.somersault.module.system.api.dict.DictDataApi;
import xyz.entdiy.somersault.module.system.api.dict.dto.DictDataRespDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static xyz.entdiy.somersault.framework.test.core.util.RandomUtils.randomPojo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * {@link DictFrameworkUtils} 的单元测试
 */
public class DictFrameworkUtilsTest extends BaseMockitoUnitTest {

    @Mock
    private DictDataApi dictDataApi;

    @BeforeEach
    public void setUp() {
        DictFrameworkUtils.init(dictDataApi);
    }

    @Test
    public void testGetDictDataLabel() {
        // mock 数据
        DictDataRespDTO dataRespDTO = randomPojo(DictDataRespDTO.class, o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        // mock 方法
        when(dictDataApi.getDictData(dataRespDTO.getDictType(), dataRespDTO.getValue())).thenReturn(CommonResult.success(dataRespDTO));
        // 断言返回值
        assertEquals(dataRespDTO.getLabel(), DictFrameworkUtils.getDictDataLabel(dataRespDTO.getDictType(), dataRespDTO.getValue()));
    }

    @Test
    public void testParseDictDataValue() {
        // mock 数据
        DictDataRespDTO resp = randomPojo(DictDataRespDTO.class, o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        // mock 方法
        when(dictDataApi.parseDictData(resp.getDictType(), resp.getLabel())).thenReturn(CommonResult.success(resp));
        // 断言返回值
        assertEquals(resp.getValue(), DictFrameworkUtils.parseDictDataValue(resp.getDictType(), resp.getLabel()));
    }

}
