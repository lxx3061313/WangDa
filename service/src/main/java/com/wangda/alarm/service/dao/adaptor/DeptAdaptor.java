package com.wangda.alarm.service.dao.adaptor;

import com.google.common.base.Splitter;
import com.wangda.alarm.service.bean.biz.DeptHierarchyInfo;
import com.wangda.alarm.service.bean.biz.DeptInfo;
import com.wangda.alarm.service.bean.standard.DeptType;
import com.wangda.alarm.service.dao.po.DeptPo;
import java.util.ArrayList;
import java.util.List;

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
        info.setDeptType(parseToDeptType(po.getPids()));
        return info;
    }

    /**
     * 部门登记信息转换
     * @param po 必修为车站业务对象
     * @param parents 车站以上级别的父部门对象
     * @return 返回整合后的业务对象
     */
    public static DeptHierarchyInfo adaptToHireraInfo(DeptPo po, List<DeptPo> parents) {
        DeptHierarchyInfo info = new DeptHierarchyInfo();
        info.setSectionId(parents.get(0).getId());
        info.setSection(parents.get(0).getFullName());
        info.setSectionSimpleName(parents.get(0).getSimpleName());
        info.setSegmentId(parents.get(1).getId());
        info.setSegment(parents.get(1).getFullName());
        info.setSegmentSimpleName(parents.get(1).getSimpleName());
        info.setWorkShopId(parents.get(2).getId());
        info.setWorkShopName(parents.get(2).getFullName());
        info.setWorkShopSimpleName(parents.get(2).getSimpleName());
        info.setWorkAreaId(parents.get(3).getId());
        info.setWorkAreaName(parents.get(3).getFullName());
        info.setWorkAreaSimpleName(parents.get(3).getSimpleName());
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
            result.add(id.replaceAll("\\[|\\]", ""));
        }
        return result;
    }

    public static DeptType parseToDeptType(String pids) {
        List<String> list = splitPids(pids);
        if (list.size() == 1 && list.get(0).contains(SECTION_KEY_WORDS)) {
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
