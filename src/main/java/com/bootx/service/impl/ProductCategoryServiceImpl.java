/*
 * Copyright 2005-2017 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.bootx.service.impl;

import com.bootx.dao.ProductCategoryDao;
import com.bootx.elasticsearch.entity.ProductCategoryTree;
import com.bootx.entity.ProductCategory;
import com.bootx.service.ProductCategoryService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service - 商品分类
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Service
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategory, Long> implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findRoots() {
		return productCategoryDao.findRoots(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findRoots(Integer count) {
		return productCategoryDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findRoots(Integer count, boolean useCache) {
		return productCategoryDao.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findParents(ProductCategory productCategory, boolean recursive, Integer count) {
		return productCategoryDao.findParents(productCategory, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findParents(Long productCategoryId, boolean recursive, Integer count, boolean useCache) {
		ProductCategory productCategory = productCategoryDao.find(productCategoryId);
		if (productCategoryId != null && productCategory == null) {
			return Collections.emptyList();
		}
		return productCategoryDao.findParents(productCategory, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findTree() {
		return productCategoryDao.findChildren(null, true, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findChildren(ProductCategory productCategory, boolean recursive, Integer count) {
		return productCategoryDao.findChildren(productCategory, recursive, count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategory> findChildren(Long productCategoryId, boolean recursive, Integer count, boolean useCache) {
		ProductCategory productCategory = productCategoryDao.find(productCategoryId);
		if (productCategoryId != null && productCategory == null) {
			return Collections.emptyList();
		}
		return productCategoryDao.findChildren(productCategory, recursive, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory" }, allEntries = true)
	public ProductCategory save(ProductCategory productCategory) {
		Assert.notNull(productCategory,"");
		System.out.println(productCategory.getName()+":"+productCategory.getOtherUrl());
		setValue(productCategory);
		return super.save(productCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory" }, allEntries = true)
	public ProductCategory update(ProductCategory productCategory) {
		Assert.notNull(productCategory,"");

		setValue(productCategory);
		for (ProductCategory children : productCategoryDao.findChildren(productCategory, true, null)) {
			setValue(children);
		}
		return super.update(productCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory" }, allEntries = true)
	public ProductCategory update(ProductCategory productCategory, String... ignoreProperties) {
		return super.update(productCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "product", "productCategory" }, allEntries = true)
	public void delete(ProductCategory productCategory) {
		super.delete(productCategory);
	}

	/**
	 * 设置值
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	private void setValue(ProductCategory productCategory) {
		if (productCategory == null) {
			return;
		}
		ProductCategory parent = productCategory.getParent();
		if (parent != null) {
			productCategory.setTreePath(parent.getTreePath() + parent.getId() + ProductCategory.TREE_PATH_SEPARATOR);
		} else {
			productCategory.setTreePath(ProductCategory.TREE_PATH_SEPARATOR);
		}
		productCategory.setGrade(productCategory.getParentIds().length);
	}

	@Override
	public ProductCategory findByOtherId(String otherId) {
		return productCategoryDao.find("otherId",otherId);
	}

    @Override
    public List<ProductCategoryTree> findTree1(String pluginId) {
		String result = redisTemplate.opsForValue().get(pluginId);
		if(StringUtils.isBlank(result)){
			List<ProductCategoryTree> productCategoryTrees = findTree(pluginId,null);
			redisTemplate.opsForValue().set(pluginId,JsonUtils.toJson(productCategoryTrees));
			return productCategoryTrees;
		}
		return JsonUtils.toObject(result, new TypeReference<List<ProductCategoryTree>>() {
		});

	}

	private List<ProductCategoryTree> findTree(String pluginId, Long parenId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select parent.id,parent.name,(select count(child.id) from `bootx-pdd`.productcategory as child where child.parent_id=parent.id and child.pluginId='").append(pluginId).append("') childrenCount from `bootx-pdd`.productcategory as parent");
		sb.append(" where parent.pluginId='").append(pluginId).append("' ");
		if(parenId==null){
			sb.append(" and parent.parent_id is null ");
		}else{
			sb.append(" and parent.parent_id=").append(parenId);
		}
		sb.append(" order by parent.id asc;");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());

		return list.stream().map(item->{
			ProductCategoryTree productCategoryTree = new ProductCategoryTree();
			productCategoryTree.setId(Long.valueOf(item.get("id")+""));
			productCategoryTree.setName(item.get("name")+"");
			if(Integer.parseInt(item.get("childrenCount")+"")>0){
				productCategoryTree.setChildren(findTree(pluginId,productCategoryTree.getId()));
			}
			return productCategoryTree;
		}).collect(Collectors.toList());


	}

	@Override
	public List<ProductCategory> findRoots(String pluginId) {
		return productCategoryDao.findRoots(pluginId,null);
	}

	@Override
	public List<ProductCategory> findRoots(String pluginId, Integer count) {
		return productCategoryDao.findRoots(pluginId,count);
	}

	@Override
	public List<ProductCategory> findRoots(String pluginId, Integer count, boolean useCache) {
		return productCategoryDao.findRoots(pluginId,count);
	}

	@Override
	public List<ProductCategory> findParents(String pluginId, ProductCategory productCategory, boolean recursive, Integer count) {
		return productCategoryDao.findParents(pluginId,productCategory,recursive,count);
	}

	@Override
	public List<ProductCategory> findParents(String pluginId, Long productCategoryId, boolean recursive, Integer count, boolean useCache) {
		ProductCategory productCategory = productCategoryDao.find(productCategoryId);
		if (productCategoryId != null && productCategory == null) {
			return Collections.emptyList();
		}
		return productCategoryDao.findParents(pluginId,productCategory, recursive, count);
	}

	@Override
	public List<ProductCategory> findTree(String pluginId) {
		return productCategoryDao.findChildren(pluginId,null, true, null);
	}

	@Override
	public List<ProductCategory> findChildren(String pluginId, ProductCategory productCategory, boolean recursive, Integer count) {
		return productCategoryDao.findChildren(pluginId,productCategory, recursive, count);
	}

	@Override
	public List<ProductCategory> findChildren(String pluginId, Long productCategoryId, boolean recursive, Integer count, boolean useCache) {
		ProductCategory productCategory = productCategoryDao.find(productCategoryId);
		if (productCategoryId != null && productCategory == null) {
			return Collections.emptyList();
		}
		return productCategoryDao.findChildren(pluginId,productCategory, recursive, count);
	}
}