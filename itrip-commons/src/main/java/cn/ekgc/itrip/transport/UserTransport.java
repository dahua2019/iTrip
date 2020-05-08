package cn.ekgc.itrip.transport;

import cn.ekgc.itrip.pojo.entity.ItripUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <b>爱旅行-用户信息传输层接口</b>
 * @author Wang
 * @version 2.0.0
 * @since 2020-05-03
 */
@FeignClient(name = "itrip-biz-provider")
@RequestMapping("/user/info")
public interface UserTransport {
	/**
	 * <b>通过用户名称获得用户对象</b>
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/get")
	ItripUser getUserByUserName(@RequestParam String name) throws Exception;

	/**
	 * <b>添加新用户信息</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/add")
	boolean doRegister(@RequestBody ItripUser user) throws Exception;

	/**
	 * <b>修改用户信息</b>
	 * @param updateUser
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/update")
	boolean updateUser(@RequestBody ItripUser updateUser) throws Exception;

	/**
	 * <b>获得激活码</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/getCode")
	String getActiveCodeByUserCode(@RequestParam String user) throws Exception;
}
