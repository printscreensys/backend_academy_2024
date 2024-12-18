package backend.academy.fractal.variation;

import backend.academy.fractal.domain.Point;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class VariationsTest {
    private static final double DELTA = 1e-15;

    @Test
    public void testLinear_givenPoint_returnsSamePoint() {
        Point point = new Point(1.0, 2.0);
        Point result = Variations.linear(point);
        assertEquals(point.x(), result.x(), DELTA);
        assertEquals(point.y(), result.y(), DELTA);
    }

    @Test
    public void testSinusoidal_givenPoint_returnsSinusoidalPoint() {
        Point point = new Point(1.0, 2.0);
        Point result = Variations.sinusoidal(point);
        assertEquals(Math.sin(1.0), result.x(), DELTA);
        assertEquals(Math.sin(2.0), result.y(), DELTA);
    }

    @Test
    public void testSpherical_givenPoint_returnsSphericalPoint() {
        Point point = new Point(1.0, 2.0);
        Point result = Variations.spherical(point);
        double r = 1.0 / (Math.pow(1.0, 2) + Math.pow(2.0, 2));
        assertEquals(1.0 + r, result.x(), DELTA);
        assertEquals(2.0 + r, result.y(), DELTA);
    }

    @Test
    public void testSwirl_givenPoint_returnsSwirlPoint() {
        Point point = new Point(1.0, 2.0);
        Point result = Variations.swirl(point);
        double r = Math.pow(1.0, 2) + Math.pow(2.0, 2);
        assertEquals(Math.sin(r) - 2.0 * Math.cos(r), result.x(), DELTA);
        assertEquals(Math.cos(r) + 2.0 * Math.sin(r), result.y(), DELTA);
    }

    @Test
    public void testHorseshoe_givenPoint_returnsHorseshoePoint() {
        Point point = new Point(1.0, 2.0);
        Point result = Variations.horseshoe(point);
        double r = 1.0 / Math.sqrt(Math.pow(1.0, 2) + Math.pow(2.0, 2));
        assertEquals(r * (1.0 - 2.0) * (1.0 + 2.0), result.x(), DELTA);
        assertEquals(r * 2.0 * 1.0 * 2.0, result.y(), DELTA);
    }

    @Test
    public void testSpiral_givenPoint_returnsSpiralPoint() {
        Point point = new Point(1.0, 2.0);
        Point result = Variations.spiral(point);
        double r = Math.sqrt(Math.pow(1.0, 2) + Math.pow(2.0, 2));
        double theta = Math.atan2(2.0, 1.0);
        assertEquals((1.0 / r) * (Math.cos(theta) + Math.sin(r)), result.x(), DELTA);
        assertEquals((1.0 / r) * (Math.sin(theta) - Math.cos(r)), result.y(), DELTA);
    }

    @Test
    public void testGaussian_givenPoint_returnsGaussianPoint() {
        ThreadLocalRandom random = mock(ThreadLocalRandom.class);
        Mockito.when(random.nextDouble(0, 1)).thenReturn(0.5);
        Point point = new Point(1.0, 2.0);
        Point result = Variations.gaussian(point);
        double psiSum = result.x() / Math.cos(2 * Math.PI * result.y() / result.x());
        assertTrue(psiSum >= -2 && psiSum <= 2);
    }
}
