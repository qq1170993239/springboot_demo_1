package com.lix.study.login.dao;

import org.apache.ibatis.annotations.Mapper;

import com.lix.study.login.dto.LoginUserEntity;
/**
 * 用户数据服务接口
 * @author lix
 * @Date 2018-05-19
 */
@Mapper
public interface LoginUserDao {
	/**
	 * 根据姓名查询用户
	 * @param userName
	 * @return
	 */
	LoginUserEntity findUserByName(String userName);

}
