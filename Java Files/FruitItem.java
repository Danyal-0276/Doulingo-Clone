package com.example.projectmaddoulingoclone;

public class FruitItem {
    private final String name;
    private final int imageResId;

    public FruitItem(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}
