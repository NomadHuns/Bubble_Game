package org.example._core;

import javax.sound.sampled.*;
import java.io.File;

public class BGM {
    public BGM() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(ImageLoader.getInstance().findImageURL("sound/bgm.wav").toURI()));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            // 소리 설정
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // 볼륨 조절
            gainControl.setValue(-30.0f);

            clip.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
