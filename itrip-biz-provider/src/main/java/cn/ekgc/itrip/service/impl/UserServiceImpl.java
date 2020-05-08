package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.dao.UserDao;
import cn.ekgc.itrip.pojo.entity.ItripUser;
import cn.ekgc.itrip.service.UserService;
import cn.ekgc.itrip.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <b>用户服务层接口实现类</b>
 * @author Wang
 * @version 2.0.0
 * @since 2020-05-04
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private MailSenderUtil mailSenderUtil;
	@Autowired
	private SmsSenderUtil smsSenderUtil;


	/**
	 * <b>通过用户名称获得用户对象</b>
	 * @param name
	 * @return
	 */
	public ItripUser getUserByUserName(String name) throws Exception{
		ItripUser queryUser = new ItripUser();
		queryUser.setUserCode(name);
		List<ItripUser> userList = userDao.findUserList(queryUser);
		if (userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}

	/**
	 * <b>添加新用户信息</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean addNewUserByUser(ItripUser user) throws Exception {
		//设定用户注册时间
		user.setCreationDate(new Date());
		//保存用户
		int count = userDao.saveUser(user);
		if (count > 0) {
			//产生激活码，将激活码保存在 Redis 中
			String activeCode = ActiveCodeUtil.createActiveCode();
			//保存激活码
			redisTemplate.opsForValue().set(user.getUserCode(), activeCode);
			//设置存储在 redis中数据的存活时间
			if(RegValidationUtil.validateEmail(user.getUserCode())) {
				return mailSenderUtil.sendActiveCodeMail(user.getUserCode(), activeCode);
			} else if (RegValidationUtil.validateCellphone(user.getUserCode())){
				return smsSenderUtil.sendSms(user.getUserCode(), activeCode);
			}
			return true;
		}
		return false;
	}

	/**
	 * <b>获得激活码</b>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public String getActiveCodeByUserCode(String userCode) throws Exception {
		return redisTemplate.opsForValue().get(userCode);
	}

	/**
	 * <b>修改用户信息</b>
	 * @param updateUser
	 * @return
	 * @throws Exception
	 */
	public boolean updateUser(ItripUser updateUser) throws Exception {
		if (userDao.updateUser(updateUser) > 0) {
			return true;
		}
		return false;
	}


}
