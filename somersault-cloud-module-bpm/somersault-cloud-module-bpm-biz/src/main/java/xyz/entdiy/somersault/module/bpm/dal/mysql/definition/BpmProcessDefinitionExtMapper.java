package xyz.entdiy.somersault.module.bpm.dal.mysql.definition;

import xyz.entdiy.somersault.framework.mybatis.core.query.LambdaQueryWrapperX;
import xyz.entdiy.somersault.module.bpm.dal.dataobject.definition.BpmProcessDefinitionExtDO;
import xyz.entdiy.somersault.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface BpmProcessDefinitionExtMapper extends BaseMapperX<BpmProcessDefinitionExtDO> {

    default List<BpmProcessDefinitionExtDO> selectListByProcessDefinitionIds(Collection<String> processDefinitionIds) {
        return selectList("process_definition_id", processDefinitionIds);
    }

    default BpmProcessDefinitionExtDO selectByProcessDefinitionId(String processDefinitionId) {
        return selectOne("process_definition_id", processDefinitionId);
    }

}
