package com.clubank.position.manager.cart.mapper;

import java.util.List;

import com.clubank.position.manager.cart.entity.CarCategory;

public interface CarCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CarCategory record);

    int insertSelective(CarCategory record);

    CarCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CarCategory record);

    int updateByPrimaryKey(CarCategory record);
    
    /**
     * 查询客户方车辆类型列表
     * @param customerId 客户方主键
     * @return
     */
    List<CarCategory> selectCarCategoryList(Long customerId);
    
    /**
     * 通过类型名称获得类型信息
     * @param name 类型名称
     * @return
     */
    CarCategory selectCarCategoryByName(String name);
}