package com.wangda.alarm.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.wangda.alarm.service.bean.biz.DeptInfo;
import com.wangda.alarm.service.bean.biz.RoleInfo;
import com.wangda.alarm.service.bean.biz.UserInfo;
import com.wangda.alarm.service.bean.biz.UserRoleMappingInfo;
import com.wangda.alarm.service.common.util.WebUtil;
import com.wangda.alarm.service.dao.UserInfoDao;
import com.wangda.alarm.service.dao.adaptor.UserInfoAdaptor;
import com.wangda.alarm.service.dao.po.UserInfoPo;
import java.util.ArrayList;
import java.util.Arrays;
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

        List<RoleInfo> roleInfos = queryRoleInfosByUserIds(Arrays.asList(infoPo.getId()));
        DeptInfo deptInfo = deptInfoService.queryDeptById(infoPo.getDeptId());
        return UserInfoAdaptor.adaptToUserInfo(infoPo, roleInfos, deptInfo);
    }

    public void updatePassword(String accout, String newPass, String salt) {
        String saltpass = WebUtil.md5(newPass, salt);
        userInfoDao.updatePassword(saltpass, accout);
    }

    public List<String> queryAccountsByDeptIds(List<Integer> deptIds) {
        List<UserInfoPo> infoPos = userInfoDao.queryUsersByDeptIds(deptIds);
        return infoPos.stream().map(UserInfoPo::getAccount).collect(Collectors.toList());
    }

    public List<RoleInfo> queryRoleInfosByUserIds(List<Integer> userIds) {
        List<UserRoleMappingInfo> roleMappingInfos = userRoleMappingService.queryAllMappings();
        Multimap<Integer, Integer> userRoleMaps = HashMultimap.create();
        roleMappingInfos.stream().forEach(p->userRoleMaps.put(p.getUserId(), p.getRoleId()));

        ArrayList<Integer> roleIds = new ArrayList<>();
        for (Integer userId : userIds) {
            Collection<Integer> integers = userRoleMaps.get(userId);
            roleIds.addAll(integers);
        }
        return roleInfoService.queryRoleByIds(roleIds);
    }


    /**
     * 查询acount同级别一级下属级别的所有用户
     * @param account 要查询的用户
     * @return 返回所有下属级别的用户
     */
    public List<UserInfo> queryUserInfosRecru(String account) {
        if (Strings.isNullOrEmpty(account)) {
            return Collections.EMPTY_LIST;
        }

        UserInfoPo infoPo = userInfoDao.queryUserInfoByAccount(account);
        if (infoPo == null) {
            return Collections.EMPTY_LIST;
        }

        // 取出所有user-role映射,避免循环查询数据库
        List<UserRoleMappingInfo> roleMappingInfos = userRoleMappingService.queryAllMappings();
        Multimap<Integer, Integer> roleUserMaps = HashMultimap.create();
        Multimap<Integer, Integer> userRoleMaps = HashMultimap.create();
        for (UserRoleMappingInfo info : roleMappingInfos) {
            roleUserMaps.put(info.getRoleId(), info.getUserId());
            userRoleMaps.put(info.getUserId(), info.getRoleId());
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

        //采用栈代替递归操作
        LinkedList<Integer> stackRole = new LinkedList<>();
        List<Integer> processedRole = new ArrayList<>();
        List<Integer> roleids = Lists.newArrayList(userRoleMaps.get(infoPo.getId()));
        roleids.stream().forEach(stackRole::push);

        Map<String, UserInfo> result = new HashMap<>();
        while (!stackRole.isEmpty()) {
            Integer pid = stackRole.pop();
            if (processedRole.contains(pid)) {
                continue;
            }

            processedRole.add(pid);
            Collection<RoleInfo> croles = proleMaping.get(pid);
            if (CollectionUtils.isEmpty(croles)) {
                continue;
            }
            // 此用户对应角色的所有子角色, 子角色进栈
            List<Integer> roleIds = croles.stream().map(RoleInfo::getId)
                    .collect(Collectors.toList());
            roleIds.stream().forEach(stackRole::push);

            // 以及此用户对应的角色
            roleIds.add(pid);
            List<Integer> userIds = queryUserIdsByRoles(roleUserMaps, roleIds);
            if (CollectionUtils.isEmpty(userIds)) {
                continue;
            }

            List<UserInfoPo> infoPos = userInfoDao.queryUsersByIds(userIds);
            for (UserInfoPo po : infoPos) {
                List<Integer> tempRoleIds = Lists.newArrayList(userRoleMaps.get(po.getId()));
                tempRoleIds.stream().forEach(stackRole::push);
                UserInfo userInfo = UserInfoAdaptor
                        .adaptToUserInfo(po, extractRoleByIds(tempRoleIds, roleInfos),
                                deptInfoMap.get(po.getDeptId()));

                //如果没有处理过
                if (result.get(userInfo.getAccount()) == null) {
                    // 在线则加入结果集
                    result.put(po.getAccount(), userInfo);
                }
            }
        }
        return Lists.newArrayList(result.values());
    }

    /**
     * 查询所有在线用户的
     * @param account 要查询的用户
     * @return 返回所有下属级别在线用户
     */
    public List<UserInfo> queryOnlineUsersInfosRecru(String account) {
        List<UserInfo> userInfos = queryUserInfosRecru(account);
        return userInfos.stream().filter(
                userInfo -> loginSessionService.isUserOnline(userInfo.getAccount()))
                .collect(Collectors.toList());
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

    private List<Integer> queryUserIdsByRoles(Multimap<Integer,
            Integer> roleUserMaps, List<Integer> roleIds) {
        List<Integer> result = new ArrayList<>();
        for (Integer roleId : roleIds) {
            result.addAll(roleUserMaps.get(roleId));
        }
        return result;
    }
}
