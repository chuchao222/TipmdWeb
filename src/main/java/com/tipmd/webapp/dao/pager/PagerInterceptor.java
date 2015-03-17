package com.tipmd.webapp.dao.pager;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

/**
 * @author bowee2010
 *	
 * 自定义Mybatis插件类，主要作用是拦截查询SQL语句，并在其上追加分页语句
 * 
 * 什么是插件类: http://blog.csdn.net/hupanfeng/article/details/9247379
 * 
 * 两种方式实现分页拦截：
 * （1）SQL 的解析是在StatementHandler里完成的,所以为了重写sql需要拦截StatementHandler
 * 		http://blog.csdn.net/hupanfeng/article/details/9238127
 * （2）通过拦截Executor的query方法
 */

//拦截的目标类型是StatementHandler实现类（这里只能写接口），拦截的方法: prepare 方法参数：Connection
//@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})

@Intercepts({@Signature(
		type= Executor.class,
		method = "query",
		args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PagerInterceptor implements Interceptor 
{
	protected final static Logger log = Logger.getLogger(PagerInterceptor.class);
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

}
