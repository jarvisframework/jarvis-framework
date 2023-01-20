package com.jarvis.framework.core.web;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>数据列表页面响应数据</p>
 *
 * @author 王涛
 * @since 1.0, 2020-11-26 18:02:51
 */
public class GridResponse implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 3898575975814621570L;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页数
     */
    private Long current;

    /**
     * 数据源
     */
    private List<?> data;


    private GridResponse(long total, long current, List<?> data) {
        this.total = total;
        this.current = current;
        this.data = data;

    }

    /**
     * 构建一个空的响应对象
     *
     * @return GridResponse
     */
    public static GridResponse create() {
        return new GridResponse(0, 0, new ArrayList<>());
    }

    /**
     * 构建一个带分页参数的响应对象
     *
     * @param data 响应数据列表
     * @return GridResponse
     */
    public static GridResponse create(List<?> data) {
        PageInfo<?> pageInfo = new PageInfo<>(data);
        return new GridResponse(pageInfo.getTotal(), pageInfo.getPageNum(), data);
    }

    /**
     * 构建一个带分页参数的响应对象
     *
     * @param total   响应总记录数
     * @param current 响应当前页数
     * @param data    响应数据列表
     * @return GridResponse
     */
    public static GridResponse create(long total, long current, List<?> data) {
        return new GridResponse(total, current, data);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
