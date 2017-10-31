package com.wangda.alarm.service.bean.biz;

/**
 * @author lixiaoxiong
 * @version 2017-11-01
 */
public class MsgPushContext {
    private String title;
    private String content;
    private String cid;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
