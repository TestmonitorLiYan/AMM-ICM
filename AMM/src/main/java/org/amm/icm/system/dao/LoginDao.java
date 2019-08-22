package org.amm.icm.system.dao;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;

public interface LoginDao {
	/**
	 * 根据用户名和密码查询对应的用户
	 */
	JSONObject getUser(@Param("username") String username, @Param("password") String password);
}
