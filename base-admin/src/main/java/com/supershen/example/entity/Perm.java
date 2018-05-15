package com.supershen.example.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;

/**
 * 权限表实体.
 */
@Entity
@Table(name = "base_perm")
public class Perm implements Idable {
	private Long id;
	/** 名称 */
	private String name;
	/** 描述 */
	private String remark;
	/** 父项 */
	private Perm parent;
	/** 子集合 */
	private List<Perm> children = Lists.newArrayList();

	public Perm() {

	}

	public Perm(Long id) {
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

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public Perm getParent() {
		return parent;
	}

	public void setParent(Perm parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent")
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
