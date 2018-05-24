package com.web.myo2o.service;

import com.web.myo2o.dto.UserProductMapExecution;
import com.web.myo2o.entity.UserProductMap;

public interface UserProductMapService {
	/**
	 * 
	 * @param
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	UserProductMapExecution listUserProductMap(
            UserProductMap userProductCondition, Integer pageIndex,
            Integer pageSize);

	/**
	 * 
	 * @param userProductMap
	 * @return
	 * @throws RuntimeException
	 */
	UserProductMapExecution addUserProductMap(UserProductMap userProductMap)
			throws RuntimeException;

}
