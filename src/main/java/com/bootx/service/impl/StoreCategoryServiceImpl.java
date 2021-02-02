
package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.StoreCategoryDao;
import com.bootx.entity.Member;
import com.bootx.entity.StoreCategory;
import com.bootx.service.StoreCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class StoreCategoryServiceImpl extends BaseServiceImpl<StoreCategory, Long> implements StoreCategoryService {

	@Resource
	private StoreCategoryDao storeCategoryDao;

	@Override
	public List<StoreCategory> findList(Member member) {
		return storeCategoryDao.findList(member);
	}

	@Override
	public Page<StoreCategory> findPage(Pageable pageable, Member member) {
		return storeCategoryDao.findPage(pageable,member);
	}

    @Override
    public StoreCategory findDefault(Member member) {
		StoreCategory storeCategory = storeCategoryDao.findDefault(member);
		if(storeCategory==null){
			storeCategory = new StoreCategory();
			storeCategory.setIsDefault(true);
			storeCategory.setName("默认");
			storeCategory.setMember(member);
			return super.save(storeCategory);
		}
		return storeCategory;
    }

}