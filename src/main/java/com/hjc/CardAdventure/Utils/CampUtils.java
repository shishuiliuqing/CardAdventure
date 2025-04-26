package com.hjc.CardAdventure.Utils;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.hjc.CardAdventure.Global.*;

public class CampUtils {
    private CampUtils() {
    }

    //生成营地按钮
    public static void createButton(Entity entity, double x, double y, String name, String content, Color color) {
        Rectangle r = new Rectangle(200, 80, color);
        EntityUtils.nodeMove(r, x, y);
        entity.getViewComponent().addChild(r);

        Texture texture = FXGL.texture(getTextureAddress(CAMP_ADDRESS, name), 80, 80);
        EntityUtils.nodeMove(texture, x, y);
        entity.getViewComponent().addChild(texture);

        Text t = EntityUtils.getText(content,
                "华文行楷", 50,
                Color.WHITE);
        EntityUtils.nodeMove(t, x + 80, y + 50);
        entity.getViewComponent().addChild(t);
    }
}
