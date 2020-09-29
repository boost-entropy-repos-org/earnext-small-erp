package top.dongxibao.erp.entity.server;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * CPU相关信息
 *
 * @author Dongxibao
 * @date 2020-07-11
 */
public class Cpu {
    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public int getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum) {
        this.cpuNum = cpuNum;
    }

    public double getTotal() {
        return new BigDecimal(String.valueOf(total)).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSys() {
        return new BigDecimal(String.valueOf(sys / total)).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setSys(double sys) {
        this.sys = sys;
    }

    public double getUsed() {
        return new BigDecimal(String.valueOf(used / total)).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getWait() {
        return new BigDecimal(String.valueOf(wait / total)).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public double getFree() {
        return new BigDecimal(String.valueOf(free / total)).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setFree(double free) {
        this.free = free;
    }
}
