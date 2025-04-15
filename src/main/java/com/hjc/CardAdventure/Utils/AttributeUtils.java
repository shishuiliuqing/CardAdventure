package com.hjc.CardAdventure.Utils;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.enemy.Enemy;

import java.util.HashMap;

import static com.hjc.CardAdventure.Global.PLAYER.player;
import static com.hjc.CardAdventure.pojo.BattleInformation.ENEMIES;
import static com.hjc.CardAdventure.pojo.BattleInformation.isBattle;

public class AttributeUtils {
    private AttributeUtils() {
    }

    //计算器初始化
    public static void initAttributeUtils() {
        //初始化发动者伤害增幅器
        FROM_ADD_DAMAGE.clear();
        //初始化倍率增幅器
        FROM_MAGNIFICATION.clear();
        TO_MAGNIFICATION.clear();
        //初始化护盾增加倍率器
        ARMOR_ADD_MAGNIFICATION.clear();

        //增幅器添加玩家单位
        FROM_ADD_DAMAGE.put(player, 0);
        FROM_MAGNIFICATION.put(player, 1.0);
        TO_MAGNIFICATION.put(player, 1.0);
        ARMOR_ADD_MAGNIFICATION.put(player, 1.0);

        //增幅器添加敌人单位
        for (Enemy enemy : ENEMIES) {
            FROM_ADD_DAMAGE.put(enemy, 0);
            FROM_MAGNIFICATION.put(enemy, 1.0);
            TO_MAGNIFICATION.put(enemy, 1.0);
            ARMOR_ADD_MAGNIFICATION.put(enemy, 1.0);
        }
    }

    //发动者伤害增幅器
    public static final HashMap<Role, Integer> FROM_ADD_DAMAGE = new HashMap<>();
    //发动者倍率增幅器
    public static final HashMap<Role, Double> FROM_MAGNIFICATION = new HashMap<>();
    //目标倍率增幅器
    public static final HashMap<Role, Double> TO_MAGNIFICATION = new HashMap<>();

    //计算物理伤害数值
    public static int mathPhyDamage(Role from, Role to, int value, int magnification) {
        //非战斗状态，显示初始数值
        if (!BattleInformation.isBattle) return Math.max(value, 0);
        //发动者不为null
        if (from != null) {
            //添加力量加成
            value += from.getRoleAttribute().getPower() * magnification;
            //添加伤害增幅器加成
            value += FROM_ADD_DAMAGE.get(from);
            //倍率加成
            value = (int) (value * FROM_MAGNIFICATION.get(from));
        }

        //目标不为null
        if (to != null) {
            //倍率加成
            value = (int) (value * TO_MAGNIFICATION.get(to));
        }

        return Math.max(value, 0);
    }

    //护盾倍率器
    public static final HashMap<Role, Double> ARMOR_ADD_MAGNIFICATION = new HashMap<>();

    //计算增加的护盾值
    public static int mathArmor(Role role, int value) {
        if (!isBattle) return value;
        if (role != null)
            return (int) ((value + role.getRoleAttribute().getDefense()) * ARMOR_ADD_MAGNIFICATION.get(role));
        return value;
    }

    //计算人物抽牌数
    public static int mathDraw() {
        int agility = player.getAttribute().getAgility();
        if (agility <= 0) return 0;
        if (agility <= 3) return agility;
        return 3 + (agility - 3) / 3;
    }
}
