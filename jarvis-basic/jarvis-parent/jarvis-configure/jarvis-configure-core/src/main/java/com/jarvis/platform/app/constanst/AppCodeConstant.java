package com.jarvis.platform.app.constanst;

/**
 * 编码code常量，跟数据库 app_code_category的code和app_code_option表的category_code对应
 * 这些值不允许前台修改
 *
 * @author zuqb
 * @version 1.0.0 2022年09月14日
 */
public class AppCodeConstant {

    /** 密级 */
    public static final String CODE_SECURITY_CLASSIFICATION = "security_classification";
    /** 标准类型 */
    public static final String CODE_STANDARD_TYPE = "standard_type";
    /** 保管期限 */
    public static final String CODE_RETENTION_PERIOD = "retention_period";
    /** 到期提醒的提醒类型 */
    public static final String CODE_REMIND_TYPE = "remind_type";
    /** 全文类型 */
    public static final String CODE_FILE_TYPE = "file_type";

    /** 所属门类类型
     ws	文书档案
     zp	照片档案
     yp	音频档案
     sp	视频档案
     kj	会计档案
     ms	民生档案
     dsqk	地情史科
     */
    public static final String CODE_OWNER_TYPE = "owner_type";

}
