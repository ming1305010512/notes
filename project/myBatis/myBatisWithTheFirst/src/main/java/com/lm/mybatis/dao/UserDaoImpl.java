package com.lm.mybatis.dao;

import com.lm.mybatis.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Created by 龙鸣 on 2017/8/14.
 */
public class UserDaoImpl implements UserDao {

    private SqlSessionFactory sqlSessionFactory;

    public   UserDaoImpl(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory=sqlSessionFactory;
    }

    public User getUserById(int id) throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        User user=sqlSession.selectOne("test.queryUser",5);
        sqlSession.close();
        return user;
    }
}
