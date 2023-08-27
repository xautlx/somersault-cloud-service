package xyz.entdiy.somersault.module.system.convert.sms;

import xyz.entdiy.somersault.module.system.controller.admin.sms.vo.channel.SmsChannelCreateReqVO;
import xyz.entdiy.somersault.module.system.controller.admin.sms.vo.channel.SmsChannelRespVO;
import xyz.entdiy.somersault.module.system.controller.admin.sms.vo.channel.SmsChannelSimpleRespVO;
import xyz.entdiy.somersault.module.system.controller.admin.sms.vo.channel.SmsChannelUpdateReqVO;
import xyz.entdiy.somersault.module.system.dal.dataobject.sms.SmsChannelDO;
import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.framework.sms.core.property.SmsChannelProperties;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信渠道 Convert
 *
 * @author theMonkeyKing
 */
@Mapper
public interface SmsChannelConvert {

    SmsChannelConvert INSTANCE = Mappers.getMapper(SmsChannelConvert.class);

    SmsChannelDO convert(SmsChannelCreateReqVO bean);

    SmsChannelDO convert(SmsChannelUpdateReqVO bean);

    SmsChannelRespVO convert(SmsChannelDO bean);

    List<SmsChannelRespVO> convertList(List<SmsChannelDO> list);

    PageResult<SmsChannelRespVO> convertPage(PageResult<SmsChannelDO> page);

    List<SmsChannelProperties> convertList02(List<SmsChannelDO> list);

    List<SmsChannelSimpleRespVO> convertList03(List<SmsChannelDO> list);

}
