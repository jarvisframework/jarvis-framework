package com.jarvis.framework.constant;

/**
 * 数据库标识枚举
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月18日
 */
public enum DatabaseIdEnum {

    /** Mysql数据库 */
    MySQL("mysql"),

    /** Oracle数据库 */
    Oracle("oracle"),

    /** SQLServer数据库 */
    SQLServer("sqlserver"),

    /** PostgreSQL数据库 */
    PostgreSQL("postgresql"),

    /** 达梦Mysql数据库 */
    DM("dm"),

    /** oscar数据库 */
    Oscar("oscar"),

    /** kingbase数据库 */
    KingBase("kingbase"),

    /** kingbase8数据库 */
    KingBase8("kingbase8"),

    /** h2数据库 */
    H2("h2"),

    /** highgo数据库 */
    Highgo("highgo"),

    /** 南大通用数据库 */
    Gbase("gbase");

    private String code;

    private DatabaseIdEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static DatabaseIdEnum toEnum(String code) {
        final DatabaseIdEnum[] values = DatabaseIdEnum.values();
        for (int i = 0, len = values.length; i < len; i++) {
            if (values[i].getCode().equals(code)) {
                return values[i];
            }
        }

        return null;
    }

}
