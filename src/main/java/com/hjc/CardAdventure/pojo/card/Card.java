package com.hjc.CardAdventure.pojo.card;

import com.hjc.CardAdventure.pojo.attribute.Attribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    //卡牌英文地址
    private String cardAddress;
    //卡牌名字
    private String cardName;
    //卡牌属性
    private Attribute attribute;
    //卡牌品质
    private CardQuality cardQuality;
    //卡牌颜色
    private String colorS;
    //卡牌效果序列
    private ArrayList<String> cardEffects;
    //卡牌指定目标类型
    private TargetType targetType;
    //卡牌有效效果数量（用于复制）
    private int effectiveEffect;
}
