package com.hjc.CardAdventure.component.role;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import javafx.scene.text.Text;

import static com.hjc.CardAdventure.Global.PLAYER.*;
import static com.hjc.CardAdventure.Global.*;

public class PlayerComponent extends Component {
    //是否被选择
    private static boolean isSelected = false;
    //人物图片
    private Texture playerImg;

    @Override
    public void onAdded() {
        //显示
        update();
    }

    //非人物组件
    private void addComponent() {
        EntityUtils.createRole(entity, isSelected, 450, 300, 180, player);
    }

    //添加人物
    private void addPlayer() {
        //人物显示
        playerImg = FXGL.texture(getTextureAddress(PLAYER_IMG_ADDRESS, player.getImg()), player.getWidth(), player.getHeight());
        EntityUtils.nodeMove(playerImg, player.getX(), player.getY());
        entity.getViewComponent().addChild(playerImg);
    }

    //添加护盾显示
    private void addArmor() {
        EntityUtils.displayArmor(entity, player, 450, 300, 180);
    }

    //更新人物指定
    public void update(boolean isSelected) {
        PlayerComponent.isSelected = isSelected;
        update();
    }

    //人物更新
    public void update() {
        entity.getViewComponent().clearChildren();
        addComponent();
        addPlayer();
        addArmor();
    }
}
