package xyz.entdiy.somersault.module.bpm.convert.definition;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.module.bpm.controller.admin.definition.vo.form.BpmFormCreateReqVO;
import xyz.entdiy.somersault.module.bpm.controller.admin.definition.vo.form.BpmFormRespVO;
import xyz.entdiy.somersault.module.bpm.controller.admin.definition.vo.form.BpmFormSimpleRespVO;
import xyz.entdiy.somersault.module.bpm.controller.admin.definition.vo.form.BpmFormUpdateReqVO;
import xyz.entdiy.somersault.module.bpm.dal.dataobject.definition.BpmFormDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 动态表单 Convert
 *
 * @author theMonkeyKing
 */
@Mapper
public interface BpmFormConvert {

    BpmFormConvert INSTANCE = Mappers.getMapper(BpmFormConvert.class);

    BpmFormDO convert(BpmFormCreateReqVO bean);

    BpmFormDO convert(BpmFormUpdateReqVO bean);

    BpmFormRespVO convert(BpmFormDO bean);

    List<BpmFormSimpleRespVO> convertList2(List<BpmFormDO> list);

    PageResult<BpmFormRespVO> convertPage(PageResult<BpmFormDO> page);

}
