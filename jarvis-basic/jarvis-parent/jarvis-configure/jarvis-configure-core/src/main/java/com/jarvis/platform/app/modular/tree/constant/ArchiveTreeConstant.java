package com.jarvis.platform.app.modular.tree.constant;

/**
 *
 * @author ronghui
 * @version 1.0.0 2022年9月19日
 */
public class ArchiveTreeConstant {

    /** 根节点 */
    public static final int TYPE_ROOT = 0;

    /** 档案馆节点 */
    public static final int TYPE_ARCHIVES = 1;

    /** 全宗节点 */
    public static final int TYPE_FONDS = 2;

    /** 空节点 */
    public static final int TYPE_EMPTY = 3;

    /** 门类节点 */
    public static final int TYPE_ARCHIVE_TYPE = 4;

    /** 静态字段节点 */
    public static final int TYPE_COLUMN_STATIC = 6;

    /** 动态字段节点 */
    public static final int TYPE_COLUMN_DYNAMIC = 7;

    /** 归档范围节点 */
    public static final int TYPE_FILING = 8;

    /** 节点值设置 固定值 */
    public static final int VALUE_TYPE_FIXATION = 1;

    /** 节点值设置 动态值 */
    public static final int VALUE_TYPE_DYNAMIC = 2;

    /** 节点显示设置 编码值 */
    public static final int NAME_TYPE_V = 2;

    /** 节节点显示设置 编码值(名称) */
    public static final int NAME_TYPE_V_N = 3;

    /** 是否有子节点：有 */
    public static final Integer HASCHILD = 1;

    /** 是否有子节点：无 */
    public static final Integer NOCHILD = 0;
}
