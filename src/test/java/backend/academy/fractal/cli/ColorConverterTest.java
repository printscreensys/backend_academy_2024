package backend.academy.fractal.cli;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

class ColorConverterTest {
    private ColorConverter colorConverter = new ColorConverter();

    @Test
    public void testConvert_ValidString() {
        String validColor = "#FFFFFF"; // white color
        Color expectedColor = Color.WHITE;
        Color actualColor = colorConverter.convert(validColor);
        assertEquals(expectedColor, actualColor);
    }

    @Test
    public void testConvert_InvalidString() {
        String invalidColor = "invalidColor";
        assertThrows(RuntimeException.class, () -> {
            colorConverter.convert(invalidColor);
        });
    }
}
