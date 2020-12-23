
package com.bootx.dao;

import com.bootx.entity.CompanyBank;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface CompanyBankDao extends BaseDao<CompanyBank, Long> {

    CompanyBank get();

    void clearDefault();

    void clearDefault(CompanyBank pCompanyBank);
}