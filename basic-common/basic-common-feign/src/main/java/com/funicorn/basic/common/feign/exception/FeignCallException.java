package com.funicorn.basic.common.feign.exception;

import com.funicorn.basic.common.base.exception.BaseErrorCode;

/**
 * @author Aimee
 * @since 2021/12/29 9:45
 */
@SuppressWarnings("all")
public class FeignCallException extends RuntimeException{

    private static final long serialVersionUID = -1L;
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public FeignCallException(BaseErrorCode error, Throwable cause, String... msg) {
        super(String.format(error.getMessage(), msg) , cause);
        this.code = error.getStatus();
        this.msg = super.getMessage();
    }


    public FeignCallException(BaseErrorCode error, String... msg) {
        super(String.format(error.getMessage(), msg));
        this.code = error.getStatus();
        this.msg = super.getMessage();
    }

    public FeignCallException(BaseErrorCode error) {
        this.code = error.getStatus();
        this.msg = error.getMessage();
    }

    public FeignCallException(int code, String info, String... msg) {
        super(String.format(info, msg));
        this.code = code;
        this.msg = super.getMessage();
    }
}
