package com.funicorn.cloud.chart.center.exception;

/**
 * @author Aimme
 */
@SuppressWarnings("unused")
public class DatavException extends RuntimeException{

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


    public DatavException(ErrorCode error, Throwable cause, String msg) {
        super(String.format(error.getMessage(), msg) , cause);
        this.code = error.getStatus();
        this.msg = super.getMessage();
    }


    public DatavException(ErrorCode error, String msg) {
        super(String.format(error.getMessage(), msg));
        this.code = error.getStatus();
        this.msg = super.getMessage();
    }

    public DatavException(ErrorCode error) {
        this.code = error.getStatus();
        this.msg = error.getMessage();
    }

    public DatavException(int code, String info, String msg) {
        super(String.format(info, msg));
        this.code = code;
        this.msg = super.getMessage();
    }
}
