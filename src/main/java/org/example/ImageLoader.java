package org.example;

import java.net.URL;

public class ImageLoader {

    private static ImageLoader instance;

    private ImageLoader() {}

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    public URL findImageURL(String imagePath) {
        URL imageURL = getClass().getClassLoader().getResource(imagePath);

        if (imageURL == null) {
            System.err.println("이미지 파일을 찾을 수 없습니다: " + imagePath);
            return null;
        }

        return imageURL;
    }
}
