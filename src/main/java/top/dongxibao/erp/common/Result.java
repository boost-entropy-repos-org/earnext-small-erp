package top.dongxibao.erp.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 统一返回实体类
 *
 * @param <T>
 * @author Dongxibao
 * @date 2020-06-07
 */
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

    public Result() {
        this("", true);
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
        this.status = false;
    }

    public Result(T data) {
        this(null, data, true);
    }

    public Result(String message) {
        this(message, true);
    }

    public Result(String message, boolean status) {
        this(message, null, status);
    }

    public Result(String message, T data) {
        this(message, data, true);
    }

    public Result(String message, T data, boolean status) {
        this.status = status;
        this.message = message;
        this.data = data;
        if (this.status || this.data != null) {
            this.code = HttpStatus.OK.value();
        } else {
            this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
    }
    public Result(T data, boolean status) {
        this.status = status;
        this.data = data;
        if (this.status || this.data != null) {
            this.code = HttpStatus.OK.value();
        } else {
            this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
    }

    public Result(String message, boolean status, int code, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public boolean getStatus() {
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
