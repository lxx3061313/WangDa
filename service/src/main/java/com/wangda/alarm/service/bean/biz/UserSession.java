package com.wangda.alarm.service.bean.biz;

import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
public class UserSession {
    private String token;
    private String userName;
    private Date loginTime;
    private RoleInfo roleInfo;
    private DeptInfo deptInfo;

    public static Builder createBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private String userName;
        private Date loginTime;
        private RoleInfo roleInfo;
        private DeptInfo deptInfo;
        public Builder addToken(String token) {
            this.token = token;
            return this;
        }

        public Builder addUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder addLoginTime(Date time) {
            this.loginTime = time;
            return this;
        }

        public Builder addRole(RoleInfo roleInfo) {
            this.roleInfo = roleInfo;
            return this;
        }

        public Builder addDept(DeptInfo deptInfo) {
            this.deptInfo = deptInfo;
            return this;
        }

        public UserSession build() {
            UserSession session = new UserSession();
            session.setLoginTime(loginTime);
            session.setRoleInfo(roleInfo);
            session.setDeptInfo(deptInfo);
            session.setToken(token);
            return session;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public RoleInfo getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(RoleInfo roleInfo) {
        this.roleInfo = roleInfo;
    }

    public DeptInfo getDeptInfo() {
        return deptInfo;
    }

    public void setDeptInfo(DeptInfo deptInfo) {
        this.deptInfo = deptInfo;
    }
}
