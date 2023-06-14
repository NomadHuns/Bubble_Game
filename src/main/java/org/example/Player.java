package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player extends JLabel implements Moveable {
    private BubbleFrame mContext;
    private List<Bubble> bubbleList;

    // 위치 상태
    private int x;
    private int y;

    // 플레이어의 방향 상태
    private PlayerWay playerWay;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    // 벽에 충돌한 상태
    private boolean leftWallCrash;
    private boolean rightWallCrash;

    private final int SPEED = 4;
    private final int JUMPSPEED = 2;

    private ImageIcon playerR, playerL;

    public Player(BubbleFrame mContext) {
        this.mContext = mContext;
        initObject();
        initSetting();
        initBackgroundPlayerService();
    }

    private void initBackgroundPlayerService() {
        try {
            new Thread(new BackgroundPlayerService(this)).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initObject() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        playerR = new ImageIcon(imageLoader.findImageURL("image/playerR.png"));
        playerL = new ImageIcon(imageLoader.findImageURL("image/playerL.png"));
        bubbleList = new ArrayList<>();
    }

    private void initSetting() {
        x = 80;
        y = 535;

        left = false;
        right = false;
        up = false;
        down = false;

        playerWay = PlayerWay.RIGHT;

        leftWallCrash = false;
        rightWallCrash = false;

        this.setIcon(playerR);
        setSize(50, 50);
        setLocation(x, y);
    }

    @Override
    public void left() {
        this.playerWay = PlayerWay.LEFT;
        this.left = true;
        new Thread(() -> {
            while (left) {
                this.setIcon(playerL);
                x = x - SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void right() {
        this.playerWay = PlayerWay.RIGHT;
        this.right = true;
        new Thread(() -> {
            while (right) {
                this.setIcon(playerR);
                x = x + SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    // left + up, right + up
    @Override
    public void up() {
//        System.out.println("up");
        up = true;
        new Thread(() -> {
            for (int i = 0; i < 130/JUMPSPEED; i++) {
                y = y - JUMPSPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            up = false;
            down();

        }).start();
    }

    @Override
    public void down() {
//        System.out.println("down");
        down =true;

        new Thread(() -> {
            while (down) {
                y = y + JUMPSPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            down = false;
        }).start();

    }

    @Override
    public void attack() {
        new Thread(() -> {
            Bubble bubble = new Bubble(mContext);
            bubbleList.add(bubble);
            mContext.add(bubble);
            if (playerWay == PlayerWay.LEFT) {
                bubble.left();
            } else {
                bubble.right();
            }
        }).start();

    }
}
