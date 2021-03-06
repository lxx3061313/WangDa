package com.wangda.alarm.service.dao;

import com.wangda.alarm.service.dao.po.BizOpLogPo;
import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
@Repository
public interface BizOpLogDao {
    int saveOpLog(BizOpLogPo po);
    List<BizOpLogPo> queryLogsByOperator(String operator);
    List<BizOpLogPo> queryLogsByOperators(List<String> operators, RowBounds rowBounds);
}
