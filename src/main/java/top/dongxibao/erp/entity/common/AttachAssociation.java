package top.dongxibao.erp.entity.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

/**
 * 附件关联对象 attach_association
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
@ApiModel(value="附件关联对象", description="attach_association")
@Data
public class AttachAssociation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 关联id */
    @ApiModelProperty(value = "关联id")
    private Long associationId;
    /** 真实附件名称 */
    @ApiModelProperty(value = "真实附件名称")
    private String realAttachName;
    /** 修改后的附件名称 */
    @ApiModelProperty(value = "修改后的附件名称")
    private String newAttachName;
    /** 附件路径 */
    @ApiModelProperty(value = "附件路径")
    private String attachPath;
    /** 附件类型 */
    @ApiModelProperty(value = "附件类型")
    private Integer attachType;
}
