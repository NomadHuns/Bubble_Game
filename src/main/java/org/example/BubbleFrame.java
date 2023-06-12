package org.example;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BubbleFrame extends JFrame {
    private JLabel backgroundMap;
    private Player player;

    public BubbleFrame () {
        initSetting();
        initObject();
        initListener();
        setVisible(true);
    }

    private void initObject() {
        backgroundMap = new JLabel(new ImageIcon(ImageLoader.getInstance().findImageURL("image/backgroundMap.png")));
        setContentPane(backgroundMap);

        player = new Player();
        add(player);
    }

    private void initSetting() {
        setSize(1000, 640);
        setLayout(null); // absolute 레이아웃 (자유롭게 그릴 수 있다.)
        setLocationRelativeTo(null); //JFrame 가운데 배치하기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    private void initListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (!player.isLeft() && !player.isLeftWallCrash())
                        player.left();
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!player.isRight() && !player.isRightWallCrash())
                        player.right();
                        break;
                    case KeyEvent.VK_UP:
                        if (!player.isUp() && !player.isDown())
                        player.up();
                        break;
                    case KeyEvent.VK_SPACE:
                        Bubble bubble = new Bubble(player);
                        add(bubble);
                        break;
                }
            }
           @Override
           public void keyReleased(KeyEvent e) {
               switch (e.getKeyCode()) {
                   case KeyEvent.VK_LEFT:
                       player.setLeft(false);
                       break;
                   case KeyEvent.VK_RIGHT:
                       player.setRight(false);
                       break;
               }
           }
       });
    }
}
