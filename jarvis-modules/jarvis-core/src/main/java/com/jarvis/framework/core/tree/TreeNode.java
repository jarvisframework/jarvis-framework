package com.jarvis.framework.core.tree;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年8月6日
 */
public class TreeNode extends HashMap<String, Object> {

    /**
     *
     */
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

    /**
     * 创建对象
     *
     * @return TreeNode
     */
    public static TreeNode create() {
        return new TreeNode();
    }

    /**
     * @return the id
     */
    public Object id() {
        return get(KEY_ID);
    }

    /**
     * @param id the id to set
     */
    public TreeNode id(Object id) {
        put(KEY_ID, id);
        return this;
    }

    /**
     * @return the parentId
     */
    public Object parentId() {
        return get(KEY_PID);
    }

    /**
     * @param parentId the parentId to set
     */
    public TreeNode parentId(Object parentId) {
        put(KEY_PID, parentId);
        return this;
    }

    /**
     * @return the name
     */
    public String name() {
        return String.valueOf(get(KEY_NAME));
    }

    /**
     * @param name the name to set
     */
    public TreeNode name(String name) {
        put(KEY_NAME, name);
        return this;
    }

    /**
     * @return the type
     */
    public Object type() {
        return get(KEY_TYPE);
    }

    /**
     * @param type the type to set
     */
    public TreeNode type(Object type) {
        put(KEY_TYPE, type);
        return this;
    }

    /**
     * @return the children
     */
    @SuppressWarnings("unchecked")
    public List<TreeNode> children() {
        return (List<TreeNode>) get(KEY_CHILDREN);
    }

    /**
     * @param children the children to set
     */
    public TreeNode children(List<TreeNode> children) {
        put(KEY_CHILDREN, children);
        return this;
    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return getBoolean(KEY_CHECKED, false);
    }

    /**
     * @param checked the checked to set
     */
    public TreeNode checked(boolean checked) {
        put(KEY_CHECKED, checked);
        return this;
    }

    /**
     * @return the disabled
     */
    public boolean isDisabled() {
        return getBoolean(KEY_DISABLED, false);
    }

    /**
     * @param disabled the disabled to set
     */
    public TreeNode disabled(boolean disabled) {
        put(KEY_DISABLED, disabled);
        return this;
    }

    /**
     * @return the expanded
     */
    public boolean isExpanded() {
        return getBoolean(KEY_EXPANDED, false);
    }

    private boolean getBoolean(String key, boolean defVal) {
        if (containsKey(key)) {
            final Object object = get(key);
            if (null != object) {
                if (object.getClass().isAssignableFrom(boolean.class)) {
                    return (boolean) object;
                }
                if (object instanceof Boolean) {
                    return ((Boolean) object).booleanValue();
                }
            }
            return defVal;
        } else {
            return defVal;
        }
    }

    /**
     * @param expanded the expanded to set
     */
    public TreeNode expanded(boolean expanded) {
        put(KEY_EXPANDED, expanded);
        return this;
    }

    /**
     * @return the data
     */
    public Object data() {
        return get(KEY_DATA);
    }

    /**
     * @param data the data to set
     */
    public TreeNode data(Object data) {
        put(KEY_DATA, data);
        return this;
    }

    /**
     * @param isLeaf the isLeaf to set
     */
    public TreeNode isLeaf(boolean isLeaf) {
        put(KEY_IS_LEAF, isLeaf);
        return this;
    }

    /**
     * @return the isLeaf
     */
    public boolean isLeaf() {
        return getBoolean(KEY_IS_LEAF, false);
    }

    public TreeNode hidden(boolean hidden) {
        put(KEY_HIDDEN, hidden);
        return this;
    }

    public TreeNode hidden(Integer hidden) {
        put(KEY_HIDDEN, 1 == hidden);
        return this;
    }

    public TreeNode hidden(String hidden) {
        put(KEY_HIDDEN, "1".equals(hidden) || "yes".equalsIgnoreCase(hidden) || "true".equalsIgnoreCase(hidden));
        return this;
    }

    /**
     * @return the hidden
     */
    public boolean hidden() {
        return getBoolean(KEY_HIDDEN, false);
    }

}
