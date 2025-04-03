package com.hjc.CardAdventure.Utils;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.card.Card;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.hjc.CardAdventure.Global.*;

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
}
