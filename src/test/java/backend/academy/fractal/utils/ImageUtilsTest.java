package backend.academy.fractal.utils;

import java.awt.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageUtilsTest {
    @Test
    public void testAverageColor_givenTwoColors_returnsAverageColor() {
        Color color1 = new Color(255, 0, 0);
        Color color2 = new Color(0, 255, 0);
        Color expectedColor = new Color(127, 127, 0);
        Color actualColor = ImageUtils.averageColor(color1, color2);
        assertEquals(expectedColor, actualColor);
    }
}
