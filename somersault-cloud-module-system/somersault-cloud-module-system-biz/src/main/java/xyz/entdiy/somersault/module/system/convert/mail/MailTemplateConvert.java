package xyz.entdiy.somersault.module.system.convert.mail;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.module.system.controller.admin.mail.vo.template.MailTemplateCreateReqVO;
import xyz.entdiy.somersault.module.system.controller.admin.mail.vo.template.MailTemplateRespVO;
import xyz.entdiy.somersault.module.system.controller.admin.mail.vo.template.MailTemplateSimpleRespVO;
import xyz.entdiy.somersault.module.system.controller.admin.mail.vo.template.MailTemplateUpdateReqVO;
import xyz.entdiy.somersault.module.system.dal.dataobject.mail.MailTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MailTemplateConvert {

    MailTemplateConvert INSTANCE = Mappers.getMapper(MailTemplateConvert.class);

    MailTemplateDO convert(MailTemplateUpdateReqVO bean);

    MailTemplateDO convert(MailTemplateCreateReqVO bean);

    MailTemplateRespVO convert(MailTemplateDO bean);

    PageResult<MailTemplateRespVO> convertPage(PageResult<MailTemplateDO> pageResult);

    List<MailTemplateSimpleRespVO> convertList02(List<MailTemplateDO> list);

}
