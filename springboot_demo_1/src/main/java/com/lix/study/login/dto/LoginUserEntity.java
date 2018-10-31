package com.lix.study.login.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录的用户
 * @author lix
 * @Date 2018-05-19
 */
public class LoginUserEntity implements Serializable {

	private static final long serialVersionUID = 2216947559250379420L;

	private Integer userId;
	private String password;
	private String userName;
	private String status;
	private Set<RoleEntity> roles = new HashSet<>();

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<RoleEntity> getRole() {
		return roles;
	}

	public void setRole(Set<RoleEntity> role) {
		this.roles = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LoginUserEntity [userId=" + userId + ", password=" + password + ", userName=" + userName + ", roles="
				+ roles + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginUserEntity other = (LoginUserEntity) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
