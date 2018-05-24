package com.web.myo2o.service.impl;

import com.web.myo2o.BaseTest;
import com.web.myo2o.entity.Area;
import com.web.myo2o.service.AreaService;
import com.web.myo2o.service.CacheService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author lw
 */

public class AreaServiceImplTest extends BaseTest{
    @Autowired
    private AreaService areaService;
    @Autowired
    private CacheService cacheService;
    @Test
    public void getAreaList() throws Exception {
        List<Area> list=areaService.getAreaList();
        System.out.println("【开始】");
        assertEquals("区域1",list.get(0).getAreaName());
        cacheService.removeFromCache(areaService.AREALISTKEY);
        list=areaService.getAreaList();
    }

}