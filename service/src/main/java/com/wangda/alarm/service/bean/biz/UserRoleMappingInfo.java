package com.wangda.alarm.service.bean.biz;

import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
public class UserRoleMappingInfo {
    private int id;
    private int userId;
    private int roleId;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
