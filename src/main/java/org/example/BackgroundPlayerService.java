package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

// 메인 스레드는 키보드 이벤트를 처리하느라 바쁨
// 백그라운드에서 계속 관찰해야함
public class BackgroundPlayerService implements Runnable{

    private BufferedImage image;
    private Player player;

    public BackgroundPlayerService(Player player) throws Exception {
        this.player = player;
        image = ImageIO.read(new File(ImageLoader.getInstance().findImageURL("image/backgroundMapService.png").toURI()));
    }

    @Override
    public void run() {
        // 색상 확인
        while (true) {
            Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
            Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
            int bottomColor = image.getRGB(player.getX() + 10, player.getY() + 50 + 5)
                    + image.getRGB(player.getX() + 50 - 10, player.getY() + 50 + 5);


            if (bottomColor != -2) {
//                System.out.println("bottomColor" + bottomColor);
//                System.out.println("바닥에 충돌");
                player.setDown(false);
            } else {
                if (!player.isUp() && !player.isDown()) {
                    player.down();
                }
            }

            // 왼쪽 오른쪽 이동시 외벽 충돌 확인
            if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
//                System.out.println("왼쪽 벽 충돌");
                player.setLeftWallCrash(true);
                player.setLeft(false);
            } else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
//                System.out.println("오른쪽 벽 충돌");
                player.setRightWallCrash(true);
                player.setRight(false);
            } else {
                player.setLeftWallCrash(false);
                player.setRightWallCrash(false);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
