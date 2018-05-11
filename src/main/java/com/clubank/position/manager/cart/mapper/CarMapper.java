package com.clubank.position.manager.cart.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.clubank.position.manager.cart.entity.Car;

public interface CarMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Car record);

    int insertSelective(Car record);

    Car selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Car record);

    int updateByPrimaryKey(Car record);
    
    /**
     * 查询某一类型下的车辆
     */
    List<Car> selectCarByCategoryId(Long categoryId);
    
    /**
     * 含模糊查询获得车辆列表个数
     * @param customerId 客户方id
     * @param categoryId 类型id
     * @param carNo 车辆编号
     * @param terminalNo 设备编号
     * @return
     */
    int selectCarTotal(@Param("customerId")Long customerId, @Param("categoryId")Long categoryId, 
    		@Param("carNo")String carNo, @Param("terminalNo")String terminalNo);
    
    /**
     * 含模糊查询获得车辆列表并分页
     * @param customerId 客户方id
     * @param categoryId 类型id
     * @param carNo 车辆编号
     * @param terminalNo 设备编号
     * @param startIndex 起始索引
     * @param pageSize 页容量
     * @return
     */
    List<Map<String, Object>> selectCarList(@Param("customerId")Long customerId, @Param("categoryId")Long categoryId, 
    		@Param("carNo")String carNo, @Param("terminalNo")String terminalNo, 
    		@Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);
    
    /**
     * 通过车辆编号查询客户方车辆
     * @param customerId 客户方id
     * @param carNo 车辆编号
     * @return
     */
    Car selectCarByCarNo(@Param("customerId")Long customerId, @Param("carNo")String carNo);
    
    /**
     * 通过终端编号查询车辆
     * @param terminalNo 终端编号
     * @return
     */
    Car selectCarByTerminalNo(String terminalNo);
    
    /**
     * 查询车辆信息
     * @param id 主键
     * @return
     */
    Map<String, Object> selectCarInfo(Long id);
}