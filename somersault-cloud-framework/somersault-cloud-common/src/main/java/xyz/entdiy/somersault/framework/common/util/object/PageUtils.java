package xyz.entdiy.somersault.framework.common.util.object;

import xyz.entdiy.somersault.framework.common.pojo.PageParam;

/**
 * {@link xyz.entdiy.somersault.framework.common.pojo.PageParam} 工具类
 *
 * @author entdiy.xyz
 */
public class PageUtils {

    public static int getStart(PageParam pageParam) {
        return (pageParam.getPageNo() - 1) * pageParam.getPageSize();
    }

}
