package com.hjc.CardAdventure.pojo;

import com.hjc.CardAdventure.pojo.attribute.Attribute;

public interface Role {

    //获取角色名字
    String getRoleName();

    //获取生命值
    int getRoleBlood();

    //获取最大生命值
    int getRoleMaxBlood();

    //获取属性
    Attribute getRoleAttribute();
}
