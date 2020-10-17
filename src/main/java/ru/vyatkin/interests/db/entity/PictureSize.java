package ru.vyatkin.interests.db.entity;

public enum PictureSize {
    S(75), M(130), X(604), P(200),
    Q(320), R(510), Y(807), Z(1024), W(2048);
    private final int maxSize;

    PictureSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }
}
