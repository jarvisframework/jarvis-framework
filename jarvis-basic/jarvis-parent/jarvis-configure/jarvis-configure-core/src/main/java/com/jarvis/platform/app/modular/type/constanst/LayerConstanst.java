package com.jarvis.platform.app.modular.type.constanst;

/**
 * @author qiucs
 * @version 1.0.0 2022年5月12日
 */
public class LayerConstanst {

    /**
     * 顶层
     */
    public static final String LAYER_TOP = "0";

    /**
     * 系统字段
     */
    public static final String SYSTEM_COLUMN = "sys";
    /**
     * 项目层
     */
    public static final String LAYER_CODE_P = "p";
    /**
     * 案卷层
     */
    public static final String LAYER_CODE_V = "v";
    /**
     * 卷内文件层
     */
    public static final String LAYER_CODE_F = "f";
    /**
     * 归档文件层
     */
    public static final String LAYER_CODE_O = "o";
    /**
     * 全文层
     */
    public static final String LAYER_CODE_D = "d";
    /**
     * 过程信息层
     */
    public static final String LAYER_CODE_I = "i";
    /**
     * 归档配置信息层
     */
    public static final String LAYER_CODE_C = "c";
    /**
     * 签名签章层
     */
    public static final String LAYER_CODE_B = "b";

    /**
     * 是否为全文层级
     * @param layerCode
     * @return
     */
    public static final boolean isDocumentLayer(String layerCode){
        return LAYER_CODE_D.equalsIgnoreCase(layerCode);
    }
}
