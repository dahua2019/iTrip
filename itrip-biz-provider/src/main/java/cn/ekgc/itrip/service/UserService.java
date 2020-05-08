package cn.ekgc.itrip.service;

import cn.ekgc.itrip.pojo.entity.ItripUser;

/**
 * <b>用户服务层接口</b>
 * @author Wang
 * @version 2.0.0
 * @since 2020-05-04
 */
public interface UserService {
	/**
	 * <b>通过用户名称获得用户对象</b>
	 * @param name
	 * @return
	 */
	ItripUser getUserByUserName(String name) throws Exception;

	/**
	 * <b>添加新用户信息</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean addNewUserByUser(ItripUser user) throws Exception;

	/**
	 * <b>修改用户信息</b>
	 * @param updateUser
	 * @return
	 * @throws Exception
	 */
	boolean updateUser(ItripUser updateUser) throws Exception ;

	/**
	 * <b>获得激活码</b>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	String getActiveCodeByUserCode(String userCode) throws Exception;
}
