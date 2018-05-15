package com.supershen.example.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.common.collect.Sets;

/**
 * 人员表实体.
 */
@Entity
@Table(name = "base_user")
public class User implements Idable {
	private Long id;
	/** 用户名. */
	private String username;
	/** 密码. */
	private String password;
	/** 姓名. */
	private String nickname;
	/** 状态 */
	private String state = "1";
	/** 创建人 */
	private String creater;
	/** 创建时间 */
	private Date createTime;
	/** 更新人 */
	private String updater;
	/** 更新时间 */
	private Date updateTime;
	
	/** 角色集合 */
	private Set<Role> roles = Sets.newLinkedHashSet();
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "base_user_role", joinColumns = { @JoinColumn(name = "user_id") }, 
		inverseJoinColumns = {@JoinColumn(name = "role_id") })
	@OrderBy("id")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Column(name="state" ,nullable=false,columnDefinition="char default 1")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Transient
	public String getRoleRemarks() {
		String remarks = "";
		
		if (this.getRoles().size() > 0) {
			for (Role r : this.getRoles()) {
				remarks += r.getRemark() + ",";
			}
			
			if(!"".equals(remarks)){
				remarks = remarks.substring(0, remarks.length() - 1);
			}
		}
		
		return remarks;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", state=" + state + ", creater=" + creater + ", createTime=" + createTime + ", updater=" + updater
				+ ", updateTime=" + updateTime + "]";
	}

}
