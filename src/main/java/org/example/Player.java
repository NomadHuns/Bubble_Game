package org.example;

import javax.swing.*;

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
        this.setIcon(playerL);
        x = x - 10;
        setLocation(x, y);
    }

    @Override
    public void right() {
        this.setIcon(playerR);
        x = x + 10;
        setLocation(x, y);
    }

    @Override
    public void up() {

    }

    @Override
    public void down() {

    }
}
