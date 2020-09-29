package top.dongxibao.erp.common;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 分页数据封装类
 *
 * @author Dongxibao
 * @date 2020-06-14
 */
public class ResultPage<T> {
    /** 当前页 */
    @ApiModelProperty("当前页")
    private Integer pageNum;
    /** 每页显示数 */
    @ApiModelProperty("每页显示数")
    private Integer pageSize;
    /** 总页数 */
    @ApiModelProperty("总页数")
    private Integer totalPage;
    /** 数据总条数 */
    @ApiModelProperty("数据总条数")
    private Long total;
    /** 数据 */
    @ApiModelProperty("数据")
    private List<T> list;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> ResultPage<T> restPage(List<T> list) {
        ResultPage<T> result = new ResultPage<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
