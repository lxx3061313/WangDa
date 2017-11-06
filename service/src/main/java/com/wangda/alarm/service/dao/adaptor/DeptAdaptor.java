package com.wangda.alarm.service.dao.adaptor;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.wangda.alarm.service.bean.biz.DeptHierarchyInfo;
import com.wangda.alarm.service.bean.biz.DeptInfo;
import com.wangda.alarm.service.bean.standard.DeptType;
import com.wangda.alarm.service.bean.standard.constant.BizConstant;
import com.wangda.alarm.service.dao.po.DeptPo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author lixiaoxiong
 * @version 2017-10-30
 */
public class DeptAdaptor {
    private static final Splitter commaSplitter = Splitter.on(",");
    private static final String SECTION_KEY_WORDS = "电务处";

    public static DeptInfo adaptToDeptInfo(DeptPo po) {
        DeptInfo info = new DeptInfo();
        info.setDeptId(po.getId());
        info.setDeptSimplename(po.getSimpleName());
        info.setDeptFullname(po.getFullName());
        info.setDeptType(parseToDeptType(po.getPids(), po.getSimpleName()));
        info.setPid(po.getPid());
        return info;
    }

    public static List<DeptInfo> adaptToDeptInfos(List<DeptPo> pos) {
        if (CollectionUtils.isEmpty(pos)) {
            return Collections.EMPTY_LIST;
        }
        return pos.stream().map(DeptAdaptor::adaptToDeptInfo).collect(Collectors.toList());
    }

    /**
     * 部门登记信息转换
     * @param po 必修为车站业务对象
     * @param parents 车站以上级别的父部门对象
     * @return 返回整合后的业务对象
     */
    public static DeptHierarchyInfo adaptToHireraInfo(DeptPo po, List<DeptPo> parents) {
        DeptHierarchyInfo info = new DeptHierarchyInfo();
        info.setSectionId(0);
        info.setSection(BizConstant.SECTION_NAME);
        info.setSectionSimpleName(BizConstant.SECTION_NAME);
        info.setSegmentId(parents.get(0).getId());
        info.setSegment(parents.get(0).getFullName());
        info.setSegmentSimpleName(parents.get(0).getSimpleName());
        info.setWorkShopId(parents.get(1).getId());
        info.setWorkShopName(parents.get(1).getFullName());
        info.setWorkShopSimpleName(parents.get(1).getSimpleName());
        info.setWorkAreaId(parents.get(2).getId());
        info.setWorkAreaName(parents.get(2).getFullName());
        info.setWorkAreaSimpleName(parents.get(2).getSimpleName());
        info.setStationId(po.getId());
        info.setStationName(po.getFullName());
        info.setStationSimpleName(po.getSimpleName());
        return info;
    }

    /**
     * 分离父id
     * @param pids eg.[0],[41],[42],[46],
     * @return 返回list id
     */
    public static List<String> splitPids(String pids) {
        Iterable<String> split = commaSplitter.split(pids);
        List<String> result = new ArrayList<>();
        for (String id : split) {
            if (Strings.isNullOrEmpty(id)) {
                continue;
            }
            result.add(id.replaceAll("\\[|\\]", ""));
        }
        return result;
    }

    public static DeptType parseToDeptType(String pids, String simpleName) {
        List<String> list = splitPids(pids);
        if (list.size() == 1 && simpleName.contains(SECTION_KEY_WORDS)) {
            return DeptType.SECTION;
        }

        if (list.size() == 1) {
            return DeptType.SEGMENT;
        }

        if (list.size() == 2) {
            return DeptType.WORK_SHOP;
        }

        if (list.size() == 3) {
            return DeptType.WORK_AREA;
        }

        return DeptType.STATION;
    }
}
