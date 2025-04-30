package com.hjc.CardAdventure.effect;

import com.hjc.CardAdventure.effect.basic.*;
import com.hjc.CardAdventure.effect.condition.ConditionTargetedEffect;
import com.hjc.CardAdventure.effect.condition.ManyEffect;
import com.hjc.CardAdventure.effect.condition.RecordEffect;
import com.hjc.CardAdventure.effect.opportunity.OpportunityDelete;
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
            //当前行动对象
            case "NOW" -> new NowDesignation(from, montage(strings));

            //基础效果
            //物理攻击效果#x#y(x为伤害数值，y为力量加成倍率)
            case "DAMAGE" -> new PhysicalDamage(from, montage(strings), to);
            //法术效果#x#y(x为法术种类，y为数值)
            case "MAGIC" -> new MagicEffect(from, montage(strings), to);
            //法术伤害#x#y(x为法术种类,y为数值)
            case "MAGIC_DAMAGE" -> new MagicDamage(from, montage(strings), to);
            //失去血量效果#x(x为失去生命的数值)
            case "LOSS_BLOOD" -> new LossBlood(from, montage(strings), to);
            //回复效果#x(x为回复血量)
            case "RESTORE" -> new RestoreEffect(from, montage(strings), to);
            //物理伤害增幅#V_A/V_D#x(x为增幅数值)
            case "DAMAGE_V_ADD" -> new PhyDamageValueAdd(from, montage(strings), to);
            //物理伤害倍率变化#FROM_M_ADD/TO_M_ADD#A/D#x(x为倍率*100)
            case "DAMAGE_M_ADD" -> new PhyDamageMagnificationAdd(from, montage(strings), to);
            //获得护盾#x(x为获得的护盾)
            case "ARMOR_GET" -> new ArmorGet(from, montage(strings), to);
            //设置护盾值
            case "ARMOR_SET" -> new ArmorSet(from, montage(strings), to);
            //护盾添加倍率变化#x(x为倍率值)
            case "ARMOR_M_ADD" -> new ArmorMagnificationAdd(from, montage(strings), to);
            //属性上升#type#x(type为上升的属性--P力量，I智力，D防御，A敏捷，U纯洁，S速度，x为上升数值，可为负)
            case "ATTRIBUTE_UP" -> new AttributeUp(from, montage(strings), to);
            //属性下降
            case "ATTRIBUTE_DOWN" -> new AttributeDown(from, montage(strings), to);
            //回合护盾是否消失设置效果
            case "ARMOR_DISAPPEAR" -> new ArmorDisappearSet(from, montage(strings), to);
            //卡牌添加效果
            case "ADD_CARD" -> new AddCard(from, montage(strings));
            //时机触发效果#effect
            case "OPPORTUNITY" -> new OpportunityEffect(from, montage(strings), to);
            //结束触发时机#effect
            case "OPPORTUNITY_END" -> new OpportunityEndEffect(from, montage(strings), to);
            //时机效果删除
            case "OPPORTUNITY_DELETE" -> new OpportunityDelete(from, montage(strings), to);

            //带目标条件效果#x#effect(x为指定条件--ONE，TWO，THREE。。。)
            case "CONDITION_TARGETED" -> new ConditionTargetedEffect(from, montage(strings), to);
            //记录器效果#target#x#effect(记录角色，x为记录目标)
            case "RECORD" -> new RecordEffect(from, montage(strings), to);
            //多段效果
            case "MANY" -> new ManyEffect(from, montage(strings), to);

            //玩家特有
            //抽牌效果#x(x为抽牌数)
            case "DRAW" -> new DrawEffect(from, montage(strings));
            //牌堆卡牌转移效果#fromCards#toCards#operation
            case "CARDS_TO_CARDS" -> new CardsToCards(from, montage(strings));
            //复用效果#0#1#2...
            case "REUSE" -> new Reuse(from, montage(strings));
            //寻回效果#cards#PRODUCE/NO_PRODUCE#cardName/x(x为寻回张数)
            case "FIND_CARD" -> new FindCard(from, montage(strings));
            //使用牌后置入牌堆效果#cards(cards为指定牌堆)
            case "USE_TO" -> new CardUseEnd(from, montage(strings));
            //减少出牌数效果#x(x为减少出牌数的数值)
            case "HEAVY" -> new ReduceProduce(from, montage(strings));
            //获得出牌效果#X
            case "GET_PRODUCE" -> new GetProduceNum(from, montage(strings));
            //回合结束
            case "ACTION_OVER" -> new PlayerActionOver(from, montage(strings));

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
            case "碎甲" -> "直至下x个自身回合结束,获得的护盾值减少50%（x为碎甲层数）";
            case "蓄势" -> "使下x次造成的物理伤害翻倍（x为蓄势层数）";
            case "燃烧" -> "自身回合开始,受到x点火焰伤害（x为燃烧层数）,触发后消失";
            case "回抽" -> "此牌使用后置入抽牌堆";
            case "消耗" -> "此牌使用后置入消耗牌堆";
            case "火焰保护" -> "受到物理伤害时,[A]获得x层燃烧（x为火焰保护层数 + 智力值）";
            case "伤害增幅" -> "使下一次造成的物理伤害增加x点（x为伤害增幅层数）";
            case "伤害减免" -> "使下一次受到的物理伤害减少x点（x为伤害减免层数）";
            case "自燃" -> "自身回合结束时,[F]失去x点生命,[A]获得y层燃烧（x为自燃层数,y = 3x+智力值）";
            case "轻盈" -> "此牌使用后不消耗出牌数";
            case "灵敏" -> "自身回合开始,抽x张牌（x为灵敏层数）";
            case "衰竭" -> "自身回合开始,失去x点全属性（x为衰竭层数）";
            case "复用" -> "再次执行此卡效果";
            case "寻回/抽" -> "从抽牌堆找到此牌并选择是否打出";
            case "血盾" -> "失去生命时,获得x点护盾（x为血盾层数+防御值）";
            case "固守" -> "自身回合开始时,护盾不再消失";
            case "淬火" -> "造成物理伤害后,给予伤害目标x层\"燃烧\"(x为淬火层数+智力值)";
            case "终止" -> "结束当前自身回合";
            case "血战" -> "失去生命时,获得x层\"伤害增幅\"(x为血战层数)";
            case "寄生*红" -> "受到物理伤害后,[N]获得1层易伤";
            case "寄生*绿" -> "受到物理伤害后,[N]获得1层虚弱";
            case "恶心" -> "状态牌，消耗";
            default -> "";
        };
    }
}
