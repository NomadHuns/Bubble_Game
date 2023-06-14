package org.example.services;

import org.example._core.ImageLoader;
import org.example.components.Enemy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// 메인 스레드는 키보드 이벤트를 처리하느라 바쁨
// 백그라운드에서 계속 관찰해야함
public class BackgroundEnemyService implements Runnable{

    private BufferedImage image;
    private Enemy enemy;

    public BackgroundEnemyService(Enemy enemy) throws Exception {
        this.enemy = enemy;
        image = ImageIO.read(new File(ImageLoader.getInstance().findImageURL("image/backgroundMapService.png").toURI()));
    }

    @Override
    public void run() {
        while (enemy.getState() == 0) {

            // 색상 확인
            Color leftColor = new Color(image.getRGB(enemy.getX() - 10, enemy.getY() + 25));
            Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 15, enemy.getY() + 25));
            int bottomColor = image.getRGB(enemy.getX() + 10, enemy.getY() + 50 + 5)
                    + image.getRGB(enemy.getX() + 50 - 10, enemy.getY() + 50 + 5);


            if (bottomColor != -2) {
//                System.out.println("bottomColor" + bottomColor);
//                System.out.println("바닥에 충돌");
                enemy.setDown(false);
            } else {
                if (!enemy.isUp() && !enemy.isDown()) {
                    enemy.down();
                }
            }

            // 왼쪽 오른쪽 이동시 외벽 충돌 확인
            if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
                enemy.setLeft(false);
                if (!enemy.isRight()) {
                    enemy.right();
                }
            } else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
                enemy.setRight(false);
                if (!enemy.isLeft()) {
                    enemy.left();
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
