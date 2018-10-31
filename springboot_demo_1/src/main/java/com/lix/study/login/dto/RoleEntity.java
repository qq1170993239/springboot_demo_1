package com.lix.study.login.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RoleEntity implements Serializable {

	private static final long serialVersionUID = -7664309253530303281L;
	
	private Integer roleId;
	private String roleValue;
	private String roleDesc;
	private Set<PermissionEntity> permissions = new HashSet<>();
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleValue() {
		return roleValue;
	}
	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public Set<PermissionEntity> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<PermissionEntity> permissions) {
		this.permissions = permissions;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
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
		RoleEntity other = (RoleEntity) obj;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RoleEntity [roleId=" + roleId + ", roleValue=" + roleValue + ", roleDesc=" + roleDesc + ", permissions="
				+ permissions + "]";
	}
	
	
	
}
