package com.clubank.position.manager.customer.mapper;

import com.clubank.position.manager.customer.entity.CustomerUser;

public interface CustomerUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerUser record);

    int insertSelective(CustomerUser record);

    CustomerUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerUser record);

    int updateByPrimaryKey(CustomerUser record);
    
    /**
     * 通过账号查询用户
     * @param account 账号
     * @return
     */
    CustomerUser selectByAccount(String account);
}