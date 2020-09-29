package top.dongxibao.erp.entity.system;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

/**
 * 系统访问记录对象 sys_login_log
 * 
 * @author Dongxibao
 * @date 2020-06-21
 */
@ApiModel(value="系统访问记录对象", description="sys_login_log")
@Data
public class SysLoginLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty(value = "主键")
    private Long id;
    /** 用户账号 */
    @ApiModelProperty(value = "用户账号")
    private String username;
    /** 登录IP地址 */
    @ApiModelProperty(value = "登录IP地址")
    private String ipAddr;
    /** 浏览器类型 */
    @ApiModelProperty(value = "浏览器类型")
    private String browser;
    /** 操作系统 */
    @ApiModelProperty(value = "操作系统")
    private String os;
    /** 登录状态（0成功 1失败） */
    @ApiModelProperty(value = "登录状态（0成功 1失败）")
    private Integer status;
    /** 提示消息 */
    @ApiModelProperty(value = "提示消息")
    private String message;
    /** 1:删除;0：正常 */
    @ApiModelProperty(value = "1:删除;0：正常")
    private Integer delFlag;
}
