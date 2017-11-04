package com.wangda.alarm.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.wangda.alarm.service.bean.biz.DeptInfo;
import com.wangda.alarm.service.bean.biz.RoleInfo;
import com.wangda.alarm.service.bean.biz.UserInfo;
import com.wangda.alarm.service.bean.biz.UserRoleMappingInfo;
import com.wangda.alarm.service.common.util.SplitterUtil;
import com.wangda.alarm.service.common.util.WebUtil;
import com.wangda.alarm.service.dao.UserInfoDao;
import com.wangda.alarm.service.dao.adaptor.UserInfoAdaptor;
import com.wangda.alarm.service.dao.po.UserInfoPo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
@Service
public class UserInfoService {

    @Resource
    UserInfoDao userInfoDao;

    @Resource
    DeptInfoService deptInfoService;

    @Resource
    RoleInfoService roleInfoService;

    @Resource
    UserRoleMappingService userRoleMappingService;

    @Resource
    LoginSessionService loginSessionService;


    public UserInfo queryUserInfo(String account) {
        UserInfoPo infoPo = userInfoDao.queryUserInfoByAccount(account);
        if (infoPo == null) {
            return null;
        }

        List<RoleInfo> roleInfos = roleInfoService.queryRoleByIds(SplitterUtil.splitByCommaToIntList(infoPo.getRoleId()));
        DeptInfo deptInfo = deptInfoService.queryDeptById(infoPo.getDeptId());
        return UserInfoAdaptor.adaptToUserInfo(infoPo, roleInfos, deptInfo);
    }

    public void updatePassword(String accout, String newPass, String salt) {
        String saltpass = WebUtil.md5(newPass, salt);
        userInfoDao.updatePassword(saltpass, accout);
    }

    public List<UserInfo> queryUserInfosRecru(String account) {
        if (Strings.isNullOrEmpty(account)) {
            return Collections.EMPTY_LIST;
        }

        UserInfoPo infoPo = userInfoDao.queryUserInfoByAccount(account);
        if (infoPo == null) {
            return Collections.EMPTY_LIST;
        }

        Map<String, UserInfo> result = new HashMap<>();

        //采用栈代替递归操作
        LinkedList<Integer> stackRole = new LinkedList<>();
        List<Integer> roleids = SplitterUtil.splitByCommaToIntList(infoPo.getRoleId());
        for (Integer i : roleids) {
            stackRole.push(i);
        }

        // 取出所有user-role映射,避免循环查询数据库
        List<UserRoleMappingInfo> roleMappingInfos = userRoleMappingService.queryAllMappings();
        Multimap<Integer, UserRoleMappingInfo> roleUserMaps = HashMultimap.create();
        for (UserRoleMappingInfo info : roleMappingInfos) {
            roleUserMaps.put(info.getRoleId(), info);
        }

        // 映射所有roleid为pid的关系
        List<RoleInfo> roleInfos = roleInfoService.queryAllRoles();
        Multimap<Integer, RoleInfo> proleMaping  =HashMultimap.create();
        Map<Integer, RoleInfo> roleInfoMap = new HashMap<>();
        for (RoleInfo roleInfo : roleInfos) {
            proleMaping.put(roleInfo.getPid(), roleInfo);
            roleInfoMap.put(roleInfo.getId(), roleInfo);
        }

        // 映射所有的科室信息
        List<DeptInfo> deptInfos = deptInfoService.queryAllDepts();
        Map<Integer, DeptInfo> deptInfoMap = deptInfos.stream().collect(
                Collectors.toMap(DeptInfo::getDeptId, p->p));

        while (!stackRole.isEmpty()) {
            Integer pid = stackRole.pop();
            Collection<RoleInfo> croles = proleMaping.get(pid);
            if (CollectionUtils.isEmpty(croles)) {
                continue;
            }

            List<RoleInfo> infos = Lists.newArrayList(croles);
            // 此用户对应角色的所有子角色
            List<Integer> roleIds = infos.stream().map(RoleInfo::getId)
                    .collect(Collectors.toList());

            // 以及此用户对应的角色
            roleIds.add(pid);
            List<UserRoleMappingInfo> mappingInfos = queryMappingsByRoles(roleUserMaps, roleIds);
            List<Integer> userIds = mappingInfos.stream().map(UserRoleMappingInfo::getUserId)
                    .collect(Collectors.toList());

            List<UserInfoPo> infoPos = userInfoDao.queryUsersByIds(userIds);
            for (UserInfoPo po : infoPos) {
                List<Integer> tempRoleIds = SplitterUtil.splitByCommaToIntList(po.getRoleId());
                UserInfo userInfo = UserInfoAdaptor
                        .adaptToUserInfo(po, extractRoleByIds(tempRoleIds, roleInfos),
                                deptInfoMap.get(po.getDeptId()));

                //如果在线则加入结果集
                if (loginSessionService.isUserOnline(po.getAccount())) {
                    result.put(po.getAccount(), userInfo);
                }
                for (Integer i : tempRoleIds) {
                    stackRole.push(i);
                }
            }
        }
        return Lists.newArrayList(result.values());
    }

    private List<RoleInfo> extractRoleByIds(List<Integer> ids, List<RoleInfo> roleInfos) {
        if (CollectionUtils.isEmpty(ids) || CollectionUtils.isEmpty(roleInfos)) {
            return Collections.EMPTY_LIST;
        }

        Map<Integer, RoleInfo> roleInfoMap = roleInfos.stream()
                .collect(Collectors.toMap(RoleInfo::getId, p -> p));
        List<RoleInfo> result = new ArrayList<>();
        for (Integer id : ids) {
            RoleInfo roleInfo = roleInfoMap.get(id);
            if (roleInfo == null) {
                continue;
            }
            result.add(roleInfo);
        }
        return result;
    }

    private List<UserRoleMappingInfo> queryMappingsByRoles(Multimap<Integer,
            UserRoleMappingInfo> roleUserMaps, List<Integer> roleIds) {
        List<UserRoleMappingInfo> result = new ArrayList<>();
        for (Integer roleId : roleIds) {
            result.addAll(roleUserMaps.get(roleId));
        }
        return result;
    }
}
