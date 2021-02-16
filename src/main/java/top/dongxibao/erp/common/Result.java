package top.dongxibao.erp.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回实体类
 *
 * @author Dongxibao
 * @date 2020-11-27
 */
@Data
@ApiModel("统一返回实体类")
public class Result<T> implements Serializable {

    @ApiModelProperty("状态")
    private boolean status;

    @ApiModelProperty("响应代码")
    private int code;

    @ApiModelProperty("消息")
    private String message;

    @ApiModelProperty("数据")
    private T data;

    public static <T> Result<T> ok() {
        return new Result(null, true, 200, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result(null, true, 200, data);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return new Result(msg, true, 200, data);
    }

    public static <T> Result<T> fail() {
        return new Result(null, false, 500, null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result(msg, false, 400, null);
    }

    public static <T> Result<T> fail(T data) {
        return new Result(null, false, 500, data);
    }

    public static <T> Result<T> fail(String msg, T data) {
        return new Result(msg, false, 500, data);
    }

    public static <T> Result<T> of(boolean status, String msg) {
        if (status) {
            return new Result(msg, status, 200, null);
        } else {
            return new Result(msg, status, 500, null);
        }
    }

    public Result(String message, boolean status, int code, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
