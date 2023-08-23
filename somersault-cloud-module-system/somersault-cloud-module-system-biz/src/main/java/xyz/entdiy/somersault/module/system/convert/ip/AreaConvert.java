package xyz.entdiy.somersault.module.system.convert.ip;

import xyz.entdiy.somersault.framework.ip.core.Area;
import xyz.entdiy.somersault.framework.ip.core.enums.AreaTypeEnum;
import xyz.entdiy.somersault.module.system.controller.admin.ip.vo.AreaNodeRespVO;
import xyz.entdiy.somersault.module.system.controller.admin.ip.vo.AreaNodeSimpleRespVO;
import xyz.entdiy.somersault.module.system.controller.app.ip.vo.AppAreaNodeRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

@Mapper
public interface AreaConvert {

    AreaConvert INSTANCE = Mappers.getMapper(AreaConvert.class);

    List<AreaNodeRespVO> convertList(List<Area> list);

    List<AreaNodeSimpleRespVO> convertList2(List<Area> list);

    @Mapping(source = "type", target = "leaf")
    AreaNodeSimpleRespVO convert(Area area);

    default Boolean convertAreaType(Integer type) {
        return Objects.equals(AreaTypeEnum.DISTRICT.getType(), type);
    }

    List<AppAreaNodeRespVO> convertList3(List<Area> list);

}
