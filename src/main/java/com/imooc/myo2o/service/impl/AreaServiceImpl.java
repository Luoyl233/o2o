package com.imooc.myo2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.myo2o.cache.JedisUtil;
import com.imooc.myo2o.dao.AreaDao;
import com.imooc.myo2o.dto.AreaExecution;
import com.imooc.myo2o.entity.Area;
import com.imooc.myo2o.enums.AreaStateEnum;
import com.imooc.myo2o.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {



	@Autowired
	private  AreaDao areaDao;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	@Autowired
	private JedisUtil.Keys jedisKeys;




//	@Override
//	public List<Area> getAreaList()  {
//
//		return areaDao.queryArea();
//	}

	@Override
	public List<Area> getAreaList() throws JsonParseException,
			JsonMappingException, IOException {
		String key = AREALISTKEY;
		List<Area> areaList = null;
		ObjectMapper mapper = new ObjectMapper();
		if (!jedisKeys.exists(key)) {
			areaList = areaDao.queryArea();
			String jsonString = mapper.writeValueAsString(areaList);
			jedisStrings.set(key, jsonString);
		} else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory()
					.constructParametricType(ArrayList.class, Area.class);
			areaList = mapper.readValue(jsonString, javaType);
		}
		return areaList;
	}


	@Override
	public AreaExecution addArea(Area area) {
		return null;
	}

	@Override
	public AreaExecution modifyArea(Area area) {
		return null;
	}

	@Override
	public AreaExecution removeArea(long areaId) {
		return null;
	}

	@Override
	public AreaExecution removeAreaList(List<Long> areaIdList) {
		return null;
	}
}
