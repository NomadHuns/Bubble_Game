package org.example.services;

import org.example._core.ImageLoader;
import org.example.components.Bubble;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// 메인 스레드는 키보드 이벤트를 처리하느라 바쁨
// 백그라운드에서 계속 관찰해야함
public class BackgroundBubbleService {

    private BufferedImage image;
    private Bubble bubble;

    public BackgroundBubbleService(Bubble bubble) {
        this.bubble = bubble;
        try {
            image = ImageIO.read(new File(ImageLoader.getInstance().findImageURL("image/backgroundMapService.png").toURI()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean leftWall() {
        Color leftColor = new Color(image.getRGB(bubble.getX() - 10, bubble.getY() + 25));

        if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
            return true;
        }

        return false;
    }

    public boolean rightWall() {
        Color rightColor = new Color(image.getRGB(bubble.getX() + 50 + 15, bubble.getY() + 25));

        if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
            return true;
        }

        return false;
    }

    public boolean topWall() {
        Color rightColor = new Color(image.getRGB(bubble.getX() + 25, bubble.getY() - 10));

        if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
            return true;
        }

        return false;
    }
}
