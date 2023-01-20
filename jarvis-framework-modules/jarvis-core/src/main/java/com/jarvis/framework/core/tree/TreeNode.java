package com.jarvis.framework.core.tree;

import java.util.HashMap;
import java.util.List;

public class TreeNode extends HashMap<String, Object> {
    private static final long serialVersionUID = 6716018477993660304L;
    private static final String KEY_PID = "parentId";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_CHILDREN = "children";
    private static final String KEY_CHECKED = "checked";
    private static final String KEY_DISABLED = "disabled";
    private static final String KEY_EXPANDED = "expanded";
    private static final String KEY_IS_LEAF = "isLeaf";
    private static final String KEY_DATA = "data";
    private static final String KEY_HIDDEN = "hidden";

    public TreeNode() {
    }

    public static TreeNode create() {
        return new TreeNode();
    }

    public Object id() {
        return this.get("id");
    }

    public TreeNode id(Object id) {
        this.put("id", id);
        return this;
    }

    public Object parentId() {
        return this.get("parentId");
    }

    public TreeNode parentId(Object parentId) {
        this.put("parentId", parentId);
        return this;
    }

    public String name() {
        return String.valueOf(this.get("name"));
    }

    public TreeNode name(String name) {
        this.put("name", name);
        return this;
    }

    public Object type() {
        return this.get("type");
    }

    public TreeNode type(Object type) {
        this.put("type", type);
        return this;
    }

    public List<TreeNode> children() {
        return (List) this.get("children");
    }

    public TreeNode children(List<TreeNode> children) {
        this.put("children", children);
        return this;
    }

    public boolean isChecked() {
        return this.getBoolean("checked", false);
    }

    public TreeNode checked(boolean checked) {
        this.put("checked", checked);
        return this;
    }

    public boolean isDisabled() {
        return this.getBoolean("disabled", false);
    }

    public TreeNode disabled(boolean disabled) {
        this.put("disabled", disabled);
        return this;
    }

    public boolean isExpanded() {
        return this.getBoolean("expanded", false);
    }

    private boolean getBoolean(String key, boolean defVal) {
        if (this.containsKey(key)) {
            Object object = this.get(key);
            if (null != object) {
                if (object.getClass().isAssignableFrom(Boolean.TYPE)) {
                    return (Boolean) object;
                }

                if (object instanceof Boolean) {
                    return (Boolean) object;
                }
            }

            return defVal;
        } else {
            return defVal;
        }
    }

    public TreeNode expanded(boolean expanded) {
        this.put("expanded", expanded);
        return this;
    }

    public Object data() {
        return this.get("data");
    }

    public TreeNode data(Object data) {
        this.put("data", data);
        return this;
    }

    public TreeNode isLeaf(boolean isLeaf) {
        this.put("isLeaf", isLeaf);
        return this;
    }

    public boolean isLeaf() {
        return this.getBoolean("isLeaf", false);
    }

    public TreeNode hidden(boolean hidden) {
        this.put("hidden", hidden);
        return this;
    }

    public TreeNode hidden(Integer hidden) {
        this.put("hidden", 1 == hidden);
        return this;
    }

    public TreeNode hidden(String hidden) {
        this.put("hidden", "1".equals(hidden) || "yes".equalsIgnoreCase(hidden) || "true".equalsIgnoreCase(hidden));
        return this;
    }

    public boolean hidden() {
        return this.getBoolean("hidden", false);
    }
}
