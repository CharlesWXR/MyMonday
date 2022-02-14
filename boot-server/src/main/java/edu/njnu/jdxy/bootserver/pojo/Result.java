package edu.njnu.jdxy.bootserver.pojo;

import edu.njnu.jdxy.bootserver.utils.ResultCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private int code;
    private String message;
    private Object result;

    public Result(ResultCode resultCode, Object result) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.result = result;
    }

    public static Result success(Object result) {
        return new Result(ResultCode.SUCCESS, result);
    }

    public static Result fail(ResultCode code, Object result) {
        return new Result(code, result);
    }
}
