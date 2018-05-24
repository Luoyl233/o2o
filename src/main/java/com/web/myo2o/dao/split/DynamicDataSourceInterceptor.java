package com.web.myo2o.dao.split;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * @author lw
 */

@Intercepts({@Signature(type=Executor.class,method = "update",args = {MappedStatement.class,Object.class}),
        @Signature(type=Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {
    private static Logger logger= LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
    private static final String REGEX=".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
        Object[] objects=invocation.getArgs();
        MappedStatement ms=(MappedStatement)objects[0];
        String lookupKey=DynamicDataSourceHolder.DB_MASTER;
        if(synchronizationActive!=true){

            //读方法
            if(ms.getSqlCommandType().equals(SqlCommandType.SELECT)){
                //selectKey为自增id查询主键（SELECT LAST_INSERT_ID()方法，使用主库）
                if(ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)){
                    lookupKey=DynamicDataSourceHolder.DB_MASTER;
                }else{
                    BoundSql boundSql=ms.getSqlSource().getBoundSql(objects[1]);
                    String sql=boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                    if(sql.matches(REGEX)){
                        lookupKey=DynamicDataSourceHolder.DB_MASTER;
                    }else{
                        lookupKey=DynamicDataSourceHolder.DB_SLAVE;
                    }
                }
            }
        }
        else {
            lookupKey=DynamicDataSourceHolder.DB_MASTER;
        }
        logger.debug("设置方法[{}] use[{}] Strategy,SqlCommandType [{}]..",ms.getId(),lookupKey,ms.getSqlCommandType().name());

        DynamicDataSourceHolder.setDbType(lookupKey);
        return invocation.proceed();
    }


    //返回封装好的对象或者是代理对象，决定返回的是本体还是编制好的代理
    //如果我们准备可能拦截的对象是Executor类型（Executor类型MyBatis中负责增删改查，然后再转给intercept决定具体是哪一个数据源）
    // 的话，我们就对其进行拦截，就将intercept（）包装到里面去；
    // 如果不是，直接返回本体，不做拦截
    @Override
    public Object plugin(Object target) {
        if(target instanceof Executor){
            return Plugin.wrap(target,this);
        }
        else{
            return target;
        }

    }

    @Override
    public void setProperties(Properties properties) {

    }
}

