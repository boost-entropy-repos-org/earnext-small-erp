package top.dongxibao.erp.entity.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

import java.util.Date;

/**
 * 系统通知对象 sys_notifice
 * 
 * @author Dongxibao
 * @date 2020-06-28
 */
@ApiModel(value="系统通知对象", description="sys_notifice")
@Data
public class SysNotifice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 类型 */
    @ApiModelProperty(value = "类型")
    private Integer type;
    /** 发送用户 */
    @ApiModelProperty(value = "发送用户")
    private String fromUser;
    /** 接受用户 */
    @ApiModelProperty(value = "接受用户")
    private String toUser;
    /** 标题 */
    @ApiModelProperty(value = "标题")
    private String title;
    /** 内容 */
    @ApiModelProperty(value = "内容")
    private String content;
    /** 阅读时间 */
    @ApiModelProperty(value = "阅读时间")
    private Date readTime;
}
