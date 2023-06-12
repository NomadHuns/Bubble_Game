package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class Player extends JLabel implements Moveable {

    // 위치 상태
    private int x;
    private int y;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;


    private ImageIcon playerR, playerL;

    public Player() {
        initObject();
        initSetting();
    }

    private void initObject() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        playerR = new ImageIcon(imageLoader.findImageURL("image/playerR.png"));
        playerL = new ImageIcon(imageLoader.findImageURL("image/playerL.png"));
    }

    private void initSetting() {
        x = 55;
        y = 535;

        left = false;
        right = false;
        up = false;
        down = false;

        this.setIcon(playerR);
        setSize(50, 50);
        setLocation(x, y);
    }

    @Override
    public void left() {
        System.out.println("left");
        this.left = true;
        new Thread(() -> {
            while (left) {
                this.setIcon(playerL);
                x = x - 1;
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
        this.right = true;
        new Thread(() -> {
            while (right) {
                this.setIcon(playerR);
                x = x + 1;
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
    public void up() {

    }

    @Override
    public void down() {

    }
}
