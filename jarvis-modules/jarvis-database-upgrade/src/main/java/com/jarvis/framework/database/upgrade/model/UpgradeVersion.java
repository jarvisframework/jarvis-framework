package com.jarvis.framework.database.upgrade.model;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月6日
 */
public class UpgradeVersion {

    private int ddlVersion = -1;

    private int dmlVersion = -1;

    /**
     * @return the ddlVersion
     */
    public int getDdlVersion() {
        return ddlVersion;
    }

    /**
     * @param ddlVersion the ddlVersion to set
     */
    public void setDdlVersion(int ddlVersion) {
        this.ddlVersion = ddlVersion;
    }

    /**
     * @return the dmlVersion
     */
    public int getDmlVersion() {
        return dmlVersion;
    }

    /**
     * @param dmlVersion the dmlVersion to set
     */
    public void setDmlVersion(int dmlVersion) {
        this.dmlVersion = dmlVersion;
    }

}
