package com.hjc.CardAdventure.effect;

import com.hjc.CardAdventure.effect.basic.*;
import com.hjc.CardAdventure.effect.condition.ConditionTargetedEffect;
import com.hjc.CardAdventure.effect.opportunity.OpportunityEffect;
import com.hjc.CardAdventure.effect.opportunity.OpportunityEndEffect;
import com.hjc.CardAdventure.effect.player.*;
import com.hjc.CardAdventure.effect.target.*;
import com.hjc.CardAdventure.effect.enemy.*;
import com.hjc.CardAdventure.pojo.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public abstract class Effect {
    //分割符
    public static final String NEW_LINE = "\n------------------------------------\n";
    //发动来源
    private final Role from;
    //下一效果代码
    private final String effect;

    //效果执行
    public abstract void action();

    //效果描述
    @Override
    public abstract String toString();

    //效果解析器
    public static Effect parse(Role from, String effect, Role to) {
        //字段为空，返回null
        if (effect == null || effect.isEmpty()) return null;

        //分割
        ArrayList<String> strings = cutEffect(effect);
        //获取操作符
        String operation = getFirst(strings);
        return switch (operation) {
            //目标指定效果
            //指定玩家效果#effect
            case "PLAYER" -> new PlayerDesignation(from, montage(strings));
            //指定目标效果#effect
            case "TARGET" -> new TargetDesignation(from, montage(strings));
            //自身目标效果#effect
            case "FROM" -> new FromDesignation(from, montage(strings));
            //群体效果#effect
            case "ALL" -> new AllDesignation(from, montage(strings));

            //基础效果
            //物理攻击效果#x#y(x为伤害数值，y为力量加成倍率)
            case "DAMAGE" -> new PhysicalDamage(from, montage(strings), to);
            //法术效果#x#y(x为法术种类，y为数值)
            case "MAGIC" -> new MagicEffect(from, montage(strings), to);
            //法术伤害#x#y(x为法术种类,y为数值)
            case "MAGIC_DAMAGE" -> new MagicDamage(from, montage(strings), to);
            //失去血量效果#x(x为失去生命的数值)
            case "LOSS_BLOOD" -> new LossBlood(from, montage(strings), to);
            //物理伤害倍率变化#FROM_M_ADD/TO_M_ADD#A/D#x(x为倍率*100)
            case "DAMAGE_M_ADD" -> new PhyDamageMagnificationAdd(from, montage(strings), to);
            //获得护盾#x(x为获得的护盾)
            case "ARMOR_GET" -> new ArmorGet(from, montage(strings), to);
            //属性上升#type#x(type为上升的属性--P力量，I智力，D防御，A敏捷，U纯洁，S速度，x为上升数值，可为负)
            case "ATTRIBUTE_UP" -> new AttributeUp(from, montage(strings), to);
            //属性下降
            case "ATTRIBUTE_DOWN" -> new AttributeDown(from, montage(strings), to);
            //时机触发效果#effect
            case "OPPORTUNITY" -> new OpportunityEffect(from, montage(strings), to);
            //结束触发时机#effect
            case "OPPORTUNITY_END" -> new OpportunityEndEffect(from, montage(strings), to);

            //带目标条件效果#x#effect(x为指定条件--ONE，TWO，THREE。。。)
            case "CONDITION_TARGETED" -> new ConditionTargetedEffect(from, montage(strings), to);

            //玩家特有
            //抽牌效果#x(x为抽牌数)
            case "DRAW" -> new DrawEffect(from, montage(strings));
            //使用牌后置入牌堆效果#cards(cards为指定牌堆)
            case "USE_TO" -> new CardUseEnd(from, montage(strings));
            //减少出牌数效果#x(x为减少出牌数的数值)
            case "HEAVY" -> new ReduceProduce(from, montage(strings));

            //敌人特有
            //删除意图效果
            case "INTENTION_DELETE" -> new IntentionDelete(from, montage(strings));
            //意图生成效果
            case "INTENTION_GENERATE" -> new IntentionGenerate(from, montage(strings));

            //暂停效果#x(x为暂停的时间，999为直接暂停)
            case "PAUSE" -> new PauseEffect(from, montage(strings));
            default -> null;
        };
    }

    //字符串转int
    public static int changeToInt(String string) {
        return Integer.parseInt(string);
    }

    //拼接效果序列数组
    public static String montage(ArrayList<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            sb.append(strings.get(i));
            //非结尾，添加
            if (i != strings.size() - 1) sb.append("#");
        }
        return sb.toString();
    }

    //取效果序列第一个元素
    public static String getFirst(ArrayList<String> strings) {
        String s = strings.get(0);
        strings.remove(0);
        return s;
    }

    //"#"分割符
    public static ArrayList<String> cutEffect(String effect) {
        String[] strings = effect.split("#");
        return new ArrayList<>(List.of(strings));
    }

    //继续执行效果
    public static void continueAction(Role from, String effect, Role to) {
        Effect next = parse(from, effect, to);
        if (next == null) return;
        next.action();
    }

    //拼接接下来的效果解析
    public static String getNextEffectString(Role from, String effect, Role to, String text) {
        Effect next = parse(from, effect, to);
        if (next == null) return "";
        if (next.toString().isEmpty()) return "";
        return text + next;
    }

    //效果详情解析
    public static String effectDetail(String sketch) {
        return switch (sketch) {
            case "虚弱" -> "使下x次造成的物理伤害降低25%（x为虚弱层数）";
            case "易伤" -> "使下x次受到的物理伤害增加50%（x为易伤层数）";
            case "蓄势" -> "使下x次造成的物理伤害翻倍（x为蓄势层数）";
            case "燃烧" -> "自身回合开始,受到x点火焰伤害（x为燃烧层数）";
            default -> "";
        };
    }
}
