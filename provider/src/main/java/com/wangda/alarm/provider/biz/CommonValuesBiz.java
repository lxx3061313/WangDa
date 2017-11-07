package com.wangda.alarm.provider.biz;

import com.google.common.collect.Lists;
import com.wangda.alarm.provider.bean.ListItemVo;
import com.wangda.alarm.provider.bean.SectionVo;
import com.wangda.alarm.provider.bean.SegmentVo;
import com.wangda.alarm.provider.bean.SelectItemValuesVo;
import com.wangda.alarm.provider.bean.WorkareaVo;
import com.wangda.alarm.provider.bean.WorkshopVo;
import com.wangda.alarm.service.bean.biz.DeptInfo;
import com.wangda.alarm.service.bean.standard.DeptType;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.protocol.StandardAlarmType;
import com.wangda.alarm.service.impl.DeptInfoService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-11-06
 */
@Service
public class CommonValuesBiz {
    private final static List<String> EXCEPT_DEPT;
    static {
        EXCEPT_DEPT = Arrays.asList("西南电务处", "调度室", "网管室", "应急大厅",
                "CCk", "CCd", "CDc", "CCi", "CCj", "CCn", "CCo");
    }

    @Resource
    DeptInfoService deptInfoService;

    public SelectItemValuesVo queryCommonValues() {
        //报警级别
        SelectItemValuesVo vo = new SelectItemValuesVo();
        List<ListItemVo> levels = new ArrayList<>();
        for (AlarmLevel level : AlarmLevel.values()) {
            ListItemVo item = new ListItemVo();
            item.setKey(level.name());
            item.setValue(level.getDesc());
            levels.add(item);
        }
        vo.setAlarmLevels(levels);

        //报警类型
        List<ListItemVo> types = new ArrayList<>();
        for (StandardAlarmType type : StandardAlarmType.values()) {
            ListItemVo itemVo = new ListItemVo();
            itemVo.setKey(type.name());
            itemVo.setValue(type.getDesc());
            types.add(itemVo);
        }
        vo.setAlarmTypes(types);
        vo.setSections(queryDeptHireInfo());
        return vo;
    }

    private List<SectionVo> queryDeptHireInfo() {
        SectionVo sectionVo = new SectionVo();
        sectionVo.setKey("西南电务处");
        sectionVo.setValue("西南电务处");
        Map<Integer, SegmentVo> segmentVoMap = new HashMap<>();
        Map<Integer, WorkshopVo> workshopVoMap = new HashMap<>();
        Map<Integer, WorkareaVo> workareaVoMap = new HashMap<>();
        List<DeptInfo> deptInfos = deptInfoService.queryAllDepts();
        Map<Integer, DeptInfo> deptInfoMap = deptInfos.stream()
                .collect(Collectors.toMap(DeptInfo::getDeptId, p -> p));
        for (DeptInfo info : deptInfos) {
            //电务处的科室排除
            if (EXCEPT_DEPT.contains(info.getDeptSimplename())) {
                continue;
            }

            if (info.getDeptType() == DeptType.STATION) {
                WorkareaVo workareaVo = workareaVoMap.get(info.getPid());
                if (workareaVo == null) {
                    DeptInfo area = deptInfoMap.get(info.getPid());
                    workareaVo = new WorkareaVo();
                    workareaVo.setKey(area.getDeptSimplename());
                    workareaVo.setValue(area.getDeptFullname());
                    workareaVoMap.put(info.getPid(), workareaVo);
                }

                List<ListItemVo> stations = workareaVo.getStations();
                ListItemVo itemVo = new ListItemVo(info.getDeptFullname(),
                        info.getDeptSimplename());
                if (!stations.contains(itemVo)) {
                    stations.add(itemVo);
                }
            } else if (info.getDeptType() == DeptType.WORK_AREA) {
                WorkshopVo workshopVo = workshopVoMap.get(info.getPid());
                if (workshopVo == null) {
                    workshopVo = new WorkshopVo();
                    DeptInfo s = deptInfoMap.get(info.getPid());
                    workshopVo.setValue(s.getDeptFullname());
                    workshopVo.setKey(s.getDeptSimplename());
                    workshopVoMap.put(info.getPid(), workshopVo);
                }

                WorkareaVo areaVo = workareaVoMap.get(info.getDeptId());
                if (areaVo == null) {
                    areaVo = new WorkareaVo();
                    areaVo.setKey(info.getDeptSimplename());
                    areaVo.setValue(info.getDeptFullname());
                    workareaVoMap.put(info.getDeptId(), areaVo);
                }
                List<WorkareaVo> areas = workshopVo.getAreas();
                if (!areas.contains(areaVo)) {
                    areas.add(areaVo);
                }
            } else if (info.getDeptType() == DeptType.WORK_SHOP) {
                SegmentVo segmentVo = segmentVoMap.get(info.getPid());
                if (segmentVo == null) {
                    segmentVo = new SegmentVo();
                    DeptInfo segmentDept = deptInfoMap.get(info.getPid());
                    segmentVo.setKey(segmentDept.getDeptSimplename());
                    segmentVo.setValue(segmentDept.getDeptFullname());
                    segmentVoMap.put(info.getPid(), segmentVo);
                }

                WorkshopVo ws = workshopVoMap.get(info.getDeptId());
                if (ws == null) {
                    ws = new WorkshopVo();
                    ws.setKey(info.getDeptSimplename());
                    ws.setValue(info.getDeptFullname());
                    workshopVoMap.put(info.getDeptId(), ws);
                }
                List<WorkshopVo> workshops = segmentVo.getWorkshops();
                if (!workshops.contains(ws)) {
                    workshops.add(ws);
                }
            } else if (info.getDeptType() == DeptType.SEGMENT) {
                List<SegmentVo> segments = sectionVo.getSegments();
                SegmentVo segmentVo = segmentVoMap.get(info.getDeptId());
                if (segmentVo == null) {
                    segmentVo = new SegmentVo();
                    segmentVo.setKey(info.getDeptSimplename());
                    segmentVo.setValue(info.getDeptFullname());
                    segmentVoMap.put(info.getDeptId(), segmentVo);
                }
                if (!segments.contains(segmentVo)) {
                    segments.add(segmentVo);
                }
            }
        }
        return Lists.newArrayList(sectionVo);
    }
}
