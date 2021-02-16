package top.dongxibao.erp.entity.system;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

/**
 * 接口日志记录表 sys_log
 * 
 * @author Dongxibao
 * @date 2020-11-27
 */
@ApiModel("接口日志记录")
@Data
public class SysLog extends BaseEntity {

	/** 请求参数 */
	@ApiModelProperty("请求参数")
	private String requestParam;
	/** 返回参数 */
	@ApiModelProperty("返回参数")
	private String resultParam;
	/** 模块 */
	@ApiModelProperty("模块")
	private String moduleCode;
	/** 状态(1成功;0失败) */
	@ApiModelProperty("状态(1成功;0失败)")
	private Integer status;
	/** 消息 */
	@ApiModelProperty("消息")
	private String message;
	/** 请求方法名称 */
	@ApiModelProperty("请求方法名称")
	private String method;
	/** 请求ip */
	@ApiModelProperty("请求ip")
	private String requestIp;
	/** 请求方式 */
	@ApiModelProperty("请求方式")
	private String requestMethod;
	/** 业务类型（0其它 1新增 2修改 3删除） */
	@ApiModelProperty("业务类型（0其它 1新增 2修改 3删除）")
	private Integer businessType;
}
