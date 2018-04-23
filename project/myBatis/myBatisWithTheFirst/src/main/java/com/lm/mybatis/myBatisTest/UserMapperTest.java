package com.lm.mybatis.myBatisTest;

import com.lm.mybatis.entity.User;
import com.lm.mybatis.entity.UserCustom;
import com.lm.mybatis.entity.UserQueryVo;
import com.lm.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 龙鸣 on 2017/8/14.
 */
public class UserMapperTest {

    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void setup() throws IOException {
        String resource="config/SqlMapConfig.xml";
        InputStream inputStream= Resources.getResourceAsStream(resource);
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void getUserById(){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        try {
           User user= userMapper.queryUser(5);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //用户的综合查询
    @Test
    public void getUserList() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        UserQueryVo userQueryVo=new UserQueryVo();
        UserCustom userCustom=new UserCustom();
        userCustom.setAddress("娄底");
        //在使用动态sql时，测试name为空的情景
//        userCustom.setName("龙鸣");

        //使用foreach是测试的代码
        List listIds=new ArrayList();
        listIds.add(1);
        listIds.add(2);
        listIds.add(5);
        userQueryVo.setIds(listIds);
        userQueryVo.setUserCustom(userCustom);
        List<UserCustom> list=userMapper.queryUserList(userQueryVo);
        System.out.println(list);
    }

    //根据id查询用户，使用resultMap作为输出映射
    @Test
    public void getUserByIdWithResultMap() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        User user=userMapper.queryUserById(5);
        System.out.println(user);
    }
}
