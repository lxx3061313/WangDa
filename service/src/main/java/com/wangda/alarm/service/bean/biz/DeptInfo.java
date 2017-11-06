package com.wangda.alarm.service.bean.biz;

import com.wangda.alarm.service.bean.standard.DeptType;

/**
 * @author lixiaoxiong
 * @version 2017-10-30
 */
public class DeptInfo {

    /**
     * 部门id
     */
    private int deptId;

    private int pid;

    /**
     * 部门是simple name
     */
    private String deptSimplename;

    /**
     * 部门全称
     */
    private String deptFullname;

    /**
     * 部门类型
     */
    private DeptType deptType;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptSimplename() {
        return deptSimplename;
    }

    public void setDeptSimplename(String deptSimplename) {
        this.deptSimplename = deptSimplename;
    }

    public String getDeptFullname() {
        return deptFullname;
    }

    public void setDeptFullname(String deptFullname) {
        this.deptFullname = deptFullname;
    }

    public DeptType getDeptType() {
        return deptType;
    }

    public void setDeptType(DeptType deptType) {
        this.deptType = deptType;
    }
}
