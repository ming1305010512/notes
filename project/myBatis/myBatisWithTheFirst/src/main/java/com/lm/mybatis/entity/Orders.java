package com.lm.mybatis.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by 龙鸣 on 2017/8/16.
 */
public class Orders {

    private int id;
    private int userId;
    private Date createTime;
    private String number;
    private String note;

    //用于关联用户
    private User user;

    //订单明细
    private List<OrderDetails> orderDetails;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", number='" + number + '\'' +
                ", note='" + note + '\'' +
                ", user=" + user +
                '}';
    }
}
