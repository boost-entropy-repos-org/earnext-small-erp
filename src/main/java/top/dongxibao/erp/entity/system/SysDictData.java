package top.dongxibao.erp.entity.system;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

/**
 * 数据字典子对象 sys_dict_data
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
@ApiModel(value="数据字典子对象", description="sys_dict_data")
@Data
public class SysDictData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主表id */
    @ApiModelProperty(value = "主表id")
    private Long dictTypeId;
    /** 字典编码 */
    @ApiModelProperty(value = "字典编码")
    private String dictCode;
    /** 字典名称 */
    @ApiModelProperty(value = "字典名称")
    private String dictName;
    /** 是否默认(1是;0否) */
    @ApiModelProperty(value = "是否默认(1是;0否)")
    private Integer isDefault;
    /** 状态（0正常 1停用） */
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private Integer status;
    /** 字典排序 */
    @ApiModelProperty(value = "字典排序")
    private Integer dictSort;
}