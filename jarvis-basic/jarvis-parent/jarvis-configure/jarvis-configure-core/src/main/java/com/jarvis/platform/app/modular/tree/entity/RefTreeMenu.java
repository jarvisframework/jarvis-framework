package com.jarvis.platform.app.modular.tree.entity;

import com.jarvis.framework.core.entity.AbstractLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 * @author ronghui
 * @version 1.0.0 2022年9月16日
 */
@Table(name = "app_ref_tree_menu")
@ApiModel("档案树与模块关联表")
@Getter
@Setter
@ToString
public class RefTreeMenu extends AbstractLongIdEntity {

    @ApiModelProperty("树根节点ID")
    private Long treeId;

    @ApiModelProperty("菜单ID")
    private Long menuId;
}
