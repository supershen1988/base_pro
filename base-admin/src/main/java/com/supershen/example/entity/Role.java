package com.supershen.example.entity;

import java.util.Set;

import javax.persistence.CascadeType;
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
 * 角色表实体.
 */
@Entity
@Table(name = "base_role")
public class Role implements Idable {
	private Long id;
	/** 名称 */
	private String name;
	/** 描述 */
	private String remark;
	/** 权限 */
	private Set<Perm> perms = Sets.newLinkedHashSet();
	
	public Role(){
		
	}
	
	public Role(Long id){
		this.id = id;
	}

	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "base_role_perm", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
			@JoinColumn(name = "perm_id") })
	@OrderBy("id")
	public Set<Perm> getPerms() {
		return perms;
	}

	public void setPerms(Set<Perm> perms) {
		this.perms = perms;
	}

	@Transient
	public String getPermNames() {
		String names = "";
		if (this.getPerms().size() > 0) {
			int i = 0;
			for (Perm r : this.getPerms()) {
				if (i > 8) {
					break;
				}
				names += r.getName() + ",";
				i++;
			}
			names = names.substring(0, names.length() - 1);
		}
		return names;
	}

	@Transient
	public String getPermRemarks() {
		String remarks = "";
		
		if (this.getPerms().size() > 0) {
			int i = 0;
			for (Perm r : this.getPerms()) {
				if (i > 8) {
					break;
				}
				remarks += r.getRemark() + ",";
				i++;
			}
			remarks = remarks.substring(0, remarks.length() - 1);
		}
		
		return remarks;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", remark=" + remark + "]";
	}
}
