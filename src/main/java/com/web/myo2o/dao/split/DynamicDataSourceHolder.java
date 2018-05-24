package com.web.myo2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lw
 */

public class DynamicDataSourceHolder {
    private static Logger logger= LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    private static ThreadLocal<String> contextHolder =new ThreadLocal<String>();
    public static final String DB_MASTER="master";
    public static final String DB_SLAVE="slave";
    public static String getDbType(){
        String db=contextHolder.get();
        if(db==null){
            db=DB_MASTER;
        }
        return db;
    }

    //设置线程的dbType
    public static void setDbType(String str){
        logger.debug("所用的数据源是："+str);
        contextHolder.set(str);
    }
    //清洗连接类型
    public static void cleanDbType(){
        contextHolder.remove();
    }
}
