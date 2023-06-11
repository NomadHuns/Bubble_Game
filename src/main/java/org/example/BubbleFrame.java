package org.example;

import javax.swing.*;

public class BubbleFrame extends JFrame {
    private JLabel backgroundMap;
    private Player player;

    public BubbleFrame () {
        initSetting();
        initObject();
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
}
