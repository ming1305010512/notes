package com.lm.mybatis.mapper;

import com.lm.mybatis.entity.Orders;
import com.lm.mybatis.entity.OrdersCustom;
import com.lm.mybatis.entity.User;

import java.util.List;

/**
 * Created by 龙鸣 on 2017/8/16.
 */
public interface OrdersCustomMapper {

    //查询订单信息，关联用户信息
    public List<OrdersCustom> getOrdersCustom()throws Exception;

    //查询订单信息，关联用户信息，使用resultMap
    public List<Orders> getOrdersResultMap()throws Exception;

    //查询订单信息，关联用户信息及订单明细信息，使用resultMap
    public List<Orders> getOrdersAndOrderDetailsResultMap()throws Exception;

    //查询用户的商品
    public List<User> getUserItemResultMap()throws Exception;

    //查询订单的用户信息，使用延迟加载
    public List<Orders> getOrdersUserWithLazyLoding()throws Exception;
}
