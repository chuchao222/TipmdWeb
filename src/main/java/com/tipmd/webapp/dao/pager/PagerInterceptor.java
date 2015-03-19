package com.tipmd.webapp.dao.pager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.tipmd.webapp.dao.pager.dialect.Dialect;
import com.tipmd.webapp.dao.pager.dialect.MysqlDialect;

/**
 * @author bowee2010
 * 
 *         自定义Mybatis插件类，主要作用是拦截查询SQL语句，并在其上追加分页语句
 * 
 *         什么是插件类: http://blog.csdn.net/hupanfeng/article/details/9247379
 * 
 *         两种方式实现分页拦截： （1）SQL
 *         的解析是在StatementHandler里完成的,所以为了重写sql需要拦截StatementHandler
 *         http://blog.csdn.net/hupanfeng/article/details/9238127
 *         （2）通过拦截Executor的query方法
 * 
 *         MyBatis执行一次查询任务，其流程如下： 调用dao的查询方法(此拦截器会对带有Pager参数的方法进行拦截) -->
 *         sqlSessionTemplate.selectList() --> sqlSession.selectList() -->
 *         excutor.query() --> statementHandler.query() -->
 *         resultSetHandler.handleResultSets 返回结果给dao
 */


// @Intercepts({@Signature(
// type= Executor.class,
// method = "query",
// args = {MappedStatement.class, Object.class, RowBounds.class,
// ResultHandler.class})})

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PagerInterceptor implements Interceptor {
	protected final static Logger log = Logger.getLogger(PagerInterceptor.class);
	private final static ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private final static ObjectWrapperFactory DEFAULT_OBJECT_WARPPER_FACTORY = new DefaultObjectWrapperFactory();
	private Dialect dialect;
	private final static String DEFAULT_DIALECT_CLASS_NAME = "com.tipmd.webapp.dao.pager.dialect.MysqlDialect";
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		//forObject后面两个参数这样写行吗?
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WARPPER_FACTORY);
		
		// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
        // 可以分离出最原始的的目标类)
//        while (metaStatementHandler.hasGetter("h")) {
//            Object object = metaStatementHandler.getValue("h");
//            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WARPPER_FACTORY);
//        }
//        // 分离最后一个代理对象的目标类
//        while (metaStatementHandler.hasGetter("target")) {
//            Object object = metaStatementHandler.getValue("target");
//            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WARPPER_FACTORY);
//        }
		
		RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
		if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
			return invocation.proceed();
		}
		
		Pager pager = null;
		if(rowBounds instanceof Pager) {
			pager = (Pager)rowBounds;
			
		} else {
			return invocation.proceed(); 
		}

		String originalSQL = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		log.debug("原始SQL: " + originalSQL);
		metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getPaginationSQL(originalSQL, pager));
		BoundSql boundSql = statementHandler.getBoundSql();
		log.debug("分页SQL : " + boundSql.getSql());
		//下面两行阻止内存分页
		metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
		metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
		
		if(pager.isGetTotalCount()) {
			log.debug("检查到程序需要查找纪录总数...");
			String countSQL = dialect.getCountSQL(originalSQL);
			log.debug("生成查找纪录总数SQL : " + countSQL);
			//TODO: 执行程序查找纪录总数
			Connection connection = (Connection) invocation.getArgs()[0];
			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
			int totalCount = getTotal(countSQL, connection, mappedStatement, boundSql);
			pager.setTotalCount(totalCount);
			log.debug("执行查找纪录总数操作...");
		}
		
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {  
	        return Plugin.wrap(target, this);  
	    } else {  
	        return target;  
	    }  
	}

	@Override
	public void setProperties(Properties properties) {
		//String dialectClass = new PropertiesHelper(properties).getRequiredString("dialectClass");  
		String dialectClass = properties.getProperty("dialectClass", DEFAULT_DIALECT_CLASS_NAME);
        try {  
            dialect = (Dialect)Class.forName(dialectClass).newInstance();  
        } catch (Exception e) {  
            throw new RuntimeException("cannot create dialect instance by dialectClass:"+dialectClass,e);  
        }
        log.debug("<====== set dialect to: " + dialectClass);  
    }  
     
	//查询纪录总数
	private int getTotal(String countSql, Connection connection, MappedStatement mappedStatement,  BoundSql boundSql) {
		PreparedStatement countStmt = null;  
	    ResultSet rs = null;  
	    try {  
	        countStmt = connection.prepareStatement(countSql);  
	        BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());  
	        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), countBS);  
	        parameterHandler.setParameters(countStmt);
	        rs = countStmt.executeQuery();  
	        int totalCount = 0;  
	        if (rs.next()) {  
	            totalCount = rs.getInt(1);  
	        }  
	        return totalCount;
	    } catch (SQLException e) {  
	        log.error("Ignore this exception", e);  
	    } finally {  
	        try {  
	            rs.close();  
	        } catch (SQLException e) {  
	        	log.error("Ignore this exception", e);  
	        }  
	        try {  
	            countStmt.close();  
	        } catch (SQLException e) {  
	        	log.error("Ignore this exception", e);  
	        }  
	    }  
	    return 0;
	}

}
