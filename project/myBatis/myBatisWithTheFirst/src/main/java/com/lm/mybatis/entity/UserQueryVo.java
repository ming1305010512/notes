package com.lm.mybatis.entity;

import java.util.List;

/**
 * Created by 龙鸣 on 2017/8/14.
 */
public class UserQueryVo {

    private List ids;

    private UserCustom userCustom;

    public UserCustom getUserCustom() {
        return userCustom;
    }

    public void setUserCustom(UserCustom userCustom) {
        this.userCustom = userCustom;
    }

    public List getIds() {
        return ids;
    }

    public void setIds(List ids) {
        this.ids = ids;
    }
}
