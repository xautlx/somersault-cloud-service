package xyz.entdiy.somersault.module.system.convert.notice;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.module.system.controller.admin.notice.vo.NoticeCreateReqVO;
import xyz.entdiy.somersault.module.system.controller.admin.notice.vo.NoticeRespVO;
import xyz.entdiy.somersault.module.system.controller.admin.notice.vo.NoticeUpdateReqVO;
import xyz.entdiy.somersault.module.system.dal.dataobject.notice.NoticeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);

    PageResult<NoticeRespVO> convertPage(PageResult<NoticeDO> page);

    NoticeRespVO convert(NoticeDO bean);

    NoticeDO convert(NoticeUpdateReqVO bean);

    NoticeDO convert(NoticeCreateReqVO bean);

}
