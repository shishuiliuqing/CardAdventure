package com.hjc.CardAdventure.component.camp;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;


import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_HEIGHT;
import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_WITH;
import static com.hjc.CardAdventure.Global.*;

//火堆图片组件
public class FireComponent extends Component {
    private static final double FIRE_X_MOVE = (APP_WITH - 500) * 1.0/2;
    private static final double FIRE_Y_MOVE = (APP_HEIGHT - 500) * 1.0 / 2;

    @Override
    public void onAdded() {
        //添加篝火图片
        Texture fireTexture = FXGL.texture(getTextureAddress(CAMP_ADDRESS,"fire"),500,500);
        EntityUtils.nodeMove(fireTexture,FIRE_X_MOVE, FIRE_Y_MOVE);
        //实体添加图片
        entity.getViewComponent().addChild(fireTexture);
    }
}
