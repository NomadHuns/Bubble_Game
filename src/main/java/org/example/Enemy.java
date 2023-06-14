package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter @Setter
public class Enemy extends JLabel implements Moveable {
    private BubbleFrame mContext;

    // 위치 상태
    private int x;
    private int y;

    // 적군의 방향 상태
    private EnemyWay enemyWay;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    // 생존 여부
    private int state; // 0 : 생존, 1 : 사망

    // 적군의 속도
    private final int SPEED = 3;
    private final int JUMPSPEED = 1;

    private ImageIcon enemyR, enemyL;

    public Enemy(BubbleFrame mContext) {
        this.mContext = mContext;
        initObject();
        initSetting();
        initBackgroundEnemyService();
    }

    private void initBackgroundEnemyService() {
//            new Thread(new BackgroundEnemyService(this)).start();
    }

    private void initObject() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        enemyR = new ImageIcon(imageLoader.findImageURL("image/enemyR.png"));
        enemyL = new ImageIcon(imageLoader.findImageURL("image/enemyL.png"));
    }

    private void initSetting() {
        x = 480;
        y = 178;

        left = false;
        right = false;
        up = false;
        down = false;

        state = 0;

        enemyWay = EnemyWay.RIGHT;

        this.setIcon(enemyR);
        setSize(50, 50);
        setLocation(x, y);
    }

    @Override
    public void left() {
        this.enemyWay = EnemyWay.LEFT;
        this.left = true;
        new Thread(() -> {
            while (left) {
                this.setIcon(enemyL);
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
        this.enemyWay = EnemyWay.RIGHT;
        this.right = true;
        new Thread(() -> {
            while (right) {
                this.setIcon(enemyR);
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
        this.down = true;

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
}
