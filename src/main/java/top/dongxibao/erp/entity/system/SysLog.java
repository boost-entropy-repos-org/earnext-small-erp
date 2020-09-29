package top.dongxibao.erp.entity.system;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

/**
 * 系统操作日志记录对象 sys_log
 * 
 * @author Dongxibao
 * @date 2020-06-21
 */
@ApiModel(value="系统操作日志记录对象", description="sys_log")
@Data
public class SysLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 模块标题 */
    @ApiModelProperty(value = "模块标题")
    private String title;
    /** 业务类型（0其它 1新增 2修改 3删除） */
    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    private Integer businessType;
    /** 方法名称 */
    @ApiModelProperty(value = "方法名称")
    private String method;
    /** 请求方式 */
    @ApiModelProperty(value = "请求方式")
    private String requestMethod;
    /** 操作类别（0其它 1后台用户 2手机端用户） */
    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;
    /** 操作人员 */
    @ApiModelProperty(value = "操作人员")
    private String operName;
    /** 请求URL */
    @ApiModelProperty(value = "请求URL")
    private String logUrl;
    /** 主机地址 */
    @ApiModelProperty(value = "主机地址")
    private String logIp;
    /** 请求参数 */
    @ApiModelProperty(value = "请求参数")
    private String requestParam;
    /** 返回参数 */
    @ApiModelProperty(value = "返回参数")
    private String resultParam;
    /** 操作状态（0正常 1异常） */
    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    private Integer status;
    /** 错误消息 */
    @ApiModelProperty(value = "错误消息")
    private String errorMsg;
}
