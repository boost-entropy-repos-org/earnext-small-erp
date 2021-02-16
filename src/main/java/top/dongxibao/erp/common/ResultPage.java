package top.dongxibao.erp.common;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页数据封装类
 *
 * @author Dongxibao
 * @date 2020-11-27
 */
public class ResultPage<T> implements Serializable {
    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private int pageNum;
    /**
     * 每页显示数
     */
    @ApiModelProperty("每页显示数")
    private Integer pageSize;
    /**
     * 总页数
     */
    @ApiModelProperty("总页数")
    private Integer pageCount;
    /**
     * 数据总条数
     */
    @ApiModelProperty("数据总条数")
    private Integer totalRecord;
    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private List<T> result;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> ResultPage<T> restPage(List<T> list) {
        ResultPage<T> result = new ResultPage<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        result.setPageCount(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotalRecord((int) pageInfo.getTotal());
        result.setResult(pageInfo.getList());
        return result;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultPage{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", pageCount=" + pageCount +
                ", totalRecord=" + totalRecord +
                ", result=" + result +
                '}';
    }
}
