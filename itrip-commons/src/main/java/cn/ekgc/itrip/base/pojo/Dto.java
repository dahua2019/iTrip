package cn.ekgc.itrip.base.pojo;

import java.io.Serializable;

/**
 * <b>系统响应数据传输对象</b>
 * @author Wang
 * @version 2.0.0
 * @since 1.0.0
 */
public class Dto<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private T data;                 //响应结果数据
	private String errorCode;       //错误编码
	private String msg;             //错误信息
	private String success;         //成功响应

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	/**
	 * <b>获得数据传输对象-响应成功（携带响应数据）</b>
	 * @param data
	 * @return
	 */
	public static Dto<Object> success(Object data) {
		Dto<Object> dto = new Dto<Object>();
		dto.setSuccess("true");
		dto.setData(data);
		return dto;
	}

	/**
	 * <b>获得数据传输对象-响应成功（不携带响应数据）</b>
	 * @return
	 */
	public static Dto<Object> success() {
		Dto<Object> dto = new Dto<Object>();
		dto.setSuccess("true");
		return dto;
	}

	/**
	 * <b>获得数据传输对象-响应失败（携带错误信息）</b>
	 * @param msg
	 * @return
	 */
	public static Dto<Object> failure(String msg) {
		Dto<Object> dto = new Dto<Object>();
		dto.setSuccess("false");
		dto.setMsg(msg);
		return dto;
	}

	/**
	 * <b>获得数据传输对象-响应失败（携带错误信息和错误编码）</b>
	 * @param msg
	 * @return
	 */
	public static Dto<Object> failure(String msg, String errorCode) {
		Dto<Object> dto = new Dto<Object>();
		dto.setSuccess("false");
		dto.setErrorCode(errorCode);
		dto.setData(msg);
		return dto;
	}

	/**
	 * <b>获得数据传输对象-响应失败（不携带错误信息）</b>
	 * @return
	 */
	public static Dto<Object> failure() {
		Dto<Object> dto = new Dto<Object>();
		dto.setSuccess("false");
		return dto;
	}
}
