package com.supershen.example.core.mybatisplus;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.supershen.example.entity.Idable;

/**
 * 业务逻辑基类
 * @author gshen
 *
 */
public class BaseService<M extends BaseMapper<T>, T extends Idable> extends ServiceImpl<BaseMapper<T>, T>{
	
	/**
	 * 单表查询分页
	 * @param searchParams map集合 eg: key:LIKE_username value:supershen
	 * @param pageNumber 分页当前页
	 * @param pageSize 每页默认行数
	 * @return 分页实体
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Page<T> findPage(Map<String, Object> searchParams,int pageNumber, int pageSize) {
		Page<T> page = new Page<T>(pageNumber,pageSize);
		
		EntityWrapper<T> ew = EntityWrapperUtil.mapfiltersToEntityWrapper(searchParams);
		ew =  (EntityWrapper<T>) SqlHelper.fillWrapper(page,  ew);
        page.setRecords(baseMapper.selectPage(page, ew));
		return page;
	}
	
	/**
	 * 保存实体【根据id判断插入or修改】
	 */
	public Boolean save(T entity) {
		if(entity.getId() == null){
			return retBool(baseMapper.insert(entity));
		}
		return retBool(baseMapper.updateById(entity));
	}
}
