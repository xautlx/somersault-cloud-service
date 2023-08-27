package xyz.entdiy.somersault.module.infra.dal.mysql.db;

import xyz.entdiy.somersault.framework.mybatis.core.mapper.BaseMapperX;
import xyz.entdiy.somersault.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author theMonkeyKing
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
