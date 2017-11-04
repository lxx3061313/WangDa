package com.wangda.alarm.service.dao.adaptor;

import com.wangda.alarm.service.bean.biz.RoleInfo;
import com.wangda.alarm.service.dao.po.RolePo;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
public class RoleInfoAdaptor {
    public static RoleInfo adaptToRoleInfo(RolePo po) {
        RoleInfo info = new RoleInfo();
        info.setId(po.getId());
        info.setNum(po.getNum());
        info.setPid(po.getPid());
        info.setName(po.getName());
        info.setDeptid(po.getDeptid());
        info.setTips(po.getTips());
        info.setVersion(po.getVersion());
        return info;
    }
}
