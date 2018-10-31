package com.lix.study.login.dto;

import java.io.Serializable;

public class PermissionEntity implements Serializable {

	private static final long serialVersionUID = -7188903058051936294L;
	
	private Integer permissionId;
	private String permissionValue;
	private String permissionDesc;

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionValue() {
		return permissionValue;
	}

	public void setPermissionValue(String permissionValue) {
		this.permissionValue = permissionValue;
	}

	public String getPermissionDesc() {
		return permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((permissionId == null) ? 0 : permissionId.hashCode());
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
		PermissionEntity other = (PermissionEntity) obj;
		if (permissionId == null) {
			if (other.permissionId != null)
				return false;
		} else if (!permissionId.equals(other.permissionId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PermissionEntity [permissionId=" + permissionId + ", permissionValue=" + permissionValue
				+ ", permissionDesc=" + permissionDesc + "]";
	}
	
	

}
