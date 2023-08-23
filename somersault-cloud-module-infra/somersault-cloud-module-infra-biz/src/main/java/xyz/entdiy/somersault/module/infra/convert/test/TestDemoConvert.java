package xyz.entdiy.somersault.module.infra.convert.test;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.module.infra.controller.admin.test.vo.TestDemoCreateReqVO;
import xyz.entdiy.somersault.module.infra.controller.admin.test.vo.TestDemoExcelVO;
import xyz.entdiy.somersault.module.infra.controller.admin.test.vo.TestDemoRespVO;
import xyz.entdiy.somersault.module.infra.controller.admin.test.vo.TestDemoUpdateReqVO;
import xyz.entdiy.somersault.module.infra.dal.dataobject.test.TestDemoDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 字典类型 Convert
 *
 * @author entdiy.xyz
 */
@Mapper
public interface TestDemoConvert {

    TestDemoConvert INSTANCE = Mappers.getMapper(TestDemoConvert.class);

    TestDemoDO convert(TestDemoCreateReqVO bean);

    TestDemoDO convert(TestDemoUpdateReqVO bean);

    TestDemoRespVO convert(TestDemoDO bean);

    List<TestDemoRespVO> convertList(List<TestDemoDO> list);

    PageResult<TestDemoRespVO> convertPage(PageResult<TestDemoDO> page);

    List<TestDemoExcelVO> convertList02(List<TestDemoDO> list);

}
