package com.hjc.CardAdventure.pojo.attribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attribute {
    //力量（1点力量增加一点物理伤害）
    private int power;
    //智力（1点智力增加一点法术效果）
    private int intelligence;
    //防御（1点防御增加一点护甲，每一点护甲）
    private int defense;
    //敏捷（前3点敏捷每回合每点加一抽，之后每3点加一抽），（每5点敏捷保留一牌）
    private int agility;
    //纯洁（前3点纯洁每点加一回血，之后每3点加一回血）
    private int purity;
    //速度（速度快一方优先出手）
    private int speed;


    //属性转字符串
    public String displayAttribute() {
        StringBuilder sb = new StringBuilder("力量：").append(power).append(addSpace(power));
        sb.append("智力：").append(intelligence).append("\n");
        sb.append("防御：").append(defense).append(addSpace(defense));
        sb.append("敏捷：").append(agility).append("\n");
        sb.append("纯洁：").append(purity).append(addSpace(purity));
        sb.append("速度：").append(speed);
        return sb.toString();
    }

    //加空格
    private String addSpace(int num) {
        if (num < 10) return "       ";
        else if (num < 100) return "     ";
        else return "   ";
    }

    //属性克隆
    public static void cloneAttribute(Attribute from, Attribute to) {
        to.power = from.power;
        to.intelligence = from.intelligence;
        to.defense = from.defense;
        to.agility = from.agility;
        to.purity = from.purity;
        to.speed = from.speed;
    }
}
