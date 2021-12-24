package com.funicorn.basic.common.base.model;

import lombok.Data;

import java.io.Serializable;

/**
 *  接口返回数据格式
 * @author Aimme
 */
@Data
@SuppressWarnings("unused")
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Integer SUCCESS = 200;
	public static final Integer FAIL = 400;

	/**
	 * 成功标志
	 */
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	private String message = "操作成功！";

	/**
	 * 返回代码
	 */
	private Integer code = 0;
	
	/**
	 * 返回数据对象 data
	 */
	private T data;


	public Result() {
		
	}
	
	public Result<T> success(String message) {
		return restResult(null,SUCCESS,message);
	}

	public static <T> Result<T> ok(T data,String message) {
		return restResult(data,SUCCESS,message);
	}
	
	public static <T> Result<T> ok() {
		return restResult(null,SUCCESS,"成功");
	}
	
	public static <T> Result<T> ok(String msg) {
		return restResult(null,SUCCESS,msg);
	}
	
	public static <T> Result<T> ok(T data) {
		return restResult(data,SUCCESS,"成功");
	}
	
	public static <T> Result<T> error(String msg) {
		return restResult(null,FAIL,msg);
	}
	
	public static <T> Result<T> error(int code, String msg) {
		return restResult(null,code,msg);
	}

	private static <T> Result<T> restResult(T data, int code, String msg) {
		Result<T> apiResult = new Result<>();
		apiResult.setCode(code);
		apiResult.setSuccess(SUCCESS==code);
		apiResult.setData(data);
		apiResult.setMessage(msg);
		return apiResult;
	}
}