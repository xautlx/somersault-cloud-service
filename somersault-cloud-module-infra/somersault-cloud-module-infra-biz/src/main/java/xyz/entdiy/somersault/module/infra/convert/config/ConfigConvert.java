package xyz.entdiy.somersault.module.infra.convert.config;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.module.infra.controller.admin.config.vo.ConfigCreateReqVO;
import xyz.entdiy.somersault.module.infra.controller.admin.config.vo.ConfigExcelVO;
import xyz.entdiy.somersault.module.infra.controller.admin.config.vo.ConfigRespVO;
import xyz.entdiy.somersault.module.infra.controller.admin.config.vo.ConfigUpdateReqVO;
import xyz.entdiy.somersault.module.infra.dal.dataobject.config.ConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ConfigConvert {

    ConfigConvert INSTANCE = Mappers.getMapper(ConfigConvert.class);

    PageResult<ConfigRespVO> convertPage(PageResult<ConfigDO> page);

    @Mapping(source = "configKey", target = "key")
    ConfigRespVO convert(ConfigDO bean);

    @Mapping(source = "key", target = "configKey")
    ConfigDO convert(ConfigCreateReqVO bean);

    ConfigDO convert(ConfigUpdateReqVO bean);

    @Mapping(source = "configKey", target = "key")
    List<ConfigExcelVO> convertList(List<ConfigDO> list);

}
