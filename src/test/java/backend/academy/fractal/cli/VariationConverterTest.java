package backend.academy.fractal.cli;

import backend.academy.fractal.variation.Variation;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VariationConverterTest {
    private final VariationConverter converter = new VariationConverter();

    @Test
    public void testConvert_givenValidVariation_returnsVariation() {
        String validVariation = "gaussian";
        Variation variation = converter.convert(validVariation);
        assertNotNull(variation);
    }

    @Test
    public void testConvert_givenInvalidVariation_throwsParameterException() {
        String invalidVariation = "invalidVariation";
        assertThrows(ParameterException.class, () -> {
            converter.convert(invalidVariation);
        });
    }
}
