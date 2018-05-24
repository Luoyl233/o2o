package com.web.myo2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.web.myo2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
	 * 通过employee id 查询店铺
	 * 
	 * @param  shopId
	 * @return List<ProductCategory>
	 */
	List<ProductCategory> queryByShopId(long shopId);

	/**
	 * 新增商品类别
	 * 
	 * @param
	 *
	 * @return effectedNum
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);

	/**
	 * 删除商品类别（初版，即只支持删除尚且没有发布商品的商品类别）
	 * 
	 * @param productCategoryId
	 * @param shopId
	 * @return effectedNum
	 */
	int deleteProductCategory(
            @Param("productCategoryId") long productCategoryId,
            @Param("shopId") long shopId);
}
