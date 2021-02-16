package top.dongxibao.erp.constant;

import java.awt.*;

/**
 * @author Dongxibao
 * @date 2021-1-18
 */
public interface WorkflowConstants {

    /**businessKey**/
    String WORKLOW_BUSINESS_KEY = "businessKey";
    /**按钮网关**/
    String WAY_TYPE = "wayType";
    /**按钮网关**/
    String WAY_TYPE_PREFIX = "way_type_";
    /**项目id**/
    String PROJECT_ID = "projectId";
    /**核心企业Id变量**/
    String CORE_ENTERPRISE_ID = "coreEnterpriseId";
    /**链属企业Id变量**/
    String CHAIN_ENTERPRISE_ID = "chainEnterpriseId";
    /**银行企业Id变量**/
    String BANK_ENTERPRISE_ID = "bankEnterpriseId";
    /**保理公司Id变量**/
    String BAOLI_ENTERPRISE_ID = "baoliEnterpriseId";
    /**立账开立企业Id变量**/
    String START_ENTERPRISE_ID = "startEnterpriseId";
    /**立账合作企业Id变量**/
    String PARTNER_ENTERPRISE_ID = "partnerEnterpriseId";
    /**母公司企业id**/
    String PARENT_ENTERPRISE_ID = "parentEnterpriseId";
    /**指定签收企业id**/
    String RECEIVE_ENTERPRISE_ID = "receiveEnterpriseId";
    /**转出方企业Id变量**/
    String TRANSFER_ENTERPRISE_ID = "transEnterpriseId";
    /**指定签收企业id**/
    String AC_TASK_ID = "acTaskId";
    /**企业所有角色**/
    String ENT_ALL_ROLE = "all";
    /**运营所有角色**/
    String OPER_ALL_ROLE = "oper";
    /**流程定义缓存时间**/
    int PROCESS_DEFINITION_CACHE = 60;
    /**流程实例激活**/
    int PROCESS_INSTANCE_ACTIVE = 1;

    /**流程实例挂起**/
    int PROCESS_INSTANCE_SUSPEND = 0;

    /**读取图片**/
    String READ_IMAGE = "image";

    /**读取xml**/
    String READ_XML = "xml";

    /**流程激活**/
    Integer ACTIVE_PROCESSDEFINITION = 1;

    /**流程挂起**/
    Integer SUSPEND_PROCESSDEFINITION = 2;

    /**流程状态：0-全部，1-正常，2-已挂起**/
    int QUERY_ALL = 0;
    int QUERY_NORMAL = 1;
    int QUERY_SUSPENDED = 2;

    /**流程实例状态：0-全部，1-正常，2-已删除**/
    int INSTANCE_ALL = 0;
    int INSTANCE_NOT_DELETED = 1;
    int INSTANCE_DELETED = 2;

    /** 系统管理员ID **/
    String INTERFACE_SYSTEM_ID = "-1";
    /** 系统管理员名称 **/
    String INTERFACE_SYSTEM_NAME = "系统操作";

    /** 流程部署类型:1-启动并激活，2-启动即挂起 **/
    int PROCESS_START_ACTIVE = 1;
    int PROCESS_START_SUSPEND = 2;

    /** 用于标识流程项目配置信息校验结果：1：新流程，2：新版本， 3：流程类别有误  **/
    int CHECK_NEW_PROCESS = 1;
    int CHECK_NEW_VERSION = 2;
    int CHECK_ERROR_PROCESS_TYPE = 3;

    /** 默认网关条件值 **/
    Integer default_GATEWAY_CONDITION_VALUE = 1;

    /** 工作流-业务状态表数据类型：1-工作流状态，2-业务状态 **/
    Integer PROCESS_STATUS = 1;
    Integer BIZNESS_STATUS = 2;

    /** 新增流程时标识：1-直接保存，2-提示覆盖 **/
    Integer PROCESS_STATUS_SAVE = 1;
    Integer BIZNESS_STATUS_WARN = 2;

    /** 模板类型标识：1-新创建或直接导入的模板，2-默认模板生成 **/
    Integer MODEL_TYPE_1 = 1;
    Integer MODEL_TYPE_2 = 2;

    /** 查询流程定义标识：1-查询最新版本流程定义，2-查询所有版本 **/
    Integer QUERY_PROCESS_LATEST_VERSION = 1;
    Integer QUERY_PROCESS_ALL_VERSION = 2;

    /** 按钮网关 通过1 */
    String WAY_TYPE_PASS = "1";
    /** 按钮网关 驳回或结束0 */
    String WAY_TYPE_REJECT = "0";

    /** 按钮网关 退回2 */
    String WAY_TYPE_BACK = "2";

    /**任务参数为空**/
    int TASK_CHECK_PARAM_NULL = -1;
    /**任务已办理**/
    int TASK_CHECK_COMPLETED = 1;
    /**无权限办理**/
    int TASK_CHECK_NO_PERMISSIONS = 2;
    /**任务校验通过**/
    int TASK_CHECK_PASS = 0;
    /** 动态流程图颜色定义 **/
    Color COLOR_NORMAL = new Color(0, 205, 0);
    Color COLOR_CURRENT = new Color(255, 0, 0);

    /** 定义生成流程图时的边距(像素) **/
    int PROCESS_PADDING = 5;

    /** 提交参数 start */
    String moduleId = "module_id";
    String moduleName = "module_name";
    String businessId = "business_id";
    String businessNo = "business_no";
    String amount = "amount";
    String tableName = "table_name";
    String initiator = "initiator";
    /** 提交参数 end */
}
