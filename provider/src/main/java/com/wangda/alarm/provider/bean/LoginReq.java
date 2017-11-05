package com.wangda.alarm.provider.bean;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
public class LoginReq {
    private String userName;
    private String password;
    private String cid;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
