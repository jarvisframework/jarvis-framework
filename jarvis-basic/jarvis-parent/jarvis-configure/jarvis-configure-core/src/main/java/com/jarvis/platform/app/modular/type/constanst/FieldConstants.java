package com.jarvis.platform.app.modular.type.constanst;

/**
 * 字段常量类
 *
 */
public class FieldConstants {

    /**
     * ID
     */
    public final static String ID = "id";

    /**
     * 所属ID
     */
    public final static String OWNER_ID = "owner_id";

    /**
     * 是否删除
     */
    public final static String IS_DELETE = "deleted";

    /**
     * 创建时间
     */
    public final static String CREATED_TIME = "created_time";

    /**
     * 创建人id
     */
    public final static String CREATED_BY = "created_by";

    /**
     * 更新时间
     */
    public final static String UPDATED_TIME = "updated_time";

    /**
     * 更新人
     */
    public final static String UPDATED_BY = "updated_by";
    /**
     * 删除时间
     */
    public final static String DELETED_TIME = "deleted_time";
    /**
     * 删除人
     */
    public final static String DELETE_BY = "delete_by";

    /**
     * 乐观锁
     */
    public final static String REVISION = "revision";

    /**
     * 题名
     */
    public final static String TITLE_PROPER = "title_proper";

    /**
     * 责任者
     */
    public final static String AUTHOR = "author";

    /**
     * 保管期限
     */
    public final static String RETENTION_PERIOD = "retention_period";

    /**
     * 全宗号
     */
    public final static String FONDS_CODE = "fonds_code";

    /**
     * 全宗名称
     */
    public final static String FONDS_NAME = "fonds_name";

    /**
     * 年度
     */
    public final static String YEAR_CODE = "year_code";

    /**
     * 密级
     */
    public final static String SECURITY_CLASSIFICATION = "security_classification";

    /**
     * 状态
     */
    public final static String STATUS = "status";

    /**
     * 档号
     */
    public final static String OFFICE_ARCHIVAL_CODE = "office_archival_code";

    /**
     * 档案类型编码
     */
    public final static String ARCHIVE_TYPE_CODE = "type_code";

    /**
     * 档案层级
     */
    public final static String LAYER_CODE = "layer_code";

    /**
     * 整理方式
     */
    public final static String FILING_CODE = "filing_code";


    /**
     * 表模板ID
     */
    public final static String TEMPLATE_TABLE_ID = "template_table_id";

    /**
     * 数量
     */
    public final static String ITEM_COUNT = "item_count";

    /**
     * 开放情况
     */
    public final static String OPEN_FLAG = "open_flag";

    /**
     * 附件级字段
     */
    public final static class Document {

        /**
         * 文件名称
         */
        public final static String FILE_NAME = "file_name";

        /**
         * 文件大小
         */
        public final static String FILE_SIZE = "file_size";

        /**
         * 文件路径
         */
        public final static String FILE_PATH = "file_path";

        /**
         * 文件格式
         */
        public final static String FILE_FORMAT = "file_format";

        /**
         * 全文类型
         */
        public final static String DOC_TYPE = "doc_type";

		/**
		 * 序号
		 */
		public final static String ITEM_NO = "item_no";

		/**
		 * 页数
		 */
		public final static String AMOUNT_OF_PAGES = "amount_of_pages";
    }

}
