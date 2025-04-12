package com.hjc.CardAdventure.effect;

import com.hjc.CardAdventure.effect.basic.*;
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

            //基础效果
            //物理攻击效果#x#y(x为伤害数值，y为力量加成倍率)
            case "DAMAGE" -> new PhysicalDamage(from, montage(strings), to);

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
}
