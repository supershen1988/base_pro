package com.supershen.example.core.mybatisplus;

import java.util.Collection;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.supershen.example.core.persistence.SearchFilter;

/**
 * mybatis-plus EntityWrapper工具类
 * @author guoshen
 *
 */
public class EntityWrapperUtil {
	
	/**
	 * 将集合map条件集合转换为mybatis-plus的ew
	 */
	public static <T>  EntityWrapper<T> mapfiltersToEntityWrapper(Map<String, Object> searchParams){
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		return filtersToEntityWrapper(filters.values());
	}
	
	/**
	 * 将枚举条件集合转换为mybatis-plus的ew
	 */
	public static <T> EntityWrapper<T> filtersToEntityWrapper(Collection<SearchFilter> filters){
		EntityWrapper<T> ew = new EntityWrapper<T>();
		for (SearchFilter filter : filters) {
			// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
			//String[] names = StringUtils.split(filter.fieldName, ".");
			// logic operator
			switch (filter.operator) {
			case EQ:
				ew.eq(filter.fieldName, filter.value);
				break;
			case LIKE:
				ew.like(filter.fieldName, String.valueOf(filter.value));
				break;
			case GT:
				ew.gt(filter.fieldName, filter.value);
				break;
			case LT:
				ew.lt(filter.fieldName, filter.value);
				break;
			case GTE:
				ew.ge(filter.fieldName, filter.value);
				break;
			case LTE:
				ew.le(filter.fieldName, filter.value);
				break;
				//TODO 
			}
		}
		return ew;
	}
}
