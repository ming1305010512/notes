package com.lm.mybatis.myBatisTest;

import com.lm.mybatis.dao.UserDao;
import com.lm.mybatis.dao.UserDaoImpl;
import com.lm.mybatis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 龙鸣 on 2017/8/14.
 */
public class UserDaoImplTest {

    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void setup() throws IOException {
        String resource="config/SqlMapConfig.xml";
        InputStream inputStream= Resources.getResourceAsStream(resource);
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void getUserById(){
        UserDao userDao=new UserDaoImpl(sqlSessionFactory);
        try {
            User user=userDao.getUserById(5);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
