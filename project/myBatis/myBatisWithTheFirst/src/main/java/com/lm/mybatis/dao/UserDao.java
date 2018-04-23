package com.lm.mybatis.dao;

import com.lm.mybatis.entity.User;

/**
 * Created by 龙鸣 on 2017/8/14.
 */
public interface UserDao {

    //查询用户
    public User getUserById(int id) throws Exception;
}
