package backend.academy.fractal.domain;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

class FractalImageTest {
    @Test
    public void testCreate_givenWidthAndHeight_returnsFractalImage() {
        int width = 10;
        int height = 10;
        FractalImage image = FractalImage.create(width, height);
        assertNotNull(image);
        assertEquals(width, image.width());
        assertEquals(height, image.height());
        assertEquals(width * height, image.data().length);
    }

    @Test
    public void testCreate_givenWidthHeightAndColor_returnsFractalImageWithColor() {
        int width = 10;
        int height = 10;
        Color color = Color.WHITE;
        FractalImage image = FractalImage.create(width, height, color);
        assertNotNull(image);
        assertEquals(width, image.width());
        assertEquals(height, image.height());
        assertEquals(width * height, image.data().length);
        for (Pixel pixel : image.data()) {
            assertEquals(color, pixel.color());
        }
    }

    @Test
    public void testContains_givenValidCoordinates_returnsTrue() {
        int width = 10;
        int height = 10;
        FractalImage image = FractalImage.create(width, height);
        assertTrue(image.contains(0, 0));
        assertTrue(image.contains(width - 1, height - 1));
    }

    @Test
    public void testContains_givenInvalidCoordinates_returnsFalse() {
        int width = 10;
        int height = 10;
        FractalImage image = FractalImage.create(width, height);
        assertFalse(image.contains(-1, 0));
        assertFalse(image.contains(0, -1));
        assertFalse(image.contains(width, 0));
        assertFalse(image.contains(0, height));
    }

    @Test
    public void testPixel_givenValidCoordinates_returnsPixel() {
        int width = 10;
        int height = 10;
        FractalImage image = FractalImage.create(width, height);
        Pixel pixel = image.pixel(5, 5);
        assertNotNull(pixel);
        assertEquals(5, pixel.x());
        assertEquals(5, pixel.y());
    }

    @Test
    public void testPixel_givenInvalidCoordinates_throwsIndexOutOfBoundsException() {
        int width = 10;
        int height = 10;
        FractalImage image = FractalImage.create(width, height);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            image.pixel(-1, 0);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            image.pixel(0, -1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            image.pixel(width, 0);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            image.pixel(0, height);
        });
    }
}
