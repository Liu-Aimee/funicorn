package com.funicorn.basic.cloud.gateway.exception;

/**
 * @author Aimee
 * @since 2021/4/22 9:46
 */

@SuppressWarnings(value = {"all"})
public class GatewayException extends RuntimeException{


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

    public GatewayException(GatewayErrorCode error, Throwable cause, String... msg) {
        super(String.format(error.getMessage(), msg) , cause);
        this.code = error.getStatus();
        this.msg = super.getMessage();
    }


    public GatewayException(GatewayErrorCode error, String... msg) {
        super(String.format(error.getMessage(), msg));
        this.code = error.getStatus();
        this.msg = super.getMessage();
    }

    public GatewayException(GatewayErrorCode error) {
        this.code = error.getStatus();
        this.msg = error.getMessage();
    }

    public GatewayException(int code, String info, String... msg) {
        super(String.format(info, msg));
        this.code = code;
        this.msg = super.getMessage();
    }
}
