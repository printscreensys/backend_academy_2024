package backend.academy.fractal.domain;

public record Point(double x, double y) {
    public boolean isInArea(double xMin, double xMax, double yMin, double yMax) {
        return x >= xMin && x <= xMax && y >= yMin && y <= yMax;
    }
}
