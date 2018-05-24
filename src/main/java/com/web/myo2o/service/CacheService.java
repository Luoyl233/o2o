package com.web.myo2o.service;

/**
 * @author lw
 */

public interface CacheService {
    /**
     * 依据key前缀删除匹配该模式下的所有key-value 如传入:shopcategory,则
     * 以shopcategory打头的key-value都会被清空
     */

    void removeFromCache(String keyPrefix);
}
