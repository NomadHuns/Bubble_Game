package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

// 메인 스레드는 키보드 이벤트를 처리하느라 바쁨
// 백그라운드에서 계속 관찰해야함
public class BackgroundPlayerService implements Runnable{

    private BufferedImage image;
    private Player player;
    private List<Bubble> bubbleList;

    public BackgroundPlayerService(Player player) throws Exception {
        this.player = player;
        this.bubbleList = player.getBubbleList();
        image = ImageIO.read(new File(ImageLoader.getInstance().findImageURL("image/backgroundMapService.png").toURI()));
    }

    @Override
    public void run() {
        while (true) {

            // 버블 충돌 확인
            for (int i = 0; i < bubbleList.size(); i++) {
                Bubble bubble = bubbleList.get(i);
                if (bubble.getState() == 1) {
                    if ((Math.abs(player.getX() - bubble.getX()) < 10)
                            && (Math.abs(player.getY() - bubble.getY()) > 0 && Math.abs(player.getY() - bubble.getY()) < 50)) {
//                        System.out.println("적군 사살 완료");
                        bubble.clearBubbled();
                        break;
                    }

                }
            }

            // 색상 확인
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
