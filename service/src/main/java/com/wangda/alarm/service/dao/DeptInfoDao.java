package com.wangda.alarm.service.dao;

import com.wangda.alarm.service.dao.po.DeptPo;
import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-10-30
 */
public interface DeptInfoDao {

    /**
     * 由于部门的数据结构问题, 此接口不能走索引, 只能通过like去查询
     * @param sname simplename
     * @return 一个部分信息
     */
    List<DeptPo> queryDeptBySName(String sname);

    List<DeptPo> queryDeptsByIds(List<Integer> ids);

    DeptPo queryDeptById(int id);
}
