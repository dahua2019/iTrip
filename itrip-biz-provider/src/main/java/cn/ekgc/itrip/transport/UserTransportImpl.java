package cn.ekgc.itrip.transport;

import cn.ekgc.itrip.pojo.entity.ItripUser;
import cn.ekgc.itrip.service.UserService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <b>爱旅行-用户信息传输层接口实现类</b>
 * @author Wang
 * @version 2.0.0
 * @since 2020-05-03
 */
@RestController("userTransport")
@RequestMapping("/user/info")
public class UserTransportImpl implements UserTransport {

	@Autowired
	UserService userService;

	/**
	 * <b>通过用户名称获得用户对象</b>
	 * @param name
	 * @return
	 */
	@PostMapping(value = "/get")
	public ItripUser getUserByUserName(@RequestParam String name) throws Exception{
		return userService.getUserByUserName(name);
	}

	/**
	 * <b>添加新用户信息</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/add")
	public boolean doRegister(@RequestBody ItripUser user) throws Exception {
		return userService.addNewUserByUser(user);
	}

	/**
	 * <b>修改用户信息</b>
	 * @param updateUser
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/update")
	public boolean updateUser(@RequestBody ItripUser updateUser) throws Exception {
		return userService.updateUser(updateUser);
	}

	/**
	 * <b>获得激活码</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/getCode")
	public String getActiveCodeByUserCode(@RequestParam String user) throws Exception {
		return userService.getActiveCodeByUserCode(user);
	}
}
