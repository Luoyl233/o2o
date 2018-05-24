package com.web.myo2o.controller.frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.web.myo2o.dto.ShopExecution;
import com.web.myo2o.entity.Area;
import com.web.myo2o.entity.Shop;
import com.web.myo2o.entity.ShopCategory;
import com.web.myo2o.service.AreaService;
import com.web.myo2o.service.ShopCategoryService;
import com.web.myo2o.service.ShopService;
import com.web.myo2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
	@Autowired
	private AreaService areaService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private ShopService shopService;

	@RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		if (parentId != -1) {
		    //如果parentId存在，则取出该一级ShopCategory下的二级ShopCategory列表
			try {
				ShopCategory shopCategoryCondition=new ShopCategory();
//				ShopCategory parent=new ShopCategory();
//				parent.setShopCategoryId(parentId);
				shopCategoryCondition.setParentId(parentId);

				shopCategoryList = shopCategoryService
						.getShopCategoryList(shopCategoryCondition);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		} else {
			try {
                //如果parentId不存在，则取出所有一级ShopCategory（用户在首页选择的是全部商店列表）
				shopCategoryList = shopCategoryService
                        .getShopCategoryList(null);
						//.getFirstLevelShopCategoryList();
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		}
		modelMap.put("shopCategoryList", shopCategoryList);
		List<Area> areaList = null;
		try {
		    //获取区域信息列表
			areaList = areaService.getAreaList();
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
			return modelMap;
		} catch (JsonParseException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		} catch (JsonMappingException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		} catch (IOException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}

	@RequestMapping(value = "/listshops", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		if ((pageIndex > -1) && (pageSize > -1)) {
		    //试着获取一级类别id
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			//试着获取特定二级类别id
			long shopCategoryId = HttpServletRequestUtil.getLong(request,
					"shopCategoryId");
		    //试着获取区域id
			long areaId = HttpServletRequestUtil.getLong(request, "areaId");
			String shopName = HttpServletRequestUtil.getString(request,
					"shopName");
			Shop shopCondition = compactShopCondition4Search(parentId,
					shopCategoryId, areaId, shopName);
			ShopExecution se = shopService.getShopList(shopCondition,
					pageIndex, pageSize);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex");
		}

		return modelMap;
	}

	private Shop compactShopCondition4Search(long parentId,
			long shopCategoryId, long areaId, String shopName) {
		Shop shopCondition = new Shop();
		if (parentId != -1L) {
		    //查询某个一级ShopCategory下面的所有二级ShopCategory里面的店铺列表
			ShopCategory parentCategory = new ShopCategory();
			parentCategory.setShopCategoryId(parentId);
			shopCondition.setParentCategory(parentCategory);
		}
		if (shopCategoryId != -1L) {
            //查询某个二级ShopCategory下面的店铺列表
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(shopCategory);
		}
		if (areaId != -1L) {
			Area area = new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}

		if (shopName != null) {
			shopCondition.setShopName(shopName);
		}
		//前端展示的店铺都是审核成功的店铺
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}
}
