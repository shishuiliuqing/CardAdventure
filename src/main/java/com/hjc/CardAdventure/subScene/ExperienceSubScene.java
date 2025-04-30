package com.hjc.CardAdventure.subScene;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.Utils.OtherUtils;
import com.hjc.CardAdventure.pojo.player.Level;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.Global.GAME_SETTING.*;
import static com.hjc.CardAdventure.Global.PLAYER.*;

public class ExperienceSubScene extends SubScene {
    //判断关闭类型
    public static int flag;
    //属性增长点数
    private static int attributeUp;

    public ExperienceSubScene(int experience) {
        //透明背景，不允许操作
        Rectangle background = new Rectangle(APP_WITH, APP_HEIGHT, Color.rgb(80, 80, 80, 0.7));
        getContentRoot().getChildren().add(background);

        //获得经验文本
        Label label = EntityUtils.getLabel(600, 100, "获 得 经 验 ！",
                "华文行楷", 100,
                Color.YELLOW);
        EntityUtils.nodeMove(label, (APP_WITH - 600.0) / 2, 170);
        getContentRoot().getChildren().add(label);

        //获得矩形背景图
        Texture rectBox = FXGL.texture(getTextureAddress(SUB_SCENE_IMG_ADDRESS, "rectBox"), 888, 574);
        EntityUtils.nodeMove(rectBox, (APP_WITH - 888.0) / 2, 280);
        getContentRoot().getChildren().add(rectBox);

        //背景图透明出现动画
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), rectBox);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setOnFinished(e -> experienceUp(experience));
        ft.play();
    }

    //经验提升
    private void experienceUp(int experience) {
        //玩家经验文本
        Text text = EntityUtils.getText(Level.getLV(player.getExperience()) + "\nexp",
                "微软雅黑", 40,
                Color.BLACK);
        EntityUtils.nodeMove(text, (APP_WITH - 888.0) / 2 + 50, 380);
        getContentRoot().getChildren().add(text);

        //经验条
        Texture experienceTexture = FXGL.texture(getTextureAddress(SUB_SCENE_IMG_ADDRESS, "experience"), 600, 40);
        EntityUtils.nodeMove(experienceTexture, (APP_WITH - 888.0) / 2 + 130, 400);
        getContentRoot().getChildren().add(experienceTexture);

        //增长经验文本
        Text upText = EntityUtils.getText("+" + experience,
                "微软雅黑", 25,
                Color.GREEN);
        EntityUtils.nodeMove(upText, (APP_WITH - 888.0) / 2 + 740, 430);
        getContentRoot().getChildren().add(upText);

        int nowExperience = player.getExperience();
        int needExperience = Level.nextExperience(nowExperience);

        //经验条原来大小
        double o = nowExperience == 0 ? 1.0 : nowExperience * 1.0;
        Rectangle expRect = new Rectangle((nowExperience == 0 ? 1.0 / needExperience : nowExperience * 1.0 / needExperience) * 586.0, 22, Color.GREEN);
        EntityUtils.nodeMove(expRect, (APP_WITH - 888.0) / 2 + 137, 408);
        getContentRoot().getChildren().add(expRect);
        //添加经验
        if (nowExperience + experience > needExperience) {
            experience = nowExperience + experience - needExperience;
            player.setExperience(needExperience);
            nowExperience = needExperience;
        } else {
            nowExperience += experience;
            player.setExperience(nowExperience);
            experience = nowExperience - needExperience;
            //experience = 0;
        }

        //经验条增长动画
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.3), expRect);
        st.setToX(nowExperience / o);
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.3), expRect);
        tt.setFromX((APP_WITH - 888.0) / 2 + 137);
        tt.setToX((APP_WITH - 888.0) / 2 + 137 + (nowExperience - o) / needExperience * 586.0 / 2);
        ParallelTransition pt = new ParallelTransition(st, tt);
        int finalExperience = experience;
        int finalNowExperience = nowExperience;
        pt.setOnFinished(e -> upgrade(finalExperience, finalNowExperience, needExperience));
        pt.play();
    }

    //升级奖励获得
    private void upgrade(int experience, int nowExperience, int needExperience) {
        //居中容器
        Rectangle r = new Rectangle(586.0, 22, Color.rgb(0, 0, 0, 0));
        StackPane stackPane = new StackPane(r);
        EntityUtils.nodeMove(stackPane, (APP_WITH - 888.0) / 2 + 137, 408);
        getContentRoot().getChildren().add(stackPane);
        //经验文本
        Text text = EntityUtils.getText(nowExperience + "/" + needExperience,
                "华文琥珀", 30,
                Color.WHITE);
        stackPane.getChildren().add(text);

        //如果剩余经验>=0，说明升级了
        if (experience >= 0) {
            //升级文本
            Label upgradeText = EntityUtils.getLabel(400, 200, "恭喜升级！",
                    "华文行楷", 50,
                    Color.RED);
            EntityUtils.nodeMove(upgradeText, (APP_WITH - 600) / 2.0 + 150, 458);
            getContentRoot().getChildren().add(upgradeText);

            //奖励获得
            int[] reward = Level.upgrade(player.getExperience());
            //属性加点
            if (reward[0] > 0) {
                //属性赠点
                attributeUp = reward[0];
                //进行属性增点选择
                attributeUp(experience);
            }
        } else {
            //前进按钮
            Texture forward = FXGL.texture(getTextureAddress(SUB_SCENE_IMG_ADDRESS, "forward"), 200, 200);
            EntityUtils.nodeMove(forward, 1700, 800);
            forward.setOnMouseClicked(e -> {
                FXGL.getSceneService().popSubScene();
                if (flag == 0) {
                    OtherUtils.nextTime();
                }
            });
            getContentRoot().getChildren().add(forward);
        }
    }

    //属性增点
    private void attributeUp(int experience) {
        double xMove = 560.0;
        double yMove = 600.0;
        double interval = 138;
        double bYMove = yMove - 50;
        //力量添加
        Label powerText = new Label("力量：" + player.getAttribute().getPower());
        powerText.setFont(new Font("华文行楷", 20));
        powerText.setTranslateX(xMove);
        powerText.setTranslateY(yMove);
        getContentRoot().getChildren().add(powerText);
        Button powerUp = new Button("+");
        powerUp.setTranslateX(xMove);
        powerUp.setTranslateY(bYMove);
        powerUp.setFocusTraversable(false);
        powerUp.setStyle("-fx-base: ORANGE");
        getContentRoot().getChildren().add(powerUp);

        //智力添加
        Label intelligenceText = new Label("智力：" + player.getAttribute().getIntelligence());
        intelligenceText.setFont(new Font("华文行楷", 20));
        intelligenceText.setTranslateX(xMove + interval * 1);
        intelligenceText.setTranslateY(yMove);
        getContentRoot().getChildren().add(intelligenceText);
        Button intelligenceUp = new Button("+");
        intelligenceUp.setTranslateX(xMove + interval * 1);
        intelligenceUp.setTranslateY(bYMove);
        intelligenceUp.setFocusTraversable(false);
        intelligenceUp.setStyle("-fx-base: ORANGE");
        getContentRoot().getChildren().add(intelligenceUp);

        //智力添加
        Label defenseText = new Label("防御：" + player.getAttribute().getDefense());
        defenseText.setFont(new Font("华文行楷", 20));
        defenseText.setTranslateX(xMove + interval * 2);
        defenseText.setTranslateY(yMove);
        getContentRoot().getChildren().add(defenseText);
        Button defenseUp = new Button("+");
        defenseUp.setTranslateX(xMove + interval * 2);
        defenseUp.setTranslateY(bYMove);
        defenseUp.setFocusTraversable(false);
        defenseUp.setStyle("-fx-base: ORANGE");
        getContentRoot().getChildren().add(defenseUp);

        //敏捷添加
        Label agilityText = new Label("敏捷：" + player.getAttribute().getAgility());
        agilityText.setFont(new Font("华文行楷", 20));
        agilityText.setTranslateX(xMove + interval * 3);
        agilityText.setTranslateY(yMove);
        getContentRoot().getChildren().add(agilityText);
        Button agilityUp = new Button("+");
        agilityUp.setTranslateX(xMove + interval * 3);
        agilityUp.setTranslateY(bYMove);
        agilityUp.setFocusTraversable(false);
        agilityUp.setStyle("-fx-base: ORANGE");
        getContentRoot().getChildren().add(agilityUp);

        //纯洁添加
        Label purityText = new Label("纯洁：" + player.getAttribute().getPurity());
        purityText.setFont(new Font("华文行楷", 20));
        purityText.setTranslateX(xMove + interval * 4);
        purityText.setTranslateY(yMove);
        getContentRoot().getChildren().add(purityText);
        Button purityUp = new Button("+");
        purityUp.setTranslateX(xMove + interval * 4);
        purityUp.setTranslateY(bYMove);
        purityUp.setFocusTraversable(false);
        purityUp.setStyle("-fx-base: ORANGE");
        getContentRoot().getChildren().add(purityUp);

        //速度添加
        Label speedText = new Label("速度：" + player.getAttribute().getSpeed());
        speedText.setFont(new Font("华文行楷", 20));
        speedText.setTranslateX(xMove + interval * 5);
        speedText.setTranslateY(yMove);
        getContentRoot().getChildren().add(speedText);
        Button speedUp = new Button("+");
        speedUp.setTranslateX(xMove + interval * 5);
        speedUp.setTranslateY(bYMove);
        speedUp.setFocusTraversable(false);
        speedUp.setStyle("-fx-base: ORANGE");
        getContentRoot().getChildren().add(speedUp);

        //提示操作框
        Label tip = EntityUtils.getLabel(600, 50, "请选择" + attributeUp + "个属性添加属性点",
                "华文行楷", 50,
                Color.ORANGE);
        EntityUtils.nodeMove(tip, 650, 660);
        getContentRoot().getChildren().add(tip);

        //力量添加
        powerUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUp--;
            //玩家力量属性+1
            player.getAttribute().setPower(player.getAttribute().getPower() + 1);
            //刷新显示
            powerText.setText("力量：" + player.getAttribute().getPower());
            //移除该按钮
            getContentRoot().getChildren().remove(powerUp);
            if (attributeUp == 0 && experience > 0) {
                //删除其他组件
                while (getContentRoot().getChildren().size() > 3) {
                    getContentRoot().getChildren().remove(3);
                }
                //继续添加剩余的经验
                experienceUp(experience);
            }
        });

        //智力添加
        intelligenceUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUp--;
            //玩家力量属性+1
            player.getAttribute().setIntelligence(player.getAttribute().getIntelligence() + 1);
            //刷新显示
            intelligenceText.setText("智力：" + player.getAttribute().getIntelligence());
            //移除该按钮
            getContentRoot().getChildren().remove(intelligenceUp);
            if (attributeUp == 0 && experience > 0) {
                //删除其他组件
                while (getContentRoot().getChildren().size() > 3) {
                    getContentRoot().getChildren().remove(3);
                }
                //继续添加剩余的经验
                experienceUp(experience);
            }
        });

        //防御添加
        defenseUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUp--;
            //玩家力量属性+1
            player.getAttribute().setDefense(player.getAttribute().getDefense() + 1);
            //刷新显示
            defenseText.setText("防御：" + player.getAttribute().getDefense());
            //移除该按钮
            getContentRoot().getChildren().remove(defenseUp);
            if (attributeUp == 0 && experience > 0) {
                //删除其他组件
                while (getContentRoot().getChildren().size() > 3) {
                    getContentRoot().getChildren().remove(3);
                }
                //继续添加剩余的经验
                experienceUp(experience);
            }
        });

        //敏捷添加
        agilityUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUp--;
            //玩家力量属性+1
            player.getAttribute().setAgility(player.getAttribute().getAgility() + 1);
            //刷新显示
            agilityText.setText("敏捷：" + player.getAttribute().getAgility());
            //移除该按钮
            getContentRoot().getChildren().remove(agilityUp);
            if (attributeUp == 0 && experience > 0) {
                //删除其他组件
                while (getContentRoot().getChildren().size() > 3) {
                    getContentRoot().getChildren().remove(3);
                }
                //继续添加剩余的经验
                experienceUp(experience);
            }
        });

        //纯洁添加
        purityUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUp--;
            //玩家力量属性+1
            player.getAttribute().setPurity(player.getAttribute().getPurity() + 1);
            //刷新显示
            purityText.setText("纯洁：" + player.getAttribute().getPurity());
            //移除该按钮
            getContentRoot().getChildren().remove(purityUp);
            if (attributeUp == 0 && experience > 0) {
                //删除其他组件
                while (getContentRoot().getChildren().size() > 3) {
                    getContentRoot().getChildren().remove(3);
                }
                //继续添加剩余的经验
                experienceUp(experience);
            }
        });

        //速度添加
        speedUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUp--;
            //玩家力量属性+1
            player.getAttribute().setSpeed(player.getAttribute().getSpeed() + 1);
            //刷新显示
            speedUp.setText("速度：" + player.getAttribute().getSpeed());
            //移除该按钮
            getContentRoot().getChildren().remove(speedUp);
            if (attributeUp == 0 && experience > 0) {
                //删除其他组件
                while (getContentRoot().getChildren().size() > 3) {
                    getContentRoot().getChildren().remove(3);
                }
                //继续添加剩余的经验
                experienceUp(experience);
            }
        });
    }
}
