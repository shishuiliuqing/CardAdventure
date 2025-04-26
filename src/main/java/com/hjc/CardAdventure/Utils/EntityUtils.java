package com.hjc.CardAdventure.Utils;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.enemy.IntentionType;
import com.hjc.CardAdventure.pojo.player.Player;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;
import static com.hjc.CardAdventure.Global.PLAYER.player;

//用于实体创建
public class EntityUtils {
    private EntityUtils() {
    }

    //节点移动
    public static void nodeMove(Node node, double x, double y) {
        node.setTranslateX(x);
        node.setTranslateY(y);
    }

    //字体大小及字体创建
    public static Text getText(String content, String fontType, double scale, Color color) {
        //设置文本内容
        Text text = new Text(content);
        //设置文本样式
        text.setFont(new Font(fontType, scale));
        //设置文本颜色
        text.setFill(color);

        return text;
    }

    //label创建
    public static Label getLabel(double with, double height, String content, String fontType, double scale, Color color) {
        //设置内容
        Label label = new Label(content);
        //设置大小
        label.setMaxSize(with, height);
        //设置文本样式
        label.setFont(new Font(fontType, scale));
        //设置文本颜色
        label.setTextFill(color);
        //设置制动换行
        label.setWrapText(true);

        return label;
    }

    //颜色解析
    public static String parseColor(String colorS) {
        if (colorS.equals("#ff0000")) return "Red";
        if (colorS.equals("#76d1ff")) return "Blue";
        return "";
    }

    //创建圆心数字
    public static StackPane generateCircleNum(double x, double y, double r, int num, String colorS, Font textFont) {
        //创建一个圆
        Circle circle = new Circle(r, Color.valueOf(colorS));
        //创建数字文本
        Text numText = new Text(String.valueOf(num));
        numText.setFont(textFont);
        //创建stack容器，令内容居中
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(circle);
        stackPane.getChildren().add(numText);
        nodeMove(stackPane, x - r, y - r);

        return stackPane;
    }

    //卡牌创建
    public static Pane createCard(Card card) {
        Pane pane = new Pane();
        pane.setPrefSize(CARD_WIDTH, CARD_HEIGHT);

        //制作矩形牌背
        Rectangle cardBack = new Rectangle(CARD_WIDTH, CARD_HEIGHT, Color.valueOf(card.getColorS()));
        pane.getChildren().add(cardBack);

        //卡牌品质框
        Rectangle cardQuality = new Rectangle(CARD_WIDTH, CARD_HEIGHT / 5, card.getCardQuality().getColor());
        //卡牌名文本
        Text cardName = getText(card.getCardName(),
                "华文行楷", 20,
                Color.BLACK);
        //卡牌品质框与卡牌名居中
        StackPane stackPane = new StackPane(cardQuality);
        stackPane.getChildren().add(cardName);
        pane.getChildren().add(stackPane);

        //卡牌描述
        //背景框
        StackPane cardDescriptionBack = new StackPane(new Rectangle(CARD_WIDTH - 10, CARD_HEIGHT / 2 - 5, Color.valueOf("#696969")));
        nodeMove(cardDescriptionBack, 5, CARD_HEIGHT / 2);
        pane.getChildren().add(cardDescriptionBack);

        Label cardDescription = getLabel(CARD_WIDTH - 10, CARD_HEIGHT / 2 - 5,
                card.toString(), "微软雅黑", 11.7,
                Color.WHITE);
        cardDescription.setStyle("-fx-background-color: #696969");
        nodeMove(cardDescription, 5, CARD_HEIGHT / 2);
        pane.getChildren().add(cardDescription);

        //卡牌属性要求打印
        Label cardAttribute = getLabel(CARD_WIDTH - 10, CARD_HEIGHT * 3 / 10,
                card.getAttribute().displayAttribute(), "华文仿宋", 11.3,
                Color.WHITE);
        nodeMove(cardAttribute, 5, CARD_HEIGHT / 5);
        pane.getChildren().add(cardAttribute);

        return pane;
    }

