package com.lm.mybatis.myBatisTest;

import com.lm.mybatis.entity.Orders;
import com.lm.mybatis.entity.OrdersCustom;
import com.lm.mybatis.entity.User;
import com.lm.mybatis.mapper.OrdersCustomMapper;
import com.lm.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import javax.xml.ws.WebServiceContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by 龙鸣 on 2017/8/16.
 */
public class OrdersCustomTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp(){
        String resourse="config/SqlMapConfig.xml";
        try {
            InputStream inputStream= Resources.getResourceAsStream(resourse);
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //测试订单和用户的关联查询，一对一
    @Test
    public void getOrdersCustom() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        OrdersCustomMapper ordersCustomMapper=sqlSession.getMapper(OrdersCustomMapper.class);
        List<OrdersCustom> list=ordersCustomMapper.getOrdersCustom();
        for(OrdersCustom ordersCustom:list){
            System.out.println(ordersCustom);
        }
    }

    //测试查询订单信息，关联用户信息，使用ResultMap
    @Test
    public void getOrdersWithResultMap() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        OrdersCustomMapper ordersCustomMapper=sqlSession.getMapper(OrdersCustomMapper.class);
        List<Orders> list=ordersCustomMapper.getOrdersResultMap();
        for(Orders orders:list){
            System.out.println(orders);
        }
    }

    //测试查询订单信息，关联用户信息及订单明细信息，使用ResultMap
    @Test
    public void getOrdersAndOrderDetailResultMap() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        OrdersCustomMapper ordersCustomMapper=sqlSession.getMapper(OrdersCustomMapper.class);
        List<Orders> list=ordersCustomMapper.getOrdersAndOrderDetailsResultMap();
        for(Orders orders:list){
            System.out.println(orders);
        }
    }

    //测试查询用户的商品，使用ResultMap
    @Test
    public void getUserItemResultMap() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        OrdersCustomMapper ordersCustomMapper=sqlSession.getMapper(OrdersCustomMapper.class);
        List<User> list=ordersCustomMapper.getUserItemResultMap();
        System.out.println(list);
    }

    //测试查询订单的用户信息，使用延迟加载
    //注意：使用延迟加载需要在SqlMapperConfig.xml中配置延迟加载
    @Test
    public void getOrdersUserWithlazyLoding() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        OrdersCustomMapper ordersCustomMapper=sqlSession.getMapper(OrdersCustomMapper.class);
        List<Orders> list=ordersCustomMapper.getOrdersUserWithLazyLoding();
//        System.out.println(list);
        for(Orders orders:list){
            //执行getUser方法时，将调用延迟加载的sql语句
            User user=orders.getUser();
            System.out.println(user);
        }
    }

    //测试一级缓存
    @Test
    public void testCache1() throws Exception {
        SqlSession sqlSession=sqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        User user1=userMapper.queryUser(6);
        System.out.println(user1);

        User user2=userMapper.queryUser(6);
        System.out.println(user2);
    }
}
