package com.wangda.alarm.service.common.appmsg;

import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.google.common.collect.Maps;
import com.wangda.alarm.service.bean.biz.MsgContentAps;
import com.wangda.alarm.service.bean.biz.MsgContentExtra;
import com.wangda.alarm.service.bean.biz.MsgContentPayload;
import com.wangda.alarm.service.bean.biz.MsgContentTemplate;
import com.wangda.alarm.service.common.util.json.JsonUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangshuo
 * @version 2016-09-19
 */
public class TransmissionTemplateBuilder {

    public static Builder createBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String appId;
        private String appKey;
        private String content;
        private String title;
        private int transmissionType = 2;//收到消息是否立即启动应用，1 为立即启动，2 则广播等待客户 端自启动
        private Map<String, Object> ext = Maps.newHashMap();
        private MsgContentTemplate contentTemplate = new MsgContentTemplate();

        public Builder() {
            ext = new HashMap<>();
        }

        public Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setAppKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setType(int type) {
            this.transmissionType = type;
            return this;
        }

        public Builder setExt(Map<String, Object> ext) {
            if (ext == null) {
                return this;
            }
            this.ext = ext;
            return this;
        }

        public Builder addExtParam(String key, Object value) {
            this.ext.put(key, value);
            return this;
        }

        public TransmissionTemplate build() {
            TransmissionTemplate transmissionTemplate = new TransmissionTemplate();
            transmissionTemplate.setAppId(this.appId);
            transmissionTemplate.setAppkey(this.appKey);
            transmissionTemplate.setTransmissionType(this.transmissionType);

            APNPayload payload = new APNPayload();
            payload.setBadge(1);
            payload.setContentAvailable(1);
            payload.setSound("default");
            payload.setCategory("$由客户端定义");
            payload.setAlertMsg(new APNPayload.SimpleAlertMsg(content));
            this.ext.entrySet().forEach(entry -> {
                payload.addCustomMsg(entry.getKey(), entry.getValue());
            });
            transmissionTemplate.setTransmissionContent(JsonUtil.of(payload));
            //IOS推送需要设置
            transmissionTemplate.setAPNInfo(payload);
            return transmissionTemplate;
        }

        private MsgContentTemplate buildTemplate() {
            MsgContentPayload payload = contentTemplate.getPayload();
            MsgContentAps aps = payload.getAps();
            aps.setAlert(content);
            contentTemplate.setExtra(new MsgContentExtra());
            return contentTemplate;
        }
    }
}
