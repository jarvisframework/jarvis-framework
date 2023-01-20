package com.yx.framework.base.enums;

/**
 * <p>物流枚举类</p>
 *
 * @author 王涛
 * @since 1.0, 2021-01-07 11:04:30
 */
public enum LogisticsEnum {
    /**
     * 顺丰
     */
    SF("SF","顺丰"),
    /**
     * 标准快递
     */
    EMS("EMS","标准快递"),
    /**
     * 经济快件
     */
    EYB("EYB","经济快件"),
    /**
     * 宅急送
     */
    ZJS("ZJS", "宅急送"),
    /**
     * 圆通
     */
    YTO("YTO", "圆通"),
    /**
     * 中通
     */
    ZTO("ZTO","中通"),
    /**
     * 百世汇通
     */
    HTKY("HTKY", "百世汇通"),
    /**
     * 百世快运
     */
    BSKY("BSKY","百世快运"),
    /**
     * 优速
     */
    UC("UC","优速"),
    /**
     * 申通
     */
    STO("STO","申通"),
    /**
     * 天天快递
     */
    TTKDEX("TTKDEX","天天快递"),
    /**
     * 全峰
     */
    QFKD("QFKD","全峰"),
    /**
     * 快捷
     */
    FAST("FAST","快捷"),
    /**
     * 邮政小包
     */
    POSTB("POSTB","邮政小包"),
    /**
     * 国通
     */
    GTO("GTO","国通"),
    /**
     * 韵达
     */
    YUNDA("YUNDA","韵达"),
    /**
     * 京东配送
     */
    JD("JD","京东配送"),
    /**
     * 当当宅配
     */
    DD("DD","当当宅配"),
    /**
     * 亚马逊物流
     */
    AMAZON("AMAZON","亚马逊物流"),
    /**
     * 德邦物流
     */
    DBWL("DBWL","德邦物流"),
    /**
     * 德邦快递
     */
    DBKD("DBKD","德邦快递"),
    /**
     * 德邦快运
     */
    DBKY("DBKY","德邦快运"),
    /**
     * 日日顺
     */
    RRS("RRS","日日顺"),
    /**
     * 新易泰
     */
    LNET("LNET","新易泰"),
    /**
     * 其他
     */
    OTHER("OTHER","其他")
    ;


    private String code;

    private String name;

    LogisticsEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
