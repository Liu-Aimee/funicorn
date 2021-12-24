package com.funicorn.cloud.task.center.exception;

/**
 * @author Aimme
 * @since 2020/12/14 9:29
 */
public class TaskException extends RuntimeException{

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


    public TaskException(TaskErrorCode error, Throwable cause, String msg) {
        super(String.format(error.getMessage(), msg) , cause);
        this.code = error.getStatus();
        this.msg = super.getMessage();
    }


    public TaskException(TaskErrorCode error, String msg) {
        super(String.format(error.getMessage(), msg));
        this.code = error.getStatus();
        this.msg = super.getMessage();
    }

    public TaskException(TaskErrorCode error) {
        this.code = error.getStatus();
        this.msg = error.getMessage();
    }

    public TaskException(int code, String info, String msg) {
        super(String.format(info, msg));
        this.code = code;
        this.msg = super.getMessage();
    }
}
