package xyz.entdiy.somersault.module.system.convert.notify;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.module.system.controller.admin.notify.vo.message.NotifyMessageRespVO;
import xyz.entdiy.somersault.module.system.dal.dataobject.notify.NotifyMessageDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 站内信 Convert
 *
 * @author theMonkeyKing
 */
@Mapper
public interface NotifyMessageConvert {

    NotifyMessageConvert INSTANCE = Mappers.getMapper(NotifyMessageConvert.class);

    NotifyMessageRespVO convert(NotifyMessageDO bean);

    List<NotifyMessageRespVO> convertList(List<NotifyMessageDO> list);

    PageResult<NotifyMessageRespVO> convertPage(PageResult<NotifyMessageDO> page);


}
