package com.jarvis.platform.app.constanst;


import static com.jarvis.framework.constant.SymbolConstant.COLON;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年5月30日
 */
public class AppCacheKeyConstant {

    /** 配置服务缓存前缀 */
    public final static String CACHE_PREFIX = "archives-configure";

    /** 档案组缓存前缀 */
    public final static String GROUP = CACHE_PREFIX + COLON + "group";

    /** 编码缓存前缀 */
    public final static String CODE = CACHE_PREFIX + COLON + "code";

    /** 档案门类缓存前缀 */
    public final static String ARCHIVE_TYPE = CACHE_PREFIX + COLON + "archive-type";

    /** 档案门类下所有表缓存前缀 */
    public final static String ARCHIVE_TYPE_TABLES = CACHE_PREFIX + COLON + "archive-type-tables";

    /** 档案门类表缓存前缀 */
    public final static String ARCHIVE_TABLE = CACHE_PREFIX + COLON + "archive-table";

    /** 档案门类表字段集合缓存前缀 */
    public final static String ARCHIVE_TABLE_COLUMNS = CACHE_PREFIX + COLON + "archive-table-columns";

    /** 档案门类字段缓存前缀 */
    public final static String ARCHIVE_COLUMN = CACHE_PREFIX + COLON + "archive-column";

    /** 档案门类列表配置缓存前缀 */
    public final static String TYPE_LIST = CACHE_PREFIX + COLON + "type-list";

    /** 档案门类表单配置缓存前缀 */
    public final static String TYPE_FORM = CACHE_PREFIX + COLON + "type-form";

    /** 档案门类基础检索配置缓存前缀 */
    public final static String TYPE_RETRIEVE = CACHE_PREFIX + COLON + "type-retrieve";

    /** 档案门类快速检索配置缓存前缀 */
    public final static String TYPE_RAPID = CACHE_PREFIX + COLON + "type-rapid";

    /** 档案门类快速检索配置缓存前缀 */
    public final static String TYPE_SORT = CACHE_PREFIX + COLON + "type-sort";

    /** 档案门类档案盒配置缓存前缀 */
    public final static String TYPE_BOX = CACHE_PREFIX + COLON + "type-box";

}
