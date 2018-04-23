package com.lm.mybatis.mapper;

import com.lm.mybatis.entity.User;
import com.lm.mybatis.entity.UserCustom;
import com.lm.mybatis.entity.UserQueryVo;

import java.util.List;

/**
 * Created by 龙鸣 on 2017/8/14.
 */
public interface UserMapper {
    //根据id查询用户
    public User queryUser(int id) throws Exception;

    //用户的综合查询
    public List<UserCustom> queryUserList(UserQueryVo userQueryVo) throws Exception;

    //根据id查询用户，使用resultMap作为输出映射
    public User queryUserById(int id) throws Exception;
}
