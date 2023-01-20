package com.jarvis.framework.constant;

public enum DatabaseIdEnum {
    MySQL("mysql"),
    Oracle("oracle"),
    SQLServer("sqlserver"),
    PostgreSQL("postgresql"),
    DM("dm"),
    Oscar("oscar"),
    KingBase("kingbase"),
    KingBase8("kingbase8"),
    H2("h2"),
    Highgo("highgo"),
    Gbase("gbase");

    private String code;

    private DatabaseIdEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static DatabaseIdEnum toEnum(String code) {
        DatabaseIdEnum[] values = values();
        int i = 0;

        for (int len = values.length; i < len; ++i) {
            if (values[i].getCode().equals(code)) {
                return values[i];
            }
        }

        return null;
    }
}
