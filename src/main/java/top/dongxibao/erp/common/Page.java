package top.dongxibao.erp.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 逻辑分页
 *
 * @param <T>
 * @author Dongxibao
 */
@Data
@ApiModel("逻辑分页")
public class Page<T> implements Serializable {

    private static final int PAGE_SIZE = 20;

    /** 当前页 */
    @ApiModelProperty("当前页")
    private int pageNum;
    /** 每页显示数 */
    @ApiModelProperty("每页显示数")
    private Integer pageSize;
    /** 总页数 */
    @ApiModelProperty("总页数")
    private Integer pageCount;
    /** 数据总条数 */
    @ApiModelProperty("数据总条数")
    private Integer totalRecord;
    /** 数据 */
    @ApiModelProperty("数据")
    private List<T> result;

    public Page(List<T> result, Integer pageNum, Integer pageSize) {
        this.result = result;
        if (pageSize != null) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = PAGE_SIZE;
        }
        if (pageNum != null) {
            this.pageNum = pageNum;
        } else {
            this.pageNum = 1;
        }
        this.totalRecord = this.result.size();
        this.pageCount = (totalRecord / this.pageSize) + 1;
        if (this.pageNum > this.pageCount) {
            this.pageNum = this.pageCount;
        }
    }

    public Page(List<T> result, Integer count, Integer pageNum, Integer pageSize) {
        this.result = result;
        if (pageSize != null) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = PAGE_SIZE;
        }
        if (pageNum != null) {
            this.pageNum = pageNum;
        } else {
            this.pageNum = 1;
        }
        this.totalRecord = count;
        if (count != null) {
            this.pageCount = (count / this.pageSize) + 1;
        } else {
            this.pageCount = 1;
        }

        if (this.pageNum > this.pageCount) {
            this.pageNum = this.pageCount;
        }
    }

    public List<T> getResult() {
        if (this.result.size() - 1 >= pageSize) {
            int toIndex = this.pageSize * this.pageNum;
            if (toIndex > result.size()) {
                toIndex = result.size();
            }
            return this.result.subList(this.pageSize * (this.pageNum - 1), toIndex);
        } else {
            return this.result;
        }
    }
}
