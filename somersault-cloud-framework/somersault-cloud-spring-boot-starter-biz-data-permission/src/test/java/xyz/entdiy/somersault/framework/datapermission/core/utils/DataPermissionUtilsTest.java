package xyz.entdiy.somersault.framework.datapermission.core.utils;

import xyz.entdiy.somersault.framework.datapermission.core.aop.DataPermissionContextHolder;
import xyz.entdiy.somersault.framework.datapermission.core.util.DataPermissionUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataPermissionUtilsTest {

    @Test
    public void testExecuteIgnore() {
        DataPermissionUtils.executeIgnore(() -> assertFalse(DataPermissionContextHolder.get().enable()));
    }

}
