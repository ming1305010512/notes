package com.lm.mybatis.entity;

import java.util.Date;

/**
 * Created by 龙鸣 on 2017/8/16.
 */
public class OrdersCustom extends Orders {

    private String name;
    private String address;
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


}
