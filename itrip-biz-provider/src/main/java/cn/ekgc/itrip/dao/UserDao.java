package cn.ekgc.itrip.dao;

import cn.ekgc.itrip.pojo.entity.ItripUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>用户数据持久层接口</b>
 * @author Wang
 * @version 2.0.0
 * @since 2020-05-04
 */
@Repository
public interface UserDao {
	/**
	 * <b>通过查询对象查询用户列表</b>
	 * @param queryUser
	 * @return
	 * @throws Exception
	 */
	List<ItripUser> findUserList(ItripUser queryUser) throws Exception;

	/**
	 * <b>保存新用户信息</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	Integer saveUser(ItripUser user) throws Exception;

	/**
	 * <b>更新用户信息</b>
	 * @param updateUser
	 * @return
	 * @throws Exception
	 */
	Integer updateUser(ItripUser updateUser) throws Exception;
}
