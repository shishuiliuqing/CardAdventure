package com.hjc.CardAdventure.configuration;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Random;

import static com.hjc.CardAdventure.Global.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventConfiguration {
    //探险事件(精英战斗)
    private ArrayList<String> adventure;
    //其他事件
    private ArrayList<String> other;

    //随机获得一个探险事件
    public String randomAdventureEvent() {
        Random r = new Random();
        String result = adventure.get(r.nextInt(adventure.size()));
        adventure.remove(result);
        return result;
    }

    //根据环境条件获取配置
    public static EventConfiguration getInstance() {
        String season = InsideInformation.getSeason().getAddress();
        String environment = InsideInformation.getEnvironment().getAddress();
        return FXGL.getAssetLoader().loadJSON(getJsonAddress(EVENT_CONFIGURATION_ADDRESS, environment + "/" + season), EventConfiguration.class).get();
    }
}
