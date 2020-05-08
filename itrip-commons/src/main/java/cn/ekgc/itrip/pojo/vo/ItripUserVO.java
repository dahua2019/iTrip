package cn.ekgc.itrip.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <b>用户注册信息数据传输对象</b>
 * @author Wang
 * @version 2.0.0
 * @since 1.0.0
 */
@ApiModel(value = "ItripUserVO", description = "用户注册信息")
public class ItripUserVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("[必填] 注册用户名称")
	private String userCode;
	@ApiModelProperty("[必填] 密码")
	private String userPassword;
	@ApiModelProperty("[非必填] 昵称")
	private String userName="";

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
