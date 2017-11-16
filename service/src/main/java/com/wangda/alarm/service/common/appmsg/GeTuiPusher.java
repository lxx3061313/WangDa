package com.wangda.alarm.service.common.appmsg;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.wangda.alarm.service.bean.biz.MsgPushContext;
import com.wangda.alarm.service.common.appmsg.TransmissionTemplateBuilder.Builder;
import com.wangda.alarm.service.common.util.json.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-11-01
 */
@Service
public class GeTuiPusher implements MsgPusher {
    private final static Logger logger = LoggerFactory.getLogger(GeTuiPusher.class);

    @Value("${gt.app.appId}")
    private String appId;
    @Value("${gt.app.appkey}")
    private String appkey;
    @Value("${gt.app.master}")
    private String master;

    private Builder builder;

    private IGtPush iGtPush;

    @Override
    public void onSucc(MsgPushContext context) {

    }

    @Override
    public void push(MsgPushContext context) {
        List<String> cids = context.getCids();
        if (CollectionUtils.isEmpty(cids)) {
            return;
        }

        IPushResult ret = null;

        try {
            if (cids.size() == 1) {
                ret = pushSingle(cids.get(0), context.getContent(), context.getTitle());
            } else {
                ret = pushTargets(cids, context.getContent(), context.getTitle());
            }
            logger.info("app消息推送返回结果:{}", JsonUtil.of(ret));
        } catch (RequestException e) {
            logger.error("app推送消息出现异常:{}", ret, e);
            //ret = iGtPush.pushMessageToSingle(message, target, e.getRequestId());
        }
        String result = ret.getResponse().get("result").toString();
        Preconditions.checkArgument(result.equalsIgnoreCase("ok"), "发送失败");
    }

    public IPushResult pushTargets(List<String> cids, String content, String title) {
        List targets = new ArrayList();
        for (String cid : cids) {
            Target t = new Target();
            t.setAppId(appId);
            t.setClientId(cid);
            targets.add(t);
        }
        ListMessage message = new ListMessage();
        message.setOffline(true);
        message.setOfflineExpireTime(TimeUnit.DAYS.toMillis(1));
        message.setPushNetWorkType(0);
        message.setData(builder.setContent(content).setTitle(title).build());
        String contentId = iGtPush.getContentId(message);
        return iGtPush.pushMessageToList(contentId, targets);
    }

    public IPushResult pushSingle(String cid, String content, String title) {
        SingleMessage message = new SingleMessage();

        //离线存储
        message.setOffline(true);

        //离线存储时间
        message.setOfflineExpireTime(TimeUnit.DAYS.toMillis(1));

        //判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
        message.setPushNetWorkType(0);

        // 推送数据
        message.setData(builder.setContent(content).setTitle(title).build());

        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        return iGtPush.pushMessageToSingle(message, target);
    }

    @Override
    public void onFail(MsgPushContext context, Exception e) {

    }

    @PostConstruct
    public void init() {
        builder = TransmissionTemplateBuilder.createBuilder().setAppId(appId)
                .setAppKey(appkey);
        iGtPush = new IGtPush(appkey, master);
    }
}
