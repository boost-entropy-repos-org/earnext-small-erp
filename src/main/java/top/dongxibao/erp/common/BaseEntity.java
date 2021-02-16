package top.dongxibao.erp.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import top.dongxibao.erp.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity基类
 *
 * @author Dongxibao
 * @date 2020-11-27
 */
@ToString(callSuper = true)
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -2050810768296497106L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @AutoId
    private Long id;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @CreateBy
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @CreateTime
    private Date createTime;
    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    @UpdateBy
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @UpdateTime
    private Date updateTime;
    /**
     * 逻辑删除(默认：0；删除：1)
     */
    @ApiModelProperty(value = "逻辑删除(默认：0；删除：1)")
    private Integer delFlag;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @JsonIgnore
    private Date beginTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @JsonIgnore
    private Date endTime;

    /**
     * 请求参数
     */
    @ApiModelProperty("请求参数")
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }
}
