package com.jarvis.framework.core.web;

import java.util.HashMap;
import java.util.Map;


/**
 * 数据列表页面请求数据
 *
 * @author Administrator
 */
public class GridRequest {

    /**
     * 待获取记录条数
     */
    private Integer take;

    /**
     * 跳过记录条数
     */
    private Integer skip;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 每页记录数
     */
    private Integer size;

    /**
     * 总记录数
     */
    private Integer total;

    /**
     * 查询条件
     */
    private Filter filter;

    /**
     * 排序条件
     */
    private Sort sort;

    public GridRequest() {
        if (filter == null) {
            filter = new Filter();
        }
    }

    /**
     * 添加筛选条件
     *
     * @param key   筛选条件-键
     * @param value 筛选条件-值
     */
    public void addFilter(String key, Object value) {
        String strValue;
        if (value instanceof Enum) {
            strValue = Integer.toString(Integer.parseInt(value.toString()));
        } else {
            strValue = value.toString();
        }
        addFilter(key, strValue);
    }

    /**
     * 添加筛选条件
     *
     * @param key   筛选条件-键
     * @param value 筛选条件-值
     */
    public void addFilter(String key, String value) {
        if (filter == null) {
            filter = new Filter();
        }
        if (filter.filters == null) {
            filter.filters = new HashMap<>(0);
        }
        filter.filters.put(key, value);
    }

    public Integer getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Filter getFilter() {
        return filter;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    /**
     * 筛选条件
     */
    public class Filter {

        /**
         * 连接条件
         */
        private String logic;

        /**
         * 查询条件列表
         */
        private Map<String, String> filters;

        public String getLogic() {
            return logic;
        }

        public void setLogic(String logic) {
            this.logic = logic;
        }

        public Map<String, String> getFilters() {
            return filters;
        }

        public void setFilters(Map<String, String> filters) {
            this.filters = filters;
        }

    }

    /**
     * 排序条件
     */
    public class Sort {

        /**
         * 排序字符
         */
        private String field;

        /**
         * 排序类型
         */
        private String dir;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }
    }
}
