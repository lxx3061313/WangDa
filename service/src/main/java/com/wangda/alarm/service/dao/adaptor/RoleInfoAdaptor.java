package com.wangda.alarm.service.dao.adaptor;

import com.wangda.alarm.service.bean.biz.RoleInfo;
import com.wangda.alarm.service.dao.po.RolePo;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

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

    public static List<RoleInfo> adaptToRoleInfos(List<RolePo> rolePos) {
        if (CollectionUtils.isEmpty(rolePos)) {
            return Collections.EMPTY_LIST;
        }
        return rolePos.stream().map(RoleInfoAdaptor::adaptToRoleInfo)
                .collect(Collectors.toList());
    }
}
