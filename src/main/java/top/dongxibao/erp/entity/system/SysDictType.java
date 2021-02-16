package top.dongxibao.erp.entity.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 数据字典主对象 sys_dict_type
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
@ApiModel(value="数据字典主对象", description="sys_dict_type")
@Data
public class SysDictType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 字典名称 */
    @NotEmpty(message = "字典名称不能为空")
    @ApiModelProperty(value = "字典名称")
    private String dictName;
    /** 字典类型 */
    @NotEmpty(message = "字典类型不能为空")
    @ApiModelProperty(value = "字典类型")
    private String dictType;
    /** 状态（0正常 1停用） */
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private Integer status;

    @ApiModelProperty(value = "字表")
    private List<SysDictData> sysDictDataList;
}
