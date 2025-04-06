package com.hjc.CardAdventure.effect;

import com.hjc.CardAdventure.effect.basic.DrawEffect;
import com.hjc.CardAdventure.effect.basic.PhysicalDamage;
import com.hjc.CardAdventure.effect.target.PlayerDesignation;
import com.hjc.CardAdventure.effect.target.TargetDesignation;
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
            //指定玩家效果
            case "PLAYER" -> new PlayerDesignation(from, montage(strings));
            //指定目标效果
            case "TARGET" -> new TargetDesignation(from, montage(strings));
            //物理攻击效果
            case "DAMAGE" -> new PhysicalDamage(from, montage(strings), to);
            //抽牌效果
            case "DRAW" -> new DrawEffect(from, montage(strings));
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
}
