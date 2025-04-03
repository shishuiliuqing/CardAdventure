package com.hjc.CardAdventure.pojo.card;

import javafx.scene.paint.Color;

public enum CardQuality {
    WHITE, BLUE, YELLOW;

    public Color getColor() {
        if (this.equals(WHITE)) return Color.valueOf("#DCDCDC");
        if (this.equals(BLUE)) return Color.valueOf("#00BBFF");
        else return Color.YELLOW;
    }
}
