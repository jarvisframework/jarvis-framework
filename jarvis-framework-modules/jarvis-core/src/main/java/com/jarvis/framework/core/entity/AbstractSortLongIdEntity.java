package com.jarvis.framework.core.entity;

public class AbstractSortLongIdEntity extends AbstractLongIdEntity implements SortSimpleEntity {
    private static final long serialVersionUID = 4569509256950124344L;
    protected Integer sortOrder;

    public AbstractSortLongIdEntity() {
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
