package com.lm.mybatis.myBatisTest;

import com.lm.mybatis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by 龙鸣 on 2017/8/11.
 */
public class QueryUser{

    //通过id查询用户
    @Test
    public  void getUser() {
        //资源文件
        String resource= "config/SqlMapConfig.xml";
        //得到配置文件流
        try {
            InputStream inputStream= Resources.getResourceAsStream(resource);
            //创建会话工厂
//            SqlSessionFactoryBuilder sqlSessionFactoryBuilder=new SqlSessionFactoryBuilder();
//            SqlSessionFactory sqlSessionFactory=sqlSessionFactoryBuilder.build(inputStream);
            SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
            //创建sqlSession
            SqlSession sqlSession=sqlSessionFactory.openSession();
            User user=sqlSession.selectOne("test.queryUser",1);

            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //通过名字查询user

    @Test
    public void findByNameTest(){
        String resource= "config/SqlMapConfig.xml";
        //得到配置文件流
        try {
            InputStream inputStream= Resources.getResourceAsStream(resource);
            //创建会话工厂
//            SqlSessionFactoryBuilder sqlSessionFactoryBuilder=new SqlSessionFactoryBuilder();
//            SqlSessionFactory sqlSessionFactory=sqlSessionFactoryBuilder.build(inputStream);
            SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
            //创建sqlSession
            SqlSession sqlSession=sqlSessionFactory.openSession();
            List<User> list=sqlSession.selectList("test.queryByName","xiao");

            for (User user:list) {
                System.out.println(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //插入一条用户记录
    @Test
    public  void insertUser(){
        String resource="config/SqlMapConfig.xml";
        try {
            InputStream inputStream=Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession=sqlSessionFactory.openSession();
            User user=new User();
            user.setName("xiaohong");
            user.setPassword("123456");
            user.setBirthday(new Date());
            user.setAddress("上海");
            sqlSession.insert("test.insertUser",user);
            sqlSession.commit();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除一条用户记录
    @Test
    public void deleteUser(){

        String resources="config/SqlMapConfig.xml";
        try {
            InputStream inputStream=Resources.getResourceAsStream(resources);
            SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession=sqlSessionFactory.openSession();
            sqlSession.delete("test.deleteUser",3);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //更新一条用户
    @Test
    public void updateUser(){
        String resource="config/SqlMapConfig.xml";

            InputStream inputStream=QueryUser.class.getClassLoader().getResourceAsStream(resource);

//            InputStream inputStream=Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession=sqlSessionFactory.openSession();
            User user=new User();
            user.setId(5);
            user.setName("xiaohong");
            user.setPassword("123456");
            user.setBirthday(new Date());
            user.setAddress("广州");
            sqlSession.update("test.updateUser",user);
            sqlSession.commit();
            sqlSession.close();


    }

}
