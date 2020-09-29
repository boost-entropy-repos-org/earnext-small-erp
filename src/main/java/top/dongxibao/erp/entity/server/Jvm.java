package top.dongxibao.erp.entity.server;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JVM相关信息
 *
 * @author Dongxibao
 * @date 2020-07-11
 */
public class Jvm {
    /**
     * 当前JVM占用的内存总数(M)
     */
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    private double max;

    /**
     * JVM空闲内存(M)
     */
    private double free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    public double getTotal() {
        return new BigDecimal(String.valueOf(total)).divide(BigDecimal.valueOf(1024 * 1024), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getMax() {
        return new BigDecimal(String.valueOf(max)).divide(BigDecimal.valueOf(1024 * 1024), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getFree() {
        return new BigDecimal(String.valueOf(free)).divide(BigDecimal.valueOf(1024 * 1024), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setFree(double free) {
        this.free = free;
    }

    public double getUsed() {
        return new BigDecimal(String.valueOf(total - free)).divide(BigDecimal.valueOf(1024 * 1024), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public double getUsage() {
        BigDecimal decimal =
                new BigDecimal(String.valueOf(total - free)).divide(new BigDecimal(String.valueOf(total)), 4,
                RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        return decimal.doubleValue();
    }

    /**
     * 获取JDK名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    /**
     * JDK启动时间
     */
    public String getStartTime() {
        String startTime =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(ManagementFactory.getRuntimeMXBean().getStartTime()));
        return startTime;
    }

    /**
     * JDK运行时间
     */
    public String getRunTime() {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = ManagementFactory.getRuntimeMXBean().getStartTime() - System.currentTimeMillis();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }
}
