package com.jarvis.framework.database.upgrade.model;

public class UpgradeVersion {

    private int ddlVersion = -1;
    
    private int dmlVersion = -1;

    public int getDdlVersion() {
        return this.ddlVersion;
    }

    public void setDdlVersion(int ddlVersion) {
        this.ddlVersion = ddlVersion;
    }

    public int getDmlVersion() {
        return this.dmlVersion;
    }

    public void setDmlVersion(int dmlVersion) {
        this.dmlVersion = dmlVersion;
    }
}