    //角色创建
    public static void createRole(Entity entity, boolean isSelected, double x, double y, double bloodBoxLen, Role role) {
        //加载指定图标
        Texture target = FXGL.texture(getTextureAddress(ROLE_ADDRESS, isSelected ? "targetLight" : "targetDark"), 250, 250);
        nodeMove(target, x, y);
        entity.getViewComponent().addChild(target);

        //血条框制作
        Rectangle bloodBox = new Rectangle(bloodBoxLen, 11.0, Color.BLACK);
        nodeMove(bloodBox, x + (250 - bloodBoxLen) / 2, y + 180);
        entity.getViewComponent().addChild(bloodBox);

        double p = role.getRoleBlood() * 1.0 / role.getRoleMaxBlood();
        //血条制作
        Rectangle blood = new Rectangle(bloodBoxLen * p, 9.0, Color.RED);
        nodeMove(blood, x + (250 - bloodBoxLen) / 2, y + 181);
        entity.getViewComponent().addChild(blood);

        //血量数字显示
        Text bloodValue = getText(role.getRoleBlood() + "/" + role.getRoleMaxBlood(),
                "华文琥珀", 20,
                Color.WHITE);

        Rectangle rectangle = new Rectangle(bloodBoxLen, 11.0, Color.rgb(0, 0, 0, 0));
        StackPane stackPane = new StackPane(rectangle);
        stackPane.getChildren().add(bloodValue);
        nodeMove(stackPane, x + (250 - bloodBoxLen) / 2, y + 178);
        entity.getViewComponent().addChild(stackPane);
    }

    //角色护盾展示
    public static void displayArmor(Entity entity, Role role, double x, double y, double bloodBoxLen) {
        if (role.getRoleArmor() <= 0) return;

        Texture armor = FXGL.texture(getTextureAddress(EFFECT_IMG_ADDRESS, "armor"), 40, 40);
        nodeMove(armor, x + (250 - bloodBoxLen) / 2 - 35, y + 180 - 15);
        entity.getViewComponent().addChild(armor);

        Rectangle textBar = new Rectangle(40, 40, Color.rgb(0, 0, 0, 0));
        Text text = getText(String.valueOf(role.getRoleArmor()),
                "微软雅黑", 15,
                Color.BLACK);
        StackPane stackPane = new StackPane(textBar);
        stackPane.getChildren().add(text);
        nodeMove(stackPane, x + (250 - bloodBoxLen) / 2 - 35, y + 180 - 15);
        entity.getViewComponent().addChild(stackPane);

        Rectangle rectangle = new Rectangle(bloodBoxLen + 5, 11, Color.valueOf("#a7fefeB3"));
        nodeMove(rectangle, x + (250 - bloodBoxLen) / 2, y + 180);
        entity.getViewComponent().addChild(rectangle);
    }

    //敌人意图展示
    public static void displayIntention(Enemy enemy, Entity entity, double x, double y) {
        //无意图，返回
        if (enemy.getNowIntention() == null) return;
        //生成攻击意图
        if (enemy.getNowIntention().getIntentionType() == IntentionType.ATTACK) {
            Texture attackTexture = FXGL.texture(getTextureAddress(INTENTION_IMG_ADDRESS, "attack"), 40, 40);
            nodeMove(attackTexture, x - 50, y - 50);
            entity.getViewComponent().addChild(attackTexture);

            displayDamage(enemy, entity, x, y);
        }
        //防御类
        else if (enemy.getNowIntention().getIntentionType() == IntentionType.DEFENSE) {
            Texture texture = FXGL.texture(getTextureAddress(INTENTION_IMG_ADDRESS, "defense"), 40, 40);
            nodeMove(texture, x - 20, y - 50);
            entity.getViewComponent().addChild(texture);
        }
        //强化类
        else if (enemy.getNowIntention().getIntentionType() == IntentionType.STRENGTHEN) {
            Texture upTexture = FXGL.texture(getTextureAddress(INTENTION_IMG_ADDRESS, "up"), 40, 40);
            nodeMove(upTexture, x - 20, y - 50);
            entity.getViewComponent().addChild(upTexture);
        }
        //攻击弱化类
        else if (enemy.getNowIntention().getIntentionType() == IntentionType.WEAKEN) {
            Texture weakenTexture = FXGL.texture(getTextureAddress(INTENTION_IMG_ADDRESS, "weaken"), 40, 40);
            nodeMove(weakenTexture, x - 20, y - 50);
            entity.getViewComponent().addChild(weakenTexture);
        }
    }

    //伤害类意图伤害显示
    private static void displayDamage(Enemy enemy, Entity entity, double x, double y) {
        int[] value = IntentionType.getAttackValue(enemy, enemy.getNowIntention().getEffects());
        String attackValue;
        if (value[0] == 0) {
            attackValue = String.valueOf(value[1]);
        } else {
            attackValue = value[1] + "✖" + value[0];
        }

        Label label = getLabel(enemy.getWidth() / 2, 40, attackValue,
                "微软雅黑", 17,
                Color.RED);
        nodeMove(label, x - 10, y - 50);
        entity.getViewComponent().addChild(label);
    }

}
