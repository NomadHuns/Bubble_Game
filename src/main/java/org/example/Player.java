package org.example;

import javax.swing.*;

public class Player extends JLabel {
    private int x;
    private int y;

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

        this.setIcon(playerR);
        setSize(50, 50);
        setLocation(x, y);
    }
}
