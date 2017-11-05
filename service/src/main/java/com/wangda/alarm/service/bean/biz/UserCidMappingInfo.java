package com.wangda.alarm.service.bean.biz;

import com.google.common.base.Strings;
import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-11-05
 */
public class UserCidMappingInfo {
    private String account;
    private String cid;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static class Builder{
        private String account;
        private String cid;

        public UserCidMappingInfo build() {
            if (Strings.isNullOrEmpty(account) || Strings.isNullOrEmpty(cid)) {
                return null;
            }

            UserCidMappingInfo info = new UserCidMappingInfo();
            info.setCid(this.getCid());
            info.setAccount(this.getAccount());
            return info;
        }

        public String getAccount() {
            return account;
        }

        public Builder setAccount(String account) {
            this.account = account;
            return this;
        }

        public String getCid() {
            return cid;
        }

        public Builder setCid(String cid) {
            this.cid = cid;
            return this;
        }
    }
}
