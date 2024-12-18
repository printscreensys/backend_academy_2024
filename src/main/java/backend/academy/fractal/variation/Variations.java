package backend.academy.fractal.variation;

import backend.academy.fractal.domain.Point;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public final class Variations {
    private Variations() {}

    public static Point linear(Point point) {
        return point;
    }

    public static Point sinusoidal(Point point) {
        var x = point.x();
        var y = point.y();
        var newX = Math.sin(x);
        var newY = Math.sin(y);

        return new Point(newX, newY);
    }

    public static Point spherical(Point point) {
        var x = point.x();
        var y = point.y();
        var r = 1.0 / (Math.pow(x, 2) + Math.pow(y, 2));
        var newX = x + r;
        var newY = y + r;

        return new Point(newX, newY);
    }

    public static Point swirl(Point point) {
        var x = point.x();
        var y = point.y();
        var r = Math.pow(x, 2) + Math.pow(y, 2);
        var newX = x * Math.sin(r) - y * Math.cos(r);
        var newY = x * Math.cos(r) + y * Math.sin(r);

        return new Point(newX, newY);
    }

    public static Point horseshoe(Point point) {
        var x = point.x();
        var y = point.y();
        var r = 1.0 / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        var newX = r * (x - y) * (x + y);
        var newY = r * 2.0 * x * y;

        return new Point(newX, newY);
    }

    public static Point spiral(Point point) {
        var x = point.x();
        var y = point.y();
        var r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        var theta = Math.atan2(y, x);
        var newX = (1.0 / r) * (Math.cos(theta) + Math.sin(r));
        var newY = (1.0 / r) * (Math.sin(theta) - Math.cos(r));

        return new Point(newX, newY);
    }

    public static Point blur(Point point) {
        var random = ThreadLocalRandom.current();
        var psy1 = random.nextDouble(0, 1);
        var psy2 = random.nextDouble(0, 1);
        var newX = psy1 * Math.cos(2 * Math.PI * psy2);
        var newY = psy1 * Math.sin(2 * Math.PI * psy2);

        return new Point(newX, newY);
    }

    public static Point gaussian(Point point) {
        var random = ThreadLocalRandom.current();
        var psiSum = IntStream.range(0, 4).mapToDouble(_ -> ThreadLocalRandom.current().nextDouble(0, 1)).sum() - 2;
        var psi5 = random.nextDouble(0, 1);
        var newX = psiSum * Math.cos(2 * Math.PI * psi5);
        var newY = psiSum * Math.sin(2 * Math.PI * psi5);

        return new Point(newX, newY);
    }
}
