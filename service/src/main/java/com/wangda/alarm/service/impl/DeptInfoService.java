package com.wangda.alarm.service.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.wangda.alarm.service.bean.biz.DeptHierarchyInfo;
import com.wangda.alarm.service.bean.biz.DeptInfo;
import com.wangda.alarm.service.bean.standard.DeptType;
import com.wangda.alarm.service.dao.DeptInfoDao;
import com.wangda.alarm.service.dao.adaptor.DeptAdaptor;
import com.wangda.alarm.service.dao.po.DeptPo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-30
 */
@Service
public class DeptInfoService {
    private final static Splitter commaSpliter = Splitter.on(",");
    @Resource
    DeptInfoDao deptInfoDao;

    public DeptInfo queryDeptInfoByCode(String code) {
        DeptPo deptPo = queryDeptPoBySName(code);
        if (deptPo == null) {
            return null;
        }
        return DeptAdaptor.adaptToDeptInfo(deptPo);
    }

    public List<DeptInfo> queryDeptInfosByCodes(List<String> simpleNames) {
        if (CollectionUtils.isEmpty(simpleNames)) {
            return Collections.EMPTY_LIST;
        }
        List<DeptPo> deptPos = deptInfoDao.queryDeptsBySimpleNames(simpleNames);
        if (CollectionUtils.isEmpty(deptPos)) {
            return Collections.EMPTY_LIST;
        }
        return DeptAdaptor.adaptToDeptInfos(deptPos);
    }

    public List<DeptInfo> queryDeptInfosByIds(List<Integer> deptIds) {
        List<DeptPo> deptPos = deptInfoDao.queryDeptsByIds(deptIds);
        return DeptAdaptor.adaptToDeptInfos(deptPos);
    }

    public DeptInfo queryDeptById(int id) {
        DeptPo deptPo = deptInfoDao.queryDeptById(id);
        if (deptPo == null) {
            return null;
        }

        return DeptAdaptor.adaptToDeptInfo(deptPo);
    }

    public List<DeptInfo> queryAllDepts() {
        List<DeptPo> deptPos = deptInfoDao.queryAllDepts();
        return DeptAdaptor.adaptToDeptInfos(deptPos);
    }

    public DeptHierarchyInfo queryDeptHireraInfo(String code) {
        DeptPo deptPo = queryDeptPoBySName(code);
        if (deptPo == null) {
            return null;
        }

        DeptType deptType = DeptAdaptor.parseToDeptType(deptPo.getPids(), deptPo.getSimpleName());
        if (deptType != DeptType.STATION) {
            throw new IllegalArgumentException("该操作仅支持查询车站级别");
        }

        List<String> list = DeptAdaptor.splitPids(deptPo.getPids());
        List<Integer> ids = list.stream().map(Integer::valueOf).collect(Collectors.toList());
        List<DeptPo> parents = deptInfoDao.queryDeptsByIds(ids);
        return DeptAdaptor.adaptToHireraInfo(deptPo, parents);
    }

    public List<DeptHierarchyInfo> queryDeptHireraInfos(List<String> stationCodes) {
        List<DeptHierarchyInfo> result = new ArrayList<>();
        for (String scode : stationCodes) {
            DeptHierarchyInfo deptHierarchyInfo = queryDeptHireraInfo(scode);
            result.add(deptHierarchyInfo);
        }
        return result;
    }

    private DeptPo queryDeptPoBySName(String sname) {
        if (Strings.isNullOrEmpty(sname)) {
            return null;
        }

        List<DeptPo> deptPos = deptInfoDao.queryDeptBySName(sname);
        for (DeptPo po : deptPos) {
            String simpleName = po.getSimpleName();
            Iterable<String> split = commaSpliter.split(simpleName);
            for (String name : split) {
                if (name.equals(sname)) {
                    return po;
                }
            }
        }
        return null;
    }
}