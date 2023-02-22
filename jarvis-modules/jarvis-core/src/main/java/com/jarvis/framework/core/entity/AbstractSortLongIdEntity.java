package com.jarvis.framework.core.entity;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年11月2日
 */
public class AbstractSortLongIdEntity extends AbstractLongIdEntity implements SortSimpleEntity {

    /**
     *
     */
    private static final long serialVersionUID = 4569509256950124344L;

    protected Integer sortOrder;

    /**
     * @return the sortOrder
     */
    @Override
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    @Override
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

}
