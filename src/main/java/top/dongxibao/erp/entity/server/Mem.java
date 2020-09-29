package top.dongxibao.erp.entity.server;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 內存相关信息
 *
 * @author Dongxibao
 * @date 2020-07-11
 */
public class Mem {
    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public double getTotal() {
        return BigDecimal.valueOf(total).divide(BigDecimal.valueOf(1024 * 1024 * 1024), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public double getUsed() {
        return BigDecimal.valueOf(used).divide(BigDecimal.valueOf(1024 * 1024 * 1024), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public double getFree() {
        return BigDecimal.valueOf(free).divide(BigDecimal.valueOf(1024 * 1024 * 1024), 2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setFree(long free) {
        this.free = free;
    }

    public double getUsage() {
        double value =
                BigDecimal.valueOf(used).divide(BigDecimal.valueOf(total), 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)).doubleValue();
        return value;
    }
}
