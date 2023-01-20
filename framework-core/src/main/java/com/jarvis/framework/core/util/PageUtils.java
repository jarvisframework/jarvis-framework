package com.jarvis.framework.core.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.jarvis.framework.core.web.GridRequest;

import java.util.Map;
import java.util.Optional;

/**
 * <p>页面工具类</p>
 *
 * @author 王涛
 * @since 1.0, 2020-11-09 10:05:07
 */
public abstract class PageUtils {

    /**
     * 获取查询包装器
     *
     * @param request 列表请求对象
     * @return 查询包装器
     */
    public static <T> QueryWrapper<T> getQueryWrapper(GridRequest request) {

        Map<String, String> queryMap = getQueryMap(request);
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for (Map.Entry<String, String> map : queryMap.entrySet()) {
            wrapper.eq(map.getKey(), map.getValue());
        }
        return wrapper;
    }

    /**
     * 获取查询Map
     *
     * @param request 列表请求对象
     * @return 查询Map
     */
    public static Map<String, String> getQueryMap(GridRequest request) {
        if (Optional.ofNullable(request.getSort()).isPresent()) {
            PageHelper.orderBy(String.format("%s %s", request.getSort().getField(), request.getSort().getDir()));
        }
        PageHelper.startPage(request.getCurrent(), request.getSize());
        Map<String, String> queryMap = request.getFilter().getFilters();
        return queryMap;
    }

}
