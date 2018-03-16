package com.imooc.myo2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.Area;
import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.entity.ShopCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopDaoTest extends BaseTest {
	@Autowired
	private ShopDao shopDao;

	@Test
	public void testAInsertShop() throws Exception {
		Shop shop = new Shop();
		shop.setOwnerId(9L);
		Area area = new Area();
		area.setAreaId(5L);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(15L);
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
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
		System.out.println("【开始】");
	}

	@Test
	public void testBQueryByEmployeeId() throws Exception {
		long employeeId = 1;
		List<Shop> shopList = shopDao.queryByEmployeeId(employeeId);
		for (Shop shop : shopList) {
			System.out.println(shop);
		}
	}

	@Test
	public void testBQueryShopList() throws Exception {
//		Shop shop = new Shop();
//		List<Shop> shopList = shopDao.queryShopList(shop, 0, 2);
//		assertEquals(2, shopList.size());
//		int count = shopDao.queryShopCount(shop);
//		assertEquals(3, count);
//		shop.setShopName("花");
//		shopList = shopDao.queryShopList(shop, 0, 3);
//		assertEquals(2, shopList.size());
//		count = shopDao.queryShopCount(shop);
//		assertEquals(2, count);
//		shop.setShopId(1L);
//		shopList = shopDao.queryShopList(shop, 0, 3);
//		assertEquals(1, shopList.size());
//		count = shopDao.queryShopCount(shop);
//		assertEquals(1, count);
		Shop shopCondition= new Shop();
        shopCondition.setOwnerId(9L);
        List<Shop> list=shopDao.queryShopList(shopCondition,0,10);
        int num=shopDao.queryShopCount(shopCondition);
        assertEquals(5,num);

        ShopCategory sc=new ShopCategory();
        sc.setShopCategoryId(18L);
        shopCondition.setShopCategory(sc);
        shopCondition.setOwnerId(null);
        list=shopDao.queryShopList(shopCondition,0,10);
        assertEquals(3,list.size());

	}

	@Test
	public void testCQueryByShopId() throws Exception {
		long shopId = 1;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println(shop);
	}

	@Test
	public void testDUpdateShop() {
		long shopId = 30;
		Shop shop = shopDao.queryByShopId(shopId);
		Area area = new Area();
		area.setAreaId(6L);
		shop.setArea(area);
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(17L);
		shop.setShopCategory(shopCategory);
		shop.setShopName("三生三世");
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testEDeleteShopByName() throws Exception {
		String shopName = "mytest1";
		int effectedNum = shopDao.deleteShopByName(shopName);
		assertEquals(1, effectedNum);

	}

}
