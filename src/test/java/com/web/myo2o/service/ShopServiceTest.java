package com.web.myo2o.service;

import com.web.myo2o.BaseTest;
import com.web.myo2o.dto.ShopExecution;
import com.web.myo2o.entity.Area;
import com.web.myo2o.entity.Shop;
import com.web.myo2o.entity.ShopCategory;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;

	@Test
	public void testAddShop() throws Exception {

		Shop shop = new Shop();
		shop.setOwnerId(1L);
		Area area = new Area();
		area.setAreaId(1L);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shop.setShopName("mytest1");
		shop.setShopDesc("mytest1");
		shop.setShopAddr("testaddr1");
		shop.setPhone("13810524526");
		shop.setShopImg("test1");
		shop.setLongitude(1D);
		shop.setLatitude(1D);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("审核中");
		shop.setArea(area);
		shop.setShopCategory(sc);
//		java.io.File shopImg=new File("src/main/resources/images/2017060420315183203.png");
//		ShopExecution se = shopService.addShop(shop,shopImg);
//		assertEquals("mytest1", se.getShop().getShopName());
	}


	@Test
	public void testGetShopList() throws Exception {
		Shop shopCondition =new Shop();

		ShopCategory sc=new ShopCategory();
		sc.setShopCategoryId(18L);

		shopCondition.setShopCategory(sc);

		ShopExecution shopExecution=shopService.getShopList(shopCondition,2,2);

		System.out.println("列表数为"+shopExecution.getShopList().size());
		System.out.println("总数为"+shopExecution.getCount());
	}


	@Test
	public void testGetByEmployeeId() throws Exception {
		long employeeId = 1;
		ShopExecution shopExecution = shopService.getByEmployeeId(employeeId);
		List<Shop> shopList = shopExecution.getShopList();
		for (Shop shop : shopList) {
			System.out.println(shop);
		}
	}

	@Ignore
	@Test
	public void testGetByShopId() throws Exception {
		long shopId = 1;
		Shop shop = shopService.getByShopId(shopId);
		System.out.println(shop);
	}


}
