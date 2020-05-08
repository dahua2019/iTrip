package cn.ekgc.itrip.controller;

import cn.ekgc.itrip.base.enums.UserActivatedEnum;
import cn.ekgc.itrip.base.pojo.Dto;
import cn.ekgc.itrip.controller.base.BaseController;
import cn.ekgc.itrip.pojo.entity.ItripUser;
import cn.ekgc.itrip.pojo.vo.ItripUserVO;
import cn.ekgc.itrip.transport.UserTransport;
import cn.ekgc.itrip.util.EmptyUtils;
import cn.ekgc.itrip.util.MD5Util;
import cn.ekgc.itrip.util.RegValidationUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <b>用户控制器</b>
 * @author Wang
 * @version 2.0.0
 * @since 1.0.0
 */
@RestController("userController")
@RequestMapping("/auth/api")
public class UserController extends BaseController {

	@Autowired
	private UserTransport userTransport;


	/**
	 * <b>处理用户登录请求</b>
	 * @param name
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/dologin")
	public Dto<Object> doLogin(String name, String password) throws Exception {
		return Dto.failure();
	}

	/**
	 * <b>处理注册请求-邮箱注册</b>
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="使用邮箱注册",httpMethod = "POST",
			protocols = "HTTP", produces = "application/json",
			response = Dto.class,notes="使用邮箱注册 ")
	@PostMapping(value = "/doregister")
	public Dto<Object> doRegister(@RequestBody ItripUserVO userVO) throws Exception {
		if (RegValidationUtil.validateEmail(userVO.getUserCode())) {
			//进行唯一性校验
			if(null == userTransport.getUserByUserName(userVO.getUserCode())) {
				//将 userVO 视图对象转换成 ItripUser 对象
				ItripUser user = new ItripUser();
				BeanUtils.copyProperties(userVO, user);
				user.setUserPassword(MD5Util.encrypt(userVO.getUserCode()));
				// 将激活状态设置为未激活
				user.setActivated(UserActivatedEnum.USER_ACTIVATED_NO.getCode());
				if (userTransport.doRegister(user)) {
					return Dto.success();
				}
			}
		}
		return Dto.failure();
	}

	/**
	 * <b>处理注册请求-使用手机注册</b>
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="使用手机注册",httpMethod = "POST",
			protocols = "HTTP", produces = "application/json",
			response = Dto.class,notes="使用手机注册 ")
	@PostMapping(value = "/registerbyphone")
	public Dto<Object> registerByPhone(@RequestBody ItripUserVO userVO) throws Exception {
		if (RegValidationUtil.validateCellphone(userVO.getUserCode())) {
			//进行唯一性校验
			ItripUser user = userTransport.getUserByUserName(userVO.getUserCode());
			if(user == null) {
				ItripUser user1 = new ItripUser();
				//将 userVO 视图对象转换成 ItripUser 对象
				BeanUtils.copyProperties(userVO, user1);
				//设置用户密码
				user1.setUserPassword(MD5Util.encrypt(userVO.getUserCode()));
				// 将激活状态设置为未激活
				user1.setActivated(UserActivatedEnum.USER_ACTIVATED_NO.getCode());
				if (userTransport.doRegister(user1)) {
					return Dto.success();
				}
			}
		}
		return Dto.failure();
	}

	/**
	 * <b>验证是否已存在该用户名</b>
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="用户名验证",httpMethod = "GET",
			protocols = "HTTP", produces = "application/json",
			response = Dto.class,notes="验证是否已存在该用户名")
	@GetMapping(value = "/ckusr")
	public Dto<Object> checkUser(String name) throws Exception {
		if (null == userTransport.getUserByUserName(name)) {
			return Dto.success();
		}
		return Dto.failure("该邮箱已被注册");
	}

	/**
	 * <b>邮箱注册用户激活</b>
	 * @param user
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="邮箱注册用户激活",httpMethod = "PUT",
			protocols = "HTTP", produces = "application/json",
			response = Dto.class,notes="邮箱激活")
	@PutMapping(value = "/activate")
	public Dto<Object> activate(String user, String code) throws Exception {
		//校验用户提交的信息是否有效
		if (EmptyUtils.isNotEmpty(user) && EmptyUtils.isNotEmpty(code)) {
			//通过 user 查询存储在 Redis中的激活码
			String activeCode = userTransport.getActiveCodeByUserCode(user);
			if (code.equals(activeCode)) {
				//修改用户的激活状态
				ItripUser updateUser = new ItripUser();
				updateUser.setActivated(UserActivatedEnum.USER_ACTIVATED_YES.getCode());
				if (userTransport.updateUser(updateUser)) {
					return Dto.success();
				}
			}
		}
		return Dto.failure();
	}

	/**
	 * <b>手机注册用户激活</b>
	 * @param user
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = "/validatephone")
	public Dto<Object> activeUserByCellphone(String user, String code) throws Exception {
		// 校验用户所给定的user和code有效
		if (user != null && !"".equals(user.trim()) && code != null && !"".equals(code)) {
			// 通过user在Redis中查询相应的code
			String activeCode = userTransport.getActiveCodeByUserCode(user);
			// 比较两个code是否相同
			if (code.equals(activeCode)) {
				// 修改用户的激活状态
				ItripUser updateUser = new ItripUser();
				updateUser.setUserCode(user);
				updateUser.setActivated(UserActivatedEnum.USER_ACTIVATED_YES.getCode());
				// 在数据库中更新用户数据
				userTransport.updateUser(updateUser);
				return Dto.success();
			}
			return Dto.failure("激活码不正确");
		}
		return Dto.failure("激活失败");
	}
}
