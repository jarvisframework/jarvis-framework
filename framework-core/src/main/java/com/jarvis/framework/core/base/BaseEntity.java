package com.jarvis.framework.core.base;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>实体对象基类</p>
 *
 * @author 王涛
 * @date 2019-11-14 19:42:52
 */
public class BaseEntity implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -7050321838862820557L;

    /**
     * 字符串类型-ID
     */
    @TableId
    private String id;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改日期
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 逻辑状态：Y-有效、N-禁用
     */
    private String validate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }
}
