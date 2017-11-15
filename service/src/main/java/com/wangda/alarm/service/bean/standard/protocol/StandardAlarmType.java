package com.wangda.alarm.service.bean.standard.protocol;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
public enum  StandardAlarmType {
    SJSBFL(0x01, "SJ锁闭封连报警"),
    DCBSBYZ(0x02, "道岔表示不一致"),
    DCJC(0x03, "道岔挤岔"),
    LCXHGB(0x04, "列车信号非正常关闭"),
    HZBJ(0x05, "火灾报警"),
    GZTZ(0x06, "故障通知"),
    WDWSLDD(0x07, "外电网双路断电报警"),
    WDWSXCX(0x08, "外电网三相错序"),
    WDWSSDYDD(0x09, "外电网输入电源瞬间断电"),
    LCXHZDSDS(0x0A, "列车信号主灯丝断丝"),
    RSDS(0x0B, "熔丝断丝"),
    DCQKBJ(0x0C, "道岔缺口报警"),
    QJSBBJ(0x0D, "区间设备报警"),
    WJJCNBSBBJ(0x0E, "微机监测内部通信故障"),
    HJJCEJBJ(0x0F, "环境监测二级报警"),
    TDCSCTCBJ(0x10, "TDCS/CTC系统报警"),
    LKXTBJ(0x11, "列控系统报警"),
    JSJLSBJ(0x12, "计算机联锁系统报警"),
    DQTXCX(0x13, "电气特性超限"),
    XTTXJKGZ(0x14, "监测与其他系统通信接口故障"),
    DCYYCSCX(0x16, "道岔运用次数超限"),
    BCDRDX(0x17, "补偿电容断线"),
    ZPWXTBJ(0x18,"ZPW2000系统报警"),
    YDQBJ(0x1A, "应答器报警"),
    ZNDYPBJ(0x1B, "智能电源屏报警"),
    FZYWQX(0x22,"防灾异物侵限"),
    TSRSSBBJ(0x26,"TSRS设备报警"),
    RBCBJ(0x27,"RBC报警"),
    DYPSCDDBJ(0x28,"电源屏输出断电报警"),
    DCWBS(0x29, "道岔无表示"),
    MNLBHQSBD(0x30,"模拟量变化趋势、突变、异常波动"),
    KZTANCZJL(0x53,"控制台按钮操作记录"),
    GDCQZY(0x58,"轨道长期占用"),
    ZNFXYJ(0x62,"智能分析预警(预留)"),
    ZNFXGZZD(0x64, "智能分析故障诊断(预留)"),
    DQTXDDBJ(0xC0, "电气特性断电三级报警"),
    TZZTXGCDY(0xC1, "调整状态小轨出电压≤63mV"),
    PFANBJ(0xC2, "破封按钮报警"),
    GDDLHGD(0xC3, "轨道电路红光带"),
    JXPBKQCS(0xC4, "检修屏蔽开启超时"),
    FLBLQDSZCS(0xC5, "分路不良区段设置超时报警"),
    FLBLQDSZCSZF(0xC6, "分路不良区段设置超时预警"),
    ZZJDZGLYC(0xC7, "转辙机动作功率（电流）异常"),
    DCDZSJBJYJ(0xC8, "道岔动作时间变化预警"),
    LSJCKGLZTBYZ(0xC9, "联锁/监测开关量状态不一致"),
    WXDCJCXXJKBJ(0xCA, "无线调车机车信号监控系统报警"),
    LKWGSBBJ(0xCB, "列控网管设备报警"),
    JZSBBJ(0xCC, "计轴设备报警"),
    BSSBBJ(0xCD, "闭塞设备报警"),
    G2PYKZZBJ(0xCE, "G2盘远控装置报警"),
    DCXSBHBJ(0xCF, "道岔限时保护报警"),
    XGJDQXLBJ(0xD0, "小轨继电器相邻报警"),
    JZYYPKGLBYZ(0xD1, "计轴与移频开关量不一致"),
    GMSBBJ(0xD2, "GM_2009G设备报警"),
    ZJAQXXCSBJ(0xD3, "64D站间安全信息传输报警"),
    GLJCBJ(0xD4, "光缆监测报警"),
    CXJDOFZJAQXXBJ(0xD5, "CXJ/DOF站间安全信息传输报警"),
    FDTBSSBBJ(0xD6, "FDT闭塞设备报警"),
    ZPWQDBTBBJ(0xD7, "ZPW2000区段GZ与GG和GB不同步报警（2级"),
    QDZSBBJ(0xD8, "全电子设备报警"),
    EAKJZSBBJ(0xD9, "EAK计轴设备报警"),
    DCSJDBS(0xDA, "道岔瞬间断表示"),
    ZZJWGLCJQX(0xDB, "转辙机无功率(电流)采集曲线"),
    SGXTDSBJ(0xDC, "思高兴通灯丝报警"),
    QJXHJMDBJ(0xDD, "区间信号机灭灯报警"),
    WDWSRDLDX(0xDE, "外电网输入单路断相/断电"),
    HJJCSCBJ(0xDF, "环境监测三级报警"),
    ZJWLTXDHZD(0xE0, "站机网络通信单环中断"),
    DQTXDDEJBJ(0xE1, "电气特性断电二级报警"),
    HJSPQKBJ(0xE2, "慧景视频缺口报警"),
    ZZJDZQXCXBJ(0xE3, "转辙机动作曲线超限报警"),
    WDWDYGD(0xE4, "外电网27.5KV电源供电"),
    DCDZQXTBBJ(0xE5, "道岔动作（功率/电流）曲线突变报警"),
    GDCSPBJ(0xE6, "轨道测试盘报警"),
    WDWCXTJ(0xF5, "外电网超限统计"),
    GDCQKXTJ(0xF6, "轨道长期空闲统计"),
    DCZZJWBDTJ(0xF7, "道岔转辙机72小时未搬动统计"),
    WBSQJZHJKXTBJ(0xF8, "WBS区间综合监控系统报警"),
    ZJFWQTXZKBJ(0xF9, "站机与服务器通信中断报警"),
    GDDYJDQZTBYZ(0xFA, "轨道电压与继电器状态不一致"),
    ALLTYPE(0xFF, "所有类型"),
    UNKNOW(0x00, "未知类型");
    private int code;
    private String desc;

    StandardAlarmType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static StandardAlarmType nameOf(String name) {
        for (StandardAlarmType type : values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public static StandardAlarmType codeOf(int code) {
        for (StandardAlarmType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}
