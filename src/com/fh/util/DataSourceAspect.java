package com.fh.util;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 
 * @author wangbin
 * 数据源切片
 */
public class DataSourceAspect {
	public void pointCut() {
	};

	public void before(JoinPoint point) {
		Object target = point.getTarget();
		String method = point.getSignature().getName();
		Class<?> clazz = target.getClass();
		Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
				.getMethod().getParameterTypes();
		try {
				Method m = clazz.getMethod(method, parameterTypes);
				/**
				 * 如果有注解按注解方式获取数据源
				 */
				if (m != null && m.isAnnotationPresent(DataSource.class)) {
					DataSource data = m.getAnnotation(DataSource.class);
					HandleDataSource.putDataSource(data.value());
				}else{
					/**
					 * 按方法返回值判断数据源
					 */
					if(m != null && "void".equals(m.getGenericReturnType().toString())){
						HandleDataSource.putDataSource("write");
						return ;
					}else{
						HandleDataSource.putDataSource("read");
						return ;
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}