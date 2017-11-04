package com.wangda.alarm.service.bean.vo.req;

/**
 * @author lixiaoxiong
 * @version 2017-10-27
 */
public class UpdatePassReq {
    private String oriPass;
    private String newPass;

    public String getOriPass() {
        return oriPass;
    }

    public void setOriPass(String oriPass) {
        this.oriPass = oriPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
