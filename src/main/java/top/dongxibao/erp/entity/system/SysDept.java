package top.dongxibao.erp.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 部门对象 sys_dept
 * 
 * @author Dongxibao
 * @date 2020-06-14
 */
@ApiModel(value="部门对象", description="sys_dept")
@Data
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 父部门id */
    @ApiModelProperty(value = "父部门id")
    private Long parentId;
    /** 祖级列表  */
    @ApiModelProperty(value = "祖级列表")
    private String ancestors;
    /** 部门编码 */
    @NotEmpty(message = "部门编码不能为空")
    @ApiModelProperty(value = "部门编码")
    private String deptCode;
    /** 部门名称 */
    @NotEmpty(message = "部门名称不能为空")
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    /** 显示顺序 */
    @ApiModelProperty(value = "显示顺序")
    private Integer deptSort;
    /** 负责人 */
    @ApiModelProperty(value = "负责人")
    private String leader;
    /** 联系电话 */
    @ApiModelProperty(value = "联系电话")
    private String phone;
    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /** 部门状态:0正常,1停用 */
    @ApiModelProperty(value = "部门状态:0正常,1停用")
    private String status;

    // ********************************
    @TableField(exist = false)
    private List<SysDept> children;
}
