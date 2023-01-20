package com.jarvis.platform.app.constanst;

import com.jarvis.framework.constant.BaseColumnConstant;

/**
 * 门类字段常量
 *
 * @author qiucs
 * @version 1.0.0 2022年9月29日
 */
public final class TypeColumnConstant extends BaseColumnConstant {

    /** 删除人 */
    public static final String DELETED_BY = "deleted_by";

    /** 删除时间 */
    public static final String DELETED_TIME = "deleted_time";

    /** 状态 **/
    public static final String STATUS = "status";

    /** 所属ID **/
    public static final String OWNER_ID = "owner_id";

    /** 所属ID路径：0/1/2 **/
    public static final String OWNER_PATH = "owner_path";

    /** 数量 **/
    public static final String ITEM_COUNT = "item_count";

    /** 存储位置 **/
    public static final String LOCATION = "location";

    /** 存储相对路径 **/
    public static final String FILE_PATH = "file_path";

    /** 文件名称 **/
    public static final String FILE_NAME = "file_name";

    /** 文件格式 **/
    public static final String FILE_FORMAT = "file_format";

    /** 文件大小 **/
    public static final String FILE_SIZE = "file_size";

}
