package com.supershen.example.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.collect.Lists;

/**
 * 权限表实体.
 */
@TableName("base_perm")
public class Perm  {
	private Integer id;
	/** 名称 */
	private String name;
	/** 描述 */
	private String remark;
	/** 父项id */
	private Integer parentId;
	/** 子集合 */
	@TableField(exist = false)
	private List<Perm> children = Lists.newArrayList();

	public Perm() {

	}

	public Perm(Integer id) {
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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public List<Perm> getChildren() {
		return children;
	}

	public void setChildren(List<Perm> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Perm [id=" + id + ", name=" + name + ", remark=" + remark + "]";
	}
}
