package com.supershen.example.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.collect.Lists;

/**
 * 角色表实体.
 */
@TableName("base_role")
public class Role implements Serializable,Idable{
	private Integer id;
	/** 名称 */
	private String name;
	/** 描述 */
	private String remark;
	/** 权限 */
	@TableField(exist = false)
	private List<Perm> perms = Lists.newArrayList();
	
	public Role(){
		
	}
	
	public Role(Integer id){
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	
//	public Set<Perm> getPerms() {
//		return perms;
//	}
//
//	public void setPerms(Set<Perm> perms) {
//		this.perms = perms;
//	}

//	@Transient
//	public String getPermNames() {
//		String names = "";
//		if (this.getPerms().size() > 0) {
//			int i = 0;
//			for (Perm r : this.getPerms()) {
//				if (i > 8) {
//					break;
//				}
//				names += r.getName() + ",";
//				i++;
//			}
//			names = names.substring(0, names.length() - 1);
//		}
//		return names;
//	}

//	@Transient
//	public String getPermRemarks() {
//		String remarks = "";
//		
//		if (this.getPerms().size() > 0) {
//			int i = 0;
//			for (Perm r : this.getPerms()) {
//				if (i > 8) {
//					break;
//				}
//				remarks += r.getRemark() + ",";
//				i++;
//			}
//			remarks = remarks.substring(0, remarks.length() - 1);
//		}
//		
//		return remarks;
//	}

	public List<Perm> getPerms() {
		return perms;
	}

	public void setPerms(List<Perm> perms) {
		this.perms = perms;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", remark=" + remark + "]";
	}
}
