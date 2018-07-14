package com.example.demo.domains;

import com.example.demo.domains.enums.ResultEnum;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Result {
    private int resultCode;
    private String resultMessage;
    private boolean success;

    public static Result getSuccessResult() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }

    public static Result getErrorResult(int code, String message) {
        Result result = new Result();
        result.setSuccess(false);
        result.setResultCode(code);
        result.setResultMessage(message);
        return result;
    }

    public static Result getErrorResult(ResultEnum resultEnum) {
        Result result = new Result();
        result.setSuccess(false);
        result.setResultCode(resultEnum.getCode());
        result.setResultMessage(resultEnum.getMessage());
        return result;
    }
}
