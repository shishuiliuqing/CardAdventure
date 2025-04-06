package com.hjc.CardAdventure;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.component.card.CardComponent;
import com.hjc.CardAdventure.configuration.PlayerCards;
import com.hjc.CardAdventure.configuration.SeasonMonsterPool;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.player.Player;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;

//全局变量
public class Global {
    //私有化类，不允许创建
    private Global() {
    }

    //游戏设置
    public static class GAME_SETTING {
        //游戏宽
        public static final int APP_WITH = 1900;
        //游戏高
        public static final int APP_HEIGHT = 980;
    }

    //营地图片地址
    public static final String CAMP_ADDRESS = "camp/";
    //局内信息图片地址
    public static final String INFORMATION_ADDRESS = "information/";
    //战斗渲染图片地址
    public static final String BATTLE_ADDRESS = "battle/";
    //牌背图片地址
    public static final String CARD_BACK_ADDRESS = "cardBack/";
    //角色图片地址
    public static final String ROLE_ADDRESS = "role/";
    //玩家图片地址
    public static final String PLAYER_IMG_ADDRESS = ROLE_ADDRESS + "player/";
    //怪物图片地址
    public static final String ENEMY_IMG_ADDRESS = ROLE_ADDRESS + "enemy/";

    //根据图片地址和图片名字获取地址
    public static String getTextureAddress(String textTureTypeAddress, String textureName) {
        return textTureTypeAddress + textureName + ".png";
    }

    //玩家角色初信息地址
    public static final String PLAYER_ADDRESS = "data/player/";
    //怪物信息地址
    public static final String ENEMY_ADDRESS = "data/enemy/";
    //玩家卡牌地址
    public static final String CARDS_ADDRESS = "data/card/";
    //配置文件地址
    public static final String CONFIGURATION_ADDRESS = "data/configuration/";
    //森林怪池文件地址
    public static final String FOREST_ADDRESS = CONFIGURATION_ADDRESS + "forest/";
    //怪物意图文件地址
    public static final String INTENTION_ADDRESS = CONFIGURATION_ADDRESS + "intention/";
    //角色卡牌
    public static final String PLAYER_CARDS_ADDRESS = CONFIGURATION_ADDRESS + "playerCards/";

    //根据json文件地址和名字获取json文件地址
    public static String getJsonAddress(String jsonAddress, String jsonName) {
        return jsonAddress + jsonName + ".json";
    }


    //信息框高度
    public static final double BAR_HEIGHT = 70;

    //卡牌框及卡牌展示
    public static class CARD_DISPLAY {
        //卡牌框长
        public static final double CARD_BOX_WIDTH = 140;
        //卡牌框高
        public static final double CARD_BOX_HEIGHT = 189;
        //牌与牌框比
        public static final double PROPORTION = 640 / CARD_BOX_WIDTH;
        //卡牌长度
        public static final double CARD_WIDTH = 575 / PROPORTION;
        //卡牌高度
        public static final double CARD_HEIGHT = 700 / PROPORTION;
    }

    //卡牌使用
    public static class CARD_USE {
        //使用按钮
        public static boolean produce = false;
        //弃牌按钮
        public static boolean abandon = false;
        //可用牌数
        public static int remainingProduce;
        //可否指定目标
        public static boolean needTarget = false;
        //群体判断
        public static boolean isAll = false;
        //随机判断
        public static boolean isRam = false;
        //目标指定
        public static Role target;
        //判断是否是玩家回合
        public static boolean isPlayer = false;
        //当前状态是否为弃牌状态
        public static boolean isAbandon = false;
        //当前状态是否可以选择卡牌
        public static boolean selectable = false;
        //当前行动卡牌
        public static CardComponent actionCard;
        //特殊使用效果
        public static boolean specialProduce = false;
        //是否需要弃牌
        public static int needAbandon = 0;

        //初始化卡牌使用
        public static void initCardUse() {
            produce = false;
            abandon = false;
            remainingProduce = 0;
            needTarget = false;
            isAll = false;
            isRam = false;
            target = null;
            isPlayer = false;
            isAbandon = false;
            selectable = true;
            actionCard = null;
            specialProduce = false;
            needAbandon = 0;
        }
    }

    //玩家信息
    public static class PLAYER {
        //当前玩家
        public static Player player;
        //当前金币数
        public static int gold = 99;
        //玩家卡组
        public static ArrayList<Card> cards = new ArrayList<>();
        //玩家护盾
        public static int armor = 0;

        //初始化角色
        public static void initPlayer() {
            //获得角色
            PLAYER.player = getAssetLoader().loadJSON(getJsonAddress(PLAYER_ADDRESS, "soldier"), Player.class).get();
            //初始化玩家初始卡组
            cards.addAll(PlayerCards.getCards(CONFIGURATION.playerCards.getInitial()));
        }
    }

    //配置
    public static class CONFIGURATION {
        //怪池
        public static SeasonMonsterPool seasonMonsterPool = FXGL.getAssetLoader().loadJSON(getJsonAddress(FOREST_ADDRESS, "spring"), SeasonMonsterPool.class).get();
        //人物卡牌
        public static PlayerCards playerCards = FXGL.getAssetLoader().loadJSON(getJsonAddress(PLAYER_CARDS_ADDRESS, "soldier"), PlayerCards.class).get();
    }
}
