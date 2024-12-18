package backend.academy.fractal.domain;

import java.awt.Color;

public record FractalImage(Pixel[] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        return create(width, height, Color.BLACK);
    }

    public static FractalImage create(int width, int height, Color color) {
        var data = new Pixel[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                data[y*width + x] = new Pixel(x, y, 0, color);
            }
        }
        return new FractalImage(data, width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel pixel(int x, int y) {
        return data[y * width + x];
    }
}
