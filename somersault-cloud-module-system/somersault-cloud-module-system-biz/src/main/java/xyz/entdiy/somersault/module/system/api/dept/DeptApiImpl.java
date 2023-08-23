package xyz.entdiy.somersault.module.system.api.dept;

import xyz.entdiy.somersault.framework.common.pojo.CommonResult;
import xyz.entdiy.somersault.module.system.api.dept.dto.DeptRespDTO;
import xyz.entdiy.somersault.module.system.convert.dept.DeptConvert;
import xyz.entdiy.somersault.module.system.dal.dataobject.dept.DeptDO;
import xyz.entdiy.somersault.module.system.service.dept.DeptService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static xyz.entdiy.somersault.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class DeptApiImpl implements DeptApi {

    @Resource
    private DeptService deptService;

    @Override
    public CommonResult<DeptRespDTO> getDept(Long id) {
        DeptDO dept = deptService.getDept(id);
        return success(DeptConvert.INSTANCE.convert03(dept));
    }

    @Override
    public CommonResult<List<DeptRespDTO>> getDeptList(Collection<Long> ids) {
        List<DeptDO> depts = deptService.getDeptList(ids);
        return success(DeptConvert.INSTANCE.convertList03(depts));
    }

    @Override
    public CommonResult<Boolean> validateDeptList(Collection<Long> ids) {
        deptService.validateDeptList(ids);
        return success(true);
    }

}
