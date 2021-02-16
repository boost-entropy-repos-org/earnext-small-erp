package top.dongxibao.erp.enums;

/**
 *@ClassName ApproveStatus
 *@Description 审批状态
 *@Author Dongxibao
 *@Date 2021/1/26
 *@Version 1.0
 */
public enum ApproveStatus {


    /**
     * 新建时默认
     */
    NEW("0"),
    /**
     * 已提交（审批中）
     */
    SUBMITTED("1"),
    /**
     * 审批完成(通过)
     */
    APPROVED("2"),
    /**
     * 驳回
     */
    REJECTED("-1");

    public String code;


    ApproveStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
