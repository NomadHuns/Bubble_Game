package org.example.components;

import lombok.Getter;
import lombok.Setter;
import org.example.*;
import org.example._core.ImageLoader;
import org.example.services.BackgroundBubbleService;

import javax.swing.*;

@Getter @Setter
public class Bubble extends JLabel implements Moveable {

    // 의존성 컴포지션
    private BubbleFrame mContext;
    private Player player;
    private Enemy enemy;
    private Enemy removeEnemy = null; // 적 제거 변수.
    private BackgroundBubbleService backgroundBubbleService;

    // 위치 상태
    private int x;
    private int y;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;

    // 적군을 맞춘 상태
    private int state; // 0 : 물방울, 1 : 적군을 맞춘 상태

    private ImageIcon bubble; // 물방울
    private ImageIcon bubbled; // 적을 가둔 물방울
    private ImageIcon bomb; // 물방울이 터진 상태

    public Bubble(BubbleFrame mContext) {
        this.mContext = mContext;
        this.player = mContext.getPlayer();
        this.enemy = mContext.getEnemy();
        initObject();
        initSetting();
    }

    private void initObject() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        bubble = new ImageIcon(imageLoader.findImageURL("image/bubble.png"));
        bubbled = new ImageIcon(imageLoader.findImageURL("image/bubbled.png"));
        bomb = new ImageIcon(imageLoader.findImageURL("image/bomb.png"));

        backgroundBubbleService = new BackgroundBubbleService(this);
    }

    private void initSetting() {
        left = false;
        right = false;
        up = false;

        x = player.getX();
        y = player.getY();

        setIcon(bubble);
        setSize(50, 50);

        state = 0;
    }


    @Override
    public void left() {
        left = true;
        for (int i = 0; i < 400; i++) {
            x--;
            setLocation(x, y);

            // 벽에 부딫힘
            if (backgroundBubbleService.leftWall()) {
                left = false;
                break;
            }

            // 적군 물방울 맞음
            if ((Math.abs(x - enemy.getX()) < 10) && (Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50)){
                if (enemy.getState() == 0){
                    attack();
                    break;
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        up();
    }

    @Override
    public void right() {
        right = true;
        for (int i = 0; i < 400; i++) {
            x++;
            setLocation(x, y);

            // 벽에 부딫힘
            if (backgroundBubbleService.rightWall()) {
                right = false;
                break;
            }

            // 적군 물방울 맞음
            if ((Math.abs(x - enemy.getX()) < 10) && (Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50)){
                if (enemy.getState() == 0){
                    attack();
                    break;
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        up();
    }

    @Override
    public void up() {
        up = true;
        while (up) {
            y--;
            setLocation(x, y);

            if (backgroundBubbleService.topWall()) {
                up = false;
                break;
            }

            try {
                if (state == 0) { // 기본 물방울
                    Thread.sleep(1);
                } else { // 적을 가둔 물방울
                    Thread.sleep(10);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // 상태가 버블일 때에만 터짐
        if (state == 0) {
            clearBubble(); // 천장에 버블이 도착하고 나서 3초 후에 메모리에서 소멸
        }

    }

    @Override
    public void attack() {
        state = 1;
        enemy.setState(1);
        setIcon(bubbled);
        removeEnemy = enemy;
        mContext.remove(enemy); // 메모리에서 사라지게 한다. (가비지 컬렉션 -> 즉시 사라지지 않는다.)
        mContext.repaint();
    }

    private void clearBubble() {
        try {
            Thread.sleep(3000);
            setIcon(bomb);
            Thread.sleep(500);
            mContext.remove(this);
            mContext.repaint();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearBubbled() {
        new Thread(() -> {
            try {
                up = false;
                setIcon(bomb);
                Thread.sleep(1000);
                mContext.getPlayer().getBubbleList().remove(this);
                mContext.getEnemy().remove(removeEnemy); // 컨텍스트에 enemy 삭제
                mContext.remove(this);
                mContext.repaint();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
